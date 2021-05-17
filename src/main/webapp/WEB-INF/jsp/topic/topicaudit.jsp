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
        <%--$(function () {--%>
        <%--    $("#managerOffice").val("${oaTopic.managerOffice}".split(",")).trigger("change");--%>
        <%--});--%>
    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <t:formvalid action="topic/audit">
                        <input type="hidden" name="id" id="id" value="${oaTopic.id}">
                        <input type="hidden" name="fileId" id="fileId" value="${oaTopic.fileId}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">科室*：</label>
                            <div class="col-sm-8">
                                <div class="input-group" disabled>
                                    <t:choose url="common/selectDepart" hiddenName="deptId"
                                              hiddenValue="${oaTopic.deptId }"
                                              textValue="${deptName }" textName="departLabel" hiddenId="departId"
                                              textId="departLabel"></t:choose>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">申请人*：</label>
                            <div class="col-sm-8">
                                <input id="applyName" name="applyName" minlength="2"  type="text"
                                       class="form-control" required value="${oaTopic.createUserName}" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">提议领导*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2" disabled
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
                                    <%--<select class="form-control m-b select2" disabled
                                            id="reportId" name="reportId">
                                        <c:forEach items="${reportList}" var="report">
                                            <option value="${report.id }">${report.realName}</option>
                                        </c:forEach>
                                    </select>--%>
                                <input id="reportName" name="reportName" minlength="2" maxlength="20" type="text"
                                       class="form-control" value="${oaTopic.reportName}" disabled>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">议题标题*：</label>
                            <div class="col-sm-8">
                                <input id="topicName" name="topicName" minlength="2"  type="text" disabled
                                       class="form-control" required value="${oaTopic.topicName}">
                            </div>
                        </div>
                        <div class=" form-group">
                            <label class="col-sm-3 control-label">内容摘要*：</label>
                            <div class="col-sm-8">
                                <textarea name="topicContent" class="form-control" disabled
                                >${oaTopic.topicContent}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-8">
                                <textarea name="topicRemark" class="form-control" disabled
                                >${oaTopic.topicRemark}</textarea>
                            </div>
                        </div>
                        <%--议题材料--%>
                        <%-- <c:if test="${not empty oaTopic.fileId}">
                             <div class="form-group">
                                 <label class="col-sm-3 control-label m-b">附件:</label>
                                 <div class="col-sm-2">
                                     <button type="button" class="btn btn-success" id="download">下载</button>
                                 </div>
                             </div>
                         </c:if>--%>
                        <%--议题材料--%>
                        <c:if test="${not empty isGeneralOffice}">
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
                                            <t:dictSelect name="isWorkingCommittee" type="radio"
                                                          typeGroupCode="byesorno"
                                                          defaultVal="false"></t:dictSelect>
                                        </c:when>
                                        <c:otherwise>
                                            <t:dictSelect name="isWorkingCommittee" type="radio"
                                                          typeGroupCode="byesorno"
                                                          defaultVal="${oaTopic.isWorkingCommittee}"></t:dictSelect>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </div>
                        </c:if>
                        <c:if test="${empty isGeneralOffice}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">主任会：</label>
                                <div class="col-sm-8">
                                        <%--<c:if test="${oaTopic.isDirector == 'false'}">不上会</c:if>
                                        <c:if test="${oaTopic.isDirector == 'true'}">上会</c:if>--%>
                                    <input id="" name="topicName" minlength="2" maxlength="20" type="text" disabled
                                           class="form-control" required
                                           value="<c:if test="${oaTopic.isDirector == 'false'}">不上会</c:if><c:if test="${oaTopic.isDirector == 'true'}">上会</c:if>">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">工委会：</label>
                                <div class="col-sm-8">
                                    <input i name="topicName" minlength="2" maxlength="20" type="text" disabled
                                           class="form-control" required
                                           value="<c:if test="${oaTopic.isWorkingCommittee == 'false'}">不上会</c:if><c:if test="${oaTopic.isWorkingCommittee == 'true'}">上会</c:if>">
                                </div>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">科室负责人*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2" disabled
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
                                <select class="form-control m-b select2" disabled
                                        id="leaderId" name="leaderId">
                                    <c:forEach items="${lv2Leader}" var="lv2l">
                                        <option value="${lv2l.id }">${lv2l.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">财务审查：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2" disabled
                                        id="financeOffice" name="financeOffice">
                                    <c:forEach items="${financeOffice}" var="finance">
                                        <option value="${finance.id }">${finance.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">纪检审查：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2" disabled
                                        id="disciplineOffice" name="disciplineOffice">
                                    <c:forEach items="${disciplineOffice}" var="discipline">
                                        <option value="${discipline.id }">${discipline.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
<%--                        <div class="form-group">--%>
<%--                            <label class="col-sm-3 control-label">纪委主管领导：</label>--%>
<%--                            <div class="col-sm-8">--%>
<%--                                <select class="form-control m-b select2" disabled--%>
<%--                                        id="managerOffice" name="managerOffice">--%>
<%--                                    <c:forEach items="${managerOffice}" var="managerOfficelist">--%>
<%--                                        <option value="${managerOfficelist.id }">${managerOfficelist.realName}</option>--%>
<%--                                    </c:forEach>--%>
<%--                                </select>--%>
<%--                            </div>--%>
<%--                        </div>--%>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">综合办*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2" disabled
                                        id="generalOffice" name="generalOffice">
                                    <c:forEach items="${generalOffice}" var="genera">
                                        <option value="${genera.id }">${genera.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <%--<c:if test="${params  == 1}">--%>
                        <c:if test="${oaTopic.isPassOne != 0}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">科室负责人意见：</label>
                                <div class="col-sm-8">
                                    <label class="control-label">审核结果：<c:choose>
                                        <c:when test="${oaTopic.isPassOne == 1}">通过</c:when>
                                        <c:when test="${oaTopic.isPassOne == 2}">不通过</c:when>
                                    </c:choose></label>
                                    <textarea name="opinionDeptLeader" class="form-control"
                                              disabled>${oaTopic.opinionDeptLeader}</textarea>
                                </div>
                                    <%--<label class="col-sm-3 control-label">审核结果：</label>
                                    <div class="col-sm-8">
                                        <p class="form-control-static" disabled>
                                            <c:choose>
                                                <c:when test="${oaTopic.isPassOne == 1}">通过</c:when>
                                                <c:when test="${oaTopic.isPassOne == 2}">不通过</c:when>
                                            </c:choose></p>
                                    </div>--%>
                            </div>
                        </c:if>
                        <c:if test="${oaTopic.isPassTwo != 0}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">主管领导意见：</label>
                                <div class="col-sm-8">
                                    <label class="control-label">审核结果：<c:choose>
                                        <c:when test="${oaTopic.isPassTwo == 1}">通过</c:when>
                                        <c:when test="${oaTopic.isPassTwo == 2}">不通过</c:when>
                                    </c:choose></label>
                                    <textarea name="opinionLeader" class="form-control"
                                              disabled>${oaTopic.opinionLeader}</textarea>
                                </div>
                                    <%--<label class="col-sm-2 control-label">审核结果：</label>
                                    <div class="col-sm-3">
                                        <p class="form-control-static" disabled>
                                            <c:choose>
                                                <c:when test="${oaTopic.isPassTwo == 1}">通过</c:when>
                                                <c:when test="${oaTopic.isPassTwo == 2}">不通过</c:when>
                                            </c:choose></p>
                                    </div>--%>
                            </div>
                        </c:if>
                        <c:if test="${oaTopic.isPassThree != 0}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">综合办意见：</label>
                                <div class="col-sm-8">
                                    <label class="control-label">审核结果：<c:choose>
                                        <c:when test="${oaTopic.isPassThree == 1}">通过</c:when>
                                        <c:when test="${oaTopic.isPassThree == 2}">不通过</c:when>
                                    </c:choose></label>
                                    <textarea name="opinionGeneralOffice" class="form-control"
                                              disabled>${oaTopic.opinionGeneralOffice}</textarea>
                                </div>
                                    <%--<label class="col-sm-2 control-label">审核结果：</label>
                                    <div class="col-sm-3">
                                        <p class="form-control-static" disabled>
                                            <c:choose>
                                                <c:when test="${oaTopic.isPassThree == 1}">通过</c:when>
                                                <c:when test="${oaTopic.isPassThree == 2}">不通过</c:when>
                                            </c:choose></p>
                                    </div>--%>
                            </div>
                        </c:if>
                        <c:if test="${oaTopic.isPassFour != 0}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">财务审查意见：</label>
                                <div class="col-sm-8">
                                    <label class="control-label">审核结果：<c:choose>
                                        <c:when test="${oaTopic.isPassFour == 1}">通过</c:when>
                                        <c:when test="${oaTopic.isPassFour == 2}">不通过</c:when>
                                    </c:choose> </label>
                                    <textarea name="opinionFinanceOffice" class="form-control"
                                              disabled>${oaTopic.opinionFinanceOffice}</textarea>
                                </div>
                                    <%--<label class="col-sm-2 control-label">审核结果：</label>
                                    <div class="col-sm-3">
                                        <p class="form-control-static">
                                            <c:choose>
                                                <c:when test="${oaTopic.isPassFour == 1}">通过</c:when>
                                                <c:when test="${oaTopic.isPassFour == 2}">不通过</c:when>
                                            </c:choose></p>
                                    </div>--%>
                            </div>
                        </c:if>
                        <%----%>
                        <%----%>
                        <c:if test="${oaTopic.isPassFive != 0}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">纪检审查意见：</label>
                                <div class="col-sm-8">
                                    <label class="control-label">审核结果：<c:choose>
                                        <c:when test="${oaTopic.isPassFive == 1}">通过</c:when>
                                        <c:when test="${oaTopic.isPassFive == 2}">不通过</c:when>
                                    </c:choose> </label>
                                    <textarea name="opinionDisciplineOffice" class="form-control"
                                              disabled>${oaTopic.opinionDisciplineOffice}</textarea>
                                </div>
                                    <%--<div class="col-sm-1">
                                        <p class="form-control-static">

                                    </div>--%>
                            </div>
                        </c:if>
<%--                        <c:if test="${oaTopic.isPassSix != 0}">--%>
<%--                            <div class="form-group">--%>
<%--                                <label class="col-sm-3 control-label">纪委主管领导意见：</label>--%>
<%--                                <div class="col-sm-8">--%>
<%--                                    <label class="control-label">审核结果：<c:choose>--%>
<%--                                        <c:when test="${oaTopic.isPassSix == 1}">通过</c:when>--%>
<%--                                        <c:when test="${oaTopic.isPassSix == 2}">不通过</c:when>--%>
<%--                                    </c:choose> </label>--%>
<%--                                    <textarea name="managerOfficeIdea" class="form-control"--%>
<%--                                              disabled>${oaTopic.managerOfficeIdea}</textarea>--%>
<%--                                </div>--%>
<%--                                    &lt;%&ndash;<div class="col-sm-1">--%>
<%--                                        <p class="form-control-static">--%>

<%--                                    </div>&ndash;%&gt;--%>
<%--                            </div>--%>
<%--                        </c:if>--%>
                        <c:if test="${lookOrAdu == null}">
                            <hr/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" readonly="true">是否通过审核：</label>
                                <div class="col-sm-8">
                                    <select name="isOk" class="form-control">
                                        <option value="1">是</option>
                                        <option value="2">否</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">审核意见：</label>
                                <div class="col-sm-8">
                                    <textarea name="opinion" class="form-control"></textarea>
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
</script>
</html>

