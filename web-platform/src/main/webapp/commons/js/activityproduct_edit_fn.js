var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {
	// 返回
	$("#backButton").click(function() {
		window.location.href = CONTEXTPATH+"/activeproduct/list";
	})
    // 提交
    $("#confirmButton").click(function() {
        var flag = checkSubmit();
        if (flag == false) {
            tipMessage('部分信息不完整或不符合规范，请修改！', function() {
            });
        }
        if (flag == true) {
            $.ajax({
                type : 'post',
                url : "../activeproduct/save",
                data : $('#activeproductForm').serialize(),
                success : function(data) {
                    if (data == 'success') {
                        tipMessage("成功，将立即返回到商品活动列表！", function() {
                            window.location.href = CONTEXTPATH+"/activeproduct/list";
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
    var endTime = $("#expiringFrom").val();
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
    var startTime = $("#expiringFrom").val();
    var start = new Date(startTime.replace("-", "/").replace("-", "/"));
    var endTime = $("#expiringTo").val();
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
    var activeName = $('#activeName').val();
    if (activeName.length > 100 || activeName.length < 1) {
        flag = false;
        $('#activeName').next().text('活动名称必填，且不能超过100字！').show();
    } else {
        $('#activeName').next().hide();
    }
   
    /*// 校验开始时间
    var startTime = $("#expiringFrom").val();
    if (startTime == "") {
        $("#expiringFrom").next().text('开始时间不能为空！').show();
        flag = false;
    } else {
        if (!checkStartTime()) {
            $("#expiringFrom").next().text('开始时间不能小于系统当前时间！').show();
            flag = false;
        } else {
            $("#expiringFrom").next().hide();
        }
    }*/
    // 校验结束时间
    /*var endTime = $("#expiringTo").val();
    if (endTime == "") {
        $("#expiringTo").next().text('结束时间不能为空！').show();
        flag = false;
    } else {
        if (!checkEndTime()) {
            $("#expiringTo").next().text('结束时间应该大于开始时间！').show();
            flag = false;
        } else {
            $("#expiringTo").next().hide();
        }
    }*/
   
    // 活动描述长度校验
    var desc = $('#description').val();
    if (desc.length > 200) {
        flag = false;
        $('#description').next().text('描述不能超过200字！').show();
    } else {
        $('#description').next().hide();
    }
    
 // 广告语长度校验
    var desc = $('#activeMsg').val();
    if (desc.length > 200) {
        flag = false;
        $('#activeMsg').next().text('描述不能超过200字！').show();
    } else {
        $('#activeMsg').next().hide();
    }
    //校验数字
    var ratio = $.trim($("#ratio").val());
    var matchnum = /^[0-9.]*$/;
    if (ratio != "" && !matchnum.test(ratio) || ratio.length > 6) {
        flag = false;
        $('#ratio').next().next().text('承担比例只能是数字，且不超过6位！').show();
    } else {
        $('#ratio').next().next().hide();
    }
    return flag;
}
