<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>商家后台管理系统-运营商商列表</title>
	<%@include file="/WEB-INF/views/zh/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path }/css/product.css"/>
    <link rel="stylesheet" type="text/css" href="${path }/css/product/reset.css"/>
    <link rel="stylesheet" type="text/css" href="${path }/css/product/reset2.css"/>
     <link rel="stylesheet" type="text/css" href="${path }/css/zh/agent/register.css"/>
    <link rel="stylesheet" type="text/css" href="${path }/css/zh/agent/bootStrap/bootstrap.min.css"/>
    <script type="text/javascript" src="${path }/js/agent/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${path }/js/agent/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript" src="${path }/js/agent/tradingFlow.js"></script>
</head>
<body>
	
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/zh/include/header.jsp"%>
	<%@include file="/WEB-INF/views/zh/include/leftProduct.jsp" %>
	<!-- 导航 end -->
	<input type="hidden" name="page" id="page" value="${page}">
	<input type="hidden" name="supplierId" id="supplierId" value="${sid}">

		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>商家中心&nbsp;&gt;&nbsp;</p>
					<p>运营商管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">交易流水</p>
				</div>
			</div>
			<div class="blank10"></div>

			<div class="c22">
				<div class="c3" id="cp3">
					<ul class="top goods-tab">
					<a style="color: #333;display: block;width: 86px;text-decoration: none; border: 1px solid #d4d4d4;border-bottom: 0;border-top: 2px solid #d4d4d4; padding: 0.5rem 1rem;margin: 0;">交易流水</a>
					</ul>
					<div class="xia">
						<form action="javascript:void(0)">
						 	<p class="p1">
								<span>日期:</span> <input type="text" id="startTime" class="rl"  autocomplete="off"
									onClick="WdatePicker()""> <i>至</i><input type="text"  autocomplete="off"
									id="endTime" class="rl" onClick="WdatePicker()" ">
							</p>
							
							
							
<!-- 							</p> -->
							<p class="p3">
								<button type="submit" id="subfm" onclick="clickSubmit1()">搜索</button>
								<button type="submit"  onclick="resetfm()">重置</button>
							</p>
						</form>
						
						<!-- 	不使用window.location.href去暴露访问路径,使用表单post提交,保护数据安全 -->
						<form id="f" action="" method="post" >
							<input type="hidden" id="spid" name="supplierId"/>
							<input type="hidden" id="parid" name="parentId"/>
						</form>
					</div>


					<div class="blank5"></div>
					<div class="c3" id="c3"></div>

				</div>
			</div>
		</div>


	</div>

	<div class="blank10"></div>
	 <!-- 底部 start -->
		<%@include file="/WEB-INF/views/zh/include/foot.jsp"%>
		<!-- 底部 end -->
		

</body>
</html>