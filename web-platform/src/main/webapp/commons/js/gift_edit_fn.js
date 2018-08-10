var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {
    $('#promotion-name').live('change', checkPrice);
    // 提交
    $("#confirmButton").click(function() {
    	alert('qd');
        var flag = checkSubmit();
        if (flag == false) {
            tipMessage('部分信息不完整或不符合规范，请修改！', function() {
            });
        }
        if (flag == true) {
            $.ajax({
                type : 'post',
                url : CONTEXTPATH + "/activity/findActivityByCondition",
                data : $('#activityAction').serialize(),
                success : function(data) {
                    if (data == 'success') {
                        tipMessage("成功，将立即返回到活动列表！", function() {
                            window.location.href = "../activity/toActivityList";
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
});



/**
 * 校验开始时间
 * 
 * @returns
 */
var checkStartTime = function() {
    var start = new Date();
    var endTime = $("#promotion-startTime").val();
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
    var startTime = $("#promotion-startTime").val();
    var start = new Date(startTime.replace("-", "/").replace("-", "/"));
    var endTime = $("#promotion-endTime").val();
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
    var activeName = $('#promotion-name').val();
    if (activeName.length > 100 || activeName.length < 1) {
        flag = false;
        $('#promotion-name').next().text('活动名称必填，且不能超过100字！').show();
    } else {
        $('#promotion-name').next().hide();
    }
   
    // 校验开始时间
    var startTime = $("#promotion-startTime").val();
    if (startTime == "") {
        $("#promotion-startTime").next().text('开始时间不能为空！').show();
        flag = false;
    } else {
        if (!checkStartTime()) {
            $("#promotion-startTime").next().text('开始时间不能小于系统当前时间！').show();
            flag = false;
        } else {
            $("#promotion-startTime").next().hide();
        }
    }
    // 校验结束时间
    var endTime = $("#promotion-endTime").val();
    if (endTime == "") {
        $("#promotion-endTime").next().text('结束时间不能为空！').show();
        flag = false;
    } else {
        if (!checkEndTime()) {
            $("#promotion-endTime").next().text('结束时间应该大于开始时间！').show();
            flag = false;
        } else {
            $("#promotion-endTime").next().hide();
        }
    }
   
    // 描述长度校验
    var desc = $('#promotion-des').val();
    if (desc.length > 200) {
        flag = false;
        $('#promotion-des').next().text('描述不能超过200字！').show();
    } else {
        $('#promotion-des').next().hide();
    }
    
 // 广告语长度校验
    var desc = $('#promotion-ad').val();
    if (desc.length > 200) {
        flag = false;
        $('#promotion-ad').next().text('描述不能超过200字！').show();
    } else {
        $('#promotion-ad').next().hide();
    }

    return flag;
}
