<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@include file="/WEB-INF/views/include/base.jsp"%>
<link href="${path}/commons/css/reset.css" rel="stylesheet"	type="text/css">
<link href="${path}/commons/css/act.css" rel="stylesheet" type="text/css">	
<link href="${path}/commons/css/promotion.css" rel="stylesheet"	type="text/css">
<link rel="stylesheet" type="text/css"	href="${path }/commons/css/coupon.css" />
<link rel="stylesheet" type="text/css"	href="${path }/commons/css/prom.css" />
<title>活动规则</title>
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/activitis/rulesEditPage.js"></script>

</head>
<body>
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->

	<div class="center">
		<input type="hidden"  id="activeId"  name="activeId"  value="${activeId }">
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
					<p class="c1">单品活动规则列表</p>
					
						<div class="form-inline">
							<div class="form-group">
								<div class="right">
								<form method="post" id="rulesAction"  enctype="multipart/form-data">
									<div class="xia"  >
									<div class="form-inline p1">
										<span>创建人员:</span>
											<input type="text" id="categoryName" name="categoryName" value=${loginName } readonly="readonly"> <span class="font-color"></span>
											<span>创建部门:</span> <input type="text" id="createBy" name="createBy"><span class="font-color"></span>
									</div>
										<div class="form-inline p1">
											<span>活动名称:</span> <input type="text" id="ruleName" name="ruleName" onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\ \.]/g,'')"><span class="font-color"></span> <span>活动兼容性:</span>
											<select id="dispAttrVal" name="dispAttrVal" style="cursor:pointer;">
												<option value="2" selected="selected">排它</option>
												<!-- <option value="1">叠加</option>
												<option value="3">排己</option> -->
												</select>
												<span class="font-color"></span>
											</div>
											
											<div class="form-inline p1">
												<span>开始时间:</span> 
												<input type="text" name="startdate"  id="startdate"  class="act-txt" onClick="WdatePicker()" style="cursor:pointer;"><span class="font-color"></span>
												<span>结束时间</span><input type="text" onClick="WdatePicker()" name="enddate" id="enddate" style="cursor:pointer;"><span class="font-color"></span>
										   </div>
										   
											<div class="form-inline p1">
												<span>用户类型:</span> 
												<select id="userGrade1" name="userGrade1" style="cursor:pointer;">
													<option value="-1" selected="selected">所有用户</option>
													<option value="8">欢购平台注册用户</option>
													<option value="884">翼支付注册用户</option>
													<option value="903">中行用户</option>
												</select><span class="font-color"></span>
												<span>限制参与次数</span>
												<input type="text"  name="limittimes1" id="limittimes1"><span class="font-color"></span>
										    </div>
										    	
											<div class="form-inline p1">
											 <span>活动类型:</span> 
											 <select onchange="activityModel(this.value)" id="ruleTerm" name="ruleTerm" style="cursor:pointer;">
												    <option value="0">--请选择--</option>
													<option value="1">满减</option>
													<option value="2">满返</option>
													<option value="3">限时直降</option>
													<option value="4">满赠</option>
													<!-- <option value="5">买赠</option> -->
											  </select><span class="font-color"></span>
											</div>
											
											<div class="form-inline p1">
												<span>支付平台:</span> 
												<select  id="cpsType" name="cpsType" style="cursor:pointer;">
												    <option value="-1">--所有支付平台--</option>
													<option value="1">中行信用卡</option>
												</select><span class="font-color"></span>
											</div>
											
											<div class="form-inline p1">
												<span>商品来源平台类型:</span> 
												<select id="platform" name="platform">
													<option value="0" selected="selected">众聚商城平台</option>
													<option value="1">北京天然气5S店</option>
												</select><span class="font-color"></span>
											</div>
											<div class="form-inline p1" id="compatibleID" style="display: none">
												<span>配送类型:</span> 
												<select id="compatible" name="compatible">
													<option value="1" selected="selected">自提</option>
													<option value="2">货到付款</option>
												</select><span class="font-color"></span>
											</div>
									</div>
								</form>
								
								
								
								<div class="item">
										<div class="ite">选择订单条件 <label  onclick="spread(10)" style="cursor:pointer;">展开<img src="${path}/commons/images/trg.png"></label></div>
										<div class="aw" id="div10" style="display: none;" >
										<div class="order-it1" id="ordercondition1"   style="display: none;">
												<p>
												<input type="radio" id="moneyCheckd1" name="fullcut" checked="checked" value="1">活动总金额大于等于<input type="text" id="activeMoneyOne"  name="activeMoneyOne" class="brr" >元<span class="font-color"></span>
												</p>
												<br>
												<p>
												<input type="radio" name="fullcut" value="2">活动总金额大于等于<input
													type="text"  id="activeMoney" name="activeMoney" class="brr" >元   该选项为等比阶梯<span class="font-color"></span><!-- 且按<input type="text"  id="ingeterMultiple" name="ingeterMultiple" class="brr" 
													>整数倍数<span class="font-color"></span> -->
												</p>
											<!-- 	<p>
												<input type="radio" id="countCheck1" name="fullcut" checked="checked" value="3">（订单）活动总数量大于等于<input type="text" id="activeCount"  name="activeCount" >个<span class="font-color"></span>
												</p>
												<p>
													<input type="radio" name="fullcut" value="4">（订单）活动总数量大于等于<input
													type="text"  id="activeMoney" name="activeMoney">元 且按<input type="text"  id="ingeterMultiple4" name="ingeterMultiple4"
													>整数倍数<span class="font-color"></span>
												</p> -->
										</div>

										<div class="order-it2" id="ordercondition2"  style="display: none;">
											<p>
												<input id="moneyCheckd3" type="radio" name="fullsend" checked="checked" value="1">（订单）活动总金额大于等于<input type="text" id="commodityMoney1"  class="brr">元<span class="font-color"></span>
											</p><br/>
											<p>
												<input id="moneyCheckd4" type="radio" name="fullsend" value="2">（订单）活动总金额大于等于<input id="commodityMoney2"
													type="text" class="brr">元  该选项为等比阶梯<span class="font-color"></span><!-- 且按<input type="text" id="ingeterMultiple2" class="brr">整数倍数<span class="font-color"></span> -->
											</p><br/>
										</div>

										<div class="order-it3" id="ordercondition3" style="display: none;" >
											<p>
												<input id="countChecked1" type="radio" name="fullsend"  value="3">（订单）活动总数量大于等于<input
													type="text" id="commodityCount1"  class="brr">个<span class="font-color"></span>
											</p><br/>
											<p>
												<input id="countChecked2" type="radio" name="fullsend"  value="4">（订单）活动总数量大于等于<input
													type="text" id="commodityCount2"  class="brr">个 该选项为等比阶梯<span class="font-color"></span><!-- 且按<input type="text" id="ingeterMultiple3" class="brr"
													>整数倍数<span class="font-color"></span> -->
											</p><br/>
										</div>
										
										<!-- <div class="order-it4" id="ordercondition6"  style="display: none;">
											<p>
												<input id="moneyCheckd5" type="radio" name="down" checked="checked" value="1">（订单）活动总金额大于等于<input type="text" id="commodityMoney3">元<span class="font-color"></span>
											</p><br/>
											<p>
												<input id="countChecked3" type="radio" name="down" value="2">（订单）活动总数量大于等于<input id="commodityMoney4"
													type="text">个<span class="font-color"></span>
											</p><br/>
										</div> -->
										

										<div class="order-it" id="ordercondition4"  style="display: none;" >
											<p>
												<input type="radio">订单中包含单品（包括：品类、品牌、单品）
											</p><br/>
										</div>

										<div class="order-it" id="ordercondition5"   style="display: none;">
											<p>
												<input type="radio">（订单）活动总金额大于等于0，且
											</p><br/>
										</div>
										</div>
									</div>
									
									
									
									<div class="item">
										<div class="ite">选择执行形式 <label  onclick="spread(9)" style="cursor:pointer;">展开<img src="${path}/commons/images/trg.png"></label></div>
										<div class="aw" id="div9" style="display: none;" >
										<!-- <div class="order-hd" >活动执行形式：</div> -->

										<div class="order-it" style="display: none;" id="">
											<span>
												<input type="radio" name="name"> 减免邮费
											</span>
											<span>
												<input type="radio" name="name"> 减免邮费<input type="text">元
											</span>
										</div>

										<div class="order-it" style="display: none;" id="fullcutResult">
											<span>
												<input type="radio" name="readioIdPresente" id="readioIdPresente1" checked="checked" value="1"> （订单）支付总金额减少<input type="text" id="moneyPresent1"  class="brr">元<span class="font-color"></span>
											</span>
										</div>
										
										
										  <div class="order-it" style="display: none;" id="stepsReduce">
											    <span>
											    	<input type="radio" name="readioIdPresente" value="2" id="readioIdPresente2"> 活动总金额按设定区间段减少<span class="font-color"></span>
											    	<a href="javascript:0" class="add" id="addDetail">添加</a><span class="font-color"></span>
											    </span>
												<div class="otb">
												   <table id="list5">
												   		<tbody>
												   		<tr id="0">
												   			<th width="90px;">阶梯</th>
												   			<th width="120px;">起点金额</th>
												   			<th width="120px;">阶点金额</th>
												   			<th width="160px;">支付总减少金额(元)</th>
												   			<th width="120px;">操作</th>
												   		</tr>
												   </tbody>
												   </table>
											   </div>
											</div>
										
										
										<div class="order-it" style="display: none;" id="cutprice">
											<span>
												<input type="radio" name="singlePricePresente" id="singlePricePresente1" checked="checked" value="1"> 活动商品销售单价减少<input type="text" id="singleMoneyPresent">元    每单限购数量<input type="text" id="restrictionCount">个<span class="font-color"></span>
											</span>
										</div>
										<div class="order-it" style="display: none;"  id="percentage">
											<span>
												<input type="radio" name="singlePricePresente" id="singlePricePresente2" value="2"> 活动商品销售单价减少<input type="text" id="percentageMoney" onKeyUp="value=/^(?:[1-9]?\d|100)$/.test(value)?value:''">%(百分比)    每单限购数量<input type="text" id="restrictionCount1">个<span class="font-color"></span>
											</span>
										</div>

										<div class="order-it"  style="display: none;"  id="sendcoupons">
											<span>
												<input type="radio"> 优惠券<select><option>优惠券名称</option></select>
											</span>
										</div>

										<div class="order-it"  style="display: none;" id="sendgifts">
											<span>
												<input type="radio"> 赠送单品 <a href="javascript:0"
													class="add">添加单品</a>
											</span>

											<div class="otb"  style="display: none;">
												<table>
													<tr>
														<th width="40px;">序</th>
														<th width="200px;">单品ID</th>
														<th width="200px;">单品名称</th>
														<th width="100px;">赠送数量</th>
														<th width="150px;">操作</th>
													</tr>
													<tr>
														<td>1</td>
														<td>2</td>
														<td>3</td>
														<td>4<a id="">选择单品</a> <a href="#">删除</a></td>
													</tr>
												</table>
											</div>

											<div class="otb bo"  style="display: none;">
												<div class="query xia"  style="width:760px; margin:0;">
													<div class="p1">
									   	   			<span>单品类目:</span>
									   	   			<select class="w150">
									   	   				<option>一级类目</option>
									   	   			</select>
									   	   			<select class="w150">
									   	   				<option>二级类目</option>
									   	   			</select>
									   	   			<select class="w150">
									   	   				<option>三级类目</option>
									   	   			</select>
									   	   			<select class="w150">
									   	   				<option>四级类目</option>
									   	   			</select>
										   	   		</div>
										   	   		<div class="p1">
										   	   			<span>供应商名称:</span>
										   	   			<select class="w150"><option>供应商</option></select>
										   	   		</div>
										   	   		<div class="p2">
										   	   			<button type="button">查询</button>
										   	   		</div>
												</div>
												<div class="list">单品列表：</div>
												<table>
													<tr>
													</tr>
													<tr>
													</tr>
												</table>

												<div class="select">
													<!-- <a href="#">全选</a> --> <a href="#">添加</a>
												</div>
											</div>

										</div>


										<div class="order-it"  style="display: none;">
											<span>
												<input type="radio"> 加价购买 <a href="javascript:0"
													class="add">添加单品</a>
											</span>

											<div class="otb"  style="display: none;">
												<table>
													<tbody>
														<tr>
															<th width="40px;">序</th>
															<th width="100px;">加价金额</th>
															<th width="100px;">单品名称</th>
															<th width="100px;">购买数量</th>
															<th width="150px;">操作</th>
														</tr>
														<tr>
															<td>1</td>
															<td></td>
															<td></td>
															<td></td>
															<td><a>选择单品</a> <a>删除</a></td>
														</tr>
													</tbody>
												</table>
											</div>

											<div class="otb bo"  style="display: none;">
												<div class="query">
													<select class="qu"><option>一级类目</option></select> <select
														class="qu"><option>二级类目</option></select> <select
														class="qu"><option>三级类目</option></select> <select
														class="qu"><option>四级类目</option></select> <select
														class="qu"><option>供应商</option></select>
													<button type="button" class="bn">查询</button>
												</div>
												<div class="list">单品列表：</div>
												<table>
													<tbody>
														<tr>
															<th width="40px;">序</th>
															<th width="40px;">选择</th>
															<th width="120px;">单品编码</th>
															<th width="120px;">单品条码</th>
															<th width="120px;">单品名称</th>
															<th width="120px;">规格</th>
															<th width="70px;">单位</th>
															<th width="120px;">供应商</th>
														</tr>
														<tr>
															<td>1</td>
															<td><input type="radio"></td>
															<td></td>
															<td></td>
															<td></td>
															<td></td>
															<td></td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
												</table>
												<div class="select">
													<!-- <a href="#">全选</a> --> <a href="#">添加</a>
												</div>
											</div>
										</div>
										<div class="order-it"  style="display: none;" id="repeatesendgifts">
											<span>
												<input type="radio" name="selectCategoryLadder" checked="checked" id="selectCategoryLadder1" value="1"> 赠送商品<span class="font-color"></span><a class="add" id="addGift">增加</a><span class="font-color"></span>
											</span>

											<div class="otb"  style="display: none;">
												<table id="list3">
													<tbody>
														<tr id="0">
															<th width="40px;">阶梯</th>
															<th width="200px;">商品名称</th>
															<th width="200px;">规格</th>
															<th style="display: none;"></th>
															<th width="100px;">赠送数量</th>
															<th style="display: none;"></th>
															<th width="150px;">操作</th>
														</tr>
													</tbody>
												</table>
											</div>

											<div class="otb bo" id="giftList" style="display: none;">
										   	   <div class="xia" style="width:760px; margin:0;">
										   	   		<p class="p1">
										   	   			<span>单品类目:</span>
										   	   			<select onchange="selectCategoryFullPresent(this.value)" id="categoryOneFullPresent">
												<option value="0">--请选择--</option>
												<c:forEach items="${cate}" var="bean">
													<option value="${bean.catePubId }">${bean.pubNameCn }</option>
												</c:forEach>
											</select> 
											<select id="categoryTwoFullPresent" onchange="selectCategoryTwoFullPresent(this.value)">
												<option value="0">--请选择--</option>
											</select> 
											
											<select id="categoryThreeFullPresent" onchange="selectCategoryThreeFullPresent(this.value)">
												<option value="0">--请选择--</option>
											
											</select>
											<select  id="categoryFourFullPresent" >
												<option value="0">--请选择--</option>
											</select>
										   	   		</p>
										   	   		<p class="p1">
										   	   			<span>供应商名称:</span>
										   	   			<input type="text" id="supplier" name="supplier">
										   	   			<span>商品ID:</span><input type="text" id="productId" name="productId">
										   	   		</p>
										   	   		<p class="p1">
													   	<span>商品编码:</span><input type="text" id="productCode1" name="productCode1">
										   	   		</p>
										   	   		<span class="p2">
										   	   			<button type="button" id="searchCommodityFullPresent" onclick="findCommodity1(1)">查询</button>
										   	   		</span>
										   	   		
										   	   </div>
										   	   <div class="list">单品列表：</div>
											       <div id="fullPresentCommodity1">
											   		
											   	</div>
											   <div class="select">
											   		<!-- <a href="#">全选</a> -->
											   		<a id="appendGift">添加<input type="hidden" id="selectCommodityNum"></a>
											   </div>
										   </div>
										</div>
										
										<div class="order-it" id="notRepeateSendGifts" style="display: none;">
											    	<input type="radio" name="selectCategoryLadder" value="2" id="selectCategoryLadder2"> 阶梯赠送商品<span class="font-color"></span>
												    <span class="sp">
												    	&nbsp;&nbsp;&nbsp;&nbsp;
												    	<input type="radio" name="countLadder"  id="countLadder1" checked="checked" value="1"> 按数量阶梯
												    	<input type="radio" name="countLadder"  id="countLadder2" value="2"> 按金额阶梯
												    	<a class="add" id="addGift1">增加</a><span class="font-color"></span>
												    </span>
													
													<div class="otb">
													   <table id="list4">
														   	<tbody>
														   		<tr id="0">
														   			<th width="40px;">阶梯</th>
														   			<th width="60px;">起点数量</th>
														   			<th width="60px;">阶点数量</th>
														   			<th width="60px;">起点金额</th>
														   			<th width="60px;">阶点金额</th>
														   			<th width="120px;">商品名称</th>
														   			<th width="120px;">规格</th>
														   			<th style="display: none;"></th>
														   			<th width="100px;">赠送数量</th>
														   			<th style="display: none;"></th>
														   			<th width="100px;">操作</th>
														   		</tr>
														   </tbody>
													   </table>
												   </div>
							
												   <div class="otb bo" id="giftList1" style="display: none;">
												   	   <div class="xia" style="width:760px; margin:0;">
																<p class="p1">
																	<span>单品类目:</span> <select
																		onchange="selectCategoryFullPresent1(this.value)"
																		id="categoryOneFullPresent1">
																		<option value="0">--请选择--</option>
																		<c:forEach items="${cate}" var="bean">
																			<option value="${bean.catePubId }">${bean.pubNameCn }</option>
																		</c:forEach>
																	</select> <select id="categoryTwoFullPresent1"
																		onchange="selectCategoryTwoFullPresent1(this.value)">
																		<option value="0">--请选择--</option>
																	</select> <select id="categoryThreeFullPresent1"
																		onchange="selectCategoryThreeFullPresent1(this.value)">
																		<option value="0">--请选择--</option>

																	</select> <select id="categoryFourFullPresent1">
																		<option value="0">--请选择--</option>
																	</select>
																</p>
																<p class="p1">
													   	   			<span>供应商名称:</span>
													   	   			<input type="text" id="supplier1" name="supplier1">
													   	   			<span>商品ID:</span><input type="text" id="productId1" name="productId1">
												   	   			</p>
												   	   			<p class="p1">
													   	   			<span>商品编码:</span><input type="text" id="productCode2" name="productCode2">
												   	   			</p>
												   	   		<span class="p2">
												   	   			<button type="button" id="searchCommodityFullPresent1" onclick="findCommodity2(1)">查询</button>
												   	   		</span>
												   	   		
												   	   </div>
												   	   <div class="list">单品列表：</div>
													   <div id="fullPresentCommodity2">
													   	
													   </div>
													   <div class="select">
													   		<!-- <a href="#">全选</a> -->
													   		<a id="appendGift1">添加<input type="hidden" id="selectCommodityNum1"></a>
													   </div>
												   </div>
											<!-- 	</div>
											</div> -->
										</div>
										
										<div class="order-it"  style="display: none;" id="repeatesendcoupons">
											<span>
												<input type="radio" name="sendCouponType" id="sendCouponType1" checked="checked" value="1"> 返还优惠券<span class="font-color"></span> <a class="add" id="addCoupon">增加</a><span class="font-color"></span>
											</span>

											<div class="otb">
												<table  id="list1">
													<tbody>
														<tr id="0">
															<th width="40px;">阶梯</th>
															<th width="200px;">优惠券名称</th>
															<th width="200px;">优惠券规则名称</th>
															<th style="display: none;"></th>
															<th width="100px;">赠送数量</th>
															<th width="150px;">操作</th>
														</tr>
													</tbody>
												</table>
											</div>

											<div class="otb bo"  id="couponList">
												<div class="xia" style="width: 760px; margin: 0;">
													<p class="p1">
														<span>优惠券名称:</span> <input type="text" id="couponName" name="couponName" value="">
														<span>优惠券类型:</span> 
														<select id="couponType">
															<option value="1">金劵</option>
														</select>
													</p>
													<p class="p1">
														<span>开始时间:</span> <input type="text" name="couponStartdate" id="couponStartdate" 
                   											class="act-txt" onClick="WdatePicker()"> 
                   										<span>结束时间:</span>
														<input type="text" name="couponEnddate" id="couponEnddate" 
                   											class="act-txt" onClick="WdatePicker()"><span class="font-color"></span>
													</p>
													<p class="p2">
														<button type="button" id="findCoupons" onclick="findCoupons(1)">查询</button>
													</p>

												</div>
												<div class="list">优惠券列表：</div>
												<div  id="couponsList" >
												
												</div>
												
												<div class="select">
													<a id="appendCoupon">添加<input type="hidden"  id="selectCouponNum"></a>
												</div>
											</div>
										</div>
											
										<div class="order-it" id="notRepeateSendCoupon" style="display: none;">
											    	<span class="sp"><input type="radio" name="sendCouponType" id="sendCouponType2" value="2">阶梯返还优惠券 <span class="font-color"></span>
											    	<i style="color:#ffaa01;">
											    	(选择优惠劵中有效的优惠劵，时间有效，数量有效)</i>
											    	</span>
												    <span class="sp">
												    	&nbsp;&nbsp;&nbsp;&nbsp;
												    	<input type="radio" name="sendtype" checked="checked" id="sendtype1" value="1"> 按数量阶梯
												    	<input type="radio" name="sendtype" id="sendtype2" value="2"> 按金额阶梯
												    	<a class="add" id="addCoupon1">增加</a><span class="font-color"></span>
												    </span>
													
													<div class="otb">
													   <table id="list2">
													   		<tbody>
														   		<tr id="0">
														   			<th width="40px;">序号</th>
														   			<th width="80px;">起点数量</th>
														   			<th width="80px;">阶点数量</th>
														   			<th width="80px;">起点金额</th>
														   			<th width="80px;">阶点金额</th>
														   			<th width="100px;">优惠劵名称</th>
														   			<th width="100px;">优惠劵规则名称</th>
														   			<th style="display: none;"></th>
														   			<th width="100px;">赠送数量</th>
														   			<th width="120px;">操作</th>
														   		</tr>
													   		</tbody>
													   </table>
												   </div>
							
												   <div class="otb bo" id="couponList1">
												   	   <div class="xia" style="width:760px; margin:0;">
												   	   		<p class="p1">
												   	   			<span>优惠券名称:</span>
												   	   			<input type="text" id="couponName1" name="couponName1">
												   	   			<span>优惠券类型:</span>
												   	   			<select id="couponType1">
												   	   				<option value="1">金劵</option>
												   	   			</select>
												   	   		</p>
												   	   		<p class="p1">
												   	   			<span>开始时间:</span>
												   	   			<input type="text" id="couponStartdate1"   onClick="WdatePicker()">
												   	   			<span>结束时间:</span>
												   	   			<input type="text" id="couponEnddate1"  onClick="WdatePicker()"><span class="font-color"></span>
												   	   		</p>
												   	   		<span class="p2">
												   	   			<button type="button" id="findCoupons1" onclick="findCoupons1(1)">查询</button>
												   	   		</span>
												   	   		
												   	   </div>
												   	   <div class="list">优惠券列表：</div>
													   	<div id="couponsList1">
													   		
													   	</div>
													   <div class="select">
													   		<!-- <a href="#">全选</a> -->
													   		<a id="appendCoupon1">添加<input type="hidden"  id="selectCouponNum1"></a>
													   </div>
												   </div>
										</div>
										</div>
									</div>
									
									
									
									
									<div class="item">
										<div class="ite">设定活动会员
										<label onclick="spread(1)" style="cursor:pointer;">展开<img src="${path}/commons/images/trg.png"></label>
										</div>
										
										<div class="aw" id="div1" style="display:none">
											<div class="attr">
												<div class="a-key">已选活动会员：</div>
												<div class="a-value setdel" id="AddMember">
													<ul>
													</ul>
												</div>
											</div>
											<div class="attr">
												<div class="a-key">会员：</div>
												<div class="a-value">
													<span class="va"><input type="radio" name="member" id="member1" checked="checked" value="3">B2B会员</span>
													<span class="va"><input type="radio" name="member" id="member2"  value="4">B2C会员   <span class="font-color"></span></span>
												</div>
											</div>
										</div>
									</div>

									<div class="item">
										<div class="ite">设定访问方式  <label  onclick="spread(2)" style="cursor:pointer;">展开<img src="${path}/commons/images/trg.png"></label></div>
										<div class="aw" id="div2" style="display:none">
											<div class="attr">
												<div class="a-key">已选访问方式：
												</div>
												<div class="a-value setdel" id="selectedAccessMode">
													<ul>
															 
													</ul>
												</div>
											</div>
											<div class="attr">
												<div class="a-key">访问方式：</div>
												<div class="a-value">
													<span class="va"><input type="radio" name="accessMethod" id="accessMethod1" checked="checked">所有访问方式</span>
													<span class="va"><input type="radio" name="accessMethod" id="accessMethod2">部分访问方式   <span class="font-color"></span></span>
												</div>
											<div class="attr at" id="accessMethodDiv">
												<div class="a-c" >
													<div class="fl">访问方式：</div>
													<div class="fr_w" id="Acmeth">
														<div class="fr" id="0">
															<input type="checkbox" name="accessMode" value="0"><span>PAD</span>
														</div>
														<div class="fr" id="1">
															<input type="checkbox" name="accessMode" value="1"><span>PC</span>
														</div>
														<div class="fr" id="2">
															<input type="checkbox" name="accessMode" value="2"><span>ANDROID</span>
														</div>
														<div class="fr" id="3">
															<input type="checkbox" name="accessMode" value="3"><span>WAP</span>
														</div>
														<div class="fr" id="4">
															<input type="checkbox" name="accessMode" value="4"><span>IOS</span>
														</div>
														<div class="fr" id="5">
															<input type="checkbox" name="accessMode" value="5"><span>OTHER</span>
														</div>
													</div>
												</div>
													<div class="a-k"><input type="checkbox" name="nonparticipator" value="1">所选访问方式，不参加本次活动。</div>
													<div  class="add-to">
													<button type="button" id="accessMode">添加</button>
													</div>
											</div>
										</div>
										</div>
									</div>
									
									<div class="item">
										<div class="ite">设定活动商品<label  onclick="spread(11)" style="cursor:pointer;">展开<img src="${path}/commons/images/trg.png"></label></div>
										<div class="aw" id="div11" style="display:none">
										<span class="font-color" id="showInfomation"></span>
										<div class="attr">
											<div class="a-key">商品发货途径：</div>
												<div class="a-value">
												<select class="sel" id="productFromMethod" onchange="productFromMethodChange(this.value)" >
													<option value="1">国内发货</option>
													<option value="11">海外直邮</option>
													<option value="12">保税区发货</option>
													<option value="21">韩国直邮</option>
													<option value="31">卓悦商品</option>
													<option value="50">特卖商品</option>
													<option value="51">POP商品</option>
												</select>
												</div>
										</div>
										</div>
									</div>

									
									<div id="cateogryBrandCommodity" style="display: none;">
									
									<div class="item" id="selectActiveCategory" style="display:none">
										<div class="ite f" >选择活动品类  <label onclick="spread(3)" style="cursor:pointer;">展开<img src="${path}/commons/images/trg.png"></label></div>
										<div class="aw" id="div3" style="display:none">
										<div class="attr">
											<div class="a-key">已选活动品类：</div>
											<div class="a-value" id="selectedSorts">
												<ul>
													
												</ul>
											</div>
										</div>
										<div class="attr at">
											<div class="a-key">品类：</div>
											<div class="a-value">
												<span class="va"><input type="radio" name="category" id="category3" checked="checked">不选</span>
												<span class="va"><input type="radio" name="category" id="category1" >所有品类</span>
												<span class="va"><input type="radio" name="category"  id="category2">部分品类   <span class="font-color"></span></span>
											</div>
										<div class="attr" id="categoryDiv">
											<div>
											<select onchange="selectCategory(this.value)" id="categoryOne">
												<option value="0">--请选择--</option>
												<c:forEach items="${cate}" var="bean">
													<option value="${bean.catePubId }">${bean.pubNameCn }</option>
												</c:forEach>
											</select> 
											<select id="categoryTwo" onchange="selectCategoryTwo(this.value)">
												<option value="0">--请选择--</option>
											</select> 
											
											<select id="categoryThree" onchange="selectCategoryThree(this.value)">
												<option value="0">--请选择--</option>
											
											</select>
											<select  id="categoryFour" >
												<option value="0">--请选择--</option>
											</select>
											</div>
											<!-- <div class="a-k"><input type="checkbox" name="nonparticipator"  value="4">所选品类，不参加本次活动。</div> -->
											<div class="add-to" >
												<button type="button" id="sorts">添加</button>
											</div>
										</div>
											</div>
										</div>
                                </div>
                                       
                                       
                                       
                           		<div class="item"  id="selectBrand" style="display: none">
									<div class="ite f">选择活动品牌   <label onclick="spread(4)" style="cursor:pointer;">展开<img src="${path}/commons/images/trg.png"></label></div>
									<div class="aw" id="div4" style="display:none">
										<div class="attr">
											<div class="a-key">已选活动品牌：</div>
											<div class="a-value setdel" id="AddBrand">
												<ul>
													
												</ul>
											</div>
										</div>
										<div class="attr at">
											<div class="a-key">品牌：</div>
											<div class="a-value">
												<span class="va"><input type="radio" name="brand" id="brand3"  checked="checked">不选</span>
												<span class="va"><input type="radio" name="brand" id="brand1" >所有品牌</span>
												<span class="va"><input type="radio" name="brand" id="brand2" onclick="findBrand(1)" >部分品牌   <span class="font-color"></span></span>
											</div>
											
										
											<div class="attr" id="brandDiv">
	
												<div class="brand" >
													<div class="br_t">
										   	   			<strong>品牌名称:</strong>
										   	   			<input type="text" id="brandName" name="brandName" class="br">
														<button type="button" class="query" id="searchBrand" onclick="findBrand(1)">查询</button>
													</div>
	
													<div id="BrandList">
														
													</div>
												</div>
												<div class="a-k"><input type="checkbox" name="nonparticipator"  value="5">所选品牌，不参加本次活动。</div>
														<div class="add-to">
															<button type="button" id="AddBrandBut">添加</button>
														</div>
											</div>
											</div>
										</div>
									</div>
									
									
									<div class="item" id="selectCommodity" style="display: none">
										<div class="ite f" >选择活动单品   <label onclick="spread(7)" style="cursor:pointer;">展开<img src="${path}/commons/images/trg.png"></label></div>
										<div class="aw" id="div7" style="display:none">
											<div class="attr">
												<div class="a-key">已选活动单品：</div>
												<div class="a-value" id="selectedSkus">
													<ul>
														
													</ul>
												</div>
											</div>
										<div class="attr">
											<div class="a-key">单品：</div>
											<div class="a-value">
												<span class="va"><input type="radio" name="commodity" id="commodity3" checked="checked" >不选</span>
												<span class="va"><input type="radio" name="commodity" id="commodity1" >所有单品</span>
												<span class="va"><input type="radio" name="commodity" id="commodity2">部分单品   <span class="font-color"></span></span>
											</div>
										
								
											</div>
													<div class="order-it" id="commodityDiv" >
									    	<div class="otb bo" id="commodityDiv" >
											<div class="xia"  style="width:760px; margin:0;">
													<p class="p1">
														<select onchange="selectCategoryCommodity(this.value)" id="categoryOneCommodity">
															<option value="0">--请选择--</option>
															<c:forEach items="${cate}" var="bean">
																<option value="${bean.catePubId }">${bean.pubNameCn }</option>
															</c:forEach>
														</select> 
														<select id="categoryTwoCommodity" onchange="selectCategoryTwoCommodity(this.value)">
															<option value="0">--请选择--</option>
														</select> 
														
														<select id="categoryThreeCommodity" onchange="selectCategoryThreeCommodity(this.value)">
															<option value="0">--请选择--</option>
														
														</select>
														<select  id="categoryFourCommodity" >
															<option value="0">--请选择--</option>
														</select>
													</p>
													<p class="p1">
														<span>商品ID:</span><input type="text" id="categoryCode">
														<span>供应商名称:</span><input type="text" id="supplier2">
													</p>
													<p class="p1">
														<span>商品编码:</span><input type="text" id="productCode">
													</p>
													<span class="p2">
														<button type="button" class="btn" id="searchCategory" onclick="clickSubmit(1)">查询</button>
													</span>
												</div>
											<div class="list">单品列表：</div>
											<div id="commodityList">

											</div>
										</div>
											<div class="attr"  >
												<div class="a-k" id="notAttend"><input type="checkbox" name="nonparticipator"  value="6">所选单品，不参加本次活动。</div>
												<div class="add-to">
													<button type="button" id="skus">添加</button>
												</div>
											</div>
											</div>
										</div>
									</div>
									
									
									</div>


									<div class="item">
										<div class="ite">选择活动区域   <label onclick="spread(5)" style="cursor:pointer;">展开<img src="${path}/commons/images/trg.png"></label></div>
										<div class="aw" id="div5" style="display:none">
										<div class="attr">
											<div class="a-key">已选活动区域：</div>
											<div class="a-value" id="selectedArea">
												<ul>
													
												</ul>
											</div>
										</div>
										<div class="attr at">
											<div class="a-key">区域：</div>
											<div class="a-value">
												<span class="va"><input type="radio" name="region" id="region1" checked="checked" >所有区域</span>
												<span class="va"><input type="radio" name="region" id="region2" >部分区域   <span class="font-color"></span></span>
											</div>
											
										
										<div class="attr" id="regionDiv">
											<div>
											<label>省：</label>
											<select onchange="selectProvices(this.value)">
												<option value="0">--请选择--</option>
													<c:forEach items="${provices }" var="bean">
													<option value="${bean.provinceid }">${bean.provincename }</option>
												</c:forEach>
											</select> 
											<label>市：</label>
											<select id="city">
												<option value="0">--请选择--</option>
											</select>
											</div>
											<div class="a-k"><input type="checkbox"  name="nonparticipator" value="2">所选区域，不参加本次活动。</div>
											<div class="add-to">
												<button type="button" id="areas">添加</button>
											</div>
										</div>
										</div>
									</div>
									</div>
									
									
									

                                      <!--选择活动渠道 -->
                                     <div class="item">
										<div class="ite">选择活动渠道   <label onclick="spread(6)" style="cursor:pointer;">展开<img src="${path}/commons/images/trg.png"></label></div>
										<div class="aw" id="div6" style="display:none">
											<div class="attr">
												<div class="a-key">已选活动渠道：</div>
												<div class="a-value setdel" id="AddChannel">
													<ul>
														
													</ul>
												</div>
												</div>
											<div class="attr ">
												<div class="a-key">渠道：</div>
												<div class="a-value">
													<span class="va"><input type="radio" name="channel" id="channel1" checked="checked">所有渠道</span>
													<span class="va"><input type="radio" name="channel" id="channel2">部分渠道   <span class="font-color"></span></span>
												</div>
											
											<div class="attr at" id="channelDiv">
															<div class="a-c" >
																	<div class="fl">销售渠道：</div>
																<div class="fr_w">
																		<c:forEach items="${channel}" var="bean">
																				<div class="fr" id="${bean.channelId }">
																					<input type="checkbox"  value="${bean.channelId }" ><span>${bean.channelName }</span>
																				</div>
																		</c:forEach>
																	</div>
															</div>
													<div class="a-k"><input type="checkbox" name="nonparticipator" value="3">所选渠道，不参加本次活动。</div>
												    <div class="add-to">
													<button type="button" id="AddChannelBut">添加</button>
												</div>
										    </div>
												</div>
										    </div>
											</div>

										</div>

									</div>

									<div class="se">
										<input type="button" name="button" id="confirmButton"
											class="btn1" value="确定" style="cursor:pointer;">
										<input type="button" name="backButton" id="backButton"
											class="btn2" value="取消" style="cursor:pointer;">
									</div>


								</div>
						
						</div>



					</div>
					
				</div>
			</div>
</body>
</html>