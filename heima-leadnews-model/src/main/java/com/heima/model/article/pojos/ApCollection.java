package com.heima.model.article.pojos;

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
 * APP收藏信息表
 * </p>
 *
 * @author 
 * @since 2024-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ap_collection")
@ApiModel(value="ApCollection对象", description="APP收藏信息表")
public class ApCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "实体ID")
    private Integer entryId;

    @ApiModelProperty(value = "文章ID")
    private Long articleId;

    @ApiModelProperty(value = "点赞内容类型 0文章 1动态")
    private Boolean type;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime collectionTime;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime publishedTime;
}
