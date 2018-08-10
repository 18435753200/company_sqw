<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<%@include file="/WEB-INF/views/commons/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui.css" />
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui_grzx_icon.css" />
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<title>众聚猫个人中心</title>
<style>
.user-info {
    background: url(../../images/grzx_head2.png);
    padding: 0.75rem 0;
    background-size: 120%;
}
.aui-btn-block {
    display: block;
    width: 30%;
    margin: 0 auto;
    margin-top: 1rem;
    height: 1.7rem;
    line-height: 1.7rem;
    margin-bottom: 1rem;
    font-size: 0.7rem;
}
.rDialog-ok, .rDialog-ok:hover {
    background: #dd191b;
    color: #fff;
}
	a{color:#000}
	.rDialog-wrap {
    position: relative;
    background: #404040;
    opacity: .9;
    background-clip: padding-box;
    border-radius: 10px;
    -moz-border-radius: 10px;
    -o-border-radius: 10px;
    -webkit-border-radius: 10px;
    box-shadow: 1px 1px 1px #000;
    padding: 1em 1em;
}
.my_top .pw img {
	border-radius: 50%;
	margin: -1px 0px 6px 0px;
	padding: 0
}

.my_quick {
	text-align: center;
	width: 100%;
	margin-top: 25px;
}

.my_quick li {
	width: 25%;
	float: left;
}

.my_star {
	width: 18px !important;
	height: 18px !important;
	position: relative;
	top: 8px;
}
.bangding{    font-size: 0.7rem;    text-align: center;    margin-bottom: 0.5rem;line-height: 2rem;}
.bd_href{padding: 0.2rem 0.5rem;    color: red;        border: 1px solid red;    background: #fff;    border-radius: 5px;    margin-left: 0.5rem;}
.aui-list .aui-list-item-inner {
    position: relative;
    min-height: 1rem;
    padding-right: 0.75rem;
    width: 100%;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    display: -webkit-box;
    display: -webkit-flex;
    display: flex;
    -webkit-box-flex: 1;
    -webkit-box-pack: justify;
    -webkit-justify-content: space-between;
    justify-content: space-between;
    -webkit-box-align: center;
    -webkit-align-items: center;
    align-items: center;
}
.aui-list-item-inner.aui-list-item-arrow {
    overflow: inherit;
    padding-right: 1.5rem;
}
</style>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp"%>
</head>

<script type="text/javascript">
	$(document).ready(

			function() {
				$.ajax({
					type : "post",
					url : $("#path").val() + "/order/getOrderSum",
					dataType : "json",
					success : function(res) {
						if (res[0].waitPayQty != 0) {
							$(".liw a").eq("0").find("i").show().text(
									res[0].waitPayQty);
						}
						if (res[0].waitReceiptQty != 0) {
							$(".liw a").eq("1").find("i").show().text(
									res[0].waitReceiptQty);
						}
						if (res[0].waitEvaluateQty != 0) {
							$(".liw a").eq("2").find("i").show().text(
									res[0].waitEvaluateQty);
						}
						if (res[0].canRefundQty != 0) {
							$(".liw a").eq("3").find("i").show().text(
									res[0].canRefundQty);
						}
						//优惠券数量
						if (res[0].prosum != 0) {
							$(".my_list .f_02").show().text(res[0].prosum);
						}
					},
					error : function() {
						showErrorMsg("网络连接超时，请您稍后重试");
					}
				});
			}

	);
	function quit() {
		$.dialog({
			content : '确定退出登录吗？',
			title : '众聚猫提示',
			ok : function() {
				setTimeout(function() {
					window.location.href = '${path }/customer/logout';
				}, 500);
			},
			cancel : function() {

			},
			lock : false
		});
	}

	function notLive() {
		$.dialog({
			content : "尊敬的${exitUser.userName}你好，请先购买激活区商品激活账户。",
			title : '众聚猫提示',
			time : 3000,
		});

	}
	function notLi() {
		$.dialog({
			content : "尊敬的${exitUser.userName}您好，请先购买激活区商品激活账户",
			title : '众聚猫提示',
			time : 3000,
		});

	}
</script>
<body>
	<input type="hidden" id="path" value="<%=path%>" />
	<input type="hidden" id="basePath" value="<%=basePath%>" />
	<input type="hidden" id="shangJiaXiaoHao" value="${shangJiaXiaoHao }" />
	<input type="hidden" id="jiaTingStatus" value="${jiaTingStatus }" />
	<input type="hidden" id="cusBusStatus" value="${cusBusStatus }" />
   <!--众聚猫个人中心head部分start-->
	<section class="aui-content user-info aui-text-center aui-margin-b-15">
	 <c:if test="${exitUser.icon == null || exitUser.icon == ''}">
        <img src="${path }/commons/images/head3.png" class="avatar aui-img-round">
        <h2 class="aui-margin-t-15">
        	<c:if test="${exitUser !=null }">
				<c:if
					test="${exitUser.nickName  !=null &&  exitUser.nickName  !='' }">
						${fn:escapeXml(exitUser.nickName)}
						<c:if test="${userStars !=null && userStarts != 0}">
						<c:forEach var="startIndex" begin="1" end="${userStars}"
							step="1">
							<img class="my_star" src="${path }/commons/images/star.png" />
						</c:forEach>
					</c:if>
				</c:if>
				<c:if
					test="${exitUser.nickName  ==null ||  exitUser.nickName  =='' }">
						${fn:escapeXml(exitUser.userName)}
				</c:if>
			 </c:if>
        </h2>
        <div class="aui-row aui-margin-t-15">
			<c:if test="${exitUser !=null }">ID:${fn:escapeXml(exitUser.userId)}</c:if>
        </div>
     </c:if>
    </section>
    <!--众聚猫个人中心head部分end-->
    <!--众聚猫个人中心常用部分start-->
    <section class="aui-content aui-margin-b-10">
        <div class="aui-grid">
            <div class="aui-row">
            <c:if test="${exitUser.type!=0 }">
              <a href="${path }/wealth/toweaithCenter">
                <div class="aui-col-xs-3">
                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/cfzx.png" class="aui_grzx_icon"></i>
                    <div class="aui-grid-label">商家中心</div>
                </div>
             </c:if>
              <c:if test="${exitUser.type==0 }">
                <div class="aui-col-xs-3">
                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/no_sjzx.png" class="aui_grzx_icon"></i>
                    <div class="aui-grid-label">商家中心</div>
                </div>
               </a>
             </c:if>   
             <%-- <a href="${path }/QrCodeMaContorller/thqrCode?rcodeType=0"> 
                <div class="aui-col-xs-3">
                    <a href="${path }/QrCodeMaContorller/thqrCode?rcodeType=0"><i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/fxtj.png" class="aui_grzx_icon"></i>
                    <div class="aui-grid-label">分享推荐</div>
                </div>
             </a>   --%>
             <a href="${path }/operate/list"> 
                <div class="aui-col-xs-3">
                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/czjl.png" class="aui_grzx_icon"></i>
                    <div class="aui-grid-label">操作记录</div>
                </div>
             </a>
             <a href="${path }/notice/infolist"> 
                <div class="aui-col-xs-3">
                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/pttz.png" class="aui_grzx_icon"></i>
                    <div class="aui-grid-label">平台通知</div>
                </div>
             </a>
             <a href="${path}/setting/index">
                <div class="aui-col-xs-3">
                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/zhsz.png" class="aui_grzx_icon"></i>
                    <div class="aui-grid-label">账户设置</div>
                </div>
             </a>
			</div>
        </div>
    </section>
   <c:if test="${userMobileLength!=11}">
     <div class="bangding"> <a href="${path}/customer/toUpdateMobile" class="bd_href">您暂未绑定手机,部分功能将无法使用 点击绑定</a></div>
    </c:if> 
    <!--众聚猫个人中心财富中心部分start-->
    <section class="aui-content aui-margin-b-10" style="width: 92%;margin: 0 auto;border-radius: 10px;">
        <ul class="aui-list aui-list-noborder">
            <div class="aui-list-header">
               <div class="aui-list-item-title">财富中心</div>
            </div>
            <div class="aui-hr aui-clearfix" style="width: 92%;margin: 0 auto"></div>
            <div class="aui-grid">
            <div class="aui-row">
              <a href="${path}/wealth/detail_grzxfxsr">
                <div class="aui-col-xs-3" style="width: 50%">
                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/fxsr.png" class="aui_grzx_icon"></i>
                    <div class="aui-grid-label">分享收入</div>
                </div>
              </a>
              <a href="${path}/wealth/detail_djq">
                <div class="aui-col-xs-3" style="width: 50%">
                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/djq.png" class="aui_grzx_icon"></i>
                    <div class="aui-grid-label">M券</div>
                </div>
              </a>
			</div>
        </div>
        </ul>
    </section>
    <!--众聚猫个人中心财富中心部分end-->
    <!--众聚猫个人中心订单中心部分start-->
    <section class="aui-content" style="width: 92%;margin: 0 auto;border-radius: 10px">
        <ul class="aui-list aui-list-noborder">
          <a href="${path }/cusOrder/toMyAllOrder?pageNow=1&status=" style="color: #000">
            <div class="aui-list-header">
                    <div class="aui-list-item-title">订单中心</div>
                    <div class="aui-list-item-right">
						<div class="aui-list-item-inner aui-list-item-arrow"></div>
					</div>
            </div>
          </a>
            <div class="aui-hr aui-clearfix" style="width: 92%;margin: 0 auto"></div>
            <div class="aui-grid">
            <div class="aui-row">
               <a href="${path }/cusOrder/toMyAllOrder?pageNow=1&status=1" style="color: #000">
                <div class="aui-col-xs-3" style="width: 50%">
                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/dfk.png" class="aui_grzx_icon"></i>
                    <div class="aui-grid-label">待付款</div>
                </div>
               </a>
               <a href="${path }/cusOrder/toMyAllOrder?pageNow=1&status=81" style="color: #000">
                <div class="aui-col-xs-3" style="width: 50%">
                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/dsh.png" class="aui_grzx_icon"></i>
                    <div class="aui-grid-label">待收货</div>
                </div>
               </a>
			</div>
        </div>
        </ul>
    </section>
    <!--众聚猫个人中心订单中心部分end-->
    <!--众聚猫个人中心个人中心部分start-->
    <section class="aui-content aui-margin-b-10" style="width: 92%;margin: 10px auto;border-radius: 10px">
        <ul class="aui-list aui-list-noborder">
            <div class="aui-list-header">
               <div class="aui-list-item-title">个人中心</div>
            </div>
            <div class="aui-hr aui-clearfix" style="width: 92%;margin: 0 auto"></div>
            <div class="aui-grid">
            <div class="aui-row">
               <%-- <a href="${path }/team/index">
                <div class="aui-col-xs-3" style="width: 33%">
                   <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/myteam.png" class="aui_grzx_icon"></i>
                    <div class="aui-grid-label">我的团队</div>
                </div>
               </a> --%>
               <a href="${path }/cusAddress/findAllAddress">
                <div class="aui-col-xs-3" style="width: 33%">
                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/shdz.png" class="aui_grzx_icon"></i>
                    <div class="aui-grid-label">收货地址</div>
                </div>
               </a>
               <a href="${path }/trade/toSetting">
                <div class="aui-col-xs-3" style="width: 33%">
                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/zfmm.png" class="aui_grzx_icon"></i>
                    <div class="aui-grid-label">支付密码</div>
                </div>
               </a>
               <a href="<%=path %>/cusInfo/toVerify">
                <div class="aui-col-xs-3" style="width: 33%">
                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/smrz.png" class="aui_grzx_icon"></i>
                    <div class="aui-grid-label">实名认证</div>
                </div>
                </a>
               <%--  <a href="<%=path %>/cusInfo/toVerify">
		      	<li>
					<li class="aui-list-item">
			                <div class="aui-list-item-label">
			                  	实名认证
			                </div> 
						<c:choose>
							<c:when test="${empty flag }">
					            <div class="aui-list-item-input">
					                <i class="aui-list-item-inner">未认证</i>
					            </div>
				            </c:when>
				            <c:otherwise>
					            <div class="aui-list-item-input">
					                <i class="aui-list-item-inner">已认证</i>
					            </div>
				            </c:otherwise>
			            </c:choose>
	            	</li>
	        	</li>
	        </a> --%>
			</div>
        </div>
        </ul>
    </section>
    <!--众聚猫个人中心中心部分end-->
    <!--众聚猫个人中心客服中心部分start-->
    <section class="aui-content">
        <ul class="aui-list aui-list-in aui-margin-b-15">
           <li class="aui-list-item">
                <div class="aui-list-item-label-icon">
                    <i class="aui-iconfont" style="font-size:0.7rem">客服中心</i>
                </div>
            </li>
          <a href="${path }/suggest/toHelpCenter">
               <li class="aui-list-item">
                <div class="aui-list-item-label-icon">
                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/bzzx.png" style="width: 80%"></i>
                </div>
                <div class="aui-list-item-inner aui-list-item-arrow">
					<div class="aui-list-item-title">帮助中心</div>
                </div>
				</li>
          </a>
           <a href="${path }/suggest/find">
            <li class="aui-list-item">
                <div class="aui-list-item-label-icon">
                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/yjfk.png" style="width: 80%"></i>
                </div>
                <div class="aui-list-item-inner aui-list-item-arrow">
                    <div class="aui-list-item-title">意见反馈</div>
                </div>
            </li>
			</a>
            <a href="${path }/kf/contact">
            <li class="aui-list-item">
                <div class="aui-list-item-label-icon">
                   <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/lxkf.png" style="width: 80%"></i>
                </div>
                <div class="aui-list-item-inner aui-list-item-arrow">
                    <div class="aui-list-item-title">联系客服</div>
                </div>
            </li>
			</a>
            <a href="${path }/operate/list">
            <%-- <li class="aui-list-item">

                <div class="aui-list-item-label-icon">
                   <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/czjl.png" style="width: 80%"></i>
                </div>
                <div class="aui-list-item-inner aui-list-item-arrow">
                    <div class="aui-list-item-title">操作记录</div>
                </div>
            </li> --%>
			</a>
			<c:if test="${exitUser.loginType!=1 }">
			 	<div class="aui-btn aui-btn-danger aui-btn-block"><a href="javascript:;" onclick="quit();" style="color:#fff">退出登录</a></div>
			</c:if>
			<a>
	             <li class="aui-list-item"></li>
			</a>
        </ul>
<!--         <div class="exit">
			<a href="javascript:;" onclick="quit();">退出登录</a>
		</div> -->
    </section>
    <!--众聚猫个人中心客服中心部分end-->
    <!--众聚猫个人中心底部部分start-->
    <div class="footer-m"> 
     <%@include file="/WEB-INF/views/commons/navigation.jsp"%>
    </div> 
    <!--众聚猫个人中心底部部分end-->
<script type="text/javascript" src="${staticFile_s}/commons/js/api.js"></script>
</body>
</html>
