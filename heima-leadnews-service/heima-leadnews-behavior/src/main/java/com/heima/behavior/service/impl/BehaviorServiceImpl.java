package com.heima.behavior.service.impl;

import com.heima.behavior.service.BehaviorService;
import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.behavior.dtos.ReadBehaviorDto;
import com.heima.model.behavior.dtos.UnLikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.constants.RedisConstants;
import com.heima.utils.thread.ApThreadLocalUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class BehaviorServiceImpl implements BehaviorService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public ResponseResult<Void> likesBehavior(LikesBehaviorDto dto) {
        if (dto == null || dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        String Key;
        if (dto.getType() == 0)   // 文章
            Key = RedisConstants.ARTICLE_LIKES + dto.getArticleId();
        else if (dto.getType() == 1)    // 动态
            Key = RedisConstants.DYNAMIC_LIKES + dto.getArticleId();
        else  // 评论
            Key = RedisConstants.COMMENT_LIKES + dto.getArticleId();
        String userId = ApThreadLocalUtils.getUser().getId().toString();
        if (dto.getOperation() == 0) {  // 点赞
            stringRedisTemplate.opsForZSet().add(Key, userId, new Date().getTime());
        } else if (dto.getOperation() == 1) {  // 取消点赞
            stringRedisTemplate.opsForZSet().remove(Key, userId);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<Void> readBehavior(ReadBehaviorDto dto) {
        if (dto == null || dto.getArticleId() == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        String Key = RedisConstants.ARTICLE_VIEWS + dto.getArticleId();
        String userId = ApThreadLocalUtils.getUser().getId().toString();
//        stringRedisTemplate.opsForHash().put(Key, userId, dto.getCount().toString());
        stringRedisTemplate.opsForSet().add(Key, userId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<Void> unLikesBehavior(UnLikesBehaviorDto dto) {
        if (dto == null || dto.getArticleId() == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        String Key = RedisConstants.ARTICLE_UNLIKES + dto.getArticleId();
        String userId = ApThreadLocalUtils.getUser().getId().toString();
        if (dto.getType() == 0) { // 不喜欢
            stringRedisTemplate.opsForSet().add(Key, userId);
        } else if (dto.getType() == 1) {  // 取消不喜欢
            stringRedisTemplate.opsForSet().remove(Key, userId);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        return ResponseResult.okResult();
    }

}
