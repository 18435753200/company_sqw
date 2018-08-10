<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<script type="text/javascript">
	$(document).ready(function(){
			$("#all-b2b-selector").click(function(){
				if ($(this).attr("checked") == "checked") { // 全选 
					$("input[type='checkbox'][name='topProB2B']").each(function() { 
						$(this).attr("checked", true); 
					}); 
				}else{ // 反选 
					$("input[type='checkbox'][name='topProB2B']").each(function() { 
						$(this).attr("checked", false); 
					});
				}
			});
			$("#all-b2c-selector").click(function(){
				if ($(this).attr("checked") == "checked") { // 全选 
					$("input[type='checkbox'][name='topProB2C']").each(function() {
						if($(this).attr("disabled")){
                        }else{
							$(this).attr("checked", true);
						}
					}); 
				}else{ // 反选 
					$("input[type='checkbox'][name='topProB2C']").each(function() {
						if($(this).attr("disabled")){
						}else{
							$(this).attr("checked", false);
						}
					});
				}
			});
	});
	function addTagsAll(){
		$.dialog.confirm('确定给新增商品添加每日限购标签么？', function(){
		$.ajax({
			type : "post",
			url : "${path}/POPproduct/addTagsAll",
			success : function(msg) {
				window.location.reload();
			},
			error : function() {
				alert("加载失败，稍后再试。");
			}
		});
		});
	}
</script>
<table id="J_BoughtTable" class="bought-table" data-spm="9"
style="width:100%;">
	<thead>
		<tr class="col-name">
			<c:if test="${pb!=null && pb.parameter.auditStatus == 2 }">
				<th>商品&nbsp;&nbsp;&nbsp;(<i style="color: #f10180;font-size: 13px;">${pb.totalCount }</i>)&nbsp;条</th>
			</c:if>
			<c:if test="${pb2.parameter.auditStatus == 1 }">
				<th>商品&nbsp;&nbsp;&nbsp;(<i style="color: #f10180;font-size: 13px;">${pb2.totalCount }</i>)&nbsp;条</th>
			</c:if>
			<th>价格(元)</th>
 			<c:if test="${pb2.parameter.auditStatus == 1 }">
			<th>库存</th>
			</c:if>
			<th>发布时间</th>
			<th>修改时间</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
	</thead>
	<%-- <tbody class="data">
		<tr class="sep-row">
			<c:forEach items="${pb2.result}" var="pbr" begin="0" end="0">
			
				<td colspan="6" style="padding:5px 0 5px 0;">
				
						<div class="operations">
							<c:if test="${! empty pb2.result}">
									
								<c:if test="${!empty buttonsMap['POP货品列表-批量下架']  || !empty buttonsMap['POP货品列表-批量上架'] }">


									<c:if test="${(pbr.istate==0&&pbr.counterfeittypeid==2)||pbr.istate==3}">
										
										<input class="all-selector gg" id="all-b2b-selector" type="checkbox">
										
										<label>B2B全选/反选</label>
	
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAway()" role="button">B2B上架</a>
	
										<label>B2C全选/反选</label>
	
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAway()" role="button">B2C上架</a>
	
									</c:if>
	
									<c:if test="${pb2.parameter.auditStatus == 1 }">
										<input class="all-selector gg" id="all-b2c-selector" type="checkbox">
										<label>全选/反选</label>
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAway(1)" role="button">上架</a>
										<a class="tm-btn" href="javascript:void(0);" onclick="alertProductStopReason('',1)" role="button">下架</a>
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAwayxj(1)" role="button">批量星级</a>
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAwayxg(1)" role="button">批量限购</a>
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAwayzj(1)" role="button">批量赠券</a>
										<a class="tm-btn" href="javascript:void(0);" onclick="addTagsAll()" role="button">新增限购</a>
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAwayhh(1)" role="button" style="width:80px;">增加比例标签</a>
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAwayhd(1)" role="button" style="width:80px;">删除比例标签</a>
	
									</c:if>

								</c:if>
								
							</c:if>
							
						</div>
				</td>
				
			</c:forEach>

		</tr>
	</tbody> --%>
	
	<tbody class="data"  id="showListTbody">
										<input class="all-selector gg" id="all-b2c-selector" type="checkbox">
										<label>全选/反选</label>&nbsp;
	<c:if test="${pb2.parameter.auditStatus == 1 }">
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAway(1)" role="button">上架</a>&nbsp;
										<a class="tm-btn" href="javascript:void(0);" onclick="alertProductStopReason('',1)" role="button">下架</a>&nbsp;
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAwayxj(1)" role="button">批量星级</a>&nbsp;
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAwayxg(1)" role="button">批量限购</a>&nbsp;
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAwayzj(1)" role="button">批量赠券</a>&nbsp;
										<!-- <a class="tm-btn" href="javascript:void(0);" onclick="addTagsAll()" role="button">新增限购</a>&nbsp; -->
	
									</c:if>
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAwayhh(1)" role="button" style="width:80px;">增加比例标签</a>&nbsp;
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAwayhd(1)" role="button" style="width:80px;">删除比例标签</a>&nbsp;
<!----------------------------------------- pop商品审核 -->
			<c:if test="${pb.parameter.auditStatus == 2 }">
			<c:if test="${empty pb.result}">
				<tr>
					<td colspan="6">
						<center><img src="${path }/commons/images/no.png" /></center>
					</td>
				</tr>
			</c:if>
			
				<c:forEach items="${pb.result}" var="pbr">
					<tr class="order-hd" >
									
						<td colspan="6"><p style="width:166px;" title="${pbr.productId}">商品ID：${pbr.productId}</p>
							<c:if test="${not empty pbr.subBrand}">
							  <p title="${pbr.brand.nameCn}>>${pbr.subBrand.nameCn}" style="width:180px;">商品品牌：${pbr.brand.nameCn}>>${pbr.subBrand.nameCn}</p>
							</c:if> 
							<c:if test="${empty pbr.subBrand}">
							   <p title="${pbr.brand.nameCn}" style="width:180px;">商品品牌：${pbr.brand.nameCn}</p>
							</c:if>
							<c:if test="${pbr.counterfeittypeid == 1}">
								<%   
                        		   request.setAttribute("vEnter", "\n");   
								  request.setAttribute("aEnter", "</br>"); 
                      
                   				 %> 
								
								<button type="text" class="sr" onclick="showmsg('${fn:replace(pbr.description, vEnter , aEnter)}')">未通过原因</button>
							</c:if>
							<c:if test="${pbr.istate==0&&pbr.counterfeittypeid==2}">
							<c:if test="${pbr.stopReason eq '缺货' || pbr.stopReason eq '滞销' || pbr.stopReason eq '汰换' || pbr.stopReason eq '更新商品信息'}">
								
								<strong class="ss">下架原因 : ${fn:escapeXml(pbr.stopReason)}</strong>
							
							</c:if>
							<c:if test="${pbr.stopReason != '缺货' && pbr.stopReason != '滞销' && pbr.stopReason != '汰换' && pbr.stopReason != '更新商品信息'}">
								
								<button type="button" class="sr" onclick="showmsg('${fn:escapeXml(pbr.stopReason)}')">下架原因:其他</button>
							
							</c:if>
							</c:if>
							<c:if test="${pbr.counterfeittypeid==5}">
							<c:if test="${pbr.description eq '缺货' || pbr.description eq '滞销' || pbr.description eq '汰换' || pbr.description eq '更新商品信息'}">
								
								<strong class="ss">违规下架原因 : ${fn:escapeXml(pbr.description)}</strong>
							
							</c:if>
							<c:if test="${pbr.description != '缺货' && pbr.description != '滞销' && pbr.description != '汰换' && pbr.description != '更新商品信息'}">
								
								<button type="button" class="sr" onclick="showmsg('${fn:escapeXml(pbr.description)}')">违规下架原因:其他</button>
							
							</c:if>
							</c:if>
						</td>
					</tr>
					<tr class="order-bd">
						<td class="baobei" colspan="1">
						<input class="selector" type="checkbox" id="${pbr.productId}"
                                        		name="topProB2C" style="float:left; margin-top:13px; margin-top:8px\9;">
						    <a class="pic J_MakePoint">
						    <c:if test="${pbr.prodType==6 }">
							幸福购商品</c:if>
						    <c:if test="${pbr.prodType==5 }">
							家庭号商品</c:if>
							<img src="${pbr.imgURL}" alt="商品图片"> </a>
							<div class="desc" title="${fn:escapeXml(pbr.productName)}" style="float:left;">
								<p class="baobei-name">
									<i> ${fn:escapeXml(pbr.productName)}</i><br/>
									${pbr.subsupplierid }
									${pbr.measureid }
								</p>
							</div>

						</td>
						<td>
							<i>
								<span>${pbr.moneyUnitSymbols} 
									<c:choose>
										<c:when test="${pbr.productPriceMin==pbr.productPriceMax}">
											<fmt:formatNumber pattern="0.00#" value="${pbr.productPriceMin}"></fmt:formatNumber>
										</c:when>
										<c:otherwise>
											<fmt:formatNumber pattern="0.00#" value="${pbr.productPriceMin}"></fmt:formatNumber>
											 ~
											 <fmt:formatNumber pattern="0.00#" value="${pbr.productPriceMax}"></fmt:formatNumber>
										</c:otherwise>
									</c:choose>
									
								</span>
							</i>
						</td>
<%-- 						<td><i> ${pbr.futures } </i></td> --%>
						<td class="trade-status">${pbr.strCreatedate }</td>
						<td class="trade-status">${pbr.strOperationdate }</td>
						<c:if test="${pbr.counterfeittypeid==0}">
						<td class="b2"><i> 待审核 </i></td>
						</c:if>
						<c:if test="${pbr.counterfeittypeid==1}">
						<td class="b2"><i> 未通过 </i></td>
						</c:if>
						<c:if test="${pbr.counterfeittypeid==5}">
						<td class="b2"><i> 审核驳回 </i></td>
						</c:if>
						<td class="trade-operate">
<%-- 								<c:if test="${supplierId == pbr.supplierid }"> --%>
									<c:choose>

										<c:when test="${pbr.counterfeittypeid==0}">
											<c:if test="${! empty buttonsMap['POP货品列表-审核商品'] }">	
												<p><a class="tm-btn" href="${path}/POPproduct/initAudit?productId=${pbr.productId}&currentPageId=${pageBean.page}&prodType=${pbr.prodType}">审核</a></p>
												<input id="currentPageId" type="hidden" value="${pageBean.page }"/>
											</c:if>
											<p><a class="tm-btn" href="${path}/POPproduct/initPOPShow?catePubId=${pbr.catePubId}&productId=${pbr.productId}&currentPageId=${pageBean.page}">查看</a></p>
										</c:when>
										<c:otherwise>
											<p><a class="tm-btn" href="${path}/POPproduct/initPOPShow?catePubId=${pbr.catePubId}&productId=${pbr.productId}&currentPageId=${pageBean.page}">查看</a></p>
											<%-- <p><a class="tm-btn" href="javascript:void(0)" onclick="prodel('${pbr.productId}')">删除</a> --%>
										</c:otherwise>
									</c:choose>
							
<%-- 							</c:if> --%>
<%-- 							<c:if test="${supplierId != pbr.supplierid }"> --%>
<%-- 								<p><a class="tm-btn" href="showProduct?productId=${pbr.productId}">查看</a></p> --%>
<%-- 							</c:if> --%>
							
<%-- 							<p><a class="tm-btn" href="toEditUI?productId=${pbr.productId}&type=2">克隆</a></p> --%>
							</td>
					</tr>
					<tr style="height:10px;"></tr>
				</c:forEach>
			</c:if>
			
		<c:if test="${pb2.parameter.auditStatus == 1 }">
		<c:if test="${empty pb2.result}">
			<tr>
				<td colspan="6">
					<center><img src="${path }/commons/images/no.png" /></center>
				</td>
			</tr>
		</c:if>
		</c:if>
		<c:forEach items="${pb2.result}" var="pbr">
		
<!----------------------------------------- 商品列表 -->
			<c:if test="${pbr.isB2c && pb2.parameter.auditStatus == 1 }">
			
			<tr class="order-hd">
			
				<td colspan="8">
<%-- 					<p  title=" ${fn:escapeXml(pbr.suppliername)}"> --%>
<%-- 						     供应商 ：${fn:escapeXml(pbr.suppliername)} --%>
<!-- 					</p> -->
					<p>商品编码：${fn:escapeXml(pbr.businessProdId)}</p>
					<p>商品ID：${fn:escapeXml(pbr.productId)} </p>
					<p>企业名称：${fn:escapeXml(pbr.suppliername)} </p>


					<c:if test="${! empty buttonsMap['POP货品列表-查看审核未通过原因'] }">
						<c:if test="${ pbr.counterfeittypeid == 1 }">
							<button type="button" class="sr" onclick="showmsg('${fn:escapeXml(pbr.description)}')">未通过原因</button>
						</c:if>
					</c:if>
					
					<c:if test="${! empty buttonsMap['POP货品列表-查看删除原因'] }">	
						<c:if test="${pbr.istate==2}">
							<button type="button" class="sr" onclick="showmsg('${fn:escapeXml(pbr.description)}')">删除原因</button>
						</c:if>
					</c:if>
					
					<%-- 
					<c:if test="${ pbr.isSupplierUpdate == 1 &&!(pbr.istate==2&&pbr.counterfeittypeid==4)}">
						<a class="sr_left" href="${path}/product/showUpdate?productId=${pbr.productId}" target="_blank">查看修改记录</a>
					</c:if> --%>
					
					<c:if test="${! empty buttonsMap['POP货品列表-查看下架原因'] }">	
					
						<c:if test="${pbr.istate==0&&pbr.counterfeittypeid==2}">
						
							<c:if test="${pbr.stopReason eq '缺货' || pbr.stopReason eq '滞销' || pbr.stopReason eq '汰换' || pbr.stopReason eq '更新商品信息'}">
								
								<strong class="ss">下架原因 : ${fn:escapeXml(pbr.stopReason)}</strong>
							
							</c:if>
							<c:if test="${pbr.stopReason != '缺货' && pbr.stopReason != '滞销' && pbr.stopReason != '汰换' && pbr.stopReason != '更新商品信息'}">
								
								<button type="button" class="sr" onclick="showmsg('${fn:escapeXml(pbr.stopReason)}')">下架原因:其他</button>
							
							</c:if>
							
						</c:if>
						
					</c:if>
					
				</td>
				
			</tr>
			
				<tr class="order-bd">
					<td class="baobei">
						<c:if test="${! empty buttonsMap['POP货品列表-批量下架'] ||! empty buttonsMap['POP货品列表-批量上架'] }">
							<c:if test="${pb2.parameter.auditStatus == 1 }">
								<c:choose>
									<c:when test="${pbr.isNationalAgency==2}">
										<input class="selector" type="checkbox" id="${fn:escapeXml(pbr.productId)}"
                                        		name="topProB2C" style="float:left; margin-top:13px; margin-top:8px\9;">
									</c:when>
									<c:otherwise>
										<input class="selector" disabled="disabled" type="checkbox" id="${fn:escapeXml(pbr.productId)}"
												name="topProB2C" style="float:left; margin-top:13px; margin-top:8px\9;">
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:if>
					
						<a class="pic J_MakePoint" target="_blank" href="http://www.zhongjumall.com/item/get/${pbr.productId}">
							
							    <c:if test="${pbr.prodType==6 }">
							幸福购商品</c:if>
						    <c:if test="${pbr.prodType==5 }">
							家庭号商品</c:if>
							<img src="${pbr.b2cImage}" title="${fn:escapeXml(pbr.b2cProductName)}">
						
						</a>
						<div class="desc">
						 <!-- <p class="bc"><button type="button">B2C</button></p> -->
							<p class="baobei-name">
								<i title="${fn:escapeXml(pbr.b2cProductName)}"> <a target="_blank" href="http://www.zhongjumall.com/item/get/${pbr.productId}">${fn:escapeXml(pbr.b2cProductName)} </a></i>
						<br/>
						${pbr.subdealerid }
							</p>
						</div>
					</td>
					<td class="b1">
						<i>
							<span>
								${fn:escapeXml(pbr.b2cMinPrice)} ~ ${fn:escapeXml(pbr.b2cMaxPrice)}
							</span>
						</i>
					</td>
 					<td class="b2">
 						<i> ${fn:escapeXml(pbr.spot)}</i><!-- <br> -->
<%-- 						<c:if test="${! empty buttonsMap['货品列表-补录库存'] }">	 --%>
<%-- 							<c:if test="${pbr.supply==11}"> --%>
<%-- 								<a class="bur" href="${path}/product/getAddInventoryView?productId=${pbr.productId}&supply=${pbr.supply}" target="_blank">补录海外库存</a> --%>
<%-- 							</c:if> --%>
<%-- 							<c:if test="${pbr.supply==12}"> --%>
<%-- 								<a class="bur" href="${path}/product/getAddInventoryView?productId=${pbr.productId}&supply=${pbr.supply}" target="_blank">补录海外库存</a> --%>
<%-- 							</c:if> --%>
<%-- 						</c:if> --%>
<!-- 					</td> -->
	
					<td class="trade-status"><fmt:formatDate value="${pbr.b2cOperateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="trade-status"><fmt:formatDate value="${pbr.b2cCreatedDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					
					<c:if test="${pbr.counterfeittypeid==2&&(pbr.b2cIsTate==0||pbr.b2cIsTate==3)}">	
							<td class="b2"><i> 已下架 </i></td>
					</c:if>
					<c:if test="${pbr.b2cIsTate==1}">	
						<td class="b2"><i> 已上架 </i></td>
					</c:if>
					<td class="trade-operate">
						<p><a class="tm-btn" href="${path}/POPproduct/initPOPShowDealerProduct?catePubId=${pbr.catePubId}&productId=${pbr.productId}&currentPageId=${pageBean.page}" target="_blank">查看</a></p>
					<c:if test="${(pbr.b2cIsTate==0 || pbr.b2cIsTate==3)}">
							<c:if test="${(! empty buttonsMap['POP货品列表-上架商品']) && (pbr.isNationalAgency == 2)}">
								<a class="tm-btn" href="javascript:void(0)" onclick="proUp('${pbr.productId}','1')">上架</a>
							</c:if>
						</c:if>
						<c:if test="${pbr.b2cIsTate==1}">	
							<c:if test="${! empty buttonsMap['POP货品列表-下架商品'] }">	
								<a class="tm-btn" href="javascript:void(0)" onclick="alertProductStopReason('${pbr.productId}','1')">下架</a>
							</c:if>
						</c:if>
						<c:if test="${! empty buttonsMap['POP货品列表-违规下架商品'] }">
							<a class="tm-btn" href="javascript:void(0)" onclick="productIllegalUnderShelf('${pbr.productId}','1')">违规下架</a>
						</c:if>
						<a class="tm-btn" href="javascript:void(0)" onclick="productIllegalUnderShelfxj('${pbr.productId}','1')">星级设置</a>
						<a class="tm-btn" href="javascript:void(0)" onclick="productIllegalUnderShelfxg('${pbr.productId}','1')">限购设置</a>
						<a class="tm-btn" href="javascript:void(0)" onclick="productIllegalUnderShelfzj('${pbr.productId}','1')">赠券设置</a>
						<a class="tm-btn" href="javascript:void(0)" onclick="productIllegalUnderShelflx('${pbr.productId}','1')">类型设置</a>
					</td>
				</tr>
			</c:if>
			<tr style="height:10px;"></tr>
		</c:forEach>
	</tbody>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp" %>
</c:if>
<c:if test="${!empty pb2.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging2.jsp" %>
</c:if>

