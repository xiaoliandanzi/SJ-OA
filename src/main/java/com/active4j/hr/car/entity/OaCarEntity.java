package com.active4j.hr.car.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

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

    @TableField("KIND")
    @NotEmpty(message = "车辆使用状态不能为空")
    private String kind;

    @TableField("ONROADTIME")
    @NotEmpty(message = "车辆购置时间不能为空")
    private Date onRoadTime;

    @TableField("CHECKCARTIME")
    @NotEmpty(message = "验车日期不能为空")
    private Date checkCarTime;

    @TableField("ENSURETIME")
    @NotEmpty(message = "保险到期时间不能为空")
    private Date ensureTime;

//    @TableField("ENSUREDAY")
//    @NotEmpty(message = "保险提醒时间不能为空")
//    private int ensureDay;car/manage


//    @TableField("MAINTAINDAY")
////    @NotEmpty(message = "保险生效时间不能为空")
////    private int maintainDay;


    @TableField("MAINTAINTIME")
    @NotEmpty(message = "维修日期不能为空")
    private Date maintainTime;

    @TableField("MEMO")
    private String memo;
}