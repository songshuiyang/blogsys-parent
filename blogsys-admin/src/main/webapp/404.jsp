<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/taglib.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>该页面不存在</title>

    <link rel="stylesheet" href="${ctx}static/admin/css/reset.css"/>
    <link rel="stylesheet" href="${ctx}static/admin/css/404.css">

    <script src="${ctx}static/plugins/jquery/jquery-3.2.1.min.js"></script>
    <%--   <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>--%>

</head>
<body>
<div class="auto">
    <div class="container">
        <div class="settings">
            <i class="icon"></i>
            <h4>很抱歉！没有找到您要访问的页面！</h4>
            <p><span id="num">5000</span> 秒后将自动跳转到首页</p>
            <div>
                <a href="${ctx}system/admin" title="返回首页">返回首页</a>
                <a href="javascript:back();" title="上一步" id="reload-btn">上一步</a>
            </div>
        </div>
    </div>
</div>

<script>
    //倒计时跳转到首页的js代码
    var $_num = $("#num");
    var num = parseInt($_num.html());
    var numId = setInterval(function () {
        num--;
        $_num.html(num);
        if (num === 0) {
            //跳转地址写在这里
            window.location.href = "${ctx}system/admin";
        }
    }, 1000);
    //返回按钮单击事件
    function back() {
        window.history.back();
    }
</script>

</body>
</html>
