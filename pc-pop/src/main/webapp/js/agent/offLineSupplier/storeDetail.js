
$(function(){
	//显示经营环境替换按钮
	$(".mdhjimg").hover(
			  function () {
				  $(this).find(".showUpdate").stop().slideDown("fast");
			  },
			  function () {
				  $(this).find(".showUpdate").stop().slideUp("fast");
			  }
	);
	//显示门店推荐替换按钮
	$(".mdtjimg").hover(
			function () {
				$(this).find(".showUpdate").stop().slideDown("fast");
			},
			function () {
				$(this).find(".showUpdate").stop().slideUp("fast");
			}
	);
	
	
	

});

//触发上传图片按钮
function upLoadPic(target){
	$(target).parent().find("input").click();
}



//更改门头照片
function updateStoreLogo(obj) {
	var path=$("#path").val();
	var file = obj.files[0];
	var fileFormat = file.name.substring(file.name.lastIndexOf(".")).toLowerCase();
    if( !fileFormat.match(/.png|.jpg|.jpeg/) ) {  
    	alert('文件格式必须为：png/jpg/jpeg');  
        return false;    
    } 
    var flag=validateFileSize(obj,1024);
    if(!flag){
    	return false;
    }
    
    var formData = new FormData(); 
    formData.append("companyStoreLogo",file);
    formData.append("supplierId",$("#supplierId").val());
    $.ajax({
		type : "POST",
		url : path+"/supplier/updateStoreDetailPic",
		data : formData,
		processData:false,
        contentType:false,
		success : function(result) {
			document.getElementById("storeLogo").src="//image01.zhongjumall.com/d1/"+result;
		}
	});
  
}

//上传/更改门店推荐照片
function updateMdtjLogo(obj,num) {
	var path=$("#path").val();
	var file = obj.files[0];
	var fileFormat = file.name.substring(file.name.lastIndexOf(".")).toLowerCase();
    if( !fileFormat.match(/.png|.jpg|.jpeg/) ) {  
    	alert('文件格式必须为：png/jpg/jpeg');  
        return false;    
    } 
    var flag=validateFileSize(obj,1024);
    if(!flag){
    	return false;
    }
    
    var formData = new FormData(); 
    formData.append("attrValue",file);
    if(num==0 || num=='0'){
    	formData.append("attrType",2);
    	formData.append("storeId",$("#storeId").val());
    }else{
    	formData.append("detailId",num);
    }
    $.ajax({
		type : "POST",
		url : path+"/supplier/updateDetailAttrPic",
		data : formData,
		processData:false,
        contentType:false,
		success : function(result) {
			if(result==0 || result=='0'){
				alert("图片上传数量超出限制!");
			}
			var arr=result.split("---");
			$(obj).parent().next().attr("src","//image01.zhongjumall.com/d1/"+arr[0]);
			$(obj).attr("onchange","updateMdtjLogo(this,"+arr[1]+")");
		}
	});
  
}



//上传/更改经营环境照片
function updateJyhjLogo(obj,num) {
	var path=$("#path").val();
	var file = obj.files[0];
	var fileFormat = file.name.substring(file.name.lastIndexOf(".")).toLowerCase();
    if( !fileFormat.match(/.png|.jpg|.jpeg/) ) {  
    	alert('文件格式必须为：png/jpg/jpeg');  
        return false;    
    } 
    var flag=validateFileSize(obj,1024);
    if(!flag){
    	return false;
    }
    
    var formData = new FormData(); 
    formData.append("attrValue",file);
    if(num==0 || num=='0'){
    	formData.append("attrType",1);
    	formData.append("storeId",$("#storeId").val());
    }else{
    	formData.append("detailId",num);
    }
    $.ajax({
		type : "POST",
		url : path+"/supplier/updateDetailAttrPic",
		data : formData,
		processData:false,
        contentType:false,
		success : function(result) {
			if(result==0 || result=='0'){
				alert("图片上传数量超出限制!");
			}
			var arr=result.split("---");
			$(obj).parent().next().attr("src","//image01.zhongjumall.com/d1/"+arr[0]);
			$(obj).attr("onchange","updateJyhjLogo(this,"+arr[1]+")");
		}
	});
  
}

//修改按钮触发事件
function changeDetailText(){
	
	$("#nameJC").removeAttr("readonly");
	$("#contactTel").removeAttr("readonly");
	$("#jyTS").removeAttr("readonly");
	$("#jySJ").removeAttr("readonly");
	$("#nameJC").removeAttr("disabled");
	$("#contactTel").removeAttr("disabled");
	$("#jyTS").removeAttr("disabled");
	$("#jySJ").removeAttr("disabled");
	$("#detailUpdate").toggle();
	$("#detailSave").toggle();
	
	
}

//保存按钮触发事件
function updateTextSubmit(){
	
	var form = document.getElementById("updateText");
	var formData = new FormData(form); 
	formData.append("supplierId",$("#supplierId").val()+"");
	 $.ajax({
			type : "POST",
			url : $("#path").val()+"/supplier/updateStoreDetailText",
			data : formData,
			processData:false,
	        contentType:false,
			success : function(result) {
				alert("修改成功");
				$("#nameJC").attr("readonly","readonly");
				$("#contactTel").attr("readonly","readonly");
				$("#jyTS").attr("readonly","readonly");
				$("#jySJ").attr("readonly","readonly");
				$("#nameJC").attr("disabled","disabled");
				$("#contactTel").attr("disabled","disabled");
				$("#jyTS").attr("disabled","disabled");
				$("#jySJ").attr("disabled","disabled");
				$("#detailUpdate").toggle();
				$("#detailSave").toggle();
			}
		});
	 
}


//文件大小验证
function validateFileSize(target,endSize){
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
		 alert("文件大小不能超过1M");
		 target.value='';
		return false;
	 }
	 return true;
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
