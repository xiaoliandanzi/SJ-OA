package com.active4j.hr.officalSeal.entity;

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
 * @Date: 2020/11/20/9:50
 * @Description:
 */
@TableName("OA_OFFICAL_SEAL")
@Getter
@Setter
public class OaOfficalSealEntity extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1906861960116263816L;

    @TableField("SEALID")
    @NotEmpty(message = "公章编号不能为空")
    private String id;

    @TableField("STATUS")
    @NotEmpty(message = "状态不能为空")
    private String status;

    @TableField("NAME")
    @NotEmpty(message = "公章名称不能为空")
    private String name;

    @TableField("MEMO")
    private String memo;

}