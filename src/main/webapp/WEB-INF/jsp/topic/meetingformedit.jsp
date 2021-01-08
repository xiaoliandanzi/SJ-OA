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
        $(function () {
            $("#meetingId").val("${meetingId}".split(",")).trigger("change");
        });
        $(function () {
            $("#conferee").val("${conferee}".split(",")).trigger("change");
        });
        $(function () {
            $("#canhuipeo").val("${canhuipeo}".split(",")).trigger("change");
        });

    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <t:formvalid action="meeting/edit">
                        <input type="hidden" name="id" id="id" value="${oaMeeting.id}">
                        <input type="hidden" name="stateId" id="stateId" value="${oaMeeting.stateId}">
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">科室*：</label>
                            <div class="col-sm-4 m-b">
                                <div class="input-group">
                                    <t:choose url="common/selectDepart" hiddenName="deptId"
                                              hiddenValue="${depname }"
                                              textValue="${depname }" textName="deptName" hiddenId="departId"
                                              textId="departLabel"></t:choose>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">登记人*：</label>
                            <div class="col-sm-4 m-b">
                                <input id="registrantName"  name="registrantName"    value="${registrantName}" readonly="value" minlength="2" type="text" class="form-control" required="" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">会议开始时间*：</label>
                            <div class="col-sm-4 m-b">
                                <input class="laydate-icon form-control layer-date" id="meetingTime" value="${meetingTime}"  name="meetingTime" required="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">会议结束时间*：</label>
                            <div class="col-sm-4 m-b">
                                <input class="laydate-icon form-control layer-date" id="meetingendTime" value="${meetingendTime}"  name="meetingendTime" required="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">会议室：</label>
                            <div class="col-sm-4">
                                <select class="form-control m-b select2" name="meetingId" id="meetingId"   multiple="multiple" >
                                    <c:forEach items="${roomList}" var="room">
                                        <option value="${room.name }">${room.name }</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">议题会议类型：</label>
                            <div class="col-sm-4 m-b">
                                <t:dictSelect name="meetingType" type="select" typeGroupCode="meetingtype" defaultVal="${meetingType}" ></t:dictSelect>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">会议名称*：</label>
                            <div class="col-sm-4 m-b">
                                <input id="meetingName" name="meetingName" minlength="2"   value="${meetingName}" maxlength="10" type="text" class="form-control" required=""  >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">议题列表：</label>
                            <div class="col-sm-4">
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
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">参会人员：</label>
                            <div class="col-sm-4 m-b">
                        <select    name="canhuipeo" id="canhuipeo">
                            <option value="1" onclick="func(1)">主要领导</option>
                            <option value="2" onclick="func(2)">主管领导</option>
                            <option value="3" onclick="func(3)">科室负责人</option>
                            <option value="4" onclick="func(4)">科员</option>
                        </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">人员：</label>
                            <div class="col-sm-4">
                                <select class="form-control m-b select2" name="conferee" id="conferee" multiple="multiple" >
                                    <c:forEach items="${dataList}" var="peo">
                                        <option value="${peo.realName }">${peo.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">备注：</label>
                            <div class="col-sm-4 m-b">
                                <textarea name="memo" class="form-control"></textarea>
                            </div>
                        </div>





                    </t:formvalid>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 脚本部分 -->
<t:datagrid actionUrl="meeting/tablebianji"   tableContentId="topictable" searchGroupId="searchGroupId" fit="true"
            multiSelect="true"     rownumbers="true"    caption="议题列表" name="toptable"  pageSize="20" sortName="creatTime" sortOrder="desc" >
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="creatTime" label="申报日期" width="250" query="false"></t:dgCol>
    <t:dgCol name="topicName" label="议题名称" width="160" query="false"></t:dgCol>
    <t:dgCol name="proposeLeaderName" label="提议领导" query="false"></t:dgCol>
    <t:dgCol name="reportName" label="汇报人" query="false"></t:dgCol>
    <t:dgCol name="deptLeaderName" label="科室负责人" query="false"></t:dgCol>
    <t:dgCol name="leaderName" label="主管领导" query="false"></t:dgCol>
    <t:dgCol name="generalOfficeName" label="综合办" query="false"></t:dgCol>
    <t:dgCol name="financeName" label="财务科" query="false"></t:dgCol>
    <t:dgCol name="disciplineName" label="纪委" query="false"></t:dgCol>
    <t:dgCol name="isSecretary" label="书记会" dictionary="byesorno" query="false"></t:dgCol>
    <t:dgCol name="isWorkingCommittee" label="主任会" dictionary="byesorno" query="flase"></t:dgCol>
    <t:dgCol name="isDirector" label="工委会" dictionary="byesorno" query="flase"></t:dgCol>
    <t:dgCol name="opt" label="操作" width="290"></t:dgCol>
    <t:dgToolBar label="添加标题" icon="fa fa-clock-o" type="define" funName="addbt"></t:dgToolBar>
    <t:dgDelOpt label="删除" url="meeting/bjtablesdel?id={id}" />
</t:datagrid>
</body>
<script type="text/javascript">
    $(function() {

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
  function addbt(){
      var  topid= $("#topid").val();
      if (topid==null){
          topid="";
      }
          $.ajax({
              url:"meeting/seteditopid" ,
              data: {ids:topid.toString()},
              success: function(){
                  reloadTable('toptable');
              },
          });

  }
    function func(st){
        $("#conferee").empty();
        $.ajax({
            url:"meeting/groupBycanhui" ,
            data: {canHuitype:st},
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

