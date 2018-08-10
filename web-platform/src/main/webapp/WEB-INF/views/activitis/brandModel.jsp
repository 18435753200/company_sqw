<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<div class="br_b" >
	<c:forEach items="${pb.result}" var="bean">
		<a id="${bean.id }">${bean.nameCn }</a>
	</c:forEach>
</div>
<!-- <div>
<p class="p1"> -->
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/activitis/pagingBrand.jsp"%>
</c:if>
<!-- </p>
</div> -->