<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品调拨(通知)单</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css">
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/transfer.css">
	
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>
</head>
<body>
	<!-- 导航 START -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 END -->

	<div class="blank"></div>
	<div class="center">
		<!-- 左侧菜单 START -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左侧菜单 END -->
	
		<div class="right">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品储存&nbsp;&gt;&nbsp;</p>
					<p class="c1">商品调拨(通知)单</p>
				</div>
			</div>
		
		<form id="addAllocatePrdFrom">
			<div class="pu_hd">
				<h3>表头信息</h3>
				<!-- 
				<div class="btn">
					<a href="javascript:void(0)" onclick="save()">保存</a>
					<a href="javascript:void(0)" onclick="audit()">审核</a>
				</div>
				 -->
			</div>

			<div class="xia">
				<p class="p1">
					<span>调拨单编号:</span> 
					<input type="text" id="transferNoChar" name="transferNoChar" value ="${transferOrder.transferNoChar}"  readonly="readonly"/> 
					
					<span>调拨时间:</span> 
					<input type="text" name="createTime" id="createTime"  readonly="readonly"  value ="<fmt:formatDate value="${transferOrder.createTime}" pattern="yyyy-MM-dd"/>" >
				</p>
				
				<p class="p1">
					<span>调出库房:</span> 
					<input type="text" id="transferOutWarehouseName" name="transferOutWarehouseName"  readonly="readonly"  value ="${transferOrder.transferOutWarehouseName}" />  
					
					<span>调入库房:</span> 
					<input type="text" id="transferInWarehouseName" name="transferInWarehouseName" readonly="readonly" value ="${transferOrder.transferInWarehouseName}" /> 
				</p>
			</div>

			<div class="pu_wrap">
				<div class="pu_hd">
					<h3>调拨商品列表</h3>
					<!--
					<div class="btn">
						<a href="javascript:void(0)" onclick="operationPage()">操作</a>
						<a href="javascript:void(0)" onclick="delOperation()">删除</a>
					</div>  -->
				</div>
				
				<div class="pu_bd">
		  			<table id ="sho">
		  				<tbody>
			  				<tr class="order_hd">
			  					<th width="40px;">序号</th>
			  					<th width="40px;">选择</th>
			  					<th width="100px;">商品编码</th>
			  					<th width="100px;">商品条码</th>
			  					<th width="80px;">商品名称</th>
			  					<th width="40px;">规格</th>
			  					<th width="40px;">单位</th>
			  					<th width="60px;">库存数量</th>
			  					<th width="60px;">调拨数量</th>
			  					<th width="70px;">商品状态</th>
			  					<th width="70px;">批次号</th>
			  					<th width="70px;">生产批号</th>
			  					<th width="70px;">生产日期</th>
			  				</tr>
			  				
			  				<c:forEach items="${transferItems}" var="transferItems" varStatus="status">
								<tr>
									<td>${status.index + 1 }</td>
					  				<td><input type='checkbox' id ="check" name='check' value='${transferItems.sid}'/></td>
					  				<td>${transferItems.pcode}</td>
					  				<td>${transferItems.barCode}</td>
					  				<td>${transferItems.pname}</td>
					  				<td>${transferItems.format}</td>
					  				<td>${transferItems.unit}</td>
					  				<td>${transferItems.stockQty}</td>
					  				<td>${transferItems.transferQty}</td>
					  				
					  				<c:choose>
										<c:when test="${transferItems.isgenuine==0}">
											<td>残品</td>
										</c:when>
										<c:otherwise>
											<td>正品</td>
										</c:otherwise>
									</c:choose>
									
					  				<td>${transferItems.batchNumber}</td>
					  				<td>${transferItems.batchId}</td>
					  				<td>
					  					<fmt:formatDate value="${transferItems.productionDate}" pattern="yyyy-MM-dd"/>
					  				</td>
			  					</tr>
		  					</c:forEach>
		  				</tbody>
		  			</table>
		  		</div> 
		  		
		  		<div class="er">
					<span>操作员:</span> <input type="text"  value="${transferOrder.createBy}" class="er_inp" readonly="readonly">
				</div>
			</div>
		</form>
			
		</div>
	</div>
</body>
</html>