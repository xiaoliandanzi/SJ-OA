<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/12/8
  Time: 下午10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,laydate,icheck,summernote,clock"></t:base>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">

    <%@include file="/WEB-INF/jsp/flow/include/info.jsp" %>

    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>申请记录详情</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="commonForm" action="flow/biz/carapproval/save" method="post">
                        <input type="hidden" name="workflowId" id="workflowId" value="${workflowId }">
                        <input type="hidden" name="optType" id="optType">
                        <input type="hidden" name="id" id="id" value="${base.id }">
                        <input type="hidden" name="attachment" id="attachment" value="${biz.attachment }">
                        <%@include file="/WEB-INF/jsp/flow/carapproval/formshow.jsp" %>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <%@include file="/WEB-INF/jsp/flow/include/apphis.jsp" %>

    <c:if test="${show == '1' }">
        <%@include file="/WEB-INF/jsp/flow/include/inputapp.jsp" %>
    </c:if>

<%--    <div class="form-group" style="margin-top: 30px;">--%>
<%--        <div class="col-sm-4 col-sm-offset-3">--%>
<%--            <button type="button"  class="btn btn-primary" onclick="printIt()">打印</button>--%>
<%--        </div>--%>
<%--    </div>--%>

</div>
</body>

<script type="text/javascript">
    var printIt = function(){
        if (confirm('确定打印吗？')) {
            //wb.execwb(6,6)   ;
            window.print();//update by liuguocheng

        }
    }
    //时间控件初始化
    $(function() {

        //让页面管理表单的字段不可编辑
        $("#commonForm input").attr("disabled", "disabled");
        $("#commonForm textarea").attr("disabled", "disabled");
    });
    //时间控件初始化
    $(function() {
        laydate({
            elem : "#useTime",
            event : "focus",
            istime : false,
            format : 'YYYY-MM-DD'
        });
        // laydate({
        //     elem : "#endDay",
        //     event : "focus",
        //     istime : true,
        //     format : 'YYYY-MM-DD hh:mm'
        // });
    });

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
</html>


