
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,treeview"></t:base>
</head>
<body class="gray-bg">
<!-- 页面部分 -->
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <%--beforeSubmit="setEditValue();"--%>
                    <t:formvalid >
                        <input type="hidden" name="id" id="id" value="${task.id }">
                        <input type="hidden" name="appointUserId" id="appointUserId" value="${appointUserId }">
                        <input type="hidden" name="appointUserName" id="appointUserName" value="${appointUserName }">
                        <input type="hidden" name="attachment" id="attachment" value="${task.attachment }">
                        <input type="hidden" name="returnAttachment" id="returnAttachment" value="${task.returnAttachment }">
                        <div class="form-group">
                            <div class="col-sm-6">
                                <dl class="dl-horizontal">
                                    <dd id="statusSpan">
                                        <c:choose>
                                            <c:when test="${task.status == '0' }"><span class="label label-primary">新建</span></c:when>
                                            <c:when test="${task.status == '1' }"><span class="label label-info">进行中</span></c:when>
                                            <c:when test="${task.status == '2' }"><span class="label label-success">完成</span></c:when>
                                            <c:when test="${task.status == '3' }"><span class="label label-warning">未完成</span></c:when>
                                            <c:when test="${task.status == '4' }"><span class="label label-danger">放弃</span></c:when>
                                            <c:when test="${task.status == '6' }"><span class="label label-danger">待审核</span></c:when>
                                            <c:otherwise>
                                                其他
                                            </c:otherwise>
                                        </c:choose>
                                    </dd>
                                </dl>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">督办编号：</label>
                            <div class="col-sm-8">
                                <input id="number" readonly name="number" type="text" class="form-control" required="" value="${task.number }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">任务标题：</label>
                            <div class="col-sm-8">
                                <input id="title" readonly name="title" type="text" class="form-control" required="" value="${task.title }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label m-b">交办时间：</label>
                            <div class="col-sm-4 m-b">
                                <input readonly class="laydate-icon form-control layer-date" id="startTime" name="startTime"  value='<fmt:formatDate value="${task.startTime }" type="both" pattern="yyyy-MM-dd HH:mm"/>'>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label m-b">本次办结时限：</label>
                            <div class="col-sm-4 m-b">
                                <input readonly class="laydate-icon form-control layer-date" id="endTime" name="endTime"  value='<fmt:formatDate value="${task.endTime }" type="both" pattern="yyyy-MM-dd HH:mm"/>'>
                            </div>
                        </div>
                        <c:if test="${task.status == '2' }">
                        <div class="form-group">
                            <label class="col-sm-3 control-label m-b">办结时间：</label>
                            <div class="col-sm-4 m-b">
                                <input readonly class="laydate-icon form-control layer-date" id="finshTime" name="finshTime"  value='<fmt:formatDate value="${task.finshTime }" type="both" pattern="yyyy-MM-dd HH:mm"/>'>
                            </div>
                        </div>
                        </c:if>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">督办内容：</label>
                            <div class="col-sm-8">
                                <textarea rows=4 readonly id="content" name="content" minlength="1" type="text" class="form-control" required="">${task.content }</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-8">
                                <textarea rows=1 readonly id="commit" name="commit" type="text" class="form-control">${task.commit }</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label m-b">督办附件：</label>
                            <div class="col-sm-8">
<%--                                <button class="btn btn-primary" type="button" onclick="doBtnDownloadFile();">下载附件</button>--%>
                                <div id="fileList" class="uploader-list"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">回执内容：</label>
                            <div class="col-sm-8">
                                <textarea rows=4 id="returnContent" name="returnContent" minlength="1" type="text" class="form-control" required="">${task.returnContent }</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">回执备注：</label>
                            <div class="col-sm-8">
                                <textarea rows=1 id="returnCommit" name="returnCommit" type="text" class="form-control">${task.returnCommit }</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label m-b">回执附件：</label>
                            <div class="col-sm-2">
                                <div id="filePicker">上传附件</div>
                            </div>
                            <div class="col-sm-4">
                                <div id="fileList2" class="uploader-list"></div>
                            </div>
                        </div>
                        <c:if test="${task.status == '6' }">
                            <div class="form-group">
                                <label class="col-sm-3 control-label m-b">审核意见：</label>
                                <div class="col-sm-8">
                                    <textarea rows=1 id="approvalCommit" name="approvalCommit" type="text" class="form-control">${task.approvalCommit }</textarea>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${task.status != '6' }">
                            <div class="form-group">
                                <label class="col-sm-3 control-label m-b">审核意见：</label>
                                <div class="col-sm-8">
                                    <textarea readonly rows=1 id="approvalCommit" name="approvalCommit" type="text" class="form-control">${task.approvalCommit }</textarea>
                                </div>
                            </div>
                        </c:if>
                    </t:formvalid>
                        <div class="row">
                            <div class="col-sm-12">
                            <p style="margin-left: 400px;">
                                <c:if test="${task.status == '0' }">
                                    <button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="changeStartAction();">
                                        <i class="fa fa-file-o"></i>&nbsp;开始执行
                                    </button>
                                </c:if>
                                <c:if test="${task.status != '2' }">
                                <button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="addFinishAction();">
                                    <i class="fa fa-tasks"></i>&nbsp;任务完成
                                </button>
                                </c:if>
                                <c:if test="${task.status == '6' }">
                                    <button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="addApprovalAction();">
                                        <i class="fa fa-tasks"></i>&nbsp;审核通过
                                    </button>
                                    <button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="addRejectAction();">
                                        <i class="fa fa-tasks"></i>&nbsp;审核驳回
                                    </button>
                                </c:if>
                            </p>
                            </div>
                        </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript">
    $(function() {
        var attachment = '${task.attachment }';
        attachment = JSON.parse(attachment);
        for (var i in attachment){
            var item = attachment[i];

            $("#fileList").append("<div class='file-name' id='file-name"+(i+1)+"'>" +
                "<a href=\"func/upload/download?id=" + item +"\">附件"+(parseInt(i)+1)+"</a>&nbsp;&nbsp;&nbsp;" +
                "</div>")
        }

        initData();
    });

    //初始化页面数据
    function initData() {
        var id = $("#id").val();

        //请求后台数据，获取目标的树形结构
        $.post("oa/work/task/getTreeData", {id : id}, function(d) {
            if (d.success) {
                var treeData = d.attributes.data;
                var df = eval(treeData);
                $("#select-tasks").treeview({
                    showCheckbox : false,
                    data : df,
                    //节点被选择
                    onNodeSelected : function(event, node) {
                        doBtnSelectionAction(node.id);
                    }
                });
                //展开全部
                $("#select-tasks").treeview('expandAll', { silent: true });

            }
        });

        changeProgress();

        getRecord(id);

        getExcute(id);
    }

    function doBtnSelectionAction(id) {
        $.post("oa/work/task/getTask", {id:id}, function(d){
            if(d.success){
                var task = d.attributes.task;
                $("#id").val(task.id);

                //目标状态
                if('0' == task.status) {
                    $("#statusSpan").html("<span class='label label-primary'>新建</span>");
                }else if('1' == task.status){
                    $("#statusSpan").html("<span class='label label-info'>进行中</span>");
                }else if('2' == task.status){
                    $("#statusSpan").html("<span class='label label-success'>完成</span>");
                }else if('3' == task.status){
                    $("#statusSpan").html("<span class='label label-warning'>取消</span>");
                }else if('4' == task.status){
                    $("#statusSpan").html("<span class='label label-danger'>放弃</span>");
                }else if('5' == task.status){
                    $("#statusSpan").html("<span class='label label-danger'>超期</span>");
                }else if('6' == task.status){
                    $("#statusSpan").html("<span class='label label-info'>待审核</span>");
                }

                $("#content").html(task.content);

                $("#appointUserName").html(task.appointUserName);
                $("#userName").html(task.userName);
                $("#monitorUserName").html(task.monitorUserName);
                $("#startTime").html(new Date(task.startTime).QHformat('yyyy年MM月dd hh:mm') + "&nbsp;到" + new Date(task.endTime).QHformat('yyyy年MM月dd hh:mm'));
                if(null == task.actEndTime || '' == task.actEndTime ){
                    if(null == task.actStartTime || '' == task.actStartTime) {
                        $("#actStartTime").html("");
                    }else {
                        $("#actStartTime").html(new Date(task.actStartTime).QHformat('yyyy年MM月dd hh:mm'));
                    }
                }else {
                    $("#actEndTime").html(new Date(task.actStartTime).QHformat('yyyy年MM月dd hh:mm') + "&nbsp;到" + new Date(task.actEndTime).QHformat('yyyy年MM月dd hh:mm'));
                }





                if(null == task.createDate || '' == task.createDate){
                }else{
                    $("#createDate").html(new Date(task.createDate).QHformat('yyyy年MM月dd hh:mm'));
                }

                $("#progress").val(task.progress);


                changeProgress();

                getRecord(id);

                getExcute(id);
            }
        });
    }

    function addApprovalAction() {
        var id = $("#id").val();
        var approvalCommit = $("#approvalCommit").val();

        qhConfirm("确定该任务已审核通过?", function(index) {
            //关闭询问
            parent.layer.close(index);

            //是
            $.post("oa/work/task/doApproval",
                {id : id, status:'2', approvalCommit:approvalCommit},
                function(d){
                    if(d.success) {
                        qhTipSuccess(d.msg);

                        $("#statusSpan").html("<span class='label label-info'>完成</span>");
                    }
                });

        }, function() {
            //否
        });
    }

    function addRejectAction() {
        var id = $("#id").val();
        var approvalCommit = $("#approvalCommit").val();

        qhConfirm("确定驳回该任务?", function(index) {
            //关闭询问
            parent.layer.close(index);

            //是
            $.post("oa/work/task/doApproval",
                {id : id, status:'1', approvalCommit:approvalCommit},
                function(d){
                    if(d.success) {
                        qhTipSuccess(d.msg);

                        $("#statusSpan").html("<span class='label label-info'>进行中</span>");
                    }
                });

        }, function() {
            //否
        });
    }

    function addFinishAction() {
        var id = $("#id").val();
        var returnContent = $("#returnContent").val();
        var returnCommit = $("#returnCommit").val();
        var returnAttachment = $("#returnAttachment").val();

        qhConfirm("确定该任务已完成?", function(index) {
            //关闭询问
            parent.layer.close(index);

            //是
            $.post("oa/work/task/doFinish",
                {id : id, status:'6', returnContent:returnContent,returnCommit:returnCommit,
                    returnAttachment:returnAttachment},
                function(d){
                if(d.success) {
                    qhTipSuccess(d.msg);

                    $("#statusSpan").html("<span class='label label-info'>待审核</span>");
                }
            });

        }, function() {
            //否
        });
    }

    //开始执行任务
    function changeStartAction() {
        var id = $("#id").val();

        qhConfirm("确定要开始该任务吗?", function(index) {
            //关闭询问
            parent.layer.close(index);

            //是
            $.post("oa/work/task/doChange", {id : id, status:'1'}, function(d){
                if(d.success) {
                    qhTipSuccess(d.msg);

                    $("#statusSpan").html("<span class='label label-info'>进行中</span>");
                }
            });

        }, function() {
            //否
        });
    }



    function doBtnDownloadFile() {
        var att = document.getElementById("attachment").value;
        if(!att) {
            qhAlert('该文件附件还未上传附件！');
            return;
        }

        location.href = "func/upload/download?id=" + att;
    };

    $(function() {
        //初始化Web Uploader
        var uploader2 = WebUploader.create({

            // 选完文件后，是否自动上传。
            auto : true,

            // swf文件路径
            swf : 'static/webuploader/Uploader.swf',

            // 文件接收服务端。
            server : 'func/upload/uploadFiles?db=1',

            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick : {
                id : '#filePicker'
            },

            fileSizeLimit: 5 * 1024 * 1024

        });


        // 文件上传过程中创建进度条实时显示。
        uploader2.on('uploadProgress', function(file, percentage) {
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader2.on('uploadSuccess', function(file, data) {
            var filePath = data.attributes.filePath;
            var count = $(".file-name").length;
            $("#fileList2").append("<div class='file-name' id='file-name"+(count+1)+"'>"+file.name+"<a href='javascript:removeFile("+(count+1)+")'>删除</a></div>")
            var attachment = $("#returnAttachment").val();
            if(attachment != ''){
                attachment = JSON.parse(attachment)
            }else{
                attachment = [];
            }
            attachment.push(filePath)

            $("#returnAttachment").val(JSON.stringify(attachment));
        });

        // 文件上传失败，显示上传出错。
        uploader2.on('uploadError', function(file) {

        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader2.on('uploadComplete', function(file) {
            qhTipSuccess('上传完成....');
        });

    });


</script>

</html>

