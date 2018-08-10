var check = true;
// 统计埋码
var _trackDataType = 'wap'; // 标记数据来源，参数是web和wap，可以为空，默认是web
var _Schannel_website_id = '10000006';// 分站编号，不存在可不写此变量或者留空
var _trackData = _trackData || [];// 必须为全局变量，假如之前并没有声明，请加此行代码；
var userId='433';
var path = $("#path").val();
$(function() {
	getProvince(null);
	//function sendUid(asd){
	//	userId=asd;
	//	if(userId==''){
	//		$.dialog({
	//			content : '获取用户ID失败，请重新登陆！',
	//			title : '众聚猫提示',
	//			time: 2000
	//		});
	//	}
	//}
});

function getProvince(proId) {
	$("#provinceId").html('<option value="">请选择</option> ');
	$("#cityId").html('<option value=""> 请选择 </option> ');
	$("#area_id").html('<option value="">请选择</option> ');

	var _dataType = "text";
	var _type = "POST";
	var _url = path + "/prize/getProvince";
	var _async = false;
	var _contentType = 'application/x-www-form-urlencoded;charset=utf-8';
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		async : _async,
		contentType : _contentType,
		success : function(res) {
			var pros = JSON.parse(res);
			for ( var i = 0; i < pros.length; i++) {
				$("#provinceId").append(
					'<option value="' + pros[i].provinceid + '"  >'
					+ pros[i].provincename + '</option> ');
			}
			if (proId != null) {
				$("#provinceId").val(proId);
			}
		},
		error : function() {
			$.dialog({
				content : '网络繁忙，请稍候重试！',
				title : '众聚猫提示',
				time: 2000
			});
		}
	});
}

function getCity(prId, cityId) {
	$("#cityId").html('<option value="">请选择 </option> ');
	$("#area_id").html('<option value="">请选择</option> ');
	var proId = $("#provinceId").val();
	var _dataType = "text";
	var _type = "POST";
	var _url = path + "/prize/getCity";
	var _async = false;
	var _data = "proId=" + proId;
	var _contentType = 'application/x-www-form-urlencoded;charset=utf-8';
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		contentType : _contentType,
		success : function(res) {
			var pros = JSON.parse(res);
			for ( var i = 0; i < pros.length; i++) {
				$("#cityId").append(
					'<option value="' + pros[i].cityid + '">'
					+ pros[i].cityname + '</option> ');
			}
			if (cityId != null) {
				$("#cityId").val(cityId);
			}

		},
		error : function() {
			$.dialog({
				content : '网络繁忙，请稍候重试！',
				title : '众聚猫提示',
				time: 2000
			});
		}
	});
}

function getArea(cityId, areaId) {
	$("#area_id").html('<option value="">请选择</option> ');
	var ciId = $("#cityId").val();

	var _dataType = "text";
	var _type = "POST";
	var _url = path + "/prize/getCountry";
	var _async = false;
	var _data = "ciId=" + ciId;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(res) {
			var pros = JSON.parse(res);
			for ( var i = 0; i < pros.length; i++) {
				$("#area_id").append(
					'<option value="' + pros[i].countyid + '">'
					+ pros[i].countyname + '</option> ');
			}
			if (areaId != null) {
				$("#area_id").val(areaId);
			}
		},
		error : function() {
			$.dialog({
				content : '网络繁忙，请稍候重试！',
				title : '众聚猫提示',
				time: 2000
			});
		}
	});
}

function savePrize() {
	var proId = $("#provinceId").val();
	var ciId = $("#cityId").val();
	var areaId = $("#area_id").val();
	var receipt_adress= $("#adress_datil").val();
	var receipt_phone= $("#adress_phone").val();
	var receipt_autor= $("#address_autor").val();
	var recordId= $("#record_id").val();
	//receipt_adress=$("#provinceId").find("option:selected").text()+" "+$("#cityId").find("option:selected").text()+" "+$("#area_id").find("option:selected").text()+" "+receipt_adress;
	//$("#provinceId").option($("#provinceId").val()).text;
	var p_select = document.getElementById("provinceId");
	var p_index = p_select.selectedIndex;
	var p_text = p_select.options[p_index].text;

	var c_select = document.getElementById("cityId");
	var c_index = c_select.selectedIndex;
	var c_text = c_select.options[c_index].text;

	var a_select = document.getElementById("area_id");
	var a_index = a_select.selectedIndex;
	var a_text = a_select.options[a_index].text;
	receipt_adress=p_text+" "+c_text+" "+a_text+" "+receipt_adress;
	var url = $("#path").val()+"/prize/savePrizeAddress";
	var data ="recordId="+recordId+"&province="+proId+"&city="+ciId+"&area="+areaId+"&address="+receipt_adress+"&ownName="+receipt_autor+"&ownPhone="+receipt_phone;
	var dataType = "text";
	var path = $("#path").val();
	if(isEmpty(receipt_autor)||isEmpty(receipt_adress)||isEmpty(proId)||isEmpty(ciId)||isEmpty(areaId)){
		$.dialog({
			content : '请完善收货信息！',
			title : '众聚猫提示',
			time: 2000
		});
		return;
	}
	if(!checkInput(receipt_phone)){
		return
	}
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		async : false,
		dataType : dataType,
		success : function(data) {
			//var jsonObj=eval(data);
			if(data=='1'){
				$("#waite").show();
				$("#linj").hide();
				$("#address_autor").attr("readonly",true);
				$("#adress_phone").attr("readonly",true);
				$("#provinceId").attr("disabled",true);
				$("#cityId").attr("disabled",true);
				$("#area_id").attr("disabled",true);
				$("#adress_datil").attr("readonly",true);
			}
		}
	});
}
function checkInput(mobile){

	// 检验手机号是否为空
	if(isEmpty(mobile)){
		$.dialog({
			content : '请输入手机号！',
			title : '众聚猫提示',
			time: 2000
		});
		return false;
	}
	// 检验手机号有效性
	if(!check_mobile(mobile)){
		$.dialog({
			content : '请输入正确的手机号！',
			title : '众聚猫提示',
			time: 2000
		});
		return false;
	}
	return true;
}
/**
 * 检查手机号码
 * @param mobile
 * @returns {Boolean}
 */
function check_mobile(mobile){
	/*var regu = /^\d{11}$/;*/
	var regu = /^(((13[0-9]{1})|(14[5,7]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	var re = new RegExp(regu);
	if(!re.test(mobile)){
		return  false;
	}
	return true;
}
/**
 * 判断是否是空
 * @param value
 */
function isEmpty(value){
	if(value == null || value == "" || value == "undefined" || value == undefined || value == "null"){
		return true;
	}
	else{
		value = value.replace(/\s/g,"");
		if(value == ""){
			return true;
		}
		return false;
	}
}