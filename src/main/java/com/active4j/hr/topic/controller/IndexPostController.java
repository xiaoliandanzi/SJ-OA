package com.active4j.hr.topic.controller;

import com.active4j.hr.activiti.biz.entity.FlowMessageApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowMessageApprovalService;
import com.active4j.hr.activiti.dao.WorkflowDao;
import com.active4j.hr.activiti.entity.WorkflowBaseEntity;
import com.active4j.hr.activiti.service.WorkflowBaseService;
import com.active4j.hr.activiti.service.WorkflowService;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.StringUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.func.timer.entity.QuartzJobEntity;
import com.active4j.hr.system.entity.SysMessageEntity;
import com.active4j.hr.system.model.ActiveUser;
import com.active4j.hr.system.service.SysMessageService;
import com.active4j.hr.topic.entity.OaTopic;
import com.active4j.hr.work.entity.OaWorkTaskEntity;
import com.active4j.hr.work.service.OaWorkTaskService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.manager.util.SessionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.record.formula.functions.Int;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author weiZiHao
 * @date 2020/12/29
 */
@RequestMapping("/oa")
@RestController
@Slf4j
public class IndexPostController extends BaseController {

    @Autowired
    private FlowMessageApprovalService flowMessageApprovalService;

    @Autowired
    private SysMessageService sysMessageService;

    @Autowired
    private OaWorkTaskService oaWorkTaskService;

    @Autowired
    private WorkflowBaseService workflowBaseService;

    /**
     * 待办事项
     *
     * @return
     */
    @RequestMapping("/index/workCount")
    public AjaxJson getIndexWork() {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            List<Integer> count = new ArrayList<>();
            //督办
            QueryWrapper<OaWorkTaskEntity> workTaskEntityQueryWrapper = new QueryWrapper<>();
            workTaskEntityQueryWrapper.eq("USER_ID", ShiroUtils.getSessionUserId());
            List<OaWorkTaskEntity> oaWorkTaskEntities = oaWorkTaskService.list(workTaskEntityQueryWrapper);
            count.add(0, oaWorkTaskEntities.size());
            //审核
            QueryWrapper<WorkflowBaseEntity> workflowBaseEntityQueryWrapper = new QueryWrapper<>();
            workflowBaseEntityQueryWrapper.eq("USER_NAME", ShiroUtils.getSessionUserName());
            List<WorkflowBaseEntity> workflowBaseEntities = workflowBaseService.list(workflowBaseEntityQueryWrapper);
            count.add(1, workflowBaseEntities.size());
            //驳回
            workflowBaseEntityQueryWrapper.eq("STATUS", 5);
            workflowBaseEntities = workflowBaseService.list(workflowBaseEntityQueryWrapper);
            count.add(2, workflowBaseEntities.size());
            ajaxJson.setObj(count);
        } catch (Exception e) {
            log.error("获取待办事项失败,错误信息:" + e.getMessage());
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("获取待办事项失败");
            e.printStackTrace();
        }
        return ajaxJson;
    }

    /**
     * 事件通知
     *
     * @return
     */
    @RequestMapping("/index/msgList")
    public AjaxJson getIndexMsg(DataGrid dataGrid) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            QueryWrapper<SysMessageEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("USER_ID", ShiroUtils.getSessionUserId());
            queryWrapper.eq("STATUS", "0");
            queryWrapper.orderByDesc("SEND_TIME");
            IPage<SysMessageEntity> page = sysMessageService.page(new Page<>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
            ajaxJson.setObj(page);
        } catch (Exception e) {
            log.error("获取系统消息失败,错误信息:" + e.getMessage());
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("获取系统消息失败");
            e.printStackTrace();
        }
        return ajaxJson;
    }

    /**
     * 视图
     *
     * @param flowMessageApprovalEntity
     * @return
     */
    //  /oa/index/viewArticleList
    @RequestMapping(value = "/index/viewArticleList")
    public ModelAndView viewArticleList(FlowMessageApprovalEntity flowMessageApprovalEntity) {
        ModelAndView modelAndView = new ModelAndView("main/articleList");
        modelAndView.addObject("messageType", flowMessageApprovalEntity.getMessageType());
        if (!StringUtil.isEmpty(flowMessageApprovalEntity.getId())) {
            modelAndView.addObject("getOne", flowMessageApprovalEntity.getId());
        } else {
            modelAndView.addObject("getOne", "0");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/index/viewArticle")
    public ModelAndView viewArticle() {
        return new ModelAndView("main/topiclist");
    }

    /**
     * 阅文
     *
     * @param flowMessageApprovalEntity
     * @return
     */
    @RequestMapping(value = "/login/getArticle")
    @Transactional(propagation = Propagation.REQUIRED)
    public AjaxJson getOne(FlowMessageApprovalEntity flowMessageApprovalEntity) {
        AjaxJson json = new AjaxJson();
        try {
            FlowMessageApprovalEntity dao = flowMessageApprovalService.getById(flowMessageApprovalEntity.getId());
            json.setObj(dao);
            dao.setCount(dao.getCount() + 1);
            flowMessageApprovalService.saveOrUpdate(dao);
        } catch (Exception e) {
            log.error("获取文章失败,错误信息:" + e.getMessage());
            json.setSuccess(false);
            json.setMsg("获取文章失败");
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 文章列
     *
     * @param flowMessageApprovalEntity
     * @param dataGrid
     * @return
     */
    @RequestMapping(value = "/login/messageList")
    public AjaxJson getMessage(FlowMessageApprovalEntity flowMessageApprovalEntity, DataGrid dataGrid) {
        AjaxJson json = new AjaxJson();
        try {
            flowMessageApprovalEntity.setApplyStatus(1);
            QueryWrapper<FlowMessageApprovalEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.setEntity(flowMessageApprovalEntity);
            queryWrapper.orderByDesc("CREATE_DATE");
            IPage<FlowMessageApprovalEntity> page = flowMessageApprovalService.page(new Page<FlowMessageApprovalEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
            json.setObj(page);
        } catch (Exception e) {
            log.error("获取文章列表失败,错误信息:" + e.getMessage());
            json.setSuccess(false);
            json.setMsg("获取文章列表失败");
            e.printStackTrace();
        }
        return json;
    }


    @RequestMapping(value = "/123456/getAll")
    public AjaxJson getAll() {
        AjaxJson json = new AjaxJson();
        //http post
        String url = "http://123.56.249.251:9001/admin/index/getTodayTotalByOA";
        json.setObj(postUrl(url));
        return json;
    }

    @RequestMapping(value = "/123456/getNum")
    public AjaxJson getNum() {
        AjaxJson json = new AjaxJson();
        String url = "http://123.56.249.251:9001/admin/index/getIsOverByOA";
        json.setObj(postUrl(url));
        return json;
    }

    /**
     * 用户信息
     *
     * @return
     */
    @RequestMapping(value = "/portal/user")
    public AjaxJson getUser() {
        AjaxJson ajaxJson = new AjaxJson();
        ActiveUser user = ShiroUtils.getSessionUser();
        ajaxJson.setObj(user);
        return ajaxJson;
    }

    /**
     * 访问请求  oa/123456/postUrl
     */
    private String postUrl(String url) {
        String back = "";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                back = EntityUtils.toString(responseEntity);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return back;
    }


}
