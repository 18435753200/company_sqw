<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
<title>UNICORN-角色列表</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${path}/commons/css/role.css">
<link rel="stylesheet" href="${path}/commons/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
<script type="text/javascript" src="${path}/commons/js/user/role.js"></script>
<script type="text/javascript" src="${path}/commons/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${path}/commons/js/ztree/jquery.ztree.excheck-3.5.js"></script>

<script type="text/javascript">
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			view: {
				showIcon: false
		    }
		};
		function checkSelect(){
		     var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
             var nodes = treeObj.getCheckedNodes();
             $(nodes).each(function(){
		        var hiddenString = '<input type="hidden" name="menus" value="'+this.id+'"/>';
                $("#hiddenBox").append(hiddenString);
		     });
		}
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, ${tree});
			$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
		});
			function  editUser(id,name){
				$("#roleId1").val(id);
				$("#name1").val(name);
				$("#hiddenName1").val(name);
				$("#edit_div").show();
			}
			function submitForm(){
				if($.trim($("#roleId2").val()).length<=0){
					return;
				}else{
					checkSelect();
					$("#updateRoleMenu").submit();
				}
			}
			/* function  delUser(id){
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
					    $.dialog.tips('执行取消操作');
					});
		    	});
			} */
			function roleDealer(roleId){
				$.dialog.confirm('确定删除此用户?', function(){
					$.ajax({
			         type: "POST",
			         url: "../role/isRoleEngaged",
			         data: "roleId="+roleId,
			         success: function (result) {
			        	 if(result>0){
			        	    alert("角色名已绑定用户不能删除！");
			        	    return false;
			        	 }else{
			        	 	window.location.href="../role/delete?roleId="+roleId;
			        	 }
			      	 }
					});
				},function(){
					 // $.dialog.tips('执行取消操作');
				});
			}
	 </script>
</head>
<body>
	<!-- 弹框 start -->
	<div id="add_div" class="alert_user2" style="display:none;">
		<div class="bg"></div>
		<div class="w">
			<div class="box1">
				<h2>创建新角色</h2>
				<img src="${path}/commons/images/close.jpg" class="w_close">
			</div>
			<div class="box3">
				<form id="addForm" action="${path}/role/save" method="post">
					<span class="s1">角色名：</span> <input id="name" type="text"
						name="name"><br>
					<button id="addBtn" type="button" class="bt1">确定</button>
					<button id="addBtnCancel" type="button" class="bt2">取消</button>
				</form>
			</div>
			<div class="blank20"></div>
		</div>
	</div>


	<div id="edit_div" class="alert_user2" style="display:none;">
		<div class="bg"></div>
		<div class="w">
			<div class="box1">
				<h2>修改新角色</h2>
				<img src="${path}/commons/images/close.jpg" class="w_close">
			</div>
			<div class="box3">
				<form id="editForm" action="${path}/role/update" method="post">
					<input id="roleId1" type="hidden" name="roleId">
					<span class="s1"> 角色名：</span>
					<input id="name1" type="text" name="name">
					<input type="hidden" id="hiddenName1">
					<br>
					<button id="editBtn" type="button" class="bt1">修改</button>
					<button id="editBtnCancel" type="button" class="bt2">取消</button>
				</form>
			</div>
			<div class="blank20"></div>
		</div>
	</div>
	<!-- 弹框 end -->

	<%@include file="/WEB-INF/views/include/header.jsp"%>

	<div class="wrap">

		<%@include file="/WEB-INF/views/include/leftUser.jsp"%>

		<!-- 右边 start -->
		<div class="right f_l">
			<div class="title">
				<p class="c1">权限管理</p>
				<div class="clear"></div>
			</div>
			<div class="blank5"></div>
			<div class="list f_l">
				<h2>角色</h2>
				<div class="au">
				    <c:set var="deleteAble" value="${! empty buttonsMap['权限管理-删除权限']}"></c:set>
				    <c:set var="editAble" value="${! empty buttonsMap['权限管理-修改权限']}"></c:set>
				<c:forEach items="${roleList}" var="role" varStatus="vs">
					<li <c:if test="${roleId==role.roleId}"> class="i0"</c:if>
						<c:if test="${roleId!=role.roleId}"> class="i1"</c:if>>
						<a href="<%=path%>/role/list?roleId=${role.roleId}" class="le" title="${role.name}">${role.name}</a>
						<div>
							<c:if test="${deleteAble}">
								<a class="delname" onclick="roleDealer('${role.roleId}')" href="javascript:void(0)">[删除]</a> 
							</c:if>
							<c:if test="${editAble}">
								<a class="xiug4" href="javascript:editUser('${role.roleId}','${role.name}')">[修改]</a>
							</c:if>
						</div>
					</li>
				</c:forEach>
				</div>
				<div class="clear"></div>
			</div>
			<div class="list2 f_l">
				<c:if test="${! empty buttonsMap['权限管理-添加权限']}">
				<button id="addbutton" class="b1 f_l">创建新角色</button>
				</c:if>
				<c:if test="${! empty buttonsMap['权限管理-管理权限']}">
				<button type="button" onclick="submitForm()" class="b2 f_l">确定</button>
				</c:if>
				<p class="blank15"></p>
				<h2>菜单权限[选中则拥有操作该菜单的权限]</h2>
				<!-- 折叠菜单begin -->
				<div class="zTreeDemoBackground left">
					<ul id="treeDemo" class="ztree"></ul>
				</div>
				<!-- 折叠菜单over -->
			</div>

			<form id="updateRoleMenu" action="${path}/role/updateRoleMenu"
				method="post">
				<div id="hiddenBox" style="display: none;">
					<input id="roleId2" type="hidden" name="roleId" value="${roleId}">
				</div>
			</form>


		</div>
		<!-- 右边 end -->
	</div>
	<!-- 内容 end -->
	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>