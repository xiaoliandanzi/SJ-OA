<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,laydate,clock"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row"><%----%>
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="oa/work/meetRoomBooks/save">
							<input type="hidden" name="id" id="id" value="${meet.id }">
							<div class="form-group">
                                <label class="col-sm-2 control-label">会议室名称*：</label>
                                <div class="col-sm-8">
                                	<select name="meetRoomId" class="form-control" required="">
	                                	<c:forEach items="${lstRooms }" var="c">
												<option value="${c.id }" <c:if test="${meet.meetRoomId == c.id }">selected="selected"</c:if>>${c.name }</option>
										</c:forEach>
									</select>
                                </div>
                            </div>
							<div class="form-group">
								<label class="col-sm-2 control-label">科室：</label>
								<div class="col-sm-4">
									<input id="dept" name="dept"  type="text" class="form-control" required="" readonly value="${dept }">
								</div>
								<label class="col-sm-2 control-label">登记人：</label>
								<div class="col-sm-4">
									<input id="userName" name="userName"  type="text" class="form-control" required="" readonly value="${userName }">
								</div>
							</div>
                            <div class="form-group">
								<label class="col-sm-2 control-label m-b">会议日期：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="bookDate" name="bookDate" required="" value='<fmt:formatDate value="${meet.bookDate }" type="date" pattern="yyyy-MM-dd"/>'>
								</div>
							</div>
                            <div class="form-group">
								<label class="col-sm-2 control-label m-b">开始时间：</label>
								<div class="col-sm-4 m-b">
									<%--<div class="col-sm-4 m-b">--%>
			                            <input class="laydate-icon form-control layer-date" id="startDate" name="startDate" required="" value='<fmt:formatDate value="${meet.startDate }" type="time" pattern="yyyy-MM-dd HH:mm"/>'>
			                            <%--<span class="input-group-addon">
			                                   <span class="fa fa-clock-o"></span>
			                            </span>--%>
			                        <%--</div>--%>
								</div>
								<label class="col-sm-2 control-label m-b">结束时间：</label>
								<div class="col-sm-4 m-b">
									<%--<div class="input-group clockpicker" data-autoclose="true">--%>
										<input class="laydate-icon form-control layer-date" id="endDate" name="endDate" required="" value='<fmt:formatDate value="${meet.endDate }" type="time" pattern="yyyy-MM-dd HH:mm"/>'>
										<%--<span class="input-group-addon">
			                                   <span class="fa fa-clock-o"></span>
			                            </span>--%>
									<%--</div>--%>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">设备配置：</label>
								<div class="col-sm-4">
									<button class="btn btn-default dropdown-toggle form-control select_multiple" style="width: 100%;margin-left: 0px;" type="button" id="dropdownMenu21" data-toggle="dropdown">
										<span class="select_text" data-is-select="false">选择设备</span>
										<span class="caret"></span>
									</button>
									<ul class="dropdown-menu dropdown_item" style="bottom: auto;">
										<li><input type="checkbox" class="check_box" value="话筒" /> <span>话筒</span></li>
										<li><input type="checkbox" class="check_box" value="投影"/> <span>投影</span></li>
										<li><input type="checkbox" class="check_box" value="水"/> <span>水</span></li>

									</ul><!-- 为了方便演示，type设置text了，实际中可以设置成hidden -->
										<input id="equipment" name="equipment" class="form-control" style="display:none"/>
<%--									<input id="equipment" name="equipment" class="form-control" >${meet.equipment}</>--%>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">参会人：</label>
								<div class="col-sm-4">
									<div class="input-group" style="width: 220%">
										<t:choose url="common/selectUsers" hiddenName="attendeeId" hiddenValue="${attendeeId}" textValue="${meet.attendee}" textName="attendee" hiddenId="attendeeId" textId="attendee"></t:choose>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">会议内容：</label>
								<div class="col-sm-4">
									<textarea rows="4" id="content" name="content" class="form-control" >${meet.content}</textarea>
								</div>
							</div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">备注：</label>
                                <div class="col-sm-8">
                                    <textarea id="memo" name="memo" class="form-control" >${meet.memo}</textarea>
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
		laydate({
			elem : "#bookDate",
			event : "focus",
			istime : false,
			format : 'YYYY-MM-DD'
		});
		laydate({
			elem : "#startDate",
			event : "focus",
			istime : true,
			format : 'YYYY-MM-DD hh:mm'
		});
		laydate({
			elem : "#endDate",
			event : "focus",
			istime : true,
			format : 'YYYY-MM-DD hh:mm'
		});
		
		
		$('.clockpicker').clockpicker();
		
	});

	$(document).on("click",".check_box",function(event){
		event.stopPropagation();//阻止事件冒泡，防止触发li的点击事件
		//勾选的项
		var $selectTextDom=$(this).parent().parent("ul").siblings("button").children(".select_text");
		//勾选项的值
		var $selectValDom=$(this).parent().parent("ul").siblings(".select_val");
		//是否有选择项了
		var isSelected=$selectTextDom[0].getAttribute("data-is-select");
		var selectText="";//文本值，用于显示

		var selectVal=$selectValDom.val();//实际值，会提交到后台的
		var selected_text=$(this).siblings("span").text();//当次勾选的文本值
		var selected_val=$(this).val();//当次勾选的实际值
		//判断是否选择过
		if(isSelected=="true"){
			selectText=$selectTextDom.text();
		}
		if(selectText!=""){
			if(selectText.indexOf(selected_text)>=0){//判断是否已经勾选过
				selectText=selectText.replace(selected_text,"").replace(",,",",");//替换掉
				selectVal=selectVal.replace(selected_val,"").replace(",,",",");//替换掉
				//判断最后一个字符是否是逗号
				if(selectText.charAt(selectText.length - 1)==","){
					//去除末尾逗号
					selectText=selectText.substring(0,selectText.length - 1);
					selectVal=selectVal.substring(0,selectVal.length - 1);
				}
			}else{
				selectText+=","+selected_text;
				selectVal+=","+selected_val;
			}
		}else{
			selectText=selected_text;
			selectVal=selected_val;
		}
		$selectTextDom.text(selectText);
		$selectValDom.val(selectVal);
		debugger
		if(selectText==""){
			$selectTextDom.text("请选择");
			$selectTextDom[0].setAttribute("data-is-select","false");
		}else{
			$selectTextDom[0].setAttribute("data-is-select","true");
		}
		document.getElementById('equipment').value = selectText;
	})
	
	
	

</script>
<style>

	/*.dropdown_item{width: 100%}*/
	/*.dropdown_item>li:HOVER{background-color: #eee;cursor: pointer;}*/
	/*.dropdown_item>li {display: block;padding: 3px 10px;clear: both;font-weight: normal;line-height: 1.428571429;color: #333;white-space: nowrap;}*/
	/*.dropdown_item>li>.check_box{width: 18px;height: 18px;vertical-align: middle;margin: 0px;}*/
	/*.dropdown_item>li>span{vertical-align: middle;}*/
	/*.select_multiple .caret{border-top: 4px solid!important;border-bottom: 0;*/
</style>
</html>

