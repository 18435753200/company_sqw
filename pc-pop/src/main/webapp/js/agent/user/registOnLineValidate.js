
$(function(){
//		toFocus() 这是获取焦点时提示内容输入
//		toBlurContent(''); 这是 失去焦点时去除样式以及检测内容长度                                               
		toFocus('nameJC','可以与注册名称一致');
		toBlurContent('nameJC');
		toFocus('contactTelP','可与联系人手机一致');
		toBlurFixedLinded('contactTelP');
		toFocus('legalPhone','可填写手机号');
		toBlurFixedLinded('legalPhone');
		
		
		toBlurPhone('phoneP');
		
		toFocusNothing('registerAddress');
		toFocusNothing('legalPerson');
		toFocusNothing('legalPhone');
		toFocusNothing('legalPersonCardNo');
		toFocusNothing('idCardFrontTextField');
		toFocusNothing('idCardVersoTextField');
		toFocusNothing('businessLicenseTextField');
		toFocusNothing('businessLicenseNo');
		toFocusNothing('accountName');
		toFocusNothing('accountNo');
		toFocusNothing('areaId');
		toFocusNothing('accoutBankno');
		
	});	


	function validateTable(){
		var flag=true;
		var i=0;
		flag=validateNull('nameJC','请输入营业名称');
		if(flag==false){i++;};
		flag=charHundred('nameJC');
		if(flag==false){i++;};
		flag=validateLocationNull('areaId','请选择企业所在地');
		if(flag==false){i++;};
		flag=validateLocationNull('accoutBankno','请选择开户行名称');
		if(flag==false){i++;};
		flag=validateNull('registerAddress','请输入注册地址');
		if(flag==false){i++;};
		flag=charHundred('registerAddress');
		if(flag==false){i++;};
		flag=validateNull('contactTelP','请输入业务联系人电话');
		if(flag==false){i++;};
		flag=charHundred('contactTelP');
		if(flag==false){i++;};
		//手机号有验证
		flag=validateNull('phoneP','请输入业务联系人手机');
		if(flag==false){i++;};
		flag=validateNull('legalPerson','请输入法人名称');
		if(flag==false){i++;};
		flag=charHundred('legalPerson');
		if(flag==false){i++;};
		flag=validateNull('legalPhone','请输入法人电话');
		if(flag==false){i++;};
		flag=charHundred('legalPhone');
		if(flag==false){i++;};
		flag=validateNull('legalPersonCardNo','请输入证件号码');
		if(flag==false){i++;};
		flag=charHundred('legalPersonCardNo');
		if(flag==false){i++;};
		flag=validateNull('businessLicenseNo','请输入营业执照号码');
		if(flag==false){i++;};
		flag=charHundred('businessLicenseNo');
		if(flag==false){i++;};
		//上传文件不要长度验证
		flag=validateFileNull('idCardFrontTextField','请上传身份证正面');
		if(flag==false){i++;};
		flag=validateFileNull('idCardVersoTextField','请上传身份证反面');
		if(flag==false){i++;};
		flag=validateFileNull('businessLicenseTextField','请上传营业执照');
		if(flag==false){i++;};
		
		flag=validateNull('accountName','请输入结算账号名称');
		if(flag==false){i++;};
		flag=charHundred('accountName');
		if(flag==false){i++;};
		flag=validateNull('accountNo','请输入结算账号');
		if(flag==false){i++;};
		flag=charHundred('accountNo');
		if(flag==false){i++;};
		if(flag==undefined && i==0){
			flag=true;
		}else{
			flag=false;
		}
		
		return flag;
	}
	
	//原始的非空校验
	function validateNull(name,content){
		if(lengthValidata(name)==false){
			$("#"+name+"_error").html(content);
			$("#"+name+"_error").removeClass("errorHidden");
			$("#"+name+"_error").addClass("errorShow");
			$("#"+name).attr("style","border-color:#FF0000;width: 200px;");
			return false;
		}
	}
	//为了文件校验不出框
	function validateFileNull(name,content){
		if(lengthValidata(name)==false){
			$("#"+name+"_error").html(content);
			$("#"+name+"_error").removeClass("errorHidden");
			$("#"+name+"_error").addClass("errorShow_file");
			$("#"+name).attr("style","border-color:#FF0000;width: 200px;");
			return false;
		}
	}
	
	
	//为了下拉框不变形
	function validateLocationNull(name,content){
		if(lengthValidata(name)==false){
			$("#"+name+"_error").html(content);
			$("#"+name+"_error").removeClass("errorHidden");
			$("#"+name+"_error").addClass("errorShow");
			$("#"+name).attr("style","border-color:#FF0000;width: 225px;");
			return false;
		}
	}
	

	
	//未输入内容判断
	function lengthValidata(name){
		var nameAS=$("#"+name);
		if(nameAS.val()==null  || nameAS.val()== ''){
			return false;
		}
	}
	
	
	//获取焦点事件  
	function toFocus(name,content){
		$("#"+name).focus(function(){
			$("#"+name+"_error").removeClass("errorShow");
			$("#"+name+"_error").removeClass("errorHidden");
			$("#"+name+"_error").html(content);
			$("#"+name).removeAttr("style","border-color:#FF0000;width: 200px;");
			$("#"+name+"_error").addClass("tipShow");
			
		});
	}
	
	//获取焦点清除样式
	function toFocusNothing(name){
		$("#"+name).focus(function(){
		$("#"+name+"_error").removeClass();
		$("#"+name).removeAttr("style","border-color:#FF0000;width: 200px;");
		$("#"+name+"_error").html("");
		});
	}
	
	
	//失去焦点时检测内容长度
	function toBlurContent(name){
		$("#"+name).blur(function(){
			$("#"+name+"_error").removeClass();
			$("#"+name+"_error").html("");
			charHundred(name);
		});
	}
	
	//失去焦点时检测手机号码
	function toBlurPhone(name){
		$("#"+name).blur(function(){
			$("#"+name+"_error").removeClass();
			$("#"+name+"_error").html("");
			$("#"+name).removeAttr("style","border-color:#FF0000;width: 200px;");
			if(lengthValidata(name)!=false && phoneValidate($("#"+name).val())==false){
				$("#"+name+"_error").html("手机号格式错误");
				$("#"+name+"_error").removeClass("errorHidden");
				$("#"+name+"_error").addClass("errorShow");
				$("#"+name).attr("style","border-color:#FF0000;width: 200px;");
				return false;
			}
		});
	}
	
	//失去焦点时座机号码判断
	function toBlurFixedLinded(name){
		$("#"+name).blur(function(){
			$("#"+name+"_error").removeClass();
			$("#"+name+"_error").html("");
			$("#"+name).removeAttr("style","border-color:#FF0000;width: 200px;");
			if(lengthValidata(name)!=false && phoneValidate($("#"+name).val())==false  && fixedLineValidate($("#"+name).val())==false){
				$("#"+name+"_error").html("座机号格式错误");
				$("#"+name+"_error").removeClass("errorHidden");
				$("#"+name+"_error").addClass("errorShow");
				$("#"+name).attr("style","border-color:#FF0000;width: 200px;");
				return false;
			}
		});
	}
	
	function charHundred(name){
		if(lengthBeyond(name)==false){
			$("#"+name+"_error").html("输入长度只能在1-100位字符之内");
			$("#"+name+"_error").removeClass("errorHidden");
			$("#"+name+"_error").addClass("errorShow");
			$("#"+name).attr("style","border-color:#FF0000;width: 200px;");
			return false;
		}
		if(lengthValidata(name)==false){
			return false;
		}
	}
	
	//输入内容超过100个字符的判断
	function lengthBeyond(name){
		var nameAS=$("#"+name);
		if(nameAS.val().length>100){
			return false;
		}
	}
	
	//手机号校验
	function phoneValidate(txt){
		 var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;  
         if (!myreg.test(txt)) {  
             return false;  
         } else {  
             return true;  
         }  
	}
	
//	座机号校验
	function fixedLineValidate(txt){
		 var myreg=/0\d{2,3}-\d{7,8}/
         if (!myreg.test(txt)) {  
             return false;  
         } else {  
             return true;  
         }  
	};
	
	
	//上传图片验证
	function uploadPicture(target,id){
		//清除样式
		$("#"+id+"_error").removeClass();
		$("#"+id+"_error").html("");
		$("#"+id).removeAttr("style","border-color:#FF0000;width: 200px;");
		
		var flag=true;
		
		//验证文件大小
		flag=validateFileSize(target,id,300);
		
		 //验证图片类型
		 if(flag){
			 flag=validatePicType(target,id);
		 }
		 
		 return flag;
		 
	}
	
	
	//上传文件验证
	function uploadFile(target,id){
		
		//清除样式
		$("#"+id+"_error").removeClass();
		$("#"+id+"_error").html("");
		$("#"+id).removeAttr("style","border-color:#FF0000;width: 200px;");
		
		var flag=true;
		
		//验证文件大小        
		flag=validateFileSize(target,id,300);
		
		//验证图片类型
		if(flag){
			flag=validateFileType(target,id);
		}
		
		return flag;
		
	}

	//	图片类型验证
	function validatePicType(target,id){
		var ext;  
	 	var imgName=target.value;
	 	var idx=imgName.lastIndexOf("."); 
        if (idx != -1){   
            ext = imgName.substr(idx+1).toUpperCase();   
            ext = ext.toLowerCase( ); 
            if (ext != 'jpg' && ext != 'png' && ext != 'jpeg'){
              //提示错误信息 
    			$("#"+id+"_error").html("只能上传.jpg.png.jpeg类型的文件!");
    			$("#"+id+"_error").removeClass("errorHidden");
    			$("#"+id+"_error").addClass("errorShow");
    			$("#"+id).attr("style","border-color:#FF0000;width: 200px;");
    			//清空上传的文件
    			 $("#"+id).val('');
    			 target.value='';
    			 return false;
            }   
        } else {  
        	 //提示错误信息 
			$("#"+id+"_error").html("只能上传.jpg.png.jpeg类型的文件!");
			$("#"+id+"_error").removeClass("errorHidden");
			$("#"+id+"_error").addClass("errorShow");
			$("#"+id).attr("style","border-color:#FF0000;width: 200px;");
			//清空上传的文件
			 $("#"+id).val('');
			 target.value='';
			 	return false;
        }  
        return true;
	}
	
	
	//	文件类型验证
	function validateFileType(target,id){
		var ext;  
		var imgName=target.value;
		var idx=imgName.lastIndexOf("."); 
		if (idx != -1){   
			ext = imgName.substr(idx+1).toUpperCase();   
			ext = ext.toLowerCase( ); 
			if (ext != 'doc' && ext != 'docx'){
				//提示错误信息 
				$("#"+id+"_error").html("只能上传.doc.docx类型的文件!");
				$("#"+id+"_error").removeClass("errorHidden");
				$("#"+id+"_error").addClass("errorShow");
				$("#"+id).attr("style","border-color:#FF0000;width: 200px;");
				//清空上传的文件
				$("#"+id).val('');
				target.value='';
				return false;
			}   
		} else {  
			//提示错误信息 
			$("#"+id+"_error").html("只能上传.doc.docx类型的文件!");
			$("#"+id+"_error").removeClass("errorHidden");
			$("#"+id+"_error").addClass("errorShow");
			$("#"+id).attr("style","border-color:#FF0000;width: 200px;");
			//清空上传的文件
			$("#"+id).val('');
			target.value='';
			return false;
		}  
		return true;
	}
	
	//文件大小验证
	function validateFileSize(target,id,endSize){
		//清除样式
		$("#"+id+"_error").removeClass();
		$("#"+id+"_error").html("");
		$("#"+id).removeAttr("style","border-color:#FF0000;width: 200px;");
		
		 var isIE = /msie/i.test(navigator.userAgent) && !window.opera;  
		 var fileSize = 0;           
		 if (isIE && !target.files){       
	        var filePath = target.value;       
	        var fileSystem = new ActiveXObject("Scripting.FileSystemObject");          
	        var file = fileSystem.GetFile (filePath);       
	        fileSize = file.Size;      
		 } else {      
	        fileSize = target.files[0].size;       
		 } 
		 
		 var size = fileSize/1024;
		 if(size>endSize){
			//提示错误信息 
			$("#"+id+"_error").html("文件大小不能超过"+endSize+"k");
			$("#"+id+"_error").removeClass("errorHidden");
			$("#"+id+"_error").addClass("errorShow");
			$("#"+id).attr("style","border-color:#FF0000;width: 200px;");
			//清空上传的文件
			 $("#"+id).val('');
			 target.value='';
			return false;
		 }
		 return true;
	}
	
	

	