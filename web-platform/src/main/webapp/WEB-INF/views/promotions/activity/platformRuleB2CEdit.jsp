<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建平台活动规则-促销活动</title>
 <%@include file="/WEB-INF/views/include/base.jsp"%> 
<link href="${path}/commons/css/reset.css" rel="stylesheet"
	type="text/css">
<link href="${path}/commons/css/promotion.css" rel="stylesheet"
	type="text/css">

<link rel="stylesheet" type="text/css"
	href="${path }/commons/css/coupon.css" />
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/platformRule_edit_fn.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(".promotion-checkbox").click(function(){
		var value = $("input[name='triggerCondition']:checked").val();
		if(value == 101){
			$("#promotion-condition").show();
			//发送ajax请求获取数据
			$.ajax({//获取可选的优惠券
	            type : 'post',
	            url : "../platformRule/getChannels",
	            dataType : "json",
	            success : function(data) {
	            	$("#channel").html("");
	            	var $li = ("<option  value=''>请选择</option>");
	            	$("#channel").append($li);
	            	for(var obj in data){
	            		var channelCode = data[obj].channelCode;
	            		var channelName = data[obj].channelName;
    	            	var $li = $("<option value="+channelCode+">"+channelName+"</option>");
                		$("#channel").append($li);
	            	}
	            }
			});
			
			$.ajax({//获取可选的优惠券
	            type : 'post',
	            url : "../platformRule/getgifts",
	            dataType : "json",
	            success : function(data) {
	            	$("#gift").html("");
	            	var $li = ("<option value=''>请选择</option>");
	            	$("#gift").append($li);
	            	for(var obj in data){
	            		var couponruleid = data[obj].mainrulename;
	            		var couponrulename = data[obj].couponrulename;
    	            	var $li = $("<option value="+couponruleid+">"+couponrulename+"</option>");
                		$("#gift").append($li);
	            	}
	            }
			});
			
		}else{
			$("#promotion-condition").hide();
		}
	});
	$("#platform").change(function(){
		var accessMode = $("#platform").val();
		if(accessMode == 1){
			$("#conditions input").last().hide();
			$("#conditions label").last().hide();
		}else {
			$("#conditions input").last().show();
			$("#conditions label").last().show();
		}
	});
	$("#promotion-condition .inline-box").each(function(){
		$(".selqd").click(function(){
			$(this).change(function(){
				var inputS = $(this).val();
				var nextinputS=$(this).prevAll().find(".selqd option:selected");
				if('' != inputS){
					$(this).next().hide();
				}else{
					$(this).next().text("请选择渠道").show();
				}/* 
				for(var i=0;i<nextinputS.length;i++){
					//console.log(nextinputS.eq(i).val());
					if(nextinputS.eq(i).val()==inputS){
						$(this).next().text("请选择渠道").show();
					}
				} */
			});
		});
	
		$(".selzp").click(function(){
			$(this).change(function(){
				var inputS = $(this).val();
				var nextinputS=$(this).prevAll().find(".selzp option:selected");
				if('' != inputS){
					$(this).next().hide();
				}else{
					$(this).next().text("请选择赠品").show();
				}/* 
				for(var i=0;i<nextinputS.length;i++){
					//console.log(nextinputS.eq(i).val());
					if(nextinputS.eq(i).val()==inputS){
						$(this).next().text("请选择赠品").show();
					}
				} */
			});
		});
	});
	
	var Tradd = $("#btn_addtr");
	var Trdel = $("#btn-deltr");
	/* Tradd.click(function(){
		var btr = $("#promotion-condition .inline-box");
		btr.each(function(index){
		    var inputS=$(this).find(".selqd option:selected").val();
			var inputE=$(this).find(".selzp option:selected").val(); 
		    var nextinputS=$(this).prevAll().find(".selqd option:selected");
		  //  var dd=$(this).prevAll().find("input.start_price");
		    
			if(index==btr.length-1){
				if(inputS==''||inputE==''){
					alert("渠道或赠品不能为空");
					return;
				}else{
					    
					for(var i=0;i<nextinputS.length;i++){
						if(nextinputS.eq(i).val()==inputS){
							alert("渠道不能重复");
						    return;
						}
					}
					   
				  	var btr1 = $("#promotion-condition .inline-box");
				  	btr1.last().clone().appendTo("#promotion-condition");
				    //$(".inline-box:first").find(".inline-box").clone().appendTo("#promotion-condition");
				    $(this).find(".selqd").attr("disabled",true).css({"background":"#eee"});
				    $(this).find(".selqd").next().hide();
				    $(this).find(".selzp").attr("disabled",true).css({"background":"#eee"});
				    $(this).find(".selzp").next().hide();
				    
				    //$("#promotion-condition .inline-box").last().find(".selqd").next().show();
				    //$("#promotion-condition .inline-box").last().find(".selzp").next().show();
					
				}
				  
			}
		 });
		 	  
	});  */
	
	//删除
    Trdel.click(function(){
		var btr = $("#promotion-condition .inline-box");
		if (btr.length == 1) {
		   alert("至少保留一行");
		   return;
		}else{
		   btr.last().remove();	
		   var btr1 = $("#promotion-condition .inline-box");
		   btr1.last().find(".selqd").attr("disabled",false).css({"background":"#FFF"});
		   btr1.last().find(".selzp").attr("disabled",false).css({"background":"#FFF"});
		}
	});
	
});
</script>
</head>
<body>
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->

	<div class="center">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/promotions/leftPage.jsp"%>
		</div>

		<!-- 左边 end -->
		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>促销管理&nbsp;&gt;&nbsp;</p>
					<p>活动管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">活动列表</p>
				</div>

				<div class="blank10"></div>


				<div class="promotion-wrap">
					<div class="promotion-bd manage-promotion">
						<div class="promotion-title">创建平台活动规则</div>

						<form method="post" id="platformRuleAction"
							enctype="multipart/form-data">
							<input type="hidden" name="activeId" id="activeId"
								value="${activeId }">
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-name">规则名称：</label>
									<div class="field">
										<input type="text" name="ruleName" id="ruleName"
											class="promotion-txt" value="${bean.ruleName }"><span></span>
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-type">赠品类型：</label>
									<div class="field">
										<select name="pType" id="pType" class="promotion-sel">
											<option selected="selected" value="1">金券</option>
											<!-- <option value="赠礼品卡">赠礼品卡</option> -->
										</select>
									</div>
								</div>
							</div>
							<div class="form-inline">
								<!-- <div class="form-group">
									<label for="promotion-vipType">会员类型：</label>
									<div class="field">
										<select name="cpsType" id="cpsType" class="promotion-sel">
											<option selected="selected" value="0">自有平台</option>
											<option value="第三方">第三方</option>
								<option value="第三方">不限</option>
										</select>
									</div>
								</div> -->
								<!-- <div class="form-group">
									<label for="promotion-vip">会员等级：</label>
									<div class="field">
										<select name="userGrade" id="userGrade" class="promotion-sel">
											<option selected="selected" value="0">不限</option>
											<option value="注册会员">注册会员</option>
								<option value="白银会员">白银会员</option>
								<option value="金牌会员">金牌会员</option>
										</select>
									</div>
								</div> -->
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-visit">访问方式：</label>
									<div class="field">
										<select name="platform" id="platform" class="promotion-sel">
											<option selected="selected" value="1">客户端</option>
											<option value="2">WAP</option>
										</select>
									</div>
								</div>
								<!-- <div class="form-group">
									<label for="promotion-amount">城市地区：</label>
									<div class="field">
										<select name="areaId" id="areaId" class="promotion-sel">
											<option selected="selected" value="0">不限</option>
											<option value="上海">上海</option>
										</select>
									</div>
								</div> -->
							</div>
							<div class="form-inline">
								<!-- <div class="form-group">
									<label for="promotion-pay">支付方式：</label>
									<div class="field">
										<select name="payment" id="payment" class="promotion-sel">
											<option selected="selected" value="0">不限</option>
											<option value="中行">中行</option>
										</select>
									</div>
								</div> -->
								<div class="form-group">
									<label for="promotion-trigger">触发条件：</label>
									<div class="field" id="conditions">
										<input  type="radio" name="triggerCondition"
											id="triggerCondition" class="promotion-checkbox"
											checked="checked" value="98"> <label
											for="promotion-trigger">注册</label>
										<input  type="radio" name="triggerCondition"
											id="triggerCondition" class="promotion-checkbox"
											 value="99"> <label
											for="promotion-trigger">充值返券</label>
										<!-- <input  type="radio" name="triggerCondition"
											id="triggerCondition" class="promotion-checkbox"
											 value="100"> <label
											for="promotion-trigger">订单好评</label> -->
										<!-- <input  type="radio" name="triggerCondition"
											id="triggerCondition" class="promotion-checkbox"
											 value="101"> <label
											for="promotion-trigger">根据渠道注册送</label> -->
										<!-- <input  type="radio" name="triggerCondition"
											id="triggerCondition" class="promotion-checkbox"
											 value="102" style="display:none"> <label
											for="promotion-trigger" style="display:none">0元购活动</label> -->
										<input  type="radio" name="triggerCondition"
											id="triggerCondition" class="promotion-checkbox"
											value="103"> <label
											for="promotion-trigger">微票(领克特)</label>
										<input  type="radio" name="triggerCondition"
											id="triggerCondition" class="promotion-checkbox"
											value="104"> <label
											for="promotion-trigger">微票新人注册送券</label>
										<input  type="radio" name="triggerCondition"
											id="triggerCondition" class="promotion-checkbox"
											value="97"> <label
											for="promotion-trigger">抢红包</label>
										<input  type="radio" name="triggerCondition"
											id="triggerCondition" class="promotion-checkbox"
											value="105"> <label
											for="promotion-trigger">抽奖</label>
										<input  type="radio" name="triggerCondition"
											id="triggerCondition" class="promotion-checkbox"
											value="106"> <label
											for="promotion-trigger">确认收货返券</label>
									</div>
								</div>
							</div>
							<div class="form-group" style="display: none" id="pay">
								<label for="promotion-des">订单满足金额：</label>
								<input type="text" id="payment" name="payment" class="promotion-txt">
							</div>
							<!-- <div style="display:none;" id="promotion-condition">
							    <div class="btn-an">
							         <input type="button" id="btn_addtr" value="增行">
									  <input type="button" id="btn-deltr"  value="删行">
							    
							    </div>
							    <div class="inline-box" style="display: none">
									<div class="form-inline"  >
										<div class="form-group">
											<label for="promotion-visit">渠道：</label>
											<div class="field">
												<select id="channel" name="channel"  class="promotion-sel selqd">
													
												</select><span>请选择渠道</span>
											</div>
										</div>
										<div class="form-group">
											<label for="promotion-conditions">赠品：</label>
											<div class="field">
												<select id="gift" name="gift"  class="promotion-sel selzp">
												</select><span>请选择赠品</span>
											</div>
									   </div>
									</div>
								
							   </div>  
							</div> -->
							<div class="form-group">
								<label for="promotion-des">规则描述：</label>
								<textarea name="description" id="description"
									class="promotion-textarea">${bean.description }</textarea>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<input type="button" name="button" id="confirmButtonB2C"
										class="promotion-btn promotionManage-btn" value="确定">
								</div>
								<div class="form-group">
									<input type="button" name="button" id="backButton"
										class="promotion-btn promotionManage-btn" value="取消">
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>

	<script type="text/javascript">
		
	</script>
</body>
</html>