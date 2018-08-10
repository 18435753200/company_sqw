var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {

    clickSubmit(1);
    
});

/* 查询条件 */
// page 页数
function clickSubmit(page) {
	var reg = new RegExp("^[0-9]*$");
    var status = $("#status").val();  
    var username = $("#username").val();  
    var couponacount = $("#couponacount").val();  
    var starttime = $("#starttime").val();  
    var endtime = $("#endtime").val();  
    var userType = $("#userType").val();
    var pro_array = new Array();
    if (userType != "") {
        pro_array.push("userType=" + userType);
    }
    if (status != "") {
        pro_array.push("status=" + status);
    }
    if (username != "") {
        pro_array.push("username=" + username);
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
        url : CONTEXTPATH + "/coupon/getCouponRecordsByCon",
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
    var status = $("#status").val();  
    var username = $("#username").val();  
    var couponacount = $("#couponacount").val();  
    var starttime = $("#starttime").val();  
    var endtime = $("#endtime").val();  
    var pro_array = new Array();
    if (status != "") {
        pro_array.push("status=" + status);
    }
    if (username != "") {
        pro_array.push("username=" + username);
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
		window.location.href=CONTEXTPATH+"/coupon/downCheckListExcel?"+pro_array.join("&");
	}

function findUseRecord(coupontype){
	console.log(couponstockid);
	var pro_array = new Array();
	pro_array.push("couponstockid="+couponstockid);
	pro_array.push("coupontype="+coupontype);
	$.ajax({
        type : "post",
        url : CONTEXTPATH + "/coupon/getCouponUseRecord",
        data : pro_array.join("&"),
        dataType : "json",
        success : function(data) {
            $("#search").html("");	
            for(var obj in data){
            	$li=$("<tr><td>2015-10-22</td><td>1000</td></tr>");
            	$("#search").append($li);
            }
            $(".logw").show();
        },
        error : function(jqXHR, textStatus, errorThrown) {
            alert("对不起，数据异常请稍后再试!");
        }
    });
}

$(function(){
	
	$(".J-bn").live('click',function(){
		var couponstockid = $(this).parent().parent().find(".J-id").text();
		var coupontype = $(this).next().val();
		var userType = $(this).next().next().val();
		var pro_array = new Array();
		pro_array.push("couponstockid="+couponstockid);
		pro_array.push("coupontype="+coupontype);
		pro_array.push("userType="+userType);
		$.ajax({
	        type : "post",
	        url : CONTEXTPATH + "/coupon/getCouponUseRecord",
	        data : pro_array.join("&"),
	        dataType : "json",
	        success : function(data) {
	            $("#search").html("");	
	            for(var obj in data){
	            	var date=data[obj].useDate.date;
	            	var month=data[obj].useDate.month+1;
	            	var year=data[obj].useDate.year+1900;
	            	var hour=data[obj].useDate.hours;
	            	var minute=data[obj].useDate.minutes;
	            	var second=data[obj].useDate.seconds;
	            	var dd = year+"-"+month+"-"+date+"  "+hour+":"+minute+":"+second;
	            	
	            	$li="<tr><td class='date'>"+dd+"</td><td>"+data[obj].amount+"</td></tr>";
	            	$("#search").append($li);
	            }
	            $(".logw").show();
	        },
	        error : function(jqXHR, textStatus, errorThrown) {
	            alert("对不起，数据异常请稍后再试!");
	        }
	    });
	});
	
});

