$(document).ready(function (){
	getAllProvince()
	
})


function getAllProvince(){
	$("#provinceId option").remove();
	$("#provinceId").append('<option value="" >请选择</option> ');
	$("#cityId option").remove();
	 $("#cityId").append('<option value=""  > 请选择 </option> ');
	 $("#areaId option").remove();
	 $("#areaId").append('<option value=""  >请选择</option> ');
	 var _dataType = "text";
	 var _type = "POST";
	 var  _url = "../limit/getProvince";
	 var _async = true;
	 $.ajax({
		 dataType:_dataType,
		 type:_type,
		 url:_url,
		 async:_async,
		 success:function(res){
			 if(res==null||res==""){
				 return false;
			 }
			 var province=eval('('+res+')');
			 for(var i=0;i<province.length;i++){
				 $("#provinceId").append('<option value="'+province[i].provinceid+'">'+province[i].provincename+'</option>')
			 }
		 }
	 });
}

function getCity(){
	$("#cityId option").remove();
	$("#cityId").append('<option value=""  >请选择</option> ');
	 $("#areaId option").remove();
	 $("#areaId").append('<option value=""  >请选择</option> ');
	 var proId=$("#provinceId").val();
	 var _dataType="text";
	 var _type="POST";
	 var _url="../limit/getCity";
	 var _data="proId="+proId;
	 var _async=true;
	 $.ajax({
		 dataType:_dataType,
		 type:_type,
		 url:_url,
		 data:_data,
		 async:_async,
		 success:function(res){
			 if(res==null||res==""){
				 return false;
			 }
			 var city=eval('('+res+')');
			 for(var i=0;i<city.length;i++){
				 $("#cityId").append('<option value="'+city[i].cityid+'">'+city[i].cityname+'</option>')
			 }
			 
		 }
	 });
	
}


function getCounty(){
	 $("#areaId option").remove();
	 $("#areaId").append('<option value=""  >请选择</option> ');
	 var cityId=$("#cityId").val();
	 var _dataType="text";
	 var _type="POST";
	 var _url="../limit/getCounty";
	 var _data="cityId="+cityId;
	 var _async=true;
	 $.ajax({
		 dataType:_dataType,
		 type:_type,
		 url:_url,
		 data:_data,
		 async:_async,
		 success:function(res){
			 if(res==null||res==""){
				 return false;
			 }
			 var county=eval('('+res+')');
			 for(var i=0;i<county.length;i++){
				 $("#areaId").append('<option value="'+county[i].countyid+'">'+county[i].countyname+'</option>')
			 }
		 }
	 })
}

	