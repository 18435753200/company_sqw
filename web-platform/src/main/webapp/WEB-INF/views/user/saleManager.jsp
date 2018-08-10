<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>销售设置</title>
	
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<%-- <link rel="stylesheet" type="text/css" href="${path }/commons/css/kuc1.css"> --%>
	<link rel="stylesheet" type="text/css" href="${path }/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="${path }/commons/css/Set_sales.css">
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="center">
	<%@include file="/WEB-INF/views/include/leftUser.jsp"%>
	 <div class="right">

	 	<div class="c21">
			<div class="title">
				<p>卖家中心&nbsp;&gt;&nbsp; </p>
				<p>信息管理&nbsp;&gt;&nbsp; </p>
				<p class="c1">销售设置</p>
			</div>
	     </div>

	     <div class="btn">
	     	<a href="#" class="ne">新增</a>
	     </div>

		<div class="pu_wrap">
		 	
	   </div>
	   
	</div>
</div>


<div class="alert_new" style="display:none;">
	<div class="bg"></div>
	<div class="wrap">
		<div class="box1">
			<p class="pic">
				<img src="${path}/commons/images/close.jpg" class="b_colse">
			</p>
		</div>
		<div class="box2">
			<p><span>销售名称:</span><input type="text" id="name"></p>
			<p><span>联系电话:</span><input type="text" id="phone"></p>
		</div>
		<div class="box3">
		   <button class="bt1" id="save" type="button">确定</button>
		   <button class="bt2" type="button">取消</button>
		</div>
	</div>
</div>

<div class="alert_up" style="display:none;">
	<div class="bg"></div>
	<div class="wrap">
		<div class="box1">
			<p class="pic">
				<img src="${path}/commons/images/close.jpg" class="b_colse">
			</p>
		</div>
		<div class="box2">
			<p><span>销售名称:</span>
			<input type="hidden" id="id">
			<input type="text" id="editName"></p>
			<p><span>联系电话:</span><input type="text" id="editPhone"></p>
		</div>
		<div class="box3">
		   <button class="bt1" id="edit" type="button">确定</button>
		   <button class="bt2" type="button">取消</button>
		</div>
	</div>
</div>


<script type="text/javascript">
	
	
	$(function(){
		$(".ne").click(function(){
			$("#name").val("");
			$("#phone").val("");
			$(".alert_new").show();
		});
		$(".b_colse,.bt2").click(function(){
			$(".alert_new").hide();
		});
	});
	

	
	
	$(document).ready(function(){
		clickSubmit(1);
		$("#save").live('click',save);
		$("#edit").live('click',edit);
	});
	
	
	function clickSubmit(page){
		$.ajax({
			async : false,
			type : "post",
			url : "${path}/saleManager/getModel?page="+page,
			dataType : "html",
			success : function(msg) {
				$(".pu_wrap").html(msg);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("对不起，数据异常请稍后再试!");
			}
		});
	}
	
	
	function save(){
		
		var name = $.trim($("#name").val());
		var phone = $.trim($("#phone").val());
		
		var submit = true;
		
		if(name.length < 2 || name.length > 10){
			alert("销售姓名只能2-10位！");		
			submit = false;
		}
		
		var match = /^((\d{3,4})|\d{3,4}-)?\d{7,8}$/;
		var  matchnum = /^1[3578][0-9]{9}$/;
		if(matchnum.test(phone) || match.test(phone)){
			
			submit = true;
		}else{
			alert("请输入正确的电话号码！");
			$("#phone").val("");
			$("#phone").focus();
			submit = false;
		}
	

		if(submit){
			var pro_array  = new Array();
		
			if(phone!=""){
				pro_array.push("phone="+phone);
			}
			if(name!=""){
				pro_array.push("name="+name);
			}
		
			$.ajax({
				type : "post", 
				url :"${path}/saleManager/saveSale", 
				data:pro_array.join("&"),
				dataType:"html",
				success : function(msg) { 
					if(msg == "1"){
						alert("添加销售成功!");
						$(".alert_new").hide();
						clickSubmit(1);
					}else{
						alert('添加失败,要么等会再试试?');
					}
				},
				error: function(jqXHR, textStatus, errorThrown){
					alert("对不起，数据异常请稍后再试!");
				}
			});
		}
	}
	
	function edit(){
	var id = $.trim($("#id").val());
	var name = $.trim($("#editName").val());
	var phone = $.trim($("#editPhone").val());
	
	var submit = true;
	
	if(name.length < 2 || name.length > 10){
		submit = false;
		alert("销售姓名只能2-10位！");		
	}
	
	var matchnumPhone = /^1[3578][0-9]{9}$/;
	var  matchnum = /^[0-9]{1,11}$/;
	var match = /^((\d{3,4})|\d{3,4}-)?\d{7,8}$/;
	if(matchnumPhone.test(phone) || match.test(phone) ){
		submit = true;
	}else{
		submit =false;
		alert("请输入正确的电话号码！");
		$("#editPhone").val("");
		$("#phone").focus();
		
	}
	

		if(submit){
			var pro_array  = new Array();

			if(phone!=""){
				pro_array.push("salePhone="+phone);
			}
			if(name!=""){
				pro_array.push("saleName="+name);
			}
			pro_array.push("saleId="+id);

			$.ajax({
				type : "post", 
				url :"${path}/saleManager/updateSale", 
				data:pro_array.join("&"),
				dataType:"html",
				success : function(msg) { 
					if(msg == "1"){
						alert("修改销售成功!");
						$(".alert_up").hide();
						clickSubmit(1);
					}else{
						alert('修改失败,要么等会再试试?');
					}
				},
				error: function(jqXHR, textStatus, errorThrown){
					alert("对不起，数据异常请稍后再试!");
				}
			});
		}
	
	}
	
</script>
<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>