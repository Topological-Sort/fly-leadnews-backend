package com.heima.article.controller;


import com.heima.article.service.IApCollectionService;
import com.heima.model.article.dtos.ArticleBehaviorDto;
import com.heima.model.article.dtos.ArticleCollectionDto;
import com.heima.model.article.vos.ArticleBehaviorVo;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * APP收藏信息表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-03-03
 */
@RestController
@RequestMapping("/api/v1")
public class ArticleBehaviorController {

    @Resource
    IApCollectionService apCollectionService;

    @PostMapping("/collection_behavior")
    public ResponseResult<Void> collect(@RequestBody ArticleCollectionDto dto){
        return apCollectionService.collect(dto);
    }

    @PostMapping("/article/load_article_behavior")
    public ResponseResult<ArticleBehaviorVo> loadArticleBehavior(@RequestBody ArticleBehaviorDto dto){
        return apCollectionService.loadArticleBehavior(dto);
    }
}
