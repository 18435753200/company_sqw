<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
	<head>
	  <meta charset="UTF-8">
	  <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	  <title>UNICORN-系统操作记录</title>
	  <%@include file="/WEB-INF/views/include/base.jsp"%>
	  <link rel="stylesheet" type="text/css" href="${path}/commons/css/user.css">
	  <script type="text/javascript" src="${path}/commons/js/user/jiben.js"></script>
	  <script src="${path}/commons/js/my97/WdatePicker.js"></script>
	</head>
	<body>
	 <%@include file="/WEB-INF/views/include/header.jsp"%>
	    
		<div class="wrap">
		
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
			
			<form  id="SearchFrom"  action="${path}/user/listsyslog" method="post">
			     <input name="sortFields"   type="hidden" value="${fn:escapeXml(page.sortFields)}"/> 
		         <input name="order"  type="hidden" value="${fn:escapeXml(page.order)}"/> 
		       	<!-- 右边 start -->
				<div class="right f_l">
					<div class="title">
						<p class="c1">系统操作记录</p></br>
						<div class="clear"></div>
					</div>
					<div class="title">
						<span>操作时间 :</span>
				 <input type="text" id="startTime" name="sTime" value="${page.parameter.sTime}" class="rl" onClick="WdatePicker()"> <i>至</i>
				 <input type="text" id="endTime" name="eTime" value="${page.parameter.eTime}"  class="rl" onClick="WdatePicker()">&nbsp;&nbsp;<button type="button" onclick="clickSubmit()">搜索</button>
						<div class="clear"></div>
					</div>
					<div class="blank5"></div>
					<div class="cont">
						<div class="u_list">
							<ul class="title_1">
								
								<li class="clear"></li>
							</ul>
							<p class="blank5"></p>
							<h2>
								<p style="width:80px;text-align: center;">操作人</p>
								<p style="width:80px;text-align: center;">姓名</p>
								<p style="width:100px;text-align: center;">部门</p>
								<p style="width:120px;text-align: center;">IP</p>
								<p>数据项</p>
								<p style="width:100px;text-align: center;">备注</p>
								<p style="width:140px;text-align: center;">时间</p>
							</h2>
							<ul class="title_2">
							     <c:forEach  items="${page.result}" var="user" varStatus="vs">
								 	<li class="">
										<p class="p1" style="width:80px;text-align: center;">${user.userCode}</p>
										<p class="p1" style="width:80px;text-align: center;">${user.userName}</p>
										<p class="p1" style="width:100px;text-align: center;">${user.deptName}</p>
										<p class="p1" style="width:120px;text-align: center;">${user.optIp}</p>
										<p class="p1" title='${user.content}'>${user.content}</p>
										<p class="p1" style="width:100px;text-align: center;" title="${user.funtName}">${user.funtName}</p>
										<p class="p1" style="width:140px;text-align: center;"><fmt:formatDate value="${user.createDate}" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="medium" /></p>
										<span style="display:block;clear:both;"></span>
									</li>
							  	</c:forEach>
						    </ul>
						</div>
					</div>
					
				</div>
				</form>
				<!-- 右边 end -->
			    <%@include file="/WEB-INF/views/include/page.jsp"%>
		</div>	
			
		 <div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
        </body>
<script type="text/javascript">
function clickSubmit(){
	jQFrm.submit();
}
</script>
</html>