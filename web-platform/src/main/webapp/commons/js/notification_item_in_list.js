var  CONTEXTPATH  = $("#conPath").val();
$(document).ready(function(){
	clickSubmit();
}); 

function clickSubmit(page){
	var fm_data = $("#notificationorder_fm").serialize();
	
	$.ajax({
		 type : "post", 
    	 url : CONTEXTPATH+"/commoditystore/getNotificationOrderPageBean", 
    	 data:fm_data+"&random="+Math.random(),
    	 dataType:"html",
        success : function(msg){
			$("#modelNotificationItemList").html(msg);
		},
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("查询失败 ，请稍后再试。。");
	     }
	});
}
