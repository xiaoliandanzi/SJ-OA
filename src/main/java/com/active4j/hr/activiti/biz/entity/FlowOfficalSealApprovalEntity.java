package com.active4j.hr.activiti.biz.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/12/14/23:55
 * @Description:
 */
public class FlowOfficalSealApprovalEntity extends BaseEntity {
    private static final long serialVersionUID = 5559860786984311762L;

    /**
     * 乘车人
     */
    @TableField("USERNAME")
    private String userName;

    /**
     * 用车事由
     */
    @TableField("REASON")
    private String reason ;

    /**
     * 乘车人数
     */
    @TableField("PERSON")
    private Integer person;

    /**
     * 地点
     */
    @TableField("DESTINATION")
    private String destination;

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
