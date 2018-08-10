
var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {
	clickSubmit(1);

});

/* 查询条件 */
// page 页数
function clickSubmit(page) {
	var activityName = $.trim($("#activityName").val());
	var activityTime = $("#activityTime").val();
	var pro_array = new Array();
	if (activityName != "") {
		pro_array.push("activityName=" + activityName);
	}
	if (activityTime != "" && activityTime != null) {
		pro_array.push("activityTime=" + activityTime);
	}
	if (page != "" && page != undefined) {
		pro_array.push("page=" + page);
	}
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/activity/findActivityByCondition",
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

function createActive() {
	window.location = CONTEXTPATH + "/activity/toActivityEdit";
}

function stopOrEnableActivity(activeId,status) {
	var pro_array = new Array();
	if(activeId !=""){
	        pro_array.push("activeId=" + activeId);
   }
	if (status != "") {
       if(status==0){
           pro_array.push("status=1");
       }
       if(status==1){
           pro_array.push("status=0");
       }
       
   }
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/activity/stopOrEnableActivity",
		data : pro_array.join("&"),
		
		success : function(data) {
			if (data == 'success') {
               tipMessage("修改成功，将立即返回到活动列表！", function() {
                   window.location.href = "../activity/toActivityList?activeId=" + activeId;
               });
           } else if(data == 'error') {
               tipMessage("修改失败，请稍后重试！", function() {
               });
           }else{
               tipMessage("修改失败,原因："+data, function() {
               });
           }
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("对不起，请稍后再试!");
		}
		
	});

}
