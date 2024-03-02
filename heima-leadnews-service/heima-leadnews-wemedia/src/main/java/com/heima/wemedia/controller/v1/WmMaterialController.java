package com.heima.wemedia.controller.v1;


import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.wemedia.service.IWmMaterialService;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 自媒体图文素材信息表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-02-19
 */
@RestController
@RequestMapping("/api/v1/material")
public class WmMaterialController {

    @Resource
    IWmMaterialService materialService;

    @PostMapping("/upload_picture")
    public ResponseResult<WmMaterial> uploadPicture(MultipartFile multipartFile){
        return materialService.uploadPicture(multipartFile);
    }

    @PostMapping("/list")
    public PageResponseResult<List<WmMaterial>> pageList(@RequestBody WmMaterialDto dto){
        return materialService.pageList(dto);
    }
}
