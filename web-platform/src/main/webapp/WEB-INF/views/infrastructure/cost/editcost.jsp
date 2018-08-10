<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>货物费用名称设置</title>
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
				<p class="c1">货物费用名称</p>
			</div>
	     </div>
	      <div class="btn">
	     	<a href="javascript:void(0)" onclick="saveCost()">保存</a>
	     	<a href="javascript:void(0)" onclick="backCostg()">放弃</a>
	     </div>
	    
<form action="" id="savecostForm">
		<div class="pu_wrap">
		 	<table class="pu">
				<tbody>
					<tr class="order-hd">
					
						<th width="200px">状态</th>
						<th width="340px">货物费用名称</th>
						<th width="500px">说明</th>
					</tr>	
			        <tr class="order-bd">
			         <td>
			         <input type="hidden" name="sid" value="${cost.sid}">
			         <input type="radio" class="it" name="status" value="1" checked="true">启用 
			         <input type="radio" class="it" name="status" value="0">禁用</td>
					<td width="340"><input type="text" class="it" name="costName" value="${cost.costName}" id="costName" style="width:200px;" > </td>
					<td  width="500"><input type="text" class="it" name="remark" value="${cost.remark}" id="remark" style="width:360px;"></td>
					</tr>
		       </tbody>
		   </table>
	   </div>
	   </form>
	</div>
</div>
</body>
</html>