
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
<div class="form-group">
    <label class="col-sm-3 control-label">用车事由*：</label>
    <div class="col-sm-5">
        <input id="reason" name="reason" minlength="1" type="text" class="form-control" required="" value="${biz.reason }">
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
        <select class="form-control" name="platenum" id="platenum" style="width: 100px">
            <option value="${biz.platenum }">${biz.platenum }</option>
        </select>
    </div>
    <label class="col-sm-3 control-label" style="width: 100px">驾驶员*：</label>
    <div class="col-sm-3">
        <select class="form-control" name="plateuser" id="plateuser" style="width: 100px">
            <option value="${biz.plateuser }">${biz.plateuser }</option>
        </select>
    </div>
</div>

<div class="form-group">
    <label class="col-sm-3 control-label">ETC使用情况：</label>
    <div class="col-sm-5">
        <input id="etcmessage" name="etcmessage" minlength="1" type="text" class="form-control" value="${biz.etcmessage }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">行驶公里数*：</label>
    <div class="col-sm-5">
        <input id="mileage" name="mileage" minlength="1" type="text" class="form-control" value="${biz.mileage }">
    </div>
</div>
<c:if test="${empty biz.id }">
    <div class="form-group">
        <label class="col-sm-3 control-label">附件:</label>
        <div class="col-sm-5">
            <div id="filePicker">上传附件</div>
        </div>
        <div class="col-sm-5">
            <div id="fileList" class="uploader-list"></div>
        </div>
    </div>
</c:if>
<c:if test="${not empty biz.id }">
    <div class="form-group">
        <label class="col-sm-3 control-label">附件:</label>
        <div class="col-sm-5">
            <button class="btn btn-primary" type="button" onclick="doBtnDownloadFile();">下载附件</button>
        </div>
    </div>
</c:if>
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
            //$('#etcmessage').removeAttr("disabled");
            $('#platenum').change(function(){
                //alert($(this).children('option:selected').val());
                var ss=$('#id').val();
                var num =$(this).children('option:selected').val();//这就是selected的值
                $.post("flow/biz/carapproval/saveetcmessage?id="+ss+"&platenum="+num, {}, function(data) {
                    if(data.success) {
                        parent.layer.msg('保存成功');
                    }else {
                        parent.layer.msg('保存失败');
                    }
                })
            })

            $('#plateuser').change(function(){
                //alert($(this).children('option:selected').val());
                var ss=$('#id').val();
                var user =$(this).children('option:selected').val();//这就是selected的值
                $.post("flow/biz/carapproval/saveetcmessage?id="+ss+"&plateuser="+user, {}, function(data) {
                    if(data.success) {
                        parent.layer.msg('保存成功');
                    }else {
                        parent.layer.msg('保存失败');
                    }
                })
            })
        }else{
            $('#platenum').attr("disabled", "disabled");
            $('#plateuser').attr("disabled", "disabled");
        }
    })

    // function doBtnDownloadFile() {
    //     var att = document.getElementById("attachment").value;
    //     if(!att) {
    //         qhAlert('该文件附件还未上传附件！');
    //         return;
    //     }
    //
    //     location.href = "func/upload/download?id=" + att;
    // };

    function doBtnDownloadFile() {
        var att = document.getElementById("attachment").value;

        if(!att) {
            qhAlert('该文件附件还未上传附件！');
            return;
        }

        var list=att.split(",");
        for (const url of list) {
            donw("func/upload/download?id="+url);
        }
    };

    function donw(url) {
        var iframe = document.createElement("iframe");
        iframe.src = url;
        iframe.style.display = "none";
        document.body.appendChild(iframe);
    };
</script>
