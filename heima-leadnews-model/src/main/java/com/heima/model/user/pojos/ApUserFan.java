package com.heima.model.user.pojos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * APP用户粉丝信息表
 * </p>
 *
 * @author 
 * @since 2024-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ap_user_fan")
@ApiModel(value="ApUserFan对象", description="APP用户粉丝信息表")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApUserFan implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "粉丝ID")
    private Integer fansId;

    @ApiModelProperty(value = "粉丝昵称")
    private String fansName;

    @ApiModelProperty(value = "粉丝忠实度 0 正常 1 潜力股 2 勇士 3 铁杆 4 老铁")
    private Byte level;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "是否可见我动态")
    private Boolean isDisplay;

    @ApiModelProperty(value = "是否屏蔽私信")
    private Boolean isShieldLetter;

    @ApiModelProperty(value = "是否屏蔽评论")
    private Boolean isShieldComment;


}
