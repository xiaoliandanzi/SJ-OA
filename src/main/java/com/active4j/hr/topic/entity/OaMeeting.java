package com.active4j.hr.topic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author weizihao
 * @since 2021-01-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="OaMeeting对象", description="")
public class OaMeeting implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "0 未开始  1进行中 2 已结束")
    @TableField("STATE_ID")
    private Integer stateId;

    @ApiModelProperty(value = "使用科室")
    @TableField("DEPT_ID")
    private String deptId;

    @ApiModelProperty(value = "登记人ID")
    @TableField("Registrant_ID")
    private String registrantId;

    @ApiModelProperty(value = "会议时间")
    @TableField("Meeting_Time")
    private Date meetingTime;

    @ApiModelProperty(value = "会议室ID")
    @TableField("Meeting_ID")
    private String meetingId;

    @ApiModelProperty(value = "议题会议类型")
    @TableField("Meeting_type")
    private String meetingType;

    @ApiModelProperty(value = "会议名称")
    @TableField("Meeting_Name")
    private String meetingName;

    @ApiModelProperty(value = "参加人员")
    @TableField("CONFEREE")
    private String conferee;

    @ApiModelProperty(value = "议题ID")
    @TableField("ISSUE_ID")
    private Long issueId;

    @ApiModelProperty(value = "备注")
    @TableField("MEMO")
    private String memo;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFY_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;


}
