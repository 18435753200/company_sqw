<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta name="viewport" content="initial-scale=1,maximum-scale=1, minimum-scale=1">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui.css" />
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/center_about.css" />

<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/grzx_yjfk_css.css" />
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/user/common.js"></script>
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<title>关于我们</title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<!-- <style>
body{background:url(../commons/images/bg.jpg) repeat ;background-size: 100%;}
img{    max-width: 100%;			display: inline;}
.about{width: 100%;background-size: 100%;overflow: hidden;text-align: center;}
.about p{text-align: center;margin-top: 2em;font-size: 0.2rem;}
.right{color: #c4c5c7;font-size: 0.7rem;padding-top: 1rem;}
.content{margin-top: 2em;}
.content p{text-align: left;margin: 0rem 1.2rem 0rem 1rem;font-size:0.7em;color:#fff}
.aui-bar-nav {
    top: 0;
    line-height: 2.25rem;
    background-color: rgb(255,255,255,0);
    color: #ffffff;
}
.jh_bot_lg{
position:absolute;
    bottom: 5rem;
    width: 100%;
    margin: 0 auto;
    text-align: center;
}
.jh_bot_lg p{
    text-align: center;
    position: absolute;
        top:75%;
    width: 100%;
    font-size: 0.7rem;
    color: #fff;
}
</style> -->
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
    <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" href="javascript:;" onclick="goBack();">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">关于我们</div>
	</header>
	<section class="aui-content" style="    position: relative;   top: 2.3rem;   z-index: 2;">
        	<div class="aui-card-list">
           		<div class="aui-card-list-content">
                	<img src="../commons/img/grzxtp/logo_z.png">
            	</div>
            	<div class="aui-content" style="color: #666666;padding:5% 3%;">
            		北京安食众聚电子商务有限公司是一家集系统研发、农业产业资源整合的创新型综合服务企业; 将全新的营销理念与互联网技术相结合，以服务实业为导向，采用平台思维、共生思维、跨界思维等创新思维，通过资源整合、共享、叠加、O+O等方式，创建了一套独有的生态营销系统。
        		</div>
        		<div class="aui-content" style="color: #666666;padding:5% 3%;padding-top: 0;">
公司重点打造“众聚猫”品牌，众聚猫是为线下商家提供简单、安全、跨界、共享的专属“营销工具”。通过价值利益链将彼此关系进行跨界连接，形成多元利益一体化的现代互联互通网络体系，融合安食农业5.0产业打造的新零售平台。为消费者提供优质产品与服务，重塑供需价值链， 实现资源优化配置，回归商业本源。        		</div>
       		</div>
    	</section>
    	<section class="aui-content-padded" style="position: relative;top: 1rem;background: #FFFFFF;">
        	<h3 class="aui-h3">企业文化</h3>
        	<p class="aui-p">
            	激情、服务、创新、众聚
        	</p>
		</section>
		<section class="aui-content-padded" style="position: relative;top: 1rem;background: #FFFFFF;">
        	<h3 class="aui-h3">公司理念</h3>
        	<p class="aui-p">
            	众享赋能 聚力破茧
        	</p>
		</section>
		<section class="aui-content-padded" style="position: relative;top: 1rem;background: #FFFFFF;">
        	<h3 class="aui-h3">企业精神</h3>
        	<p class="aui-p">
            	忠诚团队，勇挑重担，信任默契，甘于奉献
        	</p>
		</section>
		<section class="aui-content-padded" style="position: relative;top: 1rem;background: #FFFFFF;">
        	<h3 class="aui-h3">创新精神</h3>
        	<p class="aui-cxjs">
            	持续创新是企业生存和发展的源泉和动力。创新技术、创新管理、创新制度、创新服务是我们永恒不变的追求。
        	</p>
		</section>
		<section class="aui-content-padded" style="position: relative;top: 1rem;margin-top: 0.5rem;background: #fff;">
        	<h3><b class="aui-print1">●</b>我们的经营理念</h3>
        	<h4 class="weYl">
            	求真，务实，共生，整合创新
        	</h4>
        	<h3><b class="aui-print1" style="color: #67f807;">●</b>我们的使命</h3>
        	<h4 class="weYl">
            	科技产业，服务实业
        	</h4>
        	<h3><b class="aui-print1" style="color: #e60012;">●</b>我们的愿景</h3>
        	<h4 class="weYl" style="    line-height: 1.4rem;border: 0;">
            	成为国内服务平台领军者，成为新时代、新经济、新实体的领航者
        	</h4>
		</section>
		<section class="aui-content" style="    position: relative;   top: 0.5rem;   z-index: 2;">
        	<div class="aui-card-list" style="    margin: 0;">
           		<div class="aui-card-list-content" style="padding: 5%;width: 70%;">
                	<img src="../commons/img/grzxtp/zjmkt.png">
            	</div>
            	<div class="aui-content" style="color: #666666;    padding-bottom: 7%;  text-align: center;font-weight: 400;">
            		北京安食众聚电子商务有限公司
        		</div>
       		</div>
    	</section>
<!-- 			<div class="wrap" id="myinfo">
				<div class="about">
					<div class="content">
						<p style="font-weight: 700;font-size: 0.7em;line-height:2em">
						众聚猫是北京安食众聚电子商务有限公司历时3年，经过不断的市场考察、市场调研、市场验证后推出的系统综合服务平台。平台是基于“共享优惠”为企业、商家和消费者打造的专属“营销、惠民工具”。</p>
						<p>北京安食众聚电子商务有限公司是一家集系统研发、资源整合、企业孵化、企业营销策划的创新科技综合服务企业。
						        公司依托于农业产业资源、技术团队、运营团队和农产品营销团队，在线上线下和其他合作企业共同发展、利用互联网平台通过线上商城提供质优价廉的农产品及其他优质产品；
						        并通过线下进行推广商家加盟，为线下商家提供营销工具，实现依托农业产业资源打造农产品直供直销与社群运营新零售平台。</p>
					</div>
					<div class="content">
						<p style="font-weight: 700;font-size: 0.8em;line-height:2em">公司地址：北京市朝阳区化工路59号院焦奥中心B座7层706室</p>
						<p style="font-weight: 700;font-size: 0.8em;line-height:2em">官方网址：www.zhongjumall.com</p>
					</div>
			<div class="jh_bot_lg"><img src="../commons/images/zjm_lg.png" style="width:60%"/><p style="text-align: center;" class="right">
				京ICP证号&nbsp;18030876<br>
				COPYRIGHT@2018<br>
				众聚猫商城版权所有 -->
				