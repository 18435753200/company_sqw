var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {
		//var check = $(this).attr("checked");
	$(".promotion-checkbox").click(function() {
			//var value = $(".J-field").find(":checked").val();
			var value = $("input[name='triggerCondition']:checked").val();
		
			if(value == 106){
				$("#pay").show();
			}else{
				$("#pay").hide();
			}
	});
	
	
		
	/*var triggerCondition = $("#triggerCondition");
	
	$(".J-select").click(function() {
		$("#promotion-condition").show();
	});
	
	if(triggerCondition == 3){
		$("#promotion-condition").show();
	}else{
		$("#promotion-condition").hide();
	}*/
	$("#isrepeat").change(function(){
		var isRepeat = $("#isrepeat").val();
		if(isRepeat == 1 || isRepeat == 0){
			$("#promotion-conditions").show();
		}else{
			$("#promotion-conditions").hide();
		}
	});
	// view页面返回
	$("#backButton").click(function() {
		var activeId = $("#activeId").val();
		window.location.href = CONTEXTPATH+"/platformRule/toRuleList?activeId="+activeId;
	});
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
	$("#confirmButtonB2C").click(function() {
        var flag = checkSubmit();
        if (flag == false) {
            tipMessage('部分信息不完整或不符合规范，请修改！', function() {
            });
        }
       var activeId = $("#activeId").val();
       var str = new Array();
       var str1 = new Array();
       var k = 0;
       $("#promotion-condition .inline-box").each(function(){
    	   str[k] = $(this).find(".selqd").val();
    	   str1[k] = $(this).find(".selzp").val();
    	   k = k+1;
       });
        if (flag == true) {
            $.ajax({
                type : 'post',
                url : CONTEXTPATH+"/platformRule/editPlatformRuleB2C",
                traditional:true,
                data : $('#platformRuleAction').serialize()+"&channels="+str+"&gifts="+str1,
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
        $("#ruleName").next().text('活动名称必填，且不能超过100字！').show();
    } else {
        $('#ruleName').next().hide();
    }
    
    //校验是否重复送
	var value = $("input[name='triggerCondition']:checked").val();
	
	if(value == 3){
		
		if($("#isrepeat").val() == 99){
			$("#isrepeat").next().text("请选择是否重复送券").show();
		}else{
			$("#isrepeat").next().hide();
			//校验数字 
		    var condition1 = $.trim($("#condition1").val());
		    
		    var matchnum = /^[0-9]*$/;
		    if (condition1 != "" && !matchnum.test(condition1) || condition1.length > 6 || condition1 <= 0) {
		        flag = false;
		        $('#condition1').next().text('满足金额只能是正整数，且不超过6位！').show();
		    } else {
		        $('#condition1').next().hide();
		    }
		}
	}else if(value == 101){//渠道注册送
		console.log("---进入循环之前----");
		//判断渠道是否重复
			
		$("#promotion-condition .inline-box").each(function(){
			var channelCode = $(this).find(".selqd option:selected").val();
			var giftCode = $(this).find(".selzp option:selected").val();
			if(channelCode == ''){
				flag = false;
				$(this).find(".selqd").next().text("请勾选渠道").show();
			}else{
				$(this).find(".selqd").next().hide();
			}
			if(giftCode == ''){
				flag = false;
				$(this).find(".selzp").next().text("请勾选赠品").show();
			}else{
				$(this).find(".selzp").next().hide();
			}
		});
		
		var btr = $("#promotion-condition .inline-box");
		btr.each(function(index){
		    var inputS=$(this).find(".selqd option:selected").val();
			var inputE=$(this).find(".selzp option:selected").val(); 
		    var nextinputS=$(this).prevAll().find(".selqd option:selected");
		    if(index==btr.length-1){
		    	for(var i=0;i<nextinputS.length;i++){
					if(nextinputS.eq(i).val()==inputS){
						flag = false;
						alert("渠道不能重复");
					}
				}
		    }
		 });
		
	}
    
	
    // 规则描述长度校验
   /* var desc = $('#description').val();
    if (desc.length > 200) {
        flag = false;
        $('#description').next().text('描述不能超过200字！').show();
        flag = false;
    } else {
        $('#description').next().hide();
    }*/
    
    return flag;
}

