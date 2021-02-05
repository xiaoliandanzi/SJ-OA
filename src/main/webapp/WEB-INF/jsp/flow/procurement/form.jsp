<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2021/1/18
  Time: 22:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="form-group">
    <label class="col-sm-3 control-label">采购部门*：</label>
    <div class="col-sm-5">
        <textarea id="departmentName" name="departmentName" required="" class="form-control">${biz.departmentName }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">经办人*：</label>
    <div class="col-sm-5">
        <textarea id="agent" name="agent" required="" class="form-control">${biz.agent }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">电话*：</label>
    <div class="col-sm-5">
        <textarea id="telephone" name="telephone" required="" class="form-control">${biz.telephone }</textarea>
    </div>
</div>


<div class="form-group">
    <label class="col-sm-3 control-label">采购项目明细*：</label>
    <div class="col-sm-6">
        <table id="demo"></table>
    </div>
</div>
<input type="hidden" name="json_data" id="json_data">

<div class="form-group" style="margin-top: 30px;">
    <label class="col-sm-3 control-label"></label>
    <div class="col-sm-4">
        <button class="btn btn-primary" type="button" onclick="add();">添加</button>
    </div>
</div>


<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label">采购项目明细*：</label>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label">品名*：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--         <input  id="itemName" name="itemName" required="" class="form-control" />--%>
<%--    </div>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label">品牌、规格型号*：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--        <input  id="type" name="type" required="" class="form-control" />--%>
<%--    </div>--%>
<%--</div>--%>

<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label">数量*：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--        <input  id="number" name="number" required="" class="form-control" />--%>
<%--    </div>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label">单价*：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--        <input  id="unitPrice" name="unitPrice" required="" class="form-control" />--%>
<%--    </div>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label">操作：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--        <button class="btn btn-primary" type="button" onclick="deleteItem();">添加</button>--%>
<%--    </div>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label">用途*：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--        <textarea id="purpose" name="purpose" required="" class="form-control">${biz.purpose }</textarea>--%>
<%--    </div>--%>
<%--</div>--%>


<div class="form-group">
    <label class="col-sm-3 control-label">合计*：</label>
    <div class="col-sm-5">
        <input id="price" name="price" type="number" class="form-control" required="" value="${biz.price }">
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">资金来源*：</label>
    <div class="col-sm-5">
        <textarea id="foundSource" name="foundSource" required="" class="form-control">${biz.foundSource }</textarea>
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

    function add(){
        layer.confirm('<div class="input-hidden form-group">\n' +
            '    <label class="col-sm-3 control-label">品名*：</label>\n' +
            '    <div class="col-sm-5">\n' +
            '         <input  id="itemName" name="itemName" required="" class="form-control" />\n' +
            '    </div>\n' +
            '</div>\n' +
            '<div class="input-hidden form-group">\n' +
            '    <label class="col-sm-3 control-label">品牌、规格型号*：</label>\n' +
            '    <div class="col-sm-5">\n' +
            '        <input  id="type" name="type" required="" class="form-control" />\n' +
            '    </div>\n' +
            '</div>\n' +
            '\n' +
            '<div class="input-hidden form-group">\n' +
            '    <label class="col-sm-3 control-label">数量*：</label>\n' +
            '    <div class="col-sm-5">\n' +
            '        <input  id="number" name="number" required="" class="form-control" />\n' +
            '    </div>\n' +
            '</div>\n' +
            '<div class="input-hidden form-group">\n' +
            '    <label class="col-sm-3 control-label">单价*：</label>\n' +
            '    <div class="col-sm-5">\n' +
            '        <input  id="unitPrice" name="unitPrice" required="" class="form-control" />\n' +
            '    </div>\n' +
            '</div>\n' +
            '<div class="input-hidden form-group">\n' +
            '    <label class="col-sm-3 control-label">用途*：</label>\n' +
            '    <div class="col-sm-5">\n' +
            '        <textarea id="purpose" name="purpose" required="" class="form-control"></textarea>\n' +
            '    </div>\n' +
            '</div>', function(index){
            //do something

            var itemName = $("#itemName").val();
            var type = $("#type").val();
            var number = $("#number").val();
            var unitPrice = $("#unitPrice").val();
            var purpose = $("#purpose").val();
            var Subtotal = number * unitPrice;

            console.log(itemName + "," + type + "," +number + "," +unitPrice + "," +Subtotal)

            var i= list.length;
            list.push({
                itemName:itemName,
                type:type,
                number:number,
                unitPrice:unitPrice,
                purpose:purpose,
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
        $("#json_data").val(JSON.stringify(list))
        var count = 0
        for(var i in list){
            var item = list[i];
            count += item['Subtotal'];
        }
        $("#price").val(count)
        //第一个实例
        table.render({
            elem: '#demo'
            ,data: list
            ,cols: [[ //表头
                {field: 'itemName', title: '品名', width:80}
                ,{field: 'type', title: '品牌、规格型号', width:80}
                ,{field: 'number', title: '数量', width:80}
                ,{field: 'unitPrice', title: '单价', width:80}
                ,{field: 'purpose', title: '用途', width:80}
                ,{field: 'Subtotal', title: '小计', width:80}
                ,{field: 'action', title: '操作', width:100}
            ]]
        });
    }
    
    function removeItem(index) {

        list.splice(index,1)
        render_table();
    }
</script>