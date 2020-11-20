package com.active4j.hr.item.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/20/14:53
 * @Description:
 */
@TableName("OA_ITEM_BORROWED_STATISTICAL")
@Getter
@Setter
public class RequisitionStatisticalEntity extends BaseEntity {
    private static final long serialVersionUID = 3268127012727764404L;

    @TableField("USER_NAME")
    private String userName;

    @TableField("ITEM_NAME")
    private String itemName;

    @TableField("ITEM_ID")
    private String itemID;

    @TableField("ITEM_QUANTITY")
    private int itemQuantity;

    @TableField("STATUS")
    @NotEmpty(message = "状态不能为空")
    private String status;

    @TableField("RECEIVE_DATE")
    private Date receiveDate;

    @TableField("RETURN_DATE")
    private Date returnDate;

    @TableField("MEMO")
    private String memo;

}
