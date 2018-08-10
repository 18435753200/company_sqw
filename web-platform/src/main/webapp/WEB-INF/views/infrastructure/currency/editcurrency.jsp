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
	     	<a href="javascript:void(0)" onclick="saveCurrency()">保存</a>
	     	<a href="javascript:void(0)" onclick="backCurrency()">放弃</a>
	     </div>
	    
<form action="" id="savecurrencyForm">
		<div class="pu_wrap">
		 	<table class="pu">
				<tbody>
					<tr class="order-hd">
						<th width="200px">状态</th>
						<th width="236px">简码</th>
						<th width="236px">币种</th>
						<th width="200px">汇率</th>
						<th width="200px">本位币标识</th>
					</tr>	
			        <tr class="order-bd">
			         <td>
			         <input type="hidden" name="sid" value="${currency.sid}">
			         <c:if test="${currency.loaclMoney eq 1}">
				         <label class="lable"><input type="radio" class="it yesit" name="status" value="1" checked="checked" >启用</label>
				         <label class="lable"><input type="radio" class="it noit" name="status" value="0" disabled="disabled" >禁用</label></td>
			         </c:if>
			         <c:if test="${currency.loaclMoney eq 0}">
				         <input type="radio" class="it yesit" name="status" value="1" checked="checked" >启用 
				         <input type="radio" class="it noit" name="status" value="0" >禁用</td>
			         </c:if>
			         
			        <td><input type="text" class="it" name="currencyCode" value="${currency.code}" readonly="readonly"> </td>
					<td><input type="text" class="it" name="currencyType" value="${currency.currencyType}" > </td>
					<td><input type="text" class="it" name="exchangeRate" value="${currency.exchangeRate}"></td>
					<td>
						<input type="hidden" class="it" id="loaclMoney" value="${currency.loaclMoney}">
						<select class="sm" name="loaclMoney" onchange="test();">
						<c:if test="${currency.loaclMoney==1}">
						<option value="1" selected="selected">是</option>
						<option value="0">否</option>
						</c:if>
						<c:if test="${currency.loaclMoney==0}">
						<option value="1">是</option>
						<option value="0" selected="selected">否</option>
						</c:if>
						</select>
					</td>
					</tr>
		       </tbody>
		   </table>
	   </div>
	   </form>
	</div>
</div>
</body>
</html>