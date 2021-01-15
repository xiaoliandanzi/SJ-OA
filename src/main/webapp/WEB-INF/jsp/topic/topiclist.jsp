<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
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
                    <div id="jqGrid_wrapper" class="jqGrid_wrapper"></div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- 脚本部分 -->
<t:datagrid actionUrl="topic/table" tableContentId="jqGrid_wrapper" searchGroupId="searchGroupId" fit="true"
            caption="议题申请" name="topicAddList" pageSize="20" sortName="creatTime" sortOrder="desc">
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <%-- <t:dgCol name="creatTime" label="申报日期" width="300" query="true"></t:dgCol>--%>
    <t:dgCol name="creatTime" label="申报日期" width="300" datefmt="yyyy-MM-dd HH:mm:ss" datePlugin="laydate" query="true"
             queryModel="group"></t:dgCol>
    <t:dgCol name="topicName" label="议题名称" width="150" query="true"></t:dgCol>
    <t:dgCol name="proposeLeaderName" label="提议领导" query="false"></t:dgCol>
    <t:dgCol name="reportName" label="汇报人" query="false"></t:dgCol>
    <t:dgCol name="isPassOne" label="科室负责人" query="false" replace="驳回_2,通过_1, _0"></t:dgCol>
    <t:dgCol name="isPassTwo" label="主管领导" query="false" replace="驳回_2,通过_1, _0"></t:dgCol>
    <t:dgCol name="isPassThree" label="综合办" query="false" replace="驳回_2,通过_1, _0"></t:dgCol>
    <t:dgCol name="isPassFour" label="财务科" query="false" replace="驳回_2,通过_1, _0"></t:dgCol>
    <t:dgCol name="isPassFive" label="纪委" query="false" replace="驳回_2,通过_1, _0"></t:dgCol>
    <t:dgCol name="isSecretary" label="书记会" dictionary="byesorno" query="false"></t:dgCol>
    <t:dgCol name="isDirector" label="主任会" dictionary="byesorno" query="flase"></t:dgCol>
    <t:dgCol name="isWorkingCommittee" label="工委会" dictionary="byesorno" query="flase"></t:dgCol>
    <t:dgCol name="allPass" label="通过审核" query="true" replace="是_1, 否_0"></t:dgCol>
    <t:dgCol name="isHistory" label="历史议题" query="true" replace="是_1, 否_0"></t:dgCol>
    <t:dgToolBar url="topic/saveOrUpdateView" type="add" width="60%" operationCode="topic:add"></t:dgToolBar>
    <t:dgToolBar url="topic/saveOrUpdateView" type="edit" width="60%" operationCode="topic:add"></t:dgToolBar>
    <t:dgToolBar label="查看" type="define" funName="getOne"></t:dgToolBar>
    <t:dgToolBar label="打印" type="define" funName="printIt"></t:dgToolBar>
    <t:dgToolBar label="取消" type="define" funName="remove" operationCode="topic:add"></t:dgToolBar>
</t:datagrid>
<script type="text/javascript">
    $(function () {
        laydate({elem: "#creatTime_begin", event: "focus", istime: true, format: 'YYYY-MM-DD hh:mm:ss'});
        laydate({elem: "#creatTime_end", event: "focus", istime: true, format: 'YYYY-MM-DD hh:mm:ss'});
    });

    function getOne() {
        var rowId = $('#topicAddList').jqGrid('getGridParam', 'selrow');
        if (!rowId) {
            qhAlert('请选择要查看的议题');
            return;
        }
        popNoForMe("topicAddList", "topic/saveOrUpdateView?id=" + rowId + "&params=1", "查看", "60%", "80%");
    }

    function printIt() {
        var rowId = $('#topicAddList').jqGrid('getGridParam', 'selrow');
        if (!rowId) {
            qhAlert('请选择要打印的议题');
            return;
        }
        printTopic("topicAddList", "topic/printTopic?id=" + rowId, "打印", "60%", "80%");
    }


    function remove() {
        var rowId = $('#topicAddList').jqGrid('getGridParam', 'selrow');
        var topic = {};
        if (!rowId) {
            qhAlert('请选择要取消的议题');
            return;
        }
        $.get("topic/getOne?id=" + rowId, null, function (data) {
            if (data.success) {
                topic = data.obj;
            } else {
                qhTipWarning(data.msg);
            }
        })
        console.log(topic)
        if (topic.isPassOne != 0) {
            qhAlert('该议题禁止取消');
            return;
        }

        qhConfirm("你确定要删除该议题吗?", function (index) {
            parent.layer.close(index);

            $.get("topic/remove?id=" + rowId, null, function (data) {
                if (data.success) {
                    qhTipSuccess(data.msg);
                    //操作结束，刷新表格
                    reloadTable('topicAddList');
                } else {
                    qhTipWarning(data.msg);
                }
            });
        }, function () {
            //否
        });

    }
</script>
</body>
</html>