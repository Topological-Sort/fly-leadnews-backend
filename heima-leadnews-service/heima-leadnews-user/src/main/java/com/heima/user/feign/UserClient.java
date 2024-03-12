package com.heima.user.feign;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heima.apis.user.IUserClient;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.AuthPageDto;
import com.heima.model.user.dtos.CheckFollowDto;
import com.heima.model.user.pojos.ApUserFollow;
import com.heima.model.user.pojos.ApUserRealname;
import com.heima.user.service.IApUserFollowService;
import com.heima.user.service.IApUserRealnameService;
import com.heima.user.service.IApUserService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserClient implements IUserClient {

    @Resource
    IApUserRealnameService apUserRealnameService;
    @Resource
    IApUserFollowService apUserFollowService;
    @Resource
    IApUserService apUserService;

    @Override
    public PageResponseResult<List<ApUserRealname>> pageQuery(AuthPageDto dto) {
        Page<ApUserRealname> page = apUserRealnameService.page(
                new Page<>(dto.getPage(), dto.getSize()),
                new LambdaQueryWrapper<ApUserRealname>()
                        .eq(dto.getStatus() != null, ApUserRealname::getStatus, dto.getStatus()));
        PageResponseResult<List<ApUserRealname>> res =
                new PageResponseResult<>(dto.getPage(), dto.getSize(), (int) page.getTotal());
        res.setData(page.getRecords());
        return res;
    }

    @Override
    public ResponseResult<Void> authFail(AuthPageDto dto) {
        changeStatus(dto, false);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<Void> authPass(AuthPageDto dto) {
        changeStatus(dto, true);
        return ResponseResult.okResult();
    }

    @Override
    public boolean isFollow(CheckFollowDto dto) {
        ApUserFollow res = apUserFollowService.getOne(new LambdaQueryWrapper<ApUserFollow>()
                .eq(ApUserFollow::getUserId, dto.getUserId())
                .eq(ApUserFollow::getFollowId, dto.getFollowId()));
        return res != null;
    }

    public void changeStatus(AuthPageDto dto, boolean status){
        Page<ApUserRealname> page = apUserRealnameService.page(
                new Page<>(dto.getPage(), dto.getSize()),
                new LambdaQueryWrapper<ApUserRealname>()
                        .eq(dto.getStatus() != null, ApUserRealname::getStatus, dto.getStatus())
                        .orderByDesc(ApUserRealname::getCreatedTime));
        List<ApUserRealname> records = page.getRecords();
        records = records.stream().peek(o -> o.setStatus(status)).collect(Collectors.toList());
        apUserRealnameService.updateBatchById(records);
    }
}
