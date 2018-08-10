<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
<title>UNICORN-品牌审核</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${path }/commons/css/kuc1.css">

<script>
$(function(){
	$(".xiug").click(function(){
		$("#noPass-box").show();
	});
	$("#editClose").click(function(){
		$("#noPass-box").hide();
	});	
	$("#viewClose").click(function(){
		$("#look-box").hide();
	});
	clickSubmit();
});
function clickSubmit(page){
		var formArray = new Array();
		
		var fmdata = $("#SearchFrom").serialize();
		if(page!=''&&page!=undefined){
			formArray.push("page="+page);			
		} 
		
		$.ajax({
			type:'post',
			url:'<%=path %>/brand/getBrandAuditList',
			data:fmdata+'&'+formArray+'&math='+Math.random(),
			dataType:'html',
			success:function(data){
				$("#c24").html(data);
			},
			error:function(){
				alert('查询失败,请稍后再试！');
			},
		});
	}
	
	
</script>
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="blank"></div>
<!-- 导航 end -->

<div class="center"> 

	<!-- 左边 start -->
		<%@include file="/WEB-INF/views/include/leftUser.jsp"%>
	<!-- 左边 end --> 
	
	<!-- 左边 end -->
	<div class="c2" style="margin-top:10px;">
		<div class="c21">
			<div class="title">
				<p>平台管理&nbsp;&gt;&nbsp; </p>
				<p>审核管理&nbsp;&gt;&nbsp; </p>
				<p class="c1">品牌审核</p>
			</div>
		</div>
		<div class="blank10"></div>
		<div class="c22">
			<div class="xia">
				<form id="SearchFrom">
					<div class="dzf-form">
						<dl>
							<dt>
								<label for="dzfCompany">品牌名称：</label>
							</dt>
							<dd>
								<input type="text" name="name" id="name" class="dzf-txt">
							</dd>
						</dl>
							<dl>
							<dt>
								<label for="dzfCompany">供应商名称：</label>
							</dt>
							<dd>
								<input type="text" name="supplierName" id="supplierName" class="dzf-txt">
							</dd>
						</dl>
						<dl>
							<dt>
								<label for="dzfName">当前状态：</label>
							</dt>
							<dd>
								<select name="status" id="status" class="dzf-select">
									<option value="">全部</option>
									<option value="0">未审核</option>
									<option value="1">通过</option>
									<option value="2">未通过</option>
								</select>
							</dd>
						</dl>
						<dl>
							<dt>
								<label for="dzfName">开始创建时间：</label>
							</dt>
							<dd>
								<input type="text" name="startTime" id="startTime" class="dzf-txt" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
							</dd>
						</dl>
						<dl>
							<dt>
								<label for="dzfName">结束创建时间：</label>
							</dt>
							<dd>
								<input type="text" name="endTime" id="endTime" class="dzf-txt" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
							</dd>
						</dl>
						
					</div>
					<p class="p4">
						<button type="button" onclick="clickSubmit()">搜索</button>
						<a href="#" id="czhi" onclick="resetfm()">重置</a> </p>
				</form>
			</div>
		</div>
		<div class="c24" id="c24">
		</div>


	</div>
	<div class="blank20"></div>
</div>
<div class="blank10"></div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp"%>
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
</body>
</html>