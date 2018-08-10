<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
<title>UNICORN-经销商问题反馈</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${path}/commons/css/went1.css">
<script type="text/javascript" src="${path }/commons/js/user/complaint_dealer_fn.js"></script>
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script type="text/javascript">
		$(document).ready(function(){
			$.ajax({
				type : "post", 
	         	url : "../complaint_dealer/dealer", 
	         	data:"labelPage=1",
	            success : function(msg) { 
	             	$("#seelist").html(msg);
				},
				error:function(){
					alert("加载失败，稍后再试。");
				}
			}); 
			
		});
	
	</script>
	<style type="text/css">
	
	.button {

	}
	</style>
</head>
<body>

	<%@include file="/WEB-INF/views/include/header.jsp"%>

	<div class="wrap">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/include/leftUser.jsp"%>
		</div>
		<!-- 左边 end -->

		<!-- 右边 start -->
		<div class="right">
			<div class="c1">
				<div class="to">

					<ul class="top">
						<li class="list" id="waitlabel"><a href="javascript:void(0)"
							onclick="labelPage('1')">待处理</a>
						</li>
						<li id="alreadylabel"><a href="javascript:void(0)"
							onclick="labelPage('2')">已处理</a>
						</li>
					</ul>
				</div>
				
				
				<div class="xia">
					<form id="fm">
						<p class="p1">
							<strong>商户名称 ：</strong> 
							<input class="text1" type="text" name="referenceName" id="referenceName">
							
							<strong>用户名称 ：</strong> 
							<input class="text1" type="text" name="complaintBy" id="complaintBy">
							<strong>反馈类型 ：</strong>
							<select name="complaintType" id="complaintType">
								<option value="">所有类型</option>
								<option value="0">个人意见</option>
								<option value="1">商品相关</option>
								<option value="2">系统问题</option>
								<option value="3">订单相关</option>
								<option value="4">退货相关</option>
								<option value="5">服务相关</option>
								<option value="6">管理相关</option>
							</select>
						</p>
						<p class="p1">
							<strong>反馈级别 ：</strong> 
								<select class="text1" name="complaintLevel" id="complaintLevel">
								    <option value="">所有级别</option>
									<option value="0">普通</option>
									<option value="1">一般</option>
									<option value="2">紧急</option>
								</select>
							<strong class="st">&nbsp;时间 ：  </strong>
							<span>
								<input class="text1" name="startTime" id="startTime" value="" type="text" onclick="WdatePicker({maxDate:'%y-%M-%d'})">
								<span> 到 </span>
								<input class="text1" name="endTime" id="endTime" value="" type="text" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})">
							</span>
						</p>
						<p class="p4">
							<button type="button" onclick="clickSubmit()">搜索</button>
							<a href="#" id="czhi">重置</a>
						</p>
					</form>
				</div>
				<c:if test="${ !empty buttonsMap['经销商问题反馈-导出表格']}">
				<div class="btn">
					<a href="javascript:void(0)" onclick="downDealerExcel()"> 
						<span class="button red"> 
							<span class="text">
								<img alt="" src="${path }/commons/images/down-icon.png" height="19px" width="19px"> 导出表格
							</span>
						</span>
					</a>
				</div>
				</c:if>
				<div class="qb">
					<div class="biaogee" id="seelist"></div>
				</div>
			</div>
		</div>
	</div>

	<!-- 右边 end -->

	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>

	<script>
			$(document).ready(function(){
				mchu();
			});
			var mchu=function(){
			$(".to .top li").each(function(index){
				$(this).click(function(){
					var i = $(this).index();
					$(this).addClass('list').siblings().removeClass('list');
					$(".qb .biaogee:eq("+index+")").show().siblings().hide();			
				})
			});
		   }
        </script>
</body>
</html>