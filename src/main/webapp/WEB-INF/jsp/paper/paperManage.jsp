<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/12/10
  Time: 下午11:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,select2,jqgrid"></t:base>
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
                    <div id="paperManageTable" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 脚本部分 -->
<t:datagrid actionUrl="paper/manage/datagrid" tableContentId="paperManageTable" searchGroupId="searchGroupId" fit="true" caption="文件列表" name="paperManageList" pageSize="20" sortName="createDate" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="paperNumber" label="发文文号" width="100"></t:dgCol>
    <t:dgCol name="secretLevel" label="保密级别" width="100"></t:dgCol>
    <t:dgCol name="paperCount" label="文件份数" width="80"></t:dgCol>
    <t:dgCol name="paperDate" label="发文日期" width="80"></t:dgCol>
    <t:dgCol name="title" label="文件标题" width="80"></t:dgCol>
    <%--<t:dgCol name="opt" label="操作" ></t:dgCol>--%>
    <t:dgToolBar url="paper/manage/addorupdate" type="view" width="70%"></t:dgToolBar>
    <t:dgToolBar label="附件下载" icon="glyphicon glyphicon-resize-full" type="define" funName="doAttachment"></t:dgToolBar>
</t:datagrid>
</body>

<script type="text/javascript">

    function doAttachment() {
        var rowId = $('#paperManageList').jqGrid('getGridParam','selrow');
        var rowData = $('#paperManageList').jqGrid('getRowData',rowId);

        if(!rowId) {
            qhAlert('请选择文件后再下载！');
            return;
        }

        if(!rowData.attachment) {
            qhAlert('该文件附件还未上传附件！');
            return;
        }

        location.href = "func/upload/download?id=" + rowData.attachment;
    };

</script>

</html>

