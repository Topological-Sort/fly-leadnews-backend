package com.heima.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * <p>
 * 文章信息表，存储已发布的文章 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-02-17
 */
public interface ApArticleMapper extends BaseMapper<ApArticle> {


    public List<ApArticle> queryArticles(@Param("dto") ArticleHomeDto dto, @Param("type") Short type);

}
