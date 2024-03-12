package com.heima.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heima.apis.user.IUserClient;
import com.heima.article.mapper.ApCollectionMapper;
import com.heima.article.service.IApArticleService;
import com.heima.article.service.IApCollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.article.dtos.ArticleBehaviorDto;
import com.heima.model.article.dtos.ArticleCollectionDto;
import com.heima.model.article.pojos.ApCollection;
import com.heima.model.article.vos.ArticleBehaviorVo;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.constants.RedisConstants;
import com.heima.model.user.dtos.CheckFollowDto;
import com.heima.utils.thread.ApThreadLocalUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * APP收藏信息表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-03-03
 */
@Service
public class ApCollectionServiceImpl extends ServiceImpl<ApCollectionMapper, ApCollection> implements IApCollectionService {

    @Resource
    ApCollectionMapper apCollectionMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    IUserClient userClient;

    @Override
    public ResponseResult<Void> collect(ArticleCollectionDto dto) {
        if(dto == null || dto.getEntryId() == null || dto.getOperation() == null || dto.getType() == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        Integer userId = ApThreadLocalUtils.getUser().getId();
        if(dto.getType() == 0) { // 收藏
            stringRedisTemplate.opsForSet().add(RedisConstants.ARTICLE_COLLECTIONS + dto.getEntryId(), userId.toString());
//            apCollectionMapper.insert(ApCollection.builder()  // 消息队列/异步线程
//                    .userId(userId)
//                    .articleId(dto.getEntryId())
//                    .type(dto.getType())
//                    .publishedTime(new Date())
//                    .build()
//            );
        } else if(dto.getType() == 1) { // 取消收藏
            stringRedisTemplate.opsForSet().remove(RedisConstants.ARTICLE_COLLECTIONS + dto.getEntryId(), userId.toString());
//            apCollectionMapper.delete(new LambdaQueryWrapper<ApCollection>()  // 消息队列/异步线程
//                    .eq(ApCollection::getUserId, ApThreadLocalUtils.getUser().getId())
//                    .eq(ApCollection::getArticleId, dto.getEntryId()));
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<ArticleBehaviorVo> loadArticleBehavior(ArticleBehaviorDto dto) {
        if(dto == null || dto.getArticleId() == null || dto.getAuthorId() == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        Integer userId = ApThreadLocalUtils.getUser().getId();
        ArticleBehaviorVo res = ArticleBehaviorVo.builder()
                .iscollection(stringRedisTemplate.opsForSet().isMember(RedisConstants.ARTICLE_COLLECTIONS + dto.getArticleId(), userId.toString()))
                .islike(stringRedisTemplate.opsForZSet().score(RedisConstants.ARTICLE_LIKES + dto.getArticleId(), userId.toString()) != null)
                .isunlike(stringRedisTemplate.opsForSet().isMember(RedisConstants.ARTICLE_UNLIKES + dto.getArticleId(), userId.toString()))
                .isfollow(stringRedisTemplate.opsForZSet().score(RedisConstants.USER_FOLLOWS + userId, dto.getAuthorId()) != null)
                .build();
        return ResponseResult.okResult(res);
    }
}
