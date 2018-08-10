var  CONTEXTPATH  = $("#conPath").val();

/*	商品js	*/
$(document).ready(function(){
	
	clickSubmit(1);
});

/*	查询条件	*/
//page  页数  
function clickSubmit(page){

	var title = $.trim($("#title").val());
	var sketich = $.trim($("#sketich").val());
	var status = $.trim($("#status").val());
	var stickStatus = $.trim($("#stickStatus").val());
	var activityType = $.trim($("#activityType").val());
	var createby = $.trim($("#createby").val());
	var createBeginTimeStr = $('#createBeginTimeStr').val();
	var createEndTimeStr = $('#createEndTimeStr').val();
	var updateby = $.trim($("#updateby").val());
	var updateBeginTimeStr = $('#updateBeginTimeStr').val();
	var updateEndTimeStr = $('#updateEndTimeStr').val();
	
	var pro_array  = new Array();

	if(title!=""){
		pro_array.push("title="+title);
	}
	if(sketich!=""){
		pro_array.push("sketich="+sketich);
	}
	pro_array.push("status="+status);
	pro_array.push("stickStatus="+stickStatus);
	pro_array.push("activityType="+activityType);
	if(createby!=""){
		pro_array.push("createby="+createby);
	}
	if(createBeginTimeStr!=""){
		pro_array.push("createBeginTimeStr="+createBeginTimeStr);
	}
	if(createEndTimeStr!=""){
		pro_array.push("createEndTimeStr="+createEndTimeStr);
	}
	if(page!=""&&page!=undefined){
		pro_array.push("page="+page);
	}
	if(updateby!=""){
		pro_array.push("updateby="+updateby);
	}
	if(updateBeginTimeStr!=""){
		pro_array.push("updateBeginTimeStr="+updateBeginTimeStr);
	}
	if(updateEndTimeStr!=""){
		pro_array.push("updateEndTimeStr="+updateEndTimeStr);
	}

	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/activitytop/getActivityTopicByConditions", 
		data:pro_array.join("&"),
		dataType:"html",
		success : function(msg) { 
			$("#c3").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 


}


function operateStatus(status,startStatus,topicId){
	
	$.dialog.confirm('确定执行此操作吗？', function(){
		var pro_array  = new Array();
		
		
		
		//置顶
		if(status == '1'){
			pro_array.push("stickStatus=1");
		}
		
		//启用
		if(status == '2'){
			pro_array.push("status="+startStatus);
		}
		
		//删除
		if(status == '3'){
			pro_array.push("delete=1");
		}
		
		pro_array.push("topicId="+topicId);
		
		$.ajax({
			type:'post',
			url:CONTEXTPATH+'/activitytop/updateActivityTopic',
			data: pro_array.join("&"),
			success:function(data){
				if(data=='1'){
					
					alert("操作成功!");
					clickSubmit(1);
				}else{
					alert("操作失败!");
				}

			}
		});
	});
	
}