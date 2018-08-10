<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<div class="pu_bd">
	<input type="hidden" id="conQueryType" value="${queryType}">
	<table>
		<tbody>
			<tr class="order_hd">
				<c:if test="${queryType==1 }">
					<th width="40px;">序号</th>
					<th width="100px;">商品编码</th>
					<th width="100px;">商品名称</th>
					<th width="100px;">商品条码</th>
					<th width="60px;">商品状态</th>
					<th width="100px;">规格</th>
					<th width="40px;">单位</th>
					<th width="40px;">数量</th>
					<th width="90px;">批次</th>
					<th width="90px;">批号</th>
					<th width="70px;">生产日期</th>
				</c:if>
				<c:if test="${queryType==2 }">
					<th width="50px;">序号</th>
					<th width="150px;">商品编码</th>
					<th width="150px;">商品名称</th>
					<th width="150px;">商品条码</th>
					<th width="60px;">商品状态</th>
					<th width="150px;">规格</th>
					<th width="60px;">单位</th>
					<th width="60px;">数量</th>
					
				</c:if>

			</tr>
			<c:if test="${empty pb.result}">
				<tr>
					<td colspan="11">
						<center>
							<img src="${path }/commons/images/no.png" />
						</center></td>
				</tr>
			</c:if>
			<c:forEach items="${pb.result}" var="stock" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td skuid="${stock.skuId}">${fn:escapeXml(stock.pid)}</td>
					<td>
						<p <c:if test="${queryType==1 }"> class="over"</c:if>
							<c:if test="${queryType==2 }"> class="over1"</c:if>
							title="${fn:escapeXml(stock.pname)}">${fn:escapeXml(stock.pname)}</p>
					</td>
					<td>${fn:escapeXml(stock.barCode)}</td>
					<td><c:if test="${stock.pstatus==1}">
					正品
					</c:if> <c:if test="${stock.pstatus==2}">
					残品
					</c:if>
					</td>
					<td>
					<p <c:if test="${queryType==1 }"> class="over"</c:if>
							<c:if test="${queryType==2 }"> class="over1"</c:if>
							title="${fn:escapeXml(stock.format)}">${fn:escapeXml(stock.format)}</p>
					</td>
					<td>${fn:escapeXml(stock.unit)}</td>
					<td>${fn:escapeXml(stock.restQty)}</td>
					<c:if test="${queryType==1 }">
						<td>
							<p class="over" title="${fn:escapeXml(stock.batchNumber)}">${fn:escapeXml(stock.batchNumber)}</p>
						</td>
						<td>
							<p class="over" title="${fn:escapeXml(stock.batch)}">${fn:escapeXml(stock.batch)}</p>
						</td>
						<td><fmt:formatDate value="${stock.productDate}"
								pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:if test="${!empty pb.result}">
		<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
	</c:if>
</div>
