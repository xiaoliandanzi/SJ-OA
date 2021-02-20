<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2021/1/7
  Time: 22:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="form-group">
    <label class="col-sm-3 control-label">借用科室*：</label>
    <div class="col-sm-5">
        <textarea id="departmentName" name="departmentName" readonly class="form-control">${biz.departmentName }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">借用人*：</label>
    <div class="col-sm-5">
        <textarea id="userName" name="userName" readonly class="form-control">${biz.userName }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">领用餐卡*：</label>
    <div class="col-sm-4">
        <table id="demo"></table>
    </div>
</div>
<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label">餐卡类型*：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--        <textarea id="cardName" name="cardName" readonly class="form-control">${biz.cardName }</textarea>--%>
<%--    </div>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--    <label class="col-sm-3 control-label">借用数量*：</label>--%>
<%--    <div class="col-sm-5">--%>
<%--        <input id="quantity" name="quantity" type="number" class="form-control" readonly required="" value="${biz.quantity }">--%>
<%--    </div>--%>
<%--</div>--%>

<div class="form-group">
    <label class="col-sm-3 control-label m-b">领用日期*：</label>
    <div class="col-sm-4 m-b">
        <input class="laydate-icon form-control layer-date" id="useDay" name="useDay" readonly value='<fmt:formatDate value="${biz.useDay }" type="both" pattern="yyyy-MM-dd"/>'>
    </div>
</div>

<div class="form-group">
    <label class="col-sm-3 control-label">借用事由*：</label>
    <div class="col-sm-5">
        <textarea id="reason" name="reason" readonly class="form-control">${biz.reason }</textarea>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">备注：</label>
    <div class="col-sm-5">
        <textarea id="commit" name="commit" readonly class="form-control">${biz.commit }</textarea>
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
    <c:if test="${biz.jsonData != null }">
    list = ${biz.jsonData }
        </c:if>


        function add(){
            layer.confirm('<div class="form-group input-hidden">\n' +
                '    <label class="col-sm-3 control-label">借用物品*：</label>\n' +
                '    <div class="col-sm-5">\n' +
                '        <select id="cardName" name="cardName" class="form-control" required="" >\n' +
                '            <c:forEach items="${lstItems }" var="c">\n' +
                '                <option value="${c.name }" <c:if test="${biz.cardName == c.name }">selected="selected"</c:if>>${c.name }</option>\n' +
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

                var cardName = $("#cardName").val();
                var quantity = $("#quantity").val();


                console.log(cardName + "," +quantity)

                var i= list.length;
                list.push({
                    cardName:cardName,
                    quantity:quantity,
                    action:'<button class="btn btn-primary" type="button" onclick="removeItem('+i+');">删除</button>',
                });
                render_table();

                layer.close(index);
            });
        }

    layui.use('table', function(){
        table = layui.table;
        render_table();
    });


    function render_table() {

        //第一个实例
        table.render({
            elem: '#demo'
            ,data: list
            ,cols: [[ //表头
                {field: 'cardName', title: '餐卡类型', width:120}
                ,{field: 'quantity', title: '借用数量', width:120}
            ]]
        });
    }

    function removeItem(index) {

        list.splice(index,1)
        render_table();
    }
</script>