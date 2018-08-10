<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-status-bar-style" content=" black ">
<meta name="format-detection" content="telephone=no">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>商户信息</title>
<link href="${staticFile_s}/commons/css/login.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/public.css" type="text/css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/mui.min.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/merchant.css">
<script src="${staticFile_s}/commons/js/jquery-1.8.0.min.js"></script>
<meta name="apple-mobile-web-app-title" content="众聚猫">
<meta name="format-detection" content="telephone=no">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<script src="${staticFile_s}/commons/js/user/login.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/user/des.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<style>

.header {
    z-index: 10;
    width: 100%;
    height: 1.7rem;
    background: #e6101a;
    display: flex;
    position: fixed;
    border: 0;
}
.content {
    width: 100%;
    color: #4c4d4d;
    position: absolute;
    top: 1.6rem;
}
.selectClass {
	border: 0;
	padding-left: 0.5rem;
	background-color: #fff;
	width: 60%;
	overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
}

/* .selectClass1 {
	border: 0;
	padding-left: 0.5rem;
	background-color: #fff;
	width: 60%;
} */
option{
color:#999999;}
.list2_text li {
    width: 4rem;
    text-align: center;
    /* margin: 0 auto; */
}
.img_wp {
	width: 40%;
    height: 5rem;
    border: 1px dashed lightgray;
    margin: 0 auto;
}
.img_intro {
    margin-top: 0.7rem;
}


.list2_text li {
    width: 6rem;
    text-align: center;
    margin: 0 auto;
    line-height: 1.5rem;
        font-size: 0.6rem;
    color: #8f8f94;
}
.s {
    width: 100%;
    padding: 0.5rem 0;
    background: white;
    position: relative;
    top: .2rem;
    text-align: center;
    height:2.2rem
}
.btn {
    width: 100%;
    text-align: center;
    width: 100%;
    background: #fff;
    text-align: center;
    margin-top: 0.2rem;
    padding: 0.3rem 0;
}
.content .btn a {
    display: inline-block;
    height: 1.75rem;
    width: 86.6%;
    background: #ffffff;
    line-height: 1.75rem;
    color: #ff0303;
    border-radius: 2rem;
    font-size: .7rem;
}
.header span{float: right;
    margin: 0;
    padding: 0;
    color: #fff;
    font-size: 0.6rem;
    line-height: 1.7rem;
        letter-spacing: 0;
        margin-left: 2.1rem;}
        	.mui-preview-image.mui-fullscreen {
				position: fixed;
				z-index: 20;
				background-color: #000;
			}
			.mui-preview-header,
			.mui-preview-footer {
				position: absolute;
				width: 100%;
				left: 0;
				z-index: 10;
			}
			.mui-preview-header {
				height: 44px;
				top: 0;
			}
			.mui-preview-footer {
				height: 50px;
				bottom: 0px;
			}
			.mui-preview-header .mui-preview-indicator {
				display: block;
				line-height: 25px;
				color: #fff;
				text-align: center;
				margin: 15px auto 4;
				width: 70px;
				background-color: rgba(0, 0, 0, 0.4);
				border-radius: 12px;
				font-size: 16px;
			}
			.mui-preview-image {
				display: none;
				-webkit-animation-duration: 0.5s;
				animation-duration: 0.5s;
				-webkit-animation-fill-mode: both;
				animation-fill-mode: both;
			}
			.mui-preview-image.mui-preview-in {
				-webkit-animation-name: fadeIn;
				animation-name: fadeIn;
			}
			.mui-preview-image.mui-preview-out {
				background: none;
				-webkit-animation-name: fadeOut;
				animation-name: fadeOut;
			}
			.mui-preview-image.mui-preview-out .mui-preview-header,
			.mui-preview-image.mui-preview-out .mui-preview-footer {
				display: none;
			}
			.mui-zoom-scroller {
				position: absolute;
				display: -webkit-box;
				display: -webkit-flex;
				display: flex;
				-webkit-box-align: center;
				-webkit-align-items: center;
				align-items: center;
				-webkit-box-pack: center;
				-webkit-justify-content: center;
				justify-content: center;
				left: 0;
				right: 0;
				bottom: 0;
				top: 0;
				width: 100%;
				height: 100%;
				margin: 0;
				-webkit-backface-visibility: hidden;
			}
			.mui-zoom {
				-webkit-transform-style: preserve-3d;
				transform-style: preserve-3d;
			}
			.mui-slider .mui-slider-group .mui-slider-item img {
				width: auto;
				height: auto;
				max-width: 100%;
				max-height: 100%;
			}
			.mui-android-4-1 .mui-slider .mui-slider-group .mui-slider-item img {
				width: 100%;
			}
			.mui-android-4-1 .mui-slider.mui-preview-image .mui-slider-group .mui-slider-item {
				display: inline-table;
			}
			.mui-android-4-1 .mui-slider.mui-preview-image .mui-zoom-scroller img {
				display: table-cell;
				vertical-align: middle;
			}
			.mui-preview-loading {
				position: absolute;
				width: 100%;
				height: 100%;
				top: 0;
				left: 0;
				display: none;
			}
			.mui-preview-loading.mui-active {
				display: block;
			}
			.mui-preview-loading .mui-spinner-white {
				position: absolute;
				top: 50%;
				left: 50%;
				margin-left: -25px;
				margin-top: -25px;
				height: 50px;
				width: 50px;
			}
			.mui-preview-image img.mui-transitioning {
				-webkit-transition: -webkit-transform 0.5s ease, opacity 0.5s ease;
				transition: transform 0.5s ease, opacity 0.5s ease;
			}
			@-webkit-keyframes fadeIn {
				0% {
					opacity: 0;
				}
				100% {
					opacity: 1;
				}
			}
			@keyframes fadeIn {
				0% {
					opacity: 0;
				}
				100% {
					opacity: 1;
				}
			}
			@-webkit-keyframes fadeOut {
				0% {
					opacity: 1;
				}
				100% {
					opacity: 0;
				}
			}
			@keyframes fadeOut {
				0% {
					opacity: 1;
				}
				100% {
					opacity: 0;
				}
			}
			p img {
				max-width: 100%;
				height: auto;
			}
</style>
</head>

<body>
<input type="hidden" id="staticFile_s" value="<%=path %>" />
	<header class="header">
		<a class="go_back" href="#"onclick="javascript:history.back(-1)"></a>
		<div class="title">商家信息</div>
		<c:if test="${!empty activeStatus}">
			<span><a id="bj" href="#" onclick="bj()" style="color:#fff">编辑</a></span>
		</c:if>
			
	</header>
	<div class="content"> 
		<form class="form2" id="form" method="post"
			enctype="multipart/form-data"
			action="${staticFile_s}/mallRegister/regist">
			<!--基本资料填写-->
			<ul class="list1">
			<input type="hidden" id="rcodetxt" name="rcodetxt" value="${rcodetxt}" readonly="readonly"/>
			<input type="hidden" id="rcid" name="rcid" value="${rcid}" />
				<li><span style="color: red">商家状态</span><input type="text" style="color: red" value="${status}"readonly="readonly" ></li>
				<c:if test="${status eq '审核未通过'}">
				<li><span style="color: red">未通过原因</span><input type="text" style="color: red" value="${checkFailReason}"readonly="readonly" ></li>
				</c:if>
				<li><span>商家注册名称</span><input type="text" value="${supplier.name}"readonly="readonly" ></li>
				<li><span>商家营业名称</span> <input type="text" value="${supplier.nameJC}"readonly="readonly" ></li>
				<li class='ssq'><span>省市区</span>
				<div class="sel">
				     <select disabled="disabled" >    
						 <option >${province.provincename}</option>
					 </select>
					<select disabled="disabled">    
						  <option >${city.cityname}</option>
                    </select>
                    <select disabled="disabled" >    
                          <option >${county.countyname}</option>   
                    </select>  
				</div>
				</li>
				<li><span>商家营业地址</span> <input type="text" value="${supplier.address}"readonly="readonly" ></li>
				<li><span>商家注册地址</span> <input type="text" value="${supplier.registerAddress}"readonly="readonly" ></li>
				<li><span>营业执照编码</span> <input type="text" value="${supplier.businessLicenseNo}"readonly="readonly" ></li>
				<li><span>企业类型</span><div class="sel"> <select  disabled="disabled" style="width:100%">    
						<c:if test="${supplier.companyBizType==1}">
                        	 <option value="1" title="2">企业</option>   
						</c:if>
						<c:if test="${supplier.companyBizType==2}">
                        	 <option value="2" title="3">个体工商户</option>   
						</c:if>
						<c:if test="${supplier.companyBizType==3}">
                       	     <option value="3" title="4">事业单位</option>   
						</c:if>
                        </select>
                        </div>
                        </li>	
				<li><span>企业经营类目</span>
					<div class="sel">
						<select disabled="disabled" style="width:100%">    
                        	<option >${teSectors.sectorname}</option>   
                    	</select>
                    </div></li>
				<li><span>法定代表人</span> <input type="text"value="${supplier.legalPerson}"></li>
				<li><span>身份证号码</span> <input type="text"value="${supplier.legalPersonCardNo}"></li>
				<li><span>法人联系电话</span> <input type="text"value="${supplier.legalPersonPhone}"></li>
				<li><span>电子邮箱</span> <input type="text" value="${supplier.email}"></li>
				<li><span>业务联系人</span> <input type="text" value="${supplier.contact}"></li>
				<li><span>联系电话</span> <input type="text" value="${supplier.contactTel}"></li>
				<li><span>联系手机</span> <input type="text" value="${supplier.phone}"></li>
				<li><span>开户行</span>
				<div class="sel">
				 <select class="selectClass1" style="width:100%">
						<option>${bankBranch.bankBranchName }</option>
				</select>
				</div></li>
				<li><span>银行账户名称</span> <input type="text" value="${supplier.accountName}"></li>
				<li><span>银行帐号</span> <input type="text" value="${supplier.accountNo}"></li>
				<li><span>商户折扣</span> <input type="text" value="${discount.salesDiscount}"></li>
			</ul>
			</form>
			
			<!--客户认证上传各种证书-->
			<div class="updip">
				<div class="uptext">资格认证</div>
				<ul class="list2">
					<li>
						<div>
							<img id="xmTanImg"  src="//image01.zhongjumall.com/d1/${supplier.businessLicense}"  style="height: 100%; width: 100%;"   data-preview-src=""  data-preview-group="1" />
						</div>
					</li>
					<li>
						<div>
							<img id="mpImg" src="//image01.zhongjumall.com/d1/${supplier.companyStoreLogo}" style="height: 100%; width: 100%;"   data-preview-src=""  data-preview-group="1" />
						</div>
					</li>
					<li>
						<div>
							<img id="yykzmzImg" src="//image01.zhongjumall.com/d1/${supplier.bankAccountPic}" style="height: 100%; width: 100%;"   data-preview-src=""  data-preview-group="1" />
						</div>
					</li>
				</ul>
				<ul class="list2_text">
					<li>营业执照</li>
					<li>门头照片</li>
					<li>银行卡正面照</li>
				</ul>
			</div>
			<div class="upid">
				<div class="uptext">身份认证</div>
				<div class="id_img_wp">
					<div class="img_wp" >
						<img  src="//image01.zhongjumall.com/d1/${supplier.idCardFront}"   data-preview-src=""  data-preview-group="1" />
						<p class="img_intro">身份证正面照</p>
					</div>
					<div class="img_wp">
						<img  src="//image01.zhongjumall.com/d1/${supplier.idCardVerso}"   data-preview-src=""  data-preview-group="1"/>
						<p class="img_intro">身份证反面照</p>
					</div>
				</div>
			</div>
	</div>
</body>
</html>
<script src="${staticFile_s}/commons/js/rem.js"></script>
<script src="${staticFile_s}/commons/js/qcwd/mui.min.js"></script>
<script src="${staticFile_s}/commons/js/mui/mui.previewimage.js"></script>
<script src="${staticFile_s}/commons/js/mui/mui.zoom.js"></script>
<script>
	mui.previewImage();
	
	function bj(){
		var url="/mallRegister/toUpdateMall";
		window.location.href=url;
	}
</script>