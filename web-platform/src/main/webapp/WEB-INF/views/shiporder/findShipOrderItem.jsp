<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="/com/ccigmall/tag" prefix="song"  %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
</head>
<body>
<!-- 导航 start -->
	 <%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
<div class="center">
<!-- 左边 start -->
	 <div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
		</div>
		
		<!-- 左边 end -->
	<div class="right">
	<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>商品采购&nbsp;&gt;&nbsp; </p>
					<p class="c1">物流通知列表</p>
				</div>
	        </div>
<div class="pu_wrap">
			
		  		<div class="pu_bd">
		  			<table>
		  				<tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="110px;">物流通知单号</th>
		  					<th width="110px;">服务商名称</th>
		  					<th width="80px;">承运方式</th>
		  					<th width="130px;">订单送货地址</th>
		  					<th width="80px;">联系人</th>
		  					<th width="100px;">联系电话</th>
		  					<th width="90px;">制单日期</th>
		  					<th width="110px;">采购订单号</th>
		  					<th width="130px;">WMS业务单据号</th>
		  					<th width="80px;">操作</th>
		  				</tr>
		  				<c:forEach items="${pb.result }" var="Item" varStatus="status">
		  				<tr>
		  					<td>${status.index+1 }</td>
			  				<td>${Item.shipChar }</td>
			  				<td>${Item.serviceName }</td>
			  				<td><song:ContrastTag type="CY" objects="${transportWays }" value="${Item.shipperType }" /></td>
			  				<td>${Item.receiveAddress }</td>
			  				<td>${Item.receiveName }</td>
			  				<td>${Item.receivePhone }</td>
			  				<td><fmt:formatDate value="${Item.createTime }" pattern="yyyy-MM-dd"/></td>
			  				<td>${Item.businessNumberChar }</td>
			  				<td>${Item.id }</td>
			  				<c:choose>
   							<c:when test="${Item.status==10 }">
   							  <td> <a href="<%=path%>/shiporder/selectShipOrder?id=${Item.id }&pid=${Item.businessNumber}">查看</a></td>
   							</c:when>
   							<c:when test="${Item.status==15 }">
   							  <td> <a href="<%=path%>/shiporder/selectShipOrder?id=${Item.id }&pid=${Item.businessNumber}">查看</a></td>
   							</c:when>
   							<c:otherwise>
   							 <td><a href="<%=path%>/shiporder/updateShipOrder?id=${Item.id }&pid=${Item.notificationId}">修改</a></td>
   							</c:otherwise>
  							</c:choose>
		  				</tr>
		  			</c:forEach>
		  			</table>
		  			<c:if test="${!empty pb.result}">
					<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
				</c:if>
		  		</div>

	        </div>
	        
	        </div>
	        </div>
</body>
</html>