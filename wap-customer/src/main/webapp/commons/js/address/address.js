$(function(){
	setAddress($("#addressId").val());
});
/**
// 订单确认页 选择收货地址
 */
function selectReceiveAddress(addressId){
	if(addressId == null){
		addressId = 1;
	}
	var url = $("#path").val()+'/cusAddress/findAllAddress?referenceAddressId='+addressId+' #addressList';
	$('#addressList').load(url);
	$("#confirmOrder").hide();
	$('#addressList').show();
}

/**
// 重新设置goback
 * 
 */
function checkGoBack(selectAddressId){
	
	// 增加或者修改时，隐藏增加或修改DIV，显示地址列表
	if($('#addressAddOrUpdate').css('display') == "block"){
		$("#address").show();
		$("#addressAddOrUpdate").hide();
		$(".add-address").show();
	}else{
		// 购物车
		if($("#confirmOrder").length > 0){
			/*// 获取选中地址
			var selectAddress = $(".goods_item").find("input[type='radio']:checked");
			if(!selectAddress || selectAddress.length == 0){
				showErrorMsg("请选择收货地址！");
				return;
			}*/
			// 获取地址信息根据地址ID
			
			setCookie("ccigmall-settlement-address-choosed-id", selectAddressId);
			setAddress(selectAddressId);
		}else{
			goBack();
		}
	}
}

function setAddress(selectAddressId) {
	
	var addressIdInCookie = getCookie("ccigmall-settlement-address-choosed-id");
	if (!isEmpty(addressIdInCookie)) {
		selectAddressId = addressIdInCookie;
	}
	else {
		return;
	}
	
	var url = $("#path").val()+"/cusAddress/getAddressById?addressId="+selectAddressId;
	$.getJSON(url,function(result){
		// 跳转登录页
		if (null == result) {
			showErrorMsg("请选择收货地址。");
			return;
		}
		if(result.status=="needLogin"){
			var loginUrl = $("#path").val()+"/customer/toLogin";
			location.href=loginUrl;
		}else{
			var address = result;
			$(".address").find("input").val(address.addressId);// 收货地址ID
			$(".address").find("a").attr("href","javascript:selectReceiveAddress("+address.addressId+")");// 当前收货地址ID
			$(".address").find(".u_name .contactor").text(address.contactor);// 收货人姓名
			$(".address").find(".u_name .tel").text(address.mobile);// 收货人手机
			$(".address").find(".u_address .address").text(address.provinceCityCountyName + " " + address.address);// 收货地址ID
			$(".address").removeClass("hide");
			$(".add-address").addClass("hide"); // 隐藏添加收货地址
			
			$("#confirmOrder").show();
			$('#addressList').hide();
		}
	});
}

/**
 * 
//跳转修改地址页面
 */
function toUpdateAddrBt(addressId){
	if(addressId != null && addressId!=""){
		window.location=$("#path").val()+"/cusAddress/toUpdateAddress?addressId="+addressId;
		return ;
	}
}
/**
//删除地址
 * 
 */
function deleteAddrBt(addressId){
	
	$.dialog({
        content : '确定删除吗？',
        title : '众聚猫提示',
        ok : function() {
    		var _dataType="text";
    		var _type="POST";
    		var  _url=$("#path").val()+"/cusAddress/deleteAddress";
    		var _async =true;
    		var _data="addressId="+addressId;
    		$.ajax({
    			   dataType:_dataType,
    			   type: _type,
    			   url: _url,
    			   data: _data,
    			   async:_async,
    			   success: function(res){
    				   if(res =="deleteok"){
    					   showErrorMsg("删除成功");
    						var url = $("#path").val();
    						var referenceAddressId = $("#referenceAddressId").val();
    						if(!isEmpty(referenceAddressId)){
    							url += "/cusAddress/findAllAddress?referenceAddressId="+referenceAddressId+" #addressList";
    						}else{
    							url += "/cusAddress/findAllAddress #addressList";
    						}
    						$('#addressList').load(url);
    					}else{
    						showErrorMsg("删除失败，请稍后重试"); }
    			   		},
    				error:function(){
    					showErrorMsg("网络连接超时，请您稍后重试");
    			   }
    			});    
    	
        },
        cancel : function() {
            
        },
        lock : false
    });
	 
}
/**
// 设为默认
 * 
 */
function defaultAddr(addressId){
	
	if(addressId ==null || addressId==""){
		showErrorMsg("设置默认失败");
		return;
	}
	var referenceAddressId = $("#referenceAddressId").val();
	var _dataType="text";
	var _type="POST";
	var  _url=$("#path").val()+"/cusAddress/defaultAddress";
	var _async =true;
	var _data="addresssId="+addressId;
	$.ajax({
		   dataType:_dataType,
		   type: _type,
		   url: _url,
		   data: _data,
		   async:_async,
		   success: function(res){
			   if(res =="defaultok"){
				   showErrorMsg("设为默认成功");
					var url = $("#path").val();
					url += "/cusAddress/findAllAddress?referenceAddressId=" + referenceAddressId + " #addressList";
					$('#addressList').load(url);
				}else{
					showErrorMsg("设置失败，请稍后重试"); }
		   		},
			error:function(){
				showErrorMsg("网络连接超时，请您稍后重试");
		   }
		});    
}


/**
//添加地址
 * 
 */
function saveAddr(){
	
	if(!checkAddr()){
		$("#addressOperBtn").attr("href", "javascript:AddOrUpdateAddr();");
		return ;
	}
	var _dataType="text";
	var _type="POST";
	var  _url=$("#path").val()+"/cusAddress/addAddress";
	var _async =true;
	var _data=$("#updateOrAddAddrssForm").serialize();;
	$.ajax({
		   dataType:_dataType,
		   type: _type,
		   url: _url,
		   data: _data,
		   async:_async,
		   success: function(res){
			   if(res =="addok"){
					var url = $("#path").val();
					var referenceAddressId = $("#referenceAddressId").val();
					if(!isEmpty(referenceAddressId)){
						url += "/cusAddress/findAllAddress?referenceAddressId="+referenceAddressId+" #addressList";
					}else{
						url += "/cusAddress/findAllAddress #addressList";
					}
					$('#addressList').load(url);
				}
			   else if(res == "moreAddress"){
				   showErrorMsg("地址最多只能添加10条");
				   $("#addressOperBtn").attr("href", "javascript:AddOrUpdateAddr();");
			   }
			   else{
				   showErrorMsg("添加失败，请稍后重试"); 
				   $("#addressOperBtn").attr("href", "javascript:AddOrUpdateAddr();");
			   }
	   		},
			error:function(){
				$("#addressOperBtn").attr("href", "javascript:AddOrUpdateAddr();");
				showErrorMsg("网络连接超时，请您稍后重试");
		   }
		});    
	}
/**
//商户中心添加收银员
 * 
 */
/*function saveAddr(){
	
	if(!checkAddr()){
		$("#ShouyinOperBtn").attr("href", "javascript:AddOrUpdateShouyin();");
		return ;
	}
	var _dataType="text";
	var _type="POST";
	var  _url=$("#path").val()+"/supplierWealth/addShouyin";
	var _async =true;
	var _data=$("#updateOrAddAddrssForm").serialize();;
	$.ajax({
		dataType:_dataType,
		type: _type,
		url: _url,
		data: _data,
		async:_async,
		success: function(res){
			if(res =="addok"){
				var url = $("#path").val();
				var referenceAddressId = $("#referenceAddressId").val();
				if(!isEmpty(referenceAddressId)){
					url += "/cusAddress/findAllAddress?referenceAddressId="+referenceAddressId+" #addressList";
				}else{
					url += "/cusAddress/findAllAddress #addressList";
				}
				$('#addressList').load(url);
			}
			else if(res == "moreAddress"){
				showErrorMsg("地址最多只能添加10条");
				$("#ShouyinOperBtn").attr("href", "javascript:AddOrUpdateShouyin();");
			}
			else{
				showErrorMsg("添加失败，请稍后重试"); 
				$("#ShouyinOperBtn").attr("href", "javascript:AddOrUpdateShouyin();");
			}
		},
		error:function(){
			$("#ShouyinOperBtn").attr("href", "javascript:AddOrUpdateShouyin();");
			showErrorMsg("网络连接超时，请您稍后重试");
		}
	});    
}*/

//修改地址
function updateAddr(){
	
	if(!checkAddr()){
		$("#addressOperBtn").attr("href", "javascript:AddOrUpdateAddr();");
		return ;
	}
	var _dataType="text";
	var _type="POST";
	var  _url=$("#path").val()+"/cusAddress/updateAddress";
	var _async =true;
	var _data=$("#updateOrAddAddrssForm").serialize();
	$.ajax({
		   dataType:_dataType,
		   type: _type,
		   url: _url,
		   data: _data,
		   async:_async,
		   success: function(res){
			   $("#addressOperBtn").attr("href", "javascript:AddOrUpdateAddr();");
			   if(res =="updateok"){
					var url = $("#path").val();
					var referenceAddressId = $("#referenceAddressId").val();
					if(!isEmpty(referenceAddressId)){
						url += "/cusAddress/findAllAddress?referenceAddressId="+referenceAddressId+" #addressList";
					}else{
						url += "/cusAddress/findAllAddress #addressList";
					}
					$('#addressList').load(url);
			   }else{
				   showErrorMsg("修改失败，请稍后重试"); }
		   		},
			error:function(){
				$("#addressOperBtn").attr("href", "javascript:AddOrUpdateAddr();");
				showErrorMsg("网络连接超时，请您稍后重试");
		   }
		});    
	
}
//商户中心收银员修改
/*function updateAddr(){
	
	if(!checkAddr()){
		$("#ShouyinOperBtn").attr("href", "javascript:AddOrUpdateShouyin();");
		return ;
	}
	var _dataType="text";
	var _type="POST";
	var  _url=$("#path").val()+"/cusAddress/updateAddress";
	var _async =true;
	var _data=$("#updateOrAddAddrssForm").serialize();
	$.ajax({
		dataType:_dataType,
		type: _type,
		url: _url,
		data: _data,
		async:_async,
		success: function(res){
			$("#ShouyinOperBtn").attr("href", "javascript:AddOrUpdateShouyin();");
			if(res =="updateok"){
				var url = $("#path").val();
				var referenceAddressId = $("#referenceAddressId").val();
				if(!isEmpty(referenceAddressId)){
					url += "/cusAddress/findAllAddress?referenceAddressId="+referenceAddressId+" #addressList";
				}else{
					url += "/cusAddress/findAllAddress #addressList";
				}
				$('#addressList').load(url);
			}else{
				showErrorMsg("修改失败，请稍后重试"); }
		},
		error:function(){
			$("#ShouyinOperBtn").attr("href", "javascript:AddOrUpdateShouyin();");
			showErrorMsg("网络连接超时，请您稍后重试");
		}
	});    
	
}*/

//点击完成按钮修改或者添加
function AddOrUpdateAddr(){
	
	$("#addressOperBtn").attr("href", "javascript:void(0);");
	
	var AUaddressId= $("#AUaddressId").val();
	//修改
	if(AUaddressId !=null && AUaddressId!=""){
		updateAddr();
	}
	else{
		//添加
		saveAddr();
	}
}

//打开添加的div
function addAddrBt(type){
	//如果是添加 那么将 表单的参数全部制空
	if(type =="add"){
		  $("#AUaddressId").val("");
		  $("#contactorF").val("");
		  $("#contactorF").val("");
		  $("#mobileF").val("");
		  /*$("#postCodeF").val("");*/
		  $("#addrAliasF").val("");
		  $("#addressF").val("");
		  $("#provinceId").val("");
		  $("#cityId").val("");
		  $("#area_id").val("");
		  getAllProvince(null); 
	}
		  $("#address").hide();
		 $("#addressAddOrUpdate").show();
		 $(".add-address").hide();
	 
}

//商户中心的收银员打开添加,新建收银员
/*function addShouyin(type){
	//如果是添加 那么将 表单的参数全部制空
	if(type =="add"){
		$("#AUid").val("");
		$("#kfName").val("");
		$("#mobileF").val("");
		getAllProvince(null); 
	}
	$("#shouyin").hide();
	$("#shouyinAddOrUpdate").show();
	$(".add-shouyin").hide();
	
}*/
//关闭添加的div
function closeAddrBt(){
	$("#address").show();
	$("#addressAddOrUpdate").hide();
	$(".add-address").show();
}


//打开修改  的div
function updateAddrBt(addressId){
	var _dataType="text";
	var _type="POST";
	var  _url=$("#path").val()+"/cusAddress/toUpdateAddress";
	var _async =true;
	var _data="addressId="+addressId;
	$.ajax({
		   dataType:_dataType,
		   type: _type,
		   url: _url,
		   data: _data,
		   async:_async,
		   success: function(data){
			  var addr=eval("("+data+")");
			  $("#AUaddressId").val(addr.addressId);
			  $("#contactorF").val(addr.contactor);
			  $("#contactorF").val(addr.contactor);
			  $("#mobileF").val(addr.mobile);
			  /*$("#postCodeF").val(addr.postCode);*/
			  $("#addrAliasF").val(addr.addrAlias);
			  $("#addressF").val(addr.address);
			  
			  addAddrBt();
			  getAllProvince(addr.provinceId);
			  getCity(addr.provinceId, addr.cityId);
			  getCount(addr.cityId, addr.areaId);
			 
		   },
			error:function(){
			   showErrorMsg("网络连接超时，请您稍后重试");
		   }
		});   
	
}


//获取所有省份
function getAllProvince(proId){
	
	 $("#provinceId option").remove();
	 $("#provinceId").append('<option value="" >请选择</option> ');
	 $("#cityId option").remove();
	 $("#cityId").append('<option value=""  > 请选择 </option> ');
	 $("#area_id option").remove();
	 $("#area_id").append('<option value=""  >请选择</option> ');
	 var _dataType = "text";
	var _type = "POST";
	var  _url = $("#path").val()+"/cusAddress/getProvince";
	var _async = true;
	var _contentType = 'application/x-www-form-urlencoded;charset=utf-8';
	$.ajax({
		   dataType:_dataType,
		   type: _type,
		   url: _url,
		   async:_async,
		   contentType:_contentType,
		   success: function(res){
			   if(res==null ||res =="") {return false;}
			   var pros=eval('(' + res + ')');
			 //  $("#provinceId").append('<option value=""  >'+请选择+'</option> ');
			   for (var i = 0; i < pros.length; i++) {
				   $("#provinceId").append('<option value="'+pros[i].provinceid+'"  >'+pros[i].provincename+'</option> ');
			   }
			   if(proId != null ){
				   $("#provinceId").val(proId);
			   }
		   },
			error:function(){
				showErrorMsg("网络连接超时，请您稍后重试");
		   }
		});    
}
 
 //获取所有的城市
 function getCity(prId,cityId){
	 $("#cityId option").remove();
	 $("#cityId").append('<option value=""  > 请选择 </option> ');
	 $("#area_id option").remove();
	 $("#area_id").append('<option value=""  >请选择</option> ');
	 var proId=$("#provinceId").val();
	  
	 if(prId !=null){
		 proId=prId;
	 }
	var _dataType="text";
	var _type="POST";
	var  _url=$("#path").val()+"/cusAddress/getCity";
	var _async =true;
	var _data="proId="+proId;
	var _contentType='application/x-www-form-urlencoded;charset=utf-8';
	$.ajax({
		   dataType:_dataType,
		   type: _type,
		   url: _url,
		   data: _data,
		   async:_async,
		   contentType:_contentType,
		   success: function(res){
			   if(res==null ||res =="") {return false;}
			   var pros=eval('(' + res + ')');
			//   $("#cityId").append('<option value=""  >'+请选择+'</option> ');
			   for (var i = 0; i < pros.length; i++) {
			  $("#cityId").append('<option value="'+pros[i].cityid+'">'+pros[i].cityname+'</option> ');
			   }
			   if(cityId!=null){
				   $("#cityId").val(cityId);
			   }
			   
		   },
			error:function(){
				showErrorMsg("网络连接超时，请您稍后重试");
		   }
		});    
}
 
 //获取所有的乡镇
 function getCount(cityId,counId){
	 $("#area_id option").remove();
	 $("#area_id").append('<option value=""  >请选择</option> ');
	 var ciId=$("#cityId").val();
	 
	 if(cityId != null){
		 ciId=cityId;
	 }
	 
	var _dataType="text";
	var _type="POST";
	var  _url=$("#path").val()+"/cusAddress/getCountry";
	var _async =true;
	var _data="ciId="+ciId;
	$.ajax({
		   dataType:_dataType,
		   type: _type,
		   url: _url,
   		   data: _data,
		   async:_async,
		   success: function(res){
			   if(res==null ||res =="") {return false;}
			   var pros=eval('(' + res + ')');
			   //$("#area_id").append('<option value=""  >'+请选择+'</option> ');
			   for (var i = 0; i < pros.length; i++) {
			 	$("#area_id").append('<option value="'+pros[i].countyid+'">'+pros[i].countyname+'</option> ');
			   }
			   if(counId!=null){
				   $("#area_id").val(counId);
			   }
		   },
			error:function(){
				showErrorMsg("网络连接超时，请您稍后重试");
		   }
		});    
}

 /**
  * 校验收货人地址的填写
  */
 function checkAddr(){
	 
	var contact = $("#contactorF").val();
	var mobile =$("#mobileF").val();
	/*var postCode=$("#postCodeF").val();*/
	//$("#addrAliasF").val("");
	var address=$("#addressF").val();
	 
	var provinceId=$("#provinceId").val();
	var cityId=$("#cityId").val();
	var areaId=$("#area_id");
	
	if(isEmpty(contact)){
		showErrorMsg("收货人姓名不能为空");
		return false;
	} 
	if(!(/([a-zA-z0-9.\u4E00-\u9FA5]){2,30}$/).test(contact)){
		okBT("姓名不能包含特殊字符和空格，可包含'.'");
		return false;
	}
	var res=verifyPhoneNumber(mobile);
	if(res!="ok"){
		showErrorMsg(res);return false;
	}
	if(isEmpty(provinceId)){
		showErrorMsg("省份不能为空");return false;
	}if(isEmpty(cityId)){
		showErrorMsg("城市不能为空");return false;
	}
	if(isEmpty(areaId.val()) && $(areaId).find("option").length > 1){
		showErrorMsg("区域不能为空");return false;
	}
	
	/*if(isEmpty(postCode)){
		showErrorMsg("邮编不能为空");return false;
	}
	
	if(isNaN(postCode) || postCode.length != 6){
		showErrorMsg("邮编长度为6位");return false;
	}*/
 
	if(isEmpty(address)){
		showErrorMsg("请输入详细地址");return false;
	}
	
	if(address.length  > 50 ){
		showErrorMsg("详细地址必须在50个字以内");return false;
	}
	
	return true;
 }
 
 //清空详细地址重新填写
function reAddress(){
	$("#addressF").val("");
}

function showErrorMsg(str){
	//  $(".error_tips").removeClass("hide");
	//$(".error_tips").html("<font color=red>&nbsp;&nbsp;&nbsp;"+str+"</font>");  
	
	$.dialog({
        content : str,
        title : '众聚猫提示',
        time: 1000,
	});
	return;
}
 
