<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<title>专题-众聚猫手机版</title>
<link href="${staticFile_s}/commons/css/base.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="wrap">
    <div id="content">
        <div class="brand-list">
            <div class="brand">
                <div class="brand-pic">
                	<#if topicDate??>${topicDate}</#if>
                </div>
            </div>
        </div>
    </div>
</div>
<!--<#include "/footer.ftl" encoding="utf-8">-->
<a href="#top" class="backtop hide"><span class="icon i-backtop"></span></a> 
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
  var  go = function (type,key){
	 if ( typeof(window.ccigmall_b2c) != "undefined") {
		 window.ccigmall_b2c.intentToActivity(type,key);	
	 }else{

         if (type==1) {
         	window.location.href="${path}/item/get/"+key;
         }else{
			window.location.href="${path}/searchController/toSearchResult?keyword="+key;
         };
	 	
	 }
	  
  }

  $(window).scroll(function(){
  	$('a.backtop').removeClass('hide').addClass('show');
  });
</script>

</body>
</html>