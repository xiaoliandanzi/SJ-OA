<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
							<div class="form-group">
								<label class="col-sm-3 control-label">申请编号：</label>
								<div class="col-sm-5">
									<input id="projectNo" name="projectNo" minlength="2" type="text" class="form-control" required="" value="${base.projectNo }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">名称：</label>
								<div class="col-sm-5">
									<input id="name" name="name" minlength="2" type="text" class="form-control" required="" value="${base.name }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">合同名称：</label>
								<div class="col-sm-5">
									<input id="contractName" name="contractName" minlength="1" type="text" class="form-control" required="" value="${biz.contractName }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">金钱：</label>
								<div class="col-sm-5">
									<input id="money" name="money" type="number" class="form-control" required="" value="${biz.money }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">紧急程度：</label>
								<div class="col-sm-5">
									<c:choose>
                                		<c:when test="${empty base.level}">
                                			<t:dictSelect name="level" type="radio" typeGroupCode="workflowlevel" defaultVal="0"></t:dictSelect>
                                		</c:when>
                                		<c:otherwise>
                                			<t:dictSelect name="level" type="radio" typeGroupCode="workflowlevel" defaultVal="${base.level}"></t:dictSelect>
                                		</c:otherwise>
                                	</c:choose>
								</div>
							</div>