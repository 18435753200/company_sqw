<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="UTF-8">
<title>UNICORN- 平台运费模板管理</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/platformlogst_list_fn.js"></script>
<link rel="stylesheet" type="text/css"
	href="${path }/commons/css/logistics.css" />
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<!-- 导航 end -->
	<div class="blank10"></div>
	<div class="center">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->

		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>物流管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">平台运费模板管理</p>
				</div>
			</div>
			<div class="blank10"></div>
			<!--  -->
			<div class="logistics-wrap">
				<div class="logistics-hd">
					<div class="logistics-filter">
						<div class="form-group">
							<label for="logistics-name">模板名称：</label> <input type="text" style="line-height: 1.5;"
								name="logisticTempName" id="logisticTempName" class="logistics-txt">
						</div>
						<div class="form-group">
							<select name="provinceStartId" id="provinceStartId" class="logistics-sel large">
								<option value="0">发货省</option>
							</select>
						</div>
						<div class="form-group">
							<select name="provinceEndId" id="provinceEndId" class="logistics-sel large">
								<option value="0">目的省</option>
							</select>
						</div>
						<div class="form-group">
						
							<input type="button" name="button" id="button" 	onclick="clickSubmit()" class="logistics-btn large" value="查询模板">
							
							<!-- <c:if test="${! empty buttonsMap['平台运费模板管理-创建运费模板'] }">	
							
								<input type="button" name="button" id="createTemplate"  class="logistics-btn large" value="创建运费模板">
								
							</c:if> -->
							
						</div>
						
					</div>
					
				</div>
					<c:if test="${! empty buttonsMap['平台运费模板管理-导出运费模板'] }">
						  <div class="btn">
								<a href="javascript:void(0)" onclick="downListExcel()"> 
									<span class="button red"> 
										<span class="text">
											<img alt="" src="${path }/commons/images/down-icon.png" height="19px" width="19px"> 导出表格
										</span>
									</span>
								</a>
						  </div>
					 </c:if>	
				<div class="logistics-bd"></div>
			</div>
			<!--  -->
		</div>
	</div>

	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>




</body>
</html>