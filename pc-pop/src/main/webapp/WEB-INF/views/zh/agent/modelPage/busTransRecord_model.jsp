<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/zh/include/base2.jsp"%>
<table id="J_BoughtTable" class="bought-table" data-spm="9" style="width: 100%;">
		<span style="line-height: 40px; font-weight: 600; color:#000;">总合:<i style="color: #e60012;font-size: 1.5rem; margin-left: 1rem;">${bigDecimal }</i></span> 
		<input  type="button" style="color: red; cursor: pointer; border: 0; padding: 0.5rem 3rem;  float: right;border-radius: 5px; margin-top: 0.5rem;" onclick="returnPage('${parentId}')" value="返回"/>
		<thead>
			<tr class="col-name">
				<th>订单号</th>
				<th>订单价格</th>
				<th>订单时间</th>
			</tr>
		</thead>
		<tbody class="data" id="showListTbody">
		<!--        没有数据显示的页面 -->
		<c:if test="${empty acc.result}">
			<tr>
				<td colspan="6">
					<center>
						<img src="${path }/images/no.png" />
					</center>
				</td>
			</tr>
		</c:if>
		<!--        有数据显示的页面 -->
		<c:if test="${!empty acc.result}">
			<c:forEach items="${acc.result}" var="pbr">
				<tr style="border: 1px solid #e1e8ee; text-align: center; height: 80px;">
					<td class="busShow" colspan="1" >
						${pbr.id }
					</td>
					<td class="busShow" colspan="1" >
						${pbr.price }
					</td>
					<td class="busShow" colspan="1" >
						<fmt:formatDate value="${pbr.createTime }" pattern="yyyy-MM-dd  HH:mm:ss" />
					</td>
				</tr>
				<tr style="height: 10px;"></tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>

<c:if test="${!empty acc.result}">
	<%@include file="/WEB-INF/views/zh/agent/include/busTransPage.jsp"%>
</c:if>