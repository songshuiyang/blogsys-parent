<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../common/taglib.jsp" %>
<%@include file="../../../../common/cssModule.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>文章评论</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="${ctx}static/plugins/ueditor/lang/zh-cn/zh-cn.js" media="all" />
</head>
<body>
<div>
    <%--在一个容器中设定 class="layui-form" 来标识一个表单元素块--%>
    <form class="layui-form" style="margin-left: 20px;margin-top: 20px;">
        <div style="width:100%;overflow: auto;">
            <input type="hidden" id="id" name="id">

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="author" class="layui-form-label">
                        评论人
                    </label>
                    <div class="layui-input-block" style="width: 600px;">
                        <input type="text" id="author" name="author" placeholder=""  class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="email" class="layui-form-label">
                        邮件
                    </label>
                    <div class="layui-input-block" style="width: 600px;">
                        <input type="text" id="email" name="email" placeholder=""  class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="authorLocationProvince" class="layui-form-label">
                        省
                    </label>
                    <div class="layui-input-block" style="width: 600px;">
                        <input type="text" id="authorLocationProvince" name="authorLocationProvince" placeholder=""  class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="authorLocationCity" class="layui-form-label">
                        市
                    </label>
                    <div class="layui-input-block" style="width: 600px;">
                        <input type="text" id="authorLocationCity" name="authorLocationCity" placeholder=""  class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="satisfactoryNum" class="layui-form-label">
                        赞同数
                    </label>
                    <div class="layui-input-block" style="width: 600px;">
                        <input type="text" id="satisfactoryNum" name="satisfactoryNum" placeholder=""  class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="dissatisfiedNum" class="layui-form-label">
                        不赞同数
                    </label>
                    <div class="layui-input-block" style="width: 600px;">
                        <input type="text" id="dissatisfiedNum" name="dissatisfiedNum" placeholder=""  class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="content" class="layui-form-label">
                        评论内容
                    </label>
                    <div class="layui-input-block" style="width: 600px;">
                        <textarea id="content" name="content" placeholder="" class="layui-textarea"></textarea>
                    </div>
                </div>
            </div>


            <div class="layui-form-item">
                <div class="layui-inline">
                    <button class="layui-btn layui-btn-normal" lay-filter="add" lay-submit="">
                        确定
                    </button>
                    <button class="layui-btn layui-btn-primary" id="close">
                        取消
                    </button>
                </div>
            </div>
        </div>
    </form>

</div>
<%@include file="../../../../common/jsModule.jsp" %>

<script>
    layui.use(['form', 'layer', 'upload'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var layer = layui.layer;
        var upload = layui.upload;

        // 页面初始化操作
        $(function () {
            $("#id").val("${comment.id}");
            $("#articlesTitle").val("${comment.articlesTitle}");
            $("#author").val("${comment.author}");
            $("#email").val("${comment.email}");
            $("#authorLocationProvince").val("${comment.authorLocationProvince}");
            $("#authorLocationCity").val("${comment.authorLocationCity}");
            $("#satisfactoryNum").val("${comment.satisfactoryNum}");
            $("#dissatisfiedNum").val("${comment.dissatisfiedNum}");
            $("#content").val("${comment.content}");
        });

        // 关闭按钮
        $('#close').click(function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });
        // 监听提交
        form.on('submit(add)', function (data) {
            $.ajax({
                url: '/commentArticles/',
                type: 'post',
                data: data.field,
                async: false, dataType: "json", traditional: true,
                success: function (msg) {
                    layer.msg("操作成功");
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.parent.location.reload();
                    window.top.layer.msg("操作成功", {icon: 6, area: ['120px', '80px'], anim: 2});
                }, error: function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.layer.close(index);
                    window.parent.location.reload();
                    window.top.layer.msg("操作失败", {icon: 6, area: ['120px', '80px'], anim: 2});
                }
            });
            return false;
        });
        form.render();
    });
</script>
</body>

</html>
