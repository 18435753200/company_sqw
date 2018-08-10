<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<title>UNICORN-<c:if test="${target eq '1' }">查看</c:if><c:if test="${target eq '2' }">修改</c:if>商品</title>
	
	<link rel="stylesheet" href="${path }/commons/css/shang.css">
	<link rel="stylesheet" href="${path }/commons/css/lightbox.css">
	<link rel="stylesheet" href="${path }/commons/js/uploadify/uploadify.css">
	<link rel="stylesheet" type="text/css" href="${path}/commons/js/AutoComplete/jquery.autocomplete.css">
	<script type="text/javascript" src="${path}/product/ueditor.config.js"></script>
	<script type="text/javascript" src="${path}/product/ueditor.all.js"></script>
	
	
	<script type="text/javascript" src="${path}/commons/js/uploadify/jquery.uploadify.js"></script>
	<script type="text/javascript" src="${path}/commons/js/uploadify/queue.js"></script>
	
	<script type="text/javascript" src="${path}/commons/js/swfUploadEventHandler.js"></script>
	<script type="text/javascript" src="${path}/commons/js/shang.js"></script>
	<style type="text/css">
	   .mo_div .mo_b table td input{ width:90px;}
	</style>
	
</head>
<body> 
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
	
<div class="center">
<%--  <div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
		  </div> --%>
		<div class="right f_l" style="width: 925px">
	<!-- 边框start -->
<!-- 边框start -->
	<form method="post" id="productAction" enctype="multipart/form-data">
		<input type="hidden" name="dealerProduct.prodLineId" value="${fn:escapeXml(proObj.dealerProduct.prodLineId)}">
		<input type="hidden" id="productId" name="productId" value="${fn:escapeXml(proObj.productId)}">
		<input type="hidden" name="dealerProduct.supplierid" value="${fn:escapeXml(proObj.dealerProduct.supplierid)}">
		<input type="hidden" name="dealerProduct.prodType" id="prodType" value="${fn:escapeXml(proObj.dealerProduct.prodType)}">
		<input type="hidden" name="dealerProduct.catePubId" value="${fn:escapeXml(proObj.dealerProduct.catePubId)}">
		
		<c:forEach items="${proObj.dealerProductSkuDTOs}" var="skuHide" varStatus="skui">
			<input type="hidden"  name="dealerProductSkuDTOs[${skui.index }].dealerProductSku.productSkuId" value="${fn:escapeXml(skuHide.dealerProductSku.productSkuId)}"/>
			<input type="hidden"  name="dealerProductSkuDTOs[${skui.index }].dealerProductPriceMap.prodPriceId" value="${fn:escapeXml(skuHide.dealerProductPriceMap.prodPriceId)}"/>
			<input type="hidden"  name="dealerProductSkuDTOs[${skui.index }].dealerProductPriceMap.priceId" value="${fn:escapeXml(skuHide.dealerProductPriceMap.priceId)}"/>
			<input type="hidden"  name="dealerProductSkuDTOs[${skui.index }].dealerProductPriceMap.supplierprice" value="${fn:escapeXml(skuHide.dealerProductPriceMap.supplierprice)}"/>
			<input type="hidden"  name="dealerProductSkuDTOs[${skui.index }].dealerProductSku.skuNameCn" value="${fn:escapeXml(skuHide.dealerProductSku.skuNameCn)}"/>
			<input type="hidden"  name="dealerProductSkuDTOs[${skui.index }].dealerProductSku.skuNameEn" value="${fn:escapeXml(skuHide.dealerProductSku.skuNameEn)}"/>
			<input type="hidden"  name="dealerProductSkuDTOs[${skui.index }].dealerProductSku.skuId" value="${fn:escapeXml(skuHide.dealerProductSku.skuId)}"/>
		</c:forEach>
		<div class="right f_l" style="width: 925px">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品管理&nbsp;&gt;&nbsp;</p>
					<p>商品列表&nbsp;&gt;&nbsp;</p>
					<p class="c1"><c:if test="${target eq '1' }">查看</c:if><c:if test="${target eq '2' }">修改</c:if>商品</p>
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
						<c:if test="${target eq '2' }">
							<div style="position: relative;">
								<input type="button" value="修改分类"  class="download-btn" id="toEditCategory"/>
							</div>
						</c:if>	
						<div class="app">
						<span>你当前所在的类目是：</span>
						<c:forEach items="${cateNames}"  var="cateName" varStatus="var">
							<span>${fn:escapeXml(cateName.pubNameCn)}</span>
						<c:if test="${!var.last}">
							<span>&gt;</span>
						</c:if>
						</c:forEach>
						</div>
						
					<div class="mo_div">
					<div class="mo_t">
						<ul>
							<li class="at"><a href="#">B2B商品</a></li>
							<li><a href="#">B2C商品</a></li>
						</ul>
			    	</div>
					
					<div class="mo_wrap">
						<div class="mo_box">
						<div class="p_box">
							<p class="p1">
								<i class="c_red">*</i> 商品标题：
							</p>
							<p class="p2">
								<input type="text" required="required" name="pname"
									value="${fn:escapeXml(proObj.dealerProductBase.productname)}"  id="productinfo"> 
								<span class="dpl-tip-inline-warning">请填写 商品标题</span>
							</p>
							<div class="blank10"></div>
							<p class="p1">
								<i class="c_red">*</i> 商品编号：
							</p>
							<p class="p2">
								<input type="text" disabled="disabled" value="${fn:escapeXml(proObj.dealerProduct.businessProdId)}" > 
							</p>
							<div class="blank10"></div>
							<p class="p1">产品属性：</p>
							<div class="p3" id="attrobjs">
								   <p class="blank10"></p>
								   <p class="p1">
								      <i class="c_red">*</i> 主品牌：
							       </p>
							       <p class="t2">
								       <input id="keyword" style="float: left;margin-right:5px;height:25px;" value="${fn:escapeXml(proObj.brand.nameCn)}"/>  
                                       <input id="firstcategory" name="brandId" type="hidden" />  
						 			   <select id="secondcategory" name="subBrandId"></select>
									</p>
									<p class="blank10"></p>
<!-- 								<c:if test="${!empty proObj.subBrand.nameCn}">
										<p class="p1">
										      <i class="c_red">*</i> 子品牌：
									    </p>
								        <p class="t2">
								           <input type="text" value="${fn:escapeXml(proObj.subBrand.nameCn)}" disabled="disabled">
								           <input type="hidden"  name="subBrand.nameCn" value="${fn:escapeXml(proObj.subBrand.nameCn)}"/>
										</p>
									</c:if> -->
									
									<p class="blank10"></p> 
								   <p class="p1">
								   		商品编号：
							       </p>
							       <p class="t2">
								       <input type="text" value="${fn:escapeXml(proObj.dealerProduct.businessProdId)}" readonly="readonly">
									</p>
									
                                    <p class="blank10"></p>									
									
									<c:if test="${target == '1'}">
									<c:forEach items="${simpleAttrs}" var="dealerProductAttrDTO" varStatus="vs">
										<c:if test="${dealerProductAttrDTO.dealerProductAttr.saleAttr != 1 && dealerProductAttrDTO.dealerProductAttr.buyAttr != 1 }">
											<p class="p1" title="${fn:escapeXml(dealerProductAttrDTO.dealerProductAttr.attrNameCn)}">
												<c:if test="${dealerProductAttrDTO.dealerProductAttr.isneed == 1}">
													<i class="c_red">*</i>
												</c:if>
												${fn:escapeXml(dealerProductAttrDTO.dealerProductAttr.attrNameCn)}：
												<input type="hidden" name="dealerProductAttrDTOs[${vs.index}].dealerProductAttr.attrNameCn"
												value="${fn:escapeXml(dealerProductAttrDTO.dealerProductAttr.attrNameCn)}">
												<input type="hidden" name="dealerProductAttrDTOs[${vs.index}].dealerProductAttr.type"
													value="${fn:escapeXml(dealerProductAttrDTO.dealerProductAttr.type)}">
												<input type="hidden" name="dealerProductAttrDTOs[${vs.index}].dealerProductAttr.style"
													value="${fn:escapeXml(dealerProductAttrDTO.dealerProductAttr.style)}">
												<input type="hidden" name="dealerProductAttrDTOs[${vs.index}].dealerProductAttr.isneed"
													value="${fn:escapeXml(dealerProductAttrDTO.dealerProductAttr.isneed)}">
											</p>
											<c:if test="${dealerProductAttrDTO.dealerProductAttr.type == '1'}">
												<p style="margin-top: 5px;" id="checkboxattr"
												<c:if test="${dealerProductAttrDTO.dealerProductAttr.isneed == 1}">
																	required='required'
												</c:if> >
													<c:forEach items="${dealerProductAttrDTO.dealerProductAttrvals}" var="proAttrVal" varStatus="cbs">
														<span class="r2"><input type="checkbox" name="dealerProductAttrDTOs[${vs.index}].dealerProductAttrvals[${cbs.index}].lineAttrvalNameCn"
															value="${fn:escapeXml(proAttrVal.lineAttrvalNameCn)}" 
															<c:if test="${proAttrVal.isProdAttr}">checked</c:if> 
															/>
														${fn:escapeXml(proAttrVal.lineAttrvalNameCn)}</span>
													</c:forEach>
												<span class="dpl-tip-inline-warning"></span>
												</p>
												
											</c:if>

											<c:if test="${dealerProductAttrDTO.dealerProductAttr.type =='2'}">
												<p class="s_1">
													<select name="dealerProductAttrDTOs[${vs.index}].dealerProductAttrvals[0].lineAttrvalNameCn" style="width:262px; height:25px; line-height:25px;"
													<c:if test="${dealerProductAttrDTO.dealerProductAttr.isneed == 1}">
																	required='required'
													</c:if> >
														<c:forEach items="${dealerProductAttrDTO.dealerProductAttrvals}" var="dealerProductAttrval">
															<option
																<c:if test="${dealerProductAttrval.isProdAttr==true }">selected="selected"</c:if>
																value="${fn:escapeXml(dealerProductAttrval.lineAttrvalNameCn)}">
																${fn:escapeXml(dealerProductAttrval.lineAttrvalNameCn)}
															</option>
														</c:forEach>
													</select>
												</p>
											</c:if>

											<c:if test="${dealerProductAttrDTO.dealerProductAttr.type == '3'}">
												<p class="t2">
													<c:choose>
														<c:when test="${dealerProductAttrDTO.dealerProductAttrvals[0].isProdAttr }">
															<input type="text" name="dealerProductAttrDTOs[${vs.index}].dealerProductAttrvals[0].lineAttrvalNameCn"
																<c:if test="${dealerProductAttrDTO.dealerProductAttr.isneed == 1}">
																	required='required'
																</c:if>
																value="${fn:escapeXml(dealerProductAttrDTO.dealerProductAttrvals[0].lineAttrvalNameCn)}">
														</c:when>
														<c:otherwise>
															<input type="text" name="dealerProductAttrDTOs[${vs.index}].dealerProductAttrvals[0].lineAttrvalNameCn"
															<c:if test="${dealerProductAttrDTO.dealerProductAttr.isneed == 1}">
																	required='required'
															</c:if>
															>
														</c:otherwise>
													</c:choose>
													<span class="dpl-tip-inline-warning"></span>
												</p>
											</c:if>
											<c:if test="${dealerProductAttrDTO.dealerProductAttr.type == '0'}">
												<p class="t2">
													<c:choose>
														<c:when test="${dealerProductAttrDTO.dealerProductAttrvals[0].isProdAttr }">
															<input type="text" name="dealerProductAttrDTOs[${vs.index}].dealerProductAttrvals[0].lineAttrvalNameCn"
																<c:if test="${dealerProductAttrDTO.dealerProductAttr.isneed == 1}">
																	required='required'
																</c:if>
																value="${fn:escapeXml(dealerProductAttrDTO.dealerProductAttrvals[0].lineAttrvalNameCn)}">
														</c:when>
														<c:otherwise>
															<input type="text" name="dealerProductAttrDTOs[${vs.index}].dealerProductAttrvals[0].lineAttrvalNameCn"
															<c:if test="${dealerProductAttrDTO.dealerProductAttr.isneed == 1}">
																	required='required'
															</c:if>
															>
														</c:otherwise>
													</c:choose>
													<span class="dpl-tip-inline-warning"></span>
												</p>
											</c:if>
											<p class="blank10"></p>
										</c:if>
									</c:forEach>
									</c:if>
									<c:if test="${target == '2'}">
										<div id="attrdiv">
											<input type="button" id="addTable" value="添加普通属性"/>
											<span id="tblError" class="dpl-tip-inline-warning"></span>
											<table id="tbl" style="margin-top:10px;border:1px;border-style: solid; border-color: #c3c3c3;">
											<colgroup>
											<col style="width: 35px;border:1px;border-style: solid; border-color: #c3c3c3;">
											<col style="width: 65px;border:1px;border-style: solid; border-color: #c3c3c3;">
											<col style="width: 80px;border:1px;border-style: solid; border-color: #c3c3c3;">
											<col style="width: 450px;border:1px;border-style: solid; border-color: #c3c3c3;">
											<col style="width: 50px;border:1px;border-style: solid; border-color: #c3c3c3;">
											</colgroup>
												<tr style="height: 30px;">
													<th>排序</th>
													<th>形式</th>
													<th>属性</th>
													<th>属性值</th>
													<th>操作</th>
												</tr>
												<c:forEach items="${simpleAttrs}" var="dealerProductAttrDTO" varStatus="vs">
											<tr>
												<td>
													<input type="text" id="attrOrd${vs.index}" name="attrOrd" value="${dealerProductAttrDTO.dealerProductAttr.sortval}" style="width: 30px;"/>
													<input type="hidden" name="attrRows" value="100${vs.index}"/>
												</td>
												<td>
													<select name="type100${vs.index}">
														<option value="3" <c:if test="${dealerProductAttrDTO.dealerProductAttr.type ==3 }">selected="selected"</c:if>>文本</option>
														<option value="1" <c:if test="${dealerProductAttrDTO.dealerProductAttr.type ==1 }">selected="selected"</c:if>>多选框</option>
													</select>
												</td>
												<td>
													<input type="text" id="attrNm${vs.index}" name="attrName" value="${dealerProductAttrDTO.dealerProductAttr.attrNameCn}" style='width: 80px;'/>
												</td>
												<td id="addAttrvals100${vs.index}">
													<img src="../commons/images/img_+bg.jpg" onclick="addAttrval('100${vs.index}')">
													<c:forEach items="${dealerProductAttrDTO.dealerProductAttrvals}" var="proAttrVal" varStatus="cbs">
														<input type="text" name="attrval100${vs.index}" id="attrval100${vs.index}${cbs.index}" value="${fn:escapeXml(proAttrVal.lineAttrvalNameCn)}" style="width: 100px;"/>
														<img src="../commons/images/img_23.png" id="img100${vs.index}${cbs.index}" onclick="delAttrval('100${vs.index}${cbs.index}')">
													</c:forEach>
												</td>
												<td>
													<input type="button" onclick="delAttr(this)" value="删除"/>
													<input type="hidden" class="attrLists" value="${vs.index}"/>
												</td>
											</tr>
									</c:forEach>
											</table>
										</div>
									</c:if>
									
									
									<p class="p1">
										<i class="c_red">*</i> 原产国：
									</p>
									<p class="t2">
										<input disabled="disabled" required="required" type="text" value="${fn:escapeXml(proObj.dealerProduct.originplaceCName)}">
										<input type="hidden" name="dealerProduct.originplace" value="${fn:escapeXml(proObj.dealerProduct.originplace)}">
									</p>
									<p class="blank10"></p>
									<p class="p1">
										<!-- <i class="c_red">*</i> --> 制造商：
									</p>
									<p class="t2">
										<input disabled="disabled"
											type="text"
											value="${fn:escapeXml(proObj.dealerProduct.manufacturers)}"> 
										<input type="hidden" name="dealerProduct.manufacturers" value="${fn:escapeXml(proObj.dealerProduct.manufacturers)}">
									</p>
									<p class="blank10"></p>
									
										<p class="p1">
										<i class="c_red">*</i>现货预计到货天数：
									</p>
									<p class="t2">
										<input required="required" 	type="text" name="dealerProductDetail.spotArrvalDays" id="spotArrvalDays"
											value="${fn:escapeXml(proObj.dealerProductDetail.spotArrvalDays)}">
										<span class="tian">天</span>
									</p>
									<span class="dpl-tip-inline-warning"></span>
									<p class="blank10"></p>
										<p class="p1">
										<i class="c_red">*</i> 期货预计到货天数：
									</p>
									<p class="t2">
										<input required="required" 
											type="text" name="dealerProductDetail.futuresArrvalDays" id="futuresArrvalDays"
											value="${fn:escapeXml(proObj.dealerProductDetail.futuresArrvalDays)}">
											<span class="tian">天</span>
									</p>
									<span class="dpl-tip-inline-warning"></span>
									<p class="blank10"></p>
									<p class="p1">
										海关税则代码：
									</p>
									<p class="t2">
										<input readonly="readonly" type="text" value="${fn:escapeXml(proObj.dealerProductDetail.customCode)}"> 
										<input type="hidden" name="dealerProductDetail.customCode" value="${fn:escapeXml(proObj.dealerProductDetail.customCode)}"> 
									</p>
									<p class="blank10"></p>
									
									<c:if test="${proObj.dealerProductDetail.sheilLife>0}">
									<p class="p1">保质期：</p>
									<p class="t2">

										<input disabled="disabled" value="${fn:escapeXml(proObj.dealerProductDetail.sheilLife)}<c:if test="${proObj.dealerProductDetail.sheilLifeType==2}">年</c:if><c:if test="${proObj.dealerProductDetail.sheilLifeType==1}">月</c:if><c:if test="${proObj.dealerProductDetail.sheilLifeType==0}">天</c:if>"> 

										<input name="dealerProductDetail.sheilLife" type="hidden"  value="${fn:escapeXml(proObj.dealerProductDetail.sheilLife)}">
										<input name="dealerProductDetail.sheilLifeType" type="hidden" value="${fn:escapeXml(proObj.dealerProductDetail.sheilLifeType)}">
									</p>
									</c:if>
									<p class="blank10"></p>
									<p class="p1">
									 <!-- <i class="c_red">*</i> --> 商品ALT：
									 </p>
									<p class="t2">
										<input type="text" name="dealerProduct.imageAlt"
										value="${fn:escapeXml(proObj.dealerProduct.imageAlt)}"	 />
										<span class="tian"></span>
									</p>
									<p class="blank10"></p>
									<p class="p1"> 
										<!-- <i class="c_red">*</i> --> title：
									</p>
									<p class="t2">
										<input type="text" name="dealerProductDetail.prodB2btitle" 
											value="${fn:escapeXml(proObj.dealerProductDetail.prodB2btitle)}"	/>
											<span class="tian"></span>
									</p>
									
									<p class="blank10"></p>
									<p class="p1">搜索关键词：</p>
									<p class="t2">
										<input required="sizeForCurr" type="text" name="dealerProductDetail.searchKeywords" 
											value="${fn:escapeXml(proObj.dealerProductDetail.searchKeywords)}"	/>
	 										<span class="tian"></span>
									</p>
									
									<p class="blank10"></p>
									<p class="p1">description：</p>
									<p class="t2">
										<input required="sizeForCurr" type="text" name="dealerProductDetail.searchDescription" 
											value="${fn:escapeXml(proObj.dealerProductDetail.searchDescription)}"	/>
											<span class="tian"></span>
									</p>
									
									<p class="blank10"></p>
									<p class="p1">广告语：</p>
									<p class="t2">
										<input required="sizeForCurr" type="text" name="dealerProductDetail.b2bPromotionTitle"
											 value="${fn:escapeXml(proObj.dealerProductDetail.b2bPromotionTitle)}"	/>
											 <span class="tian"></span>
									</p>
								<p class="blank10"></p>
								</div>
							</div>
						
						 <div  class="z" id="zizhi">
								
								<div class='wenzi'>证明资质的图片:</div>
								<ul id="00_img">
								<li class="img-1">
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
							<p class="blank5"></p>
							<p class="blank10"></p>
							<c:if test="${target == '1'}">
							<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
								<c:if test="${attr.dealerProductAttr.buyAttr == 1}">
									<!-- 购买属性 -->
									<div class="yanse">
										<div class="yanse1" title="${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}">
											<span>*</span>${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}： 
											<input type="hidden" name="buyIndex" value="${saleVs.index}">
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.attrId"  value="${fn:escapeXml(attr.dealerProductAttr.attrId)}">
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.prodAttrId"  value="${fn:escapeXml(attr.dealerProductAttr.prodAttrId)}">
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.attrNameCn" value="${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}" id="${saleVs.index}">
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.type" value="${fn:escapeXml(attr.dealerProductAttr.type)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.style" value="${fn:escapeXml(attr.dealerProductAttr.style)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.isneed" value="${fn:escapeXml(attr.dealerProductAttr.isneed)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.buyAttr" value="${fn:escapeXml(attr.dealerProductAttr.buyAttr)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.saleAttr" value="${fn:escapeXml(attr.dealerProductAttr.saleAttr)}">
										</div>
										<div class="yanse2">
											<p>
												<c:forEach items="${attr.dealerProductAttrvals}" var="av" varStatus="vars">
													<span title="${fn:escapeXml(av.lineAttrvalNameCn)}">
														<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttrvals[${vars.index}].attrValId" value="${fn:escapeXml(av.attrValId)}" />
														<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttrvals[${vars.index}].prodAttrValId" value="${fn:escapeXml(av.prodAttrValId)}" />
														<c:if test="${av.isProdAttr}">
														<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttrvals[${vars.index}].lineAttrvalNameCn" value="${fn:escapeXml(av.lineAttrvalNameCn)}"/>
														</c:if>
														<input type="checkbox"  disabled="disabled"  id="${vars.index}" alt="${av.attrValId}" value="${fn:escapeXml(av.lineAttrvalNameCn)}"
														 isnew="${av.istate}"	<c:if test="${av.isProdAttr}">checked</c:if>>
														<c:choose>
															<c:when test="${av.istate==2}">
																<label style="color: red;">${fn:escapeXml(av.lineAttrvalNameCn)}</label>	
															</c:when>
															<c:otherwise>
															<label>${fn:escapeXml(av.lineAttrvalNameCn)}</label>
															 </c:otherwise>
														</c:choose>
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
											<input type="hidden" name="saleIndex" value="${saleVs.index}">
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.attrId"  value="${fn:escapeXml(attr.dealerProductAttr.attrId)}">
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.prodAttrId"  value="${fn:escapeXml(attr.dealerProductAttr.prodAttrId)}">
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.attrNameCn" value="${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}" id="${saleVs.index}">
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.type" value="${fn:escapeXml(attr.dealerProductAttr.type)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.style" value="${fn:escapeXml(attr.dealerProductAttr.style)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.isneed" value="${fn:escapeXml(attr.dealerProductAttr.isneed)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.buyAttr" value="${fn:escapeXml(attr.dealerProductAttr.buyAttr)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.saleAttr" value="${fn:escapeXml(attr.dealerProductAttr.saleAttr)}">
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.sortval" value="${fn:escapeXml(attr.dealerProductAttr.sortval)}">
										</div>
										<div class="chim2">
											<p>
												<c:forEach items="${attr.dealerProductAttrvals}" var="av" varStatus="vs">
													<span title="${fn:escapeXml(av.lineAttrvalNameCn)}">
														<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttrvals[${vs.index}].attrValId" value="${fn:escapeXml(av.attrValId)}" />
														<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttrvals[${vs.index}].prodAttrValId" value="${fn:escapeXml(av.prodAttrValId)}" />
														<c:if test="${av.isProdAttr==true }">
														<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttrvals[${vs.index}].lineAttrvalNameCn" value="${fn:escapeXml(av.lineAttrvalNameCn)}"> 
														</c:if>
														<input isnew="${av.istate}" type="checkbox" disabled="disabled" alt="${av.attrValId}" <c:if test="${av.isProdAttr==true }"> checked="checked" </c:if> id='${vs.index}'  value="${fn:escapeXml(av.lineAttrvalNameCn)}">
														<c:choose>
															<c:when test="${av.istate==2}">
																<label style="color: red;">${fn:escapeXml(av.lineAttrvalNameCn)}</label>	
															</c:when>
															<c:otherwise>
																<label>${fn:escapeXml(av.lineAttrvalNameCn)}</label>
															</c:otherwise>
														</c:choose>
														
														
														
													</span>
												</c:forEach>
											</p>
										</div>
									</div>
								</c:if>
							</c:forEach>
							</c:if>
							<c:if test="${target == '2'}">
								<table id="tblSale" style="margin-top:10px;border:1px;border-style: solid; border-color: #c3c3c3;">
									<colgroup>
									<col style="width: 120px;border:1px;border-style: solid; border-color: #c3c3c3;">
									<col style="width: 550px;border:1px;border-style: solid; border-color: #c3c3c3;">
									<!-- <col style="width: 50px;border:1px;border-style: solid; border-color: #c3c3c3;"> -->
									</colgroup>
									<tr style="height: 30px;">
										<th>规格属性</th>
										<th>属性值</th>
										<!-- <th>操作</th> -->
									</tr>
									<% int x = 0;%>
									<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
									<c:if test="${attr.dealerProductAttr.saleAttr==1}">
									<tr class="chim">
										<td class="chim1"  title="${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}" align="center">
											${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.attrNameCn" value="${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}" />
											<input type="hidden" name="saleIndex" value="${saleVs.index}"><!-- style="width: 100px;" readonly="readonly" -->
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.attrId"  value="${fn:escapeXml(attr.dealerProductAttr.attrId)}">
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.prodAttrId"  value="${fn:escapeXml(attr.dealerProductAttr.prodAttrId)}">
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.type" value="${fn:escapeXml(attr.dealerProductAttr.type)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.style" value="${fn:escapeXml(attr.dealerProductAttr.style)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.isneed" value="${fn:escapeXml(attr.dealerProductAttr.isneed)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.buyAttr" value="${fn:escapeXml(attr.dealerProductAttr.buyAttr)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.saleAttr" value="${fn:escapeXml(attr.dealerProductAttr.saleAttr)}">
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.sortval" value="${fn:escapeXml(attr.dealerProductAttr.sortval)}">
										</td>
										<td class="chim2" id="addSaleAttrvals<%=x%>">
											<!-- <img src="../commons/images/img_+bg.jpg" onclick="addSaleAttrval(<%=x%>)"> -->
											<c:forEach items="${attr.dealerProductAttrvals}" var="av" varStatus="vs">
												<span title="${fn:escapeXml(av.lineAttrvalNameCn)}">
													<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttrvals[${vs.index}].attrValId" value="${fn:escapeXml(av.attrValId)}" />
													<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttrvals[${vs.index}].prodAttrValId" value="${fn:escapeXml(av.prodAttrValId)}" />
													<c:if test="${av.isProdAttr==true }">
														<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttrvals[${vs.index}].lineAttrvalNameCn" value="${fn:escapeXml(av.lineAttrvalNameCn)}"> 
													</c:if>
													<input isnew="${av.istate}" type="checkbox" disabled="disabled" alt="${av.attrValId}" <c:if test="${av.isProdAttr==true }"> checked="checked" </c:if> id='${vs.index}'  value="${fn:escapeXml(av.lineAttrvalNameCn)}">
													<c:choose>
														<c:when test="${av.istate==2}">
															<label style="color: red;">${fn:escapeXml(av.lineAttrvalNameCn)}</label>	
														</c:when>
														<c:otherwise>
															<label>${fn:escapeXml(av.lineAttrvalNameCn)}</label>
														</c:otherwise>
													</c:choose>
												</span>
											</c:forEach>
										</td>
										<!-- <td><input type="button" value="删除属性值" onclick="delSaleAttrval(<%=x%>)"/></td> -->
									</tr>
									<% x++; %>
									</c:if>
									</c:forEach>
								</table>
								<br/>
								<table id="tblBuy" style="margin-top:10px;border:1px;border-style: solid; border-color: #c3c3c3;">
									<colgroup>
									<col style="width: 120px;border:1px;border-style: solid; border-color: #c3c3c3;">
									<col style="width: 550px;border:1px;border-style: solid; border-color: #c3c3c3;">
									<!-- <col style="width: 50px;border:1px;border-style: solid; border-color: #c3c3c3;"> -->
									</colgroup>
									<tr style="height: 30px;">
										<th>展示属性</th>
										<th>属性值</th>
										<!-- <th>操作</th> -->
									</tr>
									<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
									<c:if test="${attr.dealerProductAttr.buyAttr==1}">
									<tr class="yanse">
										<td class="yanse1" title="${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}" align="center">
											${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}
											<input type="hidden" name="buyIndex" value="${saleVs.index}">
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.attrId"  value="${fn:escapeXml(attr.dealerProductAttr.attrId)}">
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.prodAttrId"  value="${fn:escapeXml(attr.dealerProductAttr.prodAttrId)}">
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.attrNameCn" value="${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}" id="${saleVs.index}">
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.type" value="${fn:escapeXml(attr.dealerProductAttr.type)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.style" value="${fn:escapeXml(attr.dealerProductAttr.style)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.isneed" value="${fn:escapeXml(attr.dealerProductAttr.isneed)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.buyAttr" value="${fn:escapeXml(attr.dealerProductAttr.buyAttr)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttr.saleAttr" value="${fn:escapeXml(attr.dealerProductAttr.saleAttr)}">
										</td>
            							<td id="addBuyAttrvals" class="yanse2">
            								<!-- <img src="../commons/images/img_+bg.jpg" onclick="addBuyAttrval()"> -->
	            							<c:forEach items="${attr.dealerProductAttrvals}" var="av" varStatus="vars">
												<span title="${fn:escapeXml(av.lineAttrvalNameCn)}">
													<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttrvals[${vars.index}].attrValId" value="${fn:escapeXml(av.attrValId)}" />
													<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttrvals[${vars.index}].prodAttrValId" value="${fn:escapeXml(av.prodAttrValId)}" />
													<c:if test="${av.isProdAttr}">
													<input type="hidden" name="dealerProductAttrDTOs[${saleVs.index}].dealerProductAttrvals[${vars.index}].lineAttrvalNameCn" value="${fn:escapeXml(av.lineAttrvalNameCn)}"/>
													</c:if>
													<input type="checkbox"  disabled="disabled"  id="${vars.index}" alt="${av.attrValId}" value="${fn:escapeXml(av.lineAttrvalNameCn)}"
														 isnew="${av.istate}"	<c:if test="${av.isProdAttr}">checked</c:if>>
													<c:choose>
													<c:when test="${av.istate==2}">
														<label style="color: red;">${fn:escapeXml(av.lineAttrvalNameCn)}</label>	
													</c:when>
													<c:otherwise>
													<label>${fn:escapeXml(av.lineAttrvalNameCn)}</label>
													 </c:otherwise>
												</c:choose>
												</span>
											</c:forEach>
            							</td>
            							<!-- <td><input type="button" value="删除属性值" onclick="delBuyAttrval()" /></td> -->
            						</tr>
            						</c:if>
            						</c:forEach>
								</table>
							</c:if>
							</div>
							<p class="blank25"></p>
							
							<div class="z">
								<div id="d"></div>
							</div>
							
							
							<div class="download"><span style="margin-left: 25px; color: #f00; font-weight: bold;">商品的图片上传大小≥30K且≤100K～</span><input type="button" value="下载图片"  class="download-btn" id="J-downloadPic" /></div>
							
								
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
								<div id="priceTypeHide">
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
										
										<input type="hidden" name="dealerProductDetail.portName" value="${proObj.dealerProductDetail.portName}">
									</div>
								
								
								</div>
								<p class="blank5"></p>
								<p class="t1">
									<i class="c_red">*</i>报价：</p>
								<div class="select-quote">
									<input type="radio" id="pic_count" name="cost" class="cp1" value="1"/>
									<strong>按产品数量报价</strong>
									<input type="radio" id="pic_sku" name="cost" class="cp2" value="2"/>
									<strong>按产品规格报价</strong>
								</div>
								<div class="blank10"></div>
								<div class="tqz" style="height:auto;">
								
									<div class="tab_box">
									    <h4>供应商价格:</h4>
										<i class="c_red">*</i>最小起订量：
										<input type="text" name="" disabled="disabled" style="width:150px; height:20px; line-height:20px; border:1px solid #c7c7c7;"  value="${fn:escapeXml(supplierMinQty)}">
										<table id="tb-speca-quotation1" border="0" cellpadding="0" cellspacing="0" style="margin-top:10px;">
											<colgroup>
												<col class="weight">
												<col class="weight">
												<col class="weight">
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
													
													<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
														<c:if test="${attr.dealerProductAttr.saleAttr == 1}">
													<th>${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}</th>
														</c:if>
													</c:forEach>
																
													<th>
														<span class="c_red"> *</span>
														单价（元）<br/>
														
													</th>
										
												</tr>
											</thead>
										</table>
									</div>

									<!-- 表格 end -->
									<p class="blank15"></p>
								
								
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
												<input type="text"  name=''>元/<span class="danwei">${proObj.dealerProductPackage.measureCname}</span>
											</span>
										</p>
									</div>
									
									<!-- 新增开始 -->
									<div class="tq2" style="border-bottom:1px solid #eeeeee;" id="spotNumPrace">
										<h5>现货价格:</h5>
										<p class="pp">
											<span class="b21">
												建议零售价：
											    <input type="text" name="onlyPrice" id="onlyPrice" style="width:69px; height:22px; border:1px solid #c8c8c8;" value="${fn:escapeXml(proObj.dealerProductSkuDTOs[0].dealerProductPriceMap.retailPrice)}">
												<span class="dpl-tip-inline-warning"></span>
											</span>
											<span class="b2">
												起批量：
												<input type="text"  name='start'  id="spotStartNum" class="start">
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
										
										<img src="<%=path %>/commons/images/img_+bg.jpg"> 增加数量区间
										</span>
									</div>
									
									<div class="tq2"  id="futuresNumPrace">
										<h5>期货价格:</h5>
										<p class="pp">
											<%-- <span class="b21">
												建议零售价：
											    <input type="text" name="" id="" style="width:69px; height:22px; border:1px solid #c8c8c8;" value="${fn:escapeXml(proObj.dealerProductSkuDTOs[0].dealerProductPriceMap.retailPrice)}">
												<span class="dpl-tip-inline-warning"></span>
											</span> --%>
											<span class="b2">
												起批量：
												<input type="text"  name='supplierStart'  id="supplierStartNum"  class="start">
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
										
										<img src="<%=path %>/commons/images/img_+bg.jpg"> 增加数量区间
										</span>
									</div>
									<!-- 新增结束-->
									
									
									<div class="g">
										<p class="blank"></p>
									
										
									  <!-- 新增表格开始 -->
										<div class="tab_box" id="spotprice">
										
											<h4>现货价格:</h4>
											
											<i class="c_red">*</i>最小起订量：
											
											<input type="text" id="minNum" name="minNum" style="width:150px; height:20px; line-height:20px; border:1px solid #c7c7c7;"  value="${fn:escapeXml(proObj.dealerProductSaleSetting.minWholesaleQty)}">
											
											<table id="tb-speca-quotation2" border="0" cellpadding="0" cellspacing="0" style="margin-top:10px; width: 100%;">
												
												<colgroup>
													<col class="weight">
													<col class="weight">
													<col class="weight">
													<col class="weight">
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
														
														<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
															
															<c:if test="${attr.dealerProductAttr.saleAttr == 1}">
																<th>${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}</th>
															</c:if>
														
														</c:forEach>
																
														<th>
														
															<span class="c_red"> *</span>
															单价（元）<br/>
															<input class="same_price"  type="checkbox"  id="productPic"> 全部相同
														</th>
														
														<th>
														
															<span class="c_red"> *</span>
															建议零售价<br/>
															<input class="same_price"  type="checkbox"  id="rPrice">  全部相同
														</th>
														
													</tr>
													
												</thead>
												
											</table>
											
									   </div>
									   
									   <span class="dpl-tip-inline-warning"></span>
									   
									   <p class="blank15"></p>
									   
									   <div class="tab_box" id="futuressprice">
									   
									   		<h4>期货价格:</h4>
									   		
											<i class="c_red">*</i>最小起订量：
											
											<input type="text" id="supplierMinNum" name="supplierMinNum" style="width:150px; height:20px; line-height:20px; border:1px solid #c7c7c7;"  value="${fn:escapeXml(proObj.dealerProductSaleSetting.minSupplierQty)}">
											
											<table id="tb-speca-quotation3" border="0" cellpadding="0" cellspacing="0" style="margin-top:10px;">
												
												<colgroup>
												<col class="weight">
												<col class="weight">
												<col class="weight">
												</colgroup>
												
												<thead>
													
													<tr>
														
														<th>
														
														<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
														
															<c:if test="${attr.dealerProductAttr.buyAttr == 1}">${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}</c:if>
														
														</c:forEach>
														
														</th>
														
														
														
														<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
															
															<c:if test="${attr.dealerProductAttr.saleAttr == 1}">
															<th>${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}</th></c:if>
														
														</c:forEach>
														
																	
														
														<th>
														
															<span class="c_red"> *</span>
														
															单价（元）<br/>
															<input class="same_price"  type="checkbox"  id="supplierPic">  全部相同
														</th>
													
													</tr>
												
												</thead>
											
											</table>
											
									   </div>	
										
										<span class="dpl-tip-inline-warning"></span>
									 
									  <!-- 新增表格结束 -->
									
									</div>
									
									<p class="blank10"></p>
								
								</div>
								
								
								
								
						<p class="blank5"></p>
						<p class="t1">
						<i class="c_red">*</i> 订单收集类型：</p>
						<div class="select-quote">
						<input type="radio"   name="supplierProductDetail.orderType" <c:if test="${proObj.dealerProductDetail.orderType==1}">checked='checked'</c:if>  value='1' disabled="disabled"  onchange="changeOrderType('1')"/>
						<strong>现货库存</strong>
						
						<input type="radio"  name="supplierProductDetail.orderType" <c:if test="${proObj.dealerProductDetail.orderType==0}">checked='checked'</c:if> value='0' disabled="disabled" onchange="changeOrderType('2')"/>
						<strong>收集订单</strong>
						</div>
						<div id="collection"  <c:if test="${proObj.dealerProductDetail.orderType!=0}">style="display:none"</c:if>>
						<p class="blank15"></p>
						<p class="t1"><i class="c_red">*</i>预计发货日期：</p>
						<p class="s_1">
							<input type="text" disabled="disabled"  value="<fmt:formatDate value="${proObj.dealerProductDetail.deliverDate}" pattern="yyyy-MM-dd"/>"
									readonly="readonly" id="delivery" onClick="WdatePicker({minDate:'%y-%M-%d'})">
							<span id="deliveryDateWarning" class="dpl-tip-inline-warning"></span>	
						</p>
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
								<div class="tab_box" style="margin-left:10px;" id="skuCodeTable">
					
									<table id="tb-tiaoxingma">
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
													
													<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
														<c:if test="${attr.dealerProductAttr.saleAttr == 1}">
															<th>${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}</th>
														</c:if>
													</c:forEach>
												
											<th><span class="c_red"> *</span>条形码<br />
												 
											</th>
											<th><span class="c_red"> *</span>条形码图片<br />
											 
											</th>
											<th><span class="c_red"> *</span>sku长度<br />(cm)
											<br /><input class="same_price"  type="checkbox"  id="length"> 全部相同
											</th>
											<th><span class="c_red"> *</span>sku宽度<br />(cm)
											<br /><input class="same_price"  type="checkbox"  id="width"> 全部相同
											</th>
											<th><span class="c_red"> *</span>sku高度<br />(cm)
											<br /><input class="same_price"  type="checkbox"  id="height"> 全部相同
											</th>
											<th><span class="c_red"> *</span>sku重量<br />
											<br /><input class="same_price"  type="checkbox"  id="weight"> 全部相同
											</th>
											</tr>
										</thead>
									    <tbody>
										</tbody>
									    
									</table>
									
								</div>
								<span class="dpl-tip-inline-warning"></span>
								<!-- 表格 end -->

								
								
							
							<p class="blank15"></p>
					
						
					</div>
							
							<div class="mingxi">
								<div class="m1">商品明细：</div>
								<div class="mm">
									<p class="m2">
										<span><!-- <i class="c_red">*</i> -->包装清单：</span>
										<textarea name="dealerProductDetail.packingList" id="packinglist">${fn:escapeXml(proObj.dealerProductDetail.packingList)}</textarea>
									</p>
									<span class="dpl-tip-inline-warning">请填写 包装清单</span>
									<p class="m6">
										<span><!-- <i class="c_red">*</i> -->物流描述：</span>
										<textarea  name="dealerProductDetail.logisticsDescription" id="logisticsDescription">${fn:escapeXml(proObj.dealerProductDetail.logisticsDescription)}</textarea>
									</p>
									<span class="dpl-tip-inline-warning"></span>
									<p class="m3">
										<span><i class="c_red"></i>售后服务：</span>
										<textarea name="dealerProductDetail.salesServiceDescription"  id="salesServices">${fn:escapeXml(proObj.dealerProductDetail.salesServiceDescription)}</textarea>
									</p>
									<span class="dpl-tip-inline-warning">请填写 售后服务</span>
									<p class="m4">
										<span class="s1"><i class="c_red"></i>售后电话：</span>
										<input id="servicephone" type="text" name="dealerProductDetail.salesCalls" value="${fn:escapeXml(proObj.dealerProductDetail.salesCalls)}" />
									</p>
									<span class="dpl-tip-inline-warning" style="margin-right:100px;" id="mobiletext"></span>
									
									<p class="m3">
										<span>
											备注：
										</span>
										<textarea id="remark"  name="dealerProduct.remark">${fn:escapeXml(proObj.dealerProduct.remark)}</textarea>
										<span class="dpl-tip-inline-warning" style="width: auto;color: red;"></span>
									</p>
								</div>
							</div>
						</div>

						<!-- 文本编辑框 -->
						<input type="hidden" name="dealerProductAttachs[0].productattachid" value="${productattachid}">
						<div class="blank30"></div>
						
						<c:if test="${target eq '2' }">
						<div class="i_box">
							<h2>图文详情</h2>
							<div class="blank20"></div>
							<!--style给定宽度可以影响编辑器的最终宽度-->
							<div style="width: 804px">
							<span class="dpl-tip-inline-warning" id="Details"></span>
								<script id="editor" type="text/plain"
									style="width:804px;">
								${attch}
    						</script>
							</div>
							<script type="text/javascript">
								//实例化编辑器
								var ue = new UE.ui.Editor();
								ue.render("editor");
								
							</script>
						</div>
						</c:if>
						<c:if test="${target eq '1' }">
							<div class="i_box">
								<h2>图文详情</h2>
								<div class="blank20"></div>
								<!--style给定宽度可以影响编辑器的最终宽度-->
								<div style="max-width:820px; height:auto; overflow:auto;">
									<c:catch var="catchAtach">
										<c:if test='${attch != ""}'>
											<c:import url="${attch}" charEncoding="utf-8"></c:import>
										</c:if>
									</c:catch>
								</div>
							</div>
						
						</c:if>
						
						<!-- 文本编辑框结束 -->
						
						<!-- 填写基本信息  -->
					  <div class="blank30"></div>
					</div>
					</div>
					
					<div class="mo_box" style="display:none;">
							<div class="mo_c">
							<p>
								<span class="ml"><i class="c_red">*</i>商品标题：</span>
								<span class="mr"><input type="text" class="s" id="b2cProductName" name="b2cProductDetail.b2cProductName" value="${fn:escapeXml(proObj.b2cProductDetail.b2cProductName)}"></span>
								<span class="dpl-tip-inline-warning" >商品标题不能大于100个字,且不能是空的!</span>
							</p>
							<p>
								<span class="ml"><!-- <i class="c_red">*</i> -->发货地：</span>
								<span class="mr"><input type="text" id="b2cOriginCountry" class="inp" name="b2cProductDetail.originCountry" value="${fn:escapeXml(proObj.b2cProductDetail.originCountry)}"></span>
								<span class="dpl-tip-inline-warning" >发货地不能大于100个字,且不能是空的!</span>
							</p>
							<p>
								<span class="ml"><i class="c_red">*</i>国外币种：</span>
								<span class="mr">
									<select name="b2cProductDetail.b2cMoneyUnitId">
										<c:forEach items="${momeyUnits }" var="price">
											<option value="${price.id}" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId eq price.id }">selected="selected"</c:if>>${fn:escapeXml(price.moneyUnitCn)}</option>
										</c:forEach>
									</select>
								</span>
							</p>
							<p>
								<span class="ml"><i class="c_red">*</i>货源种类：</span>
								<span class="mr">
								<c:if test="${proObj.b2cProductDetail.b2cSupply != 31 }">
									<select name="b2cProductDetail.b2cSupply">
											<option value="1" <c:if test="${proObj.b2cProductDetail.b2cSupply == 1 || souceType == 1}">selected="selected"</c:if>>一般贸易</option>
											<option value="11" <c:if test="${proObj.b2cProductDetail.b2cSupply == 11 || souceType == 11}">selected="selected"</c:if>>海外直邮</option>
											<option value="12" <c:if test="${proObj.b2cProductDetail.b2cSupply == 12 || souceType == 12}">selected="selected"</c:if>>保税区发货</option>
											<option value="21" <c:if test="${proObj.b2cProductDetail.b2cSupply == 21 || souceType == 21}">selected="selected"</c:if>>韩国直邮</option>
											<option value="50" <c:if test="${proObj.b2cProductDetail.b2cSupply == 50 || souceType == 50 }">selected="selected"</c:if>>海外预售</option>
											<option value="51" <c:if test="${proObj.b2cProductDetail.b2cSupply == 51 || souceType == 51}">selected="selected"</c:if>>第三方国际发货(POP)</option>
									</select>
								</c:if>
									 <c:if test="${proObj.b2cProductDetail.b2cSupply == 31 }">
										<select name="b2cProductDetail.b2cSupply">
												<option value="1" <c:if test="${proObj.b2cProductDetail.b2cSupply == 31 }">selected="selected"</c:if>>卓越商品</option>
										</select>
									</c:if>
								</span>
							</p>
							<p>
								<span class="ml"><i class="c_red">*</i>行邮税（%）：</span>
								<span class="mr"><select name="b2cProductDetail.tariff">
								<option value="0"  <c:if test="${proObj.b2cProductDetail.tariff == 0 }">selected="selected"</c:if>>无</option>
								<option value="26.35"  <c:if test="${proObj.b2cProductDetail.tariff == 26.3500 }">selected="selected"</c:if>>26.35</option>
									<option value="9.1"  <c:if test="${proObj.b2cProductDetail.tariff == 9.1000 }">selected="selected"</c:if>>9.1</option>
								<option value="10"  <c:if test="${proObj.b2cProductDetail.tariff == 10.0000 }">selected="selected"</c:if>>10</option>
									<option value="11.9" <c:if test="${proObj.b2cProductDetail.tariff == 11.9000 }">selected="selected"</c:if>>11.9</option>
								<option value="20" <c:if test="${proObj.b2cProductDetail.tariff == 20.0000 }">selected="selected"</c:if>>20</option>
								<option value="30" <c:if test="${proObj.b2cProductDetail.tariff == 30.0000 }">selected="selected"</c:if>>30</option>
									<option value="47" <c:if test="${proObj.b2cProductDetail.tariff == 47.0000 }">selected="selected"</c:if>>47</option>
								<option value="50" <c:if test="${proObj.b2cProductDetail.tariff == 50.0000 }">selected="selected"</c:if>>50</option>
								</select></span>
							</p>
						</div>
						<div class="mo_b">
							<table id="tb-b2c">
								<thead>
								<tr>
									<th>	
										<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
											<c:if test="${attr.dealerProductAttr.buyAttr == 1}">
												${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}
											</c:if>
										</c:forEach>
									</th>
									
										<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
											<c:if test="${attr.dealerProductAttr.saleAttr == 1}">
													<th>${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}</th>
											</c:if>
										</c:forEach>
									
									<th>商品货号</th>
									<th><i class="c_red">*</i>销售基数</th>
									<th><i class="c_red">*</i>零售价</th>
									<th><i class="c_red">*</i>国内价</th>
									<th><i class="c_red">*</i>翼支付价</th>
									<th><!-- <i class="c_red">*</i> -->海关备案号</th>
								</tr>
								</thead>
							</table>
						</div>
						<span class="dpl-tip-inline-warning" style="display:none" id="tb-b2c-warning">请认真填写表单,不能有空数据</span>
						
						<div class="blank30"></div>
						
						<c:if test="${target eq '2' }">
							<div class="i_box">
								<h2>图文详情</h2>
								<div class="blank20"></div>
								<!--style给定宽度可以影响编辑器的最终宽度-->
								<div style="width: 804px">
								<span class="dpl-tip-inline-warning" id="Details1"></span>
									<script id="editor1" type="text/plain" style="width:804px;">
								${b2cDescription}
    						</script>
								</div>
								<script type="text/javascript">
									//实例化编辑器
									    var option = {
									    		textarea: 'editor1' //设置提交时编辑器内容的名字 
									    		};
									    var ue = new UE.ui.Editor(option);
									    ue.render("editor1");
									
								</script>
							</div>
						</c:if>
						<c:if test="${target eq '1' }">
							<div class="i_box">
								<h2>图文详情</h2>
								<div class="blank20"></div>
								<!--style给定宽度可以影响编辑器的最终宽度-->
								<div style="max-width:820px; height:auto; overflow:auto;">
									<c:catch var="catchAtach">
										<c:if test='${b2cDescription != "" }'>
											<c:import url="${b2cDescription}" charEncoding="utf-8"></c:import>
										</c:if>
									</c:catch>
								</div>
							</div>
						
						</c:if>
						
					</div>
			
				</div>
				</div>
				</div>
				</div>
				<!-- 边框 end -->
				<div class="blank10"></div>
				<div class="btn_box">
					<div>
					<c:if test="${! empty buttonsMap['货品列表-保存商品'] }">
						<button type="button" class="fabu_btn" id="postEdit">保存修改</button>
					</c:if>
					<c:if test="${! empty buttonsMap['货品列表-审核商品'] }">
						<button type="button" class="fabu_btn" id="postTrade">提交审核</button>
					</c:if>	
					<c:if test="${! empty buttonsMap['货品列表-变更属性'] && iseditproperty==1}">
						<button type="button" class="fabu_btn" id="postProperty">变更属性</button>
					</c:if>
					</div>
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
	
	<div id="lightboxOverlay" class="lightboxOverlay" style="display: none; width: 1903px; height: 3305px;"></div>
	<div id="lightbox" class="lightbox" style="display: none; top: 832px; left: 0px;">
		<div class="lb-outerContainer" style="width: 403px; height: 403px;">
			<div class="lb-container">
				<img class="lb-image" src="${path}/commons/images/loading.gif" style="display: block; width: 395px; height: 395px;">
				<!-- <div class="lb-nav" style="display: block;">
					<a class="lb-prev" href="" style="display: block;"></a>
					<a class="lb-next" href="" style="display: block;"></a>
				</div> -->
				<div class="lb-loader" style="display: none;">
				<a class="lb-cancel"></a>
				</div>
			</div>
		</div>
		<div class="lb-dataContainer" style="display: block; width: 403px;">
			<div class="lb-data">
				<div class="lb-details">
					<!-- <span class="lb-caption" style="display: inline;">Or press the right arrow on your keyboard.</span> -->
					<span class="lb-number">Image 2 of 4</span>
				</div>
				<div class="lb-closeContainer">
					<a class="lb-close"></a>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${path}/commons/js/lightbox.min.js"></script>
	<script type="text/javascript" src="${path}/commons/js/AutoComplete/jquery.autocomplete.js"></script>
	<script type="text/javascript">

	$(document).ready(function(){
		
		var showbodytype = '${bodytype}';

		if(showbodytype == '2'){

			$(".mo_t ul li:eq(1)").addClass('at').siblings().removeClass('at');
			$(".mo_wrap .mo_box:eq(1)").show().siblings().hide();
			
		}
		
		var productId = $("#productId").val();
		var editStat = '${editStat}';
		var target = '${target}';
		
		if(target == '2'){
			
			checkProductIsEdit(productId);
		
		}
		
		if(editStat != '1' && target == '1'){
			
			tipMessage("当前商品为不可修改状态,请返回商品列表后刷新页面！",function(){
				
				reloadOpennerPage();
				
			});
			
		}
		
		$("#J-downloadPic").click(function(){
			window.open(CONTEXTPATH+"/product/downBatchProductImage?productId="+productId+"&math="+Math.random(),"_blank");
		});
		
		$("#toEditCategory").click(function(){
			window.location.href="../product/toEditCategoryUI?productId="+productId+"&showbodytype="+showbodytype;
		});

		var url_array;
	
		if('${jsonImg}'!=""){
			
			url_array = ${jsonImg};
			$.each(url_array,function(n,value){
				adduploadimg(value[1],value[2],value[3]); 
			});
			
		}
		
		var qualification =  ${qualificationUrl};
		inituploadzizhi(qualification);
		
		
		var prodType = $("#prodType").val();
		
		if('${jsonProductPic}'!=''){
			
			var sku_array= ${jsonSupplierProductPic};
			sku_array.push(${jsonProductPic}[0]);
			
			if(prodType != '1'){
				
				sku_array.push(${jsonCollectionProductPic}[0]);
				
			}else{
				
				$("#futuresNumPrace").remove();
				$("#futuressprice").remove();
				$("#priceTypeHide").remove();
				
			}
			
			var qipiname =["","start","supplierStart"],qipivaluename=["","pic","supplierPrice"];
			
			var measure = $("select[name='dealerProductPackage.measureid'] option:selected").text();
			
			changebox();
			
	 	 	$.each(sku_array,function(keys,values){
	 	 			
	 	 			var statu = values.type;
	 	 			
	 	 			if(keys == '0'){
	 	 				if(statu == '0'){
	 	 					$(".tab_box").eq(0).hide();
	 	 					$(".tq2").eq(0).show();
	 	 				}else{
	 	 					$(".tq2").eq(0).hide();
	 	 					$(".tab_box").eq(0).show();
	 	 				}
	 	 			}
	 	 			
	 			   if(statu=='0'){
	 				   
	 				 if(values.start.length != 0){
	 					 
	 					$(".pp:eq("+keys+") span.b2").remove();
	 					
	 		 			  $.each(values.start,function(key,value){
	 		 				  
	 		 			  var startLength = values.start.length;
	 		 			  
	 		 			    if(keys == 0){
	 		 			    	
	 		 			    	$('.pp:eq('+keys+')').append( "<span class='b2'>起批量：<input type='text' disabled='disabled' value='"+value+"'><span class='danwei'>"+measure+"</span>及以上 <input  type='text' disabled='disabled' value='"+values.pic[key]+"'> 元/<span class='danwei'>"+measure+"</span></span>" );
	 		 			
	 		 			    }else{
	 		 			    	
	 		 			    	if(key==startLength-1 && key !=0){
	 		 	 					$('.pp:eq('+keys+')').append( "<span class='b2'>起批量：<input type='text' class='start' name='"+qipiname[keys]+"' value='"+value+"'><span class='danwei'>"+measure+"</span>及以上 <input name='"+qipivaluename[keys]+"' type='text'  class='pic' value='"+values.pic[key]+"'> 元/<span class='danwei'>"+measure+"</span><span class='del'>删除</span></span>" );
	 		 	 			 	}else{
	 		 	 			 		$('.pp:eq('+keys+')').append( "<span class='b2'>起批量：<input type='text' class='start' name='"+qipiname[keys]+"' value='"+value+"'><span class='danwei'>"+measure+"</span>及以上 <input name='"+qipivaluename[keys]+"' type='text'  class='pic' value='"+values.pic[key]+"'> 元/<span class='danwei'>"+measure+"</span></span>" );
	 		 	 			 	}
	 		 			    	
	 	 	 			 	var length = $(".pp:eq("+keys+") .b2").length;
	 	 	 			 	
	 						if (length>=3){
	 							$(".tq2:eq("+keys+") .b3").hide();
	 						}
	 						$(".pp:eq("+keys+") .g tab_box").hide();
	 		 			    };
	 		 			  });
	 				 }
		 			  
		 		     $('#pic_count').attr('checked',true);
	 				}else{
	 					
	 			 		var pku = values.pic;
		 			 	
		 			 	$(".pp:eq("+keys+") .g tab_box").show();
		 			 	$(".pp:eq("+keys+") .tq2").hide();
		 			 	
		 				var pku_array = new Array();
		 				
		 				$.each(pku,function(key,value){
		 					
		 					pku_array.push(value);
		 					var new_array = new Array();
		 					
		 					new_array.push(pku_array);
		 					var ii = keys+1;
		 					_fShowTableInfo(pku,"tb-speca-quotation"+ii); 
		 					
		 				});
		 				
		 				 $('#pic_sku').attr('checked',true);
		 			
	 			 };
	 			 
	 			 checkRadio();
	 	 	});
	 	 	
	 	 }
 		var skuCode = ${showbarCodeJson};
		var skuCode_array = new Array();
		$.each(skuCode,function(key,value){
			skuCode_array.push(value);
			var new_array = new Array();
			new_array.push(skuCode_array);
			_fShowTableInfo(skuCode,"tb-tiaoxingma"); 
		});
		
		_fShowTableInfo(${jsonB2CProductPic},"tb-b2c"); 
		
		
		if(target == '1'){
			$('.b3').hide();
			$('.del').hide();
			$('input').attr('disabled', 'disabled'); 
			$('select').attr('disabled', 'disabled'); 
			$('textarea').attr('disabled', 'disabled'); 
			$('i').hide(); 
			$('.wenzi').children(':not(span)').hide(); 
			$('.btn_box').attr('disabled', 'disabled'); 
			$('.btn_box').hide();
			$('.operate').hide();
		}
	});
	

	window.onbeforeunload = onbeforeunload_handler; 
	function onbeforeunload_handler(){
		
	      var productId = $("#productId").val();
		  $.ajax({
              type: "POST",
              data:"productId="+productId,
              url: '${path}/product/unClockProductById?id='+ Math.random(),
              async: false, //设为false就是同步请求
              cache: false,
              success: function (data) {
				  reloadOpennerPage();
              }
          });
	}
	
	
	$(".mo_t ul li").each(function(index){
		$(this).click(function(){
			var i = $(this).index();
	       $(this).addClass('at').siblings().removeClass('at');
			$(".mo_wrap .mo_box:eq("+index+")").show().siblings().hide();
			
		})
	});

	$(document).ready(function(){   
	    $("select[name=saleLineAttrvalNameCn]").change(function(){
	    	var leng = document.getElementsByName("saleLineAttrvalNameCn").length;
	        var saleAttrvalName = [];
	        for(var i=0;i<leng;i++){
	        	saleAttrvalName.push($("select[name=saleLineAttrvalNameCn]:eq("+i+")").find('option:selected').text());
	        	var tdsale = document.getElementsByName("saleAttrval"+i);
	        	for(var j=0;j<tdsale.length;j++){
	        		tdsale[j].innerHTML=$("select[name=saleLineAttrvalNameCn]:eq("+i+")").find('option:selected').text();
	        	}
	        }
	        $("select[name='saleLineAttrvalNameCn']").each(function(){
	            var value = $(this).val();
	            if( $("select[name='saleLineAttrvalNameCn'] option[value='"+value+"']:selected").size()>1){
	            	alert("属性值中有重");
					return false;
	            } 
	        });
	    });
	    
	    $("select[name=buyLineAttrvalNameCn]").change(function(){   
	        var leng = document.getElementsByName("buyLineAttrvalNameCn").length;
	        var buyAttrvalName = [];
	        for(var i=0;i<leng;i++){
	        	buyAttrvalName.push($("select[name=buyLineAttrvalNameCn]:eq("+i+")").find('option:selected').text());
	        }
	        $("select[name='buyLineAttrvalNameCn']").each(function(){
	            var value = $(this).val();
	            if( $("select[name='buyLineAttrvalNameCn'] option[value='"+value+"']:selected").size()>1){
	            	alert("属性值中有重");
					return false;
	            } 
	        });
	    });
	    var brandId='${proObj.brand.id}';
	    $("#firstcategory").val(brandId); 
        onChange(brandId);
		$.ajax({
			 type : "post", 
        	 url : "../product/getBrands",
        	 dataType:"json", 
         	 success : function(msg) {
         		$("#keyword").autocomplete(msg, {
                   minChars: 0,					//最少输入字条
                   max: 1000,
                   autoFill: false,				//是否选多个,用","分开
                   mustMatch: false,				//是否全匹配, 如数据中没有此数据,将无法输入
                   matchContains: true,			//是否全文搜索,否则只是前面作为标准
                   scrollHeight:500,
                   width:240,
                   multiple: false,
                   formatItem: function (row, i, max) {//显示格式
                       return ""+row.nameCn;
                   },
                   formatMatch: function (row, i, max) {//以什么数据作为搜索关键词,可包括中文,
                       return row.nameCn;
                   },
                   formatResult: function (row) {//返回结果
                       return row.id;
                   }
               }).result(function(event, data, formatted) { //回调  
               	    $('#keyword').val(data.nameCn);   //不知为何自动返回值后总是加了个“,”,所以改成后赋值  
                    $("#firstcategory").val(data.id); 
                    onChange(data.id);
               });
		    }
		}); 
		/*根据一级品牌查询子品牌*/
		function onChange(brandId){
			$("#secondcategory").empty();
			$.ajax({
				type : "post",
				url : "../product/getOtherBrand",
				data : "brandId="+ brandId,
				success : function(msg) {
					if(msg.length>2){
						var fistBrandlist = "";
							$.each(eval(msg),function(i,n) {
								fistBrandlist += "<option value='"+ n.id+ "'>"+ n.nameCn + "</option>";
							});
							$("#secondcategory").append(fistBrandlist).show();
					} else{
						$("#secondcategory").hide();
					}
				}
			});
		}
	});
	</script>
</body>
</html>