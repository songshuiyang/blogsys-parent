<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../common/taglib.jsp" %>
<%@include file="../../../../common/cssModule.jsp" %>
<section class="table-module" style="margin: 10px;">
    <table id="assginRoleList"></table>

    <button class="layui-btn layui-btn-radius layui-btn-normal" style="margin-left:60px;" id="assginRoleCommit">
        <i class="layui-icon">&#xe618;</i>提交
    </button>
</section>
<%@include file="../../../../common/jsModule.jsp" %>
<script >
    layui.use(['table','layer','form'], function(){
        var table = layui.table;
        var layer=layui.layer;
        var form =layui.form;
        var $=layui.jquery;
        var loadIndex  = layer.load(0, {shade: [0.1,'#000']}); // 加载效果

        table.render({
            elem: '#assginRoleList'
            ,request: {
                pageName: 'pageIndex', //页码的参数名称，默认：page
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            }
            ,url: '/role/getRolesByUserId?userId='+${userId} //数据接口
            ,cols: [[ //表头
                {type: 'checkbox',width:"10%",}
                ,{field: 'roleCode', title:'角色编号',}
                ,{field: 'roleName', title: '角色名称',}
                ,{field: 'roleDescribe', title: '角色描述',}
            ]],
            page:false,
            done: function(res, curr, count){
                layer.close(loadIndex);
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                //console.log(curr);
                //得到数据总量
                //.log(count);
            }
        });
        $('#assginRoleCommit').click(function () {
            var checkStatus = table.checkStatus('assginRoleList');
            var data = checkStatus.data;
            var roleIds = "";
            $.each(data,function(k,v){
                roleIds += v.id+ ",";
            });
            // 去最后的,
            roleIds=roleIds.substring(0,roleIds.lastIndexOf(","));
            $.ajax({
                url:'/role/updateRoleUserRelationship',
                data:{"userId":"${userId}","roleIds":roleIds},
                dataType:"json",
                type:"post",
                success:function(res){
                    if(res.code == 1 ){
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                        window.parent.layui.table.reload('userList');
                        window.top.layer.msg("角色分配成功", {icon: 6,area: ['120px', '80px'], anim: 2});
//                        layer.alert("角色分配成功");
//
//                        setTimeout(function () {
//
//                        },1500);
                    }else{
                        layer.alert("角色分配失败");
                    }
                },
                error:function( XMLHttpRequest, textStatus, errorThrown) {
                    layer.alert("系统异常",{icon: 5});
                },
            })
        });

    });
</script>