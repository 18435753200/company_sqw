<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>UNICORN-新增促销</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	
    <link rel="stylesheet" type="text/css" href="${path }/commons/css/promotionsPartOne.css"/>
    <script src="${path}/commons/js/my97/WdatePicker.js"></script>
</head>
<body>
<!-- 导航 start -->
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="blank"></div>
<!-- 导航 end -->

<div class="center">
	<!-- 左边 start -->
	<div class="left f_l">
		<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
	</div>
	<!-- 左边 end -->


	<div class="c2">
		<div class="c21">
			<div class="title">
				<p>卖家中心&nbsp;&gt;&nbsp; </p>
				<p>促销管理&nbsp;&gt;&nbsp; </p>
				<p class="c1">新增促销第一步</p>
			</div>
		</div>
		<div class="blank10"></div>
		<div class="cont">
			<form action="${path }/createPromotions/saveNewPartOne" id="fm">
			<p>
				<span>名&nbsp;&nbsp;&nbsp;称 ：</span>
				<input type="hidden" value="${promotion.promotionId }">
				<input type="text" name="promotionName" value='${fn:escapeXml(promotion.promotionName)}'/>
			</p>
			<p>
			
				<span>促销类型 ：</span>
				
				<select name="type">
					<option value="1">直降</option>
				</select>
			</p>
			<p>
				<span>开始时间 ：</span>
				<input type="text" name="startTime" onClick="WdatePicker()" value="${promotion.startTime }">
			</p>
			<p>
				<span>结束时间 ：</span>
				<input type="text" name="endTime" onClick="WdatePicker()"  value="${promotion.endTime }">
			</p>
			<p>
				<span>描&nbsp;&nbsp;&nbsp;述 ：</span>
				<textarea name="info">${fn:escapeXml(promotion.info)}</textarea>
			</p>
			<div class="nex">
				<button type="submit">下一步</button>
			</div>
			</form>
		</div>

		<script type="text/javascript">
			$(document).ready(function(){
				$("#fm").submit(function(){
					
					var subfalg = true;
					
					var promotionName = $.tirm($(".promotionName").val());
					var startTime = $(".startTime").val();
					var endTime = $(".endTime").val();
					var info = $(".info").text();
					
					if(promotionName==""){
						subfalg=false;
						alert("促销名称不能使空的！");
						return subfalg;
					}
					if(startTime==""){
						subfalg=false;
						alert("请选择促销开始时间！");
						return subfalg;
					}
					if(endTime==""){
						subfalg=false;
						alert("请选择促销结束时间！");
						return subfalg;
					}
					if(startTime!=""&&endTime!=""){
						if(createTime>endTime){
							alert("开始时间不能大于结束时间！");
							return false;
						}
					}
					if(info==""){
						subfalg=false;
						alert("请填写促销描述！");
						return subfalg;
					}
					
					return subfalg;
					
				});
			});
		</script>
	</div>



</div>

<div class="blank10"></div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp"%>
<!-- 底部 end -->
</body>
</html>