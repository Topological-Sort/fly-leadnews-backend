package com.heima.model.user.pojos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * APP实名认证信息表
 * </p>
 *
 * @author 
 * @since 2024-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ap_user_realname")
@ApiModel(value="ApUserRealname对象", description="APP实名认证信息表")
public class ApUserRealname implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "账号ID")
    private Integer userId;

    @ApiModelProperty(value = "用户名称")
    private String name;

    @ApiModelProperty(value = "资源名称")
    private String idno;

    @ApiModelProperty(value = "正面照片")
    private String fontImage;

    @ApiModelProperty(value = "背面照片")
    private String backImage;

    @ApiModelProperty(value = "手持照片")
    private String holdImage;

    @ApiModelProperty(value = "活体照片")
    private String liveImage;

    @ApiModelProperty(value = "状态 0 创建中 1 待审核 2 审核失败 9 审核通过")
    private Boolean status;

    @ApiModelProperty(value = "拒绝原因")
    private String reason;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "提交时间")
    private LocalDateTime submitedTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedTime;


}
