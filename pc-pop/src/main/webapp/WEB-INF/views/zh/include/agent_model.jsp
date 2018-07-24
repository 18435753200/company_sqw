<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/zh/include/base2.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		
			$("#all-b2b-selector").click(function(){
				if ($(this).attr("checked") == "checked") { // 全选 
					$("input[type='checkbox'][name='topProB2B']").each(function() { 
						$(this).attr("checked", true); 
					}); 
				}else{ // 反选 
					$("input[type='checkbox'][name='topProB2B']").each(function() { 
						$(this).attr("checked", false); 
					});
				}
			});
			$("#all-b2c-selector").click(function(){
				if ($(this).attr("checked") == "checked") { // 全选 
					$("input[type='checkbox'][name='topProB2C']").each(function() {
						if($(this).attr("disabled")){
						}else{
							$(this).attr("checked", true);
						}
					});
				}else{ // 反选 
					$("input[type='checkbox'][name='topProB2C']").each(function() {
						if($(this).attr("disabled")){
						}else{
							$(this).attr("checked", false);
						}
					});
				}
			});
	});
</script>


<script type="text/javascript">
/* 删除 */
function prodel(pid){
	if(pid==null&&pid==""){
		alert("选择数据再操作");
		return false;
	}else{
		
		if(window.confirm("确定删除商品!")){
			var pid_array = new Array();
			pid_array.push(pid);
			$.ajax({
				type : "post", 
				url : "deletePros", 
				data:"pid="+pid_array.join(","),
				success : function(msg) { 
					if(msg==1){
						alert("操作成功");
						clickSubmit();
					}else{
						alert("操作失败 ，请稍后再试");
					}
				}
			});

		}

	}
}

//删除dealer
function prodelDealer(pid){
	if(pid==null&&pid==""){
		alert("选择数据再操作");
		return false;
	}else{
		
		if(window.confirm("确定删除商品!")){
			var pid_array = new Array();
			pid_array.push(pid);
			$.ajax({
				type : "post", 
				url : "deleteProsDealer", 
				data:"pid="+pid_array.join(","),
				success : function(msg) { 
					if(msg==1){
						alert("操作成功");
						clickSubmit();
					}else{
						alert("操作失败 ，请稍后再试");
					}
				}
			});

		}

	}
}
</script>


<table id="J_BoughtTable" class="bought-table" data-spm="9"
	style="width: 100%;">
	<thead>
		<tr class="col-name">
			<th>代理姓名(<i style="color: #f10180; font-size: 13px;">${pass.totalCount }</i>)&nbsp;位</th>
			<th>代理地区</th>
			<th>联系电话</th>
			<th>邮箱</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
	</thead>
	
	
	<%-- <tbody class="data">
		<tr class="sep-row">
				<td colspan="6" style="padding: 5px 0 5px 0;">
					<div class="operations">
						<c:if test="${! empty pass.result}">
							<c:if test="${pass.result[0].status == 1 }">
								<input class="all-selector gg" id="all-b2c-selector"
									type="checkbox">
								<label>商品全选/反选</label>
								<a class="tm-btn" href="javascript:void(0);"
									onclick="selectProductPutAway(1)" role="button">批量上架</a>
								<a class="tm-btn" href="javascript:void(0);"
									onclick="alertProductStopReason('',1)" role="button">批量下架</a>
							</c:if>
						</c:if>
					</div>
				</td>
		</tr>
	</tbody> --%>
	



		
	<tbody class="data" id="showListTbody">

<!--        没有数据显示的页面 -->
		<c:if test="${empty pass.result}">
				<tr>
					<td colspan="6">
						<center>
							<img src="${path }/images/no.png" />
						</center>
					</td>
				</tr>
		</c:if>
		
<!--        有数据显示的页面 -->
		<c:if test="${!empty pass.result}">
			<c:forEach items="${pass.result}" var="pbr">
		<%-- 		<tr class="order-hd">
					<td colspan="1">
							<input class="selector" type="checkbox" id="${pbr.supplierId}" name="topPro">
					</td>
					<td>1212</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr> --%>
				
				<tr style="border: 1px solid #e1e8ee;text-align: center;height:80px;">
				
					<td style="border: 1px solid #e1e8ee;" colspan="1">
					${pbr.contact }
					</td>
					<td style="border: 1px solid #e1e8ee;" colspan="1">
					${pbr.contact }
					</td>
					<td style="border: 1px solid #e1e8ee;" colspan="1">
					${pbr.phone }
					</td>
					<td style="border: 1px solid #e1e8ee;" colspan="1">
					${pbr.email }
					</td>
					<td style="border: 1px solid #e1e8ee;" colspan="1">
					未审核
<%-- 					${pbr.status } --%>
					</td>
					<td style="border: 1px solid #e1e8ee;" colspan="1">
						<p>
								<a class="tm-btn" style="color:grey" href="javascript:void(0);" 
									onclick="zPro('showProduct?psupplierId=${pbr.supplierId}')">查看
								</a>
						</p>
						<p>
							    <a class="tm-btn" style="color:grey" href="javascript:void(0);" 
									onclick="zPro('showProduct?psupplierId=${pbr.supplierId}')">二维码
								</a>
						</p>
						<p>
							    <a class="tm-btn" style="color:grey" href="javascript:void(0);" 
									onclick="zPro('showProduct?psupplierId=${pbr.supplierId}')">充值
								</a>
						</p>
					</td>
					
					
<!-- 					<td> -->
<!-- 						<i>  -->
<%-- 							<span>${pbr.moneyUnitSymbols} --%>
<%-- 								 <c:choose> --%>
<%-- 									<c:when test="${pbr.productPriceMin==pbr.productPriceMax}"> --%>
<%-- 										<fmt:formatNumber pattern="0.00#" --%>
<%-- 											value="${pbr.productPriceMin}"></fmt:formatNumber> --%>
<%-- 									</c:when> --%>
<%-- 									<c:otherwise> --%>
<%-- 										<fmt:formatNumber pattern="0.00#" --%>
<%-- 											value="${pbr.productPriceMin}"></fmt:formatNumber> --%>
<!-- 											 ~ -->
<%-- 											 <fmt:formatNumber pattern="0.00#" --%>
<%-- 											value="${pbr.productPriceMax}"></fmt:formatNumber> --%>
<%-- 									</c:otherwise> --%>
<%-- 								</c:choose> --%>
<!-- 							</span> -->
<!-- 						</i> -->
<!-- 					</td> -->


<%-- 					<td class="trade-status">${pbr.strCreatedate }</td> --%>
					
<%-- 					<td class="trade-status">${pbr.strCreatedate }</td> --%>
					
					
<%-- 					<c:if test="${pbr.counterfeittypeid==0}"> --%>
<!-- 						<td class="b2"><i> 待审核 </i></td> -->
<%-- 					</c:if> --%>
<%-- 					<c:if test="${pbr.counterfeittypeid==1}"> --%>
<!-- 						<td class="b2"><i> 未通过 </i></td> -->
<%-- 					</c:if> --%>
<%-- 					<c:if test="${pbr.counterfeittypeid==5}"> --%>
<!-- 						<td class="b2"><i>审核驳回 </i></td> -->
<%-- 					</c:if> --%>
					
					
<!-- 					<td class="trade-operate"> -->
<%-- 						<c:if test="${supplierId == pbr.supplierid }"> --%>
<%-- 							<c:choose> --%>
<%-- 								<c:when test="${pbr.counterfeittypeid==1}"> --%>
<!-- 									<p> -->
<!-- 										<a class="tm-btn" href="javascript:void(0);" -->
<%-- 											onclick="zPro('${pageContext.request.contextPath}/product/toEditPOPUI?productId=${pbr.productId}&type=1')">修改</a> --%>
<!-- 									</p> -->
<!-- 									<p> -->
<!-- 										<a class="tm-btn" href="javascript:void(0);" -->
<%-- 											onclick="prodel('${pbr.productId}')">删除</a> --%>
<!-- 									</p> -->
<%-- 								</c:when> --%>
<%-- 								<c:when test="${pbr.counterfeittypeid==5}"> --%>
<!-- 									<p> -->
<!-- 										<a class="tm-btn" href="javascript:void(0);" -->
<%-- 											onclick="zPro('${pageContext.request.contextPath}/product/toEditPOPUI?productId=${pbr.productId}&type=1')">修改</a> --%>
<!-- 									</p> -->
<!-- 									<p> -->
<!-- 										<a class="tm-btn" href="javascript:void(0);" -->
<%-- 											onclick="prodel('${pbr.productId}')">删除</a> --%>
<!-- 									</p> -->
<%-- 								</c:when> --%>
<%-- 							</c:choose> --%>
<!-- 							<p> -->
<!-- 								<a class="tm-btn" href="javascript:void(0);" -->
<%-- 									onclick="zPro('${pageContext.request.contextPath}/product/toShowPOPUI?productId=${pbr.productId}')">查看</a> --%>
<!-- 							</p> -->

<%-- 						</c:if> <c:if test="${supplierId != pbr.supplierid }"> --%>
<!-- 							<p> -->
<!-- 								<a class="tm-btn" href="javascript:void(0);" -->
<%-- 									onclick="zPro('showProduct?productId=${pbr.productId}')">查看</a> --%>
<!-- 							</p> -->
<!-- 							<p> -->
<!-- 								<a class="tm-btn" href="javascript:void(0);" -->
<%-- 									onclick="editInventory(${pbr.productId}, ${supplierId})">修改库存</a> --%>

<!-- 							</p> -->
<%-- 						</c:if></td> --%>
				</tr>
				<tr style="height: 10px;"></tr>
			</c:forEach>
		</c:if>


</tbody></table>


<c:if test="${!empty pass.result}">
	<%@include file="/WEB-INF/views/zh/agent/include/page.jsp" %>
</c:if> 

