<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/22 0022
  Time: 18:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <title>企业分期还款比例设置</title>
    <%@include file="/WEB-INF/views/include/base.jsp" %>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/went1.css">
    <style>
        input{
            width: 80px;
        }
    </style>
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp" %>
<div class="center">
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
                <p class="c1">企业分期还款比例设置</p>
            </div>
        </div>
        <div class="blank5"></div>
        <div class="qb">
            <div class="cont biaogee">
                <table>
                    <tr>
                        <th style="width:45%;">企业类型</th>
                        <th style="width:55%;">分期还款比例</th>
                    </tr>
                    <c:forEach items="${supplierTypes}" var="st" varStatus="status">
                        <tr>
                            <td>${st.supplierTypeName}
                                <input type="hidden" id="stid${status.index}" name="stid${status.index}" value="${st.supplierType}"/>
                                <input type="hidden" id="stname${status.index}" name="stname${status.index}" value="${st.supplierTypeName}" />
                            </td>
                            <td>
                                <input type="text" id="sdpr${status.index}" name="sdpr${status.index}" value="${st.proportionRepayment}"/>
                                <input type="hidden" id="sdpro${status.index}" name="sdpro${status.index}" value="${st.proportionRepayment}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr class="x1">
                        <td colspan="2" align="center" class="p3">
                            <input type="hidden" id="ct" name="ct" value="${fn:length(supplierTypes)}" />
                            <input type="button" class="tjiao" onclick="saveSettings()" value="保存"/>
                        </td>
                    </tr>
                </table>
            </div>
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
    function saveSettings() {
        var cou = parseInt($("#ct").val());
        var s="";
        var d=[];
        for(var i=0;i<cou;i++) {
            var stid = $("#stid" + i).val();
            var sdpr = $("#sdpr" + i).val();
            var sdpro = $("#sdpro" + i).val();
            if ((!RegExp("^\\d{1,5}\\.\\d+$").test(sdpr) && !RegExp("^\\d{1,5}\\.?$").test(sdpr)) || Number(sdpr) <= 0 || sdpr <= "") {
                s += $("#stname" + i).val() + "、";
                continue;
            }
            if (parseFloat(sdpr) == parseFloat(sdpro)) continue;
            d.push({id: stid, pr: sdpr});
        }
        if(s>""){
            alert("请正确填写"+s+"分期还款比例！");
        }
        savetips.fai=0;
        savetips.sav=d.length;
        for(var i=0;i<d.length;i++){
            $.ajax({
                type: 'post',
                url: '../infrastructure/updateSupplierRepaymentSettings',
                data: 'supplierType='+d[i].id+"&proportionRepayment="+d[i].pr,
                success: function (data) {
                    savetips.sav=savetips.sav-1;
                    if (data != '1') {
                        savetips.fai=savetips.fai+1;
                    }
                    if(savetips.sav<=0){
                        tipMessage((savetips.fai>0)?"操作失败，请检查后重试！":"数据修改保存成功！", function () {
                        	location.reload();
                        });
                    }
                }
            });
        }
    }
</script>
</html>