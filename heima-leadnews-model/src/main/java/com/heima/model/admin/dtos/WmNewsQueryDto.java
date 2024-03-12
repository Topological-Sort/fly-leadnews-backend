package com.heima.model.admin.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WmNewsQueryDto extends PageRequestDto {
    Integer id;
    String msg;
    Integer status;
    String title;
}
