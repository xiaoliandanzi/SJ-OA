<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2021/1/4
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>我的审批</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="appForm" action="${action }" method="post">
                        <input type="hidden" name="id" id="id" value="${base.id }">
                        <input type="hidden" name="result" id="result">
                        <input type="hidden" name="type" id="type">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">当前任务：</label>
                            <div class="col-sm-5">
                                <select name="taskId" id="taskId" class="form-control">
                                    <c:forEach items="${lstTasks }" var="t">
                                        <option value="${t.id }">${t.name }</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">审批意见：</label>
                            <div class="col-sm-5">
                                <textarea id="comment" name="comment" class="form-control" required="required"></textarea>
                            </div>
                        </div>
                        <div class="form-group" id="btnLabel">
                            <label class="col-sm-4 control-label"></label>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

<script type="text/javascript">

    //表单验证
    $(function() {
        $("#appForm").validate({
            submitHandler : function(form) {
                $(form).ajaxSubmit({
                    success : function(o) {
                        if (o.success) {
                            qhTipSuccess('操作成功');
                            location.href='common/goSuccess';
                        } else {
                            qhTipWarning(o.msg);
                        }
                    },
                    error : function(data) {
                        qhTipError('系统错误，请联系系统管理员');
                    }
                });
            }
        });

        //根据任务节点，获取连线，生成页面按钮
        getTransByTaskId();
    });


    function doChange() {
        //根据任务节点，获取连线，生成页面按钮
        getTransByTaskId();
    }

    function getTransByTaskId(){
        var taskId = $("#taskId").val();
        // var sign = false;
        // debugger
        // $.ajaxSettings.async = false;
        // $.get("flow/biz/getSpe/ismanager",function (zx) {
        //     console.log("zxxxxxxxxxxxxx"+zx)
        //     var sign = zx;
        // })
        $.post("flow/biz/task/getTransByTaskIdIsManager", {taskId:taskId}, function(o) {
            if(o.success) {
                var count = o.attributes.count;
                var sign = o.attributes.sign;
                if(count == 1){
                    //默认通过

                    $("#btnLabel").empty();
                    $("#btnLabel").append("<label class='col-sm-4 control-label'></label>");
                    $("#btnLabel").append("<button class='btn btn-primary m-t' type='button' onclick='doBtnApproveAction();'>审批通过</button>");
                }else if(count == 2) {
                    //存在审批通过和驳回的情况

                    $("#btnLabel").empty();
                    $("#btnLabel").append("<label class='col-sm-4 control-label'></label>");
                    $("#btnLabel").append("<button class='col-sm-2 btn btn-primary m-t' type='button' onclick='doBtnApproveAction();'>" + "报主要领导</button>");
                    $("#btnLabel").append("<button class='col-sm-1 btn btn-danger m-t' style='margin-left:20px;' type='button' onclick='doBtnBackAction();'>驳回</button>");
                    if (sign == true){
                        $("#btnLabel").append("<button class='col-sm-2 btn btn-primary m-t' style='margin-left:20px;' type='button' onclick='doBtnOverApproveAction();'>通过&结束审批</button>");
                    }

                }
            }
        });
    }

    //审批通过
    function doBtnApproveAction() {
        $("#result").val('Y');
        $("#appForm").submit();
    }

    //驳回
    function doBtnBackAction() {
        $("#result").val('N');
        $("#appForm").submit();
    }

    //结束流程
    function doBtnOverApproveAction() {
        $("#result").val('O');
        $("#appForm").submit();
    }

</script>

