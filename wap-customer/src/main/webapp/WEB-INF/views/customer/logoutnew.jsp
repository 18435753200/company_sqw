<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>退出登录</title>
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<%@include file="/WEB-INF/views/commons/base.jsp"%>
<%--<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
--%>

<link href="${staticFile_s}/commons/css/newmycss/my.css"
	rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/revision20160606/base.css"
	rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/revision20160606/personal.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
</head>
<body>
<!-- <script type="text/javascript">
		window.location.href = 'http://m.zhongjumall.com/customer/toLogin';
		
</script> -->
<%-- hidden域 --%>
	<input type="hidden" id="path" value="<%=path%>" />
	<input type="hidden" id="basePath" value="<%=basePath%>" />
	
          <div class="container1">
			<div class="con1_head">
				<h3>财富中心</h3>
			</div>
			<div class="con1_center1">
				<dl class="cen_fir">

					<dt>
						<img src="${path }/commons/img/newmyimage/7.png">
					</dt>
					<dd>
						<h2>消费金额</h2>
						<ul>
							<li><img src="${path }/commons/img/newmyimage/8.png"></li>
							<li>0</li>
						</ul>
					</dd>

				</dl>
				<dl class="cen_fir">
					<a href="javascript:">
						<dt>
							<img src="${path }/commons/img/newmyimage/9.png">
						</dt>
						<dd>
							<h2>消费红旗券</h2>
							<ul>
								<li><img src="${path }/commons/img/newmyimage/10.png"></li>
								<li>0</li>
							</ul>
						</dd>
					</a>
				</dl>
				<dl class="cen_fir">
					<a href="javascript:">
						<dt>
							<img src="${path }/commons/img/newmyimage/11.png">
						</dt>
						<dd>
							<h2>可用红旗券</h2>
							<ul>
								<li><img src="${path }/commons/img/newmyimage/10.png"></li>
								<li>0</li>
							</ul>
						</dd>
					</a>
				</dl>
				<dl class="cen_end">
					<a href="javascript:">
						<dt>
							<img src="${path }/commons/img/newmyimage/12.png">
						</dt>
						<dd>
							<h2>未到红旗券</h2>
							<ul>
								<li><img src="${path }/commons/img/newmyimage/10.png"></li>
								<li>0</li>
							</ul>
						</dd>
					</a>
				</dl>
			</div>
			<div class="con1_center2">
				<dl class="cen_fir">
					<a href="javascript:">
						<dt>
							<img src="${path }/commons/img/newmyimage/13.jpg">
						</dt>
						<dd>
							<h2>分红额度</h2>
							<ul>
								<li><img src="${path }/commons/img/newmyimage/8.png"></li>
								<li></li>
							</ul>
						</dd>
					</a>
				</dl>
				<dl class="cen_fir">
					<a href="javascript:">
						<dt>
							<img src="${path }/commons/img/newmyimage/14.png">
						</dt>
						<dd>
							<h2 style="margin-left:30%;">红旗宝</h2>
						</dd>
					</a>
				</dl>
				<dl class="cen_fir">
					<a href="javascript:">
						<dt>
							<img src="${path }/commons/img/newmyimage/15.png">
						</dt>
						<dd>
							<h2>红旗券转账</h2>
						</dd>
					</a>
				</dl>
				<dl class="cen_end">

				</dl>
			</div>
          </div>
		<div class="container2">
			<div class="con1_head2">
				<h3>账户管理</h3>
			</div>
			<div class="con2_center1">
				<dl class="cen_fir">
					<a href="javascript:">
						<dt>
							<img src="${path }/commons/img/newmyimage/16.png">
						</dt>
						<dd>
							<h2>账户设置</h2>
						</dd>
					</a>
				</dl>
				<dl class="cen_fir">
					<a href="javascript:">
						<dt>
							<img src="${path }/commons/img/newmyimage/17.png">
						</dt>
						<dd>
							<h2>实名认证</h2>
						</dd>
					</a>
				</dl>
				<dl class="cen_fir">
					
					<a href="javascript:">
						<dt>
							<img src="${path }/commons/img/newmyimage/18.png">
						</dt>
						<dd>
							<h2>安全设置</h2>
						</dd>
					</a>
				</dl>
				<dl class="cen_end">
					<a href="javascript:">
						<dt>
							<img src="${path }/commons/img/newmyimage/19.png">
						</dt>
						<dd>
							<h2>我的团队</h2>
						</dd>
					</a>
				</dl>
			</div>
		</div>
		<div class="containe3">
			<div class="con1_head3">
				<h3>操作记录</h3>
			</div>
			<div class="con3_center1">
				<dl class="cen_fir">
					<a href="javascript:">
						<dt>
							<img src="${path }/commons/img/newmyimage/20.png">
						</dt>
						<dd>
							<h2>登录记录</h2>
						</dd>
					</a>
				</dl>
				<dl class="cen_fir">
					<a href="javascript:">
						<dt>
							<img src="${path }/commons/img/newmyimage/21.png">
						</dt>
						<dd>
							<h2>消费记录</h2>
						</dd>
					</a>
				</dl>
				<dl class="cen_fir">
					<a href="javascript:">
						<dt>
							<img src="${path }/commons/img/newmyimage/22.png">
						</dt>
						<dd>
							<h2>转账记录</h2>
						</dd>
					</a>
				</dl>
				<dl class="cen_end">
					<dt>
						<img src="javascript:">
					</dt>
					<dd>
						<h2>红旗宝记录</h2>
					</dd>
				</dl>
			</div>
		</div>
		<div class="containe4">
			<div class="con1_head4">

				<h3>订单中心</h3>
				<a href="javascript:">查看全部订单</a>

			</div>
			<div class="con4_center1">
				<dl class="cen4_fir liw">
					<a href="javascript:">
					   
						<dt>
						    <i>0</i> 
							<img src="${path }/commons/img/newmyimage/25.png">
						</dt>
						<dd>
							<h2>待付款</h2>
						</dd>
					</a>
				</dl>
				<dl class="cen4_fir">
					<dt>
						<img src="${path }/commons/img/newmyimage/26.png">
					</dt>
					<dd>
						<h2>待发货</h2>
					</dd>
				</dl>
				<dl class="cen4_fir liw">
					<a href="javascript:">
						<dt>
						    <i>0</i>
							<img src="${path }/commons/img/newmyimage/27.png">
						</dt>
						<dd>
							<h2>待收货</h2>
						</dd>
					</a>
				</dl>
				<dl class="cen4_fir">
					<dt>
						<img src="${path }/commons/img/newmyimage/28.png">
					</dt>
					<dd>
						<h2>已完成</h2>
					</dd>
				</dl>
			</div>
		</div>
		<div class="containe5">
			<div class="con1_head5">
				<h3>客户服务</h3>
				<a></a>
			</div>
			<div class="con5_center1">
				<dl class="cen_fir">
					<a href="${path }/suggest/toHelpCenter">
						<dt>
							<img src="${path }/commons/img/newmyimage/29.png">
						</dt>
						<dd>
							<h2>帮助中心</h2>
						</dd>
					</a>
				</dl>
				<dl class="cen_fir">
					<a href="javascript:">
						<dt>
							<img src="${path }/commons/img/newmyimage/30.png">
						</dt>
						<dd>
							<h2>意见反馈</h2>
						</dd>
					</a>
				</dl>
				<dl class="cen_fir">
					<a href="${path }/kf/contact">
						<dt>
							<img src="${path }/commons/img/newmyimage/31.png">
						</dt>
						<dd>
							<h2>联系客服</h2>
						</dd>
					</a>
				</dl>
				<dl class="cen_end">
					<a href="javascript:">
						<dt>
							<img src="${path }/commons/img/newmyimage/32.png">
						</dt>
						<dd>
							<h2>平台通知</h2>
						</dd>
					</a>
				</dl>
			</div>
		</div>
	
</body>
</html>
