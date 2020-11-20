<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/11/16
  Time: 上午1:39
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
                    <div id="officalSealListTable" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 脚本部分 -->
<t:datagrid actionUrl="officalSeal/list/datagrid" tableContentId="officalSealListTable" searchGroupId="searchGroupId" fit="true" caption="公章列表" name="sealList" pageSize="20" sortName="createDate" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="sealId" label="公章ID" width="40"></t:dgCol>
    <t:dgCol name="sealName" label="公章名称" width="60"></t:dgCol>
    <t:dgCol name="sealName" label="公章用途" width="120"></t:dgCol>
    <t:dgCol name="status" label="状态" width="80" dictionary="oaworkmeet" display="zeroOrOne"></t:dgCol>
</t:datagrid>
</body>

</html>

