<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="/com/ccigmall/tag" prefix="song"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
<title>Insert title here</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
    <script type="text/javascript">
    	function audit(){
			var flag = false;
			var opetion = $('#sto .sho');
			for ( var i = 0; i < opetion.length; i++) {
				var op1 = opetion[i].getElementsByTagName('input')[0];
				
				if (op1.checked == true) {
					flag = true;
				}
			}
		
			if (!flag) {
				alert("请选择需要审核的记录");
				return false;
			}
			
			var fm_data = $('#allocationForm').serialize();
			$.ajax({
				type : "post",
				url : "${path}/purchasecost/auditAllocation",
				data : fm_data + "&random=" + Math.random(),
				dataType : "json",
				success : function(data) {
					if(data.success){
						alert(data.message);
						//window.location.href = "${path}/purchasecost/findPurchaseCost";
					}else{
						alert(data.message);
					};
				},
				error:function(json){
		            alert("处理异常");
		        }
			});
		}
    </script>
</head>
<body>
<div class="pu_wrap">
		  		<div class="pu_hd">
		  		<h3>采购明细</h3>
		  			<div class="btn"><a href="javascript:void(0);" onclick="getOrders($(this));">增加费用</a>
		  			&nbsp;&nbsp;&nbsp;&nbsp;
		  			<a href="javascript:void(0);" onclick="audit();">审核</a></div>
		  		</div>
		  		<div class="pu_bd">
		  			<form id="allocationForm">
				
		  			<table id = "sto">
		  				<tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="50px;">选择</th>
		  					<th width="150px;">采购订单编号</th>
		  					<th width="200px;">供应商名称</th>
		  					<th width="100px;" >金额</th>
		  					<th width="100px;" >制单日期</th>
		  					<th width="100px;" >制单人</th>
		  					<th width="100px;" >订单状态</th>
		  				
		  				</tr>
		  				<c:forEach items="${pb.result }" var="result" varStatus="status">
		  				<tr class="sho">
		  					<td>${status.index+1 }</td>
			  				<td><input type="checkbox" name="PurchaseCostId" value="${result.id }" onclick="pushValue($(this).attr('checked'),$(this).attr('qty'),$(this).attr('price'));"></td>
			  				<td>${result.businessNumberChar }</td>
			  				<td>${result.supplierName }</td>
			  				<td>${result.itemTotalPrice }</td>
			  				<td><fmt:formatDate value="${result.createTime }" pattern="yyyy-MM-dd"/></td>
			  				<td>${result.createBy }</td>
			  				<td><song:OrderStatusTag type="CG" value="${result.orderStatus }"/></td>
			  				</tr>
		  				</c:forEach>
		  				
		  			</table>
		  			<c:if test="${!empty pb.result}">
						<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
					</c:if>
					</form>
		  		</div>

		  	   <!-- <div class="btn">
		  	   		<a href="javascript:void(0)" onclick="CreateRow();">新增</a>
		  	   		<a href="javascript:void(0)" onclick="editDetil();">修改</a>
			     	<a href="javascript:void(0)" onclick="saveDetil();">保存</a>
			     	<a href="javascript:void(0)" onclick="checkPurchase();">审核</a>
	           </div> -->
	           </div>
</body>
</html>