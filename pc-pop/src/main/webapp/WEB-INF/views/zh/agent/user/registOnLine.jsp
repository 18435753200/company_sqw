<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><spring:message code="registTitle" /></title>
    <%@include file="/WEB-INF/views/zh/include/base.jsp"%>
    <link rel="shortcut icon" href="${path}/images/favicon1.ico" />
    <link type="text/css" rel="stylesheet" href="${path}/css/zh/agent/register.css"/>
    <link type="text/css" rel="stylesheet" href="${path}/css/regist.css"/>
   	<link rel="stylesheet" type="text/css" href="${path}/css/zh/user.css">
    <link href="${path}/css/tooltip.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
		body{
		    color: #333333;
		    font-family: Arial,"宋体",Lucida,Verdana,Helvetica,sans-serif;
		    font-size: 12px;
		    line-height: 150%;
		    background: none repeat scroll 0 0 #F2F2F2;
		    margin: 0;
		}
    </style>
     <script type="text/javascript" >
    $(function(){
     	var mess= $("#message").val();
     	if(mess.length>0){
     		alert(mess);
     	}
     })
     
     </script>
  
</head>
<body>


    <div id="shortcut-2013">
   		 <div class="w">
                <ul class="fr lh">
                    <li id="loginbar" class="fore1" clstag="homepage|keycount|home2013|01b">
                    	您好,欢迎来到众聚猫商城
                    <span>
                    <a href="${path}/user/loginUI"> [<spring:message code="login" />] </a>
                    <a class="link-regist" href="${path}/supplier/registOnLine">[<spring:message code="regist" />]</a>
                    </span>
                    </li>
                </ul>
         </div>
    </div>
    
    
    
	<div class="w">
	    <div id="logo">
	    	<a href="${path}/user/loginUI" clstag="passport|keycount|login|01">
	    <img src="${path}/images/login-logo-sm.png" alt="<spring:message code="logo" />" width="170"  height="60"/>
	        </a>
	     <b style="background: url('${path}/images/regist-wel-<spring:message code="language" />.png') no-repeat scroll 0 0  !important;"></b></div>
	</div>
	


<div class="w" id="regist">

    <div class="mt">
        <div class="extra">
        <span style="text-align: right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> <span> <spring:message code="registMessage" />&nbsp;<a
                href="${path}/user/loginUI"
                class="flk13"><spring:message code="login" /></a></span>
        </div>
    </div>


    <div class="mc">
        <form id="formcompany"  method="post" action="${path}/supplier/registOnLineSupplier" enctype="multipart/form-data" >
           <input id="language" type="hidden" name="language" value="<spring:message code="language" />"  />
           <input type="hidden" name="status" value="0" />
           <input type="hidden" id="message" name="message" value="${message}" />
            <input type="hidden"  id="pa" value="${path }">
                    <div class="form ">
                    
                        <h3><spring:message code="supplier" /><span class="tishi-<spring:message code="language" />"><spring:message code="supplier.tishi" /></span></h3>
<!--                      注册名称 -->
                        <div class="item" id="select-regName">
                            <span class="label"><b class="ftx04">*</b>注册名称(企业名称)：</span>
                            <div class="f1 item-ifo">
                                <input type="text" id="companyname" name="name" onblur="checkName1();" class="text" 
                                       autocomplete="off"    />
                                <label id="company_error" class="error" style="display: none"> <spring:message code="user.comNameRegisted" /></label>
                                <label id="companyname_succeed" class="blank"></label>
                                <label id="companyname_error"></label>
                            </div>
                        </div>
                        
                        <!--                      营业名称 -->
                        <div class="item" id="select-regName">
                            <span class="label"><b class="ftx04">*</b>营业名称(企业简称)：</span>
                            <div class="f1 item-ifo">
                                <input type="text" id="nameJC" name="nameJC" onblur="checkName1();" class="text"  
                                       autocomplete="off"  />
                                <label id="nameJC_succeed" class="blank"></label>
                                <label id="nameJC_error"  ></label>
                            </div>
                        </div>
                        
                        <!--                         企业所在地 -->
							<div class="item" id="">
								<span class="label"><b class="ftx04">*</b>企业所在地：</span>
								<div class="item-ifo" > 
									<select id="provinceId" name="provinceId"  ><option value="" >请选择</option></select>
									<select id="cityId" name="cityId"  ></select> 
									<select id="areaId" name="areaId"  ></select> 
									 <label class="blank" id="areaId_succeed"></label>
                                	 <label id="areaId_error"></label>
								</div>
							</div><br/><br/><br/><br/>
                        
                        
                        <!--                         营业地址 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>营业地址：</span>
                            <div class="item-ifo">
                                <input type="text" id="companyaddr" name="address" class="text"   />
                                <label class="blank" id="companyaddr_succeed"></label>
                                <label id="companyaddr_error"></label>
                         
                            </div>
                        </div>
                        
                         <!--                         注册地址 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>注册地址：</span>
                            <div class="item-ifo">
                                <input type="text" id="registerAddress" name="registerAddress" class="text"   />
                                <label class="blank" id="registerAddress_succeed"></label>
                                <label id="registerAddress_error"></label>
                         
                            </div>
                        </div>
                        
                        
<!--                         业务联系人 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>业务联系人：</span>
                            <div class="item-ifo">
                                <input type="text" id="contactname" name="contact" class="text"   />
                                <label id="contactname_succeed" class="blank"></label>
                                <label id="contactname_error"></label>                     
                            </div>
                        </div>
                        
                        
                        
<!--                         业务联系人手机 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>业务联系人手机 ：</span>
                            <div class="item-ifo">
                           		 <input type="text" id="phoneP" name="phone" class="text"    />
                                <label id="phoneP_succeed" class="blank"></label>
                                <label id="phoneP_error"></label>
                               
                            </div>
                        </div>
                        
                        
 <!--                               业务联系人电话 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>业务联系人电话：</span>
                            <div class="item-ifo">
                                <input type="text" id="contactTelP" name="contactTel" class="text"    />
                                <label id="contactTelP_succeed" class="blank"></label>
                                <label id="contactTelP_error"></label>                     
                            </div>
                        </div>
                        
<!-- 							邮箱 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="supplier.companyMail" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="mailbox" name="email" class="text"    />
                                <label id="mailbox_succeed" class="blank"></label>
                                <label id="mailbox_error"></label>
                               
                            </div>
                        </div>
                        
                        <!--                         企业法人名称 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>法人名称：</span>
                            <div class="item-ifo">
                                <input type="text" id="legalPerson" name="legalPerson" class="text"    />
                                
                                <label class="blank" id="legalPerson_succeed"></label>
                                <label id="legalPerson_error"></label>
                         
                            </div>
                        </div>
                        
                        
                        <!--                         企业法人电话 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>法人电话：</span>
                            <div class="item-ifo">
                                <input type="text" id="legalPhone" name="legalPersonPhone" class="text"  />
                                <label class="blank" id="legalPersonPhone_succeed"></label>
                                <label id="legalPhone_error"></label>
                         
                            </div>
                        </div>
                        
                        <!--                         证件类型 -->
							<div class="item" id="">
								<span class="label"><b class="ftx04">*</b>证件类型：</span>
								<div class="item-ifo" > 
									<select id="legalPersonCardType" name="legalPersonCardType"  ><option value="1" >身份证</option></select>
								</div>
							</div>
                        
 						    
 					 <!--                         企业法人证件号码 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>法人证件号码：</span>
                            <div class="item-ifo">
                                <input type="text" id="legalPersonCardNo" name="legalPersonCardNo" class="text"  />
                                <label class="blank" id="legalPersonCardNo_succeed"></label>
                                <label id="legalPersonCardNo_error"></label>
                            </div>
                        </div>
                        
                       
                          <!--                         法人身份证正面： -->
                        <div class="item" id="" >
                        <span class="label"><b class="ftx04">*</b>法人身份证正面：</span>
                        <div class="item-ifo" style="width:320px;">
                            <input id="idCardFrontTextField" class="text" type="text" name="idCardFrontTextField" readonly="readonly" >
                                <span id="filespan">
                                <label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField1" class="file" type="file" onchange="document.getElementById('idCardFrontTextField').value=this.value;uploadPicture(this,'idCardFrontTextField')"  size="28" name="idCardFrontTo" />
                                    <input class="btn" type="button"  value="<spring:message code="upload" />"  >
                                </label>
                               </span>
                            <label id="idCardFrontTextField_succeed" class="blank"></label>
                            <label id="idCardFrontTextField_error"></label></br>
                             <span style="color:#c7c7c7; line-height:24px;">上传图片格式为jpg、png、jepg、图片大小小于300k</span>    
                        </div>
                    </div>
                    
                    
                      <!--                          法人身份证反面： -->
                        <div class="item" id="" >
                        <span class="label"><b class="ftx04">*</b> 法人身份证反面：</span>
                        <div class="item-ifo" style="width:320px;">
                            <input id="idCardVersoTextField" class="text" type="text" name="idCardVersoTextField" readonly="readonly" >
                                <span id="filespan">
                                <label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField2" class="file" type="file" onchange="document.getElementById('idCardVersoTextField').value=this.value;uploadPicture(this,'idCardVersoTextField')"    size="28" name="idCardVersoTo" 
                                      />
                                    <input class="btn" type="button" value="<spring:message code="upload" />"    >
                                </label>
                               </span>
                            <label id="idCardVersoTextField_succeed" class="blank"></label>
                            <label id="idCardVersoTextField_error"></label></br>
                             <span style="color:#c7c7c7; line-height:24px;">上传图片格式为jpg、png、jepg、图片大小小于300k</span>    
                        </div>
                    </div>
                    
 						    
                        
                        <!--                         企业营业执照： -->
                        <div class="item" id="" >
                        <span class="label"><b class="ftx04">*</b>营业执照：</span>
                        <div class="item-ifo" style="width:320px;">
                            <input id="businessLicenseTextField" class="text" type="text" name="businessLicenseTextField" readonly="readonly" >
                                <span id="filespan">
                                <label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField3" class="file" type="file" onchange="document.getElementById('businessLicenseTextField').value=this.value;uploadPicture(this,'businessLicenseTextField')"    size="28" name="businessLicenseTo" 
                                      />
                                    <input class="btn" type="button" value="<spring:message code="upload" />"    >
                                </label>
                               </span>
                            <label id="businessLicenseTextField_succeed" class="blank"></label>
                            <label id="businessLicenseTextField_error"></label></br>
                             <span style="color:#c7c7c7; line-height:24px;">上传图片格式为jpg、png、jepg、图片大小小于300k</span>    
                        </div>
                    </div>
                    
                    <!-- 							企业营业执照号码 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>营业执照号码：</span>
                            <div class="item-ifo">
                                <input type="text" id="businessLicenseNo" name="businessLicenseNo" class="text"     />
                                <label id="businessLicenseNo_succeed" class="blank"></label>
                                <label id="businessLicenseNo_error"></label>
                               
                            </div>
                        </div>
                        
                          <!--                         经营门头照片： -->
                        <div class="item" id="" >
                        <span class="label">经营门头照片：</span>
                        <div class="item-ifo" style="width:320px;">
                            <input id="companyStoreLogoTextField" class="text" type="text" name="companyStoreLogoTextField" readonly="readonly" >
                                <span id="filespan">
                                <label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField4" class="file" type="file" onchange="document.getElementById('companyStoreLogoTextField').value=this.value;uploadPicture(this,'companyStoreLogoTextField')"     size="28" name="companyStoreLogoTo" 
                                      />
                                    <input class="btn" type="button" value="<spring:message code="upload" />"    >
                                </label>
                               </span>
                            <label id="companyStoreLogoTextField_succeed" class="blank"></label>
                            <label id="companyStoreLogoTextField_error"></label></br>
                             <span style="color:#c7c7c7; line-height:24px;">上传图片格式为jpg、png、jepg、图片大小小于300k</span>    
                        </div>
                    </div>
                        
                        
                        
<!--                         上传能够证明企业合法性的证明文件： -->
                        <div class="item" id="" >
                        <span class="label"><b class="ftx04"></b><spring:message code="supplier.companyFile" />：</span>
                        <div class="item-ifo" style="width:320px;">
                            <input id="companyLegitimacyUrlTextField" class="text" type="text" name="companyLegitimacyUrlTextField" readonly="readonly" >
                                <span id="filespan">
                                <label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField5" class="file" type="file" onchange="document.getElementById('companyLegitimacyUrlTextField').value=this.value;validateFileSize(this,'companyLegitimacyUrlTextField','300')"     size="28" name="companyLegitimacy" />
                                    <input class="btn" type="button" value="<spring:message code="upload" />"    >
                                </label>
                               </span>
                            <label id="companyLegitimacyUrlTextField_succeed" class="blank"></label>
                            <label id="companyLegitimacyUrlTextField_error"></label></br>
                            <span style="color:#c7c7c7; line-height:24px;">文件大小小于300k</span> 
                        </div>
                    </div>
                    
                    
                    
<!-- 					公司详情文件： -->
					<div class="item" id="" >
                            <span class="label"><b class="ftx04"></b><spring:message code="supplier.companyDetailFile" />：(<a href="${path}/modelFile/supplierInfo_20161103.docx"><span style="color:blue"><spring:message code="supplier.companyDetailFileTemplate" /></span></a> ) </span>
                            <div class="item-ifo" style="width:320px;">
                                <input id="companyDetailedUrlTextField" class="text" type="text" name="companyDetailedUrlTextField" readonly="readonly"  >
                                <span id="filespan">
                                <label for="fileField1">
                                <input id="fileField16" class="file" type="file" onchange="document.getElementById('companyDetailedUrlTextField').value=this.value;uploadFile(this,'companyDetailedUrlTextField')" size="28" name="companyDetailed" />
                                <input class="btn" type="button" value="<spring:message code="upload" />"   >
                                </label>
                               </span>
                                <label id="companyDetailedUrlTextField_succeed" class="blank"></label>
                                <label id="companyDetailedUrlTextField_error"></label><br>
                                 <span style="color:#c7c7c7; line-height:24px;"> 详情文件限于 doc,docx、格式文件大小小于300k</span>   
                            </div>
                        </div>
                      
                        
                        <!--  						   企业信息介绍 -->
						 <div class="item" id="">
                            <span class="label"><b class="ftx04"></b><spring:message code="supplier" />：</span>
                            <div class="item-ifo">
                                <textarea style="outline:none;resize:none;" rows="6" cols="35"  name="companyInfo"     ></textarea>
                                <label class="blank" id="companyInfo_succeed"></label>
                                <label id="companyInfo_error"></label>
                         
                            </div>
                        </div></br></br></br>
                        
                        	<!-- 结算账户信息  -->
                        <h3 style="width:810px">结算账户信息</h3>
                       
                        
                <!--                         开户行名称 -->
							<div class="item" id="">
								<span class="label"><b class="ftx04">*</b>开户行名称 ：</span>
								<div class="item-ifo" > 
									<select id="bank" name="bank"  ><option value="" >请选择</option></select>
									<select id="bankProvinceId" name="bankProvinceId"  ></select> 
									<select id="bankAreaId" name="bankAreaId"  ></select> 
									<select id="accoutBankno" name="accoutBankno" ></select> 
									 <label class="blank" id="accoutBankno_succeed"></label>
                                	 <label id="accoutBankno_error"></label>
								</div>
							</div><br/><br/><br/><br/><br/>
                        
<!--    						结算账户名称 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>结算账户名称：</span>
                            <div class="item-ifo">
                                <input type="text" id="accountName" name="accountName" class="text"    />
                                <label id="accountName_succeed" class="blank"></label>
                                <l
                                abel id="accountName_error"></label>                     
                            </div>
                        </div>
                        
<!--                         结算账号 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>结算账号：</span>
                            <div class="item-ifo">
                                <input type="text" id="accountNo" name="accountNo"  class="text"  />
                                <label id="accountNo_succeed" class="blank"></label>
                                <label id="accountNo_error"></label>
                            </div>
                        </div>
                        
                        
<!--                         商品信息 -->
                        <h3>商品信息</h3>
<!--                         商品类别 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>商品类别：</span>
                            <div class="f1 item-ifo">
                          <input id="mer-cgr"  name="categories1"  class="text"  rel="select" onclick="IndustrySelect()" readonly="readonly"  >
                          <input id="mercgr" type="hidden" name="categories" value="">
						  <input id="IndustryID" type="hidden" name="IndustryID" value="">
						  <label id="mer-cgr_succeed" class="blank"></label>
						  <label id="mer-cgr_error"></label>
                            </div>
                        </div>
                        
<!--                         商品品牌 -->
                        <div class="item" id="">
                            <span class="label">商品品牌：</span>
                            <div class="f1 item-ifo">
                                <input type="text" id="mer-brand" name="brand" class="text" autocomplete="off"  value=""/>
                                <label id="mer-brand_succeed" class="blank"></label>
                                <label id="mer-brand_error"></label>
                            </div>
                           
                            </div>
                     
                        <!-- 企业个人账号 start -->
                        <h3>企业个人账号</h3>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>姓名：</span>
                            <div class="item-ifo">
                                <input type="text" id="userName" name="userName" class="text"  />
                                <label id="userName_succeed" class="blank"></label>
                                <label id="userName_error"></label>                     
                            </div>
                        </div>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="company.mobile" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="userMobile" name="userMobile" class="text" />
                                <label id="userMobile_succeed" class="blank"></label>
                                <label id="userMobile_error"></label>
                            </div>
                        </div>
                        <div class="" id="" style="margin:0px 0px 10px 250px;">
                           <span id="userMobile_info" style="color: red;">请注意：手机号即为登录名、初始密码与商家后台密码一致，请注册成功后登录修改!</span>
                      	</div>
                     
                        <h3><spring:message code="user" /></h3>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="user.loginName" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="regName" name="loginName" class="text"  autocomplete="off" onblur="checkName();"   />
                                
                                <i class="i-name"></i>
                                <label id="realname_error" class="error" style="display: none"> <spring:message code="user.loginNameRegisted" /></label>
                                <!-- <ul id="intelligent-regName" class="hide"></ul> -->
                                <label id="regName_succeed" class="blank"></label>
                                <label id="regName_error"></label>
                            </div>
                        </div>  
                        <div class="item" id="o-password">
                            <div id="capslock"><i></i><s></s><spring:message code="user.keyboardLock" /></div>
                            <span class="label"><b class="ftx04">*</b><spring:message code="user.password" />：</span>
                            <div class="item-ifo">
                                <input type="password" id="pwd" name="password"  class="text" autocomplete="off" onpaste="return  false"  />
                                <!-- <label id="loginpwd_error" class="hide" >请输入您的密码</label>
                                <label id="loginpwd_error1" class="hide" >密码字符长度不少于六位</label> -->
                                <i class="i-pass"></i>
                                <label id="pwd_succeed" class="blank"></label>
                                <label id="pwd_error" class="hide"></label>
                                <span class="clr"></span>
                                </label>
                            </div>
                        </div>
                            <script type="text/javascript">
                                $('#pwd')[0].onkeypress = function(event){
                                    var e = event||window.event,
                                        $tip = $('#capslock'),
                                            kc  =  e.keyCode||e.which, // 按键的keyCode
                                            isShift  =  e.shiftKey ||(kc  ==   16 ) || false ; // shift键是否按住
                                    if (((kc >=65&&kc<=90)&&!isShift)|| ((kc >=97&&kc<=122)&&isShift)){
                                            $tip.show();
                                    }
                                    else{
                                            $tip.hide();
                                    }
                                };
                            </script>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="user.pwdRepeat" />：</span>
                            <div class="item-ifo">
                                <input type="password" id="pwdRepeat" name="pwdRepeat" class="text" autocomplete="off" />
                                <i class="i-pass"></i>
                                <label id="pwdRepeat_succeed" class="blank"></label>
                                <label id="pwdRepeat_error"></label>
                            </div>
                        </div>
                      
                        
                       
                         
                           <div class="item form-group">
						   <span class="label"><b class="ftx04">*</b><spring:message code="login.randomCode" />：</span> 
						   <div class="item-ifo">
							   <input type="text" id="kaptcha" name="kaptcha" maxlength="4" class="text form-control"  />
							   <input type="hidden" name="uid" id="uid"  value="${uid}" />
							   <i class="i-pass"></i>
	                            <label id="kaptcha_succeed" class="blank"></label>
	                           <label id="kaptcha_error" class="hide"></label>
                           </div>
						   <div class="yzm-bar"> 
						   <img src="${path}/supplier/validateCode?uid=${uid}" id="kaptchaImage" />       
						   <a href="javascript:;" onclick="changeCode()"><spring:message code="login.codenewone"  /></a>
					   </div>  
		    		</div> 
                      
                        <div class="item">
                            <span class="label">&nbsp;</span>
                            <input type="button" class="btn-img btn-regist-<spring:message code="language" />" id="registsubmit"   >
                        </div>
                    </div>
               <!--  </div>
            </div> -->
            <div id="maskLayer" style="display: none;">
				<div id="alphadiv" style="filter:alpha(opacity=50);opacity:.5"></div>
				<div id="drag">
					<h4 id="drag_h"></h4>
					<div id="drag_con">
						<div id="jobAreaAlpha"> </div>
					</div>
				</div>
			</div>            
            
        </form>
    </div>
</div>


  
  
	
   
   
	<script type="text/javascript">
	 var ind_a=${category};
	/*  var anArray=;
	 //var  anArray= ['one','two','three'];  
     $.each(anArray,function(n,value) {  
        alert(n+' '+value);  
        ind_a.push(value);
	});  
 */
	/*  for (var i = 0; i < array.length; i++) {
		 ind_a[array[i]]=array[i];
	} */
	// 是否在数组内ind_a
		function in_array(needle, haystack) {
			if(typeof needle == 'string' || typeof needle == 'number') {
				for(var i in haystack) {
					if(haystack[i] == needle) {
							return true;
					}
				}
			}
			return false;
		}
	
	
	
	$(function(){  //生成验证码         
    $("#kaptchaImage").click(function () {  
    $(this).hide().attr("src", "${path}/supplier/validateCode?uid=${uid}&"+Math.random()*100 ).fadeIn(); });      
});   
function changeCode() {  //刷新
    $("#kaptchaImage").hide().attr("src", "${path}/supplier/validateCode?uid=${uid}&"+Math.random()*100).fadeIn();  
    event.cancelBubble=true;  
};
	</script> 
	
	
	
	 <script type="text/javascript" src="${path}/js/agent/onLineSupplier/onLine.js"></script>
    <script type="text/javascript" src="${path}/js/commons/tooltip.js"></script>
    <script type="text/javascript" src="${path}/js/agent/user/validateOnLineRegExp.js"></script>
    <script type="text/javascript" src="${path}/js/agent/user/validateOnLineprompt.js"></script>
    <script type="text/javascript" src="${path}/js/agent/user/registOnLineValidate.js"></script>
    <script  type="text/javascript" src="${path}/js/user/drag.js"></script>
    <script type="text/javascript" src="${path}/js/user/industry_func.js"></script>
	
		 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/zh/include/foot.jsp"%>
</body>
</html>


