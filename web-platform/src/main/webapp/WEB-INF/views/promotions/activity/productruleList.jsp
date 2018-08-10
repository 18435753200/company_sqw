<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建平台活动规则-促销活动</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link href="${path}/commons/css/reset.css" rel="stylesheet"	type="text/css">
<link href="${path}/commons/css/promotion.css" rel="stylesheet"	type="text/css">
<link rel="stylesheet" type="text/css"	href="${path }/commons/css/coupon.css" />
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/productrule_list_fn.js"></script>
</head>
<body>
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->

	<div class="center">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/promotions/leftPage.jsp"%>
		</div>

		<!-- 左边 end -->
		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>促销管理&nbsp;&gt;&nbsp;</p>
					<p>活动管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">商品活动规则列表</p>
				</div>

				<div class="blank10"></div>

				<div class="promotion-wrap">
					<div class="promotion-hd">
					<input type="hidden" name="activeId" id="activeId" value="${bean.activeId }">
						<div class="promotion-title">商品活动规则管理</div>
						<div class="promotionRule-hd">
							<dl>
								<dt>活动名称：</dt>
								<dd>${bean.activeName}</dd>
							</dl>
							
							
							<!-- <dl>
						<dt>规则组ID：</dt>
						<dd>001</dd>
					</dl> -->
					<c:if test="${bean.status == 0}">
					<c:if test="${!empty buttonsMap['商品活动列表-创建商品活动规则']}">
							<input type="button" name="button" id="platformRuleButton" onClick="createPlatform()"
								class="promotion-btn large" value="创建B2B商品活动规则">
								
							<input type="button" name="button" id="PricedPromotionButton" onClick="createPricedPromotion()"
								class="promotion-btn large" value="创建B2C商品活动规则">
					</c:if>
					</c:if>
							<!-- <input
						type="button" name="button" id="button1"
						class="promotion-btn large" value="创建商品规则"> <input
						type="button" name="button" id="button2"
						class="promotion-btn large" value="创建领券规则"> -->
						</div>
					</div>
					<div class="promotion-bd"></div>
				</div>
			</div>
		</div>
	</div>



	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>

	<script type="text/javascript">
		
	</script>
	
	<!-- 弹出的查看框 -->
<div class="lightbox" id="goout-box">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box"></div>
		<div class="lightbox-box-hd">
			<h2>商品列表如下：</h2>
		</div>
		<div class="lightbox-box-bd">
			<table class="act-table t-goods" data-spm="9">
				
			</table>
		</div>
	</div>
</div>

<div class="lightbox" id="goout-box-brand">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box" id="brand"></div>
		<div class="lightbox-box-hd">
			<h2>品牌列表如下：</h2>
		</div>
		<div id="categoryconent" class="lightbox-box-bd">
		</div>
	</div>
</div>
<div class="lightbox" id="goout-box-sort">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box" id="sort"></div>
		<div class="lightbox-box-hd">
			<h2>品类列表如下：</h2>
		</div>
		<div id="sortconent" class="lightbox-box-bd">
		</div>
	</div>
</div>
<div class="lightbox" id="goout-box-goods">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box" id="goods"></div>
		<div class="lightbox-box-hd">
			<h2>商品列表如下：</h2>
		</div>
		<div id="goodsconent" class="lightbox-box-bd">
		</div>
	</div>
</div>
<div class="lightbox" id="goout-box-skus">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box" id="skus"></div>
		<div class="lightbox-box-hd">
			<h2>单品列表如下：</h2>
		</div>
		<div id="skusconent" class="lightbox-box-bd">
		</div>
	</div>
</div>
<!-- 页面点击查看 规则条件-->
<div class="lightbox" id="goout-box-condition">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box"></div>
		<div class="lightbox-box-hd">
			<h2>规则条件内容如下：</h2>
		</div>
		<div id="ruleCondition" class="lightbox-box-bd">
		</div>
	</div>
</div>

<!-- 页面点击查看 规则文本-->
<div class="lightbox" id="goout-box-content">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box"></div>
		<div class="lightbox-box-hd">
			<h2>规则内容如下：</h2>
		</div>
		<div id="ruleContent" class="lightbox-box-bd">
		</div>
	</div>
</div>
</body>
</html>