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
      <link href="${path}/css/tooltip.css" rel="stylesheet" type="text/css" />
      <script type="text/javascript" src="${path}/js/commons/tooltip.js"></script>
       <style type="text/css">
		body{
		    color: #333333;
		    font-family: Arial,"宋体",Lucida,Verdana,Helvetica,sans-serif;
		    font-size: 12px;
		    line-height: 150%;
/* 		    background: none repeat scroll 0 0 #F2F2F2; */
		    margin: 0;
		}
    </style>

     <script type="text/javascript" >
    
     $(function(){
    	var supplierId=${sr.supplierId};
    	var cbc=${sr.companyBizCategory};
    	//alert(cbc);
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
    			"${path}"+"/supplier/getCategory",
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
		
     	var mess= $("#message").val();
     	if(mess.length>0){
     		alert(mess);
     	}
//     	加载市级数据
    	/* 加载二级目录 */
    		$("#companyType").change(function(){
    		$("#category").empty();
    		var a=$("#companyType").val()
//     		var name="";
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
//         				}
        			   }
        	);
    	});

//      	加载省级数据
     		$.post(
        			"${path}"+"/supplier/getProvinceList",
        			   function(data){
        				$.each(eval(data),function(i,n){
        					$("#province").append("<option value='"+n.provinceid+"'>"+n.provincename+"</option>");
        				});	
        				//*****************************
        				$("#province").val('${sr.provinceId}');
        				
        				provinceChange('${sr.cityId}');
        				countryChange('${sr.areaId}');
        			   }
        	);
        	
     		$.ajaxSettings.async = false;
     		function provinceChange(id){
     			$("#city").empty();
    			$("#country").empty();
    			$.post(
            			"${path}"+"/supplier/getCityList",
            			   function(data){
            				var a=$("#province").val();
            				var city="<option value=''>市代理</option>";
    	        				$.each(eval(data),function(i,n){
    	        					if(a==n.provinceid){
    	        					city+="<option value='"+n.cityid+"'>"+n.cityname+"</option>";
    	        					}
    	        				});	
    	        				$("#city").append(city);
    	        				$("#city").val(id);
            			   }
            	);
        	
     		}
     		
     	 function countryChange(id){
     		$("#country").empty();
			$.post(
        			"${path}"+"/supplier/getCountryList",
        			   function(data){
        				var a=$("#city").val();
        				var country="<option value=''>区代理</option>";
        				$.each(eval(data),function(i,n){
        					if(a==n.cityid){
        					country+="<option value='"+n.countyid+"'>"+n.countyname+"</option>";
        					}
        				});
						$("#country").append(country);
						$("#country").val(id);
        			   }
        	);
     	 }
     	 
     	$.ajaxSettings.async = true;
     	
     	
     	
//  	加载市级数据
		/* 加载二级目录 */
			$("#province").change(function(){
			$("#city").empty();
			$("#country").empty();
			$.post(
     			"${path}"+"/supplier/getCityList",
     			   function(data){
     				var a=$("#province").val();
     				var city="<option value=''>市代理</option>";
	        				$.each(eval(data),function(i,n){
	        					if(a==n.provinceid){
	        					city+="<option value='"+n.cityid+"'>"+n.cityname+"</option>";
	        					}
	        				});	
	        				$("#city").append(city);
//      				}
     			   }
     	);
		});

			
//  	加载区域数据
			$("#city").change(function(){
				$("#country").empty();
				$.post(
	        			"${path}"+"/supplier/getCountryList",
	        			   function(data){
	        				var a=$("#city").val();
	        				var country="<option value=''>区代理</option>";
	        				$.each(eval(data),function(i,n){
	        					if(a==n.cityid){
	        					country+="<option value='"+n.countyid+"'>"+n.countyname+"</option>";
	        					}
	        				});
							$("#country").append(country);
	        			   }
	        	);
			});
     	
//			加载银行数据
			$.post(
					"${path}"+"/supplier/getBankList",
	     			   function(data){
	     				$.each(eval(data),function(i,n){
	     					$("#bank").append("<option value='"+n.bankCode+"'>"+n.bankName+"</option>");
	     				});	
	     			   }
	     	);
			
			
			
//			 加载该银行所有的省数据
		     $("#bank").change(function(){
					$("#bankProvinceId").empty();
					$("#bankAreaId").empty();
					$("#accoutBankname").empty();
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
		     
		     
		     
//		     加载该银行所在省的所有区的数据
		   $("#bankProvinceId").change(function(){
					$("#bankAreaId").empty();
					$("#accoutBankname").empty();
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
		     
//		     加载该所在省的所有区的所有银行的数据
		   $("#bankAreaId").change(function(){
				$("#accoutBankname").empty();
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
							$("#accoutBankname").append(accoutBankname);
	     			   },
	     			   "json"
	     	);
	    });  	       
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
						<p class="c1">查看商户 </p>
					<div class="clear"></div>
				</div>
				<div class="blank5"></div>
						<div class="w" id="regist">
    <div class="mc">


<form id="formcompany" method="post"
				action="${path}/supplier/save1" enctype="multipart/form-data">
				<input id="language" type="hidden" name="language"
					value="<spring:message code="language" />" /> <input type="hidden"
					name="status" value="0" /> <input type="hidden" id="message"
					name="message" value="${message}" />
				<!-- <div class=" w1" id="entry">
                <div class="mc " id="bgDiv"> -->
				<div class="form ">
              <input type="hidden" name="supplierId" id="supplierId" value="${ss.supplierId }"> 
              </br></br>
              
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
                        
             <!--                         代理地区：  省 市 区 -->
							<div class="item" id="">
								<span class="label"><b class="ftx04"></b><spring:message code="agent.area" />：</span>
								<div class="item-ifo" >
								
									<select style="background-color:#EAEAE3" name="province" id="province" disabled="disabled"  ></select>
									<select style="background-color:#EAEAE3" name="city" id="city" disabled="disabled"  ></select> 
									<select style="background-color:#EAEAE3" name="country" id="country" disabled="disabled"></select> 
								</div>
							</div><br/><br/><br/><br/>
                        <!--                         企业类型 -->
					<div class="item" id="companybizType">
						<span class="label"><b class="ftx04">*</b> <spring:message
								code="supplier.companyType" />：</span>
						<div class="item-ifo">
				
				<select id="companyType" name="companyType" style="background-color:#EAEAE3"disabled="disabled">
                   <option   value="1"<c:if test="${sr.companyBizType==1 }">selected="selected"</c:if> >企业</option> 
                   <option  value="2"<c:if test="${sr.companyBizType==2 }">selected="selected"</c:if>>个体工商户</option>   
                   <option  value="3"<c:if test="${sr.companyBizType==3 }">selected="selected"</c:if>>事业单位</option>      
 			   </select>
 						
                         <label id="contactname_succeed" class="blank"></label>
							<label id="contactname_error"></label>
						</div>
					</div>
				
					<!--                         企业经营类目 -->
					<div class="item" id="Category">
						<span class="label"><b class="ftx04">*</b> <spring:message
								code="supplier.bizCategory" />：</span>
						<div class="item-ifo">
							<select id="category" name="category" style="background-color:#EAEAE3" disabled="disabled">
                     <option id="" >请选择</option>
 
 </select>    <label id="contactname_succeed" class="blank"></label>
							<label id="contactname_error"></label>
						</div>
					</div>
					
                        
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
                        
                        

                        
                        
                        <!--  						   企业信息介绍 -->
						 <div class="item" id="">
                            <span class="label"><b class="ftx04"></b><spring:message code="supplier" />：</span>
                            <div class="item-ifo">
                                <textarea style="outline:none;resize:none;" rows="6" cols="35"  name="companyInfo"      disabled="disabled" >${sr.companyInfo }</textarea>
                            </div>
                        </div></br></br></br>
                        
               
                        
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
    <script type="text/javascript" src="${path}/js/user/validateRegExp.js"></script>
    <script type="text/javascript" src="${path}/js/user/validateprompt.js"></script>
    <script  type="text/javascript" src="${path}/js/user/drag.js"></script> 
   
   
	<%-- <script  type="text/javascript" src="${path}/js/user/industry_arr.js"></script>  --%>
	<script type="text/javascript">
// 	 var ind_a=${category};
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
// 		function in_array(needle, haystack) {
// 			if(typeof needle == 'string' || typeof needle == 'number') {
// 				for(var i in haystack) {
// 					if(haystack[i] == needle) {
// 							return true;
// 					}
// 				}
// 			}
// 			return false;
// 		}
	
	
	
 
/* window.onbeforeunload = function(){  
    //关闭窗口时自动退出  
    if(event.clientX>360&&event.clientY<0||event.altKey){     
        alert(parent.document.location);  
    }  
};    */    

	</script> 
	<script type="text/javascript" src="${path}/js/user/industry_func.js"></script>
		<!-- 底部 start -->
     	<%@include file="/WEB-INF/views/zh/include/last.jsp"%>
	<!-- 底部 end -->

   	</body>	
  
</html>