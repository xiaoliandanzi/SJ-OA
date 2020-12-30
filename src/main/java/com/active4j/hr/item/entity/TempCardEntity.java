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
@TableName("OA_ITEM_TMEP_CARD")
@Getter
@Setter
public class TempCardEntity extends BaseEntity {


    /**
     *
     */
    private static final long serialVersionUID = 5570248248147514677L;

    @TableField("NUMBER")
    @NotEmpty(message = "编号")
    private String number;

    @TableField("START_TIME")
    private Date startTime;

    @TableField("END_TIME")
    private Date endTime;

    @TableField("STATUS")
    @NotEmpty(message = "状态不能为空")
    private String status;

    @TableField("NAME")
    @NotEmpty(message = "使用人")
    private String name;

    @TableField("MEMO")
    private String memo;

}
