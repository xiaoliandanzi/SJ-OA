package com.active4j.hr.activiti.biz.entity;

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
 * @date 2020/12/22 下午10:11
 */
@TableName("flow_message_approval")
@Getter
@Setter
public class FlowMessageApprovalEntity extends BaseEntity {
    private static final long serialVersionUID = -5129720245313454965L;

    /**
     * 发布人
     */
    @TableField("PUBLICMAN")
    private String publicMan;

    /**
     * 负责人
     */
    @TableField("DIRECTOR")
    private String director;

    /**
     * 科室
     */
    @TableField("DEPT")
    @QueryField(queryColumn="DEPT", condition=QueryCondition.eq)
    private String dept;

    /**
     * 发布时间
     */
    @TableField("PUBLICTIME")
    @QueryField(queryColumn="PUBLICTIME", condition=QueryCondition.range)
    private Date publicTime;

    /**
     * 内容
     */
    @TableField("CONTENT")
    private String content;

    /**
     * 附件
     */
    @TableField("ATTACHMENT")
    private String attachment;

    /**
     * 标题
     */
    @TableField("TITLE")
    @QueryField(queryColumn="TITLE", condition=QueryCondition.like)
    private String title;

    /**
     * 审批状态 0:审批中 1：审批完成 2：驳回 3:草稿状态
     */
    @TableField("APPLYSTATUS")
    private Integer applyStatus;

    /**
     * 信息类型 1:公示文件 2：通知公告 3:媒体聚焦
     */
    @TableField("MESSAGETYPE")
    private Integer messageType;
}
