<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<% response.setHeader("cache-control","public"); %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-家庭号统计管理</title>
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
				<p class="c1">家庭号统计管理</p>
			</div>
			</div>
			<div class="blank10"></div>

			<div class="c22">
			    <div class="c21">
						<ul class="top">
							<li id="supplier"><a href="javascript:void(0)" >家庭号统计管理</a></li>
						</ul>
				 </div>
				<div class="xia" style="height: 80px;">
					<form id="SearchFrom"  action="${path}/platform/getCompanyStatManage1" method="post" >
						<p class="p1">
						    <strong>家庭号编码：</strong><input type="text" id="userId" name="supplierCode" value="${supplier.supplierCode}" class="text1 dzf-txt">
	                        &nbsp;&nbsp;
							<strong>家庭号名称 ：</strong>
							<input type="text" class="text1 dzf-txt" name="name" value="${supplier.name}">
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
					 <th class="ar1">家庭号名称</th>
					 <th class="ar1">家庭号代码</th>
					 <th class="ar1">客服电话</th>

					 <th class="ar2">真实姓名</th>
					 <th class="ar2">类型</th>
					</tr>
					<c:if test="${supplier == null}">
						<tr>
							<td colspan="10">
								<center><img src="${path }/commons/images/no.png" /></center>
							</td>
						</tr>
					</c:if>
					<c:if test="${supplier != null}">
							<tr>
							 	<td>
								 	${fn:escapeXml(supplier.name)}
								</td>
								<td>${fn:escapeXml(supplier.supplierCode)}</td>
								<td>${supplier.kfTel}</td>
								<td>${supplier.contact}</td>
								<td>
									<span class="ckan" style="cursor: pointer;color: #ff4800;" onclick="toAccountDetail('${supplier.supplierId}','${supplier.userId}')">企业账目</span></br>
									<form id="toQyXiajihuiyuanId" action="${path}/platform/toQiYeJuniorMember" method="post">
								        <input type="hidden" id="uId" name="userId" class="text1">
					  		    	</form>
					 			</td>
							</tr>
					</c:if>
				</table>
				<div class="blank10"></div>
				<form id="toQyAccountDetailId" action="${path}/platform/toQyAccountDetail" method="post">
			        <input type="hidden" id="uuId" name="userId" class="text1">
			        <input type="hidden" id="suppId" name="supplierId" class="text1">
			        <input type="hidden" id="typeId" name="type" value="1" class="text1">
			        <input type="hidden" id="typeId" name="hqqType" value="2" class="text1">
	  		    </form>
	  		    <form id="toQySmallAccountDetailId" action="${path}/platform/toAccountDetail" method="post">
			        <input type="hidden" id="uuuId" name="userId" class="text1">
			        <input type="hidden" id="typeId" name="type" value="1" class="text1">
	  		    </form>
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
	function toAccountDetail(supplierId,userId){
		$("#uuId").val(userId);
		$("#suppId").val(supplierId);
		$("#toQyAccountDetailId").submit();
	}
	function toQyXiajihuiyuan(userId){
		$("#uId").val(userId);
		$("#toQyXiajihuiyuanId").submit();
	}
	function toQySmallAccountDetail(supplierId,userId){
		$("#uuuId").val(userId);
		$("#toQySmallAccountDetailId").submit();
	}
</script>
<script type="text/javascript">
</script>
</html>