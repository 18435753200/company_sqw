<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#all-b2b-selector").click(function() {
			if ($(this).attr("checked") == "checked") { // 全选 
				$("input[type='checkbox'][name='topProB2B']").each(function() {
					$(this).attr("checked", true);
				});
			} else { // 反选 
				$("input[type='checkbox'][name='topProB2B']").each(function() {
					$(this).attr("checked", false);
				});
			}
		});
		$("#all-b2c-selector").click(function() {
			if ($(this).attr("checked") == "checked") { // 全选 
				$("input[type='checkbox'][name='topProB2C']").each(function() {
					if ($(this).attr("disabled")) {
					} else {
						$(this).attr("checked", true);
					}
				});
			} else { // 反选 
				$("input[type='checkbox'][name='topProB2C']").each(function() {
					if ($(this).attr("disabled")) {
					} else {
						$(this).attr("checked", false);
					}
				});
			}
		});
	});
	function addTagsAll() {
		$.dialog.confirm('确定给新增商品添加每日限购标签么？', function() {
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
	style="width: 100%;">
	<thead>
		<tr class="col-name">
			<th>商品&nbsp;&nbsp;&nbsp;(<i
				style="color: #f10180; font-size: 13px;">${pb2.totalCount }</i>)&nbsp;条
			</th>
			<th>价格(元)</th>
			<th>库存</th>
			<th>发布时间</th>
			<th>修改时间</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody class="data" id="showListTbody">
		<input class="all-selector gg" id="all-b2c-selector" type="checkbox">
		<label>全选/反选</label>&nbsp;
		<a class="tm-btn" href="javascript:void(0);"
			onclick="selectProductPutAway(1)" role="button">上架</a>&nbsp;
		<a class="tm-btn" href="javascript:void(0);"
			onclick="alertProductStopReason('',1)" role="button">下架</a>&nbsp;
		<a class="tm-btn" href="javascript:void(0);"
			onclick="selectProductPutAwayxj(1)" role="button">批量星级</a>&nbsp;
		<a class="tm-btn" href="javascript:void(0);"
			onclick="selectProductPutAwayxg(1)" role="button">批量限购</a>&nbsp;
		<a class="tm-btn" href="javascript:void(0);"
			onclick="selectProductPutAwayzj(1)" role="button">批量赠券</a>&nbsp;
		<!-- <a class="tm-btn" href="javascript:void(0);" onclick="addTagsAll()" role="button">新增限购</a>&nbsp; -->
		<a class="tm-btn" href="javascript:void(0);"
			onclick="selectProductPutAwayhh(1)" role="button"
			style="width: 80px;">增加比例标签</a>&nbsp;
		<a class="tm-btn" href="javascript:void(0);"
			onclick="selectProductPutAwayhd(1)" role="button"
			style="width: 80px;">删除比例标签</a>&nbsp;
		<c:if test="${empty pb2.result}">
			<tr>
				<td colspan="6">
					<center>
						<img src="${path }/commons/images/no.png" />
					</center>
				</td>
			</tr>
		</c:if>
		<c:forEach items="${pb2.result}" var="pbr">
			<c:if test="${pbr.isB2c }">
				<tr class="order-hd">
					<td colspan="8">
						<p>商品编码：${fn:escapeXml(pbr.businessProdId)}</p>
						<p>商品ID：${fn:escapeXml(pbr.productId)}</p>
						<p>企业名称：${fn:escapeXml(pbr.suppliername)}</p> <c:if
							test="${! empty buttonsMap['POP货品列表-查看审核未通过原因'] }">
							<c:if test="${ pbr.counterfeittypeid == 1 }">
								<button type="button" class="sr"
									onclick="showmsg('${fn:escapeXml(pbr.description)}')">未通过原因</button>
							</c:if>
						</c:if> <c:if test="${! empty buttonsMap['POP货品列表-查看删除原因'] }">
							<c:if test="${pbr.istate==2}">
								<button type="button" class="sr"
									onclick="showmsg('${fn:escapeXml(pbr.description)}')">删除原因</button>
							</c:if>
						</c:if> <c:if test="${! empty buttonsMap['POP货品列表-查看下架原因'] }">
							<c:if test="${pbr.istate==0&&pbr.counterfeittypeid==2}">
								<c:if
									test="${pbr.stopReason eq '缺货' || pbr.stopReason eq '滞销' || pbr.stopReason eq '汰换' || pbr.stopReason eq '更新商品信息'}">
									<strong class="ss">下架原因 :
										${fn:escapeXml(pbr.stopReason)}</strong>
								</c:if>
								<c:if
									test="${pbr.stopReason != '缺货' && pbr.stopReason != '滞销' && pbr.stopReason != '汰换' && pbr.stopReason != '更新商品信息'}">
									<button type="button" class="sr"
										onclick="showmsg('${fn:escapeXml(pbr.stopReason)}')">下架原因:其他</button>
								</c:if>
							</c:if>
						</c:if>
					</td>
				</tr>
				<tr class="order-bd">
					<td class="baobei"><c:if
							test="${! empty buttonsMap['POP货品列表-批量下架'] ||! empty buttonsMap['POP货品列表-批量上架'] }">
							<c:choose>
								<c:when test="${pbr.isNationalAgency==2}">
									<input class="selector" type="checkbox"
										id="${fn:escapeXml(pbr.productId)}" name="topProB2C"
										style="float: left; margin-top: 13px; margin-top: 8px\9;">
								</c:when>
								<c:otherwise>
									<input class="selector" disabled="disabled" type="checkbox"
										id="${fn:escapeXml(pbr.productId)}" name="topProB2C"
										style="float: left; margin-top: 13px; margin-top: 8px\9;">
								</c:otherwise>
							</c:choose>
						</c:if> <a class="pic J_MakePoint" target="_blank"
						href="http://www.zhongjumall.com/item/get/${pbr.productId}"> <c:if
								test="${pbr.prodType==6 }">
							幸福购商品</c:if> <c:if test="${pbr.prodType==5 }">
							家庭号商品</c:if> <img src="${pbr.b2cImage}"
							title="${fn:escapeXml(pbr.b2cProductName)}">
					</a>
						<div class="desc">
							<!-- <p class="bc"><button type="button">B2C</button></p> -->
							<p class="baobei-name">
								<i title="${fn:escapeXml(pbr.b2cProductName)}"> <a
									target="_blank"
									href="http://www.zhongjumall.com/item/get/${pbr.productId}">${fn:escapeXml(pbr.b2cProductName)}
								</a></i> <br /> ${pbr.subdealerid }
							</p>
						</div></td>
					<td class="b1"><i> <span>
								${fn:escapeXml(pbr.b2cMinPrice)} ~
								${fn:escapeXml(pbr.b2cMaxPrice)} </span>
					</i></td>
					<td class="b2"><i> ${fn:escapeXml(pbr.spot)}</i>
					<!-- <br> -->
					<td class="trade-status"><fmt:formatDate
							value="${pbr.b2cOperateTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td class="trade-status"><fmt:formatDate
							value="${pbr.b2cCreatedDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<c:if
						test="${pbr.counterfeittypeid==2&&(pbr.b2cIsTate==0||pbr.b2cIsTate==3)}">
						<td class="b2"><i> 已下架</i></td>
					</c:if>
					<c:if test="${pbr.b2cIsTate==1}">
						<td class="b2"><i> 已上架 </i></td>
					</c:if>
					<td class="trade-operate">
						<p>
							<a class="tm-btn"
								href="${path}/POPproduct/initPOPShowDealerProduct?catePubId=${pbr.catePubId}&productId=${pbr.productId}&currentPageId=${pageBean.page}"
								target="_blank">查看</a>
						</p> <c:if test="${(pbr.b2cIsTate==0 || pbr.b2cIsTate==3)}">
							<c:if
								test="${(! empty buttonsMap['POP货品列表-上架商品']) && (pbr.isNationalAgency == 2)}">
								<a class="tm-btn" href="javascript:void(0)"
									onclick="proUp('${pbr.productId}','1')">上架</a>
							</c:if>
						</c:if> <c:if test="${pbr.b2cIsTate==1}">
							<c:if test="${! empty buttonsMap['POP货品列表-下架商品'] }">
								<a class="tm-btn" href="javascript:void(0)"
									onclick="alertProductStopReason('${pbr.productId}','1')">下架</a>
							</c:if>
						</c:if> <c:if test="${! empty buttonsMap['POP货品列表-违规下架商品'] }">
							<a class="tm-btn" href="javascript:void(0)"
								onclick="productIllegalUnderShelf('${pbr.productId}','1')">违规下架</a>
						</c:if> <a class="tm-btn" href="javascript:void(0)"
						onclick="productIllegalUnderShelfxj('${pbr.productId}','1')">星级设置</a>
						<a class="tm-btn" href="javascript:void(0)"
						onclick="productIllegalUnderShelfxg('${pbr.productId}','1')">限购设置</a>
						<a class="tm-btn" href="javascript:void(0)"
						onclick="productIllegalUnderShelfzj('${pbr.productId}','1')">赠券设置</a>
						<a class="tm-btn" href="javascript:void(0)"
						onclick="productIllegalUnderShelflx('${pbr.productId}','1')">类型设置</a>
					</td>
				</tr>
			</c:if>
			<tr style="height: 10px;"></tr>
		</c:forEach>
	</tbody>
</table>
<c:if test="${!empty pb2.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging2.jsp"%>
</c:if>