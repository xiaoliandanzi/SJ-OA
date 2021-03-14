<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2021/1/15
  Time: 0:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,jqgrid,datetimePicker,laydate"></t:base>
</head>
<body class="gray-bg">
<!-- 页面部分 -->
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="row">
                <div class="col-sm-12" id="searchGroupId">
                </div>
            </div>
            <div class="ibox">
                <div class="ibox-content">
                    <div id="finishTaskTable" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- 脚本部分 -->
<t:datagrid actionUrl="car/record/datagridFinish" tableContentId="finishTaskTable" searchGroupId="searchGroupId" fit="true" caption="已审批流程" name="finishTaskList" pageSize="20" sortName="applyDate" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="categoryId" label="流程类别" hidden="true"  replace="${categoryReplace}" queryId="categoryId" width="90"></t:dgCol>
    <t:dgCol name="workFlowName" label="流程名称" hidden="true" width="70"></t:dgCol>
    <t:dgCol name="projectNo" label="编号" width="120" query="true"></t:dgCol>
<%--    <t:dgCol name="name" label="标题名称" width="120" query="true"></t:dgCol>--%>
    <t:dgCol name="status" label="状态" width="70" dictionary="actstatus" classes="text-navy"></t:dgCol>
    <%--    <t:dgCol name="level" label="紧急程度" width="70" dictionary="workflowlevel" display="level"></t:dgCol>--%>
    <t:dgCol name="applyName" label="申请人" width="80" query="true"></t:dgCol>
    <%--    <t:dgCol name="opt" label="操作" ></t:dgCol>--%>
    <%--    <t:dgDelOpt label="删除" url="officalSeal/audit/delete?id={id}"/>--%>
    <t:dgCol name="applyDate" label="申请时间" width="120" query="true" datefmt="yyyy-MM-dd HH:mm:ss" queryModel="group" datePlugin="laydate"></t:dgCol>
    <%--    <t:dgCol name="useDepartment" label="用车单位"  width="70"></t:dgCol>--%>
<%--    <t:dgCol name="userName" label="乘车人" width="120"></t:dgCol>--%>
<%--    <t:dgCol name="person" label="乘车人数" width="120" ></t:dgCol>--%>
<%--    <t:dgCol name="reason" label="申请事由" width="120" ></t:dgCol>--%>
<%--    <t:dgCol name="useTime" label="使用时间" width="120" datefmt="yyyy-MM-dd" queryModel="group" datePlugin="laydate"></t:dgCol>--%>
<%--    <t:dgCol name="commit" label="备注" width="120" query="true"></t:dgCol>--%>
    <t:dgToolBar label="查看详情" icon="fa fa-eye" url="flow/biz/task/view" type="read" width="90%" height="90%"></t:dgToolBar>

    <t:dgToolBar label="查看流程图" icon="fa fa-binoculars" url="flow/biz/my/viewImage" type="read" width="80%" height="80%"></t:dgToolBar>
    <t:dgToolBar label="撤销申请" icon="fa fa-remove" url="flow/biz/my/goCancle" type="pop" width="40%" height="50%"></t:dgToolBar>

    <t:dgToolBar label="导出Excel" icon="fa fa-cloud-download" type="define" funName="doAttachment"></t:dgToolBar>

    <t:dgToolBar type="refresh" ></t:dgToolBar>
</t:datagrid>


<script type="text/javascript">
    $(function(){
        laydate({elem:"#applyDate_begin",event:"focus",istime: true, format: 'YYYY-MM-DD hh:mm:ss'});
        laydate({elem:"#applyDate_end",event:"focus",istime: true, format: 'YYYY-MM-DD hh:mm:ss'});
    });

    function doAttachment() {
        window.open("/oa/car/record/excelExport");
    }
</script>
</body>
</html>
