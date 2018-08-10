<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
    <title>现金(红旗券)支付比例设置</title>
    <%@include file="/WEB-INF/views/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="wrap">
    <!-- 左边 start -->
    <div class="left f_l">
        <%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
    </div>
    <!-- 左边 end -->

    <!-- 右边 start -->
    <div class="right">
        <div class="c21">
            <div class="title">
                <p>卖家中心&nbsp;&gt;&nbsp; </p>
                <p>基础设置&nbsp;&gt;&nbsp; </p>
                <p class="c1">现金(红旗券)支付比例设置</p>
            </div>
        </div>
        <div class="blank5"></div>
        <div class="cont">
            <ul class="ul_vertical">
                <li>
                    <p class="p1">现金支付比例：</p>
                    <input type="text" id="cash" name="cash" value="${ratio.cashRation}" style="margin-left: 20px;"/>&nbsp;（%）
                    <br/><br/>
                </li>
                <li>
                    <p class="p1">红旗券支付比例：</p>
                    <input type="text" id="ticket" name="ticket" value="${ratio.ticketRation}" style="margin-left: 20px;"/>&nbsp;（%）
                    <br/><br/>
                </li>
                <li><p class="p1"></p></li>
                <li>
                    <input type="button"  class="fabu_btn" onclick="save()" value="保存" ></input>
                </li>
            </ul>

        </div>


    </div>
    <!-- 右边 end -->
</div>



<div class="blank10"></div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
<script type="text/javascript">
    function save(){
        var cash = $("#cash").val();
        var ticket = $("#ticket").val();
        if((!RegExp("^\\d{1,5}\\.\\d+$").test(cash) && !RegExp("^\\d{1,5}\\.?$").test(cash))|| Number(cash)<=0){
            alert("请正确填写现金支付比例！");
            return;
        }
        if((!RegExp("^\\d{1,5}\\.\\d+$").test(ticket) && !RegExp("^\\d{1,5}\\.?$").test(ticket))|| Number(ticket)<=0){
            alert("请正确填写红旗券支付比例！");
            return;
        }
        if(parseFloat(cash)+parseFloat(ticket)!=100){
            alert("请正确设置现金与红旗券支付比例！");
            return;
        }
        $.ajax({
            type:'post',
            url:'../infrastructure/setOneDividendRatio',
            data: 'cash='+cash+'&ticket='+ticket,
            success:function(data){
                if(data=='1'){
                    tipMessage("操作成功！",function(){
                    });
                }else{
                    tipMessage("操作失败，请检查后重试！",function(){
                    });
                }

            }
        });
    }
</script>
</html>