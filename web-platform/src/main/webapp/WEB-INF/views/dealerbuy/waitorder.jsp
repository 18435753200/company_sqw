<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-买家中心-待合并订单</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="<%=path %>/commons/css/maidao3.css"/>	
	<script type="text/javascript">
	$(document).ready(function(){
			$.ajax({ 
			     async:false,
			     type : "post", 
			     url : "<%=path%>/waitOrder/waitSumitOrder", 
			     dataType:"html",
			     success : function(mess) {
			     	$("#c3").html(mess);
				 },
				 error:function(jqXHR,textStatus,errorThrown){
				  	alert("网络异常,请稍后再试。。。。");
				 }
		     });
		});
	</script>
</head>
<body id="CS2">
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<!-- 导航 end -->
	<div class="center" style="margin-top:10px;">
	 <!-- 左边 start -->
	 <%@include file="/WEB-INF/views/dealerbuy/basePage/maiLeft.jsp"%>
	 
		<%-- <div class="left f_l">
			<div class="title">
				<p class="f_l ff"><img src="<%=path %>/commons/images/img_t4.png" alt=""></p>
				<p class="f_l p1">买家中心</p>
				<p class="clear"></p>
			</div>
			<div class="list_box">
				<div class="demo">
					<h2><p class="p1"><img src="<%=path %>/commons/images/img_t2.png">订单管理</p></h2>
					<div class="p_b">
						<p class="p2"><a href="<%=path %>/buyOrder/buyOrder">已合并订单</a></p>
						<p class="p2 c_cut2"><a href="<%=path %>/waitOrder/SumitOrder">待合并订单</a></p>
					</div>
				</div>
			</div>
		</div> --%>
		<!-- 左边 end -->
	    <!--左边右边-->
	 <div class="c2" id="c2">
			<div class="c21">
				<div class="title">
					<p>我是买家&nbsp;>&nbsp; </p>
					<p>订单管理&nbsp;>&nbsp; </p>
					<p class="c1">待合并订单</p>
				</div>
			</div>
			<div class="blank10"></div>
			<div class="c22">
				<div class="xia">
				    <form>
						<p class="p2">
							<strong>商品名称 ：</strong> <input type="text" class="text1"  id="pName">&nbsp;&nbsp;&nbsp;&nbsp;
							<strong>供应商名称 ：</strong> <input type="text" class="text1" id="supplyName">
						</p>
						<p class="p4">
							<button type="button" id="sousuo" onclick="byConditionSearch(1)">搜索</button>
							<a href="#" id="czhi" onclick="resetfm()">重置</a>
						</p>
				   </form>  
				</div>
				<div class="c3" id="c3">
				
			    </div>
				<!-- 显示订单 -->
				
     </div>
  </div>
</div>
 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	<!-- 底部 end -->
</body>

<script type="text/javascript">
	//条件检索订单(默认加载全部)(2个条件pName-supplyName)
	function byConditionSearch(page){	
		var pName=$("#pName").val();
		var supplyName=$("#supplyName").val();
		var status=31;
		var waitord_array = new Array();
		
		if($.trim(pName)!=null || $.trim(pName)!=""){
			waitord_array.push("pName="+$.trim(pName));
		}
		
		if($.trim(supplyName)!=null || $.trim(supplyName)!=""){
			waitord_array.push("supplyName="+$.trim(supplyName));
		}
		
			waitord_array.push("status="+status);
			waitord_array.push("page="+page);
				$.ajax({ 
			     async:false,
			     type : "post", 
			     url : "<%=path%>/waitOrder/waitSumitOrder",
			     data:waitord_array.join("&"), 
			     dataType:"html",
			     success : function(mess) {
			     	$(".c3").html(mess);
				   },
				 error:function(jqXHR,textStatus,errorThrown){
				  	alert("网络异常,请稍后再试。。。。");
				  }
		     });  
	 }

	 //合并订单功能
	 function joinOrder(){
	 	var type=1;
	    var pid=$("#joinOrderBtn").val();
        window.location.href="<%=path%>/buyOrder/joinOrder?pid="+pid+"&type="+type;
	 }
</script>
</html>
