<%@ page language="java"  import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	  <meta charset="utf-8">
	 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	  <title>商家后台管理系统-代理管理</title>
      <%@include file="/WEB-INF/views/zh/include/base.jsp"%>
	  <link rel="stylesheet" type="text/css" href="${path}/css/zh/user.css">
      <link rel="stylesheet" type="text/css" href="${path}/css/zh/juese.css">
      <link type="text/css" rel="stylesheet" href="${path}/css/regist.css"/>
      <link type="text/css" rel="stylesheet" href="${path}/css/zh/agent/register.css"/>
      <link href="${path}/css/tooltip.css" rel="stylesheet" type="text/css" />
      <script type="text/javascript" src="${path}/js/commons/tooltip.js"></script>
      <script type="text/javascript" src="${path}/js/agent/offLineSupplier/lookBusiness.js"></script>
       <script type="text/javascript" src="${path}/js/agent/user/updateBusinessValidate.js"></script>
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
    			var isUpdate=${isUpdate};
    			if(isUpdate==0){
    				window.alert("修改失败!");
    			}
    			if(isUpdate==1){
    				tipMessage("修改成功!",toBusinessList);
    			}
    		});
    		
    		function toBusinessList(){
    			window.location.href="${path}/supplier/listBusiness?parentId=${sr.parentId}";
    		}
			
    </script>


	</head>
	<body>

       <%@include file="/WEB-INF/views/zh/include/header.jsp"%>	
       <div class="wrap">
				<%@include file="/WEB-INF/views/zh/include/leftProduct.jsp" %>
		       	<!-- 右边 start -->
				<div class="right f_l">
					<div class="title">
						<p class="c1">查看商户 </p>
					<div class="clear"></div>
				</div>
				<div class="blank5"></div>
						<div class="w" id="regist">
    <div class="mc">


<form id="formcompany" method="post" action="${path}/supplier/updateBusiness" enctype="multipart/form-data">
				<input id="language" type="hidden" name="language" value="<spring:message code="language" />" /> 
				<input type="hidden" name="status" value="0" /> 
				<input type="hidden" id="message" name="message" value="${message}" />
		<div class="form ">
              <input type="hidden"  id="supplierId" name="supplierId" value="${sr.supplierId }"> 
              <input type="hidden"  id="path" value="${path }"> 
              <input type="hidden"  id="proId" value="${sr.provinceId}"> 
              <input type="hidden"  id="citId" value="${sr.cityId}"> 
              <input type="hidden"  id="areId" value="${sr.areaId}"> 
              <input type="hidden"  id="cbc" value="${sr.companyBizCategory}"> 
              <!--             提交表单校验使用 -->
            <input type="hidden" id="ifSubmit" >
              </br></br>
              
             			 <c:if test="${ sr.activeStatus==-1 && sr.status==2}">
 <!--    						审核失败原因 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">审核失败原因</b>：</span>
                            <div class="item-ifo">
									<textarea rows="4" cols="33" disabled="disabled">${sr.checkFailReason }</textarea>
                            </div>
                        </div></br>
                     	</c:if>
              
              
<!--                      注册名称 -->
                        <div class="item" id="select-regName">
                            <span class="label"><b class="ftx04">*</b>注册名称(企业名称)：</span>
                            <div class="f1 item-ifo">
                                <input id="companyName" type="text"  name="name"  class="text"  value="${sr.name }"  autocomplete="off"      />
                                <label id="companyName_succeed" class="blank"></label>
                                <label id="companyName_error"></label>
                            </div>
                        </div>
                        
                        <!--                      营业名称 -->
                        <div class="item" id="select-regName">
                            <span class="label"><b class="ftx04">*</b>营业名称(企业简称)：</span>
                            <div class="f1 item-ifo">
                                <input type="text" id="nameJC" name="nameJC"  class="text"  value="${sr.nameJC }"    autocomplete="off"     />
                                 <label id="nameJC_succeed" class="blank"></label>
                                <label id="nameJC_error" ></label>
                            </div>
                        </div>
                        
             <!--                         地区：  省 市 区 -->
							<div class="item" id="">
								<span class="label"><b class="ftx04"></b>企业所在地：</span>
								<div class="item-ifo" >
									<select  name="province" id="province"    ></select>
									<select  name="city" id="city"    ></select> 
									<select  name="country" id="country"  ></select> 
								    <label class="blank" id="country_succeed"></label>
                               	    <label id="country_error"></label>
								</div>
							</div><br/><br/><br/><br/>
							
							
							
                        <!--                         企业类型 -->
					<div class="item" id="companybizType">
						<span class="label"><b class="ftx04">*</b> <spring:message code="supplier.companyType" />：</span>
					<div class="item-ifo">
						<select id="companyType" name="companyBizType"  >
		                   <option   value="1"<c:if test="${sr.companyBizType==1 }">selected="selected"</c:if> >企业</option> 
		                   <option  value="2"<c:if test="${sr.companyBizType==2 }">selected="selected"</c:if>>个体工商户</option>   
		                   <option  value="3"<c:if test="${sr.companyBizType==3 }">selected="selected"</c:if>>事业单位</option>      
		 			   </select>
					</div>
					</div>
				
					<!--                         企业经营类目 -->
					<div class="item" id="Category">
						<span class="label"><b class="ftx04">*</b> <spring:message code="supplier.bizCategory" />：</span>
						<div class="item-ifo">
							<select id="category" name="companyBizCategory" ></select>    
							<label class="blank" id="category_succeed"></label>
	                   		<label id="category_error"></label>
						</div>
					</div>
					
                        
                        <!--                         营业地址 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>营业地址：</span>
                            <div class="item-ifo">
                                <input type="text" id="companyaddr" name="address" class="text"    value="${sr.address }"  />
                                <label class="blank" id="companyaddr_succeed"></label>
                                <label id="companyaddr_error"></label>
                            </div>
                        </div>
                        
                         <!--                         注册地址 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>注册地址：</span>
                            <div class="item-ifo">
                                <input type="text" id="registerAddress" name="registerAddress" class="text"    value="${sr.nameJC }"  />
                                 <label class="blank" id="registerAddress_succeed"></label>
                                <label id="registerAddress_error"></label>
                            </div>
                        </div>
                        
                        
<!--                         业务联系人 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>业务联系人：</span>
                            <div class="item-ifo">
                                <input type="text" id="contactname" name="contact" class="text"  value="${sr.contact }"   />
                                 <label id="contactname_succeed" class="blank"></label>
                                <label id="contactname_error"></label> 
                            </div>
                        </div>
                        
                        
                        <!--                         业务联系人手机 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>业务联系人手机 ：</span>
                            <div class="item-ifo">
                           		 <input type="text" id="phoneP" name="phone" class="text"    value="${sr.phone }"  />
                           		  <label id="phoneP_succeed" class="blank"></label>
                                <label id="phoneP_error"></label>
                            </div>
                        </div>
                        
                        
                        
<!--                               业务联系人电话 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>业务联系人电话：</span>
                            <div class="item-ifo">
                                <input type="text" id="contactTelP" name="contactTel" class="text"   value="${sr.contactTel }"   />
                                 <label id="contactTelP_succeed" class="blank"></label>
                                <label id="contactTelP_error"></label>    
                            </div>
                        </div>
                        
                        


                        
                        
                      
                        <!--                         企业营业执照： -->
                        <div class="item" id="" >
                        <span class="label"><b class="ftx04">*</b>营业执照：</span>
                        <div class="item-ifo" style="width:320px;">
                            <input id="businessLicenseTextField" class="text" type="text" name="businessLicenseTextField" readonly="readonly" tabindex="11">
                                <span id="filespan">
                                <label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField" class="file" type="file" onchange="document.getElementById('businessLicenseTextField').value=this.value;uploadPicture(this,'businessLicenseTextField')"  tabindex="-1"   size="28" name="businessLicenseTo" 
                                      />
                                    <input class="btn" type="button" value="<spring:message code="upload" />" tabindex="12"   >
                                </label>
                               </span>
                            <label id="businessLicenseTextField_succeed" class="blank"></label>
                            <label id="businessLicenseTextField_error"></label></br>
                            <c:if test="${!empty sr.businessLicense }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${sr.businessLicense }')">查看    </span>
                        	</c:if>
                        	<c:if test="${empty sr.businessLicense }">
                              		<span style="color:red;">未上传</span>
                        	</c:if>&nbsp;&nbsp;&nbsp;&nbsp;
                             <span style="color:#c7c7c7; line-height:24px;">上传图片格式为jpg、png、jepg</span>    
                        </div>
                    </div>
                    
                    <!-- 							企业营业执照号码 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>营业执照号码：</span>
                            <div class="item-ifo">
                                <input type="text" id="businessLicenseNo" name="businessLicenseNo" class="text"   value="${sr.businessLicenseNo }"  />
                                <label id="businessLicenseNo_succeed" class="blank"></label>
                                <label id="businessLicenseNo_error"></label>
                               
                            </div>
                        </div>
                      
                          <!--                         经营门头照片： -->
                         <div class="item" id="" >
                        <span class="label">经营门头照片：</span>
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
                    </div>
                        
                        

                        
                        
                        <!--  						   企业信息介绍 -->
						 <div class="item" id="">
                            <span class="label"><b class="ftx04"></b><spring:message code="supplier" />：</span>
                            <div class="item-ifo">
                                <textarea style="outline:none;resize:none;" rows="6" cols="35"  name="companyInfo"       >${sr.companyInfo }</textarea>
                                <label class="blank" id="companyInfo_succeed"></label>
                                <label id="companyInfo_error"></label>
                            </div>
                        </div></br></br></br>
                        
                            
                         <div class="item">
                            <span class="label">&nbsp;</span>
                         	<a href="javascript:history.go(-1)"><input type="button" style="background-color: red; color: white;width: 150px;height: 35px;cursor: pointer; font-size:20px;border-radius:5px;border:1px  solid red"  value="返回页面"></a> 
                         	<a href="javascript:void(0)"><input id="savaUpdate" type="button" style="background-color: grey; color: white;width: 150px;height: 35px;cursor: pointer; font-size:20px;border-radius:5px;border:1px  solid grey"  value="保存提交审核"></a> 
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
   
   
	<script type="text/javascript" src="${path}/js/user/industry_func.js"></script>
	<!-- 底部 start -->
     	<%@include file="/WEB-INF/views/zh/include/last.jsp"%>
	<!-- 底部 end -->

   	</body>	
  
</html>