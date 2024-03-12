package com.heima.model.article.vos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleBehaviorVo {
    Boolean islike;

    Boolean isunlike;

    Boolean iscollection;

    Boolean isfollow;
}
