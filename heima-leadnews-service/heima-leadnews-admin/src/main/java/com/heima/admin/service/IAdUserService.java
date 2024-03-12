package com.heima.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.common.dtos.ResponseResult;

import java.util.Map;

/**
 * <p>
 * 管理员用户信息表 服务类
 * </p>
 *
 * @author 
 * @since 2024-03-02
 */
public interface IAdUserService extends IService<AdUser> {

    ResponseResult<Map<String, Object>> login(AdUserDto dto);
}
