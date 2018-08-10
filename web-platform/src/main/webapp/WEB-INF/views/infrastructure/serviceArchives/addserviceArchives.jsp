<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>服务商档案</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/currenry.css"> 
    <script type="text/javascript" src="${path }/commons/js/infrastructure/infrastructure.js"></script>
</head>
<body>
<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<!-- 导航 end -->
    <div class="blank"></div>
	<div class="center">
	   <div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>

	 <div class="right">
	 	<div class="c21">
			<div class="title">
				<p>卖家中心&nbsp;&gt;&nbsp; </p>
				<p>基础设置&nbsp;&gt;&nbsp; </p>
				<p class="c1">服务商档案</p>
			</div>
	     </div>
	      <div class="btn">
	     	<a href="javascript:void(0)" onclick="gosumitServiceArchives()">保存</a>
	     	<a href="javascript:void(0)" onclick="backServiceArchives()">放弃</a>
	     </div>
	    
<form action="" id="serviceArchivesForm">
		<div class="pu_wrap">
		 	<table class="pu">
				<tbody>
					<tr class="order-hd">
					<th width="236px">服务商名称</th>
						<th width="236px">联系人</th>
							<th width="200px">联系电话</th>
							<th width="200px">说明</th>
					</tr>	
			        <tr class="order-bd">
			        	<td><input type="text" class="it" name="serviceName" placeholder="请填写服务商名称" id="serviceName"> </td>
						<td><input type="text" class="it" name="serviceContact" placeholder="请填写联系人" id="serviceContact"> </td>
						<td><input type="text" class="it" name="servicePhone" placeholder="请填写联系电话" id="servicePhone"></td>
						<td><input type="text" class="it" name="serviceComment" placeholder="请填写说明" id="serviceComment"></td>
					</tr>
		       </tbody>
		   </table>
	   </div>
	   </form>
	</div>
</div>
</body>
</html>