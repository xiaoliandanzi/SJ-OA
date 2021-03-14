<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
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
                    <div id="jqGrid_wrapper" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 脚本部分 -->
<t:datagrid actionUrl="notificationform/table"   tableContentId="jqGrid_wrapper" searchGroupId="searchGroupId" fit="true"
                rownumbers="true"    caption="通知详情" name="table_list_1"  pageSize="20" sortName="creatTime" sortOrder="desc" >
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="depname" label="科室" width="300" query="false"></t:dgCol>
    <t:dgCol name="name" label="姓名" width="300" query="false"></t:dgCol>
    <t:dgCol name="nameid" label="姓名" width="300" hidden="true" query="false"></t:dgCol>
    <t:dgCol name="status" label="通知回复情况" width="300" replace="已回复_1, 未回复_0" query="false"></t:dgCol>
    <t:dgToolBar label="提醒"  type="define" funName="tixing"></t:dgToolBar>
</t:datagrid>



<script type="text/javascript">
   function  tixing(){
       var rowId = $('#table_list_1').jqGrid('getGridParam', 'selrow');
       if (!rowId) {
           qhAlert('请选择要提醒的数据');
           return;
       }
       var list=$("#table_list_1").jqGrid('getRowData',rowId);
       if(!list.id){
           qhAlert('请选择一条要提醒的数据');
           return;
       }
       //是
       $.post("notificationform/updatetx", {id: rowId}, function (data) {
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
