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
      <link type="text/css" rel="stylesheet" href="${path}/css/regist.css"/>
<script type="text/javascript" src="${path}/js/commons/qrcode.js"></script>
       <style type="text/css">
		body{
		    color: #333333;
		    font-family: Arial,"宋体",Lucida,Verdana,Helvetica,sans-serif;
		    font-size: 12px;
		    line-height: 150%;
		    background: none repeat scroll 0 0 #fff;
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
						<p class="c1">我的二维码 </p>
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
				

					

<input id="qrUrl" type="hidden" value="${bizRode.rcodeImgTxt}" style="width:80%" /><br />
<div id="qrcode" style="width:100px; height:100px; margin-top:15px;margin:0 auto">
</div>
<script type="text/javascript">
var qrcode = new QRCode(document.getElementById("qrcode"), {
	width : 100,
	height : 100
});

function makeCode () {		
	var elText = document.getElementById("qrUrl");
	if (!elText.value) {
		elText.focus();
		return;
	}
	
	qrcode.makeCode(elText.value);
}

makeCode();

$("#qrUrl").
	on("blur", function () {
		makeCode();
	}).
	on("keydown", function (e) {
		if (e.keyCode == 13) {
			makeCode();
		}
	});
</script>







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
    <script type="text/javascript" src="${path}/js/user/validateRegExp.js"></script>
    <script type="text/javascript" src="${path}/js/user/validateprompt.js"></script>
    <script  type="text/javascript" src="${path}/js/user/drag.js"></script> 
   
   
	<%-- <script  type="text/javascript" src="${path}/js/user/industry_arr.js"></script>  --%>
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