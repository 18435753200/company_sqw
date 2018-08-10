<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<!-- <meta http-equiv="content-type" content="text/html;charset=utf8">
<meta id="viewport" name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1; user-scalable=no;"> -->
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link href="${staticFile_s}/commons/css/base.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/order.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<title><spring:message code="title_pay_type" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
<script type="text/javascript">
function curGoBack(){
	var lastUrl = document.referrer;
	if(lastUrl.indexOf("directBuy")){
		history.go(-2);
	}else{
		goBack();
	}
}
</script>
<style>
#header {
    position: relative;
    top: 0;
    left: 0;
    width: 100%;
    height: 44px;
    display: -moz-box;
    display: -webkit-box;
    display: -webkit-flex;
    display: -moz-flex;
    display: -ms-flexbox;
    display: -ms-flex;
    display: flex;
    background-color: #e60012;
    z-index: 999;
    color: #fff;
}
.h-title {
    line-height: 44px;
    font-size: 18px;
    color: #ffffff;
    text-align: center;
    padding: 0 40px;
    font-weight: normal;
    -webkit-box-flex: 1;
    -moz-box-flex: 1;
    -webkit-flex: 1;
    -moz-flex: 1;
    -ms-flex: 1;
    flex: 1;
}
.head-bg {
    background: #e60012;
    position: fixed;
    width: 100%;
    z-index: 100;
    letter-spacing: 0;
}
.header {
    color: #ffffff;
    background: #e60012;
    height: 45px;
    line-height: 45px;
    border-bottom: 0;
    text-align: center;
    font-size: 1.8rem;
    letter-spacing: 0.1rem;
    position: relative;
}
.header a.bug-go {
    position: absolute;
    display: block;
    width: 1.25rem;
    height: 1.23rem;
    border: 0.1rem solid rgba(255,255,255,1);
    border-top: none;
    border-right: none;
    -webkit-transform: rotate(45deg);
    left: 15px;
    top: 1.7rem;
}
</style>
</head>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
	<!-- <header id="header" class="head">
	    <a href="javascript:curGoBack();" class="goback"><span class="icon i-goback"></span></a>
		<h2 class="h-title">支付方式</h2>
	</header> -->
<div class="header head-bg">
		<a href="javascript:goBack();" class="bug-go"></a>
支付方式
	</div>
	<div class="wrap" id="pay">
    <div class="item pay" id="mainPayDiv">
        <ul class="pay-list">
        <c:choose>
		<%--<c:when test="${orderSource eq 'chinabankcreditcard' }">
        		<c:if test="${supplyType eq '1'}">
 			    	<li>
					   <label> 
					       <input type="radio" name="radio" id="bocNetPay">
					       <span class="payicon i-y2"></span>中国银行网银
					   </label>
				    </li>
			    </c:if>
        	</c:when>--%>
        	<c:when test="${orderSource eq 'bocbeifen' or  orderSource eq 'bocningbo'}">
        		<c:choose>
	        		<c:when test="${supplyType eq '12'}">
<!-- 	        			<li>
						   <label> 
						       <input type="radio" name="radio" id="boccPay">
						       <span class="payicon i-y2"></span>中国银行跨境支付
						   </label>
			    		</li> -->
	        		</c:when>
	        		<c:otherwise>
<!-- 	        			<li>
						   <label> 
						       <input type="radio" name="radio" id="bocNetPay">
						       <span class="payicon i-y2"></span>中国银行网银
						   </label>
					    </li> -->
	        		</c:otherwise>
        		</c:choose>
        	</c:when>
        	<c:otherwise>
        		<c:choose>
		        <%--	<c:when test="${supplyType eq '1' or supplyType eq '12' or supplyType eq '11'}">
			        	        <li class="otherPlatform">
				                   <label >
				                      <input type="radio" name="radio" id="aliPayDirect" />
				                      <span class="payicon i-zfb"></span>支付宝
				                   </label>
				                </li>

						    <c:if test="${supplyType eq '1' or supplyType eq '11'}">
<!-- 							     <li>
								   <label> 
								       <input type="radio" name="radio" id="unipay">
								       <span class="payicon i-yl"></span>银联
								   </label>
							   	 </li>
						    	<li>
								   <label> 
								       <input type="radio" name="radio" id="bocNetPay">
								       <span class="payicon i-y2"></span>中国银行网银
								   </label>
							    </li>
						    	<li style="border:1px solid #e5e5e5;" class="wxPlatform">
								   <label> 
								       <input type="radio" name="radio" id="weiXinGZHPay">
								       <span class="weixin i-y2"></span>微信支付
								   </label>
							    </li>  -->
							    
							    &lt;%&ndash; <li>
							    	<label> 
							       		<input type="radio" name="radio" id="bestpay">
							       		<span class="payicon i-yzf"></span>翼支付
							    	</label>
					     	  </li> &ndash;%&gt;
						    </c:if>
						    
&lt;%&ndash; 						    <c:if test="${supplyType eq '12' }">
<!-- 						    	<li>
								   <label> 
								       <input type="radio" name="radio" id="boccPay">
								       <span class="payicon i-y2"></span>中国银行跨境支付
								   </label>
					    		</li> -->
						    </c:if> &ndash;%&gt;
						    
		                <c:if test="${supplyType eq '1'}">
		                	<!-- <li>
							   <label> 
							       <input type="radio" name="radio" id="bocNcpkjPay">
							       <span class="payicon i-y2"></span>中国银行信用卡
							   </label>
						    </li> -->
						    <!-- <li>
							   <label> 
							       <input type="radio" name="radio" id="bocNcpfqPay">
							       <span class="payicon i-y2"></span>中国银行信用卡分期
							   </label>
						    </li> -->
					    </c:if>
					    
					</c:when>
					<c:when test="${ supplyType eq '21'}">
		        	    <li class="otherPlatform">
			                <label >
			                    <input type="radio" name="radio" id="aliPayDirect" />
			                    <span class="payicon i-zfb"></span>支付宝
			                </label>
			            </li>
					</c:when>
					<c:when test="${supplyType eq '31' or  supplyType eq '50'}">
		        	    <li  class="otherPlatform">
			                <label >
			                    <input type="radio" name="radio" id="aliPay" />
			                    <span class="payicon i-zfb"></span>支付宝
			                </label>
			            </li>
					</c:when>--%>
					<c:when test="${supplyType eq '51'}">
						    <c:choose>
							<c:when test="${not empty accountType and accountType != null and accountType != 'null' and accountType eq 1}">
								<!-- <li class="otherPlatform">
					                <label >
					                    <input type="radio" name="radio" id="aliPay" />
					                    <span class="payicon i-zfb"></span>支付宝
					                </label>
					            </li> -->
							</c:when>
							<c:otherwise>
					<!-- 1.userAccountStatus用户激活状态 2为激活
							     2.mixPayStatus是否红旗券支付过 1表示支付过
							     3.MissZeroStatus是否出现0支付 0表示出现了
							     4.xjbl设置了现金比例且红旗券和现金都大于0的商品  1为设置0为没有设置
							     5.homeShangPin;家庭号商品1表示是0表示不是
							    -->
										<!-- 易物商品只能红旗券支付 -->
										<%-- <c:if test="${homeShangPin==1 }">
											<li class="otherPlatform"><label> <input
													type="radio" name="radio" id="hqPay" /> <span
													class="hqpayicon i-y2"></span>红旗券支付
											</label></li>

										</c:if> --%>
										<!-- 红旗宝只能现金支付 -->
										<%-- <c:if test="${mixPayStatus==1 }">
											<li class="aliPayDirectPlatform"><label> <input
													type="radio" name="radio" id="aliPayDirect" /> <span
													class="payicon i-zfb"></span>支付宝
											</label></li> -->
											<!-- <li class="otherPlatform">
								                   <label >
								                      <input type="radio" name="radio" id="jdPay" />
								                      <span class="payicon i-y2"></span>京东支付
								                   </label>
								                </li> -->
											<!-- <li style="border:1px solid #e5e5e5;" class="wxPlatform">
												<label> <input type="radio" name="radio"
													id="weiXinGZHPay"> <span class="weixin i-y2"></span>微信支付
											</label>
											</li>


										</c:if> --%>
						<!--现金比例和幸福购商品只能混合支付  -->
						<%-- <c:if test="${xjbl==1&&mixPayStatus!=1 }">
							<li class="otherPlatform"><label> <input
									type="radio" name="radio" id="hqqAndMoneyPay" /> <span
									class="hqqandmoneypayicon i-y2"></span>红旗券+现金支付
							</label></li>
							<li class="aliPayDirectPlatform"><label> <input
									type="radio" name="radio" id="aliPayDirect" /> <span
									class="payicon i-zfb"></span>支付宝
							</label></li>
							<li style="border:1px solid #e5e5e5;" class="wxPlatform">
								<label> <input type="radio" name="radio"
									id="weiXinGZHPay"> <span class="weixin i-y2"></span>微信支付
							</label>
							</li>
						</c:if> --%>
						<!-- 其他商品有所有支付方式 -->
						<%-- <c:if test="${homeShangPin==0&&mixPayStatus!=1 &&xjbl==0 }">
							<li class="otherPlatform"><label> <input
									type="radio" name="radio" id="hqPay" /> <span
									class="hqpayicon i-y2"></span>红旗券支付
							</label></li>
							 <c:if test="${MissZeroStatus!=0 && hqjPrice!=0&& cashPrice!=0}">
								<li class="otherPlatform"><label> <input
										type="radio" name="radio" id="hqqAndMoneyPay" /> <span
										class="hqqandmoneypayicon i-y2"></span>红旗券+现金支付
								</label></li>
							</c:if>
							<li class="aliPayDirectPlatform"><label> <input
									type="radio" name="radio" id="aliPayDirect" /> <span
									class="payicon i-zfb"></span>支付宝
							</label></li> --%>
							<!-- <li class="otherPlatform">
								   <label >
									  <input type="radio" name="radio" id="jdPay" />
									  <span class="payicon i-y2"></span>京东支付
								   </label>
								</li> -->
							<%-- <li style="border:1px solid #e5e5e5;" class="wxPlatform">
								<label> <input type="radio" name="radio"
									id="weiXinGZHPay"> <span class="weixin i-y2"></span>微信支付
							</label>
							</li>
						</c:if> --%>
				<!--  混合支付     -->
				 <c:if test="${hqAndMoneyPay==1 }">
				 	<c:if test="${mixPayStatus !=1 && hqj >0}">
				 		<li class="otherPlatform">
				 				<label> <input type="radio" name="radio" id="hqqAndMoneyPay" /> 
				 				<span class="hqqandmoneypayicon i-y2"></span>M券+现金支付
								</label>
						</li>
				 	</c:if>
				 </c:if>
				 
				 <!--现金支付   --> 
				 <c:if test="${moneyPay == 1}">
				 		<li class="aliPayDirectPlatform">
				 			<label>
                                <input type="radio" name="radio" id="aliPayDirect" /><span class="payicon i-zfb"></span>支付宝
                            </label>
				 		</li>
						<li style="border:1px solid #e5e5e5;" class="wxPlatform">
							<label>
								<input type="radio" name="radio" id="weiXinGZHPay"/><span class="weixin i-y2"></span>微信支付
							</label>
						</li>
				 </c:if>
				   
				<%-- <!-- 红旗券支付 -->
				 <c:if test="${hqPay == 1}">
				 			<li class="otherPlatform">
				 				<label> <input type="radio" name="radio" id="hqPay" /> 
				 				<span class="hqpayicon i-y2"></span>红旗券支付</label>
				 			</li>
				 </c:if> --%>
								</c:otherwise>
						</c:choose>
					</c:when>
		        </c:choose>
        	</c:otherwise>
        </c:choose>
        </ul>
    </div>
   <!--  <input type="button" value="确认" class="pay-btn"> -->
</div>
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<script src="${staticFile_s}/commons/js/ccigorder/myOrder.js" type="text/javascript"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
<script async="" src="//www.google-analytics.com/analytics.js"></script>
<script src="//hm.baidu.com/hm.js?a033dafefef77193d4d80cc7103c9772"></script>

<script type="text/javascript">
var appId = "${appId}";
var timestamp = "${timestamp}";
var nonceStr = "${nonceStr}";
var signature = "${signature}";
var isDisplayWeiXinFlag = "${isDisplayWeiXinFlag}";
if(isDisplayWeiXinFlag == true){
	wx.config({
	    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: appId, // 必填，公众号的唯一标识
	    timestamp: timestamp, // 必填，生成签名的时间戳
	    nonceStr: nonceStr, // 必填，生成签名的随机串
	    signature: signature,// 必填，签名，见附录1
	    jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
}

$(document).ready(function(){
	var errorMsg = "${errorMsg}";
	if(!isEmpty(errorMsg)){
		showErrorMsg(errorMsg);
	}
 	function is_weixin() {
	    var ua = navigator.userAgent.toLowerCase();
	    if (ua.match(/MicroMessenger/i) == "micromessenger") {
	        return true;
	    } else {
	        return false;
	    }
	}
	
 	var isWeixin = is_weixin();
	
	if(isWeixin){
        $(".aliPayDirectPlatform").hide();
	}else{
		$(".wxPlatform").hide();
 	}
	
	var orderId="${orderId}";
	var payType="${payType}";

	var _trackDataType = 'wap'; //标记数据来源，参数是web和wap，可以为空，默认是web
	var _Schannel_website_id = '10000006';//分站编号，不存在可不写此变量或者留空
	var _trackData = _trackData || [];//必须为全局变量，假如之前并没有声明，请加此行代码；

	/**
	 * 支付宝支付
	 */
	$("#aliPay").click(function(){
		//showErrorMsg("暂不提供支付方式，请移驾客户端");
		_trackData.push(['addorderpay',orderId+"", '支付宝']);
		aliPay("aliPay");
	});


	
	$("#hqPay").click(function(){
		location.href=$("#path").val()+"/orderPay/tradePwdPage?orderId=${orderId}&act=hqPay";
	});
	
	$("#hqqAndMoneyPay").click(function(){
		location.href=$("#path").val()+"/orderPay/tradePwdPage?orderId=${orderId}&act=hqqAndMoneyPay";
	});
	
	$("#jdPay").click(function(){
		jdPay("jdPay");
	});
	
	/**
	 * 银联支付
	 */
/*	$("#unipay").click(function(){
		//showErrorMsg("暂不支付银联支付，请移驾客户端");
		_trackData.push(['addorderpay', orderId+"", '银联支付']);
		unipay();
	});

	/!* 中行跨境 *!/
	$("#boccPay").click(function(){
		boccPay("bocCrossPay");
	});

	/!* 中行网银 *!/
	$("#bocNetPay").click(function(){
		bocNetPay("bocNetPay");
	});

	/!* 中行信用卡快捷 *!/
	$("#bocNcpkjPay").click(function(){
		bocNcpkjPay("bocNcpkjPay");
	});*/

    $("#aliPayDirect").click(function(){
        chinaumsPay("alipay");
    });
	$("#weiXinGZHPay").click(function(){
		chinaumsPay("weixin");
	});

    function chinaumsPay(act){
        var _dataType = "json";
        var _type = "POST";
        var _url = $("#path").val()+"/orderPay/chinumsOnlinePay";
        var openId = "${openId}";

        $.ajax({
            dataType : _dataType,
            type : _type,
            url : _url,
            data : {
                'orderId':orderId,'openId':openId,'nonceStr':nonceStr, 'timestamp': timestamp, 'payType': act
            },
            success : function(res) {
                var url = res.action;
                window.location.href = url;
            },
            error : function(res) {

                showErrorMsg("网络连接超时！");
            }
        });
    }
	

	/**
	 * 翼支付
	 */
	$("#bestpay").click(function(){
	 	var url=$("#path").val()+"/orderPay/payInfo?orderId="+orderId+"&act=bestoayPayDirect";
		$.get(url,null,function(redirectUrl){
			if(redirectUrl != "" && redirectUrl != null){
				window.location.href = redirectUrl;
			}else{
				showErrorMsg("跳转支付页面失败，请联系客服");
			}
		});
	 });
	function bestPayment(){
		if('13' == payType){
			var payJson={
		            "PRODUCTID": "04", //业务标识
		            "MAC": "${payResult.requestBody.MAC}", //MAC 校验
		            "ATTACHAMOUNT": "0.00", //附加金额（单位：元，保留小数点后两位）
		            "USERIP": "${userIP}", //用户 IP
		            "SUBMERCHANTID": "", //子商户号
		            "ORDERTIME": "${payResult.requestBody.ORDERREQTIME}", //订单请求时间格式yyyyMMddHHmmss
		            "MERCHANTID": "${payResult.requestBody.MERCHANTID}", //商户号
		            "DIVDETAILS": "", //分账明细，分账商户必填,
		            "ATTACH": "", //附加信息
		            "BACKMERCHANTURL": "${payResult.requestBody.NOTIFYURL}", //支付结果后台通知地址
		            "PRODUCTAMOUNT": "${payResult.requestBody.ORDERAMT}", //产品金额,（单位：元，保留小数点后两位）
		            "CURTYPE": "RMB", //币种（默认填 RMB ）
		            "BUSITYPE": "09", //业务类型
		            "PRODUCTDESC": "${productDesc}", //产品描述
		            "CUSTOMERID": "${userId}", //用户 ID, 在商户系统的登录
		            "ORDERVALIDITYTIME": "", //订单有效时间
		            "ORDERAMOUNT": "${payResult.requestBody.ORDERAMT}", //订单金额／积分扣减（单位：元，保留小数点后两位）
		            "ORDERSEQ": "${payResult.requestBody.ORDERSEQ}",//订单号
		            "MERCHANTPWD": "${payResult.requestBody.MERCHANTKEY}", //商户交易KEY
		            "ACCOUNTID": User.getProduct(), //翼支付账户号
		            "ORDERREQTRANSEQ": "${payResult.requestBody.ORDERREQTRANSEQ}", //流水号
		            "ORDERREQTRNSEQ": "${payResult.requestBody.ORDERREQTRANSEQ}", //值同上面的ORDERREQTRANSEQ
		            "SERVICE": "mobile.security.pay",   //此值写死
		            "SESSIONKEY": App.getSessionKey()
		        };
			//支付成功的回调函数
	        var paySuccess = function () { 
	           window.location.href = "<%=basePath %>?r=bestpay";
	        }
	        //支付失败的回调函数
	        var payFail = function () {
	        	window.location.href = "<%=basePath %>/cusOrder/toMyAllOrder?pageNow=1&status=1";
	        }

	        //调起翼支付支付界面
	        Payment.onPay(payJson, paySuccess, payFail);

		}
	}
	
	function jdPay(act){
		var url=$("#path").val()+"/orderPay/payInfo?orderId="+orderId+"&act="+act;
		//window.location.href=url;
		$.get(url,function(redirectUrl){
			if(redirectUrl != "" && redirectUrl != null){
				window.location.href=redirectUrl;
			}else{
				showErrorMsg("跳转支付页面失败，请联系客服");
			}
		});
	}

	function aliPay(act){
		var url=$("#path").val()+"/orderPay/payInfo?orderId="+orderId+"&act="+act;
		$.get(url,function(redirectUrl){
			if(redirectUrl != "" && redirectUrl != null){
				window.location.href=redirectUrl;
			}else{
				showErrorMsg("跳转支付页面失败，请联系客服");
			}
		});
	}
	
	function boccPay(act){
		var url=$("#path").val()+"/orderPay/boccPayInfo?orderId="+orderId+"&act="+act;
		window.location.href=url;
	}
	
	function unipay(){
		var url=$("#path").val()+"/orderPay/payInfo?orderId="+orderId+"&act=unionPay";
		$.get(url,function(redirectUrl){
			if(redirectUrl != "" && redirectUrl != null){
				window.location.href = $("#path").val() + redirectUrl;
			}else{
				showErrorMsg("跳转支付页面失败，请联系客服");
			}
		});  
	}

	function bocNetPay(){
		var url=$("#path").val()+"/orderPay/payInfo?orderId="+orderId+"&act=bocNetPay";
		$.get(url,function(redirectUrl){
			if(redirectUrl != "" && redirectUrl != null){
				window.location.href = redirectUrl;
			}else{
				showErrorMsg("跳转支付页面失败，请联系客服");
			}
		});  
	}

	function bocNcpkjPay(act){
		var url=$("#path").val()+"/orderPay/toPayMiddle?orderId="+orderId+"&act=" + act+"&planId=" +"&planNum=";
		window.location.href = url;
	}
	function bocNcpfqPay(act){
		var planId;
		var planNum;
		var url=$("#path").val()+"/orderPay/toPayMiddle?orderId="+orderId+"&act=" + act+"&planId=" + planId +"&planNum=" + planNum;
		window.location.href = url;
	}



	function toWeiXinGZHPay(params){
		wx.chooseWXPay({
	        timestamp: params.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
	        nonceStr: params.nonceStr, // 支付签名随机串，不长于 32 位
	        package: params.packager, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
	        signType: params.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
	        paySign: params.paySign, // 支付签名
	        success: function (res) {
	            // 支付成功后的回调函数
	        }
	    });
	}

	function onBridgeReady(params){
	    WeixinJSBridge.invoke(
	       'getBrandWCPayRequest', {
	           "appId":params.appId,     //公众号名称，由商户传入     
	           "timeStamp":params.timeStamp,         //时间戳，自1970年以来的秒数     
	           "nonceStr":params.nonceStr, //随机串     
	           "package":params.packager,     
	           "signType":params.signType,         //微信签名方式：     
	           "paySign":params.paySign //微信签名 
	       },
	       function(res){
               var jumpUrl = $("#path").val() + "/orderPay/queryWeiXinPay?payFlowNo=" + params.payFlowNo + "&orderId=" + params.orderId;
	           window.location.href = jumpUrl;
	          /* if(res.err_msg == "get_brand_wcpay_request:ok" ) {
	        	  var jumpUrl = $("#path").val()+"/orderPay/queryWeiXinPay?payFlowNo=" + params.payFlowNo + "&orderId=" + params.orderId;
	        	  window.location.href = jumpUrl;
	          } */    // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
	       }
	    );  
	    
	}
	
	

	function showErrorMsg(str){
		//  $(".error_tips").removeClass("hide");
		//$(".error_tips").html("<font color=red>&nbsp;&nbsp;&nbsp;"+str+"</font>");  
		
		$.dialog({
	        content : str,
	        title : '众聚猫提示',
	        time: 1000,
		});
		return;
	}
	 
});


wx.ready(function(){
    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
});
wx.error(function(res){
    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。

}); 
</script>
</body>
</html>