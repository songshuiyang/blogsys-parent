<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../../common/taglib.jsp" %>
<%@include file="../../../../../common/cssModule.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>博客详情页面</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
</head>
<style>

</style>

<body>
<div>
    <div class="layui-row layui-col-space5">
        <div class="layui-col-md12">
            <div class="layui-row grid-demo">
                <div class="layui-col-md4" style="height: 200px;">
                    <form class="layui-form" style="margin-left: 20px;margin-top: 20px;">
                        <div style="width:100%;overflow: auto;">
                            <%--行区块结构--%>
                            <div class="layui-form-item">
                                <label for="title" class="layui-form-label">
                                    封面图片：
                                </label>
                                <div class="layui-input-block" style="width: 600px;">
                                    <div class="layui-inline">
                                        <div id="uploadImageDiv" style="margin-top: -10px;">
                                            <img src="${ctx}static/layui-module/x-admin/images/bg.png" width="150px" height="150px"
                                                 class="layui-upload-img layui-show">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label for="title" class="layui-form-label">
                                        标题：
                                    </label>
                                    <div class="layui-input-block" style="width: 600px;">
                                        <input type="text" id="title" style="border:0;" name="title"  class="layui-input">
                                    </div>
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label for="author" class="layui-form-label">
                                        作者：
                                    </label>
                                    <div class="layui-input-block" style="width: 600px;">
                                        <input type="text" id="author" name="author" style="border:0;" class="layui-input">
                                    </div>
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label for="outline" class="layui-form-label">
                                        概要：
                                    </label>
                                    <div class="layui-input-block" style="width: 600px;">
                                        <textarea id="outline" name="outline" style="border:0;" class="layui-textarea"></textarea>
                                    </div>
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label for="category" class="layui-form-label">
                                        分类目录：
                                    </label>
                                    <div class="layui-input-block" style="width: 600px;">
                                        <input type="text" id="category" name="category" style="border:0;"  class="layui-input">
                                    </div>
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label for="type" class="layui-form-label">
                                        文章类型：
                                    </label>
                                    <div class="layui-input-block" style="width: 600px;">
                                        <input type="text" id="type" name="type" style="border:0;"  class="layui-input">
                                    </div>
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label for="tag" class="layui-form-label">
                                        标签：
                                    </label>
                                    <div class="layui-input-block" style="width: 600px;">
                                        <input type="text" id="tag" name="tag" style="border:0;"  class="layui-input">
                                    </div>
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label for="status" class="layui-form-label">
                                        是否发布：
                                    </label>
                                    <div class="layui-input-block" style="width: 600px;">
                                        <input type="text" id="status" name="status" style="border:0;" class="layui-input">
                                    </div>
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label for="remarks" class="layui-form-label">
                                        备注：
                                    </label>
                                    <div class="layui-input-block" style="width: 600px;">
                                        <textarea id="remarks" name="remarks" style="border:0;" class="layui-textarea"></textarea>
                                    </div>
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label for="enable" class="layui-form-label">
                                        是否删除：
                                    </label>
                                    <div class="layui-input-block" style="width: 600px;">
                                        <textarea id="enable" name="enable" style="border:0;" class="layui-textarea"></textarea>
                                    </div>
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label for="createdDate" class="layui-form-label">
                                        创建时间：
                                    </label>
                                    <div class="layui-input-block" style="width: 600px;">
                                        <input type="text" id="createdDate" name="createdDate" style="border:0;" class="layui-input">
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label for="lastModifiedDate" class="layui-form-label">
                                        最后修改时间：
                                    </label>
                                    <div class="layui-input-block" style="width: 600px;">
                                        <input type="text" id="lastModifiedDate" name="lastModifiedDate" style="border:0;" class="layui-input">
                                    </div>
                                </div>
                            </div>



                        </div>
                    </form>
                </div>
                <div class="layui-col-md8" style="width: 1000px;">
                    内容：
                    <div id="content" style="width: 990px;overflow: auto;border: 2px solid #00b7ee;"></div>
                </div>
            </div>
        </div>
    </div>


</div>
<%@include file="../../../../../common/jsModule.jsp" %>


<script>
    var flag, msg;
    $(function () {
        $.ajax({
            url: '/blogArticles/${id}',
            async: false,
            type: 'get',
            success: function (data) {

                $('#id').val(data.id)
                $('#title').val(data.title)
                $('#outline').val(data.outline)

                $('#tag').val(data.tag)
                $('#status').val(data.statusAlias)

                $('#remarks').val(data.remarks)
                $('#coverImage').val(data.coverImage)
                $('#uploadImageDiv').find('img').remove();
                $('#uploadImageDiv').append('<img src="' + data.coverImage + '"  width="100px" height="100px" class="layui-upload-img layui-show">');

                $('#category').val(data.category)
                $('#type').val(data.typeAlias)
                $('#content').html(data.content)
                $('#enable').val(data.enableAlias)

                $('#author').val(data.author)
                $('#createdDate').val(data.createdDate)
                $('#lastModifiedDate').val(data.lastModifiedDate)


            }
        });
    });
</script>
</body>

</html>
