<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.mall.mybatis.utility.PageBean" %>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${path}/commons/css/went1.css">
<form method="post" id="oneDivs" enctype="multipart/form-data">
<table>
	<tr>
		<th style="width:25%;">类型</th>
		<th style="width:25%;">专区名称</th>
		<th style="width:25%;">赠送红旗劵(%)</th>
		<th style="width:25%;">赠送分红额度(%)</th>
		<th style="width:20%; display: none;">周期返还时间</th>
	</tr>
	<c:if test="${empty ondDivList}">
		<tr>
			<td colspan="6" style="align:center"><img src="${path }/commons/images/no.png" />
			</td>
		</tr>
	</c:if>
	<c:if test="${!empty ondDivList}">
	<c:forEach items="${ondDivList}" var="oneDiv">
		<tr>
			<td>	<c:if test="${oneDiv.dividendType == 1}">现金</c:if>
					<c:if test="${oneDiv.dividendType == 2}">红旗劵</c:if>
					<c:if test="${oneDiv.dividendType == 3}">现金+红旗劵</c:if> 
					
			</td>
			<td>${companyText}
				<input type="hidden" name="company" value="${oneDiv.companyBy}" />
			</td>
			<td>
				<input type="hidden" name="dividendId" value="${oneDiv.dividendId}"/>
				<input type="text" name="giftHqj" value="${oneDiv.giftHqj}"/>
			</td>
			<td>
				<input type="text" name="giftFhed" value="${oneDiv.giftFhed}"/>
			</td>
			<td style="display: none;">
				<input type="text" name="retDate" value="${oneDiv.retDate}"/>
			</td>
		</tr>
	</c:forEach>
	<tr class="x1">
		<td colspan="5" align="center" class="p3">
			<input type="button" class="tjiao" onclick="toSubmitRply()" value="保存" />
		</td>
	</tr>
	</c:if>
</table>
</form>