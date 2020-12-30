package com.active4j.hr.activiti.biz.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/12/8 下午11:16
 */
@TableName("flow_car_approval")
@Getter
@Setter
public class FlowCarApprovalEntity extends BaseEntity {
    private static final long serialVersionUID = -6396749047428505450L;

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
