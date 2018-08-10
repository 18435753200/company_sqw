<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html style="height:100%">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>门店详情</title>
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="no">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link rel="stylesheet" href="${staticFile_s}/commons/css/mui.min.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/shopDetail.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/qcwdcss/dropify.min.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/qcwdcss/jyhj1.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/qcwdcss/jyhj2.css">
<script src="${staticFile_s}/commons/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>

<script>
!function(a,b){function h(){var b=a>540&&(b=540),c=b/15}var d,g,c=a.documentElement,e=a.querySelector('meta[name="viewport"]'),f="width=device-width,initial-scale=1,maximum-scale=1.0,user-scalable=no";e?e.setAttribute("content",f):(e=a.createElement("meta"),e.setAttribute("name","viewport"),e.setAttribute("content",f),console.log(c.firstElementChild),c.firstElementChild?c.firstElementChild.appendChild(e):(g=a.createElement("div"),g.appendChild(e),a.write(g.innerHTML))),h(),b.addEventListener("resize",function(){clearTimeout(d),d=setTimeout(h,300)},!1),b.addEventListener("pageshow",function(a){a.persisted&&(clearTimeout(d),d=setTimeout(h,300))},!1),"complete"===a.readyState?a.body.style.fontSize="16px":a.addEventListener("DOMContentLoaded",function(){a.body.style.fontSize="14px"},!1)}(document,window);
</script>
<style>
.sc_img{ 
    position: relative;
    top: 3rem;
    z-index: 8;
    font-size: 3rem;
    color: rgba(255, 255, 255, 1);
	font-weight:500;
    margin: 0;
    padding: 0;
    border-radius: 48px;
}
.add_shopimg {
    top: 2rem;
    margin: 10% 40%;
    width: 20%;
    position: absolute;
}
.mui-card {
    font-size: 14px;
    position: relative;
    overflow: hidden;
    margin: 10px 0;
    border-radius: 0; 
    background-color: #fff;
    background-clip: padding-box;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 1);
}
.up_scimg{    font-size: 1.8rem; font-weight: 400;    vertical-align: middle;    position: relative; top: 0.5rem;  z-index: 10;}
</style>
</head>
<body style="height:100%">
		<input type="hidden" id="staticFile_s" value="${staticFile_s}" />
		<input type="hidden" id="supplierId" value="${ssd.supplierId }">
		<header id="header" class="mui-bar mui-bar-transparent">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">门店详情</h1>
		</header>
		<div class="mui-content" style="    background-color: rgba(255,255,255,0.1);">
			<div class="mui-content-padded">
				 <p>
				 <span  id="photoThis" onclick="topPhotos()"class="mui-icon mui-icon-upload" style="font-size: 6rem;  position:absolute;  width: 100%; text-align: center;    padding: 1rem 0;
  border-left: 0;  top:7rem;  border-right: 0;    color: #fff;"><b class="up_scimg">点击上传图片</b></span>
					<input type="file" accept="image/*" hidden=true id="topPhoto" onchange="updatePhoto(this)" />
					<img src="//image01.zhongjumall.com/d1/${ssd.companyStoreLogo}" id="shopTop" data-preview-src="" data-preview-group="3"></p > 
			</div>
		</div>
		<section class="mui-content">
			<c:if test="${empty ssd.logoImgurl}">
			<div class="dp_logo"><img src="${staticFile_s}/commons/images/head3.png" width="30%"  class="dp_logo_img"/></div>
			</c:if>
			<c:if test="${!empty ssd.logoImgurl}">
			<div class="dp_logo"><img src="//image01.zhongjumall.com/d1/${ssd.logoImgurl}" width="30%"  class="dp_logo_img"/></div>
			</c:if>
			<h3 class="dp_name">${ssd.nameJC }<span class="mui-icon mui-icon-compose" onclick="updateForm(1)" style="float: right;    padding-right: 1rem;"></span></h3>	
			<ul class="mui-table-view mui-table-view-striped mui-table-view-condensed">
				<li class="mui-content" style="padding:0.5rem 1rem">
		            <div class="mui-table">
		                <div class="mui-table-cell mui-col-xs-10">
		                		<c:if test="${!empty ssd.mainBusiness }">
		                			 <h5>主营业务：<b>${ssd.mainBusiness }</b></h5>
		                		</c:if>
			                   	<c:if test="${!empty province && !empty city && !empty county}">
			                   	 <h5>地区：<b>${province.provincename}&nbsp;&nbsp;&nbsp;&nbsp;${city.cityname}&nbsp;&nbsp;&nbsp;&nbsp;${county.countyname }</b></h5>
			                   	</c:if>
			                    <h5>联系人：<b>${ssd.contact }</b></h5>
			                    <h5>电话：<b>${ssd.contactTel }</b></h5>
		                </div>
		            </div>
		            	<c:if test="${empty ssd.locationLat && empty ssd.locationLng}">
				             <h5><span class="mui-icon mui-icon-location-filled" style="color: #007aff;"></span>&nbsp;|&nbsp;${ssd.locationPoiaddress }</h5>
		            	</c:if>
		            	<c:if test="${!empty ssd.locationLat && !empty ssd.locationLng}">
				             <h5><span class="mui-icon mui-icon-location-filled" style="color: #007aff;"></span>&nbsp;${ssd.locationPoiaddress }</h5>
		            	</c:if>		            
		        </li>
		    </ul>
		</section>
		<div class="mui-card" style="margin-bottom: 0;">
			<div class="mui-card-header">经营特色<span class="mui-icon mui-icon-compose" onclick="updateForm(2)" style="float: right;"></span></div>
			<div class="mui-card-content">
				 <div class="mui-card-content-inner">${ssd.jyTS }</div>
			</div>
			
			<div class="mui-card-header" style="border-top:1px solid #d8d8d8;">经营环境</div>
			<div class="mui-card-content" style="border-bottom:1px solid #d8d8d8;    padding:10px 0 15px 0;">
				 <div class="m-pic-list" id="mysroll">
						<div class="picWrap">
								<section class="m-pic-list-picwrap">
									<form action="" name="form1" method="post">
										<div class="aui-photo aui-up-img clear">
											<section class="aui-file-up fl">
													 <c:if test="${!empty ssd.attrList }">
													 	<c:forEach items="${ssd.attrList }" var="pic">
															<c:if test="${pic.attrType eq 1 }">
																<a href="#">
																	<div class="m-pic-list-picwrap">
																		<span class="sc_img mui-icon mui-icon-close" onclick="remo(this,${pic.id})" ></span>
																		<img src="//image01.zhongjumall.com/d1/${pic.attrValue}" class="lazy_img" data-preview-src="" data-preview-group="4">
																	</div>
																</a>	
															</c:if>
														</c:forEach>
													 </c:if>
											</section>
											<!-- <input type="file" accept="image/*" hidden="true" name="file" id="file" class="file" value=""  multiple/> -->
											 <input accept="image/*" hidden="true" type="file" id="file"  onchange="photo(this)" class="dropify-fr"/>
										</div>
									</form>
								</section>
							</div>
				 </div>
				 <span  id="photoThis" onclick=aa()  class="mui-icon mui-icon-upload" style="font-size: 4rem;  position: relative;  width: 100%; text-align: center;    padding: 1rem 0;
    border: 1px solid #d8d8d8;    border-left: 0;    border-right: 0;    color: #636363;"><b class="up_scimg">点击上传图片</b></span>
			</div>
			
			<div class="mui-card-header">经营时间<span class="mui-icon mui-icon-compose" onclick="updateForm(3)" style="float: right;"></span></div>
			<div class="mui-card-content">
				 <div class="mui-card-content-inner">${ssd.jySJ }</div>
			</div>
			
			<div class="mui-card-header" style="border-top:1px solid #d8d8d8;">门店推荐</div>
			<div class="mui-card-content">
					<c:if test="${!empty ssd.attrList }">
					 	<c:forEach items="${ssd.attrList }" var="pic" varStatus="li">
							<c:if test="${pic.attrType eq 2 }">
								<a href=#">
									<div class="mui-imageviewer">
									    <span class="sc_img mui-icon mui-icon-close" onclick="remo(this,${pic.id})"></span>
										<img src="//image01.zhongjumall.com/d1/${pic.attrValue}" id="${pic.id}" class="qcwd_mdtj" data-preview-src="" data-preview-group="2">
									</div>
								</a>	
							</c:if>
						</c:forEach>
					 </c:if>
					
			</div>
			<div class="">
						 <input accept="image/*" hidden="true" type="file" id="input-file-french-2"  onchange="photoUp(this)" class="dropify-fr"/>
						 <span  id="photoThis" onclick="bb()"  class="mui-icon mui-icon-upload" style="font-size: 4rem;  position: relative;  width: 100%; text-align: center;    padding: 1rem 0;
    border: 1px solid #d8d8d8;    border-left: 0;    border-right: 0;    color: #636363;"><b class="up_scimg">点击上传图片</b></span>
						<%-- <img src="${staticFile_s}/commons/img/photoUp.png" id="photoThis" onclick="bb()" style="width:50%;margin: 0 25%;"/>  --%>
						
			</div>
		</div>
				<div class="mui-card" style="margin:0;height:60%">		
				 		<div class="mui-card-header" style="line-height: 2.4rem;">位置</div>
						<span  onclick="toSaveLocation()" class="mui-icon mui-icon-location-filled" style="position: absolute;right: 5rem; top: 1.2rem;    color: #007aff;" ></span>
						<span class="mui-icon mui-icon-compose" onclick="toSaveLocation()" id="toSaveLocation"  style="position: absolute; right: 2rem;top: 1.2rem;"></span>
					<div class="mui-card-content" style="height:100%">
					  		<c:if test="${!empty ssd.locationLat && !empty ssd.locationLng && !empty ssd.locationPoiaddress}">
					  			<table  style="width:100%;height:100%;  position: relative;">
									 <tr >
									  	  <td >
									<iframe id="lookLocation" width="100%" height="100%" frameborder=0   src="https://apis.map.qq.com/tools/poimarker?type=0&marker=coord:${ssd.locationLat },${ssd.locationLng };title:${ssd.locationPoiname };addr:${ssd.locationPoiaddress }&key=W6SBZ-SR5KW-WKQR6-OAFPW-S2IZQ-H6FBZ&referer=m.zhongjumall.com"></iframe>
										</td>
		 							 </tr>
								</table>
							</c:if>
					</div>    
				</div>
			</div>
		<script src="${staticFile_s}/commons/js/updateShop/updateShop.js?version=1.0.0" type="text/javascript"></script>
		<script src="${staticFile_s}/commons/js/qcwd/mui.min.js"></script>
		<script src="${staticFile_s}/commons/js/mui/mui.zoom.js"></script>
		<script src="${staticFile_s}/commons/js/mui/mui.previewimage.js"></script>
		<script src="${staticFile_s}/commons/js/qcwd/up_jyhj.js"></script>
		<script type="text/javascript">
			mui.init({
				swipeBack: false //启用右滑关闭功能
			});
			
			//mui('body').on('tap','a',function(){document.location.href=this.href;});//mui让a href 生效
			
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
		
		//跳转到地图定位页面
		function toSaveLocation(){
			var supplierId='${ssd.supplierId}';
			var nameJC='${ssd.nameJC}';
			window.location.href="${staticFile_s}/supplier/toSaveLocation?supplierId="+supplierId+"&nameJC="+nameJC;
		}
		
		
</script>
<script src="${staticFile_s}/commons/js/mui/pic_scroll.js" type="text/javascript"></script>
<script type="text/javascript">
			var picHorScroll=(function(window,document){function prefixStyle(style){if(vendor===""){return style}style=style.charAt(0).toUpperCase()+style.substr(1);return vendor+style}var dummyStyle=document.createElement("div").style,vendor=(function(){var vendors="t,webkitT,MozT,msT,OT".split(","),t,i=0,l=vendors.length;for(;i<l;i++){t=vendors[i]+"ransform";if(t in dummyStyle){return vendors[i].substr(0,vendors[i].length-1)}}return false})(),cssVendor=vendor?"-"+vendor.toLowerCase()+"-":"",transform=prefixStyle("transform"),has3d=prefixStyle("perspective") in dummyStyle,hasTouch="ontouchstart" in window,hasTransform=!!vendor,hasTransitionEnd=prefixStyle("transition") in dummyStyle,translateZ=has3d?" translateZ(0)":"",resizeEvent="onorientationchange" in window?"orientationchange":"resize",startEvent=hasTouch?"touchstart":"mousedown",moveEvent=hasTouch?"touchmove":"mousemove",endEvent=hasTouch?"touchend":"mouseup",cancelEvent=hasTouch?"touchcancel":"mouseup",transitionEndEvent=(function(){if(vendor===false){return false}var transitionEnd={"":"transitionend","webkit":"webkitTransitionEnd","Moz":"transitionend","O":"oTransitionEnd","ms":"MSTransitionEnd"};return transitionEnd[vendor]})();var picHorScroll=function(el,options){this.wraps=typeof el=="string"?document.getElementById(el):el;this.wrap=this.wraps.children[0];this.slides=this.wrap.children;if(this.slides.length<=0){return}this.cellWidth=this.slides[0].offsetWidth;this.wrapwidth=this.slides.length*this.cellWidth;if(this.cellWidth*this.slides.length<this.wraps.offsetWidth){return}this.wraps.style.visibility="visible";this.movewidth=this.wrapwidth-this.wraps.clientWidth;this.init()};picHorScroll.prototype={way:0,newX:0,moveX:0,init:function(){var _this=this;_this.wrap.addEventListener(startEvent,function(e){_this.__start(e)},false);_this.wrap.addEventListener(moveEvent,function(e){_this.__move(e)},false);_this.wrap.addEventListener(endEvent,function(e){_this.__end(e)},false);window.addEventListener(resizeEvent,function(e){_this.movewidth=_this.wrapwidth-_this.wraps.clientWidth;_this.newX=0;_this.__pos(_this.newX)},false)},__pos:function(x,duration){if(duration){this.wrap.style.cssText=cssVendor+"transform:translate3d("+x+"px,0,0);"+cssVendor+"transition-duration:"+duration+"s"}else{this.wrap.style.cssText=cssVendor+"transform:translate3d("+x+"px,0,0);"}},__start:function(e){if(this.initiated){return}this.initiated=true;var _this=this;var point=hasTouch?e.touches[0]:e;this.startX=point.pageX;this.startY=point.pageY;this.pointX=point.pageX;this.pointY=point.pageY;this.stepsX=0;this.stepsY=0;this.directionX=0},__move:function(e){if(!this.initiated){return}var _this=this;var point=hasTouch?e.touches[0]:e,deltaX=point.pageX-this.pointX,deltaY=0;if(deltaX<0){this.way=true}if(deltaX>0){this.way=false}if(deltaX==0){return}_this.stepsX=Math.abs(deltaX);_this.stepsY=0;this.stepsX=_this.stepsX;this.stepsY=0;if((_this.newX==0)&&this.way==0){}if((this.movewidth==Math.abs(_this.newX))&&this.way==1){}_this.moveX=_this.newX+deltaX;e.preventDefault();_this.__pos(_this.moveX)},__end:function(e){var _this=this;if(!this.initiated){return}this.initiated=false;_this.newX=_this.moveX;var point=hasTouch?e.changedTouches[0]:e,dist=Math.abs(point.pageX-_this.startX);if(_this.way){if((Math.abs(_this.newX)>=this.movewidth)){_this.newX=-_this.movewidth}else{_this.newX=_this.newX}}else{if(_this.newX>=0){_this.newX=0}else{_this.newX=_this.newX}}var passed=Math.round(Math.abs(_this.newX)/this.cellWidth);var showNum=passed+Math.abs(Math.ceil(this.wraps.offsetWidth/this.cellWidth));if(showNum>_this.slides.length){showNum=_this.slides.length}for(var i=0;i<showNum;i++){var imgObj=_this.slides[i].querySelector("img");if(imgObj.getAttribute("$src")){imgObj.setAttribute("src",imgObj.getAttribute("$src"));imgObj.removeAttribute("$src")}}_this.__pos(_this.newX,0.3)}};return picHorScroll})(window,document);
			var picHorScroll = new picHorScroll('mysroll');
</script>
</body>
</html>