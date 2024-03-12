package com.heima.model.article.vos;

import com.heima.model.article.pojos.ApArticle;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleVo extends ApArticle {
    /**
     * 文章分值
     */
    private Integer score;
}