<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<script type="text/javascript">
	$(document).ready(function(){
		$("#showListTbody tr").each(
				function() {
					var maxwidth = 12;
					if ($(this).find("td:eq(3)").text().length > maxwidth) {
						$(this).find("td:eq(3)").text(
								$(this).find("td:eq(3)").text().substring(0,
										maxwidth));
						$(this).find("td:eq(3)").html(
								$(this).find("td:eq(3)").html() + "...");
					}
		});
	});
</script>
<table id="J_BoughtTable" class="bought-table" data-spm="9"
style="width:100%;">
	<thead>
		<tr class="col-name">
			<th>序号</th>
			<th>话题ID</th>
			<th>标题</th>
			<th>简叙</th>
			<th>创建时间</th>
			<th>修改时间</th>
			<th>话题类型</th>
			<th>点赞数</th>
			<th>分享数</th>
			<th>状态</th>
			<th>置顶</th>
			<th>创建人</th>
			<th>修改人</th>
			<th>操作</th>
		</tr>
	</thead>
	
	<c:if test="${empty pb.result}">
		<tr>
			<td colspan="6">
				<center><img src="${path }/commons/images/no.png" /></center>
			</td>
		</tr>
	</c:if>

	<tbody class="data"  id="showListTbody">
	
		<c:forEach items="${pb.result}" var="pbr" varStatus="status">
			<tr class="order-bd">
				<td style="width:5%;">${status.index + 1}</td>
				<td class="trade-status">${pbr.topicId }</td>
				<td class="trade-status">${pbr.title }</td>
				<td class="trade-status">${pbr.sketich }</td>
				<td class="trade-status"><fmt:formatDate value="${pbr.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td class="trade-status"><fmt:formatDate value="${pbr.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td class="trade-status">
				<c:if test="${pbr.activityType == 1}">
				单品
				</c:if>
				<c:if test="${pbr.activityType == 2}">
				活动
				</c:if>
				<c:if test="${pbr.activityType == 3}">
				视频
				</c:if>
				</td>
				<td class="trade-status">${pbr.likeNum }</td>
				<td class="trade-status">${pbr.shareNum }</td>
				<td class="trade-status">
				<c:if test="${pbr.status == 1}">
				启动
				</c:if>
				<c:if test="${pbr.status == 2}">
				停止
				</c:if>
				</td>
				<td class="trade-status">
				<c:if test="${pbr.stickStatus == 1}">
				置顶
				</c:if>
				<c:if test="${pbr.stickStatus == 2}">
				非置顶
				</c:if>
				</td>
				<td class="trade-status">${pbr.createby }</td>
				<td class="trade-status">${pbr.updateby }</td>
				<td class="trade-status">
					<a class="tm-btn" href="javascript:void(0)" onclick="javascript:operateStatus(1,'','${pbr.topicId }')">置顶</a>
					<a class="tm-btn" target="_blank" href="${path}/activitytop/findActivityTopicById?target=1&TopicId=${pbr.topicId }">查看</a>
					<a class="tm-btn" href="javascript:void(0)" onclick="alertProductDelReason()">预览</a>
					<c:if test="${pbr.status == 1}">
					<a class="tm-btn" href="javascript:void(0)" onclick="javascript:operateStatus(2,2,'${pbr.topicId }')">停止</a>
					</c:if>
					<c:if test="${pbr.status == 2}">
					<a class="tm-btn" href="javascript:void(0)" onclick="javascript:operateStatus(2,1,'${pbr.topicId }')">启用</a>
					</c:if>
					
					<a class="tm-btn" target="_blank" href="${path}/activitytop/findActivityTopicById?target=2&TopicId=${pbr.topicId }">编辑</a>
					<a class="tm-btn" href="javascript:void(0)" onclick="javascript:operateStatus(3,'','${pbr.topicId }')">删除</a>
				</td>
			</tr>
			
<!-- 			<tr style="height:10px;"></tr> -->
		</c:forEach>
	</tbody>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp" %>
</c:if>

