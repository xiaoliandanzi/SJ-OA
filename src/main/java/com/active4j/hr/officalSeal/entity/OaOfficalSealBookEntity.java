package com.active4j.hr.officalSeal.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/24/20:11
 * @Description:
 */
@TableName("OA_SEAL_BOOKS")
@Getter
@Setter
public class OaOfficalSealBookEntity extends BaseEntity {
    private static final long serialVersionUID = 5093264479260108519L;


    @TableField("USER_NAME")
    private String userName;

    @TableField("USER_ID")
    private String userId;


    @TableField("DEPARTMENT_NAME")
    private String departmentName;

    @TableField("USE_UNIT")
    private String useUnit;

    @TableField("CONTENT")
    private String content;

    @TableField("BOOK_DATE")
    @QueryField(condition= QueryCondition.eq, queryColumn="BOOK_DATE")
    private Date bookDate;

    @TableField("STR_BOOK_DATE")
    private String strBookDate;

    @TableField("START_DATE")
    private Date startDate;

    @TableField("END_DATE")
    private Date endDate;

    @TableField("MEMO")
    private String memo;

    @TableField("SEAL_ID")
    @QueryField(condition=QueryCondition.eq, queryColumn="SEAL_ID")
    private String sealId;
}
