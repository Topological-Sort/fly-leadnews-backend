package com.heima.article.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heima.article.mapper.ApArticleConfigMapper;
import com.heima.article.mapper.ApArticleContentMapper;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ArticleFreemarkerService;
import com.heima.article.service.IApArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.constants.ArticleConstants;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.dtos.DateTimeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.pojos.ApArticleConfig;
import com.heima.model.article.pojos.ApArticleContent;
import com.heima.model.article.vos.HotArticleVo;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 文章信息表，存储已发布的文章 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-02-17
 */
@Service
@Transactional
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements IApArticleService {
    // 单页最大加载的数字
    private final static short MAX_PAGE_SIZE = 50;

    @Resource
    ApArticleMapper articleMapper;
    @Resource
    ApArticleConfigMapper articleConfigMapper;
    @Resource
    ApArticleContentMapper articleContentMapper;
    @Resource
    ArticleFreemarkerService articleFreemarkerService;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    /**
     * 根据参数加载文章列表
     * @param loadtype 1为加载更多  2为加载最新
     * @param dto
     * @return
     */
    @Override
    public ResponseResult<List<ApArticle>> load(Short loadtype, ArticleHomeDto dto) {
        //1.校验参数
        Integer size = dto.getSize();
        if(size == null || size == 0){
            size = 10;
        }
        size = Math.min(size, MAX_PAGE_SIZE);
        dto.setSize(size);
        //类型参数检验
        if(!loadtype.equals(ArticleConstants.LOADTYPE_LOAD_MORE)&&!loadtype.equals(ArticleConstants.LOADTYPE_LOAD_NEW)){
            loadtype = ArticleConstants.LOADTYPE_LOAD_MORE;
        }
        //文章频道校验
        if(StringUtils.isEmpty(dto.getTag())){
            dto.setTag(ArticleConstants.DEFAULT_TAG);
        }
        //时间校验
        if(dto.getMaxBehotTime() == null) dto.setMaxBehotTime(new Date());
        if(dto.getMinBehotTime() == null) dto.setMinBehotTime(new Date());
        //2.查询数据
        List<ApArticle> apArticles = articleMapper.queryArticles(dto, loadtype);
        return ResponseResult.okResult(apArticles);
    }

    @Override
    public ResponseResult<Long> saveOrUpdateArticle(ArticleDto dto) {
        if(dto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        ApArticle article = new ApArticle();
        BeanUtils.copyProperties(dto, article);
        // 更新或新增
        if(article.getId() != null && articleMapper.selectById(article.getId()) != null) {  // update
            this.updateById(article);
            ApArticleContent content = articleContentMapper.selectOne(
                    new LambdaQueryWrapper<ApArticleContent>()
                            .eq(ApArticleContent::getArticleId, article.getId()));
            content.setContent(dto.getContent());
            articleContentMapper.updateById(content);
        } else {  // save
            this.save(article);  // save后自动设置文章id
            articleConfigMapper.insert(new ApArticleConfig(article.getId()));
            ApArticleContent content = new ApArticleContent();
            content.setArticleId(article.getId());
            content.setContent(dto.getContent());
            articleContentMapper.insert(content);
        }
        //异步调用 生成静态文件上传到minio中
        articleFreemarkerService.buildArticleToMinIO(article, dto.getContent());
        return ResponseResult.okResult(article.getId());
    }

    @Override
    public ResponseResult<List<ApArticle>> getAll(LocalDateTime dateTime) {
        return ResponseResult.okResult(articleMapper.getAll(dateTime));
    }

    @Override
    public ResponseResult load2(Short loadtypeLoadMore, ArticleHomeDto dto, boolean firstPage) {
        if(firstPage){
            String Key = ArticleConstants.HOT_ARTICLE_FIRST_PAGE + dto.getTag();
            String res = stringRedisTemplate.opsForValue().get(Key);
            List<HotArticleVo> r = JSON.parseArray(res, HotArticleVo.class);
            return ResponseResult.okResult(r);
        }
        return this.load(loadtypeLoadMore, dto);
    }
}
