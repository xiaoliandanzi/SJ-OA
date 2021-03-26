package com.active4j.hr.item.entity;

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
 * @Date: 2020/11/20/14:46
 * @Description:
 */
@TableName("OA_ITEM_REQUISITIONED")
@Getter
@Setter
public class RequisitionedItemEntity extends BaseEntity {
    private static final long serialVersionUID = -2638790434373815072L;

    @TableField("NAME")
    private String name;

    @TableField("ITEMID")
    private String itemId;

    @TableField("TYPE")
    @QueryField(queryColumn = "type", condition = QueryCondition.eq)
    private String type;

    @TableField("MODEL")
    private String model;

    @TableField("QUANTITY")
    private int quantity;

    @TableField("NUMLIMIT")
    private int numLimit;

    @TableField("UNIT")
    private String unit;

    @TableField("LOCATION")
    private String location;

    @TableField("KEEPER")
    private String keeper;

    @TableField("MINQUANTITY")
    private String minQuantity;

    @TableField("STATUS")
    @NotEmpty(message = "状态不能为空")
    @QueryField(queryColumn = "STATUS", condition = QueryCondition.eq)
    private String status;

    @TableField("MEMO")
    private String memo;

}
