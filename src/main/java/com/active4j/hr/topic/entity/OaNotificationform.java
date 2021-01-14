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
 * @since 2021-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="OaNotificationform对象", description="")
public class OaNotificationform implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "部门ID")
    private String depid;

    @ApiModelProperty(value = "部门名字")
    private String depname;

    @ApiModelProperty(value = "人员id")
    private String nameid;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "操作状态")
    private String status;

    @ApiModelProperty(value = "操作状态")
    private String huiyiid;


    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFY_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;

    @TableField(value = "huiyidate")
    private String huiyidate;

    @TableField(value = "huiyihome")
    private String huiyihome;


    @TableField(exist = false)
    private String ids;

}
