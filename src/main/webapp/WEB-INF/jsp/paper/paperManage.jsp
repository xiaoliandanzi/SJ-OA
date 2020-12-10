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
    <t:dgCol name="opt" label="操作" ></t:dgCol>
    <t:dgDelOpt label="查看" url="paper/manage/paperview?id={id}"/>
</t:datagrid>
</body>

</html>

