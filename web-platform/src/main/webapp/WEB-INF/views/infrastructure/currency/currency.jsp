<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>


 <!DOCTYPE html>
 <html lang="en">
 <head>
 	<meta charset="UTF-8">
 	<title>币别设置</title>
 	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/currenry.css" />
		<script type="text/javascript" src="${path }/commons/js/infrastructure/infrastructure.js"></script>
	<script type="text/javascript">
	
//获取币别分页显示数据.
function clickSubmit(page){
	var pagearray = new Array();
	if ( undefined != page && "" != page){
		pagearray.push('page='+page);
	}else{
		pagearray.push('page='+1);
	}
	$.ajax({
		type : "post", 
		url : "../infrastructure/getCurrency",
		data:pagearray.join('&')+"&mathran="+Math.random(),
		dataType:"html",
		success : function(msg) { 
		$(".c3").html(msg);
		},
		error:function(){
			alert("服务异常!");
		}
	});	
}
	
	</script>
 </head>
 <body class="c3">
 <%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="center">
		<!-- 左边 start -->
	 <div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
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
		     	 <span class="add-ck-btn"><a href="../infrastructure/goAddCurrency" id="addoreditCurrency">添加货币</a></span>
		     </div>
			<div class="pu_wrap">
			 	<table class="pu">
					<tbody>
						<tr class="order-hd">
							<th width="200px">编号</th>
							<th width="200px">简码</th>
							<th width="236px">币种</th>
							<th width="200px">汇率</th>
							<th width="200px">本位币标识</th>
							<th width="200px">状态</th>
							<th width="200px">期间</th>
							<th width="200px">操作</th> 
						</tr>
						<c:if test="${empty pb.result}">
							<tr>
								<td colspan="6">
									<center>
										<img src="${path }/commons/images/no.png" />
									</center>
								</td>
							</tr>
						</c:if>
						<c:forEach items="${pb.result}" var="currency">
								<tr class="order-bd">
								<input type="hidden" name="sid" value="${currency.sid}">
									<td>${currency.sid}</td>
									<td>${currency.code}</td>
									<td>${currency.currencyType}</td>
									<td style="text-align:right;">${currency.exchangeRate}</td>
									<c:if test="${currency.loaclMoney==1}">
									<td>是</td>
									</c:if>
									<c:if test="${currency.loaclMoney==0}">
									<td>否</td>
									</c:if>
									<td>
									<c:if test="${currency.status==1}">启用</c:if>
									<c:if test="${currency.status==0}">禁用</c:if>
									</td>
									<td>${currency.accountingTime}</td>
									<td>
										<a href="${path}/infrastructure/editCurrency?sid=${currency.sid}" class="up">修改</a>
									</td>
								</tr>
							</c:forEach>
			       </tbody>
			   </table>
		   </div>
		   <c:if test="${!empty pb.result}">
			<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
		   </c:if>
		</div>
	</div>
<%@include file="/WEB-INF/views/include/foot.jsp" %>
 </body>
 </html>