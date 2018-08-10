var  CONTEXTPATH  = $("#conPath").val();
function toReLoad(labelPage){
	var array = new Array();
	if(labelPage!=""&&labelPage!=null&&labelPage!=undefined){
		array.push("labelPage="+labelPage);
	}
    $("#labelPage").val(labelPage);
    $("#page").val(1);
	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/complaint_retailer/retailer", 
		data:array.join("&"),
		success : function(msg) { 
			$("#seelist").html(msg);
		},
		error:function(){
			alert("加载失败，稍后再试。");
		}

	});

}

function clickSubmit(page){

	var array_complaint = new Array();

	var  labelPage = checkLabel();

	var complaintBy = $("#complaintBy").val();
	var contactWay = $("#contactWay").val();
	var complaintType = $("#complaintType").val();
	var complaintLevel = $("#complaintLevel").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	
	if(complaintBy!=""){
		array_complaint.push("complaintBy="+complaintBy);	
	}
	if(contactWay!=""){
		array_complaint.push("contactWay="+contactWay);	
	}
	if(complaintType!=""){
		array_complaint.push("complaintType="+complaintType);
	}
	if(complaintLevel!=""){
		array_complaint.push("complaintLevel="+complaintLevel);
	}
	if(startTime!=""){
		array_complaint.push("startTime="+startTime);
	}
	if(endTime!=""){
		array_complaint.push("endTime="+endTime);
	}
	if(page!=""&&page!=undefined){
		array_complaint.push("page="+page);
	}
	array_complaint.push("labelPage="+labelPage);
    $("#labelPage").val(labelPage);
    $("#page").val(page);
	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/complaint_retailer/retailer", 
		data:array_complaint.join("&")+"&"+Math.random(),
		success : function(msg) { 
			$(".biaogee").html(msg);
		},
		error:function(){
			alert("加载失败，稍后再试。");
		}
	}); 
}


function downRetailerExcel(){

	var array_complaint = new Array();

	var  labelPage = checkLabel();

	var referenceName = $("#referenceName").val();
	var complaintBy = $("#complaintBy").val();
	var complaintType = $("#complaintType").val();
	var complaintLevel = $("#complaintLevel").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	
	if(referenceName!=""){
		
		array_complaint.push("referenceName="+referenceName);
		
	}
	if(complaintBy!=""){
		
		array_complaint.push("complaintBy="+complaintBy);
		
	}
	if(complaintType!=""){
		
		array_complaint.push("complaintType="+complaintType);
		
	}
	if(complaintLevel!=""){
		
		array_complaint.push("complaintLevel="+complaintLevel);
		
	}
	if(startTime!=""){
		
		array_complaint.push("startTime="+startTime);
		
	}
	if(endTime!=""){
		
		array_complaint.push("endTime="+endTime);
		
	}
	
	
	array_complaint.push("labelPage="+labelPage);
	
	array_complaint.push("meath="+Math.random());
	
	window.location.href = CONTEXTPATH+"/complaint_retailer/downRetailerExcel?"+array_complaint.join("&");
}

function toSubmitRply(){
	var textreply = $("#textreply").val();
	var complaintId = $("#complaintId").val();

	var array = new Array();

	if(textreply!=null&&textreply!=undefined&&textreply!=""){
		array.push("replyContent="+textreply);
	}
	if(complaintId!=null&&complaintId!=undefined&&complaintId!=""){
		array.push("complaintId="+complaintId);
	}
	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/complaint_retailer/replyComplaint", 
		data:array.join("&"),
		success : function(msg) { 
			if(msg==1){
				alert("保存成功");
				//window.location.href=CONTEXTPATH+"/complaint_retailer/retailer?labelPage=0";
                gotList();
			}else{
				alert("保存失败 稍后再试");
			}
		},
		error:function(){
			alert("加载失败，稍后再试。");
		}
	}); 
}

function retailer_getReplyComplaint_edit(complaintId){

	var array = new Array();

	if(complaintId!=null&&complaintId!=undefined&&complaintId!=""){
		array.push("complaintId="+complaintId);
	}

	array.push("statuForUse=1");
	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/complaint_retailer/getComplaint", 
		data:array.join("&"),
		success : function(msg) { 
			$(".c1").html(msg);
		},
		error:function(){
			alert("加载失败，稍后再试。");
		}
	}); 
}


function retailer_getReplyComplaint_show(complaintId){
	var array = new Array();


	if(complaintId!=null&&complaintId!=undefined&&complaintId!=""){
		array.push("complaintId="+complaintId);
	}

	array.push("statuForUse=0");
	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/complaint_retailer/getComplaint", 
		data:array.join("&"),
		success : function(msg) { 
			$(".c1").html(msg);
		},
		error:function(){
			alert("加载失败，稍后再试。");
		}
	}); 
}
function checkLabel(){
	var statu = '';

	var alreadylabel = $("#alreadylabel").attr("class");
	if(alreadylabel=='list'){
		statu = '2';
	}else{
		statu = '1';
	}
	return statu;
}
//1待处理 2已处理
function labelPage (lable){
	toReLoad(lable);
}
