<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.mall.mybatis.utility.PageBean" %>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<div class="blank5"></div>

<table class="tb">
	<tr class="tz">
	  <th class="t1">商品名称</th>
	  <th class="t2">批次号</th>
	  <th class="t1">规格</th>
	  <th class="t3">库存数量</th>
	  <th class="t3">锁定数量 </th>
	  <th class="t3">订单占用数量 </th>
      <th class="t3">仓库 </th>
	  <th class="t4">入库时间</th>
	  <th class="t5">操作</th>
	<tr>
	<c:if test="${empty pb.result}">
		<tr>
			<td colspan="9">
				<center><img src="${path }/commons/images/no.png" /></center>
			</td>
		</tr>
	</c:if>
					
<c:forEach items="${pb.result }" var="inv">
	<tr class="tz1">
	 	<td class="t1">
		  <p class="over" title="${fn:escapeXml(inv.pName)}">${fn:escapeXml(inv.pName)}</p>
		</td>
		<td class="t2">
			 <p class="over2"  title=" ${fn:escapeXml(inv.batchNo)}">
			     ${fn:escapeXml(inv.batchNo)}
			 </p>
		</td>
		<td class="t1">
			 <p class="over" title="${fn:escapeXml(inv.skuName)}">${fn:escapeXml(inv.skuName)}</p>
		</td>
		<td class="t3">
		   <p class="over3" title="${fn:escapeXml(inv.qty)}">${fn:escapeXml(inv.qty)}</p>
		</td>
		<td class="t3">
		   <p class="over3" title="${fn:escapeXml(inv.lockQty)}">${fn:escapeXml(inv.lockQty)}</p>
		</td>
		<td class="t3">
		   <p class="over3" title="${fn:escapeXml(inv.preSubQty)}">${fn:escapeXml(inv.preSubQty)}</p>
		</td>
		
		<td class="t3">
			<p class="over3" title="${fn:escapeXml(inv.warehouseName)}">${fn:escapeXml(inv.warehouseName)}</p>
		</td>
	  
		<td class="t4">
			<p class="over4"><fmt:formatDate value="${inv.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></p>
		</td>
		<td>
			<c:if test="${! empty buttonsMap['库存管理-锁定库存'] }">	
				<a href="javascript:;" onclick="customerLock_fn('${inv.id}','${inv.qty}','${inv.preSubQty} ','${inv.lockQty }')">锁定库存</a>
			</c:if>
		</td>
    </tr>
</c:forEach>
</table>

<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp" %>
</c:if>