<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>税率设置</title>
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
				<p class="c1">税率</p>
			</div>
	     </div>
	      <div class="btn">
	     	<a href="javascript:void(0)" onclick="gosumitTaxrate()">保存</a>
	     	<a href="javascript:void(0)" onclick="backTaxrate()">放弃</a>
	     </div>
	    
<form action="" id="taxrateForm">
		<div class="pu_wrap">
		 	<table class="pu">
				<tbody>
					<tr class="order-hd">
						<th width="340px">税率%</th>
						<th width="500px">说明</th>
					</tr>	
			        <tr class="order-bd">
						<td><input style="text-align:right;" type="text" class="it" name="tax" placeholder="请填写税率" id="tax"> </td>
						<td><input type="text" class="it" name="remark" placeholder="请填写税率的说明" id="remark" style="width:350px;"></td>
					</tr>
		       </tbody>
		   </table>
	   </div>
	   </form>
	</div>
</div>
</body>
</html>