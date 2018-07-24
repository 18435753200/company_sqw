<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	   <title>商家后台管理系统-限购模板管理</title>

	<%@include file="/WEB-INF/views/zh/include/base.jsp"%>
      <link rel="stylesheet" type="text/css" href="${path}/css/zh/juese.css">
	  <script type="text/javascript" src="${path}/js/user/zh/limit.js"></script>
      <link rel="stylesheet" type="text/css" href="${path}/css/zh/quanxian.css">
      <link rel="stylesheet" href="${path}/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
	 <script type="text/javascript" src="${path}/js/ztree/jquery.ztree.core-3.5.js"></script>
	 <script type="text/javascript" src="${path}/js/ztree/jquery.ztree.excheck-3.5.js"></script>
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
            	 //treeNode.chkDisabled 
		        var hiddenString = '<input type="hidden" name="menus" value="'+this.id+'"/>';
                $("#hiddenBox").append(hiddenString);
		     });
		}
		
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, ${tree});
			$.fn.zTree.getZTreeObj("treeDemo").expandAll(false);
		});
		
	
		function  editUser(id,name){
			$("#roleId1").val(id);
			$("#name1").val(name);
			$("#hiddenName").val(name);
			$("#edit_div").show();
		}
		function submitForm(){
			if($.trim($("#roleId2").val()).length<=0){
				alert("请先选择要操作的模板");
				return;
			}else{
				checkSelect();
				//$("#updateRoleMenu").submit();
				ajaxSubmit("#updateRoleMenu2");
			}
			
		}
		
		
		
		
		function checkRoleDel(roleId){
			$("#roleId3").val(roleId);
			$.dialog.confirm('确定删除此模板?', function(){
				$.ajax({
		 	         type: "POST",
		 	         dataType:"html",
		 	         url: "./checkMouldDel",
		 	         data: "roleId="+roleId,
		 	         success: function (result) {
		 	        	 if(result>0){
		 	        	    alert("你已经绑定了限购地区，不能删除！");
		 	        	    return false;
		 	        	 }else{
		 	        		/* window.location.href="../role/delete?roleId="+roleId; */
		 	        		ajaxSubmit("#deleteForm");
		 	        	 }
		 	          }
		 	    });
				},function(){
					
				})
		}
	 </script> 
	</head>
	<body>
    <!-- 弹框 start -->
		<div id="add_div" class="alert_user2" style="display:none;">
			<div class="bg"></div>
			<div class="w">
				<div class="box1">
					<h2>创建新模板</h2>
					<img src="${path}/images/close.jpg" class="w_close">
				</div>
				<div class="box3">
					<form id="addForm" action="${path}/limit/save" method="post"  onkeydown="if(event.keyCode==13){$('#addBtn').click(); return false;}" >
						<t:token/>
						<span class="s1">模板名：</span> <input id="name" onkeyup=""  type="text" name="name" ><br>
						<span class="s1">类型：</span><select name="type"><option value="1">销往</option><option value="2">限购</option></select><br>
						<button id="addBtn" type="button" class="bt1">确定</button>
						<button id="addBtnCancel" type="button" class="bt2" >取消</button>
					</form>
				</div>
				<div class="blank20"></div>
			</div>
		</div>
		
		
		<div id="edit_div" class="alert_user2" style="display:none;">
		<div class="bg"></div>
		<div class="w">
			<div class="box1">
				<h2>修改新模板</h2>
				<img src="<%=path%>/images/close.jpg" class="w_close">
			</div>
			<div class="box3">
				<form id="editForm" action="${path}/limit/update" method="post"  onkeydown="if(event.keyCode==13){$('#editBtn').click(); return false;}" >
				        <input type="hidden" name="token" value="${token}">
				        <input id="hiddenName"  type="hidden" name="hiddenName" >
				        <input id="roleId1"  type="hidden" name="id" >
						<span class="s1">模板名：</span> <input id="name1"   type="text" name="name"  ><br>
						<button id="editBtn" type="button"  class="bt1">修改</button>
						<button id="editBtnCancel" type="button"  class="bt2" >取消</button>
				</form>
			</div>
			<div class="blank20"></div>
		</div>
	</div>
	<!-- 弹框 end -->
		
   <%@include file="/WEB-INF/views/zh/include/header.jsp"%>	
   
   <div class="wrap">
		
		<%@include file="/WEB-INF/views/zh/include/leftProduct.jsp"%>

         <!-- 右边 start -->
		<div class="right f_l">
			<div class="title">
				<p class="c1">限购地区模板</p>
				<div class="clear"></div>
			</div>
			<div class="blank5"></div>
			<div  class="list f_l">
				<h2>限购模板</h2>
				 <div class="au">
				  <c:forEach items="${roleList}" var="role" varStatus="vs">
						<li  <c:if test="${roleId==role.id}"> class="i0"</c:if> <c:if test="${roleId!=role.id}"> class="i1"</c:if>  >
							<a href="<%=path%>/limit/list?id=${role.id}&type=${role.type}" class="le"> ${fn:escapeXml(role.name)}</a>
							<div style="float: right;">
								<%-- <a class="delname" href="<%=path%>/role/delete?roleId=${role.roleId}">[删除]</a> --%>
								<a class="delname" onclick="checkRoleDel('${role.id}')" href="javascript:void(0)">[删除]</a>
								<a class="xiug4"  href="javascript:editUser('${role.id}','${fn:escapeXml(role.name)}')">[修改]</a>&nbsp;&nbsp;&nbsp;&nbsp;
							</div>
						</li>
				</c:forEach>
				</div>
					<%--<li class="i1" style="background:#cccccc;">
						<a href="<%=path%>/role/list?roleId=" >kk</a>
						<div style="float: right;">
							<a class="delname" href="<%=path%>/role/delete?roleId=kk" style="color:#ffffff;">[删除]</a>
							<a class="xiug4"  href="javascript:editUser('k','kk')" style="color:#ffffff;">[修改]</a>&nbsp;&nbsp;&nbsp;&nbsp;
						</div>
					</li>
				--%><div class="clear"></div>
			</div>
			<div class="list2 f_l">
				<button id="addbutton" class="b1 f_l">创建新模板</button>
				<button type="button" onclick="submitForm()" class="b2 f_l">确定</button>
				<p class="blank15"></p>
				
				<c:if test="${typ==1}">
				<h2>地区限购[选中则为发往的地区]</h2>
				</c:if>
				<c:if test="${typ==2}">
				<h2>地区限购[选中则为限售的地区]</h2>
				</c:if>
				<!-- 折叠菜单begin -->						
				 <div class="zTreeDemoBackground left">
					<ul id="treeDemo" class="ztree"></ul>
				</div>
				<!-- 折叠菜单over -->
			</div>
			
			<form id="updateRoleMenu2" action="${path}/limit/updateMouldRegion" method="post" >
			  <div id="hiddenBox" style="display: none;">
			  <input type="hidden" name="token" value="${token}">
			    <input id="roleId2"  type="hidden" name="id" value="${roleId}" >
			  </div>
			</form>
			<form id="deleteForm" action="${path}/limit/delete" method="post" >
		        <input type="hidden" name="token" value="${token}">
		        <input id="roleId3"  type="hidden" name="mouldId" value="${roleId}" >
			</form>
		</div>
		<!-- 右边 end -->
	</div>
	<!-- 内容 end -->
	<p class="blank30"></p>
		<!-- 底部 start -->
	<%@include file="/WEB-INF/views/zh/include/last.jsp"%>
	<!-- 底部 end -->
</body>
</html>