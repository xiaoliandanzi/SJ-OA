<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<style media="print">
    /*去除页眉页脚*/
    @page {
        size: auto;
        margin: 0mm;
    }
</style>
<style>
    html, body {
        overflow: auto;
    }

    .print-body {
        width: 680px; /*宽度设置固定值*/
        height: 750px;
        margin: 10px auto;
    }


    .print-body table {
        border: 1px black;
        color: black;
        border-collapse: collapse;
    }

    .print-body input {
        border: 0;
        outline: 0;
    }

    .print-body tr {
        height: 40px;
    }

    .height-td {
        height: 150px;
        text-align: left;
        vertical-align: text-top;
        position: relative;
    }

    .top-td {
        width: 220px;
    }

    .time-right {
        position: absolute;
        right: 10px;
        bottom: 10px;
    }
</style>
<body>
<!--startprint1-->

<!--打印内容开始-->
<div class="print-body">
    <div><h1 style="text-align: center">议 题 申 请 审 批 表</h1></div>
    <table width="670px" hight="978px" border="1px" style="text-align: center">
        <tr>
            <td style="width: 150px">申报科室</td>
            <td style="width: 150px" id="toCompany">${deptName}</td>
            <td style="width: 150px">提议领导</td>
            <td style="width: 150px">${oaTopic.proposeLeaderName}</td>
        </tr>
        <tr>
            <td style="width: 150px">负责人</td>
            <td style="width: 150px" id="company">${oaTopic.createUserId}</td>
            <td style="width: 150px">汇报人</td>
            <td style="width: 150px">${oaTopic.reportName}</td>
        </tr>
    </table>
    <table width="670px" hight="978px" border="1px">
        <tr>
            <td class="height-td top-td" colspan="2">科室负责人意见:
                <div class="time-right">&nbsp&nbsp&nbsp&nbsp年&nbsp&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp日</div>
            </td>
            <td class="height-td top-td" colspan="2">主管领导意见:
                <div class="time-right">&nbsp&nbsp&nbsp&nbsp年&nbsp&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp日</div>
            </td>
            <td class="height-td top-td" colspan="2">综合办意见:
                <div class="time-right">&nbsp&nbsp&nbsp&nbsp年&nbsp&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp日</div>
            </td>
        </tr>
        <tr>
            <td class="height-td" colspan="3">财务科意见:
                <div class="time-right">&nbsp&nbsp&nbsp&nbsp年&nbsp&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp日</div>
            </td>
            <td class="height-td" colspan="3">纪委意见:
                <div class="time-right">&nbsp&nbsp&nbsp&nbsp年&nbsp&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp日</div>
            </td>
        </tr>
        <tr>
            <td colspan="6">议题标题:${oaTopic.topicName}</td>
        </tr>
        <tr>
            <td colspan="6" style="height: 200px;text-align: left;vertical-align: text-top;">
                内容摘要:${oaTopic.topicContent}
            </td>
        </tr>
        <tr>
            <td colspan="6">备注:${oaTopic.topicRemark}</td>
        </tr>
    </table>
</div>
<!--打印内容结束-->

<!--endprint1-->
<div class="print-body" style="height: 30px;text-align: right">
    <button type='button' onclick=printOut(1)
            style="background: 	#008000;color: #ffffff; ">打印议题申请审批表
    </button>
</div>
</body>
<script type="text/javascript">
    function printOut(fang) {
        if (fang < 10) {
            bdhtml = window.document.body.innerHTML;//获取当前页的html代码
            sprnstr = "<!--startprint" + fang + "-->";//设置打印开始区域
            eprnstr = "<!--endprint" + fang + "-->";//设置打印结束区域
            prnhtml = bdhtml.substring(bdhtml.indexOf(sprnstr) + 18); //从开始代码向后取html
            prnhtml = prnhtml.substring(0, prnhtml.indexOf(eprnstr));//从结束代码向前取html
            window.document.body.innerHTML = prnhtml;
            remove_ie_header_and_footer();
            window.print();
            window.document.body.innerHTML = bdhtml;
        } else {
            window.print();
        }
        //location.reload();
    }

    function remove_ie_header_and_footer() {
        var hkey_root, hkey_path, hkey_key;
        hkey_path = "HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
        try {
            var RegWsh = new ActiveXObject("WScript.Shell");
            RegWsh.RegWrite(hkey_path + "header", "");
            RegWsh.RegWrite(hkey_path + "footer", "");
        } catch (e) {
        }
    }
</script>
</html>

