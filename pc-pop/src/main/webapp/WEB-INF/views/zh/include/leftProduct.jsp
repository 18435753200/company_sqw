<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
   $(document).ready(function(){
		var url='${url}';
// 		alert(url);
		if(url.indexOf("onSaleList")!=-1||url.indexOf("showProduct")!=-1||url.indexOf("editProductUI")!=-1){
			$("#product_onSale").attr("class","p2 c_cut2");
		}
		if(url.indexOf("productlist")!=-1||url.indexOf("showProduct")!=-1||url.indexOf("editProductUI")!=-1){
			$("#product_POPonSale").attr("class","p2 c_cut2");
		}
// 		if(url.indexOf("importlist")!=-1||url.indexOf("showProduct")!=-1||url.indexOf("editProductUI")!=-1){
// 			$("#product_POPImportonSale").attr("class","p2 c_cut2");
// 		}
		if(url.indexOf("category")!=-1||url.indexOf("addProductUI")!=-1){
			$("#product_addProduct").attr("class","p2 c_cut2");
		}
		if(url.indexOf("cateXfg")!=-1||url.indexOf("UIXf")!=-1){
			$("#product_addProduct2").attr("class","p2 c_cut2");
		}
		if(url.indexOf("/draft")!=-1||url.indexOf("/product/draftList")!=-1){
			$("#product_draft").attr("class","p2 c_cut2");
		}
		if(url.indexOf("/poporder/or")!=-1){
			$("#supplier_order").attr("class","p2 c_cut2");
		}
		if(url.indexOf("limit")!=-1){
			$("#limit").attr("class","p2 c_cut2");
		}
		if(url.indexOf("logistic")!=-1){
			$("#logistic").attr("class","p2 c_cut2");
		}
		if(url.indexOf("/toImport")!=-1){
			$("#purchase_price").attr("class","p2 c_cut2");
		}
		if(url.indexOf("repertory")!=-1){
			$("#inventory").attr("class","p2 c_cut2");
		}
		if(url.indexOf("soldOut")!=-1){
			$("#supplier_order").attr("class","p2 c_cut2");
		}
		if(url.indexOf("refund_list")!=-1){
			$("#supplier_order1").attr("class","p2 c_cut2");
		}
		if(url.indexOf("brand")!=-1){
			$("#supplier_brand").attr("class","p2 c_cut2");
		}
		if(url.indexOf("store")!=-1){
			$("#supplier_store").attr("class","p2 c_cut2");
		}
		if(url.indexOf("product/orderBalance")!=-1){
			$("#supplier_bal_order").attr("class","p2 c_cut2");
		}
		if(url.indexOf("agentList")!=-1){
			$("#agentList").attr("class","p2 c_cut2");
		}
		if(url.indexOf("agentRegist")!=-1){
			$("#addAgentPage").attr("class","p2 c_cut2");
		}
		if(url.indexOf("addBusiness")!=-1){
			$("#addBusiness").attr("class","p2 c_cut2");
		}
		if(url.indexOf("qrCodeSupplier")!=-1){
			$("#qrCodeSupplier").attr("class","p2 c_cut2");
		}
		if(url.indexOf("managementModle")!=-1){
			$("#managementModle").attr("class","p2 c_cut2");
		}
		if(url.indexOf("virementRecordingList")!=-1){
			$("#virementRecordingList").attr("class","p2 c_cut2");
		}
		if(url.indexOf("companyVirement")!=-1){
			$("#companyVirement").attr("class","p2 c_cut2");
		}
		if(url.indexOf("tradingFlowList")!=-1){
			$("#getLookTradingFlow").attr("class","p2 c_cut2");
		}
		if(url.indexOf("businessList")!=-1){
			$("#listBusiness").attr("class","p2 c_cut2");
		}
	});
   
</script>
<div class="center">
		<!-- 左边 start -->
		<div class="left f_l">
			<div class="title">
				<p class="f_l"><img src="${path}/images/img_title_sm.png" alt=""></p>
				<p class="f_l p1">商家中心</p>
				<p class="clear"></p>
			</div>
			<div class="blank5"></div>
			<div class="list_box">
							<c:if test="${!empty meunMap['代理管理']}" >
					<div class="demo">
					<h2><p class="p1">
					   
	                   <img src="${path}/images/img_t1.png"/>运营商管理
					   
					   </p></h2>
					<div class="p_b">
					
				
				    
							<c:if test="${ !empty meunMap['代理列表']}" >
								<p class="p2"  id="agentList"><a href="javascript:void(0)" onclick="window.parent.location='${path}/supplier/getAgentPage'">运营商列表</a></p>
							</c:if>
				    		<c:if test="${ !empty meunMap['添加代理']}" >
				    			<c:if test="${sa.code!=1640 }">
								<p class="p2" id="addAgentPage"><a href="javascript:void(0)" onclick="window.parent.location='${path}/supplier/addAgentPage'">添加运营商</a></p>
				    			</c:if>
				    			<c:if test="${sa.code==1640 }">
								<p class="p2" id="addAgentPage"><a href="javascript:void(0)" onclick="window.parent.location='${path}/supplier/addMpAgentPage'">添加合伙人</a></p>
				    			</c:if>
							</c:if>
						<c:if test="${ !empty meunMap['商家列表']}" >
					    	<p class="p2" id="listBusiness"><a href="javascript:void(0)" onclick="window.parent.location='${path}/supplier/listBusiness'">商家列表</a></p>
					    </c:if>
					    <c:if test="${ !empty meunMap['分享收入']}" >
								<p class="p2"  id="getLookTradingFlow"><a href="javascript:void(0)" onclick="window.parent.location='${path}/supplier/getLookTradingFlow'">我的分享收入</a></p>
						</c:if>
					    
<%-- 	                 	 <c:if test="${ !empty meunMap['添加商家']}" > --%>
<%-- 							<p class="p2" id="addBusiness"><a href="javascript:void(0)"  onclick="window.parent.location='${path}/supplier/addBusiness'">添加商家</a></p> --%>
<%--  						</c:if>  --%>
<%-- 						<c:if test="${ !empty meunMap['我的二维码']}" > --%>
<%-- 							<p class="p2" id="qrCodeSupplier"><a href="javascript:void(0)"  onclick="window.parent.location='${path}/supplier/qrCodeSupplier'">我的二维码</a></p> --%>
<%-- 						</c:if>--%>
					</div>
					
				</div>
				</c:if>
			
					   <c:if test="${!empty meunMap['商品管理']}" >
				<div class="demo">
					<h2><p class="p1">
	                   <img src="${path}/images/img_t1.png"/>商品管理
					   
					   </p></h2>
					<div class="p_b">
					   <%--<c:if test="${ !empty meunMap['货品列表']}" >
	                       <p class="p2" id="product_onSale"><a href="javascript:void(0)" onclick="getProduct()">货品列表</a></p>
					   </c:if>--%>
					   
						    <c:if test="${ !empty meunMap['发布商品']}" >
								<p class="p2" id="product_addProduct"><a href="javascript:void(0)" onclick="addProduct()">发布商品</a></p>
							</c:if>
						
						    <c:if test="${ !empty meunMap['发布千品王商品']}" >
								<p class="p2" id="product_addProduct2"><a href="javascript:void(0)" onclick="addXfProduct()">发布千品王商品</a></p>
							</c:if>
						
						<c:if test="${ !empty meunMap['货品列表']}" >
					    	<p class="p2" id="product_POPonSale"><a href="javascript:void(0)" onclick="getPOPProduct()">货品列表</a></p>
					    </c:if>
<!-- 					    <p class="p2" id="product_POPImportonSale"><a href="javascript:void(0)" onclick="getPOPImportProduct()">导入货品列表</a></p> -->
					 <%-- <c:if test="${ !empty meunMap['草稿箱']}" >
	                    	<p class="p2" id="product_draft"><a href="javascript:void(0)" onclick="getDrafts()">草稿箱</a></p>
	                 </c:if>--%>
						
	                	
					<%-- 	<c:if test="${ !empty meunMap['库存管理']}" >
							<p class="p2" id="inventory"><a href="javascript:void(0)" onclick="getInventory()">库存管理</a></p>
						</c:if>
						<c:if test="${ !empty meunMap['品牌管理']}" >
							<p class="p2" id="supplier_brand"><a href="javascript:void(0)" onclick="getBrand()">品牌管理</a></p>
						</c:if> --%>
						<c:if test="${ !empty meunMap['商品列表']}" >
							<p class="p2" id="supplier_area"><a href="javascript:void(0)" onclick="getBrand()">商品列表</a></p>
						</c:if>
					</div>
				</div>
				</c:if>
				
				
					<c:if test="${ !empty meunMap['地区限购管理'] }" >
				<div class="demo">
					<h2><p class="p1">
	                      <img src="${path}/images/img_t2.png"/>地区限购管理
					   </p></h2>
					   
					<div class="p_b">
					    <c:if test="${ !empty meunMap['地区限购模板']}" >
							<p class="p2" id="limit"><a href="javascript:void(0)" onclick="limitArea()">地区限购模板</a></p>
						</c:if>
					  
					</div>
				</div>
				</c:if>
				
				
					<c:if test="${ !empty meunMap['物流费管理'] }" >
				<div class="demo">
					<h2><p class="p1">
	                      <img src="${path}/images/img_t2.png"/>物流费管理
					   </p></h2>
					   
					<div class="p_b">
					    <c:if test="${ !empty meunMap['物流管理模板']}" >
							<p class="p2" id="logistic"><a href="javascript:void(0)" onclick="window.parent.location='${path}/order/getLogisticPage'">物流管理模板</a></p>
						</c:if>
					  
					</div>
				</div>
				</c:if>
				
					<c:if test="${ !empty meunMap['订单管理'] }" >
				<div class="demo">
					<h2><p class="p1">
	                      <img src="${path}/images/img_t2.png"/>订单管理
					   </p></h2>
					   
					<div class="p_b">
					   <c:if test="${ !empty meunMap['已卖出的货品']}" >
	                   		<p class="p2" id="supplier_order"><a href="javascript:void(0)" onclick="window.parent.location='${path}/order/getOrder'">订单管理</a></p>
					   </c:if>
					   <c:if test="${!empty meunMap['结算订单']}" >
	                   		<p class="p2" id="supplier_bal_order"><a href="javascript:void(0)" onclick="goBalanceOrderListPage()">结算订单</a></p>
						</c:if>
						<c:if test="${ !empty meunMap['退款订单']}" >
	                   		<p class="p2" id="supplier_order1"><a href="javascript:void(0)" onclick="getRefundOrder()">退款订单</a></p>
					   </c:if>
						<c:if test="${ !empty meunMap['订单列表']}" >
							<p class="p2" id="supplier_area_order"><a href="javascript:void(0)" onclick="getAreaOrder()">订单列表</a></p>
						</c:if>
					</div>
				</div>
				</c:if>
					<c:if test="${ !empty meunMap['商品采购']}" >
				<div class="demo">
						<h2><p class="p1">
		                      <img src="${path}/images/img_t2.png"/>商品采购
						   </p></h2>
					    
					<div class="p_b">
						<c:if test="${ !empty meunMap['采购列表']}" >
						<p class="p2" id="purchase_price"><a href="javascript:void(0)" onclick="window.parent.location='${path}/product/toImport'">采购列表</a></p>
						</c:if>
					</div>
				</div>
				 </c:if>
					<c:if test="${ !empty meunMap['店铺管理'] }" >
				<div class="demo">
						<h2><p class="p1">
	                      <img src="${path}/images/img_t2.png"/>店铺管理
					   </p></h2>
					  
					<div class="p_b">
						<c:if test="${ !empty meunMap['模板管理']}" >
							<p class="p2" id="managementModle"><a href="javascript:void(0)" onclick="window.parent.location='${path}/store/modle/getManagementModle'">模板管理</a></p>
						</c:if>
						<c:if test="${ !empty meunMap['页面编辑']}" >
							<p class="p2" id="supplier_store" ><a href="javascript:void(0)" onclick="window.parent.location='${path}/store/initEdit'">页面编辑</a></p>
						</c:if>
						<c:if test="${ !empty meunMap['设置奖励']}" >
							<p class="p2" ><a href="javascript:void(0)" onclick="window.parent.location='${path}/store/modle/getManagementModle'">设置奖励</a></p>
						</c:if>
						<c:if test="${ !empty meunMap['配送管理']}" >
							<p class="p2" ><a href="javascript:void(0)" onclick="window.parent.location='${path}/store/modle/getManagementModle'">配送管理</a></p>
						</c:if>
						<c:if test="${ !empty meunMap['拓展二维码管理']}" >
							<p class="p2" ><a href="javascript:void(0)" onclick="window.parent.location='${path}/store/modle/getManagementModle'">拓展二维码管理</a></p>
						</c:if>
						<c:if test="${ !empty meunMap['拓展商家管理']}" >
							<p class="p2" ><a href="javascript:void(0)" onclick="window.parent.location='${path}/store/modle/getManagementModle'">拓展商家管理</a></p>
						</c:if>
					</div>
				</div>
				 </c:if>
				<%-- <c:if test="${ !empty meunMap['企业转账记录']}" >
				<div class="demo">
						<h2><p class="p1">
						  <img src="${path}/images/img_t2.png"/>企业转账记录
					   </p></h2>
					<div class="p_b">
						<c:if test="${ !empty meunMap['企业转账记录']}" >
							<p class="p2" id="virementRecordingList"><a href="javascript:void(0)" onclick="window.parent.location='${path}/recording/virementRecording'">企业转账记录</a></p>
						</c:if>
						<c:if test="${ !empty meunMap['企业与企业个人转账']}" >
							<p class="p2" id="companyVirement"><a href="javascript:void(0)" onclick="window.parent.location='${path}/company/virementSetting'">企业与企业个人转账</a></p>
						</c:if>
					</div>
				</div>
				</c:if> --%>
			</div>
		</div>
		<!-- 左边 end -->
