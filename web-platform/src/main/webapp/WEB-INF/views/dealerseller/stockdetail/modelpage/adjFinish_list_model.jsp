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
			  				<th width="80px">批次号</th>
			  				<th width="80px">库位</th>
			  				<th width="80px">仓库</th>
			  				<th width="80px">原库存数量</th>
			  				<th width="75px">调整量</th>
			  				<th width="80px">现库存数量</th>
			  				<th width="80px">调整日期</th>
			  				<th width="80px">调整人</th>
			  				<th width="80px">库存调整状态</th>
			  			</tr>
			  			
		  				<c:forEach items="${pb.result }" var="adjFinishVo" varStatus="index">
			  				<c:if test="${adjFinishVo.ediflag eq 'S' || adjFinishVo.ediflag eq 'F'}">
				  				<tr>
					  				<td>${index.index+1 }</td>
									<td>${adjFinishVo.skuId }</td>
					  				<td>${adjFinishVo.sku }</td>
					  				<td>${adjFinishVo.isbnIsrc }</td>
					  				<td>${adjFinishVo.pName}</td>
					  				<td>${adjFinishVo.lot}</td>
					  				<td>${adjFinishVo.loc}</td>
					  				<td>
					  					<c:if test="${adjFinishVo.whseid eq 'WMWHSE1'}">北京仓</c:if>
					  					<c:if test="${adjFinishVo.whseid eq 'WMWHSE2'}">米兰仓</c:if>
					  				</td>
				  					<td>${adjFinishVo.sysqty-adjFinishVo.adjqty}</td>
					  				<td>${adjFinishVo.adjqty }</td>
					  				<td>${adjFinishVo.sysqty }</td>
					  				<td>${adjFinishVo.adddate }</td>
					  				<td>${adjFinishVo.addwho }</td>
					  				<td>
						  				<c:if test="${adjFinishVo.ediflag eq 'S'}">成功</c:if>
						  				<c:if test="${adjFinishVo.ediflag eq 'F'}">失败</c:if>   
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
