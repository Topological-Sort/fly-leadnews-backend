package com.heima.model.article.dtos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DateTimeDto {
    @TableField(typeHandler = LocalDateTimeTypeHandler.class)
    private LocalDateTime dateTime;
}