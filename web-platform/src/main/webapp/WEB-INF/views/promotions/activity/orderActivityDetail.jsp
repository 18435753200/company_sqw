<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<title>促销活动管理_促销活动</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link href="${path}/commons/css/reset.css" rel="stylesheet"	type="text/css">
	<style type="text/css">
	.center{width: 1000px; height: 100%; margin: 0 auto; min-height: 100%;}
	.right{float: right; width: 840px;}
	
	.xia{width: 840px; height: auto; background: #f2f2f2; overflow: hidden; padding-top: 10px; margin-top: 10px; }
	.xia .p1{display: block; float: left;  margin:5px 0; clear: both;}
	.xia .p1 span{min-width: 80px; height: 30px; line-height: 30px; text-align: center; display: block; float: left; font-size: 13px;  color: #666666;}
	.xia .p1 input{width: 163px; height: 25px; line-height: 25px; float:left; border:1px solid #c8c8c8;}
	.xia .p2{float: right; margin:10px 5px 10px 0;  display: block; }
	.xia .p2 button{ min-width: 65px; height: 23px; border:1px solid #c8c8c8; border-radius: 5px; background: url(../images/img_btn.png) repeat-x;font-size: 14px; cursor:pointer; margin:0 5px;}
	
	.pu_wrap{width: 840px; margin: 30px 0; color: #333;}
	.pu_wrap .pu_bd {clear: both;}
	.pu_wrap .pu_bd table{border-collapse: collapse; border-spacing: 0; width: 100%; margin:10px 0;}
	.pu_wrap .pu_bd .order_hd{width: 100%; height: 30px; line-height: 18px; background: #f2f2f2; border-left: 1px solid #e2e9ee; border-right: 1px solid #e2e9ee; border-top: 1px solid #e2e9ee; font-size: 13px; color: #4b4b4b; font-weight: bold;}
	.pu_wrap .pu_bd td{height:30px; line-height:18px; border:1px solid #e2e9ee; text-align: center; word-wrap:break-word; word-break:normal; word-break:break-all;}
	
	</style>
	<script type="text/javascript">
		function viewDetail(){
			var orderId = $("#orderId").val();
			console.log(orderId);
			$.ajax({
	            type : 'post',
	            url : "../view/detail",
	            data : "orderId="+orderId,
	            dataType : "json",
	            success : function(data) {
	            	$("#detail").html("");
	            	var $li = ("<tr class='order_hd'>" + 
									"<th width='210px;'>商品名称</th>" + 
									"<th width='210px;'>购买数量</th>" +
									"<th width='210px;'>商品原价</th>" +
									"<th width='210px;'>活动名称</th>" +
								"</tr>");
	            	$("#detail").append($li);
	            	var orderActivitysDTOs = data[0].orderActivitysDTOs;
	            	var couponStocks = data[0].couponStocks;
	            	console.log("orderActivitysDTOs:" + orderActivitysDTOs);
	            	console.log("couponStocks:" + couponStocks);
	            	for(var obj in orderActivitysDTOs){
	            		var pName = orderActivitysDTOs[obj].mainrulename;
	            		var skuName = orderActivitysDTOs[obj].skuName
	            		var qty = orderActivitysDTOs[obj].qty;
	            		var price = orderActivitysDTOs[obj].price;
	            		var ruleName = orderActivitysDTOs[obj].ruleName;
		            	var $li = $("<tr class='order_hd'>" + 
										"<th width='210px;'>" + pName + "  " + skuName + "</th>" + 
										"<th width='210px;'>" + qty + "</th>" +
										"<th width='210px;'>" + price + "</th>" +
										"<th width='210px;'>" + ruleName + "</th>" +
									"</tr>");
	            		$("#detail").append($li);
	            	}
	            	$("#coupon").html("");
	            	for(var obj in couponStocks){
	            		var couponacount = couponStocks[obj].couponacount;
	            		var orderpiecelimit = couponStocks[obj].orderpiecelimit
		            	var $li = $("<p class='p1'>" +
										"<span>使用了：</span><input type='text' value='" + couponacount + "' readonly='readonly'>" +
										"<span>元劵</span><span>满：</span>" +
										"<input type='text' value='" + orderpiecelimit + "' readonly='readonly'><span>可用</span>" +
									"</p>");
	            		$("#coupon").append($li);
	            	}
	            }
			});
		}
		
	</script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<!-- 导航 end -->
	<div class="blank10"></div>
	<div class="center">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/promotions/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->
		<div class="right">
			<div class="c21">
				<div class="title">
					<p>促销管理&nbsp;&gt;&nbsp; </p>
					<p class="c1">订单参与活动查询</p>
				</div>
		    </div>

			<div class="xia">
				<p class="p1">
					<span>订单号：</span>
					<input type="text" id="orderId">
				</p>
				<p class="p2">
					<button type="button" id="findDetail" onclick="viewDetail()">查询</button>
				</p>
			</div>

			<div class="pu_wrap">
				
				<div class="pu_bd">
					<table>
						<tbody id="detail">
							<tr class="order_hd">
								<th width="210px;">商品名称</th>
								<th width="210px;">购买数量</th>
								<th width="210px;">商品原价</th>
								<th width="210px;">活动名称</th>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="xia" id="coupon">
				<p class="p1">
					<span>使用了：</span>
					<input type="text">
					<span>元劵</span>
					<span>满：</span>
					<input type="text">
					<span>可用</span>
				</p>
			</div>

		</div>
	</div>
	
	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>

	<script type="text/javascript">
		
	</script>
</body>
</html>