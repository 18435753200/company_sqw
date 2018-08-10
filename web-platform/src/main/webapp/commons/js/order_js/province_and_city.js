//获取所有城市
function getProvince(obj){
	$.ajax({ 
	    type : "post", 
	    url : '../baseData/findAllProvince', 
	    dataType:"json",
	    success : function(json) { 
	        $.each(eval(json),function(i,v){
	        	obj.append("<option value='"+v.provinceid+"'>"+v.provincename+"</option>"); 
			});
		}
	});
}


//根据省份ID获取市
function getCity(obj,obj1){
	var provinceId = obj.val();
	obj1.empty();
	obj1.append("<option value='0'>请选择</option>"); 
	$.ajax({
		 type : "post", 
     	 url :  '../baseData/findCitiesByProvinceId', 
     	 data:"provinceId="+provinceId,
     	 dataType:"json", 
         success : function(json) { 
			$.each(json,function(i,n){
				obj1.append("<option value='"+n.cityid+"'>"+n.cityname+"</option>"); 
			});	
	     }
    });
}


//根据城市ID获取市
function getCounty(obj,obj1){ 
	var cityId = obj.val();
	obj1.empty();
	obj1.append("<option value='0'>请选择</option>"); 
	$.ajax({
		 type : "post", 
     	 url :  '../baseData/findCountiesByCityId', 
     	 data:"cityId="+cityId,
     	 dataType:"json", 
         success : function(json) { 
			$.each(json,function(i,n){
				obj1.append("<option value='"+n.countyid+"'>"+n.countyname+"</option>"); 
			});	
	     }
	}); 
}



