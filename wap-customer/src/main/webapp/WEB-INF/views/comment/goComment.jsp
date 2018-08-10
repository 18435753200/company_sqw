<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp"%>
<link href="${staticFile_s}/commons/css/user.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<title><spring:message code="title_comment" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<header id="header" class="header">
		<a class="bug-go" href="javascript:history.go(-1)"></a>
		<a class="home-go" href="/wap-customer/"></a>
		商品评价
	</header>
	<form action="">
		<div class="wrap mb70" <c:if test="${no==2  || no==1 }">id="goodsComment"</c:if> <c:if test="${no==0}">id="commentAdd"</c:if>>
			<input type="hidden" id="comentType" value="${no}">
		    <input type="hidden" id="orderId" value="${orderId}">
			<c:forEach items="${commentVOs}" var="commentVO" varStatus="itemStatus">
			   <c:set value="${commentVO.orderItem}" var="item" ></c:set>	
				<div class="comment-group">								
					<div class="comment-main">
					   <%--商品--%>	
						<div class="comment-item">
							<a href="<%=path%>/item/get/${item.pid}" class="link">
								<div class="comment-pic">
									<img src="${imageBaseURL}${item.imgUrl}">
								</div>
								<div class="comment-info">
									<p class="name">${item.pName }</p>
									<p class="price">
										<span> <fmt:formatNumber value="${fn:escapeXml(item.price)}" pattern="#0.00" /></span>
									</p>
								</div>
							</a>
						</div>
                       <%--评分--%>	
						<div class="comment-score">
							<span class="score-name">评分：</span> <span class="commstar" itemIndex="${itemStatus.index}">
							    <c:if test="${no==0}">
									<c:forEach begin="1" end="5" var="index">
										<a href="javascript:;" class="star${index} <c:if test="${index==5}">active</c:if>" level="${index}"></a>
									</c:forEach>
								</c:if> 
								<c:if test="${no==2 || no==1}">
									<c:set value="${commentVO.comment}" var="comment"></c:set>
									<c:set value="${commentVO.comment.userInfo.isAnonymity }" var="isAnon"></c:set>
									<c:if test="${no==1}">
								    	<c:forEach begin="1" end="5" var="index">
										    <a href="javascript:;" class="star${index} <c:if test="${index==commentVO.comment.level}">active</c:if>" level="${index}"></a>
                                            <c:if test="${index==commentVO.comment.level}">
                                               <c:set value="${index}" var="level"></c:set>
                                            </c:if>
									    </c:forEach>
							    	</c:if> 
							    	<c:if test="${no==2}">
									<a href="javascript:;" class="star${commentVO.comment.level } active" level="${commentVO.comment.level }"></a>
									</c:if> 
								</c:if>
							</span>
						</div>
					</div>
					<%--客服回复和追评内容 --%>
					<c:if test="${no==1 || no==2}">
						<div class="comment-list">
							<div class="comment-area">
								<div class="comment-name">已评价</div>
								<div class="comment-content com-box">
									<p>${fn:escapeXml(commentVO.comment.context) }</p>
								</div>
								<div class="comment-date">
									<time>
										<fmt:formatDate value="${commentVO.comment.date}" pattern="yyyy-MM-dd HH:mm:ss" />
									</time>
								</div>
							</div>
							<%--客服回复 --%>
							<c:if test="${commentVO.comment.repliesCount > 0}">
								<c:forEach items="${commentVO.comment.replies}" var="reply">
									<div class="comment-area">
										<div class="comment-content com-box">
											<p>
												<span class="kf-name">客服</span>${fn:escapeXml(reply.context) }
											</p>
										</div>
										<div class="comment-date">
											<time>
												<fmt:formatDate value="${reply.date}" pattern="yyyy-MM-dd HH:mm:ss" />
											</time>
										</div>
									</div>
								</c:forEach>
							</c:if>
							<%--追评内容 --%>
							<div class="comment-area">
							<c:if test="${!empty fn:escapeXml(commentVO.comment.commentAddTo) }">					
									<div class="comment-content com-box">
										<p>
											<span class="kf-name">[追加]</span>${fn:escapeXml(commentVO.comment.commentAddTo.context) }
										</p>
									</div>
									<div class="comment-date">
										<time>
											<fmt:formatDate value="${commentVO.comment.commentAddTo.date}" pattern="yyyy-MM-dd HH:mm:ss" />
										</time>
									</div>								
							</c:if>
							<c:if test="${no == 1 and empty fn:escapeXml(commentVO.comment.commentAddTo) }">
							       <input type="hidden" name="comments[${itemStatus.index }].commentId" value="${commentVO.comment.id}">
									<div class="comment-name">[追加评价]</div>
									<div class="comment-content">
										<textarea class="textarea" maxlength="999" id="contextId" name="comments[${itemStatus.index }].context" placeholder="商品怎么样？还可以追加评价的 ~"></textarea>
									</div>
							</c:if>				
							</div>
						</div>
					</c:if>
					<%--评论内容 --%>
					<c:if test="${no==0}">
						<div class="comment-box">
							<textarea class="textarea"  maxlength="999" id="contextId" name="comments[${itemStatus.index }].context" placeholder="写下评价,您的意见对他人有很大的帮助！"></textarea>
						</div>
					</c:if>		
					<%--参数--%>					    
				    <c:choose>
				       <c:when test="${no==1}">
				          <c:if test="${empty fn:escapeXml(commentVO.comment.commentAddTo)}">
				            <input type="hidden" name="comments[${itemStatus.index }].pId" value="${item.pid}">
				            <input type="hidden" name="comments[${itemStatus.index }].skuId" value="${item.skuId}"> 
				            <input type="hidden" name="comments[${itemStatus.index }].leve" id="level${itemStatus.index }" value="${ level}">
				          </c:if>	          
				       </c:when>
				       <c:otherwise>
				          <input type="hidden" name="comments[${itemStatus.index }].pId" value="${item.pid}">
				          <input type="hidden" name="comments[${itemStatus.index }].skuId" value="${item.skuId}"> 
				          <input type="hidden" name="comments[${itemStatus.index }].leve" id="level${itemStatus.index }" value="5">
				       </c:otherwise>
				    </c:choose>
				</div>
			</c:forEach>
		</div>
		<%--匿名--%>	
		<c:if test="${no==0 || no==1}">
		   <div class="navbar commentbar navbar-fixed-bottom">			
			   <div class="chk">
				  <label for="isAnon"> 		  
				     <input type="checkbox" name="isAnon" <c:if test="${(no==1 || no==2) && isAnon == 0}"> checked="checked"  disabled="disabled" </c:if>
				     									  <c:if test="${(no==1 || no==2) && isAnon == 1}"> disabled="disabled" </c:if>  id="isAnon" value="0">
				     									   匿名评价				 
				  </label>
		       </div>
			   <div class="go-comment">
				    <input type="button"  value="提交评价" class="goComment-btn">
			   </div>			
		   </div>
		</c:if>
	</form>
	<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
	<script type="text/javascript" src="${staticFile_s}/commons/js/comment/comment.js"></script>
</body>
</html>