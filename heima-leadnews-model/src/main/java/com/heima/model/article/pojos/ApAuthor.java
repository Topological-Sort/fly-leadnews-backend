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
 * APP文章作者信息表
 * </p>
 *
 * @author 
 * @since 2024-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ap_author")
@ApiModel(value="ApAuthor对象", description="APP文章作者信息表")
public class ApAuthor implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "作者名称")
    private String name;

    @ApiModelProperty(value = "0 爬取数据 1 签约合作商 2 平台自媒体人 ")
    private Boolean type;

    @ApiModelProperty(value = "社交账号ID")
    private Integer userId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "自媒体账号")
    private Integer wmUserId;


}
