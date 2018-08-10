<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>

<table class="tb-ck">
	<thead>
		<tr>
			<th width="70">仓库CODE</th>
			<th width="50">状态</th>
			<th width="65">仓库类型</th>
			<th width="65">仓库级别</th>
			<th width="100">仓库名称</th>
			<th width="100">仓库地址</th>
			<th width="60">联系人</th>
			<th width="70">电话</th>
			<th width="100">发货渠道</th>
			<th width="100">负责区域</th>
			<th width="70">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty pb.result}">
			<tr>
				<td colspan="6">
					<center><img src="${path }/commons/images/no.png" /></center>
				</td>
			</tr>
		</c:if>
		<c:forEach items="${pb.result }" var="warehouse">
			<tr>
				<td>${warehouse.warehouseCode }</td>
				<td>
					<c:if test="${warehouse.warehouseStatus==0 }">启用</c:if>
					<c:if test="${warehouse.warehouseStatus==1 }">禁用</c:if>
				</td>
				<td>${warehouse.warehouseTypeName }</td>
				<td>${warehouse.warehouseLevelName }</td>
				<td>${warehouse.warehouseName }</td>
				<td>${warehouse.address }</td>
				<td>${warehouse.contact }</td>
				<td>${warehouse.telephone }</td>
				<td>
				     ${warehouse.channelName }
				</td>
				<td class="ho">
				<div class="reply" style="display: none">
				<c:if test="${empty warehouse.skuStockWarehouseAreaList}">
						暂无数据</br>
				</c:if>
				<c:if test="${warehouse.skuStockWarehouseAreaList != null}">
					<c:forEach items="${warehouse.skuStockWarehouseAreaList }" var="areaList" varStatus="count">
					<font style="color: #FF7E00">负责区域${count.index+1}: </font> ${areaList.address }</br>
				</c:forEach>
				</c:if>
				
 				</div>
				   <span>
					<c:forEach items="${warehouse.skuStockWarehouseAreaList }" var="areaList">
						${areaList.address }
					</c:forEach>
					</span>
				</td>
				<td>
					<c:if test="${! empty buttonsMap['仓库管理-修改仓库'] }">		
						<a href="../warehouse/getWareHouseById?id=${warehouse.id }" class="up">修改</a>
					</c:if>
				</td>
			</tr>

		</c:forEach>
	</tbody>
</table>


<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>

