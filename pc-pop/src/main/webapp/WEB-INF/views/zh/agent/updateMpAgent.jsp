<%@ page language="java"  import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	  <meta charset="utf-8">
	 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	  <title>商家后台管理系统-修改合伙人</title>
      <%@include file="/WEB-INF/views/zh/include/base.jsp"%>
	  <link rel="stylesheet" type="text/css" href="${path}/css/zh/user.css">
      <link rel="stylesheet" type="text/css" href="${path}/css/zh/juese.css">
      <link type="text/css" rel="stylesheet" href="${path}/css/regist.css"/>
      <link type="text/css" rel="stylesheet" href="${path}/css/zh/agent/register.css"/>
      <link href="${path}/css/tooltip.css" rel="stylesheet" type="text/css" />
      <script type="text/javascript" src="${path}/js/commons/tooltip.js"></script>
      <script type="text/javascript" src="${path}/js/agent/updateAgent.js"></script>
      <script type="text/javascript" src="${path}/js/agent/user/updateValidate.js"></script>
      
       <style type="text/css">
		body{
		    color: #333333;
		    font-family: Arial,"宋体",Lucida,Verdana,Helvetica,sans-serif;
		    font-size: 12px;
		    line-height: 150%;
		    margin: 0;
		}
    </style>
    
    <script type="text/javascript">
    
    		$(function(){
    			var supplierId=${sr.supplierId};
    			var isUpdate=${isUpdate};
    			if(isUpdate==0){
    				window.alert("修改失败!");
    			}
    			if(isUpdate==1){
    				window.alert("修改成功!");
    			}
    		 	var mess= $("#message").val();
    		 	if(mess!='' && mess!=null){
    		 		alert(mess);
    		 	}
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
						<p class="c1">修改合伙人 </p>
					<div class="clear"></div>
				</div>
				<div class="blank5"></div>
						<div class="w" id="regist">
    <div class="mc">

        <form id="formcompany"  method="post" action="${path}/supplier/updateMpAgentMessage" enctype="multipart/form-data" >
           <input id="language" type="hidden" name="language" value="<spring:message code="language" />"  />
           <input type="hidden" name="status" value="0" />
           <input type="hidden" id="message" name="message" value="${message}" />
            <input type="hidden"  id="pa" value="${path }">
            <input type="hidden"  id="p" value="${sr.provinceId }">
            <input type="hidden"  id="c" value="${sr.cityId }">
            <input type="hidden"  id="a" value="${sr.areaId }">
            <input type="hidden"  id="ban" value="${bankBranch.bankCode }">
            <input type="hidden"  id="pc" value="${bankBranch.provinceCode }">
            <input type="hidden"  id="cc" value="${bankBranch.cityCode }">
            <input type="hidden"  id="bc" value="${bankBranch.bankBranchCode }">
<!--             提交表单校验使用 -->
            <input type="hidden" id="ifSubmit" >
           	<input type="hidden" value="${parentId }"  name="parentId"/>
           	<input type="hidden" value="${sr.supplierId }"  name="supplierId"/>
            
                    <div class="form " style="border:1px solid #e2e9ee;width:842px">
                        <h3>基本信息<span class="tishi-<spring:message code="language" />"><spring:message code="supplier.tishi" /></span></h3>
                     	
                     	<c:if test="${ sr.activeStatus==-1 && sr.status==2}">
 <!--    						审核失败原因 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">审核失败原因</b>：</span>
                            <div class="item-ifo">
									<textarea rows="4" cols="33" disabled="disabled">${sr.checkFailReason }</textarea>
                            </div>
                        </div></br>
                     	</c:if>
<!--    						姓名 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="company.userName" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="userName" name="userName" class="text" value="${sr.userName }" disabled="disabled" />
                                <label id="userName_succeed" class="blank"></label>
                                <label id="userName_error"></label>                     
                            </div>
                        </div>
<!--                         手机号 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="company.mobile" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="userMobile" name="userMobile"  class="text" value="${sr.userMobile }" disabled="disabled" />
                            </div>
                        </div>
                        
                        <!--                         所在地 -->
							<div class="item" id="">
								<span class="label"><b class="ftx04">*</b>所在地：</span>
								<div class="item-ifo" id="dp"> 
									<select id="provinceId" name="provinceId"  ></select>
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
                                <input type="text" id="registerAddress" name="registerAddress" class="text"  value="${sr.registerAddress }"   />
                                <label class="blank" id="registerAddress_succeed"></label>
                                <label id="registerAddress_error"></label>
                         
                            </div>
                        </div>
                        
                        
                        
<!-- 							邮箱 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="supplier.companyMail" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="mailbox" name="email" class="text"    value="${sr.email }" />
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
                        
 						    
 					 <!--                         企业法人证件号码 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>证件号码：</span>
                            <div class="item-ifo">
                                <input type="text" id="legalPersonCardNo" name="legalPersonCardNo" class="text" value="${sr.legalPersonCardNo }" />
                                <label class="blank" id="legalPersonCardNo_succeed"></label>
                                <label id="legalPersonCardNo_error"></label>
                            </div>
                        </div>
                        
                       
                          <!--                         法人身份证正面： -->
                        <div class="item" id="" >
                        <span class="label"><b class="ftx04">*</b>法人身份证正面：</span>
                        <div class="item-ifo" style="width:320px;">
                            <input id="idCardFrontTextField" class="text" type="text" name="idCardFrontTextField" readonly="readonly" tabindex="11">
                                <span id="filespan">
                                <label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField" class="file" type="file" onchange="document.getElementById('idCardFrontTextField').value=this.value;uploadPicture(this,'idCardFrontTextField')"  tabindex="-1"   size="28" name="idCardFrontTo" 
                                      />
                                    <input class="btn" type="button"  value="<spring:message code="upload" />" tabindex="12"   >
                                </label>
                               </span>
                            <label id="idCardFrontTextField_succeed" class="blank"></label>
                            <label id="idCardFrontTextField_error"></label></br>
                            <c:if test="${!empty sr.idCardFront }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${sr.idCardFront }')">查看    </span>
                        			</c:if>
                        			<c:if test="${empty sr.idCardFront }">
                              		<span style="color:red;">未上传</span>
                        			</c:if>&nbsp;&nbsp;&nbsp;&nbsp;
                             <span style="color:#c7c7c7; line-height:24px;">上传图片格式为jpg、png、jepg</span>    
                        </div>
                    </div>
                    
                    
                      <!--                          法人身份证反面： -->
                        <div class="item" id="" >
                        <span class="label"><b class="ftx04">*</b> 法人身份证反面：</span>
                        <div class="item-ifo" style="width:320px;">
                            <input id="idCardVersoTextField" class="text" type="text" name="idCardVersoTextField" readonly="readonly" tabindex="11">
                                <span id="filespan">
                                <label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField" class="file" type="file" onchange="document.getElementById('idCardVersoTextField').value=this.value;uploadPicture(this,'idCardVersoTextField')"  tabindex="-1"   size="28" name="idCardVersoTo" 
                                      />
                                    <input class="btn" type="button" value="<spring:message code="upload" />" tabindex="12"   >
                                </label>
                               </span>
                            <label id="idCardVersoTextField_succeed" class="blank"></label>
                            <label id="idCardVersoTextField_error"></label></br>
                            <c:if test="${!empty sr.idCardVerso }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${sr.idCardVerso }')">查看    </span>
                        	</c:if>
                        	<c:if test="${empty sr.idCardVerso }">
                              		<span style="color:red;">未上传</span>
                        	</c:if>&nbsp;&nbsp;&nbsp;&nbsp;
                             <span style="color:#c7c7c7; line-height:24px;">上传图片格式为jpg、png、jepg</span>    
                        </div>
                    </div>
                    
                        <!--                         工作场景照片： -->
                        <div class="item" id="" >
                        <span class="label">工作场景照片：</span>
                        <div class="item-ifo" style="width:320px;">
                            <input id="companyStoreLogoTextField" class="text" type="text" name="companyStoreLogoTextField" readonly="readonly" tabindex="11">
                                <span id="filespan">
                                <label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField" class="file" type="file" onchange="document.getElementById('companyStoreLogoTextField').value=this.value;uploadPicture(this,'companyStoreLogoTextField')"  tabindex="-1"   size="28" name="companyStoreLogoTo" 
                                      />
                                    <input class="btn" type="button" value="<spring:message code="upload" />" tabindex="12"   >
                                </label>
                               </span>
                            <label id="companyStoreLogoTextField_succeed" class="blank"></label>
                            <label id="companyStoreLogoTextField_error"></label></br>
                             <c:if test="${!empty sr.companyStoreLogo }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${sr.companyStoreLogo }')">查看    </span>
                        	</c:if>
                        	<c:if test="${empty sr.companyStoreLogo }">
                              		<span style="color:red;">未上传</span>
                        	</c:if>&nbsp;&nbsp;&nbsp;&nbsp;
                             <span style="color:#c7c7c7; line-height:24px;">上传图片格式为jpg、png、jepg</span>    
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
                                <input type="text" id="accountName" name="accountName" class="text" value="${sr.accountName }"  />
                                <label id="accountName_succeed" class="blank"></label>
                                <label id="accountName_error"></label>                     
                            </div>
                        </div>
                        
<!--                         结算账号 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>结算账号：</span>
                            <div class="item-ifo">
                                <input type="text" id="accountNo" name="accountNo"  class="text" value="${sr.accountNo }" />
                                <label id="accountNo_succeed" class="blank"></label>
                                <label id="accountNo_error"></label>
                            </div>
                        </div>
                      

<!-- 					企业账户信息 -->
						<!-- 用户名 -->
                        <h3 style="width:810px">商家登录信息</h3>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="user.loginName" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="regName" name="loginName"    class="text"   value="${supplierUser.loginName }" disabled="disabled"/>
                            </div>
                        </div>  
                                                 
		    		 
                        <div class="item">
                            <span class="label">&nbsp;</span>
                            <input type="button" id="registsubmit1" style="background-color: red; color: white;width: 227px;height: 35px;cursor: pointer; font-size:20px;border-radius:5px;border:1px  solid red"  value="修   改"> 
                        </div>
                        
                        
                        
                    </div>
                          

            
        </form>
    </div>
</div>
				</div>
	  </div>
	  

	
	<!-- 底部 start -->
	   	<%@include file="/WEB-INF/views/zh/include/last.jsp"%>
	<!-- 底部 end -->

   	</body>	
  
</html>