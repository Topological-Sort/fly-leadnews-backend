package com.heima.user.controller.v1;


import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dtos.UserRelationDto;
import com.heima.model.user.pojos.ApUserFollow;
import com.heima.user.service.IApUserFanService;
import com.heima.user.service.IApUserFollowService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * APP用户关注信息表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-03-03
 */
@RestController
@RequestMapping("api/v1/user/")
public class ApUserFollowController {

    @Resource
    IApUserFollowService apUserFollowService;
    @Resource
    IApUserFanService apUserFanService;

//    /**
//     * 用户关注，DB版本
//     * @param dto 文章id、作者id、操作类型（0：关注，1：取关）
//     * @return 无
//     */
//    @PostMapping("/user_follow")
//    public ResponseResult<Void> userFollow(@RequestBody UserRelationDto dto){
//        return apUserFollowService.userFollow(dto);
//    }

    /**
     * 用户关注，Cache版本
     * @param dto 文章id、作者id、操作类型（0：关注，1：取关）
     * @return 无
     */
    @PostMapping("/user_follow")
    public ResponseResult<Void> userFollow(@RequestBody UserRelationDto dto){
        return apUserFollowService.follow(dto);
    }
}
