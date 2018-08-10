
var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {
	clickSubmit(1);
});

/* 查询条件 */
// page 页数
function clickSubmit(page) {
	var activityName = $.trim($("#activityName").val());
	//var activityTime = $("#activityTime").val();
	var pro_array = new Array();
	if (activityName != "") {
		pro_array.push("activityName=" + activityName);
	}
	/*if (activityTime != "" && activityTime != null) {
		pro_array.push("activityTime=" + activityTime);
	}*/
	if (page != "" && page != undefined) {
		pro_array.push("page=" + page);
	}
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/appActivity/findAPPActivityByCondition",
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


function deleteActivity(actId) {
    if(confirm("是否确定删除改活动？")){
    
        $.ajax({
            type : 'post',
            url : CONTEXTPATH + "/appActivity/deleteAPPActivityView?activityId="+actId,
            success : function(data) {
                if (data == 'success') {
                    tipMessage("删除成功！", function() {
                        window.location.href = CONTEXTPATH+"/appActivity/appToList";
                    });
                } else if(data == 'error') {
                    tipMessage("删除失败，请检查后重试！", function() {
                    });
                }
            }
        });
    }
}
