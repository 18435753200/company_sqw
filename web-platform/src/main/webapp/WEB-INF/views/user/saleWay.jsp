<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>销售渠道</title>
	
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path }/commons/css/kuc1.css">
	<link rel="stylesheet" type="text/css" href="${path }/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="${path }/commons/css/Set_sales.css">
    <link rel="stylesheet" type="text/css" href="${path }/commons/css/currenry.css">
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
				<p class="c1">销售渠道</p>
			</div>
	     </div>
		<div style="margin-left: 5px" class="btn">
			<a class="ne" href="javascript:void(0);">新增</a>
			<!-- 
			<a href="#">修改</a>
			<a href="#">保存</a>
			<a href="#">放弃</a>
			 -->
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
			<p><span>渠道    名称:</span><input type="text" id="name"></p>
			<p><span>渠道减价率:</span><input type="text" id="rate"></p>
			<p><span>详细    说明:</span><input type="text" id="destion"></p>
			
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
			<p><span>渠道    名称:</span>
			<input type="hidden" id="id">
			<input type="text" id="editName"></p>
			<p><span>渠道减价率:</span><input type="text" id="editRate"></p>
			<p><span>详情    描述:</span><input type="text" id="editDestion"></p>
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
			$("#rate").val("");
			$("#destion").val("");
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
			url : "${path}/saleWay/getModel?page="+page,
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
		var rate = $.trim($("#rate").val());
		var destion = $.trim($("#destion").val());
		
		var submit = true;
			
		if(name.length < 2 || name.length > 10){
			alert("名称只能2-10位！");		
			submit = false;
			return;
		}
		var  matchnum = /^\d{1,2}(\.\d{1,2})?$/;
		if(rate == null)
			rate = 0;
		if(rate == 100){
			submit = true;
		}else{
			
			if(!matchnum.test(rate)){
				submit =false;
				alert("渠道减价率介于0-100，且只能有2位小数！");
				$("#editRate").val("");
				$("#editRate").focus();
				return;
			}else if(rate > 100){
				submit =false;
				alert("渠道减价率介于0-100，且只能有2位小数！");
				$("#editRate").val("");
				$("#editRate").focus();
				return;
			}
		}
		
		if(destion.length > 0 ){
			if(destion.length > 50){
				alert("描述只能50位以内！");		
				submit = false;
				return;
			}
		}

		if(submit){
			var pro_array  = new Array();
		
			if(name!=""){
				pro_array.push("name="+name);
			}
			if(rate!=""){
				pro_array.push("rate="+rate);
			}
			if(destion!=""){
				pro_array.push("destion"+destion);
			}
		
			$.ajax({
				type : "post", 
				url :"${path}/saleWay/save", 
//				data:pro_array.join("&"),data: "orderId=" + orderId + "&commant=" + commant
				data:"name="+$("#name").val()+"&rate="+$("#rate").val()+"&destion="+$("#destion").val(),
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
		var rate = $.trim($("#editRate").val());
		var destion = $.trim($("#editDestion").val());
		
		var submit = true;
		if(name.length < 2 || name.length > 10){
			alert("名称只能2-10位！");		
			submit = false;
			return;
		}
		var  matchnum = /^\d{1,2}(\.\d{1,2})?$/;
		if(rate == null)
			rate = 0;
		if(rate == 100){
			submit = true;
		}else{
			
			if(!matchnum.test(rate)){
				submit =false;
				alert("渠道减价率介于0-100，且只能有2位小数！");
				$("#editRate").val("");
				$("#editRate").focus();
				return;
			}else if(rate > 100){
				submit =false;
				alert("渠道减价率介于0-100，且只能有2位小数！");
				$("#editRate").val("");
				$("#editRate").focus();
				return;
			}
		}
		
		if(destion.length > 0 ){
			if(destion.length > 50){
				alert("描述只能50位以内！");		
				submit = false;
				return;
			}
		}
		
		if(submit){
			var pro_array  = new Array();
	
			if(rate!=""){
				pro_array.push("salePhone="+rate);
			}
			if(name!=""){
				pro_array.push("saleName="+name);
			}
			pro_array.push("saleId="+id);
	
			$.ajax({
				type : "post", 
				url :"${path}/saleWay/toUpdate", 
				data:"id="+id+"&name="+name+"&rate="+rate+"&destion="+destion,
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