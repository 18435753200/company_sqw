<%@ page language="java" import="java.util.*" pageEncoding="utf8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 左边 start -->
<%  
    String basePath = request.getContextPath();
	request.setAttribute("path",basePath);
	request.setAttribute("pathUrl", "/buyOrder/buyOrder");
%>
<div class="leftly">
 <script type="text/javascript">
    $(document).ready(function(){
		var url='${url}';
		//alert(url);
		if(url.indexOf("dealerbuy/buyorder")!=-1){
			$("#user_info").attr("class","p2 c_cut2");
		}
		/* if(url.indexOf("dealerseller/maichu")!=-1){
			$("#user_account").attr("class","p2 c_cut2");
		} */
	/* 	if(url.indexOf("dealerseller/kuc")!=-1){
			$("#user_role").attr("class","p2 c_cut2");
		} */
		if(url.indexOf("dealerbuy/waitorder")!=-1){
			$("#user_list").attr("class","p2 c_cut2");
		} 
	});
   
	 </script> 
	  <!-- 左边 start -->
		<div class="left f_l">
			<div class="title">
				<p class="f_l ff"><img src="${path}/commons/images/img_t4.png" alt=""></p>
				<p class="f_l p1">买家中心</p>
				<p class="clear"></p>
				
			</div>
			<div class="list_box">
				<div class="demo">
					<h2><p class="p1"><img src="${path}/commons/images/img_t2.png">订单管理</p></h2>
					<div class="p_b">
					
					
					 <c:if test="${ !empty buttonsMap['已合并订单']}" >
	                       <p id="user_info" class="p2"><a href="${path}${ buttonsMap['已合并订单']}"  >已合并订单</a></p>
					   </c:if>
					   
					   
					    <c:if test="${ !empty buttonsMap['待合并订单']}" >
	                       <p id="user_list" class="p2"><a href="${path}${ buttonsMap['待合并订单']}"  >待合并订单</a></p>
					     </c:if> 
					     
					     
						<%-- <p class="p2"><a href="${path}/buyOrder/buyOrder">已合并订单</a></p>
						<p class="p2 c_cut2"><a href="${path}/waitOrder/SumitOrder">待合并订单</a></p> --%>
					</div>
				</div>
			</div>
		</div>
		</div>
		<!-- 左边 end -->