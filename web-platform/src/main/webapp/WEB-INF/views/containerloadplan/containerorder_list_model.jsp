<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%  
    String path = request.getContextPath();
    String url = request.getServletPath();
	request.setAttribute("url",url);
	request.setAttribute("path",path);
%>
<table class="goodsTable">
	<thead>
		<tr class="goods-tr">
			<th width="300"><span class="le">
			<input type="checkbox" value="" id="quanxuan" class="quanxuan">全选 
			</span class="ri"><span>商品信息</span></th>
			<th width="110">创建时间</th>
			<th width="110">总数量</th>
			<th width="110">创建人</th>
			<th width="110">状态</th>
			<th width="80">操作</th>
		</tr>
	</thead>
	<tbody>
		<tr class="f-h10">
		    <td colspan="6"></td>
		</tr>
		<c:forEach items="${pageBean.result }" var="loadplanList">
			<tr class="order-hd">
				<td class="first" colspan="6">
					<div class="summary">
					
						<span class="number"> 
						<input type="checkbox" name="ck" class="check" value="${loadplanList.id }"> 
						装箱单号：${loadplanList.id }   &nbsp;&nbsp;
						<c:choose> 
						  <c:when test="${empty loadplanList.loadCode }">   
						  	暂无提单 
						  </c:when> 
						  <c:otherwise>   
						   	提单编号：${loadplanList.loadCode }
						  </c:otherwise> 
						</c:choose> 
			</span>
					</div>
				</td>
			</tr>	
			
			<tr class="goods-tr">
				<td class="f-tl">
					
						<ul class="goods-list clearfix">
							<c:forEach items="${loadplanList.proList }" var="productList">
								<li>
									<div class="goods-box">
										<div class="goods-pic">
											<a href="#">
											<img src="${productList.imgUrl}" width="60" height="60">
											</a>
										</div>
									    <div class="goods-txt">
										    <span class="sku">规格：${productList.pName}</span>
										    <span class="amount">数量：${productList.count}</span>
									    </div>
									</div>
								</li>
							</c:forEach>
						</ul>
				</td>
			<td><fmt:formatDate value="${loadplanList.createTime}" type="both"/></td>
			<td>${loadplanList.totalQty}</td>
			<td>${loadplanList.createBy}</td>
			<td>
				<c:if test="${loadplanList.status eq 0}">创建</c:if>
				<c:if test="${loadplanList.status eq 1}">发运</c:if>
			</td>
			<td class="order-doi">
			<c:if test="${!empty buttonsMap['装箱订单-查看'] }">
				<a href="../containerLoadPlan/getLoadPlanDetailsByPlanId?planId=${loadplanList.id }" target="_blank">查看</a>
			</c:if>
			<c:if test="${!empty buttonsMap['装箱订单-修改'] }">
				<a href="../containerLoadPlan/showUpdateContentList?planId=${loadplanList.id }">修改</a>
			</c:if>
			<c:if test="${!empty buttonsMap['装箱订单-删除'] }">
				<a href="javascript:void(0)" onclick="goDelLoadPlan('${loadplanList.id }')">删除</a>
			</c:if> 
			</td>
			</tr>
		</c:forEach>
		<tr class="f-h10">
		    <td colspan="5"></td>
		</tr>
    </tbody>
</table>
<c:if test="${!empty pageBean.result}">
	<%@include file="/WEB-INF/views/dealerbuy/basePage/basePage.jsp" %>	
</c:if>