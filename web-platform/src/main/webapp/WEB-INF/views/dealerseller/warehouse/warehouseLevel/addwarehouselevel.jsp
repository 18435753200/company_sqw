<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>库房级别</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/currenry.css"> 
    <script type="text/javascript" src="${path }/commons/js/order_js/warehouse_infrastructure.js"></script>
</head>
<body>
<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
		<div class="blank10"></div>
	<!-- 导航 end -->
    <div class="blank"></div>
	<div class="center">
	   <div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<div class="center">
	 <div class="right">
	 	<div class="c21">
			<div class="title">
				<p>卖家中心&nbsp;&gt;&nbsp; </p>
				<p>商品管理&nbsp;&gt;&nbsp; </p>
				<p class="c1">库房级别设置</p>
			</div>
	     </div>
	      <div class="btn">
	     	<a href="javascript:void(0)" onclick="gosumitWareHouseLevel()">保存</a>
	     </div>
	    
<form action="" id="wareHouseLevelForm">
		<div class="pu_wrap">
		 	<table class="pu">
				<tbody>
					<tr class="order-hd">
						<th width="340px">库房级别名称</th>
						<th width="500px">说明</th>
					</tr>	
			        <tr class="order-bd">
						<td><input type="text" class="it" name="levelName" placeholder="请填写库房级别名称" id="levelName"> </td>
						<td><input type="text" class="it" name="remark" placeholder="请填写库房级别的说明" id="remark"></td>
					</tr>
		       </tbody>
		   </table>
	   </div>
	   </form>
	</div>
</div>
</body>
</html>