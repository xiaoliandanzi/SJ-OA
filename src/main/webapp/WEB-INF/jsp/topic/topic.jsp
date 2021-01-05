<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,select2,icheck"></t:base>
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
                    <t:formvalid action="topic/save">
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
                                <select class="form-control m-b select2" name="proposeLeader" multiple="multiple">
                                    <c:forEach items="${proposeLeaderList}" var="propose">
                                        <option value="${propose.id }">${propose.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">汇报人*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2" name="reportId" multiple="multiple">
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
                                       class="form-control" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">内容摘要*：</label>
                            <div class="col-sm-8">
                                <textarea name="topicContent" class="form-control"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-8">
                                <textarea name="topicRemark" class="form-control"></textarea>
                            </div>
                        </div>
                        <%--议题材料--%>
                        <%--议题材料--%>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">工委会：</label>
                            <div class="col-sm-8">
                                <c:choose>
                                    <c:when test="${empty oaTopic.isWorkingCommittee}">
                                        <t:dictSelect name="isWorkingCommittee" type="radio" typeGroupCode="byesorno"
                                                      defaultVal="1"></t:dictSelect>
                                    </c:when>
                                    <c:otherwise>
                                        <t:dictSelect name="isWorkingCommittee" type="radio" typeGroupCode="byesorno"
                                                      defaultVal="0"></t:dictSelect>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">主任会：</label>
                            <div class="col-sm-8">
                                <c:choose>
                                    <c:when test="${empty oaTopic.isDirector}">
                                        <t:dictSelect name="isDirector" type="radio" typeGroupCode="byesorno"
                                                      defaultVal="1"></t:dictSelect>
                                    </c:when>
                                    <c:otherwise>
                                        <t:dictSelect name="isDirector" type="radio" typeGroupCode="byesorno"
                                                      defaultVal="0"></t:dictSelect>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">科室负责人*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2" name="deptLeaderId" multiple="multiple">
                                    <c:forEach items="${deptLeader}" var="deptL">
                                        <option value="${deptL.id }">${deptL.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">主管领导*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2" name="leaderId" multiple="multiple">
                                    <c:forEach items="${lv2Leader}" var="lv2l">
                                        <option value="${lv2l.id }">${lv2l.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">综合办*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2" name="generalOffice" multiple="multiple">
                                    <c:forEach items="${generalOffice}" var="genera">
                                        <option value="${genera.id }">${genera.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">财务科*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2" name="financeOffice" multiple="multiple">
                                    <c:forEach items="${financeOffice}" var="finance">
                                        <option value="${finance.id }">${finance.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">纪委*：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b select2" name="disciplineOffice" multiple="multiple">
                                    <c:forEach items="${disciplineOffice}" var="discipline">
                                        <option value="${discipline.id }">${discipline.realName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                    </t:formvalid>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>

