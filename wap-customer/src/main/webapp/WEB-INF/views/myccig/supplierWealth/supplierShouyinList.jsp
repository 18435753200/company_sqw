<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<link rel="stylesheet" href="${staticFile_s}/commons/css/mui.min.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/add_syy.css">
  <script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
  <script type="text/javascript" src="${staticFile_s}/commons/js/jquery-1.8.0.min.js"></script>  
  <script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert2.js"></script> 

<title>收银员</title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp"%>
<%@include file="/WEB-INF/views/commons/commonsIco.jsp"%>
<%@include file="/WEB-INF/views/commons/base.jsp" %>
</head>
<body>
<input type="hidden" id="path" value="<%=path %>"/>
	<header id="header" class="mui-bar mui-bar-nav">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left" href="${path }/wealth/toweaithCenter" style="color: #fff";></a>
		<h1 class="mui-title">收银员</h1>
	</header>
	<section class="add_syy">
		<form >
			<c:forEach items="${pageBean.result }" var="getShouyin">
				<div class="mui-card">
					<div class="mui-card-content">
						<div class="mui-card-content-inner">
							<ul class="mui-table-view mui-grid-view">
								<li class="mui-table-view-cell mui-media mui-col-xs-6">
									<div class="mui-media-body">收银员</div>
								</li>
								<li class="mui-table-view-cell mui-media mui-col-xs-6"
									style="width: 23%;">
									<div class="mui-media-body" style="color: #000;">${getShouyin.kfName }
									<c:if test="${getShouyin.onlineType==1 }">
									<img
											src="${path }/commons/img/shzxtp/dg.png"
											style="vertical-align: middle; margin-left: 10px;" />
									</c:if>
									</div>
								</li>
								<li class="mui-table-view-cell mui-media mui-col-xs-6">
									<div class="mui-media-body">手机号</div>
								</li>
								<li class="mui-table-view-cell mui-media mui-col-xs-6"
									style="width: 30%;">
									<div class="mui-media-body" style="color: #000;">${getShouyin.mobile }</div>
								</li>
								<li class="mui-table-view-cell mui-media mui-col-xs-1"
									style="position: absolute; right: 0.5rem;">
									<a onclick="deleteSy(${getShouyin.id})" id="icon-minus">
									<span class="mui-icon mui-icon-trash"></span></a></li>
							</ul>
							<ul class="mui-table-view mui-grid-view">
								<li class="mui-table-view-cell mui-media mui-col-xs-6" style="width: 50%;">
									<div class="mui-input-row mui-radio mui-left">
										<label>默认</label>
										<c:choose>
											<c:when test="${getShouyin.isDefault}">
												<input type="radio" checked="checked" onclick="cancelDefault(${getShouyin.id })">
											</c:when>
											<c:otherwise>
												<input type="radio" onclick="setDefault(${getShouyin.id })">
											</c:otherwise>
										</c:choose> 
										
									</div>
								</li>
								<li class="mui-table-view-cell mui-media mui-col-xs-6"
									style="width: 50%;">
									<a onclick="toupdateSyInfo(${getShouyin.id })"><span class="mui-icon mui-icon-compose" style="float: right;"></span></a>
								</li>
								
							</ul>
						</div>

						<li class="mui-table-view-cell" style="list-style:none;border-top: 1px solid #dedede;color: #676767;">
							<a class="mui-navigate-right" href="${path }/wealth/supplierMoneyJL?id=${getShouyin.id }">收银记录</a></li>
					</div>
				</div>
			</c:forEach>
		</form>
	</section>
	<P>&nbsp;</P>
	<a href="${path}/wealth/toAddShouYinewm"><button type="button" class="mui-btn mui-btn-danger mui-btn-block"
		style="position: fixed; bottom: 1rem; background: #e60012; z-index: 2; width: 90%; margin: 0 5%; border-radius: 50px; height: 3.5rem;">添加收银员</button></a>
</body>

<script type="text/javascript">

	function cancelDefault(id){
			$.ajax({
				type: 'POST',
				url: $("#path").val()+'/wealth/cancelDefault',
				dataType: 'json',
				async:false,
				data:"id="+ id,
				success: function(data) {
					if(data==1){
						showMsg("取消默认成功");
						
					}else{
						showMsg("服务器异常请稍后再试");
						
					}
					},
				error: function() {
				}
			});
	}
	function setDefault(id){
		$.ajax({
			type: 'POST',
			url: $("#path").val()+'/wealth/setDefault',
			dataType: 'json',
			async:false,
			data:"id="+ id,
			success: function(data) {
				if(data==1){
					showMsg("设置默认成功");
				}else if(data==0){
					showMsg("设置默认失败");
				}else if(data==2){
					showMsg("默认收银员最多设置3个");
					
				}
				},
			error: function() {
			}
		});
	}
	function toupdateSyInfo(id){
		window.location = "${path}/wealth/toupdateSyInfo?id="+id;
	}
	
	
	
	function deleteSy(id){
		$.dialog({
	        content : '确定删除吗？',
	        title : '众聚猫提示',
	        ok : function() {
	    		$.ajax({
	    			type: 'POST',
	    			url: $("#path").val()+'/wealth/deleteSy',
	    			dataType: 'json',
	    			async:false,
	    			data:"id="+ id,
	    			success: function(data) {
	    				if(data==1){
	    					showMsg("删除成功");
	    					
	    				}else {
	    					showMsg("服务器异常");
	    				}
	    				},
	    			error: function() {
	    			}
	    		});
	    	
	        },
	        cancel : function() {
	            
	        },
	        lock : false
	    });
	}
	function showMsg(content){
		$.dialog({
	        content : content,
	        title : '众聚猫提示',
	        time: 2000,
		});
	}
</script>
</html>
