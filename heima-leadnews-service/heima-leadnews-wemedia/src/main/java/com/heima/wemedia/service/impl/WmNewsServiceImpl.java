package com.heima.wemedia.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heima.apis.article.IArticleClient;
import com.heima.common.constants.WemediaConstants;
import com.heima.common.constants.WmNewsConstants;
import com.heima.common.constants.WmNewsMessageConstants;
import com.heima.common.exception.CustomException;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsEnableDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmNewsMaterial;
import com.heima.utils.thread.WmThreadLocalUtils;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.mapper.WmNewsMaterialMapper;
import com.heima.wemedia.service.IWmNewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.wemedia.service.WmNewsAutoScanService;
import com.heima.wemedia.service.WmNewsTaskService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 自媒体图文内容信息表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-02-19
 */
@Service
@Transactional
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements IWmNewsService {

    @Resource
    WmNewsMaterialMapper newsMaterialMapper;
    @Resource
    WmMaterialMapper materialMapper;
    @Resource
    WmNewsMapper newsMapper;
    @Resource
    WmNewsAutoScanService autoScanService;
    @Resource
    WmNewsTaskService wmNewsTaskService;
    @Resource
    IArticleClient articleClient;

    @Override
    public ResponseResult<List<WmNews>> pageQuery(WmNewsPageReqDto dto) {
        dto.checkParam();  // pageQueryParamCheck
        Page<WmNews> result = this.page(new Page<>(dto.getPage(), dto.getSize()),
                new LambdaQueryWrapper<WmNews>()
                        .eq(WmNews::getUserId, WmThreadLocalUtils.getUser().getId())
                        .eq(dto.getStatus() != null, WmNews::getStatus, dto.getStatus())
                        .eq(dto.getChannelId() != null, WmNews::getChannelId, dto.getChannelId())
                        .ge(dto.getBeginPubDate() != null, WmNews::getPublishTime, dto.getBeginPubDate())
                        .le(dto.getEndPubDate() != null, WmNews::getPublishTime, dto.getEndPubDate())
                        .like(StringUtils.isNotBlank(dto.getKeyword()), WmNews::getTitle, "%" + dto.getKeyword() + "%")
                        .orderByDesc(WmNews::getPublishTime));
        if(result == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST, "无记录");
        PageResponseResult<List<WmNews>> res = new PageResponseResult<>(dto.getPage(), dto.getSize(), (int) result.getTotal());
        res.setData(result.getRecords());
        return res;
    }

    @Override
    public ResponseResult<Integer> submitNews(WmNewsDto dto) {
        /* 1.校验参数 */
        if (dto == null || dto.getContent() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        /* 2.WmNews 新闻记录添加 */
        WmNews news = new WmNews();
        BeanUtils.copyProperties(dto, news);  // 若dto中有id，则是修改，否则是新增
        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            String imgStr = org.apache.commons.lang3.StringUtils.join(dto.getImages(), ",");
            news.setImages(imgStr);  // list --> String [dto --> news]
        }
        if (dto.getType().equals(WemediaConstants.WM_NEWS_TYPE_AUTO)) {
            news.setType(null);
        }
        saveOrUpdateNews(news, WmThreadLocalUtils.getUser().getId());  // save后获取文章id
        if(dto.getStatus().equals((short)0)){  // 0：草稿，直接结束
            return ResponseResult.okResult(null);
        }
        Integer newsId = news.getId();
        /* 3.提取素材图片 (两种图片：images / content -| [{ type: "image", value: ~ }, ...]) */
        List<String> contentImages = extractContentImages(dto);
        /* 4.更新news封面信息，并保存引用记录到关联表：news <--> [news_material] <--> materials */
        saveRefForMain(news, dto, contentImages);     // dto.getImages（封面图，设置自动时从contentImages中提取）
        saveRefForContent(news, dto, contentImages);  // content -| [{ type: image, value: ~ }, ...]（内容插图）
        /* 5.异步审核 / 发送任务到redis */
//        autoScanService.autoScanWmNews(newsId);
        wmNewsTaskService.addNewsToTask(news.getId(), news.getPublishTime());
        return ResponseResult.okResult(newsId);
    }


    @Resource
    KafkaTemplate<String, String> kafkaTemplate;
    @Override
    public ResponseResult<Void> updateEnable(WmNewsEnableDto dto) {
//        this.lambdaUpdate()
//                .setSql("enable = " + dto.getEnable())
//                .eq(WmNews::getId, dto.getId());
        if(dto == null || dto.getId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        WmNews news = newsMapper.selectById(dto.getId());
        if(news == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "文章不存在");
        }
        if(!news.getStatus().equals(WmNews.Status.PUBLISHED.getCode())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "文章暂未发布，无法上下架");
        }
        if(dto.getEnable() != null && dto.getEnable() >= 0 && dto.getEnable() <= 1) {
            this.lambdaUpdate()
                    .set(WmNews::getEnable, dto.getEnable() == 1 ? 0 : 1)
                    .eq(WmNews::getId, dto.getId());
        }
        //发送消息，通知article端修改文章配置
        if(news.getArticleId() != null){
            Map<String,Object> map = new HashMap<>();
            map.put("articleId",news.getArticleId());
            map.put("enable",dto.getEnable());
            kafkaTemplate.send(WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_TOPIC,
                    JSON.toJSONString(map));
        }
        return ResponseResult.okResult(null);
    }

    private void saveOrUpdateNews(WmNews news, Integer userId) {
        /* 补全属性 */
        news.setUserId(userId);
        news.setCreatedTime(new Date());
        news.setSubmitedTime(new Date());
        news.setEnable((short)1);  // 默认发布
        /* 保存或修改 */
        if(news.getId() == null){
            this.save(news);  // save后自动设置news的id
        } else {     // 修改：删除原文章图片与素材的关系
            newsMaterialMapper.delete(new LambdaQueryWrapper<WmNewsMaterial>()
                    .eq(WmNewsMaterial::getNewsId, news.getId()));
            updateById(news);
        }
    }

    private List<String> extractContentImages(WmNewsDto dto) {
        List<String> contentImages = new ArrayList<>();
        List<Map> contents = JSON.parseArray(dto.getContent(), Map.class);
        for (Map content : contents) {
            if(content.get("type").equals("image")){
                contentImages.add((String) content.get("value"));
            }
        }
        return contentImages;
    }

    private void saveRefForMain(WmNews news, WmNewsDto dto, List<String> contentImages) {
        /* 用户设置了的封面图 */
        List<String> images = dto.getImages();
        /* 设置“自动”类型的封面图：从内容的图片中抽取 */
        if(dto.getType().equals(WemediaConstants.WM_NEWS_TYPE_AUTO)){
            if(contentImages.size() >= 3){  // 多图 --> 3 图
                news.setType(WemediaConstants.WM_NEWS_MANY_IMAGE);
                images = contentImages.stream().limit(3).collect(Collectors.toList());
            }
            else if(contentImages.size() >= 1){ // 单图
                news.setType(WemediaConstants.WM_NEWS_SINGLE_IMAGE);
                images = contentImages.stream().limit(1).collect(Collectors.toList());
            }
            else{  // 无图
                news.setType(WemediaConstants.WM_NEWS_NONE_IMAGE);
            }
        }
        /* 保存引用数组到news, 并更新news */
        if(images != null && !images.isEmpty()) {
            news.setImages(StringUtils.join(images, ","));
        }
        this.updateById(news);
        /* 保存关联：news <--> [news_material] <--> materials */
        saveRelativeInfo(news.getId(), images, WemediaConstants.WM_COVER_REFERENCE);
    }

    private void saveRefForContent(WmNews news, WmNewsDto dto, List<String> contentImages) {
        saveRelativeInfo(news.getId(), contentImages, WemediaConstants.WM_CONTENT_REFERENCE);
    }

    private void saveRelativeInfo(Integer newsId, List<String> images, Short type) {
        List<WmMaterial> materials = materialMapper.selectList(new LambdaQueryWrapper<WmMaterial>()
                .in(images != null && !images.isEmpty(), WmMaterial::getUrl, images));
        if(materials == null || materials.isEmpty()){
            //手动抛出异常: 1.能够提示调用者素材失效了 2.进行数据的回滚
            throw new CustomException(AppHttpCodeEnum.MATERIAL_REFERENCE_FAIL);
        }
        if(materials.size() != images.size()){  // 引用的素材有部分失效
            throw new CustomException(AppHttpCodeEnum.MATERIAL_REFERENCE_FAIL);
        }
        List<Integer> matIds = materials.stream().map(WmMaterial::getId).collect(Collectors.toList());
        newsMaterialMapper.insertBatches(matIds, newsId, type);
    }

}
