<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../../common/taglib.jsp" %>
<%@include file="../../../../../common/cssModule.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>用户管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="${ctx}static/plugins/ueditor/lang/zh-cn/zh-cn.js" media="all" />
</head>
<style>
    .ueditorContainer{
        margin-left: 110px;
        width: 1200px;
        height: 800px;
    }
</style>

<body>
<div>
    <%--在一个容器中设定 class="layui-form" 来标识一个表单元素块--%>
    <form class="layui-form" style="margin-left: 20px;margin-top: 20px;">
        <input type="hidden" id="id" name="id" />
        <input type="hidden" id="coverImage" name="coverImage" />
        <input type="hidden" id="content" name="content"/>
        <input type="hidden" id="status" name="status" value="0"/>
        <div style="width:100%;overflow: auto;">
            <%--行区块结构--%>
            <div class="layui-form-item">
                <label for="title" class="layui-form-label">
                    <span class="x-red">*</span>封面图片
                </label>
                <div class="layui-input-block" style="width: 600px;">
                    <div class="layui-inline">
                        <div id="uploadImageDiv" style="margin-top: -10px;">
                            <img src="${ctx}static/layui-module/x-admin/images/bg.png" width="150px" height="150px"
                                 class="layui-upload-img layui-show">
                        </div>
                    </div>

                    <div class="layui-inline">
                        <div class="layui-upload-drag" style="height: 80px;width: 350px;" id="uploadImage">
                            <i style="font-size:30px;" class="layui-icon"></i>
                            <p style="font-size: 10px">点击上传，或将文件拖拽到此处</p>
                        </div>
                    </div>

                </div>
            </div>

            <div class="layui-form-item">
                <label for="title" class="layui-form-label">
                    <span class="x-red">*</span>标题
                </label>
                <div class="layui-input-block" style="width: 600px;">
                    <input type="text" id="title" name="title"  required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label for="author" class="layui-form-label">
                    <span class="x-red">*</span>博客作者
                </label>
                <div class="layui-input-block" style="width: 600px;">
                    <input type="text" id="author" name="author" placeholder="" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="outline" class="layui-form-label">
                        <span class="x-red">*</span>概要
                    </label>
                    <div class="layui-input-block" style="width: 600px;">
                        <textarea id="outline" name="outline" placeholder="请输入内容" class="layui-textarea"></textarea>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="category" class="layui-form-label">
                        <span class="x-red">*</span>分类目录
                    </label>
                    <div class="layui-input-block" style="width: 600px;">
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
                    <label for="articlesType" class="layui-form-label">
                        <span class="x-red">*</span>文章类型
                    </label>
                    <div class="layui-input-block" style="width: 600px;">
                        <select id="articlesType" name="type" lay-filter="selFilter" lay-search>
                            <option value=""></option>
                            <option value="0">转载</option>
                            <option value="1">原创</option>
                            <option value="2">精贴</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="tag" class="layui-form-label">
                        <span class="x-red">*</span>标签
                    </label>
                    <div class="layui-input-block">
                        <c:forEach items="${existTagList}" var="existTag" varStatus="j">
                            <input type="checkbox" id="tag" name="tag" value="${existTag.id}" title="${existTag.name}" <c:if test="${isUpdate == true}">checked</c:if>/>
                        </c:forEach>
                        <c:forEach items="${notExistTagList}" var="notExistTag" varStatus="k">
                            <input type="checkbox" id="tag" name="tag" value="${notExistTag.id}" title="${notExistTag.name}"/>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <%--采用隐藏文本框进行存值--%>
                <label class="layui-form-label">是否发布</label>
                <div class="layui-input-block">
                    <input type="checkbox" id="publish" lay-filter="switch" lay-skin="switch" lay-text="发布|不发">
                </div>
            </div>

            <div class="layui-form-item">
                <div>
                    <label for="remarks" class="layui-form-label">
                        <span class="x-red"></span>备注
                    </label>
                    <div class="layui-input-block" style="width: 600px;">
                        <textarea id="remarks" name="remarks" placeholder="请输入内容" class="layui-textarea"></textarea>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div>
                    <label for="remarks" class="layui-form-label">
                        <span class="x-red"></span>内容
                    </label>
                    <div class="ueditorContainer" >
                        <!-- 加载编辑器的容器 -->
                        <script id="ueditorContainer" style="height: 400px;" name="content" type="text/plain"></script>
                    </div>
                </div>
            </div>



            <div style="width: 100%;height: 55px;background-color: white;position: fixed;bottom: 1px;margin-left:-20px;">
                <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">

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
<%@include file="../../../../../common/jsModule.jsp" %>
<!-- ueditor 配置文件 -->
<script type="text/javascript" src="${ctx}static/plugins/ueditor/ueditor.config.js?v=${version}"></script>
<!-- ueditor 编辑器源码文件 -->
<script type="text/javascript" src="${ctx}static/plugins/ueditor/ueditor.all.js?v=${version}"></script>
<!-- 实例化编辑器 -->
<script type="text/javascript">
    // 当action是如下时，访问自己定义的服务接口
    UE.Editor.prototype._bkGetActionUrl=UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl=function(action){
        // 上传图片, 文件, 视频
        if (action == 'uploadimage' || action == 'uploadfile'  || action == 'uploadvideo') {
            return '/file/uploadLocal';
        }  else if( action== 'uploadscrawl'){ // 上传涂鸦
            return '/file/uploadScrawl';
        }   else if(action == 'listimage'){
            return this._bkGetActionUrl.call(this, action);
        } else{
            return this._bkGetActionUrl.call(this, action);
        }
    }
    var ueditor = UE.getEditor('ueditorContainer');
</script>



<script>
    // 页面初始化操作
    $(function () {
        // 编辑修改页面赋值
        if ("${isUpdate}" === "true") {
            $.ajax({
                url: '/blogArticles/${id}',
                async: false,
                type: 'get',
                success: function (data) {
                    $('#id').val(data.id)
                    $('#title').val(data.title)
                    $('#author').val(data.author)
                    $('#outline').val(data.outline)
                    $('#status').val(data.status)
                    $('#publish').val(data.status)
                    layui.use(['form'],function () {
                        var form = layui.form
                        if (data.status == 1) {
                            $("#publish").prop("checked",true);
                            form.render('checkbox');
                        }

                    })

                    $('#remarks').val(data.remarks)
                    $('#coverImage').val(data.coverImage)
                    $('#uploadImageDiv').find('img').remove();
                    $('#uploadImageDiv').append('<img src="' + data.coverImage + '"  width="100px" height="100px" class="layui-upload-img layui-show">');

                    // 下拉框赋值
                    layui.use(['form'],function () {
                        var form = layui.form
                        $("select[name=type]").val(data.type);
                        $("select[name=category]").val(data.category);
                        form.render();
                    })
                    // 富文本框赋值
                    var content = data.content
                    $('#content').val(content)
                    // 等UEditor创建完成就使用UEditor的setContent函数
                    ueditor.ready(function() {
                        ueditor.setContent(content);
                    });
                }
            });
        }
    });
    layui.use(['form', 'layer', 'upload'], function () {
        $ = layui.jquery;
        var form = layui.form
            , layer = layui.layer,
            upload = layui.upload;
        // 封面上传
        upload.render({
            elem: '#uploadImage'
            , url: '/file/uploadLocal'
            , method: 'post'
            , field: 'upfile'
            , before: function (obj) {

            }, done: function (res) {
                console.log(res)
                if (res.state != 'SUCCESS') {
                    layer.msg(res.content, {icon: 5, anim: 6});
                } else {
                    console.log("执行")
                    $('#coverImage').val(res.url)
                    $('#uploadImageDiv').find('img').remove();
                    $('#uploadImageDiv').append('<img src="' + res.url + '"  width="100px" height="100px" class="layui-upload-img layui-show">');

                }
            }
        });
        // 关闭按钮
        $('#close').click(function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });
        // 监听switch
        form.on('switch(switch)', function(data){
            // 选中改变隐藏域值
            if (data.elem.checked) {
                $('#status').val(1);
            } else {
                $('#status').val(0);
            }
        });

        // 监听提交
        form.on('submit(add)', function (data) {
            // ueditor赋值
            $('#content').val(ueditor.getContent());
            // 标签处理
            var tagDoc = document.getElementsByName("tag");
            var tagList = [];
            for (var i = 0; i < tagDoc.length; i++) {
                if (tagDoc[i].checked) {
                    console.info(tagDoc[i].value);
                    tagList.push(tagDoc[i].value);
                }
            }
            data.field.tag = null
            data.field.tag = tagList;
            console.info(data.field.tag);
            console.log("请求数据", data);
            $.ajax({
                url: '/blogArticles/add',
                type: 'post',
                data: data.field,
                async: false, dataType: "json", traditional: true,
                success: function (msg) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.parent.layui.table.reload('blogAritclesList');
                    window.top.layer.msg(msg, {icon: 6, area: ['120px', '80px'], anim: 2});
                }, error: function (res) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.top.layer.msg(res.content, {icon: 5, area: ['120px', '80px'], anim: 2});
                }
            });
            return false;
        });
        form.render();
    });
</script>
</body>

</html>
