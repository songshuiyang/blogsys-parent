<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../common/taglib.jsp" %>
<%@include file="../../../../common/cssModule.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>图片管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
</head>
<body>
<div>
    <%--在一个容器中设定 class="layui-form" 来标识一个表单元素块--%>
    <form class="layui-form" style="margin-left: 20px;margin-top: 20px;">
        <div style="width:100%;overflow: auto;">
            <input type="hidden" id="id" name="id">
            <div class="layui-form-item">

                <div class="layui-col-md12">
                    <label for="url" class="layui-form-label">
                        图片:
                    </label>
                    <div class="layui-inline">
                        <div id="uploadImageDiv" style="margin-top: -10px;overflow: scroll">
                            <img src="" id="image" class="layui-upload-img layui-show">
                        </div>
                    </div>
                </div>

                <div class="layui-col-md5">
                    <button type="button" class="layui-btn layui-btn-radius layui-btn-normal"
                            id="uploadImage">
                        <i class="layui-icon">&#xe67c;</i>上传图片
                    </button>
                </div>


            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="title" class="layui-form-label">
                        图片标题
                    </label>
                    <div class="layui-input-block" style="width: 800px;">
                        <input type="text" id="title" name="title" lay-verify="required" placeholder="" value="" class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="outline" class="layui-form-label">
                        图片简介
                    </label>
                    <div class="layui-input-block" style="width: 800px;">
                        <textarea id="outline" name="outline" placeholder="" class="layui-textarea"></textarea>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="url" class="layui-form-label">
                        图片链接
                    </label>
                    <div class="layui-input-block" style="width: 800px;">
                        <input type="text" id="url" name="url" required lay-verify="required" placeholder="" value="" class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="category" class="layui-form-label">
                        相册
                    </label>
                    <div class="layui-input-block" style="width: 800px;">
                        <select id="category" name="category" lay-filter="selFilter" lay-search>
                            <option value=""></option>
                            <c:forEach items="${categoryList}" var="category" varStatus="k">
                                <option value="${category.value}">${category.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="type" class="layui-form-label">
                        图片类型
                    </label>
                    <div class="layui-input-block" style="width: 800px;">
                        <select id="type" name="type" lay-filter="selFilter" lay-search>
                            <option value=""></option>
                            <option value="0">精图</option>
                            <option value="1">原创</option>
                            <option value="2">转载</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="orderNum" class="layui-form-label">
                        排序号
                    </label>
                    <div class="layui-input-block" style="width: 800px;">
                        <input type="text" id="orderNum" name="orderNum" placeholder=""  class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="shootingTime" class="layui-form-label">
                        拍摄时间
                    </label>
                    <div class="layui-input-block" style="width: 800px;">
                        <input type="text" class="layui-input" id="shootingTime" name="shootingTime">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="remarks" class="layui-form-label">
                        <span class="x-red"></span>备注
                    </label>
                    <div class="layui-input-block" style="width: 800px;">
                        <textarea id="remarks" name="remarks" placeholder="请输入内容" class="layui-textarea"></textarea>
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
    layui.use(['form', 'layer','laydate', 'upload'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var layer = layui.layer;
        var upload = layui.upload;
        var laydate = layui.laydate;

        // 执行一个laydate实例
        laydate.render({
            elem: '#shootingTime', //指定元素
            type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss' //可任意组合
        });

        // 封面上传
        upload.render({
            elem: '#uploadImage'
            , url: '/file/uploadLocal'
            , method: 'post'
            , field: 'upfile'
            , before: function (obj) {
                loadIndex  = layer.load(0, {shade: [0.1,'#000']});
            }, done: function (res) {
                layer.close(loadIndex);
                if (res.state != 'SUCCESS') {
                    layer.msg(res.content, {icon: 5, anim: 6});
                } else {
                    $('#url').val(res.url)
                    $('#uploadImageDiv').find('img').remove();
                    $('#uploadImageDiv').append('<img src="' + res.url + '" class="layui-upload-img layui-show">');

                }
            }
        });

        // 页面初始化操作
        $(function () {
            $("#id").val("${record.id}");
            $("#title").val("${record.title}");
            $("#outline").val("${record.outline}");
            $("#url").val("${record.url}");

            $('#image').attr("src","${record.url}");

            $("#orderNum").val("${record.orderNum}");
            $("#shootingTime").val("${record.shootingTimeStr}");


            // 下拉框赋值
            layui.use(['form'],function () {
                var form = layui.form;
                $("select[name=type]").val("${record.type}");
                $("select[name=category]").val("${record.category}");
                form.render();
            })

            $("#remarks").val("${record.remarks}");
        });


        // 关闭按钮
        $('#close').click(function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });
        // 监听提交
        form.on('submit(add)', function (data) {
            $.ajax({
                url: '/photoAlbum/add',
                type: 'post',
                data: data.field,
                async: false, dataType: "json", traditional: true,
                success: function (msg) {
                    layer.msg("新增成功");
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.parent.layui.table.reload('photoAlbumList');
                    window.top.layer.msg(msg, {icon: 6,  area: ['120px', '80px'], anim: 2});
                }, error: function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.top.layer.msg('请求失败', {icon: 5,  area: ['120px', '80px'], anim: 2});
                }
            });
            return false;
        });
        form.render();
    });
</script>
</body>

</html>
