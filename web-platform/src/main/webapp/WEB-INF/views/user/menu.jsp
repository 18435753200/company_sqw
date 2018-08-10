<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
<title>UNICORN-菜单管理</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${path}/commons/css/menu.css">
<link rel="stylesheet" href="${path}/commons/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
<script type="text/javascript" src="${path}/commons/js/menu_fn.js"></script>
<script type="text/javascript" src="${path}/commons/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${path}/commons/js/ztree/jquery.ztree.excheck-3.5.js"></script>

<script type="text/javascript">
		var setting = {
			check: {
				enable: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			view: {
				showIcon: false
		    },
		    callback:{
		    	onClick: zTreeOnClick
		    }
		};
		function zTreeOnClick(event, treeId, treeNode) {

			$.ajax({
				type:'post',
				url:'${path}/menu/loadChildrenByPID',
				data:"pId="+treeNode.id,
				error:function(){
					alert('服务器忙，请稍后再试！');
				},
				success:function(data){
					$("#contentList").html(data);
				}
			});

		}
		$(document).ready(function(){
			$.fn.zTree.init($("#menutree"), setting, ${tree});
			$.fn.zTree.getZTreeObj("menutree").expandAll(true);
		});
		
	 </script>
</head>
<body>

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
			<div class="list2 f_l">
				<p class="blank15"></p>
				<h2></h2>
				
				<!-- 折叠菜单begin -->
				
				<div class="zTreeDemoBackground left">
				
					<ul id="menutree" class="ztree"></ul>
					
				</div>
				<!-- 折叠菜单over -->
			</div>
		<div id="contentList" class="menu-main"></div>

		</div>
		
		
		<!-- 右边 end -->
		
	</div>
	
	<!-- 内容 end -->
	
	<div class="blank10"></div>
	
	<!-- 底部 start -->
	
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	
<div class="lightbox" id="EditMenu">
	<form id="fm">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box"></div>
		<div class="lightbox-box-hd">
			<h2>修改菜单</h2>
		</div>
		<div class="lightbox-box-bd menu-box">
				<dl>
					<dt>名称：</dt>
					<dd>
						<input type="text" class="u-text" name="name" id="name" />
						<input type="hidden" class="u-text"  name="menuId" id=menuId />
						<input type="hidden" class="u-text"  name="parentMenuId" id=parentMenuId />
					</dd>
				</dl>
				<dl>
					<dt>路径：</dt>
					<dd><input type="text" class="u-text"name="url"  id="url"/></dd>
				</dl>
				<dl>
					<dt>状态：</dt>
					<dd><select class="u-select" name="status"  id="status"><option value="1">启用</option><option value="0">关闭</option></select></dd>
				</dl>
		</div>
		<div class="lightbox-box-bar">
			<a href="javascript:void(0);" class="lightbox-btn true-btn" onclick="saveMenu()">确定</a>
		</div>
	</div>
	</form>
</div>
	
</body>
</html>