<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2021/1/11
  Time: 上午12:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,select2,jqgrid,datetimePicker,laydate"></t:base>
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
                    <div id="assetManageTable" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 脚本部分 -->
<t:datagrid actionUrl="asset/manage/apply/adddatagrid" tableContentId="assetManageTable" searchGroupId="searchGroupId" fit="true" caption="申请列表" name="assetManageList" pageSize="20" sortName="createDate" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="dept" label="所属科室" width="60" query="true"></t:dgCol>
    <t:dgCol name="assetName" label="入库固定资产名称" width="60" query="true"></t:dgCol>
    <t:dgCol name="quantity" label="数量" width="40"></t:dgCol>
    <t:dgCol name="amount" label="价格" width="40"></t:dgCol>
    <t:dgCol name="model" label="规格/型号" width="60"></t:dgCol>
    <t:dgCol name="address" label="移交地点" width="60"></t:dgCol>
    <t:dgDelOpt label="删除" url="aseet/manage/apply/delete?id={id}"/>
    <t:dgToolBar url="asset/manage/apply/addgo" label="入库申请" type="define" funName="apply" width="70%"></t:dgToolBar>
    <t:dgToolBar label="查看详情" icon="fa fa-eye" url="flow/biz/task/viewApprove" type="check" width="90%" height="90%"></t:dgToolBar>
    <%--<t:dgToolBar url="asset/manage/addorupdate" label="编辑" type="edit" width="70%"></t:dgToolBar>--%>
    <t:dgToolBar type="refresh" ></t:dgToolBar>
    <t:dgToolBar label="导出" type="define" funName="printIt" ></t:dgToolBar>
</t:datagrid>
</body>

<script type="text/javascript">
    function apply() {
        location.href = "asset/manage/apply/addgo";
    }

    function printIt() {
        var x = new XMLHttpRequest();
        x.open("GET","asset/export/exportApplyWord", true);
        x.responseType = 'blob';
        x.onload = function (e) {
            var url = window.URL.createObjectURL(x.response)
            var a = document.createElement('a');
            a.href = url
            a.download = "双井街道固定资产入库单.doc"
            a.click()
        }
        x.send();
        // window.location.href="asset/export/exportWord"
    }
</script>

</html>

