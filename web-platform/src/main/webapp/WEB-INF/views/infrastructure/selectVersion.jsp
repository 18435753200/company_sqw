<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<title>UNICORN-版本控制</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/r/easyui/themes/default/easyui.css">
<!--JQuery主文件-->
<!--EasyUI主文件-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/r/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/custom.box.main.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/custom.jquery.form.js"></script>
<link rel="stylesheet" type="text/css"
	href="${path}/commons/css/jiben.css">
<script type="text/javascript" src="${path}/commons/js/notice.js"></script>
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script type="text/javascript">
	function frozen() {
		$("#goout-box-undertj").css("display", "block");
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
	$(function() {
		$('#dgs')
				.datagrid(
						{
							toolbar : '#tb',
							url : '${pageContext.request.contextPath}/infrastructure/selectVersions',
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
	},{
				field : 'version',
				title : '版本',
				width : 100,

			},
			{
				field : 'versionNum',
				title : '版本号',
				width : 100,
			},
			{
				field : 'appType',
				title : '手机类型',
				width : 100,
				formatter : function(value, row, index) {
					if (value == 1) {
						return "android";
					} else {
						return "ios";
					}
				}
			},
			{
				field : 'createTime',
				title : '创建时间',
				width : 150,
				formatter : function(value, row, index) {
					if (value != null) {
						return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
					} else {
						return "";
					}
				}
			},
			{
				field : 'memo',
				title : '备注',
				width : 200,
			},
			{
				field : 'comple',
				title : '是否强制更新',
				width : 100,
				formatter : function(value, row, index) {
					if (value == 1) {
						return "是";
					} else {
						return "否";
					}
				}
			},
			{
				field : 'state',
				title : '状态',
				width : 100,
				formatter : function(value, row, index) {
					if (value != 2) {
						return "正在使用";
					} else {
						return "历史版本";
					}
				}
			},{
				field : 'id',
				title : '操作',
				width : 100,
				formatter : function(value, row, index) {
					if (value != null) {
						return " <input  type='button' value='修改' onclick='toupdatev()'>";
					} else {
						return "";
					}
				}
			},
			] ];
	function toupdatev() {
		var sss = $('#dgs').datagrid('getSelected').id;
		$.ajax({
			type : "get",
			url : "${pageContext.request.contextPath}/infrastructure/selectbyidversion?id="
					+ sss,
			success : function(msg) {
				console.log(msg);
				$("#version").val(msg.version);
				$("#versionNum").val(msg.versionNum);
				$("#appType").val(msg.appType);
				$("#state").val(msg.state);
				$("#stopTimes").val(new Date(msg.stopTime).Format("yyyy-MM-dd hh:mm:ss"));
				$("#memo").val(msg.memo);
				$("#comple").val(msg.comple);
				$("#id").val(msg.id);
				$("#goout-box-undertgsv").css("display", "block");
			}
		})

	}
	
	function reloadquerys() {
		//2.1用jQuery的serialize的工具获取表单中的数据
		var params = $("#cardqueryForm").serializeJson();
		//2.2用easyui的dataGrid里的load事件来重新载入数据
		$("#dgs").datagrid("load", params);
	}

	function frozens() {
		var bool = window.confirm("确定要删除选中版本么？");
		if (bool) {
			var ids = [];
			var rows = $('#dgs').datagrid('getSelections');
			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			if (ids.length > 0) {
				$("#frozenIds").val(ids.join(','));
				$("#frozenForms").submit();
				//	window.location.reload();
			} else {
				alert("请选择要删除的版本");
			}
		} else {

		}
	}
	function deletetagss() {
		var bool = window.confirm("确定要冻结选中公司么？");
		if (bool) {
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
		} else {

		}
	}
	function closebq() {
		$("#goout-box-underbq").css("display", "none");
	}
	function closetjv() {
		$("#goout-box-undertgsv").css("display", "none");
	}
	function closetj() {
		$("#goout-box-undertj").css("display", "none");
	}
	function closetg() {
		$("#goout-box-undertg").css("display", "none");
	}
	function editUser(index) {
		$("#goout-box-underbq").css("display", "block");
		$.ajax({
					type : "get",
					url : "${pageContext.request.contextPath}/complaint_retailer/selectbyid?id="
							+ index,
					success : function(msg) {
						$("#title").val(msg.title);
						$("#grade").val(msg.grade);
						$("#status").val(msg.status);
						$("#type").val(msg.type);
						$("#stopTime").val(msg.stopTime);
						$("#txtF_Content").val(msg.content);
					}
				})

	}
	function productUnderbqv() {
		$("#productIllegalUnderShelftjv").submit();
	}
	function productUndertj() {
		$("#productIllegalUnderShelftj").submit();
	}
</script>
</head>
<body>

	<%@include file="/WEB-INF/views/include/header.jsp"%>

	<div class="wrap" style="width: 980px; margin: 0 auto;">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->

		<!-- 右边 start -->
		<form id="frozenForms"
			action="${pageContext.request.contextPath}/infrastructure/deleteVersion"
			method="post">
			<input type="hidden" id="frozenIds" name="ids" />
		</form>
		<form id="unfreezeForms"
			action="${pageContext.request.contextPath}/complaint_retailer/updateSupplier"
			method="post">
			<input type="hidden" id="unfreezeIds" name="ids" />
		</form>
		<div id="tb">
			<button type="button" class="btn btn-warning" onclick="frozen()">添加版本</button>
			<button type="button" class="btn btn-warning" onclick="frozens()">删除选中</button>
		</div>
		<form action="" id="cardqueryForm">
			<br /> <br /> 版本:<input type="text" name="version">
			版本号:<input type="text" name="versionNum">
 				app类型: <select name="appType">
				<option value="">请选择</option>
				<option value="1">android</option>
				<option value="2">ios</option>
				</select>
 				状态: <select name="state">
				<option value="">请选择</option>
				<option value="1">正在使用</option>
				<option value="2">历史版本</option>
			</select> <input type="button" value="搜索" onclick="reloadquerys()"><br />
			<br />
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
					<form id="productIllegalUnderShelfbq"
						action="${pageContext.request.contextPath}/complaint_retailer/updatetag"
						method="post">
						<h3></h3>
						<br /> <input type="hidden" value="" id="Idbq" name="id">
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
				<br />
				<div class="lightbox-box-bar">
					<a href="javascript:void(0);" class="lightbox-btn true-btn"
						onclick="productUnderbq()">修改</a> <span
						style="margin-left: 20px; color: red;" id="boxwarnunder"></span>
				</div>
			</div>
		</div>
		<div class="lightbox" id="goout-box-undertj">
			<div class="lightbox-overlay"></div>
			<div class="lightbox-box">
				<div class="close-box" onclick="closetj()"></div>
				<div class="lightbox-box-hd">
					<h2>添加版本</h2>
				</div>
				<div class="lightbox-box-bd">
					<form id="productIllegalUnderShelftj"
						action="${pageContext.request.contextPath}/infrastructure/addVersion"
						method="post">
						<h3></h3>
						<br />
						<ul class="ul_vertical">
							版本:<input type="text" name="version">
			版本号:<input type="text" name="versionNum">
 				app类型: <select name="appType">
				<option value="">请选择</option>
				<option value="1">android</option>
				<option value="2">ios</option>
				</select>
 				状态: <select name="state">
				<option value="">请选择</option>
				<option value="1">正在使用</option>
				<option value="2">历史版本</option>
			</select>
							<br> 
							<li>
								<p class="p1">备注：</p> <textarea  rows="5" 
									cols="100" name="memo" style="outline: none; resize: none;" 
									onkeydown="countChar('txtF_Content','counter',1000);"></textarea>
							</li>
							<li class="blank20" style="visibility: visible;">
								<p class="p1"></p>

							</li>
							<li><p class="p1"></p></li>
							<li></li>
							是否强制更新: <select name="comple">
				<option value="">请选择</option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select>
						</ul>
					</form>
				</div>
				<br />
				<div class="lightbox-box-bar">
					<a href="javascript:void(0);" class="lightbox-btn true-btn"
						onclick="productUndertj()">添加</a> <span
						style="margin-left: 20px; color: red;" id="boxwarnunder"></span>
				</div>
			</div>
		</div>
		<div class="lightbox" id="goout-box-undertgsv">
			<div class="lightbox-overlay"></div>
			<div class="lightbox-box">
				<div class="close-box" onclick="closetjv()"></div>
				<div class="lightbox-box-hd">
					<h2>修改版本</h2>
				</div>
				<div class="lightbox-box-bd">
					<form id="productIllegalUnderShelftjv"
						action="${pageContext.request.contextPath}/infrastructure/updateVersion"
						method="post">
						<h3></h3>
						<br />
						<ul class="ul_vertical">
							版本:<input type="text" name="version" id="version">
			版本号:<input type="text" name="versionNum" id="versionNum">
			版本号:<input type="hidden" name="id" id="id">
 				app类型: <select name="appType" id="appType">
				<option value="">请选择</option>
				<option value="1">android</option>
				<option value="2">ios</option>
				</select>
 				状态: <select name="state" id="state">
				<option value="">请选择</option>
				<option value="1">正在使用</option>
				<option value="2">历史版本</option>
			</select>
							<br> 
							<li>
								<p class="p1">备注：</p> <textarea  rows="5" 
									cols="100" name="memo" style="outline: none; resize: none;" id="memo"
									onkeydown="countChar('txtF_Content','counter',1000);"></textarea>
							</li>
							<li class="blank20" style="visibility: visible;">
								<p class="p1"></p>

							</li>
							<li><p class="p1"></p></li>
							<li></li>
							是否强制更新: <select name="comple" id="comple">
				<option value="">请选择</option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select>
						</ul>
					</form>
				</div>
				<br />
				<div class="lightbox-box-bar">
					<a href="javascript:void(0);" class="lightbox-btn true-btn"
						onclick="productUnderbqv()">修改</a> <span
						style="margin-left: 20px; color: red;" id="boxwarnunder"></span>
				</div>
			</div>
		</div>
		<div class="lightbox" id="goout-box-undertg">
			<div class="lightbox-overlay"></div>
			<div class="lightbox-box">
				<div class="close-box" onclick="closetg()"></div>
				<div class="lightbox-box-hd">
					<h2>添加平台通知</h2>
				</div>
				<div class="lightbox-box-bd">
					<form id="productIllegalUnderShelftj"
						action="${pageContext.request.contextPath}/infrastructure/addVersion"
						method="post">
						<h3></h3>
						<br />
						<ul class="ul_vertical">
							通知标题:
							<input type="text" name="title" id="title"> 是否弹窗:
							<select name="grade" id="grade">
								<option value="">请选择</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select> 消息状态:
							<select name="status" id="status">
								<option value="">请选择</option>
								<option value="1">显示</option>
								<option value="0">不显示</option>
							</select>
							<br> 消息类型:
							<input type="text" name="type" id="type"> 截止时间:
							<input style="width: 100px;" class="text1" name="stopTime"
								id="stopTime" value="" type="text" onclick="WdatePicker()">
								<input type="hidden" name="publishUser" value="${loginUser.username}">
							<li>
								<p class="p1">通知内容：</p> <textarea id="txtF_Content" rows="10" 
									cols="50" name="content" style="outline: none; resize: none;" 
									onkeydown="countChar('txtF_Content','counter',1000);"></textarea>
							</li>
							<li class="blank20" style="visibility: visible;">
								<p class="p1"></p>
								<p>
									最多输入1000字，目前已经输入<span  id="counter"
										style="COLOR: red; FONT-WEIGHT: bold">0</span>字
								</p>
							</li>
							<li><p class="p1"></p></li>
							<li></li>
						</ul>
					</form>
				</div>
			</div>
		</div>
		<!-- 右边 end -->

		<div class="blank10"></div>
		<input id="labelPage" name="labelPage" type="hidden"
			value="${labelPage}" /> <input id="page" name="page" type="hidden"
			value="${page}" />
		<!-- 底部 start -->
		<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>