<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<title>UNICORN-平台通知管理</title>
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
							url : '${pageContext.request.contextPath}/infrastructure/selectNotices',
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
	}, {
				field : 'title',
				title : '通知标题',
				width : 100,

			},
			{
				field : 'content',
				title : '通知内容',
				width : 100,
			},
			{
				field : 'grade',
				title : '是否弹窗',
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
				field : 'status',
				title : '消息状态',
				width : 100,
				formatter : function(value, row, index) {
					if (value == 1) {
						return "显示";
					} else {
						return "不显示";
					}
				}
			},
			{
				field : 'stopTime',
				title : '截止时间',
				width : 100,
				formatter : function(value, row, index) {
					if (value != null) {
						return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
					} else {
						return "";
					}
				}
			},
			{
				field : 'id',
				title : '操作',
				width : 100,
				formatter : function(value, row, index) {
					if (value != null) {
						return "<input  type='button' value='查看内容' onclick='toSelect()'> <input  type='button' value='修改' onclick='toupdate()'>";
					} else {
						return "";
					}
				}
			}, ] ];
	function toSelect() {
		var sss = $('#dgs').datagrid('getSelected').id;
		$.ajax({
			type : "get",
			url : "${pageContext.request.contextPath}/infrastructure/selectbyid?id="
					+ sss,
			success : function(msg) {
				console.log(msg);
				$("#title").val(msg.title);
				$("#grade").val(msg.grade);
				$("#status").val(msg.status);
				$("#type").val(msg.type);
				$("#stopTime").val(new Date(msg.stopTime).Format("yyyy-MM-dd hh:mm:ss"));
				$("#txtF_Content").val(msg.content);
				$("#goout-box-undertg").css("display", "block");
			}
		})

	}
	function toupdate() {
		var sss = $('#dgs').datagrid('getSelected').id;
		$.ajax({
			type : "get",
			url : "${pageContext.request.contextPath}/infrastructure/selectbyid?id="
					+ sss,
			success : function(msg) {
				console.log(msg);
				$("#titles").val(msg.title);
				$("#grades").val(msg.grade);
				$("#statuss").val(msg.status);
				$("#types").val(msg.type);
				$("#stopTimes").val(new Date(msg.stopTime).Format("yyyy-MM-dd hh:mm:ss"));
				$("#txtF_Contents").val(msg.content);
				$("#id").val(msg.id);
				$("#goout-box-undertgs").css("display", "block");
			}
		})

	}
	function reloadquery() {
		//2.1用jQuery的serialize的工具获取表单中的数据
		var params = $("#cardqueryForm").serializeJson();
		//2.2用easyui的dataGrid里的load事件来重新载入数据
		$("#dgs").datagrid("load", params);
	}

	function frozens() {
		var bool = window.confirm("确定要删除选中通知么？");
		if (bool) {
			var ids = [];
			var rows = $('#dgs').datagrid('getSelections');
			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			if (ids.length > 0) {
				$("#frozenIds").val(ids.join(','));
				$("#frozenForms").submit();
			} else {
				alert("请选择要删除的通知");
			}
		} else {

		}
	}
	function deletetagss() {
		var bool = window.confirm("确定要删除选中公司么？");
		if (bool) {
			var ids = [];
			var rows = $('#dgs').datagrid('getSelections');
			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].supplier_id);
			}
			alert(ids);
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
	function closetj() {
		$("#goout-box-undertj").css("display", "none");
	}
	function closetg() {
		$("#goout-box-undertg").css("display", "none");
	}
	function closetgs() {
		$("#goout-box-undertgs").css("display", "none");
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
	function productUnderbq() {
		$("#productIllegalUnderShelfbq").submit();
	}
	function productUndertjs() {
		$("#productIllegalUnderShelftj").submit();
	}
	function productUndertjsupdate() {
		$("#productIllegalUnderShelftjupdate").submit();
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
			action="${pageContext.request.contextPath}/infrastructure/deleteNotice"
			method="post">
			<input type="hidden" id="frozenIds" name="ids" />
		</form>
		<form id="unfreezeForms"
			action="${pageContext.request.contextPath}/infrastructure/deleteNotice"
			method="post">
			<input type="hidden" id="unfreezeIds" name="ids" />
		</form>
		<div id="tb">
			<button type="button" class="btn btn-warning" onclick="frozen()">添加通知</button>
			<button type="button" class="btn btn-warning" onclick="frozens()">删除选中</button>
		</div>
		<form action="" id="cardqueryForm">
			<br /> <br /> 通知标题:<input type="text" name="title"> 是否弹窗: <select
				name="grade">
				<option value="">请选择</option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select> 消息状态: <select name="status">
				<option value="">请选择</option>
				<option value="1">显示</option>
				<option value="0">不显示</option>
			</select> <input type="button" value="搜索" onclick="reloadquery()"><br />
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
					<h2>添加平台通知</h2>
				</div>
				<div class="lightbox-box-bd">
					<form id="productIllegalUnderShelftj"
						action="${pageContext.request.contextPath}/infrastructure/addNotices"
						method="post">
						<h3></h3>
						<br />
						<ul class="ul_vertical">
							通知标题:
							<input type="text" name="title" > 是否弹窗:
							<select name="grade" >
								<option value="">请选择</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select> 消息状态:
							<select name="status" >
								<option value="">请选择</option>
								<option value="1">显示</option>
								<option value="0">不显示</option>
							</select>
							<br> 消息类型:
							<input type="text" name="type" > 截止时间:
							<input style="width: 100px;" class="text1" name="stopTime"
								 value="" type="text" onclick="WdatePicker()">
								<input type="hidden" name="publishUser" value="${loginUser.username}">
							<li>
								<p class="p1">通知内容：</p> <textarea  rows="10" 
									cols="50" name="content" style="outline: none; resize: none;" 
									onkeydown="countChar('txtF_Content','counter',1000);"></textarea>
							</li>
							<li class="blank20" style="visibility: visible;">
								<p class="p1"></p>
								
							</li>
							<li><p class="p1"></p></li>
							<li></li>
						</ul>
					</form>
				</div>
				<br />
				<div class="lightbox-box-bar">
					<a href="javascript:void(0);" class="lightbox-btn true-btn"
						onclick="productUndertjs()">添加</a> <span
						style="margin-left: 20px; color: red;" id="boxwarnunder"></span>
				</div>
			</div>
		</div>
		<div class="lightbox" id="goout-box-undertg">
			<div class="lightbox-overlay"></div>
			<div class="lightbox-box">
				<div class="close-box" onclick="closetg()"></div>
				<div class="lightbox-box-hd">
					<h2>查看平台通知</h2>
				</div>
				<div class="lightbox-box-bd">
					<form id="productIllegalUnderShelftj"
						action="${pageContext.request.contextPath}/infrastructure/addNotices"
						method="post">
						<h3></h3>
						<br />
						<ul class="ul_vertical">
							通知标题:
							<input type="text" name="title" id="title">
							<br> 是否弹窗:
							<select name="grade" id="grade">
								<option value="">请选择</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select> 消息状态:
							<select name="status" id="status">
								<option value="">请选择</option>
								<option value="1">显示</option>
								<option value="0">不显示</option>
							</select>消息类型:
							<input type="text" name="type" id="type"><br> 截止时间:
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
							</li>
							<li><p class="p1"></p></li>
							<li></li>
						</ul>
					</form>
				</div>
			</div>
		</div>
		<div class="lightbox" id="goout-box-undertgs">
			<div class="lightbox-overlay"></div>
			<div class="lightbox-box">
				<div class="close-box" onclick="closetgs()"></div>
				<div class="lightbox-box-hd">
					<h2>修改平台通知</h2>
				</div>
				<div class="lightbox-box-bd">
					<form id="productIllegalUnderShelftjupdate"
						action="${pageContext.request.contextPath}/infrastructure/updateNotices"
						method="post">
						<h3></h3>
						<br />
						<ul class="ul_vertical">
							通知标题:
							<input type="text" name="title" id="titles">
							<br> 是否弹窗:
							<select name="grade" id="grades">
								<option value="">请选择</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select> 消息状态:
							<select name="status" id="statuss">
								<option value="">请选择</option>
								<option value="1">显示</option>
								<option value="0">不显示</option>
							</select>
							消息类型:
							<input type="text" name="type" id="types"><br> 截止时间:
							<input style="width: 100px;" class="text1" name="stopTime"
								id="stopTimes" value="" type="text" onclick="WdatePicker()">
								<input type="hidden" name="publishUser" value="${loginUser.username}">
								<input type="hidden" name="id" id="id">
							<li>
								<p class="p1">通知内容：</p> <textarea id="txtF_Contents" rows="10" 
									cols="50" name="content" style="outline: none; resize: none;" 
									onkeydown="countChar('txtF_Content','counter',1000);"></textarea>
							</li>
							<li class="blank20" style="visibility: visible;">
								<p class="p1"></p>
							</li>
							<li><p class="p1"></p></li>
							<li></li>
						</ul>
					</form>
					<div class="lightbox-box-bar">
					<a href="javascript:void(0);" class="lightbox-btn true-btn"
						onclick="productUndertjsupdate()">确定</a> <span
						style="margin-left: 20px; color: red;" id="boxwarnunder"></span>
				</div>
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