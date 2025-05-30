<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <!--字体图标-->
    <link href="../javaex/pc/css/icomoon.css" rel="stylesheet" />
    <!--动画-->
    <link href="../javaex/pc/css/animate.css" rel="stylesheet" />
    <!--骨架样式-->
    <link href="../javaex/pc/css/common.css" rel="stylesheet" />
    <!--皮肤（缇娜）-->
    <link href="../javaex/pc/css/skin/tina.css" rel="stylesheet" />
    <!--jquery，不可修改版本-->
    <script src="../javaex/pc/lib/jquery-1.7.2.min.js"></script>
    <!--全局动态修改-->
    <script src="../javaex/pc/js/common.js"></script>
    <!--核心组件-->
    <script src="../javaex/pc/js/javaex.min.js"></script>
    <!--表单验证-->
    <script src="../javaex/pc/js/javaex-formVerify.js"></script>
    <title>后台管理</title>
    <style>
        .bg-wrap, body, html {height: 100%;}
        input{line-height: normal;-webkit-appearance: textfield;background-color: white;-webkit-rtl-ordering: logical;cursor: text;padding: 1px;border-width: 2px;border-style: inset;border-color: initial;border-image: initial;}
        input[type="text"], input[type="password"]{border: 0;outline: 0;}
        input, button{text-rendering: auto;color: initial;letter-spacing: normal;word-spacing: normal;text-transform: none;text-indent: 0px;text-shadow: none;display: inline-block;text-align: start;margin: 0em;font: 400 13.3333px Arial;}
        input[type=button]{-webkit-appearance: button;cursor: pointer;}
        .bg-wrap {position: relative;background: url(../javaex/img/background.jpg) top left no-repeat;background-size: cover;overflow: hidden;}
        .main-cont-wrap{z-index: 1;position: absolute;top: 50%;left: 50%;margin-left: -190px;margin-top: -255px;box-sizing: border-box;width: 380px;padding: 30px 30px 40px;background: #fff;box-shadow: 0 20px 30px 0 rgba(63,63,65,.06);border-radius: 10px;}
        .form-title{margin-bottom: 40px;}
        .form-title>span{font-size: 20px;color: #2589ff;}
        .form-item{margin-bottom: 30px;position: relative;height: 40px;line-height: 40px;border-bottom: 1px solid #e3e3e3;box-sizing: border-box;}
        .form-txt{display: inline-block;width: 70px;color: #595961;font-size: 14px;margin-right: 10px;}
        .form-input{border: 0;outline: 0;font-size: 14px;color: #595961;width: 155px;}
        .form-control{background: #FFFFFF;width: 200px;height: 30px;border-radius: 10px}
        .form-btn{margin-top: 40px;}
        .ui-button{display: block;width: 320px;height: 50px;text-align: center;color: #fff;background: #2589ff;border-radius: 6px;font-size: 16px;border: 0;outline: 0;}
    </style>
</head>
<body>
<div class="bg-wrap">
    <div class="main-cont-wrap login-model">
        <form id="form">
            <div class="form-title">
                <span>图书管理系统登录页面</span>
            </div>
            <div class="form-item">
                <span class="form-txt">账号：</span>
                <input type="text" class="form-input original" id="uname" name="loginName" placeholder="请输入账号" data-type="必填" error-pos="32" />
            </div>
            <div class="form-item">
                <span class="form-txt">密码：</span>
                <input type="password" class="form-input original" name="password" id="pass" placeholder="请输入密码" data-type="必填" error-pos="32" />
            </div>
            <div class="form-item">
                <span class="form-txt">角色：</span>
                <select id="select" name="identity" class="form-control">
                    <option value="">请选择身份</option>
                    <option value="3">管理员</option>
                    <option value="0">普通用户</option>
                </select>
            </div>
            <div class="form-item">
                <input type="checkbox" id="remember" name="remember" class="fill"/> 记住我
            </div>
            <div class="form-btn">
                <input type="button" class="ui-button" id="save" value="登录" />
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    // 登录
    $("#save").on("click", function(){
        var username =  $("#uname").val();
        var password =  $("#pass").val();
        var identity = $("#select").val();
        // 记住我
        var remember = $("#remember").val();

        $.post("/user/login",{"username":username,"password": password,"identity":identity,"remember":remember},function(result) {
            // 由于SpringSecurity 框架返回的是HTML页面, 所以使用js形式登录只能以html页面元素判定是否登录成功
            var index = result.indexOf("remember");
            //console.log(identity);
            if (index>0) {
                // 提示登录失败
                javaex.optTip({
                    content : "用户名或密码错误!",
                    type : "error"
                });
            }else {
                // 登录成功 跳转首页
                if (identity == 3 && username == "admin") {
                    window.location.href = "/index";
                }else if (identity == 0 && username != "admin"){
                    window.location.href = "/user/index";
                }else {
                    alert("请选择角色");
                }
            }

        });
    });
</script>
</body>
</html>