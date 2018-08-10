<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-热词列表</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path }/commons/css/product.css"/>
    <script type="text/javascript" src="${path}/commons/js/hotWords_list_fn.js"></script>
    <script type="text/javascript" src="<%=path %>/commons/js/my97/WdatePicker.js"></script>
</head>
<body>
	
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
    <div class="blank"></div>

	<div class="center">
		<!--中间左边开始-->
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->
		<!--中间左边结束-->
		<div class="alert_user3" style="display: none;">
			<div class="bg"></div>
			<div class="w">
				<div class="box1">
					<img src="${path}/commons/images/close.jpg" class="w_close">
				</div>
				<div class="box3">
					<p id="showmsgplat"></p>
				</div>
				<div class="blank20"></div>
			</div>
		</div>
		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">热词列表</p>
				</div>
			</div>
			<div class="blank10"></div>

			<div class="c22">
				<div class="c3" id="cp3">
					<div class="xia" style="min-height: 65px;">
						<p class="p3">
							<a type="button" id="createTopicfm" href="${path}/hotWords/createHotWords" target="_blank">创建热词</a>
						</p>
					</div>


					<div class="blank5"></div>
					<div class="c3" id="c3">
					<table id="J_BoughtTable" class="bought-table" data-spm="9"
						style="width:100%;">
							<thead>
								<tr class="col-name">
									<th>序号</th>
									<th>描述</th>
<!-- 									<th>链接地址</th> -->
									<th>操作</th>
								</tr>
							</thead>
							
							<c:if test="${empty pb}">
								<tr>
									<td colspan="6">
										<center><img src="${path }/commons/images/no.png" /></center>
									</td>
								</tr>
							</c:if>
						
							<tbody class="data"  id="showListTbody">
							
								<c:forEach items="${pb}" var="pbr" varStatus="status">
									<tr class="order-bd">
										<td style="width:5%;">${status.index + 1}</td>
										<td class="trade-status">${pbr.kw }</td>
<%-- 										<td class="trade-status">${pbr.url }</td> --%>
										<td class="trade-status">
											<a class="tm-btn" target="_blank" href="${path}/hotWords/createHotWords?id=${pbr.id }">编辑</a>
											<a class="tm-btn" href="javascript:void(0)" onclick="javascript:operateStatus('${pbr.id }')">删除</a>
										</td>
									</tr>
									
						<!-- 			<tr style="height:10px;"></tr> -->
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>




	<div class="blank10"></div>
	 <!-- 底部 start -->
		<%@include file="/WEB-INF/views/include/foot.jsp"%>
		<!-- 底部 end -->
</body>
</html>