<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>币别设置</title>
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
				<p class="c1">币别设置</p>
			</div>
	     </div>
	      <div class="btn">
	     	<a href="javascript:void(0)" onclick="gosumitCurrency()">保存</a>
	     	<a href="javascript:void(0)" onclick="backCurrency()">放弃</a>
	     </div>
	    
<form action="" id="currencyForm">
		<div class="pu_wrap">
		 	<table class="pu">
				<tbody>
					<tr class="order-hd">
					<th width="236px">简码</th>
						<th width="236px">币种</th>
							<th width="200px">汇率</th>
							<th width="200px">本位币标识</th>
					</tr>	
			        <tr class="order-bd">
			        	<td><input type="text" class="it" name="code" placeholder="请填写币种简码"> </td>
						<td><input type="text" class="it" name="currencyType" placeholder="请填写币种"> </td>
						<td><input type="text" class="it" name="exchangeRate" placeholder="请填写汇率"></td>
						<td><select class="sm" name="loaclMoney">
					<option value="1">是</option>
					<option value="0">否</option>
						</select></td>
					</tr>
		       </tbody>
		   </table>
	   </div>
	   </form>
	</div>
</div>
</body>
</html>