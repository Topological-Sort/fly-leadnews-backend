package com.heima.admin.controller;


import com.heima.admin.service.IAdUserService;
import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 管理员用户信息表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/admin")
public class AdUserController {

    @Autowired
    IAdUserService adUserService;

    @PostMapping("/login/in")
    public ResponseResult<Map<String, Object>> login(@RequestBody AdUserDto dto){
        return adUserService.login(dto);
    }
}
