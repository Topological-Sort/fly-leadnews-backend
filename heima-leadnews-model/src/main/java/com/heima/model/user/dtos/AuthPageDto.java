package com.heima.model.user.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthPageDto extends PageRequestDto {
    String msg;
    Short status;
}
