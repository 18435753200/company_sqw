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
				<p class="c1">企业统计管理</p>
			</div>
			</div>
			<div class="blank10"></div>

			<div class="c22">
			    <div class="c21">
						<ul class="top">
							<li id="supplier"><a href="javascript:void(0)" >企业统计管理</a></li>
						</ul>
				 </div>
				<div class="xia" style="height: 80px;">
					<form id="SearchFrom"  action="${path}/platform/getCompanyStatManage" method="post" >
						<p class="p1">
						    <strong>企业编码：</strong><input type="text" id="userId" name="supplierCode" value="${supplier.supplierCode}" class="text1 dzf-txt">
	                        &nbsp;&nbsp;
							<strong>企业名称 ：</strong>
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
					 <th class="ar1">企业名称</th>
					 <th class="ar1">企业代码</th>
					 <th class="ar1">企业经理人</th>
					 <th class="ar1">企业经理人电话</th>
					 <th class="ar2">上级企业代码</th>
					 <th class="ar2">邀请码</th>
					 <th class="ar2">企业个人账号姓名/ID</th>
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
								<td>${supplier.manager}</td>
								<td>${supplier.managerTel}</td>
								<td>${supplier.sjSupplierCode }</td>
								<td>${supplier.userTj }</td>
								<td>${supplier.uName}/${supplier.userId }</td>
								<td>
									<span class="ckan" style="cursor: pointer;color: #ff4800;" onclick="toAccountDetail('${supplier.supplierId}','${supplier.userId}')">企业账目</span></br>
									<c:if test="${empty supplier.userId }">
										<span class="ckan" style="cursor: pointer;color: #c8c8c8;" title="无企业小号">企业小号账目</span>
										<span class="ckan" style="cursor: pointer;color: #c8c8c8;" title="无企业小号">查看下级会员</span>
									</c:if>
									<c:if test="${!empty supplier.userId }">
										<span class="ckan" style="cursor: pointer;color: #ff4800;" onclick="toQySmallAccountDetail('${supplier.supplierId}','${supplier.userId}')">企业小号账目</span>
										<span class="ckan" style="cursor: pointer;color: #ff4800;" onclick="toQyXiajihuiyuan('${supplier.userId}')">查看下级会员</span>
									</c:if>
									<form id="toQyXiajihuiyuanId" action="${path}/platform/toQiYeJuniorMember" method="post">
								        <input type="hidden" id="uId" name="userId" class="text1">
					  		    	</form>
					 			</td>
							</tr>
					</c:if>
				</table>
				<div class="blank10"></div>
				<table>
					<tr>
					 <th class="ar1">关系类型</th>
					 <th class="ar1">企业名称</th>
					 <th class="ar1">企业代码</th>
					 <th class="ar1">企业经理人</th>
					 <th class="ar1">企业经理人电话</th>
					 <th class="ar2">上级企业代码</th>
					 <th class="ar2">邀请码</th>
					 <th class="ar2">企业个人账号姓名/ID</th>
					 <th class="ar2">类型</th>
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
								<c:choose>
									<c:when test="${data.level eq 1}">
										上级
									</c:when>
									<c:when test="${data.level eq 2}">
										下级
									</c:when>
									<c:otherwise>
										--
									</c:otherwise>
								</c:choose>
							</td>
							<td>${fn:escapeXml(data.name)}</td>
						 	<td>${fn:escapeXml(data.supplierCode)}</td>
							<td>${fn:escapeXml(data.manager)}</td>
							<td>${data.managerTel }</td>
							<td>${data.sjSupplierCode}</td>
							<td>${data.userTj}</td>
							<td>${data.uName}/${data.userId }</td>
							<td>
								<span class="ckan" style="cursor: pointer;color: #ff4800;" onclick="toAccountDetail('${data.supplierId}','${data.userId}')">企业账目</span>
								</br>
								<c:if test="${empty data.userId }">
									<span class="ckan" style="cursor: pointer;color: #c8c8c8;" title="无企业小号">企业小号账目</span>
								</c:if>
								<c:if test="${!empty data.userId }">
									<span class="ckan" style="cursor: pointer;color: #ff4800;" onclick="toQySmallAccountDetail('${data.supplierId}','${data.userId}')">企业小号账目</span>
								</c:if>
				 			</td>
						</tr>
					</c:forEach>
				</table>
				</table>
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