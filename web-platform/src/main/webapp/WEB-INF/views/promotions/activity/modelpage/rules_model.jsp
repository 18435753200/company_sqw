<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table class="tb-promotion">
	<colgroup>
		<col width="278px">
		<col width="278px">
		<col width="282px">
	</colgroup>
	<thead>
		<tr>
			<th>规则名称</th>
			<th>规则类型</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty pb.result}">
			<tr>
				<td colspan="5">
					<center>
						<img src="${path }/commons/images/no.png" />
					</center></td>
			</tr>
		</c:if>
		<c:forEach items="${pb.result}" var="rule">
			<tr>

				<td class="name">${fn:escapeXml(rule.ruleName)}</td>
				<!-- name -->
				<c:if test="${rule.pType == 0 }">
					<td class="name">B2B平台规则</td>
				</c:if>
				<c:if test="${rule.pType == 1 }">
					<td class="name">B2C平台规则</td>
				</c:if>
				<!-- ruleType -->

				<td class="tac"><a
					href="${path}/platformRule/toShowRule?ruleId=${rule.ruleId}&ruleType=${rule.status}&activeId=${rule.activeId}&pType=${rule.pType}">查看</a>
					
					<a href="${path}/gift/toGiftList?ruleId=${rule.ruleId}&pType=${rule.pType}">管理赠品</a>
					
			<c:if test="${rule.activeStatus == 0}">					
					<c:if test="${rule.status==0}">
						<a id="stopOrEnableRule"
							onclick="stopOrEnableRule('${rule.activeId}','${rule.ruleId}','0','${rule.pType }')"
							href="javascript:void(0)"> 启用</a>
							
						<%-- <a href="javascript:void(0)"
							onclick="deleteRule('${rule.activeId}','${rule.ruleId}')">删除</a>	 --%>
					</c:if> 
					
					<c:if test="${rule.status==1}">
							<a id="stopOrEnableRule"
								onclick="stopOrEnableRule('${rule.activeId}','${rule.ruleId}','1','${rule.pType }')"
								href="javascript:void(0)"> 停用</a>
					</c:if>
			</c:if>
			<c:if test="${rule.activeStatus == 1}">
				<c:if test="${rule.status==0}">
					未启用
				</c:if>
				<c:if test="${rule.status==1}">
					已启用
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