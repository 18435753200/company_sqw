<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="/com/ccigmall/tag" prefix="song"  %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/manual.css">
</head>
<body>
	<div class="pu_wrap">
		  		<div class="pu_bd">
		  			<table>
		  				<tbody><tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="40px;">选择</th>
		  					<th width="150px;">出库申请单编号</th>
		  					<th width="110px;">出库日期</th>
		  					<th width="150px;">库房名称</th>
		  					<th width="110px;">出库类型</th>
		  					<th width="120px;">申请人</th>
		  					<th width="110px;">单据状态</th>
		  					<th width="110px;">操作</th>
		  				</tr>
		  				<c:forEach items="${pb.result }" var="result" varStatus="status">
		  				<tr>
		  					<td>${status.index+1 }</td>
			  				<td><input type="checkbox" name="apply" value="${result.sid}"></td>
			  				<td><a href="${path }/Apply/findApplyOrderItemList?id=${result.sid}" target="_blank">${result.applyChar }</a></td>
			  				<td><fmt:formatDate value="${result.createTime }" pattern="yyyy-MM-dd"/></td>
			  				<td>${result.warehouseName }</td>
			  				<td><song:ApplyTag value="${result.outType }"/></td>
			  				<td>${result.createBy }</td>
			  				<td><c:choose>
   <c:when test="${result.status==10}">  
     <img src="<%=path%>/commons/images/flag_yellow.png" style="width:25px; height:25px;">
   </c:when>
   <c:otherwise> 
    <img src="<%=path%>/commons/images/flag_red.png" style="width:25px; height:25px;">
   </c:otherwise>
  </c:choose></td>
  					<td><c:choose>
   <c:when test="${result.status==10}">  
    <a href="${path }/Apply/ApplyFind?id=${result.sid}" target="_blank">查看</a> 
   </c:when>
   <c:otherwise> 
    <a href="${path }/Apply/ApplyUpdate?id=${result.sid}" target="_blank">修改</a>
   </c:otherwise>
  </c:choose></td>
		  				</tr>
		  				</c:forEach>
		  			</tbody>
		  			</table>
		  			<c:if test="${!empty pb.result}">
					<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
				</c:if>
		  		</div>
	        </div>
</body>
</html>