<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
request.setAttribute("path",path);
%>
 <c:forEach items="${pb.result }" var="comment">	   
       <ul class="comments-item">
           <li class="td4">
	           <p class="sku">${fn:escapeXml(comment.orderInfo.pName)}</p>
	           <p class="date">购买时间：<fmt:formatDate value="${comment.orderInfo.buyDate }" type="both"/></p>
           </li>
           <li class="td1"><span class="icon-star star${comment.level }"></span></li>
           <li class="td2"><span>${fn:escapeXml(comment.userInfo.userName)}</span></li>
           <li class="td3">
               <p class="con">${fn:escapeXml(comment.context)}</p>
               <p class="date"><fmt:formatDate value="${comment.date }" type="both"/></p>
  			   <input type="hidden" value="${comment.id }" id="id">
           </li>
           <li class="td5">
           <c:if test="${! empty buttonsMap['评价列表-删除'] }">	
            	<span><a href="#" onclick="deleteComment('${comment.id}')">删除</a></span>
            </c:if>
            <c:if test="${! empty buttonsMap['评价列表-查看回复'] }">	
            	<span><a href="${path }/comment/getReplyListByCommentId?commentId=${comment.id }&page=1">查看回复</a></span>
            </c:if>
            <c:if test="${! empty buttonsMap['评价列表-回复解释'] }">	
            	<span class="replyComment"><a href="javascript:;">回复解释</a></span>
            </c:if>
           </li>
       </ul>
</c:forEach>
<%@include file="/WEB-INF/views/dealerseller/comment/model/paging.jsp" %>
