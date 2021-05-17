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
    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <t:formvalid action="">
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">科室*：</label>
                            <div class="col-sm-4 m-b">
                                <div class="input-group">
                                    <input  type="text" class="form-control" value="${depname}" readonly="value">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">登记人*：</label>
                            <div class="col-sm-4 m-b">
                                <input   type="text" class="form-control"  value="${registrantName}"    required=""  readonly="value">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">会议开始时间*：</label>
                            <div class="col-sm-4 m-b">
                                <input  type="text" class="form-control"  value="${meetingTime}"    required=""  readonly="value">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">会议结束时间*：</label>
                            <div class="col-sm-4 m-b">
                                <input  type="text" class="form-control"  value="${meetingendTime}"    required=""  readonly="value">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">会议室：</label>
                            <div class="col-sm-4">
                                <input  type="text" class="form-control"  value="${meetingId}"    required=""  readonly="value">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">议题会议类型：</label>
                            <div class="col-sm-4 m-b">
                                <input   type="text" class="form-control" value="${meetingType}"    required=""  readonly="value">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">会议名称*：</label>
                            <div class="col-sm-4 m-b">
                                <input   type="text" class="form-control" value="${meetingName}"    required=""  readonly="value">
                            </div>
                        </div>
                        <div class="ibox">
                            <div class="ibox-content">
                                <div id="topictable" class="topictable"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">人员：</label>
                            <div class="col-sm-4">

                                    <input   type="text" class="form-control" value="${oaMeeting.conferee}"    required=""  readonly="value">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label m-b">备注：</label>
                            <div class="col-sm-4 m-b">
                                <input   type="text" class="form-control" value="${memo}"    required=""  readonly="value">
                            </div>
                        </div>
                    </t:formvalid>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 脚本部分 -->
<t:datagrid actionUrl="meeting/tablechakans"   tableContentId="topictable" searchGroupId="searchGroupId" fit="true"
            multiSelect="true"     rownumbers="true"    caption="议题列表" name="toptable"  pageSize="20" sortName="creatTime" sortOrder="desc" >
    <t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
    <t:dgCol name="creatTime" label="申报日期" width="250" query="false"></t:dgCol>
    <t:dgCol name="topicName" label="议题名称" width="160" query="false"></t:dgCol>
    <t:dgCol name="proposeLeaderName" label="提议领导" query="false"></t:dgCol>
    <t:dgCol name="reportName" label="汇报人" query="false"></t:dgCol>
    <t:dgCol name="deptLeaderName" label="科室负责人" query="false"></t:dgCol>
    <t:dgCol name="leaderName" label="主管领导" query="false"></t:dgCol>
    <t:dgCol name="generalOfficeName" label="综合办" query="false"></t:dgCol>
    <t:dgCol name="financeName" label="财务审查" query="false"></t:dgCol>
    <t:dgCol name="disciplineName" label="纪检审查" query="false"></t:dgCol>
    <t:dgCol name="isSecretary" label="书记会" dictionary="byesorno" query="false"></t:dgCol>
    <t:dgCol name="isWorkingCommittee" label="主任会" dictionary="byesorno" query="flase"></t:dgCol>
    <t:dgCol name="isDirector" label="工委会" dictionary="byesorno" query="flase"></t:dgCol>
    <t:dgCol name="opt" label="操作" width="290"></t:dgCol>
</t:datagrid>
</body>
<script type="text/javascript">
    $(function() {

        laydate({
            elem: "#meetingTime",
            event: "focus",
            istime: true,
            format: 'YYYY-MM-DD'
        });

    });
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
    function func(st){
        $("#conferee").empty();
        $.ajax({
            url:"meeting/groupBycanhui" ,
            data: {canHuitype:st},
            success: function(data){
                var len=data.obj.length;
                for(var i=0;i<len;i++){
                    var option=document.createElement("option");
                    option.value = data.obj[i].id;
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

