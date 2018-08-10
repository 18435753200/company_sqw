//门头照片图片上传
function topPhotos(){
	$("#topPhoto").click();
}
function updatePhoto(obj) {
	
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
		 var img = document.getElementById("shopTop");
	     image.onload=function(){  
	        var width = image.width;  
	        var height = image.height;  
	  		var img64 =compress(image);
		    var formData = new FormData(); 
		    formData.append("companyStoreLogo",convertBase64UrlToBlob(img64));
		    if(img64.length>(1024*1024)){
				showError("图片不能大于1M");
				return false;
			}
		  	$.ajax({
				type : "POST",
				url : "/supplier/regisUploadFile",
				data : formData,
				processData:false,
	            contentType:false,
				success : function() {
				}
			})
			img.src = img64;
	      };  
	      image.src= data; 
	}
	reader.readAsDataURL(file);
}
function aa(){
	var numUp = document.getElementsByClassName("aui-up-section").length;
    var ht = document.getElementsByClassName("lazy_img").length;
    var totalNum = numUp +ht+1;
    if (totalNum > 6) {
    	showError("你好！上传图片不得超过6张");
        return false;
    }
	$("#file").click();
}
//经营环境图片上传
function photo(obj) {
	
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
		 var img = document.getElementById("imgId");
	     image.onload=function(){  
	        var width = image.width;  
	        var height = image.height;  
	  		var img64 =compress(image);
		    var formData = new FormData(); 
		    formData.append("companyStoreLogo",convertBase64UrlToBlob(img64));
		    if(img64.length>(1024*1024)){
				showError("图片不能大于1M");
				return false;
			}
		    $.ajax({
				type : "POST",
				url : "/supplier/uploadPhone",
				data : formData,
				processData:false,
	            contentType:false,
				success : function(result) {
					if(result.code==0){
						window.location.href="/supplier/toMyStore";
					}
				}
			})
			img.src = img64;
	      };  
	      image.src= data; 
	}
	reader.readAsDataURL(file);
	return file;
}
function bb(){
	var numUp = document.getElementsByClassName("qcwd_mdtj").length;
	if(numUp>=8){
		showError("你好！上传图片不得超过8张");
		 return false;
	}
	$("#input-file-french-2").click();
}
function photoUp(obj){
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
		 var img = document.getElementById("photoThis");
	     image.onload=function(){  
	        var width = image.width;  
	        var height = image.height;  
	  		var img64 =compress(image);
		    var formData = new FormData(); 
		    formData.append("companyStoreLogo",convertBase64UrlToBlob(img64));
		    formData.append("attrType",2);
		    if(img64.length>(1024*1024)){
				showError("图片不能大于1M");
				return false;
			}
		  	$.ajax({
				type : "POST",
				url : "/supplier/uploadPhone",
				data : formData,
				processData:false,
	            contentType:false,
				success : function(result) {
					if(result.code==0){
						window.location.href="/supplier/toMyStore";
					}
				}
			})
			img.src = img64;
	      };  
	      image.src= data; 
	}
	reader.readAsDataURL(file);
}
function remo(span,id){

	$.ajax({
		type:"POST",
		data:{id:id},
		url:"/supplier/deletePhoto",
		success:function(e){
			showError(e.message);
			span.parentNode.remove();
			window.location.href="/supplier/toMyStore";
		}
	})
}
function showError(str) {
	jQuery.dialog({
		content : str,
		title : '众聚猫提示',
		time : 1500
	});
}
function updateForm(select){
	window.location.href="/supplier/updateMdxx?select="+select;
}
function updateData(){
	var form=document.forms[0];
	var provinceId = $("#provinceId").val();
	var cityId =$("#cityId").val();
	var areaId =$("#areaId").val();
	if(cityId==null||""==cityId||areaId==null&&""==areaId){
		showError("请选择省市区");
		return false;
	}
	$.ajax({
		type:"POST",
		data:$("#form1").serialize(),
		url:form.action,
		processData:false,
		success:function(e){
			if(e.code==0){
				var redirectUrl ="/supplier/toMyStore";
				okBT("修改成功", redirectUrl);
				return;
			}else{
				showError(e.message);
				return false;
			}
		}
	})
}
function okBT(content, redirectUrl) {
	$.dialog({
		content : content,
		title : '众聚猫提示',
		ok : function() {
			if (!isEmpty(redirectUrl)) {
				window.location.href = redirectUrl;
			} else {
			}
		},
		lock : true
	});
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
    var ratio;
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
    }
    
    ctx.drawImage(img, 0, 0, width, height)
    //进行最小压缩
    var ndata = canvas.toDataURL('image/jpeg',100);
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
function ProvincesAndCitiesLinkage(s){
	var provinceid =s;
	$.ajax({
		type : "POST",
		url : "/mallRegister/getCity",
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
		url : "/mallRegister/getCountry",
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