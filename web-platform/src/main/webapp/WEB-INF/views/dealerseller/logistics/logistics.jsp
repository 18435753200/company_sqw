<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="UTF-8">
<title>UNICORN-物流商管理</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/logistics_list_fn.js"></script>
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
		<!-- 弹出窗口设置begin -->
		<div class="alert_Provider" style="display:none;">
			<div class="bg"></div>
			<div class="wrap">
				<div class="box1">
					<p class="pic">
						<img src="${path}/commons/images/close.jpg" class="b_colse">
					</p>
				</div>
				<div class="box2">
					<form>
						<div>
							<lable class="lab">物流商名称:</lable>
							<input type="text" id="providerName">
						</div>
						<button class="bt1" type="button"
							onclick="createProvider()">确定</button>
						<button class="bt2" type="button">取消</button>
					</form>
				</div>
			</div>
		</div>
		<!-- 弹出窗口设置end -->

		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>物流管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">物流商管理</p>
				</div>
			</div>
			<div class="blank10"></div>
			<!--  -->
			<div class="logistics-wrap">
				<div class="logistics-hd">
					<div class="logistics-filter">
						<div class="form-group">
							<label for="logistics-type">物流商：</label> 
							<select name="providerId" id="providerId" class="logistics-sel large">
							</select>
						</div>
						<div class="form-group">
					
							<input type="button" name="button" id="button" onclick="createProvder()" class="logistics-btn large" value="创建物流商">
							
							<!-- <c:if test="${! empty buttonsMap['物流商管理-创建运费模板'] }">	
								 <input type="button" name="button" id="createTemplate"  class="logistics-btn large" value="创建运费模板">
							 </c:if> -->
							 
						</div>
					</div>
				</div>
				<c:if test="${! empty buttonsMap['物流商管理-导出运费模板'] }">	
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
				<div class="logistics-bd" style="display:none;"></div>
			</div>
			<!--  -->
		</div>
	</div>

	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>




</body>
</html>