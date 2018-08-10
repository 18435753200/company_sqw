<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>

<div class="pu_wrap">
	<div class="pu_hd">
		<h3>WMS推送错误信息查询列表</h3>
		<div class="btn"></div>
	</div>
	<div class="pu_bd">
		<table>
			<tbody>
				<tr class="order_hd">
					<th width="60px;">序号</th>
					<th width="160px;">信息ID</th>
					<th width="160px;">平台标记</th>
					<th width="90px;">JSON消息</th>
					<th width="140px;">消息类型</th>
					<th width="120px;">接口名称</th>
					<th width="120px;">创建时间</th>
					<th width="120px;">修改时间</th>
					<th width="120px;">发送时间</th>
					<th width="120px;">状态</th>
					<th width="120px;">发送次数</th>
					
					<th width="120px;">扩展1</th>
					<th width="120px;">扩展2</th>
					<th width="120px;">扩展3</th>
					<th width="120px;">平台时间</th>
				</tr>
				<c:forEach items="${pb.result }" var="logisticsThtxMessage" varStatus="status">
					<tr>
						<td>${status.index + 1 }</td>
						<td>${logisticsThtxMessage.messageId}</td>
						<td>${logisticsThtxMessage.platformMark}</td>
						
						<td> <a href="${path}/errorInfo/selectByPrimaryKey?messageId=${logisticsThtxMessage.messageId}"  target="_blank">查看</a>
						<!-- <td> <a href="javascript:void(0)" onclick="selectById('${logisticsThtxMessage.messageId}')" >查看</a> -->
						<!-- <td>${logisticsThtxMessage.messageType}</td> -->
						
						<c:choose>
							<c:when test="${logisticsThtxMessage.messageType==1}">
								<td>入库</td>
							</c:when>
							<c:when test="${logisticsThtxMessage.messageType==2}">
								<td>出库</td>
							</c:when>
							<c:otherwise>
								<td>物流</td>
							</c:otherwise>
						</c:choose>
						
						<td>${logisticsThtxMessage.interfaceName}</td>
						<td><fmt:formatDate value="${logisticsThtxMessage.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
						<td><fmt:formatDate value="${logisticsThtxMessage.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" />
						<td><fmt:formatDate value="${logisticsThtxMessage.sendTime}" pattern="yyyy-MM-dd HH:mm:ss" />
						<!-- <td>${logisticsThtxMessage.status}</td>  -->
						<c:choose>
							<c:when test="${logisticsThtxMessage.status==0}">
								<td>未处理</td>
							</c:when>
							<c:when test="${logisticsThtxMessage.status==1}">
								<td>发送成功</td>
							</c:when>
							<c:otherwise>
								<td>数据异常</td>
							</c:otherwise>
						</c:choose>
						
						<td>${logisticsThtxMessage.sendNum}</td>
						<td>${logisticsThtxMessage.businessExt1}</td>
						<td>${logisticsThtxMessage.businessExt2}</td>
						<td>${logisticsThtxMessage.businessExt3}</td>
						<td><fmt:formatDate value="${logisticsThtxMessage.platformTime}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${!empty pb.result}">
			<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
		</c:if>
	</div>
</div>

