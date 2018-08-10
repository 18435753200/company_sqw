<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
	<head>
	  <meta charset="UTF-8">
	  <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	   <title>UNICORN-商户信息</title>
	   <%@include file="/WEB-INF/views/include/base.jsp"%>
	   <link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben1.css">
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
		ol, ul,li {
			list-style: none;
		}
	</style>
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
     			<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>公司名称：</p>
						<input class="i1" name ="companyName" value="${fn:escapeXml(data.companyName)}"  onblur="validate(this, 'notnull,biglong')" />
						<span  class="input_warning"></span>
					</li>
					
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>法人姓名：</p>
						<input class="i1" name ="legalPerson" value="${fn:escapeXml(data.legalPerson)}"  onblur="validate(this, 'notnull,biglong')" />
							<span  class="input_warning"></span>
					</li>
					
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>法人证件号码：</p>
						<input class="i1" name ="legalPersonNo" value="${fn:escapeXml(data.legalPersonNo)}"  onblur="validate(this, 'notnull,biglong')" />
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
						<input class="i1" name ="contactor" value="${fn:escapeXml(data.contactor)}" onblur="validate(this, 'notnull,long')">
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
						<p class="p1"><b style=" color: #FF0000;"></b>电话：</p>
						<input class="i1" name ="phone" value="${fn:escapeXml(data.phone)}" onblur="validate(this, 'notnull,telphone')">
							<span  class="input_warning"></span>
					</li>

						<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;"></b>邮政编码：</p>
						<input class="i1" name ="post" value="${fn:escapeXml(data.post)}"  onblur="validate(this, 'notnull,post')">
							<span  class="input_warning"></span>
					</li>
					
					<li class="blank20"></li>
					<li>
						<p class="p1">资质文件：</p>
						<p class="p2">
							<c:if test="${not empty dataFile.businessLicenseUrl }">
							<a href="${path}/user/downloadFile?url=${dataFile.businessLicenseUrl}" target="downloadFileIframe">营业执照下载</a>
							</c:if>
							<c:if test="${empty dataFile.businessLicenseUrl }">
							营业执照   &nbsp; &nbsp;<b style=" color: #FF0000;"> 未上传</b>
							 </c:if>
						</p>
					 </li>
					<li class="blank20"></li>
					<li>
						<p class="p1"></p>
						<p class="p2">
							<c:if test="${not empty dataFile.taxLicenseUrl }">
							  <a href="${path}/user/downloadFile?url=${dataFile.taxLicenseUrl}" target="downloadFileIframe">税务登记证下载</a>
							</c:if>
							<c:if test="${empty dataFile.taxLicenseUrl }">
							税务登记证 &nbsp; &nbsp; <b style=" color: #FF0000;"> 未上传</b>
							 </c:if>
						</p>
					 </li>
					 <li class="blank20"></li>
					 <li>
						  <p class="p1"></p>
						  <p class="p2">
							<c:if test="${not empty dataFile.accountLicensesUrl }">
							<a href="${path}/user/downloadFile?url=${dataFile.accountLicensesUrl}" target="downloadFileIframe">开户许可证下载</a>
							</c:if>
							<c:if test="${empty dataFile.accountLicensesUrl }">
							开户许可证  &nbsp; &nbsp;<b style=" color: #FF0000;"> 未上传</b>
							 </c:if>
						   </p>
					 </li>
					 
					  <li class="blank20"></li>
					  <li>
						  <p class="p1"></p>
						  <p class="p2">
							<c:if test="${not empty dataFile.taxpayerCertificateUrl }">
							<a href="${path}/user/downloadFile?url=${dataFile.taxpayerCertificateUrl}" target="downloadFileIframe">一般纳税人资格证明下载</a>
							</c:if>
							<c:if test="${empty dataFile.taxpayerCertificateUrl }">
							一般纳税人资格证明 &nbsp; &nbsp; <b style=" color: #FF0000;"> 未上传</b>
							 </c:if>
						  </p>
					 </li>		
					 
					  <li class="blank20"></li>
					  <li>
						  <p class="p1"></p>
						  <p class="p2">
							<c:if test="${not empty dataFile.legalCertificateUrl }">
							<a href="${path}/user/downloadFile?url=${dataFile.legalCertificateUrl}" target="downloadFileIframe">法人代表身份证明文件下载</a>
							</c:if>
							<c:if test="${empty dataFile.legalCertificateUrl }">
							法人代表身份证明文件  &nbsp; &nbsp;<b style=" color: #FF0000;"> 未上传</b>
							 </c:if>
						 </p>
					 </li>		
					 
					  <li class="blank20"></li>
					  <li>
					  <p class="p1"></p>
					    <p class="p2">
						<c:if test="${not empty dataFile.exportRegistrationUrl }">
						<a href="${path}/user/downloadFile?url=${dataFile.exportRegistrationUrl}" target="downloadFileIframe">对外贸易经营者备案登记表下载</a>
						</c:if>
						<c:if test="${empty dataFile.exportRegistrationUrl }">
						对外贸易经营者备案登记表 &nbsp; &nbsp; <b style=" color: #FF0000;"> 未上传</b>
						 </c:if>
						  </p>
					 </li>
					 
					  <li class="blank20"></li>
					  <li>
						  <p class="p1"></p>
						  <p class="p2">
							<c:if test="${not empty dataFile.goodsRegistrationUrl }">
							<a href="${path}/user/downloadFile?url=${dataFile.goodsRegistrationUrl}" target="downloadFileIframe">进出口货物收发货人报关注册登记证下载</a>
							</c:if>
							<c:if test="${empty dataFile.goodsRegistrationUrl }">
							进出口货物收发货人报关注册登记证 &nbsp; &nbsp; <b style=" color: #FF0000;"> 未上传</b>
							 </c:if>
						   </p>
					 </li>		
					 
					  <li class="blank20"></li>
					  <li>
						  <p class="p1"></p>
						  <p class="p2">
							<c:if test="${not empty dataFile.foodCirculationUrl }">
							<a href="${path}/user/downloadFile?url=${dataFile.foodCirculationUrl}" target="downloadFileIframe">食品流通许可证下载</a>
							</c:if>
							<c:if test="${empty dataFile.foodCirculationUrl }">
							食品流通许可证 &nbsp; &nbsp; <b style=" color: #FF0000;"> 未上传</b>
							 </c:if>
						   </p>
					 </li>	
					 
					 <li class="blank20"></li>
					  <li>
						  <p class="p1"></p>
						  <p class="p2">
							<c:if test="${not empty dataFile.importRegistrationUrl }">
							<a href="${path}/user/downloadFile?url=${dataFile.importRegistrationUrl}" target="downloadFileIframe">进口食品/化妆品收货人检验检疫备案登记证下载</a>
							</c:if>
							<c:if test="${empty dataFile.importRegistrationUrl }">
							进口食品/化妆品收货人检验检疫备案登记证 &nbsp; &nbsp; <b style=" color: #FF0000;"> 未上传</b>
							 </c:if>
						  </p>
					 </li>	
					 
					  <li class="blank20"></li>
					  <li>
						  <p class="p1"></p>
						  <p class="p2">
							<c:if test="${not empty dataFile.liquorLicenseUrl }">
							<a href="${path}/user/downloadFile?url=${dataFile.liquorLicenseUrl}" target="downloadFileIframe">酒类经营许可证/酒类流通备案登记表下载</a>
							</c:if>
							<c:if test="${empty dataFile.liquorLicenseUrl }">
							酒类经营许可证/酒类流通备案登记表 &nbsp; &nbsp;<b style=" color: #FF0000;"> 未上传</b>
							 </c:if>
						   </p>
					 </li>	
					 
					  <li class="blank20"></li>
					  <li>
						  <p class="p1"></p>
						  <p class="p2">
							<c:if test="${not empty dataFile.foodLicenseUrl }" >
							<a href="${path}/user/downloadFile?url=${dataFile.foodLicenseUrl}" target="downloadFileIframe">保健食品卫生许可证（经营许可证）下载</a>
							</c:if>
							<c:if test="${empty dataFile.foodLicenseUrl }">
							   保健食品卫生许可证（经营许可证） <b style=" color: #FF0000;"> 未上传</b>
							 </c:if>
						   </p>
					 </li>
					 <p class="blank30"></p>
					<%--<li class="blank20"></li>
					<li>
						<p class="p1">公司网址：</p>
						<input class="i1" name="companyWebsite" value="${data.companyWebsite}">
							<span  class="input_warning"></span>
					</li>
						<li class="blank20"></li>
					<li>
						<p class="p1">附件：</p>
						<a style="font-size：12px; " href="${path}/platform/download?url=${data.companyDetailedUrl}" target="downloadFileIframe">供应商详情附件</a>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">公司简介：</p>
						<p class="i4"><textarea rows="9" name="companyDescribe" cols="37" class="te">${data.companyDescribe} </textarea></p>
					</li>
					--%><p class="blank30"></p>
					
					<div class="eck">
					   <c:if test="${!empty buttonsMap['商户审核-审核商户']}">
						   <c:if test="${1!=data.status&& 2!=data.status}">
							<div class="eck1"><button type="text" onclick="updateCheck('${data.dealerId}',1,1)"  class="tong1">通过</button></div>
							<div class="eck2">
								 <form id="checkForm">
								 	<input type="hidden" id="supplyType" name="supplyType" class="text1" value="-1">
								 	<input type="hidden" id="isRecord" name="isRecord" class="text1">
							        <input type="hidden" id="id1" name="id1" class="text1">
							        <input type="hidden" id="status1" name="status1"   class="text1">
							        <input type="hidden" id="source1" name="source"  value="3" class="text1">
									<textarea id="reason" rows=""  name="reason" cols=""></textarea>
								</form>
								<!-- <span class="bfu"><input type="checkbox">公司名称与实际不符</span> -->
							</div>
							<div class="eck3"><button type="text" onclick="updateCheck('${data.dealerId}',2,0)"  class="tong2">不通过</button></div>
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
				    
				     <input type="button"  class="fabu_btn" onclick="go()" value="返回" ></input>
					<p class="blank30"></p>
			</div>
		</div>
		<!-- 右边 end -->
	</div>
	<p class="blank30"></p>
		   
		</div>
		<div class="clear"/>
		
		
		 <div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	 <iframe name="downloadFileIframe" style="display:none">
	     
	 </iframe> 
	</body>
    <script type="text/javascript">
          $(function(){
        	  $("#reason").keyup(function(){
        	   var len = $(this).val().length;
        	   if(len > 1000){
        	    $(this).val($(this).val().substring(0,500));
        	    alert("字数不能超过500字!");

        	   }
        	  });
        	 });
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
          
          
          function go(){
      		var source = $("#source1").val();
      		 window.location.href = "../user/checklist?source=3";	
      	}
        </script>
</html>