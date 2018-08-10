//*************************公共方法和变量*************************

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

/**
 * 判断是否是数字
 */
function isNumber(value){
	if(isNaN(value)){
		return false;
	}
	else{
		return true;
	}
}

/**
 * 只包含中文和英文
 * @param cs
 * @returns {Boolean}
 */
function isGbOrEn(value){
    var regu = "^[a-zA-Z\u4e00-\u9fa5]+$";
    var re = new RegExp(regu);
    if (value.search(re) != -1){
      return true;
    } else {
      return false;
    }
}

/**
 * 检查邮箱格式
 * @param email
 * @returns {Boolean}
 */
function check_email(email){  
   if(email){
   var myReg=/(^\s*)\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*(\s*$)/;
   if(!myReg.test(email)){return false;}
   return true;
   }
   return false;
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
 * 检查身份证号
 * @param identity
 */
function check_identity(identity){
	var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	if(!reg.test(identity)){
		return false;
	}
	return true;
}

/**
 * 检查邮政编码
 * @param postal
 * @returns {Boolean}
 */
function check_postcode(postcode){
  var re = /^[0-9][0-9]{5}$/;
  if(!re.test(postcode)){
	 return  false;
  }
  return true;
}

/**
 * 验证电话号码，带"(,),-"字符和数字其他不通过
 * @param str
 * @returns {Boolean}
 */
function checkPhone(str){
   if(str.length > 20){
    return false;
   }
   var patternStr = "(0123456789-)";
   var  strlength=str.length; 
   for(var i=0;i<strlength;i++){ 
        var tempchar=str.substring(i,i+1); 
		if(patternStr.indexOf(tempchar)<0){
		    return false;
		}
   } 
   return true ; 
}

//正则
function trimTxt(txt){
 return txt.replace(/(^\s*)|(\s*$)/g, "");
}
/**
 * 检查是否含有非法字符
 * @param temp_str
 * @returns {Boolean}
 */
function is_forbid(temp_str){
    temp_str=trimTxt(temp_str);
	temp_str = temp_str.replace('*',"@");
	temp_str = temp_str.replace('--',"@");
	temp_str = temp_str.replace('/',"@");
	temp_str = temp_str.replace('+',"@");
	temp_str = temp_str.replace('\'',"@");
	temp_str = temp_str.replace('\\',"@");
	temp_str = temp_str.replace('$',"@");
	temp_str = temp_str.replace('^',"@");
	temp_str = temp_str.replace('.',"@");
	temp_str = temp_str.replace(';',"@");
	temp_str = temp_str.replace('<',"@");
	temp_str = temp_str.replace('>',"@");
	temp_str = temp_str.replace('"',"@");
	temp_str = temp_str.replace('=',"@");
	temp_str = temp_str.replace('{',"@");
	temp_str = temp_str.replace('}',"@");
	var forbid_str=new String('@,%,~,&');
	var forbid_array=new Array();
	forbid_array=forbid_str.split(',');
	for(i=0;i<forbid_array.length;i++){
		if(temp_str.search(new RegExp(forbid_array[i])) != -1)
		return false;
	}
	return true;
}

/**
 * 检查数量
 * @param txtObj
 * @returns {Number}
 */
function checkLength(txtObj){
	var val=txtObj;
	var valLength=0;
	for(var ii=0;ii<val.length;ii++){
		var word=val.substring(ii,1);
		if(/[^\x00-\xff]/g.test(word)){
			valLength+=2;
		}else{
			valLength++;
		}
	}
	return valLength;
}
function checkParameters(id, type, length) {
	var errorFlag = false;
	var errorMessage = null;
	var value = $("#"+id).val();
	switch(type)
	{
		//手机验证
		case 0:
			if (isEmpty(value)) {
				errorFlag = true;
				errorMessage = "手机号码不能为空！";
			} else {
				if (!check_mobile(value)) {
					errorFlag = true;
					errorMessage = "请输入正确的手机号码！";
				}
			}
			break;
		//电话验证
		case 1:
			if (isEmpty(value)) {
				errorFlag = true;
				errorMessage = "电话号码不能为空！";
			} else {
				if (!checkPhone(value)) {
					errorFlag = true;
					errorMessage = "请输入正确的电话号码！";
				}
			}
			break;
		//邮箱验证
		case 2:
			if (isEmpty(value)) {
				errorFlag = true;
				errorMessage = "邮箱地址不能为空！";
			} else {
				if (!check_email(value)) {
					errorFlag = true;
					errorMessage = "请输入正确的邮箱地址！";
				}
			}
			break;
		//邮政编码验证
		case 3:
			if (isEmpty(value)) {
				errorFlag = true;
				errorMessage = "邮政编码不能为空！";
			} else {
				if (!check_postcode(value)) {
					errorFlag = true;
					errorMessage = "请输入正确的邮政编码！";
				}
			}
			break;
		default:
			if (isEmpty(value)) {
				errorFlag = true;
				errorMessage = "不能为空！";
			}
			break;
	}

	if(length != "undefind" && checkLength(value) > length){
		errorFlag = true;
		errorMessage = "输入内容过长，请重新输入！";
	}
	
	if (errorFlag) {
		$("#" + id + "_error").html(errorMessage);
		$("#" + id + "_div").addClass("message");
		return false;
	} else {
		$("#" + id + "_div").removeClass("message");
		$("#" + id + "_error").html("");
	}
	return true;
}

function showMsg(content){
	$.dialog({
        content : content,
        title : '众聚猫提示',
        time: 2000,
	});
}

