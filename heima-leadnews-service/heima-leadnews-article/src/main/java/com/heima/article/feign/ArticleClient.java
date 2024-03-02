package com.heima.article.feign;

import com.heima.apis.article.IArticleClient;
import com.heima.article.service.IApArticleService;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ArticleClient implements IArticleClient {

    @Resource
    private IApArticleService articleService;

    @Override
    @PostMapping("/api/v1/article/save")
    public ResponseResult<Long> saveArticle(@RequestBody ArticleDto dto) {
        return articleService.saveOrUpdateArticle(dto);
    }
}