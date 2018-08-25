<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../common/taglib.jsp" %>
<%@include file="../../../../common/cssModule.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" href="${ctx}static/layui-module/build/css/main.css?v=${version}" media="all" />
</head>
<body>
<div style="padding: 10px">
    <div class="layui-row">
        <div  class="layui-col-md4" id="persXtree" style="display: block;height:20px;">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top:0px;">
                <legend>权限预览</legend>
                <form class="layui-form" style="padding:10px 100px;">
                    <div id="xtree1" style="width:80%;padding:40px 40px;"></div>

                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <button class="layui-btn" lay-submit lay-filter="sure">确定分配</button>
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                </form>
            </fieldset>

        </div>
    </div>
</div>
</body>
<%@include file="../../../../common/jsModule.jsp" %>
<script type="text/javascript" src="${ctx}static/layui-module/layui/layui-xtree.js"></script>
<script>
    layui.use(['layer','form','jquery'], function(){
        var form=layui.form;
        var layer=layui.layer;
        var $= layui.jquery;
        var roleid="${id}";

        xtree3= new layuiXtree({
            elem: 'xtree1'
            , form: form
            , data: '/permission/getPermissionXTree/${roleId}'
            , isopen: true  //加载完毕后的展开状态，默认值：true
            , ckall: true    //启用全选功能，默认值：false
            , ckallback: function () { } //全选框状态改变后执行的回调函数
            , icon: {        //三种图标样式，更改几个都可以，用的是layui的图标
                open: "&#xe7a0;"       //节点打开的图标
                , close: "&#xe622;"    //节点关闭的图标
                , end: "&#xe621;"      //末尾节点的图标
            }
            , color: {       //三种图标颜色，独立配色，更改几个都可以
                open: "#EE9A00"        //节点图标打开的颜色
                , close: "#EEC591"     //节点图标关闭的颜色
                , end: "#828282"       //末级节点图标的颜色
            }
        });
        // 提交
        form.on('submit(sure)',function (data){
            console.log(xtree3.GetChecked());
            var menuids=new Array();
            var listDomCheck=xtree3.GetChecked();
            if(listDomCheck.length>0){
                $.each(listDomCheck,function(k,v){
                    var value=$(v).val();
                    menuids.push(value);
                    var oCks = xtree3.GetParent(value);  //这是方法
                    if (oCks != null) { //如果返回的对象不为null，则获取到父节点了，如果为null，说明这个值对应的节点是一级节点或是值错误
                        menuids.push(oCks.value);
                        var oValue=oCks.value;
                        oCks = xtree3.GetParent(oValue);
                        if(oCks!=null){
                            menuids.push(oCks.value);
                        }
                    }
                    else {
                        console.log('无父级节点或value值错误');
                    }
                });
            }else{
                layer.msg("选中一项吧");
            }
            var newArr = [];
            for(var i =0;i<menuids.length;i++){
                if(newArr.indexOf(menuids[i]) == -1){
                    newArr.push(menuids[i]);
                }
            }
            var index = layer.load();
            $.ajax({
                url:"/role/updateRolePermRelationship",
                data:{"roleId":${roleId},"permissionIds":newArr.join(",")},
                dataType:"json",
                type:"post",
                success:function(res){
                    layer.close(index);
                    if(res){
                        layer.msg('分配权限成功', {icon: 6, area: ['120px', '80px'], anim: 2});
                    }else{
                        layer.msg('分配权限失败', {icon: 6, area: ['120px', '80px'], anim: 2});
                    }
                    // 两秒后自动关闭
                    setTimeout(function(){
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }, 2000);
                },
                error:function( XMLHttpRequest, textStatus, errorThrown) {
                    layer.close(index);
                    commonAjax.error(XMLHttpRequest,layer);
                },
            })
            return false;
        });
    });
</script>
</html>