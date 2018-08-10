<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>UNICORN-修改商品</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	
	<link rel="stylesheet" href="${path }/commons/css/shang.css">
	<link rel="stylesheet" href="${path }/commons/js/uploadify/uploadify.css">
	
	<script type="text/javascript" src="${path }/product/editor_config.js"></script>
	<script type="text/javascript" src="${path }/product/editor_all.js"></script>
	
	
	<script type="text/javascript" src="${path }/commons/js/uploadify/jquery.uploadify.js"></script>
	<script type="text/javascript" src="${path }/commons/js/uploadify/queue.js"></script>
	
	<script type="text/javascript" src="${path }/commons/js/swfUploadEventHandler.js"></script>
	<script type="text/javascript" src="${path }/commons/js/updateNewSku.js"></script>
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
<!-- 		<div class="right f_l">
	边框start
边框start -->
	<form method="post" id="updateSku" enctype="multipart/form-data">
		<input type="hidden" name="dealerProduct.prodLineId" value="${fn:escapeXml(proObj.dealerProduct.prodLineId)}">
		<input type="hidden" name="productId" value="${fn:escapeXml(proObj.productId)}">
		<input type="hidden" name="dealerProduct.supplierid" value="${fn:escapeXml(proObj.dealerProduct.supplierid)}">
		<input type="hidden" name="dealerProduct.catePubId" value="${fn:escapeXml(proObj.dealerProduct.catePubId)}">
		<input type="hidden" name="dealerProduct.cateDispId" value="${fn:escapeXml(proObj.dealerProduct.cateDispId)}">
		
		<c:forEach items="${proObj.dealerProductSkuDTOs}" var="skuHide" varStatus="skui">
			<input type="hidden"  name="dealerProductSkuDTOs[${skui.index }].dealerProductSku.productSkuId" value="${fn:escapeXml(skuHide.dealerProductSku.productSkuId)}"/>
			<input type="hidden"  name="dealerProductSkuDTOs[${skui.index }].dealerProductPriceMap.prodPriceId" value="${fn:escapeXml(skuHide.dealerProductPriceMap.prodPriceId)}"/>
			<input type="hidden"  name="dealerProductSkuDTOs[${skui.index }].dealerProductPriceMap.priceId" value="${fn:escapeXml(skuHide.dealerProductPriceMap.priceId)}"/>
			<input type="hidden"  name="dealerProductSkuDTOs[${skui.index }].dealerProductPriceMap.supplierprice" value="${fn:escapeXml(skuHide.dealerProductPriceMap.supplierprice)}"/>
			<input type="hidden"  name="dealerProductSkuDTOs[${skui.index }].dealerProductSku.skuNameCn" value="${fn:escapeXml(skuHide.dealerProductSku.skuNameCn)}"/>
			<input type="hidden"  name="dealerProductSkuDTOs[${skui.index }].dealerProductSku.skuNameEn" value="${fn:escapeXml(skuHide.dealerProductSku.skuNameEn)}"/>
			<input type="hidden"  name="dealerProductSkuDTOs[${skui.index }].dealerProductSku.skuId" value="${fn:escapeXml(skuHide.dealerProductSku.skuId)}"/>
			<input type="hidden"  name="dealerProductSkuDTOs[${skui.index }].dealerProductSku.istate" value="${fn:escapeXml(skuHide.dealerProductSku.istate)}"/>
		</c:forEach>
		<div class="right f_l">
		
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品管理&nbsp;&gt;&nbsp;</p>
					<p>商品列表&nbsp;&gt;&nbsp;</p>
					<p class="c1">修改商品</p>
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
						<div class="blank15"></div>
						<div class="p_box">
							<p class="p1">
								<i class="c_red">*</i> 商品标题：
							</p>
							<p class="p2">
								<input type="text" required="required" name="pname"
									value="${fn:escapeXml(proObj.dealerProductBase.productname)}" disabled="disabled"  id="productinfo"> 
								<span class="dpl-tip-inline-warning">请填写 商品标题</span>
							</p>
							<div class="blank10"></div>
							
							<!-- <div class="p3"> -->
						<!-- 填写基本信息  -->
						<div class="jinben">
							<div class="chanp">
								<p>产品规格:</p>
							</div>
							<div class="p3">
							<p class="blank5"></p>
							<label style="color: red; margin-left:70px;">红色标记为新增单品</label>
							<p class="blank10"></p>
							<c:forEach items="${proObj.dealerProductAttrDTOs }" var="attr" varStatus="saleVs">
								<c:if test="${attr.dealerProductAttr.buyAttr == 1}">
									<!-- 购买属性 -->
									<div class="yanse">
										<div class="yanse1" title="${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}">
											<span>*</span>${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}： 
											<input type="hidden" name="buyIndex" value="${saleVs.index}">
											<input type="hidden" name="dealerProductAttrDTOs[0].dealerProductAttr.attrId"  value="${fn:escapeXml(attr.dealerProductAttr.attrId)}">
											<input type="hidden" name="dealerProductAttrDTOs[0].dealerProductAttr.prodAttrId"  value="${fn:escapeXml(attr.dealerProductAttr.prodAttrId)}">
											<input type="hidden" name="dealerProductAttrDTOs[0].dealerProductAttr.attrNameCn" value="${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}" id="${saleVs.index}">
											<input type="hidden" name="dealerProductAttrDTOs[0].dealerProductAttr.type" value="${fn:escapeXml(attr.dealerProductAttr.type)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[0].dealerProductAttr.style" value="${fn:escapeXml(attr.dealerProductAttr.style)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[0].dealerProductAttr.isneed" value="${fn:escapeXml(attr.dealerProductAttr.isneed)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[0].dealerProductAttr.buyAttr" value="${fn:escapeXml(attr.dealerProductAttr.buyAttr)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[0].dealerProductAttr.saleAttr" value="${fn:escapeXml(attr.dealerProductAttr.saleAttr)}">
										</div>
										<div class="yanse2">
											<p>
												<c:forEach items="${attr.dealerProductAttrvals}" var="av" varStatus="vars">
													<span title="${fn:escapeXml(av.lineAttrvalNameCn)}">
														<input type="hidden" name="dealerProductAttrDTOs[0].dealerProductAttrvals[${vars.index}].attrValId" value="${fn:escapeXml(av.attrValId)}" />
														<input type="hidden" name="dealerProductAttrDTOs[0].dealerProductAttrvals[${vars.index}].prodAttrValId" value="${fn:escapeXml(av.prodAttrValId)}" />
														<c:if test="${av.isProdAttr}">
														<input type="hidden" name="dealerProductAttrDTOs[0].dealerProductAttrvals[${vars.index}].lineAttrvalNameCn" value="${fn:escapeXml(av.lineAttrvalNameCn)}"/>
														<input type="hidden" name="dealerProductAttrDTOs[0].dealerProductAttrvals[${vars.index}].istate" value="${fn:escapeXml(av.istate)}"/>
														</c:if>
														<input type="checkbox"  disabled="disabled"  id="${vars.index}" value="${fn:escapeXml(av.lineAttrvalNameCn)}"
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
											<input type="hidden" name="dealerProductAttrDTOs[1].dealerProductAttr.attrId"  value="${fn:escapeXml(attr.dealerProductAttr.attrId)}">
											<input type="hidden" name="dealerProductAttrDTOs[1].dealerProductAttr.prodAttrId"  value="${fn:escapeXml(attr.dealerProductAttr.prodAttrId)}">
											<input type="hidden" name="dealerProductAttrDTOs[1].dealerProductAttr.attrNameCn" value="${fn:escapeXml(attr.dealerProductAttr.attrNameCn)}" id="${saleVs.index}">
											<input type="hidden" name="dealerProductAttrDTOs[1].dealerProductAttr.type" value="${fn:escapeXml(attr.dealerProductAttr.type)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[1].dealerProductAttr.style" value="${fn:escapeXml(attr.dealerProductAttr.style)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[1].dealerProductAttr.isneed" value="${fn:escapeXml(attr.dealerProductAttr.isneed)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[1].dealerProductAttr.buyAttr" value="${fn:escapeXml(attr.dealerProductAttr.buyAttr)}"> 
											<input type="hidden" name="dealerProductAttrDTOs[1].dealerProductAttr.saleAttr" value="${fn:escapeXml(attr.dealerProductAttr.saleAttr)}">
											
										</div>
										<div class="chim2">
											<p>
												<c:forEach items="${attr.dealerProductAttrvals}" var="av" varStatus="vs">
													<span title="${fn:escapeXml(av.lineAttrvalNameCn)}">
														<input type="hidden" name="dealerProductAttrDTOs[1].dealerProductAttrvals[${vs.index}].attrValId" value="${fn:escapeXml(av.attrValId)}" />
														<input type="hidden" name="dealerProductAttrDTOs[1].dealerProductAttrvals[${vs.index}].prodAttrValId" value="${fn:escapeXml(av.prodAttrValId)}" />
														<c:if test="${av.isProdAttr==true }">
														<input type="hidden" name="dealerProductAttrDTOs[1].dealerProductAttrvals[${vs.index}].lineAttrvalNameCn" value="${fn:escapeXml(av.lineAttrvalNameCn)}"> 
														<input type="hidden" name="dealerProductAttrDTOs[1].dealerProductAttrvals[${vs.index}].istate" value="${fn:escapeXml(av.istate)}"/>
														</c:if>
														<input isnew="${av.istate}" type="checkbox" disabled="disabled" <c:if test="${av.isProdAttr==true }"> checked="checked" </c:if> id='${vs.index}'  value="${fn:escapeXml(av.lineAttrvalNameCn)}">
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
							</div>
							<div class="z">
								<div id="d"></div>
							</div>

						</div>
					</div>
					<div class="blank"></div>
					</div>
					
				<!-- 	<div class="blank"></div> -->
					
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
								<select name="dealerProductPackage.measureid" disabled="disabled">
									<c:forEach items="${measure}" var="measure">
										<option value="${fn:escapeXml(measure.measureid)}" <c:if test="${proObj.dealerProductPackage.measureid == measure.measureid}">selected</c:if> >${fn:escapeXml(measure.cname)}</option>
									</c:forEach>
								</select></p>
								<p class="blank5"></p>
									
								<p class="t1">
									<i class="c_red">*</i>报价：</p>
								<div class="select-quote">
									<input type="radio" id="pic_count" name="cost" class="cp1" value="1"  disabled="disabled"/>
									<strong>按产品数量报价</strong>
									<input type="radio" id="pic_sku" name="cost" value="2" class="cp2" disabled="disabled"/>
									<strong>按产品规格报价</strong>
								</div>

								<div class="tqz" style="height:auto;">
									<div class="tq2">
										
										<p class="pp">
											<span class="b21">
											建议零售价：
										    <input type="text" name="onlyPrice" readonly="readonly" id="onlyPrice" style="width:69px; height:22px; border:1px solid #c8c8c8;" value="${fn:escapeXml(proObj.dealerProductSkuDTOs[0].dealerProductPriceMap.retailPrice)}">
											<span class="dpl-tip-inline-warning"></span>
											 </span>
											<span class="b2">
												起批量：
												<input type="text"  name='start'  id="startNum">
												<span class="danwei">
													${proObj.dealerProductPackage.measureCname}
												</span>
												及以上
												<input type="text"  name='pic'>
												元/
												<span class="danwei">
													${proObj.dealerProductPackage.measureCname}
												</span>
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
									<div class="g">
										<p class="blank"></p>
										<div class="blank10"></div>
										<div class="tab_box">
											<i class="c_red">*</i>最小起订量：
											<input type="text" id="minNum" readonly="readonly" name="minNum" style="width:150px; height:20px; line-height:20px; border:1px solid #c7c7c7;"  value="${fn:escapeXml(proObj.dealerProductSaleSetting.minWholesaleQty)}">
											<table id="tb-speca-quotation" border="0" cellpadding="0"
												cellspacing="0" style="margin-top:10px;">
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
														<input id="same_price" type="hidden"></th>
														<th>建议零售价</th>
													</tr>
												</thead>
												<tbody>
												</tbody>
											</table>
											
										</div>
										<span class="dpl-tip-inline-warning" id="g1"></span>
										<span class="dpl-tip-inline-warning" id="g2"></span>
										<span class="dpl-tip-inline-warning" id="g3"></span>
										<span class="dpl-tip-inline-warning" id="g4">建议零售价需要大于批发价</span>
										<!-- 表格 end -->
										<p class="blank15"></p>
									</div>
									<p class="blank10"></p>
								</div>

							</div>
								<div class="blank10"></div>	
								
								
								
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
											<th><span class="c_red"> *</span>sku重量<br />
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
					
						<div class="blank10"></div>
					</div>
			<p class="blank15"></p>	
				<div class="btn_box">
					<button type="button" class="fabu_btn" id="editProdSku">发布商品</button>
					<p class="clear"></p>
				</div>
				 </div>
				</div>
				</div>				
	</form>	
	
	 </div>
	  </div> 	
	</div>		
	</div>			
	<div class="blank10"></div>												
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	<!-- 底部 end -->
	<script type="text/javascript">
/* 		var responsedata;
		if('${responsedata}'!=""){
			if('${responsedata}'=='1'){
				clearsession();
				alert("保存成功，返回到商品列表！");
				window.location.href="../product/getPro";
			}
			if('${responsedata}'=='0'){
				clearsession();
				alert("保存失败，请检查后重试！");
			}
		
		}
	
		function clearsession(){
			$.ajax({
				 type : "post", 
	         	 url : "../product/clearSession", 
			});
				
		}; */
		$(document).ready(function(){
			var url_array;
			if('${jsonImg}'!=""){
				url_array = ${jsonImg};
				$.each(url_array,function(n,value){
					adduploadimg(value[1],value[2],value[3]); 
				});
			}

			if('${skuPriceAndCount}'!=''){
				var sku_array= ${skuPriceAndCount};

				var measure = $("select[name='dealerProductPackage.measureid'] option:selected").text();
				$.each(sku_array,function(keys,values){

					var statu = values.type;
					if(statu=='0'){
						changebox();
						$("#pic_count").attr("checked","checked");
						$('.pp span.b2').remove();
						$.each(values.start,function(key,value){
							var startLength = values.start.length;
							if(key==startLength-1){
								$('.pp').append( "<span class='b2'>起批量：<input type='text' name='start' value='"+value+"'><span class='danwei'>"+measure+"</span>及以上 <input name='pic' type='text'  value='"+values.pic[key]+"'> 元/<span class='danwei'>"+measure+"</span><span class='del'>删除</span></span>" );
							}else{
								$('.pp').append( "<span class='b2'>起批量：<input type='text' name='start' value='"+value+"'><span class='danwei'>"+measure+"</span>及以上 <input name='pic' type='text'  value='"+values.pic[key]+"'> 元/<span class='danwei'>"+measure+"</span></span>" );
							}
							var length = $(".pp .b2").length;
							if (length>=3){
								$(".b3").hide();
							}
							$(".g").hide();

						});

					}else{
						var pku = values.pic;
						$("#pic_sku").attr("checked","checked");
						$(".g").show();
						$(".tq2").hide();
						var pku_array = new Array();
						$.each(pku,function(key,value){
							pku_array.push(value);
							var new_array = new Array();
							new_array.push(pku_array);
							changebox();
							_fShowTableInfo(pku,"tb-speca-quotation"); 
						});
					}; 
				});
			}
			if('${showbarCodeJson}'!=''){
				var skuCode = ${showbarCodeJson};
				var skuCode_array = new Array();
				$.each(skuCode,function(key,value){
					skuCode_array.push(value);
					var new_array = new Array();
					new_array.push(skuCode_array);
					_fShowTableInfo(skuCode,"tb-tiaoxingma"); 
				});
			}


		})
	
	</script>
</body>
</html>
							
							
							
							