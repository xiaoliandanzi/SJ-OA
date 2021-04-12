
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,select2,jqgrid"></t:base>
</head>
<body class="gray-bg">
<!-- 页面部分 -->
<div class="wrapper wrapper-content animated fadeInRight">

    <div id="app">
        <div class="navbarBox">
            <div class="logoBox">
                <img src="./img/a.png" alt="">
            </div>
            <div class="title">
                双井街道智慧办公系统
            </div>
            <div class="btnBox">
                <img class="btnImg" src="./img/d.png" alt="">
                <span class="btnTxt">
                    <a href="/oa/" class="nava">门户首页</a>
                </span>
            </div>
            <div class="btnBox">
                <img class="btnImg" src="./img/b.png" alt="">
                <span class="btnTxt">
                   <a href="/oa/index" class="nava">个人办公</a>
                </span>
            </div>
        </div>

        </div>
        <div class="tabs">
            <div class="tabsBox">
                <div class="tabs_item">
                    双井风采
                </div>
            </div>
        </div>

    </div>
    <div class="row">
        <div class="col-sm-12">

            <div class="container">
                <div class="list" id="listImg" style="left:0px;margin-left: 150px"></div>
            </div>
        </div>
    </div>
</div>

</body>
<script>
    $.post("oa/index/getAllImgPath", function (data) {
        for(var i=0;i<data.length;i++){
            var ui="<img style='width: 400px;height: 300px;margin: 5px' src="+data[i]+" />";
            $("#listImg").append(ui);
        }
    });
</script>
<style>
    * {
        margin: 0;
        padding: 0;
    }

    .navbarBox {
        display: flex;
        position: relative;
    }

    .logoBox {
        margin-left: 20px;
    }

    .title {
        font-size: 30px;
        color: #347BB7;
        line-height: 90px;
        font-weight: bold;
        margin-left: 18px;
    }

    .btnBox {
        height: 90px;
        position: relative;
        margin-left: 20px;
        line-height: 90px;

    }

    .btnImg {
        position: absolute;
        top: 0;
        bottom: 0;
        width: 20px;
        height: 20px;
        margin: auto;
    }

    .btnTxt {
        font-size: 16px;
        font-weight: 400;
        color: #2F4050;
        margin-left: 20px;
    }

    .btnTxt:hover {
        color: #D30404;
    }

    .selectBox {
        width: 300px;
        /* margin-top: 26px; */
        /* display: flex; */
        /* margin-left: 45px; */
        height: 28px;
        position: relative;
        top: 0;
        bottom: 0;
        margin: auto;
        margin-left: 45px;
        margin-right: 0;
        border: 1px solid #000;
    }


    .selectBtn {
        display: block;
        width: 100px;
        position: relative;
        top: 0;
        bottom: 0;
        margin: auto;
        margin-left: 0;
        height: 28px;
        text-align: center;
        font-size: 20px;
        color: #FFFFFF;
        background: #E60012;
        line-height: 28px;
        border: 1px solid #000;
    }

    .tabs {
        width: 100%;
        height: 40px;
        background: #347BB7;
    }

    .tabsBox {
        margin: 0 auto;
        display: flex;
        text-align: center;
        justify-content: center;
    }

    .tabs_item {
        width: 70px;
        height: 30px;
        margin-top: 10px;
        line-height: 30px;
        color: #FFFFFF;
        font-weight: 500;
        font-size: 16px;
        margin-left: 16px;

    }

    .tabs_item:hover {

        background: #296190;
    }


    .nava {
        color: #2F4050;
        text-decoration: none;
    }
</style>
</html>