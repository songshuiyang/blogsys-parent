<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../common/taglib.jsp" %>
<%@include file="../../../../common/cssModule.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<section class="container-module">
    <section class="search-module">
        用户名：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="username" autocomplete="off">
        </div>
        昵称：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="nickname" autocomplete="off">
        </div>
        <%--搜索按钮--%>
        <button class="layui-btn layui-btn-normal" data-type="select">
            <i class="layui-icon"></i>
        </button>



    </section>

    <section class="functional-module">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="add">
                <i class="layui-icon">&#xe608;</i>
                新增
            </button>

            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="update">
                <i class="layui-icon">&#xe642;</i>
                修改
            </button>


            <button class="layui-btn layui-btn-radius layui-btn-warm" data-type="batchDelete">
                批量删除用户
            </button>

            <%--列表刷新按钮--%>
            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="reload"  id="refresh" style="float: right;">
                <i class="layui-icon">ဂ</i>
            </button>
        </div>
    </section>

    <section class="table-module">
        <table id="userList" lay-filter="userListTool"></table>
    </section>
</section>

<%@include file="../../../../common/jsModule.jsp" %>
<%--列表操作模板--%>
<script type="text/html" id="toolTemplete">
    <a class="layui-btn layui-btn-radius layui-btn-xs" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</a>
    <a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-warm" lay-event="assignRole"><i class="layui-icon">&#xe642;</i>分配角色</a>
    <a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-normal" lay-event="detail"><i class="layui-icon">&#xe60b;</i>详情</a>
</script>


<%--头像处理模板--%>
<script type="text/html" id="imageTemplate">
    {{#  if(d.headPortrait != null){ }}
        <a href="#"><img src="{{d.headPortrait}}" style="width:30px;height:30px;border-radius:30px"></a>
    {{#  } else { }}
        <span></span>
    {{#  } }}
</script>

<script>
    // 回车提交表单
    document.onkeydown = function (e) {
        var theEvent = window.event || e;
        var code = theEvent.keyCode || theEvent.which;
        if (code == 13) {
            $('.layui-btn').click();
        }
    }
    layui.use(['table','layer','form'], function(){
        var table = layui.table;
        var layer=layui.layer;
        var form =layui.form;
        var $=layui.jquery;
        // 执行表格渲染
        table.render({
            id: 'userList',
            elem: '#userList',
            request: {
                pageName: 'pageIndex', //页码的参数名称，默认：page
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
//            height: 600,
//            width: 1657,
            url: '/user/list',
            limits:[5,10,15,30,50],
            page: true,
            cols: [[ //表头
                 {type: 'checkbox',width:"3%",}
                ,{field: 'id', title: 'ID',width:"4%", sort: true}
                ,{field: 'headPortrait',title: '头像',width:"5%", event: 'imageEvent', unresize: true ,templet:'#imageTemplate'}
                ,{field: 'username', title: '用户名',width:"7%", sort: true, style: 'background-color: #00c0ef; color: #fff'}
                ,{field: 'nickname', title: '昵称', width:"7%",sort: true}
                ,{field: 'sexAlias', title: '性别',width:"4%", sort: true}
                ,{field: 'phone', title: '手机', width:"7%",sort: true}
                ,{field: 'email', title: '邮件', width:"10%",sort: true}
                ,{field: 'createdDate', title: '创建时间', width:"10%",sort: true}
                ,{field: 'lastModifiedDate', title: '最后时间',width:"10%", sort: true}
                ,{field: 'remarks', title: '备注',width:"13%", sort: true}
                ,{field: 'enableAlias', title: '状态', width:"4%",sort: true}
                ,{title:'操作', align:'center',width:"15%", toolbar: '#toolTemplete'}
            ]]
        });
        //监听工具条
        table.on('tool(userListTool)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            if(layEvent === 'detail'){ //查看
                layer.msg('待开发');

            }else if(layEvent === 'edit'){ //编辑
                layer.open({
                    id: 'user-edit',
                    title : "修改用户",
                    type : 2,
                    shade: false,
                    zIndex: layer.zIndex, //重点1
                    maxmin:true,
                    area:["100%","100%"],
                    content : 'loadUserUpdatePage?id=' + data.id,
                    success : function(layero, index){
                        layer.setTop(layero); //重点2
                        setTimeout(function () {
                            layer.tips('这里是关闭窗口', '.layui-layer-setwin .layui-layer-close', {
                                tips: 3
                            });
                        }, 500)
                    }
                });
            } else if (layEvent == 'assignRole') {
                layer.open({
                    id: 'assign-role',
                    title : "分配角色",
                    type : 2,
                    shade: false,
                    zIndex: layer.zIndex,
                    maxmin:true,
                    skin:'layui-layer-lan',
                    shade: 0.4,
                    area:["60%","80%"],
                    content : 'loadAssginRolePage?id=' + data.id,
                    success : function(layero, index){

                    }
                });

            } else if(obj.event === 'imageEvent'){ // 图片展示
                layer.open({
                    type: 1,
                    title: false,
                    closeBtn: 0,
                    skin: 'layui-layer-nobg', //没有背景色
                    shadeClose: true,
                    content: '<div><img src="' + data.headPortrait + '"><div>'
                });
            }
        });

        var active = {
            // 查询按钮
            select: function () {
                var username = $('#username').val();
                var nickname = $('#nickname').val();
                console.info(username);
                table.reload('userList', {
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
                table.reload('userList', {
                    where: {
                        username: username,
                        nickname: nickname
                    }
                });
            },
            // 新增按钮
            add: function () {
                layer.open({
                    title : "添加用户",
                    id: 'user-add',
                    type : 2,
                    shade: false,
                    zIndex: layer.zIndex, //重点1
                    maxmin:true,
                    area:["100%","100%"],
                    content : "loadUserAddPage",
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
            // 修改按钮
            update: function () {
                var checkStatus = table.checkStatus('userList')
                    , data = checkStatus.data;
                if (data.length != 1) {
                    layer.msg('请选择一行编辑,已选['+data.length+']行', {icon: 5});
                    return false;
                }
                layer.open({
                    id: 'user-edit',
                    title : "修改用户",
                    type : 2,
                    shade: false,
                    zIndex: layer.zIndex, //重点1
                    maxmin:true,
                    area:["100%","100%"],
                    content : 'loadUserUpdatePage?id=' + data[0].id,
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
            // 删除按钮
            delete: function () {
                var checkStatus = table.checkStatus('userList')
                    , data = checkStatus.data;
                if (data.length != 1) {
                    layer.msg('请选择一行编辑,已选['+data.length+']行', {icon: 5});
                    return false;
                }
                layer.confirm('确定要删除该 ' + data[0].nickname + ' 用户?', {icon: 3, title:'删除用户'}, function(index){
                    //do something
                    $.ajax({
                        url: "/user/" + data[0].id,
                        type: "delete",
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('userList');
                        }
                    });
                    layer.close(index);
                });
            },
            // 批量删除按钮
            batchDelete: function () {
                var checkStatus = table.checkStatus('userList')
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
                    if (data[i].username == "admin") {
                        layer.alert('不能删除admin用户', {icon: 0});
                        return false;
                    }
                }
                layer.confirm('确定要删除这 ' + data.length + ' 个用户?', {icon: 3, title:'删除用户'}, function(index){
                    //do something
                    $.ajax({
                        url: "/user/batchDelete",
                        type: "post",
                        dataType:"json",
                        data: {ids: ids},
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('userList');
                        }
                    });
                    layer.close(index);
                });
            },

        };
        $('.layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });

</script>

<style>


</style>
</html>
