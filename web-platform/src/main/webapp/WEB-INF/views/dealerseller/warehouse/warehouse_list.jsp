<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-库存管理</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/ckManage.css" />
	<script>
	$(document).ready(function(){
		$(".ho").live("hover",onMouseOver);
		$(".ho").live("mouseleave",onMouseLeave);
	});
	function onMouseOver(){
		var td = $(this);
		td.find(".reply").show();
	}
	
	function onMouseLeave(){
		var td = $(this);
		td.find(".reply").hide();
	}
</script>
</head>
<body>

<%@include file="/WEB-INF/views/include/header.jsp"%>

	
	<div class="center">
		<!-- 左边 start -->
		
	 <div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
	 </div>
	 
		<!-- 左边 end -->
		
	 <!--左边右边-->
	 <div class="c2">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>商品管理&nbsp;&gt;&nbsp; </p>
					<p class="c1">仓库管理</p>
				</div>
			</div>
			<div class="blank10"></div>
			
			<div class="c22">
			
			<!-- 所有订单 -->
			<div class="ck-hd">
   				<c:if test="${! empty buttonsMap['仓库管理-添加仓库'] }">		
   					 <span class="add-ck-btn"><a href="../warehouse/goAddWareHouse" id="addoreditwarehouse">添加仓库</a></span>
   				</c:if>
   			</div>
			
			<div class="c3">
					

			</div>
			
		</div>

  </div>
	  <div class="blank10"></div>
	<!--下一页-->
	
</div>
 <%@include file="/WEB-INF/views/include/foot.jsp" %>
<div id="add-CK" class="lightbox" style="display: none;">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box"></div>
		<div class="lightbox-box-hd">
			<h2>添加仓库</h2>
		</div>
		<form id="addwarehousefm">
			<div class="lightbox-box-bd">
					<dl>
						<dt>仓库CODE：</dt>
						<dd>
							<input type="text" class="add-CK-text" name="warehouseCode" id="codeId" />
							<input type="hidden" name="warehouseCode" id="codeIdHid" />
							<input type="hidden" name="id" id="id" />
						</dd>
					</dl>
					
					<dl>
						<dt>仓库类型：</dt>
						<dd>
							<select class="add-CK-s" name="warehouseType" id="warehouseType">
							<option value="0">请选择</option>
							<option value="1">国内仓库</option>
							<option value="2">保税区仓库</option>
							<option value="3">海外转运仓</option>
							</select>
						</dd>
					</dl>
					
					<dl>
						<dt>经销商名称：</dt>
						<dd>
							<input type="hidden" name="dealerName" id="dealerName" value="" />
							<select class="add-CK-s" name="dealerId" id="dealerId" onchange="fuzhi()"><option>请选择</option></select>
						</dd>
					</dl>
					<dl>
						<dt>仓库名称：</dt>
						<dd>
							<input type="text" class="add-CK-text" name="warehouseName" id="codeName" />
						</dd>
					</dl>
					<dl>
						<dt>仓库地址：</dt>
						<dd>
							<select class="add-CK-sel" name="provinceId" id="codeProvince"><option>请选择</option></select> 
							<select class="add-CK-sel" name="cityId" id="codeCity"><option>请选择</option></select> 
							<select class="add-CK-sel" name="areaId" id="codeArea"><option>请选择</option></select>
							<input type="text" class="add-CK-text" name="address" id="codeAddress"/>
						</dd>
					</dl>
			</div>
			<div class="lightbox-box-bar">
				<c:if test="${! empty buttonsMap['仓库管理-保存仓库'] }">		
					<a onclick="save()" class="lightbox-btn true-btn" href="javascript:void(0);">提 交</a>
				</c:if>
			</div>
			</form>
		</div>
</div>

</body>
	<script type="text/javascript" src="${path }/commons/js/order_js/warehouse_list.js"></script>
</html>