<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改秒杀商品</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link href="${path}/commons/css/reset.css" rel="stylesheet" type="text/css">
<link href="${path}/commons/css/promotion.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${path }/commons/css/seckill_add.css" />
<script src="${path}/commons/js/order_js/b2c_seckill_add.js"></script>
<script src="${path}/commons/js/my97/WdatePicker.js"></script>

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
					<p class="c1">修改秒杀商品</p>
				</div>

				<div class="blank10"></div>

				<div class="promotion-wrap">
					<div class="promotion-bd manage-promotion">
						<div class="promotion-title">修改秒杀商品</div>
						<form method="post" id="seckillProduct1" action="${path}/secondKill/updateSeckKillById" enctype="multipart/form-data">
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-name">商品编号：</label>
									<div class="field">
										<input type="hidden" name="id" id="activeName" class="promotion-txt" value="${secKill.id }"> 
										<input type="text" name="pid" id="activeName" class="promotion-txt" value="${secKill.pid }"> 
										<span class="dpl-tip-inline-warning"></span>
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-section">规格编号：</label>
									<div class="field">
										<input type="text" name="skuid" id="activeName" class="promotion-txt" value="${secKill.skuid }"> 
										<span class="dpl-tip-inline-warning"></span>
									</div>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-startTime">开始时间：</label>
									<div class="field">
										<input type="text" onClick="WdatePicker()" name="startDate" id="expiringFrom" class="promotion-txt" value="<fmt:formatDate value="${secKill.startTime }" pattern="yyyy-MM-dd HH:mm:ss" />"/>
										<span class="dpl-tip-inline-warning"></span>
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-endTime">结束时间：</label>
									<div class="field">
										<input type="text" onClick="WdatePicker()" name="endDate" id="expiringTo" class="promotion-txt" value="<fmt:formatDate value="${secKill.endTime }" pattern="yyyy-MM-dd HH:mm:ss" />"/>
										<span class="dpl-tip-inline-warning"></span>
									</div>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-organizersType">活动价格：</label> 
									<input type="text" name="price" id="expiringTo" class="promotion-txt" value="${secKill.price }"/>
									<span class="dpl-tip-inline-warning"></span>
								</div>
								<div class="form-group">
									<label for="promotion-organizers">活动库存：</label> 
									<input type="text" name="stock" id="expiringTo" class="promotion-txt" value="${secKill.stock }"/>
									<span class="dpl-tip-inline-warning"></span>
								</div>
							</div>
							
							<div class="form-group">
								<label for="promotion-name">商品主图：</label>
								<div class="field">
								<img alt="" width="60" height="60" src="${secKill.imageUrl}">
								</div>
							</div>
							
							<div class="form-group">
								<label for="promotion-name">重新上传：</label>
								<div class="field">
				                    <input type="text" readonly="readonly" class="promotion-txt" id="fTaxpayqualicerti"> 
				      				<div class="div1">
										<div class="div2">上传图像</div>
										<input name="imageProductUrl" class="inputstyle" type="file" onchange="document.getElementById('fTaxpayqualicerti').value=this.value">
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="promotion-des">活动描述：</label>
								<textarea name="remark" id="description" class="promotion-textarea" >${secKill.remark }</textarea>
								<span class="dpl-tip-inline-warning"></span>
							</div>
							
							<div class="form-inline">
								<div class="form-group">
									<input type="button" name="button" id="confirmButton" class="promotion-btn promotionManage-btn" onclick="sumit1()" value="确定">
								</div>
								<div class="form-group">
									<input type="button" name="backButton" id="backButton"
										class="promotion-btn promotionManage-btn" value="取消">
								</div>
							</div>
						</form>
					</div>
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