package com.heima.model.admin.pojos;

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
 * 标签信息表
 * </p>
 *
 * @author 
 * @since 2024-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ad_label")
@ApiModel(value="AdLabel对象", description="标签信息表")
public class AdLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "频道名称")
    private String name;

    @ApiModelProperty(value = "0系统增加 1人工增加")
    private Boolean type;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdTime;


}
