<%--
  Created by IntelliJ IDEA.
  User: Zhutaoshen
  Date: 2017/1/3 0003
  Time: 9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <title>幸福购入驻区域设置</title>
    <%@include file="/WEB-INF/views/include/base.jsp" %>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/B2C_orderlist.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/wuliu_alert.css"/>
    <script src="${path}/commons/js/my97/WdatePicker.js"></script>
    <script src="${path }/commons/js/oneFhCycle.js"></script>
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp" %>
<div class="wrap">
    <!-- 左边 start -->
    <div class="left f_l">
        <%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
    </div>
    <!-- 左边 end -->

    <!-- 右边 start -->
    <div class="right">
        <div class="c21">
            <div class="title">
                <p>卖家中心&nbsp;&gt;&nbsp; </p>
                <p>基础设置&nbsp;&gt;&nbsp; </p>
                <p class="c1">幸福购入驻区域设置</p>
            </div>
        </div>
        <div class="tb-void">
            <form method="post" id="planImpl" enctype="multipart/form-data">
                <div class="tb-void">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tbody>
                        <tr>
                            <th width="22%">区域编号</th>
                            <th width="22%">区域名称</th>
                            <th width="22%">周期返还天数</th>
                            <th width="22%">周期返还折扣比率</th>
                            <th width="12%">操作</th>
                        </tr>
                        </tbody>
                        <tbody>
                        <c:forEach items="${pageBean.result}" var="st" varStatus="status">
                            <tr>
                                <td>${st.regionId}
                                    <input type="hidden" id="sid${status.index}" name="sid${status.index}" value="${st.regionId}"/>
                                </td>
                                <td>
                                    <input type="text" id="stx${status.index}" name="stx${status.index}" value="${st.regionText}"/>
                                    <input type="hidden" id="sto${status.index}" name="sto${status.index}" value="${st.regionText}"/>
                                </td>
                                <td>
                                    <input type="text" id="sdx${status.index}" name="sdx${status.index}" value="${st.retDate}"/>
                                    <input type="hidden" id="sdo${status.index}" name="sdo${status.index}" value="${st.retDate}"/>
                                </td>
                                <td>
                                    <input type="text" id="szx${status.index}" name="szx${status.index}" value="${st.retZk}"/>
                                    <input type="hidden" id="szo${status.index}" name="szo${status.index}" value="${st.retZk}"/>
                                </td>
                                <td>
                                    <input type="button" class="tjiao" onclick="delSettings(${st.regionId})" value="删除"/>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td>
                                <input type="text" id="sid" name="sid" value=""/>
                            </td>
                            <td>
                                <input type="text" id="stx" name="stx" value=""/>
                                <input type="hidden" id="sto" name="sto" value=""/>
                            </td>
                            <td>
                                <input type="text" id="sdx" name="sdx" value=""/>
                            </td>
                            <td>
                                <input type="text" id="szx" name="szx" value=""/>
                            </td>
                            <td>
                                <input type="button" class="tjiao" onclick="addSettings()" value="新增"/>
                            </td>
                        </tr>
                        <tr class="x1">
                            <td colspan="5" align="center" class="p3">
                                <input type="hidden" id="ct" name="ct" value="${fn:length(pageBean.result)}" />
                                <input type="button" class="tjiao" onclick="saveSettings()" value="保存修改"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <c:if test="${!empty pageBean.result}">
                    <%@include file="/WEB-INF/views/dealerbuy/basePage/basePage.jsp" %>
                </c:if>
            </form>
        </div>
    </div>
    <!-- 右边 end -->
</div>
<div class="blank10"></div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp" %>
</body>
<script type="text/javascript">
    var savetips={sav:0,fai:0,suc:0};
    $(document).ready(function () {
    });
    function addSettings() {
        var sid=$("#sid").val();
        var stx=$("#stx").val();
        var sto=$("#sto").val();
        var sdx=$("#sdx").val();
        var szx=$("#szx").val();
        if((!RegExp("^\\d{1,5}\\.\\d+$").test(sid) && !RegExp("^\\d{1,5}\\.?$").test(sid))|| Number(sid)<=0||sid<=""||stx<=""||sdx<=""||szx<=""){
            alert("请全部正确填写新增的区域数据！");
        }else{
            $.ajax({
                type: 'post',
                url: '../infrastructure/updateSupplierRegionSettingsxfg',
                data: 'mode=1&rid='+sid+"&text="+stx+"&dt="+sdx+"&zt="+szx,
                success: function (data) {
                    if (data == '1') {
                        window.location.href="${path}/infrastructure/supplierRegionSettingsxfg?page=${page}";
                    } else {
                        tipMessage("操作失败，请检查后重试！", function () {
                        });
                    }
                }
            });
        }
    }
    function delSettings(sid) {
        $.ajax({
            type: 'post',
            url: '../infrastructure/updateSupplierRegionSettingsxfg',
            data: "mode=2&rid="+sid+"&text=0&dt=0&zt=0",
            success: function (data) {
                if (data == '1') {
                    window.location.href="${path}/infrastructure/supplierRegionSettingsxfg?page=${page}";
                } else {
                    tipMessage((data=='-1')?"此区域已有入驻企业，不允许删除！":"操作失败，请检查后重试！", function () {
                    });
                }
            }
        });
    }
    function saveSettings() {
        var cou = parseInt($("#ct").val());
        var s="";
        var d=[];
        for(var i=0;i<cou;i++) {
            var sid = $("#sid" + i).val();
            var stx = $("#stx" + i).val();
            var sto = $("#sto" + i).val();
            var sdx = $("#sdx" + i).val();
            var sdo = $("#sdo" + i).val();
            var szx = $("#szx" + i).val();
            var szo = $("#szo" + i).val();
            if ((stx == sto)&&(sdx == sdo)&&(szx == szo)) continue;
            d.push({id: sid, text: stx, dt:sdx, zt:szx});
        }
        savetips.fai=0;
        savetips.sav=d.length;
        for(var i=0;i<d.length;i++){
            $.ajax({
                type: 'post',
                url: '../infrastructure/updateSupplierRegionSettingsxfg',
                data: "mode=0&rid="+d[i].id+"&text="+d[i].text+"&dt="+d[i].dt+"&zt="+d[i].zt,
                success: function (data) {
                    savetips.sav=savetips.sav-1;
                    if (data != '1') {
                        savetips.fai=savetips.fai+1;
                    }
                    if(savetips.sav<=0){
                        tipMessage((savetips.fai>0)?"操作失败，请检查后重试！":"数据修改保存成功！", function () {
                        });
                    }
                }
            });
        }
    }
    function toPage(page){
        window.location.href="${path}/infrastructure/supplierRegionSettingsxfg?page="+page;
    }
</script>
</html>