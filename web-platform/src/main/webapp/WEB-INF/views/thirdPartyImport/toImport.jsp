<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<!doctype html>
<html lang="en">
	<head>
	  <meta charset="UTF-8">
	  <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	  <title>UNICORN-用户管理</title>
		<%@include file="/WEB-INF/views/include/base.jsp"%>
		<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css">
		<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
	  <script type="text/javascript">
		  $(document).ready(function(){
			var v = '${message}';
			  $("#import").removeClass("disable").removeAttr('disabled');
			  if(v!=''){
				  
				  alert(eval(v)[0].msg);
			  }

		  });
		function import_(){
			var v = $("#file").val();
			if(v==''){
				alert("请选择文件");
				return;
			}
			document.getElementById("import").setAttribute("disabled",true);
			$("#form").submit();
		}
			
	 </script> 
	</head>
	<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
	<div class="center">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
		</div>

		<!-- 左边 end -->
		<div class="right">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>订单管理&nbsp;&gt;&nbsp; </p>
					<p class="c1">第三方订单导入</p>
				</div>
			</div>
			<form id="form"  action="${path}/thirdPartyImport/importFile" method="post"  enctype="multipart/form-data">
			<div class="xia">
				<p class="p1">
					<span>文件导入:</span>
					<input type="file" name="file" id="file" >

				</p>
				<p class="p2">
					<button type="button" id="import" onclick="javascript:import_();">导入</button>
				</p>
			</div>
			</form>


		</div>




	</div>


	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	</body>
</html>