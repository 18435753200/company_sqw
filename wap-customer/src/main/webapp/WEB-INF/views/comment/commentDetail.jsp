<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${not empty result.comments}">
	<c:forEach items="${result.comments}" var="comment" varStatus="statu">
		<div class="comment-item">
			<div class="comment-title">
				<span class="comment-user"><i></i> ${comment.userInfo.userName}</span>
				<div class="comment-score">
					<span class="comment-star"><span class="comment-ystar star-${comment.level}"></span></span>
			    </div>
			</div>
			
			<div class="comment-area">
				<div class="comment-content com-box">
					<p>${fn:escapeXml(comment.context)}</p>
				</div>
			</div>
			<div class="comment-date">
				<time>
					<fmt:formatDate value="${comment.date}" pattern="yyyy-MM-dd HH:mm:ss" />
				</time>
			</div>
			<c:if test="${comment.repliesCount > 0}">
				<c:forEach items="${comment.replies}" var="reply">
					<div class="comment-area">
						<div class="comment-content com-box comment-bg">
							<p>
								<span class="kf-name">客服</span>${fn:escapeXml(reply.context)}
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
			<c:if test="${!empty comment.commentAddTo}">
				<div class="comment-area">
					<div class="comment-content com-box">
						<p>
							<span class="kf-name">[追加]</span>${fn:escapeXml(comment.commentAddTo.context) }
						</p>
					</div>
					<div class="comment-date">
						<time>
							<fmt:formatDate value="${comment.commentAddTo.date}" pattern="yyyy-MM-dd HH:mm:ss" />
						</time>
					</div>
				</div>
			</c:if>
		</div>
	</c:forEach>
</c:if>