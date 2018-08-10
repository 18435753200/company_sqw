<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
	<head>
	  <meta charset="UTF-8">
	  <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	  <title>UNICORN-用户管理</title>
	  <%@include file="/WEB-INF/views/include/base.jsp"%>
	  <link rel="stylesheet" type="text/css" href="${path}/commons/css/user.css">
	  <script type="text/javascript" src="${path}/commons/js/user/jiben.js"></script>
	  <script type="text/javascript">	
			function  editUser(id,name,t){
				$("#userId").val(id);
				$("#name1").val(name);
				$("#namehidden").val(name);
				$("#roleId1").val(t);
			}
			/* function  delUser(id){
				if(window.confirm("确定删除此用户!")){
					$.ajax( {
						url : '${path}'+"/user/delete?id="+id,
						type : 'POST',
						timeout : 30000,		
						error : function() {
							alert('Error loading ');
						},
						success : function() {
							 window.location.reload();
						}	
					});
				}
			} */
			function  delUser(id){
				$.dialog.confirm('确定删除此用户?', function(){
					$.ajax( {
						url : '${path}'+"/user/delete?id="+id,
						type : 'POST',
						timeout : 30000,		
						error : function() {
							alert('服务器忙,请稍后再试!');
						},
						success : function() {
							 window.location.reload();
						}	
					}, function(){
					   // $.dialog.tips('执行取消操作');
					});
		    	});
			}
		
			
	 </script> 
	</head>
	<body>
	 <%@include file="/WEB-INF/views/include/header.jsp"%>
    <!-- 弹框 start -->
		<div id="addDiv" class="alert_user2" style="display:none;">
			<div class="bg"></div>
			<div class="w">
				<div class="box1">
					<h2>创建新用户</h2>
					<img src="${path}/commons/images/close.jpg" class="w_close">
				</div>
				<div class="box3">
					<form id="addForm" action="${path}/user/save" method="post" >
						<span class="s1">用户名：</span> <input id="name"  type="text" name="name" ><br>
						<span class="s2">密码：<span><input id="password" type="password" name="password"  ><br>
						<span>再次输入密码：<span><input id="repassword" type="password" name="repassword">
						<span class="s3">角色：<span>
                            <select  id="roleId" name ="roleId" >
							    <option value="" selected="selected">请选择</option>
							    <c:forEach items="${roles}" varStatus="status" var="role">
							        <option value="${role.roleId}" >${role.name}</option>
							    </c:forEach>
							</select>
						<button id="addUser" type="button" class="bt1">确定</button>
						<button id="addUserCancel" type="button" class="bt2" >取消</button>
					</form>
				</div>
				<div class="blank20"></div>
			</div>
		</div>
		
		
		<div id="editDiv" class="alert_user2" style="display:none;">
		<div class="bg"></div>
		<div class="w">
			<div class="box1">
				<h2>修改新用户</h2>
				<img src="${path}/commons/images/close.jpg" class="w_close">
			</div>
			<div class="box3">
				<form id="editForm" action="${path}/user/update" method="post" >
				        <input id="userId"  type="hidden" name="id" >
						<span class="s1">用户名：</span> <input id="name1" disabled="disabled"  type="text"> 
						<input id="namehidden" name="name" type="hidden"><br>
						<span class="s2">密码：<span><input id="password1" type="password" name="password"  ><br>
						<span>再次输入密码：<span><input id="repassword1" type="password" name="repassword"  >
						<span class="s3">角色：<span>
                            <select  id="roleId1" name ="roleId" >
							    <option value="" selected="selected">请选择</option>
							    <c:forEach items="${roles}" varStatus="status" var="role">
							        <option value="${role.roleId}" >${fn:escapeXml(role.name)}</option>
							    </c:forEach>
							</select>
						<button id="userEidt" type="button"  class="bt1">修改</button>
						<button id="userEidtCancel" type="button"  class="bt2" >取消</button>
				</form>
			</div>
			<div class="blank20"></div>
		</div>
	</div>
	<!-- 弹框 end -->

       	
	    
		<div class="wrap">
		
			<%@include file="/WEB-INF/views/include/leftUser.jsp"%>
			
			<form  id="SearchFrom"  action="${path}/user/list" method="post">
			     <input name="sortFields"   type="hidden" value="${fn:escapeXml(page.sortFields)}"/> 
		         <input name="order"  type="hidden" value="${fn:escapeXml(page.order)}"/> 
			</form>
			<form  id="deleteForm"  action="${path}/user/deleteByIds" method="post">
		       	<!-- 右边 start -->
				<div class="right f_l">
					<div class="title">
						<p class="c1">用户管理</p>
						<div class="clear"></div>
					</div>
					<div class="blank5"></div>
					<div class="cont">
						<div class="u_list">
							<ul class="title_1">
								<li class="p1 f_l" style="margin-top:2px;">
									<input type="checkbox" class="f_l c_mtop2" style="margin-top:-2px\9;">
									<p class="f_l" style="margin-top:2px;">&nbsp;全选</p>
								</li>
							    <c:if test="${! empty buttonsMap['用户管理-添加用户'] }">	
									<li class="p2 f_l">
										<button type="button" class="btn1 f_l">创建新用户</button>
									</li>
								</c:if>	
								<c:if test="${! empty buttonsMap['用户管理-批量删除'] }">	
									<li class="p2 f_l">
										<button type="button" class="btn2 f_l">删除</button>
									</li>
								</c:if>	
								
								<li class="clear"></li>
							</ul>
							<p class="blank5"></p>
							<h2>
								<p>选项</p>
								<p>用户名</p>
								<p>角色</p>
								<p>操作</p>
							</h2>
							<ul class="title_2">
							     <c:set var="deleteAble" value="${! empty buttonsMap['用户管理-删除用户']}"></c:set>
							     <c:set var="editAble" value="${! empty buttonsMap['用户管理-修改用户']}"></c:set>
							     
							     <c:forEach  items="${page.result}" var="user" varStatus="vs">
								 	<li class="">
								 	    <c:if test="${loginUser.id==user.userId}">
											<p class="p1"><input disabled="disabled" type="checkbox" name="nn" value="${user.userId}"></p>
										</c:if>
										<c:if test="${loginUser.id!=user.userId}">
											<p class="p1"><input type="checkbox" name="nn" value="${user.userId}"></p>
										</c:if>
										<p class="p2" title="${fn:escapeXml(user.name)}">${fn:escapeXml(user.name)}&nbsp;</p>
										<c:set var="key" value="${mapvo[user.name]}" ></c:set>
										<p class="p3" title="${key}">${key}&nbsp; </p>
										<p class="p5">
										   <c:if test="${loginUser.id!=user.userId and deleteAble}">
												<span class="c_poin dele" onclick="delUser('${user.userId}')">[删除]</span>
											</c:if>
											<c:if test="${editAble}">
												<span class="c_poin span2" onclick="editUser('${user.userId}','${fn:escapeXml(user.name)}','${rolemap[key]}')" >[修改]</span>
										    </c:if>
										    
										</p>
										<span style="display:block;clear:both;"></span>
									</li>
							  	</c:forEach>
						    </ul>
						</div>
					</div>
					
				</div>
				<!-- 右边 end -->
			    </form>
			    
			    <%@include file="/WEB-INF/views/include/page.jsp"%>
		</div>	
			
		 <div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
        </body>
</html>