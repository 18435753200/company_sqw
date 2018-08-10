<%@page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%  
    String path = request.getContextPath();
    String url = request.getServletPath();
	request.setAttribute("url",url);
	request.setAttribute("path",path);
	
%>
<%-- <script type="text/javascript" src="${path}/resources/js/app.js">  </script> --%>
	
<html>
	<head>
        <meta charset="utf-8">
        <title>专题情景详情</title>
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
        <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
        <meta name="apple-mobile-web-app-title" content="众聚猫">
        <meta name="format-detection" content="telephone=no">
        <meta name="baidu-tc-cerfication" content="30f757b11897dc4f697eb568cb7eb2a3" />
        <meta name="baidu-site-verification" content="SgTbeKNm8i" />
		<link rel="stylesheet" href="${path }/commons/css/xsj.css">
		<link rel="stylesheet" href="${path }/commons/css/zepto.alert.css"/>
		<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
	</head>
    <body>
    	<section class="column detail">
				<img src="${mix.detailImageUrl}" />
    	</section>  
    	<div  id="box"> 
    		<section class="column detail_shop">
				<ul class="shop_tui">
		            <c:forEach items="${coms}" var="com" varStatus="status">
		            	<c:if test="${com.isDateil == 1 }">
			            	<li  class="<c:if test="${status.index == 0 }">cur</c:if><c:if test="${com.groupNum == mixGroupNum}">no-b</c:if>" >
			            		<a href="javascript:;">
			            		<input type="hidden" class="groupNum" value="${com.groupNum}"/>
			            		<img src="${com.imageUrl}">
			            		</a>
			            		<i><em></em></i>
			            	</li>
			            </c:if>
		            </c:forEach>
		            <li class="shop_cart">
						<a href="javascript:;">
						<input type="hidden" class="groupNum" value="-1"/>
							<span class="ico_tarr"></span>
							<span class="ico_car"><b></b>打包购买</span>
						</a>
						<i><em></em></i>
					</li>
		        </ul>
	    	</section> 
    	</div>
    	
   		<div class="column-l">
		</div>
    	
    	
   		<section class="column" >
   			<c:forEach  items="${comMap}" var="coms" varStatus="status"  >
    			<div class="column_body" <c:if test="${status.index != 0 }">style="display:none;"</c:if> >
		    		<c:forEach items="${coms.value}" var="com">
		    				<input type="hidden" class="comGroupNum${com.groupNum}"/>
			    			<dl class="dl_temp">
								<dt>
									<a href="${path}/item/get/${com.productId}" >
										<img src="${com.imageUrl}" />
									</a>
								</dt>
								<dd>
									<h3><a href="${path}/item/get/${com.productId}" >${com.productName}  ${com.skuName}</a></h3>
									<p class="domeprice">
									   <c:if test="${not empty com.domesticPrice && com.domesticPrice != 0}">
									   <%-- <span class="sdome qjzh">市场价：<b> </b><i>${fn:escapeXml(com.domesticPrice)}</i></span> --%>
									   <%-- <span class="sdome qjzh">市场价：<b> </b><i><fmt:formatNumber value='${fn:escapeXml(com.domesticPrice)}' type="currency" pattern=".00"/></i></span> --%>
									   </c:if>
								    </p>
									<p>
										<span> 	<i><fmt:formatNumber value='${com.price}' type="currency" pattern=".00"/></i></span>
										<a href="javascript:;" onclick="javascript:saveSku('${com.skuId}',${com.skuNum});"></a>
									</p>
								</dd>
							</dl>
		    		</c:forEach>
		    	</div>
	    	</c:forEach>
   			<div class="column_body" style="display:none;" >
	    		<c:forEach items="${coms}" var="com">
	    			<c:if test="${com.isDateil == 1}">
	    				<input type="hidden" class="comGroupNum-1"/>
	    				
		    			<dl class="dl_temp">
							<dt>
								<a  href="${path}/item/get/${com.productId}" >
									<img src="${com.imageUrl}" />
								</a>
							</dt>
							<dd>
								<h3><a  href="${path}/item/get/${com.productId}" >${com.productName}  ${com.skuName}</a></h3>
								<p class="domeprice">
								<c:if test="${not empty com.domesticPrice && com.domesticPrice != 0}">
<%-- 									   <span class="sdome qjzh">市场价：<b> </b><i>${fn:escapeXml(com.domesticPrice)}</i></span> --%>
										   <span class="sdome qjzh">市场价：<b> </b><i><fmt:formatNumber value='${fn:escapeXml(com.domesticPrice)}' type="currency" pattern=".00"/></i></span>
								</c:if>
								</p>
								<p>	
									<span> 	<i><fmt:formatNumber value='${com.price}' type="currency" pattern=".00"/></i></span>
									<a href="javascript:;" onclick="javascript:saveSku('${com.skuId}',${com.skuNum});">${status.index} </a>
								</p>
							</dd>
						</dl>
					</c:if>
	    		</c:forEach>
	    	</div>
   		</section>
  		<div class="no-foot"></div>
  		<%@include file="/WEB-INF/views/commons/navigation.jsp"%>
	 	<script src="${path }/commons/js/zepto.min.js"></script>			
		<script src="${path }/commons/js/jqueryAlert/zepto.alert.js"></script>

		
		
	<script type="text/javascript">
	function showError(showMessage) {
		// $(".error_tips").removeClass("hide");
		// $(".error_tips").html(errormsg);
		$.dialog({
			content : showMessage,
			title : '众聚猫提示',
			time : 1000,
		});
	}
	function toProdDetail(productId){
		console.log("\"productId\":"+productId);
		/*window.ccigmall_b2c.jumpToProductDetails("{\"productId\":"+productId+",\"pageType\":\"\",\"startDate\":\"\",\"endDate\":\"\"}");*/
		window.open("${path}/item/get/"+productId);

	}
	function saveSku(skuId,num){ 
		var condition = "skuId="+skuId+"&number="+num+"";
		   $.ajax({
				type : "post",
				url :"${path}/cart/addItem",
				data : condition,
				dataType : "text",
				success : function(addCartFlag){
					var showMessage = "系统繁忙，请稍候重试";
					switch(addCartFlag){
					case '20000':	// 添加成功
											
						//弹出添加成功信息
						showMessage = "添加购物车成功！";
						break;
					
					case '50001':	// 库存不足
						showMessage = "库存不足！添加购物车失败！";
						break;
					
					case '50002':	// 购物车内商品数量超过100件
						showMessage = "购物车内商品数量超过100件,请结算后添加！";
						break;
					}
					
					showError(showMessage);
				}
			}); 
	
	
	}
	$(function(){
	  var oDome=$('#box');
	  var oSpan=oDome.find('.shop_tui li');
	  var oP=oDome.find('.shop_list .column_body');
	  oSpan.click(function(){
		   $(this).addClass('cur').siblings().removeClass('cur');
		   console.log($(this).find('.groupNum').val());
		   
		   var num = $(this).find('.groupNum').val();
		   $('.column_body').hide();
		   console.log($('.comGroupNum'+num));
		   $('.comGroupNum'+num).parent('.column_body').show();
		  
	   });
	})

	</script>
	</body>
</html>