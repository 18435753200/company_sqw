var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {
	clickSubmit(1);

});

/* 查询条件 */
// page 页数
function clickSubmit(page) {
	var pro_array = new Array();
	var ruleId = $("#ruleId").val();
	var pType = $("#pType").val();
	
	if (ruleId != "" && ruleId != null) {
		pro_array.push("ruleId=" + ruleId);
	}
	if (page != "" && page != undefined) {
		pro_array.push("page=" + page);
	}
	if(pType != "" && pType != undefined){
		pro_array.push("pType=" + pType);
	}
	 
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/gift/findGiftByCondition",
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

function backButton(ruleId) {
	window.location.href = CONTEXTPATH+"/platformRule/toRuleList?ruleId="+ruleId;
}

function toAddGift() {

	var ruleId = $("#ruleId").val();
	var pType = $("#pType").val();
	var giftType = $("#giftType").val();
	window.location.href = CONTEXTPATH+"/gift/toGiftAdd?ruleId="+ruleId+"&giftType="+giftType+"&pType="+pType;
}
