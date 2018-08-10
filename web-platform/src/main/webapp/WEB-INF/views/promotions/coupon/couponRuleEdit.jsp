<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>UNICORN-新增优惠券规则</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${path }/commons/css/coupon.css" />
<link href="${path}/commons/css/act.css" rel="stylesheet" type="text/css">	
<link href="${path}/commons/css/reset.css" rel="stylesheet" type="text/css">
<script src="${path}/commons/js/couponRule_edit_fn.js"></script>
<script src="${path}/commons/js/category_promotion.js"></script>
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

		<form method="post" id="couponRuleAction"
			enctype="multipart/form-data">
			<!-- 消息 -->
			<input type="hidden" name="errorMsg" id="errorMsg"
				value="${fn:escapeXml(errorMsg)}" />
			<div class="c2">
				<div class="c21">
					<div class="title">
						<p>促销管理&nbsp;&gt;&nbsp;</p>
						<p>优惠券管理&nbsp;&gt;&nbsp;</p>
						<p class="c1">编辑优惠券规则</p>
					</div>
				</div>
				<div class="blank10"></div>
				<div class="coupon-wrap">
					<div class="coupon-bd manage-coupon">
						<div class="form-inline">
							<div class="form-group">
								<label for="coupon-name">优惠券使用规则名称：</label>
								<div class="field">
									<input type="text" name="couponrulename" id="couponrulename"
										class="coupon-txt"> <span
										class="dpl-tip-inline-warning"></span>
								</div>
							</div>
							<div class="form-group">
								<label for="coupon-name">优惠券名称：</label>
								<div class="field">
								<input type="hidden" name="couponid" id="couponid"
										value="${fn:escapeXml(coupon.couponid)}">
								<input type="hidden" name="couponType" id="couponType"
										value="${fn:escapeXml(coupon.coupontype)}">
									<input type="text" name="couponname" id="couponname"
										readonly="readonly"
										value="${fn:escapeXml(coupon.couponname) }" class="coupon-txt">
								</div>
							</div>
						</div>
						<div class="form-inline">
							<div class="form-group">
								<label for="coupon-type">优惠券类型：</label>
								<div class="field">
									<select name="coupontype" id="coupontype" class="coupon-sel">
										<c:if test="${coupon.coupontype==1}">
											<option selected="selected" value="1">金券</option>
										</c:if>
										<c:if test="${coupon.coupontype==2}">
											<option selected="selected" value="2">现金券</option>
										</c:if>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="coupon-limit">一级限制条件：</label>
								<div class="field">
									<select name="mainruletype" id="mainruletype"
										class="coupon-sel">
										<option selected="selected" value="1">全场</option>
										<c:if test="${coupon.coupontype==1}">
											<option value="2">品类</option>
											<option value="3">品牌</option>
											<option value="4">单品</option>
										</c:if>
									</select>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label for="coupon-name">优惠券金额：</label>
							<div class="field">
								<input type="text" name="couponacount" id="couponacount"
									readonly="readonly"
									value="${fn:escapeXml(coupon.couponacount)}" class="coupon-txt">
								<span>￥</span>
							</div>
						</div>
						<div class="form-group">
							<label for="coupon-meet">消费满足金额：</label>
							<div class="field">
							<c:if test="${coupon.coupontype==2}">
								<input type="text" name="meetpiece" id="meetpiece"
									class="coupon-txt" value="0" disabled="disabled">
									<span>￥</span> <span
									class="dpl-tip-inline-warning"></span>
							</c:if>
							<c:if test="${coupon.coupontype!=2}">
								<input type="text" name="meetpiece" id="meetpiece"
									class="coupon-txt">
									<span>￥</span> <span
									class="dpl-tip-inline-warning"></span>
							</c:if>
							</div>
						</div>
						<!-- b2b优惠券增加品类，品牌，单品限制     修改部分开始 -->
						<div class="form-group" id="selectoption">
				            <label>已选条件：</label>
				            <div class="field">
				               	<div class="selected" id="selected-brands">
									<div class="selected-name">品牌<em>&gt;</em></div>
									<div class="selected-list">
										<ul>
											<!-- brands -->
										</ul>
									</div>
								</div>
								<div class="selected" id="selected-sort">
									<div class="selected-name">分类<em>&gt;</em></div>
									<div class="selected-list">
										<ul>
											<!-- sort -->
										</ul>
									</div>
								</div>
								<div class="selected" id ="selected-goods">
									<div class="selected-name">单品<em>&gt;</em><span id="skuNum" style="color: red">已选：0条</span></div>
									<div class="selected-list">
										<ul>
											<!-- goods -->
										</ul>
									</div>
								</div>
				            </div>
				        </div>
				        <!-- 品类 -->
				        <div class="hide J-why" id="J-why-sort">
				            <div class="act-sort-crumb" id="selectCategory">
				            	<dt style="float:left">您当前选择类目：</dt>
				                <input type="button" name="addCategory" id="addCategory" class="addto" value="添加">
				            </div>
				            <div class="form-box">
				                <div class="act-sort-content">
				                	<!-- 一级分类 -->
				                    <div class="sort-item">
				                        <ul>
				                            <c:forEach items="${topCategoryList }" var="topList">
				                            	<li id="${topList.catePubId }">${topList.pubNameCn}</li>
				                            </c:forEach>
				                        </ul>
				                    </div>
				                    <!-- 二级分类 -->
				                    <div class="sort-item original">
				                        <ul style="display: none;">
				                            
				                        </ul>
				                    </div>
				                    <!-- 三级分类 -->
				                    <div class="sort-item original">
				                        <ul style="display: none;">
				                            
				                        </ul>
				                    </div>
				                    <!-- 四级分类 -->
				                    <div class="sort-item original">
				                        <ul style="display: none;">
				                            
				                        </ul>
				                    </div>
				                </div>
				            </div>
				        </div>
				        <!-- 品牌 -->
				        <div class="form-box hide J-why" id="J-why-brands">
				            <div class="act-brands-content">
				                <div class="act-brands-list">
				                    <ul class="act-list">
				                   		
				                    </ul>
				                </div>
				                <div class="act-brands-sub">
				                    <input type="button" name="addbrand" id="addbrand" class="addto" value="添加">
				                </div>
				            </div>
				        </div>
				        <!-- 商品 -->
				        <div class="form-box hide J-why" id="J-why-goods">
				            <div class="act-goods-hd" style="height: 60px;">
				                                         单品名称:<input type="text" name="skuName" id="skuName" class="act-txt">
							           单品 ID：<input type="text" name="productIdStr" id="productIdStr" class="act-txt" style="width:150px;">
			                                                   单品 SkuId：<input type="text" name="skuIdStr" id="skuIdStr" class="act-txt" style="width:150px;">
			                    <input type="button" class="act-btn" value="查询" id="findbyskuId" name="findbypid"><br>
   								全选：<input type="checkbox" id="allCheck">
                                                                                反选：<input type="checkbox" id="noCheck">
				                <div class="act-goods-sub">
				                    <input type="button" name="addProduct" id="addProduct" class="addto" value="添加">
				                </div>
				            </div>
				            
				            <div class="act-goods-content">
				                <div class="act-goods-list">
				                    <ul class="act-list">
				                    
				                    </ul>
				                </div>
				                
				            </div>
				            <div id="pages" align="right" class="page-goods">
				            	<input id="fPage" name="firstPage" value="首页" type="button">
				               	<input id="pPage" name="previousPage" value="上一页" type="button">
				               	<label id="page">1</label>
				               	<input id="nPage" name="nextPage" value="下一页" type="button">
				               	<input id="totalPage" name="totalPage" value="99" type="text" style="display: none">
				               	共<label id="total"></label>页
				            </div>
				        </div>
						<!-- 修改部分结尾 -->
						<div class="form-group">
							<input type="button" name="createRule" id="createRule"
								class="coupon-btn couponManage-btn" value="创建">
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>

	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	<!-- 底部 end -->
</body>
</html>