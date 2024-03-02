package com.heima.wemedia.controller.v1;


import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.wemedia.service.IWmChannelService;
import com.heima.wemedia.service.IWmMaterialService;
import com.heima.wemedia.service.IWmNewsMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 频道信息表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-02-19
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/channel")
public class WmChannelController {

    @Resource
    IWmChannelService channelService;

    @GetMapping("/channels")
    public ResponseResult<List<WmChannel>> list(){
        List<WmChannel> channels = channelService.list();
        return ResponseResult.okResult(channels);
    }

}
