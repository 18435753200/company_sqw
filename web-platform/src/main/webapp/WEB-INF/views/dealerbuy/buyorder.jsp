<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-买家中心-已合并订单</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path %>/commons/css/maidao.css"/>
	<script type="text/javascript">
	//初步加载此项目这个页面时
	$(document).ready(function(){
			$.ajax({ 
			     async:false,
			     type : "post", 
			     url : "<%=path%>/buyOrder/findBuyOrder", 
			     dataType:"html",
			     success : function(waitOrder) {
			     	$(".c3").html(waitOrder);
				 },
				 error:function(jqXHR,textStatus,errorThrown){
				  	alert("网络异常,请稍后再试。。。。");
				 }
		     });
		});
	
	</script>
</head>
<body id="CS5">
<%@include file="/WEB-INF/views/include/header.jsp"%>
	<!-- 导航 start -->
	<div class="blank"></div>
	<!-- 导航 end -->
	<div class="center" style="margin-top:10px;">
		<%@include file="/WEB-INF/views/dealerbuy/basePage/maiLeft.jsp"%>
	 <!-- 左边 end -->
	 <%@include file="/WEB-INF/views/dealerbuy/basePage/fendanList.jsp"%>
	 <!--左边右边-->
	 <div class="c2">
			<div class="c21">
				<div class="title">
					<p>我是买家&nbsp;>&nbsp; </p>
					<p>订单管理&nbsp;>&nbsp; </p>
					<p class="c1">已合并订单</p>
				</div>
			</div>
			<div class="blank10"></div>
			<div class="c22">
			<!-- 搜索条件开始 -->
				<div class="xia">
						<p class="p1">
							<strong>采购单号 ：</strong> <input type="text" class="text1" name="orderId"  id="orderId">
							<strong class="st">下单时间 ：  </strong>
							<span>
								<input type="text" class="text1" name="createTime" id="createTime"  onClick="WdatePicker()" value="${createTime }">
								<span> 到 </span>
								<input type="text" class="text1" id="endTime" name="endTime" onClick="WdatePicker()" value="${endTime }">
							</span>
						</p>
						<p class="p2">
							<strong>商品名称 ：</strong> <input type="text" class="text1" name="pName"  id="pName" value="${pName }">
							<strong class="st">订单状态 ：</strong> 
								<select id="status" name="status" class="text1">
								    <option value="0"  <c:if test='${status == "0"}'>  selected='selected'  </c:if>>全部</option>
									<option value="1"  <c:if test='${status == "1"}'>  selected='selected'  </c:if>>已合单</option>
									<option value="21"  <c:if test='${status == "21"}'>  selected='selected'  </c:if>>已分配</option>
									<option value="31"  <c:if test='${status == "31"}'>  selected='selected'  </c:if>>已提交采购单</option>
									<option value="32"  <c:if test='${status == "32"}'>  selected='selected'  </c:if>>经销商已填写合同</option>
									<option value="33"  <c:if test='${status == "33"}'>  selected='selected'  </c:if>>供应商已填写合同</option>
									<option value="34"  <c:if test='${status == "34"}'>  selected='selected'  </c:if>>经销商已确认合同</option>
									<option value="62"  <c:if test='${status == "62"}'>  selected='selected'  </c:if>>供应商已确认合同</option>
									<option value="71"  <c:if test='${status == "71"}'>  selected='selected'  </c:if>>供应商已发货</option>
									<option value="81"  <c:if test='${status == "81"}'>  selected='selected'  </c:if>>经销商已收货</option>
								</select>
						</p>
						<p class="p3">
							<strong>供应商名称 ：</strong> <input type="text" class="text1" name="supplyName" id="supplierId" value="">
						</p>
						<p class="p4">
							<button onclick="byConditionSearchBuy(1)" type="button">搜索</button>
							<a href="#" id="czhi" onclick="resetfm()">重置</a>
						</p>
				</div>
			<!-- 搜索条件结束 -->
			<!-- 检索结果开始 -->
				<div class="c3">
			    </div>
     </div>
  </div>
</div>
<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	<!-- 底部 end -->
<%-- <script src="<%=path %>/commons/js/my97/calendar.js"></script> --%>
<script src="<%=path %>/commons/js/my97/WdatePicker.js"></script>
<script type="text/javascript">
//条件检索订单

	function byConditionSearchBuy(page){
		var orderId = $("#orderId").val();
		var createTime = $("#createTime").val();
		var endTime = $("#endTime").val();
		var pName = $("#pName").val();
		var supplyName = $("#supplierId").val();
		var status = $("#status").val();
		var buyord_array = new Array();
		//条件一订单Id
		if($.trim(orderId)!=null || $.trim(orderId) !=""){
			buyord_array.push("orderId="+$.trim(orderId));
		}
		//添加2-成交时间
		if($.trim(createTime)!=null || $.trim(createTime) !=""){
			buyord_array.push("createTime="+$.trim(createTime));
		}
		//天剑3-结束时间
		if($.trim(endTime)!=null || $.trim(endTime) !=""){
			buyord_array.push("endTime="+$.trim(endTime));
		}
		//条件4-商品名称
		if($.trim(pName)!=null || $.trim(pName) !=""){
			buyord_array.push("pName="+$.trim(pName));
		}
		//条件5-供应商名称
		if($.trim(supplyName)!=null || $.trim(supplyName)!=""){
			buyord_array.push("supplyName="+$.trim(supplyName));
		}
		//条件6-订单状态
		if(status!=null){
			buyord_array.push("status="+status);
		}
		//条件7-页码
		buyord_array.push("page="+page);
		
		var  matchnum = /^[0-9]*$/;
		    if(!matchnum.test($.trim(orderId))){
		     		alert("订单ID只能是数字！");
		     		 $("#orderId").val("");
		     		 $("#orderId").focus();
		     		 return false;
		     	}
			if($.trim(createTime)!=""&&$.trim(endTime)!=""){
				if($.trim(createTime)>=$.trim(endTime)){
					alert("开始时间不能大于结束时间！");
					return false;
				}
				
				if($.trim(createTime)>new Date()){
					alert("填入时间已经大于系统时间");
					return false;
				}
			}
	
			//alert(new Date()+"系统时间");
		 $.ajax({ 
			     async:false,
			     type : "post", 
			     url : "<%=path%>/buyOrder/findBuyOrder", 
			     data:buyord_array.join("&"),
			     dataType:"html",
			     success : function(waitOrder) {
			     	$(".c3").html(waitOrder);
			     },
				 error:function(jqXHR,textStatus,errorThrown){
				   alert("网络异常,请稍后再试。。。。");
				 }
		  }); 
	}	
		//点击分单时显示的经销商列表
 		function fendan(proId,orderId){
 			$.ajax({ 
				async:false,
				type : "post", 
				url : "<%=path%>/buyOrder/findDealerByPid",
				data: {"proId":proId,"orderId":orderId},
				dataType:"html",
				success : function(json) {
				$("#dealer").html(json);
				$(".alert_bj").show();	
				},
				error:function(jqXHR,textStatus,errorThrown){
					alert("网络异常,请稍后再试。。。。");
				}
			});
		}
		//关闭列表
		function close1(){
			$(".alert_bj").hide();
		}
		function close2(){
			$(".alert_bj").hide();
		}
		
		function queRenShouHuo(){
		var poId;
		$("#poId").val();	
		alert("订单编号"+$("#poId").val());
		}
		//UNICORN完成分单功能
		
		
		
		function fendan1(orderId){
		//点击分单时显示的经销商列表
		var dealerId = $('input:radio[name="dealerId"]:checked').val();
		if(dealerId!=null){
			$.ajax({ 
				async:false,
				type : "post", 
				url : "<%=path%>/buyOrder/divideOrder",
				data: {"dealerId":dealerId,"orderId":orderId},
				success : function(json) {
					tipMessage(json,function(){
	            		window.location.href="<%=path %>/buyOrder/buyOrder";
	            	});
				},
				error:function(jqXHR,textStatus,errorThrown){
					alert("网络异常,请稍后再试。。。。");
				}
			});
		}else{
		 		alert("请务必勾选要分配的经销商");	
		 	}
		 }
		
</script>
</body>
</html>