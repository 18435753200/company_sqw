<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>

<div class="pu_bd">
	
	<table>
		<tbody>
			<tr class="order_hd">
						<th width="140px;">情景组合ID</th>
						<th width="150px;">情景组合名称</th>
						<th width="150px;">默认商品名称</th>
						<th width="100px;">创建日期</th>
						<th width="100px;">创建人员</th>
						<th width="100px;">当前状态</th>
						<th width="100px;">操作</th>
			</tr>
			<c:forEach items="${pb.result}" var="mix" varStatus="status">
				<tr>
					<td>${mix.sceneId}</td>
					<td>${fn:escapeXml(mix.sceneName)}</td>
					<td>
						<div class="ord-list" >
							<c:forEach items="${mix.skuName}" var="skuNmae">
								<%-- ${skuNmae}<br/> --%>
										<c:forEach items="${ skuNmae}" var="sku">
									  		<span title="${sku}">${sku}</span>
									  </c:forEach>
							</c:forEach>
						</div>
					</td>
					<td><fmt:formatDate value="${mix.creatTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><%-- ${fn:escapeXml(mix.unit)} --%>
						${fn:escapeXml(mix.creatOperatorName)}
					</td>
					<td>
						<c:if test="${mix.isTate == 0}">
							 下架中
						</c:if>
						<c:if test="${mix.isTate != 0}">
							 上架中
						</c:if>
					</td>
					<td>
						<a href="${path}/productMix/showMix?mixId=${mix.sceneId}">查看</a>
						<a href="" onclick="updateStats('${mix.sceneId}')"> <!-- ${path}/productMix/updateMixTate?mixId=${mix.sceneId} -->
						<c:if test="${mix.isTate != 0}">
							 下架
						</c:if>
						<c:if test="${mix.isTate == 0}">
							 上架
							 <br/>
							 <a href="${path}/productMix/toUpdateMix?mixId=${mix.sceneId}">修改</a>
							 <a href="" onclick="deleteMix('${mix.sceneId}')">删除</a><!-- ${path}/productMix/deleteMix?mixId=${mix.sceneId} -->
						</c:if>
						</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:if test="${!empty pb.result}">
		<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
	</c:if>
</div>
