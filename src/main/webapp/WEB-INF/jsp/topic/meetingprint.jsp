<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
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
        text-align: left;
        vertical-align:middle;
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
    <div><h1 style="text-align: center">年第&nbsp&nbsp&nbsp&nbsp次主任办公室议题</h1></div><br><br>
    <span>会议时间:${time}</span><br><br>
    <span>会议地点:${meetingId}</span><br><br>
    <span>会议标题:${meetingName}</span><br><br>
    <table width="650px" height="500px" border="1px" style="text-align: center">
        <tr>
            <td style="width: 150px">序号</td>
            <td style="width:1000px">议题
            <td style="width: 150px">提议领导</td>
            <td style="width: 150px">汇报人</td>
            <td style="width: 150px">列席</td>
        </tr>
        <tr>
            <td style="">一</td>
            <td style="" class="height-td" colspan="5">&nbsp工作通报</td>

        </tr>
        <tr>
            <td style="">1</td>
            <td style=""  class="height-td"  style="width:1000px"></td>
            <td style="width: 150px"></td>
            <td style="width: 150px"></td>
            <td style="width: 150px"></td>
        </tr>
        <tr>
            <td style="">2</td>
            <td style="" class="height-td"  style="width:1000px"></td>
            <td style="width: 150px"></td>
            <td style="width: 150px"></td>
            <td style="width: 150px"></td>
        </tr>
        <tr>
            <td style="">二</td>
            <td style="" class="height-td" colspan="5">&nbsp审议事项</td>
        </tr>
        <tr>
            <td style="">3</td>
            <td style="" class="height-td"  style="width:1000px">${oaTopic.topicName}</td>
            <td style="width: 150px">${oaTopic.proposeLeaderName}</td>
            <td style="width: 150px">${oaTopic.reportName}</td>
            <td style="width: 150px"></td>
        </tr>
        <tr>
            <td style="">三</td>
            <td style="" class="height-td" colspan="5">&nbsp研究部署</td>
        </tr>
        <tr>
            <td style="">4</td>
            <td style="" class="height-td"  style="width:1000px"></td>
            <td style="width: 150px"></td>
            <td style="width: 150px"></td>
            <td style="width: 150px"></td>
        </tr>
        <tr>
            <td style="">5</td>
            <td style="" class="height-td"  style="width:1000px;"></td>
            <td style="width: 150px"></td>
            <td style="width: 150px"></td>
            <td style="width: 150px"></td>
        </tr>
    </table>

</div>
<!--打印内容结束-->

<!--endprint1-->
<div class="print-body" style="height: 30px;text-align: right">
    <button type='button' onclick=printOut(1)
            style="background: 	#008000;color: #ffffff; ">打印议题单
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