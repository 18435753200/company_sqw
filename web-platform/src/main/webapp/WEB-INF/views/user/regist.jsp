<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%  
    String path = request.getContextPath();
	request.setAttribute("path",path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>UNICORN-注册</title>
    <link type="text/css" rel="stylesheet" href="${path}/commons/css/regist.css"/>
   <script type="text/javascript" src="${path}/commons/js/jquery-1.8.1.min.js"></script>
    <style type="text/css">
		body{
		    color: #333333;
		    font-family: Arial,"宋体",Lucida,Verdana,Helvetica,sans-serif;
		    font-size: 12px;
		    line-height: 150%;
		    background: none repeat scroll 0 0 #F2F2F2;
		    margin: 0;
		}
		#filespan{ font-size:12px; overflow:hidden; position:absolute}  
		#fileField{ position:absolute; z-index:100; margin-left:-180px; font-size:60px;opacity:0;filter:alpha(opacity=0); margin-top:-5px;}  
    </style>
     <script type="text/javascript" >
     function checkName(){
    	 var data="pin="+$("#regName").val();
    	// alert(data);
    	 $.ajax({
	         type: "POST",
	         dataType:"html",
	         url: "${path}/user/isPinEngaged",
	         data: data,
	         success: function (result) {
	        	 if(result>0){
	        		// $("#realname_error").removeClass("hide");
	        		 //$("#realname_error").addClass("error"); 
	        		 $("#realname_error").show();
	        	 }else{
	        		 $("#realname_error").hide();
	        	 }
	         }
	     });
     }
   
     </script>
  
</head>
<body>
    <div id="shortcut-2013">
    <div class="w">
                <ul class="fr lh">
                    <li id="loginbar" class="fore1" clstag="homepage|keycount|home2013|01b">
                      <a  href="?locale=zh_CN">中文 &nbsp; </a>    
		              <a href="?locale=en">English&nbsp;  </a>    
                    <spring:message code="welcome" />
                    <span>
                    <a href="${path}/user/loginUI"> [<spring:message code="login" />] </a>
                    <a class="link-regist" href="javascript:regist();">[<spring:message code="regist" />]</a>
                    </span>
                    </li>
                </ul>
            </div>
        </div>
<div class="w">
    <div id="logo"><a href="${path}/user/loginUI" clstag="passport|keycount|login|01">
    <img src="${path}/commons/images/login-logo.png" alt="<spring:message code="logo" />" width="170"
                                                     height="60"/></a>
     <b style="background: url('${path}/commons/images/regist-wel-<spring:message code="language" />.png') no-repeat scroll 0 0  !important;"></b></div>
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
        
        <form id="formcompany"  method="post" action="${path}/platform/regist" enctype="multipart/form-data" >
           <input id="language" type="hidden" name="language" value="<spring:message code="language" />"  />
           <input type="hidden" name="status" value="0" />
            <!-- <div class=" w1" id="entry">
                <div class="mc " id="bgDiv"> -->
                    <div class="form ">
                    <%--
                        <h3><spring:message code="supplier" /></h3>
                        <div class="item" id="select-regName">
                            <span class="label"><b class="ftx04">*</b><spring:message code="supplier.companyName" />：</span>
                            <div class="f1 item-ifo">
                            <c:out value="${name}"></c:out>
                                <input type="text" id="companyname" name="companyName" value="<c:out value='${name}'></c:out>" class="text"    tabindex="1"
                                       autocomplete="off"/>
                                
                                <label id="companyname_succeed" class="blank"></label>
                                <label id="companyname_error"></label>
                            </div>

                        </div>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="supplier.companyNature" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="companytype" name="companyNature" class="text" tabindex="2"/>
                                <label id="companytype_succeed" class="blank"></label>
                                <label id="companytype_error"></label>
                        
                            </div>
                        </div>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="supplier.companyArea" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="companyarea" name="countryArea" class="text" tabindex="3"/>
                                <label id="companyarea_succeed" class="blank"></label>
                                <label id="companyarea_error"></label>
            
                            </div>
                        </div>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="supplier.companyAddress" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="companyaddr" name="address" class="text" tabindex="4" />
                                <label class="blank" id="companyaddr_succeed"></label>
                                <label id="companyaddr_error"></label>
                         
                            </div>
                        </div>
                         <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="supplier.companyContact" />: </span>
                            <div class="item-ifo">
                                <input type="text" id="contactname" name="contact" class="text" tabindex="5"/>
                                <label id="contactname_succeed" class="blank"></label>
                                <label id="contactname_error"></label>                     
                            </div>
                        </div>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="supplier.companyPhone" />: </span>
                            <div class="item-ifo">
                                <input type="text" id="tele" name="phone" class="text" tabindex="6"/>
                                <label id="tele_succeed" class="blank"></label>
                                <label id="tele_error"></label>
                               
                            </div>
                        </div>

                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="supplier.companyMail" />: </span>
                            <div class="item-ifo">
                                <input type="text" id="mailbox" name="email" class="text" tabindex="7"/>
                                <label id="mailbox_succeed" class="blank"></label>
                                <label id="mailbox_error"></label>
                               
                            </div>
                        </div>
                        
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="supplier.companyTelOrFax" />: </span>
                            <div class="item-ifo">
                                <input type="text" id="contacttele" name="fax" class="text" tabindex="8" autocomplete="off"/>
                                <label id="contacttele_succeed" class="blank"></label>
                                <label id="contacttele_error"></label>
                            </div>
                        </div>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="supplier.companyPostcode" />: </span>
                            <div class="item-ifo">
                                <input type="text" id="postalcode" name="post" class="text" tabindex="9" autocomplete="off"/>
                                <label id="postalcode_succeed" class="blank"></label>
                                <label id="postalcode_error"></label>
                            </div>
                        </div>
                        <div class="item" id="" >
                            <span class="label"><b class="ftx04">*</b><spring:message code="supplier.companyFile" />: </span>
                            <div class="item-ifo" style="width:320px;">
                                <input id="textfield" class="text" type="text" name="textfield" readonly="readonly">
                                <span id="filespan">
                                <input id="fileField" class="file" type="file" onchange="document.getElementById('textfield').value=this.value" size="28" name="myfile">
                                <input class="btn" type="button" value="<spring:message code="upload" />" style="float: none;" tabindex="9" >
                               </span>
                                <label id="fileField_succeed" class="blank"></label>
                                <label id="fileField_error"></label>
                                                      
                            </div>
                        </div>
                         <div class="item" id="" >
                            <span class="label"><b class="ftx04">*</b><spring:message code="supplier.companyDetailFile" />:(<a href="${path}/supplier/download"><spring:message code="supplier.companyDetailFileTemplate" /></a> ) </span>
                            <div class="item-ifo" style="width:320px;">
                                <input id="textfield1" class="text" type="text" name="textfield" readonly="readonly">
                                <span id="filespan">
                                <input id="fileField1" class="file" type="file" onchange="document.getElementById('textfield1').value=this.value" size="28" name="myfile1">
                                <input class="btn" type="button" value="<spring:message code="upload" />" style="float: none;" tabindex="9" >
                               </span>
                                <label id="fileField1_succeed" class="blank"></label>
                                <label id="fileField1_error"></label>               
                            </div>
                        </div>
                        
                        
                        <h3><spring:message code="product" /></h3>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="product.categories" />: </span>
                            <div class="f1 item-ifo">
                                <input type="text" id="mer-cgr" name="categories" class="text"    tabindex="10"
                                       autocomplete="off"/>
                                
                                <label id="mer-cgr_succeed" class="blank"></label>
                                <label id="mer-cgr_error"></label>
                            </div>
                        </div>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="product.brand" />: </span>
                            <div class="f1 item-ifo">
                                <input type="text" id="mer-brand" name="brand" class="text"    tabindex="10"
                                       autocomplete="off"/>
                                
                                <label id="mer-brand_succeed" class="blank"></label>
                                <label id="mer-brand_error"></label>
                            </div>
                            </div>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="product.isRegister" />: </span>
                            <div class="f1 item-ifo">
                               <p style="float:left; margin-right:10px;">
                                   <input type="radio"  name="isRegister" value="1" checked="checked"/ style="margin:10px 5px 0 0;"> 是</p>
                               <p><input type="radio"  name="isRegister" value="0"  / style="margin:10px 5px 0 0;">否</p>
                                <label id="mer-islogin_succeed" class="blank"></label>
                                <label id="mer-islogin_error"></label>
                            </div>
                            </div>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="product.isOnsale" />：</span>
                             <div class="f1 item-ifo">
                               <p style="float:left; margin-right:10px;">
                                 <input type="radio"  name="isOnsale" value="1" checked="checked"/ style="margin:10px 5px 0 0;"> 是</p>
                               <p><input type="radio"  name="isOnsale" value="0"  / style="margin:10px 5px 0 0;">否</p>
                                <label id="mer-issale_succeed" class="blank"></label>
                                <label id="mer-issale_error"></label>
                            </div>
                            </div>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="product.skuNum" />：</span>
                            <div class="f1 item-ifo">
                                <input type="text" id="skunum" name="skuNum" class="text"    tabindex="10"
                                       autocomplete="off"/>
                                
                                <label id="skunum_succeed" class="blank"></label>
                                <label id="skunum_error"></label>
                            </div>
                            </div>
                        <h3><spring:message code="partner" /></h3>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="partner.companyName" />：</span>
                            <div class="f1 item-ifo">
                                <input type="text" id="chinacompanyname" name="p_companyName" class="text"    tabindex="10"
                                       autocomplete="off"/>
                                
                                <label id="chinacompanyname_succeed" class="blank"></label>
                                <label id="chinacompanyname_error"></label>
                            </div>
                        </div>
                        
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="partner.companyAddress" />：</span>
                            <div class="f1 item-ifo">
                                <input type="text" id="chinacompanyaddr" name="p_companyAddress" class="text"    tabindex="10"
                                       autocomplete="off"/>
                                
                                <label id="chinacompanyaddr_succeed" class="blank"></label>
                                <label id="chinacompanyaddr_error"></label>
                            </div>
                        </div>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="partner.companyContact" />：</span>
                            <div class="f1 item-ifo">
                                <input type="text" id="chinacontactname" name="p_companyContact" class="text"    tabindex="10"
                                       autocomplete="off"/>
                                
                                <label id="chinacontactname_succeed" class="blank"></label>
                                <label id="chinacontactname_error"></label>
                            </div>
                        </div>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="partner.companyTelOrFax" />：</span>
                            <div class="f1 item-ifo">
                                <input type="text" id="chinacontacttele" name="p_companyTel" class="text"    tabindex="10"
                                       autocomplete="off"/>
                                
                                <label id="chinacontacttele_succeed" class="blank"></label>
                                <label id="chinacontacttele_error"></label>
                            </div>
                        </div>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="partner.companyMail" />：</span>
                            <div class="f1 item-ifo">
                                <input type="text" id="chinamail" name="p_companyMail" class="text"    tabindex="10"
                                       autocomplete="off"/>
                                
                                <label id="chinamail_succeed" class="blank"></label>
                                <label id="chinamail_error"></label>
                            </div>
                        </div>
                        --%><h3><spring:message code="user" /></h3>
                        <div class="item" id="">
                            <span class="label"><b class="ftx04">*</b><spring:message code="user.loginName" />：</span>
                            <div class="item-ifo">
                                <input type="text" id="regName" name="loginName" class="text" tabindex="2"   autocomplete="off" onblur="checkName()"  />
                                
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
                                <input type="password" id="pwd" name="password"  class="text" tabindex="2" autocomplete="off" onpaste="return  false"/>
                                <!-- <label id="loginpwd_error" class="hide" >请输入您的密码</label>
                                <label id="loginpwd_error1" class="hide" >密码字符长度不少于六位</label> -->
                                <i class="i-pass"></i>
                                <label id="pwd_succeed" class="blank"></label>
                                <label id="pwd_error" class="hide"></label>
                                <span class="clr"></span>
                                <!-- <label id="pwdstrength" class="hide">
                                <span class="fl">安全程度：</span>
                                <b></b> -->
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
                                <input type="password" id="pwdRepeat" name="pwdRepeat" class="text" tabindex="2" autocomplete="off"/>
                                <i class="i-pass"></i>
                                <label id="pwdRepeat_succeed" class="blank"></label>
                                <label id="pwdRepeat_error"></label>
                            </div>
                        </div>  
                        <div class="item">
                            <span class="label">&nbsp;</span>
                            <input type="button" class="btn-img btn-regist-<spring:message code="language" />" id="registsubmit" value="<spring:message code="registsubmit" />" tabindex="25" >
                        </div>
                        <!-- <div class="item login-btn2013">
                            <span class="label"> </span>
                            <input type="button" class="btn-img btn-entry" id="registsubmit" value="注册" tabindex="8" clstag="passport|keycount|login|06"/>
                        </div> -->
                    </div>
               <!--  </div>
            </div> -->
        </form>
    </div>
</div>


  
  
<div class="w1">
    <div id="mb-bg" class="mb"></div>
</div>
<div class="w">
    <div id="footer-2013">
        <div class="copyright">Copyright</div>
    </div>
</div>
    <script type="text/javascript" src="${path}/commons/js/user/validateRegExp.js"></script>
    <script type="text/javascript" src="${path}/commons/js/user/validateprompt.js"></script>
</body>
   
</html>


