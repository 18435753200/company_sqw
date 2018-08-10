<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
<title>UNICORN-设备审核</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<!-- <link rel="shortcut icon" href="/web-platform/commons/images/favicon1.ico" /> -->
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
		
		var payCompany = $("#dzfCompany").val();
		var payUser = $("#dzfName").val();
		var payStatus = $("#dzfStatus").val();
		var dzfDeviceId = $("#dzfDeviceId").val();
		var formArray = new Array();
		
		if(payCompany!=''){
			formArray.push("payCompany="+payCompany);
		}
		if(payUser!=''){
			formArray.push("payUser="+payUser);
		}
		if(payStatus!=''){
			formArray.push("payStatus="+payStatus);
		}
		if(dzfDeviceId!=''){
			formArray.push("deviceId="+dzfDeviceId);
		}
		
		if(page!=''&&page!=undefined){
			formArray.push("page="+page);			
		}
		
		$.ajax({
			type:'post',
			url:'../equipment/getEquipmentAuditList',
			data:formArray.join("&"),
			dataType:'html',
			success:function(data){
				$("#c24").html(data);
			},
			error:function(){
				alert('查询失败,请稍后再试！');
			},
		});
	}
	
	
function valuationChangeStatus(payId,status,equipmentId){
	$("#parPayId").val(payId);
	$("#parPayId").attr("equipmentId",equipmentId);
	$("#parPayStatus").val(status);
	$("#opini").val(""); 
	$("#noPass-box").show();
}
function changeStatus(){
	var payId = $("#parPayId").val();
	var payStatus = $("#parPayStatus").val();
	var remark = $("#opini").val();
	var equipmentId = $("#parPayId").attr("equipmentId");
	$.ajax({
		type:'post',
		url:'../equipment/changeStatus',
		data:'payStatus='+payStatus+'&payId='+payId+'&remark='+remark+'&equipmentId='+equipmentId,
		success:function(data){
			if(data=="1"){
				alert("修改成功。");
				clickSubmit();
			}
			if(data=="0"){
				alert("修改失败,请稍后再试");
			}
			$("#noPass-box").hide();
		},
		error:function(){
			alert('修改失败,请稍后再试！');
		},
	});
}
function valuationToView(msg){
	$("#opini1").val(msg);
	$("#look-box").show();
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
				<p class="c1">设备审核</p>
			</div>
		</div>
		<div class="blank10"></div>
		<div class="c22">
			<div class="xia">
				<form id="SearchFrom">
					<div class="dzf-form">
						<dl>
							<dt>
								<label for="dzfCompany">付款商户：</label>
							</dt>
							<dd>
								<input type="text" name="payCompany" id="dzfCompany" class="dzf-txt">
							</dd>
						</dl>
							<dl>
							<dt>
								<label for="dzfCompany">设备ID：</label>
							</dt>
							<dd>
								<input type="text" name="deviceId" id="dzfDeviceId" class="dzf-txt">
							</dd>
						</dl>
						<dl>
							<dt>
								<label for="dzfName">付款人姓名：</label>
							</dt>
							<dd>
								<input type="text" name="payUser" id="dzfName" class="dzf-txt">
							</dd>
						</dl>
						<dl>
							<dt>
								<label for="dzfStatus">状态：</label>
							</dt>
							<dd>
								<select name="payStatus" id="dzfStatus" class="dzf-select">
									<option value="">全部</option>
									<option value="1">通过</option>
									<option value="-1">未通过</option>
								</select>
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
			<!--#start NoPass DIV-->
			<div id="noPass-box" class="lightbox">
				<div class="lightbox-overlay"></div>
				<div class="lightbox-box">
					<div class="close-box" id="editClose"></div>
					<div class="lightbox-box-hd">
						<h2>设备审核</h2>
					</div>
					
					<div class="lightbox-box-bd">
						<div class="sh-content">
							<label for="opini">审核回复信息：</label>
							<textarea name="textarea" id="opini" class="sh-text"></textarea>
							<input id="parPayId" type="hidden">
							<input id="parPayStatus" type="hidden">
							<div class="msg-txt"></div>
						</div>
					</div>
					<div class="lightbox-box-bar">
						<input type="button" onclick="changeStatus()" value="提交" class="lightbox-btn true-btn">
					</div>
				</div>
			</div>
			<!--#end NoPass DIV-->
			<!--#start Look DIV-->
			<div id="look-box" class="lightbox">
				<div class="lightbox-overlay"></div>
				<div class="lightbox-box">
					<div class="close-box" id="viewClose"></div>
					<div class="lightbox-box-hd">
						<h2>设备审核</h2>
					</div>
					
					<div class="lightbox-box-bd">
						<div class="sh-content">
							<label for="opini">查看审核回复信息：</label>
							<textarea name="textarea" id="opini1" class="sh-text" readonly="readonly"></textarea>
							<div class="msg-txt"></div>
						</div>
					</div>
					<div class="lightbox-box-bar">
					</div>
				</div>
			</div>
			<!--#end Look DIV-->
	</div>
	<div class="blank20"></div>
</div>
<div class="blank10"></div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>