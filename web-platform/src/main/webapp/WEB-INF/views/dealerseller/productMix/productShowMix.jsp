<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-情景组合列表</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path }/commons/css/comm.css"/>
    <link href="/web-platform/commons/js/my97/skin/WdatePicker.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${path }/commons/js/productMix_list_fn.js"></script>
    <script src="${path}/commons/js/my97/WdatePicker.js"></script>

</head>
<body>
	
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
    <div class="blank"></div>

	<div class="center">
		<!--中间左边开始-->
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->
		<!--中间左边结束-->
		<div class="alert_user3" style="display: none;">
			<div class="bg"></div>
			<div class="w">
				<div class="box1">
					<img src="${path}/commons/images/close.jpg" class="w_close">
				</div>
				<div class="box3">
					<p id="showmsgplat"></p>
				</div>
				<div class="blank20"></div>
			</div>
		</div>
		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>情景组合&nbsp;&gt;&nbsp; </p>
					<p class="c1">查看商品情景组合</p>
				</div>
	        </div>

			<div class="xia">
				<p class="p1">
					<span>创建日期：</span>
					<input type="text" readonly="readonly" value="<fmt:formatDate value='${productMix.creatTime}' pattern="yyyy-MM-dd HH:mm:ss" />">
					<span>创建人员：</span>
					<input type="text" readonly="readonly" value="${fn:escapeXml(productMix.creatOperatorName)}">
				</p>
				<p class="p1">
					<span>商品情景组合名称：</span>
					<input type="text" class="w240" readonly="readonly" value="${productMix.sceneName}" title="${productMix.sceneName}">
				</p>
				<p class="p1">
					<span>商品情景组合图:</span>
					<input type="text" class="w240" readonly="readonly" value="${productMix.detailImageUrl}">

				<div class="fi">
					<div class="bn">
						<a href="" onclick="openWin()" >图片预览 <input id="mixImageUrl" type="hidden" value="${barImageUrl}"/> </a>
					</div>
			    </div>

					
				</p>
				<p class="p1">
					<span>默认组合商品数量:</span>
					<input type="text" readonly="readonly" value="${productMix.sceneNum}">
				</p>
				<p class="p1 fawei">
					<span>使用范围: </span><!--
					<em><input name="" type="checkbox" value=""><label>PC</label></em>
					<em><input name="" type="checkbox" value=""><label>APP</label></em>
					<em><input name="" type="checkbox" value=""><label>WAP</label></em>
					<em><input name="" type="checkbox" value=""><label>PAD</label></em>-->
					<em><input name="" type="checkbox" disabled=true checked="checked"><label>B2C</label></em>
				</p>
				<p class="p1">
					<span>页面显示排列方式:</span>
					<select>
						<option>
							<c:if test="${productMix.showStyle == 0}">
								网格							
							</c:if>
							<c:if test="${productMix.showStyle == 1}">
								列表
							</c:if>
						</option>	
					</select>
					<%-- <span id="wd">横向:</span>
					<c:if test='${productMix.showStyle == 0}'>
						<input type="text" readonly="readonly" value="2">							
					</c:if>
					<c:if test='${productMix.showStyle == 1}'>
						<input type="text" readonly="readonly" value="1">
					</c:if> --%>
						
					<!--<span id="wd">纵向:</span>
					<input type="text">-->
				</p>
			</div>


			<div class="pu_wrap">
				<div class="pu_hd">
				   <h3>商品列表:</h3>
				</div>

				<div class="pu_bd" id="tab">
					<table>
						<tr class="order_hd">
							<th width="80px;">组号</th>
							<th width="130px;">商品ID</th>
							<th width="130px;">商品条码</th>
							<th width="150px;">商品名称</th>
							<th width="120px;">默认标记</th>
							<th width="120px;">默认图片</th>
							<th width="60px;">组合数量</th>
						</tr>
						
					<c:forEach items="${productMix.combinations}" var="com">
						<tr>
							<td>${com.groupNum}</td>
							<td>${com.productId}</td>
							<td>${com.skuCode}</td>
							<td>
								<span title="${com.productName}&nbsp;${com.skuName}">${com.productName}&nbsp;${com.skuName}</span>
							</td>
							<td><c:if test="${com.isDateil == 1 }">默认商品</c:if></td>
							<td>
								<img src="${com.imageUrl}" height="100" width="100">
							</td>
							<td>${com.skuNum}</td>
						</tr>
					</c:forEach>
					</table>
				</div>

			</div>

		</div>
	</div>
			
			
			
			
			




	<div class="blank10"></div>
	 <!-- 底部 start -->
		<div class="t_footer">
				<h1 class="logo_dl"></h1>
		</div>
		<!-- 底部 end -->
		
</body>
</html>