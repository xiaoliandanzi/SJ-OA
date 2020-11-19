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
                    <div id="officalSealTable" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 脚本部分 -->
<t:datagrid actionUrl="oa/work/meetRoom/datagrid" tableContentId="officalSealTable" searchGroupId="searchGroupId" fit="true" caption="公章管理" name="oaWorkMeetRoomTable" pageSize="20" sortName="createDate" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="name" label="公章编码" width="100"></t:dgCol>
    <t:dgCol name="persons" label="公章名称" width="80"></t:dgCol>
    <t:dgCol name="status" label="状态" width="80" dictionary="oaworkmeet" display="zeroOrOne"></t:dgCol>
    <t:dgCol name="opt" label="操作" ></t:dgCol>
    <t:dgDelOpt label="删除" url="oa/work/meetRoom/delete?id={id}"/>
    <t:dgToolBar url="oa/work/meetRoom/addorupdate" type="add" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar url="oa/work/meetRoom/addorupdate" type="edit" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar url="oa/work/meetRoom/addorupdate" type="view" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar label="预定" icon="fa fa-cog" url="oa/work/meetRoom/bookview" type="pop" width="50%" height="70%"></t:dgToolBar>
    <t:dgToolBar label="查看预定" icon="fa fa-list-alt" url="oa/work/meetRoom/view" type="pop" width="80%" height="95%"></t:dgToolBar>
</t:datagrid>
</body>

</html>
