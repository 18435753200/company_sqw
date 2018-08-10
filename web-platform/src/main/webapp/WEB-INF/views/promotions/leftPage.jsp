<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%  
    String basePath = request.getContextPath();
	request.setAttribute("path",basePath);
	request.setAttribute("pathUrl", "/coupon/getCouponPage");
%>
<!-- 左边 start -->
<div class="leftly">
<script type="text/javascript">
	$(document).ready(function() {
		var url = '${url}';
		//alert(url);
		if (url.indexOf("equipment") != -1) {
			$("#equipment").attr("class", "p2 c_cut2");
		}
		if (url.indexOf("coupon/couponEdit") != -1) {
			$("#promotion_couponEdit").attr("class", "p2 c_cut2");
		}
		if (url.indexOf("coupon/couponRecordList") != -1) {
			$("#promotion_couponRecordList").attr("class", "p2 c_cut2");
		}
		if (url.indexOf("coupon/couponList") != -1||url.indexOf("coupon/couponView") != -1
				||url.indexOf("coupon/couponRuleList") != -1||url.indexOf("/coupon/sendCouponPage") != -1
				||url.indexOf("coupon/couponRuleEdit") != -1) {
			$("#promotion_couponList").attr("class", "p2 c_cut2");
		}
		if (url.indexOf("activity/activityList") != -1||url.indexOf("activity/activityView") != -1
				||url.indexOf("activity/ruleList") != -1||url.indexOf("activity/giftList") != -1
				||url.indexOf("activity/platformRuleEdit") != -1) {
			$("#promotion_activityList").attr("class", "p2 c_cut2");
		}
		if (url.indexOf("activity/activityEdit") != -1) {
			$("#promotion_activityEdit").attr("class", "p2 c_cut2");
		}
		if (url.indexOf("activity/activeproductadd") != -1) {
			$("#promotion_activeproductadd").attr("class", "p2 c_cut2");
		}
		if (url.indexOf("activity/activeproductlist") != -1||url.indexOf("activity/productruleList") != -1
				||url.indexOf("activity/productRuleEdit") != -1) {
			$("#promotion_activeproductlist").attr("class", "p2 c_cut2");
		}
	});
</script>
<!-- 左边 start -->
<div class="left f_l">
	<p class="blank10"></p>
	<div class="title">
		<p class="f_l">
			<img src="${path}/commons/images/img_title2.png" alt="">
		</p>
		<p class="f_l p1">促销管理</p>
		<p class="clear"></p>
	</div>
	<p class="blank5"></p>
	<div class="list_box">
		
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t1.png">优惠券管理
					</p>
				</h2>
				<div class="p_b">
					
						<p id="promotion_couponList" class="p2 ">
							<a href="${path}/coupon/getCouponPage">优惠券列表</a>
						</p>
					
				</div>
				<div class="p_b">
					
						<p id="promotion_couponEdit" class="p2 ">
							<a href="${path}/coupon/toCreateCoupon">创建优惠券</a>
						</p>
					
				</div>
				<div class="p_b">
					
						<p id="promotion_couponRecordList" class="p2 ">
							<a href="${path}/coupon/getCouponRecord">已发放券查询</a>
						</p>
					
				</div>
			</div>
		

		
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t1.png">活动管理
					</p>
				</h2>
				<div class="p_b">
					
						<p id="promotion_activityList" class="p2 ">
							<a href="${path}/activity/toActivityList">活动列表</a>
						</p>
					
				</div>
				<div class="p_b">
					
						<p id="promotion_activityEdit" class="p2 ">
							<a href="${path}/activity/toActivityEdit">创建活动</a>
						</p>
					
				</div>
				<div class="p_b">
					
						<p id="promotion_activeproductadd" class="p2 ">
							<a href="${path}/activeproduct/add">创建商品活动</a>
						</p>
					
				</div>
				<div class="p_b">
					
						<p id="promotion_activeproductlist" class="p2 ">
							<a href="${path}/activitis/toList">商品活动列表</a>
						</p>
			
				</div>
			<%-- 	<div class="p_b">
				
						<p id="promotion_activeproductlistnew" class="p2 ">
							<a href="${path}${ buttonsMap['新商品活动列表']}">新商品活动列表</a>
						</p>
			
				</div> --%>
				<%-- <div class="p_b">
					<c:if test="${ !empty buttonsMap['新商品活动列表']}">
						<p id="promotion_activeproductlistnew" class="p2 ">
							<a href="${path}/view/toDetail">订单参加活动明细</a>
						</p>
					</c:if>
				</div> --%>
			</div>
		
	
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t1.png">APP活动管理
					</p>
				</h2>
				<div class="p_b">
					
						<p id="promotion_activityEdit" class="p2 ">
							<a href="${path}/appActivity/createAPPActivity">创建APP活动</a>
						</p>
					
				</div>
				<div class="p_b">
					
						<p id="promotion_activeproductadd" class="p2 ">
							<a href="${path}/appActivity/relevanceAP">活动关联商品</a>
						</p>
					
				</div>
				<div class="p_b">
					
						<p id="promotion_activeproductadd" class="p2 ">
							<a href="${path}/appActivity/appToList">APP活动列表页</a>
						</p>
					
				</div>
				<div class="p_b">
					
						<p id="promotion_activeproductadd" class="p2 ">
							<a href="${path}/secondKill/goAddecondKillProduct">秒杀活动</a>
						</p>
					
				</div>
			</div>
	
	</div>
</div>
</div>
