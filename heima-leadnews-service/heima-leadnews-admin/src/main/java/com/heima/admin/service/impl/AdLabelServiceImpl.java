package com.heima.admin.service.impl;

import com.heima.admin.mapper.AdLabelMapper;
import com.heima.admin.service.IAdLabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.admin.pojos.AdLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签信息表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-03-02
 */
@Service
public class AdLabelServiceImpl extends ServiceImpl<AdLabelMapper, AdLabel> implements IAdLabelService {
    @Autowired
    AdLabelMapper adLabelMapper;

}
