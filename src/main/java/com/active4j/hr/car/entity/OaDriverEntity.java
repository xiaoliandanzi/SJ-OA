package com.active4j.hr.car.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@TableName("oa_driver")
@Getter
@Setter
public class OaDriverEntity  extends BaseEntity{

    private static final long serialVersionUID = -4478471099483970300L;

    @TableField("NAME")
    @NotEmpty(message = "驾驶员姓名不能为空")
    private String name;

    @TableField("AGE")
    @NotEmpty(message = "驾驶员年龄不能为空")
    private int age;

    @TableField("BIRTHDAY")
    private Date birthday;

    @TableField("MEMO")
    private String memo;
}
