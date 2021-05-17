<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,select2,icheck,laydate,jqgrid"></t:base>
    <script type="text/javascript">
        $(function () {
            $("#meetingId").val("${oaMeeting.meetingId}".split(",")).trigger("change");
        });
    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <t:formvalid action="meeting/save">
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">会议名称*：</label>
                            <div class="col-sm-4 m-b">
                                <input id="meetingName" style="width: 401px" name="meetingName"
                                       value="${oaMeeting.meetingName}" minlength="2"  type="text"
                                       class="form-control" required="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">议题会议类型：</label>
                            <div class="col-sm-4 m-b">
                                <input id="meetingType" style="width: 401px" name="meetingType" value="${meetingType}"
                                       minlength="2" maxlength="10" type="text" class="form-control" required="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">会议开始时间*：</label>
                            <div class="col-sm-4 m-b">
                                <input class="laydate-icon form-control layer-date" id="meetingTime"
                                       value="${oaMeeting.meetingTime}" name="meetingTime" required="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">会议结束时间*：</label>
                            <div class="col-sm-4 m-b">
                                <input class="laydate-icon form-control layer-date" id="meetingendTime"
                                       value="${oaMeeting.meetingendTime}" name="meetingendTime" required="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">会议室：</label>
                            <div class="col-sm-4">
                                <select class="form-control m-b select2" name="meetingId" id="meetingId"
                                        multiple="multiple" style="width: 401px">
                                    <c:forEach items="${roomList}" var="room">
                                        <option value="${room.name }">${room.name }</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="ibox">
                            <div class="ibox-content">
                                <div id="topictable" class="topictable"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">备注：</label>
                            <div class="col-sm-4 m-b">
                                <input name="memo" style="width: 401px; height:40px" value=${oaMeeting.memo}></input>
                            </div>
                        </div>
                    </t:formvalid>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 脚本部分 -->
<t:datagrid actionUrl="meeting/tablechakanyiti" tableContentId="topictable" searchGroupId="searchGroupId" fit="true"
            multiSelect="true" rownumbers="true" caption="议题列表" name="toptable" pageSize="20" sortName="creatTime"
            sortOrder="desc">

    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="creatTime" label="申报日期" width="300" query="false"></t:dgCol>
    <t:dgCol name="topicName" label="议题名称" width="150" query="flase"></t:dgCol>
    <t:dgCol name="proposeLeaderName" label="提议领导" query="false"></t:dgCol>
    <t:dgCol name="reportName" label="汇报人" query="false"></t:dgCol>
    <t:dgCol name="isPassOne" label="科室负责人" query="false" replace="驳回_2,通过_1, _0"></t:dgCol>
    <t:dgCol name="isPassTwo" label="主管领导" query="false" replace="驳回_2,通过_1, _0"></t:dgCol>
    <t:dgCol name="isPassThree" label="综合办" query="false" replace="驳回_2,通过_1, _0"></t:dgCol>
    <t:dgCol name="isPassFour" label="财务审查" query="false" replace="驳回_2,通过_1, _0"></t:dgCol>
    <t:dgCol name="isPassFive" label="纪检审查" query="false" replace="驳回_2,通过_1, _0"></t:dgCol>
    <t:dgCol name="isSecretary" label="书记会" dictionary="byesorno" query="false"></t:dgCol>
    <t:dgCol name="isDirector" label="主任会" dictionary="byesorno" query="flase"></t:dgCol>
    <t:dgCol name="isWorkingCommittee" label="工委会" dictionary="byesorno" query="flase"></t:dgCol>
    <t:dgCol name="isHistory" label="历史议题" query="flase" replace="是_1, 否_0"></t:dgCol>
    <t:dgToolBar label="批量下载" type="define" funName="piliangxiazai"></t:dgToolBar>
</t:datagrid>

</body>
<script type="text/javascript">
    $(function () {

        laydate({
            elem: "#meetingTime",
            event: "focus",
            istime: true,
            format: 'YYYY-MM-DD hh:mm:ss'
        });
        laydate({
            elem: "#meetingendTime",
            event: "focus",
            istime: true,
            format: 'YYYY-MM-DD hh:mm:ss'
        });
    });

    var DD="";
    function piliangxiazai() {
        var rowId = $('#toptable').jqGrid('getGridParam', 'selarrrow');
        if (!rowId) {
            qhAlert('请选择要下载的附件');
            return;
        }
        //是
        $.post("meeting/getFileListS", {ids: rowId.toString()}, function (data) {
            if (data.success) {
                //操作结束，刷新表格
                for (var i = 0; i < data.obj.length; i++) {
                    console.log(data.obj[i])
                    var fileId = data.obj[i];
                    down(fileId);
                    sleep(2000);
                }
            } else {
            }
        });
    }
    function sleep(delay) {
        for(let start = Date.now(); Date.now() - start< delay;);
    }
 function  down(fileId){
     var filename = '';
     $.get("topicFile/name?id=" + fileId, null, function (data) {
         if (data.success) {
             filename = data.obj;
         } else {
             qhTipWarning(data.msg);
         }
     })
     //  setTimeout(function () {
     var x = new XMLHttpRequest();
     x.open("POST", "topicFile/down?id=" + fileId, true);
     x.responseType = 'blob';
     x.onload = function (e) {
         var URL = window.URL.createObjectURL(x.response)
         var a = document.createElement('a');
         a.href = URL
         a.download = filename
         a.click()
     }
     x.send();
 }

    function addbt() {
        var topid = $("#topid").val();
        if (topid == null) {
            topid = "";
        }
        $.ajax({
            url: "meeting/setTopid",
            data: {ids: topid.toString()},
            success: function () {
                reloadTable('toptable');
            },
        });

    }

    function func(st) {
        $("#conferee").empty();
        $.ajax({
            url: "meeting/groupBycanhui",
            data: {canHuitype: st},
            success: function (data) {
                var len = data.obj.length;
                for (var i = 0; i < len; i++) {
                    var option = document.createElement("option");
                    option.value = data.obj[i].realName;
                    option.Text = data.obj[i].realName;
                    option.innerHTML = data.obj[i].realName;

                    $("#conferee").append(option);
                }
            },
            error: function (data) {
                console.log(data);
            }
        });
    }
</script>
</html>

