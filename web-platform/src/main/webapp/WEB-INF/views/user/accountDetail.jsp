<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<% response.setHeader("cache-control","public"); %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-平台统计管理</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/kuc1.css"/>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>
	<style type="text/css">
	</style>
	
	<script type="text/javascript">
		function toGetList(){
			$("#SearchFrom").submit();
		}
		$(function(){
			var type='${type}'; 
			if(type == 1){
				$(".type1").css('background','#ffffff');
			}else if(type == 2){
				$(".type2").css('background','#ffffff');
			}else if(type == 3){
				$(".type3").css('background','#ffffff');
			}else if(type == 4){
				$(".type4").css('background','#ffffff');
			}
		});
	</script>
</head>
<body>

 <%@include file="/WEB-INF/views/include/header.jsp"%>
	
	<div class="center">
		<!-- 左边 start -->
		<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		<!-- 左边 end -->
		<div class="c2" style="margin-top:10px;">
			<div class="c21">
			<div class="title">
				<p>基础设置&nbsp;>&nbsp; </p>
				<p class="c1">账目明细</p>
			</div>
			</div>
			<div class="blank10"></div>

			<div class="c22">
			    <div class="c21">
						<ul class="top">
							<li id="supplier"><a class="type1" href="${path}/platform/toAccountDetail?type=1&userId=${userId}&userName=${userName}" >可用分红额度</a></li>
							<li id="supplier"><a class="type2" href="${path}/platform/toAccountDetail?type=2&userId=${userId}&userName=${userName}" >消费红旗券</a></li>
							<li id="supplier"><a class="type3" href="${path}/platform/toAccountDetail?type=3&userId=${userId}&userName=${userName}" >未到账红旗券</a></li>
							<li id="supplier"><a class="type4" href="${path}/platform/toAccountDetail?type=4&userId=${userId}&userName=${userName}" >可用红旗券</a></li>
						</ul>
				 </div>
				<div class="xia" style="height: 80px;">
					<form id="SearchFrom"  action="${path}/platform/toAccountDetail" method="post" >
						<input name="userId" value="${userId}" type="hidden">
						<input name="userName" value="${userName}" type="hidden">
						<input name="type" value="${type}" type="hidden">
						<p class="p1">
						    <strong>开始日期：</strong><input type="text" id="fromDate" name="fromDate" class="rl" onClick="WdatePicker()">
	                        &nbsp;&nbsp;
							<strong>结束日期：</strong>
							<input type="text" id="toDate" class="rl" name="toDate" onClick="WdatePicker()">
							<strong>订单编号：</strong>
							<input type="text" id="orderId" class="rl" name="orderId" >
						</p>
						</br>
						<p>
					    <button type="button" onclick="toGetList()" style="margin-left: 670px;">搜索</button>
						<a href="#" id="czhi" onclick="resetfm()">重置</a>
						</p>
					</form>
				</div>
			</div>
			<div class="blank10"></div>
			<div class="c24" id="c24">
				<table>
					<tr>
					
					 <th class="ar1">用户ID</th>
					 <th class="ar1">手机号</th>
					 <th class="ar1">消费金额</th>
					 <th class="ar1">消费红旗券</th>
					 <th class="ar1">可用红旗券</th>
					 <th class="ar1">未到红旗券</th>
					 <th class="ar2">分红额度</th>
					</tr>
					<tr>
					 	<td>
						 	${userId}
						</td>
					 	<td>
						 	${userName}
						</td>
					 	<td>
						 	${fn:escapeXml(fenhong)}
						</td>
						<td>${fn:escapeXml(hqq)}</td>
						<td>
			  	 			${kyHqq}
			 			 </td>
						<td>
			  	 			${noToHqq}
			 			</td>
						<td>
							${kyFenhong}
			 			</td>
					</tr>
				</table>
				<div class="blank10"></div>
				<table>
					<tr>
					 <th class="ar1">日期</th>
					 <th class="ar1">订单编号</th>
					 <th class="ar1">商品名称</th>
					 <c:if test="${type ne 3}">
						 <th class="ar1">用户ID</th>
					 </c:if>
					 <th class="ar1">金额(券)</th>
					 <c:if test="${type eq 4}">
					 	<th class="ar2">备注</th>
					 </c:if>
					</tr>
					<c:if test="${empty page.result}">
						<tr>
							<td colspan="10">
								<center><img src="${path }/commons/images/no.png" /></center>
							</td>
						</tr>
					</c:if>
					<c:forEach items="${page.result}" var="data">
						<tr>
							<td>
								${data.date}
							</td>
						 	<td>
							 	${fn:escapeXml(data.orderId)}
							</td>
							<td>
								<c:forEach items="${data.pNameList}" var="goods" varStatus="id">
									 ${id.index + 1}、${goods};
								</c:forEach>
							</td>
							<c:if test="${type ne 3}">
								<td>
					  	 			${data.userId}
					 			</td>
				 			</c:if>
							<td>
				  	 			${data.price }
				 			</td>
				 			<c:if test="${type eq 4}">
								<td>
									${data.memo }
					 			</td>
				 			</c:if>
						</tr>
					</c:forEach>
				</table>
				</table>
				<c:if test="${!empty page.result}">
					<%@include file="/WEB-INF/views/include/page.jsp"%>
				</c:if>
			</div>
		</div>
	</div>
	 <div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
<script type="text/javascript">
</script>
<script type="text/javascript">
</script>
</html>