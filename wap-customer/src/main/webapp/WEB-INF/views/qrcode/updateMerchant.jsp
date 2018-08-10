
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-status-bar-style" content=" black ">
<meta name="format-detection" content="telephone=no">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>商户信息</title>
<link href="${staticFile_s}/commons/css/login.css" rel="stylesheet"
	type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/public.css"
	type="text/css">
		<link rel="stylesheet" href="${staticFile_s}/commons/css/mui.min.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/merchant.css">
<script src="${staticFile_s}/commons/js/jquery-1.8.0.min.js"></script>
<meta name="apple-mobile-web-app-title" content="众聚猫">
<meta name="format-detection" content="telephone=no">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<script src="${staticFile_s}/commons/js/user/des.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<style>
body{font-family: "微软雅黑"}
.header {
    z-index: 10;
    width: 100%;
    height: 1.8rem;
    background: #e6101a;
    display: flex;
    position: fixed;
    border:0;
}
.content {
    width: 100%;
    color: #4c4d4d;
    position: absolute;
    top: 1.7rem;
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
.selectClass1 {
	border: 0;
	padding-left: 0.5rem;
	background-color: #fff;
	width: 60%;
}
select {
	-webkit-appearance: none;
	-webkit-tap-highlight-color: #fff;
	outline: 0;
	background-color: #fff;
	color:#999999;
}
option{
color:#999999;}
.list2 li {
    list-style: none;
    width: 40%;
    height: 5rem;
    border: 1px dashed lightgray;
    background-size: cover;
    font-size: .6rem;
   /*  margin: 0 auto; */
}
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
        font-size: .6rem;
    color: #9c9c9c;
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
    text-align: center;
    width: 100%;
    background: #fff;
    text-align: center;
    padding: 0.5rem 1rem;
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
.fdj{
    width: 18%;
    display: inline;
    height: 20%;
    transform: rotate(1deg);
}
.header span{float: right;
    margin: 0;
    padding: 0;
    color: #fff;
    font-size: 0.8rem;
    line-height: 2.2rem;
        margin-left: 1.6rem;}
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
.chxps{
	position: absolute;
	margin: 0 auto;  
    height: 140px;  
    width: 18px; 
	}
</style>
</head>
<body>
<!-- 隐藏域 -->
<input type="hidden" id="staticFile_s" value="<%=path%>" />
<input type="hidden" id="supCId" value="${supplier.cityId}" />
<input type="hidden" id="countyId" value="${supplier.areaId}" />
<input type="hidden" id="countyType" value="${supplier.companyBizType}" />
<input type="hidden" id="sectorId" value="${supplier.companyBizCategory}" />
<input type="hidden" id="bankcode" value="${bankBranchByCode.bankCode}" />
<input type="hidden" id="provinceCode" value="${bankBranchByCode.provinceCode}" />
<input type="hidden" id="cityCode" value="${bankBranchByCode.cityCode}" />
<input type="hidden" id="bankBranchcode" value="${bankBranchByCode.bankBranchCode}" />
	<header class="header">
		<a class="go_back" href="#"onclick="javascript:history.back(-1)"></a>
		<div class="title">商家信息</div>
	</header>
	<div class="content">
		<form class="form2" id="form" method="post"
			enctype="multipart/form-data"
			action="${staticFile_s}/mallRegister/updateMall">
			<!--基本资料填写-->
			<ul class="list1">
			<input type="hidden" id="rcodetxt" name="rcodetxt" value="${supplier.parentId}" />
			<input type="hidden" id="rcid" name="rcid" value="${rcid}" />
			<input type="hidden" id="supplierId" name="supplierId" value="${supplier.supplierId}" />
				<li><span>商家注册名称</span><input type="text" id="name" name="name"value="${supplier.name}"
					placeholder="请输入商家注册名称"></li>
				<li><span>商家营业名称</span> <input type="text" id="nameJc"value="${supplier.nameJC}"
					name="nameJc" placeholder="请输入商家营业名称"></li>
				<li class='ssq'><span>省市区</span>
				<div class="sel">
				     <select id="provinceId" name="provinceId"   onchange="ProvincesAndCitiesLinkage(this.value)" style="font-size:0.6rem;color: #393939;">    
						<c:forEach items="${allProvices}" var="allProvice" >
						 	<option  value="${allProvice.provinceid}" <c:if test="${allProvice.provinceid eq province.provinceid}" >selected</c:if> >${allProvice.provincename}</option>
						 </c:forEach>
						 </select><select id="cityId" name="cityId" onchange="ProvincesAndCitie(this.value)" style="font-size:0.6rem;color: #393939;">    
						   	   <option value="${city.cityid}">请选择市</option>
                         </select> <select id="areaId" name="areaId" style="font-size:0.6rem;color: #393939;">    
                               <option value="" >请选择县</option>   
                           </select>  
				</div>
				</li>
				<li><span>商家营业地址</span> <input type="text" id="address" value="${supplier.address}"
					name="address" placeholder="请输入商家营业地址"></li>
				<li><span>商家注册地址</span> <input type="text" id="registerAddress"value="${supplier.registerAddress}"
					name="registerAddress" placeholder="请输入商家注册地址"></li>
				<li><span>营业执照编码</span> <input type="text"value="${supplier.businessLicenseNo}"
					id="businessLicenseno" name="businessLicenseno"
					placeholder="请输入营业执照编码"></li>
				<li><span>企业类型</span> 
					<div class="sel">
					<select id="companyBizType"  name="companyBizType" onchange="getType(this.value)" style="width:100%;font-size:0.6rem;color: #393939;">    
					<c:if test="${supplier.companyBizType==1}">
						<option value="">企业类型</option>
                        <option value="1" selected title="2">企业</option>   
                        <option value="2" title="3">个体工商户</option>   
                        <option value="3" title="4">事业单位</option>
					</c:if>
			         <c:if test="${supplier.companyBizType==2}">
						<option value="">企业类型</option>
                        <option value="1" title="2">企业</option>   
                        <option value="2" selected title="3">个体工商户</option>   
                        <option value="3" title="4">事业单位</option>
					</c:if>
					 <c:if test="${supplier.companyBizType==3}">
						<option value="">企业类型</option>
                        <option value="1" title="2">企业</option>   
                        <option value="2" title="3">个体工商户</option>   
                        <option value="3" selected title="4">事业单位</option>
					</c:if>
                        </select>
                        </div></li>
				<li>
					<span>企业经营类目</span>
					<div class="sel">
						<select id="companyBizCategory" class="selectClass" name="companyBizCategory" style="width:100%;font-size:0.6rem;color: #393939;">    
                        	<option value="">企业经营类目</option>   
                         </select>
                    </div>
               </li>
				<li><span>法定代表人</span> <input type="text" id="legalPerson"value="${supplier.legalPerson}"
					name="legalPerson" placeholder="请输入法定代表人姓名"></li>
				<li><span>身份证号码</span> <input type="text"value="${supplier.legalPersonCardNo}"
					id="legalPersonCardno" name="legalPersonCardno"
					placeholder="请输入15~18位身份证号码"></li>
				<li><span>法人联系电话</span> <input type="text"value="${supplier.legalPersonPhone}"
					id="legalPersonPhone" name="legalPersonPhone"
					placeholder="请输入法定代表人联系电话"></li>
				<li><span>电子邮箱</span> <input type="text" id="email"value="${supplier.email}"
					name="email" placeholder="请输入电子邮箱@xxx.com"></li>
				<li><span>商家联系人</span> <input type="text" id="contact"value="${supplier.contact}"
					name="contact" placeholder="请输入商家姓名"></li>
				<li><span>商家联系电话</span> <input type="text" id="contactTel"value="${supplier.contactTel}"
					name="contactTel" placeholder="请输入商家联系人电话"></li>
				<li><span>商家联系手机</span> <input type="text" id="phone"value="${supplier.phone}"
					name="phone" placeholder="请输入商家联系人手机"></li>
				<li><span>开户银行名称</span>
					<div class="sel">
					<select id="accout"
					onchange="getBankList(this.value)" class="selectClass" style="width:100%;font-size:0.6rem;color: #393939;">
						<c:forEach items="${bank}" var="bankList">	
							<c:if test="${bankList.status==1 }">
								<option value="${bankList.bankCode}"<c:if test="${bankList.bankCode eq bankBranchByCode.bankCode}" >selected</c:if>>${bankList.bankName}</option>
							</c:if>
						</c:forEach>
				</select>
				</div>
				</li>
				<li><span>开户地址</span> 
				<div class="sel1">
				<select onchange="getBankCityList(this.value)" id="provincename" class="selectClass" style="width:46%;    background-color: rgba(255,255,255,0);font-size:0.6rem;color: #393939;">
						<option>请选择省份</option>
				</select> 
				<select onchange="getSubBankOfCity(this.value)" id="cityname" class="selectClass" style="width:46%;    background-color: rgba(255,255,255,0);font-size:0.6rem;color: #393939;">
						<option>请选择区域</option>
				</select>
				</div>
				</li>
				<li><span>开户行</span><div class="sel1"> <select id="bankBranchname" name="bankBranchname"
					class="selectClass1" style="width:100%;font-size:0.6rem;color: #393939;">
						<option>请选择支行</option>
				</select></div></li>
				<li><span>银行账户名称</span> <input type="text" id="accoutName"value="${supplier.accountName}"
					name="accoutName" placeholder="请输入银行账户名称"></li>
				<li><span>银行帐号</span> <input type="text" id="accoutNo"value="${supplier.accountNo}"
					name="accoutNo" placeholder="请输入结算帐号"></li>
			</ul>
			<input type="hidden" id="yingyezhizhao" name="yingyezhizhao" />
			<input type="hidden" id="mentouzhaopian" name="mentouzhaopian" />
			<input type="hidden" id="yinhangkazhaopian" name="yinhangkazhaopian" />
			<input type="hidden" id="shenfenzhengzhengmian" name="shenfenzhengzhengmian" />
			<input type="hidden" id="shenfenzhengfanmian" name="shenfenzhengfanmian" />
			</form>
			<!--客户认证上传各种证书-->
			<div class="updip">
				<div class="uptext">资格认证</div>
				<ul class="list2">
					<li>
						<div id="yyzzdiv" onclick="yyzzDiv()">
							<img id="xmTanImg"src="//image01.zhongjumall.com/${supplier.businessLicense}"  style="height: 100%; width: 100%;">
						</div> <input type="file" id="yyzz" accept="image/*"  hidden="true"value="${supplier.businessLicense}" name="businessLicense"
						onchange="yyzzImg(this)" />
					</li>
					<li>
						<div id="mpdiv" onclick="mpDiv()">
							<img id="mpImg" src="//image01.zhongjumall.com/${supplier.companyStoreLogo}" style="height: 100%; width: 100%;">
						</div>
						<input type="file" accept="image/*"  id="mp"name="companyStoreLogo"value="${supplier.companyStoreLogo}" hidden="true" onchange="mpImages(this)" />
					</li>
					<li>
						<div id="yykzmz" onclick="yykzmz()">
							<img id="yykzmzImg" src="//image01.zhongjumall.com/${supplier.bankAccountPic}" style="height: 100%; width: 100%;">
						</div>
						<input type="file" accept="image/*"  id="bankAccountPic"name="bankAccountPic" hidden="true" onchange="yykzmzImages(this)" />
					</li>
				</ul>
				<ul class="list2_text">
					<li>营业执照</li>
					<li>门头照片</li>
					<li>银行卡正面照</li>
				</ul>
			</div>
			<div class="upid"  style="padding-bottom: 0.6rem;    border-bottom: 1px solid #ececf1;">
				<div class="uptext">身份认证</div>
				<div class="id_img_wp">
					<input type="file"   accept="image/*" name="idCardFront" onchange="zmImg(this)"hidden="true" value="${supplier.idCardFront}" id="img_z" />
					<input type="file"   accept="image/*"  onchange="fmImg(this)" id="img_f" name="idCardVerso"hidden="true"value="${supplier.idCardVerso}" />
					<div id="sfzzmz" class="img_wp" onclick="zhengMianDiv()">
						<img src="//image01.zhongjumall.com/${supplier.idCardFront}" id="zmz" />
					</div>
					<div id="sfzfmz" class="img_wp"onclick="fanmianDiv()" >
						<img src="//image01.zhongjumall.com/${supplier.idCardVerso}" id="fmz"   />
					</div>
				</div>
				<ul class="list2_text">
					<li>身份证正面照</li><li>身份证反面照</li></ul>
			</div>
			
			<div class="btn" onclick="submitRegister()" >
			<section class="button-demo">
				<button   data-color="red" data-style="contract-overlay" style="z-index: 10;font-size: 0.8rem;  padding: 0.2rem;    width: 90%;    border-radius: 30px; color: #fff;   background: #e60012;">保&nbsp;&nbsp;&nbsp;存</button>
			</section>
	  	    </div>
	</div>
</body>
</html>
<script src="${staticFile_s}/commons/js/user/updateMregister.js?version=1.0.0"></script>
<script src="${staticFile_s}/commons/js/qcwd/mui.min.js"></script>
<script src="${staticFile_s}/commons/js/mui/mui.previewimage.js"></script>
<script src="${staticFile_s}/commons/js/mui/mui.zoom.js"></script>
<script>
console.log(document.getElementById('xmTanImg').src)
var a =document.getElementById('xmTanImg').src;
mui.previewImage();
</script>