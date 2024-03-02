package com.heima.model.wemedia.pojos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 自媒体图文引用素材信息表
 * </p>
 *
 * @author 
 * @since 2024-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wm_news_material")
@ApiModel(value="WmNewsMaterial对象", description="自媒体图文引用素材信息表")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WmNewsMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "素材ID")
    private Integer materialId;

    @ApiModelProperty(value = "图文ID")
    private Integer newsId;

    @ApiModelProperty(value = "引用类型 0 内容引用 1 主图引用")
    private Boolean type;

    @ApiModelProperty(value = "引用排序")
    private Boolean ord;


}
