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
<table>
	<tr class="order-hd">
	<th class="head1">商品名称</th>
	<th class="head2"><input type="checkbox" class="all" id="all-selector">全选 SUK名称</th>
	<th class="head3">价格</th>
	<th class="head4">期货库存</th>
	<th class="head4">现货库存</th>
	<th class="head5">发布时间</th>
	<th class="head6">供应商</th>
	</tr>
	<c:forEach items="${pb.result }" var="product">
		<tr class="order-bd">
			<td class="baobei" style="border-bottom: none;">
				<a href="#" class="pic"> <img src="${fn:escapeXml(product.prodImgURL)}"> </a>
				<div class="desc">
					<i title="${fn:escapeXml(product.productName)}">
						${fn:escapeXml(product.productName)} </i>
				</div>
			</td>
			<td colSpan="6">
				<c:forEach items="${product.skuList }" var="skuList">
					<div class="t1">
						<span class="suh" title="100g">
							<input type="checkbox" name="skuId" <c:if test="${skuList.chosePromo }">checked="checked"</c:if> value="${fn:escapeXml(skuList.skuId)}">${fn:escapeXml(skuList.skuNameCn)}
						</span>
						<span class="Price">
							<fmt:formatNumber value="${skuList.productPriceMin }" type="currency" pattern="#0.00"/>
							~
							<fmt:formatNumber value="${skuList.productPriceMax }" type="currency" pattern="#0.00"/>
						</span> 
						<span class="Stock" title="${skuList.futures }">${skuList.futures }</span> 
						<span class="Stock" title="${skuList.spot }">${skuList.spot }</span> 
						<span class="time"><fmt:formatDate value="${skuList.createddate }" pattern="yyyy-MM-dd HH:mm"/></span> 
						<span class="Supplier" title="${skuList.supplierName }">${skuList.supplierName }</span>
					</div>
				</c:forEach>
			</td>
		</tr>
	</c:forEach>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp" %>
</c:if>

