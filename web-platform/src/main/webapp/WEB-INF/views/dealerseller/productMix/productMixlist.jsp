<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-情景组合列表</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path }/commons/css/comm.css"/>
    <link href="/web-platform/commons/js/my97/skin/WdatePicker.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${path }/commons/js/productMix_list_fn.js"></script>
    <script src="${path}/commons/js/my97/WdatePicker.js"></script>
    <script type="text/javascript">
    	$(document).ready(function() {
			clickSubmit(1);
		});
    </script>

</head>
<body>
	
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
    <div class="blank"></div>

	<div class="center">
		<!--中间左边开始-->
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->
		<!--中间左边结束-->
		<div class="alert_user3" style="display: none;">
			<div class="bg"></div>
			<div class="w">
				<div class="box1">
					<img src="${path}/commons/images/close.jpg" class="w_close">
				</div>
				<div class="box3">
					<p id="showmsgplat"></p>
				</div>
				<div class="blank20"></div>
			</div>
		</div>
		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">情景组合列表</p>
				</div>
			</div>
			<div class="blank10"></div>

			<div class="pu_hd">
				<h3>查询条件:</h3>
			</div>
			<div class="xia">
				<p class="p1 mr">
					<span>商品情景组合名称：</span>
					<input type="text"  id="sceneName"> 
				</p>
				<p class="p1">
					<span>创建日期:</span>
					<input type="text" class="search-text" onClick="WdatePicker()" readonly="readonly" id="startDate"> 
					<i>至</i>
					 <input type="text" class="search-text" onClick="WdatePicker()" readonly="readonly" id="endDate">
				</p>
				
				<p class="p2">
					<button type="submit" id="subfm" onclick="clickSubmit()">查询</button>
				</p>
			</div>


			<div class="pu_wrap" >
				<div class="pu_hd">
				   <h3>商品情景组合列表:</h3>
					<div class="btn">
						<a href="${path}/productMix/toCreat">新建情景组合</a>
					</div>
				</div>

			 <div id="pu_wrap"></div>

			</div>
			
			
			
			
			
		</div>
	</div>




	<div class="blank10"></div>
	 <!-- 底部 start -->
		<div class="t_footer">
				<h1 class="logo_dl"></h1>
		</div>
		<!-- 底部 end -->
		
</body>
</html>