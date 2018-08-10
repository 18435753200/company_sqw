var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {

    clickSubmit(1);

});

/* 查询条件 */
// page 页数
function clickSubmit(page) {
	var reg = new RegExp("^[0-9]*$");
    var couponName = $.trim($("#couponname").val());
    var couponType = $("#coupontype").val();  
    var couponacount = $("#couponacount").val();  
    var starttime = $("#starttime").val();  
    var endtime = $("#endtime").val();  
    var pro_array = new Array();
    if (couponName != "") {
        pro_array.push("couponname=" + couponName);
    }
    if (couponType != "" && couponType !=0) {
        pro_array.push("coupontype=" + couponType);
    }
    if (couponacount != "") {
    	if(!couponacount.match(reg)){  
            alert("请输入数字!");  
            return false;
        } 
        pro_array.push("couponacount=" + couponacount);
    }
    if (starttime != "") {
        pro_array.push("starttime=" + starttime);
    }
    if (endtime != "") {
        pro_array.push("endtime=" + endtime);
    }
    if (page != "" && page != undefined) {
        pro_array.push("page=" + page);
    }

    $.ajax({
        type : "post",
        url : CONTEXTPATH + "/coupon/getCouponsByCon",
        data : pro_array.join("&"),
        dataType : "html",
        success : function(msg) {
            $(".coupon-bd").html(msg);
        },
        error : function(jqXHR, textStatus, errorThrown) {
            alert("对不起，数据异常请稍后再试!");
        }
    });

}

function downCheckListExcel(){
	var reg = new RegExp("^[0-9]*$");
	var couponName = $.trim($("#couponname").val());
    var couponType = $("#coupontype").val();  
    var couponacount = $("#couponacount").val();  
    var starttime = $("#starttime").val();  
    var endtime = $("#endtime").val();  
    var pro_array = new Array();
    if (couponName != "") {
        pro_array.push("couponName=" + couponName);
    }
    if (couponType != "") {
        pro_array.push("couponType=" + couponType);
    }
    if (couponacount != "") {
    	if(!couponacount.match(reg)){  
            alert("请输入数字!");  
            return false;
        } 
        pro_array.push("couponacount=" + couponacount);
    }
    if (starttime != "") {
        pro_array.push("starttime=" + starttime);
    }
    if (endtime != "") {
        pro_array.push("endtime=" + endtime);
    }
    pro_array.push("meath="+Math.random());
		window.location.href=CONTEXTPATH+"/coupon/downCouponListExcel?"+pro_array.join("&");
	}


