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
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<section class="container-module">
    <section class="search-module">
        评论人：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="author" autocomplete="off">
        </div>
        <%--搜索按钮--%>
        <button class="layui-btn layui-btn-radius layui-btn-normal" id="search" data-type="select">
            <i class="layui-icon"></i>
        </button>
    </section>

    <section class="functional-module">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="batchDelete">
                <i class="layui-icon">&#xe640;</i>
                删除评论
            </button>

            <%--列表刷新按钮--%>
            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="reload"  id="refresh" style="float: right;">
                <i class="layui-icon">ဂ</i>
            </button>
        </div>
    </section>


    <section class="table-module">
        <table id="commentArticlesList" lay-filter="commentArticlesListTool"></table>
    </section>
</section>
<%--头像处理模板--%>
<script type="text/html" id="imageTemplate">
    {{#  if(d.coverImage != null){ }}
    <a href="#"><img src="{{d.coverImage}}" style="width:30px;height:30px;border-radius:30px"></a>
    {{#  } else { }}
    <span></span>
    {{#  } }}
</script>

<%@include file="../../../../common/jsModule.jsp" %>
<%--列表操作模板--%>
<script type="text/html" id="toolTemplete">
    <a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-warm" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</a>
</script>
<script>
    // 回车提交表单
    document.onkeydown = function (e) {
        var theEvent = window.event || e;
        var code = theEvent.keyCode || theEvent.which;
        if (code == 13) {
            $('#search').click();
        }
    }
    layui.use(['table','layer','form'], function(){
        var table = layui.table;
        var layer=layui.layer;
        var form =layui.form;
        var $=layui.jquery;
        // 执行表格渲染
        table.render({
            id: 'commentArticlesList',
            elem: '#commentArticlesList',
            request: {
                pageName: 'pageIndex', //页码的参数名称，默认：page
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
            url: '/commentArticles/list',
            limits:[5,10,15,30,50],
            page: true,
            cols: [[ //表头
                 {type: 'checkbox'}
                ,{field: 'id', title: 'ID',width:"4%", sort: true}
                ,{field: 'coverImage',title: '头像',width:"5%", event: 'imageEvent', unresize: true ,templet:'#imageTemplate'}
                ,{field: 'articlesTitle', title: '文章', sort: true}
                ,{field: 'content', title: '评论', sort: true, style: 'background-color: #00c0ef; color: #fff'}
                ,{field: 'author', title: '评论人', sort: true}
                ,{field: 'email', title: '联系邮箱', sort: true}
                ,{field: 'authorLocationProvince', title: '所在省',width:"8%", sort: true}
                ,{field: 'authorLocationCity', title: '所在市',width:"8%",sort: true}
                ,{title:'操作', align:'center',toolbar: '#toolTemplete'}
            ]]
        });
        // 监听工具条
        table.on('tool(commentArticlesListTool)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if(layEvent === 'delete'){ //删除
                layer.confirm('确定要删除该【 ' + data.author + ' 】评论?', {icon: 3, title:'删除评论'}, function(index){
                    //do something
                    $.ajax({
                        url: "/commentArticles/" + data.id,
                        type: "delete",
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('commentArticlesList');
                        }
                    });
                    layer.close(index);
                });
            } else if(layEvent === 'edit'){ //编辑
                layer.open({
                    title : "修改评论",
                    type : 2,
                    shade: false,
                    zIndex: layer.zIndex,
                    maxmin:true,
                    skin:'layui-layer-lan',
                    shade: 0.4,
                    area:["60%","80%"],
                    content : 'loadUpdatePage?id=' + data.id,
                    success : function(layero, index){
                    }
                });
            }  else if(obj.event === 'imageEvent'){ // 图片展示
                layer.open({
                    type: 1,
                    title: false,
                    closeBtn: 0,
                    skin: 'layui-layer-nobg', //没有背景色
                    shadeClose: true,
                    content: '<div><img src="' + data.coverImage + '"><div>'
                });
            }
        });
        var active = {
            // 查询按钮
            select: function () {
                var author = $('#author').val();
                console.info(username);
                table.reload('commentArticlesList', {
                    where: {
                        author: author
                    }
                });
            },
            // 刷新按钮
            reload: function () {
                var author = $('#author').val();
                table.reload('commentArticlesList', {
                    where: {
                        author: author
                    }
                });
            },
            // 批量删除按钮
            batchDelete: function () {
                var checkStatus = table.checkStatus('commentArticlesList')
                var data = checkStatus.data;
                if (data.length == 0) {
                    layer.msg('至少要选择一条记录吧...');
                    return false;
                }
                var ids = "";
                for (var i = 0; i< data.length; i++) {
                    if (i == 0) {
                        ids = ids + data[i].id;
                    } else {
                        ids = ids + "," + data[i].id ;
                    }
                }
                layer.confirm('确定要删除这【'+ data.length +'】条评论吗?', {icon: 3, title:'批量删除评论'}, function(index){
                    $.ajax({
                        url: "/commentArticles/batchDelete",
                        type: "post",
                        dataType:"json",
                        data: {ids: ids},
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('commentArticlesList');
                        },
                        error: function () {
                            layer.alert("系统异常",{icon: 5});
                        },
                        complete: function () {
                            layer.close('loading');
                        }
                    });
                    layer.close(index);
                });
            }

        };
        // 监控带有layui-btn类的元素, 并触发相应的方法
        $('.layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
</script>

<style>


</style>
</html>
