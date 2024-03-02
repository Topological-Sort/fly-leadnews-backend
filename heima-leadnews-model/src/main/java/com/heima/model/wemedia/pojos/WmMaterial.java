package com.heima.model.wemedia.pojos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 自媒体图文素材信息表
 * </p>
 *
 * @author 
 * @since 2024-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wm_material")
@ApiModel(value="WmMaterial对象", description="自媒体图文素材信息表")
public class WmMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "自媒体用户ID")
    private Integer userId;

    @ApiModelProperty(value = "图片地址")
    private String url;

    @ApiModelProperty(value = "素材类型 0 图片 1 视频")
    private Short type;

    @ApiModelProperty(value = "是否收藏")
    private Short isCollection;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;


}
