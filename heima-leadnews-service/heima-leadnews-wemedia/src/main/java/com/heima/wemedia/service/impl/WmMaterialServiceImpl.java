package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heima.file.service.FileStorageService;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.utils.thread.WmThreadLocalUtils;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.service.IWmMaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 自媒体图文素材信息表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-02-19
 */
@Service
@Slf4j
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements IWmMaterialService {

    @Resource
    FileStorageService fileStorageService;
    @Resource
    WmMaterialMapper materialMapper;

    @Override
    public ResponseResult<WmMaterial> uploadPicture(MultipartFile multipartFile) {
        //1.检查参数
        if(multipartFile == null || multipartFile.getSize() == 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.上传图片到minIO中
        String fileName = UUID.randomUUID().toString().replace("-", "");
        //aa.jpg
        String originalFilename = multipartFile.getOriginalFilename();
        String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileId = null;
        try {
            fileId = fileStorageService.uploadImgFile("", fileName + postfix, multipartFile.getInputStream());
            log.info("上传图片到MinIO中，fileId:{}",fileId);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("WmMaterialServiceImpl-上传文件失败");
        }

        //3.保存到数据库中
        WmMaterial wmMaterial = new WmMaterial();
        wmMaterial.setUserId(WmThreadLocalUtils.getUser().getId());
        wmMaterial.setUrl(fileId);
        wmMaterial.setIsCollection((short)0);  // 是否收藏
        wmMaterial.setType((short)0);  // 0 :图片，1 :视频
        wmMaterial.setCreatedTime(new Date());
        this.save(wmMaterial);  // save: 插入后entity自动设置id，insert不行

        //4.返回结果
        return ResponseResult.okResult(wmMaterial);
    }

    @Override
    public PageResponseResult<List<WmMaterial>> pageList(WmMaterialDto dto) {
        dto.checkParam();
        Page<WmMaterial> page = this.page(new Page<>(dto.getPage(), dto.getSize()),
                new LambdaQueryWrapper<WmMaterial>()
                        .eq(dto.getIsCollection() != null, WmMaterial::getIsCollection, dto.getIsCollection())
                        .orderByDesc(WmMaterial::getCreatedTime));
        List<WmMaterial> records = page.getRecords();
        PageResponseResult<List<WmMaterial>> result = new PageResponseResult<>(dto.getPage(), dto.getSize(), (int)page.getTotal());
        result.setData(records);
        return result;
    }
}
