<%--
  Created by IntelliJ IDEA.
  User: jinxin
  Date: 2021/1/10
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,laydate,icheck,summernote,clock"></t:base>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row"><%----%>
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>物品领用登记</h5>
                </div>
                <div class="ibox-content">
<%--                    <t:formvalid  action="item/get/save">--%>
                    <form class="form-horizontal m-t" id="commonForm" action="item/get/save" method="post">
                        <input type="hidden" name="id" id="id" value="${item.id }">
                        <div class="form-group">
                            <label class="col-sm-3 control-label m-b">领用科室*：</label>
                            <div class="col-sm-5 m-b">
<%--                                <input id="departmentName" name="departmentName" type="text" class="form-control" required="" value="${item.departmentName }">--%>
                                <textarea id="departmentName" name="departmentName" required="" defaultVal="${departmentName}" class="form-control">${item.departmentName }</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">领用人*：</label>
                            <div class="col-sm-5">
                                <textarea id="userName" name="userName" required="" defaultVal="${userName}" class="form-control">${item.userName }</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">领用物品明细*：</label>
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
                        <input type="hidden" name="jsonData" id="jsonData">
                        <%--<div class="form-group">
                            <label class="col-sm-3 control-label">领用物品*：</label>
                            <div class="col-sm-5">
                                <select id="itemName" name="itemName" class="form-control" required="" >
                                    <c:forEach items="${lstItems }" var="c">
                                        <option value="${c.name }" <c:if test="${item.itemName == c.name }">selected="selected"</c:if>>${c.name }</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>--%>
                        <%--<div class="form-group">
                            <label class="col-sm-3 control-label">领取数量*：</label>
                            <div class="col-sm-5">
                                <input id="quantity" name="quantity" type="number" class="form-control" required="" value="${item.quantity }">
                            </div>
                        </div>--%>
                        <div class="form-group">
                            <label class="col-sm-3 control-label m-b">领取时间*：</label>
                            <div class="col-sm-4 m-b">
                                <input class="laydate-icon form-control layer-date" id="getDay" name="getDay" placeholder="领取日期只能为星期二" value='<fmt:formatDate value="${item.getDay }" type="both" pattern="yyyy-MM-dd"/>'>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-5">
                                <textarea id="memo" name="memo" class="form-control" >${item.memo}</textarea>
                            </div>
                        </div>
                        <div class="form-group" style="margin-top: 30px;">
                            <div class="col-sm-4 col-sm-offset-3">
                                <button class="btn btn-primary" type="button" onclick="doBtnSaveApplyAction();">登记</button>
                            </div>
                        </div>
                    </form>
<%--                    </t:formvalid>--%>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var list = [];
    var table;
    var is_show = false;
    <c:if test="${item.jsonData != null }">
    list = ${item.jsonData }
        is_show = true;
    </c:if>

    function add(){
        layer.confirm(
            '<div class="form-group">\n' +
            '    <label class="col-sm-3 control-label">领用物品*：</label>\n' +
            '    <div class="col-sm-5">\n' +
            '         <select id="itemName" name="itemName" class="form-control" required="" >\n' +
            '               <c:forEach items="${lstItems }" var="c">\n' +
            '                   <option value="${c.name }" <c:if test="${item.itemName == c.name }">selected="selected"</c:if>>${c.name}</option>\n' +
            '               </c:forEach>\n' +
            '         </select>\n' +
            '    </div>\n' +
            '</div>\n' +
            '<div class="input-hidden form-group">\n' +
            '    <label class="col-sm-3 control-label">数量*：</label>\n' +
            '    <div class="col-sm-8">\n' +
            '        <input  id="quantity" name="quantity" required="" class="form-control" />\n' +
            '    </div>\n' +
            '</div>\n'
            ,{area:['800px']}, function(index){
                //do something
                var itemName = $("#itemName").val();
                var quantity = $("#quantity").val();
                console.log(itemName + "," + quantity);

                var i= list.length;
                list.push({
                    itemName:itemName,
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
        $("#jsonData").val(JSON.stringify(list))
        var count = 0
        for(var i in list){
            var item = list[i];
            count += item['Subtotal'];
        }
        //第一个实例
        if(is_show){
            table.render({
                elem: '#demo'
                ,data: list
                ,cols: [[ //表头
                    {field: 'itemName', title: '名称', width:80}
                    ,{field: 'quantity', title: '数量', width:80}
                ]]
            });
        }else{
            table.render({
                elem: '#demo'
                ,data: list
                ,cols: [[ //表头
                    {field: 'itemName', title: '名称', width:80}
                    ,{field: 'quantity', title: '数量', width:80}
                ]]
            });
        }
    }

    //表单验证
    $(function() {
        $("#commonForm").validate({
            submitHandler : function(form) {
                $(form).ajaxSubmit({
                    success : function(o) {
                        if (o.success) {
                            qhTipSuccess('保存成功');   
                            location.href='common/goSuccess';
                        } else {
                           // $('#msg').removeAttr("hidden");
                            qhTipWarning(o.msg);
                        }
                    },
                    error : function(data) {
                        qhTipError('系统错误，请联系系统管理员');
                    }
                });
            }
        });
    });
    $(function() {
        laydate({
            elem : "#getDay",
            event : "focus",
            istime : false,
            format : 'YYYY-MM-DD'
        });
    });
    //保存申请
    function doBtnSaveApplyAction() {
        $("#commonForm").submit();

        // location.href='common/goSuccess';
    }
</script>
</html>
