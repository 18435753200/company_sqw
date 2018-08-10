<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<div class="close-box"></div>
		<div class="lightbox-box-hd">
			<h2>商品列表</h2>
		</div>
		<div class="lightbox-box-bd">
			<table class="copyData-tb">
			    <colgroup>
				    <col>
				    <col width="20%">
				    <col width="20%">
			    </colgroup>
			    <thead>
			        <tr>
			            <td colspan="3"><p>已复制</p></td>
			        </tr>
			    </thead>
			    <tbody>
			        <tr>
			            <th scope="col">商品名称</th>
			            <th scope="col">商品ID</th>
			            <th scope="col">商品编码</th>
			        </tr>
			        <tr>
			            <td><div class="copy-goods">
			                    <div class="goods-pic"><img src="${fn:escapeXml(pageBean.parameter.seleByPidStatus) }" width="80" height="80" /></div>
			                    <div class="goods-text"><span>${fn:escapeXml(pageBean.parameter.productName) }</span></div>
			                </div></td>
			            <td><p>${fn:escapeXml(pageBean.parameter.productId) }</p></td>
			            <td><p>${fn:escapeXml(pageBean.parameter.businessProdId) }</p></td>
			        </tr>
			    </tbody>
			</table>
			<table class="copyData-tb">
			    <colgroup>
				    <col>
				    <col width="20%">
				    <col width="20%">
				    <col width="20%">
			    </colgroup>
			    <thead>
			        <tr>
			            <td colspan="4"><p>可合并的商品</p></td>
			        </tr>
			    </thead>
			    <tbody>
			        <tr>
			            <th scope="col">商品名称</th>
			            <th scope="col">商品ID</th>
			            <th scope="col">商品编码</th>
			            <th scope="col">操作</th>
			        </tr>
			        <c:forEach items="${pageBean.result }" var="pb">
				        <tr>
				            <td><div class="copy-goods">
				                    <div class="goods-pic"><img src="${fn:escapeXml(pb.imgURL) }" width="80" height="80" /></div>
				                    <div class="goods-text"><span>${fn:escapeXml(pb.productName) }</span></div>
				                </div></td>
				            <td><p>${fn:escapeXml(pb.productId) }</p></td>
				            <td><p>${fn:escapeXml(pb.businessProdId) }</p></td>
				            <td><input type="button" value="数据覆盖" class="copyData-btn" onclick="coverProduct('${fn:escapeXml(pb.productId) }','${fn:escapeXml(pageBean.parameter.productId) }')"></td>
			        	</tr>
			        </c:forEach>
			    </tbody>
			</table>

		</div>