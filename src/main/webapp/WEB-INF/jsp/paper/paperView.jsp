<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/12/10
  Time: 下午11:39
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/12/10
  Time: 上午12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,laydate,icheck"></t:base>
    <script type="text/javascript">
        $(function() {
            laydate({elem:"#paperDate",event:"focus",istime: false, format: 'YYYY-MM-DD'});
        });

    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">

    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="commonForm">
                        <input type="hidden" name="attachment" id="attachment" value="${biz.attachment }">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">科室：</label>
                        <div class="col-sm-8">
                            <input id="dept" name="dept"  type="text" class="form-control" required="" readonly value="${biz.dept }">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">起草人：</label>
                        <div class="col-sm-8">
                            <input id="draftMan" name="draftMan"  type="text" class="form-control" required="" readonly value="${biz.draftMan }">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">公开选择：</label>
                        <div class="col-sm-8">
                            <t:dictSelect name="paperPublic" type="select" typeGroupCode="paperpublicstatus" defaultVal="${biz.paperPublic}"></t:dictSelect>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">保密级别：</label>
                        <div class="col-sm-8">
                            <input readonly id="secretLevel" name="secretLevel" minlength="1" type="text" class="form-control" required="" value="${biz.secretLevel }">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">文件份数：</label>
                        <div class="col-sm-8">
                            <input readonly id="paperCount" name="paperCount" type="number" class="form-control" required="" value="${biz.paperCount }">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">发文日期：</label>
                        <div class="col-sm-8">
                            <input readonly id="paperDate" name="paperDate" type="text" class="laydate-icon form-control layer-date" value='<fmt:formatDate value="${biz.paperDate }" type="date" pattern="yyyy-MM-dd"/>'>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">发文文号：</label>
                        <div class="col-sm-8">
                            <input readonly id="paperNumber" name="paperNumber" minlength="1" type="text" class="form-control" required="" value="${biz.paperNumber }">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">发文范围：</label>
                        <div class="col-sm-8">
                            <input readonly id="paperArea" name="paperArea" minlength="1" type="text" class="form-control" required="" value="${biz.paperArea }">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">文件标题：</label>
                        <div class="col-sm-8">
                            <input readonly id="title" name="title" minlength="1" type="text" class="form-control" required="" value="${biz.title }">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">内容摘要：</label>
                        <div class="col-sm-8">
                            <textarea rows="4" readonly id="paperAbstract" name="paperAbstract" minlength="1" type="text" class="form-control" required="">${biz.paperAbstract }</textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">备注：</label>
                        <div class="col-sm-8">
                            <textarea id="commit" name="commit" minlength="1" type="text" class="form-control" required="">${biz.commit }</textarea>
                        </div>
                    </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label m-b">附件：</label>
                            <div class="col-sm-8">
                                <button class="btn btn-primary" type="button" onclick="doBtnDownloadFile();">下载附件</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>

<script type="text/javascript">
    function doBtnDownloadFile() {
        var att = document.getElementById("attachment").value;
        if(!att) {
            qhAlert('该文件附件还未上传附件！');
            return;
        }

        location.href = "func/upload/download?id=" + att;
    };
</script>
</html>