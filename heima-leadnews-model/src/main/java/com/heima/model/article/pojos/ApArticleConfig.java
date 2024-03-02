package com.heima.model.article.pojos;

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
 * APP已发布文章配置表
 * </p>
 *
 * @author 
 * @since 2024-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ap_article_config")
@ApiModel(value="ApArticleConfig对象", description="APP已发布文章配置表")
public class ApArticleConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    public ApArticleConfig(Long articleId){
        this.articleId = articleId;
        this.isComment = true;
        this.isForward = true;
        this.isDelete = false;
        this.isDown = false;
    }

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "文章ID")
    private Long articleId;

    @ApiModelProperty(value = "是否可评论")
    private Boolean isComment;

    @ApiModelProperty(value = "是否转发")
    private Boolean isForward;

    @ApiModelProperty(value = "是否下架")
    private Boolean isDown;

    @ApiModelProperty(value = "是否已删除")
    private Boolean isDelete;


}
