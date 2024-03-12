package com.heima.behavior.controller.v1;


import com.heima.behavior.service.BehaviorService;
import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.behavior.dtos.ReadBehaviorDto;
import com.heima.model.behavior.dtos.UnLikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1")
public class BehaviorController {

    @Resource
    BehaviorService behaviorService;

    @PostMapping("/likes_behavior")
    public ResponseResult<Void> likesBehavior(@RequestBody LikesBehaviorDto dto){
        return behaviorService.likesBehavior(dto);
    }

    @PostMapping("/read_behavior")
    public ResponseResult<Void> readBehavior(@RequestBody ReadBehaviorDto dto){
        return behaviorService.readBehavior(dto);
    }

    @PostMapping("/un_likes_behavior")
    public ResponseResult<Void> unLikesBehavior(@RequestBody UnLikesBehaviorDto dto){
        return behaviorService.unLikesBehavior(dto);
    }

}
