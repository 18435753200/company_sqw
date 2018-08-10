<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	
	<meta charset="UTF-8">
	<title>UNICORN-促销管理</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
    <script src="${path}/commons/js/my97/WdatePicker.js"></script>
    <script src="${path}/commons/js/promotion_list_fn.js"></script>
    <link rel="stylesheet" type="text/css" href="${path }/commons/css/promotionsList.css"/>
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<!-- 导航 end -->

<div class="center">
	<!-- 左边 start -->
	<div class="left f_l">
		<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
	</div>
	<!-- 左边 end -->
	<div class="c2">
		<div class="c21">
			<div class="title">
				<p>卖家中心&nbsp;&gt;&nbsp; </p>
				<p>促销管理&nbsp;&gt;&nbsp; </p>
				<p class="c1">促销列表</p>
			</div>
		</div>
		<div class="blank10"></div>
		<div class="top">
			<ul id="promotionstatu">
				<li class="list">
					<a href="javascript:void(0)" onclick="getProductByLabel(1)">进行中的促销</a>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="getProductByLabel(2)">已结束的促销</a>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="getProductByLabel(3)">未完成的促销</a>
				</li>
			</ul>
		</div>
		
		
		<div class="xia">
			<form>
				<p class="p1">
				 <strong>促销名称 ：</strong> <input type="text" class="text1" id="promotionName">
				 <strong class="st">促销ID ：</strong> <input type="text" class="text1" id="promotionId">
				 <strong class="st">促销类型 ：</strong> 
				 <select id="promotiontype">
					 <option value="0">请选择</option>
					 <option value="1">直降</option>
				 </select>
				</p>
				<p class="p1">
					<strong>开始时间 ：
					</strong>
						<input type="text" class="text1" onClick="WdatePicker()" id="startTime">
					<strong>结束时间 ：
					</strong>
						<input type="text" class="text1" onClick="WdatePicker()" id="endTime">
				</p>
				<p class="p3">
					<button type="button" onclick="clickSubmit()">搜索</button>
					<a href="#"  id="czhi" onclick="resetfm()">重置</a>
				</p>
			</form>
		</div>
		<div class="cont">
			<div class="qb" id="promotionTable">
				
			</div>
		</div>

	</div>



</div>

 <div class="blank10"></div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp"%>

<script type="text/javascript">
$(document).ready(function(){
   chushou();
  
});
var chushou=function(){
	$(".top li").each(function(index){
		$(this).click(function(){
			var i = $(this).index();
		    $(this).addClass('list').siblings().removeClass('list');
			$(".cont .qb:eq("+index+")").show().siblings().hide();
			
		});
	});
 };


</script>


</body>
</html>