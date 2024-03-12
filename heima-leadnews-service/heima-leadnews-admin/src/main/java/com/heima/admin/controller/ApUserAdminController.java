package com.heima.admin.controller;

import com.heima.apis.user.IUserClient;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.AuthPageDto;
import com.heima.model.user.pojos.ApUserRealname;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping
public class ApUserAdminController {

    @Resource
    IUserClient userClient;

    @PostMapping("/user/api/v1/auth/list")
    public PageResponseResult<List<ApUserRealname>> pageQuery(@RequestBody AuthPageDto dto){
        return userClient.pageQuery(dto);
    }
    @PostMapping("/api/v1/auth/authFail")
    public ResponseResult<Void> authFail(@RequestBody AuthPageDto dto){
        return userClient.authFail(dto);
    }

    @PostMapping("/api/v1/auth/authPass")
    public ResponseResult<Void> authPass(@RequestBody AuthPageDto dto){
        return userClient.authPass(dto);
    }
}
