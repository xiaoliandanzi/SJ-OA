package com.active4j.hr.item.entity;

import com.active4j.hr.common.entity.BaseEntity;
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
 * @Date: 2020/11/20/14:44
 * @Description:
 */
@TableName("OA_ITEM_BORROWED")
@Getter
@Setter
public class BorrowedItemEntity extends BaseEntity {
    private static final long serialVersionUID = -126521290616619135L;

    @TableField("ITEM_NAME")
    @NotEmpty(message = "物品名称不能为空")
    private String itemName;

    @TableField("ITEM_ID")
    @NotEmpty(message = "物品ID不能为空")
    private String itemID;

    @TableField("ITEM_QUANTITY")
    @NotEmpty(message = "物品数量不能为空")
    private int itemQuantity;

    @TableField("ITEM_OUT_OF_STORE")
    private int itemOutOfStore;

    @TableField("STATUS")
    @NotEmpty(message = "状态不能为空")
    private String status;

    @TableField("MEMO")
    private String memo;


}
