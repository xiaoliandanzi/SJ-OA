<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default"></t:base>
</head>

<body class="gray-bg">
<div class="row">
            <div class="col-sm-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <%--<span class="label label-success pull-right">月</span>--%>
                        <h3>12345案件统计</h3>
                    </div>
                    <div class="ibox-content" >
                        <%--<h1 class="no-margins">40 886,200</h1>--%>
                        <%--<div class="stat-percent font-bold text-success">98% <i class="fa fa-bolt"></i>--%>
                        <%--</div>--%>
                        <%--<small>总收入</small>--%>
                    </div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <%--<span class="label label-info pull-right">全年</span>--%>
                        <h3>工作信息</h3>
                    </div>
                    <div class="ibox-content">
                        <%--<h1 class="no-margins">275,800</h1>--%>
                        <%--<div class="stat-percent font-bold text-info">20% <i class="fa fa-level-up"></i>--%>
                        <%--</div>--%>
                        <%--<small>新订单</small>--%>
                    </div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <%--<span class="label label-primary pull-right">今天</span>--%>
                        <h3>文件处理</h3>
                    </div>
                    <div class="ibox-content">

                        <%--<h1 class="no-margins">106,120</h1>--%>
                        <%--<div class="stat-percent font-bold text-navy">44% <i class="fa fa-level-up"></i>--%>
                        <%--</div>--%>
                        <%--<small>新访客</small>--%>
                    </div>
                </div>
            </div>
            <%--<div class="col-sm-3">--%>
                <%--<div class="ibox float-e-margins">--%>
                    <%--<div class="ibox-title">--%>
                        <%--&lt;%&ndash;<span class="label label-danger pull-right">最近一个月</span>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<h5>活跃用户</h5>&ndash;%&gt;--%>
                    <%--</div>--%>
                    <%--<div class="ibox-content">--%>
                        <%--&lt;%&ndash;<h1 class="no-margins">80,600</h1>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<div class="stat-percent font-bold text-danger">38% <i class="fa fa-level-down"></i>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<small>12月</small>&ndash;%&gt;--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        </div>
    <div class="row  border-bottom white-bg dashboard-header">
        <div class="col-sm-4">
            <h3>公示文件</h3>
            <div class="ibox-content">

            </div>
            <%--<small>欢迎扫描以下二维码关注我们：</small>--%>
            <%--<br>--%>
            <%--<br>--%>
            <%--<img src="https://zh-active4j-1251505225.cos.ap-shanghai.myqcloud.com/active4jboot/active4j-wx.jpg" width="100%" style="max-width:264px;">--%>
            <%--<br>--%>
        </div>
        <div class="col-sm-4">
            <h3>
                通知公告
            </h3>
                <div class="ibox-content">

                </div>
            <%--<p>Active4j-jsp是基于SpingBoot2.0轻量级的java快速开发框架。以Spring Framework为核心容器，Spring MVC为模型视图控制器，Mybatis Plus为数据访问层， Apache Shiro为权限授权层, Redis为分布式缓存，Quartz为分布式集群调度，JSP作为前端页面引擎，采用JSTL标签库封装组件的开源框架。</p>--%>
            <%--<p>Active4j-jsp定位于企业快速开发平台建设，代码全部开源，持续更新，共同维护。Active4j-jsp可以应用在任何J2EE的项目开发中，尤其适合企业信息管理系统（MIS），企业办公系统（OA），客户关系管理系统（CRM）等。</p>--%>
            <%--<p>--%>
                <%--<b>当前版本：</b>active4j-jsp版--%>
            <%--</p>--%>
            <%--<p>--%>
                <%--<b>官方网站：</b><span class="label"><a href="www.active4j.com" target="_blank">www.active4j.com</a></span>--%>
            <%--</p>--%>
             <%--<p>--%>
                <%--<b>QQ群：</b><span class="label label-warning">203802692</span>--%>
            <%--</p>--%>
            <%--<br>--%>
            <%--<p>--%>
                <%--<a class="btn btn-success btn-outline" href="https://github.com/yunchaoyun/active4j-jsp" target="_blank">--%>
                    <%--<i class="fa fa-home"> </i> github--%>
                <%--</a>--%>
                <%--<a class="btn btn-white btn-bitbucket" href="https://gitee.com/active4j/active4j-jsp" target="_blank">--%>
                    <%--<i class="fa fa-home"></i> gitee--%>
                <%--</a>--%>
            <%--</p>--%>
        </div>
        <div class="col-sm-4">
            <h3>制度规范</h3>
            <div class="ibox-content">
            </div>
            <%--<ol>--%>
            <a href="car/manage/show"><li>开箱即用，节省开发时间，提高开发效率</li></a>
                <a href="//www.baidu.com"><li onclick="next()">代码全部开源，持续更新，共同维护</li></a>
            <a href="javascript:search()"><li >支持分布式部署，session统一由redis进行管理</li></a>
                <a url="oa/work/meetRoom/bookview" type="pop"><li>基于SpringBoot，简化了大量项目配置和maven依赖，让您更专注于业务开发</li></a>
                <li>使用分层设计，分为dao，service，Controller，view层，层次清楚，低耦合，高内聚</li>
            <a href="wf/flow/center/list"><li>提供了诸多的UI组件</li></a>
                <li>友好的代码结构及注释，便于阅读及二次开发</li>
                <li>灵活的权限控制, 整合shiro，可控制到页面或按钮，满足绝大部分的权限需求,优化权限注解方便权限配置</li>
                <li>日志记录采用aop(LogAop类)方式，可对用户所有操作进行记录</li>
                <li>引入quartz定时任务，可动态完成任务的添加、修改、删除、暂停、恢复及日志查看等功能</li>
                <li>数据统计报表：丰富的报表统计功能</li>
                <li>集成jsp页面，采用标准JSTL标签库对常用组件进行封装，便于将传统项目过度到springboot</li>
                <li>组件库丰富，对常用页面组件进行了代码封装，提高开发效率</li>
                <li>前端页面简洁优美，支持移动端</li>
                <li>更多……</li>
            <%--</ol>--%>
        </div>
    </div>
<div class="row  border-bottom white-bg dashboard-header">
    <div class="col-sm-4">
        <h3>媒体聚焦</h3>
        <div class="ibox-content">

        </div>
    </div>
    <div class="col-sm-4">
        <h3>
            双井图库
        </h3>
        <div class="ibox-content">

        </div>
    </div>
    <div class="col-sm-4">
        <h3>会议通知</h3>
        <div class="ibox-content">
        </div>

    </div>
</div>



    <%--<div class="wrapper wrapper-content">--%>
        <%--<div class="row">--%>
            <%--<div class="col-sm-4">--%>

                <%--<div class="ibox float-e-margins">--%>
                    <%--<div class="ibox-title">--%>
                        <%--&lt;%&ndash;<h5>二次开发</h5>&ndash;%&gt;--%>
                    <%--</div>--%>
                    <%--<div class="ibox-content">--%>
                        <%--&lt;%&ndash;<p>我们是专业的软件基础平台与解决方案提供商，为企事业单位提供创新可靠的软件基础平台产品及技术服务</p>&ndash;%&gt;--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="ibox float-e-margins">--%>
                    <%--<div class="ibox-title">--%>
                        <%--&lt;%&ndash;<h5>联系信息</h5>&ndash;%&gt;--%>

                    <%--</div>--%>
                    <%--<div class="ibox-content">--%>
                        <%--&lt;%&ndash;<p><i class="fa fa-send-o"></i> 官方网站：<a href="http://www.active4j.com/" target="_blank">www.active4j.com</a>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</p>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<p><i class="fa fa-qq"></i> QQ：<a href="javascript:;" target="_blank">113578375</a>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</p>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<p><i class="fa fa-weixin"></i> 微信号：<a href="javascript:;">chen_terry</a>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</p>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<p><i class="fa fa-credit-card"></i> qq群：<a href="javascript:;">203802692</a>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</p>&ndash;%&gt;--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="col-sm-4">--%>
                <%--<div class="ibox float-e-margins">--%>
                    <%--&lt;%&ndash;<div class="ibox-title">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<h5>更新日志</h5>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<div class="ibox-content no-padding">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<div class="panel-body">&ndash;%&gt;--%>
                            <%--&lt;%&ndash;<div class="panel-group" id="version">&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div class="panel panel-default">&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<div class="panel-heading">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<h5 class="panel-title">&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<a data-toggle="collapse" data-parent="#version" href="#v1.0">v1.0</a><code class="pull-right">2020.3.24</code>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;</h5>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<div id="v1.0" class="panel-collapse collapse in">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<div class="panel-body">&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<div class="alert alert-warning">项目发布并开源</div>&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
</div>
</body>
<script type="text/javascript">
    function search()
    { $.ajax({
        type:"post",
        dataType:"text",
        data:{"id":0},
        url:'/car/manage/list'//目标地址
    })
    }
</script>
</html>



