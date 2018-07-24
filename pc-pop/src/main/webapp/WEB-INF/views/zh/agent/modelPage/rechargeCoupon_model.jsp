<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt">
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>商家后台管理系统-商品列表</title>
	<%@include file="/WEB-INF/views/zh/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path }/css/product.css"/>
   <%--  <link rel="stylesheet" type="text/css" href="${path }/css/product/reset.css"/>
    <link rel="stylesheet" type="text/css" href="${path }/css/product/reset2.css"/>
     --%>
   <%--  <script type="text/javascript" src="${path }/js/bussion/balance_list_fn.js"></script>   --%> 
</head>
<script type="text/javascript">
/* $(document).ready(function() {
	var supplierId="${supplierId}";
	$.ajax({
		type:"post",
		url : "../supplier/rechargeCoupon1",
		data:{'supplierId':supplierId},
		dataType:"JSON",
		success : function(msg) { 
			name=msg.name;
			decimal=msg.decimal;
			$("#name").val(name);
			$("#decimal2").val(decimal2);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});  
	

}); */
function clickSubmit1(page){
	var startTime = $("#startTime").val().trim();
	//var oid = $("#oid").val().trim();
	var endTime = $("#endTime").val().trim();
	var supplierId="${supplierId}";
	//var type = $("#typeId").val().trim();
	var pro_array  = new Array();
	/*if(page!=""&&page!=undefined){
		pro_array.push("page="+page);
	}*/
	if(startTime != "" && startTime!=null){
		pro_array.push("startTime="+startTime);
	}
	if(endTime != ""&& endTime!=null){
		pro_array.push("endTime="+endTime);
	}
	if(supplierId!= ""&& supplierId!=null){
		pro_array.push("supplierId="+supplierId);
	}
	$.ajax({
		type : "post", 
		url : "../supplier/getlookTradingFlow",
		data:pro_array.join("&"),
		dataType:"html",
		success : function(msg) { 
			$("#c3").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert(errorThrown);
			alert("对不起，数据异常请稍后再试555!");
		}
	}); 


}
function coupon(){
	
	var memo=$("#memo").val();
	var supplierId="${supplierId}";
	
	var coupon=$("#coupon").val();
	var decimal="${decimal }";
	var pro_array  = new Array();
	if(coupon != "" && coupon!=null){
		pro_array.push("coupon="+coupon);
	}
	if(memo != ""&& memo!=null){
		pro_array.push("memo="+memo);
	}
	if(supplierId!= ""&& supplierId!=null){
		pro_array.push("supplierId="+supplierId);
	}
	if(decimal!= ""&& decimal!=null){
		pro_array.push("decimal="+decimal);
	}
	$.ajax({
		type : "post", 
		url : "../supplier/rechargeCouponYes",
		data:pro_array.join("&"),
		dataType:"html",
		success : function(msg) { 
			alert("充值成功");
			location.reload(true);
		},
		error: function(jqXHR, textStatus, errorThrown){
			//alert(errorThrown);
			alert("对不起，充值失败");
		}
	}); 
}
</script>
<body>
		<div id="c3" class="c2">
	
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/zh/include/header.jsp"%>
	<%@include file="/WEB-INF/views/zh/include/leftProduct.jsp" %>
	<!-- 导航 end -->
	<input type="hidden" name="dpro" id="dpro" value="${dpro}">
	<input type="hidden" name="page" id="page" value="${page}">
		<div id="c3" class="c2">
			<div class="c21">
				<div class="title">
					<p>运营商中心&nbsp;&gt;&nbsp;</p>
					
					<p class="c1">充值优惠券</p>
				</div>
			</div>
      <input type="hidden"  id="supplierId1" name="supplierId1" value="${sb }"> 
      
		<div class="xia"  style="min-height: 100px;">
			<p class="p6">
				<br /> <br />  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span>对方账户:</span> 
				<input  id="name" disabled="disabled" value="${name}">
					<input type="hidden" name="page" id="page" />
			</p>
				<p class="p6">
				<br /> <br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span>对方账户余额:</span> 
				<input  id="decimal2" disabled="disabled" value="${decimal2 }">	
			</p>
			<p class="p6">
				<br /> <br />  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span>充值:</span> <input type="text" id="coupon" class="rl" name="coupon"
					value="" />请输入充值优惠券数量！
					<input type="hidden" name="page" id="page" value="${page}">
			</p>
				<p class="p6">
				<br /> <br />  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span>备注:</span>
					<input type="text" value=""
								style="width: 200px; height: 50px" id="memo" name="memo"/>
			</p>
			<p class="p3" style="">
					<button type="button" onclick="coupon()">确定</button>
				
				</p>
			<c:if test="${supplier.type!=1601}">	
			<p class="p6">
				<br /> <br /> <br /> <br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span>当前优惠券余额:</span> ${decimal }
			</p>
				</c:if>	
			<br /> <br />
	
		</div>

		<div class="blank10">
	    </div>
    <div class="blank">
   
    
    </div>
		<!--中间左边开始-->
		<!-- 左边 start -->

<%-- 			<%@include file="/WEB-INF/views/zh/dealerseller/leftPage.jsp"%> --%>

		<!-- 左边 end -->
		<!--中间左边结束-->
		<div class="alert_user3" style="display: none;">
			<div class="bg"></div>
			<div class="w">
				<div class="box1">
					<img src="${path}/images/close.jpg" class="w_close">
				</div>
				<div class="box3">
					<p id="showmsgplat"></p>
				</div>
				<div class="blank20"></div>
			</div>
		</div>


	</div>

	<div class="blank10"></div>




<div class="lightbox" id="copyData" style="display:none">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		
	</div>
</div>
	
	</div>
</body>
</html>