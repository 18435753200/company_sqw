<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.mall.mybatis.utility.PageBean" %>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
		
		
            	<table>
						<tr>
							<th style="width:150px;">用户名称</th>
							<th style="width:150px;">时间</th>
							<th style="width:260px">反馈内容</th>
							<th style="width:150px;">反馈类型</th>
							<th style="width:150px;">反馈级别</th>
							<th style="width:150px">明细</th>
						</tr>
						<c:if test="${empty pb.result}">
							<tr>
								<td colspan="6">
									<center><img src="${path }/commons/images/no.png" /></center>
								</td>
							</tr>
						</c:if>
						<c:forEach items="${pb.result}" var="complaint">
						<tr>
							<!-- <td><p style="width:150px; display:block; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${fn:escapeXml(complaint.referenceName)}">${fn:escapeXml(complaint.referenceName)}</p></td> -->
							 <td><p style="width:150px; display:block; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${fn:escapeXml(complaint.complaintBy)}">${fn:escapeXml(complaint.complaintBy)}</p></td>
							<td><fmt:formatDate value="${complaint.complaintTime}" pattern="yyyy-MM-dd HH:mm"/></td>
							<td><p style="width:260px; display:block; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${fn:escapeXml(complaint.complaintContent)}">${fn:escapeXml(complaint.complaintContent)}</p></td>
							<td>
								
								<c:if test="${complaint.complaintType==1}">
									众聚商城公司相关问题
								</c:if>
								<c:if test="${complaint.complaintType==2}">
									消费者相关问题 
								</c:if>
								<c:if test="${complaint.complaintType==3}">
									 红旗手相关问题
								</c:if>
								<c:if test="${complaint.complaintType==4}">
									指导师培训相关问题
								</c:if>
								<c:if test="${complaint.complaintType==5}">
									子公司建议相关问题
								</c:if>
								<c:if test="${complaint.complaintType==6}">
									企业相关问题
								</c:if>
								<c:if test="${complaint.complaintType==7}">
									连锁企业相关问题
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
								<a href="javascript:void(0)" onclick="retailer_getReplyComplaint_show('${complaint.complaintId }')" class="she">查看</a>
								<!-- <a href="#" class="schu">删除</a> -->
							</td>
						</tr>
						</c:forEach>
					</table>
	<c:if test="${!empty pb.result}">
		<%@include file="/WEB-INF/views/dealerseller/paging.jsp" %>
	</c:if>