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
 * APP用户关注信息表
 * </p>
 *
 * @author 
 * @since 2024-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ap_user_follow")
@ApiModel(value="ApUserFollow对象", description="APP用户关注信息表")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApUserFollow implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "关注作者ID")
    private Integer followId;

    @ApiModelProperty(value = "粉丝昵称")
    private String followName;

    @ApiModelProperty(value = "关注度 0 偶尔感兴趣 1 一般 2 经常 3 高度")
    private Byte level;

    @ApiModelProperty(value = "是否动态通知")
    private Byte isNotice;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;


}
