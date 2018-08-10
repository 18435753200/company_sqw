var validateRegExp = {
    levelName: "^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$"
};
//验证规则
var validateRules = {
   betweenLength: function (str, _min, _max) {
        return (str.length >= _min && str.length <= _max);
    },
    isNull: function (str) {
        return (str == "" || typeof str != "string");
    },
    islevelName:function(str){
        return new RegExp(validateRegExp.levelName).test(str);
    }
};
//执行函数
var validateFunction = {
		levelName: function (option,type) {
			var flag = true;
			var format = validateRules.islevelName(option.val());
			var isNull = validateRules.isNull(option.val());
			var length = validateRules.betweenLength(option.val(), 3, 20);
			if(isNull){
				if(type==1){
					alert("请填写库房级别名称!");
				}else if(type==2){
					alert("请填写库房类型名称!");
				}else if(type==3){
					alert("请填写发货渠道名称!");
				}
				flag = false;
				return flag;
			}else if(!format){
				if(type==1){
					alert("库房级别名称只能由中文、英文、数字及“_”、“-”、()、（）组成!");
					
				}else if(type==2){
					alert("库房类型名称只能由中文、英文、数字及“_”、“-”、()、（）组成!");
				}else if(type==3){
					alert("发货渠道名称只能由中文、英文、数字及“_”、“-”、()、（）组成!");
				}
				option.val("");
				flag = false;
				return flag;
			}else if(!length){
				if(type==1){
					alert("库房级别名称只能为3-20个字符");
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
					alert("请填写库房级别备注说明!");
				}else if(type==2){
					alert("请填写库房类型备注说明!");
				}else if(type==3){
					alert("请填写发货渠道备注说明!");
				}
				flag = false;
				return flag;
			}else if(!length){
				if(type==1){
					alert("库房级别备注说明只能为3-20个字符!");
				}else if(type==2){
					alert("库房类型备注说明只能为3-20个字符!");
				}else if(type==3){
					alert("发货渠道备注说明只能为3-20个字符!");
				}
				flag = false;
				return flag;
			}else{
				return flag;
			}
		},
}

    


//库房级别添加保存
function gosumitWareHouseLevel(){
	var path=$("#conPath").val();
	var data = $('#wareHouseLevelForm').serialize();
	var flag = true;
	var flag = validateFunction.levelName($("#levelName"),1) && validateFunction.remark($("#remark"),1) ;
	if(flag){
		$.ajax({
			type : "post", 
			url  : path+"/warehouseInfras/addWareHouseLevel", 
			data :data+"&random="+Math.random(),
			success : function(msg) {
				 tipMessage(msg,function(){
					 location.href="../warehouseInfras/getAllWarehousesStorelevel";
			   	 });
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
	}
}



//修改保存库房级别
function savesumitWareHouseLevel(){
	var path=$("#conPath").val();
	var flag = true;
	var flag = validateFunction.levelName($("#levelName"),1) && validateFunction.remark($("#remark"),1) ;
	var data = $('#savewareHouseLevelForm').serialize();	
	if(flag){
		$.ajax({
			type : "post", 
			url  : path+"/warehouseInfras/saveWareHouseLevel", 
			data :data+"&random="+Math.random(),
			success : function(msg) {
				 tipMessage(msg,function(){
					 location.href="../warehouseInfras/getAllWarehousesStorelevel";
			   	 });
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
	}
}



//添加保存库房类型	
function gosumitWarehouseStoretype(){
	var path=$("#conPath").val();
	var flag = true;
	var flag = validateFunction.levelName($("#typeName"),2) && validateFunction.remark($("#remark"),2);
	var data = $('#warehouseStoretypeForm').serialize();
	if(flag){
		$.ajax({
			type : "post", 
			url  : path+"/warehouseInfras/addWarehouseStoretype", 
			data :data+"&random="+Math.random(),
			success : function(msg) {
				tipMessage(msg,function(){
					location.href="../warehouseInfras/getAllWarehousesStoretype";
			   	 });
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
	}
}

//修改保存库房类型	
function savesumitWarehouseStoretype(){
	var path=$("#conPath").val();
	var flag = true;
	var flag = validateFunction.levelName($("#typeName"),2) && validateFunction.remark($("#remark"),2) ;
	var data = $('#savewarehouseStoretypeForm').serialize();	
	if(flag){
		$.ajax({
			type : "post", 
			url  : path+"/warehouseInfras/saveWarehouseStoretype", 
			data :data+"&random="+Math.random(),
			success : function(msg) {
				tipMessage(msg,function(){
					location.href="../warehouseInfras/getAllWarehousesStoretype";
			   	 });
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
	}
}	


//添加保存发货渠道
function gosumitWarehouseChannel(){
	var path=$("#conPath").val();
	var flag = true;
	var flag = validateFunction.levelName($("#channelName"),3) && validateFunction.remark($("#remark"),3) ;
	var data = $('#warehouseChannelForm').serialize();	
	if(flag){
		$.ajax({
			type : "post", 
			url  : path+"/warehouseInfras/addWarehouseChannel", 
			data :data+"&random="+Math.random(),
			success : function(msg) {
				tipMessage(msg,function(){
					location.href="../warehouseInfras/getAllChannels";
			   	 });
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
	}
}

//修改保存发货渠道
function savesumitWarehouseChannel(){
	var path=$("#conPath").val();
	var flag = true;
	var flag = validateFunction.levelName($("#channelName"),3) && validateFunction.remark($("#remark"),3) ;
	var data = $('#savewarehouseChannelForm').serialize();	
	if(flag){
		$.ajax({
			type : "post", 
			url  : path+"/warehouseInfras/saveWarehouseChannel", 
			data :data+"&random="+Math.random(),
			success : function(msg) {
				tipMessage(msg,function(){
					location.href="../warehouseInfras/getAllChannels";
			   	 });
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
	}
}	