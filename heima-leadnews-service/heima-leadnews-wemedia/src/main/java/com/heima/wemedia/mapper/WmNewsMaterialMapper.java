package com.heima.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmNewsMaterial;

import java.util.List;

/**
 * <p>
 * 自媒体图文引用素材信息表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-02-19
 */
public interface WmNewsMaterialMapper extends BaseMapper<WmNewsMaterial> {

    void insertBatches(List<Integer> matIds, Integer newsId, Short type);
}
