<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>UNICORN-查看商品</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" href="${path }/commons/css/shang.css">
	<link rel="stylesheet" href="${path }/commons/js/uploadify/uploadify.css">
	
	
	<script type="text/javascript" src="${path }/commons/js/uploadify/jquery.uploadify.js"></script>
	<script type="text/javascript" src="${path }/commons/js/uploadify/queue.js"></script>
	
	<script type="text/javascript" src="${path }/commons/js/swfUploadEventHandler.js"></script>
	<script type="text/javascript" src="${path }/commons/js/shangtoShow.js"></script>
</head>
<body>
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
	
<div class="center">
 <div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
		  </div>
		<div class="right f_l">
	<!-- 边框start -->
<!-- 边框start -->
	<form method="post" action="${path }/product/editProduct"
		id="productAction" enctype="multipart/form-data">
		<div class="right f_l">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品管理&nbsp;&gt;&nbsp;</p>
					<p>商品列表&nbsp;&gt;&nbsp;</p>
					<p class="c1">查看商品</p>
					<div class="clear"></div>
				</div>
			</div>
			<div class="blank10"></div>

			<!-- 边框start -->
			<div class="border">
				<div class="blank"></div>
				<div class="cont">
					<!-- 填写基本信息  -->
					<div class="i_box">
						<h2>填写基本信息</h2>
						<div class="app">
						<span>你当前所在的类目是：</span>
						<c:forEach items="${cateNames}"  var="cateName" varStatus="var">
							<span>${fn:escapeXml(cateName.pubNameCn)}</span>
						<c:if test="${!var.last}">
							<span>&gt;</span>
						</c:if>
						</c:forEach>
						</div>
						<div class="blank15"></div>
						<div class="p_box">
							<p class="p1">
								<i class="c_red">*</i> 商品标题：
							</p>
							<p class="p2">
								<input type="text" required="required" name="pname"  readonly="readonly"
									value="${fn:escapeXml(proObj.dealerProductBase.productname)}"  id="productinfo"> 
							</p>
							<div class="blank10"></div>
							<p class="p1">产品属性：</p>
							<div class="p3">
								   <p class="blank10"></p>
								   <p class="p1">
								      <i class="c_red">*</i> 主品牌：
							       </p>
							       <p class="t2">
								       <input type="text" value="${fn:escapeXml(proObj.brand.nameCn)}" readonly="readonly">
									</p>
									<p class="blank10"></p>
									<c:if test="${!empty proObj.subBrand.nameCn}">
										<p class="p1">
										      <i class="c_red">*</i> 子品牌：
									    </p>
								        <p class="t2">
								           <input type="text" value="${fn:escapeXml(proObj.subBrand.nameCn)}" readonly="readonly">
										</p>
									</c:if>
                                     <p class="blank10"></p>									
									<c:forEach items="${proObj.dealerProductAttrDTOs}" var="dealerProductAttrDTO" varStatus="vs">
										<c:if test="${dealerProductAttrDTO.dealerProductAttr.saleAttr != 1 && dealerProductAttrDTO.dealerProductAttr.buyAttr != 1 }">
											<p class="p1" title="${fn:escapeXml(dealerProductAttrDTO.dealerProductAttr.attrNameCn)}">
												<c:if test="${dealerProductAttrDTO.dealerProductAttr.isneed == 1}">
													<i class="c_red">*</i>
												</c:if>
												${fn:escapeXml(dealerProductAttrDTO.dealerProductAttr.attrNameCn)}：
											</p>
											
											<p class="t2">
										          </p>
													<c:if
														test="${dealerProductAttrDTO.dealerProductAttr.type == '1'}">
														<p style="margin-top: 5px;">
															<c:forEach
																items="${dealerProductAttrDTO.dealerProductAttrvals}"
																var="proAttrVal" varStatus="cbs">
																<span class="r2"><input type="checkbox"  readonly="readonly" disabled="disabled"
																	name="dealerProductAttrDTOs[${vs.index}].dealerProductAttrvals[${cbs.index}].lineAttrvalNameCn"
																	value="${fn:escapeXml(proAttrVal.lineAttrvalNameCn)}"
																	<c:if test="${proAttrVal.isProdAttr }">checked</c:if> />
																${fn:escapeXml(proAttrVal.lineAttrvalNameCn)}</span>

															</c:forEach>
														</p>
													</c:if>

												
													<c:if test="${dealerProductAttrDTO.dealerProductAttr.type =='2'}">
														<p class="s_1">
															<select name="dealerProductAttrDTOs[${vs.index}].dealerProductAttrvals[0].lineAttrvalNameCn"  disabled="disabled"  style="width:262px; height:25px; line-height:25px;">
																<c:forEach items="${dealerProductAttrDTO.dealerProductAttrvals}" var="dealerProductAttrval">
																		<option 
																			<c:if test="${dealerProductAttrval.isProdAttr==true }">selected="selected"</c:if> value="${fn:escapeXml(dealerProductAttrval.lineAttrvalNameCn)}">
																			${fn:escapeXml(dealerProductAttrval.lineAttrvalNameCn)}
																		</option>
																</c:forEach>
															</select>
														</p> 
													</c:if>
													<c:if test="${dealerProductAttrDTO.dealerProductAttr.type == '3'}">
													
													<p class="t2">
															<input type="text"  readonly="readonly"  name="dealerProductAttrDTOs[${vs.index}].dealerProductAttrvals[0].lineAttrvalNameCn" 
															value='<c:if test="${dealerProductAttrDTO.dealerProductAttrvals[0].isProdAttr }">${fn:escapeXml(dealerProductAttrDTO.dealerProductAttrvals[0].lineAttrvalNameCn)}</c:if>'>
													</p>
													</c:if>
											<p class="blank10"></p>
											</c:if>
									</c:forEach>
									<p class="p1">
										<i class="c_red">*</i> 原产国：
									</p>
									<p class="t2">
										<input readonly="readonly" required="required" 
											type="text" value="${fn:escapeXml(proObj.dealerProduct.originplaceCName)}">
									</p>
									<p class="blank10"></p>
									<p class="p1">
										<i class="c_red">*</i> 制造商：
									</p>
									<p class="t2">
										<input  required="required" readonly="readonly"
											type="text"
											value="${fn:escapeXml(proObj.dealerProduct.manufacturers)}"> 
									</p>
									<p class="blank10"></p>
									
										<p class="p1">
										<i class="c_red">*</i>现货预计到货天数：
									</p>
									<p class="t2">
										<input 	type="text" disabled="disabled"
											value="${fn:escapeXml(proObj.dealerProductDetail.spotArrvalDays)}"> 天
									</p>
									<p class="blank10"></p>
									
										<p class="p1">
										<i class="c_red">*</i> 期货预计到货天数：
									</p>
									<p class="t2">
										<input  disabled="disabled"
											type="text" 
											value="${fn:escapeXml(proObj.dealerProductDetail.futuresArrvalDays)}"> 天
									</p>
									<p class="blank10"></p>
									
									<c:if test="${proObj.dealerProductDetail.sheilLife>0}">
									<p class="p1">保质期：</p>
									<p class="t2">

										<input readonly="readonly" value="${fn:escapeXml(proObj.dealerProductDetail.sheilLife)}<c:if test="${proObj.dealerProductDetail.sheilLifeType==2}">年</c:if><c:if test="${proObj.dealerProductDetail.sheilLifeType==1}">月</c:if><c:if test="${proObj.dealerProductDetail.sheilLifeType==0}">天</c:if>"> 

									</p>
									</c:if>
								<p class="blank10"></p>
								<p class="p1">
									 <i class="c_red">*</i> 商品ALT：
									 </p>
									<p class="t2">
										<input required="required" readonly="readonly"	type="text"
										value="${fn:escapeXml(proObj.dealerProduct.imageAlt)}"	 />
									</p>
									<p class="blank10"></p>
									<p class="p1"> 
										<i class="c_red">*</i> title：
									</p>
									<p class="t2">
										<input required="required" readonly="readonly"	type="text" value="${fn:escapeXml(proObj.dealerProductDetail.prodB2btitle)}"	/>
									</p>
									
									<p class="blank10"></p>
									<p class="p1">搜索关键词：</p>
									<p class="t2">
										<input type="text" 
										value="${fn:escapeXml(proObj.dealerProductDetail.searchKeywords)}"	/>
									</p>
									
									<p class="blank10"></p>
									<p class="p1">description：</p>
									<p class="t2">
										<input   type="text" readonly="readonly" value="${fn:escapeXml(proObj.dealerProductDetail.searchDescription)}"	/>
									</p>
									
									<p class="blank10"></p>
									<p class="p1">广告语：</p>
									<p class="t2">
										<input   type="text" readonly="readonly" value="${fn:escapeXml(proObj.dealerProductDetail.b2bPromotionTitle)}"	/>
									</p>
								<p class="blank10"></p>	
								</div>
							</div>
						</div>
						<div class="p3">
						
					 <div  class="z" id="zizhi">
								
								<div class='wenzi'>证明资质的图片:</div>
								<ul id="00_img">
								<li>
								<div class="p-img"></div>
								</li>
								<li>
								<div class="p-img"></div>
								</li>
								<li>
								<div class="p-img"></div>
								</li>
								<li>
								<div class="p-img"></div>
								</li>
								<li>
								<div class="p-img"></div>
								</li>
								<li>
									<div class="p-img"></div>
								</li>
								
								</ul>
							</div>
						
						<div class="p3">
						
						
						
						<div class="blank15"></div>
							
						
						
						
						<!-- 填写基本信息  -->
						<div class="jinben">
							<div class="chanp">
								<p>产品规格:</p>
							</div>
							<div class="p3">
							<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
								<c:if test="${attr.dealerProductAttr.buyAttr == 1}">
									<!-- 购买属性 -->
									<div class="yanse">
										<div class="yanse1" title="${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}">
											<span>*</span>${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}： 
										</div>
										<div class="yanse2">
											<p>
												<c:forEach items="${attr.dealerProductAttrvals}" var="av" varStatus="vars">
													<span title="${fn:escapeXml(av.lineAttrvalNameCn)}">
														<input type="checkbox"  disabled="disabled"  id="${vars.index}" value="${fn:escapeXml(av.lineAttrvalNameCn)}"  isnew="${av.istate}"	
															<c:if test="${av.isProdAttr}">checked</c:if>>
															${fn:escapeXml(av.lineAttrvalNameCn)}
													</span>
												</c:forEach>
											</p>
										</div>
									</div>
									<br>
								</c:if>

								<c:if test="${attr.dealerProductAttr.saleAttr==1}">
									<!-- 规格属性 -->
									<div class="chim">
										<div class="chim1" title="${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}">
											<span>*</span> ${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}： 
										</div>
										<div class="chim2">
											<p>
												<c:forEach items="${attr.dealerProductAttrvals}" var="av" varStatus="vs">
													<span title="${fn:escapeXml(av.lineAttrvalNameCn)}">
														<input type="checkbox" disabled="disabled"  isnew="${av.istate}"	 <c:if test="${av.isProdAttr==true }"> checked="checked" </c:if> id='${vs.index}'  value="${fn:escapeXml(av.lineAttrvalNameCn)}">
														${fn:escapeXml(av.lineAttrvalNameCn)}
													</span>
												</c:forEach>
											</p>
										</div>
									</div>
								</c:if>
							</c:forEach>
							</div>
							<div class="z">
								<div id="d"></div>
							</div>

						</div>
					</div>
					<div class="blank"></div>
					
						
					<!-- 填写基本信息  -->
				
					<div class="i_box">
						<h2>产品销售信息</h2>
						<div class="blank5"></div>
						<div class="p_box">
							<div class="blank20"></div>
							<p class="p1">交易信息：</p>
							<div class="p3">
								<p class="blank5"></p>
								<p class="t1"><i class="c_red">*</i>计量单位：</p>
								<input name="dealerProductPackage.packageid" type="hidden" value="${fn:escapeXml(proObj.dealerProductPackage.packageid)}"></input>
								
								<p class="s_1">
								<select name="dealerProductPackage.measureid">
									<c:forEach items="${measure}" var="measure">
										<option value="${fn:escapeXml(measure.measureid)}" <c:if test="${proObj.dealerProductPackage.measureid == measure.measureid}">selected</c:if> >${fn:escapeXml(measure.cname)}</option>
									</c:forEach>
								</select></p>
								
								
								<p class="blank5"></p>
								
								<p class="t1"><i class="c_red">*</i>预付比例：</p>
								<input name="dealerProductSaleSetting.retaiPrePayPercent" id="retaiPrePayPercent" type="text" value="${fn:escapeXml(proObj.dealerProductSaleSetting.retaiPrePayPercent)}">%<br>
								<span class="dpl-tip-inline-warning"></span>
								
								<p class="blank5"></p>
								<p class="t1"><i class="c_red">*</i> 价格类型：</p>
								<div class="select-quote" style="margin-left:120px" id="priceTypes">
									<!-- onclick="javascript:$('#priceType1text').attr('disabled','disabled')" -->
									<input type="radio"  name="priceType" value="0"  disabled="disabled"  <c:if test="${proObj.dealerProductDetail.priceType==0}">checked</c:if>  id="priceType"/>
									
									<strong>FOB价格</strong>&nbsp;&nbsp;离岸港口名称<input disabled="disabled" id="priceType0text" type="text" name="fobPort"  <c:if test="${proObj.dealerProductDetail.priceType==0}">value="${proObj.dealerProductDetail.portName}"</c:if> >
									<span id="priceType0warning" class="dpl-tip-inline-warning"></span> 
									<div class="clear"></div>
									<input type="radio" name="priceType" disabled="disabled" value="1" <c:if test="${proObj.dealerProductDetail.priceType==1}">checked</c:if> id="priceType"/>
									<strong>CIF价格</strong>&nbsp;&nbsp;&nbsp;到岸港口名称<input id="priceType1text" type="text" name="cifPort" disabled="disabled"   <c:if test="${proObj.dealerProductDetail.priceType==1}">value="${proObj.dealerProductDetail.portName}"</c:if>>
									<span id="priceType1warning" class="dpl-tip-inline-warning"></span> 
									<div class="clear"></div>
									<input type="radio"  name="priceType" disabled="disabled" value="2" <c:if test="${proObj.dealerProductDetail.priceType==2}">checked</c:if> id="priceType"/>
									<strong>EXW价格</strong>
								</div>
								<p class="blank5"></p>
								<p class="t1">
									<i class="c_red">*</i>报价：</p>
								<div class="select-quote">
									<input type="radio" id="pic_count" disabled="disabled" name="cost" class="cp1" value="1" checked="checked"/>
									<strong>按产品数量报价</strong>
									<input type="radio" id="pic_sku" disabled="disabled" name="cost" value="2" class="cp2"/>
									<strong>按产品规格报价</strong>
								</div>
								<div class="blank10"></div>
								<div class="tqz" style="height:auto;">
									<div class="tq2">
										<h5>供应商价格:</h5>
										<p class="pp">
											<%-- <span class="b21">
												建议零售价：
											    <input type="text" name="onlyPrice" id="onlyPrice" style="width:69px; height:22px; border:1px solid #c8c8c8;" value="${fn:escapeXml(proObj.dealerProductSkuDTOs[0].dealerProductPriceMap.retailPrice)}">
												<span class="dpl-tip-inline-warning"></span>
											</span> --%>
											<span class="b2">
												起批量：
												<input type="text" disabled="disabled" id="startNum">
												<span class="danwei">
													${proObj.dealerProductPackage.measureCname}
												</span>及以上
												<input type="text"  name=''>
												元/<span class="danwei">${proObj.dealerProductPackage.measureCname}</span>
											</span>
									</div>
									
									<!-- 新增开始 -->
									<div class="tq2" style="border-bottom:1px solid #eeeeee;">
										<h5>现货价格:</h5>
										<p class="pp">
											<span class="b21">
												建议零售价：
											    <input type="text" name="onlyPrice" id="onlyPrice" style="width:69px; height:22px; border:1px solid #c8c8c8;" disabled="disabled" value="${fn:escapeXml(proObj.dealerProductSkuDTOs[0].dealerProductPriceMap.retailPrice)}">
												<span class="dpl-tip-inline-warning"></span>
											</span>
											<span class="b2">
												起批量：
												<input type="text"  name='start'  id="startNum" class="start">
												<span class="danwei">
													${proObj.dealerProductPackage.measureCname}
												</span>及以上
												<input type="text"  name='pic'  class="pic">
												元/<span class="danwei">${proObj.dealerProductPackage.measureCname}</span>
											</span>
											
										</p>
										<span class="dpl-tip-inline-warning" id="startNumWarning">
										</span>
										<span class="dpl-tip-inline-warning" id="inputwarning">
										</span>
										<span class="b3">
										
										</span>
									</div>
									
									<div class="tq2">
										<h5>期货价格:</h5>
										<p class="pp">
											<%-- <span class="b21">
												建议零售价：
											    <input type="text" name="" id="" style="width:69px; height:22px; border:1px solid #c8c8c8;" value="${fn:escapeXml(proObj.dealerProductSkuDTOs[0].dealerProductPriceMap.retailPrice)}">
												<span class="dpl-tip-inline-warning"></span>
											</span> --%>
											<span class="b2">
												起批量：
												<input type="text"  name='supplierStart'  id="startNum"  class="start">
												<span class="danwei">
													${proObj.dealerProductPackage.measureCname}
												</span>及以上
												<input type="text"  name='supplierPrice'  class="pic">
												元/<span class="danwei">${proObj.dealerProductPackage.measureCname}</span>
											</span>
											
										</p>
										<span class="dpl-tip-inline-warning" id="startNumWarning">
										</span>
										<span class="dpl-tip-inline-warning" id="inputwarning">
										</span>
										<span class="b3">
										
										</span>
									</div>
									<!-- 新增结束-->
									
									
									
									<div class="g">
										<p class="blank"></p>
										<div class="tab_box">
										    <h4>供应商价格:</h4>
											<i class="c_red">*</i>最小起订量：
											<input type="text" id="minNum" name="" disabled="disabled" style="width:150px; height:20px; line-height:20px; border:1px solid #c7c7c7;"  value="${fn:escapeXml(proObj.dealerProductSaleSetting.minWholesaleQty)}">
											<table id="tb-speca-quotation1" border="0" cellpadding="0"
												cellspacing="0" style="margin-top:10px;">
												<colgroup>
													<col class="color">
													<col class="size">
													<col class="price">
													
												</colgroup>
												<thead>
													<tr>
														<th>
														<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
															<c:if test="${attr.dealerProductAttr.buyAttr == 1}">
																${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}
															</c:if>
														</c:forEach>
														</th>
														<th>
														<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
															<c:if test="${attr.dealerProductAttr.saleAttr == 1}">
																${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}
															</c:if>
														</c:forEach>
														</th>			
														<th>
															<span class="c_red"> *</span>
															单价（元）<br/>
															<!-- <input id="same_price" type="hidden"> -->
														</th>
											
													</tr>
												</thead>
											</table>
											
										</div>
										<span class="dpl-tip-inline-warning" id="g1"></span>
										<span class="dpl-tip-inline-warning" id="g2"></span>
										<span class="dpl-tip-inline-warning" id="g3"></span>
										<span class="dpl-tip-inline-warning" id="g4">建议零售价需要大于批发价</span>
										<!-- 表格 end -->
										<p class="blank15"></p>
										
										
									  <!-- 新增表格开始 -->
										<div class="tab_box">
											<h4>现货价格:</h4>
											<i class="c_red">*</i>最小起订量：
											<input type="text" id="minNum" disabled="disabled" style="width:150px; height:20px; line-height:20px; border:1px solid #c7c7c7;"  value="${fn:escapeXml(proObj.dealerProductSaleSetting.minWholesaleQty)}">
											<table id="tb-speca-quotation2" border="0" cellpadding="0" cellspacing="0" style="margin-top:10px;">
												<colgroup>
													<col class="color">
													<col class="size">
													<col class="price">
													<col class="operate">
												</colgroup>
												<thead>
													<tr>
														<th>
														<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
															<c:if test="${attr.dealerProductAttr.buyAttr == 1}">
																${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}
															</c:if>
														</c:forEach>
														</th>
														<th>
														<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
															<c:if test="${attr.dealerProductAttr.saleAttr == 1}">
																${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}
															</c:if>
														</c:forEach>
														</th>			
														<th>
															<span class="c_red"> *</span>
															单价（元）<br/>
															<!-- <input id="same_price" type="hidden"> -->
														</th>
														<th>
															<span class="c_red"> *</span>
															建议零售价
														</th>
													</tr>
												</thead>
											</table>
											
									   </div>
									   
									   <p class="blank15"></p>
									   <div class="tab_box">
									   		<h4>期货价格:</h4>
											<i class="c_red">*</i>最小起订量：
											<input type="text" disabled="disabled" style="width:150px; height:20px; line-height:20px; border:1px solid #c7c7c7;"  value="${fn:escapeXml(proObj.dealerProductSaleSetting.minSupplierQty)}">
											<table id="tb-speca-quotation3" border="0" cellpadding="0"
												cellspacing="0" style="margin-top:10px;">
												<colgroup>
													<col class="color">
													<col class="size">
													<col class="price">
												</colgroup>
												<thead>
													<tr>
														<th>
														<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
															<c:if test="${attr.dealerProductAttr.buyAttr == 1}">
																${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}
															</c:if>
														</c:forEach>
														</th>
														<th>
														<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
															<c:if test="${attr.dealerProductAttr.saleAttr == 1}">
																${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}
															</c:if>
														</c:forEach>
														</th>			
														<th>
															<span class="c_red"> *</span>
															单价（元）<br/>
															<!-- <input id="same_price" type="hidden"> -->
														</th>
													</tr>
												</thead>
											</table>
											
									   </div>	
									  <!-- 新增表格结束 -->
									  
									  
									  
									</div>
									<p class="blank10"></p>
								</div>
								
								
								
								
							<p class="blank5"></p>
								<p class="t1">
								<i class="c_red">*</i> 订单收集类型：</p>
								<div class="select-quote">
								<input type="radio"   name="supplierProductDetail.orderType" <c:if test="${proObj.dealerProductDetail.orderType==1}">checked</c:if>  value="1" disabled="disabled"  onchange="changeOrderType('1')"/>
								<strong>现货库存</strong>
								
								<input type="radio"  name="supplierProductDetail.orderType" <c:if test="${proObj.dealerProductDetail.orderType==0}">checked</c:if> value="0" disabled="disabled" onchange="changeOrderType('2')"/>
								<strong>收集订单</strong>
								</div>
						<p class="blank15"></p>
						<p class="t1"><i class="c_red">*</i>预计发货日期：</p>
						<p class="s_1">
							<input type="text" disabled="disabled"  value="<fmt:formatDate value="${proObj.dealerProductDetail.deliverDate}" pattern="yyyy-MM-dd"/>"
									readonly="readonly" id="delivery" onClick="WdatePicker({minDate:'%y-%M-%d'})">
							<span id="deliveryDateWarning" class="dpl-tip-inline-warning"></span>	
							</p>
						<div id="collection"  <c:if test="${proObj.dealerProductDetail.orderType!=0}">style="display:none"</c:if>>
							<p class="blank15"></p>
							<p class="t1"><i class="c_red">*</i>生产能力：</p>
							<p class="s_3">
								<input id="produceNum" disabled="disabled"  type="text" value="${proObj.dealerProductDetail.produceNum}"/><span class="danwei">${fn:escapeXml(proObj.dealerProductPackage.measureCname)}</span>
								<select  disabled="disabled" >
										<option value="0" <c:if test="${proObj.dealerProductDetail.produceType==0}">selected</c:if>>天</option>
										<option value="4" <c:if test="${proObj.dealerProductDetail.produceType==4}">selected</c:if>>周</option>
										<option value="1" <c:if test="${proObj.dealerProductDetail.produceType==1}">selected</c:if>>月</option>
										<option value="2" <c:if test="${proObj.dealerProductDetail.produceType==2}">selected</c:if>>年</option>
								</select>
									<span id="produceNumWarning" class="dpl-tip-inline-warning"></span> 
									<br>
							</p>
																<p class="blank15"></p>
								<p class="t1"><i class="c_red">*</i>最后收单日期：</p>
								<p class="s_1">
								<input type="text"  disabled="disabled"
									readonly="readonly" id="closing" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'delivery\')}',minDate:'%y-%M-%d'})"
									value="<fmt:formatDate value="${proObj.dealerProductDetail.receiveDate}" pattern="yyyy-MM-dd"/>"
									>
										<span id="closingWarning" class="dpl-tip-inline-warning"></span> 
									
							</p>
							<span id="orderDate" class="dpl-tip-inline-warning"></span> 
								<p class="blank15"></p>
								<p class="t1"><i class="c_red">*</i>最大收单量：</p>
								<p class="s_1">
									<input  id="maxProdNum" type="text"  disabled="disabled" value="${proObj.dealerProductDetail.maxProdNum}">
								<span id="maxProdNumWarning" class="dpl-tip-inline-warning"></span>
								</p>
							 
							<p class="blank15"></p>
							
						</div>

							</div>
						
						
							
						<div class="blank20"></div>
							<p class="p1">条形码信息：</p>
							<div class="p3">							
								<p class="blank5"></p>
								<div class="tab_box" style="margin-left:60px;" id="skuCodeTable">
					
									
									<table  cellpadding="0" border="0" style="margin-top:10px;" id="tb-tiaoxingma">
										<colgroup>
					            			<col class="color">
					                       	<col class="size">
											<col class="price">
											<col class="weight">
											<!-- <col class="operate">	 -->	
										</colgroup>
										<thead>
											<tr >
												<th>
													<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
														<c:if test="${attr.dealerProductAttr.buyAttr == 1}">
															${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}
														</c:if>
													</c:forEach>
													</th>
													<th>
													<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
														<c:if test="${attr.dealerProductAttr.saleAttr == 1}">
															${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}
														</c:if>
													</c:forEach>
												</th>
											<th><span class="c_red"> *</span>条形码<br />
											</th>
											<th><span class="c_red"> *</span>条形码图片<br />
											</th>
											<th><span class="c_red"> *</span>sku重量<br />
											</th>
											</tr>
										</thead>
									    <tbody>
										</tbody>
									    
									</table>
									
								<span class="dpl-tip-inline-warning"></span>
								</div>
								<!-- 表格 end -->

								
								
							
							<p class="blank15"></p>
					
						
					</div>

								<div class="mingxi">
								<div class="m1">商品明细：</div>
								<div class="mm">
									<p class="m2">
										<span><i class="c_red">*</i>包装清单：</span>
										<textarea disabled="disabled"  required="required" name="dealerProductDetail.packingList" id="packinglist">${fn:escapeXml(proObj.dealerProductDetail.packingList)}</textarea>
									</p>
									<p class="m6">
										<span><i class="c_red">*</i>物流描述：</span>
										<textarea disabled="disabled"  required="required" name="dealerProductDetail.logisticsDescription" id="logisticsDescription">${fn:escapeXml(proObj.dealerProductDetail.logisticsDescription)}</textarea>
									</p>
									<span class="dpl-tip-inline-warning"></span>
									<p class="m3">
										<span><i class="c_red"></i>售后服务：</span>
										<textarea disabled="disabled"  name="dealerProductDetail.salesServiceDescription"  id="salesServices">${fn:escapeXml(proObj.dealerProductDetail.salesServiceDescription)}</textarea>
									</p>
									<p class="m4">
										<span class="s1"><i class="c_red"></i>售后电话：</span>
										<input id="servicephone" type="text" disabled="disabled"  name="dealerProductDetail.salesCalls" value="${fn:escapeXml(proObj.dealerProductDetail.salesCalls)}" />
									</p>
									<p class="m3">
										<span>
											备注：
										</span>
										<textarea id="remark" disabled="disabled">${fn:escapeXml(proObj.dealerProduct.remark)}</textarea>
									</p>
									
								</div>
							</div>
							</div>

					</div>
						<!-- 文本编辑框 -->

						<div class="blank30"></div>
						<div class="i_box">
							<h2>图文详情</h2>
							<div class="blank20"></div>
							<!--style给定宽度可以影响编辑器的最终宽度-->
							<div style="max-width:820px; height:auto; overflow:auto;">
								<c:catch var="catchAtach">
									<c:import url="${attch}" charEncoding="utf-8"></c:import>
								</c:catch>
							</div>
						</div>
						<!-- 文本编辑框结束 -->
						<div class="blank"></div>
						<!-- 填写基本信息  -->

						<div class="blank30"></div>
					</div>
					<!-- 内容 end -->
				</div>
				<!-- 边框 end -->
				<div class="blank10"></div>
				<div class="btn_box">
					<p class="clear"></p>
				</div>
			</div>
			<div class="clear"></div>
			<p class="blank30"></p>
		</div>
	</form>
 </div>
 </div>
 
	<div class="blank10"></div>	
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	
	<!-- 底部 end -->
	<!-- 底部 end -->
	<script type="text/javascript">
	$(document).ready(function(){
		var url_array;
		if('${jsonImg}'!=""){
			url_array = ${jsonImg};
			$.each(url_array,function(n,value){
				adduploadimg(value[1],value[2],value[3]); 
			});
		}
		
		var qualification =  ${qualificationUrl};
		inituploadzizhi(qualification);
		
		
		
		if('${jsonProductPic}'!=''){
		var sku_array= ${jsonSupplierProductPic};
		sku_array.push(${jsonProductPic}[0]);
		sku_array.push(${jsonCollectionProductPic}[0]);
		
		var qipiname =["","start","supplierStart"],qipivaluename=["","pic","supplierPrice"];
		
		var measure = $("select[name='dealerProductPackage.measureid'] option:selected").text();
		changebox();
 	 	$.each(sku_array,function(keys,values){
 	 		
 	 			var statu = values.type;
 	 			
 	 			   if(statu=='0'){
 	 			  
	 	 			  
	 	 			  $("#pic_count").attr("checked","checked");
	 	 			  $('.pp:eq('+keys+') span.b2').remove();
	 	 			  $.each(values.start,function(key,value){
	 	 			  var startLength = values.start.length;
	 	 			    	$('.pp:eq('+keys+')').append( "<span class='b2'>起批量：<input type='text' disabled='disabled' value='"+value+"'><span class='danwei'>"+measure+"</span>及以上 <input  type='text' disabled='disabled' value='"+values.pic[key]+"'> 元/<span class='danwei'>"+measure+"</span></span>" );
		 	 			 	var length = $(".pp:eq("+keys+") .b2").length;
								if (length>=3){
									$(".pp:eq("+keys+") .b3").hide();
								}
							$(".pp:eq("+keys+") .g").hide();
					
	 	 			  });
 
 	 				}else{
 	 					
 	 			 		var pku = values.pic;
 	 			 	
	 	 			 	$("#pic_sku").attr("checked","checked");
	 	 			 	$(".pp:eq("+keys+") .g").show();
	 	 			 	$(".pp:eq("+keys+") .tq2").hide();
	 	 				var pku_array = new Array();
	 	 				$.each(pku,function(key,value){
	 	 					pku_array.push(value);
	 	 					var new_array = new Array();
	 	 					new_array.push(pku_array);
	 	 					var ii = keys+1;
	 	 					_fShowTableInfo(pku,"tb-speca-quotation"+ii); 
	 	 				});
 	 			 }; 
 	 		});
 	 		checkRadio();
 	 		}
	 	 		var skuCode = ${showbarCodeJson};
				var skuCode_array = new Array();
				$.each(skuCode,function(key,value){
					skuCode_array.push(value);
					var new_array = new Array();
					new_array.push(skuCode_array);
					_fShowTableInfo(skuCode,"tb-tiaoxingma"); 
				});
		});  
	
	var orderType=$('input:radio[name="supplierProductDetail.orderType"]:checked').val();
	if(orderType==0){
		$("#collection").css('display','block'); 
	}
	function changeOrderType(type) {
		if (type == 2) {
			$("#collection").show();			
		}else{
			$("#collection").hide();			
		}
	};
	
	</script>
</body>
</html>