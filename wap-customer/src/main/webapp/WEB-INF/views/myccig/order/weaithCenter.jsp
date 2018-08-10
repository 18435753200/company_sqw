<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link href="${staticFile_s}/commons/css/grzx_core.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/grzx_home.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/grzx_icon.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/person.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/md5.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/wealth/transfer.js"></script>
<title>商户中心</title>
</head>
<body>
	<header class="aui-header-default aui-header-fixed aui-header-bg">
		 <a href="个人中心.html" class="aui-header-item">
			<!--<i class="aui-icon aui-icon-back-white" id="scrollSearchI" style="display:block"></i>-->
			<div id="scrollSearchDiv" style="display: none;">
				<img src="${staticFile_s}/commons/images/head-3.jpg" alt="">
			</div>
		 </a>
		 <div class="aui-header-center aui-header-center-clear">
			 <div style="color: white;font-weight: 700">商户中心
			 </div>
		 </div>
		 <a href="个人中心.html" class="aui-header-item-icon">
			<i class="aui-icon aui-icon-Set"></i>
		 </a>
	</header> 
	
	    <section class="aui-me-content">
	    	<div class="aui-me-content-box">
			<div class="aui-me-content-info"></div>
			<div class="aui-me-content-list">
				<div class="aui-me-content-item">
					<div class="aui-me-content-item-head">
                        <div class="aui-me-content-item-img">
							<img src="${staticFile_s}/commons/images/head-3.jpg" alt="">
						</div>
						<div class="aui-me-content-item-title"><em style="color:#373333;padding-left: 10px;font-weight: 900">众聚猫</em><em style="padding-left: 20px;color: #9A9999;font-size: 12px">ID:750667978</em></div>
					</div>
				</div>
			</div>
		</div>
		<section class="aui-grid-content">
		<div class="aui-grid-row">
			<div class="aui-me-content-order">
				<a href="订单中心.html" class="aui-well ">
					<div class="aui-well-bd">财富中心</div>
					<!--<div class="aui-well-ft">查看全部</div>-->
				</a>
			</div>
				<a href="<%=path %>/wealth/detail_n/8" class="aui-grid-row-item">
					<i class="aui-icon-large aui-icon-large-sm"><img src="${staticFile_s}/commons/images/xj.png" width="34" height="34"></i>
					<p class="aui-grid-row-label">企业现金账户</p>
				</a>
				<a href="<%=path %>/wealth/detail_n/4" class="aui-grid-row-item">
					<i class="aui-icon-large aui-icon-large-sm"><img src="${staticFile_s}/commons/images/yhq.png" width="34" height="34"></i>
					<p class="aui-grid-row-label">企业优惠券</p>
				</a>
				<a href="<%=path %>/wealth/detail_n/7" class="aui-grid-row-item">
					<i class="aui-icon-large aui-icon-large-sm"><img src="${staticFile_s}/commons/images/qydjq.png" width="34" height="34"></i>
					<p class="aui-grid-row-label">企业代金券</p>
				</a>
				<a href="${staticFile_s}/wealth/applyCoupons" class="aui-grid-row-item">
					<i class="aui-icon-large aui-icon-large-sm"><img src="${staticFile_s}/commons/images/sqyhq.png" width="34" height="34"></i>
					<p class="aui-grid-row-label">申请优惠券</p>
				</a>
				<a href="${staticFile_s}/wealth/redTransfer" class="aui-grid-row-item">
					<i class="aui-icon-large aui-icon-large-sm"><img src="${staticFile_s}/commons/images/dzy.png" width="34" height="34"></i>
					<p class="aui-grid-row-label">企业代金券转优惠券</p>
				</a>
				<a href="${staticFile_s}/wealth/turnVoucher" class="aui-grid-row-item">
					<i class="aui-icon-large aui-icon-large-sm"><img src="${staticFile_s}/commons/images/shdzy.png" width="34" height="34"></i>
					<p class="aui-grid-row-label">企业代金券转为个人代金券</p>
				</a>
			</div>	
		
			<div class="aui-dri"></div> <!--//分割代码	-->
			
			<div class="aui-me-content-order">
				<a href="订单中心.html" class="aui-well ">
					<div class="aui-well-bd">商户中心</div>
					<!--<div class="aui-well-ft">查看全部</div>-->
				</a>
			</div>
			<div class="aui-grid-row">
				<a href="${staticFile_s}/wealth/supplierUsers" class="aui-grid-row-item" style="width: 33%">
					<i class="aui-icon-large aui-icon-large-sm"><img src="${staticFile_s}/commons/images/shyh.png" width="34" height="34"></i>
					<p class="aui-grid-row-label">商户用户</p>
				</a>
				<a href="${staticFile_s}/Qr/qrCode?rcodeType=2" class="aui-grid-row-item" style="width: 33%">
					<i class="aui-icon-large aui-icon-large-sm"><img src="${staticFile_s}/commons/images/ewm.png" width="34" height="34"></i>
					<p class="aui-grid-row-label">商户二维码</p>
				</a>
				<a href="#" class="aui-grid-row-item" style="width: 33%">
					<i class="aui-icon-large aui-icon-large-sm"><img src="${staticFile_s}/commons/images/shxx.png" width="34" height="34"></i>
					<p class="aui-grid-row-label">商户信息</p>
				</a>
				
			</div>
	<div class="aui-dri"></div> <!--//分割代码	-->
			
		
		</section>
	</section>

 <!-- <div class="footer-m"> 
   <ul class="footer"> 
    <li><a href="../商城主页/商城主页.html" class="nav1"></a></li> 
    <li><a href="../分类/分类.html" class="nav2"></a> </li> 
    <li><a href="../N+泰华/N+泰华.html" class="nav3"></a> </li> 
    <li><a href="../购物车/购物车.html" class="nav4"></a></li> 
    <li class="set"><a href="个人中心 - 副本.html" class="nav5"></a> </li> 
   </ul> 
  </div>  -->
	<script type="text/javascript">
        $(function () {
            //绑定滚动条事件
            //绑定滚动条事件
            $(window).bind("scroll", function () {
                var sTop = $(window).scrollTop();
                var sTop = parseInt(sTop);
                if (sTop >= 44) {
                    if (!$("#scrollSearchDiv").is(":visible")) {
                        try {
                            $("#scrollSearchDiv").slideDown();
                        } catch (e) {
                            $("#scrollSearchDiv").show();
                        }
                    }
                }
                else {
                    if ($("#scrollSearchDiv").is(":visible")) {
                        try {
                            $("#scrollSearchDiv").slideUp();
                        } catch (e) {
                            $("#scrollSearchDiv").hide();
                        }
                    }
                }
            });
        })
	</script>
</body>
</html>