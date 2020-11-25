<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2020/11/19
  Time: 18:27
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
                    <div id="officalSealManagerTable" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 脚本部分 -->
<t:datagrid actionUrl="officalSeal/manager/datagrid" tableContentId="officalSealManagerTable" searchGroupId="searchGroupId" fit="true" caption="公章管理" name="woaWorkMeetRoomList" pageSize="20" sortName="createDate" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="sealid" label="公章编号" width="100"></t:dgCol>
    <t:dgCol name="name" label="公章名称" width="80"></t:dgCol>
    <t:dgCol name="status" label="状态" width="80" dictionary="oaofficalsealstatus" display="zeroOrOne"></t:dgCol>
    <t:dgCol name="memo" label="备注" width="80"></t:dgCol>
    <t:dgCol name="opt" label="操作" ></t:dgCol>
    <t:dgDelOpt label="删除" url="officalSeal/manager/delete?id={id}"/>
    <t:dgToolBar url="officalSeal/manager/addorupdate" type="add" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar label="归还" icon="fa fa-list-alt" url="officalSeal/manager/return" type="pop" width="80%" height="95%"></t:dgToolBar>
    <t:dgToolBar url="officalSeal/manager/addorupdate" type="edit" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar url="officalSeal/manager/addorupdate" type="view" width="40%" height="70%"></t:dgToolBar>
</t:datagrid>
</body>

</html>
