<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
<title>预留号段占位</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
<link rel="stylesheet" type="text/css" href="${path}/commons/css/kuc1.css"/>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	 <div class="wrap">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->

		<!-- 右边 start -->
		<div  class="right" style="min-height: 2px;">
			<div class="c21">
			<div class="title">
				<p>卖家中心&nbsp;&gt;&nbsp; </p>
				<p>平台会员管理&nbsp;&gt; </p>
				<p class="c1">预留号段占位</p>
			</div>
			<div class="xia" style="">
					<form id="SearchFrom"  action="${path}/platform/toUserStance" method="post" >
					</form>
				</div>
	     </div>
			<div class="blank5"></div>
			<div class="cont" style="padding-top: 5px;">
				<p style="padding-top:15px;margin-left: 15px;color: #ff6600;font-weight: bold;font-size: 15px;">号段占位：</p>
				<ul class="ul_vertical">
					<li>
						<p class="p1">占位开始：</p>
						<input type="text" id="startUserId" name="startUserId" onblur="checkVal('1')" value="" style="margin-left: 10px;"/>&nbsp;
						<span  class="input_warning1"></span>
						<br/><br/>
					</li>
					<li>
						<p class="p1">占位结束：</p>
						<input type="text" id="endUserId" name="" value="" onblur="compareStance('2')" style="margin-left: 10px;"/>&nbsp;
						<span  class="input_warning2"></span>
						<br/><br/>
					</li>
					<li style="margin-left: 30px;">
					<input type="button"  class="fabu_btn" onclick="operatecomDiv()" value="占位" ></input>
					</li>
					<div class="blank15"></div>
				</ul>
			</div>
			</div>	
<!-- 			<div class="blank5"></div> -->
<!-- 			<div class="cont" style="padding-top: 5px;"> -->
<!-- 				<p style="padding-top:15px;margin-left: 15px;color: #ff6600;font-weight: bold;font-size: 15px;">分配号段：</p> -->
<!-- 				<ul class="ul_vertical"> -->
<!-- 					<li> -->
<!-- 						<p class="p1">手机号：</p> -->
<!-- 						<input type="text" id="userMobileId" name="userMobileVal" onblur="checkIsExist('3')" value="" style="margin-left: 10px;"/>&nbsp; -->
<!-- 						<span  class="input_warning3"></span> -->
<!-- 						<br/><br/> -->
<!-- 					</li> -->
<!-- 					<li> -->
<!-- 						<p class="p1">邀请码：</p> -->
<!-- 						<input type="text" id="tjUserMobileId" name="tjUserMobileVal" value="" onblur="checkIsExist('4')" style="margin-left: 10px;"/>&nbsp; -->
<!-- 						<span  class="input_warning4"></span> -->
<!-- 						<br/><br/> -->
<!-- 					</li> -->
<!-- 					<li> -->
<!-- 						<p class="p1" style="margin-right: 10px;">已占号段：</p> -->
<!-- 						 <select id="userTypeId" name ="type"> -->
<!-- 						 	<option  value="-1">--请选择号段--</option> -->
<%-- 						 	<c:forEach items="${userStanceList}" var="userStance"> --%>
<%-- 						      <option  value="${userStance.userId}">${userStance.userId}</option> --%>
<%-- 						 	</c:forEach> --%>
<!-- 						  </select> -->
<!-- 						<span  class="input_warning"></span> -->
<!-- 						<br/><br/> -->
<!-- 					</li> -->
<!-- 					<li style="margin-left: 30px;"> -->
<!-- 					<input type="button"  class="fabu_btn" onclick="sendUser()" value="发号" ></input> -->
<!-- 					</li> -->
<!-- 					<div class="blank15"></div> -->
<!-- 				</ul> -->
<!-- 			</div> -->
	<div class="center">
		<div class="c2">
			<div class="c24" id="c24">
				<table>
					<tr>
					 <th class="ar1">用户ID</th>
<!-- 					 <th class="ar1">用户名</th> -->
					 <th class="ar1">手机号</th>
					 <th class="ar1">邀请码</th>
					 <th class="ar4">操作</th>
					</tr>
					<c:if test="${empty page.result}">
						<tr>
							<td  <c:if test="${2==param.source}">colspan="8"</c:if><c:if test="${1==param.source }">colspan="7"</c:if><c:if test="${3==param.source ||empty param.source}">colspan="6"</c:if>>
								<center><img src="${path }/commons/images/no.png" /></center>
							</td>
						</tr>
					</c:if>
						<c:forEach items="${page.result }" var="data">
							<tr>
								<td>${fn:escapeXml(data.userId)}</td>
<!-- 							 	<td> -->
<%-- 								 	<input id="userName_${data.userId}" name="userName" value=""/> --%>
<!-- 								</td> -->
								<td>
					  	 			<input id="userMobileId_${data.userId}" onblur="checkIsExist('${data.userId}',1)" value=""/>
					 			 </td>
								<td>
					  	 			<input id="tjUserMobileId_${data.userId}" onblur="checkIsExist('${data.userId}',2)" value="" />
					 			 </td>
								<td>
									<span class="ckan" style="cursor: pointer;color: red;" onclick="sendUser('${data.userId}')">发号</span>
								</td>
							</tr>
						</c:forEach>
				</table>
				<c:if test="${!empty page.result}">
					<%@include file="/WEB-INF/views/include/page.jsp"%>
				</c:if>
			</div>
		</div>
	</div>
<!-- 		</div>			  -->
		<!-- 右边 end -->
	</div>
		
<div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
   </body>
<script type="text/javascript">
	
	function checkVal(id){
		var reg = '^[1-9]\\d*$';
		var start = $("#startUserId").val().trim();
		if(!start.match(reg)){
			$(".input_warning"+id).html('非法输入,请输入正整数').css("color", "red");
			$("#startUserId").val('');
			return false;
		}else{
			$(".input_warning"+id).html('').css("color", "");
		}
	}
	
	function compareStance(id){
		var reg = '^[1-9]\\d*$';
		var start = $("#startUserId").val().trim();
		var end = $("#endUserId").val().trim();
// 		if(start == '' || end == ''){
// 			$(".input_warning"+id).html('不能为空,请认真输入').css("color", "red");
// 			return false;
// 		}
		if(!end.match(reg)){
			$(".input_warning"+id).html('非法输入,请输入正整数').css("color", "red");
			$("#endUserId").val('');
			return false;
		}else{
			$(".input_warning"+id).html('').css("color", "");
		}
		if((end-start)<0 || (end-start) > 500){
			$(".input_warning"+id).html('结束值必须大于开始值并且不能超过500个').css("color", "red");
			$("#endUserId").val('');
			return false;
		}
		
	}
 function operatecomDiv(){
	 var startUserIdVal = $("#startUserId").val().trim();
	 var endUserIdVal = $("#endUserId").val().trim();
	 if(!RegExp("^[1-9]\\d*$").test(startUserIdVal) || !RegExp("^[1-9]\\d*$").test(endUserIdVal) 
			 || (endUserIdVal-startUserIdVal)<0 || (endUserIdVal-startUserIdVal)>500){
		 alert("输入规则不对,请认真填写！");
		 return;
	 }
	 $.ajax({
			type:'post',
			url:'../platform/stanceUser',
			data: 'startUserId='+startUserIdVal+'&endUserId='+endUserIdVal,
			success:function(data){
				if(data=='1'){
					tipMessage("占位成功！",function(){
						location.href='../platform/toUserStance';
					});
				}else if(data == '2'){
					tipMessage("所输入的号段已存在,请重新输入！",function(){
						location.href='../platform/toUserStance';
					});
				}else if(data == '0'){
					tipMessage("网络异常,占位失败！",function(){
						location.href='../platform/toUserStance';
					});
				}
			}
		});
 }
 
 	function checkIsExist(id,id2){
		var reg = "^0?(13|15|17|18|14)[0-9]{9}$";
		var mobileVal = $("#userMobileId_"+id).val().trim();
		var tjMobileVal = $("#tjUserMobileId_"+id).val().trim();
		if(id2 == 1){
			if(!mobileVal.match(reg)){
				alert('手机号不能为空或规则不正确，请重新输入!');
				$("#userMobileId_"+id).val('');
				return false;
			}else{
				$.ajax({
	  	   		     type:"POST",
	  	   		     url:"../platform/checkTJUserIsExists?tjName="+mobileVal, 
	  	   		     dataType:'html',
	  	   		     success : function(result) {
		  	   		     if(result != 0){
		  	   		    	alert('手机存在，请重新输入!');
		  	   		   		$("#userMobileId_"+id).val('');
		  	   		    	return false;
		  	   		     }
	  	   			},
	        	}); 
			}
		}else{
			if(!tjMobileVal.match(reg)){
				alert('手机号不能为空或规则不正确，请重新输入!');
				$("#tjUserMobileId_"+id).val('');
				return false;
			}else{
				$.ajax({
	  	   		     type:"POST",
	  	   		     url:"../platform/checkTJUserIsExists?tjName="+tjMobileVal, 
	  	   		     dataType:'html',
	  	   		     success : function(result) {
 		  	   		     if(result == 0){
 		  	   		    	alert('邀请码不存在，请重新输入!');
 		  	   		 		$("#tjUserMobileId_"+id).val('');
 		  	   		    	return false;
 		  	   		     }
	  	   			},
	        	}); 
			}
		}
 	}
 	
 	function sendUser(id){
 		var reg = "^0?(13|15|17|18|14)[0-9]{9}$";
 		var mobileVal = $("#userMobileId_"+id).val().trim();
 		var tjMobileVal = $("#tjUserMobileId_"+id).val().trim();
//  		var userName = $("#userName_"+id).val().trim();
 		if(!mobileVal.match(reg) || !tjMobileVal.match(reg)){
 			alert("数据输入不正确，请重新输入！");
 			return false;
 		}else{
 			$.dialog.confirm('确定要分配该号段吗?', function(){
	 			$.ajax({
	 	   		     type:"POST",
	 	   		     url:"../platform/sendUserAndModifyMobile", 
	 	   		     dataType:'json',
	 	   		     data:{
	 	   		    	 "userId":id,
	 	   		    	 "mobile":mobileVal,
	 	   		    	 "tjMobile":tjMobileVal
	 	   		     },
	 	   		     success : function(result) {
		 	   		     if(result == 1){
			        		 alert("分号成功!");
			        		 window.location.href = "../platform/toUserStance";
			        	 }else{
			        		 alert("分号失败");
			        	 }
	   				},
	       		}); 
 			});
 		}
 	}
</script>
</html>