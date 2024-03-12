package com.heima.model.behavior.dtos;


import lombok.Data;

@Data
public class LikesBehaviorDto {
    Long articleId;

    Integer operation;

    Integer type;
}
