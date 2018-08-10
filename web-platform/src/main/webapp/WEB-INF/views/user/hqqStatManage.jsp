<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<% response.setHeader("cache-control","public"); %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-红旗券统计管理</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/kuc1.css"/>
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>
	
	<script type="text/javascript">
		function toGetList(){
			$("#SearchFrom").submit();
		}
		$(function(){
			var message='${message}'; 
			if(message=='ok'){
				alert("操作成功!");
			}
			if(message=='1'){
				alert("缺少参数,操作失败!");
			}
			if(message=='2'){
				alert("上传失败,请重新选择上传文件!"); 		
			}
			if(message=='3'){
				alert("上传文件到图片服务器出错,请稍后再试!"); 	
			}
			if(message=='4'){
				alert("服务器忙,请稍后再试!"); 	
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
				<p class="c1">红旗券统计管理</p>
			</div>
			</div>
			<div class="blank10"></div>

			<div class="c22">
			    <div class="c21">
						<ul class="top">
							<li id="supplier"><a href="javascript:void(0)" >红旗券统计管理</a></li>
						</ul>
				 </div>
				 <div class="xia" style="min-height: 60px;">
					<p class="p1">
						<span style="font-size: 16px;width: initial;">注册会员总数:&nbsp;${sqwAccount.userNum}</span>
						<span style="font-size: 16px;width: initial;">注册账户激活总数:&nbsp;${sqwAccount.accountActivedNum}</span>
						<span style="font-size: 16px;width: initial;">红旗券转出总数:&nbsp;${sqwAccount.hqqTransOutNum}</span>
						<%-- <span style="font-size: 16px;width: initial;">现金消费总数:&nbsp;${sqwAccount.cashConsumeNum}</span> --%>
						</br>
						<span style="font-size: 16px;width: initial;">红旗券消费总数:&nbsp;${sqwAccount.hqqConsumeNum}</span>
						<span style="font-size: 16px;width: initial;">周期内红旗券总数:&nbsp;${sqwAccount.hqqNovalidNum}</span>
						<span style="font-size: 16px;width: initial;">可用红旗券总数:&nbsp;${sqwAccount.hqqValidNum}</span>
						</br>
						<span style="font-size: 16px;width: initial;">分红额度剩余总数:&nbsp;${sqwAccount.fhNum}</span>
						<%-- <span style="font-size: 16px;width: initial;">红旗券个人资产总数:&nbsp;${sqwAccount.hqqTotalNum}</span> --%>
						<span style="font-size: 16px;width: initial;">会员推荐奖励红旗券总数:&nbsp;${sqwAccount.hqqTuijianNum}</span>
					</p>
				</div>
				<div class="blank10"></div>
				<div class="xia" style="height:260px;">
					<form id="SearchFrom"  action="${path}/platform/hqqStatManage" method="post" >
						<p class="p1">
							<strong>ID/手机号 ：</strong>
							<input type="text" class="text1 dzf-txt" name="userIdOrPhone" value="${userIdOrPhone}">
						     &nbsp;
						    <strong>用户姓名：</strong><input type="text" id="userNameId" name="realName" value="${realName }" style="width:80px;">
						    </br>
							<strong>会员注册开始日期：</strong><input type="text" id="fromDate" value="${signUpFromDate}" name="signUpFromDate" class="rl" onClick="WdatePicker()">
	                        &nbsp;
							<strong>会员注册结束日期：</strong><input type="text" id="toDate" value="${signUpToDate}" name="signUpToDate" class="rl" onClick="WdatePicker()">
	                        &nbsp;
							<strong>会员激活开始日期：</strong>
							<input type="text" id="toDate" class="rl" value="${activeFromDate}" name="activeFromDate" onClick="WdatePicker()">
							<strong>会员激活结束日期：</strong>
							<input type="text" id="toDate" class="rl" value="${activeToDate}" name="activeToDate" onClick="WdatePicker()">
							 &nbsp;
							<%-- <strong>红旗券个人资产总数开始：</strong><input type="text" id="hqTotalFromId" name="hqTotalFrom" value="${hqTotalFrom }" style="width:80px;">
						     &nbsp;
							<strong>红旗券个人资产总数结束：</strong><input type="text" id="hqTotalToId" name="hqTotalTo" value="${hqTotalTo }" style="width:80px;"> --%>
							</br>
							<%-- <strong>现金消费总额开始 ：</strong>
							<input type="text" class="text1 dzf-txt" name="cashAccumulateFrom" value="${cashAccumulateFrom }" style="width:80px;">
							 &nbsp;
							<strong>现金消费总额 结束：</strong>
							<input type="text" class="text1 dzf-txt" name="cashAccumulateTo" value="${cashAccumulateTo }" style="width:80px;">
							&nbsp; --%>
							<strong>会员推荐奖励红旗券总数开始：</strong><input type="text" id="hqTuijianFromId" name="hqTuijianFrom" value="${hqTuijianFrom }" style="width:80px;">
							&nbsp;
							<strong>会员推荐奖励红旗券总数结束：</strong><input type="text" id="hqTuijianToId" name="hqTuijianTo" value="${hqTuijianTo }" style="width:80px;">
							<%--  &nbsp;
							<strong>红旗券消费总额开始：</strong><input type="text" id="hqAccumulateFromId" name="hqAccumulateFrom" value="${hqAccumulateFrom }" style="width:80px;">
							 &nbsp;
							<strong>红旗券消费总额结束：</strong><input type="text" id="hqAccumulateToId" name="hqAccumulateTo" value="${hqAccumulateTo }" style="width:80px;"> --%>
	                       	</br>
							<strong>可用红旗券 开始：</strong>
							<input type="text" class="text1 dzf-txt" name="hqValidFrom" id="hqValidFromId" value="${hqValidFrom }" style="width:80px;">
	                        &nbsp;
							<strong>可用红旗券结束 ：</strong>
							<input type="text" class="text1 dzf-txt" name="hqValidTo" id="hqValidToId" value="${hqValidTo }" style="width:80px;">
	                        &nbsp;
							<strong>分红额度 开始：</strong>
							<input type="text" class="text1 dzf-txt" name="fhBalanceFrom" value="${fhBalanceFrom }" style="width:80px;">
	                        &nbsp;
							<strong>分红额度 结束：</strong>
							<input type="text" class="text1 dzf-txt" name="fhBalanceTo" value="${fhBalanceTo }" style="width:80px;">
						</p>
						</br>
						<p style="margin-top: 160px;">
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
					 <th class="ar1">注册日期</th>
					 <th class="ar1">激活日期</th>
					 <th class="ar1">省市区</th>
					 <th class="ar1">姓名</th>
					 <th class="ar1">ID/手机号</th>
					 <th class="ar1">可用红旗券</th>
					 <th class="ar1">分红额度</th>
					 <!-- <th class="ar1">现金消费总额</th> -->
					 <th class="ar1">红旗券消费总额</th>
					<!--  <th class="ar1">红旗券个人资产总数</th> -->
					 <th class="ar2">会员推荐奖励红旗券总数</th>
					</tr>
					<c:if test="${empty page.result}">
						<tr>
							<td colspan="12">
								<center><img src="${path }/commons/images/no.png" /></center>
							</td>
						</tr>
					</c:if>
						<c:forEach items="${page.result }" var="data">
							<tr>
								<td style="width: 140px;">
									<fmt:parseDate value="${data.signupDate}" pattern="yyyy-MM-dd" var="data1"/>
									<fmt:formatDate value="${data1}" pattern="yyyy-MM-dd"/>
								</td>
							 	<td style="width: 140px;">
								 	<fmt:parseDate value="${data.avtiveDate}" pattern="yyyy-MM-dd" var="data2"/>
									<fmt:formatDate value="${data2}" pattern="yyyy-MM-dd"/>
								</td>
								<td>
					  	 			${data.area }
					 			 </td>
					 			<td>${data.name }</td>
					 			<td>${data.idAndPhone }</td>
					 			<td>${data.hqqValid }</td>
					 			<td>${data.fhBalance }</td>
								<%-- <td>
									${data.cashConsumeNum }
					 			 </td> --%>
								<td>
									${data.hqqConsumeNum }
								</td>
								<%-- <td>
									${data.hqqTotalNum }
								</td> --%>
								<td>
									${data.hqqTuijianNum }
								</td>
							</tr>
						</c:forEach>
				</table>
				<c:if test="${!empty page.result}">
					<%@include file="/WEB-INF/views/include/page.jsp"%>
				</c:if>
			</div>
		  
		</div>
		<div class="blank20"></div>
	</div>

	 <div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
<script type="text/javascript">

</script>
</html>