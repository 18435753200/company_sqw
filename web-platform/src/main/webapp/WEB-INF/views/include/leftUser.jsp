<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%  
    String basePath = request.getContextPath();
	request.setAttribute("path",basePath);
	request.setAttribute("pathUrl", "/user/checklist");
%>
<!-- 左边 start -->
<div class="leftly">
<script type="text/javascript">
    $(document).ready(function(){
		var url='${url}';
		//alert(url);
		if(url.indexOf("equipment")!=-1){
			$("#equipment").attr("class","p2 c_cut2");
		}
		if(url.indexOf("jiben")!=-1){
			$("#user_info").attr("class","p2 c_cut2");
		}
		
		
		if(url.indexOf("checkAgent")!=-1){
			$("#checklistAgent").attr("class","p2 c_cut2");
		}
		
		if(url.indexOf("checklistLine")!=-1){
			$("#checklistLine").attr("class","p2 c_cut2");
		}
		
		if(url.indexOf("checkAll")!=-1){
			$("#checklist").attr("class","p2 c_cut2");
		}
		if(url.indexOf("check2")!=-1){
			$("#checklist2").attr("class","p2 c_cut2");
		}
		if(url.indexOf("check1")!=-1){
			$("#checklist1").attr("class","p2 c_cut2");
		}
		if(url.indexOf("account")!=-1){
			$("#user_account").attr("class","p2 c_cut2");
		}
		if(url.indexOf("user/list")!=-1){
			$("#user_list").attr("class","p2 c_cut2");
		}
		if(url.indexOf("role")!=-1){
			$("#user_role").attr("class","p2 c_cut2");
		}
		if(url.indexOf("complaint_retailer")!=-1){
			$("#user_retailer").attr("class","p2 c_cut2");
		}
		if(url.indexOf("brand")!=-1){
			$("#brand").attr("class","p2 c_cut2");
		}
		if(url.indexOf("complaint_dealer")!=-1){
			$("#user_dealer").attr("class","p2 c_cut2");
		}
		if(url.indexOf("/user/menu")!=-1){
			$("#user_menu").attr("class","p2 c_cut2");
		}
		if(url.indexOf("saleManager")!=-1){
			$("#sale").attr("class","p2 c_cut2");
		}
		if(url.indexOf("saleWay")!=-1){
			$("#sale_way").attr("class","p2 c_cut2");
		}
/* 		if(url.indexOf("check")!=-1
		||url.indexOf("supplierInfo")!=-1
		||url.indexOf("dealerInfo")!=-1
		||url.indexOf("retailerInfo")!=-1){
			$("#checklist").attr("class","p2 c_cut2");
		} */
		if(url.indexOf("batch")!=-1){
			$("#checklist").attr("class","p2 c_cut2");
		}
	});
   
	 </script>
<!-- 左边 start -->
<div class="left f_l">
	<p class="blank10"></p>
	<div class="title">
		<p class="f_l">
			<img src="${path}/commons/images/img_title2.png" alt="">
		</p>
		<p class="f_l p1">系统中心</p>
		<p class="clear"></p>
	</div>
	<p class="blank5"></p>
	<div class="list_box">
			<c:if test="${ !empty buttonsMap['审核管理']}">
			<div class="demo">
				<h2>
					<p class="p1  gl">审核管理</p>
				</h2>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['普通商户审核']}">
						<p id="checklist" class="p2 ">
							<a href="${path}${ buttonsMap['普通商户审核']}?source=1">普通商户审核</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['会员商户审核']}">
						<p id="checklist2" class="p2 ">
							<a href="${path}${ buttonsMap['会员商户审核']}?source=1">会员商户审核</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['家庭号商户审核']}">
						<p id="checklist1" class="p2 ">
							<a href="${path}${ buttonsMap['家庭号商户审核']}?source=1">家庭号商户审核</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['区域运营商审核']}">
						<p id="checklist1" class="p2 ">
							<a href="${path}${ buttonsMap['区域运营商审核']}?source=1">区域运营商审核</a>
						</p>
					</c:if>
				</div>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['线下合作商家审核']}">
						<p id="checklistLine" class="p2 ">
							<a href="${path}${ buttonsMap['线下合作商家审核']}?source=1">线下合作商家审核</a>
						</p>
					</c:if>
				</div>
				
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['代理审核']}">
						<p id="checklistAgent" class="p2 ">
							<a href="${path}${ buttonsMap['代理审核']}?source=1">代理审核</a>
						</p>
					</c:if>
				</div>
				
				
				
<%-- 				<div class="p_b">
					<c:if test="${ !empty buttonsMap['设备审核']}">
						<p id="equipment" class="p2 ">
							<a href="${path}${ buttonsMap['设备审核']}">设备审核</a>
						</p>
					</c:if>
				</div> --%>
<%-- 				<div class="p_b">
					<c:if test="${ !empty buttonsMap['品牌审核']}">
						<p id="brand" class="p2 ">
							<a href="${path}${ buttonsMap['品牌审核']}">品牌审核</a>
						</p>
					</c:if>
				</div> --%>
			</div>
		</c:if>
		<%-- <c:if test="${ !empty buttonsMap['问题反馈']}">
			<div class="demo">
				<h2>
					<p class="p1 gl">问题反馈</p>
				</h2>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['零售商问题反馈']}">
						<p id="user_retailer" class="p2 ">
							<a href="${path}${ buttonsMap['零售商问题反馈']}">零售商问题反馈</a>
						</p>
					</c:if>
					<c:if test="${ !empty buttonsMap['经销商问题反馈']}">
						<p id="user_dealer" class="p2 ">
							<a href="${path}${ buttonsMap['经销商问题反馈']}">经销商问题反馈</a>
						</p>
					</c:if>
				</div>
			</div>
		</c:if> --%>
		<c:if test="${ !empty buttonsMap['信息管理']}">
			<div class="demo">
				<h2>
					<p class="p1 gl">信息管理</p>
				</h2>
				<div class="p_b">
					<c:if test="${ !empty buttonsMap['基本信息管理']}">
						<p id="user_info" class="p2 ">
							<a href="${path}${ buttonsMap['基本信息管理']}">基本信息管理</a>
						</p>
					</c:if>
					<%-- <c:if test="${ !empty buttonsMap['用户管理']}">
						<p id="user_list" class="p2 ">
							<a href="${path}${ buttonsMap['用户管理']}">用户管理</a>
						</p>
					</c:if>
	
					<c:if test="${ !empty buttonsMap['权限管理']}">
						<p id="user_role" class="p2 ">
							<a href="${path}${ buttonsMap['权限管理']}">权限管理</a>
						</p>
					</c:if>
	
					<c:if test="${ !empty buttonsMap['菜单管理']}">
						<p id="user_menu" class="p2 ">
							<a href="${path}${ buttonsMap['菜单管理']}">菜单管理</a>
						</p>
					</c:if> --%>
<%-- 					<c:if test="${ !empty buttonsMap['销售管理']}">
						<p id="sale" class="p2 ">
							<a href="${path}${ buttonsMap['销售管理']}">销售管理</a>
						</p>
					</c:if> --%>
					<%-- <c:if test="${ !empty buttonsMap['销售渠道']}">
						<p id="sale_way" class="p2 ">
							<a href="${path}${ buttonsMap['销售渠道']}">销售渠道</a>
						</p>
					</c:if> --%><br>
					<!-- 
					<a href="${path}/saleManager/toList">销售渠道</a>
					
					<a href="${path}/saleWay/toList">销售渠道</a>
					 -->
				</div>
			</div>
	</c:if>
	
	
	
	
	
	
	</div>
</div>
</div>
<!-- 左边 end -->
<%--  old ----
     function ajaxRight(url) {
			 alert(url);
			 var data = $("#formID").serialize();
				$.ajax( {
					url : url,
					type : 'POST',
					timeout : 30000,
					data : data,
					error : function() {
						alert('Error loading ');
					},
					success : processJson
				});
			};
			function processJson(contentHtml) {
				$("#content").html(contentHtml);
			};
    <div class="left f_l">
	<h2><img src="${path}/commons/images/img_15.jpg" alt=""></h2>
	<p class="p2 cut"><a href="${path}/supplier/jiben"  >基本信息管理</a></p>
	<p class="p2"><a href="#" onclick="ajaxRight('${path}/account/right')" >账号管理</a></p>
	<p class="p2"><a href="#" onclick="ajaxRight('${path}/user/right')" >用户管理</a></p>
	<p class="p2"><a href="#" onclick="ajaxRight('${path}/role/right')" >权限管理</a></p>
--%>
