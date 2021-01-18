package com.active4j.hr.activiti.biz.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;


/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2021/01/18/23:55
 * @Description:
 */
@Setter
@Getter
@TableName("flow_procurement_approval")
public class FlowProcurementApprovalEntity  extends BaseEntity {


    /**
     * 采购部门
     */
    @TableField("DEPARTMENTNAME")
    private String departmentName;

    /**
     * 经办人
     */
    @TableField("AGENT")
    private String agent;

    /**
     * 电话
     */
    @TableField("TELEPHONE")
    private String telephone;

    /**
     * 总计（元）
     */
    @TableField("PRICE")
    private Integer price;


    /**
     * 审批状态 0:审批中 1：确认中 2：采购中 3：驳回
     */
    @TableField("APPLY_STATUS")
    private Integer applyStatus;

}
