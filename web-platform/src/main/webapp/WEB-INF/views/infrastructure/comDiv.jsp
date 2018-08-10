<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
<title>企业分红设置</title>
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
				<p class="c1">企业分红设置</p>
			</div>
	     </div>
			<div class="blank5"></div>
			<div class="cont">
			<input type="hidden" name="compDividendId" id="compDividendId" value="${comDiv.compDividendId}"/>
				<ul class="ul_vertical">
					<li>
						<p class="p1">周期返还时间：</p>
						<input type="text" id="retDate" name="retDate" value="${comDiv.retDate}" style="margin-left: 20px;"/>&nbsp;天
						<br/><br/>
					</li>
					<li>
						<p class="p1">周期返还折扣：</p>
						<input type="text" id="retZk" name="retZk" value="${comDiv.retZk}" style="margin-left: 20px;"/>&nbsp;折
						<br/><br/>
					</li>
					<li><p class="p1"></p></li>
					<li>
					<input type="button"  class="fabu_btn" onclick="operatecomDiv()" value="保存" ></input>
					</li>
				</ul>
				
			</div>
			
		
		</div>			 
		<!-- 右边 end -->
	</div>


		
<div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
   </body>
<script type="text/javascript">
 function operatecomDiv(){
	 var retDate = $("#retDate").val();
	 var retZk = $("#retZk").val();
	 var compDividendId = $("#compDividendId").val();
	 if((!RegExp("^\\d{1,5}\\.\\d+$").test(retZk) && !RegExp("^\\d{1,5}\\.?$").test(retZk))|| Number(retZk)<=0){
		 alert("请正确填写返还折扣！");
		 return;
	 }
	 if(!RegExp("^\\d{1,3}$").test(retDate) || retDate =="0"){
		 alert("请正确填写返还时间！");
		 return;
	 }
	 $.ajax({
			type:'post',
			url:'../infrastructure/updateCompanyDividend',
			data: 'retDate='+retDate+'&retZk='+retZk+'&compDividendId='+compDividendId,
			success:function(data){
				if(data=='1'){
					tipMessage("操作成功！",function(){
						location.href='../infrastructure/companyDividend';
					});
				}else{
					tipMessage("操作失败，请检查后重试！",function(){
					});
				}
				
			}
		});
 }
</script>
</html>