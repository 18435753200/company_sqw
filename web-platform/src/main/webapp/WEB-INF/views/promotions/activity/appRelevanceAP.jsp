<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品关联APP活动</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link href="${path}/commons/css/reset.css" rel="stylesheet"
	type="text/css">
<link href="${path}/commons/css/promotion.css" rel="stylesheet"
	type="text/css">

<link rel="stylesheet" type="text/css"
	href="${path }/commons/css/coupon.css" />
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/activity_edit_fn.js"></script>

</head>
<body>

	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->

	<div class="center">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/promotions/leftPage.jsp"%>
		</div>

		<!-- 左边 end -->
		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>促销管理&nbsp;&gt;&nbsp;</p>
					<p>APP活动管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">活动关联商品</p>
				</div>

				<div class="blank10"></div>

				<div class="promotion-wrap">
					<div class="promotion-bd manage-promotion">
						<div class="promotion-title">活动关联商品</div>
						<form method="post" id="activityProductAction"
							enctype="multipart/form-data">
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-name">商品（PID）：</label>
									<div class="field">
									<input type="text" name="productId" id="productId" class="promotion-txt" value="${bean.productId }">
									<span class="dpl-tip-inline-warning"></span>
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-section">APP活动：</label>
									<div class="field">
										<select name="select" id="promotion-section" class="promotion-sel">
										<c:forEach items="${activities}" var="act" >
											<option  value="${act.activityId}">${act.activityName}</option>
										</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<input type="button" name="button" id="addBtn" class="promotion-btn promotionManage-btn" value="确定关联">
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="blank10"></div>
	<!-- 底部 start -->
	<div class="t_footer">
			<h1 class="logo_dl"></h1>
	</div>
	<!-- 底部 end -->
	<script type="text/javascript">
	$(document).ready(function() {
		
	  $("#addBtn").click(function() {
	    	var productId = $("#productId").val();
	    	var activityId = null;
	    	 activityId =  $("#promotion-section").find("option:selected").val();
	    	 if(productId==""||productId==null||activityId==""||activityId==null){
	    		 return;
	    	 }
	    	 var url = "../appActivity/addActivityProduct?productId="+productId+"&activityId="+activityId;
	            $.ajax({
	                type : 'post',
	                url : url,
	              //  data : $('#activityAPPAction').serialize(),
	                success : function(data) {
	                    if (data == 'success') {
	                    	window.location.href = CONTEXTPATH+"/appActivity/relevanceAP";
	                    }else if(data == 'exsit'){
	                    	alert("改活动于产品已存在关联信息，无需重复添加");
	                    } else if(data == 'error') {
	                    	alert("错误，请检查产品ID是否有效");
	                    }
	                }
	            });
	    });
	  
    });    
	</script>
</body>
</html>