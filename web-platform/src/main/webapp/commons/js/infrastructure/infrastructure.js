$(document).ready(function(){
	$("#tax").live('blur',onblurTax);
	$("select[name='loaclMoney']").bind("change",function(){
		updateStatus(2);
    });
	$(".lable").bind("click",function(){
		updateStatus(1);
    });
	$("input[name='exchangeRate']").live('blur',onBlur);
	$("input[name='code']").live('blur',goValidationCode);
}); 

var validateRegExp = {
	tax:"^[0-9]+(.[0-9]{0,2})+$",
	currency:"^([0-9]{1,5})+(.[0-9]{0,2})+$",
	levelName: "^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$",
	tel: "^[0-9\-()（）]{7,18}$", //电话号码的函数(包括验证国内区号,国际区号,分机号)
	mobile: "^0?(13|15|17|18|14)[0-9]{9}$" //手机
};

//验证规则
var validateRules = {
   betweenCount: function (str, _min, _max) {
        return (Number(str) >= _min && Number(str) <= _max);
    },
    betweenLength: function (str, _min, _max) {
        return (str.length >= _min && str.length <= _max);
    },
    isNull: function (str) {
        return (str == "" || typeof str != "string");
    },
    istax:function(str){
        return new RegExp(validateRegExp.tax).test(str);
    },
    islevelName:function(str){
        return new RegExp(validateRegExp.levelName).test(str);
    },
    
    isServicePhone:function(str){
        return new RegExp(validateRegExp.tel).test(str) || new RegExp(validateRegExp.mobile).test(str);
    }
};
//执行函数
var validateFunction = {
		tax: function (option,type) {
			var flag = true;
			var format = validateRules.istax(option.val());
			var isNull = validateRules.isNull(option.val());
			var length = validateRules.betweenCount(option.val(), 0, 100);
			if(isNull){
				if(type==1){
					alert("请填写汇率!");
				}else if(type==2){
					alert("请填写库房类型名称!");
				}else if(type==3){
					alert("请填写发货渠道名称!");
				}
				flag = false;
				return flag;
			}else if(!format){
				if(type==1){
					alert("税率请填写0-100的数字!");
				}else if(type==2){
					alert("税率请填写0-100的数字!");
				}else if(type==3){
					alert("税率请填写0-100的数字!");
				}
				option.val("");
				flag = false;
				return flag;
			}else if(!length){
				if(type==1){
					alert("税率请填写0-100的数字!");
					option.val("");
				}else if(type==2){
					alert("库房类型名称只能为3-20个字符!");
				}else if(type==3){
					alert("发货渠道名称只能为3-20个字符!");
				}
				flag = false;
				return flag;
			}else{
				return flag;
			}
		},
		remark: function (option,type) {
			var flag = true;
			var isNull = validateRules.isNull(option.val());
			var length = validateRules.betweenLength(option.val(), 3, 20);
			if(isNull){
				if(type==1){
					alert("请填写税率备注说明!");
				}else if(type==2){
					alert("请填写货物费用名称备注说明!");
				}else if(type==3){
					alert("请填写服务商名称说明!");
				}
				flag = false;
				return flag;
			}else if(!length){
				if(type==1){
					alert("税率备注说明只能为3-20个字符!");
				}else if(type==2){
					alert("货物费用名称备注说明只能为3-20个字符!");
				}else if(type==3){
					alert("服务商备注说明只能为3-20个字符!");
				}
				flag = false;
				return flag;
			}else{
				return flag;
			}
		},
		levelName: function (option,type) {
			var flag = true;
			var format = validateRules.islevelName(option.val());
			var isNull = validateRules.isNull(option.val());
			var length = validateRules.betweenLength(option.val(), 3, 20);
			if(isNull){
				if(type==1){
					alert("请填写货物费用名称!");
				}else if(type==2){
					alert("请填写服务商名称!");
				}else if(type==3){
					alert("请填写服务商联系人!");
				}
				flag = false;
				return flag;
			}else if(!format){
				if(type==1){
					alert("货物费用名称只能由中文、英文、数字及“_”、“-”、()、（）组成!");
					
				}else if(type==2){
					alert("服务商名称只能由中文、英文、数字及“_”、“-”、()、（）组成!");
				}else if(type==3){
					alert("服务商联系人只能由中文、英文、数字及“_”、“-”、()、（）组成!");
				}
				option.val("");
				flag = false;
				return flag;
			}else if(!length){
				if(type==1){
					alert("货物费用名称只能为3-20个字符");
				}else if(type==2){
					alert("服务商名称只能为3-20个字符!");
				}else if(type==3){
					alert("服务商联系人只能为3-20个字符!");
				}
				flag = false;
				return flag;
			}else{
				return flag;
			}
		},
		servicePhone: function (option) {
			var flag = true;
			var format = validateRules.isServicePhone(option.val());
			var isNull = validateRules.isNull(option.val());
			if(isNull){
					alert("请填写联系电话!");
				flag = false;
				return flag;
			}else if(!format){
					alert("请填写正确的联系电话!");
				option.val("");
				flag = false;
				return flag;
			}else{
				return flag;
			}
		},
		currency: function (option) {
			var flag = true;
			var format = validateRules.isCurrency(option.val());
			var isNull = validateRules.isNull(option.val());
			if(isNull){
					alert("请填写汇率!");
				flag = false;
				return flag;
			}else if(!format){
					alert("请填写正确的汇率!");
				option.val("");
				flag = false;
				return flag;
			}else{
				return flag;
			}
		},
}

function onblurTax(){
	var flag = true;
	var tax = $("#tax").val();
	if(tax != ""){
		flag = validateFunction.tax($("#tax"),1)
	}
	if(flag){
		$("#tax").val(Number(tax).toFixed(2));
	}
}


function updateStatus(type){
	var value = $("#loaclMoney").val();
	if(value == 1){
		if(type == 1){
			alert("本位币标识不能禁用!");
			return;
		}else{
			
			$("select[name='loaclMoney'] option[value='1']").attr("selected", "selected"); 
			alert("本位币标识不能修改为否!");
		}
	}
}

function test(){
	var smvl = $(".sm").val();
	var oRa =$(".order-bd").find("input.noit");
	var oYa =$(".order-bd").find("input.yesit");
	if(smvl==1){
		oRa.attr("disabled",true);
		oYa.attr("checked",true);
	}else{
		oYa.attr("checked",true);
		oRa.attr("disabled",false);
	}
}
//添加保存税率
function gosumitTaxrate(){
	var path=$("#conPath").val();
	var data = $('#taxrateForm').serialize();
	var flag = validateFunction.tax($("#tax"),1) && validateFunction.remark($("#remark"),1);
	if(flag){
		$.ajax({
			type : "post", 
			url  : path+"/infrastructure/addTaxrate", 
			data :data+"&random="+Math.random(),
			success : function(msg) {
				tipMessage(msg,function(){
					location.href="../infrastructure/getTaxrate";
			   	});
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
	}	
}

//编辑数据获取税率相应的值
function editTax(code){
	var path=$("#conPath").val();
	$.ajax({
		type : "post", 
		url : path+"/infrastructure/editTaxrate?t="+new Date().getTime(),
		data:{code:code},
		dataType:"json",
		success : function(msg) { 
			alert(msg.tax+ "#####" +msg.remark + "####" +msg.status);
			location.href="../infrastructure/getTaxrate";
			$("#tax").val(msg.tax);
			$("#remark").val(msg.remark);
			$("#status").val(msg.status);
		},
		error:function(){
			alert("服务异常!");
		}
	});
}


//修改保存税率
function saveTaxrate(){
	var path=$("#conPath").val();
	var flag = validateFunction.tax($("#tax"),1) && validateFunction.remark($("#remark"),1);
	var data = $('#savetaxrateForm').serialize();	
	if(flag){
		$.ajax({
			type : "post", 
			url  : path+"/infrastructure/saveTaxrate", 
			data :data+"&random="+Math.random(),
			success : function(msg) {
				 tipMessage(msg,function(){
					 location.href="../infrastructure/getTaxrate";
			   	 });
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
	}
}

function goValidationCode(){
	var code = $(this).val();
	var currentDate = new Date();
	var year = currentDate. getFullYear()+"";
    var month  = currentDate.getMonth ()+ 1+"" ;
    var accountingTime="";
    if ( month >= 10 )
    {
    	accountingTime = year+"-"+month;
    }
    else
    {
    	accountingTime = year+"-"+"0" + month;
    }
	$.ajax({
		type :"post", 
		url  :"../infrastructure/goValidationCode", 
		data:{code:code,accountingTime:accountingTime},
		success : function(msg) {
			if(msg==1){
				alert("此简码已经填写过,不能重复填写!");
				return;
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("保存失败 ，请稍后再试。。");
		}
	});
}

//添加保存币种
function gosumitCurrency(){
	var flag = true;
	var path=$("#conPath").val();
	var code = $("input[name='code']").val();
	var currencyType = $("input[name='currencyType']").val();
	var exchangeRate = $("input[name='exchangeRate']").val();
	var regex = /^[A-Z]{3}$/;
	var regex1 = /^([0-9]{1,6})(.[0-9]{1,4})$/;
	if(code==""){
		alert("简码为必填项!");
		flag = false;
		return flag;
	}else if(!regex.test(code)){
		alert('简码请填写三个大写字母!');
	    flag = false;
	    return flag;
	}else if(currencyType == ""){
		alert("币种为必填项!");
		flag = false;
	    return flag;
	}else if(currencyType.length>10 || currencyType.length<1){
		alert("币种为1-10字符!");
		flag = false;
	    return flag;
	}else if(exchangeRate==""){
		alert("汇率为必填项!");
		flag = false;
	    return flag;
	}else if(!regex1.test(exchangeRate)){
		alert("汇率由1-5位整数和1-4位小数组成!");
		flag = false;
	    return flag;
	}
	var data = $('#currencyForm').serialize();
	if(flag){
		$.ajax({
			type : "post", 
			url  : path+"/infrastructure/addCurrency", 
			data :data+"&random="+Math.random(),
			success : function(msg) {
				tipMessage(msg,function(){
					location.href="../infrastructure/getCurrency";
			   	});
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
	}
}


//编辑数据获取币种相应的值
function editTax(code){
	var path=$("#conPath").val();
	$.ajax({
		type : "post", 
		url : path+"/infrastructure/editTaxrate?t="+new Date().getTime(),
		data:{code:code},
		dataType:"json",
		success : function(msg) { 
			alert(msg.tax+ "#####" +msg.remark + "####" +msg.status);
			//location.href="../infrastructure/getTaxrate";
			$("#tax").val(msg.tax);
			$("#remark").val(msg.remark);
			$("#status").val(msg.status);
		},
		error:function(){
			alert("服务异常!");
		}
	});
}

//修改保存币种
function saveCurrency(){
	var path=$("#conPath").val();
	var currencyType = $("input[name='currencyType']").val();
	var exchangeRate = $("input[name='exchangeRate']").val();
	var regex = /^[A-Z]{3}$/;
	var regex1 = /^([0-9]{1,6})(.[0-9]{1,4})$/;
	if(currencyType == ""){
		alert("币种为必填项!");
		flag = false;
	    return flag;
	}else if(currencyType.length>10 || currencyType.length<1){
		alert("币种为1-10字符!");
		flag = false;
	    return flag;
	}else if(exchangeRate==""){
		alert("汇率为必填项!");
		flag = false;
	    return flag;
	}else if(!regex1.test(exchangeRate)){
		alert("汇率由1-5位整数和1-4位小数组成!");
		flag = false;
	    return flag;
	}
	var data = $('#savecurrencyForm').serialize();	
	$.ajax({
		type : "post", 
		url  : path+"/infrastructure/saveCurrency", 
		data :data+"&random="+Math.random(),
		success : function(msg) {
			tipMessage(msg,function(){
				location.href="../infrastructure/getCurrency";
		   	});
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("保存失败 ，请稍后再试。。");
		}
	});
}

function gosumitCostg(){
	var path=$("#conPath").val();
	var flag = validateFunction.levelName($("#costName"),1) && validateFunction.remark($("#remark"),2);
	var data = $('#costgForm').serialize();	
	if(flag){
		$.ajax({
			type : "post", 
			url  : path+"/infrastructure/addCostg", 
			data :data+"&random="+Math.random(),
			success : function(msg) {
				tipMessage(msg,function(){
					location.href="../infrastructure/getCostg";
			   	});
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
	}
}

function saveCost(){
	var path=$("#conPath").val();
	var flag = validateFunction.levelName($("#costName"),1) && validateFunction.remark($("#remark"),2);
	var data = $('#savecostForm').serialize();	
	if(flag){
		$.ajax({
			type : "post", 
			url  : path+"/infrastructure/saveCost", 
			data :data+"&random="+Math.random(),
			success : function(msg) {
				tipMessage(msg,function(){
					location.href="../infrastructure/getCostg";
			   	 });
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
	}	
}

//添加保存服务商档案
function gosumitServiceArchives(){
	var path=$("#conPath").val();
	var flag = validateFunction.levelName($("#serviceName"),2) && validateFunction.levelName($("#serviceContact"),3) && validateFunction.servicePhone($("#servicePhone")) && validateFunction.remark($("#serviceComment"),3);
	var data = $('#serviceArchivesForm').serialize();	
	if(flag){
		$.ajax({
			type : "post", 
			url  : path+"/infrastructure/addServiceArchives", 
			data :data+"&random="+Math.random(),
			success : function(msg) {
				tipMessage(msg,function(){
					location.href="../infrastructure/getServiceArchives";
			   	 });
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
	}
}


//修改保存服务商档案
function saveServiceArchives(){
	var path=$("#conPath").val();
	var flag = validateFunction.levelName($("#serviceName"),2) && validateFunction.levelName($("#serviceContact"),3) && validateFunction.servicePhone($("#servicePhone")) && validateFunction.remark($("#serviceComment"),3);
	var data = $('#saveserviceArchivesForm').serialize();	
	if(flag){
		$.ajax({
			type : "post", 
			url  : path+"/infrastructure/saveServiceArchives", 
			data :data+"&random="+Math.random(),
			success : function(msg) {
				tipMessage(msg,function(){
					location.href="../infrastructure/getServiceArchives";
			   	 });
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
	}
}


function onBlur(){
	var regex1 = /^([0-9]{1,6})(.[0-9]{1,4})$/;
	var value = $("input[name='exchangeRate']").val();
	if(value != ""){
		if(!regex1.test(value)){
			$("input[name='exchangeRate']").val("");
			alert("汇率由1-5位整数和1-4位小数组成!");
		}else{
			$("input[name='exchangeRate']").val(Number(value).toFixed(4));
		}
	}
}

function backCostg(){
	location.href="../infrastructure/getCostg";
}

function backTaxrate(){
	location.href="../infrastructure/getTaxrate";
}

function backCurrency(){
	location.href="../infrastructure/getCurrency";
}

function backServiceArchives(){
	location.href="../infrastructure/getServiceArchives";
}