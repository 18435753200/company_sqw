<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%  
    String basePath = request.getContextPath();
	request.setAttribute("path",basePath);
	request.setAttribute("pathUrl", "/product/getPro");
%>
<div class="leftly">
<div class="left f_l">
	<p class="blank"></p>
	<div class="title">
		<p class="f_l">
			<img src="${path}/commons/images/img_title.png" alt="">
		</p>
		<p class="f_l p1">卖家中心</p>
		<p class="clear"></p>
	</div>
	<p class="blank5"></p>
	<div class="list_box">
	<!-- 商品管理左边菜单start -->
		<c:if test="${ !empty buttonsMap['商品管理']}">
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t1.png">商品管理
					</p>
				</h2>
				<div class="p_b">
				  <%-- <c:if test="${!empty buttonsMap['货品列表']}">
						<p class="p2" id="platform_saleProduct">
							<a href="${path}${ buttonsMap['货品列表']}">商品列表</a>
						</p>
					</c:if> 
				--%>
					<c:if test="${!empty buttonsMap['POP货品列表']}">
					<p class="p2" id="POPplatform_saleProduct">
							<a href="${path}${ buttonsMap['POP货品列表']}">商品列表</a>
					</p>
					</c:if>
<%-- 					<c:if test="${!empty buttonsMap['情景组合']}">
						<p class="p2" id="product_mix">
							<a href="${path}${ buttonsMap['情景组合']}">情景组合</a>
						</p>
					</c:if> --%>
<%-- 					<c:if test="${!empty buttonsMap['库存管理']}">
						<p class="p2" id="inventory">
							<a href="${path}${ buttonsMap['库存管理']}">库存管理</a>
						</p>
					</c:if> --%>
					<%-- <c:if test="${!empty buttonsMap['批次库存管理']}">
						<p class="p2" id="batch_inventory">
							<a href="${path}${ buttonsMap['批次库存管理']}">批次库存管理</a>
						</p>
					</c:if> --%>
<%-- 					<c:if test="${!empty buttonsMap['仓库管理']}">
						<p class="p2" id="warehouse">
							<a href="${path}${ buttonsMap['仓库管理']}">仓库管理</a>
						</p>
					</c:if> --%>
<%-- 					<c:if test="${!empty buttonsMap['库房级别']}">
						<p class="p2" id="warehouseLevel">
							<a href="${path}${ buttonsMap['库房级别']}">库房级别</a>
						</p>
					</c:if> --%>
<%-- 					<c:if test="${!empty buttonsMap['库房类型']}">
						<p class="p2" id="warehouseType">
							<a href="${path}${ buttonsMap['库房类型']}">库房类型</a>
						</p>
					</c:if> --%>
					<c:if test="${!empty buttonsMap['发货渠道']}">
						<p class="p2" id="deliveryChannel">
							<a href="${path}${ buttonsMap['发货渠道']}">发货渠道</a>
						</p>
					</c:if>
					<c:if test="${!empty buttonsMap['热词列表']}">
						<p class="p2" id="HotWordsList">
							<a href="${path}${ buttonsMap['热词列表']}">搜索热词表</a>
						</p>
					</c:if>
					<c:if test="${!empty buttonsMap['商品成本价导入']}">
						<p class="p2" id="HotWordsList">
							<a href="${path}${ buttonsMap['商品成本价导入']}">商品成本价导入</a>
						</p>
					</c:if>
				</div>
			</div>
		</c:if>
		
<%-- 		<c:if test="${ !empty buttonsMap['尖货设置']}">
		    <div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t2.png">尖货设置
					</p>
				</h2>
				<div class="p_b">
					<c:if test="${!empty buttonsMap['话题管理']}">
						<p class="p2" id="platform_activityTopic">
							<a href="${path}${ buttonsMap['话题管理']}">话题管理</a>
						</p>
					</c:if>
				</div>
			</div>
		</c:if> --%>
		
	<!-- 商品管理左边菜单end -->
	
	<!-- 订单管理左边菜单start -->
		<c:if test="${ !empty buttonsMap['订单管理']}">
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t2.png">订单管理
					</p>
				</h2>
				<div class="p_b">
<%-- 					<c:if test="${ !empty buttonsMap['期货订单']}">
						<p id="platform_orderfutures" class="p2 ">
							<a href="${path}${ buttonsMap['期货订单']}">期货订单</a>
						</p>
					</c:if> --%>
<%-- 					<c:if test="${ !empty buttonsMap['B订单管理']}">
						<p id="platform_retailerorder_alllist" class="p2 ">
							<a href="${path}${ buttonsMap['B订单管理']}">B订单管理</a>
						</p>
					</c:if> --%>
<%-- 					<c:if test="${ !empty buttonsMap['包裹查询']}">
						<p id="platform_package" class="p2 ">
							<a href="${path}${ buttonsMap['包裹查询']}">包裹查询</a>
						</p>
					</c:if> --%>
<%-- 					<c:if test="${ !empty buttonsMap['装箱订单']}">
						<p id="platform_loadplan" class="p2 ">
							<a href="${path}${ buttonsMap['装箱订单']}">装箱订单</a>
						</p>
					</c:if> --%>
					<c:if test="${ !empty buttonsMap['C订单管理']}">
						<p id="platform_Corder" class="p2 ">
							<a href="${path}${ buttonsMap['C订单管理']}">商城订单管理</a>
						</p>
					</c:if>
					<%-- <c:if test="${ !empty buttonsMap['海外直邮订单管理']}">
						<p id="platform_overseasCorder" class="p2 ">
							<a href="${path}${ buttonsMap['海外直邮订单管理']}">海外直邮订单管理</a>
						</p>
					</c:if> --%>
<%-- 					<c:if test="${ !empty buttonsMap['退款订单']}">
						<p id="platform_Corder_refund" class="p2 ">
							<a href="${path}${ buttonsMap['退款订单']}">退款订单</a>
						</p>
					</c:if> --%>
<%--  					<c:if test="${ !empty buttonsMap['海关订单重推']}">
						<p id="platform_Corder_order_push_again" class="p2 ">
							<a href="${path}${ buttonsMap['海关订单重推']}">海关订单重推</a>
						</p>
					</c:if>
 					<c:if test="${ !empty buttonsMap['充值失败重推']}">
						<p id="platform_recharge_error_again" class="p2 ">
							<a href="${path}${ buttonsMap['充值失败重推']}">充值失败重推</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['第三方订单导入']}">
						<p id="platform_Corder_order_thirdparty" class="p2 ">
							<a href="${path}${ buttonsMap['第三方订单导入']}">第三方订单导入</a>
						</p>
					</c:if>
 					<c:if test="${ !empty buttonsMap['B订单取消(未发货)']}">
						<p id="platform_Corder_order_b2b_cancel" class="p2 ">
								<a href="${path}${ buttonsMap['B订单取消(未发货)']}">B订单取消(未发货)</a>
						</p>
					</c:if>
 					<c:if test="${ !empty buttonsMap['C订单取消(未发货)']}">
						<p id="platform_Corder_order_cancel" class="p2 ">
							<a href="${path}${ buttonsMap['C订单取消(未发货)']}">C订单取消(未发货)</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['身份证查询']}">
						<p id="platform_Corder_idcard_find" class="p2 ">
							<a href="${path}${ buttonsMap['身份证查询']}">身份证查询</a>
						</p>
					</c:if>  --%>
				</div>
			</div>
		</c:if>
	<!-- 订单管理左边菜单end -->
	
	<!-- 账务管理左边菜单start -->
	<%-- 	<c:if test="${ !empty buttonsMap['账务管理']}">
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t1.png">账务管理
					</p>
				</h2>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['交易账务']}">
						<p id="platform_account" class="p2">
							<a href="${path}${ buttonsMap['交易账务']}">交易账务</a>
						</p>
					</c:if>
				</div>
			</div>
		</c:if> --%>
	<!-- 账务管理左边菜单end -->
	
	<!-- 评价管理左边菜单start -->
		<%-- <c:if test="${ !empty buttonsMap['评价管理']}">
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t1.png">评价管理
					</p>
				</h2>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['评价列表']}">
						<p id="platform_commentsManage" class="p2">
							<a href="${path}${ buttonsMap['评价列表']}">评价列表</a>
						</p>
					</c:if>
				</div>
			</div>
		</c:if> --%>
	<!-- 评价管理左边菜单end -->
	<!-- 采购单管理左边菜单start -->
		<c:if test="${ !empty buttonsMap['采购单管理']}">
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t2.png">采购单管理
					</p>
				</h2>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['采购单列表']}">
						<p id="platform_purchaseorder" class="p2 ">
							<a href="${path}${ buttonsMap['采购单列表']}">采购单列表</a>
						</p>
					</c:if>
				</div>
			</div>
		</c:if>
		
	<!-- 采购单管理左边菜单end -->
	
	<!-- 商品采购左边菜单begin -->
<%--  	<c:if test="${ !empty buttonsMap['商品采购']}">
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t2.png">商品采购
					</p>
				</h2>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['采购列表']}">
						<p id="platform_purchaseorder" class="p2 ">
							<a href="${path}${ buttonsMap['采购列表']}">采购列表</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['采购发票']}">
						<p id="platform_purchaseorder" class="p2 ">
							<a href="${path}${ buttonsMap['采购发票']}">采购发票</a>
						</p>
					</c:if>
				</div>
			</div>
		</c:if>  --%>
	<!-- 商品采购左边菜单end -->
	
	<!-- 特卖采购单管理左边菜单start -->
		<%-- <c:if test="${ !empty buttonsMap['特卖虚拟库存设置']}">
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t2.png">特卖虚拟库存设置
					</p>
				</h2>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['虚拟库存设置']}">
						<p id="platform_purchasvirtualeorder" class="p2 ">
							<a href="${path}${ buttonsMap['虚拟库存设置']}">虚拟库存设置</a>
						</p>
					</c:if>
				</div>
			</div>
		</c:if> --%>
		
	<!-- 特卖采购单管理左边菜单end -->
	
<%-- 	<c:if test="${ !empty buttonsMap['商品储存']}">
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t2.png">商品储存
					</p>
				</h2>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['入库通知']}">
						<p id="platform_notificationinorder" class="p2">
							<a href="${path}${ buttonsMap['入库通知']}">入库通知</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['入库单']}">
						<p id="platform_warehousewarrant" class="p2 ">
							<a href="${path}${ buttonsMap['入库单']}">入库单</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['物流通知']}">
						<p id="platform_wuliutongzhi" class="p2 ">
							<a href="${path}${ buttonsMap['物流通知']}">物流通知</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['调拨通知']}">
						<p id="platform_diaobotongzhi" class="p2 ">
							<a href="${path}${ buttonsMap['调拨通知']}">调拨通知</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['采购到齐']}">
						<p id="platform_purchasinghere" class="p2 ">
							<a href="${path}${ buttonsMap['采购到齐']}">采购到齐</a>
						</p>
					</c:if>
				</div>
				
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['物流查询']}">
						<p id="platform_wuliuchaxun" class="p2 ">
							<a href="${path}${ buttonsMap['物流查询']}">物流查询</a>
						</p>
					</c:if>
				</div>
				
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['库存调整查询']}">
						<p id="platform_kucundiaozhengchaxun" class="p2 ">
							<a href="${path}${ buttonsMap['库存调整查询']}">库存调整查询</a>
						</p>
					</c:if>
				</div>
				
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['库存冻结查询']}">
						<p id="platform_kucundongjiechaxun" class="p2 ">
							<a href="${path}${ buttonsMap['库存冻结查询']}">库存冻结查询</a>
						</p>
					</c:if>
				</div>
				
			</div>
		</c:if> --%>
		
		<%-- <c:if test="${ !empty buttonsMap['销售出库']}">
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t1.png">销售出库
					</p>
				</h2>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['出库申请']}">
						<p id="platform_Notifytheoutbound" class="p2">
							<a href="${path}${ buttonsMap['出库申请']}">出库申请</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['通知出库']}">
						<p id="platform_Notifytheoutbound" class="p2">
							<a href="${path}${ buttonsMap['通知出库']}">通知出库</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['通知发货']}">
						<p id="platform_shippingadvice" class="p2">
							<a href="${path}${ buttonsMap['通知发货']}">通知发货</a>
						</p>
					</c:if>
				</div>
			</div>
		</c:if> --%>
		
		
	<%-- 	<c:if test="${ !empty buttonsMap['商品发货']}">
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t1.png">商品发货
					</p>
				</h2>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['商品发货']}">
						<p id="platform_ItemsShipped" class="p2">
							<a href="${path}${ buttonsMap['商品发货']}">商品发货</a>
						</p>
					</c:if>
				</div>
				
			</div>
		</c:if> --%>
		
		
		<%-- <c:if test="${ !empty buttonsMap['库存查询']}">
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t1.png">库存查询
					</p>
				</h2>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['库存明细']}">
						<p id="platform_ItemsShipped1" class="p2">
							<a href="${path}${ buttonsMap['库存明细']}">库存明细</a>
						</p>
					</c:if>
				</div>
				
			</div>
		</c:if> --%>
		
		
	<%-- 	<c:if test="${ !empty buttonsMap['发货跟踪']}">
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t1.png">发货跟踪
					</p>
				</h2>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['发货跟踪']}">
						<p id="platform_Deliverytrackcharge" class="p2">
							<a href="${path}${ buttonsMap['发货跟踪']}">发货跟踪</a>
						</p>
					</c:if>
				</div>
				
			</div>
		</c:if> --%>
	
		<%-- <c:if test="${ !empty buttonsMap['费用归集']}">
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t2.png">费用归集
					</p>
				</h2>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['采购费用']}">
						<p id="platform_buyingexpenses" class="p2 ">
							<a href="${path}${ buttonsMap['采购费用']}">采购费用</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['国内运费']}">
						<p id="platform_homefreight" class="p2 ">
							<a href="${path}${ buttonsMap['国内运费']}">国内运费</a>
						</p>
					</c:if>
				</div>
			</div>
		</c:if> --%>
	
	<!-- 物流管理左边菜单start -->
		<c:if test="${ !empty buttonsMap['物流管理']}">
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t1.png">物流管理
					</p>
				</h2>
				<%-- <div class="p_b">
					<c:if test="${ !empty buttonsMap['平台运费模板管理']}">
						<p id="platform_platformlogistics" class="p2">
							<a href="${path}${ buttonsMap['平台运费模板管理']}">平台运费模板管理</a>
						</p>
					</c:if>
				</div> --%>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['物流商管理']}">
						<p id="platform_logistics" class="p2">
							<a href="${path}${ buttonsMap['物流商管理']}">物流商管理</a>
						</p>
					</c:if>
				</div>
			</div>
		</c:if>
	<!-- 物流管理左边菜单end -->
	<!-- 平台会员管理左边菜单start -->
	<c:if test="${ !empty buttonsMap['平台会员管理']}">
		<div class="demo">
			<h2>
				<p class="p1">
					<img src="${path}/commons/images/img_t1.png">平台会员管理
				</p>
			</h2>
			<div class="p_b">
				<c:if test="${ !empty buttonsMap['会员手机号修改']}">
					<p id="platform_mobile" class="p2">
						<a href="${path}${ buttonsMap['会员手机号修改']}">会员手机号修改</a>
					</p>
				</c:if>
			</div>
			<div class="p_b">
				<c:if test="${ !empty buttonsMap['预留号段分配']}">
					<p id="platform_preNumber" class="p2" title="（含预留号段及预留号段分配）">
						<a href="${path}${ buttonsMap['预留号段分配']}">预留号段分配</a>
					</p>
				</c:if>
			</div>
			<div class="p_b">
				<c:if test="${ !empty buttonsMap['会员账号设置']}">
					<p id="platform_accountNumber" class="p2">
						<a href="${path}${ buttonsMap['会员账号设置']}">会员账号设置</a>
					</p>
				</c:if>
			</div>
			<div class="p_b">
				<c:if test="${ !empty buttonsMap['会员账号管理']}">
					<p id="platform_accountSet" class="p2">
						<a href="${path}${ buttonsMap['会员账号管理']}">会员账号管理</a>
					</p>
				</c:if>
			</div>
			<div class="p_b">
				<c:if test="${ !empty buttonsMap['企业账户管理']}">
					<p id="platform_companyAccountset" class="p2">
						<a href="${path}${ buttonsMap['企业账户管理']}">企业账户管理</a>
					</p>
				</c:if>
			</div>
			<div class="p_b">
				<c:if test="${ !empty buttonsMap['企业数据排行']}">
					<p id="platform_supperDataSort" class="p2">
						<a href="${path}${ buttonsMap['企业数据排行']}">企业数据排行</a>
					</p>
				</c:if>
			</div>
		</div>
	</c:if>
	<!-- 平台会员管理左边菜单end -->
		
		<!-- 代理设置左边菜单开始 -->
		<c:if test="${ !empty buttonsMap['代理管理']}">
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t1.png">代理管理
					</p>
				</h2>
				
				<c:if test="${ !empty buttonsMap['代理商管理设置']}">
						<p id="platform_supperManager" class="p2">
							<a href="${path}${ buttonsMap['代理商管理设置']}">代理商管理设置</a>
						</p>
					</c:if>
					
					<c:if test="${ !empty buttonsMap['审批优惠券设置']}">
						<p id="platform_supperApprove" class="p2">
							<a href="${path}${ buttonsMap['审批优惠券设置']}">审批优惠券设置</a>
						</p>
					</c:if>
					
					<c:if test="${ !empty buttonsMap['二维码管理设置']}">
						<p id="platform_supperCode" class="p2">
							<a  href="${path}${ buttonsMap['二维码管理设置']}">二维码管理设置</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['二维码生成设置']}">
						<p id="platform_supperCodeCreate" class="p2">
							<a  href="${path}${ buttonsMap['二维码生成设置']}">二维码生成设置</a>
						</p>
					</c:if>
					
		</div>
		</c:if>
		<!-- 代理设置左边菜单end -->
		
		
		
		
	<!-- 基础设置左边菜单start -->
		<c:if test="${ !empty buttonsMap['基础设置']}">
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t1.png">基础设置
					</p>
				</h2>
				<%-- <div class="p_b">
					<c:if test="${ !empty buttonsMap['紧急程度']}">
						<p id="platform_emergency" class="p2">
							<a href="${path}${ buttonsMap['紧急程度']}">紧急程度</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['承运方式']}">
						<p id="platform_forwarding" class="p2">
							<a href="${path}${ buttonsMap['承运方式']}">承运方式</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['币别设置']}">
						<p id="platform_currency" class="p2">
							<a href="${path}${ buttonsMap['币别设置']}">币别设置</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['账期计算方式']}">
						<p id="platform_account" class="p2">
							<a href="${path}${ buttonsMap['账期计算方式']}">账期计算方式</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['税率设置']}">
						<p id="platform_taxrate" class="p2">
							<a href="${path}${ buttonsMap['税率设置']}">税率设置</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['承运方']}">
						<p id="platform_shipper" class="p2">
							<a href="${path}${ buttonsMap['承运方']}">承运方</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['货物费用分摊方式']}">
						<p id="platform_cost" class="p2">
							<a href="${path}${ buttonsMap['货物费用分摊方式']}">货物费用分摊方式</a>
						</p>
					</c:if>
				</div>
					<div class="p_b">
					<c:if test="${ !empty buttonsMap['货物费用名称设置']}">
						<p id="platform_costg" class="p2">
							<a href="${path}${ buttonsMap['货物费用名称设置']}">货物费用名称设置</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['成交条款']}">
						<p id="platform_deal" class="p2">
							<a href="${path}${ buttonsMap['成交条款']}">成交条款</a>
						</p>
					</c:if>
				</div>
					<div class="p_b">
					<c:if test="${ !empty buttonsMap['服务商档案']}">
						<p id="platform_deal" class="p2">
							<a href="${path}${ buttonsMap['服务商档案']}">服务商档案</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['商品分类']}">
						<p id="platform_getGoodsCategory" class="p2">
							<a href="${path}${ buttonsMap['商品分类']}">商品分类</a>
						</p>
					</c:if>
				</div> --%>
				
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['开屏设置']}">
						<p id="platform_setStartupPageList" class="p2">
							<a href="${path}${ buttonsMap['开屏设置']}">app开屏设置</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['平台通知']}">
						<p id="platform_notices" class="p2">
							<a href="${path}${ buttonsMap['平台通知']}">平台通知</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['版本控制']}">
						<p id="platform_versionSet" class="p2">
							<a href="${path}${ buttonsMap['版本控制']}">版本控制</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['意见反馈']}">
						<p id="platform_retailerComplait" class="p2">
							<a href="${path}${ buttonsMap['意见反馈']}">意见反馈</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['建立区域设置']}">
						<p id="platform_supplierRegionSettings" class="p2">
							<a href="${path}${ buttonsMap['建立区域设置']}">企业入驻区域设置</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['幸福购入住区域设置']}">
						<p id="platform_supplierRegionSettingsxfg" class="p2">
							<a href="${path}${ buttonsMap['幸福购入住区域设置']}">幸福购入住区域设置</a>
						</p>
					</c:if>
					
					<c:if test="${ !empty buttonsMap['企业分红设置']}">
						<p id="platform_suppercomDiv" class="p2">
							<a href="${path}${ buttonsMap['企业分红设置']}">企业分红设置</a>
						</p>
					</c:if>
					
					
					
					
					
					<c:if test="${ !empty buttonsMap['个人分红设置']}">
						<p id="platform_supperOneDiv" class="p2">
							<a href="${path}${ buttonsMap['个人分红设置']}">个人分红设置</a>
						</p>
					</c:if>

					<c:if test="${ !empty buttonsMap['现金(红旗券)支付比例']}">
						<p id="platform_oneDividendRatio" class="p2">
							<a href="${path}${ buttonsMap['现金(红旗券)支付比例']}">现金(券)支付比例</a>
						</p>
					</c:if>
					
					<c:if test="${ !empty buttonsMap['现金支付最大比例']}">
						<p id="platform_cmaxDividendRatio2" class="p2">
							<a href="${path}${ buttonsMap['现金支付最大比例']}">现金支付最大比例</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['家庭号商品数量设置']}">
						<p id="platform_homeNumbersSet" class="p2">
							<a href="${path}${ buttonsMap['家庭号商品数量设置']}">家庭号商品数量设置</a>
						</p>
					</c:if>

					<c:if test="${ !empty buttonsMap['个人周期分红设置']}">
						<p id="platform_oneFhCycle" class="p2">
							<a href="${path}${ buttonsMap['个人周期分红设置']}">个人周期分红设置</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['会员星级设置']}">
						<p id="platform_memberStarSettings" class="p2">
							<a href="${path}${ buttonsMap['会员星级设置']}">会员星级设置</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['会员分红设置']}">
						<p id="platform_memDiv" class="p2">
							<a href="${path}${ buttonsMap['会员分红设置']}">会员团队分红设置</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['收银系统设置']}">
						<p id="platform_cashierSettings" class="p2">
							<a href="${path}${ buttonsMap['收银系统设置']}">收银系统设置</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['企业入驻分期还款设置']}">
						<p id="platform_supplierRepaymentSettings" class="p2">
							<a href="${path}${ buttonsMap['企业入驻分期还款设置']}">企业分期还款设置</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['平台统计管理']}">
						<p id="platform_platformStatManage" class="p2">
							<a href="${path}${ buttonsMap['平台统计管理']}">平台统计管理</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['平台交易费设置']}">
						<p id="platform_tradefee" class="p2">
							<a href="${path}${ buttonsMap['平台交易费设置']}">平台交易费设置</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['转账设置']}">
						<p id="platform_virementSetting" class="p2">
							<a href="${path}${ buttonsMap['转账设置']}">转账设置</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['商品成本价设置']}">
						<p id="platform_productCostPriceSetting" class="p2">
							<a href="${path}${ buttonsMap['商品成本价设置']}">商品成本价设置</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['红旗券管理统计']}">
						<p id="platform_hqqStatManage" class="p2">
							<a href="${path}${ buttonsMap['红旗券管理统计']}">红旗券管理统计</a>
						</p>
					</c:if>
					<c:if test="${!empty buttonsMap['企业统计管理']}">
						<p id="platform_companyStatManage" class="p2">
							<a href="${path}${ buttonsMap['企业统计管理']}">企业统计管理</a>
						</p>
					</c:if>
					<c:if test="${!empty buttonsMap['家庭号统计管理']}">
						<p id="platform_homeCompanyManage1" class="p2">
							<a href="${path}${ buttonsMap['家庭号统计管理']}">家庭号统计管理</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['商品标签管理']}">
						<p id="platform_complaint_selecttags" class="p2">
							<a href="${path}${ buttonsMap['商品标签管理']}">商品标签管理</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['限购标签属性设置']}">
						<p id="platform_buyLimitTags" class="p2">
							<a href="${path}${ buttonsMap['限购标签属性设置']}">限购标签属性设置</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['赠券标签属性设置']}">
						<p id="platform_zjTagsSettings" class="p2">
							<a href="${path}${ buttonsMap['赠券标签属性设置']}">赠券标签属性设置</a>
						</p>
					</c:if>
				</div>
			</div>
		</c:if>	
		<!-- 基础设置左边菜单start -->
		<c:if test="${ !empty buttonsMap['日志查询']}">
			<div class="demo">
				<h2>
					<p class="p1">
						<img src="${path}/commons/images/img_t1.png">日志查询
					</p>
				</h2>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['系统操作']}">
						<p id="platform_listsyslog" class="p2">
							<a href="${path}${ buttonsMap['系统操作']}">系统操作</a>
						</p>
					</c:if>
				</div>
			</div>
		</c:if>	
	<!-- 基础设置左边菜单end -->	
	</div>



</div>
<script type="text/javascript">
    $(document).ready(function(){
		var url='${url}';
		//var menuurl = '${menuurl}';
		// alert(url);
		//alert('url:' + url) 
		if(url.indexOf("POPproductlist")!=-1||url.indexOf("POPeditproduct")!=-1||url.indexOf("POPviewproduct")!=-1||url.indexOf("POPnewProductSku")!=-1){
			$("#POPplatform_saleProduct").attr("class","p2 c_cut2");
			return;
		}
		
		if(url.indexOf("productlist")!=-1||url.indexOf("editproduct")!=-1||url.indexOf("viewproduct")!=-1||url.indexOf("newProductSku")!=-1){
			$("#platform_saleProduct").attr("class","p2 c_cut2");
		}
		if(url.indexOf("activityTopicList")!=-1||url.indexOf("activityTopic_model")!=-1||url.indexOf("viewActivityTopic")!=-1||url.indexOf("editActivityTopic")!=-1){
			$("#platform_activityTopic").attr("class","p2 c_cut2");
		}
		if(url.indexOf("order_futures")!=-1){
			$("#platform_orderfutures").attr("class","p2 c_cut2");
		}
		if(url.indexOf("order_spot")!=-1){
			$("#platform_ordernow").attr("class","p2 c_cut2");
		}
		
		
		
		
		if(url.indexOf("order_spo")!=-1){
			$("#platform_supperManager").attr("class","p2 c_cut2");
		}

		if(url.indexOf("order_sp")!=-1){
			$("#platform_supperApprove").attr("class","p2 c_cut2");
		}
		if(url.indexOf("order_sps")!=-1){
			$("#platform_supperCode").attr("class","p2 c_cut2");
		}
		if(url.indexOf("order_sps")!=-1){
			$("#platform_supperCodeCreate").attr("class","p2 c_cut2");
		}
		
		
		
		
		if(url.indexOf("accounting")!=-1){
			$("#platform_account").attr("class","p2 c_cut2");
		}
		if(url.indexOf("inventory")!=-1){
			$("#inventory").attr("class","p2 c_cut2");
		}
		if(url.indexOf("batch")!=-1){
			$("#batch_inventory").attr("class","p2 c_cut2");
		}
		if(url.indexOf("providerlogistics") !=-1 ){
			$("#platform_logst").attr("class","p2 c_cut2");
		}
		if(url.indexOf("platformlogist") !=-1 ){
			$("#platform_logstwofe").attr("class","p2 c_cut2");
		}
		if(url.indexOf("warehouselist") !=-1 ){
			$("#warehouse").attr("class","p2 c_cut2");
		}
		if(url.indexOf("orderpackage") !=-1 ){
			$("#platform_package").attr("class","p2 c_cut2");
		}
		if(url.indexOf("order/retailerorder_alllist") !=-1 ){
			$("#platform_retailerorder_alllist").attr("class","p2 c_cut2");
		}
		if(url.indexOf("comment/commentsManage") !=-1 ){
			$("#platform_commentsManage").attr("class","p2 c_cut2");
		}
		if(url.indexOf("logistics/logistics") !=-1 ){
			$("#platform_logistics").attr("class","p2 c_cut2");
		}
		if(url.indexOf("platformlogistics/platformlogistics") !=-1 ){
			$("#platform_platformlogistics").attr("class","p2 c_cut2");
		}
		if(url.indexOf("purchaseorder") !=-1 ){
			$("#platform_purchaseorder").attr("class","p2 c_cut2");
		}
		if(url.indexOf("purchasevirtualorder") !=-1 ){
			$("#platform_purchasvirtualeorder").attr("class","p2 c_cut2");
		}
		if(url.indexOf("containerloadplan/containerorder_list") !=-1 ){
			$("#platform_loadplan").attr("class","p2 c_cut2");
		}
		if(url.indexOf("B2Corder/B2Corder_list") !=-1 ){
			$("#platform_Corder").attr("class","p2 c_cut2");
		}
		if(url.indexOf("B2Corder/platform_overseasCorder_list") !=-1 ){
			$("#platform_overseasCorder").attr("class","p2 c_cut2");
		}
		if(url.indexOf("B2Corder/B2Corder_refund_list") !=-1 ){
			$("#platform_Corder_refund").attr("class","p2 c_cut2");
		}
		if(url.indexOf("B2Corder/B2Corder_order_push_again") !=-1 ){
			$("#platform_Corder_order_push_again").attr("class","p2 c_cut2");
		}
		if(url.indexOf("B2Corder/platform_recharge_error_again") !=-1 ){
			$("#platform_recharge_error_again").attr("class","p2 c_cut2");
		}
		if(url.indexOf("B2Corder/B2Corder_order_cancel") !=-1 ){
			$("#platform_Corder_order_cancel").attr("class","p2 c_cut2");
		}
		if(url.indexOf("warehouse/warehouse_list") !=-1 ){
			$("#warehouse").attr("class","p2 c_cut2");
		}
		if(url.indexOf("channel/warehouseChannel") !=-1 ){
			$("#deliveryChannel").attr("class","p2 c_cut2");
		}
		
		if(url.indexOf("hotWordList") !=-1||url.indexOf("createHotWords")!=-1){
			$("#HotWordsList").attr("class","p2 c_cut2");
		}
		
		if(url.indexOf("warehouseLevel/warehouseLevel") !=-1 ){
			$("#warehouseLevel").attr("class","p2 c_cut2");
		}
		if(url.indexOf("warehouseStoretyp/warehouseType") !=-1 ){
			$("#warehouseType").attr("class","p2 c_cut2");
		}
		if(url.indexOf("shiporder/findShipOrder") !=-1 ){
			$("#platform_wuliutongzhi").attr("class","p2 c_cut2");
		}
		if(url.indexOf("stockdetail/adjFinish_list") !=-1 ){
			$("#platform_kucundiaozhengchaxun").attr("class","p2 c_cut2");
		}	
		if(url.indexOf("stockdetail/moveFinish_list") !=-1 ){
			$("#platform_kucundongjiechaxun").attr("class","p2 c_cut2");
		}		
		if(url.indexOf("stockorder/findStockOrder") !=-1 ){
			$("#platform_warehousewarrant").attr("class","p2 c_cut2");
		}
		if(url.indexOf("commoditystore/notificationInList") !=-1 ){
			$("#platform_notificationinorder").attr("class","p2 c_cut2");
		}
		if(url.indexOf("purchasebuy/findPurchaseBuy") !=-1 ){
			$("#platform_purchasinghere").attr("class","p2 c_cut2");
		}
		if(url.indexOf("stockdetail/findStockRest") !=-1 ){
			$("#platform_stockdetail").attr("class","p2 c_cut2");
		}
		if(url.indexOf("selloutstorage/notificationOutList") !=-1 ){
			$("#platform_Notifytheoutbound").attr("class","p2 c_cut2");
		}
		if(url.indexOf("outstock/findOutStock") !=-1 ){
			$("#platform_shippingadvice").attr("class","p2 c_cut2");
		}
		if(url.indexOf("productStock/findProductStock") !=-1 ){
			$("#platform_ItemsShipped").attr("class","p2 c_cut2");
		}
		if(url.indexOf("expressMgs/findExpressMgs") !=-1 ){
			$("#platform_Deliverytrackcharge").attr("class","p2 c_cut2");
		}
		if(url.indexOf("purchasecost/findPurchaseCost") !=-1 ){
			$("#platform_buyingexpenses").attr("class","p2 c_cut2");
		}
		if(url.indexOf("purchasereg/findPurchaseReg") !=-1 ){
			$("#platform_homefreight").attr("class","p2 c_cut2");
		}
		if(url.indexOf("infrastructure/emergency") !=-1 ){
			$("#platform_emergency").attr("class","p2 c_cut2");
		}
		if(url.indexOf("infrastructure/forwarding") !=-1 ){
			$("#platform_forwarding").attr("class","p2 c_cut2");
		}
		
		if(url.indexOf("B2Border/retailerorder_cancellist") !=-1 ){
			$("#platform_Corder_order_b2b_cancel").attr("class","p2 c_cut2");
		}
		
		if(url.indexOf("user/userUpdateInfo") !=-1 ){
			$("#platform_mobile").attr("class","p2 c_cut2");
		}
		if(url.indexOf("user/userStance") !=-1 ){
			$("#platform_preNumber").attr("class","p2 c_cut2");
		}
		if(url.indexOf("user/userAccountSet") !=-1 ){
			$("#platform_accountNumber").attr("class","p2 c_cut2");
		}
		if(url.indexOf("user/userCheck") !=-1 ){
			$("#platform_accountSet").attr("class","p2 c_cut2");
		}
		if(url.indexOf("complaint_selectSupplier") !=-1 ){
			$("#platform_companyAccountset").attr("class","p2 c_cut2");
		}
		if(url.indexOf("complaint_supplier1") !=-1 ){
			$("#platform_supperDataSort").attr("class","p2 c_cut2");
		}
		if(url.indexOf("infrastructure/selectNotice") !=-1 ){
			$("#platform_notices").attr("class","p2 c_cut2");
		}
		if(url.indexOf("infrastructure/selectVersion") !=-1 ){
			$("#platform_versionSet").attr("class","p2 c_cut2");
		}
		if(url.indexOf("user/complaint_retailer") !=-1 ){
			$("#platform_retailerComplait").attr("class","p2 c_cut2");
		}
		if(url.indexOf("supplierRegionSettings") !=-1 ){
			$("#platform_supplierRegionSettings").attr("class","p2 c_cut2");
		}
		if(url.indexOf("supplierRegionsxfg") !=-1 ){
			$("#platform_supplierRegionSettingsxfg").attr("class","p2 c_cut2");
		}
		if(url.indexOf("infrastructure/comDiv") !=-1 ){
			$("#platform_suppercomDiv").attr("class","p2 c_cut2");
		}
		if(url.indexOf("infrastructure/oneDiv") !=-1 ){
			$("#platform_supperOneDiv").attr("class","p2 c_cut2");
		}
		if(url.indexOf("cashDividendRatio") !=-1 ){
			$("#platform_oneDividendRatio").attr("class","p2 c_cut2");
		}
		if(url.indexOf("cmaxDividendRatio2") !=-1 ){
			$("#platform_cmaxDividendRatio2").attr("class","p2 c_cut2");
		}
		if(url.indexOf("infrastructure/homeNumbersSet") !=-1 ){
			$("#platform_homeNumbersSet").attr("class","p2 c_cut2");
		}
		if(url.indexOf("oneFhCycle") !=-1 ){
			$("#platform_oneFhCycle").attr("class","p2 c_cut2");
		}
		if(url.indexOf("memberStarSettings") !=-1 ){
			$("#platform_memberStarSettings").attr("class","p2 c_cut2");
		}
		if(url.indexOf("infrastructure/memDiv") !=-1 ){
			$("#platform_memDiv").attr("class","p2 c_cut2");
		}
		if(url.indexOf("cashierSettings") !=-1 ){
			$("#platform_cashierSettings").attr("class","p2 c_cut2");
		}
		if(url.indexOf("supplierRepaymentSettings") !=-1 ){
			$("#platform_supplierRepaymentSettings").attr("class","p2 c_cut2");
		}
		if(url.indexOf("platformStatManage") !=-1 ){
			$("#platform_platformStatManage").attr("class","p2 c_cut2");
		}
		if(url.indexOf("tradefee") !=-1 ){
			$("#platform_tradefee").attr("class","p2 c_cut2");
		}
		if(url.indexOf("virementSetting") !=-1 ){
			$("#platform_virementSetting").attr("class","p2 c_cut2");
		}
		
		if(url.indexOf("hqqStatManage") !=-1 ){
			$("#platform_hqqStatManage").attr("class","p2 c_cut2");
		}
		if(url.indexOf("companyStatManage") !=-1 ){
			$("#platform_companyStatManage").attr("class","p2 c_cut2");
		}
		
		if(url.indexOf("homeCompanyManage1") !=-1 ){
			$("#platform_homeCompanyManage1").attr("class","p2 c_cut2");
		}
		if(url.indexOf("complaint_selecttags") !=-1 ){
			$("#platform_complaint_selecttags").attr("class","p2 c_cut2");
		}
		
		if(url.indexOf("buyLimitTags") !=-1 ){
			$("#platform_buyLimitTags").attr("class","p2 c_cut2");
		}
		if(url.indexOf("oneDivzj") !=-1 ){
			$("#platform_oneDivzj").attr("class","p2 c_cut2");
		}
		if(url.indexOf("zjTagsSettings") !=-1 ){
			$("#platform_zjTagsSettings").attr("class","p2 c_cut2");
		}
		if(url.indexOf("listsyslog") !=-1 ){
			$("#platform_listsyslog").attr("class","p2 c_cut2");
		}
		if(url.indexOf("setStartupPageList") !=-1 ){
			$("#platform_setStartupPageList").attr("class","p2 c_cut2");
		}
		 
	});
	 </script> 
</div>