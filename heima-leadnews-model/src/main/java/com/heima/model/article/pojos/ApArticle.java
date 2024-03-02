package com.heima.model.article.pojos;

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
 * 文章信息表，存储已发布的文章
 * </p>
 *
 * @author 
 * @since 2024-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ap_article")
@ApiModel(value="ApArticle对象", description="文章信息表，存储已发布的文章")
public class ApArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "文章作者的ID")
    private Integer authorId;

    @ApiModelProperty(value = "作者昵称")
    private String authorName;

    @ApiModelProperty(value = "文章所属频道ID")
    private Integer channelId;

    @ApiModelProperty(value = "频道名称")
    private String channelName;

    @ApiModelProperty(value = "文章布局 0 无图文章 1 单图文章 2 多图文章")
    private Short layout;

    @ApiModelProperty(value = "文章标记 0 普通文章 1 热点文章 2 置顶文章 3 精品文章 4 大V 文章")
    private Integer flag;

    @ApiModelProperty(value = "文章图片 多张逗号分隔")
    private String images;

    @ApiModelProperty(value = "文章标签最多3个 逗号分隔")
    private String labels;

    @ApiModelProperty(value = "点赞数量")
    private Integer likes;

    @ApiModelProperty(value = "收藏数量")
    private Integer collection;

    @ApiModelProperty(value = "评论数量")
    private Integer comment;

    @ApiModelProperty(value = "阅读数量")
    private Integer views;

    @ApiModelProperty(value = "省市")
    private Integer provinceId;

    @ApiModelProperty(value = "市区")
    private Integer cityId;

    @ApiModelProperty(value = "区县")
    private Integer countyId;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "发布时间")
    private Date publishTime;

    @ApiModelProperty(value = "同步状态")
    private Short syncStatus;

    @ApiModelProperty(value = "来源")
    private Short origin;

    private String staticUrl;

}
