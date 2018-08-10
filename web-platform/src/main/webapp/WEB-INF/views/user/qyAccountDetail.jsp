<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<% response.setHeader("cache-control","public"); %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-企业统计管理</title>
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
			
			var hqqType='${hqqType}';
			if(hqqType == 1){
				$("#nHqqId").css('background','#ffffff');
			}else if(hqqType == 2){
				$("#yHqqId").css('background','#ffffff');
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
							<li id="supplier"><a class="type1" href="${path}/platform/toQyAccountDetail?type=1&userId=${userId}&supplierId=${supplierId}&hqqType=2" >商品返券明细</a></li>
							<li id="supplier"><a class="type2" href="${path}/platform/toQyAccountDetail?type=2&userId=${userId}&supplierId=${supplierId}&hqqType=2" >企业转账明细</a></li>
							<%-- <li id="supplier"><a class="type3" href="${path}/platform/toQyAccountDetail?type=3&userId=${userId}&supplierId=${supplierId}" >未到账红旗券</a></li>
							<li id="supplier"><a class="type4" href="${path}/platform/toQyAccountDetail?type=4&userId=${userId}&supplierId=${supplierId}" >可用红旗券</a></li> --%>
						</ul>
				 </div>
				<div class="xia" style="height: 1px;">
					<form id="SearchFrom"  action="${path}/platform/toQyAccountDetail" method="post" >
						<input name="supplierId" value="${supplierId}" type="hidden">
						<input name="type" value="${type}" type="hidden">
						<input name="hqqType" value="${hqqType}" type="hidden">
						<!-- <p class="p1">
						    <strong>开始日期：</strong><input type="text" id="fromDate" name="fromDate" class="rl" onClick="WdatePicker()">
	                        &nbsp;&nbsp;
							<strong>结束日期：</strong>
							<input type="text" id="toDate" class="rl" name="toDate" onClick="WdatePicker()">
						</p>
						</br>
						<p>
					    <button type="button" onclick="toGetList()" style="margin-left: 670px;">搜索</button>
						<a href="#" id="czhi" onclick="resetfm()">重置</a>
						</p> -->
					</form>
				</div>
			</div>
			<div class="blank10"></div>
			<div class="c24" id="c24">
				<table>
					<tr>
						<c:if test="${type eq 1}">
							 <th class="ar1" id="yHqqId"><a href="${path}/platform/toQyAccountDetail?type=1&userId=${userId}&supplierId=${supplierId}&hqqType=2">已到账红旗券</a></th>
							 <th class="ar1" id="nHqqId"><a href="${path}/platform/toQyAccountDetail?type=1&userId=${userId}&supplierId=${supplierId}&hqqType=1">未到账红旗券</a></th>
						</c:if>
						<c:if test="${type eq 2}">
							 <th class="ar1">红旗券转账总额</th>
						</c:if>
					</tr>
					<tr>
						<c:if test="${type eq 1}">
								<td>${qyAccount.hqBalance}</td>
								<td>${qyAccount.hqNovalid}</td>
							</c:if>
							<c:if test="${type eq 2}">
								 <td>${qyAccount.hqTransOut}</td>
							</c:if>
						</tr>
					</tr>
				</table>
				<div class="blank10"></div>
				<table style="table-layout: fixed;">
					<tr>
						<c:if test="${type eq 1}">
							 <th class="ar1">订单日期</th>
							 <th class="ar1">订单编号</th>
							 <th class="ar1">商品名称</th>
							 <th class="ar1">商品总金额</th>
							 <th class="ar1">平台返券额</th>
							 <th class="ar1">平台交易费</th>
							 <th class="ar1">企业分期还款额</th>
							 <th class="ar1">备注</th>
						</c:if>
						<c:if test="${type eq 2}">
							 <th class="ar3">序号</th>
							 <th class="ar3">时间</th>
							 <th class="ar3" style="width: 300px;">企业账号/企业个人账号ID姓名</th>
							 <th class="ar3">转账额（券）</th>
						</c:if>
					</tr>
					<c:if test="${empty page.result}">
						<tr>
							<td colspan="10">
								<center><img src="${path }/commons/images/no.png" /></center>
							</td>
						</tr>
					</c:if>
					<c:if test="${type eq 1}">
						<c:forEach items="${page.result}" var="data">
							<tr>
								<td>
									<fmt:formatDate value="${data.operatorTime}" pattern="yyyy-MM-dd"/>
								</td>
								<td style="overflow: hidden;white-space: nowrap;text-overflow:ellipsis;" title="${data.refObjId}">
									${data.refObjId}
								</td>
								<td style="overflow: hidden;white-space: nowrap;text-overflow:ellipsis;" title="${data.productNameList}">
									${data.productNameList}
								</td>
							 	<td>
								 	${fn:escapeXml(data.totalPrice)}
								</td>
								<td>
									${fn:escapeXml(data.earning)}
								</td>
								<td>
					  	 			${data.payDealfee}
					 			</td>
								<td>
					  	 			${data.payFenqi }
					 			</td>
								<td>
									${data.memo }
					 			</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${type eq 2}">
						<c:forEach items="${page.result}" var="data">
							<tr>
								<td>${data.id}</td>
							 	<td>${data.time}</td>
								<td>${data.name}</td>
								<td>${data.price }</td>
							</tr>
						</c:forEach>
					</c:if>
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