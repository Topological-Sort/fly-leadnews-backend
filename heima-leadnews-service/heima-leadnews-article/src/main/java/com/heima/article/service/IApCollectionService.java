package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.article.dtos.ArticleBehaviorDto;
import com.heima.model.article.dtos.ArticleCollectionDto;
import com.heima.model.article.pojos.ApCollection;
import com.heima.model.article.vos.ArticleBehaviorVo;
import com.heima.model.common.dtos.ResponseResult;

/**
 * <p>
 * APP收藏信息表 服务类
 * </p>
 *
 * @author 
 * @since 2024-03-03
 */
public interface IApCollectionService extends IService<ApCollection> {

    ResponseResult<Void> collect(ArticleCollectionDto dto);

    ResponseResult<ArticleBehaviorVo> loadArticleBehavior(ArticleBehaviorDto dto);
}
