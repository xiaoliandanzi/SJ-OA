package com.active4j.hr.activiti.biz.entity;


import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author weizihao
 * @since 2021-03-19
 */
@Getter
@Setter
@TableName("flow_asset_add")
public class FlowAssetAddEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /*@TableId(value = "ID", type = IdType.UUID)
    private String id;*/

    /*@ApiModelProperty(value = "版本号")
    @TableField(fill=FieldFill.INSERT)
    private Integer versions;*/

    /*@ApiModelProperty(value = "申请人")
    @TableField(value="CREATE_NAME", fill=FieldFill.INSERT)
    private String createName;

    @ApiModelProperty(value = "申请时间")
    @TableField(value="CREATE_DATE", fill=FieldFill.INSERT)
    private Date createDate;*/

    @ApiModelProperty(value = "入库日期")
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("TIME")
    private String time;

    @ApiModelProperty(value = "部门")
    @TableField("DEPT")
    private String dept;

    @ApiModelProperty(value = "资产名称")
    @TableField("ASSETNAME")
    private String assetName;

    @ApiModelProperty(value = "价格")
    @TableField("AMOUNT")
    private Double amount;

    @ApiModelProperty(value = "备注")
    @TableField("COMMIT")
    private String commit;

    @ApiModelProperty(value = "数量")
    @TableField("QUANTITY")
    private Integer quantity;

    @ApiModelProperty(value = "申请状态")
    @TableField("APPLYSTATUS")
    private Integer applystatus;

    @ApiModelProperty(value = "型号")
    @TableField("MODEL")
    private String model;

    @ApiModelProperty(value = "地址")
    @TableField("ADDRESS")
    private String address;

    @ApiModelProperty(value = "用途")
    @TableField("APPLICATION")
    private String application;

    /**
     * 多规格数据
     */
    @TableField("JSON_DATA")
    private String jsonData;
}
