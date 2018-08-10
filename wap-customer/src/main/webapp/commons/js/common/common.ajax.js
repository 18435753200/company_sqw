$(function(){

	$("body").append("<div id='imgLoading'></div>");
	
    $(document).on("ajaxStart",function(){
    	$("#imgLoading").show();
    });
    
    $(document).on("ajaxStop",function(){
    	$("#imgLoading").hide();
    });
    
    $(document).on("ajaxSuccess",function(){
    	
    });
    
    $(document).on("ajaxError",function(){
    	alert("系统繁忙，请您稍候再试。");
    });
    
    /*$(".goPay-btn").on("click",function(){
    	var type = "get";
    	var dataType = "text";
    	var url = "/wap-customer/cart/update";
    	var data = "testStr=aaa";
    	
    	var result = ajaxRequest(type, dataType, url, data);
    	alert(result);
    	
    })*/
    
});

/**
 * 
 * @param type:get or post
 * @param dataType:text、json等等
 * @param url
 * @param data：request params
 */
function ajaxRequest(type,dataType,url,data){
	var result;
	$.ajax({
		type:type,
		data:data,
		url:url,
		async:true,
		dataType:dataType,
		success:function(obj){
			result = obj;
		}
	});
	return result;
}
