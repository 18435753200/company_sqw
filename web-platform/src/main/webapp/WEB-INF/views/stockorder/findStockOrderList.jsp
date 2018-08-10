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

<div class="pu_wrap">
				<div class="pu_hd"><h3>入库通知列表</h3>
				<div class="btn">
	        		<a href="javascript:void(0);"  onclick="writePDF($(this));">打印</a>
	        		
	        	</div>
				</div>
		  		<div class="pu_bd">
		  			<table>
		  				<tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="50px;">选择</th>
		  					<th width="110px;">入库通知单号</th>
		  					<th width="110px;">服务商名称</th>
		  					<th width="80px;">承运方式</th>
		  					<th width="100px;">订单送货地址</th>
		  					<th width="100px;">联系人</th>
		  					<th width="80px;">联系电话</th>
		  					<th width="60px;">制单日期</th>
		  					<th width="110px;">采购订单号</th>
		  				</tr>
		  				<c:forEach items="${pb.result }" var="result" varStatus="status">
		  				<tr>
		  					<td>${status.index+1 }</td>
			  				<td><input type="checkbox" name="OrderItem" value="${result.id}"></td>
			  				<td><a href="<%=path%>/stockorder/findStockOrderItem?id=${result.id}">${result.notificationChar}</a></td>
			  				<td>${result.serviceName}</td>
			  				<td><song:ContrastTag type="CY" objects="${transportWays }" value="${result.shipperType }" /></td>
			  				<td>${result.sendAddress }</td>
			  				<td>${result.receiveName }</td>
			  				<td>${result.receivePhone }</td>
			  				<td><fmt:formatDate value="${result.createTime }"  pattern="yyyy-MM-dd"/></td>
			  				<td>${result.businessNumberChar }</td>
		  				</tr>
		  			</c:forEach>
		  			</table>
		  			<c:if test="${!empty pb.result}">
					<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
				</c:if>
		  		</div>

	        </div>
</body>
</html>