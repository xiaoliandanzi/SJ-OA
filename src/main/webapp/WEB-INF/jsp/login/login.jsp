<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>双井智慧办公系统</title>
    <meta name="keywords" content="active4j-oa演示系统">
    <meta name="description" content="active4j-oa演示系统">
    <link rel="shortcut icon" href="static/bootstrap/image/favicon.ico">
    <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/bootstrap/css/font-awesome.min.css" rel="stylesheet">
    <link href="static/bootstrap/css/animate.min.css" rel="stylesheet">
    <link href="static/bootstrap/css/style.min.css" rel="stylesheet">
    <link href="static/toastr/css/toastr.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/index.css"/>
    <link href="static/layui/css/layui.css" rel="stylesheet"/>
    <link rel="stylesheet" href="css/index.css"/>
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/>
    <![endif]-->
    <script>if (window.top !== window.self) {
        window.top.location = window.location;
    }</script>
</head>
<body style="background: url(static/bootstrap/image/login-bg.jpg) no-repeat; background-size: 100% 100%;">

<div style="position: absolute;top: 21%;height: 404px;width: 463px;border-radius: 20px;right: 100px;background: url(static/bootstrap/image/card.png) no-repeat;">
    <div class="middle-box text-center loginscreen  animated fadeInDown" style="width: 500px !important;">
        <div>
            <div style="    color: #2d6bb8;
        position: absolute;
        left: 53px;
        font-size: 36px;
        top: 64px;">欢迎登录系统
            </div>
            <form class="form-horizontal m-t" id="signupForm" action="login" method="post"
                  style="margin-left: 100px;margin-top:82px !important;">
                <div class="form-group" style="position: relative;">
                    <i style="width: 30px;
                position: absolute;
                height: 34px;
                border: 1px solid #e5e6e7;
                left: -30px;"><img src="static/bootstrap/image/admin.png" style="margin-top: 6px;"></i>
                    <input type="text" name="userName" id="userName" value="" class="form-control" placeholder="用户名"
                           required="" style="width: 210px;">
                </div>
                <div class="form-group" style="position: relative;">
                    <i style="width: 30px;top: 20px;
        position: absolute;
        height: 34px;
        border: 1px solid #e5e6e7;
        left: -30px;"><img src="static/bootstrap/image/password.png" style="margin-top: 6px;"></i>
                    <input type="password" name="password" id="password" value="" class="form-control"
                           placeholder="密码" required="" style="width: 210px;margin: 20px 0">
                </div>
                <%--                <div class="form-group">--%>
                <%--                <input id="randCode" name="randCode" type="text" class="form-control" placeholder="验证码" required="" style="width: 60%">--%>
                <%--                <div style="float: right; margin-top: -3.4rem;">--%>
                <%--                <img id="randCodeImage" src="" />--%>
                <%--                </div>--%>
                <%--                </div>--%>
                <div class="form-group" style="position: relative;left: -156px;color: #2d6bb8;">
                    <input type="checkbox" value="true" title="记住密码"/> 记住密码
                </div>
                <button type="button" class="btn btn-primary block full-width m-b" onclick="doSubmit()"
                        style="width: 100% !important;margin-left: -51px;margin-top: 20px; background-color:#47b6b5;border-color:#47b6b5">
                    登 录
                </button>

                <%--<p class="text-muted text-center"> <a href="https://github.com/yunchaoyun/active4j-oa">github</a> | <a href="https://gitee.com/active4j/active4j-oa">码云</a>--%>
                </p>
            </form>
        </div>
    </div>
</div>
</body>

<script src="static/jquery/js/jquery.min.js"></script>
<script src="static/bootstrap/js/bootstrap.min.js"></script>
<script src="static/bootstrap/js/qq.js"></script>
<script src="static/validate/js/jquery.validate.min.js"></script>
<script src="static/validate/js/messages_zh.min.js"></script>
<script src="static/login/js/login.js"></script>
<script src="static/toastr/js/toastr.min.js"></script>
<script src="static/layui/layui.js"></script>


</html>

