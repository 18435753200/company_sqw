<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="UTF-8">
<title>UNICORN-优惠券列表</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/coupon_record_list_fn.js"></script>
<link rel="stylesheet" type="text/css" href="${path }/commons/css/coupon.css" />
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
		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>促销管理&nbsp;&gt;&nbsp;</p>
					<p>优惠券管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">优惠券发放记录</p>
				</div>
			</div>
			<div class="blank10"></div>
			<!--  -->
			<div class="coupon-wrap">
				<div class="coupon-hd">
					<div class="coupon-filter">
						<div class="form-group">
							<label for="coupon-name">用户名：(B2C用户请输入手机号)</label> <input type="text"
								name="username" id="username" class="coupon-txt w200 large">
						</div>
						<div class="form-group">
							<label for="coupon-type">券状态：</label> <select name="status"
								id="status" class="coupon-sel large">
								<option selected="" value="">全部</option>
								<!-- 0. 新建  1.已领用 2.未领用 3.已使用 4.已到期 5.已暂停 6.已作废 -->
								<option value="0">未使用</option>
								<option value="1">已使用</option>
								<!-- <option value="2">已使用</option> -->
								<!-- <option value="3">已使用</option>
								<option value="4">已到期</option>
								<option value="5">已暂停</option>
								<option value="6">已作废</option> -->
							</select>
						</div>
						<!-- <div>
							<label for="coupon-name">面值：</label> <input type="text"
								name="amount" id="amount" class="coupon-txt large"><span value=""></span>
						</div> -->
						<div class="form-group">
							<label for="coupon-value">面值：</label>
							<input type="text" name="couponacount" id="couponacount" class="coupon-txt w100 large">
						</div>
						<div class="form-group">
							<label for="coupon-bTime">开始时间：</label>
							<input type="text" name="starttime" id="starttime" onClick="WdatePicker()" class="coupon-txt w150 large">
						</div>
						<div class="form-group">
							<label for="coupon-eTime">结束时间：</label>
							<input type="text" name="endtime" id="endtime" onClick="WdatePicker({minDate:'#F{$dp.$D(\'starttime\')}'})" class="coupon-txt w150 large">
						</div>
						<div class="form-group">
							<label for="coupon-value">用户类型：</label>
							<select id="userType" class="coupon-sel large" name="userType">
								<option value="0" selected="">全部</option>
								<option value="1">B2B</option>
								<option value="2">B2C</option>
							</select>
						</div>
						<input type="hidden" name="coupontype" value="1" id="coupontype"/>
						<div class="form-group">
							<input type="button" name="button" id="button" onclick="clickSubmit()"
								class="coupon-btn large" value="查询">
							<input type="button" name="button2" id="button2" onclick="downCheckListExcel()"
								class="coupon-btn large" value="导出表格">
						</div>
					</div>
				</div>
				<div class="coupon-bd">
					
				</div>
			</div>
			<!--  -->
		</div>
	</div>
	
	
	<div class="logw" style="display: none;">

		<div class="bg"></div>
		<div class="log-c">

			<div class="log-c1">
				<h4>使用记录</h4><img src="${path }/commons/images/close.jpg" class="w_close">
			</div> 

			<div class="log_c2">
				<p><span>使用时间</span><span>使用金额</span></p>
				<table id="search">
					
				</table>
			</div>

		</div>

	</div>
	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>

	<script type="text/javascript">
    
		$(function(){
			$(".w_close").click(function(){
				$(".logw").hide();
			})
			$(".bt2").click(function(){
				$(".logw").hide();
			})
		});

    </script>


</body>
</html>