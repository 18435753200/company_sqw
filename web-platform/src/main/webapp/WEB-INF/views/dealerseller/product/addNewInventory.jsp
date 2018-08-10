<%@ page language="java" import="java.util.*" pageEncoding="utf8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>UNICORN-补录库存</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/addNewInventory.css"/>
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>

    <script type="text/javascript">
   
    </script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="center">
	<!--logo开始-->
	<div class="logo3">
		<p class="p1">您的位置： 卖家中心&gt; 商品列表 &gt; <span>补录库存</span></p>
		<p class="p2" id="p2"></p>
	</div>
	<!--logo结束-->

	<!--订单信息开始-->
	<div class="xing">
		<div class="xing1"><p>SKU信息</p></div>
	
		<!--商品清单开始-->
		<div class="xing4">
			<div class="zong">
				
				<div class="z2" style="display: none">
					<p>
						<span class="s2"><i>经销商名称：</i><input type="text" id="checkeddealername" readonly="readonly" productId="${productId }"></span> 
					</p>
				</div>
				<div class="z2">
						<p>
							<span class="s2"><i>仓库名称：</i><input type="text" id="warehouseId" readonly="readonly" warehouseId=""></span> 
						</p>
				</div>
			</div>
			
			<div class="c3" >
				<div class="two">
					<div class="two1">
						<form id="despatch">
						
						</form>
						<div class="blink"></div>
						<div class="two2">
						</div>
						
					</div>
				</div>
			</div>
		
		<div class="bink"></div>
		<div class="xing5"></div>
	</div>
	<!--订单信息结束-->
</div>
</div>


<div class="alert_bu">
  <div class="bg"></div>
	<div class="wrap">
	<div class="pic">
	</div>
	<div class="box1">
		<div id="boxtitle">
			<h2>选择仓库 ( 一次补录只能选择一个仓库，请谨慎选择 )</h2>
		</div>
		<div id="dealernametext" style="display: none;">
			<input type="text" id="getDealerName">
			<button type="button" onclick="getDealerList()">查询</button>
		</div>
	</div>
	<div class="box2">
		<table>
			<thead>
				<tr>
					<th class="t1">单选</th>
					<th class="t2">仓库名称</th>
				</tr>
			</thead>
			<tbody id="dealertable">
				<c:forEach items="${warehouse }" var="warehouse">
					<tr>
						<td class='t1'><input type='radio' name='warehouseid' value="${warehouse.warehouseCode }"></td>
						<td class='t2' title='${warehouse.warehouseName}'><span>${warehouse.warehouseName}</span></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="box3">
		<c:if test="${! empty buttonsMap['货品列表-补录-查看经销商列表'] }">	
			<button type="button" class="bt1" id="warehouseclick">确定</button>
		</c:if>
		<c:if test="${! empty buttonsMap['货品列表-补录-查询库存列表'] }">	
			<button type="button" class="bt1" id="dealerclick" style="display: none;" onclick="loadInventoryPage()">确定</button>
		</c:if>
		<button type="button" class="bt2" id="dealerclose" onclick="closebox()" style="display: none;">取消</button>
	</div>
  </div>
  
</div>
   <!-- 底部 start -->
		<div class="t_footer">
			<h1 class="logo_dl"></h1>
	</div>
	<script src="${path}/commons/js/addnewinventory.js" type="text/javascript"></script>
</body>
</html>