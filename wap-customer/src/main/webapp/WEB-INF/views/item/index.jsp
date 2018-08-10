<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<title><spring:message code="title_index" /></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="<%=path%>/commons/css/base.css">
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>

<%@include file="/WEB-INF/views/commons/top.jsp" %>
<div class="wrap index-wrap">
    <div id="slider" class="swipe">
        <div class="swipe-wrap">
            <div style="background-color:#fb4a9b"><a href="#"><img src="<%=path%>/commons/images/banner.jpg"></a></div>
            <div style="background-color:#fb4a9b"><a href="#"><img src="<%=path%>/commons/images/banner.jpg"></a></div>
            <div style="background-color:#fb4a9b"><a href="#"><img src="<%=path%>/commons/images/banner.jpg"></a></div>
            <div style="background-color:#fb4a9b"><a href="#"><img src="<%=path%>/commons/images/banner.jpg"></a></div>
            <div style="background-color:#fb4a9b"><a href="#"><img src="<%=path%>/commons/images/banner.jpg"></a></div>
            <div style="background-color:#fb4a9b"><a href="#"><img src="<%=path%>/commons/images/banner.jpg"></a></div>
        </div>
        <div class="slider-doc" id="slider-doc"></div>
    </div>
    <div id="content">
        <div class="brand-list">
            <div class="brand-tit">今日特卖 · 每天早10点 晚8点上新</div>
            <div class="brand">
                <div class="brand-pic"><a href="list.html"><img src="<%=path%>/commons/images/brand_01.jpg" /></a></div>
                <div class="brand-msg">
                    <p><span class="brand-discount">1.6</span>折起雾道CANTO MOTTO女装专</p>
                </div>
            </div>
            <div class="brand">
                <div class="brand-pic"><a href="list.html"><img src="<%=path%>/commons/images/brand_02.jpg" /></a></div>
                <div class="brand-msg">
                    <p><span class="brand-discount">1.6</span>折起雾道CANTO MOTTO女装专</p>
                </div>
            </div>
            <div class="brand">
                <div class="brand-pic"><a href="list.html"><img src="<%=path%>/commons/images/brand_03.jpg" /></a></div>
                <div class="brand-msg">
                    <p><span class="brand-discount">1.6</span>折起雾道CANTO MOTTO女装专</p>
                </div>
            </div>
            <div class="brand">
                <div class="brand-pic"><a href="list.html"><img src="<%=path%>/commons/images/brand_04.jpg" /></a></div>
                <div class="brand-msg">
                    <p><span class="brand-discount">1.6</span>折起雾道CANTO MOTTO女装专</p>
                </div>
            </div>
            <div class="brand">
                <div class="brand-pic"><a href="list.html"><img src="<%=path%>/commons/images/brand_05.jpg" /></a></div>
                <div class="brand-msg">
                    <p><span class="brand-discount">1.6</span>折起雾道CANTO MOTTO女装专</p>
                </div>
            </div>
        </div>
        <div class="J_pager_more">
            <div class="more-btn loading"><img src="<%=path%>/commons/img/loading-big.gif" />查看更多在售品牌..</div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<a href="#top" class="backtop show"><span class="icon i-backtop"></span></a>
<div id="J_Shade" onclick="$('#J_fixNav').removeClass('show');$('#J_fixBtn').removeClass('on');$(this).hide();"></div>
<div id="J_ccigplus" class="ccigplus">
    <div class="circle" id="J_fixNav">
        <div class="cpicons">
            <ul>
                <li class="more"><a href="<%=path%>/searchController/toGlobalAndCategory"></a><span class="bg"></span></li>
                <li class="other"><a href="#"></a><span class="bg"></span></li>
                <li class="logis"><a href="#"></a><span class="bg"></span></li>
                <li class="individ"><a href="#"></a><span class="bg"></span></li>
                <li class="cart"><a href="#"></a><span class="bg"></span></li>
                <li class="search"><a href="<%=path%>/searchController/toSearchPage"></a><span class="bg"></span></li>
                <li class="cphome"><a href="#"></a><span class="bg"></span></li>
            </ul>
        </div>
    </div>
    <div class="cpbtn" id="J_fixBtn" onclick="$('#J_fixNav').toggleClass('show');$('#J_Shade').toggle();$(this).toggleClass('on');"><span class="icon i-ccig"></span></div>
</div>
<!-- js相关内容 -->
<script src="<%=path%>/commons/js/zepto.min.js"></script> 
<script src="<%=path%>/commons/js/swipe.min.js"></script> 
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
