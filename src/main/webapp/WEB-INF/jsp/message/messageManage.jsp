<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/12/23
  Time: 上午12:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,select2,jqgrid,datetimePicker,laydate"></t:base>
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
                    <div id="messageManageTable" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 脚本部分 -->
<t:datagrid actionUrl="message/manage/datagrid" tableContentId="messageManageTable" searchGroupId="searchGroupId" fit="true" caption="信息列表" name="messageManageList" pageSize="20" sortName="createDate" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="messageType" dictionary="messagetypes" label="信息类型" width="60" query="true"></t:dgCol>
    <t:dgCol name="title" label="标题" width="120" query="true"></t:dgCol>
    <t:dgCol name="publicTime" label="发布日期" width="60" datefmt="yyyy-MM-dd HH:mm" query="true" queryModel="group" datePlugin="laydate"></t:dgCol>
    <t:dgCol name="publicMan" label="发布人" width="60" query="true"></t:dgCol>
    <t:dgCol name="attachment" label="附件" hidden="true"></t:dgCol>
    <t:dgCol name="opt" label="操作" ></t:dgCol>
    <t:dgDelOpt label="删除" url="message/manage/delete?id={id}"/>
    <t:dgToolBar url="message/manage/addorupdate" type="view" width="70%"></t:dgToolBar>
    <t:dgToolBar label="附件下载" icon="glyphicon glyphicon-resize-full" type="define" funName="doAttachment"></t:dgToolBar>
    <t:dgToolBar type="refresh" ></t:dgToolBar>
</t:datagrid>
</body>

<script type="text/javascript">

    $(function(){
        laydate({elem:"#publicTime_begin",event:"focus",istime: true, format: 'YYYY-MM-DD'});
        laydate({elem:"#publicTime_end",event:"focus",istime: true, format: 'YYYY-MM-DD'});
    });

    function doAttachment() {
        var rowId = $('#messageManageList').jqGrid('getGridParam','selrow');
        var rowData = $('#messageManageList').jqGrid('getRowData',rowId);

        if(!rowId) {
            qhAlert('请选择信息后再下载！');
            return;
        }

        console.info(rowData);

        if(!rowData.attachment) {
            qhAlert('该信息附件还未上传附件！');
            return;
        }

        location.href = "func/upload/download?id=" + rowData.attachment;
    };

</script>

</html>
