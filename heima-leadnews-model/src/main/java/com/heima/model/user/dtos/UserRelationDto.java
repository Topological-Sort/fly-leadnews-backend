package com.heima.model.user.dtos;

import lombok.Data;

@Data
public class UserRelationDto {
    Long articleId;

    Integer authorId;

    Integer operation;
}
