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
     * 品名
     */
    @TableField("ITEMNAME")
    private String itemName;

    /**
     * 品牌规格 型号
     */
    @TableField("TYPE")
    private String type;

    /**
     * 数量
     */
    @TableField("NUMBER")
    private Integer number;

    /**
     * 单价
     */
    @TableField("UNITPRICE")
    private Integer unitPrice;

    /**
     * 单价
     */
    @TableField("COUNT")
    private Integer count;

    /**
     * 用途
     */
    @TableField("PURPOSE")
    private String purpose;

    /**
     * 总计（元）
     */
    @TableField("PRICE")
    private Integer price;

    /**
     * 资金来源
     */
    @TableField("FUNDSOURCE")
    private String foundSource;

    /**
     * 审批状态 0:审批中 1：确认中 2：采购中 3：驳回
     */
    @TableField("APPLYSTATUS")
    private Integer applyStatus;

    /**
     * 多规格数据
     */
    @TableField("JSON_DATA")
    private String json_data;
}
