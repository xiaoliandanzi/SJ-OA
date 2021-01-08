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
        $(function () {
            $("#reportId").val("${oaTopic.reportId}".split(",")).trigger("change");
        });
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
                                <select class="form-control m-b select2"
                                        id="proposeLeader" name="proposeLeader" multiple="multiple">
                                    <c:forEach items="${proposeLeaderList}" var="propose">
                                        <option value="${propose.id }">${propose.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">汇报人*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2"
                                        id="reportId" name="reportId" multiple="multiple">
                                    <c:forEach items="${reportList}" var="report">
                                        <option value="${report.id }">${report.realName}</option>
                                    </c:forEach>
                                </select>
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
                        <%--议题材料--%>
                        <c:if test="${empty oaTopic.id}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label m-b">附件:</label>
                                <div class="col-sm-2">
                                    <div id="filePicker">上传附件</div>
                                </div>
                                <div class="col-sm-4">
                                    <div id="fileList" class="uploader-list"></div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${not empty oaTopic.id}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label m-b">附件:</label>
                                <div class="col-sm-2">
                                    <button type="button" class="btn btn-success" id="download">下载</button>
                                </div>
                            </div>
                        </c:if>
                        <%--议题材料--%>
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
                                <select class="form-control m-b select2"
                                        id="deptLeaderId" name="deptLeaderId" multiple="multiple">
                                    <c:forEach items="${deptLeader}" var="deptL">
                                        <option value="${deptL.id }">${deptL.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">主管领导*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2"
                                        id="leaderId" name="leaderId" multiple="multiple">
                                    <c:forEach items="${lv2Leader}" var="lv2l">
                                        <option value="${lv2l.id }">${lv2l.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">综合办*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2"
                                        id="generalOffice" name="generalOffice" multiple="multiple">
                                    <c:forEach items="${generalOffice}" var="genera">
                                        <option value="${genera.id }">${genera.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">财务科*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2"
                                        id="financeOffice" name="financeOffice" multiple="multiple">
                                    <c:forEach items="${financeOffice}" var="finance">
                                        <option value="${finance.id }">${finance.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">纪委*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2"
                                        id="disciplineOffice" name="disciplineOffice" multiple="multiple">
                                    <c:forEach items="${disciplineOffice}" var="discipline">
                                        <option value="${discipline.id }">${discipline.realName}</option>
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
                    </t:formvalid>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        $("#download").click(function () {
            if ('${oaTopic.fileId}' != null) {
                var x = new XMLHttpRequest();
                x.open("GET", "func/upload/download?id=" + '${oaTopic.fileId}', true);
                x.responseType = 'blob';
                x.onload = function (e) {
                    var url = window.URL.createObjectURL(x.response)
                    var a = document.createElement('a');
                    a.href = url
                    a.download = ''
                    a.click()
                }
                x.send();
            }
        });
    });

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

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader2.on('uploadSuccess', function (file, data) {
            var filePath = data.attributes.filePath;
            $("#fileList").html(file.name);
            $("#fileId").val(filePath);
        });

        // 文件上传失败，显示上传出错。
        uploader2.on('uploadError', function (file) {

        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader2.on('uploadComplete', function (file) {
            qhTipSuccess('上传完成....');
        });

    });
</script>
</html>

