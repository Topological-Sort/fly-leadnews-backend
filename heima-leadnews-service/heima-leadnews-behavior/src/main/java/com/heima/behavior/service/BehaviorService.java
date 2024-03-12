package com.heima.behavior.service;


import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.behavior.dtos.ReadBehaviorDto;
import com.heima.model.behavior.dtos.UnLikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

public interface BehaviorService {

    ResponseResult<Void> likesBehavior(LikesBehaviorDto dto);

    ResponseResult<Void> readBehavior(ReadBehaviorDto dto);

    ResponseResult<Void> unLikesBehavior(UnLikesBehaviorDto dto);
}
