package com.heima.model.article.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleCollectionDto {
    // 文章id
    Long entryId;
    // 0：收藏   1：取消收藏
    Integer operation;
    // 发布时间
    Date publishedTime;
    // 0：文章   1：动态
    Integer type;
}
