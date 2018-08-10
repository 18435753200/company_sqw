
//根据条件检索相应的装箱单信息.
function getLoadPlanListByCondition(page){
	var planId = $.trim($("#planId").val());
	var startTime = $.trim($("#startTime").val());
	var endTime = $.trim($("#endTime").val());
	var createBy = $.trim($("#createBy").val());
	var loadCode = $("#loadCode").val();
	var planLoad_Array = new Array();
	if(planId != ""){
		planLoad_Array.push("planId="+planId);
	}
	if(endTime != ""){
		planLoad_Array.push("endTime="+endTime);
	}
	if(startTime != ""){
		planLoad_Array.push("startTime="+startTime);
	}
	if(createBy != ""){
		planLoad_Array.push("createBy="+createBy);
	}
	if(loadCode != ""){
		planLoad_Array.push("loadCode="+loadCode);
	}
	planLoad_Array.push("page="+page);
	$.ajax({ 
	     async:false,
	     type : "post", 
	     url : "../containerLoadPlan/getCLPOrderPageBeanByCondition", 
	     data:planLoad_Array.join("&"),
	     dataType:"html",
	     success : function(loadPlan) {
	     	$(".goodsWrap").html(loadPlan);
	     },
		 error:function(jqXHR,textStatus,errorThrown){
			alert("网络异常,请稍后再试。。。。");
		 }
	}); 
}

//删除没有确认的装箱单
function goDelLoadPlan(planId){
	$.dialog.confirm('确定要删除此装箱单?',function(){
		$.ajax({ 
		     async:false,
		     type : "post", 
		     url : "../containerLoadPlan/goDelLoadPlanById", 
		     data: "planId="+planId,
		     dataType:"html",
		     success : function(resultMsg) {
		    	 tipMessage(resultMsg,function(){
		    		 window.location.reload();
				 });
		     },
			  error:function(jqXHR,textStatus,errorThrown){
			  	alert("网络异常,请稍后再试。。。。");
			  }
		 }); 
	},function(){}); 
}



//海外人确认装箱订单
function goConfirmLoadPlan(planId){
	$.dialog.confirm('确定要做此操作,操作完成无法再操作此单?',function(){
		$.ajax({ 
		     async:false,
		     type : "post", 
		     url : "../containerLoadPlan/goConfirmLoadPlanByPlanId", 
		     data: "planId="+planId,
		     dataType:"html",
		     success : function(resultMsg) {
		    	 tipMessage(resultMsg,function(){
		    		 window.location.reload();
				 });
		     },
			  error:function(jqXHR,textStatus,errorThrown){
			  	alert("网络异常,请稍后再试。。。。");
			  }
		 }); 
	},function(){}); 
}




function exportSheet(){
	var checked = $(".check:checked");
	var planLoad_Array = new Array();
	if(!checked.length>0){
		alert("请先选择要导出的装箱单!");
	}else{
		$.each(checked,function(i,item){
			var palnId = $(item).val();
			planLoad_Array.push("planId="+palnId);
		});
	    window.location.href="../containerLoadPlan/exportLoadPlanListByPlanIds?"+planLoad_Array.join("&")+""; 
	}	
}





