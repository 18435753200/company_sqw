var path ;
$(function(){
	path = $("#path").val();
});
function getVerificationCode(){
		//正则验证手机号
		var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
		if(!myreg.test($("#mobile").val())) { 
                        dialogStr('请输入有效的手机号码');
			return; 
		}
          /*     var captcha = $("#captcha").val()
               if(captcha == null || captcha == ""){
                       dialogStr('请输入有效验证码');
			return; 
                }*/
		$(".fb-banner").css('display', '');
		$(".fb-banner2").css('display', 'none');
	    //获取验证码
	    var mobile=$("#mobile").val();
		var _dataType = "jsonp";
		var _type = "get";
		var _url =path+"/customer/getIPTVRegistCodeJsonp";
		$.ajax({
			dataType : _dataType,
			type : _type,
			url : _url,
			jsonp: 'jsoncallbackUCcode',
			data : {
				"mobile":mobile//,"captcha":captcha 
			},
			success : function(json) {
			}
		});	

	}
    //发送验证码end

    //获取验证码返回事件
    function jsoncallbackUCcode(result){   
    	result = "success";
		 if(result == "captchaerror"){
             dialogStr('验证码有误请重新输入');
             $("#captchaImage").attr("src",path+"/customer/getImageRegist?date="+new Date());
         }else if (result == "success") {
			time=60;
			$("codeText").text("重新获取(60)秒");
			callbackCode();
		}
    }  


    //重新发送
	function callbackCode() {
		time = time - 1;
		$("#codeLink").removeAttr("onclick");
		$("#codeLink").text("重新发送(" + time +")秒" );
		if (time < 1) {
			$("#codeLink").attr("onclick", "getVerificationCode()");
			$("#codeLink").text("重新发送");
                        window.clearTimeout(callbackCode);
			return;
		}
              window.setTimeout(callbackCode, 1000); 
	}
	//验证码 END



	function registerCoud(){
		var mobile=$("#mobile").val();
		var code=$("#code").val();
		if(code==null||code==""||mobile==null||mobile==""){
                       dialogStr("信息不能为空");
			return false;
		}



		$.ajax({
			dataType:"text",
			url : path+"/customerActivity/redPacketActivityPC",
			type : "get",
			data : {
					"mobile":mobile,"code":code
			},
			success : function(result) {
				if(result=="-500"){
					dialogStr('验证码错误');
				}else if(result=="0"){
					//dialogStr('红包已抢光');
					$("#box-1").hide();
					$("#box-4").show();
				}else if(result=="-2"){
					//dialogStr('今天已领过，明天再来');
					$("#box-1").hide();
					$("#box-3").show();
				}else{
					$("#box-1").hide();
					//dialogStr('抢到红包了');
					$("#box-2").show();
				}
			}
		});

	}


	function dialogStr(str){
		$.dialog({
			content : str,
			title : '众聚猫提示',
			time: 3000,
		});
	}
