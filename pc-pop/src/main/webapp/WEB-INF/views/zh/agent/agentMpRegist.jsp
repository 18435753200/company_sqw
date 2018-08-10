<%@ page language="java"  import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	  <meta charset="utf-8">
	 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	  <title>商家后台管理系统-添加合伙人</title>
      <%@include file="/WEB-INF/views/zh/include/base.jsp"%>
	  <link rel="stylesheet" type="text/css" href="${path}/css/zh/user.css">
      <link rel="stylesheet" type="text/css" href="${path}/css/zh/juese.css">
      <link type="text/css" rel="stylesheet" href="${path}/css/regist.css"/>
      <link type="text/css" rel="stylesheet" href="${path}/css/zh/agent/register.css"/>
      <link href="${path}/css/tooltip.css" rel="stylesheet" type="text/css" />
      <script type="text/javascript" src="${path}/js/commons/tooltip.js"></script>
      <script type="text/javascript" src="${path}/js/agent/registAgent.js"></script>
       <style type="text/css">
		body{
		    color: #333333;
		    font-family: Arial,"宋体",Lucida,Verdana,Helvetica,sans-serif;
		    font-size: 12px;
		    line-height: 150%;
		    margin: 0;
		}
    </style>
	</head>
	<body>

       <%@include file="/WEB-INF/views/zh/include/header.jsp"%>	
       <div class="wrap">
					<%@include file="/WEB-INF/views/zh/include/leftProduct.jsp" %>
			       	<!-- 右边 start -->
		<div class="right f_l">
					<div class="title">
							<p class="c1">添加合伙人</p>
<!-- 							<div class="clear"> -->
<!-- 							</div> -->
					</div>
					<div class="blank5"></div>
		
	
	<div class="w" id="regist">
    			<div class="mc">

        <form id="formcompany"  method="post" action="${path}/supplier/addMpAgentModel" enctype="multipart/form-data" >
           <input id="language" type="hidden" name="language" value="<spring:message code="language" />"  />
           <input type="hidden" name="status" value="0" />
           <input type="hidden" id="message" name="message" value="${message}" />
           <input type="hidden" name="agentCode" value="${supplierAgentType.code }" />
           <input type="hidden"  id="pa" value="${path }">
           <input type="hidden"   name="accoutBankname" id="accoutBankname"/>
                    <div class="form" style="border:1px solid #e2e9ee;width:842px">
                        <h3 style="width:810px">基本信息<span class="tishi-<spring:message code="language" />"><spring:message code="supplier.tishi" /></span></h3>
                     	
                     	<input type="hidden" value="${parentId }"  name="parentId"/>
                     	
                     	  <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="company.userName" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="userName" name="userName" class="text"     />
                                <label id="userName_succeed" class="blank"></label>
                                <label id="userName_error"></label>                     
                            </div>
                        </div>
<!--                         手机号 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="company.mobile" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="userMobile" name="userMobile"  class="text"   />
                                <label id="userMobile_succeed" class="blank"></label>
                                <label id="userMobile_error"></label>
                            </div>
                        </div>
                        <div class="" id="" style="margin:0px 0px 10px 250px;">
                           <span id="userMobile_info" style="color: red;">请注意：手机号即为个人账户名、初始密码与商家系统账户密码一致，请注册成功后登录修改!</span>
                      	</div>
                     	
                        
                        <!--                         企业所在地 -->
							<div class="item" id="">
								<span class="label"><b class="ftx04">*</b>所在地：</span>
								<div class="item-ifo" > 
									<select id="provinceId" name="provinceId"  ><option value="" >请选择</option></select>
									<select id="cityId" name="cityId"  ></select> 
									<select id="areaId" name="areaId"  ></select> 
									 <label class="blank" id="areaId_succeed"></label>
                                	 <label id="areaId_error"></label>
								</div>
							</div><br/><br/><br/><br/>
                        
                        
                        <!--                         地址 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>地址：</span>
                            <div class="item-ifo">
                                <input type="text" id="registerAddress" name="registerAddress" class="text"     />
                                <label class="blank" id="registerAddress_succeed"></label>
                                <label id="registerAddress_error"></label>
                         
                            </div>
                        </div>
                        
                      
<!-- 							邮箱 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="supplier.companyMail" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="mailbox" name="email" class="text"     />
                                <label id="mailbox_succeed" class="blank"></label>
                                <label id="mailbox_error"></label>
                               
                            </div>
                        </div>
                        
                      
                        <!--                         证件类型 -->
							<div class="item" id="">
								<span class="label"><b class="ftx04">*</b>证件类型：</span>
								<div class="item-ifo" > 
									<select id="legalPersonCardType" name="legalPersonCardType"  ><option value="1" >身份证</option></select>
								</div>
							</div>
                        
 						    
 					 <!--                         法人证件号码 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>证件号码：</span>
                            <div class="item-ifo">
                                <input type="text" id="legalPersonCardNo" name="legalPersonCardNo" class="text"     />
                                <label class="blank" id="legalPersonCardNo_succeed"></label>
                                <label id="legalPersonCardNo_error"></label>
                            </div>
                        </div>
                        
                       
                          <!--                         身份证正面： -->
                        <div class="item" id="" >
                        <span class="label"><b class="ftx04">*</b>身份证正面：</span>
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
                    
                    
                      <!--                          身份证反面： -->
                        <div class="item" id="" >
                        <span class="label"><b class="ftx04">*</b>身份证反面：</span>
                        <div class="item-ifo" style="width:320px;">
                            <input id="idCardVersoTextField" class="text" type="text" name="idCardVersoTextField" readonly="readonly" >
                                <span id="filespan">
                                <label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField2" class="file" type="file" onchange="document.getElementById('idCardVersoTextField').value=this.value;uploadPicture(this,'idCardVersoTextField')"    size="28" name="idCardVersoTo" 
                                      />
                                    <input class="btn" type="button" value="<spring:message code="upload" />"   >
                                </label>
                               </span>
                            <label id="idCardVersoTextField_succeed" class="blank"></label>
                            <label id="idCardVersoTextField_error"></label></br>
                             <span style="color:#c7c7c7; line-height:24px;">上传图片格式为jpg、png、jepg、图片大小小于300k</span>    
                        </div>
                    </div>
                    
 						    
 						       <!--                         工作场景照片： -->
                        <div class="item" id="" >
                        <span class="label"><b class="ftx04">*</b>工作场景照片：</span>
                        <div class="item-ifo" style="width:320px;">
                            <input id="companyStoreLogoTextField" class="text" type="text" name="companyStoreLogoTextField" readonly="readonly" >
                                <span id="filespan">
                                <label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField3" class="file" type="file" onchange="document.getElementById('companyStoreLogoTextField').value=this.value;uploadPicture(this,'companyStoreLogoTextField')"     size="28" name="companyStoreLogoTo" 
                                      />
                                    <input class="btn" type="button" value="<spring:message code="upload" />"    >
                                </label>
                               </span>
                            <label id="companyStoreLogoTextField_succeed" class="blank"></label>
                            <label id="companyStoreLogoTextField_error"></label></br>
                             <span style="color:#c7c7c7; line-height:24px;">上传图片格式为jpg、png、jepg、图片大小小于300k</span>    
                        </div>
                    </div><br>
                     
                        
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
                                <input type="text" id="accountName" name="accountName" class="text"     />
                                <label id="accountName_succeed" class="blank"></label>
                                <l
                                abel id="accountName_error"></label>                     
                            </div>
                        </div>
                        
<!--                         结算账号 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>结算账号：</span>
                            <div class="item-ifo">
                                <input type="text" id="accountNo" name="accountNo"  class="text"   />
                                <label id="accountNo_succeed" class="blank"></label>
                                <label id="accountNo_error"></label>
                            </div>
                        </div>
                        

<!-- 					企业账户信息 -->
						<!-- 用户名 -->
                        <h3 style="width:810px">商家系统账号</h3>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="user.loginName" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="regName" name="loginName"    class="text"   autocomplete="off" onblur="checkName();"   />
                                <i class="i-name"></i>
                                <label id="realname_error" class="error" style="display: none"> <spring:message code="user.loginNameRegisted" /></label>
                                <!-- <ul id="intelligent-regName" class="hide"></ul> -->
                                <label id="regName_succeed" class="blank"></label>
                                <label id="regName_error"></label>
                            </div>
                        </div>  
                        		<!--                         密码 -->
                        <div class="item" id="o-password">
                            <div id="capslock"><i></i><s></s><spring:message code="user.keyboardLock" /></div>
                            <span class="label"><b class="ftx04">*</b><spring:message code="user.password" />：</span>
                            <div class="item-ifo">
                                <input type="password" id="pwd" name="password" value="${supplierUser.password }" class="text" autocomplete="off" onpaste="return  false"    />
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
                        

                           
                           	<!--                             确认密码 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="user.pwdRepeat" />：</span>
                            <div class="item-ifo">
                                <input type="password" id="pwdRepeat" name="pwdRepeat" value="${supplierUser.password }" class="text" autocomplete="off"   />
                                <i class="i-pass"></i>
                                <label id="pwdRepeat_succeed" class="blank"></label>
                                <label id="pwdRepeat_error"></label>
                            </div>
                        </div>
                         	
<!--                          验证码 -->
                           <div class="item form-group">
						   <span class="label"><b class="ftx04">*</b><spring:message code="login.randomCode" />：</span> 
						   <div class="item-ifo">
							   <input type="text" id="kaptcha" name="kaptcha" maxlength="4" class="text form-control" autocomplete="off"/>
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
                            <input type="button" class="btn-img btn-regist-<spring:message code="language" />"  id="registsubmit"    >
                        </div>
                        
                    </div>
                          

            
        </form>
    </div>
</div>
				</div>
	  </div>
	  
    <script type="text/javascript" src="${path}/js/agent/user/validateMpRegExp.js"></script>
    <script type="text/javascript" src="${path}/js/agent/user/validateMpprompt.js"></script>
    <script type="text/javascript" src="${path}/js/agent/user/registMpValidate.js"></script>
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