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
      <script type="text/javascript" src="${path}/js/commons/tooltip.js"></script>
         <script type="text/javascript" src="${path }/js/agent/agentMp.js"></script>
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
						<p class="c1">查看合伙人 </p>
					<div class="clear"></div>
				</div>
				<div class="blank5"></div>
						<div class="w" id="regist">
    <div class="mc">
    
<!--     使用表单提交数据,保证数据的安全性 -->
    <form id="f" action="" method="post" ><input type="hidden" id="parid" name="parentId"/></form>

        <form id="formcompany"  method="post" action="${path}/supplier/agentRegist" enctype="multipart/form-data" >
           <input id="language" type="hidden" name="language" value="<spring:message code="language" />"  />
           <input type="hidden" name="status" value="0" />
           <input type="hidden" id="message" name="message" value="${message}" />
                    <div class="form " style="border:1px solid #e2e9ee;width:842px">
                        <h3>合伙人信息</span></h3>
                     	
                     	<input type="hidden" value="${parentId }"  name="parentId"/>
                        
                        
<!--                         		姓名 -->
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
								<div class="item-ifo" > 
									<select id="provinceId" style="background-color:#EAEAE3"  disabled="disabled"><option value="" >请选择</option></select>
									<select id="cityId" style="background-color:#EAEAE3"  disabled="disabled"></select> 
									<select id="areaId" style="background-color:#EAEAE3" disabled="disabled"></select> 
								</div>
							</div><br/><br/><br/><br/>
                        
                        
                        
                         <!--                         地址 -->
                          <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b>地址：</span>
                            <div class="item-ifo">
                                <input type="text" id="registerAddress" name="registerAddress" class="text"  tabindex="5"   value="${sr.nameJC }" disabled="disabled"/>
                            </div>
                        </div>
                        
                           <!--                         工作场景照片： -->
                        <div class="item" id="" >
                        <span class="label">工作场景照片：</span>
                        <div class="item-ifo" style="width:320px;cursor:pointer;">
                        	 <c:if test="${!empty sr.companyStoreLogo }">
                              		<span style="color:red;"  onclick="window.open('Http://image01.zhongjumall.com/'+'${sr.companyStoreLogo }')">查看    </span>
                        	</c:if>
                        </div>
                        	<c:if test="${empty sr.companyStoreLogo }">
                              		<span style="color:red;">未上传</span>
                        	</c:if>
                    </div></br>
                        
                    

                           	 <div class="item">
                            <span class="label">&nbsp;</span>
                             <input type="button" style="background-color: red; color: white;width: 227px;height: 35px;cursor: pointer; font-size:20px;border-radius:5px;border:1px  solid red" onclick="returnAgentPage('${sr.parentId }')" value="返回页面"> 
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