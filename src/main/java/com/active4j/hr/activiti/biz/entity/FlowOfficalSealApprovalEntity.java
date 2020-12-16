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
     * 开始日期
     */
    @TableField("START_DAY")
    private Date startDay;

    /**
     * 结束日期
     */
    @TableField("END_DAY")
    private Date endDay;
}
