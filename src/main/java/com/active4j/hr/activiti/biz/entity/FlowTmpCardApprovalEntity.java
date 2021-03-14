package com.active4j.hr.activiti.biz.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2021/01/07/23:55
 * @Description:
 */
@Setter
@Getter
@TableName("flow_tmpcard_approval")
public class FlowTmpCardApprovalEntity extends BaseEntity {


    private static final long serialVersionUID = 1146739798463217903L;
    /**
     * 使用科室
     */
    @TableField("DEPARTMENTNAME")
    private String departmentName;

    /**
     * 借用人
     */
    @TableField("USERNAME")
    private String userName;

    /**
     * 餐卡名称
     */
    @TableField("CARDNAME")
    private String cardName;

    /**
     * 数量
     */
    @TableField("QUANTITY")
    private Integer quantity;



    /**
     * 使用日期
     */
    @TableField("USE_DAY")
    private Date useDay;

    /**
     * 多规格数据
     */
    @TableField("JSON_DATA")
    private String jsonData;

    /**
     * 使用事由
     */
    @TableField("REASON")
    private String reason;


    /**
     * 备注
     */
    @TableField("COMMIT")
    private String commit;


    /**
     * 审批状态 0:审批中 1：审批完成 2：驳回
     */
    @TableField("APPLY_STATUS")
    private Integer applyStatus;

    /**
     * 归还状态 0:已归还 1:未归还
     */
    @TableField("RETURN_FLAG")
    private Integer returnFlag;
}


