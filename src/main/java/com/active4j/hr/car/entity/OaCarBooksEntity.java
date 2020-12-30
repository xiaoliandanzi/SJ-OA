package com.active4j.hr.car.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.active4j.hr.core.annotation.QueryField;
import com.active4j.hr.core.query.QueryCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/11/22 下午8:13
 */
@TableName("OA_Car_BOOKS")
@Getter
@Setter
public class OaCarBooksEntity extends BaseEntity {
    private static final long serialVersionUID = 3044159498203928853L;

    @TableField("USER_NAME")
    private String userName;

    @TableField("USER_ID")
    private String userId;

    @TableField("BOOK_DATE")
    @QueryField(condition=QueryCondition.eq, queryColumn="BOOK_DATE")
    private Date bookDate;

    @TableField("STR_BOOK_DATE")
    private String strBookDate;

    @TableField("START_DATE")
    private Date startDate;

    @TableField("END_DATE")
    private Date endDate;

    @TableField("MEMO")
    private String memo;

    @TableField("CAR_ID")
    @QueryField(condition=QueryCondition.eq, queryColumn="CAR_ID")
    private String carId;
}
