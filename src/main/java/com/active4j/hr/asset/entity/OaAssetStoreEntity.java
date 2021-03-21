package com.active4j.hr.asset.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2021/1/24 下午5:15
 */
@TableName("OA_ASSET_STORE")
@Getter
@Setter
public class OaAssetStoreEntity extends BaseEntity {
    private static final long serialVersionUID = -9217585424356431457L;

    /**
     * 保管人
     *
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
    private Double amount;

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
     *
     */
    @TableField(value = "CHANGE_TIME" ,fill= FieldFill.UPDATE)
    private Date changeTime;

//    /**
//     * 多规格数据
//     */
//    @TableField("JSON_DATA")
//    private String jsonData;
}
