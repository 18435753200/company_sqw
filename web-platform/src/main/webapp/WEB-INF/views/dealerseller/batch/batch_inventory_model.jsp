<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.mall.mybatis.utility.PageBean" %>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<div class="blank5"></div>
<div class="btn">
    <c:if test="${! empty buttonsMap['库存管理-导出表格'] }">
        <a href="javascript:void(0)" onclick="downExcel(0)">
			<span class="button red">
				<span class="text">
					<img alt="" src="${path }/commons/images/down-icon.png" height="19px" width="19px"> 导出表格
				</span>
			</span>
        </a>
    </c:if>
</div>


<table class="tb" style="width:1200px">
    <tr class="tz">
        <th class="t1">商品名称</th>
        <th class="t1">规格</th>
        <th class="t1">SKU</th>
        <th class="t1">库存数量</th>
        <th class="t1">批次</th>
        <th class="t3">生产日期</th>
        <th class="t3">有效日期</th>
    <tr>
        <c:if test="${empty pb.result}">
    <tr>
        <td colspan="9">
            <center><img src="${path }/commons/images/no.png" /></center>
        </td>
    </tr>
    </c:if>

    <c:forEach items="${pb.result }" var="inv">
        <tr class="tz1">
            <td class="t1">
                <p style="margin:0 auto;" class="over" title="${fn:escapeXml(inv.pName)}">${fn:escapeXml(inv.pName)}</p>
            </td>
            <td class="t3">
                <p style="margin:0 auto;" class="over" title="${fn:escapeXml(inv.skuName)}">${fn:escapeXml(inv.skuName)}</p>
            </td>
            <td class="t3">
                <p style="margin:0 auto;" class="over3" title="${fn:escapeXml(inv.skuId)}">${fn:escapeXml(inv.skuId)}</p>
            </td>
            <td class="t3">
                <p style="margin:0 auto;" class="over3" title="${fn:escapeXml(inv.qty)}">${fn:escapeXml(inv.qty)}</p>
            </td>
            <td class="t3">
                <p style="margin:0 auto;" class="over3" title="${fn:escapeXml(inv.batchNo)}">${fn:escapeXml(inv.batchNo)}</p>
            </td>
            <td class="t4">
                <p class="over4"><fmt:formatDate value="${inv.productionDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
            </td>
            <td class="t4">
                <p class="over4"><fmt:formatDate value="${inv.expirationDate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
            </td>
        </tr>
    </c:forEach>
</table>

<c:if test="${!empty pb.result}">
    <%@include file="/WEB-INF/views/dealerseller/paging.jsp" %>
</c:if>