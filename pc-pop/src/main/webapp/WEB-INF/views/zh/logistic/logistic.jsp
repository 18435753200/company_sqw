<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	  <title>商家后台管理系统-物流管理</title>
      <%@include file="/WEB-INF/views/zh/include/base.jsp"%>
	  <link rel="stylesheet" type="text/css" href="${path}/css/zh/user.css">
      <link rel="stylesheet" type="text/css" href="${path}/css/zh/juese.css">
	</head>
	<body>
		

        <%@include file="/WEB-INF/views/zh/include/header.jsp"%>	
	    
		<div class="wrap">
			<%@include file="/WEB-INF/views/zh/include/leftProduct.jsp" %>
			<form  id="SearchFrom"  action="${path}/brand/list" method="post">
			     <input name="sortFields"   type="hidden" value="${page.sortFields}"/> 
		         <input name="order"  type="hidden" value="${page.order}"/> 
			</form>
			
			
		       	<!-- 右边 start -->
			<div class="right f_l">
				<div class="title">
					<p class="c1">物流模板管理 </p>
					<div class="clear"></div>
				</div>
				
				<div class="blank5"></div>
				
				<div id="modelCont">
				
				</div>
				<!-- 右边 end -->
			    
			</div>	
			
		</div>
		<!-- 底部 start -->
     	<%@include file="/WEB-INF/views/zh/include/last.jsp"%>
	<!-- 底部 end -->
	
	
   </body>
   <script type="text/javascript" src="${path}/js/brand/brand.js"></script>
   <script type="text/javascript">
   			
   $(document).ready(function(){
	   clickSubmit(1);
   });
   
	function clickSubmit(page){
		
		var brandArray = new Array();
		
		if(page!=undefined && "" != page){
			brandArray.push('page='+page);
		}
		
		$.ajax({
			url : '${path}'+"/order/findAllGeneralLogisTpl",
			type : 'POST',
			data : brandArray.join('&')+'&math='+Math.random(),
			success: function (msg) {
				 $('#modelCont').html(msg);
			}, 
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
				alert("服务器忙，请稍后再试!"); 
			} 
		});
	}
	 </script> 
</html>