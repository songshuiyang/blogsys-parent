<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../common/taglib.jsp" %>
<%@include file="../../../../common/cssModule.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>菜单管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<section class="container-module">
    <section class="search-module">
        一级菜单：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="menuName" autocomplete="off">
        </div>
        二级菜单：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="menuCode" autocomplete="off">
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
                新增菜单
            </button>

            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="batchDelete">
                <i class="layui-icon">&#xe640;</i>
                删除菜单
            </button>

            <%--列表刷新按钮--%>
            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="reload"  id="refresh" style="float: right;">
                <i class="layui-icon">ဂ</i>
            </button>
        </div>
    </section>


    <%--<section class="table-module">--%>
        <%--<table id="menuList" lay-filter="menuListTool"></table>--%>
    <%--</section>--%>
    <section class="table-module">
        <%--<table id="permissionList" lay-filter="permissionListTool"></table>--%>
        <div id="menuList"></div>
    </section>
</section>

<%@include file="../../../../common/jsModule.jsp" %>
<%--图标模板--%>
<script type="text/html" id="iconTemplate">
    <i class="{{ d.icon }}"></i>
</script>

<%--列表操作模板--%>
<script type="text/html" id="toolTemplete">
    <a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-warm" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</a>
</script>

<script type="text/html" id="statusTemplate">
    <input type="checkbox" name="status" value="{{d.id}}"  lay-skin="switch" lay-text="是|否" lay-filter="status" {{ d.status == 0 ? 'checked' : '' }}>

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
    var layout = [
        { name: '菜单名称', treeNodes: true, headerClass: 'value_col',align:'center', colClass: 'value_col', style: 'width: 10%'
        },
        { name: 'url',headerClass: 'value_col', colClass: 'value_col',align:'center',  style: 'width: 10%',
            render: function(row) {
                return '<div class="layui-table-cell laytable-cell-1-username">'+(typeof(row.url)=="undefined"?'':row.url)+'</div>'; //列渲染
            }
        },
        { name: '图标',align:'center', headerClass: 'value_col', colClass: 'value_col', style: 'width: 5%',
            render: function(row) {
                return '<div class="layui-table-cell laytable-cell-1-username"><i class="'+row.icon+'"></i></div>'; //列渲染
            }
        },{ name: '排序号',align:'center', headerClass: 'value_col', colClass: 'value_col', style: 'width: 5%',
            render: function(row) {
                if(row.orderNum==null){
                    return "";
                }else{
                    return row.orderNum;
                }

            }
        },
        {
            name: '操作',
            headerClass: 'value_col',
            colClass: 'value_col',
            style: 'width: 20%',
            render: function(row) {
                var chil_len=row.children.length;
                var str='<a class="layui-btn layui-btn-radius layui-btn-xs" onclick="updateRow(\'' + row.id + '\')"><i class="layui-icon">&#xe642;</i>编辑</a>'; //列渲染
                if(chil_len==0){
                    str+='<a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger" onclick="deleteRow(\'' + row.id + '\')"><i class="fa fa-remove""></i> 删除</a>';
                }
                return str;
            }
        },
    ];
    var layer;
    var $;
    layui.use(['layer','table','form','tree','jquery'], function(){
        layer=layui.layer;
        $=layui.jquery;
        var table = layui.table;
        var form =layui.form;
        // 执行表格渲染

        var nodes;
        layui.jquery.ajax({
            url:"/menu/getMenuTreeGrid",
            type:"get",
            success:function(res){
                nodes = res;
                layui.treeGird({
                    elem: '#menuList',
                    nodes: nodes,
                    layout: layout
                });
            }
        });


//        table.render({
//            id: 'menuList',
//            elem: '#menuList',
//            request: {
//                pageName: 'pageIndex', //页码的参数名称，默认：page
//                limitName: 'pageSize' //每页数据量的参数名，默认：limit
//            },
//            url: '/menu/list',
//            limits:[5,10,15,30,50],
//            page: true,
//            cols: [[ //表头
//                 {type: 'checkbox'}
//                ,{field: 'id', title: 'ID',width:"4%", sort: true}
//                ,{field: 'firstMenu', title: '一级菜单', width:"12%", sort: true}
//                ,{field: 'secondMenu', title: '二级菜单',width:"12%", sort: true}
//                ,{field: 'typeAlias', title: '类型', width:"8%",sort: true}
//                ,{field: 'icon', title: '图标',width:"8%", sort: true,toolbar: '#iconTemplate'}
//                ,{field: 'url', title: '链接',sort: true}
//                ,{field: 'orderNum', title: '排序号',width:"8%", sort: true}
//                ,{field: 'status', title: '是否可见',width:"8%", sort: true, toolbar: '#statusTemplate'}
//                ,{title:'操作', align:'center',toolbar: '#toolTemplete'}
//            ]]
//        });
        // 监听工具条
        table.on('tool(menuListTool)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if(layEvent === 'edit'){ //编辑

            }
        });
        // 监听菜单状态
        form.on('switch(status)', function(obj){
            var id=this.value;
            var status=0;
            if(!obj.elem.checked){
                status=1;
            }
            var mode= layer.msg('修改中', {
                icon: 16
                ,shade: 0.4
            });
            setTimeout(function(){
                $.ajax({
                    url: '/menu/',
                    type: 'post',
                    data: {"id":id,"status":status},
                    async: false,
                    dataType: "json",
                    traditional: true,
                    success: function (msg) {
                        layer.msg("修改成功");
                    }, error: function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                        window.top.layer.msg('请求失败', {icon: 5,  area: ['120px', '80px'], anim: 2});
                    }
                });
            },500);

        });
        var active = {
            // 查询按钮
            select: function () {
                location.reload();
            },
            // 刷新按钮
            reload: function () {
                location.reload();
            },
            // 新增按钮
            add: function () {
                layer.open({
                    title : "添加菜单",
                    type : 2,
                    shade: false,
                    zIndex: layer.zIndex, //重点1
                    maxmin:true,
                    skin:'layui-layer-lan',
                    shade: 0.4,
                    area:["60%","80%"],
                    content : "loadAddPage",
                    success : function(layero, index){
                    }
                });
            },

        };
        // 监控带有layui-btn类的元素, 并触发相应的方法
        $('.layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });

    function switchStatus(id) {

    }

    function updateRow(id){
        layer.open({
            title : "修改菜单",
            type : 2,
            shade: false,
            zIndex: layer.zIndex,
            maxmin:true,
            skin:'layui-layer-lan',
            shade: 0.4,
            area:["60%","80%"],
            content : 'loadUpdatePage?id=' + id,
            success : function(layero, index){
            }
        });
    }
    function deleteRow(id){
        layer.confirm("确定要删除吗",{btn:['是的','取消'],title:"提示信息",icon:2},function(){
            layer.msg('删除中', {
                icon: 16
                ,shade: 0.5
            });
            setTimeout(function(){
                $.ajax({
                    url:"/menu/"+id,
                    type:"delete",
                    success:function(res){
                        layer.closeAll("loading");
                        if(res){
                            layer.msg("删除成功")
                            location.reload();
                        }else{
                            layer.msg("删除失败")
                        }
                    },
                    error:function( XMLHttpRequest, textStatus, errorThrown) {
                        layer.closeAll("loading");
                        commonAjax.error(XMLHttpRequest,layer);
                    },
                })
            },500);

        });
    }
</script>

<style>


</style>
</html>
