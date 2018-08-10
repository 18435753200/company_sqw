<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>UNICORN-编辑物流商运费模板</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>

<link rel="stylesheet" type="text/css"
	href="${path }/commons/css/logisTemplate.css" />
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/logistics_temp_edit_fn.js"></script>
</head>
<body>
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->

	<div class="center">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->
		<form method="post" id="templateAction" enctype="multipart/form-data">
			<input type="hidden" name="errorMsg" id="errorMsg"
				value="${fn:escapeXml(errorMsg)}" />
			<input type="hidden" name="providerId" id="providerId"
				value="${fn:escapeXml(logisticTemp.providerId)}" />
			<input type="hidden" name="logisticTempId" id="logisticTempId"
				value="${fn:escapeXml(logisticTemp.logisticTempId)}" />
			<input type="hidden"  id="typeDef" value="${fn:escapeXml(logisticTemp.type)}" />

			<div class="c2">
				<div class="c21">
					<div class="title">
						<p>卖家中心&nbsp;&gt;&nbsp;</p>
						<p>物流管理&nbsp;&gt;&nbsp;</p>
						<p class="c1">编辑物流模板</p>
					</div>
				</div>
				<div class="blank10"></div>
				<div class="right">
					<div class="item">
						<label><i>*</i>物流商：</label>
						<div class="itemright">
							<input type="text" class="providername" value="${fn:escapeXml(logisticTemp.providerName)}"  readonly="readonly"/>
						</div>
					</div>
					<div class="item">
						<label><i>*</i>发货地：</label>
						<div class="itemright">
							<select name="provinceStartId" id="provinceStartId" defaultVal="${logisticTemp.provinceStartId }">
								<option value="0">选择省</option>
							</select> <select name="cityStartId" id="cityStartId" defaultVal="${logisticTemp.cityStartId }">
								<option value="0">全部</option>
							</select>
							<span class="dpl-tip-inline-warning"></span>
						</div>
					</div>
					<div class="item">
						<label><i>*</i>目的地：</label>
						<div class="itemright">
							<select name="provinceEndId" id="provinceEndId" defaultVal="${logisticTemp.provinceEndId }">
								<option value="0">选择省</option>
							</select> <select name="cityEndId" id="cityEndId" defaultVal="${logisticTemp.cityEndId }">
								<option value="0">全部</option>
							</select>
							<span class="dpl-tip-inline-warning"></span>
						</div>
					</div>

					<div class="item">
						<label><i>*</i>收费类型：</label>
						<div class="itemright">
							<div class="item_top">
								
								<input type="radio" name="type" value="1" class="weight" checked="checked">按件重量计价：
								<input type="radio" name="type" value="2" class="piece" >按件计价：
							</div>
							<div class="item_bottom">
								<div class="item_bo">
									默认运费<input type="text" name="baseQt" value="${logisticTemp.baseQt }" id="baseQt" ><span id="basewp">公斤</span>内，
									<input type="text" name="baseFee" id="baseFee" value="${logisticTemp.baseFee }">元，
									每增加<input type="text" name="stepQt" id="stepQt" value="${logisticTemp.stepQt }"><span id="stepwp">公斤</span>，增加运费
									<input type="text" name="stepFee" id="stepFee" value="${logisticTemp.stepFee }">元
								<p class="temp">  </p> <span class="dpl-tip-inline-warning"></span>
								</div>
							</div>
							
						</div>

					</div>


					<div class="item">
						<label><i>*</i>时限：</label>
						<div class="itemright">
							<input type="text" name="timeLimitMin" id="timeLimitMin" value="${logisticTemp.timeLimitMin }" />至
							<input type="text" name="timeLimitMax" id="timeLimitMax"  value="${logisticTemp.timeLimitMax }"/>天
							<span class="dpl-tip-inline-warning"></span>
						</div>
					</div>

					<div class="btn">
						<button type="button" id="createTemp">保存</button>
					</div>

				</div>

			</div>
		</form>
	</div>
	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	<!-- 底部 end -->
	<script type="text/javascript">
		
	</script>
</body>
</html>