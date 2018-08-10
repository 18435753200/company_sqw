<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<title>发票说明</title>
</head>
<script type="text/javascript"
	src="${staticFile_s}/commons/js/zepto.min.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<link href="${staticFile_s}/commons/css/newbese.css" rel="stylesheet"
	type="text/css">
<link href="${staticFile_s}/commons/css/order.css" rel="stylesheet"
	type="text/css">

<body >

	<div id="invoiceInfo">
		<div class="header">
			<a href="javascript:goBack();" class="goback"></a> 发票说明
		</div>
		<div class="invoice">
			<p>1.发票类型：众聚猫目前仅支持开具增值税普通发票，保税区发货商品暂不支持开具发票；</p>
			<p>2.发票金额：不包含现金券、红包抵扣金额及运费金额；</p>
			<p>3.发票内容：</p>
			<p>1)订单中众聚猫自营的商品，由众聚猫开具发票，发票内容根据购买商品所属分类确定；</p>
			<p>2)订单中非众聚猫自营的商品，由对应的商家开具发票，发票的内容由商家决定。</p>
		</div>

	</div>

</body>
</html>

<script>
;(function(win,doc){
	function change(){
		document.documentElement.style.fontSize=document.documentElement.clientWidth/16+'px';
	}
	
	win.addEventListener('resize',change,false);
	win.addEventListener('DOMContentLoaded',change,false);
})(window,document);
</script>
