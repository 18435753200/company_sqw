<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<script type="text/javascript" src="${path}/commons/js/allocateNotifyEdit.js"></script>

<div class="pu_wrap">
	<div class="pu_hd">
		<h3>调拨单列表</h3>
		<div class="btn">
			<!-- <a href="#">新增调拨单</a> <a href="#">调拨完成</a> -->
			<a href="javascript:void(0)" onclick="addAllocatePrdNotify()">新增调拨单</a>
			<a href="javascript:void(0)" onclick="complePrdNotify()">调拨完成</a>
			
			<!-- 
			<button  type="button" id="addPrdNotify" name="addPrdNotify"  value="新增调拨单" onclick="addAllocatePrdNotify()">新增调拨单</button> 
			<button  type="button" id="complePrdNotify" name="complePrdNotify"  value="新增调拨单" onclick="complePrdNotify()">调拨完成</button>
			 -->
		</div>
	</div>
	<div class="pu_bd">
		<table>
			<tbody>
				<tr class="order_hd">
					<th width="60px;">序号</th>
					<th width="60px;">选择</th>
					<th width="140px;">调拨单编号</th>
					<th width="140px;">调出库房</th>
					<th width="140px;">调入库房</th>
					<th width="120px;">调拨时间</th>
					<th width="180px;">操作</th>
					<th width="180px;">释放</th>
				</tr>
				<c:forEach items="${pb.result }" var="transferOrderDto" varStatus="status">
				<tr>
					<td >${status.index + 1 }</td>
					<!-- 
					<td><input type="checkbox" name="transferOrderId" orderStatus="${transferOrderDto.status}" value="${transferOrderDto.sid}" onclick="addAllocatePrdNotify(this)"></td>
					 -->
					<td><input type="checkbox" id="sid" name="sid"  value="${transferOrderDto.sid}" ></td>
					<td>${transferOrderDto.transferNoChar}</td>
					<td>${transferOrderDto.transferOutWarehouseName}</td>
					<td>${transferOrderDto.transferInWarehouseName}</td>
					<td><fmt:formatDate value="${transferOrderDto.createTime}" pattern="yyyy-MM-dd"/></td>
					<td>
						<c:if test="${transferOrderDto.status ==1 }">
							<a href="${path}/allocateNotify/showUpdatePage?sid=${transferOrderDto.sid}" target="_blank">修改</a> 
						</c:if>
						<c:if test="${transferOrderDto.status ==10}">
							<a href="${path}/allocateNotify/showPage?sid=${transferOrderDto.sid}" target="_blank">查看(未调拨)</a> 
						</c:if>
						<c:if test="${transferOrderDto.status ==15}">
							<a href="${path}/allocateNotify/showPage?sid=${transferOrderDto.sid}" target="_blank">查看(完成调拨)</a> 
						</c:if>
					</td>
					<td>
						<c:if test="${transferOrderDto.status ==1 }">
							<!-- 
							<a href="javascript:void(0)" onclick="clearStock(${transferOrderDto.sid},20)">释放库存</a> -->
							<a href="${path}/allocateNotify/releaseStock?sid=${transferOrderDto.sid}&status=20">取消</a>
						</c:if>
						<c:if test="${transferOrderDto.status ==20 }">
							<!-- 
							<a href="javascript:void(0)" onclick="clearStock(${transferOrderDto.sid},1)">休眠</a>  -->
							<a href="${path}/allocateNotify/releaseStock?sid=${transferOrderDto.sid}&status=1">还原</a>
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${!empty pb.result}">
			<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
		</c:if>
	</div>
</div>

