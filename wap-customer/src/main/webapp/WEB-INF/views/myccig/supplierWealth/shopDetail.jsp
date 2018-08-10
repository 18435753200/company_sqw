<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="height:100%">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>门店详情</title>
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<%@include file="/WEB-INF/views/commons/base.jsp"%>
<link rel="stylesheet" href="${staticFile_s}/commons/css/mui.min.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/shopDetail.css">
<script>
	!function(a,b){function h(){var b=a>540&&(b=540),c=b/15}var d,g,c=a.documentElement,e=a.querySelector('meta[name="viewport"]'),f="width=device-width,initial-scale=1,maximum-scale=1.0,user-scalable=no";e?e.setAttribute("content",f):(e=a.createElement("meta"),e.setAttribute("name","viewport"),e.setAttribute("content",f),console.log(c.firstElementChild),c.firstElementChild?c.firstElementChild.appendChild(e):(g=a.createElement("div"),g.appendChild(e),a.write(g.innerHTML))),h(),b.addEventListener("resize",function(){clearTimeout(d),d=setTimeout(h,300)},!1),b.addEventListener("pageshow",function(a){a.persisted&&(clearTimeout(d),d=setTimeout(h,300))},!1),"complete"===a.readyState?a.body.style.fontSize="16px":a.addEventListener("DOMContentLoaded",function(){a.body.style.fontSize="14px"},!1)}(document,window);
</script>
</head>
<body style="height:100%">
		<header id="header" class="mui-bar mui-bar-transparent">
			<span class="mui-icon mui-icon-back" onclick="javascript:history.back(-1)"></span>
			<h1 class="mui-title">门店详情</h1>
		</header>
		<div id="slider" class="mui-slider">
				<div class="mui-content-padded">
					<p><img src="//image01.zhongjumall.com/d1/${ssd.companyStoreLogo}" data-preview-src="" data-preview-group="3"></p >
				</div>
		</div>
		<section class="mui-content">
<%-- 			<c:if test="${empty ssd.logoImgurl}"> --%>
			<div class="dp_logo"><img src="${staticFile_s}/commons/img/qcwd/zjm_2.png" width="30%"  class="dp_logo_img"/></div>
			<%-- </c:if>
			<c:if test="${!empty ssd.logoImgurl}">
			<div class="dp_logo"><img src="//image01.zhongjumall.com/d1/${ssd.logoImgurl}" width="30%"  class="dp_logo_img"/></div>
			</c:if> --%>
			<h3 class="dp_name">${ssd.nameJC }</h3>	
			<ul class="mui-table-view mui-table-view-striped mui-table-view-condensed">
				<li class="mui-content" style="padding:0.5rem 1rem">
		            <div class="mui-table">
		                <div class="mui-table-cell mui-col-xs-10">
		                    <h5>主营业务：<b>${ssd.companyBizCategoryName }</b></h5>
		                    <h5>地区：<b>${ssd.provinceName}${ssd.cityName}${ssd.areaName }</b></h5>
		                </div>
		                 <a href="tel:${ssd.contactTel}">
		                	<div class="mui-table-cell mui-col-xs-2 mui-text-right">
		                    	<span class="mui-h5"><span class="mui-icon mui-icon-phone-filled" style="color: #007aff;"></span></span>
		                	</div>
		                </a>
		            </div>
				             <h5><span class="mui-icon mui-icon-location-filled" style="color: #007aff;"></span>&nbsp;|&nbsp;${ssd.sdLocationPoiaddress }</h5>
		            
		        </li>
		    </ul>
		</section>
		<div class="mui-card" style="margin-bottom: 0;">
				<div class="mui-card-header">经营特色</div>
				<div class="mui-card-content">
					<div class="mui-card-content-inner">
						${ssd.sdJyTS }
					</div>
				</div>
				<div class="mui-card-header" style="border-top:1px solid #d8d8d8;">经营环境</div>
				<div class="mui-card-content" style="border-bottom:1px solid #d8d8d8;    padding:10px 0 15px 0;;">
						<div class="m-pic-list" id="mysroll">
							<div class="picWrap">
								<c:if test="${isHavaJY!=1}">
										<a href="#">
										<div class="m-pic-list-picwrap"><img src="${staticFile_s}/commons/images/noUpload.png" class="lazy_img" data-preview-src="" data-preview-group="2"></div>
										</a>
										<a href="#">
										<div class="m-pic-list-picwrap"><img src="${staticFile_s}/commons/images/noUpload.png" class="lazy_img" data-preview-src="" data-preview-group="2"></div>
										</a>
										<a href="#">
										<div class="m-pic-list-picwrap"><img src="${staticFile_s}/commons/images/noUpload.png" class="lazy_img" data-preview-src="" data-preview-group="2"></div>
										</a>
										<a href="#">
										<div class="m-pic-list-picwrap"><img src="${staticFile_s}/commons/images/noUpload.png" class="lazy_img" data-preview-src="" data-preview-group="2"></div>
										</a>
										<a href="#">
										<div class="m-pic-list-picwrap"><img src="${staticFile_s}/commons/images/noUpload.png" class="lazy_img" data-preview-src="" data-preview-group="2"></div>
										</a>
										<a href="#">
										<div class="m-pic-list-picwrap"><img src="${staticFile_s}/commons/images/noUpload.png" class="lazy_img" data-preview-src="" data-preview-group="2"></div>
										</a>
								</c:if>
								
								<c:if test="${isHavaJY==1}">
									<c:forEach items="${ssd.attrList }" var="pic">
										<c:if test="${pic.attrType==1 }">
											<a href="#">
											<div class="m-pic-list-picwrap"><img src="//image01.zhongjumall.com/d1/${pic.attrValue}" class="lazy_img" data-preview-src="" data-preview-group="2"></div>
											</a>	
										</c:if>
								</c:forEach>
								</c:if>
								
							</div>
					</div>
				</div>
				<div class="mui-card-header">经营时间</div>
				<div class="mui-card-content">
					<div class="mui-card-content-inner">
						${ssd.sdJySJ }
					</div>
					</div>
			<div class="mui-card-header" style="border-top:1px solid #d8d8d8;">门店推荐</div>
				<div class="mui-card-content">
					<div class="mui-card-content-inner" style="padding: 2%;">
						<c:if test="${isHavaTJ!=1}">
							<div class="mui-imageviewer">
								<img src="${staticFile_s}/commons/images/noUpload.png" data-preview-src="" data-preview-group="1" class="qcwd_mdtj"/>
							</div>
							<div class="mui-imageviewer">
								<img src="${staticFile_s}/commons/images/noUpload.png" data-preview-src="" data-preview-group="1" class="qcwd_mdtj"/>
							</div>
							<div class="mui-imageviewer">
								<img src="${staticFile_s}/commons/images/noUpload.png" data-preview-src="" data-preview-group="1" class="qcwd_mdtj"/>
							</div>
							<div class="mui-imageviewer">
								<img src="${staticFile_s}/commons/images/noUpload.png" data-preview-src="" data-preview-group="1" class="qcwd_mdtj"/>
							</div>
						</c:if>
						<c:if test="${isHavaTJ==1}">
								<c:forEach items="${ssd.attrList }" var="pic">
									<c:if test="${pic.attrType==2 }">
										<div class="mui-imageviewer">
											<img src="//image01.zhongjumall.com/d1/${pic.attrValue}" data-preview-src="" data-preview-group="1" class="qcwd_mdtj"/>
										</div>
									</c:if>
								</c:forEach>
						</c:if>
					</div>
				</div>
		</div>
			<c:if test="${!empty ssd.sdLocationLat && !empty ssd.sdLocationLng && !empty ssd.sdLocationPoiaddress}">
				<div class="mui-card" style="margin:0;height:60%;">
							<div class="mui-card-header">位置</div>
							<div class="mui-card-content" style="height:100%">
								<table  style="width:100%;height:100%;  position: relative;">
									  <tr >
									    <td >
									<iframe id="lookLocation" width="100%" height="100%" frameborder=0   src="https://apis.map.qq.com/tools/poimarker?type=0&marker=coord:${ssd.sdLocationLat },${ssd.sdLocationLng };title:${ssd.sdLocationPoiname };addr:${ssd.sdLocationPoiaddress }&key=W6SBZ-SR5KW-WKQR6-OAFPW-S2IZQ-H6FBZ&referer=m.zhongjumall.com"></iframe>
									 	</td>
		 							 </tr>
								</table>
							</div>
				</div>
		</c:if>
			
		<script src="${staticFile_s}/commons/js/qcwd/mui.min.js"></script>
		<script src="${staticFile_s}/commons/js/mui/mui.zoom.js"></script>
		<script src="${staticFile_s}/commons/js/mui/mui.previewimage.js"></script>
		<script src="${staticFile_s}/commons/js/mui/pic_scroll.js" type="text/javascript"></script>
		<script type="text/javascript">
			mui.init({
				swipeBack: false //启用右滑关闭功能
			});
			mui('body').on('tap','a',function(){document.location.href=this.href;});//mui让a href 生效
			var slider = document.getElementById("slider");
			mui('.mui-input-group').on('change', 'input', function() {
				if (this.checked) {
					switch (this.value) {
						case 'static':
							document.getElementById("img1").className = "";
							document.getElementById("slider").classList.add("mui-hidden");
							break;
						case 'slider':
							document.getElementById("img1").className = "mui-hidden";
							if(slider.classList.contains("mui-hidden")){
								document.getElementById("slider").classList.remove("mui-hidden");
							}
							break;
					}
				}
			});

		mui.previewImage();
		
		
    
		</script>
<script type="text/javascript">
			var picHorScroll=(function(window,document){function prefixStyle(style){if(vendor===""){return style}style=style.charAt(0).toUpperCase()+style.substr(1);return vendor+style}var dummyStyle=document.createElement("div").style,vendor=(function(){var vendors="t,webkitT,MozT,msT,OT".split(","),t,i=0,l=vendors.length;for(;i<l;i++){t=vendors[i]+"ransform";if(t in dummyStyle){return vendors[i].substr(0,vendors[i].length-1)}}return false})(),cssVendor=vendor?"-"+vendor.toLowerCase()+"-":"",transform=prefixStyle("transform"),has3d=prefixStyle("perspective") in dummyStyle,hasTouch="ontouchstart" in window,hasTransform=!!vendor,hasTransitionEnd=prefixStyle("transition") in dummyStyle,translateZ=has3d?" translateZ(0)":"",resizeEvent="onorientationchange" in window?"orientationchange":"resize",startEvent=hasTouch?"touchstart":"mousedown",moveEvent=hasTouch?"touchmove":"mousemove",endEvent=hasTouch?"touchend":"mouseup",cancelEvent=hasTouch?"touchcancel":"mouseup",transitionEndEvent=(function(){if(vendor===false){return false}var transitionEnd={"":"transitionend","webkit":"webkitTransitionEnd","Moz":"transitionend","O":"oTransitionEnd","ms":"MSTransitionEnd"};return transitionEnd[vendor]})();var picHorScroll=function(el,options){this.wraps=typeof el=="string"?document.getElementById(el):el;this.wrap=this.wraps.children[0];this.slides=this.wrap.children;if(this.slides.length<=0){return}this.cellWidth=this.slides[0].offsetWidth;this.wrapwidth=this.slides.length*this.cellWidth;if(this.cellWidth*this.slides.length<this.wraps.offsetWidth){return}this.wraps.style.visibility="visible";this.movewidth=this.wrapwidth-this.wraps.clientWidth;this.init()};picHorScroll.prototype={way:0,newX:0,moveX:0,init:function(){var _this=this;_this.wrap.addEventListener(startEvent,function(e){_this.__start(e)},false);_this.wrap.addEventListener(moveEvent,function(e){_this.__move(e)},false);_this.wrap.addEventListener(endEvent,function(e){_this.__end(e)},false);window.addEventListener(resizeEvent,function(e){_this.movewidth=_this.wrapwidth-_this.wraps.clientWidth;_this.newX=0;_this.__pos(_this.newX)},false)},__pos:function(x,duration){if(duration){this.wrap.style.cssText=cssVendor+"transform:translate3d("+x+"px,0,0);"+cssVendor+"transition-duration:"+duration+"s"}else{this.wrap.style.cssText=cssVendor+"transform:translate3d("+x+"px,0,0);"}},__start:function(e){if(this.initiated){return}this.initiated=true;var _this=this;var point=hasTouch?e.touches[0]:e;this.startX=point.pageX;this.startY=point.pageY;this.pointX=point.pageX;this.pointY=point.pageY;this.stepsX=0;this.stepsY=0;this.directionX=0},__move:function(e){if(!this.initiated){return}var _this=this;var point=hasTouch?e.touches[0]:e,deltaX=point.pageX-this.pointX,deltaY=0;if(deltaX<0){this.way=true}if(deltaX>0){this.way=false}if(deltaX==0){return}_this.stepsX=Math.abs(deltaX);_this.stepsY=0;this.stepsX=_this.stepsX;this.stepsY=0;if((_this.newX==0)&&this.way==0){}if((this.movewidth==Math.abs(_this.newX))&&this.way==1){}_this.moveX=_this.newX+deltaX;e.preventDefault();_this.__pos(_this.moveX)},__end:function(e){var _this=this;if(!this.initiated){return}this.initiated=false;_this.newX=_this.moveX;var point=hasTouch?e.changedTouches[0]:e,dist=Math.abs(point.pageX-_this.startX);if(_this.way){if((Math.abs(_this.newX)>=this.movewidth)){_this.newX=-_this.movewidth}else{_this.newX=_this.newX}}else{if(_this.newX>=0){_this.newX=0}else{_this.newX=_this.newX}}var passed=Math.round(Math.abs(_this.newX)/this.cellWidth);var showNum=passed+Math.abs(Math.ceil(this.wraps.offsetWidth/this.cellWidth));if(showNum>_this.slides.length){showNum=_this.slides.length}for(var i=0;i<showNum;i++){var imgObj=_this.slides[i].querySelector("img");if(imgObj.getAttribute("$src")){imgObj.setAttribute("src",imgObj.getAttribute("$src"));imgObj.removeAttribute("$src")}}_this.__pos(_this.newX,0.3)}};return picHorScroll})(window,document);
			var picHorScroll = new picHorScroll('mysroll');
</script>
</body>
</html>