var CONTEXTPATH = $("#conPath").val();

$(document).ready(function() {

});

function clickSubmit(page) {
	var transferNoChar = $("#transferNoChar").val();
	//	var firstDate = $("#firstDate").val();
	// var lastDate = $("#lastDate").val();
	var transferOutWarehouseCodes = $("#transferOutWarehouseCodes").val();
	var transferInWarehouseCodes = $("#transferInWarehouseCodes").val();
	var statusCode = $("#statusCode").val();

	var transferOutWarehouseNames = $("#transferOutWarehouseCodes").find("option:selected").text();
	// transferOutWarehouseName =
	// encodeURI(encodeURI(transferOutWarehouseName));// encodeURI

	var transferInWarehouseNames = $("#transferInWarehouseCodes").find("option:selected").text();

	// transferInWarehouseName =
	// encodeURI(encodeURI(transferInWarehouseName));// encodeURI

	if (transferNoChar.length > 50) {
		alert("调拨单号长度超限");

		return false;
	}

	if (transferOutWarehouseCodes.length > 0
			&& transferInWarehouseCodes.length > 0) {
		if (transferOutWarehouseCodes == transferInWarehouseCodes) {
			alert('调入库房和调出库房不能相同 ');

			return false;
		}
	}

	$("#transferOutWarehouseCode").val(transferOutWarehouseCodes);
	$("#transferInWarehouseCode").val(transferInWarehouseCodes);
	$("#transferOutWarehouseName").val(transferOutWarehouseNames);
	$("#transferInWarehouseName").val(transferInWarehouseNames);
	$("#status").val(statusCode);

	var fm_data = $('#queryallocateProductInfo').serialize();

	if (page != undefined) {
		fm_data += "&page=" + page;
	}

	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/allocateNotify/getAllocatePrdPageBean",
		data : fm_data + "&random=" + Math.random(),
		/**
		 * data : { "transferNoChar" : transferNoChar, "firstDate" : firstDate,
		 * "lastDate" : lastDate, "transferOutWarehouseCode" :
		 * transferOutWarehouseCode, "transferInWarehouseCode" :
		 * transferInWarehouseCode, "transferOutWarehouseName" :
		 * transferOutWarehouseName, "transferInWarehouseName" :
		 * transferInWarehouseName, "status" : status },
		 */
		dataType : "html",
		success : function(msg) {
			$("#cbAllocatePrdList").html(msg);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("查询失败 ，请稍后再试。。");
		}
	});
}


function addAllocatePrdNotify() {
	// window.open(CONTEXTPATH + "/allocateNotify/getAddAllocatePrdPage");
	window.location.href = CONTEXTPATH
			+ "/allocateNotify/getAddAllocatePrdNotifyPage";
}


function operationPage() {
	var transferNoChar = $("#transferNoChar").val();
	var createTime = $("#createTime").val();
	var transferOutWarehouseCode = $("#transferOutWarehouseCode").val();

	var transferOutWarehouseName = $("#transferOutWarehouseCode").find(
			"option:selected").text();

	transferOutWarehouseName = encodeURI(encodeURI(transferOutWarehouseName));// encodeURI
	// alert("transferOutWarehouseCode1--------------------:"+transferOutWarehouseName);
	// alert("transferOutWarehouseCode--------------------:"+transferOutWarehouseCode);
	var transferInWarehouseCode = $("#transferInWarehouseCode").val();

	if (transferNoChar.length == 0) {
		alert('调拨单编号必须输入 ');

		return;
	}

	if (createTime == null || createTime == "") {
		alert('调拨时间不能为空 ');

		return;
	}

	if (transferOutWarehouseCode.length == 0) {
		alert('调出库房不能为空 ');

		return;
	}

	if (transferInWarehouseCode.length == 0) {
		alert('调入库房不能为空 ');

		return;
	}

	if (transferOutWarehouseCode == transferInWarehouseCode) {
		alert('调入库房和调出库房不能相同 ');

		return;
	}

	showDialog(CONTEXTPATH
			+ "/allocateNotify/getOperationPage?transferOutWarehouseCode="
			+ transferOutWarehouseCode + "&transferOutWarehouseName="
			+ transferOutWarehouseName);
}

function showDialog(url) {
	var isChrome = window.navigator.userAgent.indexOf("Chrome") != -1;
	if (isChrome) {
		window
				.open(
						url,
						"库存明细",
						"height="
								+ window.screen.height
								* 0.8
								+ ", width="
								+ window.screen.width
								* 0.8
								+ ", top="
								+ (window.screen.availHeight - 30 - window.screen.height * 0.8)
								/ 2
								+ ", left="
								+ (window.screen.availWidth - 10 - window.screen.width * 0.8)
								/ 2
								+ ", toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
	} else {
		window.showModalDialog(url, window, "dialogWidth:"
				+ window.screen.width * 0.8 + "px;dialogHeight:"
				+ window.screen.height * 0.8 + "px");
	}
}


function callParentFunction() {
	var pWindow = window.dialogArguments;
	if (pWindow != null) {
		pWindow.doThingsAfterAdd(param);

	} else {
		window.opener.doThingsAfterAdd(param);
	}
}


function selectPage() {
	var transferOutWarehouseCode = $("#transferOutWarehouseCode").val();
	var transferOutWarehouseName = $("#transferOutWarehouseName").val();
	var pcode = $("#pcode").val();
	var barCode = $("#barCode").val();
	var pname = $("#pname").val();
	var isgenuine = $("#isgenuine").val();
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/allocateNotify/getOperationDataToTable",
		data : {
			"transferOutWarehouseCode" : transferOutWarehouseCode,
			"transferOutWarehouseName" : transferOutWarehouseName,
			"pcode" : pcode,
			"barCode" : barCode,
			"pname" : pname,
			"isgenuine" : isgenuine
		},
		dataType : "html",
		success : function(msg) {
			$("#cbAllocateOpeList").html(msg);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("查询失败 ，请稍后再试。。");
		}
	});
}


function delOperation() {
	
	/**var str = document.getElementsByName("skuName");
	var objarray = str.length;
	for ( var i = 0; i < objarray; i++) {
		if (str[i].checked == true) {

			$('#sho tr').eq(str[i].value).remove();
		}else {
			alert("请选择需要删除的记录");
		}
	}*/
	
	var num = 0;
	$(':checkbox[name=skuName]').each(function(){
		if($(this).attr('checked')){
			$(this).closest('tr').remove();
			num++;
		}
	});
	var len = $("#sho tr").length;
	if(len==1){
		$("#transferOutWarehouseCode").removeAttr("disabled"); 
		$("#transferInWarehouseCode").removeAttr("disabled"); 
	}
}


function save() {
	var transferOutWarehouseCodes = $("#transferOutWarehouseCode").val();
	var transferOutWarehouseNames = $("#transferOutWarehouseCode").find(
			"option:selected").text();
	var transferInWarehouseCodes = $("#transferInWarehouseCode").val();
	var transferInWarehouseNames = $("#transferInWarehouseCode").find(
			"option:selected").text();

	$("#transferOutWarehouseCodes").val(transferOutWarehouseCodes);
	$("#transferInWarehouseCodes").val(transferInWarehouseCodes);
	$("#transferOutWarehouseNames").val(transferOutWarehouseNames);
	$("#transferInWarehouseNames").val(transferInWarehouseNames);

	var transferNoChar = $("#transferNoChar").val();
	var createTime = $("#createTime").val();

	if (transferNoChar.length == 0) {
		alert('调拨单编号必须输入 ');

		return;
	}

	if (createTime == null || createTime == "") {
		alert('调拨时间不能为空 ');

		return;
	}

	if (transferOutWarehouseCodes.length == 0) {
		alert('调出库房不能为空 ');

		return;
	}

	if (transferInWarehouseCodes.length == 0) {
		alert('调入库房不能为空 ');

		return;
	}

	if (transferOutWarehouseCodes == transferInWarehouseCodes) {
		alert('调入库房和调出库房不能相同 ');

		return;
	}

	var chkTrue = "";
	$("input[type='checkbox'][name='skuName']").each(function() {
		if (this.checked) {
			chkTrue = chkTrue + "1,";
		} else {
			chkTrue = chkTrue + "0,";
		}
	});

	chkTrue = chkTrue.substring(0, chkTrue.length - 1);
	if (chkTrue != "") {
		$("#chkTrue").val(chkTrue);
	}

	var flag = false;
	var opetion = $('#sho .sotClass');
	for ( var i = 0; i < opetion.length; i++) {
		var op1 = opetion[i].getElementsByTagName('input')[0];
		var op6 = opetion[i].getElementsByTagName('input')[6];
		var op7 = opetion[i].getElementsByTagName('input')[7];

		if (op1.checked == true && op6.value - op7.value < 0) {
			alert('调拨数量大于库存数量，请重新输入调拨数量');
			return false;
		}

		if (op1.checked == true && op7.value == 0) {
			alert('调拨数量不能为0');
			return false;
		}

		if (op1.checked == true) {
			flag = true;
		}
	}

	if (!flag) {
		alert('请选择需要调拨的库存商品');
		return false;
	}

	var fm_data = $('#addAllocatePrdFrom').serialize();
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/allocateNotify/saveAllocateNotify",
		data : fm_data + "&random=" + Math.random(),
		dataType : "text",
		success : function(msg) {
			// alert("msg-----------------"+msg);
			window.location.href = CONTEXTPATH
					+ "/allocateNotify/getAllocateProductPage";
			/*
			 * if(msg =="success"){ window.location.href =
			 * CONTEXTPATH+"/allocateNotify/getNotificationInOrder"; }else{
			 * alert("操作失败,请重试"); }
			 */
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("请稍后再试。。");
		}
	});
}


function showUpdatePage(sid) {

	window.location.href = CONTEXTPATH + "/allocateNotify/showUpdatePage?sid="
			+ sid;
}


function delComp() {
	/**
	var str = document.getElementsByName("checkName");
	var objarray = str.length;
	for ( var i = 0; i < objarray; i++) {
		if (str[i].checked == true) {
			
			$('#shoComp tr').eq(str[i].value).remove();
		}
	}*/
	
	var num = 0;
	$(':checkbox[name=checkName]').each(function(){
		if($(this).attr('checked')){
			$(this).closest('tr').remove();
			num++;
		}
	});
	var len = $("#shoComp tr").length;
	if(len==1){
		$("#transferOutWarehouseCode").removeAttr("disabled"); 
		$("#transferInWarehouseCode").removeAttr("disabled"); 
	}
}


function opeComp(){
	var transferOutWarehouseCode = $("#transferOutWarehouseCode").val();
	var transferOutWarehouseName = $("#transferOutWarehouseCode").find("option:selected").text();
	transferOutWarehouseName = encodeURI(encodeURI(transferOutWarehouseName));// encodeURI

	showDialog(CONTEXTPATH
			+ "/allocateNotify/getOperationPage?transferOutWarehouseCode="
			+ transferOutWarehouseCode + "&transferOutWarehouseName="
			+ transferOutWarehouseName);
}


function saveComp(){
	var transferOutWarehouseCodes = $("#transferOutWarehouseCode").find("option:selected").val();
	var transferOutWarehouseNames = $("#transferOutWarehouseCode").find("option:selected").text();
	var transferInWarehouseCodes = $("#transferInWarehouseCode").find("option:selected").val();
	var transferInWarehouseNames = $("#transferInWarehouseCode").find("option:selected").text();

	$("#transferOutWarehouseCodes").val(transferOutWarehouseCodes);
	$("#transferInWarehouseCodes").val(transferInWarehouseCodes);
	$("#transferOutWarehouseNames").val(transferOutWarehouseNames);
	$("#transferInWarehouseNames").val(transferInWarehouseNames);
	
	var chkTrue = "";
	$("input[type='checkbox'][name='checkName']").each(function() {
		if (this.checked) {
			chkTrue = chkTrue + "1,";
		} else {
			chkTrue = chkTrue + "0,";
		}
	});

	chkTrue = chkTrue.substring(0, chkTrue.length - 1);
	if (chkTrue != "") {
		$("#chkTrue").val(chkTrue);
	}

	var flag = false;
	var opetion = $('#shoComp .sotClassComp');
	for ( var i = 0; i < opetion.length; i++) {
		var op1 = opetion[i].getElementsByTagName('input')[0];
		var op6 = opetion[i].getElementsByTagName('input')[6];
		var op7 = opetion[i].getElementsByTagName('input')[7];

		if (op1.checked == true && op6.value - op7.value < 0) {
			alert('调拨数量大于库存数量，请重新输入调拨数量');
			return false;
		}

		if (op1.checked == true && op7.value == 0) {
			alert('调拨数量不能为0');
			return false;
		}

		if (op1.checked == true) {
			flag = true;
		}
	}

	if (!flag) {
		alert('请选择需要保存的调拨记录');
		return false;
	}

	var fm_data = $('#opeAllocatePrdFrom').serialize();
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/allocateNotify/saveCompAllocateNotify",
		data : fm_data + "&random=" + Math.random(),
		dataType : "text",
		success : function(msg) {
			window.location.href = CONTEXTPATH
					+ "/allocateNotify/getAllocateProductPage";
			/*
			 * if(msg =="success"){ window.location.href =
			 * CONTEXTPATH+"/allocateNotify/getNotificationInOrder"; }else{
			 * alert("操作失败,请重试"); }
			 */
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("请稍后再试。。");
		}
	});
	
}


function auditComp(){
	var transferOutWarehouseCodes = $("#transferOutWarehouseCode").find("option:selected").val();
	var transferOutWarehouseNames = $("#transferOutWarehouseCode").find("option:selected").text();
	var transferInWarehouseCodes = $("#transferInWarehouseCode").find("option:selected").val();
	var transferInWarehouseNames = $("#transferInWarehouseCode").find("option:selected").text();

	$("#transferOutWarehouseCodes").val(transferOutWarehouseCodes);
	$("#transferInWarehouseCodes").val(transferInWarehouseCodes);
	$("#transferOutWarehouseNames").val(transferOutWarehouseNames);
	$("#transferInWarehouseNames").val(transferInWarehouseNames);
	
	var chkTrue = "";
	$("input[type='checkbox'][name='checkName']").each(function() {
		if (this.checked) {
			chkTrue = chkTrue + "1,";
		} else {
			chkTrue = chkTrue + "0,";
		}
	});

	chkTrue = chkTrue.substring(0, chkTrue.length - 1);
	if (chkTrue != "") {
		$("#chkTrue").val(chkTrue);
	}

	var flag = false;
	var opetion = $('#shoComp .sotClassComp');
	for ( var i = 0; i < opetion.length; i++) {
		var op1 = opetion[i].getElementsByTagName('input')[0];
		var op6 = opetion[i].getElementsByTagName('input')[6];
		var op7 = opetion[i].getElementsByTagName('input')[7];

		if (op1.checked == true && op6.value - op7.value < 0) {
			alert('调拨数量大于库存数量，请重新输入调拨数量');
			return false;
		}

		if (op1.checked == true && op7.value == 0) {
			alert('调拨数量不能为0');
			return false;
		}

		if (op1.checked == true) {
			flag = true;
		}
	}

	if (!flag) {
		alert('请选择需要审核的调拨记录');
		return false;
	}

	var fm_data = $('#opeAllocatePrdFrom').serialize();
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/allocateNotify/auditCompAllocateNotify",
		data : fm_data + "&random=" + Math.random(),
		dataType : "text",
		success : function(msg) {
			window.location.href = CONTEXTPATH
					+ "/allocateNotify/getAllocateProductPage";
			/*
			 * if(msg =="success"){ window.location.href =
			 * CONTEXTPATH+"/allocateNotify/getNotificationInOrder"; }else{
			 * alert("操作失败,请重试"); }
			 */
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("请稍后再试。。");
		}
	});
}


function complePrdNotify(){
	var sids = document.getElementsByName("sid");
	var objarray = sids.length;
	var chestr = "";
	for ( var i = 0; i < objarray; i++) {
		if (sids[i].checked == true) {
			chestr += sids[i].value + ",";
		}
	}
	
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/allocateNotify/compleAllocateNotify",
		data : {
			"sids" : chestr
		},
		dataType : "json",
		success : function(data) {
			if(data.success){
				alert(data.message);
				//window.location.href = CONTEXTPATH + "/allocateNotify/getAllocateProductPage";
			}else{
				alert(data.message);
			};
		},
		error:function(json){
            alert("处理异常");
        }
		
	});
}


function clearStock(sid,status){
	
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/allocateNotify/releaseStock",
		data : {
			"sid" : sid,
			"status" : status
		},
		dataType : "text",
		success : function(msg) {
			window.location.href = CONTEXTPATH + "/allocateNotify/getAllocateProductPage";
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("查询失败 ，请稍后再试。。");
		}
	});
	
}