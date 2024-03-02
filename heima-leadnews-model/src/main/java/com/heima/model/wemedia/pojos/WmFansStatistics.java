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
 * 自媒体粉丝数据统计表
 * </p>
 *
 * @author 
 * @since 2024-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wm_fans_statistics")
@ApiModel(value="WmFansStatistics对象", description="自媒体粉丝数据统计表")
public class WmFansStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "主账号ID")
    private Integer userId;

    @ApiModelProperty(value = "子账号ID")
    private Integer article;

    private Integer readCount;

    private Integer comment;

    private Integer follow;

    private Integer collection;

    private Integer forward;

    private Integer likes;

    private Integer unlikes;

    private Integer unfollow;

    private String burst;

    @ApiModelProperty(value = "创建时间")
    private LocalDate createdTime;


}
