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
    <title>企业分红设置</title>
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
                <p class="c1">收银系统设置</p>
            </div>
        </div>
        <div class="blank5"></div>
        <div class="qb">
            <div class="cont biaogee">
                <table>
                    <tr>
                        <th style="width:15%;">类型</th>
                        <th style="width:17%;">实付倍数</th>
                        <!--<th style="width:17%;">实体店收入倍数</th>-->
                        <th style="width:17%;">手续费(%)</th>
                        <th style="width:17%;">赠送红旗券</th>
                        <th style="width:17%;">赠送分红额度</th>
                    </tr>
                    <tr>
                        <td>红旗宝<input type="hidden" id="cashierId1" value="${cashierSettings1.cashierId}"/></td>
                        <td><input type="text" id="realpayMultipe1" value="${cashierSettings1.realpayMultipe}"/></td>
                        <!--<td><input type="text" id="payMultiple1" value="${cashierSettings1.payMultiple}"/></td>-->
                        <td><input type="text" id="counterFee1" value="${cashierSettings1.counterFee}"/></td>
                        <td><input type="text" id="giftHqj1" value="${cashierSettings1.giftHqj}"/></td>
                        <td><input type="text" id="giftFhed1" value="${cashierSettings1.giftFhed}"/></td>
                    </tr>
                    <tr>
                        <td>生鲜宝<input type="hidden" id="cashierId2" value="${cashierSettings2.cashierId}"/></td>
                        <td><input type="text" id="realpayMultipe2" value="${cashierSettings2.realpayMultipe}"/></td>
                        <!--<td><input type="text" id="payMultiple2" value="${cashierSettings2.payMultiple}"/></td>-->
                        <td><input type="text" id="counterFee2" value="${cashierSettings2.counterFee}"/></td>
                        <td><input type="text" id="giftHqj2" value="${cashierSettings2.giftHqj}"/></td>
                        <td><input type="text" id="giftFhed2" value="${cashierSettings2.giftFhed}"/></td>
                    </tr>
                    <tr class="x1">
                        <td colspan="5" align="center" class="p3">
                            <input type="button" class="tjiao" onclick="saveCashierSettings()" value="保存"/>
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
    function saveCashierSettings() {
        function getOneCashierSettings(num, tit) {
            var cid = $("#cashierId" + num).val();
            var rm = $("#realpayMultipe" + num).val();
            //var pm = $("#payMultiple" + num).val();
            var cf = $("#counterFee" + num).val();
            var gh = $("#giftHqj" + num).val();
            var gf = $("#giftFhed" + num).val();
            if (isNaN(cid)) return "";
            if (isNaN(rm)) {
                alert("请正确填写" + tit + "的实付倍数！")
                return "0";
            }
            /*if (isNaN(pm)) {
                alert("请正确填写" + tit + "的实体店收入倍数！");
                return "0";
            }*/
            if (isNaN(cf)) {
                alert("请正确填写" + tit + "的手续费！");
                return "0";
            }
            if (isNaN(gh)) {
                alert("请正确填写" + tit + "的赠送红旗券！");
                return "0";
            }
            if (isNaN(gf)) {
                alert("请正确填写" + tit + "的赠送分红额度！");
                return "0";
            }
            return "&cid" + num + "=" + cid + "&rm" + num + "=" + rm + "&cf" + num + "=" + cf + "&gh" + num + "=" + gh + "&gf" + num + "=" + gf;//+ "&pm" + num + "=" + pm
        }

        var dataStr = "";
        var s = "";
        s = getOneCashierSettings("1", "现金");
        if (s == "0") return;
        dataStr = dataStr + s;
        s = getOneCashierSettings("2", "红旗劵");
        if (s == "0") return;
        dataStr = dataStr + s;
        s = getOneCashierSettings("3", "现金+红旗劵");
        if (s == "0") return;
        dataStr = dataStr + s;
        if(dataStr<="") return;
        $.ajax({
            type: 'post',
            url: '../infrastructure/updateCashierSettings',
            data: dataStr,
            success: function (data) {
                if (data == '1') {
                    tipMessage("操作成功！", function () {
                        location.href = '../infrastructure/cashierSettings';
                    });
                } else {
                    tipMessage("操作失败，请检查后重试！", function () {
                    });
                }
            }
        });
    }
</script>
</html>