<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,select2,icheck,laydate,jqgrid"></t:base>
    <script type="text/javascript">
        $(function () {
            $("#roleid").val("${roleId}".split(",")).trigger("change");
        });
        $(function(){
            $('#meetingId').attr('disabled', 'disabled');
        });
        $(function () {
            $("#meetingId").val("111".split(",")).trigger("change");
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
                            <label class="col-sm-2 control-label">科室*：</label>
                            <div class="col-sm-8">
                                <div class="input-group">
                                    <t:choose url="common/selectDepart" hiddenName="depId"
                                              hiddenValue="${depid }"
                                              textValue="${deptName }" textName="deptName" hiddenId="depId"
                                              textId="deptName" ></t:choose>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">登记人*：</label>
                            <div class="col-sm-8">
                                <input id="registrantName"  name="registrantName"    value="${user.realName }"   required="" minlength="2" type="text" class="form-control" required="" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">会议开始时间*：</label>
                            <div class="col-sm-8">
                                <input class="laydate-icon form-control layer-date" id="meetingTime" name="meetingTime"   required="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">会议结束时间*：</label>
                            <div class="col-sm-8">
                                <input class="laydate-icon form-control layer-date" id="meetingendTime" name="meetingendTime" required="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">会议室*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2" name="meetingId" id="meetingId" required="" >
                                    <c:forEach items="${roomList}" var="room">
                                        <option value="${room.name }">${room.name }</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">议题会议类型*：</label>
                            <div class="col-sm-8">
                                <t:dictSelect name="meetingType" type="select" typeGroupCode="meetingtype" defaultVal="1"></t:dictSelect>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">会议名称*：</label>
                            <div class="col-sm-8">
                                <input id="meetingName" name="meetingName" minlength="2"  type="text" class="form-control" required=""  >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">议题列表*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2" name="topid" id="topid" multiple="multiple" >
                                    <c:forEach items="${oalist}" var="top">
                                        <option value="${top.id }">${top.topicName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="ibox">
                            <div class="ibox-content">
                                <div id="topictable" class="topictable"></div>
                            </div>
                        </div>
<%--                        <div class="form-group">--%>
<%--                            <label class="col-sm-2 control-label">参会人员*：</label>--%>
<%--                            <div class="col-sm-8">--%>
<%--                                <select   class="col-sm-8"  name="canhuipeo" id="canhuipeo"  onchange="funcchui()"  style=" width:663px;height: 32px" >--%>
<%--                                    <option class="col-sm-8"  id="1">主要领导</option>--%>
<%--                                    <option  class="col-sm-8" id="2">主管领导</option>--%>
<%--                                    <option  class="col-sm-8"  id="3" >科室负责人</option>--%>
<%--                                    <option  class="col-sm-8"  id="4" >科员</option>--%>
<%--                                </select>--%>
<%--                            </div>--%>
<%--                        </div>--%>
                        <div class="form-group">
<%--                            <label class="col-sm-2 control-label">人员*：</label>--%>
<%--                            <div class="col-sm-8" >--%>
<%--                                <select class="form-control m-b select2" name="conferee"  style=" width:663px; height: 80px" id="conferee" multiple="multiple"  style="  height: 100px"  required="">--%>
<%--                                    <c:forEach items="${dataList}" var="peo">--%>
<%--                                        <option value="${peo.realName }">${peo.realName}</option>--%>
<%--                                    </c:forEach>--%>
<%--                                </select>--%>
<%--                            </div>--%>
                                    <label class="col-sm-2 control-label">人员：</label>
                                    <div class="col-sm-4">
                                        <div class="input-group" style="width: 220%">
                                            <t:choose url="common/selectUsers" hiddenName="attendeeId" hiddenValue="${attendeeId}" textValue="${meet.attendee}" textName="conferee" hiddenId="attendeeId" textId="conferee"></t:choose>
                                        </div>
                                    </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">备注：</label>
                            <div class="col-sm-4 m-b" >
                                <textarea name="memo" class="form-control"  style=" width:663px;"></textarea>
                            </div>
                        </div>
                    </t:formvalid>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 脚本部分 -->
<t:datagrid actionUrl="meeting/tables"   tableContentId="topictable" searchGroupId="searchGroupId" fit="true"
            multiSelect="true"     rownumbers="true"    caption="议题列表" name="toptable"  pageSize="20" sortName="creatTime" sortOrder="desc" >

    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="creatTime" label="申报日期" width="300" query="false"></t:dgCol>
    <t:dgCol name="topicName" label="议题名称" width="150" query="flase"></t:dgCol>
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
    <t:dgCol name="isHistory" label="历史议题" query="flase" replace="是_1, 否_0"></t:dgCol>
    <t:dgCol name="opt" label="操作" width="290"></t:dgCol>
    <t:dgToolBar label="添加议题" icon="fa fa-clock-o" type="define" funName="addbt"></t:dgToolBar>
    <t:dgToolBar label="批量打印" icon="fa fa-clock-o" type="define" funName="printItAll"></t:dgToolBar>
    <t:dgToolBar label="打印议题单" icon="fa fa-clock-o" type="define" funName="printItAll"></t:dgToolBar>
    <t:dgToolBar label="批量删除" icon="fa fa-clock-o" type="define" funName="deleteAll"></t:dgToolBar>
    <t:dgToolBar label="打印签到表" icon="fa fa-clock-o" type="define" funName="printqd"></t:dgToolBar>
    <t:dgToolBar label="批量下载" type="define" funName="piliangxiazai"></t:dgToolBar>
    <t:dgDelOpt label="删除" url="meeting/tablesdel?id={id}" />
</t:datagrid>

</body>
<script type="text/javascript">
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
    /* function funtimes(){
         var meetingTime=$("#meetingTime").val();
         var meetingendTime=$("#meetingendTime").val();
         alert(meetingTime)
         alert(meetingendTime)
         alert(3)
         if (meetingTime==""||meetingTime==null||meetingendTime==""||meetingendTime==null) {
             alert(2)
             $('#meetingId').attr('disabled', 'disabled');
         }else{
             alert(1)
             $("#meetingId").removeAttr("disabled");
         }
     }
     function funtime(){
         var meetingTime=$("#meetingTime").val();
         var meetingendTime=$("#meetingendTime").val();
         if (meetingTime==""||meetingTime==null||meetingendTime==""||meetingendTime==null) {
             $('#meetingId').attr('disabled', 'disabled');
         }else{
             $("#meetingId").removeAttr("disabled");
         }
     }*/
    function printqd() {
        var conferee=$("#conferee").val();
        if(conferee==""||conferee==null){
            qhAlert('参会人员为空不能打印');
            return;
        }
        var x = new XMLHttpRequest();
        x.open("GET", "topicFile/getprintqdHtml?conferee="+conferee,true);
        x.responseType = 'blob';
        x.onload = function (e) {
            var url = window.URL.createObjectURL(x.response)
            var a = document.createElement('a');
            a.href = url
            a.download = "签到表.doc"
            a.click()
        }
        x.send();

    }

    $(function () {
        $('#meetingId').removeAttr("disabled");
        $('#meetingId').change(function () {
            var meetingTime=$("#meetingTime").val();
            var meetingendTime=$("#meetingendTime").val();
            var meetingId=$("#meetingId").val();
            //var id=$("#id").val();
            //是
            $.post("meeting/isoccUpy", {meetingTime: meetingTime,meetingendTime:meetingendTime,meetingId:meetingId}, function (data) {
                if ("111"==data.obj) {
                    parent.layer.msg('会议室被占用');
                    // qhAlert('');
                }
            });
        })
    })

    function printItAll() {
        var meetingId=$("#meetingId").val();
        var meetingName=$("#meetingName").val();
        var meetingTime=$("#meetingTime").val();
        var id=$("#id").val();
        var rowIds = $('#toptable').jqGrid('getGridParam', 'selarrrow');
        if (meetingId==""||meetingId==null) {
            qhAlert('会议室未未选择不能打印');
            return;
        }
        if (meetingName==""||meetingName==null) {
            qhAlert('会议名称未填写不能打印');
            return;
        }
        if (meetingTime==""||meetingTime==null) {
            qhAlert('会议时间未填写不能打印');
            return;
        }
        if (rowIds==""||rowIds==null) {
            qhAlert('请选择要打印的议题');
            return;
        }
        var x = new XMLHttpRequest();
        x.open("GET", "topicFile/getMeetingHtml?issueId=" + rowIds+"&meetingId="+meetingId+"&meetingName="+meetingName+"&meetingTime="+meetingTime+"&id="+id, true);
        x.responseType = 'blob';
        x.onload = function (e) {
            var url = window.URL.createObjectURL(x.response)
            var a = document.createElement('a');
            a.href = url
            a.download = "议题单.doc"
            a.click()
        }
        x.send();
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

    function deleteAll() {
        var rowIds = $('#toptable').jqGrid('getGridParam', 'selarrrow');
        if (rowIds==""||rowIds==null) {
            qhAlert('请选择要删除的数据');
            return;
        }
        //是
        $.post("meeting/deleteAll", {ids: rowIds.toString()}, function (data) {
            if (data.success) {
                qhTipSuccess(data.msg);
                //操作结束，刷新表格
                reloadTable('toptable');
            } else {
                qhTipWarning(data.msg);
            }
        });
    }

    var start=    ({
        elem: "#meetingTime",
        event: "focus",
        istime: true,
        format: 'YYYY-MM-DD hh:mm:ss',
        min: laydate.now(), //最大日期,
        choose: function(datas){
            end.min = datas; //开始日选好后，重置结束日的最小日期
            var meetingTime=datas;
            var meetingendTime=$("#meetingendTime").val();
            if (meetingTime==""||meetingTime==null||meetingendTime==""||meetingendTime==null) {
                $('#meetingId').attr('disabled', 'disabled');
            }else{
                $("#meetingId").removeAttr("disabled");
            };
        }
    });
    var end=   ({
        elem: "#meetingendTime",
        event: "focus",
        istime: true,
        format: 'YYYY-MM-DD hh:mm:ss',
        choose: function(datas){
            var meetingTime=$("#meetingTime").val();
            var meetingendTime=datas;
            if (meetingTime==""||meetingTime==null||meetingendTime==""||meetingendTime==null) {
                $('#meetingId').attr('disabled', 'disabled');
            }else{
                $("#meetingId").removeAttr("disabled");
            };
        }
    });
    //  });
    function addbt(){
        var  topid= $("#topid").val();
        if (topid==null){
            topid="";
        }
        $.ajax({
            url:"meeting/setTopid" ,
            data: {ids:topid.toString()},
            success: function(){
                reloadTable('toptable');
            },
        });

    }
    laydate(start);
    laydate(end);
    function funcchui(){
        //    $("#conferee").empty();
        $.ajax({
            url:"meeting/groupBycanhui" ,
            data: {canHuitype: $('#canhuipeo option:selected').attr('id')},
            success: function(data){
                var len=data.obj.length;
                for(var i=0;i<len;i++){
                    var option=document.createElement("option");
                    option.value = data.obj[i].realName;
                    option.Text = data.obj[i].realName;
                    option.innerHTML=data.obj[i].realName;
                    $("#conferee").append(option);
                }
            },
            error:function(data){
                console.log(data);
            }
        });
    }
</script>
</html>

