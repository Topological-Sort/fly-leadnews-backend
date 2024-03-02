package com.heima.wemedia.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 自媒体图文素材信息表 服务类
 * </p>
 *
 * @author 
 * @since 2024-02-19
 */
public interface IWmMaterialService extends IService<WmMaterial> {

    ResponseResult<WmMaterial> uploadPicture(MultipartFile multipartFile);

    PageResponseResult<List<WmMaterial>> pageList(WmMaterialDto dto);
}
