package com.active4j.hr.item.entity;

import com.active4j.hr.common.entity.BaseEntity;
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
