package com.active4j.hr.car.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/11/19 上午12:50
 */
@TableName("OA_CAR")
@Getter
@Setter
public class OaCarEntity extends BaseEntity {

    private static final long serialVersionUID = -8245456083851005068L;

    @TableField("CARID")
    @NotEmpty(message = "车牌不能为空")
    private String carId;

    @TableField("NAME")
    @NotEmpty(message = "车辆名称不能为空")
    private String name;

    @TableField("STATUS")
    @NotEmpty(message = "状态不能为空")
    private String status;

    @TableField("PERSONS")
    private int persons;

    @TableField("MEMO")
    private String memo;
}
