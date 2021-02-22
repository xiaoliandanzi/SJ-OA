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
        <input id="useDepartment" name="useDepartment" minlength="2" type="text" class="form-control" required="" value="${biz.useDepartment }">
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
    <label class="col-sm-3 control-label">用车事由*：</label>
    <div class="col-sm-3">
       <select class="form-control" name="reason" id="reason">
           <option value="应急类">应急类</option>
           <option value="文件">文件</option>
           <option value="重大活动">重大活动</option>
           <option value="临时调研">临时调研</option>
           <option value="财务">财务</option>
           <option value="城六区外参加会议或公务活动">城六区外参加会议或公务活动</option>
       </select>
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
