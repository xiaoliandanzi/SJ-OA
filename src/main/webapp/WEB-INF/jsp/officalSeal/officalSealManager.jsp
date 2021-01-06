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
<t:datagrid actionUrl="officalSeal/manager/datagrid" tableContentId="officalSealManagerTable" searchGroupId="searchGroupId" fit="true" caption="公章管理" name="officalSealList" pageSize="20" sortName="createDate" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
<%--    <t:dgCol name="departmentId" label="部门编号" width="100"></t:dgCol>--%>
    <t:dgCol name="name" label="科室名称" width="80"></t:dgCol>
    <t:dgCol name="status" label="公章借用状态" width="80" dictionary="officalsealstatus" display="status" query="true"></t:dgCol>
<%--    <t:dgCol name="overtimedays" label="借用超时天数" width="40" ></t:dgCol>--%>
<%--    <t:dgCol name="opt" label="操作" ></t:dgCol>--%>
<%--    <t:dgDelOpt label="删除" url="officalSeal/manager/delete?id={id}"/>--%>
    <t:dgToolBar url="officalSeal/manager/addorupdate" type="add" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar url="officalSeal/manager/addorupdate" type="edit" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar label="封禁" icon="fa fa-lock" type="define" funName="doBanDepartment"></t:dgToolBar>
    <t:dgToolBar label="解禁" icon="fa fa-unlock" type="define" funName="doUnsealDepartment"></t:dgToolBar>
<%--    <t:dgToolBar label="已送达" icon="fa fa-unlock" type="define" funName="returnSeal"></t:dgToolBar>--%>
</t:datagrid>
</body>
<script type="text/javascript">

    function doBanDepartment() {
        var rowId = $('#officalSealList').jqGrid('getGridParam','selrow');
        if(!rowId) {
            qhAlert('请选择要禁止借用的科室');
            return;
        }
        var rowData = $('#officalSealList').jqGrid('getRowData',rowId);
        var strtmp = rowData.status;
        var str1 = strtmp.split(">");
        var str2 = str1[1];
        var str3 = str2.split("<");
        var status = str3[0]

        if('暂停借用' == status) {
            qhAlert('该科室已被禁用！');
            return;
        }

        qhConfirm("你确定要封禁该科室吗?", function(index) {
            //关闭询问
            parent.layer.close(index);

            //是
            $.post("officalSeal/manager/lock", {id : rowId}, function(d){
                if(d.success) {
                    qhTipSuccess(d.msg);
                    //操作结束，刷新表格
                    reloadTable('officalSealList');
                }else {
                    qhTipWarning(d.msg);
                }
            });

        }, function() {
            //否
        });
    };

    function doUnsealDepartment() {
        var rowId = $('#officalSealList').jqGrid('getGridParam','selrow');
        if(!rowId) {
            qhAlert('请选择要解禁的科室');
            return;
        }
        var rowData = $('#officalSealList').jqGrid('getRowData',rowId);
        var strtmp = rowData.status;
        var str1 = strtmp.split(">");
        var str2 = str1[1];
        var str3 = str2.split("<");
        var status = str3[0]


        if('可借用' == status) {
            qhAlert('解禁失败，该科室状态正常');
            return;
        }

        qhConfirm("你确定要解禁该科室吗?", function(index) {
            //关闭询问
            parent.layer.close(index);

            //是
            $.post("officalSeal/manager/unlock", {id : rowId}, function(d){
                if(d.success) {
                    qhTipSuccess(d.msg);
                    //操作结束，刷新表格
                    reloadTable('officalSealList');
                }else {
                    qhTipWarning(d.msg);
                }
            });

        }, function() {
            //否
        });
    };

    function returnSeal() {
        var rowId = $('#officalSealList').jqGrid('getGridParam','selrow');
        if(!rowId) {
            qhAlert('请选择要归还公章的科室');
            return;
        }

        var rowData = $('#officalSealList').jqGrid('getRowData',rowId);
        var strtmp = rowData.status;
        var str1 = strtmp.split(">");
        var str2 = str1[1];
        var str3 = str2.split("<");
        var status = str3[0]

        if('暂停借用' == status) {
            qhAlert('该科室以封禁，请先解禁');
            return;
        }

        if('可借用' == status) {
            qhAlert('该科室尚未借用公章');
            return;
        }

        qhConfirm("你确定要归借用公章吗?", function(index) {
            //关闭询问
            parent.layer.close(index);

            //是
            $.post("officalSeal/manager/sealreturn", {id : rowId}, function(d){
                if(d.success) {
                    qhTipSuccess(d.msg);
                    //操作结束，刷新表格
                    reloadTable('officalSealList');
                }else {
                    qhTipWarning(d.msg);
                }
            });

        }, function() {
            //否
        });
    };

</script>
</html>
