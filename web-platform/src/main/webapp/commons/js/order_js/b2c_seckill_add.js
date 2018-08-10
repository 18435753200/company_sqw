function sumit(){
	var result1= validateFunction.productId($("#pid"));
	var result2= validateFunction.skuId($("#skuid"));
	var result3= validateFunction.startDate($("#startDate"));
	var result4= validateFunction.endDate($("#endDate"));
	var result5= validateFunction.price($("#price"));
	var result6= validateFunction.stock($("#stock"));
	var result7=validateFunction.imageUrl($("#fTaxpayqualicerti"));
	if(result1 && result2 && result3 && result4 && result5 && result6 &&result7){
		$("#seckillProduct").submit();
	}
}

function sumit1(){
	$("#seckillProduct1").submit();
}

//根据Id删除秒杀活动商品.
function deleteSeckillById(id){
	$.dialog.confirm('确定要删除商品?',function(){
		$.ajax({ 
		     async:false,
		     type : "post", 
		     url : "../secondKill/deleteSeckillById",
		     data: "id="+id,
		     dataType:"html",
		     success : function(resultMsg) {
		    	 tipMessage(resultMsg,function(){
		    		 window.location.reload();
				 });
		     },
			  error:function(jqXHR,textStatus,errorThrown){
			  	alert("网络异常,请稍后再试。。。。");
			  }
		 }); 
	},function(){}); 
}

//根据Id删除秒杀活动商品.
function updateSeckKillById(id){
	$.ajax({
	    type : 'post',
	    url : "../secondKill/updateSeckKillById",
	    dataType:"html",
	    data :{"id":id}, 
	    success : function(msg) {
	    	alert(msg);
	    }
	});
}

function toPage(page){
	byConditionSearchCustomerOrder(page);
}

function reset(){
	$("#pid").val("");
	$("#skuid").val("");
	$("#endTime").val("");
	$("#startTime").val("")
}

function byConditionSearchCustomerOrder(page){
	var pid = $.trim($("#pid").val());
	var skuid  = $("#skuid").val();
	var endTime  = $("#endTime").val();
	var startTime  = $.trim($("#startTime").val());
	var matchnum = /^[0-9]*$/;
	var customerOrder_array = new Array();
	if(!matchnum.test(pid)){
  		alert("订单编号只能是数字！");
  		 $("#pid").val("");
  		 $("#pid").focus();
  		 return false;
  	}
	if(pid != ""){
		customerOrder_array.push("pid="+pid);
	}
	if(skuid != ""){
		customerOrder_array.push("skuId="+skuid);
	}
	if(endTime != ""){
		customerOrder_array.push("endTime="+endTime);
	}
	if(startTime != ""){
		customerOrder_array.push("startTime="+startTime);
	}
	if(page!=undefined&&page!=null&&page!=""){
		customerOrder_array.push("page="+page);
	}
	$.ajax({
		 type : "post", 
    	 url : "../secondKill/getSecondKillListByContion", 
    	 data:customerOrder_array.join('&')+"&random="+Math.random(),
    	 dataType:"html",
    	 success : function(secondKill) {
			$(".promotion-bd").html(secondKill);
    	 },
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("查询失败 ，请稍后再试。。");
		 }
	}); 
}

//公用正则表达
var validateRegExp = {
		tax:"^[0-9]+(.[0-9]{0,2})+$",
		price:"^([1-9]+)|([1-9]+(.[1-9]{0,4}))$",
		qty:"^([1-9]+)|[1-9][0-9]{1,9}$",
};

//验证规则
var validateRules = {
    betweenLength: function (str, _min, _max) {
        return (str.length >= _min && str.length <= _max);
    },
    isNull: function (str) {
        return (str == "" || typeof str != "string");
    },
    isQty:function(str){
        return new RegExp(validateRegExp.qty).test(str);
    },
    isPrice:function(str){
        return new RegExp(validateRegExp.price).test(str);
    },
    betweenLength: function (str, _min, _max) {
        return (str.length >= _min && str.length <= _max);
    },
};


//执行函数
var validateFunction = {
	productId: function (option) {
		var flag = true;
		var isNull = validateRules.isNull(option.val());
		if(isNull){
			$("input[name='pid']").addClass("rm");
			$("input[name='pid']").next().text("请填写商品编号");
			flag = false;
			return flag;
		}else{
			$("input[name='telephone']").removeClass("rm");
			$("input[name='telephone']").next().text("");
			return flag;
		}
	},
	skuId: function (option) {
		var flag = true;
		var isNull = validateRules.isNull(option.val());
		if(isNull){
			$("input[name='skuid']").addClass("rm");
			$("input[name='skuid']").next().text("请填写规格编号");
			flag = false;
			return flag;
		}else{
			$("input[name='telephone']").removeClass("rm");
			$("input[name='telephone']").next().text("");
			return flag;
		}
	},
	startDate: function (option) {
		var flag = true;
		var isNull = validateRules.isNull(option.val());
		if(isNull){
			$("input[name='startDate']").addClass("rm");
			$("input[name='startDate']").next().text("请选择开始时间");
			flag = false;
			return flag;
		}else{
			$("input[name='telephone']").removeClass("rm");
			$("input[name='telephone']").next().text("");
			return flag;
		}
	},
	endDate: function (option) {
		var flag = true;
		var isNull = validateRules.isNull(option.val());
		if(isNull){
			$("input[name='endDate']").addClass("rm");
			$("input[name='endDate']").next().text("请选择结束时间");
			flag = false;
			return flag;
		}else{
			$("input[name='telephone']").removeClass("rm");
			$("input[name='telephone']").next().text("");
			return flag;
		}
	},
	
	price: function (option) {
		var flag = true;
		var isNull = validateRules.isNull(option.val());
		var format = validateRules.isPrice(option.val())
		if(isNull){
			$("input[name='price']").addClass("rm");
			$("input[name='price']").next().text("请填写抢购价格!");
			flag = false;
			return flag;
		}else if(!format){
			$("input[name='price']").addClass("rm");
			$("input[name='price']").next().text("请填写正确的抢购价格!");
			option.val("");
			flag = false;
			return flag;
		}else{
			$("input[name='price']").removeClass("rm");
			$("input[name='price']").next().text("");
			return flag;
		}
	},
	stock: function (option) {
		var flag = true;
		var isNull = validateRules.isNull(option.val());
		var isQty = validateRules.isQty(option.val());
		if(isNull){
			$("input[name='stock']").addClass("rm");
			$("input[name='stock']").next().text("请填写商品库存!");
			flag = false;
			return flag;
		}else if(!isQty){
			$("input[name='stock']").addClass("rm");
			$("input[name='stock']").next().text("请填写正确商品库存");
			option.val("");
			flag = false;
			return flag;
		}else{
			$("input[name='stock']").removeClass("rm");
			$("input[name='stock']").next().text("");
			return flag;
		}
	},
	imageUrl: function (option) {
		var flag = true;
		var isNull = validateRules.isNull(option.val());
		if(isNull){
			$("#fTaxpayqualicerti").addClass("rm");
			$("#imageUrlNull").text("请选择商品图片!");
			flag = false;
			return flag;
		}else{
			$("#fTaxpayqualicerti").removeClass("rm");
			$("#imageUrlNull").text("");
			return flag;
		}
	},
	
	
}

//验证添加秒杀活动页面
function checkAdd(){
	var pid = $("[name='pid']").val();
	var skuid = $("[name='skuid']").val();
	if($.trim(pid) == ""){
		return false;
	}
	alert(pid);
}





