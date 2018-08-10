<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>

 <div class="pu_wrap">
		  		
		  		<div class="pu_bd">
			  		<table>
			  			<tr class="order_hd">
			  			    <th width="40px">序号</th>
			    			<th width="130px">商品编号</th>
			  				<th width="130px">商品sku</th>
			  				<th width="130px">商品条码</th>
			  				<th width="130px">商品名称</th>
			  				<th width="80px">仓库</th>
			  				<th width="80px">原产品状态</th>
			  				<th width="75px">冻结量</th>
			  				<th width="80px">现产品状态</th>
			  				<th width="80px">冻结日期</th>
			  				<th width="80px">操作人</th>
			  				<th width="80px">库存冻结状态</th>
			  			</tr>
			  			
		  				<c:forEach items="${pb.result }" var="moveFinishVo" varStatus="index">
			  				<c:if test="${moveFinishVo.ediflag eq 'S' || moveFinishVo.ediflag eq 'F'}">
				  				<tr>
					  				<td>${index.index+1 }</td>
									<td>${moveFinishVo.skuId }</td>
					  				<td>${moveFinishVo.sku }</td>
					  				<td>${moveFinishVo.isbnIsrc }</td>
					  				<td>${moveFinishVo.pName}</td>
					  				<td>
					  					<c:if test="${moveFinishVo.whseid eq 'WMWHSE1'}">北京仓</c:if>
					  					<c:if test="${moveFinishVo.whseid eq 'WMWHSE2'}">米兰仓</c:if>
					  				</td>
				  					<td>
				  						<c:if test="${empty moveFinishVo.fromloc || moveFinishVo.fromloc eq '1'}">正品</c:if>
				  						<c:if test="${moveFinishVo.fromloc eq '999'}">残品</c:if>
				  					</td>
					  				<td>${moveFinishVo.qty}</td>
					  				<td>
										<c:if test="${empty moveFinishVo.toloc || moveFinishVo.toloc eq '1'}">正品</c:if>
				  						<c:if test="${moveFinishVo.toloc eq '999'}">残品</c:if>
					  				</td>
					  				<td>${moveFinishVo.adddate }</td>
					  				<td>${moveFinishVo.addwho }</td>
					  				<td>
						  				<c:if test="${moveFinishVo.ediflag eq 'S'}">成功</c:if>
						  				<c:if test="${moveFinishVo.ediflag eq 'F'}">失败</c:if>   
					  				</td>
					  			</tr>
				  			</c:if>
			  			</c:forEach>
			  			
			  			
			  		</table>
			  		<c:if test="${!empty pb.result}">
					<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
					</c:if>
		  		</div>
		  </div>
