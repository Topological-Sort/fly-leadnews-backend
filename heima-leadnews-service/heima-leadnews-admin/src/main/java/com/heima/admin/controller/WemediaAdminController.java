package com.heima.admin.controller;


import com.heima.apis.wemedia.IWemediaClient;
import com.heima.model.admin.dtos.WmNewsCheckDto;
import com.heima.model.admin.dtos.WmNewsQueryDto;
import com.heima.model.admin.vos.WmNewsQueryVo;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.admin.dtos.WmChannelQueryDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.admin.dtos.WmSensitiveQueryDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.model.wemedia.pojos.WmSensitive;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author PW
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/wemedia")
public class WemediaAdminController {

    @Resource
    IWemediaClient wemediaClient;

    /* Channel Administrate */
    @PostMapping("/api/v1/channel/save")
    public ResponseResult<Integer> saveChannel(@RequestBody WmChannel wmChannel){
        return wemediaClient.saveChannel(wmChannel);
    }

    @GetMapping("/api/v1/channel/delete/{id}")
    public ResponseResult<Void> deleteChannel(@PathVariable Integer id){
        return wemediaClient.deleteChannel(id);
    }

    @PostMapping("/api/v1/channel/list")
    public ResponseResult<List<WmChannel>> queryChannel(@RequestBody WmChannelQueryDto dto){
        return wemediaClient.queryChannel(dto);
    }

    @PostMapping("/api/v1/channel/update")
    public ResponseResult<Void> queryChannel(@RequestBody WmChannel wmChannel){
        return wemediaClient.updateChannel(wmChannel);
    }

    /* Sensitive Administrate */
    @DeleteMapping("/api/v1/sensitive/del/{id}")
    public ResponseResult<Void> deleteSensitive(@PathVariable Integer id){
        return wemediaClient.deleteSensitive(id);
    }

    @PostMapping("/api/v1/sensitive/list")
    public PageResponseResult<List<WmSensitive>> querySensitive(@RequestBody WmSensitiveQueryDto dto){
        return wemediaClient.querySensitive(dto);
    }

    @PostMapping("/api/v1/sensitive/save")
    public ResponseResult<Integer> saveSensitive(@RequestBody WmSensitive wmSensitive){
        return wemediaClient.saveSensitive(wmSensitive);
    }

    @PostMapping("/api/v1/sensitive/update")
    public ResponseResult<Void> updateSensitive(@RequestBody WmSensitive wmSensitive){
        return wemediaClient.updateSensitive(wmSensitive);
    }

    /* News Administrate */
    @PostMapping("/api/v1/news/list_vo")
    public PageResponseResult<List<WmNewsQueryVo>> listNews(@RequestBody WmNewsQueryDto dto){
        return wemediaClient.queryNews(dto);
    }

    @GetMapping("/api/v1/news/one_vo/{id}")
    public ResponseResult<WmNewsQueryVo> getNewsDetail(@PathVariable Integer id){
        return wemediaClient.getNewsDetail(id);
    }

    @PostMapping("/api/v1/news/auth_fail")
    public ResponseResult<Void> newsFail(@RequestBody WmNewsCheckDto dto){
        return wemediaClient.newsFail(dto);
    }

    @PostMapping("/api/v1/news/auth_pass")
    public ResponseResult<Void> newsPass(@RequestBody WmNewsCheckDto dto){
        return wemediaClient.newsPass(dto);
    }
}
