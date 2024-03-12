package com.heima.apis.wemedia;

import com.heima.model.admin.dtos.WmNewsCheckDto;
import com.heima.model.admin.dtos.WmNewsQueryDto;
import com.heima.model.admin.vos.WmNewsQueryVo;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.admin.dtos.WmChannelQueryDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.admin.dtos.WmSensitiveQueryDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.model.wemedia.pojos.WmSensitive;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("leadnews-wemedia")
public interface IWemediaClient {

    /* Channel */
    @PostMapping("/api/v1/channel/save")
    public ResponseResult<Integer> saveChannel(@RequestBody WmChannel wmChannel);

    @GetMapping("/api/v1/channel/del/{id}")
    public ResponseResult<Void> deleteChannel(@PathVariable Integer id);

    @PostMapping("/api/v1/channel/list")
    public ResponseResult<List<WmChannel>> queryChannel(@RequestBody WmChannelQueryDto dto);

    @PostMapping("/api/v1/channel/update")
    public ResponseResult<Void> updateChannel(@RequestBody WmChannel wmChannel);

    /* Sensitive */
    @DeleteMapping("/api/v1/sensitive/del/{id}")
    public ResponseResult<Void> deleteSensitive(@PathVariable Integer id);

    @PostMapping("/api/v1/sensitive/list")
    public PageResponseResult<List<WmSensitive>> querySensitive(@RequestBody WmSensitiveQueryDto dto);

    @PostMapping("/api/v1/sensitive/save")
    public ResponseResult<Integer> saveSensitive(@RequestBody WmSensitive wmSensitive);

    @PostMapping("/api/v1/sensitive/update")
    public ResponseResult<Void> updateSensitive(@RequestBody WmSensitive wmSensitive);

    /* News */
    @PostMapping("/api/v1/news/list_vo")
    public PageResponseResult<List<WmNewsQueryVo>> queryNews(@RequestBody WmNewsQueryDto dto);

    @PostMapping("/api/v1/news/one_vo/{id}")
    ResponseResult<WmNewsQueryVo> getNewsDetail(@PathVariable Integer id);

    @PostMapping("/api/v1/news/news/auth_fail")
    ResponseResult<Void> newsFail(@RequestBody WmNewsCheckDto dto);

    @PostMapping("/api/v1/news/news/auth_pass")
    ResponseResult<Void> newsPass(@RequestBody WmNewsCheckDto dto);

    /* HotNews */
    // channels
    @GetMapping("/api/v1/channel/list")
    ResponseResult<List<WmChannel>> listChannels();
}