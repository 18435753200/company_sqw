<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/zh/include/base2.jsp"%>
<table id="J_BoughtTable" class="bought-table" data-spm="9" style="width: 100%;">
		<span style="line-height: 40px; font-weight: 600; color:#000;">总合:<i style="color: #e60012;font-size: 1.5rem; margin-left: 1rem;">${bigDecimal }</i></span> 
		<c:if test="${supplierId!=sid}">
			<input  type="button" style="color: red; cursor: pointer; border: 0; padding: 0.5rem 3rem;  float: right;border-radius: 5px; margin-top: 0.5rem;" onclick="returnPage('${parentId}')" value="返回"/>
		</c:if>
		<thead>
			<tr class="col-name">
<!-- 				<th>用户名称</th> -->
				<th>收入</th>
				<th>时间</th>
				<th>备注</th>
				<th>详细信息</th>
			</tr>
		</thead>
		<tbody class="data" id="showListTbody">
		<!--        没有数据显示的页面 -->
		<c:if test="${empty acc.result}">
			<tr>
				<td colspan="6">
					<center>
						<img src="${path }/images/no.png" />
					</center>
				</td>
			</tr>
		</c:if>
		<!--        有数据显示的页面 -->
		<c:if test="${!empty acc.result}">
			<c:forEach items="${acc.result}" var="pbr">
				<tr style="border: 1px solid #e1e8ee; text-align: center; height: 80px;">
					<td class="busShow" colspan="1" >
						${pbr.earning }
					</td>
					<td class="busShow" colspan="1" >
						<fmt:formatDate value="${pbr.operatorTime }" pattern="yyyy-MM-dd  HH:mm:ss" />
					</td>
					<td class="busShow" colspan="1" >
						${pbr.memo }
					</td>
					<td>
							<a class="see_more" onclick="showDiv(${pbr.id })"  href="javascript:void(0)">查看</a>
					</td>
				</tr>
				<tr style="height: 10px;"></tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>


<div style="height:100%;width:100%;position:fixed;top:0px;left:0px; z-index: 10;background-color:rgba(111,111,111,0.5);display:none;" id="tshow">
	<div style="width: 500px;position: relative; margin: 0 auto; top: 10%; background:rgba(255,255,255,1);    padding: 1rem;">
		<p style="text-align: right;padding-right: 10px;"><span onclick="closeDiv();"  class="srxx_close" >关闭</span></p>
		<div class="srxx">
		<h4>收入详细</h4>
			<p><span>订单:  </span><label  id="orderId"/></label></p>
			<p><span>时间:  </span><label id="time"/></label></p>
			<p><span>订单金额:  </span><label id="price"/></label></p>
			<p><span>订单分润:  </span><label id="earning"/></label></p>
			<p><span>商家名称:    </span><label id="supplierName" /></label></p>
			<p><span>商家所在省:  </span><label id="supplierProvince"/></label></p>
			<p><span>商家所在市:  </span><label id="supplierCity"/></label></p>
			<p><span>商家所在县:  </span><label id="supplierCounty"/></label></p>
			<p><span>商家详细地址:  </span><label id="address"/></label></p>
			<p><span>消费者昵称:  </span><label id="userNickName"/></label></p>
		</div>
	</div>
</div>



<c:if test="${!empty acc.result}">
	<%@include file="/WEB-INF/views/zh/agent/include/trandingFlowPage.jsp"%>
</c:if>