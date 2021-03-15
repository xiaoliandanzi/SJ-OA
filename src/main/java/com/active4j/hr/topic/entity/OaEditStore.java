package com.active4j.hr.topic.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ...
 * @since 2021-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="OaEditStore对象", description="")
public class OaEditStore extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /*@TableId(value = "ID", type = IdType.UUID)
    private String id;*/

    @TableField("NAME")
    private String name;

    @TableField("ITEMID")
    private String itemId;

    @TableField("ASSETNAME")
    private String assetName;

    @ApiModelProperty(value = "规格")
    @TableField("MODEL")
    private String model;

    @ApiModelProperty(value = "科室")
    @TableField("DEPT")
    private String dept;

    /*@TableField("CREATE_NAME")
    private String createName;

    @TableField("CREATE_DATE")
    private Date createDate;*/

    @TableField("QUANTITY")
    private Integer quantity;

    @ApiModelProperty(value = "单位")
    @TableField("UNIT")
    private String unit;

    @TableField("MINQUANTITY")
    private String minquantity;

    @ApiModelProperty(value = "保管人")
    @TableField("RECEIVER")
    private String receiver;

    @ApiModelProperty(value = "价格")
    @TableField("AMOUNT")
    private Double amount;

    @ApiModelProperty(value = "存放地点")
    @TableField("ADDRESS")
    private String address;


}
