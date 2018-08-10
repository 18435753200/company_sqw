<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
<title>UNICORN-企业数据排行</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/r/easyui/themes/default/easyui.css">
<!--JQuery主文件-->
<!--EasyUI主文件-->
<script type="text/javascript" src="${pageContext.request.contextPath}/r/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/custom.box.main.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/custom.jquery.form.js"></script>
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		$('#dgs').datagrid({
			toolbar : '#tb',
			url : '${pageContext.request.contextPath}/complaint_retailer/querySuppliernew',
			columns : colArr,
			pagination : true,
			nowrap : true,
			fitColumns : true,
			pageList : [ 10, 20, 30, 40, 50 ],
			pageSize : 20
		});
	})
	var colArr = [ [ {
		field : '',
		title : '全选',
		width : 100,
		checkbox : true
	},  {
		field : 'supplierCode',
		title : '企业编号',
		width : 100,
		
	}, {
		field : 'name',
		title : '企业名称',
		width : 200
	},  {
		field : 'status',
		title : '商家状态',
		width : 100,
		formatter : function(value, row, index) {
			if(value==5){
				return "冻结";
			}else{
				return "正常";
			}
}
	}, {
		field : 'organizationType',
		title : '商家类别',
		width : 100,
		formatter : function(value, row, index) {
			if(value==5){
				return "家庭号";
			}else{
				return "普通商家";
			}
}
	},{
		field : 'priceTotal',
		title : '收入',
		width : 100,
	}, ] ];
	function toSelect(){
		var sss=$('#dgs').datagrid('getSelected').supplier_code;
        window.open("${pageContext.request.contextPath}/platform/getCompanyStatManage?supplierCode="+sss);
	}
	function reloadquery() {
		//2.1用jQuery的serialize的工具获取表单中的数据
		var params = $("#cardqueryForm").serializeJson();
		//2.2用easyui的dataGrid里的load事件来重新载入数据
		$("#dgs").datagrid("load", params);
	}
	
	function frozens(){
		var bool = window.confirm("确定要解冻选中公司么？");
		if(bool){
			var ids = [];
			var rows = $('#dgs').datagrid('getSelections');
			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].supplier_id);
			}
			if (ids.length > 0) {
				$("#frozenIds").val(ids.join(','));
				$("#frozenForms").submit();
			//	window.location.reload();
			} else {
				alert("请选择要解冻的公司");
			}
		}else{
			
		}
	}
	function deletetagss(){
		var bool = window.confirm("确定要冻结选中公司么？");
		if(bool){
			var ids = [];
			var rows = $('#dgs').datagrid('getSelections');
			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].supplier_id);
			}
			if (ids.length > 0) {
				$("#unfreezeIds").val(ids.join(','));
				$("#unfreezeForms").submit();
			//	window.location.reload();
			} else {
				alert("请选择要冻结的公司");
			}
		}else{
			
		}
	}
	function closebq(){
		$("#goout-box-underbq").css("display","none");
	}
	function closetj(){
		$("#goout-box-undertj").css("display","none");
	}
	function editUser(index){
				$("#goout-box-underbq").css("display","block");
				$.ajax({
					type : "get", 
					url : "${pageContext.request.contextPath}/complaint_retailer/selectbyid?id="+index, 
					success : function(msg){
						$("#Idbq").val(msg.id);
						$("#tagCodeid").val(msg.tagCode);
						$("#tagNameid").val(msg.tagName);
						$("#tagTypeid").val(msg.tagType);
						
					}
				})
				
		}
	function productUnderbq(){
		$("#productIllegalUnderShelfbq").submit();
	}
	function productUndertj(){
		$("#productIllegalUnderShelftj").submit();
	}
</script>
</head>
<body>

	<%@include file="/WEB-INF/views/include/header.jsp"%>

	<div class="wrap"  style="width:980px; margin:0 auto;">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->

		<!-- 右边 start -->
	<form id="frozenForms"
		action="${pageContext.request.contextPath}/complaint_retailer/frozenSupplier"
		method="post">
		<input type="hidden" id="frozenIds" name="ids" />
	</form>
	<form id="unfreezeForms"
		action="${pageContext.request.contextPath}/complaint_retailer/updateSupplier"
		method="post">
		<input type="hidden" id="unfreezeIds" name="ids" />
	</form>
	<form action="" id="cardqueryForm">
	<br/><br/>
		开始时间
		<input style="width:100px;" class="text1" name="startTime" id="startTime${status.count}" value="" type="text" onclick="WdatePicker()">
		结束时间
		<input style="width:100px;" class="text1" name="endTime" id="endTime${status.count}" value="" type="text" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startTime${status.count}\')}'})">
		商家类型&nbsp;&nbsp;
		<select name="companyQy">
			<option value="">请选择</option>
			<option value="1">家庭号商家</option>
			<option value="2">普通商家</option>
		</select>
		<input  type="button" value="搜索" onclick="reloadquery()"><br/><br/>
		<table id="dgs">

		</table>
	</form>
	<div class="lightbox" id="goout-box-underbq">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box" onclick="closebq()"></div>
		<div class="lightbox-box-hd">
			<h2>商品标签修改</h2>
		</div>
		<div class="lightbox-box-bd">
			<form id="productIllegalUnderShelfbq" action="${pageContext.request.contextPath}/complaint_retailer/updatetag" method="post">
				<h3 ></h3>
			    <br/>
				<input type="hidden"  value="" id="Idbq" name="id">
				标签编码:<input id="tagCodeid" type="text" name="tagCode" value="">
				标签名称:<input type="text" id="tagNameid" name="tagName" value="">
				标签类型:<select id="tagTypeid" name="tagType">
					<option value="">--请选择--</option>
					<option value="1">星级标签</option>
					<option value="2">限购标签</option>
					<option value="3">赠券标签</option>
					<option value="4">类型标签</option>
				</select>
			</form>
		</div>
		<br/>
		<div class="lightbox-box-bar">
			<a href="javascript:void(0);" class="lightbox-btn true-btn" onclick="productUnderbq()">修改</a>
			<span style="margin-left: 20px;color: red;" id="boxwarnunder"></span>
		</div>
	</div>
</div>
<div class="lightbox" id="goout-box-undertj">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box" onclick="closetj()"></div>
		<div class="lightbox-box-hd">
			<h2>商品标签添加</h2>
		</div>
		<div class="lightbox-box-bd">
			<form id="productIllegalUnderShelftj" action="${pageContext.request.contextPath}/complaint_retailer/addtag" method="post">
				<h3 ></h3>
			    <br/>
				标签编码:<input id="tagCodeid" type="text" name="tagCode" value="">
				标签名称:<input type="text" id="tagNameid" name="tagName" value="">
					标签类型:<select id="tagTypeid" name="tagType">
					<option value="">--请选择--</option>
					<option value="1">星级标签</option>
					<option value="2">限购标签</option>
					<option value="3">赠券标签</option>
					<option value="4">类型标签</option>
				</select>
			</form>
		</div>
		<br/>
		<div class="lightbox-box-bar">
			<a href="javascript:void(0);" class="lightbox-btn true-btn" onclick="productUndertj()">添加</a>
			<span style="margin-left: 20px;color: red;" id="boxwarnunder"></span>
		</div>
	</div>
</div>
	<!-- 右边 end -->

	<div class="blank10"></div>
	<input id="labelPage" name="labelPage" type="hidden" value="${labelPage}"/>
	<input id="page" name="page" type="hidden" value="${page}"/>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>

</body>
</html>