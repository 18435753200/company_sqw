<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
<title>平台交易费设置</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	 <div class="wrap">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->

		<!-- 右边 start -->
		<div class="right">
			<div class="c21">
			<div class="title">
				<p>卖家中心&nbsp;&gt;&nbsp; </p>
				<p>基础设置&nbsp;&gt;&nbsp; </p>
				<p class="c1">平台交易费设置</p>
			</div>
	     </div>
			<div class="blank5"></div>
			<div class="cont">
			<form action="${path}/infrastructure/updateTradeFee" id="formfee">
			<input type="hidden" name="id" id="id" value="${tradeFee.id}"/>
			<input type="hidden" name="type" id="type" value="1"/>
			普通交易:
				<ul class="ul_vertical">
						<p class="p1">收取交易费用户ID：</p>
						<input type="text" id="userId" name="userId" value="${tradeFee.userId}" />
						<br/><br/>
						<p class="p1">交易费比率：</p>
						<input type="text" id="tradeFee" name="tradeFee" value="${tradeFee.tradeFee}" />%
						<br/><br/>
					<input type="button"  class="fabu_btn" onclick="operateTradeFee()" value="保存" ></input>
					
				</ul>
				</form>
					<form action="${path}/infrastructure/updateTradeFee" id="formfee1">
					<input type="hidden" name="id" id="id" value="${tradeFee1.id}"/>
			<input type="hidden" name="type" id="type" value="2"/>
			家庭号交易:
				<ul class="ul_vertical">
					<li>
						<p class="p1">收取交易费用户ID：</p>
						<input type="text" id="userId" name="userId" value="${tradeFee1.userId}" />
						<br/><br/>
					</li>
					<li>
						<p class="p1">交易费比率：</p>
						<input type="text" id="tradeFee1" name="tradeFee" value="${tradeFee1.tradeFee}" />%

					</li>
					<input type="button"  class="fabu_btn" onclick="operateTradeFee1()" value="保存" ></input>
				</ul>
				</form>
			</div>
			
		
		</div>			 
		<!-- 右边 end -->
	</div>


		
<div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
   </body>
<script type="text/javascript">
 function operateTradeFee(){
	 var tradeFee = $("#tradeFee").val();
	 if((!RegExp("^\\d{1,5}\\.\\d+$").test(tradeFee) && !RegExp("^\\d{1,5}\\.?$").test(tradeFee))|| Number(tradeFee)<=0){
		 alert("请正确填写交易费率！");
		 return;
	 }
	 $("#formfee").submit();

 }
 function operateTradeFee1(){
	 var tradeFee = $("#tradeFee1").val();
	 if((!RegExp("^\\d{1,5}\\.\\d+$").test(tradeFee) && !RegExp("^\\d{1,5}\\.?$").test(tradeFee))|| Number(tradeFee)<=0){
		 alert("请正确填写交易费率！");
		 return;
	 }
	 $("#formfee1").submit();

 }
</script>
</html>