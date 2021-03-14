package com.active4j.hr.activiti.biz.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2021/1/10 下午10:47
 */
@TableName("flow_asset_approval")
@Getter
@Setter
public class FlowAssetApprovalEntity extends BaseEntity {
    private static final long serialVersionUID = 4068928535576332222L;

    /**
     * 移交人
     */
    @TableField("USERNAME")
    private String userName;

    /**
     * 监交人
     */
    @TableField("MONITOR")
    private String monitor;

    /**
     * 接受人
     */
    @TableField("RECEIVER")
    private String receiver;

    /**
     * 科室
     */
    @TableField("DEPT")
    @QueryField(queryColumn="DEPT", condition=QueryCondition.eq)
    private String dept;

    /**
     * 固定资产名称
     */
    @TableField("ASSETNAME")
    @QueryField(queryColumn="assetName", condition=QueryCondition.eq)
    private String assetName;

    /**
     * 数量
     */
    @TableField("QUANTITY")
    private Integer quantity;

    /**
     * 价格
     */
    @TableField("AMOUNT")
    private double amount;

    /**
     * 型号
     */
    @TableField("MODEL")
    private String model;

    /**
     * 地址
     */
    @TableField("ADDRESS")
    private String address;

    /**
     * 备注
     */
    @TableField("COMMIT")
    private String commit;

    /**
     * 状态
     */
    @TableField("APPLYSTATUS")
    private Integer applyStatus;

    /**
     * 多规格数据
     */
    @TableField("JSON_DATA")
    private String jsonData;
}
