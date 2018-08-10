<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
	<head>
	  <meta charset="UTF-8">
	  <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	  <title>UNICORN-商户信息</title>
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
		.divfile{
		   display: none;
		}
		#thief_warning{
			height:12px;
		}
		#filespan {margin:0;}
		
	</style>
	<script type="text/javascript">

	  var subUpdate =function(){
		  alert($('#textfield').val());
		if($('#textfield').val()!='') {	
			$("#vatInvoices").submit();
		}
	 }	
	</script>
</head>
    <body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	 <div class="wrap">
		<%@include file="/WEB-INF/views/include/leftUser.jsp"%>
		<div class="right f_l">
			<div class="title">

				<p class="c1">基本信息</p>
				<div class="clear"></div>
			</div>
			<div class="blank5"></div>
			<div class="cont">
				<ul class="ul_vertical">
				<form id="vatInvoices"  method="post" action="${path}/user/addVatInvoices" enctype="multipart/form-data" >
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>销售人员：</p>
							<c:if test="${!empty allSale}">
								<select class="sec" name="saleId">
										<c:forEach items="${allSale}" var="sa">
											 <option <c:if test="${data.saleId == sa.saleId}">selected</c:if> value="${fn:escapeXml(sa.saleId)}">${fn:escapeXml(sa.saleName)}</option>
										</c:forEach>
								</select>
							</c:if>
						<span  class="input_warning"></span>
					</li>
				
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>经营主体名称：</p>
						<input class="i1" name ="name" value="${fn:escapeXml(data.businessName)}"  onblur="validateSingle(this, 'notnull')" />
							<span  class="input_warning"></span>
					</li>
		           <li class="blank20"></li>
				    <li>
						<p class="p1"><b style=" color: #FF0000;">*</b>营业执照注册号：</p>
						<input class="i1" name="address" value="${fn:escapeXml(data.businessLicense)}" onblur="validateSingle(this, 'notnull')" >
							<span  class="input_warning"></span>
					</li>
							           <li class="blank20"></li>
				    <li>
						<p class="p1"><b style=" color: #FF0000;">*</b>商户名称：</p>
						<input class="i1" name="address" value="${fn:escapeXml(data.name)}" onblur="validateSingle(this, 'notnull')" >
							<span  class="input_warning"></span>
					</li>
							           <li class="blank20"></li>
				    <li>
						<p class="p1"><b style=" color: #FF0000;"></b>商户编号：</p>
						<input class="i1" name="address" value="${fn:escapeXml(data.retailerCode)}" onblur="validateSingle(this, 'notnull')" >
							<span  class="input_warning"></span>
					</li>
							           <li class="blank20"></li>
				    <li>
						<p class="p1"><b style=" color: #FF0000;">*</b>商户地址：</p>
						<input class="form-text" name="address" value="${fn:escapeXml(data.address)}" onblur="validateSingle(this, 'notnull')" >
							<span  class="input_warning"></span>
					</li>
		
					<li class="blank20"></li>
					 <li>
						<p class="p1"><b style=" color: #FF0000;">*</b>地址：</p>
						<select class="se"><option>${provincename}</option></select>
						<select class="se"><option>${city}</option></select>
						<select class="se" name="areaId">
						<c:if test="${!empty data.areaId}">
							<c:forEach items="${counties}" var="agentCounty">
								<option <c:if test="${agentCounty.countyid == data.areaId}">selected</c:if> value="${agentCounty.countyid}">${agentCounty.countyname}</option>
							</c:forEach>
						</c:if>
						
						</select>
						<span  class="input_warning"></span>
					</li>
		
					<li class="blank20"></li>
					
				    <li>
						<p class="p1"><b style=" color: #FF0000;">*</b>经营者姓名：</p>
						<input class="i1" name="address" value="${fn:escapeXml(data.legalPerson)}" onblur="validateSingle(this, 'notnull')" >
							<span  class="input_warning"></span>
					</li>
						<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>经营者证件编号：</p>
						<input class="i1" name ="contactor" value="${fn:escapeXml(data.legalPersonNo)}" onblur="validateSingle(this, 'notnull')">
							<span  class="input_warning"></span>
						
					</li>
						<li class="blank20"></li>
				<%-- 	<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>联系人姓名：</p>
						<input class="i1" name ="mobile" value="${fn:escapeXml(data.contactor)}" onblur="validateSingle(this, 'telphone')">
							<span  class="input_warning"></span>
					</li>
						<li class="blank20"></li> --%>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>手机：</p>
						<input class="i1" name="email" value="${data.mobile}">
							<span  class="input_warning"></span>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;"></b>电话/传真：</p>
						<input class="i1" name ="fax" value="${fn:escapeXml(data.fax)}">
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>邮箱：</p>
						<input class="i1" name ="phone" value="${fn:escapeXml(data.email)}">
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>开户行：</p>
						<input class="i1" name ="phone" value="${fn:escapeXml(data.accountBank)}">
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>银行账号：</p>
						<input class="i1" name ="phone" value="${fn:escapeXml(data.accountNumber)}">
					</li>
					<li class="blank20"></li>
					<li>
					    <p class="p1">工商执照：</p>
						<c:if test="${not empty dataFile.businessLicenseUrl }">
						<a href="${path}/user/downloadFile?url=${dataFile.businessLicenseUrl}" target="downloadFileIframe">工商执照下载</a>
						</c:if>
					    <c:if test="${empty dataFile.businessLicenseUrl }">
						<span style="margin-top:10px; display: inline-block;">工商执照 &nbsp;&nbsp;&nbsp;<b style=" color: #FF0000;">未上传</b></span>
						</c:if>
					</li>				
					<li class="blank20"></li>
				   <%--  <form id="vatInvoices"  method="post" action="${path}/platform/regist" enctype="multipart/form-data" >
					<li>
					    <p class="p1">增值税发票：</p>
						<c:if test="${not empty dataFile.vatInvoicesUrl }">
							<a href="${path}/user/downloadFile?url=${dataFile.vatInvoicesUrl}" target="downloadFileIframe">增值税发票下载</a>
							
						</c:if>
					    <c:if test="${empty dataFile.businessLicenseUrl }">
					        <input class="i1" name ="fax" value="${fn:escapeXml(data.fax)}">
						</c:if>
					</li>				 --%>
					<%-- <form id="vatInvoices"  method="post" action="${path}/user/addVatInvoices" enctype="multipart/form-data" > --%>
					  <input  type="hidden"  name="source" value="2">
					  <input  type="hidden"  name="retailerId" value="${data.retailerId}">
					  <input  type="hidden"  name="fileId" value="${dataFile.fileId}">
						<li>
							<p class="p1">增值税发票：</p>
							<c:if test="${not empty dataFile.vatInvoicesUrl }">
							   <c:if test="${!empty buttonsMap['商户审核-添加发票']}">
							     <span id="filespan">
							       <input id="textfield1" class="t1 " type="text" readonly="readonly" >
								   <input id="fileField" class="file " type="file" onchange="document.getElementById('textfield1').value=this.value"  size="28" name="myfile" >
			                       <input class="btn" type="button"  value="变更"   style="float: none;" tabindex="-1"   >
		                         </span>
		                       </c:if>
							   <a  style="margin-top:1px;" href="${path}/user/downloadFile?url=${dataFile.vatInvoicesUrl}" target="downloadFileIframe">增值税发票下载</a>
					        </c:if>
					        <c:if test="${empty dataFile.vatInvoicesUrl and not empty buttonsMap['商户审核-添加发票']}">
						         <span id="filespan">
						           <input id="textfield1" class="t2 " type="text" readonly="readonly" >
								   <input id="fileField" class="file" type="file" onchange="document.getElementById('textfield1').value=this.value"  size="28" name="myfile" >
			                       <input class="btn" type="button"  value="上传"   style="float: none;" tabindex="-1"   >
			                      </span>
					        </c:if>
						</li>
						<li class="blank20"></li>
						<li>
						<p class="p1">一般纳税人资质：</p>
							<c:if test="${not empty dataFile.effectiveCertificateUrl }">
							   <c:if test="${!empty buttonsMap['商户审核-添加一般纳税人资质']}">
							     <span id="filespan">
							       <input id="textfield2" class="t1 " type="text" readonly="readonly" >
								   <input id="fileField3" class="file " type="file" onchange="document.getElementById('textfield2').value=this.value"  size="28" name="myfile3" >
			                       <input class="btn" type="button"  value="变更"   style="float: none;" tabindex="-1"   >
								   <a  style="margin-top:1px;" href="${path}/user/downloadFile?url=${dataFile.effectiveCertificateUrl}" target="downloadFileIframe">一般纳税人资质下载</a>
		                         </span>
		                       </c:if>
					        </c:if>
					        <c:if test="${empty dataFile.effectiveCertificateUrl and not empty buttonsMap['商户审核-添加一般纳税人资质']}">
						         <span id="filespan">
						           <input id="textfield2" class="t1 " type="text" readonly="readonly" >
								   <input id="fileField3" class="file" type="file" onchange="document.getElementById('textfield2').value=this.value"  size="28" name="myfile3" >
			                       <input class="btn" type="button"  value="上传"   style="float: none;" tabindex="-1"   >
			                      </span>
					        </c:if>
						</li>
						<p class="blank20"></p>
						<li>
							<c:if test="${!empty buttonsMap['商户审核-添加发票']}">
						      <input type="button"  class="fabu_btn" onclick="subUpdate()" value="修改" ></input>	 
						    </c:if>
							<input type="button"  class="fabu_back" onclick="go()" value="返回" >
							
							<p class="blank30"></p>
						</li>
					 </form>
					
					</ul>
					
						<div class="eck">
						   <c:if test="${!empty buttonsMap['商户审核-审核商户']}">
							   <c:if test="${1!=data.status&& 2!=data.status}">
								<div class="eck1"><button type="text" onclick="updateCheck('${data.retailerId}',1,1)"  class="tong1">通过</button></div>
								<div class="eck2">
									 <form id="checkForm" >
									 	<input type="hidden" id="isRecord" name="isRecord" class="text1">
								        <input type="hidden" id="id1" name="id1" class="text1">
								        <input type="hidden" id="status1" name="status1"   class="text1">
								        <input type="hidden" id="source1" name="source"  value="2" class="text1">
										<textarea id="reason" rows=""  name="reason" cols=""></textarea>
									</form>
									<!-- <span class="bfu"><input type="checkbox">公司名称与实际不符</span> -->
								</div>
								<div class="eck3"><button type="text" onclick="updateCheck('${data.retailerId}',2,0)"  class="tong2">不通过</button></div>
						      </c:if>
					      </c:if>
					      
 						  <c:if test="${2==data.status}">
						    <span style="color:red;">审核不通过原因</span>
						    <div style="color:red;"></div>
						    </br>
							<div class="eck2">
									<textarea id="reason" rows="" disabled="disabled"  name="reason" cols="">${data.remark}</textarea>
							</div>
						  </c:if>
					  </div>
				    
			</div>
			
		
		</div>
		<!-- 右边 end -->
	</div>
	<p class="blank30"></p>
		   
		</div>
		<div class="clear"/>
		<iframe name="downloadFileIframe" style="display:none"></iframe>
 </body>
<script type="text/javascript">


	function go(){
		 window.location.href = "../user/checklist?source=2";	
	}
	
	
	$(function(){
		var message='${message}'; 
		if(message=='ok'){
			alert("操作成功!");
		}
		if(message=='1'){
			alert("缺少参数,操作失败!");
		}
		if(message=='2'){
			alert("上传失败,请重新选择上传文件!"); 		
		}
		if(message=='3'){
			alert("上传文件到图片服务器出错,请稍后再试!"); 	
		}
		if(message=='4'){
			alert("服务器忙,请稍后再试!"); 	
		}
		$("#reason").keyup(function(){
			var len = $(this).val().length;
			if(len > 1000){
			$(this).val($(this).val().substring(0,500));
			alert("字数不能超过500字!");
			}
		});
	});
	  
/* 	function checkFile(){
		  var filepath=$("#shangchuan").val(); 
          var extStart=filepath.lastIndexOf("."); 
          var ext=filepath.substring(extStart,filepath.length).toUpperCase(); 
          if(ext!=".JPG"&&ext!=".JPEG"&&ext!=".PNG"&&ext!=".DOCX"){ 
         // $("#fileshang").html("<font class='error_mes' >文件格式不正确！</font>");
           alert(); 
         $("#subject").focus();
            return false; 
	}; */
	
   	function updateCheck(id,status,isRecord){
  		if(status==2&&$("#reason").val()==''){
  			alert("请填写审核失败原因!");
  			return;
  		}else{
  			
			$("#id1").val(id);
			$("#status1").val(status);
			$("#isRecord").val(isRecord);
   			var source = $("#source1").val();
			var fmdata = $("#checkForm").serialize();
			$.ajax({
		         type: "POST",
		         data: fmdata+"&"+Math.random(),
		         url: "../user/updateCheck",
		         dataType:'json',
		         success: function (result) {
		        	 if(result == '1'){
		        		 tipMessage("审核成功!",function(){
		        	  		 window.location.href = "../user/checklist?source="+source;
		        		 });
		        	 }
		        	 if(result == '0'){
		        		alert("服务调用异常！");
		        	 }
		        	 if(result == '-1'){
			        		alert("用户信息异常！");
			         }
		          }
		 	   });
      	}
	}
</script>
</html>