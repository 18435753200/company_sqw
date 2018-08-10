<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>UNICORN_海外直邮订单管理</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/B2C_orderlist.css">
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/wuliu_alert.css"/>
	<script type="text/javascript" src="${path}/commons/js/order_js/overseas_order.js"></script>
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$(".lyan").live("hover",onMouseOver);
		$(".lyan").live("mouseleave",onMouseLeave);
		$("#popSupplierLable").hide();
		$("#popSupplier").hide();
		$("#supplyType").bind("change", supplyTypeChange);
		$.ajax({ 
		     async:false,
		     type : "post", 
		     url : "../customerOrder/getOverseasOrderPageBeanByCondition", 
		     data:{supplyType:11},
		     dataType:"html",
		     success : function(customerOrder){
		     	$(".tab-bd").html(customerOrder);
			 },
			 error:function(jqXHR,textStatus,errorThrown){
			  	alert("网络异常,请稍后再试。。。。");
			  }
	     }); 
		
		loadSupplyStore();
	});

	function toPage(page){
		byConditionSearchCustomerOrder(page);
	}
	function onMouseOver(){
		var td = $(this);
		td.parent(".tcolr").next().show();
	}
	
	function onMouseLeave(){
		var td = $(this);
		td.parent(".tcolr").next().hide();
	}
	
	</script>
</head>
<body>
<style>
.wlgs{clear: right;margin-top:10px; max-height:100px; overflow-y: auto;}
#wuliu td{ border:1px solid #ccc; padding:5px; text-align:center}
.addresss p{font-size:13px; color:#333; margin-top:5px;} 
.ecwl{width:300px;  background:#fff;  position: fixed; top: 30%;    left: 50%;
    z-index: 100; margin-left:210px; padding:10px;     display: none;   border-radius: 5px;    border: 5px solid #f5f5f5;}
.ecwl p{ font-size:13px; color:#333; margin-bottom:8px; }  
.ecwl-but{text-align: center;    margin-top: 10px;} 
.ecwl-but button{padding:2px 10px;} 
</style>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="center">
	<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
	<div class="right">
	
	<!-- 补录物流信息开始 -->
  	<div class="logw">
		<div class="bg"></div>
		<div class="log-c" style="top:20%;left:50%; margin-left:-195px;">
			<div class="log-c1">
				<h4>补录物流信息</h4><img src="${path }/commons/images/close.jpg" class="w_close">
			</div> 
			<div class="log-c2" style="height: inherit;">
				<div class="item">
					<span>物流商：</span>
					<select id="logistic" class="">
				</select>
				</div>
				<div class="item">
					<span>物流单号:</span>
					<input type="text" id="logisticCode" class="">
				</div>
				<button type="button" class="bt1" onclick="addwuliu()" style="float: right;">添加</button>
				<input type="hidden" value="" id="shipOrderId">
				<div class="item">
					<i class="error" style="float:none;padding-left:0px;"></i>
					<!-- <i class="pass">通过</i> -->
				</div>
				<div class="addresss">
					<p>收货人：<span id="receiveNameField"></span></p>
					<p>联系电话：<span id="receivePhoneField"></span></p>
					<p>收货地址：<span id="receiveAddressField"></span></p>
				</div>
				<div class="wlgs" >
					<table id="wuliu">
						<tr>
							<td style="width: 100px;">物流公司</td><td style="width: 150px;">运单号</td><td style="width: 100px;">操作</td>
						</tr>
					</table>
				</div>
				
						
							
				</div>
			
			<div class="log-c3">
				<button type="button" class="bt1" onclick="goCheckShipOrderLogistic()">确定</button>
				<button type="button" class="bt2">取消</button>
			</div>
		</div>
	</div>
	<!-- 补录物流信息结束 -->
	
	<div id="addDiv" class="alert_user2" style="">
		<div class="bg"></div>
		<div class="w">
			<div class="box1" onclick="closeUpdateXinxi()">
				<img src="../commons/images/close.jpg" class="w_close">
			</div>
			<div class="box3">
			   <h2>修改订单状态</h2>
				<p>
				<span class="sh">订单状态：
				
				 </span>
				 <select id="upStatus">
				<option value="0">请选择</option>
				<option value="21">已支付</option>
				<option value="41">待发货</option>
				<option value="67">海关审核订单失败(待退款)</option>
				<option value="68">海关支付单审核失败(待退款)</option>
				<option value="69">海关物流单审核失败(待退款)</option>
				<option value="70">海关审核失败(待退款)</option>
				<option value="79">已退款</option>
				<option value="81">已发货</option>
				<option value="91">已收货</option>
				<option value="99">取消</option>
				</select>
				</p>
				<p><button type="button" class="bt1" onclick="updateB2cOrderStatus()">确定</button></p>
				<input type="hidden" id="upOrderId">
				<br/>
			</div>
			
		</div>
	</div>
	
	
	
		<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>订单管理&nbsp;&gt;&nbsp; </p>
					<p class="c1">海外直邮订单管理</p>
				</div>
		</div>
			
		<div class="xia" style="height:246px">
		<form action="">
		
		
			<p class="p1">
				<span>订单编号 :</span><input type="text" id="orderId">
				<span>下单时间 :</span>
				 <input type="text" id="startTime" class="rl" onClick="WdatePicker()"> <i>至</i>
				 <input type="text" id="endTime" class="rl" onClick="WdatePicker()">
			</p>
			<p class="p1">
				<span>商品名称 :</span><input type="text" id="pName">
				<span>买家昵称 :</span><input type="text" id="userName">
				<span>收货人 :</span><input type="text" id="receiveName">
			</p>
			<p class="p1">
				<span>订单状态 :</span>
				<!-- 67,68,69 -->
				<select id="status">
				   <option value="">全部</option>
			       <option value="1">待支付</option>
			       <option value="21">已支付</option>
			       <option value="31">代海关审核</option>
			       <option value="41">待发货</option>
			       <option value="67">海关审核订单失败(待退款)</option>
			       <option value="68">海关支付单审核失败</option>
			       <option value="69">海关物流单审核失败</option>
			       <option value="70">海关审核失败</option>
			       <option value="79">已退款</option>
			       <option value="81">待收货</option>
			       <option value="91">已收货</option>
			       <option value="93">拒收货</option>
			       <option value="99">用户取消</option>
			       <option value="100">自动取消</option>
					<option value="33">取消中</option>
					<option value="98">已退款</option>
				</select>
				<span>平台类型 :</span>
				<select id="orderPlatform">
				<option value="">全部方式</option>
				 <option value="1">PC</option>
				 <option value="2">ANDROID</option>
				 <option value="3">WAP</option>
				 <option value="4">IOS</option>
				 <option value="6">5S</option> 
				 </select>
			</p>
			<p class="p1">
			     <span>配送方式 :</span>
				 <select id="shipType">
				     <option value="">全部</option>
				     <option value="0">普通</option>
				     <option value="1">自提</option>
				     <%--<option value="2">货到付款</option>--%>
				 </select>
			     <span>支付方式 :</span>
				 <select id="payType">
				     <option value="">全部</option>
				     <option value="0">线上</option>
				     <option value="1">货到付款</option>
				 </select>
				 <span>订单类型 :</span>


				 <select id="orderType">
				 	 <option value="">全部</option>
					 <c:forEach items="${_orderTypeList}" var="__orderTypeList">
						 <option value="${__orderTypeList.value}">${__orderTypeList.key}</option>
					 </c:forEach>
				 </select>

			</p>
			<p class="p1">
				<span id="popSupplierLable">POP商家 :</span>
				<select id="popSupplier">
					<option value="">全部</option>
				</select>
				<span>订单来源 :</span>
				<select id="orderSource">
					<option value="">全部</option>
					<c:forEach items="${_orderSourceList}" var="__orderSourceList">
						<option value="${__orderSourceList.key}">${__orderSourceList.value}</option>
					</c:forEach>
				</select>
			</p>
			</form>
			<p class="p2">
				<button type="button" onclick="byConditionSearchCustomerOrder(1)">搜索</button>
				<a href="javascript:void(0)" id="czhi" onclick="resetfm()">重置</a>
				<button onclick="exportCustomerOrderExcel()" type="button" class="dc-btn">导出</button>
			</p>
		</div>
		<div class="tab-bd">
		
		</div>
	</div>
</div>
<!--二次确认物流信息 -->
<div class="ecwl">
	<div class="wl"></div>
	<div class="addresss" style="margin-top: 20px;">
		<p>---------------------------------------</p>
		<p>收货人：<span id="receiveNameField1"></span></p>
		<p>联系电话：<span id="receivePhoneField1"></span></p>
		<p>收货地址：<span id="receiveAddressField1"></span></p>
	</div>
	<div class="ecwl-but">
				<button type="button"  onclick="goUpdateOverseasOrderLogistic()">确定</button>
				<button type="button" onclick="close1()">取消</button>
	</div>
</div>
<%@include file="/WEB-INF/views/include/foot.jsp" %>	
</body>
</html>