package com.active4j.hr.activiti.biz.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/12/14/23:55
 * @Description:
 */
@Setter
@Getter
@TableName("flow_officalseal_approval")
public class FlowOfficalSealApprovalEntity extends BaseEntity {
    private static final long serialVersionUID = 5559860786984311762L;


    /**
     * 科室
     */
    @TableField("DEPARTMENTNAME")
    private String departmentName;

    /**
     * 借用人
     */
    @TableField("USERNAME")
    private String userName;

    /**
     * 主送单位
     */
    @TableField("USEUNIT")
    private String useUnit;

    /**
     * 内容
     */
    @TableField("CONTENT")
    private String content;

    /**
     * 备注
     */
    @TableField("COMMIT")
    private String commit;

    /**
     * 使用日期
     */
    @TableField("USE_DAY")
    private Date useDay;

//    /**
//     * 结束日期
//     */
//    @TableField("END_DAY")
//    private Date endDay;

    /**
     * 审批状态 0:审批中 1：审批完成 2：驳回
     */
    @TableField("APPLY_STATUS")
    private Integer applyStatus;

    /**
     * 归还状态 0:已归还
     */
    @TableField("RETURN_FLAG")
    private Integer returnFlag;

    /**
     *
     * 公章类型：办事处章/工委章
     * */
    @TableField("SEALTYPE")
    private String sealtype;
}
