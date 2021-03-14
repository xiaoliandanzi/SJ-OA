<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/11/17
  Time: 下午11:00
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/11/17
  Time: 下午9:42
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
                    <div id="itemRequisitionTable" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 脚本部分 -->
<t:datagrid actionUrl="item/manage/requisition/datagrid" tableContentId="itemRequisitionTable" searchGroupId="searchGroupId" fit="true" caption="领用物品管理" name="requisitionManageList" pageSize="20" sortName="createDate" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="type" label="物品类型" width="80" dictionary="itemkind" display="status" query="true"></t:dgCol>
    <t:dgCol name="itemId" label="物品编号" width="80"></t:dgCol>
    <t:dgCol name="name" label="物品名称" width="80"></t:dgCol>
    <t:dgCol name="model" label="规格/类型" width="80"></t:dgCol>
    <t:dgCol name="quantity" label="数量" width="80"></t:dgCol>
    <t:dgCol name="unit" label="数量单位" width="80"></t:dgCol>
    <t:dgCol name="location" label="存放地点" width="80"></t:dgCol>
    <t:dgCol name="keeper" label="保管人" width="80"></t:dgCol>
    <t:dgCol name="minQuantity" label="低量预警线" width="80"></t:dgCol>
    <t:dgCol name="memo" label="备注" width="80"></t:dgCol>
    <t:dgCol name="status" label="物品状态" width="80" dictionary="itemstatus" display="level" query="true"></t:dgCol>
    <t:dgCol name="opt" label="操作" ></t:dgCol>
    <t:dgDelOpt label="删除" url="item/manage/requisition/delete?id={id}"/>
    <t:dgToolBar url="item/manage/requisition/addorupdate" type="add" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar url="item/manage/requisition/addorupdate" type="edit" width="40%" height="70%"></t:dgToolBar>
    <%--<t:dgToolBar url="item/manage/requisition/addorupdate" type="view" width="40%" height="70%"></t:dgToolBar>--%>
</t:datagrid>
</body>

</html>

