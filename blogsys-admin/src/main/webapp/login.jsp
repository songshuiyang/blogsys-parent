<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
    <%@include file="common/front/meta.jsp" %>
    <title>博客系统后台登录</title>
    <link rel="shortcut icon" href="${ctx}static/front/images/favicon.ico" type="image/x-icon">
    <link rel='stylesheet' type="text/css" href='${ctx}static/plugins/bootstrap/bootstrap-3.3.0/css/bootstrap.css'>
    <link rel="stylesheet" type="text/css" href="${ctx}static/admin/css/login.css?v=${version}" />
    <link rel="stylesheet" type="text/css" href="${ctx}static/front/css/hover.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${ctx}static/front/css/animate/animate.min.css" rel="stylesheet" />
</head>
<body>
<div class="login-container">
    <div class="content">
        <div id="large-header" class="large-header">
            <div class="wrapper" style="position:fixed">
                <form class="form-signin" action="${ctx}system/login" method="post">
                    <div class="form-signin-heading animated bounceInLeft">博客系统后台登录</div>
                    <c:if test="${not empty user }">
                        <span style="color: red;font-size:12px;">${user.username} ${message}</span>
                    </c:if>
                    <input type="text" class="form-control animated bounceInRight" name="username" value="admin" placeholder="用户名:" required="" autofocus="" />
                    <input type="password" style="margin-top: 10px;" class="form-control animated bounceInLeft" name="password" value="123" placeholder="密码" required=""/>
                    <button class="btn btn-lg btn-primary btn-block animated bounceInRight hvr-grow" type="submit">登录</button>
                </form>
            </div>
            <canvas id="demo-canvas"></canvas>
        </div>
    </div>
</div><!-- /container -->
<script src="${ctx}static/admin/js/login/rAF.js"></script>
<script src="${ctx}static/admin/js/login/login.js"></script>
</body>
</html>