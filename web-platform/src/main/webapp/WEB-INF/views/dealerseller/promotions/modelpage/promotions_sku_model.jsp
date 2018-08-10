<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<script type="text/javascript">
	$(document).ready(function(){
			$("#all-selector").click(function(){
				if ($(this).attr("checked") == "checked") { // 全选 
					$("input[type='checkbox'][name='skuId']").each(function() { 
						$(this).attr("checked", true); 
					}); 
				}else{ // 反选 
					$("input[type='checkbox'][name='skuId']").each(function() { 
						 this.checked=!this.checked;
					});
				}
			});
	});
</script>
<form action="">

	<table>
		<tr class="order-hd">
			<th class="head1">商品名称 </th>
			<th class="head2">SUK名称</th>
			<th class="head3">现价</th>
			<th class="head4">直降价格</th>
			<th class="head5">库存</th>
			<th class="head6">预留</th>
		</tr>
		<c:forEach items="${pb.result }" var="promotionsku">
			<tr class="order-bd">
				<td class="baobei"><a href="#" class="pic"><img
						src="${promotionsku.imgURL }"></a></td>
				<td class="suh">${promotionsku.skuName }</td>
				<td class="Price"><span> ￥<i><fmt:formatNumber
								value="${promotionsku.minPrice }" pattern="#0.00"></fmt:formatNumber></i>
						<br>至<br> ￥<i><fmt:formatNumber
								value="${promotionsku.maxPrice }" pattern="#0.00"></fmt:formatNumber></i>
				</span></td>
				<td class="Stock"><input type="text"
					onblur="verificationprice(event)"
					comparedata="${promotionsku.minPrice}" id="promotionPrice"
					name="promotionPrices"></td>
				<td class="time">${promotionsku.usableQty}</td>
				<td class="Supplier"><input type="text"
					onblur="verificationstock(event)"
					comparedata="${promotionsku.usableQty}" id="stock" name="stocks"></td>
			</tr>
		</c:forEach>
	</table>
	<c:if test="${!empty pb.result}">
		<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
	</c:if>
</form>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>

