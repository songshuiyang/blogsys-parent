<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../common/taglib.jsp" %>
<%@include file="../../../../common/cssModule.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>博客文章管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<style>
    .table-module .layui-icon {
        margin-top: 6px;
    }
</style>
<section class="container-module">
    <section class="search-module">
        标题：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="title" autocomplete="off">
        </div>
        作者：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="author" autocomplete="off">
        </div>
        类型：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="category" autocomplete="off">
        </div>
        标签：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="tag" autocomplete="off">
        </div>
        <%--搜索按钮--%>
        <button class="layui-btn layui-btn-radius layui-btn-normal" id="search" data-type="select">
            <i class="layui-icon"></i>
        </button>



    </section>

    <section class="functional-module">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="add">
                <i class="layui-icon">&#xe608;</i>
                新增博客
            </button>

            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="batchDelete">
                <i class="layui-icon">&#xe640;</i>
                批量删除博客
            </button>

            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="batchPublish">
                <i class="layui-icon">&#xe609;</i>
                批量发布博客
            </button>

            <%--列表刷新按钮--%>
            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="reload"  id="refresh" style="float: right;">
                <i class="layui-icon">ဂ</i>
            </button>
        </div>




    </section>

    <section class="table-module">
        <table id="blogAritclesList" lay-filter="blogAritclesListTool"></table>
    </section>
</section>

<%@include file="../../../../common/jsModule.jsp" %>

<%--封面图片处理模板--%>
<script type="text/html" id="imageTemplate">
    {{#  if(d.coverImage != null){ }}
    <a href="#"><img src="{{d.coverImage}}" style="width:30px;height:30px;"></a>
    {{#  } else { }}
    <span></span>
    {{#  } }}
</script>
<%--列表操作模板--%>
<script type="text/html" id="toolTemplete">
    <a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-warm" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</a>
    {{#  if(d.status == 0){ }}
    <a class="layui-btn layui-btn-radius layui-btn-xs" lay-event="release"><i class="layui-icon">&#xe609;</i>发布</a>
    {{#  } else { }}
    <a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-primary" lay-event="notRelease"><i class="layui-icon">&#xe857;</i>卸货</a>
    {{# } }}
    <a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-normal" lay-event="detail"><i class="layui-icon">&#xe60b;</i>详情</a>
    {{#  if(d.enable == 1){ }}
    <a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger " lay-event="delete"><i class="layui-icon">&#xe640;</i>删除</a>
    {{#  } }}
</script>
<%--标签tag模板--%>
<script type="text/html" id="tagTemplate">
    {{#  layui.each(d.blogTagList, function(index, item){ }}
        <a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-primary" lay-event="tag"><i class="iconfont icon-biaoqian"></i>{{ item.name }}</a>

    {{#  }); }}
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
            id: 'blogAritclesList',
            elem: '#blogAritclesList',
            request: {
                pageName: 'pageIndex', //页码的参数名称，默认：page
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
//            height: 600,
//            width: 1657,
            url: '/blogArticles/list',
            limits:[5,10,15,30,50],
            page: true,
            cols: [[ //表头
                 {type: 'checkbox',width:"3%",}
                ,{field: 'id', title: 'ID',width:"4%", sort: true}
                ,{field: 'coverImage',title: '封面图',width:"5%", event: 'imageEvent', unresize: true ,templet:'#imageTemplate'}
                ,{field: 'title', title: '标题',width:"10%", sort: true, style: 'background-color: #009688; color: #fff'}
                ,{field: 'outline', title: '简介', width:"10%",sort: true}
                ,{field: 'author', title: '作者',width:"6%", sort: true}
                ,{field: 'category', title: '分类目录', width:"6%",sort: true}
                ,{field: 'typeAlias', title: '类型', width:"6%",sort: true}
                ,{field: 'tag', title: '标签', width:"7%",sort: true,templet:'#tagTemplate'}
                ,{field: 'createdDate', title: '创建时间', width:"10%",sort: true}
                ,{field: 'statusAlias', title: '状态', width:"6%",sort: true}
                ,{title:'操作', align:'center',width:"21%", toolbar: '#toolTemplete'}
            ]]
        });
        // 监听工具条
        table.on('tool(blogAritclesListTool)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            if(layEvent === 'detail'){ //查看
                layer.open({
                    title : "博客详情",
                    type : 2,
                    shade: false,
                    zIndex: layer.zIndex,
                    maxmin:true,
                    area:["100%","100%"],
                    content : 'loadDetailPage?id=' + data.id,
                    success : function(layero, index){
                        layer.setTop(layero);
                        setTimeout(function () {
                            layer.tips('这里是关闭窗口', '.layui-layer-setwin .layui-layer-close', {
                                tips: 3
                            });
                        }, 500)
                    }
                });
            } else if(layEvent === 'delete'){ //删除
                layer.confirm('确定要删除该【 ' + data.title + ' 】博客?', {icon: 3, title:'删除博客'}, function(index){
                    //do something
                    $.ajax({
                        url: "/blogArticles/" + data.id,
                        type: "delete",
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('blogAritclesList');
                        }
                    });
                    layer.close(index);
                });
            } else if(layEvent === 'edit'){ //编辑
                layer.open({
                    title : "修改博客",
                    type : 2,
                    shade: false,
                    zIndex: layer.zIndex,
                    maxmin:true,
                    area:["100%","100%"],
                    content : 'loadUpdatePage?id=' + data.id,
                    success : function(layero, index){
                        layer.setTop(layero);
                        setTimeout(function () {
                            layer.tips('这里是关闭窗口', '.layui-layer-setwin .layui-layer-close', {
                                tips: 3
                            });
                        }, 500)
                    }
                });
            } else if(layEvent === 'release') { // 发布
                layer.confirm('确定要发布该篇【 ' + data.title + ' 】博客?', {icon: 3, title:'发布博客'}, function(index){
                    //do something
                    $.ajax({
                        url: "/blogArticles/" + data.id + "/1",
                        type: "put",
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('blogAritclesList');
                        }
                    });
                    layer.close(index);
                });
            } else if(layEvent === 'notRelease') { // 卸货
                layer.confirm('确定要卸载该篇【 ' + data.title + ' 】博客?', {icon: 3, title:'卸载博客'}, function(index){
                    //do something
                    $.ajax({
                        url: "/blogArticles/" + data.id + "/0" ,
                        type: "put",
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('blogAritclesList');
                        }
                    });
                    layer.close(index);
                });
            } else if(obj.event === 'imageEvent'){ // 图片展示
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
                var username = $('#username').val();
                var nickname = $('#nickname').val();
                console.info(username);
                table.reload('blogAritclesList', {
                    where: {
                        username: username,
                        nickname: nickname
                    }
                });
            },
            // 刷新按钮
            reload: function () {
                var username = $('#username').val();
                var nickname = $('#nickname').val();
                table.reload('blogAritclesList', {
                    where: {
                        username: username,
                        nickname: nickname
                    }
                });
            },
            // 新增按钮
            add: function () {
                layer.open({
                    title : "添加博客",
                    type : 2,
                    shade: false,
                    zIndex: layer.zIndex, //重点1
                    maxmin:true,
                    area:["100%","100%"],
                    content : "loadAddPage",
                    success : function(layero, index){
                        layer.setTop(layero); //重点2
                        setTimeout(function () {
                            layer.tips('这里是关闭窗口', '.layui-layer-setwin .layui-layer-close', {
                                tips: 3
                            });
                        }, 500)
                    }
                });
            },
            // 批量删除按钮
            batchDelete: function () {
                var checkStatus = table.checkStatus('blogAritclesList')
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
                layer.confirm('确定要删除这【'+ data.length +'】条博客吗?', {icon: 3, title:'批量删除博客'}, function(index){
                    $.ajax({
                        url: "/blogArticles/batchDelete",
                        type: "post",
                        dataType:"json",
                        data: {ids: ids},
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('blogAritclesList');
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
            },
            // 批量发布按钮
            batchPublish: function () {
                var checkStatus = table.checkStatus('blogAritclesList')
                var data = checkStatus.data;
                if (data.length == 0) {
                    layer.msg('至少要选择一条吧...');
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
                layer.confirm('确定要发布这【'+ data.length +'】条博客吗?', {icon: 3, title:'批量发布博客'}, function(index){
                    $.ajax({
                        url: "/blogArticles/batchPublish",
                        type: "post",
                        data: {ids: ids},
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('blogAritclesList');
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
