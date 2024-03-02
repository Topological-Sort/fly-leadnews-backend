package com.heima.wemedia.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsEnableDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;

import java.util.List;

/**
 * <p>
 * 自媒体图文内容信息表 服务类
 * </p>
 *
 * @author 
 * @since 2024-02-19
 */
public interface IWmNewsService extends IService<WmNews> {

    ResponseResult<List<WmNews>> pageQuery(WmNewsPageReqDto dto);

    ResponseResult<Integer> submitNews(WmNewsDto dto);

    ResponseResult<Void> updateEnable(WmNewsEnableDto dto);
}
