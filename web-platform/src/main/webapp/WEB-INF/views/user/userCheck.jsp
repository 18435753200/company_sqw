<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<% response.setHeader("cache-control","public"); %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-账号冻结</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/kuc1.css"/>
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>
    <style>
    .c24 table td {
	    border: 1px solid #c8c8c8;
    line-height: 33px;
    text-align: center;
    color: #666666;
    font-size: 13px;
	}
    </style>	
	<script type="text/javascript">
		function toGetList(){
			$("#SearchFrom").submit();
		}
		$(function(){
			var message='${message}'; 
			if(message=='ok'){
				alert("操作成功!");
			}
			if(message=='1'){
				alert("缺少参数,操作失败!");
			}
			if(message=='2'){
				alert("上传失败,请重新选择上传文件!"); 		
			}
			if(message=='3'){
				alert("上传文件到图片服务器出错,请稍后再试!"); 	
			}
			if(message=='4'){
				alert("服务器忙,请稍后再试!"); 	
			}
			
		});
		function totuandui(userId){
			window.open("${path}/platform/toPlatformStatManage?userId="+userId);
		}
		Date.prototype.Format = function(fmt) { //author: meizz 
			var o = {
				"M+" : this.getMonth() + 1, //月份 
				"d+" : this.getDate(), //日 
				"h+" : this.getHours(), //小时 
				"m+" : this.getMinutes(), //分 
				"s+" : this.getSeconds(), //秒 
				"q+" : Math.floor((this.getMonth() + 3) / 3), //季度 
				"S" : this.getMilliseconds()
			//毫秒 
			};
			if (/(y+)/.test(fmt))
				fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
						.substr(4 - RegExp.$1.length));
			for ( var k in o)
				if (new RegExp("(" + k + ")").test(fmt))
					fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
							: (("00" + o[k]).substr(("" + o[k]).length)));
			return fmt;
		}
	</script>
</head>
<body>

 <%@include file="/WEB-INF/views/include/header.jsp"%>
	
	<div class="center">
		<!-- 左边 start -->
		<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		<!-- 左边 end -->
		<div class="c2" style="margin-top:10px;">
			<div class="c21">
			<div class="title">
				<p>平台管理&nbsp;>&nbsp; </p>
				<p>平台会员管理&nbsp;&gt;</p>
				<p class="c1">账号冻结</p>
			</div>
			</div>
			<div class="blank10"></div>

			<div class="c22">
			    <div class="c21">
						<ul class="top">
							<li id="supplier"><a href="javascript:void(0)" >用户中心</a></li>
						</ul>
				 </div>
				<div class="xia" style="height: 105px;">
					<form id="SearchFrom"  action="${path}/platform/userChecklist" method="post" >
						<p class="p1">
						    <strong>用户ID：</strong><input type="text" id="userId" name="userId" value="${page.parameter.userId}" class="text1 dzf-txt">
							&nbsp;
						    <strong>用户名：</strong><input type="text" id="userName" name="userName" value="${page.parameter.userName}" class="text1 dzf-txt">
	                        &nbsp;&nbsp;
							<strong>手机号 ：</strong>
							<input type="text" class="text1 dzf-txt" name="mobile" value="${page.parameter.mobile}">
							&nbsp;<strong>状态：</strong>
						    <select name="freezeStatus" style="height: 30px;">
						        <option value="">所有</option> 
						    	<option value="0"  <c:if test="${page.parameter.freezeStatus==0}"> selected="selected" </c:if>>未冻结</option>
						        <option value="1" <c:if test="${page.parameter.freezeStatus==1}"> selected="selected" </c:if>>冻结</option>
						    </select>
						    <br/><strong>星级：</strong>
							  <select name="stars" style="height: 30px;">
						        <option value="">-请选择-</option> 
						        <option value="1" <c:if test="${page.parameter.stars==1}"> selected="selected" </c:if>>1星</option>
						        <option value="2" <c:if test="${page.parameter.stars==2}"> selected="selected" </c:if>>2星</option>
						        <option value="3" <c:if test="${page.parameter.stars==3}"> selected="selected" </c:if>>3星</option>
						        <option value="4" <c:if test="${page.parameter.stars==4}"> selected="selected" </c:if>>4星</option>
						        <option value="5" <c:if test="${page.parameter.stars==5}"> selected="selected" </c:if>>5星</option>
						    </select>&nbsp;
						    <strong>推荐人手机：</strong>
							<input type="text" class="text1 dzf-txt" name="tjMobile" value="${page.parameter.tjMobile}">
						</p>
						</br>
						<p>
					    <button type="button" onclick="toGetList()" style="margin-left: 670px;">搜索</button>
						<a href="#" id="czhi" onclick="resetfm()">重置</a>
						</p>
					</form>
				</div>
			</div>
			<div class="blank10"></div>
<%-- 		  <c:if test="${! empty buttonsMap['商户审核-导出表格'] }">	 --%>
<!-- 			  <div class="btn"> -->
<!-- 					<a href="javascript:void(0)" onclick="downCheckListExcel()">  -->
<!-- 						<span class="button red">  -->
<!-- 							<span class="text"> -->
<%-- 								<img alt="" src="${path }/commons/images/down-icon.png" height="19px" width="19px"> 导出表格 --%>
<!-- 							</span> -->
<!-- 						</span> -->
<!-- 					</a> -->
<!-- 			  </div> -->
<%-- 		  </c:if>	 --%>
			<div class="c24" id="c24">
				<table>
					<tr>
					 <th class="ar1">用户ID</th>
					 <th class="ar1">用户名</th>
					 <th class="ar1">手机号</th>
<!-- 					 <th class="ar1">邀请码</th> -->
					 <th class="ar1">消费金额</th>
					 <th class="ar1">消费红旗券</th>
					 <th class="ar1">已到账红旗券</th>
					 <th class="ar1">未到账红旗券</th>
					 <th class="ar1">分红额度</th>
					 <th class="ar2">状态</th>
					 <th class="ar4">操作</th>
					 <th class="ar4">团队</th>
					</tr>
					<c:if test="${empty page.result}">
						<tr>
							<td colspan="10">
								<center><img src="${path }/commons/images/no.png" /></center>
							</td>
						</tr>
					</c:if>
						<c:forEach items="${page.result }" var="data">
							<tr>
								<td>${fn:escapeXml(data.userId)}</td>
							 	<td>
								 	${fn:escapeXml(data.userName)}
								</td>
								<td>
					  	 			${data.mobile }
					 			 </td>
<!-- 								<td> -->
<%-- 					  	 			${data.tjMobile } --%>
<!-- 					 			 </td> -->
					 			<td>${data.consumeMoney }</td>
					 			<td>${data.consumeHqq }</td>
					 			<td>${data.availHqq }</td>
					 			<td>${data.noHqq }</td>
					 			<td>${data.kyFenHong }</td>
								<td>
									<c:choose>
										<c:when test="${data.freezeStatus eq 0 }">未冻结</c:when>
										<c:otherwise><span style="color: red;">已冻结</span></c:otherwise>
									</c:choose>
					 			 </td>
								<td>
									<c:choose>
										<c:when test="${data.freezeStatus eq 0 }">
											<span class="ckan" style="cursor: pointer;" onclick="modifyFreezeStatus('${data.userId}',1)">冻结账户</span>
										</c:when>
										<c:otherwise>
											<span class="ckan" style="cursor: pointer;color: red;" onclick="modifyFreezeStatus('${data.userId}',0)">取消冻结</span>
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<input type="button" onclick="totuandui(${fn:escapeXml(data.userId)})" value="会员团队">
								</td>
							</tr>
						</c:forEach>
				</table>
				<c:if test="${!empty page.result}">
					<%@include file="/WEB-INF/views/include/page.jsp"%>
				</c:if>
			</div>
		  
		</div>
		<div class="blank20"></div>
	</div>

	 <div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
<script type="text/javascript">
	function modifyFreezeStatus(userId,type){
 			$.ajax({
	         type: "GET",
	         url: "../platform/modifyFreezeStatus?userId="+userId+"&freezeStatus="+type,
	         dataType:'json',
	         success: function (result) {
	        	 if(result == 1){
	        		 if(type == 1){
		        		 alert("冻结用户成功");
	        		 }else if(type == 0){
	        			 alert("取消冻结成功");
	        		 }
	        		 window.location.href = "../platform/userChecklist?page=${page.page}";
	        	 }else{
	        		 alert("操作失败");
	        	 }
	          }
 	    });
	}
</script>
</html>