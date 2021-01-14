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
    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "")
    @TableField("STATE_ID")
    private String stateId;



    @ApiModelProperty(value = "使用科室")
    @TableField("DEPT_NAME")
    private String deptName;

    @ApiModelProperty(value = "科室id")
    @TableField("depId")
    private String depId;


    @ApiModelProperty(value = "登记人")
    @TableField("Registrant_NAME")
    private String registrantName;


    @ApiModelProperty(value = "会议时间")
    @TableField("Meeting_Time")
    private String meetingTime;

    @ApiModelProperty(value = "会议时间")
    @TableField("Meeting_endTime")
    private String meetingendTime;




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

    @ApiModelProperty(value = "参加人员ID")
    @TableField("CONFEREEID")
    private String confereeid;



    @ApiModelProperty(value = "议题ID")
    @TableField("ISSUE_ID")
    private String issueId;

    @ApiModelProperty(value = "备注")
    @TableField("MEMO")
    private String memo;



    @ApiModelProperty(value = "参会")
    @TableField("canhuipeo")
    private String canhuipeo;


    @TableField(exist = false)
    private String  ids;


    @TableField(exist = false)
    private String  canHuitype;



    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFY_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;


}
