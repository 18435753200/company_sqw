<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<%
	request.setAttribute("vEnter", "\n");
	request.setAttribute("aEnter", "</br>");
%>
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
				style="color: #f10180; font-size: 13px;">${pb.totalCount }</i>)&nbsp;条
			</th>
			<th>价格(元)</th>
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
			onclick="selectProductPutAwayhh(1)" role="button"
			style="width: 80px;">增加比例标签</a>&nbsp;
		<a class="tm-btn" href="javascript:void(0);"
			onclick="selectProductPutAwayhd(1)" role="button"
			style="width: 80px;">删除比例标签</a>&nbsp;
		<c:if test="${empty pb.result}">
			<tr>
				<td colspan="6">
					<center>
						<img src="${path }/commons/images/no.png" />
					</center>
				</td>
			</tr>
		</c:if>
		<c:forEach items="${pb.result}" var="pbr">
			<tr class="order-hd">
				<td colspan="6"><p style="width: 166px;"
						title="${pbr.productId}">商品ID：${pbr.productId}</p> <c:if
						test="${not empty pbr.subBrand}">
						<p title="${pbr.brand.nameCn}>>${pbr.subBrand.nameCn}"
							style="width: 180px;">商品品牌：${pbr.brand.nameCn}>>${pbr.subBrand.nameCn}</p>
					</c:if> <c:if test="${empty pbr.subBrand}">
						<p title="${pbr.brand.nameCn}" style="width: 180px;">商品品牌：${pbr.brand.nameCn}</p>
					</c:if> <c:if test="${pbr.counterfeittypeid == 1}">
						<button type="text" class="sr"
							onclick="showmsg('${fn:replace(pbr.description, vEnter , aEnter)}')">未通过原因</button>
					</c:if> <c:if test="${pbr.istate==0&&pbr.counterfeittypeid==2}">
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
					</c:if> <c:if test="${pbr.counterfeittypeid==5}">
						<c:if
							test="${pbr.description eq '缺货' || pbr.description eq '滞销' || pbr.description eq '汰换' || pbr.description eq '更新商品信息'}">
							<strong class="ss">违规下架原因 :
								${fn:escapeXml(pbr.description)}</strong>
						</c:if>
						<c:if
							test="${pbr.description != '缺货' && pbr.description != '滞销' && pbr.description != '汰换' && pbr.description != '更新商品信息'}">
							<button type="button" class="sr"
								onclick="showmsg('${fn:escapeXml(pbr.description)}')">违规下架原因:其他</button>
						</c:if>
					</c:if></td>
			</tr>
			<tr class="order-bd">
				<td class="baobei" colspan="1"><input class="selector"
					type="checkbox" id="${pbr.productId}" name="topProB2C"
					style="float: left; margin-top: 13px; margin-top: 8px\9;">
					<a class="pic J_MakePoint"> <c:if test="${pbr.prodType==6 }">
							幸福购商品</c:if> <c:if test="${pbr.prodType==5 }">
							家庭号商品</c:if> <img src="${pbr.imgURL}" alt="商品图片">
				</a>
					<div class="desc" title="${fn:escapeXml(pbr.productName)}"
						style="float: left;">
						<p class="baobei-name">
							<i> ${fn:escapeXml(pbr.productName)}</i><br />
							${pbr.subsupplierid } ${pbr.measureid }
						</p>
					</div></td>
				<td><i> <span>${pbr.moneyUnitSymbols} <c:choose>
								<c:when test="${pbr.productPriceMin==pbr.productPriceMax}">
									<fmt:formatNumber pattern="0.00#"
										value="${pbr.productPriceMin}"></fmt:formatNumber>
								</c:when>
								<c:otherwise>
									<fmt:formatNumber pattern="0.00#"
										value="${pbr.productPriceMin}"></fmt:formatNumber>
											 ~
											 <fmt:formatNumber pattern="0.00#"
										value="${pbr.productPriceMax}"></fmt:formatNumber>
								</c:otherwise>
							</c:choose>
					</span>
				</i></td>
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
				<td class="trade-operate"><c:choose>
						<c:when test="${pbr.counterfeittypeid==0}">
							<c:if test="${! empty buttonsMap['POP货品列表-审核商品'] }">
								<p>
									<a class="tm-btn"
										href="${path}/POPproduct/initAudit?productId=${pbr.productId}&currentPageId=${pageBean.page}&prodType=${pbr.prodType}">审核</a>
								</p>
								<input id="currentPageId" type="hidden"
									value="${pageBean.page }" />
							</c:if>
							<p>
								<a class="tm-btn"
									href="${path}/POPproduct/initPOPShow?catePubId=${pbr.catePubId}&productId=${pbr.productId}&currentPageId=${pageBean.page}">查看</a>
							</p>
						</c:when>
						<c:otherwise>
							<p>
								<a class="tm-btn"
									href="${path}/POPproduct/initPOPShow?catePubId=${pbr.catePubId}&productId=${pbr.productId}&currentPageId=${pageBean.page}">查看</a>
							</p>
						</c:otherwise>
					</c:choose></td>
			</tr>
			<tr style="height: 10px;"></tr>
		</c:forEach>
	</tbody>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>