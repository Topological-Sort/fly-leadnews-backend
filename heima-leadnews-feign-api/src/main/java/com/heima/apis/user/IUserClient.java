package com.heima.apis.user;

import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.AuthPageDto;
import com.heima.model.user.dtos.CheckFollowDto;
import com.heima.model.user.pojos.ApUserRealname;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("leadnews-user")
public interface IUserClient {

    @PostMapping("/api/v1/auth/list")
    public PageResponseResult<List<ApUserRealname>> pageQuery(@RequestBody AuthPageDto dto);

    @PostMapping("/api/v1/auth/authFail")
    ResponseResult<Void> authFail(@RequestBody AuthPageDto dto);

    @PostMapping("/api/v1/auth/authPass")
    ResponseResult<Void> authPass(@RequestBody AuthPageDto dto);

    @PostMapping("/api/v1/auth/isFollow")
    boolean isFollow(@RequestBody CheckFollowDto dto);
}