<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,select2,jqgrid,laydate"></t:base>
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
                    <div id="jqGrid_wrapper" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 脚本部分 -->
<t:datagrid actionUrl="meeting/mytablelist" tableContentId="jqGrid_wrapper" searchGroupId="searchGroupId" fit="true"
            multiSelect="false"     rownumbers="true"    caption="会议列表" name="table_list_1"  pageSize="20" sortName="creatTime" sortOrder="desc" >
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="stateId" label="状态" width="150" query="false"></t:dgCol>
    <t:dgCol name="deptName" label="使用科室" width="150" query="false"></t:dgCol>
    <t:dgCol name="registrantName" label="登记人" query="false"></t:dgCol>
    <t:dgCol name="meetingId" label="会议室" query="false"></t:dgCol>
    <t:dgCol name="meetingTime" label="会议开始时间" query="true"  datefmt="yyyy-MM-dd HH:mm:ss" datePlugin="laydate"></t:dgCol>
    <t:dgCol name="meetingendTime" label="会议结束时间" query="false"></t:dgCol>
    <t:dgCol name="meetingName" label="会议名称" query="true"></t:dgCol>
    <t:dgCol name="meetingType" label="会议类型" hidden="true" query="true"  replace="书记会_书记会,工委会_工委会,主任会_主任会" ></t:dgCol>
    <t:dgToolBar label="查看" type="define" funName="getOne"></t:dgToolBar>
    <t:dgToolBar label="接收通知" type="define" funName="gettongzhi"></t:dgToolBar>
</t:datagrid>
<script type="text/javascript">
    $(function () {
        laydate({elem: "#meetingTime", event: "focus", istime: true, format: 'YYYY-MM-DD hh:mm:ss'});
    });
    function getOne() {
        var rowIds = $('#table_list_1').jqGrid('getGridParam', 'selrow');
        if (!rowIds) {
            qhAlert('请选择要查看的议题会议');
            return;
        }
        var list=$("#table_list_1").jqGrid('getRowData',rowIds);
        if(!list.id){
            qhAlert('请选择一条要查看的议题会议');
            return;
        }
        popNoForMe("table_list_1", "meeting/myyitihuiyiview?id=" + rowIds , "查看", "70%", "80%");
    }
    function gettongzhi() {
        var rowId = $('#table_list_1').jqGrid('getGridParam', 'selrow');
        if (!rowId) {
            qhAlert('请选择要操作的数据');
            return;
        }
        var list=$("#table_list_1").jqGrid('getRowData',rowId);
        if(!list.id){
            qhAlert('请选择一条要操作的数据');
            return;
        }
        $.post("notificationform/caozuo", {huiyiid: rowId}, function (data) {
            if (data.success) {
                qhTipSuccess(data.msg);
                //操作结束，刷新表格
                reloadTable('table_list_1');
            } else {
                qhTipWarning(data.msg);
            }
        });
    }
</script>
</body>
</html>
