<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2021/1/10
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
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
                    <div id="itemGetTable" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 脚本部分 -->
<t:datagrid actionUrl="item/get/datagrid" tableContentId="itemGetTable" searchGroupId="searchGroupId" fit="true" caption="领用物品登记" name="requisitionManageList" pageSize="20" sortName="createDate" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="departmentName" label="领用科室" width="80" query="true"></t:dgCol>
    <t:dgCol name="userName" label="领用人" width="80" query="true"></t:dgCol>
    <t:dgCol name="itemName" label="领用物品" width="80"></t:dgCol>
    <t:dgCol name="quantity" label="领用数量" width="80"></t:dgCol>
    <t:dgCol name="getDay" label="领用时间" width="80" datefmt="yyyy-MM-dd" queryModel="group" datePlugin="laydate" query="true"></t:dgCol>
    <t:dgCol name="memo" label="备注" width="80"></t:dgCol>
<%--    <t:dgCol name="opt" label="操作" ></t:dgCol>--%>
<%--    <t:dgDelOpt label="删除" url="item/manage/requisition/delete?id={id}"/>--%>
<%--    <t:dgToolBar url="item/manage/requisition/addorupdate" type="add" width="40%" height="70%"></t:dgToolBar>--%>
<%--    <t:dgToolBar url="item/manage/requisition/addorupdate" type="edit" width="40%" height="70%"></t:dgToolBar>--%>
</t:datagrid>


<script type="text/javascript">
    $(function(){
        laydate({elem:"#getDay",event:"focus",istime: false, format: 'YYYY-MM-DD'});
    });
</script>
</body>

</html>