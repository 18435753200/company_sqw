<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table class="tb-promotion">
	<colgroup>
		<!-- <col width="278px">
		<col width="278px">
		<col width="282px"> -->
		<col width="100px">
		<col width="100px">
		<col width="80px">
		<col width="80px">
		<col width="100px">
		<col width="50px">
		<col width="100px">
		<col width="80px">
		<col width="50px">
		<col width="138px">
	</colgroup>
	<thead>
		<tr>
			<th>规则ID</th>
			<th>规则名称</th>
			<th>是否私有</th>
			<th>规则范围</th>
			<th>规则条件</th>
			<th>规则优先级</th>
			<th>规则时间</th>
			<th>B2B/B2C</th>
			<th>活动类型</th>
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
				<td >${rule.id}</td>
				<td class="name">${rule.ruleName }</td>
				<td>
					<c:choose>
						<c:when test="${rule.ruleType==1 }">是</c:when>
						<c:otherwise>否</c:otherwise>
					</c:choose>
				</td>
				<td >
					<c:choose>
						<c:when test="${rule.ruleCondition==1 }"><a style="cursor: pointer;" onclick="javascript:viewScop('1','${rule.id}');" >品类</a></c:when>
						<c:when test="${rule.ruleCondition==2 }"><a style="cursor: pointer;" onclick="javascript:viewScop('2','${rule.id}');">品牌</a></c:when>
						<c:when test="${rule.ruleCondition==3 }"><a style="cursor: pointer;" onclick="javascript:viewScop('3','${rule.id}');">商品</a></c:when>
						<c:when test="${rule.ruleCondition==4 }"><a style="cursor: pointer;" onclick="javascript:viewScop('4','${rule.id}');">单品</a></c:when>
						<c:when test="${rule.ruleCondition==0 }">全场</c:when>
						<c:otherwise>无</c:otherwise>
					</c:choose>
				</td>	
				<td class="tac"><a style="cursor: pointer;" onclick="javascript:viewRuleCondition('${rule.id}');">点击查看</a></td>
				<td>${rule.ruleSort }</td>
				<td><fmt:formatDate value="${rule.startdate }" pattern="yyyy-MM-dd HH:mm:ss" />至<fmt:formatDate value="${rule.enddate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td>
					<c:if test="${rule.isB2c == null }">B2B</c:if>
					<c:if test="${rule.isB2c == 1 }">B2C</c:if>
				</td>
				<td>
					<c:if test="${rule.ruleTerm == 1 }">满返B2B(券)</c:if>
					<c:if test="${rule.ruleTerm == 2 }">阶梯满赠B2B(赠品)</c:if>
					<c:if test="${rule.ruleTerm == 10 }">限时直降</c:if>
					<c:if test="${rule.ruleTerm == 13 }">阶梯满赠B2C(赠品)</c:if>
					<c:if test="${rule.ruleTerm == 14 }">满返B2C(券)</c:if>
				</td>
				<td class="tac">
				<c:if test="${!empty buttonsMap['商品活动列表-规则停用启用']}">
					<a style="cursor: pointer;" onclick="javascript:stopOrEnableRule('${rule.activeId }','${rule.id}','${rule.status }','');">
						<c:choose>
							<c:when test="${rule.status == 1}">停用</c:when>
							<c:when test="${rule.status == 0}">启用</c:when>
							<c:otherwise>状态非法</c:otherwise>
						</c:choose>
					</a>
				</c:if>
					<a style="cursor: pointer;" onclick="javascript:viewRuleContent('${rule.id}');">查看规则文本</a>
				</td>

			</tr>
		</c:forEach>
	</tbody>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>