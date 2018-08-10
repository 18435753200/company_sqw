<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>发票登记查询</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/inventory.css">
     <script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
</head>
<body>
<div class="pu_bd">
	  			<table>
	  				<tbody>
	  				<tr class="order_hd">
	  					<th width="40px;">序号</th>
	  					<th width="140px;">发票登记单编号</th>
	  					<th width="140px;">发票号码</th>
	  					<th width="260px;">供应商名称</th>
	  					<th width="140px;">发票金额(元)</th>
	  					<th width="120px;">操作</th>
	  				</tr>
	  				<c:forEach items="${pb.result }" var="result" varStatus="status">
	  				<tr>
	  					<td>${status.index+1}</td>
		  				<td>${result.invoiceChar}</td>
		  				<td>${result.invoiceNumber}</td>
		  				<td>${result.supplierName}</td>
		  				<td>${result.invoiceTotalPrice}</td>
		  				<td>
		  				<c:if test="${result.status==1 }"><a href="${path }/purchasecost/InvoiceUpt?sid=${result.sid}">修改</a></c:if>
		  				<c:if test="${result.status==10 }"><a href="${path }/purchasecost/InvoiceSelect?sid=${result.sid}">查看</a></c:if>
		  				</td>
	  				</tr>
	  				</c:forEach>
	  			</tbody>
	  			</table>
	  			<c:if test="${!empty pb.result}">
					<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
				</c:if>
	  		</div>
	</body>
</html>