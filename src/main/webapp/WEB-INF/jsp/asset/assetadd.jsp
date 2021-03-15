<%--
  Created by IntelliJ IDEA.
  User: zhangxufeng
  Date: 2021/1/24
  Time: 下午5:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="default,laydate,icheck,jqgrid"></t:base>
    <script type="text/javascript">
        $(function() {
            laydate({elem:"#changeTime",event:"focus",istime: false, format: 'YYYY-MM-DD'});
        });

    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <t:formvalid action="asset/assetmanage/save">
                        <input type="hidden" name="id" id="id" value="${asset.id }">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">科室*：</label>
                            <div class="col-sm-8">
                                <div class="input-group">
                                    <t:choose url="common/selectDepart" hiddenName="depId"
                                              hiddenValue="${depid }"
                                              textValue="${dept }" textName="dept" hiddenId="depId"
                                              textId="dept" ></t:choose>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">固定资产名称*：</label>
                            <div class="col-sm-3">
                                <input id="assetName" name="assetName" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${asset.assetName }">
                            </div>
                            <label class="col-sm-3 control-label">保管人*：</label>
                            <div class="col-sm-3">
                                <input id="receiver" name="receiver" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${asset.receiver }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">数量*：</label>
                            <div class="col-sm-3">
                                <input id="quantity" name="quantity" type="number" class="form-control" required="" value="${asset.quantity }">
                            </div>
                            <label class="col-sm-3 control-label">价格*：</label>
                            <div class="col-sm-3">
                                <input id="amount" name="amount" type="number" class="form-control" required="" value="${asset.amount }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">规格/型号*：</label>
                            <div class="col-sm-8">
                                <input id="model" name="model" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${asset.model }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">时间*：</label>
                            <div class="col-sm-3">
                                <input id="changeTime" name="changeTime" type="text" class="laydate-icon form-control layer-date" value='<fmt:formatDate value="${asset.changeTime }" type="date" pattern="yyyy-MM-dd"/>'>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">存放地址*：</label>
                            <div class="col-sm-8">
                                <input id="address" name="address" minlength="1" maxlength="30" type="text" class="form-control" required="" value="${asset.address }">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-8">
                                <textarea id="commit" name="commit" class="form-control" >${asset.commit}</textarea>
                            </div>
                        </div>
                    </t:formvalid>
                </div>
                <div class="col-sm-12">
                    <div class="row">
                        <div class="col-sm-12" id="searchGroupId">
                        </div>
                    </div>
                    <div class="ibox">
                        <div class="ibox-content">
                            <div id="assetManageTable" class="jqGrid_wrapper"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 脚本部分 -->
<t:datagrid actionUrl="asset/assetmanage/asset_history/datagrid" tableContentId="assetManageTable" fit="true" caption="当前资产库存历史记录" name="assetManageList" pageSize="20" sortName="createDate" sortOrder="desc">
    <%--<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>--%>
    <t:dgCol name="createDate" label="更新时间" width="80" datefmt="yyyy-MM-dd HH:mm:SS" queryModel="group" datePlugin="laydate"></t:dgCol>
    <t:dgCol name="receiver" label="保管人" width="50"></t:dgCol>
    <t:dgCol name="assetName" label="资产名称" width="50" query="true" defval="${asset.assetName }"></t:dgCol>
    <t:dgCol name="model" label="规格/型号" width="50"></t:dgCol>
    <t:dgCol name="quantity" label="数量" width="50"></t:dgCol>
    <t:dgCol name="amount" label="价格" width="50"></t:dgCol>
    <t:dgCol name="address" label="库存地点" width="70"></t:dgCol>
    <%--<t:dgCol name="opt" label="操作" ></t:dgCol>
    <t:dgDelOpt label="删除" url="asset/assetmanage/delete?id={id}"/>
    <t:dgToolBar url="asset/assetmanage/addorupdate" type="add" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar url="asset/assetmanage/addorupdate" type="edit" width="40%" height="70%"></t:dgToolBar>
    <t:dgToolBar type="refresh" ></t:dgToolBar>--%>
</t:datagrid>
</body>
<script>
    var json = '${asset.jsonData}';

    json = JSON.parse(json);

    for (var i in json){
        $("#record").append(json[i] + "<br>")
    }

</script>

</html>


