package com.active4j.hr.item.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/12/3 上午12:28
 */
@TableName("OA_ITEM_DISCARDED")
@Getter
@Setter
public class DiscardedItemEntity extends BaseEntity {

    private static final long serialVersionUID = -1571375737967173784L;

    @TableField("NAME")
    private String name;

    @TableField("TYPE")
    private String type;

    @TableField("BOARD")
    private String board;

    @TableField("QUANTITY")
    private int quantity;

    @TableField("STATUS")
    @NotEmpty(message = "状态不能为空")
    private String status;

    @TableField("MEMO")
    private String memo;
}
