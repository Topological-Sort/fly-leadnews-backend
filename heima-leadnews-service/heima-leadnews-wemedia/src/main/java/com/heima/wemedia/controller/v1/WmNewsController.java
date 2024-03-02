package com.heima.wemedia.controller.v1;


import com.heima.common.constants.WmNewsConstants;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsEnableDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.service.IWmNewsService;
import com.heima.wemedia.service.WmNewsAutoScanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 自媒体图文内容信息表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-02-19
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/news")
public class WmNewsController {

    @Resource
    IWmNewsService newsService;
    @Resource
    WmNewsAutoScanService autoScanService;

    @PostMapping("/list")
    public ResponseResult<List<WmNews>> list(@RequestBody WmNewsPageReqDto dto){
        log.info(StringUtils.repeat("-", 100));
        log.info("【Wm】分页查询文章：{}", dto);
        log.info(StringUtils.repeat("-", 100));
        return newsService.pageQuery(dto);
    }

    @PostMapping("/submit")
    public ResponseResult<Integer> submitNews(@RequestBody WmNewsDto dto){
        log.info(StringUtils.repeat("-", 100));
        log.info("【Wm】上传文章(并异步审核)：{}", dto);
        log.info(StringUtils.repeat("-", 100));
        return ResponseResult.okResult(newsService.submitNews(dto));  // 提交并异步审核文章
    }

    @PostMapping("/down_or_up")
    public ResponseResult<Void> downOrUp(@RequestBody WmNewsEnableDto dto){
        log.info(StringUtils.repeat("-", 100));
        log.info("【Wm】上架/下架文章：{}", dto);
        log.info(StringUtils.repeat("-", 100));
        return newsService.updateEnable(dto);
    }

}
