<%@ page language="java"  import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	  <meta charset="utf-8">
	 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	  <title>商家后台管理系统-商家添加</title>
      <%@include file="/WEB-INF/views/zh/include/base.jsp"%>
	  <link rel="stylesheet" type="text/css" href="${path}/css/zh/user.css">
      <link rel="stylesheet" type="text/css" href="${path}/css/zh/juese.css">
      <link type="text/css" rel="stylesheet" href="${path}/css/regist.css"/>
      <link href="${path}/css/tooltip.css" rel="stylesheet" type="text/css" />
<%--  <link rel="shortcut icon" href="${path}/images/favicon1.ico" /> --%>
      <script type="text/javascript" src="${path}/js/commons/tooltip.js"></script>
     
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
<script type="text/javascript">
$(function(){ 
// 	加载市级数据
	/* 加载二级目录 */
		$("#companyType").change(function(){
		$("#category").empty();
		var a=$("#companyType").val()
		var name="";
		if(a==1){
			name="企业";
		}
		if(a==2){
			name="个体工商户";
		}
		if(a==3){
			name="事业单位";
		}
		$.post(
    			"${path}"+"/supplier/getCategory",
    			{'sectorname':a},
    			   function(data){
    				var city="<option value=''>请选择</option>";
        				$.each(eval(data),function(i,n){
        					if(name==n.sectortype){
        					city+="<option value='"+n.sectorcode+"'>"+n.sectorname+"</option>";
        					}
        				});	
        				$("#category").append(city);
//     				}
    			   }
    	);
	});
// 	加载省级数据
		$.post(
			"${path}"+"/supplier/getProvinceList",
			   function(data){
				$.each(eval(data),function(i,n){
					$("#province").append("<option value='"+n.provinceid+"'>"+n.provincename+"</option>");
				});	
			   }
	);
	
// 	加载市级数据
	/* 加载二级目录 */
		$("#province").change(function(){
		$("#city").empty();
		$("#country").empty();
		$.post(
    			"${path}"+"/supplier/getCityList",
    			   function(data){
    				var a=$("#province").val();
    				var city="<option value=''>请选择</option>";
        				$.each(eval(data),function(i,n){
        					if(a==n.provinceid){
        					city+="<option value='"+n.cityid+"'>"+n.cityname+"</option>";
        					}
        				});	
        				$("#city").append(city);
//     				}
    			   }
    	);
	});

		
// 	加载区域数据
		$("#city").change(function(){
			$("#country").empty();
			$.post(
        			"${path}"+"/supplier/getCountryList",
        			   function(data){
        				var a=$("#city").val();
        				var country="<option value=''>请选择</option>";
        				$.each(eval(data),function(i,n){
        					if(a==n.cityid){
        					country+="<option value='"+n.countyid+"'>"+n.countyname+"</option>";
        					}
        				});
						$("#country").append(country);
        			   }
        	);
		});

	
//		加载银行数据
		$.post(
				"${path}"+"/supplier/getBankList",
     			   function(data){
     				$.each(eval(data),function(i,n){
     					$("#bank").append("<option value='"+n.bankCode+"'>"+n.bankName+"</option>");
     				});	
     			   }
     	);
		
		
		
//		 加载该银行所有的省数据
	     $("#bank").change(function(){
				$("#bankProvinceId").empty();
				$("#bankAreaId").empty();
				$("#accoutBankno").empty();
				var bankCode=$("#bank").val();
				$.post(
						"${path}"+"/supplier/getBankProvinceList",
						{'bankCode':bankCode},
	     			   function(data){
	     				var bankProvince="<option value=''>请选择</option>";
	     				$.each(eval(data),function(i,n){
	     					bankProvince+="<option value='"+n+"'>"+i+"</option>";
	     				});
							$("#bankProvinceId").append(bankProvince);
	     			   },
	     			   "json"
	     	);
	     });
	     
	     
	     
//	     加载该银行所在省的所有区的数据
	   $("#bankProvinceId").change(function(){
				$("#bankAreaId").empty();
				$("#accoutBankno").empty();
				var bankCode=$("#bank").val();
				var bankProvinceId=$("#bankProvinceId").val();
				$.post(
						"${path}"+"/supplier/getBankCityList",
						{'bankCode':bankCode,'bankProvinceId':bankProvinceId},
	     			   function(data){
	     				var bankArea="<option value=''>请选择</option>";
	     				$.each(eval(data),function(i,n){
	     					bankArea+="<option value='"+n+"'>"+i+"</option>";
	     				});
							$("#bankAreaId").append(bankArea);
	     			   },
	     			   "json"
	     	);
	    });
	     
//	     加载该所在省的所有区的所有银行的数据
	   $("#bankAreaId").change(function(){
			$("#accoutBankno").empty();
			var bankCode=$("#bank").val();
			var bankAreaId=$("#bankAreaId").val();
			$.post(
					"${path}"+"/supplier/getSubBankOfCity",
					{'bankCode':bankCode,'bankAreaId':bankAreaId},
     			   function(data){
     				var accoutBankname="<option value=''>请选择</option>";
     				$.each(eval(data),function(i,n){
     					accoutBankname+="<option value='"+n.bankBranchCode+"'>"+n.bankBranchName+"</option>";
     				});
						$("#accoutBankno").append(accoutBankname);
     			   },
     			   "json"
     	);
    });  
	   
	   	
});


</script>

<script type="text/javascript">
var jsSexMan = document.getElementById("companyType").value;
var category=document.getElementById("category").value;
</script>
	</head>
	<body>

       <%@include file="/WEB-INF/views/zh/include/header.jsp"%>	
       <div class="wrap">
				<%@include file="/WEB-INF/views/zh/include/leftProduct.jsp" %>
		       	<!-- 右边 start -->
				<div class="right f_l">
					<div class="title">
						<p class="c1">添加商户 </p>
					<div class="clear"></div>
				</div>
				<div class="blank5"></div>
						<div class="w" id="regist">
    <div class="mc">

<form id="formcompany" method="post" action="${path}/supplier/save" enctype="multipart/form-data">
				
				<input type="hidden" name="parentId" value="${parentId }"/>
				
				
				<input id="language" type="hidden" name="language"
					value="<spring:message code="language" />" /> <input type="hidden"
					name="status" value="0" /> <input type="hidden" id="message"
					name="message" value="${message}" />
				<!-- <div class=" w1" id="entry">
                <div class="mc " id="bgDiv"> -->
				<div class="form ">
					<h3>
					<%-- 	<spring:message code="supplier" />
						 --%>
					</h3>
                    <!--                      注册名称 -->
                        <div class="item" id="select-regName">
                            <span class="label"><b class="ftx04">*</b>注册名称(企业名称)：</span>
                            <div class="f1 item-ifo">
                                <input type="text" id="companyname" name="name" onblur="checkName1();" class="text" 
                                       autocomplete="off"  tabindex="1"    />
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
                                       autocomplete="off"  tabindex="1"    />
                                <label id="company_error" class="error" style="display: none"> <spring:message code="user.comNameRegisted" /></label>
                                <label id="companyname_succeed" class="blank"></label>
                                <label id="companyname_error"></label>
                            </div>
                        </div>
                        
                        <!--                         企业所在地 -->
							<div class="item" id="">
								<span class="label"><b class="ftx04">*</b>企业所在地：</span>
								<div class="item-ifo" > 
									<select id="province" name="provinceId"  ><option value="" >请选择</option></select>
									<select id="city" name="cityId"  ></select> 
									<select id="country" name="areaId"  ></select> 
								</div>
							</div><br/><br/><br/><br/>
                        
                        
                        <!--                         营业地址 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>营业地址：</span>
                            <div class="item-ifo">
                                <input type="text" id="companyaddr" name="address" class="text"  tabindex="5"   />
                                
                                <label class="blank" id="companyaddr_succeed"></label>
                                <label id="companyaddr_error"></label>
                         
                            </div>
                        </div>
	
						<!--                         企业类型 -->
					<div class="item" id="companybizType">
						<span class="label"><b class="ftx04">*</b> <spring:message
								code="supplier.companyType" />：</span>
						<div class="item-ifo">
						
				<select id="companyType" name="companyType">
                   <option  value="1">企业</option> 
                   <option  value="2">个体工商户</option>   
                   <option  value="3">事业单位</option>      
 			   </select>
 						
                       <!--   <label id="contactname_succeed" class="blank"></label>
							<label id="contactname_error"></label> -->
						</div>
					</div>
				
					<!--                         企业经营类目 -->
					<div class="item" id="Category">
						<span class="label"><b class="ftx04">*</b> <spring:message
								code="supplier.bizCategory" />：</span>
						<div class="item-ifo">
							<select id="category" name="category">
                     <option id="" >请选择</option>
 
 </select>    <!-- <label id="contactname_succeed" class="blank"></label>
							<label id="contactname_error"></label> -->
						</div>
					</div>
				


				  <!--                         注册地址 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>注册地址：</span>
                            <div class="item-ifo">
                                <input type="text" id="companyaddr" name="registerAddress" class="text"  tabindex="5"   />
                                <label class="blank" id="companyaddr_succeed"></label>
                                <label id="companyaddr_error"></label>
                         
                            </div>
                        </div>
                        
                        
<!--                         业务联系人 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>业务联系人：</span>
                            <div class="item-ifo">
                                <input type="text" id="contactname" name="contact" class="text"    tabindex="6"  />
                                <label id="contactname_succeed" class="blank"></label>
                                <label id="contactname_error"></label>                     
                            </div>
                        </div>
                        
                        
<!--                               业务联系人电话 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>业务联系人电话：</span>
                            <div class="item-ifo">
                                <input type="text" id="contactname" name="contactTel" class="text"    tabindex="6"  />
                                <label id="contactname_succeed" class="blank"></label>
                                <label id="contactname_error"></label>                     
                            </div>
                        </div>
                        
                        
<!--                         业务联系人手机 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>业务联系人手机 ：</span>
                            <div class="item-ifo">
                                <input type="hidden" id="teleCode" name="phone"  readonly="readonly"  class="text" style="width:30px;" />
                                <input type="text" id="tele" name="phone" class="text"  style="width:135px;" tabindex="7"  />
                                <label id="tele_succeed" class="blank"></label>
                                <label id="tele_error"></label>
                               
                            </div>
                        </div>
                        
<!-- 							邮箱 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="supplier.companyMail" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="mailbox" name="email" class="text"   tabindex="8"  />
                                <label id="mailbox_succeed" class="blank"></label>
                                <label id="mailbox_error"></label>
                               
                            </div>
                        </div>
                        
                        <!--                         企业法人名称 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>法人名称：</span>
                            <div class="item-ifo">
                                <input type="text" id="companyaddr" name="legalPerson" class="text"  tabindex="5"   />
                                
                                <label class="blank" id="companyaddr_succeed"></label>
                                <label id="companyaddr_error"></label>
                         
                            </div>
                        </div>
                        
                        
                        <!--                         企业法人电话 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>法人电话：</span>
                            <div class="item-ifo">
                                <input type="text" id="companyaddr" name="legalPersonPhone" class="text"  tabindex="5"   />
                                
                                <label class="blank" id="companyaddr_succeed"></label>
                                <label id="companyaddr_error"></label>
                         
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
                                <input type="text" id="companyaddr" name="legalPersonCardNo" class="text"  tabindex="5"   />
                                
                                <label class="blank" id="companyaddr_succeed"></label>
                                <label id="companyaddr_error"></label>
                            </div>
                        </div>
                        
                       
                          <!--                         法人身份证正面： -->
                        <div class="item" id="" >
                        <span class="label"><b class="ftx04">*</b>法人身份证正面：</span>
                        <div class="item-ifo" style="width:320px;">
                            <input id="idCardFrontTextField" class="text" type="text" name="idCardFrontTextField" readonly="readonly" tabindex="11">
                                <span id="filespan">
                                <label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField" class="file" type="file" onchange="document.getElementById('idCardFrontTextField').value=this.value;"  tabindex="-1"   size="28" name="idCardFrontTo" 
                                      />
                                    <input class="btn" type="button" value="<spring:message code="upload" />" tabindex="12"   >
                                </label>
                               </span>
                            <label id="fileField_succeed" class="blank"></label>
                            <label id="fileField_error"></label></br>
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
                                    <input id="fileField" class="file" type="file" onchange="document.getElementById('idCardVersoTextField').value=this.value;"  tabindex="-1"   size="28" name="idCardVersoTo" 
                                      />
                                    <input class="btn" type="button" value="<spring:message code="upload" />" tabindex="12"   >
                                </label>
                               </span>
                            <label id="fileField_succeed" class="blank"></label>
                            <label id="fileField_error"></label></br>
                             <span style="color:#c7c7c7; line-height:24px;">上传图片格式为jpg、png、jepg</span>    
                        </div>
                    </div>
                    
 						    
                        
                        <!--                         企业营业执照： -->
                        <div class="item" id="" >
                        <span class="label"><b class="ftx04">*</b>营业执照：</span>
                        <div class="item-ifo" style="width:320px;">
                            <input id="businessLicenseTextField" class="text" type="text" name="businessLicenseTextField" readonly="readonly" tabindex="11">
                                <span id="filespan">
                                <label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField" class="file" type="file" onchange="document.getElementById('businessLicenseTextField').value=this.value;"  tabindex="-1"   size="28" name="businessLicenseTo" 
                                      />
                                    <input class="btn" type="button" value="<spring:message code="upload" />" tabindex="12"   >
                                </label>
                               </span>
                            <label id="fileField_succeed" class="blank"></label>
                            <label id="fileField_error"></label></br>
                             <span style="color:#c7c7c7; line-height:24px;">上传图片格式为jpg、png、jepg</span>    
                        </div>
                    </div>
                    
                    <!-- 							企业营业执照号码 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>营业执照号码：</span>
                            <div class="item-ifo">
                                <input type="text" id="mailbox" name="businessLicenseNo" class="text"   tabindex="8"  />
                                <label id="mailbox_succeed" class="blank"></label>
                                <label id="mailbox_error"></label>
                               
                            </div>
                        </div>
                        
                          <!--                         经营门头照片： -->
                        <div class="item" id="" >
                        <span class="label">经营门头照片：</span>
                        <div class="item-ifo" style="width:320px;">
                            <input id="companyStoreLogoTextField" class="text" type="text" name="companyStoreLogoTextField" readonly="readonly" tabindex="11">
                                <span id="filespan">
                                <label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField" class="file" type="file" onchange="document.getElementById('companyStoreLogoTextField').value=this.value;"  tabindex="-1"   size="28" name="companyStoreLogoTo" 
                                      />
                                    <input class="btn" type="button" value="<spring:message code="upload" />" tabindex="12"   >
                                </label>
                               </span>
                            <label id="fileField_succeed" class="blank"></label>
                            <label id="fileField_error"></label></br>
                             <span style="color:#c7c7c7; line-height:24px;">上传图片格式为jpg、png、jepg</span>    
                        </div>
                    </div>
                        
                        
                        
<!--                         上传能够证明企业合法性的证明文件： -->
                        <div class="item" id="" >
                        <span class="label"><b class="ftx04"></b><spring:message code="supplier.companyFile" />：</span>
                        <div class="item-ifo" style="width:320px;">
                            <input id="companyLegitimacyUrlTextField" class="text" type="text" name="companyLegitimacyUrlTextField" readonly="readonly" tabindex="11">
                                <span id="filespan">
                                <label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField" class="file" type="file" onchange="document.getElementById('companyLegitimacyUrlTextField').value=this.value;"  tabindex="-1"   size="28" name="companyLegitimacy" 
                                      />
                                    <input class="btn" type="button" value="<spring:message code="upload" />" tabindex="12"   >
                                </label>
                               </span>
                            <label id="fileField_succeed" class="blank"></label>
                            <label id="fileField_error"></label></br>
                               
                        </div>
                    </div>
                    
                    
                    
<!-- 					公司详情文件： -->
					<div class="item" id="" >
                            <span class="label"><b class="ftx04"></b><spring:message code="supplier.companyDetailFile" />：(<a href="${path}/modelFile/supplierInfo_20161103.docx"><span style="color:blue"><spring:message code="supplier.companyDetailFileTemplate" /></span></a> ) </span>
                            <div class="item-ifo" style="width:320px;">
                                <input id="companyDetailedUrlTextField" class="text" type="text" name="companyDetailedUrlTextField" readonly="readonly"  tabindex="13">
                                <span id="filespan">
                                <label for="fileField1">
                                <input id="fileField1" class="file" type="file" onchange="document.getElementById('companyDetailedUrlTextField').value=this.value" size="28" name="companyDetailed" />
                                <input class="btn" type="button" value="<spring:message code="upload" />"   tabindex="14" >
                                </label>
                               </span>
                                <label id="fileField1_succeed" class="blank"></label>
                                <label id="fileField1_error"></label><br>
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
                        <h3 style="width:810px">企业营销折扣</h3>
                        					<!--                         折扣 -->
					<div class="item" id="zk">
						<span class="label"><b class="ftx04">*</b> <spring:message
								code="discount" />：</span>
						<div class="item-ifo">
							<input type="text" id="contactnameFlow" name="disCount" class="text"
								tabindex="6" /> <label id="contactname_succeed" class="blank"></label>
							
							<label id="contactname_error"></label>
						</div>
						
					</div> 
					<div class="item" id="zk">
						<span class="label"><b class="ftx04"></b> <spring:message
								code="ss" />：</span>
						<div class="item-ifo">
							<input type="text" id="contactname" name="contact22" class="text" style="border:none"
								tabindex="6" value="请输入7到9折(小数点后两位)"/> <label id="contactname_succeed" class="blank"></label>
							
							<label id="contactname_error"></label>
						</div>
						
					</div> 
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
                                <input type="text" id="userName" name="accountName" class="text"   tabindex="6"  />
                                <label id="userName_succeed" class="blank"></label>
                                <label id="userName_error"></label>                     
                            </div>
                        </div>
                        
<!--                         结算账号 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>结算账号：</span>
                            <div class="item-ifo">
                                <input type="text" id="userMobile" name="accountNo"  class="text"  tabindex="7"  />
                                <label id="userMobile_succeed" class="blank"></label>
                                <label id="userMobile_error"></label>
                            </div>
                        </div>
                        <div class="" id="" style="margin:0px 0px 10px 250px;">
                           <span id="userMobile_info" style="color: red;">请注意：手机号即为登录名、初始密码与商家后台密码一致，请注册成功后登录修改!</span>
                      	</div>
                        
                        
<!--                         <h3 style="width:810px">代理地区</h3> -->
<!--                          代理级别 --> 
<!--  						<div class="item" id=""> -->
<!--                             <span class="label"><b class="ftx04">*</b>代理级别：</span> -->
<!--                             <div class="item-ifo"> -->
<%--                                 <input type="text"  class="text"  value="${supplierAgentType.name }" tabindex="8"  disabled="disabled"/> --%>
<%--                                 <input type="hidden" name="agentCode" value="${supplierAgentType.code }" /> --%>
<!--                             </div> -->
<!--                         </div> -->

                        
<!--                         代理地区：  省 市 区 --> 
<!-- 							<div class="item" id=""> -->
<%-- 								<span class="label"><b class="ftx04">*</b><spring:message code="agent.area" />：</span> --%>
<!-- 								<div class="item-ifo" >  -->
<!-- 									<select id="province" name="province"  ><option value="" >省代理</option></select> -->
<!-- 									<select id="city" name="city"  ></select>  -->
<!-- 									<select id="country" name="country"  ></select>  -->
<!-- 								</div> -->
<!-- 							</div><br/><br/><br/><br/> -->
							

   						<!-- 企业个人账号  -->
<!--    						姓名 -->
                        <h3 style="width:810px"><spring:message code="company.account" /></h3>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="company.userName" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="userName" name="userName" class="text"   tabindex="6"  />
                                <label id="userName_succeed" class="blank"></label>
                                <label id="userName_error"></label>                     
                            </div>
                        </div>
<!--                         手机号 -->
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="company.mobile" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="userMobile" name="userMobile"  class="text"  tabindex="7"  />
                                <label id="userMobile_succeed" class="blank"></label>
                                <label id="userMobile_error"></label>
                            </div>
                        </div>
                        <div class="" id="" style="margin:0px 0px 10px 250px;">
                           <span id="userMobile_info" style="color: red;">请注意：手机号即为登录名、初始密码与商家后台密码一致，请注册成功后登录修改!</span>
                      	</div>


<!-- 					企业账户信息 -->
						<!-- 用户名 -->
                        <h3 style="width:810px">商家登录信息</h3>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="user.loginName" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="regName" name="loginName"    class="text"   autocomplete="off" onblur="checkName();"  tabindex="17"  />
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
                                <input type="password" id="pwd" name="password" value="${supplierUser.password }" class="text" autocomplete="off" onpaste="return  false"  tabindex="18"  />
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
                                <input type="password" id="pwdRepeat" name="pwdRepeat" value="${supplierUser.password }" class="text" autocomplete="off"  tabindex="19" />
                                <i class="i-pass"></i>
                                <label id="pwdRepeat_succeed" class="blank"></label>
                                <label id="pwdRepeat_error"></label>
                            </div>
                        </div>
                        <!--                          验证码 -->
                           <div class="item form-group">
						   <span class="label"><b class="ftx04">*</b><spring:message code="login.randomCode" />：</span> 
						   <div class="item-ifo">
							   <input type="text" id="kaptcha" name="kaptcha" maxlength="4" class="text form-control"  tabindex="20"/>
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
				<!-- 确认 -->
                        <div class="item">
                            <span class="label">&nbsp;</span>
                            <input type="button" class="btn-img btn-regist-<spring:message code="language" />"  id="registsubmit"  tabindex="21" " >
                        </div>
                        

				</div>
				<!--  </div>
            </div> -->
				<div id="maskLayer" style="display: none;">
					<div id="alphadiv" style="filter: alpha(opacity = 50); opacity: .5"></div>
					<div id="drag">
						<h4 id="drag_h"></h4>
						<div id="drag_con">
							<div id="jobAreaAlpha"></div>
						</div>
					</div>
				</div>

			</form>
    </div>
</div>
				</div>
	  </div>
	  
<div class="w">
    <div id="footer-2013"><%--
        <div class="copyright">Copyright</div>
    --%></div>
</div>
    <script type="text/javascript" src="${path}/js/bussion/validateRegExp.js"></script>
    <script type="text/javascript" src="${path}/js/bussion/validateprompt.js"></script>
    <script  type="text/javascript" src="${path}/js/user/drag.js"></script> 
   <script type="text/javascript">
					$("#registsubmit").click(function(){
						//alert(3333333);
					 	var r = $("#formcompany").form('validate');
						if(r){
							$("#formcompany").submit();
						} 
					});
				</script>
				<script type="text/javascript">
		var ind_a = $
		{
			category
		};
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
			if (typeof needle == 'string' || typeof needle == 'number') {
				for ( var i in haystack) {
					if (haystack[i] == needle) {
						return true;
					}
				}
			}
			return false;
		}

		$(function() { //生成验证码         
			$("#kaptchaImage").click(
					function() {
						$(this).hide().attr(
								"src",
								"${path}/supplier/validateCode?uid=${uid}&"
										+ Math.random() * 100).fadeIn();
					});
		});
		/* window.onbeforeunload = function(){  
		 //关闭窗口时自动退出  
		 if(event.clientX>360&&event.clientY<0||event.altKey){     
		 alert(parent.document.location);  
		 }  
		 };    */
		function changeCode() { //刷新
			$("#kaptchaImage").hide().attr(
					"src",
					"${path}/supplier/validateCode?uid=${uid}&" + Math.random()
							* 100).fadeIn();
			event.cancelBubble = true;
		};
	</script>
   
	<%-- <script  type="text/javascript" src="${path}/js/user/industry_arr.js"></script>  --%>

	<script type="text/javascript" src="${path}/js/user/industry_func.js"></script>
		<!-- 底部 start -->
     	<%@include file="/WEB-INF/views/zh/include/last.jsp"%>
	<!-- 底部 end -->

   	</body>	
  
</html>