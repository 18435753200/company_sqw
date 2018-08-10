<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table class="tb-logistics">
	<colgroup>
		<col width="140px">
		<col width="160px">
		<col width="160px">
		<col width="80px">
		<col width="80px">
		<col width="80px">
		<col width="138px">
	</colgroup>
	<thead>
		<tr>
			<th>模板编号</th>
			<th>发出省/市</th>
			<th>目的省/市</th>
			<th>类型</th>
			<th>时效(天)</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty pb.result}">
			<tr>
				<td colspan="6">
					<center>
						<img src="${path }/commons/images/no.png" />
					</center>
				</td>
			</tr>
		</c:if>
		<c:forEach items="${pb.result}" var="template">
			<tr>
				<td class="name">${fn:escapeXml(template.logisticTempId)}</td>

				<td>${fn:escapeXml(template.startAddress)}</td>
				<td>${fn:escapeXml(template.endAddress)}</td>
				<td><c:if test="${template.type==1 }">
						<div class="fee">
							按重量计费
							<div class="fee_orange">首重 ${fn:escapeXml(template.baseQt)}
								公斤 <fmt:formatNumber value="${fn:escapeXml(template.baseFee)}"  pattern="0.00"/>元； 每增加
								${fn:escapeXml(template.stepQt)} 公斤，增加运费
								<fmt:formatNumber value="${fn:escapeXml(template.stepFee)}"  pattern="0.00"/>元</div>
						</div>
					</c:if> <c:if test="${template.type==2 }">
						<div class="fee">
							按件计费
							<div class="fee_orange"><fmt:formatNumber value="${fn:escapeXml(template.baseQt)}"  pattern="0"/>
								件以内 <fmt:formatNumber value="${fn:escapeXml(template.baseFee)}"  pattern="0.00"/> 元； 每增加
								<fmt:formatNumber value="${fn:escapeXml(template.stepQt)}"  pattern="0"/>件，增加运费
								<fmt:formatNumber value="${fn:escapeXml(template.stepFee)}"  pattern="0.00"/> 元</div>
						</div>
					</c:if></td>

				<td>${fn:escapeXml(template.timeLimitMin)} - ${fn:escapeXml(template.timeLimitMax)}</td>
				<td>
					<c:if test="${template.status==1 }">有效</c:if>
					<c:if test="${template.status==2 }">无效</c:if>
					<c:if test="${template.status==3 }">无效</c:if>
				</td>
				<td class="tac">
					<c:if test="${template.status==1 }">
					
						<c:if test="${! empty buttonsMap['物流商管理-修改运费模板'] }">	
							<a href="${path}/providerlogistics/toUpdateTemplate?logisticTempId=${template.logisticTempId}" target="_blank">修改</a>
						</c:if>
											
						<c:if test="${! empty buttonsMap['物流商管理-停用或启用模板'] }">	
				        	<a href="javascript:void(0);" onclick="updateTempStatus('${template.logisticTempId}','2')" >置无效</a>
				        </c:if>
				        
					</c:if>
					<c:if test="${template.status==2 }">
					
					<c:if test="${! empty buttonsMap['物流商管理-停用或启用模板'] }">	
						<a href="javascript:void(0);" onclick="updateTempStatus('${template.logisticTempId}','1')" >恢复有效</a>
					</c:if>
					
					</c:if>
				</td>
			</tr>

		</c:forEach>
	</tbody>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>
<script type="text/javascript">
$(".fee").hover(function(e) {
	Mouse(e);
	$(this).find(".fee_orange").css({
		top : toppos,
		left : leftpos
	}).fadeIn(100);
}, function() {
	$(this).find(".fee_orange").hide();
});
</script>