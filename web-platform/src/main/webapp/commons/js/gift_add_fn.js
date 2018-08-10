var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {
	clickSubmit(1);

});

/* 查询条件 */
// page 页数
function clickSubmit(page) {
	var pro_array = new Array();
	var giftType = $("#giftType").val();
	var pType = $("#pType").val();
	var ruleId = $("#ruleId").val();
	if (giftType != "" && giftType != null) {
		pro_array.push("giftType=" + giftType);
	}
	if (ruleId != "" && ruleId != null) {
		pro_array.push("ruleId=" + ruleId);
	}
	if (pType != "" && pType != null) {
		pro_array.push("pType=" + pType);
	}
	if (page != "" && page != undefined) {
		pro_array.push("page=" + page);
	}
	
	 
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/gift/findGiftByCon",
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
	window.location.href = CONTEXTPATH+"/gift/toGiftList?ruleId="+ruleId;
}

function addGift(couponruleid,couponrulename) {
	var pType = $("#pType").val();
	var ruleId = $("#ruleId").val();
	var giftType = $("#giftType").val();
	window.location.href = CONTEXTPATH+"/gift/toGiftAdd?ruleId="+ruleId+"&pType="+pType+"&couponruleid="+couponruleid+"&giftType="+giftType+"&couponrulename="+couponrulename;
};


