var staticFile_s=$("#staticFile_s").val();
(function(win, doc) {
	// 浏览器缩放时
	win.onresize = function() {
		change();
	};
	change();
	function change() {
		var oFs = doc.documentElement.clientWidth / (320 / 20);
		// 设置根目录字体大小
		doc.documentElement.style.fontSize = oFs + 'px';
	}
})(window, document);
$(document).ready(function(){
	var e =$("#provinceId").val();
	var cid=$("#supCId").val();
	var couId= $("#countyId").val();
	var countyType =$("#countyType").val();
	var sectorId =$("#sectorId").val();
	var sectortype ;
	if(countyType==1){
		sectortype="企业";
	}else if(countyType==2){
		sectortype="个体工商户";
	}else if(countyType==3){
		sectortype="事业单位";
	}
	var bankCode = $("#bankcode").val();
	var provinceCode = $("#provinceCode").val();
	var cityCode = $("#cityCode").val();
	var bankBranchcode = $("#bankBranchcode").val();
	$.ajax({
		type : "POST",
		url : "/mallRegister/getCity",
		data : {provinceid:e},
		async : false,
		success : function(data) {
			$("#cityId").find("option").remove(); 
			$("#areaId").find("option").remove(); 
			$("#cityId").append('<option value="">请选择市</option>');
			$("#areaId").append('<option value="">请选择县</option>');
			for(var i=0;i<data.length;i++){
				if(data[i].cityid==cid){
					$("#cityId").append("<option value='"+data[i].cityid+"' selected>"+data[i].cityname+"</option>");
					var cityid =data[i].cityid;
					$.ajax({
						type : "POST",
						url : staticFile_s+"/mallRegister/getCountry",
						data : {cityid:cityid},
						async : false,
						success : function(count) {
							$("#areaId").find("option").remove(); 
							$("#areaId").append('<option value="">请选择县</option>');
							for(var i=0;i<count.length;i++){
								 if(count[i].countyid==couId){
									 $("#areaId").append('<option value="'+count[i].countyid+'" selected>' + count[i].countyname + '</option>');
								 }else{
									 $("#areaId").append('<option value="'+count[i].countyid+'">' + count[i].countyname + '</option>');
								 }
							}
						}
					})
				}else{
					$("#cityId").append("<option value='"+data[i].cityid+"'>"+data[i].cityname+"</option>");
				}
			}
	}
	})
	$.ajax({
		type : "POST",
		url : staticFile_s+"/mallRegister/getType",
		data : {sectortype:sectortype},
		async : true,
		success : function(e) {
			$("#companyBizCategory").find("option").remove(); 
			$("#companyBizCategory").append('<option value="">企业经营类目</option>');
			for(var i=0;i<e.length;i++){
				if(e[i].sectorcode==sectorId){
					$("#companyBizCategory").append('<option value="'+e[i].sectorcode+'" selected >' + e[i].sectorname + '</option>');
				}else{
					$("#companyBizCategory").append('<option value="'+e[i].sectorcode+'">' + e[i].sectorname + '</option>');
				}
		}
	}
	}) 
	$("#provincename").find("option").remove();
	$.ajax({
		type : "POST",
		url : staticFile_s+"/mallRegister/getBankProvinceList",
		data : {bankCode : bankCode},
		async : true,
		success : function(data) {
			$("#provincename").find("option").remove();
			$("#cityname").find("option").remove();
			$("#bankBranchname").find("option").remove();
			$("#provincename").append('<option value="" >请选择省份</option>');
			$("#cityname").append('<option  value="">请选择区域</option>');
			$("#bankBranchname").append('<option  value="">请选择支行</option>');
			for ( var key in data) {
				if(data[key]==provinceCode){
					$("#provincename").append('<option value="' + data[key] + '"selected>' + key+ '</option>');
					$.ajax({
						type : "POST",
						url : staticFile_s+"/mallRegister/getBankCityList",
						data : {bankCode : bankCode,bankProvinceId : provinceCode},
						async : true,
						success : function(provin) {
							$("#cityname").find("option").remove();
							$("#cityname").append('<option>请选择区域</option>');
							$("#bankBranchname").find("option").remove();
							$("#bankBranchname").append('<option>请选择支行</option>');
							for (var key in provin) {
								if(provin[key]==cityCode){
									$("#cityname").append('<option value="' + provin[key] + '"selected>' + key+ '</option>');
										$.ajax({
											type : "POST",
											url : staticFile_s+"/mallRegister/getSubBankOfCity",
											data : {bankCode : bankCode,bankAreaId : cityCode},
											async : true,
											success : function(bankBranArea) {
												$("#bankBranchname").find("option").remove();
												$("#bankBranchname").append('<option>请选择支行</option>');
												for (var i = 0; i < bankBranArea.length; i++) {
													if(bankBranArea[i].bankBranchCode==bankBranchcode){
														$("#bankBranchname").append('<option value="' + bankBranArea[i].bankBranchCode + ','+ bankBranArea[i].bankBranchName +'"selected>'+ bankBranArea[i].bankBranchName + '</option>');
													}else{
														$("#bankBranchname").append('<option value="' + bankBranArea[i].bankBranchCode + ','+ bankBranArea[i].bankBranchName +'">'+ bankBranArea[i].bankBranchName + '</option>');
													}
												}
											}
										})
								}else{
									$("#cityname").append('<option value="' + provin[key] + '">' + key+ '</option>');
								}
								
							}
						}
					})
				}else{
					$("#provincename").append('<option value="' + data[key] + '">' + key+ '</option>');

				}
			}
		}
	})
	
})
function ProvincesAndCitiesLinkage(s){
	var provinceid =s;
	$.ajax({
		type : "POST",
		url : staticFile_s+"/mallRegister/getCity",
		data : {provinceid:provinceid},
		async : false,
		success : function(data) {
			$("#cityId").find("option").remove(); 
			$("#areaId").find("option").remove(); 
			$("#cityId").append('<option value="">请选择市</option>');
			$("#areaId").append('<option value="">请选择县</option>');
			for(var i=0;i<data.length;i++){
				 $("#cityId").append('<option value="'+data[i].cityid+'">' + data[i].cityname + '</option>');
			}
	}
	})
}
function ProvincesAndCitie(s){
	var cityid =s;
	$.ajax({
		type : "POST",
		url : staticFile_s+"/mallRegister/getCountry",
		data : {cityid:cityid},
		async : false,
		success : function(data) {
			$("#areaId").find("option").remove(); 
			$("#areaId").append('<option value="">请选择县</option>');
			for(var i=0;i<data.length;i++){
				 $("#areaId").append('<option value="'+data[i].countyid+'">' + data[i].countyname + '</option>');
			}
	}
	})
}
function getType(s){
	var sectortype;
	if(s==1){
		sectortype="企业";
	}else if(s==2){
		sectortype="个体工商户";
	}else if(s==3){
		sectortype="事业单位";
	}
	$.ajax({
		type : "POST",
		url : staticFile_s+"/mallRegister/getType",
		data : {sectortype:sectortype},
		async : true,
		success : function(e) {
			$("#companyBizCategory").find("option").remove(); 
			$("#companyBizCategory").append('<option value="">企业经营类目</option>');
		for(var i=0;i<e.length;i++){
			 $("#companyBizCategory").append('<option value="'+e[i].sectorcode+'">' + e[i].sectorname + '</option>');
		}
	}
	}) 
}
// 选择银行获取省份
function getBankList(e) {
	var bankCode = e;
	if (e == "-1") {
		showError("请选择开户银行")
		return false;
	}
	$("#provincename").find("option").remove();
	$.ajax({
		type : "POST",
		url : staticFile_s+"/mallRegister/getBankProvinceList",
		data : {bankCode : bankCode},
		async : true,
		success : function(data) {
			$("#provincename").find("option").remove();
			$("#cityname").find("option").remove();
			$("#bankBranchname").find("option").remove();
			$("#provincename").append('<option value="" >请选择省份</option>');
			$("#cityname").append('<option  value="">请选择区域</option>');
			$("#bankBranchname").append('<option  value="">请选择支行</option>');
			for ( var key in data) {
				$("#provincename").append('<option value="' + data[key] + '">' + key+ '</option>');
			}
		}
	})
}
// 选择省份获取市区
function getBankCityList(e) {
	var bankCode = $("#accout option:selected").val();
	var bankProvinceId = e;
	$.ajax({
		type : "POST",
		url : staticFile_s+"/mallRegister/getBankCityList",
		data : {
			bankCode : bankCode,
			bankProvinceId : bankProvinceId
		},
		async : true,
		success : function(data) {
			$("#cityname").find("option").remove();
			$("#cityname").append('<option>请选择区域</option>');
			$("#bankBranchname").find("option").remove();
			$("#bankBranchname").append('<option>请选择支行</option>');
			for ( var key in data) {
				$("#cityname").append(
						'<option value="' + data[key] + '">' + key
								+ '</option>');
			}
		}
	})
}
// 选择市区获取支行信息
function getSubBankOfCity(e) {
	var bankCode = $("#accout option:selected").val();
	var bankAreaId = e;
	$.ajax({
		type : "POST",
		url : staticFile_s+"/mallRegister/getSubBankOfCity",
		data : {
			bankCode : bankCode,
			bankAreaId : bankAreaId
		},
		async : true,
		success : function(data) {
			$("#bankBranchname").find("option").remove();
			$("#bankBranchname").append('<option>请选择支行</option>');
			for (var i = 0; i < data.length; i++) {
				$("#bankBranchname").append(
						'<option value="' + data[i].bankBranchCode + ','+ data[i].bankBranchName +'">'
								+ data[i].bankBranchName + '</option>');
			}
		}
	})
}
function yyzzDiv() {
	$("#yyzz").click();
}
function yyzzImg(obj) {
	
	var file = obj.files[0];
	  
	var fileFormat = file.name.substring(file.name.lastIndexOf(".")).toLowerCase();
    if( !fileFormat.match(/.png|.jpg|.jpeg/) ) {  
    	showError('文件格式必须为：png/jpg/jpeg');  
        return false;    
    } 
    var reader = new FileReader();
	
	reader.onloadstart = function(e) {
		// console.log("开始读取....");
	}
	reader.onprogress = function(e) {
		// console.log("正在读取中....");
	}
	reader.onabort = function(e) {
		// console.log("中断读取....");
	}
	reader.onerror = function(e) {
		// console.log("读取异常....");
	}
	reader.onload = function(e) {
		 var data = e.target.result;  
		 var image = new Image();  
		 var img = document.getElementById("xmTanImg");
	     image.onload=function(){  
	        var width = image.width;  
	        var height = image.height;  
	       // console.log("成功读取....");
	  		var img64 =compress(image);
		    var formData = new FormData(); 
		    formData.append("businessLicense",convertBase64UrlToBlob(img64));
		    if(img64.length>(1024*1024)){
				showError("营业执照图片不能大于1M");
				return false;
			}
		  	$.ajax({
				type : "POST",
				url : staticFile_s+"/mallRegister/regisUploadFile",
				data : formData,
				processData:false,
	            contentType:false,
				success : function(data) {
					$("#yingyezhizhao").val(data);
					img.hidden="true"
						$("#yyzzdiv").children('#ycyyzz').remove();
						$("#yyzzdiv").children('#yyzzcx').remove();
					  	$("#yyzzdiv").append("<img  src='//image01.zhongjumall.com/"+data+"' style='height: 100%; width: 100%;' id='ycyyzz'  data-preview-src=''  data-preview-group='1'/>"
					  	+"<a id='sfzzmcx'><span class='mui-icon mui-icon-upload' style='position: relative;top: -4rem;font-size: 3rem;color: #fff;left:1.5rem'></span></a>");
				}
			})
			img.src = img64;
	      };  
	      image.src= data; 
	}
	reader.readAsDataURL(file);
}
/* 
 * 图片压缩
 * img    原始图片
 * width   压缩后的宽度
 * height  压缩后的高度
 * ratio   压缩比率 
 */
 function compress(img) {
    var initSize = img.src.length;
    var width = img.width;
    var height =img.height;
    var canvas = document.createElement('canvas');
    var ctx = canvas.getContext('2d');
    canvas.width = width;
    canvas.height = height;
    //如果图片大于四百万像素，计算压缩比并将大小压至400万以下
/*    var ratio;
    if ((ratio = width * height / 4000000)>1) {
        ratio = Math.sqrt(ratio);
        width /= ratio;
        height /= ratio;
    }else {
        ratio = 1;
    }
   

    //如果图片像素大于100万则使用瓦片绘制
    var count;
    if ((count = width * height / 1000000) > 1) {
        count = ~~(Math.sqrt(count)+1); //计算要分成多少块瓦片
//      计算每块瓦片的宽和高
        var nw = ~~(width / count);
        var nh = ~~(height / count);

        canvas.width = nw;
        canvas.height = nh;

        for (var i = 0; i < count; i++) {
            for (var j = 0; j < count; j++) {
                ctx.drawImage(img, i * nw * ratio, j * nh * ratio, nw * ratio, nh * ratio, 0, 0, nw, nh);

                ctx.drawImage(canvas, i * nw, j * nh, nw, nh);
            }
        }
    } else {
        ctx.drawImage(img, 0, 0, width, height);
    }*/
    
    ctx.drawImage(img, 0, 0, width, height)
    //进行最小压缩
    var ndata = canvas.toDataURL('image/jpeg',0.2);
   /* console.log('压缩前：' + initSize);
    console.log('压缩后：' + ndata.length);
    console.log('压缩率：' + ~~(100 * (initSize - ndata.length) / initSize) + "%");*/

    canvas.width = canvas.height = canvas.width = canvas.height = 0;

    return ndata;
}
/**  
 * 将以base64的图片url数据转换为Blob  
 * @param urlData  
 *            用url方式表示的base64图片数据  
 */  
function convertBase64UrlToBlob(urlData){
    var bytes=window.atob(urlData.split(',')[1]);        //去掉url的头，并转换为byte  
      
    //处理异常,将ascii码小于0的转换为大于0  
    var ab = new ArrayBuffer(bytes.length);  
    var ia = new Uint8Array(ab);  
    for (var i = 0; i < bytes.length; i++) {  
        ia[i] = bytes.charCodeAt(i);  
    }  
  
    return new Blob( [ab] , {type : 'image/png'});  
}  
function mpDiv() {
	$("#mp").click();
}
function mpImages(obj) {
	var file = obj.files[0];
	var fileFormat = file.name.substring(file.name.lastIndexOf(".")).toLowerCase();
    if( !fileFormat.match(/.png|.jpg|.jpeg/) ) {  
    	showError('文件格式必须为：png/jpg/jpeg');  
        return false;    
    } 
	var reader = new FileReader();
	reader.onloadstart = function(e) {
	}
	reader.onprogress = function(e) {
	}
	reader.onabort = function(e) {
	}
	reader.onerror = function(e) {
	}
	reader.onload = function(e) {
		 var data = e.target.result;  
		 var image = new Image();  
		
	     image.onload=function(){  
	        var width = image.width;  
	        var height = image.height; 
	  		var img64 =compress(image);
	  		var img = document.getElementById("mpImg");
	  		var formData = new FormData(); 
		    formData.append("companyStoreLogo",convertBase64UrlToBlob(img64));
		    if(img64.length>(1024*1024)){
				showError("门头照片不能大于1M");
				return false;
			}
		  	$.ajax({
				type : "POST",
				url : staticFile_s+"/mallRegister/regisUploadFile",
				data : formData,
				processData:false,
	            contentType:false,
				success : function(data) {
					$("#mentouzhaopian").val(data);
					img.hidden="true"
					$("#mpdiv").children('#ycmtzp').remove();
					$("#mpdiv").children('#mtzbcx').remove();
					$("#mpdiv").append("<img  src='//image01.zhongjumall.com/"+data+"' style='height: 100%; width: 100%;' id='ycmtzp'  data-preview-src=''  data-preview-group='1'/>"
					+"<a id='sfzzmcx'><span class='mui-icon mui-icon-upload' style='position: relative;top: -4rem;font-size: 3rem;color: #fff;left:1.5rem'></span></a>");
				}
			})
			img.src = img64;
	      };  
	      image.src= data;  
	}
	reader.readAsDataURL(file)
}

function yykzmz() {
	$("#bankAccountPic").click();
}
function yykzmzImages(obj) {
	var file = obj.files[0];
	var fileFormat = file.name.substring(file.name.lastIndexOf(".")).toLowerCase();
	if( !fileFormat.match(/.png|.jpg|.jpeg/) ) {  
		showError('文件格式必须为：png/jpg/jpeg');  
		return false;    
	} 
	var reader = new FileReader();
	reader.onloadstart = function(e) {
	}
	reader.onprogress = function(e) {
	}
	reader.onabort = function(e) {
	}
	reader.onerror = function(e) {
	}
	reader.onload = function(e) {
		var data = e.target.result;  
		var image = new Image();  
		image.onload=function(){  
			var width = image.width;  
			var height = image.height;  
			// console.log("成功读取....");
			var img = document.getElementById("yykzmzImg");
			var img64 =compress(image);
			var formData = new FormData(); 
			formData.append("bankAccountPic",convertBase64UrlToBlob(img64));
			if(img64.length>(1024*1024)){
				showError("银行卡照片不能大于1M");
				return false;
			}
			img.src = img64;
			$.ajax({
				type : "POST",
				url : staticFile_s+"/mallRegister/regisUploadFile",
				data : formData,
				processData:false,
				contentType:false,
				success : function(data) {
					$("#yinhangkazhaopian").val(data);
					img.hidden="true"
					$("#yykzmz").children('#yykzb').remove();
					$("#yykzmz").children('#yykzp').remove();
					$("#yykzmz").append("<img  src='//image01.zhongjumall.com/"+data+"' style='height: 100%; width: 100%;' id='yykzb'  data-preview-src=''  data-preview-group='1'/>"
							+"<a id='yykzp'><b class='chxps'>重新拍摄</b></a>");
				}
			})
			
		};  
		image.src= data;  
	}
	reader.readAsDataURL(file)
}
function zhengMianDiv() {
	$("#img_z").click();
}
function zmImg(obj) {
	var file = obj.files[0];
	var fileFormat = file.name.substring(file.name.lastIndexOf(".")).toLowerCase();
    if( !fileFormat.match(/.png|.jpg|.jpeg/) ) {  
    	showError('文件格式必须为：png/jpg/jpeg');  
        return false;    
    } 
	var reader = new FileReader();
	reader.onloadstart = function(e) {
	}
	reader.onprogress = function(e) {
	}
	reader.onabort = function(e) {
	}
	reader.onerror = function(e) {
	}
	reader.onload = function(e) {
		 var data = e.target.result;  
		 var image = new Image();  
		 var img = document.getElementById("zmz");
	     image.onload=function(){
	        var width = image.width;  
	        var height = image.height;  
	       // console.log("成功读取....");
	  		var img64 =compress(image);
	  		var formData = new FormData(); 
		    formData.append("idCardFront",convertBase64UrlToBlob(img64));
		    if(img64.length>(1024*1024)){
				showError("身份证正面图片不能大于1M");
				return false;
			}
		  	$.ajax({
				type : "POST",
				url : staticFile_s+"/mallRegister/regisUploadFile",
				data : formData,
				processData:false,
	            contentType:false,
				success : function(data) {
					$("#shenfenzhengzhengmian").val(data);
					img.hidden="true"
					$("#sfzzmz").children('#ycsfzzm').remove();
					$("#sfzzmz").children('#sfzzmcx').remove();
					$("#sfzzmz").append("<img  src='//image01.zhongjumall.com/"+data+"' style='height: 100%; width: 100%;' id='ycsfzzm'  data-preview-src=''  data-preview-group='1'/>"
					+"<a id='sfzzmcx'><span class='mui-icon mui-icon-upload' style='position: relative;top: -4rem;font-size: 3rem;color: #fff;'></span></a>");
				}
			})
			img.src = img64;
	      };  
	      image.src= data;  
	}
	reader.readAsDataURL(file)
}
function fanmianDiv() {
	$("#img_f").click();
}
function fmImg(obj) {
	var file = obj.files[0];
	var fileFormat = file.name.substring(file.name.lastIndexOf(".")).toLowerCase();
    if( !fileFormat.match(/.png|.jpg|.jpeg/) ) {  
    	showError('文件格式必须为：png/jpg/jpeg');  
        return false;    
    } 
	var reader = new FileReader();
	reader.onloadstart = function(e) {
	}
	reader.onprogress = function(e) {
	}
	reader.onabort = function(e) {
	}
	reader.onerror = function(e) {
	}
	reader.onload = function(e) {
		 var data = e.target.result;  
		 var image = new Image();  
	     image.onload=function(){  
	        var width = image.width;  
	        var height = image.height;  
	       // console.log("成功读取....");
	  		var img = document.getElementById("fmz");
	  		var img64 =compress(image);
	  		var formData = new FormData(); 
		    formData.append("idCardVerso",convertBase64UrlToBlob(img64));
		    if(img64.length>(1024*1024)){
				showError("身份证反面图片不能大于1M");
				return false;
			}
		  	$.ajax({
				type : "POST",
				url : staticFile_s+"/mallRegister/regisUploadFile",
				data : formData,
				processData:false,
	            contentType:false,
				success : function(data) {
					$("#shenfenzhengfanmian").val(data);
					img.hidden="true"
					$("#sfzfmz").children('#ycsfzfm').remove();
					$("#sfzfmz").children('#sfcfmcx').remove();
					$("#sfzfmz").append("<img  src='//image01.zhongjumall.com/"+data+"' style='height: 100%; width: 100%;' id='ycsfzfm'  data-preview-src=''  data-preview-group='1'/>"
					+"<a id='sfzzmcx'><span class='mui-icon mui-icon-upload' style='position: relative;top: -4rem;font-size: 3rem;color: #fff;'></span></a>");
				}
			})
			img.src = img64;
	      };  
	      image.src= data;  
	}
	reader.readAsDataURL(file)
}
function showError(str) {
	jQuery.dialog({
		content : str,
		title : '众聚猫提示',
		time : 3000
	});
}
function showInfo(str) {
	jQuery.dialog({
		content : str,
		title : '众聚猫提示',
		time : 300000
	});
}
function submitRegister() {
	var rcodetxt =$("#rcodetxt").val();//MP的SupplierId
	var name = $("#name").val();// 注册名称
	var nameJc = $("#nameJc").val();// 营业名称
	var registerAddress = $("#registerAddress").val();// 注册地址
	var provinceId = $("#provinceId").val();// 省份
	var cityId = $("#cityId").val();// 市
	var areaId = $("#areaId").val();// 区域
	var address = $("#address").val();// 营业地址
	var businessLicenseno = $("#businessLicenseno").val();// 营业执照编码
	var companyBizType = $("#companyBizType").val();// 企业类型
	var companyBizCategory = $("#companyBizCategory").val();// 企业经营类目
	var legalPerson = $("#legalPerson").val();// 法定代表人
	var legalPersonCardno = $("#legalPersonCardno").val();// 法定身份证
	var legalPersonPhone = $("#legalPersonPhone").val();// 法定电话
	var email = $("#email").val();// 邮件
	var contact = $("#contact").val();// 业务联系人
	var contactTel = $("#contactTel").val();// 联系电话
	var phone = $("#phone").val();// 联系手机
	var accoutName = $("#accoutName").val();// 银行账户名称
	var bankBranchname = $("#bankBranchname").val();// 开户银行支行
	var accoutNo = $("#accoutNo").val();// 银行帐号
	
	if (name == '' || name == "undefined") {
		showError("商家注册名称不能为空");
		return false;
	}
	if (nameJc == '' || nameJc == "undefined") {
		showError("商家营业名称不能为空");
		return false;
	}
	if (registerAddress == '' || registerAddress == "undefined") {
		showError("商家注册地址不能为空");
		return false;
	}
	if (provinceId == '' || provinceId == "undefined") {
		showError("请选择省市区");
		return false;
	}
	if (cityId == '' || cityId == "undefined") {
		showError("请选择省市区");
		return false;
	}
	if (areaId == '' || areaId == "undefined") {
		showError("请选择省市区");
		return false;
	}
	if (address == '' || address == "undefined") {
		showError("商家营业地址不能为空");
		return false;
	}
	if (businessLicenseno == '' || businessLicenseno == "undefined") {
		showError("商家营业编码不能为空");
		return false;
	}
	if (companyBizType == '' || companyBizType == "undefined") {
		showError("请选择企业类型");
		return false;
	}
	if (companyBizCategory == '' || companyBizCategory == "undefined") {
		showError("请选择企业经营类目");
		return false;
	}
	if (legalPerson == '' || legalPerson == "undefined") {
		showError("法定代表人不能为空");
		return false;
	}
	if (legalPersonCardno == '' || legalPersonCardno == "undefined") {
		showError("身份证号码不能为空");
		return false;
	}
	if (legalPersonPhone == '' || legalPersonPhone == "undefined") {
		showError("法人联系电话不能为空");
		return false;
	}
	if (email == '' || email == "undefined") {
		showError("电子邮件不能为空");
		return false;
	}
	if (contact == '' || contact == "undefined") {
		showError("商家联系人不能为空");
		return false;
	}
	if (contactTel == '' || contactTel == "undefined") {
		showError("商家联系电话不能为空");
		return false;
	}
	if (phone == '' || phone == "undefined") {
		showError("商家联系手机不能为空");
		return false;
	}
	if (accoutName == '' || accoutName == "undefined") {
		showError("银行账户名称不能为空");
		return false;
	}
	if (bankBranchname == '' || bankBranchname == "undefined") {
		showError("开户银行不能为空");
		return false;
	}
	if (accoutNo == '' || accoutNo == "undefined") {
		showError("银行帐号不能为空");
		return false;
	}
	var reg = new RegExp(
			"^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); // 正则表达式
	if (email === "") { // 输入不能为空
		showError("输入不能为空!");
		return false;
	} else if (!reg.test(email)) { // 正则验证不通过，格式不对
		showError("邮箱格式错误");
		return false;
	}
	var ctl = /^[0-9]*$/;
	if (!ctl.test(contactTel)) {
		showError("联系电话格式不正确");
		return false
	}
	var res = verifyPhoneNumber(phone);
	if (res != "ok") {
		showError(res);
		return false;
	}
	var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	if (!reg.test(legalPersonCardno)) {
		showError("身份证格式不正确");
		return false
	}
	 sendForm();
}

function sendForm() {
	
	var businessLicenseImg = document.getElementById("xmTanImg");
	var companyStoreLogoImg = document.getElementById("mpImg");
	var idCardFrontImg = document.getElementById("zmz");
	var idCardVersoImg = document.getElementById("fmz");
	var bankAccountPic = document.getElementById("bankAccountPic");
	var form=document.forms[0];  
    
	var formData = new FormData(form); 
	
	if(businessLicenseImg!=null&&businessLicenseImg!=''&&businessLicenseImg!='undefined'){
		if(businessLicenseImg.src.length>(1024*1024)){
			showError("营业执照图片不能大于1M");
			return false;
		}
	}
	if(companyStoreLogoImg!=null&&companyStoreLogoImg!=''&&companyStoreLogoImg!='undefined'){
		if(companyStoreLogoImg.src.length>(1024*1024)){
			showError("门头照图片不能大于1M");
			return false;
		}
	}
	if(idCardFrontImg!=null&&idCardFrontImg!=''&&idCardFrontImg!='undefined'){
		if(idCardFrontImg.src.length>(1024*1024)){
			showError("身份证正面图片不能大于1M");
			return false;
		}
	}
	if(idCardVersoImg!=null&&idCardVersoImg!=''&&idCardVersoImg!='undefined'){
		if(idCardVersoImg.src.length>(1024*1024)){
			showError("身份证反面图片不能大于1M");
			return false;
		}
	}
	if(bankAccountPic!=null&&bankAccountPic!=''&&bankAccountPic!='undefined'){
		if(bankAccountPic.src.length>(1024*1024)){
			showError("银行卡照片不能大于1M");
			return false;
		}
	}
	
	 
	  var oReq = new XMLHttpRequest();
	  oReq.open("POST", form.action, true);
	  oReq.onload = function(oEvent) {
	    if (oReq.status == 200) {
	    	window.location.href = "https://m.zhongjumall.com/customer/toMy";
	    }else{
	    	window.location.href="/mallRegister/toFailure";
	    }
	  };
	  oReq.send(formData);
	  showInfo("正在提交");
	}