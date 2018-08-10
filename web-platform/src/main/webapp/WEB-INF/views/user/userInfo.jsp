<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
	<head>
	  <meta charset="UTF-8">
	  <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	  <title>UNICORN-会员手机号修改</title>
	  <%@include file="/WEB-INF/views/include/base.jsp"%>
	   <link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
     <script type="text/javascript" src="${path}/commons/js/user/validate.js"></script>
	  <script type="text/javascript" src="${path}/commons/js/user/jiben.js"></script>
		<style type="text/css">
	     input:focus{
			border:1px solid #09F;
			outline-style:none;
		}
		.input_warning{
			float:left;
		    font-family:Arial,"宋体",Lucida,Verdana,Helvetica,sans-serif;
			font-size:12px;
			padding-top:4px;
			padding-left:24px;
		}
		#thief_warning{
			height:12px;
		}
	</style>
	<script type="text/javascript">
	$(function(){
		$(".fabu_btn").val('保存');
		$("#mobileId").removeAttr('disabled');
	});
	</script>
	
    </head>
	<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	 <div class="wrap">
		<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		<div class="right f_l">
			<div class="title">
				<p class="c1">修改手机号</p>
				<div class="clear"></div>
			</div>
			<div class="blank5"></div>
			<div class="cont">
				<ul class="ul_vertical">
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>用户名：</p>
						<input class="i1" name ="name" value="${fn:escapeXml(user.userName)}"/>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>手机号：</p>
						<input class="i1" id="mobileId"  value="${fn:escapeXml(user.mobile)}"  onblur="checkMobile()" />
						<input id="oldMobileId" type="hidden" name="oldMobile" value="${fn:escapeXml(user.mobile)}"/>
							<span  class="input_warning"></span>
					</li>
				    <input type="button"  class="fabu_btn" onclick="modifyMobile('${user.userId}')" value="修改" />
					<input type="button"  class="fabu_back" onclick="ret()" value="返回" />
					<p class="blank30"></p>
			</div>
		</div>
		<!-- 右边 end -->
	</div>
  	</body>
	<script type="text/javascript">
		function checkMobile(){
			var newVal = $("#mobileId").val().trim();
			var oldVal = $("#oldMobileId").val().trim();
			var reg = "^0?(13|15|17|18|14)[0-9]{9}$";
			if(newVal == oldVal){
				$(".input_warning").html('手机号跟原来手机号一样，请重新输入!').css("color", "red");
				$("#mobileId").val('');
				return;
			}else if(!newVal.match(reg)){
				$(".input_warning").html('手机格式不正确，请重新输入!').css("color", "red");
			}else{
				$.ajax({
	  	   		     type:"POST",
	  	   		     url:"../platform/checkTJUserIsExists?tjName="+newVal, 
	  	   		     dataType:'html',
	  	   		     success : function(result) {
	  	   		     if(result != 0){
	  	   		    	$(".input_warning").html('系统内已有此手机号，请重新输入!').css("color", "red");
	  	   		    	 return false;
	  	   		     }else{
	  	   		    	$(".input_warning").html('').css("color", "");
	  	   		    	 return true;
	  	   		     }
	  	   			},
	        	}); 
			}
		};
		function modifyMobile(userId){
			if($(".fabu_btn").val().trim() == '修改'){
				$(".fabu_btn").val('保存');
				$("#mobileId").removeAttr('disabled');
			}else{
				var htmlVal = $(".input_warning").html();
				var newMobile = $("#mobileId").val().trim();
				var oldMobile = $("#oldMobileId").val().trim();
				if(!htmlVal == ''){
					$(".input_warning").html('输入不正确，请确认后再操作!').css("color", "red");
					return false;
				}else if(newMobile == '' || oldMobile == ''){
					$(".input_warning").html('新手机号不能为空!').css("color", "red");
					return false;
				}else{
					$.dialog.confirm('确定要修改会员手机号吗?', function(){
						$.ajax( {
							type: "GET",
					         url: "../platform/modifyUserMobile?userId="+userId+"&newMobile="+newMobile+"&oldMobile="+oldMobile,
					         dataType:'json',
					         success: function (result) {
					        	 if(result == 1){
					        		 alert("修改成功");
					        		 window.location.href = "../platform/userUpdateMobilelist?pageSize=20&page=${page}";
					        	 }else{
					        		 alert("修改失败");
					        	 }
					          }
						}, function(){
						   // $.dialog.tips('执行取消操作');
						});
			    	});
				}
			}
		}
		function ret(){
            window.location.href = "../platform/userUpdateMobilelist?pageSize=20&page=${page}";
		}
	</script>  
</html>