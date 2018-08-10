<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.mall.mybatis.utility.PageBean" %>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

     <table>
		<tr>
			<th style="width:150px">商户名称</th>
			<th style="width:150px">用户名称</th>
			<th style="width:150px">时间</th>
			<th style="width:260px">反馈内容</th>
			<th style="width:150px">反馈类型</th>
			<th style="width:150px">反馈级别</th>
			<th style="width:100px">操作</th>
		</tr>
		<c:if test="${empty pb.result}">
			<tr>
				<td colspan="7">
					<center><img src="${path }/commons/images/no.png" /></center>
				</td>
			</tr>
		</c:if>
		<c:forEach items="${pb.result}" var="complaint">
		<tr>
			<td><p style="width:150px; display:block; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${fn:escapeXml(complaint.referenceName)}">${fn:escapeXml(complaint.referenceName)}</p></td>
			<td><p style="width:150px; display:block; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${fn:escapeXml(complaint.complaintBy)}">${fn:escapeXml(complaint.complaintBy)}</p></td>
			<td><fmt:formatDate value="${complaint.complaintTime}" pattern="yyyy-MM-dd HH:mm"/></td>
			<td><p style="width:260px; display:block; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${fn:escapeXml(complaint.complaintContent)}">${fn:escapeXml(complaint.complaintContent)}</p></td>
			<td>
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
			</td>
			<td>
				<c:if test="${complaint.complaintLevel==0}">
					普通
				</c:if>
				<c:if test="${complaint.complaintLevel==1}">
					一般
				</c:if>
				<c:if test="${complaint.complaintLevel==2}">
					紧急
				</c:if>
			</td>
			<td>
			
			<c:if test="${ !empty buttonsMap['经销商问题反馈-提交回复']}">
			   <a href="javascript:void(0)" onclick="dealer_getReplyComplaint_edit('${complaint.complaintId }')" class="she">回复</a>
		    </c:if>
			<c:if test="${ empty buttonsMap['经销商问题反馈-提交回复']}">
			   <a href="javascript:void(0)" onclick="dealer_getReplyComplaint_show('${complaint.complaintId }')" class="she">查看</a>
			</c:if>
				<!-- <a href="#" class="schu">删除</a> -->
			</td>
		</tr>
		</c:forEach>
	</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp" %>
</c:if>