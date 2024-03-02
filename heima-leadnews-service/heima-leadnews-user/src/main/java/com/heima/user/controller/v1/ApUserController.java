package com.heima.user.controller.v1;


import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.LoginDto;
import com.heima.user.service.IApUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * APP用户信息表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-02-16
 */
@RestController
@RequestMapping("/api/v1/login")
public class ApUserController {

    @Resource
    IApUserService apUserService;

    @PostMapping("/login_auth")
    public ResponseResult<Map<String, Object>> login(@RequestBody LoginDto loginDto) {
        return apUserService.login(loginDto);
    }

}
