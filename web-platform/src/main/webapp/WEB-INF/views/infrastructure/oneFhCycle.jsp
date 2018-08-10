<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
<title>个人周期分红设置</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/B2C_orderlist.css">
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/wuliu_alert.css"/>
<script type="text/javascript" src="${path }/commons/js/oneFhCycle.js"></script>
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
				<p class="c1">个人周期分红设置</p>
			</div>
	     </div>
			<div class="blank5"></div>
			<div class="cont" style="height: 400px; padding-top: 140px;">
			<input type="button" style="width: 200px; height: 60px;" class="fabu_btn" onclick="labelPage(1)" value="创建计划" ></input>
			<input type="button" style="width: 200px; height: 60px;" class="fabu_btn" onclick="findFhList()" value="查询计划" ></input>
				
		</div>	
		<div class="cont" id="tab_plan" style="width: 100%">
		
			</div>		 
		<!-- 右边 end -->
	</div>

</div>
<div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
   </body>
<script type="text/javascript">
 
</script>
</html>