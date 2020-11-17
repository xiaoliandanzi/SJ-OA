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
                    <div id="carListTable" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 脚本部分 -->
<t:datagrid actionUrl="car/list/datagrid" tableContentId="carListTable" searchGroupId="searchGroupId" fit="true" caption="车辆列表" name="carList" pageSize="20" sortName="createDate" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="carId" label="车牌号" width="100"></t:dgCol>
    <t:dgCol name="carName" label="汽车名称" width="100"></t:dgCol>
    <t:dgCol name="persons" label="载人数" width="80"></t:dgCol>
    <t:dgCol name="status" label="状态" width="80" dictionary="oaworkmeet" display="zeroOrOne"></t:dgCol>
</t:datagrid>
</body>

</html>
