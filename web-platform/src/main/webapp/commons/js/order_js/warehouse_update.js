$(document).ready(function(){
	editShow();
	$(".deleteSelect").live('click',deleteSelect);
	$(".addSelect").live('click',addSelect);
	$('select[name="provinceId1"]').live('change',getCityChange);
	$('select[name="cityId1"]').live('change',getCountyChange);
	$("#provinceId").bind("change",function(){
		getCity($("#provinceId"),$("#cityId"));
    });

	$("#cityId").bind("change",function(){
		getCounty($("#cityId"),$("#arealId"));
    });
	
	$("#provinceId1").bind("blur",function(){
		getProvince($("#provinceId1"));
    });
	$("select[name='warehouseLevelCode']").live('change',getWarehouseByLevel);
	
	$("select[name='warehouseType']").live('change',changeWarehouseType);
	
	$("input[name='telephone']").bind("blur", function () {
		validateFunction.servicePhone($("#telephone"))
    });
	$("input[name='contact']").bind("blur", function () {
		validateFunction.contact($("#contact"))
    });
	$("input[name='address']").bind("blur", function () {
		validateFunction.address($("#address"))
    });
	$("select[name='warehouseType']").bind("blur", function () {
		validateFunction.warehouseType($("select[name='warehouseType'] option:selected"))
    });
	$("select[name='warehouseLevelCode']").bind("blur", function () {
		validateFunction.warehouseLevelCode($("select[name='warehouseLevelCode'] option:selected"))
    });
	$("select[name='channelCode']").bind("blur", function () {
		validateFunction.channelCode($("select[name='channelCode'] option:selected"))
    });
	$("select[name='warehouseLevelCode']").bind("blur", function () {
		validateFunction.warehouseLevelCode($("select[name='warehouseLevelCode'] option:selected"))
    });
	$("select[name='parentCode']").bind("blur", function () {
		validateFunction.parentCode($("select[name='parentCode'] option:selected"))
    });
	$("input[name='warehouseName']").bind("blur", function () {
		validateFunction.warehouseName($("input[name='warehouseName']"))
    });
	$("select[name='provinceId']").bind("blur", function () {
		validateFunction.provinceId($("select[name='provinceId'] option:selected"))
    });

});

//公用正则表达
var validateRegExp = {
		tax:"^[0-9]+(.[0-9]{0,2})+$",
		contact: "^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$",
		tel: "^[0-9\-()（）]{7,11}$", //电话号码的函数(包括验证国内区号,国际区号,分机号)
		mobile: "^0?(13|15|17|18|14)[0-9]{9}$" //手机
};

//验证规则
var validateRules = {
    betweenLength: function (str, _min, _max) {
        return (str.length >= _min && str.length <= _max);
    },
    isNull: function (str) {
        return (str == "" || typeof str != "string");
    },
    isNullo: function (str) {
        return (str == 0);
    },
    isContact:function(str){
        return new RegExp(validateRegExp.contact).test(str);
    },
    isPhone:function(str){
        return new RegExp(validateRegExp.tel).test(str) || new RegExp(validateRegExp.mobile).test(str);
    }
};

//执行函数
var validateFunction = {
	servicePhone: function (option) {
		var flag = true;
		var format = validateRules.isPhone(option.val());
		var isNull = validateRules.isNull(option.val());
		if(isNull){
			$("input[name='telephone']").addClass("rm");
			$("input[name='telephone']").next().text("请填写的电话");
			flag = false;
			return flag;
		}else if(!format){
			$("input[name='telephone']").addClass("rm");
			$("input[name='telephone']").next().text("请填写正确的电话格式");
			option.val("");
			flag = false;
			return flag;
		}else{
			$("input[name='telephone']").removeClass("rm");
			$("input[name='telephone']").next().text("");
			return flag;
		}
	},
	contact: function (option) {
		var flag = true;
		var format = validateRules.isContact(option.val());
		var isNull = validateRules.isNull(option.val());
		var length = validateRules.betweenLength(option.val(),1,5);
		if(isNull){
			$("input[name='contact']").addClass("rm");
			$("input[name='contact']").next().text("请填写的联系人!");
			flag = false;
			return flag;
		}else if(!format){
			$("input[name='contact']").addClass("rm");
			$("input[name='contact']").next().text("联系人只能由中文、英文、数字及“_”、“-”、()、（）组成!");
			option.val("");
			flag = false;
			return flag;
		}else if(!length){
			$("input[name='contact']").addClass("rm");
			$("input[name='contact']").next().text("联系人只能为1-5个字符");
			flag = false;
			return flag;
		}else{
			$("input[name='contact']").removeClass("rm");
			$("input[name='contact']").next().text("");
			return flag;
		}
	},
	warehouseName: function (option) {
		var flag = true;
		var format = validateRules.isContact(option.val());
		var isNull = validateRules.isNull(option.val());
		var length = validateRules.betweenLength(option.val(),3,25);
		if(isNull){
			$("input[name='warehouseName']").addClass("rm");
			$("input[name='warehouseName']").next().text("请填写仓库名称!");
			flag = false;
			return flag;
		}else if(!format){
			$("input[name='warehouseName']").addClass("rm");
			$("input[name='warehouseName']").next().text("仓库名称只能由中文、英文、数字及“_”、“-”、()、（）组成!");
			option.val("");
			flag = false;
			return flag;
		}else if(!length){
			$("input[name='warehouseName']").addClass("rm");
			$("input[name='warehouseName']").next().text("仓库名称只能为3-25个字符");
			flag = false;
			return flag;
		}else{
			$("input[name='warehouseName']").removeClass("rm");
			$("input[name='warehouseName']").next().text("");
			return flag;
		}
	},
	address: function (option) {
		var flag = true;
		var isNull = validateRules.isNull(option.val());
		var length = validateRules.betweenLength(option.val(),5,50);
		if(isNull){
			$("input[name='address']").addClass("rm");
			$("input[name='address']").next().text("请填写详细地址!");
			flag = false;
			return flag;
		}else if(!length){
			$("input[name='address']").addClass("rm");
			$("input[name='address']").next().text("详细地址只能为5-50个字符");
			flag = false;
			return flag;
		}else{
			$("input[name='address']").removeClass("rm");
			$("input[name='address']").next().text("");
			return flag;
		}
	},
	warehouseType: function (option) {
		var flag = true;
		var isNull = validateRules.isNullo(option.val());
		if(isNull){
			$("select[name='warehouseType']").addClass("rm");
			$("select[name='warehouseType']").next().text("请选择仓库类型");
			flag = false;
			return flag;
		}else{
			$("select[name='warehouseType']").removeClass("rm");
			$("select[name='warehouseType']").next().text("");
			return flag;
		}
	},
	warehouseLevelCode: function (option) {
		var flag = true;
		var isNull = validateRules.isNullo(option.val());
		if(isNull){
			$("select[name='warehouseLevelCode']").addClass("rm");
			$("select[name='warehouseLevelCode']").next().text("请选择仓库级别");
			flag = false;
			return flag;
		}else{
			$("select[name='warehouseLevelCode']").removeClass("rm");
			$("select[name='warehouseLevelCode']").next().text("");
			return flag;
		}
	},
	channelCode: function (option) {
		var flag = true;
		var isNull = validateRules.isNullo(option.val());
		if(isNull){
			$("select[name='channelCode']").addClass("rm");
			$("select[name='channelCode']").next().text("请选择发货渠道");
			flag = false;
			return flag;
		}else{
			$("select[name='channelCode']").removeClass("rm");
			$("select[name='channelCode']").next().text("");
			return flag;
		}
	},
	parentCode: function (option) {
		var flag = true;
		var value = $("select[name='warehouseLevelCode'] option:selected").val();
		if(value != 1){
			var isNull = validateRules.isNullo(option.val());
			if(isNull){
				$("select[name='parentCode']").addClass("rm");
				$("select[name='parentCode']").next().text("请选择上级仓库名称");
				flag = false;
				return flag;
			}else{
				$("select[name='parentCode']").removeClass("rm");
				$("select[name='parentCode']").next().text("");
				return flag;
			}
		}
	},
	provinceId: function (option) {
		var flag = true;
		var isNull = validateRules.isNullo(option.val());
		if(isNull){
			$("select[name='provinceId']").addClass("rm");
			$("select[name='provinceId']").nextAll(".error").text("请选择仓库地址的省份!");
			flag = false;
			return flag;
		}else{
			$("select[name='provinceId']").removeClass("rm");
			$("select[name='provinceId']").nextAll(".error").text("");
			return flag;
		}
	},
	provinceId1: function (option) {
		var flag = true;
		var isNull = validateRules.isNull(option.val());
		if(isNull){
			$("select[name='provinceId1']").addClass("rm");
			$("select[name='provinceId1']").nextAll(".error").text("请选择负责区域的省份!");
			flag = false;
			return flag;
		}else{
			$("select[name='provinceId1']").removeClass("rm");
			$("select[name='provinceId1']").nextAll(".error").text("");
			return flag;
		}
	},
	dealerName: function (option) {
		var flag = true;
		var isNull = validateRules.isNullo(option.val());
		if(isNull){
			$("select[name='dealerId']").addClass("rm");
			$("select[name='dealerId']").nextAll(".error").text("请选择仓库的经销商!");
			flag = false;
			return flag;
		}else{
			$("select[name='dealerId']").removeClass("rm");
			$("select[name='dealerId']").nextAll(".error").text("");
			return flag;
		}
	},
}

//根据省份ID获取市
function getCityChange(){
	var that = $(this);
	var provinceId = $(this).val();
	$(this).next().next().empty();
	$(this).next().next().append("<option value='0'>请选择</option>"); 
	$.ajax({
		 type : "post", 
     	 url :  '../baseData/findCitiesByProvinceId', 
     	 data:"provinceId="+provinceId,
     	 dataType:"json", 
         success : function(json) { 
			$.each(json,function(i,n){
				that.next().next().append("<option value='"+n.cityid+"'>"+n.cityname+"</option>"); 
			});	
	     }
    });
}

//根据省份ID获取市
function getCountyChange(){
	var that = $(this);
	var provinceId = $(this).val();
	$(this).next().next().empty();
	$(this).next().next().append("<option value='0'>请选择</option>"); 
	$.ajax({
		 type : "post", 
     	 url :  '../baseData/findCountiesByCityId', 
     	 data:"cityId="+provinceId,
     	 dataType:"json", 
         success : function(json) { 
			$.each(json,function(i,n){
				that.next().next().append("<option value='"+n.countyid+"'>"+n.countyname+"</option>"); 
			});	
	     }
    });
}

//获取所有仓库类型.
function getWarehouseStoretype(){
	$.ajax({
		type : "post", 
		url : "../warehouse/getAllWarehouseStoretype", 
		dataType:"json",
		success : function(warehouseStoretypes) { 
			$.each(warehouseStoretypes,function(i,n){
				$("select[name='warehouseType']").append("<option value='"+n.typeCode+"'>"+n.typeName+"</option>"); 
			});
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});
}


function changeWarehouseType(){
	var warehouseTypeName = $("select[name='warehouseType'] option:selected").text();
	var areaSelect = "<div class='item' id='select1'>"
			+"<label><i>*</i>负责区域：</label>"
			+"<div class='itemright'>"
			+"<a href='javascript:void(0)' class='addSelect'>添加</a>"
			+"<a href='javascript:void(0)' class='deleteSelect'>删除</a>"
			+"</div>"
			+"</div>"
			+"<div class='item' id='parent'>"
			+"<div class='item' id='select'>"
			+"<label><input type='checkbox' class='checkbox'></label>"
			+"<div class='itemright'>"
			+"<input type='hidden'  id='provinceIdInput' class='provinceIdInput' value=''>"
			+"<select class='si' id='provinceId1' name='provinceId1'><option value=''>请选择</option></select>"
			+"<input type='hidden'  id='cityIdInput' class='cityIdInput' value=''>"
			+"<select class='si' id='cityId1' name='cityId1'><option value='0'>请选择</option></select>"
			+"<input type='hidden'  id='arealIdInput' class='arealIdInput' value=''>"
			+"<select class='si' id='arealId1' name='arealId1'><option value='0'>请选择</option></select>"
			+"<span class='error'></span>"
			+"</div>"
			+"</div>"
			+"</div>";
	if(warehouseTypeName == "国内仓库"){
		$("#addAreaSelect").append(areaSelect);
	}else{
		$("#select1").remove();
		$("#parent").remove();
	}
	getProvince($("#provinceId1"));
}


//获取所有仓库级别.
function getWarehouseStorelevel(){
	$.ajax({
		type : "post", 
		url : "../warehouse/getAllWarehouseStorelevel", 
		dataType:"json",
		success : function(warehouseStorelevels) { 
			$.each(warehouseStorelevels,function(i,n){
				$("select[name='warehouseLevelCode']").append("<option value='"+n.levelCode+"'>"+n.levelName+"</option>"); 
			});
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});
}

//获取发货渠道
function getChannel(){
	$.ajax({
		type : "post", 
		url : "../warehouse/getAllChannel", 
		dataType:"json",
		success : function(channels) { 
			$.each(channels,function(i,n){
				$("select[name='channelCode']").append("<option value='"+n.channelCode+"'>"+n.channelName+"</option>"); 
			});
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});
}


//按仓库级别获取上一级仓库
function getWarehouseByLevel(){
	var warehouseStorelevelCode = $("select[name='warehouseLevelCode']  option:selected").val();
	var selectCode = "<div class='item' id='warehouseRemove'>"
			+"<label><i>*</i>上级仓库名称：</label>"
			+"<div class='itemright'>"
			+"<select class='sm' name='parentCode'>"
			+"<option value='0'>请选择</option>"
			+"</select>"
			+"</div>"
			+"</div>";
	if(warehouseStorelevelCode==1){
		$("#warehouseRemove").remove();
	}else{
		var select = $("select[name='parentCode']");
		if(!select.length>0){
			$("#warehouseName1").append(selectCode);
		}
		
		$.ajax({
			type : "post", 
			url : "../warehouse/getWarehouseByLevel", 
			dataType:"json",
			data:{"warehouseLevelCode":warehouseStorelevelCode},
			success : function(warehouseByLevels) { 
				$.each(warehouseByLevels,function(i,n){
					$("select[name='parentCode']").append("<option value='"+n.warehouseCode+"'>"+n.warehouseName+"</option>"); 
				});
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("对不起，数据异常请稍后再试!");
			}
		});
	}
}

//添加负责区域
function addSelect(){
	var option="";
	$.ajax({ 
	    type : "post", 
	    url : '../baseData/findAllProvince', 
	    dataType:"json",
	    success : function(json) { 
	        $.each(eval(json),function(i,v){
	        	option += "<option value='"+v.provinceid+"'>"+v.provincename+"</option>"
			});
	        var select = "<div class='item' id='select'>"
	    		+"<label><input type='checkbox' class='checkbox'></label>"
	    		+"<div class='itemright'>"
	    		+"<input type='hidden'  id='provinceIdInput' class='provinceIdInput' value=''>"
				+"<select class='si' id='provinceId1' name='provinceId1'><option value=''>请选择</option>"+option+"</select>"
				+"<input type='hidden'  id='cityIdInput' class='cityIdInput' value=''>"
				+"<select class='si' id='cityId1' name='cityId1'><option value='0'>请选择</option></select>"
				+"<input type='hidden'  id='arealIdInput' class='arealIdInput' value=''>"
				+"<select class='si' id='arealId1' name='arealId1'><option value='0'>请选择</option></select>"
				+"<span class='error'></span>"
	    		+"</div>"
	    		+"</div>";
	        $("#parent").append(select);
		}
	});
}

//删除为国内仓库时负责的区域
function deleteSelect(){
	var length = $("input[type='checkbox']:checked").length;
	var length1 = $("input[type='checkbox']").length;
	if(length==0){
		alert("请选择要删除的列!");
	}else if(length1 == 1){
		alert("国内仓库必须添加负责区域!");
	}else if(length1 > 0){
		$("input[type='checkbox']:checked").parent().parent().remove();
	}
}


function editShow(){
	var warehouseType = $("#warehouseTypeInput").val();
	var channelCode = $("#channelCode").val();
	var warehouseLevelCode = $("#warehouseLevelCode").val();
	$("select[name='warehouseType'] option[value='"+warehouseType+"']").attr("selected", "selected");
	$("select[name='channelCode'] option[value='"+channelCode+"']").attr("selected", "selected");	
	$("select[name='warehouseLevelCode'] option[value='"+warehouseLevelCode+"']").attr("selected", "selected");	
	var parentCode = $("#parentCode").val();
	$("select[name='parentCode'] option[value='"+parentCode+"']").attr("selected", "selected");
	var provinceId = $("#provinceId2").val();
	$("select[name='provinceId'] option[value='"+provinceId+"']").attr("selected", "selected");
	var cityId = $("#cityId2").val();
	$("select[name='cityId'] option[value='"+cityId+"']").attr("selected", "selected");
	var areaId = $("#areaId2").val();
	$("select[name='areaId'] option[value='"+areaId+"']").attr("selected", "selected");
	
	var dealerIdInput = $("#dealerIdInput").val();
	
	$("select[name='dealerId'] option[value='"+dealerIdInput+"']").attr("selected", "selected");
	var provinceIdInput = $(".provinceIdInput");
	provinceIdInput.each(function(i,item){	
		var value = $(item).val();
		if(value !="" && value !=0){
			var select = $(item).next();
			select.find("option[value='"+value+"']").attr("selected", "selected");
		}
	});
	
	var cityIdInput = $(".cityIdInput");
	cityIdInput.each(function(i,item){
		var provinceId = $(item).prev().prev().val();
		$.ajax({
			 type : "post", 
	     	 url :  '../baseData/findCitiesByProvinceId', 
	     	 data:"provinceId="+provinceId,
	     	 dataType:"json", 
	         success : function(json) { 
				$.each(json,function(i,n){
					$(item).next().append("<option value='"+n.cityid+"'>"+n.cityname+"</option>"); 
				});
				var value = $(item).val();
				if(value !="" && value !=0){
					var select = $(item).next();
					select.find("option[value='"+value+"']").attr("selected", "selected");
				}
		     }
	    });
		
	});

	var arealIdInput = $(".arealIdInput");
	arealIdInput.each(function(i,item){
		var provinceId = $(item).prev().prev().val();
		$.ajax({
			 type : "post", 
	    	 url :  '../baseData/findCountiesByCityId', 
	    	 data:"cityId="+provinceId,
	    	 dataType:"json", 
	    	 success : function(json) { 
				$.each(json,function(i,n){
					$(item).next().append("<option value='"+n.countyid+"'>"+n.countyname+"</option>"); 
				});	
				var value = $(item).val();
				if(value !="" && value !=0){
					var select = $(item).next();
					select.find("option[value='"+value+"']").attr("selected", "selected");
				}
		     }
		}); 
		
	});
}

function gosumitWarehouse(){
	var flag = true;
	var pagearray = new Array();
	var data = $('#warehouseForm').serialize();	
	var channelName = $("select[name='channelCode'] option:selected").text();
	var warehouseTypeName = $("select[name='warehouseType'] option:selected").text();
	var warehouseTypeCode = $("select[name='warehouseType'] option:selected").val();
	var warehouseLevelName = $("select[name='warehouseLevelCode'] option:selected").text();
	var warehouseLevelCode = $("select[name='warehouseLevelCode'] option:selected").val();
	var dealerName = $("select[name='dealerId'] option:selected").text();
	
	var result1= validateFunction.servicePhone($("#telephone"));
	var result2= validateFunction.contact($("#contact"));
	var result3= validateFunction.address($("#address"));
	var result4= validateFunction.warehouseType($("select[name='warehouseType'] option:selected"));
	var result5= validateFunction.warehouseLevelCode($("select[name='warehouseLevelCode'] option:selected"));
	var result6= validateFunction.channelCode($("select[name='channelCode'] option:selected"));
	if(warehouseLevelCode!=1){
		var result7= validateFunction.parentCode($("select[name='parentCode'] option:selected"));
	}
	
	var result8= validateFunction.warehouseName($("input[name='warehouseName']"));
	var result9= validateFunction.provinceId($("select[name='provinceId'] option:selected"));
	var result11= validateFunction.dealerName($("select[name='dealerId'] option:selected"))
	if(warehouseTypeCode=="1"){
		var result10= validateFunction.provinceId1($("select[name='provinceId1'] option:selected"));
	}
	if(warehouseLevelCode!=1 && warehouseTypeCode=="1"){
		flag = result1 && result2 && result3 && result4 && result5 && result6 && result7 && result8 && result9 && result10 && result11;
	}else if(warehouseLevelCode!=1 && warehouseTypeCode!="1"){
		flag = result1 && result2 && result3 && result4 && result5 && result6 && result8 && result9 && result11;
	}else if(warehouseLevelCode==1 && warehouseTypeCode=="1"){
		flag = result1 && result2 && result3 && result4 && result5 && result6 && result8 && result9 && result11;
	}
	pagearray.push('warehouseTypeName='+warehouseTypeName);
	pagearray.push('channelName='+channelName);
	pagearray.push('warehouseLevelName='+warehouseLevelName);
	pagearray.push('dealerName='+dealerName);
	if(flag){
		$.ajax({
			type : "post", 
			url  : "../warehouse/updateWareHouse", 
			data :data+"&"+pagearray.join('&'),
			success : function(msg) {
				 tipMessage(msg,function(){
					 window.location.href = "../warehouse/getWarehouse";
			   	 });
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
	}	
}