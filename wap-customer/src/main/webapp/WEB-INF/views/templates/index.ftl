<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<link rel="shortcut icon" type="image/x-icon" href="${staticFile_s}/commons/img/favicon.ico" />
<link rel="stylesheet" href="${staticFile_s}/commons/css/base.css">
<title>众聚猫_畅购全球（zhongjumall.com）-100%海外正品保障，国内领先的海外购物网站</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta content="众聚猫，是一家专业的全球购海淘网站。精选世界畅销产品，经营品类包括食品、酒水饮料、母婴玩具、美妆个护、服饰鞋包、运动户外、家居家纺、时尚家电、钟表首饰等，快速清关、闪电送达，100%正品进口商品，让您放心购遍世界！" name="description">
<meta content="众聚猫、众聚猫网、全球购、海外购物网站、海淘网、海淘网站、限时抢购、海外代购、跨境电商、原装进口、跨境购物" name="keywords">


</head>

<body>
<#include "/top.ftl" encoding="utf-8">
<div class="wrap index-wrap">
    <div id="slider" class="swipe">
        <div class="swipe-wrap">
           <!-- <div style="background-color:#fb4a9b"><a href="#"><img src="${path}/commons/images/banner.jpg"></a></div>
            <div style="background-color:#fb4a9b"><a href="#"><img src="${path}/commons/images/banner.jpg"></a></div>
            <div style="background-color:#fb4a9b"><a href="#"><img src="${path}/commons/images/banner.jpg"></a></div>-->
            
            <#if carousel??>
            	<#list carousel as carousel>
            		<div style="background-color:#fb4a9b">
            			<#if carousel.type=1>
            				<a href="${path}/item/get/${carousel.targetUrl}">
            				<#elseif carousel.type=2>
							<a href="${path}/searchController/toSearchResult?keyword=${carousel.targetUrl}">
							<#elseif carousel.type=3>
							<a href="${path}/index/topic.html?topicUrl=${carousel.targetUrl}">
            			</#if>
            				<img src="${picUrl1}${carousel.content}">
            			</a>
            		</div>
				</#list>
            </#if>
            
            
        </div>
        <div class="slider-doc" id="slider-doc"></div>
    </div>
    <div id="content">
	    <!--<div class="brand-list">
	            <div class="brand-tit">今日特卖 · 每天早10点 晚8点上新</div>
	            <div class="brand">
	                <div class="brand-pic"><a href="list.html"><img src="${path}/commons/images/brand_01.jpg" /></a></div>
	                <div class="brand-msg">
	                    <p><span class="brand-discount">1.6</span>折起雾道CANTO MOTTO女装专</p>
	                </div>
	            </div>
	        </div>-->
	        <input type="hidden" id="totalPage" value="${totalPage}">
	        <input id="path" type="hidden" value="${path}">
        <div class="brand-list">
        	<#if contentList??>
            	<#list contentList as content>
            		<div class="brand">
            		 	<div class="brand-pic">
	            			<#if content.type=1>
	            				<a href="${path}/item/get/${content.targetUrl}">
	            				<#elseif content.type=2>
								<a href="${path}/searchController/toSearchResult?keyword=${content.targetUrl}">
								<#elseif content.type=3>
								<a href="${content.targetUrl}">
	            			</#if>
	            				<img src="${picUrl1}${content.content}">
	            			</a>
            			</div>
		               <#-- <div class="brand-msg">
		                    <p><span class="brand-discount">1.6</span>折起雾道CANTO MOTTO女装专</p>
		                </div>-->
            		</div>
				</#list>
       	 	</#if>
        </div>    
            
        <!--<div class="J_pager_more">
            <div class="more-btn loading"><img src="${path}/commons/img/loading-big.gif" />查看更多在售品牌..</div>
        </div>-->
    </div>
</div>
<!-- <%@include file="/WEB-INF/views/commons/footer.jsp" %> -->
<#include "/footer.ftl" encoding="utf-8">
<a href="#top" class="backtop show"><span class="icon i-backtop"></span></a>
<div id="J_Shade" onclick="$('#J_fixNav').removeClass('show');$('#J_fixBtn').removeClass('on');$(this).hide();"></div>
<div id="J_ccigplus" class="ccigplus">
    <div class="circle" id="J_fixNav">
        <div class="cpicons">
            <ul>
                <li class="more"><a href="${path}/searchController/toGlobalAndCategory"></a><span class="bg"></span></li>
                <li class="logis"><a href="${path}/customer/toMy"></a><span class="bg"></span></li>
                <li class="cart"><a href="${path}/cart/index"></a><span class="bg"></span></li>
                <li class="search"><a href="${path}/searchController/toSearchPage"></a><span class="bg"></span></li>
                <li class="cphome"><a href="${path}/index/index.html"></a><span class="bg"></span></li>
            </ul>
        </div>
    </div>
    <div class="cpbtn" id="J_fixBtn" onclick="$('#J_fixNav').toggleClass('show');$('#J_Shade').toggle();$(this).toggleClass('on');"><span class="icon i-ccig"></span></div>
</div>
<!-- js相关内容 -->
<script src="${staticFile_s}/commons/js/zepto.min.js"></script> 
<script src="${staticFile_s}/commons/js/swipe.min.js"></script> 
<script src="${staticFile_s}/commons/js/item/index_static.js"></script>
<script>
function slider(id) {
    var elm = document.getElementById(id);
    if (!elm) return ;
    var doc = document.getElementById(id+'-doc') ;
    var slides = elm.children[0].children;
    var temp = '';
    var index = 0 ;
    for (var i = 0 , len=slides.length; i < len ; i++) {
        temp += '<em '+(i==0?'class="on"':'')+'></em>';
    }
    doc.innerHTML = temp ;
    var docs = doc.querySelectorAll('em');
    new Swipe(elm,{
    speed: 400,
    auto : 3000,
    continuous: true,
    callback : function (pos) {
        docs[index].className='';
        docs[pos].className='on';
        index = pos ;
    }
    })
}
slider("slider");
</script>
</body>
</html>
