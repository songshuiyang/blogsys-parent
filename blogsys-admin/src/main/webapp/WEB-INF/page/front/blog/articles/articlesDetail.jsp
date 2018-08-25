<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../../../../common/taglib.jsp" %>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>文章详情</title>
    <%@include file="../../../../../common/front/meta.jsp" %>
    <%@include file="../../../../../common/front/link.jsp" %>
    <link href="${ctx}static/front/css/detail.css?v=${version}" rel="stylesheet" />
    <%--代码高亮--%>
    <script type="text/javascript" src="${ctx}static/plugins/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
    <link rel="stylesheet" href="${ctx}static/plugins/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css">
    <script type="text/javascript">
        SyntaxHighlighter.all();
    </script>
</head>
<body style="background-color: #ecf0f5">
<div class="overflay"></div>
<%@include file="../../../../../common/front/header.jsp" %>

<!-- 主体（一般只改变这里的内容） -->
<div class="blog-body">
    <div class="blog-container">
        <blockquote class="layui-elem-quote sitemap shadow">
            <a href="${ctx}" title="网站首页">网站首页 > </a>
            <a><cite>${blog.title}</cite></a>
        </blockquote>
        <div class="blog-main">
            <div class="blog-main-left" style="width:100%">
                <div class="article-detail shadow animated zoomIn">
                    <div class="article-detail-title animated bounceInLeft">
                        ${blog.title}
                    </div>
                    <div class="article-detail-info">
                        <span style="color: blue">
                            <i class="fa fa-clock-o"></i>
                            创建时间: ${blog.createdDateAlias}
                        </span>
                        <span style="color: blue">
                             <i class="fa fa-user"></i>
                            作者：${blog.createdBy}
                        </span>
                        <span style="color: blue">
                             <i class="fa fa-eye"></i>
                            浏览量：${blog.hitsNum}
                        </span>
                    </div>
                    <div class="article-detail-content">
                        ${blog.content}
                    </div>
                </div>
                <!-- 评论区域 -->
                <div class="blog-module shadow animated zoomIn" style="box-shadow: 0 1px 8px #a6a6a6;">
                    <div class="layui-container">
                        <div class="layui-row">
                            <div class="layui-col-md6">
                                <button type="button" class="layui-btn layui-btn-radius layui-btn-normal"
                                        id="satisfactoryClick" style="margin-left:75%;">
                                    <i class="fa fa-thumbs-o-up"></i> 赞一个(${blog.satisfactoryNum})
                                </button>
                            </div>
                            <div class="layui-col-md6">
                                <button type="button" class="layui-btn layui-btn-radius layui-btn-warm"
                                        id="dissatisfiedClick">
                                    <i class="fa fa-thumbs-o-down"></i> 呵呵(${blog.dissatisfiedNum})
                                </button>
                            </div>
                        </div>
                    </div>
                    <fieldset class="layui-elem-field layui-field-title" style="margin-bottom:0">
                        <legend>不来几句?</legend>
                        <div class="layui-field-box" style="margin-top: 20px;">
                            <form class="layui-form">
                                <input type="hidden" id="coverImage" name="coverImage" value="${randomHeadPortrait}">
                                <input type="hidden" id="blogArticlesId" name="blogArticlesId" value="${blog.id}">
                                <input type="hidden" id="type" name="type" value="0">

                                <div class="layui-container">
                                    <div class="layui-row">
                                        <div class="layui-form-item">
                                            <label for="coverImage" class="layui-form-label">
                                                头像:
                                            </label>

                                            <div class="layui-col-md1">
                                                <div class="layui-inline">
                                                    <div id="uploadImageDiv" style="margin-top: -10px;">
                                                        <img src="${randomHeadPortrait}"  style="border-radius:30px;" width="60px" height="60px"
                                                             class="layui-upload-img layui-show">
                                                    </div>

                                                </div>
                                            </div>

                                            <div class="layui-col-md3">
                                                <label for="coverImage" class="layui-form-label" style="color: red;width: 120px;">
                                                    刷新页面更换头像
                                                </label>
                                            </div>
                                            <%--<div class="layui-col-md3">--%>
                                                <%--<button type="button" class="layui-btn layui-btn-radius layui-btn-normal" id="uploadImage">--%>
                                                    <%--<i class="layui-icon">&#xe67c;</i>上传图片--%>
                                                <%--</button>--%>
                                            <%--</div>


                                                    // 封面上传
        upload.render({
            elem: '#uploadImage'
            , url: '/file/uploadLocal'
            , size: 20480
            , method: 'post'
            , field: 'upfile'
            , before: function (obj) {

            }, done: function (res) {
                if (res.state != 'SUCCESS') {
                    layer.msg(res.content, {icon: 5, anim: 6});
                } else {
                    $('#coverImage').val(res.url)
                    $('#uploadImageDiv').find('img').remove();
                    $('#uploadImageDiv').append('<img src="' + res.url + '"  style="border-radius:30px;" width="60px" height="60px" class="layui-upload-img layui-show">');

                }
            }
        });





                                            --%>
                                        </div>

                                        <div class="layui-col-md5">
                                            <div class="layui-form-item">
                                                <div class="layui-inline">
                                                    <label for="author" class="layui-form-label">
                                                        你的大名：
                                                    </label>
                                                    <div class="layui-input-block input-div">
                                                        <input class="layui-input" type="text" id="author" name="author" lay-verify="required" placeholder="必填">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="layui-col-md5">
                                            <div class="layui-form-item">
                                                <div class="layui-inline">
                                                    <label for="email" class="layui-form-label">
                                                        邮箱地址：
                                                    </label>
                                                    <div class="layui-input-block input-div">
                                                        <input class="layui-input" type="text" id="email" name="email" lay-verify="required|email" placeholder="必填">
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>

                                    <div class="layui-form-item">
                                        <div class="layui-inline">
                                            <label for="content" class="layui-form-label">
                                                内容：
                                            </label>
                                            <div class="layui-input-block input-div-textarea">
                                                <textarea class="layui-textarea" name="content" id="content" lay-verify="required"  placeholder="请输入你的内容"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="layui-form-item">
                                    <button class="layui-btn layui-btn-radius layui-btn-normal"  style="margin-left:60px;" lay-filter="comment" lay-submit="">
                                        <i class="layui-icon">&#xe618;</i>提交评论
                                    </button>
                                </div>
                            </form>
                        </div>
                    </fieldset>
                    <div class="blog-module-title">最新评论</div>
                    <ul class="blog-comment">
                        <c:if test="${fn:length(commentList)==0}">
                            &nbsp;&nbsp;&nbsp;无人评论。。。
                        </c:if>
                        <c:forEach items="${commentList}" var="comment" varStatus="status">
                            <%--左右滑进动画--%>
                            <c:if test="${status.index%2==0}">
                                <li class="animated zoomInLeft">
                            </c:if>
                            <c:if test="${status.index%2!=0}">
                                <li class="animated zoomInRight">
                            </c:if>
                                <div class="comment-parent">
                                    <div class="imgcomment" ><img class="layui-circle" style="border-radius:30px;" width="60px" height="60px" src="${comment.coverImage}"  alt="${comment.author}" /></div>
                                    <div class="leftcomment" style="">
                                        <div class="info">
                                            <i class="fa fa-user"></i>&nbsp;
                                            <span class="username">${comment.author}</span>
                                            &nbsp;&nbsp;&nbsp;
                                            <i class="fa fa-clock-o"></i>&nbsp;
                                            <span class="time">${comment.createdDateAlias}</span>

                                        </div>
                                        <div class="content" style="overflow: auto">${comment.content}</div>
                                    </div>
                                    <div class="rightcomment">

                                        <a class='animated lightSpeedIn' onclick="satisfactoryCommentClick(${comment.id})"  href="javascript:void(0)">
                                            <i class="fa fa-thumbs-o-up"></i>&nbsp;
                                            <span>${comment.satisfactoryNum}</span>
                                        </a>
                                        &nbsp;&nbsp;&nbsp;
                                        <a class='animated lightSpeedIn' onclick="dissatisfiedCommentClick(${comment.id})"  href="javascript:void(0)">
                                            <i class="fa fa-thumbs-o-down"></i>&nbsp;
                                            <span>${comment.dissatisfiedNum}</span>
                                        </a>
                                        <br/>
                                        <br/>

                                        <c:if test="${comment.authorLocationProvince !=null}">
                                            <i class="fa fa-location-arrow"></i>&nbsp;
                                            <span>${comment.authorLocationProvince} ${comment.authorLocationCity}</span>
                                        </c:if>

                                    </div>
                                </div>

                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
</script>
<!-- layui.js -->
<script src="${ctx}static/layui-module/layui/layui.js"></script>
<!-- 全局脚本 -->
<script src="${ctx}static/front/js/global.js?v=${version}"></script>
<script>
    var $;
    layui.use(['form','layer', 'upload'], function () {
        var form = layui.form;
        $ = layui.jquery;
        var layer = layui.layer;
        var upload = layui.upload;

        // 监听提交
        form.on('submit(comment)', function (data) {
            console.log("请求数据", data);
            $.ajax({
                url: '/commentArticles/front/add',
                type: 'post',
                data: data.field,
                async: false, dataType: "json", traditional: true,
                success: function (msg) {
                    layer.msg("你的意见是站长不懈的动力, 感谢你的反馈! ");
                    location.reload();
                }, error: function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.top.layer.msg('请求失败', {icon: 5,  area: ['120px', '80px'], anim: 2});
                }
            });
            return false;
        });

        form.render();

        // 文章点赞
        $("#satisfactoryClick").click(function () {
            $("#satisfactoryClick").fadeOut("slow");
            $("#dissatisfiedClick").fadeOut("slow");

            $.ajax({
                url: '/commentArticles/front/thumbs/${blog.id}/1',
                type: 'get',
                async: false, dataType: "json", traditional: true,
                success: function (msg) {
                    layer.msg("少侠好眼力!");
                }, error: function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.top.layer.msg('请求失败', {icon: 5,  area: ['120px', '80px'], anim: 2});
                }
            });
        });
        // 文章差评
        $("#dissatisfiedClick").click(function () {
            $("#satisfactoryClick").fadeOut("slow");
            $("#dissatisfiedClick").fadeOut("slow");
            $.ajax({
                url: '/commentArticles/front/thumbs/${blog.id}/0',
                type: 'get',
                async: false, dataType: "json", traditional: true,
                success: function (msg) {
                    layer.msg("你的意见是站长不懈的动力, 感谢你的反馈! ");
                }, error: function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.top.layer.msg('请求失败', {icon: 5,  area: ['120px', '80px'], anim: 2});
                }
            });
        });
    });
    // 评论点赞
    function satisfactoryCommentClick (commentId) {
        $.ajax({
            url: '/commentArticles/front/thumbs/commmet/'+commentId+'/1',
            type: 'get',
            async: false, dataType: "json", traditional: true,
            success: function (msg) {
                layer.msg("你的意见是站长不懈的动力, 感谢你的反馈! ");
                location.reload();
            }, error: function () {
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
                window.top.layer.msg('请求失败', {icon: 5,  area: ['120px', '80px'], anim: 2});
            }
        });
    }
    // 评论差评
    function dissatisfiedCommentClick (commentId) {
        $.ajax({
            url: '/commentArticles/front/thumbs/commmet/'+commentId+'/0',
            type: 'get',
            async: false, dataType: "json", traditional: true,
            success: function (msg) {
                layer.msg("你的意见是站长不懈的动力, 感谢你的反馈! ");
                location.reload();
            }, error: function () {
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
                window.top.layer.msg('请求失败', {icon: 5,  area: ['120px', '80px'], anim: 2});
            }
        });
    }
</script>
</body>
</html>