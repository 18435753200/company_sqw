<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.mall.mybatis.utility.PageBean" %>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<div class="blank5"></div>
<div class="btn">
	<c:if test="${! empty buttonsMap['库存管理-导出表格'] }">	
		<a href="javascript:void(0)" onclick="downExcel(0)"> 
			<span class="button red"> 
				<span class="text">
					<img alt="" src="${path }/commons/images/down-icon.png" height="19px" width="19px"> 导出表格
				</span>
			</span>
		</a>
	</c:if>
</div>


<table class="tb" style="width:1200px">
	<tr class="tz">
	  <th class="t1">经销商名称</th>
	  <th class="t1">商品名称</th>
	  <!-- <th class="t2">批次号</th> -->
		<th class="t1">SKUID</th>
	  <th class="t1">规格</th>

	  <th class="t1">条形码</th>
	  <th class="t3">虚拟库存</th>
		<th class="t3">残次品库存</th>
	  <th class="t3">库存数量</th>
	  <th class="t3">锁定数量 </th>
	  <th class="t3">订单占用数量 </th>
	  <c:if test="${pb.parameter.ownerType == 1 }">
	  	<th class="t3">仓库 </th>
	  </c:if>
	  <th class="t4">入库时间</th>
	   <!-- <th class="t3">真实批次号</th>
	  <th class="t4">生产日期</th>
	  <th class="t4">有效日期</th> -->
	  <th class="t5">操作</th>
	<tr>
	<c:if test="${empty pb.result}">
		<tr>
		<td
		<c:if test="${pb.parameter.ownerType == 1 }">
			 colspan="10"
	  </c:if>
		<c:if test="${pb.parameter.ownerType != 1 }">
			 colspan="9"
	  </c:if>
	  >
				<center><img src="${path }/commons/images/no.png" /></center>
			</td>
		</tr>
	</c:if>
					
<c:forEach items="${pb.result }" var="inv">
	<tr class="tz1">
		<td class="t1">
			 <p class="over" title="${fn:escapeXml(inv.dealerName)}">${fn:escapeXml(inv.dealerName)}</p>
		</td>
	 	<td class="t1">
		  <p class="over" title="${fn:escapeXml(inv.pName)}">${fn:escapeXml(inv.pName)}</p>
		</td>
		<%-- <td class="t2">
			 <p class="over2"  title=" ${fn:escapeXml(inv.batchNo)}">
			     ${fn:escapeXml(inv.batchNo)}
			 </p>
		</td> --%>
		<td class="t1" style="width: 130px;">
			<p class="over" style="width: 130px;" title="${fn:escapeXml(inv.skuId)}">${fn:escapeXml(inv.skuId)}</p>
		</td>
		<td class="t1">
			 <p class="over" title="${fn:escapeXml(inv.skuName)}">${fn:escapeXml(inv.skuName)}</p>
		</td>
		<td class="t1" style="width: 100px;">
			 <p class="over" style="width: 100px;" title="${fn:escapeXml(inv.skuCode)}">${fn:escapeXml(inv.skuCode)}</p>
		</td>
		<td class="t3">
		   <p class="over3" title="${fn:escapeXml(inv.vmQty)}">${fn:escapeXml(inv.vmQty)}</p>
		</td>
		<td class="t3">
			<p class="over3" title="${fn:escapeXml(inv.incompleteQty)}">${fn:escapeXml(inv.incompleteQty)}</p>
		</td>
		<td class="t3">
		   <p class="over3" title="${fn:escapeXml(inv.sumqty)}">${fn:escapeXml(inv.sumqty)}</p>
		</td>
		<td class="t3">
		   <p class="over3" title="${fn:escapeXml(inv.lockQty)}">${fn:escapeXml(inv.lockQty)}</p>
		</td>
		<td class="t3">
		   <p class="over3" title="${fn:escapeXml(inv.preSubQty)}">${fn:escapeXml(inv.preSubQty)}</p>
		</td>
		
		<c:if test="${pb.parameter.ownerType == 1 }">
			<td class="t3">
				<p class="over3" title="${fn:escapeXml(inv.warehouseName)}">${fn:escapeXml(inv.warehouseName)}</p>
			</td>
		</c:if>
	  
		<td class="t4">
			<p class="over4"><fmt:formatDate value="${inv.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></p>
		<%-- </td>
			<td class="t3">
				<p class="over3" title="${fn:escapeXml(inv.lotNo)}">${fn:escapeXml(inv.lotNo)}</p>
			</td>
		<td class="t4">
			<p class="over4"><fmt:formatDate value="${inv.productDate }" pattern="yyyy-MM-dd HH:mm:ss"/></p>
		</td>
		<td class="t4">
			<p class="over4"><fmt:formatDate value="${inv.effecTiveDate }" pattern="yyyy-MM-dd HH:mm:ss"/></p>
		</td> --%>
		<td class="t5">
		<c:if test="${inv.sheilLife==1 }">
		
		</c:if>
		<c:if test="${! empty buttonsMap['库存管理-修改库存'] }">
			<c:choose>
				<c:when test="${inv.sheilLife==0 }">
					<span class="xg" onclick="editInventoryNotBatch('${fn:escapeXml(inv.id)}','${fn:escapeXml(inv.preSubQty)}','${fn:escapeXml(inv.lockQty)}','${fn:escapeXml(inv.sumqty)}','${fn:escapeXml(inv.version)}','${fn:escapeXml(inv.dealerId)}')">修改</span>
				</c:when>
				<c:otherwise>
					<span class="xg" onclick="editInventory('${fn:escapeXml(inv.id)}','${fn:escapeXml(inv.batchNo)}','${fn:escapeXml(inv.preSubQty)}','${fn:escapeXml(inv.sumqty)}','${fn:escapeXml(inv.lockQty)}','${fn:escapeXml(inv.version)}','${fn:escapeXml(inv.dealerId)}')">修改</span>
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${! empty buttonsMap['库存管理-锁定库存'] }">	
		<input type="hidden" id="pName1" value="${inv.pName}">
		<input type="hidden" id="skuName1" value="${inv.skuName}">
			<span class="cj" onclick="lockInventory('${inv.id}','${inv.skuId}','${inv.pId}','${inv.batchNo}','${inv.ownerType}','${inv.dealerId}','','')">
					锁定库存
			</span>
		</c:if>
		<!-- 目前简单拦截 没有使用js拦截 -->
		<c:if test="${pb.parameter.ownerType == 1 }">
			<c:if test="${inv.warehouseName != 'NEW米兰转运仓'}">
				<c:if test="${! empty buttonsMap['库存管理-转移到赠品'] }">
					<input type="hidden" id="pName2" value="${inv.pName}">
					<input type="hidden" id="skuName2" value="${inv.skuName}">	
					<span class="xg" onclick="moveInventoryToGift('${fn:escapeXml(inv.id)}','${fn:escapeXml(inv.batchNo)}','${fn:escapeXml(inv.preSubQty)}','${fn:escapeXml(inv.sumqty)}','${fn:escapeXml(inv.lockQty)}','${fn:escapeXml(inv.version)}','${fn:escapeXml(inv.dealerId)}','${inv.skuId}','${inv.pId}','${inv.batchNo}','${inv.ownerType}','','')">
							转移到赠品
					</span>
			    </c:if>
		    </c:if>
		</c:if>
			
	    </td>
    </tr>
</c:forEach>
</table>

<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp" %>
</c:if>