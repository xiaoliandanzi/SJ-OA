<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/11/16
  Time: 上午1:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,jqgrid,laydate"></t:base>
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
                    <div id="carReturn" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 脚本部分 -->
<t:datagrid actionUrl="car/manage/datagrid" tableContentId="carReturn" searchGroupId="searchGroupId" fit="true" caption="车辆归还" name="carManageList" pageSize="20" sortName="createDate" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="carId" label="车牌号" width="100"></t:dgCol>
    <t:dgCol name="carName" label="汽车名称" width="100"></t:dgCol>
    <t:dgCol name="persons" label="载人数" width="80"></t:dgCol>
    <t:dgCol name="status" label="状态" width="80" dictionary="oaworkmeet" display="zeroOrOne"></t:dgCol>
    <t:dgCol name="opt" label="操作" ></t:dgCol>
    <t:dgDelOpt label="删除" url="car/manage/delete?id={id}"/>
    <t:dgToolBar url="car/manage/addorupdate" type="add" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar url="car/manage/addorupdate" type="edit" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar url="car/manage/addorupdate" type="view" width="40%" height="70%"></t:dgToolBar>
<%--    <t:dgToolBar label="预定" icon="fa fa-cog" url="car/manage/bookview" type="pop" width="50%" height="70%"></t:dgToolBar>--%>
<%--    <t:dgToolBar label="查看预定" icon="fa fa-list-alt" url="car/manage/view" type="pop" width="80%" height="95%"></t:dgToolBar>--%>
</t:datagrid>
</body>

</body>
</html>
