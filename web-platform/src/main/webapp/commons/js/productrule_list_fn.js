var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {
	clickSubmit(1);
	
	/* 弹出框信息*/
	$("#sort").live('click',closeboxsort);
	$("#brand").live('click',closeboxbrand);
	$("#goods").live('click',closeboxgoods);
	$("#skus").live('click',closeboxskus);
	$(".close-box").live('click',closeboxcategory);
	$(".close-box").live('click',closeboxrulecondition);
	$(".close-box").live('click',closeboxrulecontent);
	

});

/* 查询条件 */
// page 页数
function clickSubmit(page) {
	var pro_array = new Array();
	var activeId = $("#activeId").val();

	if (activeId != "" && activeId != undefined) {
		pro_array.push("activeId=" + activeId);
	}
	if (page != "" && page != undefined) {
		pro_array.push("page=" + page);
	}
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/productrule/findProductRuleByCondition",
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

function createPlatform() {
	var activeId = $("#activeId").val();
	window.location.href = "../productrule/add?activeId="+ activeId;

}

function createPricedPromotion(){
	var activeId = $("#activeId").val();
	window.location.href = "../productrule/addPricedPromotion?activeId="+ activeId;
}


/*1 啟用 0停用*/
function stopOrEnableRule(activeId,ruleId, status,pageNo) {
	var pro_array = new Array();
	if (ruleId != "") {
		pro_array.push("ruleId=" + ruleId);
	}
	if (status != "") {
		if (status == 0) {
			pro_array.push("status=1");
		}
		if (status == 1) {
			pro_array.push("status=0");
		}

	}
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/productrule/ajaxModifyRuleStatus",
		data : pro_array.join("&"),

		success : function(data) {
			if (data == 'success') {
				tipMessage("修改成功，将立即返回到规则列表！", function() {
					window.location.href = "../productrule/list?activeId="
							+ activeId;
				});
			} else if (data == 'error') {
				tipMessage("修改失败，请稍后重试！", function() {
				});
			}else if (data == 'timeout') {
				tipMessage("该规则时间已经结束！", function() {
				});
			}else {
				tipMessage("修改失败,原因：" + data, function() {
				});
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("对不起，请稍后再试!");
		}

	});
}

/*查看范围*/
function viewScop(type,ruleId){
	$("#categoryconent").empty();
	$("#sortconent").empty();
	$("#goodsconent").empty();
	$("#skusconent").empty();
	var ruleid_array = new Array();
	ruleid_array.push("ruleId="+ruleId);
	if('1' == type){//品类
		
		$.ajax({//发送ajax请求数据
			type : "post", 
			url : CONTEXTPATH+"/productrule/ajaxCategory",//获取该规则范围：类目
			data:ruleid_array.join("&")+"&math="+Math.random(),
			success : function(msg) { 
				var content = "<table class=\"act-table t-brands\" data-spm=\"9\">";
				$.each(eval(msg),function(i,n){
					content+="<tr><th>"+n+"</th></tr>";
				});
				content+="</table>";
				
				$("#sortconent").append(content);
				
				$("#goout-box-sort").css("display","block");
				
			}
		});
		
		
		
		
		
	}else if("2" == type){//品牌
		
		$.ajax({//发送ajax请求数据
			type : "post", 
			url : CONTEXTPATH+"/productrule/ajaxBrand",//获取该规则范围：品类
			data:ruleid_array.join("&")+"&math="+Math.random(),
			success : function(msg) { 
				var content = "<table class=\"act-table t-brands\" data-spm=\"9\">";
				$.each(eval(msg),function(i,n){
					content+="<tr><th>"+n.nameCn+"</th></tr>";
				});
				content+="</table>";
				
				$("#categoryconent").append(content);
				
				$("#goout-box-brand").css("display","block");
				
			}
		});
		
	}else if("3" == type){//商品
		
		$.ajax({//发送ajax请求数据
			type : "post", 
			url : CONTEXTPATH+"/productrule/ajaxProduct",//获取该规则范围：商品
			data:ruleid_array.join("&")+"&math="+Math.random(),
			success : function(msg) { 
				var content = "<table class=\"act-table t-brands\" data-spm=\"9\">";
				$.each(eval(msg),function(i,n){
					console.log(n);
					console.log(n.productName);
					content+="<tr><th>"+n.productName+"</th></tr>";
				});
				content+="</table>";
				
				$("#goodsconent").append(content);
				
				$("#goout-box-goods").css("display","block");
				
			}
		});
		
	}else if("4" == type){//单品
		$.ajax({//发送ajax请求数据
			type : "post", 
			url : CONTEXTPATH+"/productrule/ajaxSku",//获取该规则范围：单品
			data:ruleid_array.join("&")+"&math="+Math.random(),
			success : function(msg) { 
				var content = "<table class=\"act-table t-brands\" data-spm=\"9\"><tr class=\"col-name\"><th scope=\"row\">商品名称:</th><th>商品单价:</th></tr>";
				$.each(eval(msg),function(i,n){
					console.log(n);
					console.log(n.skuName);
					content+="<tr><td>"+n.skuName+"</td><td>"+n.price+"元</td></tr>";
				});
				content+="</table>";
				
				$("#skusconent").append(content);
				$("#goout-box-skus").css("display","block");
				
			}
		});
		
	}
	
	
	
}

/*点击查看规则内容*/
function viewRuleCondition(ruleId){
	$("#ruleCondition").empty();
	var ruleid_array = new Array();
	ruleid_array.push("ruleId="+ruleId);
	
	
	$.ajax({//发送ajax请求数据
		type : "post", 
		url : CONTEXTPATH+"/productrule/ajaxRuleCondition",
		data:ruleid_array.join("&")+"&math="+Math.random(),
		success : function(msg) { 
			var content = "<table class=\"act-table t-brands\" data-spm=\"9\">";
			$.each(eval(msg),function(i,n){
				content+="<tr><th>"+n+"</th></tr>";
			});
			content+="</table>";
			
			$("#ruleCondition").append(content);
			
			$("#goout-box-condition").css("display","block");
			
		}
	});
	
}

/*查看规则文本 */
function viewRuleContent(ruleId){
	$("#ruleContent").empty();
	var ruleid_array = new Array();
	ruleid_array.push("ruleId="+ruleId);
	
	
	$.ajax({//发送ajax请求数据
		type : "post", 
		url : CONTEXTPATH+"/productrule/ajaxRuleContent",
		data:ruleid_array.join("&")+"&math="+Math.random(),
		success : function(msg) { 
			var content = "<table class=\"act-table t-brands\" data-spm=\"9\">";
			$.each(eval(msg),function(i,n){
				content+="<tr><th>"+n.ruleContent+"</th></tr>";
			});
			content+="</table>";
			
			$("#ruleContent").append(content);
			
			$("#goout-box-content").css("display","block");
			
		}
	});
	
}


var closeboxbrand = function(){
	$("#goout-box-brand").fadeOut();
};
var closeboxsort = function(){
	$("#goout-box-sort").fadeOut();
};
var closeboxgoods = function(){
	$("#goout-box-goods").fadeOut();
};
var closeboxskus = function(){
	$("#goout-box-skus").fadeOut();
};
var closeboxcategory = function(){
	$("#goout-box-category").fadeOut();
};

var closeboxrulecondition = function(){
	$("#goout-box-condition").fadeOut();
};

var closeboxrulecontent = function(){
	$("#goout-box-content").fadeOut();
};

