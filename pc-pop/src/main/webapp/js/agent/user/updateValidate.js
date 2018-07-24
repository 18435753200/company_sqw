$(function(){
	$("#registsubmit1").click(function () {
			var va=$("#ifSubmit").text();
			if(va==''){
				$("#formcompany").submit();
			}
	});
});



$(function(){
		toFocus('nameJC','可以与注册名称一致');
		toBlurContent('nameJC','请输入营业名称');
		
		toBlurContent('areaId','请选择企业所在地');
		
		toFocus('companyaddr','营业地址');
		toBlurContent('companyaddr','请输入营业地址');
		
		toFocus('registerAddress','注册地址');
		toBlurContent('registerAddress','请输入注册地址');
		
		toBlurContent('contactname','请输入业务联系人');
		
		toFocusNothing('phoneP');
		toBlurPhone('phoneP','请输入手机号');
		
		toFocus('contactTelP','可与联系人手机一致');
		toBlurFixedLinded('contactTelP','请输入业务联系人电话');
		
		toFocusNothing('mailbox');
		toBlurCheckMail('mailbox','请输入邮箱');
		
		toFocusNothing('legalPerson');
		toBlurContent('legalPerson','请输入法人名称');
		
		toFocusNothing('legalPhone');
		toBlurFixedLinded('legalPhone','请输入法人电话');
		
		toFocusNothing('legalPersonCardNo');
		toBlurContent('legalPersonCardNo','请输入法人证件号码');
		
		toFocusNothing('businessLicenseNo');
		toBlurContent('businessLicenseNo','请输入营业执照号码');
		
		toFocusNothing('accountName');
		toBlurContent('accountName','请输入结算账号名称');
		
		toFocusNothing('accountNo');
		toBlurContent('accountNo','请输入结算账号');
		
		
		
		
		toBlurSelect('provinceId','areaId','请选择企业所在地');
		toBlurSelect('cityId','areaId','请选择企业所在地');
		toBlurSelect('areaId','areaId','请选择企业所在地');
		
		toBlurSelect('bank','accoutBankno','请选择开户行名称');
		toBlurSelect('bankProvinceId','accoutBankno','请选择开户行名称');
		toBlurSelect('bankAreaId','accoutBankno','请选择开户行名称');
		toBlurSelect('accoutBankno','accoutBankno','请选择开户行名称');
		
		
		
	});	
		
	
	function validateNull(name,content){
		if(lengthValidata(name)==false){
			$("#"+name+"_error").html(content);
			$("#"+name+"_error").removeClass("errorHidden");
			$("#"+name+"_error").addClass("errorShow");
			$("#"+name).attr("style","border-color:#FF0000;width: 200px;");
			return false;
		}
		return true;
	}
	
	
	//失去焦点邮箱验证
	function toBlurCheckMail(name,content){
		var flag=true;
		$("#"+name).blur(function(){
			$("#"+name+"_error").removeClass();
			$("#"+name+"_error").html("");
			$("#"+name).removeAttr("style","border-color:#FF0000;width: 200px;");
			flag=validateNull(name,content);
			var va=$("#ifSubmit").text();
			if(flag==false){
				if(flag==false && va.indexOf(name)==-1){
					$("#ifSubmit").text(va+name)
					return false;
				};
			}else{
				var ch=checkMail($("#"+name).val());
				if(!ch){
					$("#"+name+"_error").html('邮箱格式不正确');
					$("#"+name+"_error").removeClass("errorHidden");
					$("#"+name+"_error").addClass("errorShow");
					$("#"+name).attr("style","border-color:#FF0000;width: 200px;");
					if(va.indexOf(name)==-1){
						$("#ifSubmit").text(va+name)
						return false;
					};
				}else{
					if(va.indexOf(name)>-1){
						var str=va.replace(name,'');
						$("#ifSubmit").text(str);
					}
				}
			}
			if(flag==true && va.indexOf(name)>-1){
				var str=va.replace(name,'');
				$("#ifSubmit").text(str);
			}
			return true;
		});
	}
	
	
	//邮箱校验
	function checkMail(txt){
		var myreg=/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;  
		if (!myreg.test(txt)) {  
            return false;  
        } else {  
            return true;  
        }  
	}
	
	function toBlurSelect(id,name,content){
		$("#"+id).blur(function(){
			$("#"+name+"_error").removeClass();
			$("#"+name+"_error").html("");
			$("#"+name).removeAttr("style","border-color:#FF0000;width: 200px;");
			var value=$("#"+name).val();
			var va=$("#ifSubmit").text();
			if(value==null || value==''){
				$("#"+name+"_error").html(content);
				$("#"+name+"_error").removeClass("errorHidden");
				$("#"+name+"_error").addClass("errorShow");
				$("#"+name).attr("style","border-color:#FF0000;width: 225px;");
				if(va.indexOf(name)==-1){
					$("#ifSubmit").text(va+name);
				};
			}else{
				if(va.indexOf(name)>-1){
					var str=va.replace(name,'');
					$("#ifSubmit").text(str);
				}
			}
		});
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
	
	
	//失去焦点时检测内容长度,以及是否为空
	function toBlurContent(name,content){
		var flag=true;
		$("#"+name).blur(function(){
			 
			$("#"+name+"_error").removeClass();
			$("#"+name+"_error").html("");
			$("#"+name).removeAttr("style","border-color:#FF0000;width: 200px;");
			charHundred(name);
			flag=validateNull(name,content);
			var va=$("#ifSubmit").text();
			if(flag==false && va.indexOf(name)==-1){
				$("#ifSubmit").text(va+name);
			};
			if(flag==true && va.indexOf(name)>-1){
				var str=va.replace(name,'');
				$("#ifSubmit").text(str);
			}
		});
	}
	
	//失去焦点时检测手机号码
	function toBlurPhone(name,content){
		var flag=true;
		$("#"+name).blur(function(){
			 
			$("#"+name+"_error").removeClass();
			$("#"+name+"_error").html("");
			$("#"+name).removeAttr("style","border-color:#FF0000;width: 200px;");
			flag=validateNull(name,content);
			var va=$("#ifSubmit").text();
			if(flag==false && va.indexOf(name)==-1){
				$("#ifSubmit").text(va+name);
				return ;
			};
			if(flag==true && va.indexOf(name)>-1){
				var str=va.replace(name,'');
				$("#ifSubmit").text(str);
				return ;
			}
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
	function toBlurFixedLinded(name,content){
		var flag=true;
		$("#"+name).blur(function(){
			$("#"+name+"_error").removeClass();
			$("#"+name+"_error").html("");
			$("#"+name).removeAttr("style","border-color:#FF0000;width: 200px;");
			flag=validateNull(name,content);
			var va=$("#ifSubmit").text();
			if(flag==false && va.indexOf(name)==-1){
				$("#ifSubmit").text(va+name);
				return ;
			};
			if(flag==true && va.indexOf(name)>-1){
				var str=va.replace(name,'');
				$("#ifSubmit").text(str);
				return ;
			}
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
		flag=validateFileSize(target,id,200);
		
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
		flag=validateFileSize(target,id,200);
		
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
            if (ext != 'jpg' && ext != 'png' && ext != 'jpeg' && ext != 'gif'){
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
	
	

	