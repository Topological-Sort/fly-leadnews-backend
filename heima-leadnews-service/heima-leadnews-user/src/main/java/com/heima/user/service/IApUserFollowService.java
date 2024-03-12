package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.UserRelationDto;
import com.heima.model.user.pojos.ApUserFollow;

/**
 * <p>
 * APP用户关注信息表 服务类
 * </p>
 *
 * @author 
 * @since 2024-03-03
 */
public interface IApUserFollowService extends IService<ApUserFollow> {

    ResponseResult<Void> userFollow(UserRelationDto dto);

    ResponseResult<Void> follow(UserRelationDto dto);
}
