<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>


<div class="sq">
	<c:forEach items="${productskus }" var="productsku">
	
		<div class="tabborder">
		   
			<div class="t1">
			
				
				<span>SKU号</span> 
				<span>规格</span>
				<span>条形码</span>
				
				
			</div>
			
			<div class="t2">
			
				
				
				<span>${productsku.skuId}<input type="hidden" name="pName" value="${productsku.productNameCn }"></span> 
				
				<span class="ov2" title="${fn:escapeXml(productsku.skuNameCn)}">${fn:escapeXml(productsku.skuNameCn)}</span>
				
				<span class="ov2" >${fn:escapeXml(productsku.skuCode)}</span>
				
				
				
				
			</div>
		
			<table class="sq_wrap">
			
				<c:choose>
				
					<c:when test="${productsku.sheilLife>0}">
						
						<tr>
						
							<th width="175px">批次号</th>
							<th width="175px">数量</th>
							<th width="175px">生产时间</th>
							<th width="175px">过期时间</th>
							<th width="175px">仓库名称</th>
							<th width="44px">操作</th>
							
						</tr>
						
						<c:forEach items="${productskumaps }" var="productskumaps">
						
							<c:if test="${productskumaps.key==productsku.skuId&&productskumaps.value!=null}">
								
								<c:forEach items="${productskumaps.value }" var="productskumap">
								
									<tr>
									
										<td class="pici">${productskumap.batchNo }</td>
										<td>${productskumap.qty }</td>
										<td><fmt:formatDate value="${productskumap.productTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td><fmt:formatDate value="${productskumap.lastproductTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td name="${fn:escapeXml(productskumap.warehouseName)}">${productskumap.warehouseName}</td>
										<td>&nbsp;-	&nbsp;</td>
										
									</tr>
							
								</c:forEach>
							
							</c:if>
							
						</c:forEach>
						
					</c:when>
					
					<c:otherwise>
					
						<tr>
						
							<th width="175px">批次号</th>
							<th width="175px">数量</th>
							<th width="175px">仓库名称</th>
							<th width="44px">操作</th>
							
						</tr>
						
						<c:forEach items="${productskumaps }" var="productskumaps">
						
							<c:if test="${productskumaps.key==productsku.skuId&&productskumaps.value!=null}">
							
								<c:forEach items="${productskumaps.value }" var="productskumap">
								
									<tr class="sq_b">
									
										<td class="pici">${productskumap.batchNo }</td>
										<td>${productskumap.qty }</td>
										<td name="${fn:escapeXml(productskumap.warehouseName)}">${fn:escapeXml(productskumap.warehouseName)}</td>
										<td>&nbsp;-	&nbsp;</td>
										
									</tr>
									
								</c:forEach>
								
							</c:if>
							
						</c:forEach>
						
					</c:otherwise>
					
				</c:choose>
				
			</table>
			
			<c:choose>
			
				<c:when test="${productsku.sheilLife>0}">
				
					<c:if test="${! empty buttonsMap['货品列表-补录-添加批次'] }">	
					
						<button class="zengj" type="button" onclick="addtime(event,'${productsku.skuId}','${productsku.skuNameCn}')">添加</button>
					
					</c:if>
					
				</c:when>
				
				<c:otherwise>
				
					<c:if test="${! empty buttonsMap['货品列表-补录-添加批次'] }">	
				
						<button class="zengj" type="button" onclick="add(event,'${productsku.skuId}','${productsku.skuNameCn}')">添加</button>
					
					</c:if>
					
				</c:otherwise>
				
			</c:choose>
			
			<div style="border: 1px solid #c7c7c7; margin: 10px 0 20px 0;"></div>
			
		</div>
		
	</c:forEach>
	
	<c:if test="${! empty buttonsMap['货品列表-补录-保存'] }">	
	
		<button class="sub" type="button" onclick="fmsubmit()">提交</button>
	
	</c:if>
	
</div>