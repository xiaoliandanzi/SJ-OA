<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,select2,icheck"></t:base>
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
                    <t:formvalid action="topic/auditSecond">
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
                        <%--议题材料--%>
                        <%--<c:if test="${not empty oaTopic.fileId}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label m-b">附件:</label>
                                <div class="col-sm-2">
                                    <button type="button" class="btn btn-success" id="download">下载</button>
                                </div>
                            </div>
                        </c:if>--%>
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
                            <label class="col-sm-3 control-label">财务科是否参与审核：</label>
                            <div class="col-sm-8">
                                <select name="isFO" class="form-control">
                                    <option value="2">否</option>
                                    <option value="1">是</option>
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
                            <label class="col-sm-3 control-label">纪委是否参与审核：</label>
                            <div class="col-sm-8">
                                <select name="isDO" class="form-control">
                                    <option value="2">否</option>
                                    <option value="1">是</option>
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
                                <select class="form-control m-b select2"
                                        id="generalOffice" name="generalOffice">
                                    <c:forEach items="${generalOffice}" var="genera">
                                        <option value="${genera.id }">${genera.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <hr/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">综合办审核意见：</label>
                            <div class="col-sm-8">
                                <textarea name="opinion" class="form-control"></textarea>
                            </div>
                        </div>
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
                                    </c:forEach>
                                </div>
                            </div>
                        </c:if>
                        <%-- </c:if>--%>
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

    function downThis(date) {
        var fileId = $(date).attr('id');
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
</script>

</html>

