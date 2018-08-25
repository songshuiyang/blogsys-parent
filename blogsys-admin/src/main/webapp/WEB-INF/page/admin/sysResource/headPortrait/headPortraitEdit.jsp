<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../../common/taglib.jsp" %>
<head>
    <link href="${ctx}static/layui-module/layui/css/layui.css" rel="stylesheet" />
</head>
<html>
<form class="layui-form" style="margin-top:50px;">
    <input type="hidden" id="pkey" name="pkey" value="headPortrait">
    <input type="hidden" id="value" name="value" value="http://111.230.226.158/uploadfile/20180606/002203-QQ头像.jpg">
    <div class="layui-container">
        <div class="layui-row">
            <div class="layui-form-item">
                <label for="value" class="layui-form-label">
                    头像:
                </label>

                <div class="layui-col-md2">
                    <div class="layui-inline">
                        <div id="uploadImageDiv" style="margin-top: -10px;">
                            <img src="http://111.230.226.158/uploadfile/20180606/002203-QQ头像.jpg"
                                 style="border-radius:100px;" width="100px" height="100px"
                                 class="layui-upload-img layui-show">
                        </div>

                    </div>
                </div>
                <div class="layui-col-md3">
                    <button type="button" class="layui-btn layui-btn-radius layui-btn-normal"
                            id="uploadImage">
                        <i class="layui-icon">&#xe67c;</i>上传图片
                    </button>
                </div>
            </div>

            <div class="layui-col-md12">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label for="value1" class="layui-form-label">
                            文件名：
                        </label>
                        <div class="layui-input-block" style="width: 800px;">
                            <input class="layui-input" type="text" id="value1" name="value1" readonly
                                   lay-verify="required" placeholder="自动生成">
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-col-md12">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label for="value2" class="layui-form-label">
                            图片链接：
                        </label>
                        <div class="layui-input-block" style="width: 800px;">
                            <input class="layui-input" type="text" id="value2" name="value2" readonly
                                   lay-verify="required" placeholder="自动生成">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="layui-form-item" style="margin-left: 70px;">
        <button class="layui-btn layui-btn-radius layui-btn-normal" style="margin-left:60px;" lay-filter="comment"
                lay-submit="">
            <i class="layui-icon">&#xe618;</i>提交
        </button>
    </div>
</form>

<!-- layui.js -->
<script src="${ctx}static/layui-module/layui/layui.js"></script>
<script>
    layui.use(['form','layer', 'upload','element'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var layer = layui.layer;
        var upload = layui.upload;
        var element = layui.element;
        var loadIndex; // 加载效果
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
                    $('#value').val(res.url)
                    $('#value1').val(res.title)
                    $('#value2').val(res.url)
                    $('#uploadImageDiv').find('img').remove();
                    $('#uploadImageDiv').append('<img src="' + res.url + '"  style="border-radius:100px;" width="100px" height="100px" class="layui-upload-img layui-show">');

                }
            }
        });

        // 监听提交
        form.on('submit(comment)', function (data) {
            console.log("请求数据", data);
            $.ajax({
                url: '/sysResouce/add',
                type: 'post',
                data: data.field,
                async: false, dataType: "json", traditional: true,
                success: function (msg) {
                    layer.msg("新增成功");
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.parent.layui.table.reload('headPortraitList');

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
</html>
