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
                    <div id="carManageTable" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 脚本部分 -->
<t:datagrid actionUrl="car/manage/datagrid" tableContentId="carManageTable" searchGroupId="searchGroupId" fit="true" caption="车辆管理" name="carManageList" pageSize="20" sortName="createDate" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="carId" label="车牌号" width="100"></t:dgCol>
    <t:dgCol name="kind" label="车辆类型" width="100"></t:dgCol>

    <t:dgCol name="onRoadTime" label="上路时间" width="80" datefmt="yyyy-MM-dd"></t:dgCol>
    <t:dgCol name="ensureTime" label="保险日期" width="80" datefmt="yyyy-MM-dd"></t:dgCol>
    <t:dgCol name="ensureDay" label="保险提醒频率/天" width="100"></t:dgCol>

    <t:dgCol name="maintainTime" label="保养日期" width="80" datefmt="yyyy-MM-dd"></t:dgCol>
    <t:dgCol name="maintainDay" label="保养提醒频率/天" width="100"></t:dgCol>
    <t:dgCol name="checkCarTime" label="验车日期" width="80" datefmt="yyyy-MM-dd"></t:dgCol>

    <t:dgCol name="opt" label="操作" ></t:dgCol>
    <t:dgDelOpt label="删除" url="car/manage/delete?id={id}"/>
    <t:dgToolBar url="car/manage/addorupdate" type="add" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar url="car/manage/addorupdate" type="edit" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar url="car/manage/addorupdate" type="view" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar type="print" width="40%" height="70%"></t:dgToolBar>
<%--    <t:dgToolBar label="预定" icon="fa fa-cog" url="car/manage/bookview" type="pop" width="50%" height="70%"></t:dgToolBar>--%>
<%--    <t:dgToolBar label="查看预定" icon="fa fa-list-alt" url="car/manage/view" type="pop" width="80%" height="95%"></t:dgToolBar>--%>
</t:datagrid>
</body>

</html>

