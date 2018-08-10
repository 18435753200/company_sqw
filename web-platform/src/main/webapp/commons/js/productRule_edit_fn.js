var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {
	// view页面返回
	$("#backButton").click(function() {
		var activeId = $("#activeId").val();
		window.location.href = CONTEXTPATH+"/platformRule/toRuleList?activeId="+activeId;
	})
	// 提交
    $("#confirmButton").click(function() {
        var flag = checkSubmit();
        if (flag == false) {
            tipMessage('部分信息不完整或不符合规范，请修改！', function() {
            });
        }
       var activeId = $("#activeId").val();
        if (flag == true) {
            $.ajax({
                type : 'post',
                url : CONTEXTPATH+"/platformRule/editPlatformRule",
                data : $('#platformRuleAction').serialize(),
                success : function(data) {
                    if (data == 'success') {
                        tipMessage("成功，将立即返回到规则列表！", function() {
                            window.location.href = CONTEXTPATH+"/platformRule/toRuleList?activeId="+activeId;
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


// 提交校验项
function checkSubmit() {
    var flag = true;
    // 校验名称
    var ruleName = $('#ruleName').val();
    if (ruleName.length > 100 || ruleName.length < 1) {
        flag = false;
        $('#ruleName').next().text('活动名称必填，且不能超过100字！').show();
    } else {
        $('#ruleName').next().hide();
    }
   
    // 规则描述长度校验
    var desc = $('#description').val();
    if (desc.length > 200) {
        flag = false;
        $('#description').next().text('描述不能超过200字！').show();
    } else {
        $('#description').next().hide();
    }

    return flag;
}

