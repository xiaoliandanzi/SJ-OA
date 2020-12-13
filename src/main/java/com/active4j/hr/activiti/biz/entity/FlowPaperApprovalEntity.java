package com.active4j.hr.activiti.biz.entity;

import com.active4j.hr.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/12/9 下午10:12
 */
@TableName("flow_paper_approval")
@Getter
@Setter
public class FlowPaperApprovalEntity extends BaseEntity {

    private static final long serialVersionUID = 6412306086754933137L;
    /**
     * 起草人
     */
    @TableField("DRAFTMAN")
    private String draftMan;

    /**
     * 保密级别
     */
    @TableField("SECRETLEVEL")
    private String secretLevel;

    /**
     * 文件份数
     */
    @TableField("PAPERCOUNT")
    private Integer paperCount;

    /**
     * 发文日期
     */
    @TableField("PAPERDATE")
    private Date paperDate;

    /**
     * 发文文号
     */
    @TableField("PAPERNUMBER")
    private String paperNumber;

    /**
     * 公开选择
     */
    @TableField("PAPERPUBLIC")
    private Integer paperPublic;

    /**
     * 发放范围
     */
    @TableField("PAPERAREA")
    private String paperArea;

    /**
     * 内容摘要
     */
    @TableField("PAPERABSTRACT")
    private String paperAbstract;

    /**
     * 附件
     */
    @TableField("ATTACHMENT")
    private String attachment;

    /**
     * 备注
     */
    @TableField("COMMIT")
    private String commit;

    /**
     * 标题
     */
    @TableField("TITLE")
    private String title;

    /**
     * 审批状态 0:审批中 1：审批完成 2：驳回 3:草稿状态
     */
    @TableField("APPLYSTATUS")
    private Integer applyStatus;

}
