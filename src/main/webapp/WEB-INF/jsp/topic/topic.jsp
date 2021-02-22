<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,select2,icheck,webuploader"></t:base>
    <script type="text/javascript">
        $(function () {
            $("#proposeLeader").val("${oaTopic.proposeLeader}".split(",")).trigger("change");
        });
        /*$(function () {
            $("#reportId").val("${oaTopic.reportId}".split(",")).trigger("change");
        });*/
        $(function () {
            $("#deptLeaderId").val("${oaTopic.deptLeaderId}".split(",")).trigger("change");
        });
        $(function () {
            $("#leaderId").val("${oaTopic.leaderId}".split(",")).trigger("change");
        });
        $(function () {
            $("#generalOffice").val("${oaTopic.generalOffice}".split(",")).trigger("change");
        });
        $(function () {
            $("#financeOffice").val("${oaTopic.financeOffice}".split(",")).trigger("change");
        });
        $(function () {
            $("#disciplineOffice").val("${oaTopic.disciplineOffice}".split(",")).trigger("change");
        });
    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <t:formvalid action="topic/saveOrUpdate">
                        <input type="hidden" name="id" id="id" value="${oaTopic.id}">
                        <input type="hidden" name="fileId" id="fileId" value="${oaTopic.fileId}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">科室*：</label>
                            <div class="col-sm-8">
                                <div class="input-group">
                                    <t:choose url="common/selectDepart" hiddenName="deptId"
                                              hiddenValue="${oaTopic.deptId }"
                                              textValue="${deptName }" textName="departLabel" hiddenId="departId"
                                              textId="departLabel"></t:choose>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">提议领导*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2" required
                                        id="proposeLeader" name="proposeLeader">
                                    <c:forEach items="${proposeLeaderList}" var="propose">
                                        <option value="${propose.id }">${propose.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">汇报人*：</label>
                            <div class="col-sm-8">
                                    <%--<select class="form-control m-b select2"
                                            id="reportId" name="reportId">
                                        <c:forEach items="${reportList}" var="report">
                                            <option value="${report.id }">${report.realName}</option>
                                        </c:forEach>
                                    </select>--%>
                                <input id="reportName" name="reportName" minlength="2" maxlength="20" type="text"
                                       class="form-control" required value="${oaTopic.reportName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">议题标题*：</label>
                            <div class="col-sm-8">
                                <input id="topicName" name="topicName" minlength="2" maxlength="20" type="text"
                                       class="form-control" required value="${oaTopic.topicName}">
                            </div>
                        </div>
                        <div class=" form-group">
                            <label class="col-sm-3 control-label">内容摘要*：</label>
                            <div class="col-sm-8">
                                <textarea name="topicContent" class="form-control"
                                >${oaTopic.topicContent}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-8">
                                <textarea name="topicRemark" class="form-control"
                                >${oaTopic.topicRemark}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">主任会：</label>
                            <div class="col-sm-8">
                                <c:choose>
                                    <c:when test="${empty oaTopic.isDirector}">
                                        <t:dictSelect name="isDirector" type="radio" typeGroupCode="byesorno"
                                                      defaultVal="false"></t:dictSelect>
                                    </c:when>
                                    <c:otherwise>
                                        <t:dictSelect name="isDirector" type="radio" typeGroupCode="byesorno"
                                                      defaultVal="${oaTopic.isDirector}"></t:dictSelect>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">工委会：</label>
                            <div class="col-sm-8">
                                <c:choose>
                                    <c:when test="${empty oaTopic.isWorkingCommittee}">
                                        <t:dictSelect name="isWorkingCommittee" type="radio" typeGroupCode="byesorno"
                                                      defaultVal="false"></t:dictSelect>
                                    </c:when>
                                    <c:otherwise>
                                        <t:dictSelect name="isWorkingCommittee" type="radio" typeGroupCode="byesorno"
                                                      defaultVal="${oaTopic.isWorkingCommittee}"></t:dictSelect>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">科室负责人*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2" required
                                        id="deptLeaderId" name="deptLeaderId">
                                    <c:forEach items="${deptLeader}" var="deptL">
                                        <option value="${deptL.id }">${deptL.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">主管领导*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2" required
                                        id="leaderId" name="leaderId">
                                    <c:forEach items="${lv2Leader}" var="lv2l">
                                        <option value="${lv2l.id }">${lv2l.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">财务科：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2"
                                        id="financeOffice" name="financeOffice">
                                    <c:forEach items="${financeOffice}" var="finance">
                                        <option value="${finance.id }">${finance.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">纪委：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2"
                                        id="disciplineOffice" name="disciplineOffice">
                                    <c:forEach items="${disciplineOffice}" var="discipline">
                                        <option value="${discipline.id }">${discipline.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">综合办*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2" required
                                        id="generalOffice" name="generalOffice">
                                    <c:forEach items="${generalOffice}" var="genera">
                                        <option value="${genera.id }">${genera.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <c:if test="${params  == 1}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">科室负责人意见：</label>
                                <div class="col-sm-8">
                                <textarea name="opinionDeptLeader" class="form-control"
                                >${oaTopic.opinionDeptLeader}</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">主管领导意见：</label>
                                <div class="col-sm-8">
                                <textarea name="opinionLeader" class="form-control"
                                >${oaTopic.opinionLeader}</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">综合办意见：</label>
                                <div class="col-sm-8">
                                <textarea name="opinionGeneralOffice" class="form-control"
                                >${oaTopic.opinionGeneralOffice}</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">财务科意见：</label>
                                <div class="col-sm-8">
                                <textarea name="opinionFinanceOffice" class="form-control"
                                >${oaTopic.opinionFinanceOffice}</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">纪委意见：</label>
                                <div class="col-sm-8">
                                <textarea name="opinionDisciplineOffice" class="form-control"
                                >${oaTopic.opinionDisciplineOffice}</textarea>
                                </div>
                            </div>
                        </c:if>
                        <%--议题材料--%>
                        <c:if test="${params != 1}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label m-b">附件:</label>
                                <div class="col-sm-2">
                                    <div id="filePicker">上传附件</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label m-b">附件列表:</label>
                                <div class="col-sm-9">
                                    <div id="fileList">
                                        <table>
                                            <thead>
                                            <tr>
                                                <th style="width: 200px;">文件名</th>
                                                <th style="width: 100px;">大小</th>
                                                <th style="width: 100px;">状态</th>
                                            </tr>
                                            </thead>

                                        </table>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${not empty uploadList}">
                            <hr/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label m-b">已有附件:</label>
                                <div class="col-sm-8">
                                    <c:forEach items="${uploadList}" var="fileDown">
                                        <div class="col-sm-8"
                                             style="font-size: 16px;color: black">${fileDown.name}</div>
                                        <button type="button" onclick="downThis(this)"
                                                class="btn btn-success col-sm-2" id="${fileDown.id}">
                                            下载
                                        </button>
                                        <c:if test="${params  != 1}">
                                            <button type="button" onclick="delFile(this)"
                                                    class="btn btn-success col-sm-2 " id="${fileDown.id}">
                                                删除
                                            </button>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:if>
                        <%--议题材料--%>
                    </t:formvalid>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var fileIds = '${oaTopic.fileId}';
    var oaId = '${oaTopic.id}';
    var fileIdForDel = '';
    var start = '<table><thead><tr><th style="width: 200px;">文件名</th><th style="width: 100px;">大小</th><th style="width: 100px;">状态</th></tr></thead>';
    var end = '</table>';


    function delFile(dom) {
        var fileId = $(dom).attr('id');
        fileIdForDel = fileId;
        console.log('删除')
        console.log(fileId)
        $.get("topicFile/remove?id=" + oaId + "&fileId=" + fileId, null, function (data) {
            if (data.success) {
                fileIds = data.obj;
                $(dom)[0].onClick = "";
                $(dom)[0].innerHTML = "已删除";
            } else {
                qhTipWarning(data.msg);
            }
        })
    }

    function downThis(date) {
        var fileId = $(date).attr('id');
        if (fileId == fileIdForDel) {
            qhTipWarning('该文件已删除!');
            return;
        }
        var filename = '';
        $.get("topicFile/name?id=" + fileId, null, function (data) {
            if (data.success) {
                filename = data.obj;
            } else {
                qhTipWarning(data.msg);
            }
        })
        setTimeout(function () {
            var x = new XMLHttpRequest();
            x.open("GET", "topicFile/down?id=" + fileId, true);
            x.responseType = 'blob';
            x.onload = function (e) {
                var url = window.URL.createObjectURL(x.response)
                var a = document.createElement('a');
                a.href = url
                a.download = filename
                a.click()
            }
            x.send();
        }, 1000);
    }

    $(function () {
        //初始化Web Uploader
        var uploader2 = WebUploader.create({
            // 选完文件后，是否自动上传。
            auto: true,
            // swf文件路径
            swf: 'static/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: 'func/upload/uploadFiles?db=1',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: {
                id: '#filePicker'
            },

            fileSizeLimit: 5 * 1024 * 1024

        });


        // 文件上传过程中创建进度条实时显示。
        uploader2.on('uploadProgress', function (file, percentage) {
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。 ,c8fe3347e868490a4053601e9e510fa2,37378ca49173719fdd354b8415310620
        uploader2.on('uploadSuccess', function (file, data) {
            if (data.success) {
                var fileId = data.attributes.filePath;
                var init = getTdList(file);
                $("#fileList").html(init);
                fileIds = fileIds + ',' + fileId;
                console.log(fileIds);
                $("#fileId").val(fileIds);
            } else {
                qhTipWarning(data.msg);
            }
        });

        // 文件上传失败，显示上传出错。
        uploader2.on('uploadError', function (file, data) {
            qhTipWarning(data.msg);
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader2.on('uploadComplete', function (file) {
            qhTipSuccess('上传完成....');
        });

    });

    function getTdList(file) {
        var fileSize = file.size;
        fileSize = (fileSize / 1024).toFixed(2);
        var fileName = file.name;
        start = start + '<tr><th style="width: 200px;">' + fileName + '</th><th style="width: 100px;">' + fileSize + '</th><th style="width: 100px;">成功</th></tr>';
        var init = start + end;
        return init;
    }
</script>
</html>

