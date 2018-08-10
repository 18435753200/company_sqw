<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<link rel="stylesheet" type="text/css" href="${path}/commons/css/went1.css">
		<div class="right">
			<div class="x1">
				<div class="p1">
					<input type="hidden" id="complaintId" value="${complaint.complaintId}">
					用   户   名：${fn:escapeXml(complaint.complaintBy)}
					<span>
						时间：<fmt:formatDate value="${complaint.complaintTime}" pattern="yyyy-MM-dd HH:mm"/>
					</span>
					<br>
					反馈类型：
					<c:if test="${complaint.complaintType==0}">
						个人意见
					</c:if>
					<c:if test="${complaint.complaintType==1}">
						商品相关
					</c:if>
					<c:if test="${complaint.complaintType==2}">
						 系统问题 
					</c:if>
					<c:if test="${complaint.complaintType==3}">
						 订单相关
					</c:if>
					<c:if test="${complaint.complaintType==4}">
						退货相关
					</c:if>
					<c:if test="${complaint.complaintType==5}">
						服务相关
					</c:if>
					<c:if test="${complaint.complaintType==6}">
						管理相关
					</c:if>
					<br>
					反馈内容：${fn:escapeXml(complaint.complaintContent)}<br>
					联系方式：${fn:escapeXml(complaint.contactWay)}<br>
					反馈级别：
					<c:if test="${complaint.complaintLevel==0}">
						普通
					</c:if>
					<c:if test="${complaint.complaintLevel==1}">
						一般
					</c:if>
					<c:if test="${complaint.complaintLevel==2}">
						紧急
				   </c:if>
				</div>
				<div class="p2">
				处理记录：
				 <textarea rows="10" cols="87" id="textreply"  style="resize: none;"></textarea>
				</div>
				<div class="p3">
				    	<input type="button" value="确定" class="tjiao" onclick="toSubmitRply()"/>
						&nbsp;&nbsp;&nbsp;&nbsp;
					<!--  <c:if test="${ type=='dealer' and  !empty buttonsMap['经销商问题反馈-提交回复'] }">
						<input type="button" value="提交回复" class="tjiao" onclick="toSubmitRply()"/>
						&nbsp;&nbsp;&nbsp;&nbsp;
					</c:if> -->
					<input type="button"  class="tjiao" onclick="gotList();" value="返回" >
				</div>
			</div>
		</div>

	<p class="blank"></p>
	
	
