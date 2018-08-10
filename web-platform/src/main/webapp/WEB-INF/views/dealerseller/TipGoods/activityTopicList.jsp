<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-话题列表</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path }/commons/css/product.css"/>
    <script type="text/javascript" src="${path}/commons/js/activitytopic_list_fn.js"></script>
    <script type="text/javascript" src="<%=path %>/commons/js/my97/WdatePicker.js"></script>
</head>
<body>
	
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
    <div class="blank"></div>

	<div class="center">
		<!--中间左边开始-->
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->
		<!--中间左边结束-->
		<div class="alert_user3" style="display: none;">
			<div class="bg"></div>
			<div class="w">
				<div class="box1">
					<img src="${path}/commons/images/close.jpg" class="w_close">
				</div>
				<div class="box3">
					<p id="showmsgplat"></p>
				</div>
				<div class="blank20"></div>
			</div>
		</div>
		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>尖货设置&nbsp;&gt;&nbsp;</p>
					<p class="c1">话题管理</p>
				</div>
			</div>
			<div class="blank10"></div>

			<div class="c22">
				<div class="c3" id="cp3">
					<div class="xia">
						<form action="javascript:void(0)">
							<p class="p1">
								<span>标题 ：</span> 
								<input type="text" class="text1" id="title"> 
								<span>简叙：</span>
								<input type="text" class="text1" id="sketich">
								<span>状态 ：</span> 
								<select id="status">
									<option value="-1">全部</option>
									<option value="1">启动</option>
									<option value="2">停止</option>
								</select>
							</p>
						 	<p class="p1">
								<span>置顶状态：</span> 
								<select id="stickStatus">
									<option value="-1">全部</option>
									<option value="1">置顶</option>
									<option value="2">非置顶</option>
								</select>
								<span>话题类型：</span> 
								<select id="activityType">
									<option value="-1">全部</option>
									<option value="1">单品</option>
									<option value="2">活动</option>
									<option value="3">视频</option>
								</select>
								</p>
								<p class="p1"> 
<!-- 									<span>&nbsp;&nbsp;创建人：</span>  -->
<!-- 									<input type="text" class="text1" id="createby"> -->
									<span>&nbsp;&nbsp;创建时间：</span> 
									<input type="text" class="w275" id="createBeginTimeStr" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${serchDto.createOrderTime }" pattern="yyyy-MM-dd" />">   
									<label style="display: block; float: left; color: #ccc;padding: 0px 10px;">----</label> 
								    <input type="text" class="w275" id="createEndTimeStr" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${serchDto.createOrderTime }" pattern="yyyy-MM-dd" />">
								</p>
							<p class="p1" id="productIsTate">
<!-- 								<span>&nbsp;&nbsp;修改人：</span>  -->
<!-- 								<input type="text" class="text1" id="updateby"> -->
								<span>&nbsp;&nbsp;修改时间：</span> 
								<input type="text" class="w275" id="updateBeginTimeStr" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${serchDto.createOrderTime }" pattern="yyyy-MM-dd" />">
								<label style="display: block; float: left; color: #ccc;padding: 0px 10px;">----</label>
								<input type="text" class="w275" id="updateEndTimeStr" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${serchDto.createOrderTime }" pattern="yyyy-MM-dd" />">
							</p>
							<p class="p3">
								<a type="button" id="createTopicfm" href="${path}/activitytop/editActivity" target="_blank">创建话题</a>
								<a type="button" id="createBannerfm" href="${path}/activitytop/bannerSet" target="_blank">Banner设置</a>
								<button type="submit" id="subfm" onclick="clickSubmit()">搜索</button>
<!-- 								<a href="#" id="czhi" onclick="resetfm()">重置</a> -->
							</p>
						</form>
					</div>


					<div class="blank5"></div>
					<div class="c3" id="c3"></div>
				</div>
			</div>
		</div>
	</div>




	<div class="blank10"></div>
	 <!-- 底部 start -->
		<%@include file="/WEB-INF/views/include/foot.jsp"%>
		<!-- 底部 end -->
</body>
</html>