var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {
	clickSubmit(1);
    $('#create').click(function(){
        var couponId=$("#couponId").val();
        var coupontype=$("#coupontype").val();
        if(couponId){            
            window.location.href=CONTEXTPATH+"/couponRule/toCreateRule?couponId="+couponId+"&coupontype="+coupontype;
        }else{
            alert("优惠券编码异常!");
        }
    
    });
    
    $('#createB2C').click(function(){
        var couponId=$("#couponId").val();
        var coupontype=$("#coupontype").val();
        if(couponId){            
            window.location.href=CONTEXTPATH+"/productrule/addB2C?couponId="+couponId+"&coupontype="+coupontype;
        }else{
            alert("优惠券编码异常!");
        }
    
    });

});


/* 查询条件 */
// page 页数
function clickSubmit(page) {
    var couponrulename = $.trim($("#couponrulename").val());
    //var meetPiece = $("#meetPiece").val();  
    var couponId=$("#couponId").val();
    var coupontype=$("#coupontype").val();
    var pro_array = new Array();
    if(couponId !=""){
        pro_array.push("couponid=" + couponId);
    }
    if (couponrulename != "") {
        pro_array.push("couponrulename=" + couponrulename);
    }
    //if (meetPiece != "") {
    //    pro_array.push("meetPiece=" + meetPiece);
    //}
    if (page != "" && page != undefined) {
        pro_array.push("page=" + page);
    }
    $.ajax({
        type : "post",
        url : CONTEXTPATH + "/couponRule/getCouponRulesByCon",
        data : pro_array.join("&"),
        dataType : "html",
        success : function(msg) {
            $(".coupon-bd").html(msg);
        },
        error : function(jqXHR, textStatus, errorThrown) {
            alert("对不起，数据异常请稍后再试!");
        }
    });
    
	$("#type").change(function(){
		var couponId = $("#couponId").val();
		var type = $("#type").val();
		if(type == 0){
			$("#type").next().text("请选择优惠券类型").show();
		}else{
			$("#type").next().hide();
			$.ajax({
		        type : "post",
		        url : "../productrule/findAbleB2CCouponsByCouponId",
		        dataType : "json",
		        data : "couponId="+couponId+"&type="+type,
		        success : function(data) {
		            $(".gift_twob").html("");
		            for(var obj in data){
		            	$li=$("<span><input type='radio' name='coupons' class='gi' value='"+data[obj].mainrulename+"'>"+data[obj].couponrulename+"</span>");
			            $(".gift_twob").append($li);
		        	}
		        }
		    });
		}
	});
	
}

function updateStatus(couponruleid,status){
    var couponId=$("#couponId").val();
    var pro_array = new Array();
    if(couponruleid !=""){
        pro_array.push("couponruleid=" + couponruleid);
    }
    if(couponId !=""){
        pro_array.push("couponid=" + couponId);
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
        type : 'post',
        url : '../couponRule/updateStatus',
        data : pro_array.join("&"),
        success : function(data) {
            if (data == 'success') {
                tipMessage("修改成功，将立即返回到规则列表！", function() {
                    window.location.href = "../couponRule/toShowRuleList?couponId=" + couponId;
                });
            } else if(data == 'error') {
                tipMessage("修改失败，请稍后重试！", function() {
                });
            }else{
                tipMessage("修改失败,原因："+data, function() {
                });
            }
        }
    });
}
//送券动作
function giveCouponToUser(){
	//校验数据
	var flag = checkSubmit();
	if(flag == false){
		tipMessage('部分信息不完整或不符合规范，请修改！', function() {
        });
	}else{
		$.dialog.confirm("确认提交？", function() {
			//收集数据
			var desc = $("#desc").val();
			var couponruleid = $('input:radio[name="coupons"]:checked').val();
			var userId = $('input:radio[name="userid"]:checked').val();
			var couponId = $("#couponId").val();
			var type = $("#type").val();
			//ajax异步请求，收集数据，发券，返回成功或者失败提示
			$.ajax({
		        type : 'post',
		        url : '../couponRule/giveCouponToUser',
		        data : "userId="+userId+"&couponruleid="+couponruleid+"&desc="+desc+"&type="+type+"&b2cType="+1,
		        success : function(data) {
		        	tipMessage("成功，将立即返回到规则列表！", function() {
	                    window.location.href = CONTEXTPATH+"/couponRule/toShowRuleList?couponId="+couponId;
	                });
		        }
		    }
			);
			
		});
	}
}
//跳转赠送页面
function showtable(couponId){
	var couponid = ""+couponId;
	window.location.href="../couponRule/toShowSendPage?couponId="+couponId;
}

function findUserByName(){
	var userId = $("#userId").val();
	if(userId.trim() == null||userId.trim() == ""){
		$("#findUsers").next().text("请输入查询内容").show();
		$(".gift_oneb").html("");
	}else{
		$.ajax({
	        type : 'post',
	        url : '../couponRule/findUserByUserId',
	        data : "userId="+userId,
	        dataType : "json",
	        success : function(data) {
	        	$(".gift_oneb").html("");
	        	if(data == "" || null == data ){
	        		
	        	}else{
	        		$li=$("<span><input type='radio' name='userid' class='gi' value='"+data[0].userId+"'>"+data[0].userName+"</span>");
		            $(".gift_oneb").append($li);
	        	}
	        }
	    });
		$("#findUsers").next().hide();
	}
}



//表单提交验证
function checkSubmit(){
	var flag = true;
	var desc = $("#desc").val();
	//校验赠送理由
	if(desc.trim() == ""||desc.trim() == null){
		flag = false;
		$("#des").text("描述必填").show();
	}else{
		$("#des").hide();
	}
	var userId = $('input:radio[name="userid"]:checked').val();
	//校验已选用户
	if(userId == ""||userId == null||userId == undefined){
		flag =false;
		$("#findUsers").next().text("用户必选").show();
	}else{
		$("#findUsers").next().hide();
	}
	//校验选择的优惠券
	var couponRuleId = $('input:radio[name="coupons"]:checked').val();
	//校验已选用户
	if(couponRuleId == ""||couponRuleId == null||couponRuleId == undefined){
		flag =false;
		$("#type").next().text("赠品必选").show();
	}else{
		$("#type").next().hide();
	}
	return flag;
}
