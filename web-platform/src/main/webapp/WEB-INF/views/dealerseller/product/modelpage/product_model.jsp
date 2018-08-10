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
						$(this).attr("checked", true); 
					}); 
				}else{ // 反选 
					$("input[type='checkbox'][name='topProB2C']").each(function() { 
						$(this).attr("checked", false); 
					});
				}
			});
	});
</script>
<table id="J_BoughtTable" class="bought-table" data-spm="9"
style="width:100%;">
	<thead>
		<tr class="col-name">
			<th>商品&nbsp;&nbsp;&nbsp;(<i style="color: #f10180;font-size: 13px;">${pb.totalCount }</i>)&nbsp;条</th>
			<th>价格(元)</th>
			<th>现货</th>
			<th>期货</th>
			<th>发布时间</th>
			<th>修改时间</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody class="data">
		<tr class="sep-row">
			<c:forEach items="${pb.result}" var="pbr" begin="0" end="0">
			
				<td colspan="6" style="padding:5px 0 5px 0;">
				
						<div class="operations">
							<c:if test="${! empty pb.result}">
									
								<c:if test="${!empty buttonsMap['货品列表-批量下架']  || !empty buttonsMap['货品列表-批量上架'] }">


									<%-- <c:if test="${(pbr.istate==0&&pbr.counterfeittypeid==2)||pbr.istate==3}">
										
										<input class="all-selector gg" id="all-b2b-selector" type="checkbox">
										
										<label>B2B全选/反选</label>
	
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAway()" role="button">B2B上架</a>
	
										<label>B2C全选/反选</label>
	
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAway()" role="button">B2C上架</a>
	
									</c:if> --%>
	
									<c:if test="${pb.parameter.auditStatus==11}">
									
										<input class="all-selector gg" id="all-b2b-selector" type="checkbox">
										<label>B2B全选/反选</label>
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAway(0)" role="button">B2B上架</a>
										<a class="tm-btn" href="javascript:void(0);" onclick="alertProductStopReason('',0)" role="button">B2B下架</a>

										<input class="all-selector gg" id="all-b2c-selector" type="checkbox">
										<label>B2C全选/反选</label>
										<a class="tm-btn" href="javascript:void(0);" onclick="selectProductPutAway(1)" role="button">B2C上架</a>
										<a class="tm-btn" href="javascript:void(0);" onclick="alertProductStopReason('',1)" role="button">B2C下架</a>
	
									</c:if>

								</c:if>
								
							</c:if>
							
						</div>
				</td>
				
			</c:forEach>

		</tr>
	</tbody>
	<c:if test="${empty pb.result}">
		<tr>
			<td colspan="6">
				<center><img src="${path }/commons/images/no.png" /></center>
			</td>
		</tr>
	</c:if>

	<tbody class="data"  id="showListTbody">
	
		<c:forEach items="${pb.result}" var="pbr">
		
			<tr class="order-hd">
			
				<td colspan="8">
					<p  title=" ${fn:escapeXml(pbr.suppliername)}">
						     供应商 ：${fn:escapeXml(pbr.suppliername)}
					</p>
					<p>商品编码：${fn:escapeXml(pbr.businessProdId)}</p>
					<p>商品ID：${fn:escapeXml(pbr.productId)} </p>
					 
					 
					 <c:if test="${! empty buttonsMap['货品列表-查看审核未通过原因'] }">	
						<c:if test="${ pbr.counterfeittypeid == 1 }">
							<button type="button" class="sr" onclick="showmsg('${fn:escapeXml(pbr.description)}')">未通过原因</button>
						</c:if>
					</c:if>
					
					<c:if test="${! empty buttonsMap['货品列表-查看删除原因'] }">	
						<c:if test="${pbr.istate==2}">
							<button type="button" class="sr" onclick="showmsg('${fn:escapeXml(pbr.description)}')">删除原因</button>
						</c:if>
					</c:if>
					
					<%-- 
					<c:if test="${ pbr.isSupplierUpdate == 1 &&!(pbr.istate==2&&pbr.counterfeittypeid==4)}">
						<a class="sr_left" href="${path}/product/showUpdate?productId=${pbr.productId}" target="_blank">查看修改记录</a>
					</c:if> --%>
					
					<c:if test="${! empty buttonsMap['货品列表-查看下架原因'] }">	
					
						<c:if test="${pbr.istate==0&&pbr.counterfeittypeid==2}">
						
							<c:if test="${pbr.stopReason eq '缺货' || pbr.stopReason eq '滞销' || pbr.stopReason eq '汰换' || pbr.stopReason eq '更新商品信息'}">
								
								<strong class="ss">B2B下架原因 : ${fn:escapeXml(pbr.stopReason)}</strong>
							
							</c:if>
							<c:if test="${pbr.stopReason != '缺货' && pbr.stopReason != '滞销' && pbr.stopReason != '汰换' && pbr.stopReason != '更新商品信息'}">
								
								<button type="button" class="sr" onclick="showmsg('${fn:escapeXml(pbr.stopReason)}')">下架原因:其他</button>
							
							</c:if>
							
						</c:if>
						
					</c:if>
					
				</td>
				
			</tr>

			<!-- B2B商品 -->
			<c:if test="${pb.parameter.auditStatus != 0 }">
				<tr class="order-bd">
					<td class="baobei">
						<c:if test="${! empty buttonsMap['货品列表-批量下架'] ||! empty buttonsMap['货品列表-批量上架'] }">	
							<c:if test="${pb.parameter.auditStatus==11}">
								<input class="selector" type="checkbox" id="${fn:escapeXml(pbr.productId)}" name="topProB2B" style="float:left; margin-top:13px; margin-top:8px\9;">
							</c:if>
						</c:if>
						<a class="pic J_MakePoint"> 
						<img src="${pbr.imgURL}" title="${fn:escapeXml(pbr.productName)}"> 
						</a>
						<div class="desc">
							<p class="bt"><button type="button">B2B</button></p>
							<p class="baobei-name">
								<i title="${fn:escapeXml(pbr.productName)}"> ${fn:escapeXml(pbr.productName)} </i>
							</p>
						</div>
					</td>
					<td class="b1">
						<i>
							<span>
								${fn:escapeXml(pbr.productPriceMin)} ~ ${fn:escapeXml(pbr.productPriceMax)}
							</span>
						</i>
					</td>
					<td class="b2">
						<i> ${fn:escapeXml(pbr.spot)}</i><br>
						<%-- <c:if test="${! empty buttonsMap['货品列表-补录库存'] }">	
							<c:if test="${pbr.istate!=2}">
								<a class="bur" href="${path}/product/getAddInventoryView?productId=${pbr.productId}" target="_blank">补录库存</a>
							</c:if>
							
						</c:if> --%>
					</td>
	
					<td class="b2"><i> ${fn:escapeXml(pbr.futures)} </i></td>
					<td class="trade-status">${pbr.strCreatedate }</td>
					<td class="trade-status">${pbr.strOperatetime }</td>
					
					<td class="trade-operate">
                    <!-- 如果是POP商品，则不显示操作按钮  -->
					<c:if test="${empty pbr.supply || pbr.supply != 51}">
					
					<c:if test="${! empty buttonsMap['货品列表-查看商品'] }">
							<a class="tm-btn" href="${path}${buttonsMap['货品列表-查看商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=1" target="_blank">查看</a>
					</c:if>
					
					
					<c:if test="${pbr.counterfeittypeid!=0&&pbr.b2bIsTate!=2 }">
						<c:if test="${! empty buttonsMap['货品列表-修改商品'] }">	
							<c:if test="${pbr.b2bIsTate==1 }">
								<a class="tm-btn" href="${path}${buttonsMap['货品列表-修改商品']}&iseditproperty=1&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=1" target="_blank">修改</a>
							</c:if>
							<c:if test="${pbr.b2bIsTate==0 }">
								<a class="tm-btn" href="${path}${buttonsMap['货品列表-修改商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=1" target="_blank">修改</a>
							</c:if>
						</c:if>
					</c:if>
					
						<c:if test="${pbr.counterfeittypeid==2&&(pbr.b2bIsTate==0||pbr.b2bIsTate==3)}">	
							<c:if test="${! empty buttonsMap['货品列表-上架商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="proUp('${pbr.productId}','0')">上架</a>
							</c:if>
						</c:if>
						<c:if test="${pbr.b2bIsTate==1}">	
							<c:if test="${! empty buttonsMap['货品列表-下架商品'] }">	
								<a class="tm-btn" href="javascript:void(0)" onclick="alertProductStopReason('${pbr.productId}','0')">下架</a>
							</c:if>
						</c:if>
						
						<c:if test="${(pbr.counterfeittypeid==4 ||pbr.counterfeittypeid==5 ||pbr.counterfeittypeid==1 ||pbr.counterfeittypeid==2)&&pbr.istate!=2&&pbr.b2cIsTate!=1&&pbr.b2bIsTate!=1 }">
							<c:if test="${! empty buttonsMap['货品列表-删除商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="alertProductDelReason('${pbr.productId}')">删除</a>
							</c:if>
						</c:if>
						<c:if test="${pbr.counterfeittypeid!=0 &&pbr.counterfeittypeid!=1 &&pbr.counterfeittypeid!=4}">
							<c:if test="${! empty buttonsMap['货品列表-复制商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="getProductByCategory('${fn:escapeXml(pbr.productId)}','${fn:escapeXml(pbr.imgURL)}','${fn:escapeXml(pbr.productName)}','${fn:escapeXml(pbr.businessProdId)}')">数据复制</a>
								</c:if>
						</c:if>
					
					
					
					
					
						<%-- <c:choose>
							<c:when test="${pbr.istate==1}">
							<!-- 上架的商品  -->
								<c:if test="${! empty buttonsMap['货品列表-查看商品'] }">	上架的商品
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-查看商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=1" target="_blank">查看</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-修改商品'] }">	
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-修改商品']}&iseditproperty=1&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=1" target="_blank">修改</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-下架商品'] }">	
									<c:if test="${pbr.b2bIsTate!=1}">	
										<a class="tm-btn" href="javascript:void(0)" onclick="proUp('${pbr.productId}','0')">上架</a>
									</c:if>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-复制商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="getProductByCategory('${fn:escapeXml(pbr.productId)}','${fn:escapeXml(pbr.imgURL)}','${fn:escapeXml(pbr.productName)}','${fn:escapeXml(pbr.businessProdId)}')">数据复制</a>
								</c:if>
							</c:when>
								
							<c:when test="${pbr.istate==2}">
								<!-- 删除的商品  --> 删除
								<a class="tm-btn" href="${path}/product/toCreateUI?productId=${pbr.productId}&catePubId=${pbr.catePubId}&target=1" target="_blank">查看</a>
							</c:when>
							
							
							<c:when test="${(pbr.istate==0||pbr.istate==3)&&pbr.counterfeittypeid==2}">
							<!-- 下架的商品  待出售的商品 -->
								<c:if test="${! empty buttonsMap['货品列表-查看商品'] }">	待出售的商品
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-查看商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=1" target="_blank">查看</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-修改商品'] }">	
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-修改商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=1" target="_blank">修改</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-上架商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="proUp('${pbr.productId}','0')">上架</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-删除商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="alertProductDelReason('${pbr.productId}')">删除</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-复制商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="getProductByCategory('${fn:escapeXml(pbr.productId)}','${fn:escapeXml(pbr.imgURL)}','${fn:escapeXml(pbr.productName)}','${fn:escapeXml(pbr.businessProdId)}')">数据复制</a>
								</c:if>
							</c:when>
							
							
							<c:when test="${pbr.counterfeittypeid==0}">
								<!-- 待审核的商品 -->
								<c:if test="${! empty buttonsMap['货品列表-查看商品'] }">	待审核的商品
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-查看商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=1" target="_blank">查看</a>
								</c:if>
							</c:when>
							
							
							<c:when test="${(pbr.counterfeittypeid==1||pbr.counterfeittypeid==5)&&pbr.counterfeittypeid!=4}">
								<!-- 未通过审核的商品  counterfeittypeid==4新增 counterfeittypeid==5保存 counterfeittypeid==1审核未通过-->
								<c:if test="${! empty buttonsMap['货品列表-查看商品'] }">	未通过审核的商品 保存
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-查看商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=1" target="_blank">查看</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-修改商品'] }">	
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-修改商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=1" target="_blank">修改</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-删除商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="alertProductDelReason('${pbr.productId}')">删除</a>
								</c:if>
							</c:when>
							
							
							<c:when test="${pbr.counterfeittypeid==1||pbr.counterfeittypeid==4||pbr.counterfeittypeid==5}">
								<!-- 未通过审核的商品  counterfeittypeid==4新增 counterfeittypeid==5保存 counterfeittypeid==1审核未通过-->
								<c:if test="${! empty buttonsMap['货品列表-查看商品'] }">	保存 未通过审核的商品 新增 
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-查看商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=1" target="_blank">查看</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-修改商品'] }">	
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-修改商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=1" target="_blank">修改</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-删除商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="alertProductDelReason('${pbr.productId}')">删除</a>
								</c:if>
							</c:when>
							
							
						</c:choose> --%>
						</c:if>
					</td>
				</tr>
			</c:if>
			
			
			
			
			<!-- B2C商品 -->
			<c:if test="${pbr.isB2c && pb.parameter.auditStatus != 1 }">
				<tr class="order-bd">
					<td class="baobei">
						
						<c:if test="${! empty buttonsMap['货品列表-批量下架'] ||! empty buttonsMap['货品列表-批量上架'] }">	
							<c:if test="${pb.parameter.auditStatus==11}">
								<input class="selector" type="checkbox" id="${fn:escapeXml(pbr.productId)}" name="topProB2C" style="float:left; margin-top:13px; margin-top:8px\9;">
							</c:if>
						</c:if>
					
						<a class="pic J_MakePoint"> 
						<img src="${pbr.b2cImage}" title="${fn:escapeXml(pbr.b2cProductName)}"> 
						</a>
						<div class="desc">
						 <p class="bc"><button type="button">B2C</button></p>
							<p class="baobei-name">
								<i title="${fn:escapeXml(pbr.b2cProductName)}"> ${fn:escapeXml(pbr.b2cProductName)} </i>
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
						<i> ${fn:escapeXml(pbr.b2cSpot)}</i><br>
						<c:if test="${! empty buttonsMap['货品列表-补录库存'] }">	
							<c:if test="${pbr.supply==11}">
								<a class="bur" href="${path}/product/getAddInventoryView?productId=${pbr.productId}&supply=${pbr.supply}" target="_blank">补录海外库存</a>
							</c:if>
							<c:if test="${pbr.supply==12}">
								<a class="bur" href="${path}/product/getAddInventoryView?productId=${pbr.productId}&supply=${pbr.supply}" target="_blank">补录海外库存</a>
							</c:if>
						</c:if>
					</td>
	
					<td class="b2"><i> - </i></td>
					<td class="trade-status"><fmt:formatDate value="${pbr.b2cCreatedDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="trade-status"><fmt:formatDate value="${pbr.b2cOperateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="trade-operate">
						
					<c:if test="${empty pbr.supply || pbr.supply != 51}">	
										
					<c:if test="${! empty buttonsMap['货品列表-查看商品'] }">
							<a class="tm-btn" href="${path}${buttonsMap['货品列表-查看商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=2" target="_blank">查看</a>
					</c:if>
					
					
					<c:if test="${pbr.counterfeittypeid!=0&&pbr.b2cIsTate!=2 }">
						<c:if test="${! empty buttonsMap['货品列表-修改商品'] }">	
							 <c:if test="${pbr.b2cIsTate ==1 }">
								<a class="tm-btn" href="${path}${buttonsMap['货品列表-修改商品']}&iseditproperty=1&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=2" target="_blank">修改</a>
							</c:if>
							 <c:if test="${pbr.b2cIsTate ==0 }">
								<a class="tm-btn" href="${path}${buttonsMap['货品列表-修改商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=2" target="_blank">修改</a>
							</c:if>
						</c:if>
					</c:if>
					
						<c:if test="${pbr.counterfeittypeid==2&&(pbr.b2cIsTate==0||pbr.b2cIsTate==3)}">	
							<c:if test="${! empty buttonsMap['货品列表-上架商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="proUp('${pbr.productId}','1')">上架</a>
							</c:if>
						</c:if>
						<c:if test="${pbr.b2cIsTate==1}">	
							<c:if test="${! empty buttonsMap['货品列表-下架商品'] }">	
								<a class="tm-btn" href="javascript:void(0)" onclick="alertProductStopReason('${pbr.productId}','1')">下架</a>
							</c:if>
						</c:if>
						
						<c:if test="${(pbr.counterfeittypeid==4 ||pbr.counterfeittypeid==5 ||pbr.counterfeittypeid==1 ||pbr.counterfeittypeid==2)&&pbr.istate!=2&&pbr.b2cIsTate!=1&&pbr.b2bIsTate!=1 }">
							<c:if test="${! empty buttonsMap['货品列表-删除商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="alertProductDelReason('${pbr.productId}')">删除</a>
							</c:if>
						</c:if>
						<c:if test="${pbr.counterfeittypeid!=0 &&pbr.counterfeittypeid!=1 &&pbr.counterfeittypeid!=4}">
							<c:if test="${! empty buttonsMap['货品列表-复制商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="getProductByCategory('${fn:escapeXml(pbr.productId)}','${fn:escapeXml(pbr.imgURL)}','${fn:escapeXml(pbr.productName)}','${fn:escapeXml(pbr.businessProdId)}')">数据复制</a>
								</c:if>
						</c:if>
						
						<%-- <c:choose>
							<c:when test="${pbr.istate==1}">
							<!-- 上架的商品  -->
								<c:if test="${! empty buttonsMap['货品列表-查看商品'] }">	
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-查看商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=2" target="_blank">查看</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-修改商品'] }">	
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-修改商品']}&iseditproperty=1&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=2" target="_blank">修改</a>
								</c:if>
								<c:if test="${pbr.b2cIsTate!=1}">	
									<a class="tm-btn" href="javascript:void(0)" onclick="proUp('${pbr.productId}','1')">上架</a>
								</c:if>
								<c:if test="${pbr.b2cIsTate==1}">	
									<c:if test="${! empty buttonsMap['货品列表-下架商品'] }">	
										<a class="tm-btn" href="javascript:void(0)" onclick="alertProductStopReason('${pbr.productId}','1')">下架</a>
									</c:if>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-复制商品'] }">
									<a class="tm-btn" href="javascript:void(0)" onclick="getProductByCategory('${fn:escapeXml(pbr.productId)}','${fn:escapeXml(pbr.imgURL)}','${fn:escapeXml(pbr.productName)}','${fn:escapeXml(pbr.businessProdId)}')">数据复制</a>
								</c:if>
							</c:when>
							
							<c:when test="${pbr.istate==2}">
								<!-- 删除的商品  -->
								<a class="tm-btn" href="${path}/product/toCreateUI?productId=${pbr.productId}&catePubId=${pbr.catePubId}&target=1" target="_blank">查看</a>
							</c:when>
							
							<c:when test="${(pbr.istate==0||pbr.istate==3)&&pbr.counterfeittypeid==2}">
							<!-- 下架的商品  待出售的商品 -->
								<c:if test="${! empty buttonsMap['货品列表-查看商品'] }">	
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-查看商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=2" target="_blank">查看</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-修改商品'] }">	
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-修改商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=2" target="_blank">修改</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-上架商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="proUp('${pbr.productId}','1')">上架</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-删除商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="alertProductDelReason('${pbr.productId}')">删除</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-复制商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="getProductByCategory('${fn:escapeXml(pbr.productId)}','${fn:escapeXml(pbr.imgURL)}','${fn:escapeXml(pbr.productName)}','${fn:escapeXml(pbr.businessProdId)}')">数据复制</a>
								</c:if>
							</c:when>
							
							<c:when test="${pbr.counterfeittypeid==0}">
								<!-- 待审核的商品 -->
								<c:if test="${! empty buttonsMap['货品列表-查看商品'] }">	
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-查看商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=2" target="_blank">查看</a>
								</c:if>
							</c:when>
							<c:when test="${pbr.counterfeittypeid==1||pbr.counterfeittypeid==4||pbr.counterfeittypeid==5}">
								<!-- 未通过审核的商品  counterfeittypeid==4新增 counterfeittypeid==5保存 counterfeittypeid==1审核未通过-->
								<c:if test="${! empty buttonsMap['货品列表-查看商品'] }">	
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-查看商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=2" target="_blank">查看</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-修改商品'] }">	
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-修改商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=2" target="_blank">修改</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-删除商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="alertProductDelReason('${pbr.productId}')">删除</a>
								</c:if>
							</c:when>
							<c:when test="${pbr.counterfeittypeid==1||pbr.counterfeittypeid!=4||pbr.counterfeittypeid==5}">
								<!-- 未通过审核的商品  counterfeittypeid==4新增 counterfeittypeid==5保存 counterfeittypeid==1审核未通过-->
								<c:if test="${! empty buttonsMap['货品列表-查看商品'] }">	
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-查看商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=2" target="_blank">查看</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-修改商品'] }">	
									<a class="tm-btn" href="${path}${buttonsMap['货品列表-修改商品']}&iseditproperty=0&productId=${pbr.productId}&catePubId=${pbr.catePubId}&bodytype=2" target="_blank">修改</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-删除商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="alertProductDelReason('${pbr.productId}')">删除</a>
								</c:if>
								<c:if test="${! empty buttonsMap['货品列表-复制商品'] }">	
									<a class="tm-btn" href="javascript:void(0)" onclick="getProductByCategory('${fn:escapeXml(pbr.productId)}','${fn:escapeXml(pbr.imgURL)}','${fn:escapeXml(pbr.productName)}','${fn:escapeXml(pbr.businessProdId)}')">数据复制</a>
								</c:if>
							</c:when>
						</c:choose> --%>
						</c:if>
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

