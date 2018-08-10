<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<title>UNICORN-修改商品分类</title>
	
	<link rel="stylesheet" href="${path }/commons/css/shang.css">
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/fenlei_new.css">
	<script type="text/javascript" src="${path}/commons/js/categoryEdit.js"></script>
</head>
<body> 
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
	
<div class="center" style="min-height:87.8%;">
	<!-- 边框start -->
		<div class="right f_l" style="width: 925px">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品管理&nbsp;&gt;&nbsp;</p>
					<p>商品列表&nbsp;&gt;&nbsp;</p>
					<p class="c1">修改商品分类</p>
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
						<h2>选择分类</h2>
						<input type="hidden" value="${firCategoryId}" id="firCategoryId"/>
					    <input type="hidden" value="${secCategoryId}" id="secCategoryId"/>
					    <input type="hidden" value="${thiCategoryId}" id="thiCategoryId"/>
					    <input type="hidden" value="${fouCategoryId}" id="fouCategoryId"/>
					    <input type="hidden" value="${showbodytype}" id="showbodytype"/>
					    <form id="productAction" >
					    	<input type="hidden" value="${productId}" id="productId" name="productId"/>
					    	<input type="hidden" id="cateId" name="cateId"/>
					    </form>
					<div class="mo_div">
			        <div class="main">
						<div class="main_m main_m_fir">
							<ul>
								<c:forEach items="${categorys}" var="cate">
									<c:choose>
									   <c:when test="${cate.cid ==firCategoryId}">
										   <li class="color" pval="${cate.cid}" pid="${cate.leaf}" title="${fn:escapeXml(cate.nameCn)}">
												<a href="javascript:;">${fn:escapeXml(cate.nameCn)}</a>
										   </li>
									   </c:when>
									   <c:otherwise>  
										    <li pval="${cate.cid}" pid="${cate.leaf}" title="${fn:escapeXml(cate.nameCn)}">
												<a href="javascript:;">${fn:escapeXml(cate.nameCn)}</a>
											</li>
									   </c:otherwise>
									</c:choose>
								</c:forEach>
							</ul>
						</div>
						<div class="main_m original main_m_sec"><ul></ul></div>
						<div class="main_m original main_m_thi"><ul></ul></div>
						<div class="main_m original main_m_fou"><ul></ul></div>
						<div class="clear"></div>			
						<div class="main_b">
						 	<div class="app" id="selectCategory">
						 		你当前选择的类目： 
						 	</div>
							<input type="hidden" id="path" value="${path}">
							<div class="s3" id="nextView"><a href="#">修改商品分类信息</a></div>
						</div>
					</div>
				</div>
				</div>
				</div>
				<!-- 边框 end -->
				<div class="blank10"></div>
			</div>
			<div class="clear"></div>
			<div class="dw_temp" style="display:none;">
				<ul>
					<li value="">
						<a href="javascript:;"></a>
					</li>
				</ul>
				<div class="blank10"></div>
			</div>
		</div>
 </div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	<!-- 底部 end -->
	
	<div id="lightboxOverlay" class="lightboxOverlay" style="display: none; width: 1903px; height: 3305px;"></div>
	<div id="lightbox" class="lightbox" style="display: none; top: 832px; left: 0px;">
		<div class="lb-outerContainer" style="width: 403px; height: 403px;">
			<div class="lb-container">
				<img class="lb-image" src="${path}/commons/images/loading.gif" style="display: block; width: 395px; height: 395px;">
				<div class="lb-loader" style="display: none;">
				<a class="lb-cancel"></a>
				</div>
			</div>
		</div>
		<div class="lb-dataContainer" style="display: block; width: 403px;">
			<div class="lb-data">
				<div class="lb-details">
					<span class="lb-number">Image 2 of 4</span>
				</div>
				<div class="lb-closeContainer">
					<a class="lb-close"></a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>