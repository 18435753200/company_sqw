<%@ page language="java"  import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	  <meta charset="utf-8">
	 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	  <title>商家后台管理系统-查看运营商</title>
      <%@include file="/WEB-INF/views/zh/include/base.jsp"%>
	  <link rel="stylesheet" type="text/css" href="${path}/css/zh/user.css">
      <link rel="stylesheet" type="text/css" href="${path}/css/zh/juese.css">
      <link type="text/css" rel="stylesheet" href="${path}/css/regist.css"/>
      <link href="${path}/css/tooltip.css" rel="stylesheet" type="text/css" />
<%--       <link rel="shortcut icon" href="${path}/images/favicon1.ico" /> --%>
      <script type="text/javascript" src="${path}/js/commons/tooltip.js"></script>
       <style type="text/css">
		body{
		    color: #333333;
		    font-family: Arial,"宋体",Lucida,Verdana,Helvetica,sans-serif;
		    font-size: 12px;
		    line-height: 150%;
/*  		    background: none repeat scroll 0 0 #F2F2F2;  */
		    margin: 0;
		}
    </style>
     <script type="text/javascript" >
  
     $(function(){
    	var supplierId=${sr.supplierId};
     	var mess= $("#message").val();
     	
     	
     	if(mess.length>0){
     		alert(mess);
     	}

//      	回显省市区数据
				$.post(
	        			"${path}"+"/supplier/getProvinceList",
	        			   function(data){
	        				$.each(eval(data),function(i,n){
	        						$("#provinceId").append("<option value='"+n.provinceid+"'>"+n.provincename+"</option>");
	        				});
	        				$("#provinceId").val("${sr.provinceId}");
	        			   }
	        	);
				$.post(
	        			"${path}"+"/supplier/getCityList",
	        			   function(data){
	        				var city="<option value=''>市运营商</option>";
		        				$.each(eval(data),function(i,n){
		        					city+="<option value='"+n.cityid+"'>"+n.cityname+"</option>";
		        				});	
		        				$("#cityId").append(city);
		        				$("#cityId").val("${sr.cityId}");
	        			   }
	        	);
				$.post(
	        			"${path}"+"/supplier/getCountryList",
	        			   function(data){
	        				var country="<option value=''>区运营商</option>";
	        				$.each(eval(data),function(i,n){
	        					country+="<option value='"+n.countyid+"'>"+n.countyname+"</option>";
	        				});
							$("#areaId").append(country);
							$("#areaId").val("${sr.areaId}");					
				        },"json"
				        	); 
	     	 
    	
    	
     });
     
     </script>
	</head>
	<body>

       <%@include file="/WEB-INF/views/zh/include/header.jsp"%>	
       <div class="wrap">
				<%@include file="/WEB-INF/views/zh/include/leftProduct.jsp" %>
		       	<!-- 右边 start -->
				<div class="right f_l">
					<div class="title">
						<p class="c1">查看运营商 </p>
					<div class="clear"></div>
				</div>
				<div class="blank5"></div>
						<div class="w" id="regist">
    <div class="mc">

        <form id="formcompany"  method="post" action="${path}/supplier/agentRegist" enctype="multipart/form-data" >
           <input id="language" type="hidden" name="language" value="<spring:message code="language" />"  />
           <input type="hidden" name="status" value="0" />
           <input type="hidden" id="message" name="message" value="${message}" />
                    <div class="form " style="border:1px solid #e2e9ee;width:842px">
                        <h3><spring:message code="supplier" /><span class="tishi-<spring:message code="language" />"><spring:message code="supplier.tishi" /></span></h3>
                     	
                     	<input type="hidden" value="${parentId }"  name="parentId"/>
<!--                      注册名称 -->
                        <div class="item" id="select-regName">
                            <span class="label"><b class="ftx04">*</b>注册名称(企业名称)：</span>
                            <div class="f1 item-ifo">
                                <input type="text" id="companyname" name="name" onblur="checkName1();" class="text"  value="${sr.name }" disabled="disabled"
                                       autocomplete="off"  tabindex="1"    />
                            </div>
                        </div>
                        
                        <!--                      营业名称 -->
                        <div class="item" id="select-regName">
                            <span class="label"><b class="ftx04">*</b>营业名称(企业简称)：</span>
                            <div class="f1 item-ifo">
                                <input type="text" id="nameJC" name="nameJC" onblur="checkName1();" class="text"  value="${sr.nameJC }" disabled="disabled"
                                       autocomplete="off"  tabindex="1"    />
                            </div>
                        </div>
                        
                        <!--                         企业所在地 -->
							<div class="item" id="">
								<span class="label"><b class="ftx04">*</b>企业所在地：</span>
								<div class="item-ifo" > 
									<select id="provinceId" style="background-color:#EAEAE3"  disabled="disabled"><option value="" >请选择</option></select>
									<select id="cityId" style="background-color:#EAEAE3"  disabled="disabled"></select> 
									<select id="areaId" style="background-color:#EAEAE3" disabled="disabled"></select> 
								</div>
							</div><br/><br/><br/><br/>
                        
                        
                        <!--                         营业地址 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>营业地址：</span>
                            <div class="item-ifo">
                                <input type="text" id="companyaddr" name="address" class="text"  tabindex="5"   value="${sr.address }" disabled="disabled"/>
                            </div>
                        </div>
                        
                         <!--                         注册地址 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>注册地址：</span>
                            <div class="item-ifo">
                                <input type="text" id="registerAddress" name="registerAddress" class="text"  tabindex="5"   value="${sr.nameJC }" disabled="disabled"/>
                            </div>
                        </div>
                        
                        
<!--                         业务联系人 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>业务联系人：</span>
                            <div class="item-ifo">
                                <input type="text" id="contactname" name="contact" class="text"  tabindex="6"  value="${sr.contact }"  disabled="disabled"/>
                            </div>
                        </div>
                        
                        
<!--                               业务联系人电话 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>业务联系人电话：</span>
                            <div class="item-ifo">
                                <input type="text" id="contactTel" name="contactTel" class="text"   value="${sr.contactTel }" disabled="disabled" />
                            </div>
                        </div>
                        
                        
<!--                         业务联系人手机 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>业务联系人手机 ：</span>
                            <div class="item-ifo">
                           		 <input type="text" id="phoneP" name="phone" class="text"   tabindex="6"  value="${sr.phone }" disabled="disabled"/>
                            </div>
                        </div>
                        
<!-- 							邮箱 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="supplier.companyMail" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="mailbox" name="email" class="text"   tabindex="8"  value="${sr.email }" disabled="disabled"/>
                            </div>
                        </div>
                        
                        <!--                         企业法人名称 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>法人名称：</span>
                            <div class="item-ifo">
                                <input type="text" id="legalPerson" name="legalPerson" class="text"  tabindex="5"  value="${sr.legalPerson }" disabled="disabled" />
                            </div>
                        </div>
                        
                        
                        <!--                         企业法人电话 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>法人电话：</span>
                            <div class="item-ifo">
                                <input type="text" id="legalPersonPhone" name="legalPersonPhone" class="text"  value="${sr.legalPersonPhone }" disabled="disabled" />
                            </div>
                        </div>
                        
                        <!--                         证件类型 -->
							<div class="item" id="">
								<span class="label"><b class="ftx04">*</b>证件类型：</span>
								<div class="item-ifo" > 
									<select style="background-color:#EAEAE3" disabled="disabled" id="legalPersonCardType" name="legalPersonCardType"  ><option value="1" >身份证</option></select>
								</div>
							</div>
                        
 						    
 					 <!--                         企业法人证件号码 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>法人证件号码：</span>
                            <div class="item-ifo">
                                <input type="text" id="legalPersonCardNo" name="legalPersonCardNo" class="text"  tabindex="5"  value="${sr.legalPersonCardNo }" disabled="disabled" />
                            </div>
                        </div>
                        
                       
                          <!--                         法人身份证正面： -->
                        <div class="item" id="" >
                        <span class="label"><b class="ftx04">*</b>法人身份证正面：</span>
                        <div class="item-ifo" style="width:320px;">
                        			<c:if test="${!empty sr.idCardFront }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${sr.idCardFront }')">查看    </span>
                        			</c:if>
                        			<c:if test="${empty sr.idCardFront }">
                              		<span style="color:red;">未上传</span>
                        			</c:if>
                        </div>
                    </div>
                    
                    
                      <!--                          法人身份证反面： -->
                        <div class="item" id="" >
                        <span class="label"><b class="ftx04">*</b> 法人身份证反面：</span>
                        <div class="item-ifo" style="width:320px;">
                        	<c:if test="${!empty sr.idCardVerso }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${sr.idCardVerso }')">查看    </span>
                        	</c:if>
                        	<c:if test="${empty sr.idCardVerso }">
                              		<span style="color:red;">未上传</span>
                        	</c:if>
                        </div>
                    </div>
                    
 						    
                        <!--                         企业营业执照： -->
                        <div class="item" id="" >
                        <span class="label"><b class="ftx04">*</b>营业执照：</span>
                        <div class="item-ifo" style="width:320px;">
                           <c:if test="${!empty sr.businessLicense }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${sr.businessLicense }')">查看    </span>
                        	</c:if>
                        	<c:if test="${empty sr.businessLicense }">
                              		<span style="color:red;">未上传</span>
                        	</c:if>
                        </div>
                    </div>
                    
                    <!-- 							企业营业执照号码 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>营业执照号码：</span>
                            <div class="item-ifo">
                                <input type="text" id="businessLicenseNo" name="businessLicenseNo" class="text"   tabindex="8"  value="${sr.businessLicenseNo }" disabled="disabled"/>
                                <label id="businessLicenseNo_succeed" class="blank"></label>
                                <label id="businessLicenseNo_error"></label>
                               
                            </div>
                        </div>
                      
                          <!--                         经营门头照片： -->
                        <div class="item" id="" >
                        <span class="label">经营门头照片：</span>
                        <div class="item-ifo" style="width:320px;">
                        	 <c:if test="${!empty sr.companyStoreLogo }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${sr.companyStoreLogo }')">查看    </span>
                        	</c:if>
                        	<c:if test="${empty sr.companyStoreLogo }">
                              		<span style="color:red;">未上传</span>
                        	</c:if>
                        </div>
                    </div>
                        
                        
                        
<!--                         上传能够证明企业合法性的证明文件： -->
                        <div class="item" id="" >
                        <span class="label"><b class="ftx04"></b><spring:message code="supplier.companyFile" />：</span>
                        <div class="item-ifo" style="width:320px;">
                        	<c:if test="${!empty sr.companyLegitimacyUrl }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${sr.companyLegitimacyUrl }')">下载    </span>
                        	</c:if>
                        	<c:if test="${empty sr.companyLegitimacyUrl }">
                              		<span style="color:red;">未上传</span>
                        	</c:if>
                        </div>
                    </div>
                    
                    
                    
<!-- 					公司详情文件： -->
					<div class="item" id="" >
                            <span class="label"><b class="ftx04"></b><spring:message code="supplier.companyDetailFile" />： </span>
                            <div class="item-ifo" style="width:320px;">
                              <c:if test="${!empty sr.companyDetailedUrl }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${sr.companyDetailedUrl }')">下载    </span>
                        	</c:if>
                        	<c:if test="${empty sr.companyDetailedUrl }">
                              		<span style="color:red;">未上传</span>
                        	</c:if>
                            </div>
                        </div>
                        
                        
                        <!--  						   企业信息介绍 -->
						 <div class="item" id="">
                            <span class="label"><b class="ftx04"></b><spring:message code="supplier" />：</span>
                            <div class="item-ifo">
                                <textarea style="outline:none;resize:none;" rows="6" cols="35"  name="companyInfo"      disabled="disabled" >${sr.companyInfo }</textarea>
                            </div>
                        </div></br></br></br>
                        
                        	<!-- 结算账户信息  -->
                        <h3 style="width:810px">结算账户信息</h3>
                       
                        
                <!--                         开户行名称 -->
							<div class="item" id="">
								<span class="label"><b class="ftx04">*</b>开户行名称 ：</span>
								<div class="item-ifo" > 
									<select id="bank" style="background-color:#EAEAE3" disabled="disabled"><option>${bankBranch.bankName }</option></select>
									<select id="bankProvinceId" style="background-color:#EAEAE3" disabled="disabled"><option>${bankBranch.provinceName }</option></select> 
									<select id="bankAreaId" style="background-color:#EAEAE3" disabled="disabled"><option>${bankBranch.cityName }</option></select> 
									<select id="accoutBankno" style="background-color:#EAEAE3" disabled="disabled"><option>${bankBranch.bankBranchName }</option></select> 
								</div>
							</div><br/><br/><br/><br/><br/>
                        
<!--    						结算账户名称 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>结算账户名称：</span>
                            <div class="item-ifo">
                                <input type="text" id="accountName" name="accountName" class="text" tabindex="6" value="${sr.accountName }" disabled="disabled" />
                                <label id="accountName_succeed" class="blank"></label>
                                <label id="accountName_error"></label>                     
                            </div>
                        </div>
                        
<!--                         结算账号 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>结算账号：</span>
                            <div class="item-ifo">
                                <input type="text" id="accountNo" name="accountNo"  class="text" tabindex="7" value="${sr.accountNo }" disabled="disabled" />
                            </div>
                        </div>
                        

   						<!-- 企业个人账号  -->
<!--    						姓名 -->
                        <h3 style="width:810px"><spring:message code="company.account" /></h3>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="company.userName" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="userName" name="userName" class="text"   tabindex="6"  value="${sr.userName }" disabled="disabled"  />
                            </div>
                        </div>
<!--                         手机号 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="company.mobile" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="userMobile" name="userMobile"  class="text"  tabindex="7"  value="${sr.userMobile }" disabled="disabled"/>
                            </div>
                        </div>


<!-- 					企业账户信息 -->
						<!-- 用户名 -->
                        <h3 style="width:810px">商家登录信息</h3>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="user.loginName" />：</span>
                            <div class="item-ifo">
                                <input type="text" class="text"  value="${supplierUser.loginName }" disabled="disabled"  />
                            </div>
                        </div>  


                            
                           	 <div class="item">
                            <span class="label">&nbsp;</span>
                             <input type="button" style="background-color: red; color: white;width: 227px;height: 35px;cursor: pointer; font-size:20px;border-radius:5px;border:1px  solid red" onclick="window.parent.location='${path}/supplier/getAgentPage'" value="返回页面"> 
                        </div>
                     
                        
                    </div>
                          

            
        </form>
    </div>
</div>
				</div>
	  </div>
	  
    <script type="text/javascript" src="${path}/js/user/validateRegExp.js"></script>
    <script type="text/javascript" src="${path}/js/user/validateprompt.js"></script>
    <script  type="text/javascript" src="${path}/js/user/drag.js"></script> 
   
   
	<script type="text/javascript">
	$(function(){  //生成验证码         
    $("#kaptchaImage").click(function () {  
    $(this).hide().attr("src", "${path}/supplier/validateCode?uid=${uid}&"+Math.random()*100 ).fadeIn(); });      
});   
function changeCode() {  //刷新
    $("#kaptchaImage").hide().attr("src", "${path}/supplier/validateCode?uid=${uid}&"+Math.random()*100).fadeIn();  
    event.cancelBubble=true;  
};
	</script> 
	
	
	<script type="text/javascript" src="${path}/js/user/industry_func.js"></script>
	<!-- 底部 start -->
	   	<%@include file="/WEB-INF/views/zh/include/last.jsp"%>
	<!-- 底部 end -->

   	</body>	
  
</html>