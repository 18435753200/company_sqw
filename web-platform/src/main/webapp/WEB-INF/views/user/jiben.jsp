<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
	<head>
	  <meta charset="UTF-8">
	  <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	  <title>UNICORN-基本信息管理</title>
	  <%@include file="/WEB-INF/views/include/base.jsp"%>
	  <link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
	  <script type="text/javascript" src="${path}/commons/js/user/jquery-1.7.2.js"></script>
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

	  var subUpdate =function(){
		if($('.fabu_btn').val()=="保存") {	
			if(formSubmit()){
				$("#formID").submit();
			};
		}else{
			$('.i1').removeAttr('disabled');
			$('.te').removeAttr('disabled');
			$('.fabu_btn').val("保存");
			$(".div1").css("display","block");
		}
	}	
	</script>
    </head>
	<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	
	 <div class="wrap">
		<%@include file="/WEB-INF/views/include/leftUser.jsp"%>
		<form  id="formID"  action="${path}/platform/update" method="post" enctype="multipart/form-data">
		<input class="i1" type="hidden" name ="platformId" value="${data.platformId}"/>
		<div class="right">
			<div class="title">

				<p class="c1">基本信息管理</p>
				<div class="clear"></div>
			</div>
			<div class="blank5"></div>
			<div class="cont">
				<ul class="ul_vertical">
					<li>
						<p class="p1">当前头像：</p>
						<p class="i2"><img src="${data.icon}" alt="" width="100%" height="100%"><p>
						<p class="bt">
						    <div class="div1" style="display: none;">
								<div class="div2">上传图像</div>
								<input type="file" name="iconUrl"  class="inputstyle">
							</div>
						</p>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>公司名称：</p>
						<input class="i1" name ="name" value="${fn:escapeXml(data.name)}"  onblur="validate(this, 'notnull,biglong')">
							<span  class="input_warning"></span>
					</li>

						<li class="blank20"></li>
				    <li>
						<p class="p1"><b style=" color: #FF0000;">*</b>详细地址：</p>
						<input class="i1" name="address" value="${fn:escapeXml(data.address)}" onblur="validate(this, 'notnull,biglong')" >
							<span  class="input_warning"></span>
					</li>
						<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>联系人：</p>
						<input class="i1" name ="contactor" value="${fn:escapeXml(data.contactor)}"  onblur="validate(this, 'notnull,long')">
							<span  class="input_warning"></span>
						
					</li>
						<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>手机：</p>
						<input class="i1" name ="mobile" value="${fn:escapeXml(data.mobile)}" onblur="validate(this, 'notnull,phone')">
							<span  class="input_warning"></span>
					</li>
						<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>邮箱：</p>
						<input class="i1" name="email" value="${fn:escapeXml(data.email)}" onblur="validate(this, 'notnull,email')">
							<span  class="input_warning"></span>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>电话：</p>
						<input class="i1" name ="phone" value="${fn:escapeXml(data.phone)}"  onblur="validate(this, 'notnull,telphone')">
							<span  class="input_warning"></span>
					</li>
						<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>传真：</p>
						<input class="i1" name ="fax" value="${fn:escapeXml(data.fax)}"  onblur="validate(this, 'notnull,telphone')">
							<span  class="input_warning"></span>
					</li>
						<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>邮政编码：</p>
						<input class="i1" name ="post" value="${fn:escapeXml(data.post)}"  onblur="validate(this, 'notnull,post')">
							<span  class="input_warning"></span>
					</li>
					<%--<li class="blank20"></li>
					<li>
						<p class="p1">公司网址：</p>
						<input class="i1" name="companyWebsite" value="${data.companyWebsite}">
							<span  class="input_warning"></span>
					</li>
						<li class="blank20"></li>
					<li>
						<p class="p1">附件：</p>
						<a style="font-size：12px; " href="${path}/platform/download?url=${data.companyDetailedUrl}">供应商详情附件</a>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">公司简介：</p>
						<p class="i4"><textarea rows="9" name="companyDescribe" cols="37" class="te">${data.companyDescribe} </textarea></p>
					</li>
					--%>
					</ul>
					<p class="blank30"></p>
					<c:if test="${!empty buttonsMap['基本信息管理-修改']}">
				   		<input type="button"  class="fabu_btn" onclick="subUpdate()" value="修改" ></input>
				    </c:if>
				    <p class="blank30"></p>

			</div>
			
		
		</div>
	             </form>			 
		<!-- 右边 end -->
	</div>


		
<div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
   </body>
</html>