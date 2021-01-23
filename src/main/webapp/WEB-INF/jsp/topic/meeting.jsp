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
                    <div id="jqGrid_wrapper3" class="jqGrid_wrapper3"></div>
                    <button id="add" onclick="add()" >议题会议发起</button>
                    <button id="chakanhuiyi" onclick="chakanhuiyi()" >查看议题</button>
                    <div id="jqGrid_wrapper" class="jqGrid_wrapper"></div>
                    <button id="chakan" onclick="chakan()" >查看</button>
                    <button id="bianji" onclick="bianji()" >编辑</button>
                    <button id="tongzhi" onclick="tongzhi()" >通知详情</button>
                    <div id="jqGrid_wrappers" class="jqGrid_wrappers"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 脚本部分 -->
<t:datagrid actionUrl="meeting/table" tableContentId="jqGrid_wrappers" searchGroupId="searchGroupId" fit="true"
            multiSelect="false"    sortable="true"    rownumbers="true"    caption="会议列表" name="table_list_2"  pageSize="20" sortName="meetingTime" sortOrder="desc" >
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="stateId" label="状态" width="150" query="false"></t:dgCol>
    <t:dgCol name="deptName" label="使用科室" width="150" query="false"></t:dgCol>
    <t:dgCol name="registrantName" label="登记人" query="false"></t:dgCol>
    <t:dgCol name="meetingId" label="会议室" query="false"></t:dgCol>
    <t:dgCol name="meetingTime" label="会议开始时间" query="false"></t:dgCol>
    <t:dgCol name="meetingendTime" label="会议结束时间" query="false"></t:dgCol>
    <t:dgCol name="meetingName" label="会议名称" query="false"></t:dgCol>
</t:datagrid>
<!-- 脚本部分 -->
<t:datagrid actionUrl="meeting/tableAll" tableContentId="jqGrid_wrapper" searchGroupId="searchGroupId" fit="true"
            multiSelect="true"    sortable="true"    rownumbers="true"    caption="议题列表" name="table_list_1"  pageSize="20" sortName="creatTime" sortOrder="desc" >
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="creatTime" label="申报日期" width="300" query="true" datefmt="yyyy-MM-dd HH:mm:ss" datePlugin="laydate"  queryModel="group"></t:dgCol>
    <t:dgCol name="topicName" label="议题名称" width="150" query="true"></t:dgCol>
    <t:dgCol name="proposeLeaderName" label="提议领导" query="false"></t:dgCol>
    <t:dgCol name="opinion" label="会议类型" hidden="true" query="true"   replace="书记会_书记会,工委会_工委会,主任会_主任会"></t:dgCol>
    <t:dgCol name="reportName" label="汇报人" query="false"></t:dgCol>
    <t:dgCol name="isPassOne" label="科室负责人" query="false" replace="驳回_2,通过_1, _0"></t:dgCol>
    <t:dgCol name="isPassTwo" label="主管领导" query="false" replace="驳回_2,通过_1, _0"></t:dgCol>
    <t:dgCol name="isPassThree" label="综合办" query="false" replace="驳回_2,通过_1, _0"></t:dgCol>
    <t:dgCol name="isPassFour" label="财务科" query="false" replace="驳回_2,通过_1, _0"></t:dgCol>
    <t:dgCol name="isPassFive" label="纪委" query="false" replace="驳回_2,通过_1, _0"></t:dgCol>
    <t:dgCol name="isSecretary" label="书记会" dictionary="byesorno" query="false"></t:dgCol>
    <t:dgCol name="isDirector" label="主任会" dictionary="byesorno" query="flase"></t:dgCol>
    <t:dgCol name="isWorkingCommittee" label="工委会" dictionary="byesorno" query="flase"></t:dgCol>
    <t:dgCol name="isHistory" label="历史议题" query="true" replace="是_1, 否_0"></t:dgCol>
  <%--  <t:dgCol name="opt" label="操作" width="290"></t:dgCol>
    <t:dgToolBar label="议题会议发起"  type="define" funName="add"></t:dgToolBar>
    <t:dgToolBar label="议题列表"    type="define" funName="yitilb"></t:dgToolBar>
    <t:dgToolBar label="会议列表"  type="define" funName="huiyilb"></t:dgToolBar>--%>
</t:datagrid>
<!-- 脚本部分 -->
<t:datagrid actionUrl="meeting/tableAll" tableContentId="jqGrid_wrapper3" searchGroupId="searchGroupId" fit="true"
            multiSelect="true"     rownumbers="true"   caption="议题列表11" name="table_list_3"  pageSize="20" sortOrder="desc" >
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="topicName" label="议题名称" width="150" query="false"></t:dgCol>
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
    <t:dgCol name="isHistory" label="历史议题" query="false" replace="是_1, 否_0"></t:dgCol>
    <t:dgCol name="isWorkingCommittee" label="工委会" replace="○_1, _0" query="false"></t:dgCol>
    <t:dgCol name="opt" label="操作" width="290"></t:dgCol>
    <t:dgToolBar label="议题列表"    type="define" funName="yitilb"></t:dgToolBar>
    <t:dgToolBar label="会议列表"  type="define" funName="huiyilb"></t:dgToolBar>
</t:datagrid>
<script type="text/javascript">
    $(function () {
        laydate({elem: "#creatTime_begin", event: "focus", istime: true, format: 'YYYY-MM-DD hh:mm:ss'});
        laydate({elem: "#creatTime_end", event: "focus", istime: true, format: 'YYYY-MM-DD hh:mm:ss'});
    });
    //自动加载
    window.onload = function(){
        $("#jqGrid_wrappers").hide();
        $("#jqGrid_wrapper3").hide();
        $("#chakan").hide();
        $("#chakanhuiyi").show();
        $("#add").show();
        $("#tongzhi").hide();
        $("#bianji").hide();
    }
    function chakanhuiyi() {
        var rowId = $('#table_list_1').jqGrid('getGridParam', 'selrow');
        if (!rowId) {
            qhAlert('请选择要查看的议题');
            return;
        }
        popNoForMe("table_list_1", "topic/auditModel?id=" + rowId + "&params=1", "查看", "60%", "80%");
    }

    //发起议题会议
    function add(){
        var ids =$('#table_list_1').jqGrid('getGridParam', 'selarrrow');
        if (ids==""||ids==null) {
            qhAlert('请选择要发起的议题');
            return;
        }
        popNo("table_list_1", "meeting/saveOrUpdateView?ids="+ids.toString(), "增加议题会议", "70%", "80%");
    }
    //议题列表
    function yitilb() {
        reloadTable('table_list_1');
        $("#jqGrid_wrapper").show();
        $("#jqGrid_wrappers").hide();
        $("#jqGrid_wrapper3").hide();
        $("#chakan").hide();
        $("#tongzhi").hide();
        $("#chakanhuiyi").show();
        $("#add").show();
        $("#bianji").hide();
    }
    //会议列表
    function huiyilb() {
        reloadTable('table_list_2');
        $("#jqGrid_wrappers").show();
       $("#jqGrid_wrapper").hide();
        $("#jqGrid_wrapper3").hide();
        $("#chakan").show();
        $("#tongzhi").show();
        $("#chakanhuiyi").hide();
        $("#add").hide();
        $("#bianji").show();
    }
    //会议查看
    function chakan(){
        var rowIds = $("#table_list_2").jqGrid('getGridParam', 'selrow');
        var list=$("#table_list_2").jqGrid('getRowData',rowIds);
        console.log(list)
        if(!list.id){
            qhAlert('请选择一条要查看的会议详情');
            return;
        }
        if(list.stateId!='已结束'){
            qhAlert('只有已结束的数据方可查看');
            return;
        }
        popNoYT("table_list_1", "meeting/lookView?id="+list.id.toString(), "查看议题会议", "70%", "80%");
    }
    //通知详情
    function tongzhi(){
        var rowIds = $("#table_list_2").jqGrid('getGridParam', 'selrow');
        var list=$("#table_list_2").jqGrid('getRowData',rowIds);
        if(!list.id){
            qhAlert('请选择一条要查看的通知详情');
            return;
        }
        popNoYT("table_list_2", "notificationform/saveOrUpdateView?huiyiid=" +list.id.toString(),  "通知详情", "70%", "80%");
    }
    //会议编辑
    function bianji(){
        var rowIds = $("#table_list_2").jqGrid('getGridParam', 'selrow');
        var list=$("#table_list_2").jqGrid('getRowData',rowIds);
        if(!list.id){
            qhAlert('请选择一条要编辑的会议详情');
            return;
        }
        if(list.stateId=='已结束'){
            qhAlert('未开始和进行中的数据方可编辑');
            return;
        }
        popNo("table_list_2", "meeting/editView?id="+list.id.toString(), "编辑议题会议", "70%", "80%");
    }
    //锁定用户
    function lockUser() {
        var rowId = $('#table_list_2').jqGrid('getGridParam', 'selrow');
        if (!rowId) {
            qhAlert('请选择要编辑的项目');
            return;
        }

        qhConfirm("你确定要锁定该用户吗?", function (index) {
            //关闭询问
            parent.layer.close(index);

            //是
            $.post("sys/user/lock", {id: rowId}, function (data) {
                if (data.success) {
                    qhTipSuccess(data.msg);
                    //操作结束，刷新表格
                    reloadTable('table_list_2');
                } else {
                    qhTipWarning(data.msg);
                }
            });

        }, function () {
            //否
        });
    }

    //解锁用户
    function unLockUser() {
        var rowId = $('#table_list_2').jqGrid('getGridParam', 'selrow');
        if (!rowId) {
            qhAlert('请选择要编辑的项目');
            return;
        }

        qhConfirm("你确定要解锁该用户吗?", function (index) {
            //关闭询问
            parent.layer.close(index);

            //是
            $.post("sys/user/unlock", {id: rowId}, function (data) {
                if (data.success) {
                    qhTipSuccess(data.msg);
                    //操作结束，刷新表格
                    reloadTable('table_list_2');
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
