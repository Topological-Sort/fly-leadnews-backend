package com.heima.wemedia.feign;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heima.apis.wemedia.IWemediaClient;
import com.heima.model.admin.dtos.WmNewsCheckDto;
import com.heima.model.admin.dtos.WmNewsQueryDto;
import com.heima.model.admin.vos.WmNewsQueryVo;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.admin.dtos.WmChannelQueryDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.admin.dtos.WmSensitiveQueryDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmSensitive;
import com.heima.wemedia.service.IWmChannelService;
import com.heima.wemedia.service.IWmNewsService;
import com.heima.wemedia.service.IWmSensitiveService;
import com.heima.wemedia.service.WmUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WemediaClient implements IWemediaClient {
    @Resource
    IWmChannelService wmChannelService;
    @Resource
    IWmSensitiveService wmSensitiveService;
    @Resource
    IWmNewsService wmNewsService;
    @Resource
    WmUserService wmUserService;

    /* Channel */
    @Override
    @Transactional
    public ResponseResult<Integer> saveChannel(WmChannel wmChannel) {
        WmChannel channel = wmChannelService.getOne(new LambdaQueryWrapper<WmChannel>()
                .eq(WmChannel::getName, wmChannel.getName()));
        if(channel != null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST, "频道名称重复");
        }
        wmChannelService.save(wmChannel);
        return ResponseResult.okResult(wmChannel.getId());
    }

    @Override
    public ResponseResult<Void> deleteChannel(Integer id) {
        wmChannelService.removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<List<WmChannel>> queryChannel(WmChannelQueryDto dto) {
        dto.checkParam();
        Page<WmChannel> page = wmChannelService.page(new Page<>(dto.getPage(), dto.getSize()),
                new LambdaQueryWrapper<WmChannel>()
                        .eq(dto.getStatus() != null, WmChannel::getStatus, dto.getStatus())
                        .orderByDesc(WmChannel::getCreatedTime)
                        .like(StringUtils.isNotBlank(dto.getName()), WmChannel::getName, "%" + dto.getName() + "%"));
        long total = page.getTotal();
        List<WmChannel> records = page.getRecords();
        return ResponseResult.okResult(records);
    }

    @Override
    public ResponseResult<Void> updateChannel(WmChannel wmChannel) {
        wmChannelService.updateById(wmChannel);
        return ResponseResult.okResult();
    }

    /* Sensitive */
    @Override
    public ResponseResult<Void> deleteSensitive(Integer id) {
        wmSensitiveService.removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public PageResponseResult<List<WmSensitive>> querySensitive(WmSensitiveQueryDto dto) {
        dto.checkParam();
        Page<WmSensitive> page = wmSensitiveService.page(
                new Page<>(dto.getPage(), dto.getSize()),
                new LambdaQueryWrapper<WmSensitive>()
                        .like(StringUtils.isNotBlank(dto.getName()), WmSensitive::getSensitives, dto.getName())
                        .orderByDesc(WmSensitive::getCreatedTime));
        PageResponseResult<List<WmSensitive>> res = new PageResponseResult<>(dto.getPage(), dto.getSize(), (int) page.getTotal());
        res.setData(page.getRecords());
        return res;
    }

    @Override
    public ResponseResult<Integer> saveSensitive(WmSensitive wmSensitive) {
        wmSensitiveService.save(wmSensitive);
        return ResponseResult.okResult(wmSensitive.getId());
    }

    @Override
    public ResponseResult<Void> updateSensitive(WmSensitive wmSensitive) {
        wmSensitiveService.updateById(wmSensitive);
        return ResponseResult.okResult();
    }

    /* News */
    @Override
    @Transactional
    public PageResponseResult<List<WmNewsQueryVo>> queryNews(WmNewsQueryDto dto) {
        dto.checkParam();
        Page<WmNews> page = wmNewsService.page(
                new Page<>(dto.getPage(), dto.getSize()),
                new LambdaQueryWrapper<WmNews>()
                        .eq(dto.getStatus() != null, WmNews::getStatus, dto.getStatus())
                        .like(StringUtils.isNotBlank(dto.getTitle()), WmNews::getTitle, "%" + dto.getTitle() + "%")
                        .orderByDesc(WmNews::getCreatedTime));
        List<WmNewsQueryVo> records = page.getRecords().stream()
                .map(o -> BeanUtil.copyProperties(o, WmNewsQueryVo.class))
                .peek(o -> o.setAuthorName(wmUserService.getById(o.getUserId()).getName()))
                .collect(Collectors.toList());
        PageResponseResult<List<WmNewsQueryVo>> res = new PageResponseResult<>(
                dto.getPage(), dto.getSize(), (int) page.getTotal()
        );
        res.setData(records);
        return res;
    }

    @Override
    public ResponseResult<WmNewsQueryVo> getNewsDetail(Integer id) {
        WmNews news = wmNewsService.getById(id);
        WmNewsQueryVo vo = BeanUtil.copyProperties(news, WmNewsQueryVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult<Void> newsFail(WmNewsCheckDto dto) {
        if(dto == null || dto.getId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "要审核的文章不存在");
        }
        wmNewsService.update(new LambdaUpdateWrapper<WmNews>()
                .set(WmNews::getStatus, 2)  // 2: 人工审核失败
                .set(StringUtils.isNotBlank(dto.getMsg()), WmNews::getReason, dto.getMsg())
                .eq(WmNews::getId, dto.getId()));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<Void> newsPass(WmNewsCheckDto dto) {
        if(dto == null || dto.getId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "要审核的文章不存在");
        }
        wmNewsService.update(new LambdaUpdateWrapper<WmNews>()
                .set(WmNews::getStatus, 4)  // 4: 人工审核通过
                .set(WmNews::getReason, "审核通过")
                .eq(WmNews::getId, dto.getId()));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<List<WmChannel>> listChannels() {
        return ResponseResult.okResult(wmChannelService.list());
    }
}