<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2021/1/7
  Time: 22:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="form-group">
    <label class="col-sm-3 control-label">借用科室*：</label>
    <div class="col-sm-5">
        <textarea id="departmentName" name="departmentName" class="form-control">${biz.departmentName }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">借用人*：</label>
    <div class="col-sm-5">
        <textarea id="userName" name="userName" class="form-control">${biz.userName }</textarea>
    </div>
</div>

<div class="form-group">
    <label class="col-sm-3 control-label">借用物品*：</label>
    <div class="col-sm-4">
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
<%--    <label class="col-sm-3 control-label">借用物品*：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--        <select id="itemName" name="itemName" class="form-control" required="" >--%>
<%--            <c:forEach items="${lstItems }" var="c">--%>
<%--                <option value="${c.name }" <c:if test="${biz.itemName == c.name }">selected="selected"</c:if>>${c.name }</option>--%>
<%--            </c:forEach>--%>
<%--        </select>--%>
<%--    </div>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label">借用数量*：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--        <input id="quantity" name="quantity" type="number" class="form-control" required="" value="${biz.quantity }">--%>
<%--    </div>--%>
<%--</div>--%>





<div class="form-group">
    <label class="col-sm-3 control-label m-b">使用日期*：</label>
    <div class="col-sm-4 m-b">
        <input class="laydate-icon form-control layer-date" id="useDay" name="useDay"  value='<fmt:formatDate value="${biz.useDay }" type="both" pattern="yyyy-MM-dd"/>'>
    </div>
</div>

<div class="form-group">
    <label class="col-sm-3 control-label m-b">归还日期*：</label>
    <div class="col-sm-4 m-b">
        <input class="laydate-icon form-control layer-date" id="returnDay" name="returnDay"  value='<fmt:formatDate value="${biz.returnDay }" type="both" pattern="yyyy-MM-dd"/>'>
    </div>
</div>

<div class="form-group">
    <label class="col-sm-3 control-label">借用事由*：</label>
    <div class="col-sm-5">
        <textarea id="reason" name="reason" class="form-control">${biz.reason }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">备注：</label>
    <div class="col-sm-5">
        <textarea id="commit" name="commit" class="form-control">${biz.commit }</textarea>
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
        layer.confirm('<div class="form-group input-hidden">\n' +
            '    <label class="col-sm-3 control-label">借用物品*：</label>\n' +
            '    <div class="col-sm-5">\n' +
            '        <select id="itemName" name="itemName" class="form-control" required="" >\n' +
            '            <c:forEach items="${lstItems }" var="c">\n' +
            '                <option value="${c.name }" <c:if test="${biz.itemName == c.name }">selected="selected"</c:if>>${c.name }</option>\n' +
            '            </c:forEach>\n' +
            '        </select>\n' +
            '    </div>\n' +
            '</div>\n' +
            '<div class="form-group input-hidden">\n' +
            '    <label class="col-sm-3 control-label">借用数量*：</label>\n' +
            '    <div class="col-sm-5">\n' +
            '        <input id="quantity" name="quantity" type="number" class="form-control" required="" value="">\n' +
            '    </div>\n' +
            '</div>', function(index){
            //do something

            var itemName = $("#itemName").val();
            var quantity = $("#quantity").val();


            console.log(itemName + "," +quantity)

            var i= list.length;
            list.push({
                itemName:itemName,
                quantity:quantity,
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

        //第一个实例
        table.render({
            elem: '#demo'
            ,data: list
            ,cols: [[ //表头
                {field: 'itemName', title: '领用物品', width:80}
                ,{field: 'quantity', title: '领用数量', width:80}
                ,{field: 'action', title: '操作', width:100}
            ]]
        });
    }

    function removeItem(index) {

        list.splice(index,1)
        render_table();
    }
</script>