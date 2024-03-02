package com.heima.model.wemedia.pojos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 自媒体图文数据统计表
 * </p>
 *
 * @author 
 * @since 2024-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wm_news_statistics")
@ApiModel(value="WmNewsStatistics对象", description="自媒体图文数据统计表")
public class WmNewsStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "主账号ID")
    private Integer userId;

    @ApiModelProperty(value = "子账号ID")
    private Integer article;

    @ApiModelProperty(value = "阅读量")
    private Integer readCount;

    @ApiModelProperty(value = "评论量")
    private Integer comment;

    @ApiModelProperty(value = "关注量")
    private Integer follow;

    @ApiModelProperty(value = "收藏量")
    private Integer collection;

    @ApiModelProperty(value = "转发量")
    private Integer forward;

    @ApiModelProperty(value = "点赞量")
    private Integer likes;

    @ApiModelProperty(value = "不喜欢")
    private Integer unlikes;

    @ApiModelProperty(value = "取消关注量")
    private Integer unfollow;

    private String burst;

    @ApiModelProperty(value = "创建时间")
    private LocalDate createdTime;


}
