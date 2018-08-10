var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {
	// 返回
	$("#backButton").click(function() {
		window.location.href = CONTEXTPATH+"/appActivity/appToList";
	});
    // 提交
    $("#confirmButton").click(function() {
        var flag = checkSubmit();
        if (flag == false) {
            /*tipMessage('部分信息不完整或不符合规范，请修改！', function() {
            });*/
        	return false;
        }
        if (flag == true) {
            $.ajax({
                type : 'post',
                url : "../appActivity/appActivityEdit",
                data : $('#activityAPPAction').serialize(),
                success : function(data) {
                    if (data == 'success') {
                        tipMessage("成功，将立即返回到活动列表！", function() {
                            window.location.href = CONTEXTPATH+"/appActivity/appToList";
                        });
                    } else if(data == 'error') {
                        tipMessage("失败，请检查后重试！", function() {
                        });
                    }else{
                        tipMessage("失败,原因："+data, function() {
                        });
                    }
                }
            });
        }
    });
   /* var chuVal = $("#statuss").val();
    if(chuVal==2){
    	$("#timeTo").hide();
		$("#mainPic").hide();
    }
    $('#status').change(function(){
    	var selectVal = $(this).children('option:selected').val();
    	if(selectVal==2){
    		$("#timeTo").hide();
    		$("#mainPic").hide();
    	}
    	if(selectVal==1){
    		$("#timeTo").show();
    		$("#mainPic").show();
    	}
    }); */
});



/**
 * 校验开始时间
 * 
 * @returns
 */
var checkStartTime = function() {
    var start = new Date();
    var endTime = $("#startTime").val();
    var end = new Date(endTime.replace("-", "/").replace("-", "/"));
    if (end < start) {
        return false
    } else {
        return true;
    }

};

/**
 * 校验结束时间
 * 
 * @returns
 */
var checkEndTime = function() {
    var startTime = $("#startTime").val();
    var start = new Date(startTime.replace("-", "/").replace("-", "/"));
    var endTime = $("#endTime").val();
    var end = new Date(endTime.replace("-", "/").replace("-", "/"));
    if (end <= start) {
        return false;
    } else {
        return true;
    }
};
/**
 * 提交校验项
 */
function checkSubmit() {
    var flag = true;
    // 校验名称
    var activityName = $('#activityName').val();
    if (activityName.length > 10 || activityName.length < 1) {
        flag = false;
        $('#activityName').next().text('必填项，字符不超10字！').show();
    } else {
        $('#activityName').next().hide();
    }
    var status = $('#status').val();
    // 校验开始时间
    var startTime = $("#startTime").val();
    if (startTime == ""&&status=="1") {
        $("#startTime").next().text('限时抢购活动开始时间不能为空！').show();
        flag = false;
    } else {
        if (!checkStartTime()) {
            $("#startTime").next().text('开始时间不能小于系统当前时间！').show();
            flag = false;
        } else {
            $("#startTime").next().hide();
        }
    }
    // 校验结束时间
    var endTime = $("#endTime").val();
    if (endTime == ""&&status=="1") {
        $("#endTime").next().text('限时抢购活动结束时间不能为空！').show();
        flag = false;
    } else {
        if (!checkEndTime()) {
            $("#endTime").next().text('结束时间应该大于开始时间！').show();
            flag = false;
        } else {
            $("#endTime").next().hide();
        }
    }
    var mainTitle = $("#mainTitle").val();
    var mainPicUrl = $("#mainPicUrl").val();
    var picUrl = $("#picUrl").val();
    var title = $("#title").val();
    if (mainTitle == "") {
        $("#mainTitle").next().text('一级主图标题不能为空！').show();
        flag = false;
    } else{
    	$("#mainTitle").next().hide();
    }
    if (mainPicUrl == "") {
        $("#mainPicError").text('一级主图不能为空！').show();
        flag = false;
    } else{
    	$("#mainPicError").hide();
    }
    if (picUrl == "") {
        $("#picError").text('二级主图不能为空！').show();
        flag = false;
    } else{
    	$("#picError").hide();
    }
    if (title == "") {
        $("#title").next().text('二级主图标题不能为空！').show();
        flag = false;
    } else{
    	$("#title").next().hide();
    }
    // 活动描述长度校验
    /*var desc = $('#description').val();
    if (desc.length > 200) {
        flag = false;
        $('#description').next().text('描述不能超过200字！').show();
    } else {
        $('#description').next().hide();
    }*/
    
    return flag;
}



function deleteActivityPro(actproId,activityId) {
    if(confirm("是否确定删除改关联商品？")){
    
        $.ajax({
            type : 'post',
            url : CONTEXTPATH + "/appActivity/deleteAPActivityView?actproId="+actproId,
            success : function(data) {
                if (data == 'success') {
                    tipMessage("删除成功！", function() {alert(activityId);
                        window.location.href = CONTEXTPATH+"/appActivity/toAPPActivityView?activityId="+activityId;
                    });
                } else if(data == 'error') {
                    tipMessage("删除失败，请检查后重试！", function() {
                    });
                }
            }
        });
    }
}

