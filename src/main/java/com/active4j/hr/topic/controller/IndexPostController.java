package com.active4j.hr.topic.controller;

import com.active4j.hr.activiti.biz.entity.FlowMessageApprovalEntity;
import com.active4j.hr.activiti.biz.service.FlowMessageApprovalService;
import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.func.timer.entity.QuartzJobEntity;
import com.active4j.hr.topic.entity.OaTopic;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author weiZiHao
 * @date 2020/12/29
 */
@RequestMapping("oa")
@RestController
@Slf4j
public class IndexPostController extends BaseController {

    @Autowired
    private FlowMessageApprovalService flowMessageApprovalService;

    @RequestMapping("/login/messageList")
    public AjaxJson getMessage(FlowMessageApprovalEntity flowMessageApprovalEntity, DataGrid dataGrid) {
        AjaxJson json = new AjaxJson();
        dataGrid.setRows(8);
        flowMessageApprovalEntity.setApplyStatus(1);
        QueryWrapper<FlowMessageApprovalEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(flowMessageApprovalEntity);
        queryWrapper.orderByDesc("CREATE_DATE");
        IPage<FlowMessageApprovalEntity> page = flowMessageApprovalService.page(new Page<FlowMessageApprovalEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
        json.setObj(page.getRecords());
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
