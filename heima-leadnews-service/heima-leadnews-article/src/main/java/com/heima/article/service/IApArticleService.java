package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.dtos.DateTimeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;
import io.lettuce.core.dynamic.annotation.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 文章信息表，存储已发布的文章 服务类
 * </p>
 *
 * @author 
 * @since 2024-02-17
 */
public interface IApArticleService extends IService<ApArticle> {

    ResponseResult<List<ApArticle>> load(Short loadtype, ArticleHomeDto dto);

    ResponseResult<Long> saveOrUpdateArticle(ArticleDto dto);

    /**
     * 获取n天前的数据
     * @param dateTime
     * @return
     */
    ResponseResult<List<ApArticle>> getAll(@Param("dateTime") LocalDateTime dateTime);

    ResponseResult<List<ApArticle>> load2(Short loadtypeLoadMore, ArticleHomeDto dto, boolean firstPage);
}
