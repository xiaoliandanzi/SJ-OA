package com.active4j.hr.officalSeal.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/20/9:50
 * @Description:
 */
@TableName("OA_OFFICAL_SEAL")
@Getter
@Setter
public class OaOfficalSealEntity extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1906861960116263816L;

    @TableField("DEPARTMENTID")
    @NotEmpty(message = "部门编号不能为空")
    private String departmentId;

    // 流程状态 0:借用中 1:可借用: 2:暂停借用
    @TableField("STATUS")
    @NotEmpty(message = "状态不能为空")
    @QueryField(queryColumn="STATUS", condition= QueryCondition.eq)
    private String status;

    @TableField("DEPARTMENTNAME")
    @NotEmpty(message = "部门名称不能为空")
    private String name;

    @TableField("OVERTIMEDAYS")
    private Integer overtimedays;

    @TableField("MEMO")
    private String memo;

}