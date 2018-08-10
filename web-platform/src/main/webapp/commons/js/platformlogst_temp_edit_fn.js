var CONTEXTPATH = $("#conPath").val();
var matchnum = /^(\+|-)?\d+$/;
/* 商品js */
$(document).ready(
	function() {
		var errorMsg = $("#errorMsg").val();
		
		if (errorMsg != "") {
			tipMessage(errorMsg + ",将立即返回到运费模板列表", function() {
				window.location.href = CONTEXTPATH
						+ "/platformlogist/getLogisticsPage";
			});
		}
		// 加载省份
		getProvince();
		//初始化页面
		init();

		$(".piece").click(function() {
			$("#basewp").text("件");
			$("#stepwp").text("件");
		});
		$(".weight").click(function() {
			$("#basewp").text("公斤");
			$("#stepwp").text("公斤");
			
		});
		
		$("#baseQt").change(function(){
			checkQt();
		});
		$("#stepQt").change(function(){
			checkQt();
		});
		$("#baseFee").change(function(){
			checkFee();
		});
		$("#stepFee").change(function(){
			checkFee();
		});
		
		$("#createTemp").click(function() {
			var flag = checkSubmit();
			if (flag == false) {
				tipMessage('部分信息不完整或不符合规范，请修改！',
						function() {
						});
			}
			if (flag == true) {
				var logisticTempId = $("#logisticTempId").val();
				var url="../platformlogist/createTemplate";
				if(logisticTempId !=0){
					url="../platformlogist/updateTemplate";
				}
				$.ajax({
					type : 'post',
					url : url,
					data : $('#templateAction').serialize(),
					success : function(data) {
						if (data == 'success') {
							tipMessage("保存成功，将立即返回到模板列表！",
									function() {
										window.location.href = "../platformlogist/getLogisticsPage";
									});
						} else if (data == 'error') {
							tipMessage("保存失败，请检查后重试！",
									function() {
									});
						} else {
							tipMessage("保存失败,原因：" + data,function() {
									});
						}
					}
				});
			}
		});

	});

/**
 * 初始化赋值
 */
function init() {
	var logisticTempId = $("#logisticTempId").val();
	var type = $("#typeDef").val();
	if (logisticTempId != 0) {
		//修改初始化
		if (type == 1) {
			$(".weight").attr("checked", "checked");
			$("#basewp").text("公斤");
			$("#stepwp").text("公斤");
		} else if (type == 2) {
			$(".piece").attr("checked", "checked");
			$("#basewp").text("件");
			$("#stepwp").text("件");
			$("#baseQt").val(Number($("#baseQt").val()).toFixed(0));
			$("#stepQt").val(Number($("#stepQt").val()).toFixed(0));
		}
		//省市初始化
		var oldPStart = $("#provinceStartId").attr("defaultVal");
		var oldPEnd = $("#provinceEndId").attr("defaultVal");
		
		if(oldPStart != 0){
			$("#provinceStartId option").each(function() { 
				if(oldPStart == $(this).val()){
					$(this).attr("selected","selected"); 
					return false;
				}
			});
		}
		if(oldPEnd != 0){
			$("#provinceEndId option").each(function() { 
				if(oldPEnd == $(this).val()){
					$(this).attr("selected","selected"); 
					return false;
				}
			});
		}
		
	} else {
		//新增初始化
		$("#baseQt").val("");
		$("#baseFee").val("");
		$("#stepQt").val("");
		$("#stepFee").val("");
		$("#timeLimitMin").val("");
		$("#timeLimitMax").val("");
	}
}

function getProvince() {
	$.ajax({
		async : false,
		type : "post",
		url : CONTEXTPATH + "/platformlogist/getProvince",
		success : function(msg) {
			$.each(eval(msg), function(i, n) {
				$("#provinceStartId").append(
						"<option value='" + n.provinceid + "'>"
								+ n.provincename + "</option>");
				$("#provinceEndId").append(
						"<option value='" + n.provinceid + "'>"
								+ n.provincename + "</option>");
			});
		}
	});
}


/**
 * 提交项校验
 * 
 * @returns {Boolean}
 */
function checkSubmit() {
	
	var flag = true;
	//校验名称
	var logisticTempName=$("#logisticTempName").val();
	if(logisticTempName.length<1 || logisticTempName.length>20){
		flag = false;
		$('#logisticTempName').next().text('请输入模板名称，且20字内').show();
	} else {
		$('#logisticTempName').next().hide();
	}
	// 校验出发地
	var provinceStartId = $('#provinceStartId').val();
	if (provinceStartId == "0") {
		flag = false;
		$('#provinceStartId').next().text('请选择发货地省份').show();
	} else {
		$('#provinceStartId').next().hide();
	}
	// 校验目的地
	var provinceEndId = $('#provinceEndId').val();
	if (provinceEndId == "0") {
		flag = false;
		$('#provinceEndId').next().text('请选择目的地省份').show();
	} else {
		$('#provinceEndId').next().hide();
	}
	// 校验首重，续重
	if(checkQt()==false){
		flag = false;
	}
	if(checkFee()==false){
		flag = false;
	}
	// 校验时效
	var timeLimitMin = $.trim($("#timeLimitMin").val());
	var timeLimitMax = $.trim($("#timeLimitMax").val());
	if ((!matchnum.test(timeLimitMin) || timeLimitMin.length > 9 || Number(timeLimitMin) <=0) || 
			(!matchnum.test(timeLimitMax) || timeLimitMax.length > 9 || parseInt(timeLimitMax)<parseInt(timeLimitMin))) {
		$("#timeLimitMax").next().text('时效请填写小于10位的正整数,且后者大于前者').show();
		flag = false;
	}else{
		$("#timeLimitMax").next().hide();
	}
	return flag;
}


/**
 * 校验首重 续重
 * @returns {Boolean}
 */
function checkQt(){
	var type = $('input[name="type"]:checked').val();
	var baseQy = $("#baseQt").val();
	var stepQt = $("#stepQt").val();
	var baseFee = $("#baseFee").val();
	var stepFee = $("#stepFee").val();
	var flag=true;
	// 按重量计费
	if (type == "1") {
		if (baseQy.length < 1
				|| ((!RegExp("^\\d{1,4}\\.\\d+$").test(baseQy) && !RegExp(
						"^\\d{1,4}\\.?$").test(baseQy)) || Number(baseQy) < 0)
				|| (stepQt.length < 1
						|| ((!RegExp("^\\d{1,4}\\.\\d+$").test(stepQt) && !RegExp(
								"^\\d{1,4}\\.?$").test(stepQt))) || Number(stepQt) < 0)) {
			flag = false;
			$("#stepFee").next().next().text('重量必填，且为4位以内正整数或者正浮点数').show();
		} else {
			$("#baseQt").val(Number(baseQy).toFixed(2));
			$("#stepQt").val(Number(stepQt).toFixed(2));
			$("#stepFee").next().next().hide();
		}

	} else {// 按件计费

		if (baseQy.length < 1 || baseQy.length > 10
				||(!matchnum.test(baseQy) || Number(baseQy) < 0) ||
				(stepQt.length < 1 || stepQt.length > 10
						|| (!matchnum.test(stepQt) || Number(stepQt) < 0))
			) {
			flag = false;
			$("#stepFee").next().next().text('件数必填，且为10位以内正整数').show();
		} else {
			$("#baseQt").val(Number(baseQy).toFixed(0));
			$("#stepQt").val(Number(stepQt).toFixed(0));
			$("#stepFee").next().next().hide();
			
			
		}
	}
	return flag;
}

function checkFee() {
	var baseFee = $("#baseFee").val();
	var stepFee = $("#stepFee").val();
	var flag = true;
	if (baseFee.length < 1
			|| ((!RegExp("^\\d{1,9}\\.\\d+$").test(baseFee) && !RegExp(
					"^\\d{1,9}\\.?$").test(baseFee)) || Number(baseFee) < 0)
			|| (stepFee.length < 1
					|| ((!RegExp("^\\d{1,9}\\.\\d+$").test(stepFee) && !RegExp(
							"^\\d{1,9}\\.?$").test(stepFee))) || Number(stepFee) < 0)) {
		flag = false;
		$("#stepFee").next().next().text('费用必填，且为9位以内正整数或者正浮点数').show();
	} else {
		$("#baseFee").val(Number(baseFee).toFixed(2));
		$("#stepFee").val(Number(stepFee).toFixed(2));
		$("#stepFee").next().next().hide();
		flag=checkQt();
		
	}

	return flag;
}