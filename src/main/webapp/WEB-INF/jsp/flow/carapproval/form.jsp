<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/12/8
  Time: 下午10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="form-group">
    <label class="col-sm-3 control-label">用车单位*：</label>
    <div class="col-sm-5">
        <input id="useDepatment" name="useDepatment" minlength="2" type="text" class="form-control" required="" value="${biz.useDepatment }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">乘车人*：</label>
    <div class="col-sm-5">
        <input id="userName" name="userName" minlength="2" type="text" class="form-control" required="" value="${biz.userName }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">乘车人数*：</label>
    <div class="col-sm-5">
        <input id="person" name="person" type="number" class="form-control" required="" value="${biz.person }">
    </div>
</div>
<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label">用车事由*：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--        <input id="reason" name="reason" minlength="1" type="text" class="form-control" required="" value="${biz.reason }">--%>
<%--    </div>--%>
<%--</div>--%>
<div class="form-group">
    <label class="col-sm-3 control-label">用车类别*：</label>
    <div class="col-sm-3">
        <select class="form-control" style="width: 160%" name="reason" id="reason">
            <option value="处置辖区内紧急突发的各类自然灾害、事故灾难、公共卫生事件、上访、群体讨薪事件">
                处置辖区内紧急突发的各类自然灾害、事故灾难、公共卫生事件、上访、群体讨薪事件
            </option>
            <option value="领取紧急、保密、时限性强的文件资料">
                领取紧急、保密、时限性强的文件资料
            </option>
            <option value="重大活动">
                重大活动
            </option>
            <option value="集中领取和办理组织人事、社会劳动关系档案等">
                集中领取和办理组织人事、社会劳动关系档案等
            </option>
            <option value="协助辖区派出所接回重点人员">
                协助辖区派出所接回重点人员
            </option>
            <option value="参加各类重大活动，参加与之相关的各类应急保障专项任务">
                参加各类重大活动，参加与之相关的各类应急保障专项任务
            </option>
            <option value="参加临时通知（仅限当天）的区级部门以及区领导召集的紧急会议、调研、接待等紧急公务事项">
                参加临时通知（仅限当天）的区级部门以及区领导召集的紧急会议、调研、接待等紧急公务事项
            </option>
            <option value="财务人员集中赴银行办理大额现金业务等涉及财物安全的工作">
                财务人员集中赴银行办理大额现金业务等涉及财物安全的工作
            </option>
            <option value="赴不具备社会化出行保障条件的地区参加会议、考察、调研、学习等公务活动">
                赴不具备社会化出行保障条件的地区参加会议、考察、调研、学习等公务活动
            </option>
        </select>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">用车事由*：</label>
    <div class="col-sm-5">
        <input id="usecarreason" name="usecarreason" minlength="1" type="text" class="form-control" required="" value="${biz.usecarreason }">
    </div>
</div>

<div class="form-group">
    <label class="col-sm-3 control-label m-b">用车日期*：</label>
    <div class="col-sm-4 m-b">
        <input class="laydate-icon form-control layer-date" id="useTime" name="useTime"  value='<fmt:formatDate value="${biz.useTime }" type="both" pattern="yyyy-MM-dd"/>'>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">时间段*：</label>
    <div class="col-sm-4 m-b">
        <t:dictSelect name="morningOrAfternoon" type="select" typeGroupCode="morningroafternoon" defaultVal="${biz.morningOrAfternoon}"></t:dictSelect>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">目的地*：</label>
    <div class="col-sm-5">
        <input id="destination" name="destination" minlength="1" type="text" class="form-control" required="" value="${biz.destination }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">备注：</label>
    <div class="col-sm-5">
        <input id="commit" name="commit" minlength="1" type="text" class="form-control" value="${biz.commit }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">车牌号*：</label>
    <div class="col-sm-3">
        <select class="form-control" name="platenum" id="platenum" style="width: 100px" onchange="">
            <option>请选择</option>
        </select>
    </div>
    <label class="col-sm-3 control-label" style="width: 100px">驾驶员*：</label>
    <div class="col-sm-3">
        <select class="form-control" name="plateuser" id="plateuser" style="width: 100px" onchange="">
            <option>请选择</option>
        </select>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">ETC使用情况：</label>
    <div class="col-sm-5">
        <input id="etcmessage" name="etcmessage" minlength="1" type="text" class="form-control">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">行驶公里数*：</label>
    <div class="col-sm-5">
        <input id="mileage" name="mileage" minlength="1" required="" type="text" class="form-control">
    </div>
</div>
<script type="text/javascript">
    $("#plateuser").ready(function(){
        $.ajax({
            type: "POST",
            url: "flow/biz/carapproval/getplatemessage",
            success: function(data){
                var driver = data.driver;
                for(var i=0;i<driver.length;i++){
                    var ui="<option value='"+driver[i]+"'>"+driver[i]+"</option>";
                    $("#plateuser").append(ui);
                }

            }
        });
    });

    $("#platenum").ready(function(){
        $.ajax({
            type: "POST",
            url: "flow/biz/carapproval/getplatemessage",
            success: function(data){
                var plate = data.plate;
                for(var i=0;i<plate.length;i++){
                    var ui="<option value='"+plate[i]+"'>"+plate[i]+"</option>";
                    $("#platenum").append(ui);
                }

            }
        });
    });
    $.post("flow/biz/getSpe/getcaradminrole", {}, function(data) {
        if($.trim(data)=="true"){
        }else{
            $('#platenum').attr("disabled", "disabled");
            $('#plateuser').attr("disabled", "disabled");
            //$('#etcmessage').removeAttr("disabled");
            $('#etcmessage').attr("disabled", "disabled");
        }
    })
</script>


