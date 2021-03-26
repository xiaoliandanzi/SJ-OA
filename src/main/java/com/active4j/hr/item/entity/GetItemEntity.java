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
 * @Date: 2020/11/20/11:16
 * @Description:
 */
@TableName("oa_get_item")
@Getter
@Setter
public class GetItemEntity extends BaseEntity {


    /**
     *
     */
    private static final long serialVersionUID = 5570248248147514677L;

    /**
     * 使用科室
     */
    @TableField("DEPARTMENTNAME")
    private String departmentName;

    /**
     * 领用人
     */
    @TableField("USERNAME")
    private String userName;

    /**
     * 多规格数据
     */
    @TableField("JSON_DATA")
    private String jsonData;

    /**
     * 物品名称
     */
    @TableField("ITEMNAME")
    private String itemName;

    /**
     * 数量
     */
    @TableField("QUANTITY")
    private Integer quantity;

    /**
     * 领取时间
     */
    @TableField("GET_DAY")
    private Date getDay;

    @TableField("MEMO")
    private String memo;

    /**
     * 领用状态：已领取/未领取
     * */
    @TableField("GOODSTAUS")
    private String goodstaus;


}
