<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>促销活动查询</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link href="${path}/commons/css/reset.css" rel="stylesheet"	type="text/css">
<link href="${path}/commons/css/promotion.css" rel="stylesheet"	type="text/css">
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/activitis/activitylist.js"></script>

<link rel="stylesheet" type="text/css" href="${path }/commons/css/coupon.css" />
<link rel="stylesheet" type="text/css" href="${path }/commons/css/prom.css" />

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
		<div class="center">
			<div class="right">
				<div class="c21">
					<div class="title">
						<p>促销管理&nbsp;&gt;&nbsp; </p>
						<p>活动管理&nbsp;&gt;&nbsp; </p>
						<p class="c1">促销活动查询</p>
					</div>
		        </div>
				<div class="pu_hd"><h3>查询条件</h3></div>
		        <div class="xia">
		        	<p class="p1">
		        		<span>活动名称:</span>
		        		<input type="text" class="w300" id="activityName" name="activityName">
		        	</p>
		        	<p class="p1">
		        		<span>创建时间:</span>
		        		<input type="text" onClick="WdatePicker()" name="expiringFrom"
											id="expiringFrom" class="promotion-txt"
									pattern="yyyy-MM-dd HH:mm:ss" />
						<i>至</i>
						<input type="text" onClick="WdatePicker()" name="expiringTo"
											id="expiringTo" class="promotion-txt"
									pattern="yyyy-MM-dd HH:mm:ss" />
		        		<span>创建人员:</span>
		        		<input type="text" id="createBy">
		        	</p>
		        	<p class="p2"><button type="button" onclick="findActivityList()">查询</button></p>
		        </div>
		        <div class="pu_wrap">
		        	<div class="pu_hd"><h3>活动列表</h3>
						<div class="btn">
			        		<a onclick="create()" style="cursor:pointer;">创建活动</a>	
			        	</div>
					</div>
					<div class="promotion-bd">
						
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

	<div class="lightbox" id="goout-box">
		<div class="lightbox-overlay"></div>
		<div class="lightbox-box">
			<div class="close-box"></div>
			<div class="lightbox-box-hd">
				<h2>活动详情如下：</h2>
			</div>
			<div class="lightbox-box-bd">
				<!-- 显示活动基本信息 -->
				<div class="pu_wrap">
		        	<div class="pu_hd"><h3>访问方式</h3></div>
					<div class="pu_bd">
						<table id="accessModes">
							<tbody>
								<tr class="order_hd">
									<th width="100px;">访问方式名称</th>
									<th width="100px;">是否参加活动</th>
								</tr>
								<tr>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				
				<div class="pu_wrap">
		        	<div class="pu_hd"><h3>会员等级</h3></div>
					<div class="order-it" id="userLevel">
						
					</div>
				</div>
				
				<div class="pu_wrap">
		        	<div class="pu_hd"><h3>渠道</h3></div>
					<div class="pu_bd">
						<table id="channels">
							<tbody>
								
							</tbody>
						</table>
					</div>
				</div>
				
				<div class="pu_wrap">
	        		<div class="pu_hd"><h3>活动商品</h3></div>

					<div class="pu_bd">
						<table id="conditionDetail">
							<tbody>
								<tr class="order_hd" id="tr1">
									<th width="300px;">活动品类/品牌/单品类型</th>
									<th width="300px;">活动品类/品牌/单品ID</th>
									<th width="270px;">是否参与活动</th>
									<th width="270px;">商品发货途径</th>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
								</tr>
						</tbody>
					</table>
					</div>
				</div>

			
				<div class="pu_wrap">
		        	<div class="pu_hd"><h3>订单条件</h3></div>
					<div class="order-it" id="orderCondition">
						<span>订单金额大于等于<input type="text" class="te">元</span>
					</div>
				</div>
			
				<div class="pu_wrap">
		        	<div class="pu_hd"><h3>执行条件</h3></div>
					<div class="order-it" id="exec">
						<span>订单金额大于等于<input type="text" class="te">元</span>
					</div>
					<div class="pu_bd">
						<table id="exec1">
							<tbody>
								<tr class="order_hd">
									<th width="270px;">赠品名称</th>
									<th width="300px;">赠品数量</th>
									<th width="300px;">赠送类型(按金额/按数量)</th>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
	
					<div class="pu_bd">
						<table id="exec2">
							<tbody>
							<tr class="order_hd">
								<th width="150px;">起点金额/数量</th>
								<th width="150px;">结点金额/数量</th>
								<th width="150px;">赠品名称</th>
								<th width="150px;">赠品数量</th>
								<th width="240px;">赠送类型(按金额/按数量)</th>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
					</div>
	
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		
	</script>
</body>
</html>