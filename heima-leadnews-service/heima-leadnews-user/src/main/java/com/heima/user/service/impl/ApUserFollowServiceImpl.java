package com.heima.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.constants.RedisConstants;
import com.heima.model.user.dtos.UserRelationDto;
import com.heima.model.user.pojos.ApUserFan;
import com.heima.model.user.pojos.ApUserFollow;
import com.heima.user.mapper.ApUserFanMapper;
import com.heima.user.mapper.ApUserFollowMapper;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.IApUserFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.utils.thread.ApThreadLocalUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * APP用户关注信息表 服务实现类
 * </p>
 *
 * @author
 * @since 2024-03-03
 */
@Service
public class ApUserFollowServiceImpl extends ServiceImpl<ApUserFollowMapper, ApUserFollow> implements IApUserFollowService {

    @Resource
    ApUserMapper apUserMapper;
    @Resource
    ApUserFollowMapper apUserFollowMapper;
    @Resource
    ApUserFanMapper apUserFanMapper;

    @Override
    @Transactional
    public ResponseResult<Void> userFollow(UserRelationDto dto) {
        if (dto == null || dto.getArticleId() == null || dto.getAuthorId() == null || dto.getOperation() == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        Integer userId = ApThreadLocalUtils.getUser().getId();
        if (dto.getOperation() == 0) {  // 关注
            apUserFollowMapper.insert(ApUserFollow.builder()
                    .userId(userId)
                    .followId(dto.getAuthorId())
                    .followName(apUserMapper.selectById(dto.getAuthorId()).getName())
                    .level((byte) 0)
                    .isNotice((byte) 0)
                    .createdTime(new Date())
                    .build());
            apUserFanMapper.insert(ApUserFan.builder()
                    .userId(dto.getAuthorId())
                    .fansId(userId)
                    .fansName(ApThreadLocalUtils.getUser().getName())
                    .level((byte) 0)
                    .isDisplay(true)
                    .isShieldLetter(true)
                    .isShieldComment(true)
                    .createdTime(new Date())
                    .build()
            );
        } else if(dto.getOperation() == 1){ // 取关
            apUserFollowMapper.delete(new LambdaQueryWrapper<ApUserFollow>()
                    .eq(ApUserFollow::getFollowId, dto.getAuthorId())
                    .eq(ApUserFollow::getUserId, userId));
            apUserFanMapper.delete(new LambdaQueryWrapper<ApUserFan>()
                    .eq(ApUserFan::getUserId, dto.getAuthorId())
                    .eq(ApUserFan::getFansId, userId));
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        return ResponseResult.okResult();
    }


    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public ResponseResult<Void> follow(UserRelationDto dto) {
        if (dto == null || dto.getArticleId() == null || dto.getAuthorId() == null || dto.getOperation() == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        String userId = ApThreadLocalUtils.getUser().getId().toString();
        String authorId = dto.getAuthorId().toString();
        String Follows = RedisConstants.USER_FOLLOWS + userId;
        String Fans = RedisConstants.USER_FANS + authorId;
        if(dto.getOperation() == 1) {
            stringRedisTemplate.opsForZSet().add(Follows, authorId, new Date().getTime());
            stringRedisTemplate.opsForZSet().add(Fans, userId, new Date().getTime());
        } else if (dto.getOperation() == 0) {
            stringRedisTemplate.opsForZSet().remove(Follows, authorId);
            stringRedisTemplate.opsForZSet().remove(Fans, userId);
        } else{
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        return ResponseResult.okResult();
    }
}
