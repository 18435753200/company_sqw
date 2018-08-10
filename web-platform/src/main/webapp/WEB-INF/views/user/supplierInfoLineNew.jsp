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
	  <script type="text/javascript" src="${path}/commons/js/user/scode.js"></script>
	  <script type="text/javascript" src="${path}/commons/js/user/address.js"></script>
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
	
	
	
	$(function(){
	    	var supplierId=${data.supplierId};
	    	var cbc=${data.companyBizCategory};
	    	
	    	var ct=$("#companyType").val();
			var name="";
			if(ct==1){
				name="企业";
			}
			if(ct==2){
				name="个体工商户";
			}
			if(ct==3){
				name="事业单位";
			}
			$.post(
	    			"../getCategory",
	    			{'sectorname':ct},
	    			   function(data){
	    				var city="<option value=''>请选择</option>";
	        				$.each(eval(data),function(i,n){
	        					if(name==n.sectortype){
	        					city+="<option value='"+n.sectorcode+"'>"+n.sectorname+"</option>";
	        					}
	        				});	
	        				$("#category").append(city);
	        				$("#category").val(cbc);
	    			   }
	    	); 
			 
	    
//	     	加载市级数据
	    	/* 加载二级目录 */
	    		$("#companyType").change(function(){
	    		$("#category").empty();
	    		var a=$("#companyType").val()
//	     		var name="";
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
	        			"../getCategory",
	        			{'sectorname':a},
	        			   function(data){
	        				var city="<option value=''>请选择</option>";
	            				$.each(eval(data),function(i,n){
	            					if(name==n.sectortype){
	            					city+="<option value='"+n.sectorcode+"'>"+n.sectorname+"</option>";
	            					}
	            				});	
	            				$("#category").append(city);

	        			   }
	        	);
	    	}); 
});

	
	
		$(function(){
			var status='${data.status}'; 
			if(status == 1 || status == 2){
				$("#noPayHqqId").attr("disabled","disabled");
				$("#fanliPriceId").attr("disabled","disabled");
			}
		})
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
			<input type="hidden" value="${data.supplierId}" id="suid">
			<input type="hidden" value="${bb.bankCode}" id="bankId">
			<input type="hidden" value="${bb.provinceCode}" id="provinceCodeId">
			<input type="hidden" value="${bb.cityCode}" id="cityCodeId">
			<input type="hidden" value="${bb.bankBranchCode}" id="bankBranchCodeId">
			
			
			
			
				<ul class="ul_vertical">
					
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>注册名称：</p>
						<input id="name"  class="jq6" name ="name" value="${fn:escapeXml(data.name)}"  onblur="validateSingle(this, 'notnull')" />
							
							<span  class="input_warning"></span>
					</li>
					
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>营业名称：</p>
						<input   class="jq6" name ="nameJC" id="nameJC" value="${fn:escapeXml(data.nameJC)}"  onblur="validateSingle(this, 'notnull')" />
							<span  class="input_warning"></span>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>营业地址：</p>
						<input   class="jq6" id="address" name ="address" value="${fn:escapeXml(data.address)}"  onblur="validateSingle(this, 'notnull')" />
							<span  class="input_warning"></span>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>注册地址：</p>
						<input   class="jq6" name ="registerAddress" id="registerAddress" value="${fn:escapeXml(data.registerAddress)}"  onblur="validateSingle(this, 'notnull')" />
							<span  class="input_warning"></span>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>业务联系人：</p>
						<input class="jq6" id="contact" name ="contact" value="${fn:escapeXml(data.contact)}" onblur="validateSingle(this, 'notnull')">
							<span  class="input_warning"></span>
						
					</li>
					
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>业务联系人电话：</p>
						<input class="jq6" id="contactTel" name ="contactTel" value="${fn:escapeXml(data.contactTel)}" onblur="validateSingle(this, 'notnull')">
							<span  class="input_warning"></span>
						
					</li>
					
						<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>业务联系人手机：</p>
						<input class="jq6" id="phone"  name ="phone" value="${fn:escapeXml(data.phone)}" onblur="validateSingle(this, 'telphone')">
							<span  class="input_warning"></span>
					</li>
						<li class="blank20"></li>
						<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>邮箱：</p>
						<input class="jq6" id="email"  name="email" value="${fn:escapeXml(data.email)}" onblur="validateSingle(this, 'email')">
							<span  class="input_warning"></span>
					</li>
					
						<li class="blank20"></li>
						<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>法人名称：</p>
						<input class="jq6" id="legalPerson"  name="legalPerson" value="${fn:escapeXml(data.legalPerson)}" onblur="validateSingle(this, 'notnull')">
							<span  class="input_warning"></span>
					</li>
					
						<li class="blank20"></li>
						<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>法人电话：</p>
						<input class="jq6" id="legalPersonPhone"  name="legalPersonPhone" value="${fn:escapeXml(data.legalPersonPhone)}" onblur="validateSingle(this, 'notnull')">
							<span  class="input_warning"></span>
					</li>
				
						<li class="blank20"></li>
						<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>证件类型：</p>
						<!-- <input class="jq6" id="email"  name="email" value="身份证" onblur="validateSingle(this, 'email')"> -->
							<select id="legalPersonCardType" name="legalPersonCardType"  ><option value="1" >身份证</option></select>
							<span  class="input_warning"></span>
					</li>
					
						<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>法人证件号码：</p>
						<input class="jq6" id="legalPersonCardNo" name="legalPersonCardNo" value="${fn:escapeXml(data.legalPersonCardNo)}" onblur="validateSingle(this, 'notnull')">
							<span  class="input_warning"></span>
					</li>
					
					
					<li class="blank20"></li>
					<%-- <form id="formcompany" method="post" action="${path}/user/fileUpload" enctype="multipart/form-data"> --%>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>法人身份证正面：</p>
						 <input id="idCardFrontTextField" class="text" type="text" name="idCardFrontTextField" readonly="readonly" tabindex="11">
                           <c:if test="${!empty data.idCardFront }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${data.idCardFront }')">查看    </span>
                        			</c:if>
                        			<c:if test="${empty data.idCardFront }">
                              		<span style="color:red;">未上传</span>
                        			</c:if>
                        			<label for="fileField"  onClick="document.getElementById('textfield').focus();" />

                        			
                                    <input id="fileField" class="file" type="file" onchange="document.getElementById('idCardFrontTextField').value=this.value;"  tabindex="-1"   size="28" name="idCardFrontTo"  />
                                    <input class="btn" type="button" value="上传" tabindex="12"   >
                                   
                                   
                                  </label>
					</li>
					
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>法人身份证反面：</p>
						<input id="idCardVersoTextField" class="text" type="text" name="idCardVersoTextField" readonly="readonly" tabindex="11">
                            <c:if test="${!empty data.idCardVerso }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${data.idCardVerso }')">查看    </span>
                        	</c:if>
                        	<c:if test="${empty data.idCardVerso }">
                              		<span style="color:red;">未上传</span>
                        	</c:if>
                        	<label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField" class="file" type="file" onchange="document.getElementById('idCardVersoTextField').value=this.value;"  tabindex="-1"   size="28" name="idCardVersoTo" 
                                      />
                                    <input class="btn" type="button" value="上传" tabindex="12"   >
                                </label>
					</li>
					
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>营业执照：</p>
						<input id="businessLicenseTextField" class="text" type="text" name="businessLicenseTextField" readonly="readonly" tabindex="11">
                            <c:if test="${!empty data.businessLicense }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${data.businessLicense }')">查看    </span>
                        	</c:if>
                        	<c:if test="${empty data.businessLicense }">
                              		<span style="color:red;">未上传</span>
                        	</c:if>
                        	<label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField" class="file" type="file" onchange="document.getElementById('businessLicenseTextField').value=this.value;"  tabindex="-1"   size="28" name="businessLicenseTo" 
                                      />
                                    <input class="btn" type="button" value="上传" tabindex="12"   >
                                </label>
					</li>
					
					</li>
					
						
					
					
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>经营门头照片：</p>
						  <input id="companyStoreLogoTextField" class="text" type="text" name="companyStoreLogoTextField" readonly="readonly" tabindex="11">
                            <c:if test="${!empty data.companyStoreLogo }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${data.companyStoreLogo }')">查看    </span>
                        	</c:if>
                        	<c:if test="${empty data.companyStoreLogo }">
                              		<span style="color:red;">未上传</span>
                        	</c:if>
                        	<label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField" class="file" type="file" onchange="document.getElementById('companyStoreLogoTextField').value=this.value;"  tabindex="-1"   size="28" name="companyStoreLogoTo" 
                                      />
                                    <input class="btn" type="button" value="上传" tabindex="12"   >
                                </label>
					</li>
					
					
					
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>上传能够证明企业合法性的证明文件：</p>
						 <input id="companyLegitimacyUrlTextField" class="text" type="text" name="companyLegitimacyUrlTextField" readonly="readonly" tabindex="11">
                           <c:if test="${!empty data.companyLegitimacyUrl }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${data.companyLegitimacyUrl }')">下载    </span>
                        	</c:if>
                        	<c:if test="${empty data.companyLegitimacyUrl }">
                              		<span style="color:red;">未上传</span>
                        	</c:if>
                        	 <label for="fileField"  onClick="document.getElementById('textfield').focus();" >
                                    <input id="fileField" class="file" type="file" onchange="document.getElementById('companyLegitimacyUrlTextField').value=this.value;"  tabindex="-1"   size="28" name="companyLegitimacy" 
                                      />
                                    <input class="btn" type="button" value="上传" tabindex="12"   >
                                </label>
					</li>
					
					<li class="blank20"></li>
						<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>公司详情文件：</p>
						 <input id="companyDetailedUrlTextField" class="text" type="text" name="companyDetailedUrlTextField" readonly="readonly"  tabindex="13">
						<c:if test="${!empty data.companyDetailedUrl }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${data.companyDetailedUrl }')">下载    </span>
                        	</c:if>
                        	<c:if test="${empty data.companyDetailedUrl }">
                              		<span style="color:red;">未上传</span>
                        	</c:if>
                        	<label for="fileField1">
                                <input id="fileField1" class="file" type="file" onchange="document.getElementById('companyDetailedUrlTextField').value=this.value" size="28" name="companyDetailed" />
                                <input class="btn" type="button" value="上传"   tabindex="14" >
                                </label>
					</li>
					<!-- </form> -->
					<li class="blank20"></li>
						<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>营业执照号码：</p>
						<input class="jq6" id="businessLicenseNo"  name="businessLicenseNo" value="${fn:escapeXml(data.businessLicenseNo)}" onblur="validateSingle(this, 'notnull')">
							<span  class="input_warning"></span>
					</li>
					
					</li>
					
						<input  type="hidden"  class="i1" name ="nameJC" value="${fn:escapeXml(data.nameJC)}"  onblur="validateSingle(this, 'notnull')" />
						<input  type="hidden"  class="i1" name ="nameJC" id="sjSupplierCodeId" value="${fn:escapeXml(sjSupplier.supplierCode)}"  onblur="validateSingle(this, 'notnull')" />
						<c:if test="${data.status eq 0 && data.activeStatus eq -1}">
							<input  type="hidden" type="button" style="margin-left: -20px;height:25px;margin-top: 2px;"  class="fabu_btn" id="productName" onclick="" value="选择" ></input>
						</c:if>
						<input  type="hidden"   class="i1" name ="nameJC" value="${fn:escapeXml(data.supplierCode)}"  onblur="validateSingle(this, 'notnull')" />
							<span  class="input_warning"></span>
						<input   type="hidden" class="i1" id="companyLevelId" name ="companyLevel" value="${fn:escapeXml(data.companyLevel)}"  onblur="validateSingle(this, 'notnull')" />
						<input type="hidden"  id="companyLevelOldId" type="hidden" value="${fn:escapeXml(data.companyLevel)}"/>
						<c:if test="${data.status eq 1 && (data.activeStatus eq 1 || data.activeStatus eq 0)}">
							<input  type="hidden"  type="button" style="margin-left: -10px;height: 25px;margin-top: 2px"  class="fabu_btn" id="companyLevelModifyId" onclick="modifyInfo('${data.supplierId}')" value="修改" ></input>
						</c:if>
							<span  class="input_warning"></span>
					<li class="blank20"></li>
					 <li>
						<p class="p1"><b style=" color: #FF0000;">*</b>企业管理类型：</p>
							 <select    id="typeId" name ="type" <c:if test="${!empty data.type && data.type != -1}"></c:if> >
						     <option  value="0" <c:if test="${data.type eq 0}">selected="selected"</c:if>>非自营企业</option>
					          <option  value="1" <c:if test="${data.type eq 1}">selected="selected"</c:if>>子公司</option>
				              <option  value="2" <c:if test="${data.type eq 2}">selected="selected"</c:if>>连锁企业</option>
				              <option  value="3" <c:if test="${data.type eq 3}">selected="selected"</c:if>>连锁子公司</option>
				              <option  value="4" <c:if test="${data.type eq 4}">selected="selected"</c:if>>自营企业</option>
				              <option  value="5" <c:if test="${data.type eq 5}">selected="selected"</c:if>>项目产业</option>
						  </select>
							<span  class="input_warning"></span>
					</li> 
				 <li class="blank20"></li>
					<li>
					<p class="p1"><b style=" color: #FF0000;">*</b>企业类型：</p>
				<select id="companyType" name="companyType">
				
                   <option   value="1" <c:if test="${fn:escapeXml(data.companyBizType==1 )}">selected="selected"</c:if> >企业</option> 
                   <option  value="2"  <c:if test="${fn:escapeXml(data.companyBizType==2 )}">selected="selected"</c:if>>个体工商户</option>   
                   <option  value="3" <c:if test="${fn:escapeXml(data.companyBizType==3 )}">selected="selected"</c:if>>事业单位</option>      
 			   </select>
					</li>
					
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>企业经营类目：</p>
						<select id="category" name="category">
                     <option id="" >请选择</option>
 
                     </select>
							
							<span  class="input_warning"></span>
					</li>
					
					
				
						
						<input type="hidden" class="" id="noPayHqqId" name ="noPayHqq" value="${data.noPayhqq}" onblur="noHqq()" style="float: left;height: 23px;"/>
						<span  class="input_warning"></span>
					
					
						<input  type="hidden" class="" id="fanliPriceId" name ="fanliPrice" value="${data.fanliPrice}" onblur="fanliPrice()" style="float: left;height: 23px;"/>
						<span  class="input_warning"></span>
					
					
					
					
						
						<input type="hidden"  class="i1" name="countryArea" value="${fn:escapeXml(data.countryArea)}">
						<span  class="input_warning"></span>
						
						<input  type="hidden" class="i1" name="manager" value="${fn:escapeXml(data.manager)}" onblur="validateSingle(this, 'notnull')" >
							<span  class="input_warning"></span>
						<input   type="hidden" class="i1" name ="managerTel" value="${fn:escapeXml(data.managerTel)}" onblur="validateSingle(this, 'telphone')">
							<span  class="input_warning"></span>
						<input type="hidden" class="i1" name ="userTj" value="${fn:substring(data.userTj,0,3)}****${fn:substring(data.userTj,7,11)}" onblur="validateSingle(this, 'telphone')">
							<span  class="input_warning"></span>
						
						
					
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>地区：</p>
						<div class="item" style="border-radius:5px;">
								<!--                         地区：  省 市 区 -->
									<select id="province" name="province" ><option value="" >省</option></select>
									<select id="city" name="city" ></select> 
									<select id="country" name="country"></select> 
                        </div>
					</li>
					
					<li class="blank20"></li>
						<p class="p1"><b style=" color: #FF0000;">*</b>折扣：</p>
						<input class="jq6"  id="salesDiscount" name="salesDiscount" value="${fn:escapeXml(salesDiscount)}" onblur="validateSingle(this, 'notnull')">
							<span  class="input_warning"></span>
					</li>
					
					
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>开户行名称 (开户行行号后台)：</p>
						<div class="item" style="border-radius:5px;">
								<select id="bank" name="bank"  ><option value="" >请选择</option></select>
									<select id="bankProvinceId" name="bankProvinceId"  ><option value="" >请选择</option></select> 
									<select id="bankAreaId" name="bankAreaId"  ><option value="" >请选择</option></select> 
									<select id="accoutBankno" name="accoutBankno"  ><option value="" >请选择</option></select> 
								</div><br/><br/><br/>
					</li>
					
					<li class="blank20"></li>
						<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>结算账户名称：</p>
						<input class="jq6" id="accountName" name=accountName value="${fn:escapeXml(data.accountName)}" onblur="validateSingle(this, 'notnull')">
							<span  class="input_warning"></span>
					</li>
					
					<li class="blank20"></li>
						<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>结算账号：</p>
						<input class="jq6" id="accountNo"  name="accountNo"  value="${fn:escapeXml(data.accountNo)}" onblur="validateSingle(this, 'notnull')">
							<span  class="input_warning"></span>
					</li>
					
					
						<input type="hidden"  class="i1" name ="post" value="${fn:escapeXml(data.post)}">
							<span  class="input_warning"></span>
						<input type="hidden" class="i1" name ="fax" value="${fn:escapeXml(data.fax)}">
						<input type="hidden"  class="i1" name ="kfTel" value="${fn:escapeXml(data.kfTel)}">
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>企业信息：</p>
						<textarea class="jq6" id="companyInfo"  name="companyInfo" rows="10" cols="55">${fn:escapeXml(data.companyInfo)}</textarea>
					</li>
						<input type="hidden" class="i1 validate[required,minSize[1],maxSize[100]]" id="mer-cgr"  name="categories"  readonly="readonly"  />
						<input type="hidden" id="mercgr" class="validate[required]" type="hidden" name="IndustryID" value="${product.categories}" >
						<input type="hidden" class="i1 validate[required,minSize[1],maxSize[100]]" id="mer-brand" name="brand" value="${product.brand}" />

					<li>
						<p class="p1">入驻区域类型：</p>
						<select id="companyRegion"  name="companyRegion" >
							<option value="1" <c:if test="${fn:containsIgnoreCase(data.companyRegion, '1')}">selected="selected"</c:if> >自营</option>
							<option value="2" <c:if test="${fn:containsIgnoreCase(data.companyRegion, '2')}">selected="selected"</c:if> >非自营</option>
						</select>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">入驻区域：</p>
						<select id="companyQy"  name="companyQy">
							<c:forEach items="${regionList}" var="reg">
								<option value="${reg.regionId}" <c:if test="${fn:containsIgnoreCase(data.companyQy,reg.regionId)}">selected="selected"</c:if> >${reg.regionText}</option>
							</c:forEach>
							<%-- <option value="1" <c:if test="${fn:containsIgnoreCase(data.companyQy, '1')}">selected="selected"</c:if> >自营</option>
							<option value="2" <c:if test="${fn:containsIgnoreCase(data.companyQy, '2')}">selected="selected"</c:if> >孵化</option>
							<option value="3" <c:if test="${fn:containsIgnoreCase(data.companyQy, '3')}">selected="selected"</c:if> >高新</option>
							<option value="4" <c:if test="${fn:containsIgnoreCase(data.companyQy, '4')}">selected="selected"</c:if> >普通</option> --%>
						</select>
							<input id="oldCompanyQyId" type="hidden" value="${data.companyQy}"/>
							<c:if test="${data.status eq 1 && (data.activeStatus eq 1 || data.activeStatus eq 0)}">
								<input type="button"  class="fabu_btn" id="companyQyModifyId" onclick="modifyCompanyQy('${data.supplierId}')" value="修改" ></input>
							</c:if>
					</li>
					<%-- <li class="blank20"></li>
						<li>
					<p class="p1">资质文件：</p>
						<c:if test="${not empty data.companyLegitimacyUrl }">
						<a href="${path}/user/downloadFile?url=${data.companyLegitimacyUrl}" target="downloadFileIframe">资质文件下载</a>
						</c:if>
					    <c:if test="${empty data.companyLegitimacyUrl }">
						<span style="margin-top:10px; display: inline-block;">资质文件 &nbsp;&nbsp;&nbsp;<b style=" color: #FF0000;">未上传</b></span>
						</c:if>
					</li>				
				    <li class="blank20"></li>
					<li>
					<p class="p1">详情文件：</p>
					    <c:if test="${not empty data.companyDetailedUrl  }">
					        <a href="${path}/user/downloadFile?url=${data.companyDetailedUrl}" target="downloadFileIframe">详情文件下载</a>
				       </c:if>
				        <c:if test="${empty data.companyDetailedUrl }">
						<span style="margin-top:10px; display: inline-block;">详情文件 &nbsp;&nbsp;&nbsp;<b style=" color: #FF0000;">未上传</b></span>
						</c:if>
					</li> --%>
					
						
				   <%--  <li class="blank20"></li>
					<li>
					<p class="p1">企业Logo：</p>
					    <c:if test="${not empty data.logoImgurl  }">
					    	<img alt="企业Logo" src="http://image01.zhongjumall.com/${data.logoImgurl}">
				       </c:if>
				        <c:if test="${empty data.logoImgurl }">
						<span style="margin-top:10px; display: inline-block;">企业Logo &nbsp;&nbsp;&nbsp;<b style=" color: #FF0000;">未上传</b></span>
						</c:if>
					</li> --%>
					
					
					<div class="eck">
					<c:if test="${(data.status eq 0 && data.activeStatus eq -1)||(data.status eq 1 && data.activeStatus eq -1)}">
						<div class="eck1"><button type="text" onclick="checkSuccessBtn('${data.supplierId}',1,1)"  class="tong1">审核通过</button></div>
						<div class="eck2">
							 <form id="checkForm">
							 	<input type="hidden" id="supplyType" name="supplyType" class="text1" >
							 	<input type="hidden" id="isRecord" name="isRecord" class="text1">
						        <input type="hidden" id="id1" name="id1" class="text1">
						        <input type="hidden" id="status1" name="status1"   class="text1">
						        <input type="hidden" id="source1" name="source"  value="1" class="text1">
						        <c:if test="${data.status eq 2 }">
									<textarea id="reason" rows="" maxlength="200"  name="reason" cols="">${data.checkFailReason}</textarea>
						        </c:if>
						        <c:if test="${data.status ne 2 }">
									<textarea id="reason" rows="" maxlength="200" name="reason" cols=""></textarea>
						        </c:if>
								 <input type="hidden" id="isOnekeySend" name="isOnekeySend"  value="1">
								 <input type="hidden" id="paymentType" name="paymentType"  value="3">
								 
								 <input type="hidden" id="companyRegion" name="companyRegion"  class="text1">
								 <input type="hidden" id="companyQy" name="companyQy"  class="text1">
								 <input type="hidden" id="type" name="type"  class="text1">
							</form>
							<!-- <span class="bfu"><input type="checkbox">公司名称与实际不符</span> -->
						</div>
						<div class="eck3"><button type="text" onclick="checkFailureBtn('${data.supplierId}',2,0)"  class="tong2">审核不通过</button></div>
						 </c:if>
						 <c:if test="${2==data.status}">
						    <span style="color:red;">审核不通过原因</span>
						    <div style="color:red;"></div>
						    </br>
							<div class="eck2">
									<textarea id="reason" rows="" disabled="disabled"  name="reason" cols="">${data.checkFailReason}</textarea>
							</div>
						</c:if>
						  <c:if test="${1==data.status && data.activeStatus ne -1}">
						    <span style="color:red;">审核通过原因</span>
						    <div style="color:red;"></div>
						    </br>
							<div class="eck2">
									<textarea id="reason" rows="" disabled="disabled"  name="reason" cols="">${data.checkFailReason}</textarea>
							</div>
						</c:if>
						 <form id="createForm">
							 	<input type="hidden" id="supplyType2" name="supplyType" class="text1" >
							 	<input type="hidden" id="isRecord2" name="isRecord" class="text1">
						        <input type="hidden" id="id2" name="id1" class="text1">
						        <input type="hidden" id="status2" name="status1"   class="text1">
						        <input type="hidden" id="source2" name="source"  value="1" class="text1">
								 <input type="hidden" id="isOnekeySend2" name="isOnekeySend"  value="1">
								 <input type="hidden" id="paymentType2" name="paymentType"  value="3">
								 
								 <input type="hidden" id="companyRegion2" name="companyRegion"  class="text1">
								 <input type="hidden" id="companyQy2" name="companyQy"  class="text1">
								 <input type="hidden" id="type2" name="type"  class="text1">
								 <input type="hidden" id="noPayHqqId2" name="noPayHqq"  class="text1">
								 <input type="hidden" id="fanliPriceId2" name="fanliPrice"  class="text1">
								 <input type="hidden" id="sjSupplierCodeId2" name="sjSupplierCode"  class="text1">
							</form>
	            	 
				    </div>
				    <input type="button"  class="fabu_btn" id="saveupdate" onclick="saveupdate()" value="保存修改1" ></input>
				    <input type="button"  class="fabu_btn" onclick="go()" value="返回" ></input>
					<p class="blank30"></p>
					
			</div>
	
		</div>
		
	
		<!-- 右边 end -->
	</div>
	<p class="blank30"></p>
		   
	</div>
	<!-- 触发 table 1 -->
	<div class="alert_c" id="skuDiv" style="display:none;">
	  <div class="bg"></div>
		<div class="wrap">
			<div class="pic"></div>
			<div class="box1">
				<div id="boxtitle">
					<h2>选择上级企业编号</h2>
				</div>
				<div class="xia">
					<form id="SearchFrom"  action="" method="post" >
						<p class="p1" style="text-align: center;">
							<strong>企业代码：</strong><input type="text" id="serSupplierCodeId"/>
							<strong>企业名称：</strong><input type="text" id="serSupplierNameId"/>
						</p>
					</form>
				</div>
				<div id="suppliernametext" style="display: block;">
					<button type="button" onclick="hideShow();">退出</button>
					<button href="#" id="czhi" onclick="resetfm()">重置</button>
					<button type="button" onclick="GoPage();">查询</button>
				</div>
			</div>
			<div class="box2" id="skubox">
			
			</div>
			<c:if test="${empty page.result }">
				<div class="box3">
					<button type="button" class="bt1" id="supplierclick" onclick="loadSku()">确定</button>
				</div>
			</c:if>
		</div>
	</div>
	<div class="clear"/>
	   <iframe name="downloadFileIframe" style="display:none"></iframe>
       </body>
       <script type="text/javascript">
       function go(){
     		 window.location.href = "../user/checklistLine?source=1";	
     		}
      
 
      
         $(function(){
        	
       		var category='${category}';
       		var ind_a='';
       		if(category.length>0){
       			ind_a=${category};	
       		}
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
      			};
        	var indStr='';
			if($('#mercgr').val().length>0){
				 ind_flag_arr=$('#mercgr').val().split(",");
			}
			for(var i in ind_flag_arr){
				indStr+=','+ind_a[ind_flag_arr[i]];
			}
			indStr=indStr.substring(1)?indStr.substring(1):'';
			$('#mer-cgr').val(indStr);
		    //字数限制
        	$("#reason").keyup(function(){
	        	   var len = $(this).val().length;
	        	   if(len > 1000){
	        	    $(this).val($(this).val().substring(0,500));
	        	    alert("字数不能超过500字!");
	
	        	   };
        	  });

         });

		function checkSuccessBtn(id,status,isRecord){
			
			$.dialog.confirm('您确定审核通过吗？', function(){
				updateCheck(id,status,isRecord);
			}, function(){
				console.info("取消");
			});
		}

	   function checkFailureBtn(id,status,isRecord){
		   $.dialog.confirm('您确定审核不通过吗？', function(){
			   updateCheck1(id,status,isRecord);
		   }, function(){
			   console.info("取消");
		   });
	   }

         function updateCheck(id,status,isRecord){
     		if(status==2&&$("#reason").val()==''){
     			alert("请填写审核失败原因!");
     			return;
     		}else{

					var reason=$("#reason").val();
					if(reason==''){
						reason="审核通过";
					}
 					var pid_array = new Array();
 					pid_array.push('id='+id);
 					pid_array.push('reason='+reason);
 				$.ajax({
 	 					 type: "POST",
 				         data: pid_array.join("&"),
 				         url: "../user/updateCheckLineOK",
 				         dataType:'json',
 			         success: function (result) {
 			        	 if(result == '1'){
 			        		 tipMessage("审核成功!",function(){
 			        	  		 window.location.href = "../user/checklistLine?source=1";
 			        		 });
 			        	 }
 			        	 if(result == '0'){
 			        		alert("服务调用异常！");
 			        	 }
 			        	 if(result == '-1'){
  			        		alert("用户信息异常！");
  			        	 };
 			        	 
 			          }
		 	    });
   			};
		}
         function updateCheck1(id,status,isRecord){
     		if(status==2&&$("#reason").val()==''){
     			alert("请填写审核失败原因!");
     			return;
     		}else{
//      			var info = $('#xsname option:selected').val();
//      			if(null==info|| info=='' || undefined ==info){
//      				alert("请选择供应商类型!");
//          			return;
//      	}
				var reason=$("#reason").val();
				var pid_array = new Array();
				pid_array.push('id='+id);
				pid_array.push('reason='+reason);
 				$.ajax({
 					type: "POST",
			         data: pid_array.join("&"),
			         url: "../user/updateCheckLineNO",
			         dataType:'json',
 			         success: function (result) {
 			        	 if(result == '1'){
 			        		 tipMessage("审核成功!",function(){
 			        	  		 window.location.href = "../user/checklistLine?source=1";
 			        		 });
 			        	 }
 			        	 if(result == '0'){
 			        		alert("服务调用异常！");
 			        	 }
 			        	 if(result == '-1'){
  			        		alert("用户信息异常！");
  			        	 };
 			        	 
 			          }
		 	    });
   			};
		}
    </script>
  	<script type="text/javascript">
  		function modifyInfo(supplierId){
  			if($("#companyLevelModifyId").val() == '修改'){
  				$("#companyLevelModifyId").val('保存');
  				$("#companyLevelId").removeAttr('disabled');
  			}else{
	  			var levelValue = $("#companyLevelId").val().trim();
	  			var oldLevelValue = $("#companyLevelOldId").val().trim();
	  			if(levelValue == null || levelValue == ''){
	  				alert("内容不能为空!");
	  				return;
	  			}else if(levelValue == oldLevelValue){
	  				alert("内容没有修改，请修改后再保存!");
	  				return;
	  			}
	  			
	  			$.ajax({
			         type: "GET",
			         url: "../user/modifyInfo?supplierId="+supplierId+"&newLevelVal="+levelValue,
			         dataType:'json',
			         success: function (result) {
			        	 if(result == 1){
			        		 alert("修改成功");
			        		 window.location.href = "../user/checklistLine?source="+1;
			        	 }else{
			        		 alert("修改失败");
			        	 }
			          }
		 	    });
  			}
  			
  		}
  		function modifyCompanyQy(supplierId){
  			if($("#companyQyModifyId").val() ==  '修改'){
  				$("#companyQyModifyId").val('保存');
  				$("#companyQyId").removeAttr('disabled');
  			}else{
	  			var oldCompanyQyId = $("#oldCompanyQyId").val().trim();
	  			var newCompanyQy = $("#companyQyId").val().trim();
	  			if(newCompanyQy == null || newCompanyQy == ''){
	  				alert("内容不能为空!");
	  				return;
	  			}else if(oldCompanyQyId == newCompanyQy){
	  				alert("内容没有修改，请修改后再保存!");
	  				return;
	  			}
	  			
	  			$.ajax({
			         type: "GET",
			         url: "../user/modifyInfo?supplierId="+supplierId+"&newCompanyQy="+newCompanyQy,
			         dataType:'json',
			         success: function (result) {
			        	 if(result == 1){
			        		 alert("修改成功");
			        		 window.location.href = "../user/checklistLine?source="+1;
			        	 }else{
			        		 alert("修改失败");
			        	 }
			          }
		 	    });
  			}
  		}
  		
  		function creatAccountBtn(id,status,isRecord){
			$.dialog.confirm('您确定创建账号吗？', function(){
				creatAccount(id,status,isRecord);
			}, function(){
				console.info("取消");
			});
		}
  		//创建账号
  		function creatAccount(id,status,isRecord){
  			var noPayHqqVal = $("#noPayHqqId").val().trim();
  			var fanliPriceVal = $("#fanliPriceId").val().trim();
  			if((noPayHqqVal == null || noPayHqqVal == '') && (fanliPriceVal == null || fanliPriceVal == '')){
  				alert('创建账号时,未付红旗券和激活购买商品金额 不能同时为空!');
  				return false;
  			}
			var companyQy = $("#companyQyId").val();
			var companyRegion = $("#companyRegionId").val();
			var type = $("#typeId").val();
			var sjSupplierCodeVal = $("#sjSupplierCodeId").val();
			if(sjSupplierCodeVal == null || sjSupplierCodeVal == ''){
				if(type == 0){
					sjSupplierCodeVal = 'SQ69';		//众聚商城非自营企业
				}else if(type == 1){
					sjSupplierCodeVal = 'SQ37';		//众聚商城子公司
				}else if(type == 2){
					sjSupplierCodeVal = 'SQ89';		//众聚商城连锁企业
				}else if(type == 3){
					sjSupplierCodeVal = 'SQ79';		//众聚商城连锁子公司
				}else if(type == 4){
					sjSupplierCodeVal = 'SQ59';		//众聚商城自营企业
				}else if(type == 5){
					sjSupplierCodeVal = 'SQ99';		//众聚商城项目产业
				}
			}
			
			$("#companyQy2").val(companyQy);
			$("#companyRegion2").val(companyRegion);
			$("#type2").val(type);
			$("#sjSupplierCodeId2").val(sjSupplierCodeVal);

			var onekeySend_select = $('input[name="isOnekeySend_select"]:checked ').val();
			var paymentType_select = $('input[name="paymentType_select"]:checked ').val();

   			$("#supplyType2").val(51);		//供应商类型默认给51
			$("#id2").val(id);
			$("#status2").val(status);
			$("#isRecord2").val(isRecord);
			$("#isOnekeySend2").val(onekeySend_select);
			$("#paymentType2").val(paymentType_select);
			$("#noPayHqqId2").val(noPayHqqVal);
			$("#fanliPriceId2").val(fanliPriceVal);

   			var source = $("#source1").val();
			var fmdata = $("#createForm").serialize();
				$.ajax({
			         type: "POST",
			         data: fmdata+"&"+Math.random(),
			         url: "../user/createAccount",
			         dataType:'json',
			         success: function (result) {
			        	 if(result == '1'){
			        		 tipMessage("创建账号成功!",function(){
			        	  		 window.location.href = "../user/viewInfo?source="+1+"&id2="+id;
			        		 });
			        	 }
			        	 if(result == '0'){
			        		alert("服务调用异常！");
			        	 }
			        	 if(result == '-1'){
 			        		alert("用户信息异常！");
 			        	 };
 			        	if(result == '-2'){
 			        		alert("该企业类型下企业个人账号范围已超！");
 			        	 };
			          }
	 	    });
  		}
  	</script>
  	<script type="text/javascript">
  		function noHqq(){
  			var noHqqVal = $("#noPayHqqId").val().trim();
  			var reg = '^[1-9]\\d*$';
  			if(!noHqqVal.match(reg)){
  				alert('不能为空或小数，请重新输入!');
  				$("#noPayHqqId").val('');
  				$("#fanliPriceId").removeAttr('disabled');
  				return false;
  			}else{
	  			$("#fanliPriceId").attr("disabled","disabled");
  			}
  			/* if((parseFloat(newFenHongVal) + parseFloat(num))>60000000){
  				alert('此用户分红额度已达上限!');
  				$("#fenHongId").val('');
  				return false;
  			} */
  		}
  		function fanliPrice(){
  			var fanliPriceVal = $("#fanliPriceId").val().trim();
  			var reg = '^[1-9]\\d*$';
  			if(!fanliPriceVal.match(reg)){
  				alert('不能为空或小数，请重新输入!');
  				$("#fanliPriceId").val('');
  				$("#noPayHqqId").removeAttr('disabled');
  				return false;
  			}else{
	  			$("#noPayHqqId").attr("disabled","disabled");
  			}
  		}
  	</script>
  	
  		<script type="text/javascript">
  	 //修改
  	 
  	             
/* salesDiscount                bankProvinceId  bankAreaId  accoutBankname
companyInfo   companyRegion companyQy */
    function saveupdate(){
      var  CONTEXTPATH  = $("#conPath").val();
 	   var supplierId='${fn:escapeXml(data.supplierId)}';
 	   var type=$('#typeId').val();
 	   var companyBizType=$('#companyType').val();
 	   var companyBizCategory=$('#category').val();
 	   var salesDiscount=$('#salesDiscount').val();
 	  var companyBizType=$('#companyType').val();
	   var companyBizCategory=$('#category').val();
	   var salesDiscount=$('#salesDiscount').val();
	   var companyRegion=$('#companyRegion').val();
	   var companyQy=$('#companyQy').val();
	   
	  var name=$('#name').val();
	   var nameJC=$('#nameJC').val();
	   var address=$('#address').val();
	   var registerAddress=$('#registerAddress').val();
	   var contact=$('#contact').val();
	   var contactTel=$('#contactTel').val();
	   var phone=$('#phone').val();
	   var email=$('#email').val();
	   var legalPerson=$('#legalPerson').val();
	   var legalPersonPhone=$('#legalPersonPhone').val();
	   var companyInfo=$('#companyInfo').val();
	   
	   var legalPersonCardNo=$('#legalPersonCardNo').val();
	   var businessLicenseNo=$('#businessLicenseNo').val();
	   var accountNo=$('#accountNo').val();
	   var accountName=$('#accountName').val();
	   
	  
	   
	   var province=$('#province').val();
	   var city=$('#city').val();
	   var country=$('#country').val();
	   
	   
	   var bank=$('#bank').val();
	   var bankProvinceId=$('#bankProvinceId').val();
	   var bankAreaId=$('#bankAreaId').val();
	   var accoutBankno=$('#accoutBankno').val();
	  
	 
	  
 	   var pro_array  = new Array();
 	  if(salesDiscount!=""){
			pro_array.push("salesDiscount="+salesDiscount);
		}else{
			 alert("折扣不能为空");
		   return;
		}
 	 if(companyBizType!=""){
			pro_array.push("companyBizType="+companyBizType);
		}else{
			 alert("企业类型不能为空");
 		   return;
		}
 	 if(type!=""){
			pro_array.push("type="+type);
		}else{
			 alert("企业管理类型不能为空");
		   return;
		}
 	 if(companyBizCategory!=""){
 		pro_array.push("companyBizCategory="+companyBizCategory);
		}else{
		 alert("企业经营类目不能为空");
		   return;
	}
 	
	   
	   if(companyQy!=""){
		pro_array.push("companyQy="+companyQy);
	}
 	if(companyRegion!=""){
		pro_array.push("companyRegion="+companyRegion);
	}
 	 if(accoutBankno!=""){
			pro_array.push("accoutBankno="+accoutBankno);
		}
	  
	   
	   if(legalPersonCardNo!=""){
			pro_array.push("legalPersonCardNo="+legalPersonCardNo);
		}
	   if(businessLicenseNo!=""){
			pro_array.push("businessLicenseNo="+businessLicenseNo);
		}
	   if(accountName!=""){
			pro_array.push("accountName="+accountName);
		}
	   if(accountNo!=""){
			pro_array.push("accountNo="+accountNo);
		}
	   if(bank!=""){
			pro_array.push("bank="+bank);
		}
	   if(bankProvinceId!=""){
			pro_array.push("bankProvinceId="+bankProvinceId);
		}
	   if(bankAreaId!=""){
			pro_array.push("bankAreaId="+bankAreaId);
		}
	   if(name!=""){
		pro_array.push("name="+name);
	}else{
			 alert("注册名称不能为空");
		   return;
	}
	  if(nameJC!=""){
		pro_array.push("nameJC="+nameJC);
	}else{
			 alert("营业名称不能为空");
		   return;
	}
	  if(address!=""){
			pro_array.push("address="+address);
		   }else{
			 alert("营业地址不能为空");
		   return;
		}
	   if(registerAddress!=""){
		pro_array.push("registerAddress="+registerAddress);
	}else{
			 alert("注册地址不能为空");
		   return;
	}
	   if(contact!=""){
		pro_array.push("contact="+contact);
	}else{
		 alert("业务联系人不能为空");
		   return;
		}
	   
	   if(contactTel!=""){
			pro_array.push("contactTel="+contactTel);
		}else{
			 alert("业务联系人电话不能为空");
		   return;
		}
	   if(supplierId!=""){
			pro_array.push("supplierId="+supplierId);
		}
	  
	   if(phone!=""){
			pro_array.push("phone="+phone);
		}else{
			 alert("业务联系人手机不能为空");
		   return;
		}
	   if(legalPerson!=""){
		pro_array.push("legalPerson="+legalPerson);
	}else{
		 alert("法人名称不能为空");
		   return;
		}
	   if(legalPersonPhone!=""){
		pro_array.push("legalPersonPhone="+legalPersonPhone);
	}else{
		 alert("法人电话不能为空");
		   return;
		}
	  
	   if(companyInfo!=""){
			pro_array.push("companyInfo="+companyInfo);
		}else{
			 alert("企业信息不能为空");
		   return;
		}
	   if(email!=""){
			pro_array.push("email="+email);
		}else{
			 alert("邮箱不能为空");
		   return;
		}
	   if(province!=""){
		pro_array.push("provinceId="+province);
	   }
	   if(city!=null){
		pro_array.push("cityId="+city);
	   }
	   if(country!=null){
		pro_array.push("areaId="+country);
	   }
 	
	
	  
		/* $("#formcompany").submit(); */
				
				
 	   $.ajax({
 			type : "post", 
 			url : "../user/saveupdateLine", 
 			data:pro_array.join("&"),
 			dataType:"json",
 			success : function(msg) { 
 				alert("修改成功!");
 				window.location.reload();
 			},
 			error: function(jqXHR, textStatus, errorThrown){
 				alert("服务器异常!");
 			}
 		}); 
 	   
    } 	
    
	
  	</script>
  	<script type="text/javascript">
var jsSexMan = document.getElementById("companyType").value;
var category=document.getElementById("category").value;
</script>
</html>