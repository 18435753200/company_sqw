<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>销售渠道</title>
	<link rel="stylesheet" type="text/css" href="css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="css/currenry.css">
</head>
<body>
	<div class="center">
		
		<div class="right">

			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>基础设置&nbsp;&gt;&nbsp; </p>
					<p class="c1">销售渠道</p>
				</div>
	       </div>

			<div class="btn">
				<a href="#">新增</a>
				<a href="#">修改</a>
				<a href="#">保存</a>
				<a href="#">放弃</a>
			</div>
			
			<div class="pu_wrap">
				<table class="pu">
					<tr class="order-hd">
						<th width="40px">序</th>
						<th width="250px">渠道名称</th>
						<th width="50px">启用</th>
						<th width="200px">渠道减价率(%)</th>
						<th width="300px">说明</th>
					</tr>
					<tr>
						<td>1</td>
						<td>商贸渠道一类（代理商/经销商）</td>
						<td><input type="checkbox"></td>
						<td>62.50</td>
						<td>此类商户为在鑫网进货后供大\中型连锁超市、商城、便利店</td>
					</tr>
				</table>
			</div>

		</div>

	</div>
</body>
