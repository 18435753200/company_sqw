var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {
	clickSubmit(1);

});

/* 查询条件 */
// page 页数
function clickSubmit(page) {
	var pro_array = new Array();
	var activeId = $("#activeId").val();

	if (activeId != "" && activeId != undefined) {
		pro_array.push("activeId=" + activeId);
	}
	if (page != "" && page != undefined) {
		pro_array.push("page=" + page);
	}
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/platformRule/findRuleByCondition",
		data : pro_array.join("&"),
		dataType : "html",
		success : function(msg) {
			$(".promotion-bd").html(msg);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("对不起，数据异常请稍后再试!");
		}

	});

}

function createPlatform() {
	var activeId = $("#activeId").val();
	window.location.href = "../platformRule/toPlatformRuleEdit?activeId="+ activeId;

}

function createPlatformB2C(){
	var activeId = $("#activeId").val();
	window.location.href = "../platformRule/toPlatformRuleB2CEdit?activeId="+ activeId;
}

function deleteRule(activeId, ruleId) {
	var pro_array = new Array();
	if (ruleId != "") {
		pro_array.push("ruleId=" + ruleId);
	}

	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/platformRule/deleteRule",
		data : pro_array.join("&"),
		success : function(data) {
			if (data == 'success') {
				tipMessage("删除成功，将立即返回到规则列表！", function() {
					window.location.href = "../platformRule/toRuleList?activeId="
							+ activeId;
				});
			} else if (data == 'error') {
				tipMessage("删除失败，请稍后重试！", function() {
				});
			} else {
				tipMessage("删除失败,原因：" + data, function() {
				});
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("对不起，请稍后再试!");
		}

	});

}

function stopOrEnableRule(activeId, ruleId, status, pType) {
	var pro_array = new Array();
	if (ruleId != "") {
		pro_array.push("ruleId=" + ruleId);
	}
	if (pType != "") {
		pro_array.push("pType=" + pType);
	}
	if (status != "") {
		if (status == 0) {
			pro_array.push("status=1");
		}
		if (status == 1) {
			pro_array.push("status=0");
		}

	}
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/platformRule/stopOrEnableRule",
		data : pro_array.join("&"),

		success : function(data) {
			if (data == 'success') {
				tipMessage("修改成功，将立即返回到规则列表！", function() {
					window.location.href = "../platformRule/toRuleList?activeId="
							+ activeId;
				});
			} else if (data == 'error') {
				tipMessage("修改失败，请稍后重试！", function() {
				});
			} else {
				tipMessage("修改失败,原因：" + data, function() {
				});
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("对不起，请稍后再试!");
		}

	});
}
