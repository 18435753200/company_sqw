<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	  <title>商家后台管理系统-基本信息管理</title>
	<%@include file="/WEB-INF/views/zh/include/base.jsp"%>
     
      <link rel="stylesheet" type="text/css" href="${path}/css/zh/jiben.css"/>
      <link rel="stylesheet" type="text/css" href="${path}/js/validation/validationEngine.jquery.css"/>
      <link href="${path}/css/tooltip.css" rel="stylesheet" type="text/css" />
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
	    .t2 {
		    border: 1px solid #c8c8c8;
		    float: left;
		    font-size: 12px;
		    height: 23px;
		    line-height: 23px;
		    margin-top: 2px;
		    margin-left: 20px;
		    padding: 0 5px;
		    width: 175px;
		}
		#filespan {margin:0;}
		
	</style>
    </head>
    
    
    
	<body>
		<%@include file="/WEB-INF/views/zh/include/header.jsp"%>
			 <div class="wrap">
				<%@include file="/WEB-INF/views/zh/include/leftUser.jsp"%>
		<form  id="formID"  action="${path}/supplier/update" method="post" enctype="multipart/form-data">
		 <input type="hidden" name="token" value="${token}">
		<input id="language" type="hidden" name="language" value="${language}" />
		<input class="i1" type="hidden" name ="supplierId" value="${data.supplierId}"/>
		<input type="hidden" id="activeStatus" name="activeStatus" value="${fn:escapeXml(data.activeStatus)}"/>
		<div class="right f_l">
			<div class="title">

				<p class="c1">基本信息管理</p>
				<div class="clear"></div>
			</div>
			<div class="blank5"></div>
			<div class="cont">
				<ul class="ul_vertical">
					
					<li>
						<p class="p1"> 注册名称：</p>
						<input class="i1" id ="name" name="name" value="${fn:escapeXml(data.name)}" />
						<input type="hidden" id="name1" name="name1" value="${fn:escapeXml(data.name)}"/>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1"> 企业简称：</p>
						<input class="i1" id ="nameJC" name="nameJC" value="${fn:escapeXml(data.nameJC)}" />
						<input type="hidden" id="nameJC1" name="nameJC1" value="${fn:escapeXml(data.nameJC)}"/>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  营业地址 ：</p>
						<input class="i1" id ="address" name="address" value="${fn:escapeXml(data.address)}" />
						<input type="hidden" id="address1" name="address1" value="${fn:escapeXml(data.address)}"/>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  注册地址 ：</p>
						<input class="i1" id ="registerAddress" name="registerAddress" value="${fn:escapeXml(data.registerAddress)}" />
						<input type="hidden" id="registerAddress1" name="registerAddress1" value="${fn:escapeXml(data.registerAddress)}"/>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  业务联系人 ：</p>
						<input class="i1" id ="contact" name="contact" value="${fn:escapeXml(data.contact)}" />
						<input type="hidden" id="contact1" name="contact1" value="${fn:escapeXml(data.contact)}"/>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  业务联系人电话：</p>
						<input class="i1" id ="contactTel" name="contactTel" value="${fn:escapeXml(data.contactTel)}" />
						<input type="hidden" id="contactTel1" name="contactTel1" value="${fn:escapeXml(data.contactTel)}"/>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  业务联系人手机：</p>
						<input class="i1" id ="phone" name="phone" value="${fn:escapeXml(data.phone)}" />
						<input type="hidden" id="phone1" name="phone1" value="${fn:escapeXml(data.phone)}"/>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  邮箱：</p>
						<input class="i1" id ="email" name="email" value="${fn:escapeXml(data.email)}" />
						<input type="hidden" id="email1" name="email1" value="${fn:escapeXml(data.email)}"/>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  法人名称：</p>
						<input class="i1" id ="legalPerson" name="legalPerson" value="${fn:escapeXml(data.legalPerson)}" />
						<input type="hidden" id="legalPerson1" name="legalPerson1" value="${fn:escapeXml(data.legalPerson)}"/>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  法人电话：</p>
						<input class="i1" id ="legalPersonPhone" name="legalPersonPhone" value="${fn:escapeXml(data.legalPersonPhone)}" />
						<input type="hidden" id="legalPersonPhone1" name="legalPersonPhone1" value="${fn:escapeXml(data.legalPersonPhone)}"/>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  证件类型：</p>
						<select style="background-color:#EAEAE3" disabled="disabled" id="legalPersonCardType" name="legalPersonCardType"  ><option value="1" >身份证</option></select>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  法人证件号码：</p>
						<input class="i1" id ="legalPersonCardNo" name="legalPersonCardNo" value="${fn:escapeXml(data.legalPersonCardNo)}" />
						<input type="hidden" id="legalPersonCardNo1" name="legalPersonCardNo1" value="${fn:escapeXml(data.legalPersonCardNo)}"/>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  法人身份证正面：</p>
						<div class="item-ifo" style="width:320px;cursor: pointer;">
                        			<c:if test="${!empty data.idCardFront }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${data.idCardFront }')">查看    </span>
                        			</c:if>
                        </div>
                        			<c:if test="${empty data.idCardFront }">
                              		<span style="color:red;">未上传</span>
                        			</c:if>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  法人身份证反面：</p>
						<div class="item-ifo" style="width:320px;cursor: pointer;">
                        	<c:if test="${!empty data.idCardVerso }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${data.idCardVerso }')">查看    </span>
                        	</c:if>
                        </div>
                        	<c:if test="${empty data.idCardVerso }">
                              		<span style="color:red;">未上传</span>
                        	</c:if>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  企业营业执照：</p>
						<div class="item-ifo" style="width:320px;cursor: pointer;">
                           <c:if test="${!empty data.businessLicense }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${data.businessLicense }')">查看    </span>
                        	</c:if>
                        </div>
                        	<c:if test="${empty data.businessLicense }">
                              		<span style="color:red;">未上传</span>
                        	</c:if>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  营业执照号码：</p>
						<input class="i1" id ="businessLicenseNo" name="businessLicenseNo" value="${fn:escapeXml(data.businessLicenseNo)}" />
						<input type="hidden" id="businessLicenseNo1" name="businessLicenseNo1" value="${fn:escapeXml(data.businessLicenseNo)}"/>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  经营门头照片：</p>
						<div class="item-ifo" style="width:320px;cursor: pointer;">
                        	 <c:if test="${!empty fn:escapeXml(data.companyStoreLogo)}">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${fn:escapeXml(data.companyStoreLogo)}')">查看    </span>
                        	</c:if>
                        </div>
                        	<c:if test="${empty fn:escapeXml(data.companyStoreLogo)}">
                              		<span style="color:red;">未上传</span>
                        	</c:if>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  证明文件：</p>
						<div class="item-ifo" style="width:320px;cursor: pointer;">
                        	<c:if test="${!empty fn:escapeXml(data.companyLegitimacyUrl)}">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${fn:escapeXml(data.companyLegitimacyUrl)}')">查看/下载    </span>
                        	</c:if>
                        </div>
                        	<c:if test="${empty fn:escapeXml(data.companyLegitimacyUrl)}">
                              		<span style="color:red;">未上传</span>
                        	</c:if>
					</li>
					<li class="blank20"></li>
					<li>
					<p class="p1">详情文件：</p>
					<div class="item-ifo" style="width:320px;cursor: pointer;">
					    <c:if test="${not empty data.companyDetailedUrl }">
							<a  href="${path}/supplier/download?url=${data.companyDetailedUrl}" target="downloadFileIframe">详情文件</a>&nbsp;&nbsp;&nbsp;&nbsp;
				       </c:if>
				       </div>
				       <c:if test="${empty data.companyDetailedUrl }">
				       		<a href="JavaScript:void(0)" ><font color="red">未上传</font></a>
				       </c:if>
					</li>
				    <li class="blank20"></li>
					<li>
						<p class="p1">  企业信息介绍：</p>
						 <textarea style="outline:none;resize:none;" rows="6" cols="35"  name="companyInfo"      disabled="disabled" >${fn:escapeXml(data.companyInfo)}</textarea>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  开户行名称：</p>
						<input class="i1" id ="accoutBankname" name="accoutBankname" value="${fn:escapeXml(data.accoutBankname)}" />
						<input type="hidden" id="accoutBankname1" name="accoutBankname1" value="${fn:escapeXml(data.userName)}"/>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  结算账户名称：</p>
						<input class="i1" id ="accountName" name="accountName" value="${fn:escapeXml(data.accountName)}" />
						<input type="hidden" id="accountName1" name="accountName1" value="${fn:escapeXml(data.accountName)}"/>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  结算账号：</p>
						<input class="i1" id ="accountNo" name="accountNo" value="${fn:escapeXml(data.accountNo)}" />
						<input type="hidden" id="accountNo1" name="accountNo1" value="${fn:escapeXml(data.accountNo)}"/>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  个人账号姓名：</p>
						<input class="i1" id ="userName" name="userName" value="${fn:escapeXml(data.userName)}" />
						<input type="hidden" id="userName1" name="userName1" value="${fn:escapeXml(data.userName)}"/>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">  个人账号手机号 ：</p>
						<input class="i1" id ="userMobile" name="userMobile" value="${fn:escapeXml(data.userMobile)}" />
						<input type="hidden" id="userMobile1" name="userMobile1" value="${fn:escapeXml(data.userMobile)}"/>
					</li>
					<li class="blank20"></li>
					
					<!----------------------------------------------------------------------------------------------->
					
					<%-- <!----------------------------------------------------------------------------------------------->
				   	<li>
						<p class="p1"> 运营商级别：</p>
						<input class="i1 validate[required,custom[email]]" id="agentType" name="agentType"  value="${sa.name}"/>
					</li>
						<li class="blank20"></li>	
					<!----------------------------------------------------------------------------------------------->	
					<li>
						<c:if test="${data.activeStatus==-1 && data.status==0 }">
							请等待审核
						</c:if>
						<c:if test="${data.activeStatus==1 && data.status==1  }">
							审核通过
						</c:if>
						<c:if test="${data.activeStatus==-1 && data.status==2 }">
							审核未通过，请修改并重新提交
						</c:if>
						<c:if test="${data.activeStatus==-1 && data.status==1 }">
							创建账号
						</c:if>
						<c:if test="${data.activeStatus==-1 && data.status==5 }">
							账号冻结
						</c:if>
						
						<p class="p1">审核留言：</p>
						<textarea class="i1" id="checkFailReaId" name="checkFailReason" style="height: 80px;" rows="6" cols="35" readonly="readonly">${fn:escapeXml(data.checkFailReason)}</textarea>
					</li> --%>
					
				
			</div>
			 
		</div>
		  </form>
		 
		<!-- 右边 end -->
	</div>
	 <iframe name="downloadFileIframe" style="display:none">
	     
	 </iframe> 
	<p class="blank30"></p>
	
	<script type="text/javascript" src="${path}/js/user/zh/industry_func.js"></script>
	<script type="text/javascript">
	  //categories根据Id显示文本框的值
	  showText();
	</script> 
	<script type="text/javascript" src="${path}/js/commons/tooltip.js"></script>
	<%@include file="/WEB-INF/views/zh/include/last.jsp"%>
	<!-- 底部 end -->
     </body>
</html>