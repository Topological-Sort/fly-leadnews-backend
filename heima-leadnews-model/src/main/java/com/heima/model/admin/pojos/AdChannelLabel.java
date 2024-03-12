package com.heima.model.admin.pojos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 频道标签信息表
 * </p>
 *
 * @author 
 * @since 2024-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ad_channel_label")
@ApiModel(value="AdChannelLabel对象", description="频道标签信息表")
public class AdChannelLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer channelId;

    @ApiModelProperty(value = "标签ID")
    private Integer labelId;

    @ApiModelProperty(value = "排序")
    private Integer ord;


}
