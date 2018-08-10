<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
<title>个人周期分红设置</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/B2C_orderlist.css">
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/wuliu_alert.css"/>
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>
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
				<p class="c1">个人周期分红设置</p>
			</div>
	     </div>
			<div class="blank5"></div>
			<div class="cont" id="plan">
			<input type="hidden" name="compDividendId" id="compDividendId" value="${comDiv.compDividendId}"/>
				<ul class="ul_vertical">
					<li>
						<p class="p1">订单支付截止时间：</p>
						<input type="hidden" name="cyclePlanId" value="${plan.cyclePlanId}" id="cyclePlanId" />
						<input type="text" id="orderEndTime" name="orderEndTime" value="<fmt:formatDate value="${plan.orderEndTime}" type="both"/>" 
							class="rl" onClick="WdatePicker()" style="margin-left: 20px;">
						<br/><br/>
					</li>
					<li>
						<p class="p1">设置订单类型：</p>
						<!-- <select id="orderType" style="margin-left: 20px;">
							<option value="">--全部--</option>
							<option value="1">普通订单</option>
							<option value="38">红旗宝交易订单</option>
							<option value="39">幸福购订单</option>
							<option value="40">家庭号订单</option>
						</select> -->
						<select id="orderType" style="margin-left: 20px;">
							<option value="">--全部--</option>
							<option value="1">普通订单</option>
							<option value="38">红旗宝交易订单</option>
							<option value="39">幸福购订单</option>
							<option value="40">家庭号订单</option>
						</select>
						<%-- <c:if test="${updateFlag == true }">
						<c:if test="${plan.cyclePlanId}"></c:if>
						
						
						</c:if> --%>
						
						
						<br/><br/>
					</li>
					<li>
						<p class="p1">设置执行时间：</p>
						<input type="text" id="executeStartTime" name="executeStartTime" value="<fmt:formatDate value="${plan.executeStartTime}" type="both"/>" 
							class="rl" onClick="WdatePicker()" style="margin-left: 20px;">
						<br/><br/>
					</li><li>
						<p class="p1">设置执行批次：</p>
						<input type="text" id="executeBatch" name="executeBatch" value="${plan.executeBatch}" 
							style="margin-left: 20px;"/>
						<br/><br/>
					</li>
					<li>
						<p class="p1">设置批次时间：</p>
						<input type="text" id="executeBatchTime" name="executeBatchTime" value="${plan.executeBatchTime}" 
							style="margin-left: 20px;"/>&nbsp;小时
						<br/><br/>
					</li>
					<li><p class="p1"></p></li>
					<li>
					<input type="button"  class="fabu_btn" onclick="operateCyclePlan()" value="下一步" ></input>
					</li>
				</ul>
				
			</div>
			<div class="tab-bd">
		
			</div>
		
		</div>			 
		<!-- 右边 end -->
	</div>


		
<div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
   </body>
<script type="text/javascript">
 function operateCyclePlan(){
	 var cyclePlanId = $("#cyclePlanId").val();
	 var executeBatch = $("#executeBatch").val();
	 var executeBatchTime = $("#executeBatchTime").val();
	 var orderEndTime = $("#orderEndTime").val();
	 var executeStartTime = $("#executeStartTime").val();
	 var orderType = $("#orderType").val();alert(orderType)
	 if(!RegExp("^\\d{1,3}$").test(executeBatch) || executeBatch =="0"){
		 alert("请正确填写执行批次！");
		 return;
	 }
	 if(!RegExp("^\\d{1,5}$").test(executeBatchTime) || executeBatchTime =="0"){
		 alert("请正确填写批次时间！");
		 return;
	 }
	  $.ajax({
			type:'post',
			url:'../infrastructure/updateFhCyclePlan',
			data: 'cyclePlanId='+cyclePlanId+'&executeBatch='+executeBatch+'&executeBatchTime='+executeBatchTime+'&orderEndTime='+orderEndTime+"&executeStartTime="+executeStartTime+"&orderType="+orderType,
			success:function(data){
				if(data == 'error1'){
					tipMessage("该阶段内已有周期计划！",function(){
					});
				}else if(data == 'error2'){
					tipMessage("创建失败，请检查后重试！",function(){
					});
				}else{
					tipMessage("请选择要操作的订单！",function(){
						window.location.href="${path}/infrastructure/toUpdateFhCyclePlanImpl?cyclePlanId="+data;
						
						//location.href='../infrastructure/toAddFhCyclePlanImpl?'+data;
					});
				}
				
			}
		}); 
 }
</script>
</html>