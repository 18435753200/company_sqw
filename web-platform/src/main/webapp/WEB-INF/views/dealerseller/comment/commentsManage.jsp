<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>评价管理</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link href="${path}/commons/css/reset.css" rel="stylesheet" type="text/css">
<link href="${path}/commons/css/comments.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${path}/commons/js/comment_list_reploy.js"></script>
<script src="<%=path %>/commons/js/my97/WdatePicker.js"></script>
<script type="text/javascript">
	//初步加载此项目这个页面时
	$(document).ready(function(){
			$.ajax({ 
			     async:false,
			     type : "post", 
			     url : "<%=path%>/comment/getProductCommentListByCondition", 
			     dataType:"html",
			     success : function(comments) {
			     	$(".comments-tbody").html(comments);
				 },
				 error:function(jqXHR,textStatus,errorThrown){
				  	alert("网络异常,请稍后再试。。。。");
				 }
		     });
		});
	

</script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="center">
	<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
		<div class="c2">
				<div class="c21">
					<div class="title">
						<p>卖家中心&nbsp;>&nbsp; </p>
						<p>评价管理&nbsp;>&nbsp; </p>
						<p class="c1">评价列表</p>
					</div>
				</div>
				<div class="blank10"></div>
				<div class="c22">
				<!-- 搜索条件开始 -->
					<div class="xia">
						   <p class="p1">
								<strong>商品名称：</strong> <input type="text" class="text1" name="pName" id="pName">
								<strong class="st">评论时间： </strong>
								<span>
									<input type="text" class="text1" onClick="WdatePicker()" name="startTime" id="startTime">
									<span> 到 </span>
									<input type="text" class="text1" onClick="WdatePicker()" name="endTime" id="endTime">
								</span>
							</p>
							<p class="p2">
								<!-- <strong>评论人：</strong> <input type="text" class="text1"> -->
								<strong>评论等级：</strong> 
									<select class="text1" name="level" id="level">
									    <option value="0" selected="selected">全部</option>
									    <option value="1" >好评</option>
									    <option value="2" >中评</option>
									    <option value="3" >差评</option>
									</select>
							   
								<strong class="st">平台模式：</strong> 
								   <select class="text1" name="platformType" id="platformType">
									    <option value="" selected="selected">全部</option>
									    <option value="0" >B2B</option>
									    <option value="1" >B2C</option>
									</select>
							</p>
							<p class="p4">
								<button onclick="getCommentListByCondition(1)" type="button" class="bt">搜索</button>
								<a href="#" id="czhi" onclick="resetfm()">重置</a>
								<c:if test="${!empty buttonsMap['评价列表-导出列表'] }">
									<button onclick="goexportCommentListByConditionExcel()" type="button" class="dc-btn">导出</button>
								</c:if>
							</p>
					</div>
				<!-- 搜索条件结束 -->
		</div>	
		<div class="comments">
		    <div class="comments-thead">
		        <ul>
		            <li class="th4">商品信息</li>
		            <li class="th1">星级</li>
		            <li class="th2">评论人</li>
		            <li class="th3">评论内容</li>
		            <li class="th5">操作</li>
		        </ul>
		    </div>
		    <div class="comments-tbody">
		    </div>
		</div>
		</div>
	</div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp"%>
<!-- 底部 end -->


</body>
</html>
