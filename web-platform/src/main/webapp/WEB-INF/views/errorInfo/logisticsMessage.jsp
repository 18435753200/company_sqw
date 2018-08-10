<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>WMS推送错误信息查询</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${path}/commons/css/reset.css">
<link rel="stylesheet" type="text/css"
	href="${path}/commons/css/transfer.css">
<script src="${path}/commons/js/my97/WdatePicker.js"></script>

<script type="text/javascript">
	
</script>
</head>
<body>
	<!-- 导航 START -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 END -->

	<div class="blank"></div>

	<div class="center">
		<!-- 左侧菜单 START -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左侧菜单 END -->

		<div class="right">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品储存&nbsp;&gt;&nbsp;</p>
					<p class="c1">WMS推送错误信息查询</p>
					<input type="hidden" id="messageType" name="stamessageTypetus">
				</div>
			</div>

			<div class="pu_bd" id="cbAllocatePrdList">
				<div class="pu_wrap">
					<div class="pu_hd">
						<h3>WMS推送错误信息查询</h3>
						<div class="btn"></div>
					</div>
					<div class="pu_bd">
						<table>
							<tr>
								<td width="110" align="right" valign="middle">JSON消息详细内容</td>

								<td><textarea id="sdf" name="sdf" style="width:500px;height:500px">${logisticsThtxMessage.message}</textarea>
								</td>
							</tr>

						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>