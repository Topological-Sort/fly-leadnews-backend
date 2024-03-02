package com.heima.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.LoginDto;
import com.heima.model.user.pojos.ApUser;

import java.util.Map;

/**
 * <p>
 * APP用户信息表 服务类
 * </p>
 *
 * @author 
 * @since 2024-02-16
 */
public interface IApUserService extends IService<ApUser> {

    ResponseResult<Map<String, Object>> login(LoginDto loginDto);
}
