<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2020/12/22
  Time: 下午10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="form-group">
    <label class="col-sm-3 control-label">资产管理部门*：</label>
    <div class="col-sm-8">
        <div class="input-group">
            <t:choose url="common/selectDepart" hiddenName="dept" hiddenValue="${biz.dept}" textValue="" textName="departLabel" hiddenId="departId" textId="departLabel"></t:choose>
        </div>
    </div>
</div>

<div class="form-group">
    <label class="col-sm-3 control-label">固定资产入库明细*：</label>
    <div class="col-sm-6">
        <table id="demo"></table>
    </div>
</div>

<div class="form-group" style="margin-top: 30px;">
    <label class="col-sm-3 control-label"></label>
    <div class="col-sm-4">
        <button class="btn btn-primary" type="button" onclick="add();">添加</button>
    </div>
</div>

<div class="form-group">
    <label class="col-sm-3 control-label">合计*：</label>
    <div class="col-sm-5">
        <input id="amount" name="amount" type="number" class="form-control" required="">
    </div>
</div>
<input type="hidden" name="jsonData" id="jsonData">
<%--<div class="form-group">
    <label class="col-sm-2 control-label">数量*：</label>
    <div class="col-sm-3">
        <input id="quantity" name="quantity"  type="text" class="form-control" required="" value="${biz.quantity }">
    </div>
    <label class="col-sm-2 control-label">价格*：</label>
    <div class="col-sm-3">
        <input id="amount" name="amount"  type="text" class="form-control" required="" value="${biz.amount }">
    </div>
</div>--%>
<div class="form-group">
    <%--<label class="col-sm-2 control-label">规格型号*：</label>
    <div class="col-sm-3">
        <input id="model" name="model"  type="text" class="form-control" required="" value="${biz.model }">
    </div>--%>
    <label class="col-sm-3 control-label m-b">取得日期*：</label>
    <div class="col-sm-4 m-b">
        <input class="laydate-icon form-control layer-date" id="time" name="time" value='<fmt:formatDate value="${biz.time }" type="time" pattern="yyyy-MM-dd"/>'>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">库存地址*：</label>
    <div class="col-sm-3">
        <input id="address" name="address"  type="text" class="form-control" required="" value="${biz.address }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">用途*：</label>
    <div class="col-sm-3">
        <input id="application" name="application"  type="text" class="form-control" required="" value="${biz.application }">
    </div>
</div>

<div class="form-group">
    <label class="col-sm-3 control-label">备注：</label>
    <div class="col-sm-5">
        <input id="commit" name="commit" minlength="1" type="text" class="form-control" value="${biz.commit }">
    </div>
</div>

<style>
    .input-hidden{
        overflow: hidden;
    }
</style>
<script src="static/layui/layui.js"></script>
<script type="text/javascript">

    var list = [];
    var table;
    var is_show = false;
    <c:if test="${biz.jsonData != null }">
    list = ${biz.jsonData }
        is_show = true;
    </c:if>

    function add(){
        layer.confirm('<div class="input-hidden form-group">\n' +
            '    <label class="col-sm-3 control-label">名称*：</label>\n' +
            '    <div class="col-sm-8">\n' +
            '         <input  id="assetName" name="assetName" required="" class="form-control" />\n' +
            '    </div>\n' +
            '</div>\n' +
            '<div class="input-hidden form-group">\n' +
            '    <label class="col-sm-3 control-label">数量*：</label>\n' +
            '    <div class="col-sm-8">\n' +
            '        <input  id="quantity" name="quantity" required="" class="form-control" />\n' +
            '    </div>\n' +
            '</div>\n' +
            '\n' +
            '<div class="input-hidden form-group">\n' +
            '    <label class="col-sm-3 control-label">价格*：</label>\n' +
            '    <div class="col-sm-8">\n' +
            '        <input  id="amount" name="amount" required="" class="form-control" />\n' +
            '    </div>\n' +
            '</div>\n' +
            '<div class="input-hidden form-group">\n' +
            '    <label class="col-sm-3 control-label">规格/型号*：</label>\n' +
            '    <div class="col-sm-8">\n' +
            '        <input  id="model" name="model" required="" class="form-control" />\n' +
            '    </div>\n' +
            '</div>\n'
            ,{area:['800px']}, function(index){
                //do something

                var assetName = $("#assetName").val();
                var quantity = $("#quantity").val();
                var amount = $("#amount").val();
                var model = $("#model").val();
                var Subtotal = quantity * amount;

                console.log(assetName + "," + quantity + "," +amount + "," +model + "," +Subtotal)

                var i= list.length;
                list.push({
                    assetName:assetName,
                    quantity:quantity,
                    amount:amount,
                    model:model,
                    Subtotal:Subtotal,
                    action:'<button class="btn btn-primary" type="button" onclick="removeItem('+i+');">删除</button>',
                });
                render_table();

                layer.close(index);
            });

        // layer.open({
        //     title: '在线调试'
        //     ,content: '可以填写任意的layer代码'
        // });
    }

    layui.use('table', function(){
        table = layui.table;
        render_table();
    });


    function render_table() {
        $("#jsonData").val(JSON.stringify(list))
        var count = 0
        for(var i in list){
            var item = list[i];
            count += item['Subtotal'];
        }
        $("#amount").val(count)
        //第一个实例
        if(is_show){
            table.render({
                elem: '#demo'
                ,data: list
                ,cols: [[ //表头
                    {field: 'assetName', title: '名称', width:80}
                    ,{field: 'quantity', title: '数量', width:80}
                    ,{field: 'amount', title: '价格', width:80}
                    ,{field: 'model', title: '规格/型号', width:80}
                    ,{field: 'Subtotal', title: '小计', width:80}
                ]]
            });
        }else{
            table.render({
                elem: '#demo'
                ,data: list
                ,cols: [[ //表头
                    {field: 'assetName', title: '名称', width:80}
                    ,{field: 'quantity', title: '数量', width:80}
                    ,{field: 'amount', title: '价格', width:80}
                    ,{field: 'model', title: '规格/型号', width:80}
                    ,{field: 'Subtotal', title: '小计', width:80}
                    ,{field: 'action', title: '操作', width:80}
                ]]
            });
        }
    }

    function removeItem(index) {

        list.splice(index,1)
        render_table();
    }

</script>