<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			 <div class="pu_bd">
		  			<table>
		  				<tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="50px;">选择</th>
		  					<th width="105px;">物流通知单号</th>
		  					<th width="105px;">入库通知单编号</th>
		  					<th width="105px;">入库单编号</th>
		  					<th width="90px;">服务商名称</th>
		  					<th width="60px;">运输方式</th>
		  					<th width="90px;">接货地点</th>
		  					<th width="105px;">采购订单编号</th>
		  					<th width="105px;">操作</th>
		  				</tr>
		  				<c:forEach items="${pb.result }" var="result" varStatus="status">
		  				<tr>
		  					<td>${status.index+1 }</td>
			  				<td><input type="checkbox" name="regid" value="<c:choose><c:when test="${result.id==null}">0</c:when><c:otherwise>${result.id } </c:otherwise></c:choose>,<c:choose><c:when test="${result.notificationId==null }">0</c:when><c:otherwise>${result.notificationId }</c:otherwise></c:choose>,<c:choose><c:when test="${result.shipId==null }">0</c:when><c:otherwise>${result.shipId }</c:otherwise></c:choose>,<c:choose><c:when test="${result.businessNumber==null }">0</c:when><c:otherwise>${result.businessNumber }</c:otherwise></c:choose>"></td>
			  				<td>${result.shipId }</td>
			  				<td>${result.notificationId }</td>
			  				<td>${result.id }</td>
			  				<td>${result.serviceName }</td>
			  				<td>${result.shipperType }</td>
			  				<td>${result.receiveAddress }</td>
			  				<td>${result.businessNumber }</td>
			  				<td><a href="<%=path%>/purchasereg/setReg?id=<c:choose><c:when test="${result.id==null}">0</c:when><c:otherwise>${result.id }</c:otherwise></c:choose>&notificationId=<c:choose><c:when test="${result.notificationId==null }">0</c:when><c:otherwise>${result.notificationId }</c:otherwise></c:choose>&shipId=<c:choose><c:when test="${result.shipId==null }">0</c:when><c:otherwise>${result.shipId }</c:otherwise></c:choose>&businessNumber=<c:choose><c:when test="${result.businessNumber==null }">0</c:when><c:otherwise>${result.businessNumber }</c:otherwise></c:choose>&qty=10&price=100">设置</a>|<a href="<%=path%>/purchasereg/editReg?id=<c:choose><c:when test="${result.id==null}">0</c:when><c:otherwise>${result.id }</c:otherwise></c:choose>&notificationId=<c:choose><c:when test="${result.notificationId==null }">0</c:when><c:otherwise>${result.notificationId }</c:otherwise></c:choose>&shipId=<c:choose><c:when test="${result.shipId==null }">0</c:when><c:otherwise>${result.shipId }</c:otherwise></c:choose>&businessNumber=<c:choose><c:when test="${result.businessNumber==null }">0</c:when><c:otherwise>${result.businessNumber }</c:otherwise></c:choose>&qty=10&price=100">修改</a>|<a href="<%=path%>/purchasereg/editPurchaseRegDetil?id=<c:choose><c:when test="${result.id==null}">0</c:when><c:otherwise>${result.id }</c:otherwise></c:choose>&notificationId=<c:choose><c:when test="${result.notificationId==null }">0</c:when><c:otherwise>${result.notificationId }</c:otherwise></c:choose>&shipId=<c:choose><c:when test="${result.shipId==null }">0</c:when><c:otherwise>${result.shipId }</c:otherwise></c:choose>&businessNumber=<c:choose><c:when test="${result.businessNumber==null }">0</c:when><c:otherwise>${result.businessNumber }</c:otherwise></c:choose>">审核</a></td>
		  				</tr>
		  				</c:forEach>
		  			</table>
		  			<c:if test="${!empty pb.result}">
					<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
				</c:if>
		  		</div>
		  		

	    <!--国内运费登记开始-->
		
	</div>
	</div>
</body>
</html>