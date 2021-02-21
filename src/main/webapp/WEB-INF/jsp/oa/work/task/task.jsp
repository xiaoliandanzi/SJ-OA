<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,summernote,laydate,prettyfile,webuploader"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<%--beforeSubmit="setEditValue();"--%>
						<t:formvalid action="oa/work/task/save" >
							<input type="hidden" name="id" id="id" value="${task.id }">
							<input type="hidden" name="appointUserId" id="appointUserId" value="${appointUserId }">
							<input type="hidden" name="appointUserName" id="appointUserName" value="${appointUserName }">
							<input type="hidden" name="attachment" id="attachment" value="${task.attachment }">
							<div class="form-group">
								<label class="col-sm-3 control-label">督办编号：*</label>
								<div class="col-sm-8">
									<input id="number" name="number" type="text" class="form-control" required="" value="${task.number }">
								</div>
							</div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">任务标题：*</label>
                                <div class="col-sm-8">
                                    <input id="title" name="title" type="text" class="form-control" required="" value="${task.title }">
                                </div>
                            </div>
							<div class="form-group">
								<label class="col-sm-3 control-label">联系人：*</label>
								<div class="col-sm-5">
									<div class="input-group">
										<input readonly id="contractName" name="contractName" minlength="2" type="text" class="form-control" required="" value="${appointUserName }">
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">联系电话：*</label>
								<div class="col-sm-5">
									<div class="input-group">
										<input id="contractPhone" name="contractPhone" minlength="2" type="text" class="form-control" required="" value="${task.contractPhone }">
									</div>
								</div>
							</div>
                            <%--<div class="form-group">--%>
								<%--<label class="col-sm-3 control-label">上级任务：</label>--%>
								<%--<div class="col-sm-8">--%>
									<%--<select class="form-control" name="parentTaskId" id="parentTaskId">--%>
										<%--<option value=""></option>--%>
										<%--<c:forEach items="${lstTasks }" var="t">--%>
											<%--<option value="${t.id}" <c:if test="${t.id == task.parentTaskId }">selected='selected'</c:if>>${t.title }</option>--%>
										<%--</c:forEach>--%>
									<%--</select>--%>
								<%--</div>--%>
							<%--</div>--%>
							<div class="form-group">
								<label class="col-sm-3 control-label">承办科室：</label>
								<div class="col-sm-8">
									<div class="input-group">
										<t:choose url="common/selectDepart" hiddenName="dept" hiddenValue="${deptId }" textValue="${dept }" textName="dept" hiddenId="deptId" textId="dept"></t:choose>
									</div>
								</div>
							</div>
                            <%--<div class="form-group">
                                <label class="col-sm-3 control-label">分配人：</label>
                                <div class="col-sm-8">
                                	<div class="input-group">
                                		<t:choose url="common/selectUsers" hiddenName="appointUserId" hiddenValue="${appointUserId }" textValue="${appointUserName }" textName="appointUserName" hiddenId="appointUserId" textId="appointUserName"></t:choose>
                                	</div>
                                </div>
                            </div>--%>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">责任人：*</label>
                                <div class="col-sm-8">
                                	<div class="input-group">
                                		<t:choose url="common/selectDeptManagers" hiddenName="userId" hiddenValue="${userId }" textValue="${userName }" textName="userName" hiddenId="userId" textId="userName"></t:choose>
                                	</div>
                                </div>
                            </div>
                            <%--<div class="form-group">--%>
                                <%--<label class="col-sm-3 control-label">监控人：</label>--%>
                                <%--<div class="col-sm-8">--%>
                                	<%--<div class="input-group">--%>
                                		<%--<t:choose url="common/selectUsers" hiddenName="monitorUserId" hiddenValue="${monitorUserId }" textValue="${monitorUserName }" textName="monitorUserName" hiddenId="monitorUserId" textId="monitorUserName"></t:choose>--%>
                                	<%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <div class="form-group">
								<label class="col-sm-3 control-label m-b">交办时间：*</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="startTime" name="startTime"  value='<fmt:formatDate value="${task.startTime }" type="both" pattern="yyyy-MM-dd HH:mm"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label m-b">本次办结时限：*</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="endTime" name="endTime"  value='<fmt:formatDate value="${task.endTime }" type="both" pattern="yyyy-MM-dd HH:mm"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label m-b">附件:</label>
								<div class="col-sm-4">
									<div id="filePicker">上传附件</div>
								</div>
								<div class="col-sm-4">
									<div id="fileList" class="uploader-list"></div>
								</div>
							</div>
                            <div class="form-group">
								<label class="col-sm-3 control-label">督办内容：*</label>
								<div class="col-sm-8">
									<textarea rows=8 id="content" name="content" minlength="1" type="text" class="form-control" required="">${task.content }</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">备注：</label>
								<div class="col-sm-8">
									<textarea rows=1 id="commit" name="commit" type="text" class="form-control">${task.commit }</textarea>
								</div>
							</div>
						</t:formvalid>
                    </div>
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
$(function() {
	$("#summernote").summernote({
		lang : "zh-CN",
		height : 300,
		// 重写图片上传  
		onImageUpload : function(files, editor, $editable) {
			uploadFile(files[0], editor, $editable);
		}
	});

	var content = '${task.content}';

	$('#summernote').code(content);
	
	
	laydate({
		elem : "#startTime",
		event : "focus",
		istime : true,
		format : 'YYYY-MM-DD hh:mm'
	});
	
	laydate({
		elem : "#endTime",
		event : "focus",
		istime : true,
		format : 'YYYY-MM-DD hh:mm'
	});
});

function uploadFile(file, editor, $editable) {
	var filename = false;
	try {
		filename = file['name'];
	} catch (e) {
		filename = false;
	}
	if (!filename) {
		$(".note-alarm").remove();
	}

	//以上防止在图片在编辑器内拖拽引发第二次上传导致的提示错误  
	data = new FormData();
	data.append("file", file);
	data.append("key", filename); //唯一性参数  

	$.ajax({
		data : data,
		type : "POST",
		url : "func/upload/uploadImages?db=1",
		cache : false,
		contentType : false,
		processData : false,
		success : function(data) {
			var o = $.parseJSON(data);
			if(o.success) {
				var filePath = o.attributes.filePath;
				editor.insertImage($editable, filePath);
			}else {
				qhTipError("上传失败!" + o.msg);
			}
		},
		error : function() {
			qhTipError("上传失败!");
			return;
		}
	});
}

function setEditValue() {

	var content = $('#summernote').code();

	$("#content").val(content);

	return true;
}

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
        $("#fileList").html(file.name);
        $("#attachment").val(filePath);
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
