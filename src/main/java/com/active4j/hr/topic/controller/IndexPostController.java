package com.active4j.hr.topic.controller;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.topic.entity.OaTopic;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author weiZiHao
 * @date 2020/12/29
 */
@RequestMapping("oa/123456")
@RestController
@Slf4j
public class IndexPostController extends BaseController {


    @RequestMapping(value = "getAll")
    public AjaxJson getAll() {
        AjaxJson json = new AjaxJson();
        //http post
        String url = "http://123.56.249.251:9001/admin/index/getTodayTotalByOA";
        json.setObj(postUrl(url));
        return json;
    }

    @RequestMapping(value = "getNum")
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
