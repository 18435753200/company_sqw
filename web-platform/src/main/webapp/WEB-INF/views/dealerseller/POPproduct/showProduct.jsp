<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>UNICORN-查看POP商品</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" href="${path }/commons/css/shang.css">
	<link rel="stylesheet" href="${path }/commons/js/uploadify/uploadify.css">
	<link rel="stylesheet" href="${path }/commons/css/lightbox.css">
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
	<!-- 边框start -->
		<div class="right f_l">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;>&nbsp;</p>
					<p>商品管理&nbsp;>&nbsp;</p>
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
						<input type="hidden" id="currentPageId" value="${currentPageId}"/>
						</div>
						<div class="blank15"></div>
						<div class="p_box">
							<p class="p1">
								<i class="c_red">*</i> 商品标题：
							</p>
							<p class="p2">

								<input type="text"  name="pname" id="productinfo" readonly="readonly"
									value="${fn:escapeXml(proObj.supplierProductBase.productname)}"> 
							</p>
							<div class="blank10"></div>
							<p class="p1">产品属性：</p>
							<div class="p3">
								<div class="bb">
									<p class="blank10"></p>
									<!-- 基本属性属性值 -->
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
									<c:forEach items="${simpleAttrs}" var="supplierProductAttr" varStatus="vs">
										<c:if test="${supplierProductAttr.supplierProductAttr.saleAttr != 1 && supplierProductAttr.supplierProductAttr.buyAttr != 1 }">
											<p class="p1" title="${fn:escapeXml(supplierProductAttr.supplierProductAttr.attrNameCn)}">
												<%-- <c:if test="${supplierProductAttr.supplierProductAttr.isneed == 1}">
													<i class="c_red">*</i>
												</c:if> --%>
												
												${fn:escapeXml(supplierProductAttr.supplierProductAttr.attrNameCn)}：
											</p>
													<c:if test="${supplierProductAttr.supplierProductAttr.type == 1}">
															<p>
																<c:forEach items="${supplierProductAttr.supplierProductAttrvals}"
																	var="proAttrVal" varStatus="cbs">
																		<span class="r2" title="${fn:escapeXml(proAttrVal.lineAttrvalNameCn)}"><input type="checkbox" disabled="disabled"
																			name="supplierProductAttrDTOs[${vs.index}].supplierProductAttrvals[${cbs.index}].lineAttrvalNameCn"
																			<c:if test="${proAttrVal.isProdAttr}">checked</c:if>/>
																		${fn:escapeXml(proAttrVal.lineAttrvalNameCn)}</span>
																</c:forEach>
															</p>
													</c:if>
													
													<c:if test="${supplierProductAttr.supplierProductAttr.type ==2}">
														<p class="s_1">
															<select name="supplierProductAttrDTOs[${vs.index}].supplierProductAttrvals[0].lineAttrvalNameCn" disabled="disabled">
																<c:forEach items="${supplierProductAttr.supplierProductAttrvals}" var="supplierProductAttrval">
																	<option value="${fn:escapeXml(supplierProductAttrval.lineAttrvalNameCn)}"
																		<c:if test="${supplierProductAttrval.isProdAttr}">selected</c:if>>
																		${fn:escapeXml(supplierProductAttrval.lineAttrvalNameCn)} 
																	</option>
																</c:forEach>
															</select>
														</p> 
													</c:if>
													
													<c:if test="${supplierProductAttr.supplierProductAttr.type == 3}">
															<p class="t2">
															<c:choose>
																<c:when test="${supplierProductAttr.supplierProductAttrvals[0].isProdAttr}">
																	<input type="text" readonly="readonly"
																		value="${fn:escapeXml(supplierProductAttr.supplierProductAttrvals[0].lineAttrvalNameCn)}" /></p>
																</c:when>
																<c:otherwise>
																	<input type="text" disabled="disabled"
																		value="" /></p>
																	
																</c:otherwise>
															</c:choose>
													</c:if>
													
											<p class="blank10"></p>
											</c:if>
									</c:forEach>
									<!-- 基本属性属性值 -->


									<p class="blank10"></p>
									<!-- <p class="p1">
										<i class="c_red">*</i> 原产国：
									</p>
									<p class="t2">
										<input  name="supplierProduct.originplace" disabled="disabled"
											type="text" value="${fn:escapeXml(proObj.supplierProduct.originplaceCName)}">
									</p>
									</p> -->
									<p class="blank10" style="display: none;"></p>
									<p class="p1" style="display: none;">
										<i class="c_red">*</i> 制造商：
									</p>
									<p class="t2" style="display: none;">
										<input readonly="readonly"
											name="supplierProduct.manufacturers" type="text"
											value="${fn:escapeXml(proObj.supplierProduct.manufacturers)}"> 
									</p>


									<%-- 新加 b2c字段开始部分 start--%>

									<p class="blank10" style="display: none;"></p>
									<p class="p1" style="display: none;"><i class="c_red">*</i> 发货地：</p>
									<p class="t2" style="display: none;">
										<input required="b2cOriginCountry" name="b2cProductDetail.originCountry" type="text" value="${proObj.b2cProductDetail.originCountry}">
									<span class="dpl-tip-inline-warning">
									发货地只能1-200字
									</span>
									</p>

									<p class="blank10" style="display: none;"></p>
									<p class="p1" style="display: none;"><i class="c_red">*</i> 国外币种：</p>
									<p class="s_1" style="display: none;">
										<select name="b2cProductDetail.b2cMoneyUnitId">
											<option value="1" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==1}">selected</c:if> >人民币</option>
											<option value="2" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==2}">selected</c:if> >欧元</option>
											<option value="4" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==4}">selected</c:if> >英镑</option>
											<option value="5" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==5}">selected</c:if> >日元</option>
											<option value="6" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==6}">selected</c:if> >美元</option>
											<option value="15" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==15}">selected</c:if> >新台币</option>
											<option value="17" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==17}">selected</c:if> >阿联酋迪拉姆</option>
											<option value="18" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==18}">selected</c:if> >澳元</option>
											<option value="19" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==19}">selected</c:if> >澳门元</option>
											<option value="20" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==20}">selected</c:if> >埃及磅</option>
											<option value="21" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==21}">selected</c:if> >俄罗斯卢布</option>
											<option value="22" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==22}">selected</c:if> >丹麦克朗</option>
											<option value="23" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==23}">selected</c:if> >新西兰元</option>
											<option value="24" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==24}">selected</c:if> >新加坡元</option>
											<option value="25" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==25}">selected</c:if> >文莱元</option>
											<option value="26" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==26}">selected</c:if> >匈牙利福林</option>
											<option value="27" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==27}">selected</c:if> >越南盾</option>
											<option value="28" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==28}">selected</c:if> >印度卢比</option>
											<option value="29" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==29}">selected</c:if> >印尼卢比</option>
											<option value="30" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==30}">selected</c:if> >智利比索</option>
											<option value="31" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==31}">selected</c:if> >瑞士法郎</option>
											<option value="32" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==32}">selected</c:if> >瑞典克朗</option>
											<option value="33" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==33}">selected</c:if> >斯里兰卡卢比</option>
											<option value="34" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==34}">selected</c:if> >泰铢</option>
											<option value="35" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==35}">selected</c:if> >肯尼亚先令</option>
											<option value="36" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==36}">selected</c:if> >老挝基普</option>
											<option value="37" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==37}">selected</c:if> >缅甸元</option>
											<option value="38" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==38}">selected</c:if> >马来西亚林吉特</option>
											<option value="39" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==39}">selected</c:if> >墨西哥元</option>
											<option value="40" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==40}">selected</c:if> >挪威克朗</option>
											<option value="41" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==41}">selected</c:if> >南非兰特</option>
											<option value="42" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==42}">selected</c:if> >加元</option>
											<option value="43" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==43}">selected</c:if> >韩元</option>
											<option value="44" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==44}">selected</c:if> >港元</option>
											<option value="45" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==45}">selected</c:if> >菲律宾比索</option>
											<option value="46" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==46}">selected</c:if> >柬埔寨瑞尔</option>
											<option value="47" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==47}">selected</c:if> >哥伦比亚比索</option>
											<option value="49" <c:if test="${proObj.b2cProductDetail.b2cMoneyUnitId==49}">selected</c:if> >新土耳其里拉</option>
										</select>
										<%--<span class="dpl-tip-inline-warning">--%>
										<%--发货地只能1-200字--%>
										<%--</span>--%>
									</p>

									<p class="blank10" style="display: none"></p>
									<p class="p1" style="display: none"><i class="c_red">*</i>货源种类：</p>
									<p class="s_1" style="display: none">
										<select name="b2cProductDetail.b2cSupply">
											<option value="1" >一般贸易</option>
											<option value="11">海外直邮</option>
											<option value="12">保税区发货</option>
											<option value="21">韩国直邮</option>
											<option value="50">海外预售</option>
											<option value="51" selected="selected">第三方国际发货(POP)</option>
										</select>
										<%--<span class="dpl-tip-inline-warning">--%>
										<%--发货地只能1-200字--%>
										<%--</span>--%>
									</p>

									<div style="display: none;">
										<p class="blank10"></p>
										<p class="p1"><i class="c_red">*</i>行邮税（%）：</p>
										<p class="s_1">
											<select name="b2cProductDetail.tariff">
												<option value="0" selected="selected">无</option>
												<option value="10">10</option>
												<option value="20">20</option>
												<option value="30">30</option>
												<option value="50">50</option>
											</select>
											<%--<span class="dpl-tip-inline-warning">--%>
											<%--发货地只能1-200字--%>
											<%--</span>--%>
										</p>
									</div>

									<%-- 新加 b2c字段开始部分 end--%>

									<p class="blank10"></p>
										<p class="p1">保质期：</p>
										<p class="t2">
											<input name="supplierProductDetail.sheilLife" type="text"
												style="width:200px"
												disabled="disabled"
												value="${proObj.supplierProductDetail.sheilLife}">
													<c:if test="${proObj.supplierProductDetail.sheilLifeType==2}">年</c:if>
													<c:if test="${proObj.supplierProductDetail.sheilLifeType==1}">月</c:if>
													<c:if test="${proObj.supplierProductDetail.sheilLifeType==0}">天</c:if>
											</select>
										</p>
								</div>
								
								
								<p class="blank10"></p>
								<p class="blank10" style="display: none;"></p>
									<p class="p1" style="display: none;">海关税则代码：</p>
									<p class="t2" style="display: none;">
										<textarea id="customCode"  disabled="disabled" style="width: 400px; height: 150px;"  name="supplierProductDetail.customCode">${proObj.supplierProductDetail.customCode}</textarea>
									</p>
									<span class="dpl-tip-inline-warning">海关税则不能大于200字</span>
									<p class="blank10"></p>
								<p class="blank10"></p>

								<p class="blank10" style="display: none;"></p>
									<p class="p1" style="display: none;">商品备注：</p>
									<p class="t2" style="display: none;">
										<textarea id="remark" disabled="disabled"  style="width: 400px; height: 150px;"  name="supplierProduct.remark">${proObj.supplierProduct.remark}
										</textarea>
									</p>
									<p class="blank10"></p>
							</div>
						</div>
						
						<p class="blank15" style="display: none;"></p>
							<div  class="z" id="zizhi" style="display: none;">
								
								<div class='wenzi'>证明资质的图片:</div>

								<ul id="00_img">
									<c:forEach items="${qualificationUrl}" var="qualification">
										<li class="img-1">
											<div class="p-img"><img src="${qualification}"></div>
										</li>
									</c:forEach>

								</ul>
							</div>
							<p class="blank15"></p>
						
						
						
						<div class="jinben">
							<div class="chanp">
								<p>产品规格:</p>
							</div>
							
							<div class="p3">
							<!-- 规格属性 -->
							<c:forEach items="${proObj.supplierProductAttrDTOs }" var="attr" varStatus="saleVs">
								<c:if test="${attr.supplierProductAttr.buyAttr == 1}">
									<!-- 购买属性 -->
									<div class="yanse">
										<div class="yanse1" title="${fn:escapeXml(attr.supplierProductAttr.attrNameCn)}">
											<span>*</span>${fn:escapeXml(attr.supplierProductAttr.attrNameCn)}： 
										</div>
										<div class="yanse2">
											<p>
												<c:forEach items="${attr.supplierProductAttrvals}" var="av" varStatus="vars">
													<span title="${fn:escapeXml(av.lineAttrvalNameCn)}">
													<input type="checkbox" disabled="disabled"
														name="supplierProductAttrDTOs[${saleVs.index}].supplierProductAttrvals[${vars.index}].lineAttrvalNameCn"
														<c:if test="${av.isProdAttr}">checked</c:if>
														value="${fn:escapeXml(av.lineAttrvalNameCn)}" id="${vars.index}">${fn:escapeXml(av.lineAttrvalNameCn)}</span>
												</c:forEach>
											</p>
										</div>
									</div>
									<br>
								</c:if>

								<c:if test="${attr.supplierProductAttr.saleAttr==1}">
									<!-- 规格属性 -->
									<div class="chim">
										<div class="chim1" title="${fn:escapeXml(attr.supplierProductAttr.attrNameCn)}">
											<span>*</span> ${fn:escapeXml(attr.supplierProductAttr.attrNameCn)}： 
										</div>
										<div class="chim2">
											<p>
												<c:forEach items="${attr.supplierProductAttrvals}" var="av" varStatus="vs">
													<span title="${fn:escapeXml(av.lineAttrvalNameCn)}">
													<input type="checkbox" disabled="disabled"
														name="supplierProductAttrDTOs[${saleVs.index}].supplierProductAttrvals[${vs.index}].lineAttrvalNameCn"
														value="${fn:escapeXml(av.lineAttrvalNameCn)}" 
														<c:if test="${av.isProdAttr}">checked="checked"</c:if>
														id="${vs.index}">
														${fn:escapeXml(av.lineAttrvalNameCn)}
														</span>
												</c:forEach>
											</p>
										</div>
									</div>
								</c:if>
							</c:forEach>
							<!-- 规格属性 -->
							
							
							<div class="z">
								<div id="d"></div>
							</div>
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
							<%--<p class="p1">交易信息：</p>--%>
							<div class="p3" style="display: none;">
								<p class="blank5"></p>
								<p class="t1"><i class="c_red">*</i>计量单位：</p>
								<p class="s_1">
									<input type="text" id="measure" disabled="disabled" value="${fn:escapeXml(proObj.supplierProductPackage.measureCname)}">
								</p>
								<p class="blank5"></p>
								<p class="t1"><i class="c_red">*</i>计价单位：</p>
								
								<p class="s_1">
									<input type="text" id="price" disabled="disabled" value="${fn:escapeXml(proObj.supplierProductSaleSetting.moneyUnitNameCn)}">
								</p>
								<p class="blank5"></p>
								
								
								<div <c:if test="${supplierType eq 3 }">style="display: none"</c:if> >
									<p class="t1"><i class="c_red">*</i> 价格类型：</p>
									<div class="select-quote" style="margin-left:120px" id="priceTypes">
										
										<input type="radio"  disabled="disabled"  name="priceType" value="0" <c:if test="${proObj.supplierProductDetail.priceType==0}">checked</c:if>  id="priceType"/>
										<strong>FOB价格</strong>&nbsp;&nbsp;离岸港口名称<input  id="priceType0text"  disabled="disabled" type="text" name="fobPort" <c:if test="${proObj.supplierProductDetail.priceType==0}">value="${proObj.supplierProductDetail.portName}"</c:if> >
										<span id="priceType0warning" class="dpl-tip-inline-warning"></span> 
										<div class="clear"></div>
										<input type="radio"  disabled="disabled"  name="priceType" value="1" <c:if test="${proObj.supplierProductDetail.priceType==1}">checked</c:if> id="priceType"/>
										<strong>CIF价格</strong>&nbsp;&nbsp;&nbsp;到岸港口名称<input id="priceType1text"  disabled="disabled" type="text" name="cifPort" <c:if test="${proObj.supplierProductDetail.priceType==1}">value="${proObj.supplierProductDetail.portName}"</c:if>>
										<span id="priceType1warning" class="dpl-tip-inline-warning"></span> 
										<div class="clear"></div>
										<input type="radio"  disabled="disabled"  name="priceType" value="2" <c:if test="${proObj.supplierProductDetail.priceType==2}">checked</c:if> id="priceType"/>
										<strong>EXW价格</strong>
									</div>
									<p class="blank5"></p>
									
									
									
									<p class="t1">
										<i class="c_red">*</i>报价：</p>
											<div class="select-quote">
											<input type="radio" name="cost"  disabled="disabled" id="pic_count" class="cp1" value="1"/>
											
											<strong>按产品数量报价</strong>
											<input type="radio" name="cost" disabled="disabled" id="pic_sku" value="2" class="cp2" />
											<strong>按产品规格报价</strong>
									</div>

								<div class="tqz">
									<!--<p class="tq1"><i class="c_red">*</i>价格区间：</p>-->

									<div class="tq2">
										<p class="pp">
											
										</p>
									</div>
									<div class="g">
										<p class="blank"></p>
										<div class="blank10"></div>
										<div class="tab_box">
											最小起订量：<input type='text' disabled="disabled" name='minNum' value="${proObj.supplierProductSaleSetting.minWholesaleQty }">
											<table id="tb-speca-quotation" border="0" cellpadding="0"
												cellspacing="0">
												<colgroup>
													<col class="color">
													<col class="size">
													<col class="price">
													<col class="operate">
												</colgroup>
												<thead>
													<tr>
												<th>
														<c:forEach items="${proObj.supplierProductAttrDTOs }" var="attr" varStatus="saleVs">
														<c:if test="${attr.supplierProductAttr.buyAttr == 1}">
															${fn:escapeXml(attr.supplierProductAttr.attrNameCn)}
														</c:if>
													</c:forEach>
													</th>
													
													<c:forEach items="${proObj.supplierProductAttrDTOs }" var="attr" varStatus="saleVs">
														<c:if test="${attr.supplierProductAttr.saleAttr == 1}">
													<th>${fn:escapeXml(attr.supplierProductAttr.attrNameCn)}</th>
														</c:if>
													</c:forEach>
														<th><span class="c_red"> *</span>单价（元）<br />
														
														<input id="same_price" type="hidden"></th></th>
													</tr>
												</thead>
												<tbody>
												</tbody>

											</table>

										</div>
										<!-- 表格 end -->



										<p class="blank15"></p>
									</div>
									<p class="blank10"></p>
								</div>
								
								
								
							<!-- 	<p class="blank15"></p> -->
								<p class="blank5"></p>
								<p class="t1">
								<i class="c_red">*</i> 订单收集类型：</p>
								<div class="select-quote">
								<input type="radio" disabled="disabled" name="supplierProductDetail.orderType" <c:if test="${proObj.supplierProductDetail.orderType==1}">checked</c:if>  value="1"  onchange="changeOrderType('1')"/>
								<strong>现货库存</strong>
								<input type="radio" disabled="disabled" name="supplierProductDetail.orderType" <c:if test="${proObj.supplierProductDetail.orderType==0}">checked</c:if> value="0" onchange="changeOrderType('2')"/>
								<strong>收集订单</strong>
								</div>
								
							</div>
						
						<div id="collection"  <c:if test="${proObj.supplierProductDetail.orderType!=0}">style="display:none"</c:if>>
						<p class="blank15"></p>
						<p class="t1"><i class="c_red">*</i>预计发货日期：</p>
						<p class="s_1">
							<input type="text" disabled="disabled" name="supplierProductDetail.deliverDate" value="<fmt:formatDate value="${proObj.supplierProductDetail.deliverDate}" pattern="yyyy-MM-dd"/>"
									readonly="readonly" id="delivery" onClick="WdatePicker({minDate:'%y-%M-%d'})">
							<span id="deliveryDateWarning" class="dpl-tip-inline-warning"></span>	
							</p>
							<p class="blank15"></p>
							<p class="t1"><i class="c_red">*</i>生产能力：</p>
							<p class="s_3">
								<input id="produceNum" disabled="disabled"  type="text" name="supplierProductDetail.produceNum" value="${proObj.supplierProductDetail.produceNum}"/><span class="danwei">${fn:escapeXml(proObj.supplierProductPackage.measureCname)}</span>
								<select name="supplierProductDetail.produceType" disabled="disabled" >
										<option value="0" <c:if test="${proObj.supplierProductDetail.produceType==0}">selected</c:if>>天</option>
										<option value="4" <c:if test="${proObj.supplierProductDetail.produceType==4}">selected</c:if>>周</option>
										<option value="1" <c:if test="${proObj.supplierProductDetail.produceType==1}">selected</c:if>>月</option>
										<option value="2" <c:if test="${proObj.supplierProductDetail.produceType==2}">selected</c:if>>年</option>
								</select>
									<span id="produceNumWarning" class="dpl-tip-inline-warning"></span> 
									<br>
							</p>
																<p class="blank15"></p>
								<p class="t1"><i class="c_red">*</i>最后收单日期：</p>
								<p class="s_1">
								<input type="text" name="supplierProductDetail.receiveDate" disabled="disabled"
									readonly="readonly" id="closing" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'delivery\')}',minDate:'%y-%M-%d'})"
									value="<fmt:formatDate value="${proObj.supplierProductDetail.receiveDate}" pattern="yyyy-MM-dd"/>"
									>
										<span id="closingWarning" class="dpl-tip-inline-warning"></span> 
									
							</p>
							<span id="orderDate" class="dpl-tip-inline-warning"></span> 
								<p class="blank15"></p>
								<p class="t1"><i class="c_red">*</i>最大收单量：</p>
								<p class="s_1">
									<input  id="maxProdNum" type="text" name="supplierProductDetail.maxProdNum" disabled="disabled" value="${proObj.supplierProductDetail.maxProdNum}">
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
					
								<!-- 	<span style="color:red;">请如实填写条形码</span> -->
									<table cellspacing="0" cellpadding="0" border="0" style="margin-top:10px;" id="tb-tiaoxingma" width="100%">
										<colgroup>
					            			<!-- <col class="color">
					                       	<col class="size">
						 					<col class="price"> -->
											<!-- <col class="operate">	 -->	
										</colgroup>
										<thead>
											<tr >
							   				<th>
														<c:forEach items="${proObj.supplierProductAttrDTOs }" var="attr" varStatus="saleVs">
														<c:if test="${attr.supplierProductAttr.buyAttr == 1}">
															${fn:escapeXml(attr.supplierProductAttr.attrNameCn)}
														</c:if>
													</c:forEach>
													</th>
													
													<c:forEach items="${proObj.supplierProductAttrDTOs }" var="attr" varStatus="saleVs">
														<c:if test="${attr.supplierProductAttr.saleAttr == 1}">
													<th>${fn:escapeXml(attr.supplierProductAttr.attrNameCn)}</th>
														</c:if>
													</c:forEach>
												<th><span class="c_red"> *</span>条形码<br /></th>
												<th style="display: none;">条形码图片</th>
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
							<!-- b2c 价格录入 start-->
							<div class="blank20"></div>
							<p class="p1">商品价格信息：</p>
							<div class="p3">
								<p class="blank5"></p>

								<div class="tab_box" style="margin-left:60px;" id="skuPriceTable">
									<table cellspacing="0" cellpadding="0" border="0" style="margin-top:10px;" id="tb-skuprice" width="100%">
										<colgroup>
											<!-- <col class="color">
											<col class="size">
											<col class="price">
											<col class="tiao"> -->
											<!-- <col class="operate"> -->

										</colgroup>
										<thead>
										<tr>
											<th>
														<c:forEach items="${proObj.supplierProductAttrDTOs }" var="attr" varStatus="saleVs">
														<c:if test="${attr.supplierProductAttr.buyAttr == 1}">
															${fn:escapeXml(attr.supplierProductAttr.attrNameCn)}
														</c:if>
													</c:forEach>
													</th>
													
													<c:forEach items="${proObj.supplierProductAttrDTOs }" var="attr" varStatus="saleVs">
														<c:if test="${attr.supplierProductAttr.saleAttr == 1}">
													<th>${fn:escapeXml(attr.supplierProductAttr.attrNameCn)}</th>
														</c:if>
													</c:forEach>
											<th style="display: none;">商品货号</th>
											<th><i class="c_red">*</i>市场价(￥)</th>
											<th><i class="c_red">*</i>零售价(￥)</th>
											<th style="display: none;"><i class="c_red">*</i>翼支付价(￥)</th>
											<th><i class="c_red"></i>
											<c:if test="${proObj.supplierProduct.prodType!=5&&proObj.supplierProduct.prodType!=6 }">
											支付比例(%)
											</c:if>	
											<c:if test="${proObj.supplierProduct.prodType==6 }">
											现金金额
											</c:if>
											</th>
											<th style="display: none;"><i class="c_red"></i>分红额度</th>
										</tr>
										</thead>
									</table>
								</div>
								<span class="dpl-tip-inline-warning" style="margin-left: 60px;"></span>
								<!-- 表格 end -->
								<p class="blank15"></p>
							</div>
							<!-- b2c 价格录入 end-->
							<div class="mingxi" style="display: none;">
								<div class="m1">商品明细：</div>
								<div class="mm">
									<p class="m2">
										<span>包装清单：</span>
										<textarea disabled="disabled" name="supplierProductDetail.packingList">${fn:escapeXml(proObj.supplierProductDetail.packingList)}</textarea>
									</p>
									<p class="m3">
										<span>售后服务：</span>
										<textarea disabled="disabled" name="supplierProductDetail.salesServiceDescription">${fn:escapeXml(proObj.supplierProductDetail.salesServiceDescription)}</textarea>
									</p>
									<p class="m4">
										<span  class="s1">售后电话：</span>
										<input type="text" disabled="disabled"
											name="supplierProductDetail.salesCalls"
											value="${proObj.supplierProductDetail.salesCalls}" />
									</p>
									
								</div>
							</div>
						</div>


						<!-- 文本编辑框 -->

						<div class="blank30"></div>
						<div class="i_box">
							<h2>图文详情</h2>
							<div class="blank20"></div>
							<!--style给定宽度可以影响编辑器的最终宽度-->
							<div style="max-width:820px; height:auto; overflow:auto;" id="attach">
								<c:catch var="catchAtach">
									<c:import url="${fileurl}" charEncoding="utf-8"></c:import>
								</c:catch>
							</div>
							</div>
						<!-- 文本编辑框结束 -->
						<div class="blank"></div>
						<!-- 填写基本信息  -->


						<!--有审核标记才可以审核-->
						<c:choose>
							<c:when test="${revewFlag==1}">
								<!-- 项目 -->
								<div class="blank30"></div>
								<div class="blank30"></div>
								<div class="i_box">
									<h2>商品审核</h2>
									<div class="mingxi">
										<div class="mm">
											<p class="m2">
												<span>审核意见：</span>
												<textarea id="rejectReason" name="rejectReason" cols="60" rows="5"></textarea>
											</p>
										</div>
									</div>
								</div>
							</c:when>
						</c:choose>
						<div class="blank30"></div>
					</div>
					<!-- 内容 end -->
					<div class="btn_box">
						<div align="center">
							<!--有审核标记才出现审核按钮-->
							<c:if test="${revewFlag==1}">
								<button id="reivewSuccesssj" class="fabu_btn" type="button" onClick="reivewBtnsj()">商家信息
								</button>
								<button id="reivewSuccess" class="fabu_btn" type="button" onClick="reivewBtn(1)">审核通过
								</button>
								<button id="reivewFailure" class="fabu_btn" type="button" onClick="reivewBtn(2)">审核不通过
								</button>
							</c:if>
							<!--<button id="reivewBack" class="fabu_btn" type="button" onClick="reivewBtn(0)">返回</button>-->
							<button id="reivewBack" class="fabu_btn" type="button" onClick="history.back(-1)">关闭</button>
						</div>
					</div>
				</div>
				<!-- 边框 end -->
			</div>
			<div class="clear"></div>
			<p class="blank30"></p>
		</div>
		</div>
</div>
</div>

<div class="lightbox" id="goout-box-underxg" style="position:fixed">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box" onclick="closexj1()"></div>
		<div class="lightbox-box-hd">
			<h2>审核成功请设置商品限购标签:</h2>
		</div>
		<div class="lightbox-box-bd">
			<form id="productIllegalUnderShelfxg">
				<h3 ></h3>
			    <br/>
			   	<h3 id="xgid"></h3>	
				<input type="hidden"  value="" id="productIllegalUnderShelfIdxg" name="pid">
			</form>
		</div>
		<br/><br/><br/>
		<div class="lightbox-box-bar">
			<a href="javascript:void(0);" class="lightbox-btn true-btn" onclick="productUnderxg()">提 交</a>
			<span style="margin-left: 20px;color: red;" id="boxwarnunder"></span>
		</div>
	</div>
</div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp"%>

<div id="lightboxOverlay" class="lightboxOverlay" style="display: none;width: 1903px; height: 3305px;"></div>
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

<script type="text/javascript" src="${path}/commons/js/product/popShowProduct.js"></script>
<script type="text/javascript" src="${path}/product/ueditor.config.js"></script>
<script type="text/javascript" src="${path}/product/ueditor.all.js"></script>
<script type="text/javascript" src="${path}/commons/js/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript" src="${path}/commons/js/uploadify/queue.js"></script>
<script type="text/javascript" src="${path}/commons/js/product/tooltip.js"></script>

<!--引入JS-->
<script type="text/javascript" src='${path}/product/third-party/webuploader/webuploader.js'></script>
<script type="text/javascript" src="${path}/commons/js/product/webuploaderhandler.js"></script>

<script type="text/javascript" src="${path }/commons/js/swfUploadEventHandler.js"></script>
<script type="text/javascript" src="${path}/commons/js/lightbox.min.js"></script>
<%--<script type="text/javascript" src="${path }/commons/js/product/popShangtoShow.js"></script>--%>
</body>
</html>

	<script type="text/javascript">
	
		var  CONTEXTPATH  = "${path}";

		/* 限购设置*/
		function productIllegalUnderShelfxg(productId,stopType){
			//商品ID置空
			$("#productIllegalUnderShelfIdxg").val('');
			$("#xgid").html('')
			var list=null;
			var pid_array = new Array();
			if(productId==""||productId == undefined){
				var data;
				if(stopType=="0"){
					data=$("input[name='topProB2B']:checked")
				}else{
					data=$("input[name='topProB2C']:checked")
				}
				data.each(function() {
					pid_array.push($(this).attr("id"));
				}); 
				if(pid_array.toString().length==0){
					alert("请先选择商品。");
					return false;
				}
			}else{
				pid_array.push(productId);
			}
			if(pid_array.length>0){
				
				var join=pid_array.join(",");
				$("#productIllegalUnderShelfIdxg").val(join);
				$("#productIllegalUnderShelfIdxg").attr('stopType',stopType);
				$("#goout-box-underxg").css("display","block");
				$(function(){
					$.ajax({
						type : "get", 
						url : CONTEXTPATH+"/POPproduct/productxg", 
						success : function(msg){
							list=msg;
							$(list).each(function(index){
								//alert(list[index].tagName)
								$("#xgid").append('&nbsp;').append("<input type='checkbox' id='xg"+index+"'  name='xgCodeId' value='"+list[index].tagCode+"'>").append(list[index].tagName);
//								alert(index);
							})
							$.ajax({
								type : "get", 
								url : CONTEXTPATH+"/POPproduct/productxgselect?pid="+join, 
								success : function(msg){
									var rows=document.getElementsByName("xgCodeId");
									for (var i = 0; i < rows.length; i++) {
										$(msg).each(function(index){
											if(rows[i].value==msg[index].tagCode){
												console.log(this);
												$("#xg"+i+"").attr("checked", true);
											}
										});
									}
								}
							})
						}
					})
				});
			}
		}
		function productUnderxg(){
			
			//商品ID
			var pid = $('#productIllegalUnderShelfIdxg').val();
			if(pid==""){//商品ID为空提示数据异常
				$('#boxwarnunder').text("数据异常！");
				return false;
			}else{
				var stopType = $("#productIllegalUnderShelfIdxg").attr('stopType');
				var pid_array = new Array();
				pid_array.push('pid='+pid);
				$("#goout-box-underxg").css("display","none");
				$.dialog.confirm('确定给商品添加限购标签么？', function(){
					var rows=document.getElementsByName("xgCodeId");
					var ids=[];
					for (var i = 0; i < rows.length; i++) {
						if(rows[i].checked){
							ids.push(rows[i].value);
						}
						
					}
					//alert(ids);
					$.ajax({
						type : "get", 
						url : CONTEXTPATH+"/POPproduct/updatetagsxg?ids="+ids+"&pid="+pid, 
						success : function(msg) { 
							if(msg==1){
								
								window.location.href="${path}/POPproduct/getPro?isReivew=2&currentPageId=1";
							}else{
								alert("操作失败 ，请稍后再试");
							}
						}
					});
				},function(){
					window.location.href="${path}/POPproduct/getPro?isReivew=2&currentPageId=1";
				});
				
			}
		}
		/**
		 * 审核按钮方法
		 * @param operationType
         */
		function reivewBtn(operationType){
		    if(operationType==0){
		        var pageId=$("#currentPageId").val();
		        if(pageId=="") pageId="1";
                window.location.href="${path}/POPproduct/getPro?isReivew=1&currentPageId="+pageId;
                return;
			}
			// 审核失败需要填写审核意见
			var rejectReason = $("#rejectReason").val();
			if(operationType == 2){
				if($.trim(rejectReason).length < 1){
					alert("审核不通过时，请填写审核意见!");
				} else if($.trim(rejectReason).length > 200){
					var different = 200 - $.trim(rejectReason).length;
					if(different < 0){
						alert("审核意见不能超过200字,您超了 "+(-(different))+" 个字!");
					} else {
						ajaxReivew('${proObj.productId}',operationType,rejectReason);
					}
				} else {
					ajaxReivew('${proObj.productId}',operationType,rejectReason);
				}
			}else{
				ajaxReivew1('${proObj.productId}',operationType,rejectReason);
			}
		}
		function closexj1(){
			 window.location.href="${path}/POPproduct/getPro?isReivew=2&currentPageId=1";
		}
		/**
		 * 审核请求操作
		 */
		function  ajaxReivew(pid,operationType,rejectReason){
			var data_json = {
				'pid':pid,
				'operationType':operationType,
				'rejectReason':rejectReason
			};
			var currentPage = $("#currentPageId").val();
			$.ajax({
				type : "post",
				url : "${path}/POPproduct/review",
				data:data_json,
				dataType:"html",
				success : function(msg) {
					if(msg == '1'){
				 		 tipMessage("操作成功，关闭当前页面！",function(){
							window.location.href="${path}/POPproduct/getPro?isReivew=1&currentPageId="+currentPage;
						}); 
						//$("#goout-box-underxj1").css("display","block");
					}else if(msg == '2'){
						alert("操作失败,请稍后再试");
					}else if(msg == '3'){
						alert("审核不通过时，请填写审核意见");
					}

				},
				error: function(jqXHR, textStatus, errorThrown){
					alert("对不起，数据异常请稍后再试!");
				}
			});
		}
		function  ajaxReivew1(pid,operationType,rejectReason){
			var data_json = {
				'pid':pid,
				'operationType':operationType,
				'rejectReason':rejectReason
			};
			var currentPage = $("#currentPageId").val();
			$.ajax({
				type : "post",
				url : "${path}/POPproduct/review",
				data:data_json,
				dataType:"html",
				success : function(msg) {
					if(msg == '1'){
				 		/* tipMessage("审核成功，关闭当前页面！",function(){
							window.location.href="${path}/POPproduct/getPro?isReivew=2&currentPageId="+currentPage;
						}); */
                        if(${pdtp==6} ){
                            tipMessage("审核成功，关闭当前页面！",function(){
                                window.location.href="${path}/POPproduct/getPro?isReivew=2&currentPageId="+currentPage;
                            });
                        }else{
                            productIllegalUnderShelfxg(pid,1);
                            $("#goout-box-underxg").css("display","block");
                        }

					}else if(msg == '2'){
						alert("操作失败,请稍后再试");
					}else if(msg == '3'){
						alert("审核不通过时，请填写审核意见");
					}

				},
				error: function(jqXHR, textStatus, errorThrown){
					alert("对不起，数据异常请稍后再试!");
				}
			});
		}

	$(document).ready(function(){
		var url_array = ${jsonImg};
		$.each(url_array,function(n,value){
			adduploadimg(value[1],value[2],value[3]); 
		});


 	 		var skuCode = ${skusCode};

 	 		var skuCode_array = new Array();
			$.each(skuCode,function(key,value){
				skuCode_array.push(value);
				var new_array = new Array();
				new_array.push(skuCode_array);
				_fShowTableInfo(skuCode,"tb-tiaoxingma");
				_fShowTableInfo(skuCode,"skuPriceTable");
			});
		});  
	function reivewBtnsj(){
		pid='${proObj.productId}';
		$.ajax({
			type : "get",
			url : "${path}/POPproduct/reviewsj?pid="+pid,
			success : function(msg) {
				var pid=msg;
				topage(pid);
						
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("对不起，数据异常请稍后再试!");
			}
		});
	}
	function topage(pid){
		window.open("${path}/user/viewInfo?source=1&id2="+pid);
	};
</script>
