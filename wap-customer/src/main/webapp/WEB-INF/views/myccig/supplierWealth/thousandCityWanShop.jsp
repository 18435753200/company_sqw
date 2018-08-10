<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${staticFile_s}/commons/css/qcwdcss/aui.css" />
<link rel="stylesheet"
	href="${staticFile_s}/commons/css/qcwdcss/swiper-3.2.7.min.css">
<link rel="stylesheet"
	href="${staticFile_s}/commons/css/qcwdcss/qcwd_dplb.css">
<link rel="stylesheet" type="text/css"
	href="${staticFile_s}/commons/css/grzxcss/index.css" />
<link rel="stylesheet" type="text/css"
	href="${staticFile_s}/commons/css/grzxcss/grzx_djq_css.css" />
<script language="javascript">
	function div_none(theclass) {
		var allPageTags = new Array();
		var allPageTags = document.getElementsByTagName("div");
		for (i = 0; i < allPageTags.length; i++) {
			if (allPageTags[i].className == theclass) {
				var obj = allPageTags[i];
				if (obj.style.display == "none") {
					obj.style.display = "";
				} else {
					obj.style.display = "none";
				}
			}
		}
	}
</script>
<style>
	.wrapper03 {position:fixed;height: 2rem;width: 100%;overflow: hidden;margin:0 auto;border-bottom:1px solid #ccc;    top: 2.2rem;
    z-index: 20;background:#fff}
	.wrapper03 .scroller {position:absolute}
	.wrapper03 .scroller li {height: 2rem;color:#333;float: left;line-height: 2rem;font-size: 0.7rem;text-align: center}
	.wrapper03 .scroller li a{color:#333;display:inline;margin:0 .3rem;padding:0 .1rem;padding-bottom: 0.4rem}
	.wrapper03 .scroller li.cur a{height:2rem}

.qd {
	background-color: #fb4c03;
	width: 100%;
	border: 0;
	height: 2rem;
	font-size: 0.8rem;
	color: #fff;
}

.container {
	/*width: 100%;*/
	border: 1px solid #ccc;
	position: fixed;
	z-index: 20;
	top: 2.2rem;
	background: rgba(255,255,255,1);
}



.dp_pic {
	width: 100%;
	height: 100%;
	display: block;
	border: none;
	border-radius: 5px;
}

.dp_b p {
	color: #000000;
	position: relative;
	top: -1.3rem;
	font-size: 0.6rem;
	line-height: 1rem;
}

.list-wrapper {
	position: relative;
	z-index: 0;
	top: 6.5rem;
	bottom: 0rem;
	width: 100%;
	background: #f9f9f9;
	overflow: hidden;
}

.aui-card-list {
	height: auto;
	width: 100%;
	margin: 0 auto;
	overflow: hidden;
	position: relative;
	margin-bottom: 0.4rem;
}

.dp_b {
	position: absolute;
	bottom: 0;
	height: 25%;
	padding-left: 1rem;
	background: rgba(255, 255, 255, 0.6);
	width: 100%;
}

.qcwd_tel {
	position: absolute;
	z-index: 1000;
	right: 2rem;
	bottom: 0.8rem;
	display: block;
	border: none;
	border-radius: 5px;
}

.show_city {
	position: fixed;
	background: rgba(255, 255, 255, 0.8);
	width: 100%;
	top: 4rem;
	text-align: center;
	line-height: 2rem;
	color: #fb4c03;
	letter-spacing: 0.2rem;
	font-weight: 600;
	font-size: 0.7rem;
	border-bottom: 1px solid #ececec;
	z-index:10;
}

select {
	font-size: 14px;
	height: auto;
	margin-top: 1px;
	border: 0 !important;
	background-color: #fff;
	width: 50%;
	float: left;
	margin-bottom: 1rem;
}
.more{    text-align: center;
    color: gray;
    line-height: 2rem;
        font-size: 0.6rem;}
</style>
</head>
<body>
	<input type="hidden" id="path" value="<%=path%>" />
	<input type="hidden" id="staticFile_s" value="<%=path%>" />
	<input type="hidden" id="page" value="${page}" />
	<input type="hidden" id="totalPage" value="${totalPage}" />
	<input type="hidden" id="sectorCodeName" value="${sectorCodeName}" />
	<input type="hidden" id="cityIds" value="${cityId}" />
	<header class="aui-bar aui-bar-nav aui-bar-light"
		style="background: #fb4c03;">
		<a class="aui-pull-left aui-btn" href="javaScript:void(0)"
			onclick="returnMain()"> <span class="aui-iconfont aui-icon-left"></span>
		</a>
		<div class="aui-title" style="color: #fff;">千城万店</div>
		<div class="aui-pull-right aui-btn aui-btn-outlined" name="a1"
			onclick="div_none('a1');">
			<span class="aui-iconfont aui-icon-location"></span>
		</div>
	</header>
	<div
		style="display: none; position: fixed; z-index: 100; top: 2.5rem;width: 100%;"
		class="a1">
		<h4>请选择地区</h4>
		<div style="margin: 1rem;">
			<select id="provinceId" name="provinceId"
				onchange="ProvincesAndCitiesLinkage(this.value)">
				<option value="">请选择省</option>
				<c:forEach items="${allProvices}" var="allProvice">
					<option value="${allProvice.provinceid}">${allProvice.provincename}</option>
				</c:forEach>
			</select> 
			<select id="cityId" name="cityId">
				<option value="">请选择市</option>
			</select>
		</div>
		<button class="qd" onclick="changeCity();">确定</button>
	</div>
	<div class="wrapper wrapper03" id="wrapper03">
	<div class="scroller">
	<ul class="clearfix">
	<c:choose>
	   <c:when test="${not empty sectorCodeName}">
			<li style="margin:0 0.5rem"><a  href="${path }/supplier/thousandCityWanShop?cityId=${cityId}">全部</a></li>
	   </c:when>
	<c:otherwise>
			<li  style="margin:0 0.5rem"><a style="color:#e60012;border-bottom:.1rem solid #e60012" href="${path }/supplier/thousandCityWanShop?cityId=${cityId}" >全部</a></li>		
	</c:otherwise>
	</c:choose>
	
	<c:forEach items="${searchSupplier.companyBizCategorys}" var="companyBizCategorys" varStatus="status">
		<c:choose>
			<c:when	test="${not empty sectorCodeName && sectorCodeName eq companyBizCategorys}">
					<li  style="margin:0 0.5rem" >
						<a style="color:#e60012;border-bottom:.1rem solid #e60012" href="${path }/supplier/thousandCityWanShop?cityId=${cityId}&sectorCodeName=${companyBizCategorys}">${companyBizCategorys}</a>
					</li>
			</c:when>
			<c:otherwise>
					   <li  style="margin:0 0.5rem">
								<a href="${path }/supplier/thousandCityWanShop?cityId=${cityId}&sectorCodeName=${companyBizCategorys}">${companyBizCategorys}</a>
						</li>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	</ul>
</div>
</div> 
	<div class="show_city">${city }</div>
	<div class="list-wrapper list-wrapper-hook">
		<div>
			<!-- 顶部提示信息 -->
			<div class="top-tip">
				<span class="refresh-hook" style="display: none">下拉刷新</span>
			</div>
			<!-- 列表 -->
			<ul class="list-content list-content-hook" id="thelist">
				<c:forEach items="${searchSupplier.items}" var="supplier"
					varStatus="status">
					<li class="list-item">
						<div class="aui-card-list">
							<div class="aui-card-list-header">
								<img src="//image01.zhongjumall.com/d1/${supplier.companyStoreLogo}" class="dp_pic" onclick="lookDetail('${supplier.supplierId}','${supplier.sdStatus}')">
								<div class="dp_b">
									<p>
										<img src="${path }/commons/img/qcwd/zjm_2.png"
											style="width: 10%; width: 15%; display: inline; position: relative; background: #fff; border-radius: 30px; padding: 0.4rem;" />
										<b style="margin-left: 1rem">${supplier.nameJC }</b>
									</p>
									<p>${supplier.sdLocationPoiaddress}</p>
									<a href="tel:${supplier.contactTel}"><img
										src="https://cms.3qianwan.com/mImg/2018/07/07/5413bb339fe24630ad030f04735dc123.png"
										class="qcwd_tel" style="width: 30px; height: 30px;"></a>
								</div>
							</div>
						</div>
					</li>
				</c:forEach>
			</ul>
			<!-- 
        1、底部提示信息 
        2、这里有一种情况,就是没有更多数据时,这里的文本应该显示"暂无更多数据"
      -->
		 <div class="more">
                 <a href="javascript:void(0);" onclick="more()" style="color: #A2A2A2;">点击加载更多</a>
         </div>
		</div>
	</div>

	<!-- content end  -->

	<!-- footer -->

	<!-- alert -->
	<div class="alert alert-hook" style="display: none">刷新成功</div>
	<!-- js -->
	<script type="text/javascript" src="${staticFile_s}/commons/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${staticFile_s}/commons/js/main.js?version=1.0.0"></script>
	<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js?version=1.0.0"></script>
	<script src="${staticFile_s}/commons/js/rem.js?version=1.0.0"></script>
	<script  src="${staticFile_s}/commons/js/qcwd/iscroll.js"></script>
	<script  src="${staticFile_s}/commons/js/qcwd/navbarscroll.js"></script>
	<script type="text/javascript">
	$(function(){
		$('.wrapper').navbarscroll();
		isShowMore();
	});
	
	//查看店铺详情
	function lookDetail(supplierId,sdStatus){
		if(sdStatus=='0'){
			showErrorMsg("店铺已被冻结");
		}else{
				document.location.href = "${path }/supplier/lookDetail?supplierId="+ supplierId;
		}
		
	}
	
	//返回千城万店首页
	function returnMain(){
		window.location.href="http://m.zhongjumall.com/qcwd.html";
	}
	
	
	//切换地区
	function changeCity() {
		var cityId = $("#cityId").val();
		if (cityId == null || cityId == '') {
			showErrorMsg("请选择城市");
		} else {
			document.location.href = "${path }/supplier/thousandCityWanShop?cityId="
					+ cityId;
		}
	}
	function showErrorMsg(str) {
		// $(".error_tips").removeClass("hide");
		// $(".error_tips").html("<font
		// color=red>&nbsp;&nbsp;&nbsp;"+str+"</font>");
		$.dialog({
			content : str,
			title : '众聚猫提示',
			time : 1000,
		});
		return;
	}

	// 加载更多方法\
	function more() {
		var page =  parseInt($("#page").val()) + 1;
		var cityId = $("#cityIds").val();
		var sectorCodeName = $("#sectorCodeName").val();
		$.ajax({
			type: 'POST',
			url: $("#path").val()+'/supplier/ajaxPullUp',
			dataType: 'json',
			async:false,
			data:"pageNo="+ page+"&sectorCodeName="+sectorCodeName+"&cityId="+cityId,
			success: function(data) {
				$("#page").val(data.data.page);
				isShowMore();
				for(var i in data.data.searchSupplier.items){
					var result="";
					result+="<li class=\"list-item\">";
					result+="<div class=\"aui-card-list\">";
					result+="<div class=\"aui-card-list-header\">";
					if(data.data.searchSupplier.items[i].companyStoreLogo!=null){
						result+="<img src='//image01.zhongjumall.com/d1/"+data.data.searchSupplier.items[i].companyStoreLogo+"' class='dp_pic'"+"onclick='lookDetail("+data.data.searchSupplier.items[i].supplierId+","+data.data.searchSupplier.items[i].sdStatus+")'>";
						
					}else{
						result+="<img src='"+$("#path").val()+"/commons/img/qcwd/dp_ban.jpg' class='dp_pic'>";
					}
					result+="<div class=\"dp_b\"><p>";
					result+="<img src='"+$("#path").val()+"/commons/img/qcwd/zjm_2.png' style='width: 10%; width: 15%; display: inline; position: relative; top: rem; background: #fff; border-radius: 30px; padding: 0.4rem;' />";
					result+="<b style='margin-left: 1rem'>"+data.data.searchSupplier.items[i].nameJC+"</b></p><p>"+data.data.searchSupplier.items[i].sdLocationPoiaddress+"</p>";
					result+="<a href='tel:"+data.data.searchSupplier.items[i].contactTel+"'><img src='https://cms.3qianwan.com/mImg/2018/07/07/5413bb339fe24630ad030f04735dc123.png' class='qcwd_tel' style='width: 30px;height: 30px;'></a>";
					result+="</div></div></div></li>";
					$("#thelist li").last().after(result);
				}
				
			},
			error: function() {
			}
		});

	}
	function isShowMore(){
		var page = $("#page").val();
		var totalPage = $("#totalPage").val();
		if(totalPage == 0 || page == totalPage){
			$(".more").hide();
		}else{
			$(".more").show();
		}
	}
</script>
</body>
</html>