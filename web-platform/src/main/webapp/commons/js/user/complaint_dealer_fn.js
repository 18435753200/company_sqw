var  CONTEXTPATH  = $("#conPath").val();
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
		url : "../complaint_dealer/replyComplaint", 
		data:array.join("&"),
		success : function(msg) { 
			if(msg==1){
				alert("保存成功");
				window.location.href=CONTEXTPATH+"/complaint_dealer/dealer?labelPage=0";
			}else{
				alert("保存失败 稍后再试");
			}
		},
		error:function(){
			alert("加载失败，稍后再试。");
		}
	}); 
}
function toReLoad(labelPage){
	var array = new Array();
	if(labelPage!=""&&labelPage!=null&&labelPage!=undefined){
		array.push("labelPage="+labelPage);
	}
	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/complaint_dealer/dealer", 
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
	

	if(page!=""&&page!=undefined){
		array_complaint.push("page="+page);
	}

	array_complaint.push("labelPage="+labelPage);

	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/complaint_dealer/dealer", 
		data:array_complaint.join("&")+"&math="+Math.random(),
		success : function(msg) { 
			$(".biaogee").html(msg);
		},
		error:function(){
			alert("加载失败，稍后再试。");
		}
	}); 
}

function downDealerExcel(){

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
	window.location.href = CONTEXTPATH+"/complaint_dealer/downDealerExcel?"+array_complaint.join("&");
}

function dealer_getReplyComplaint_edit(complaintId){

	var array = new Array();


	if(complaintId!=null&&complaintId!=undefined&&complaintId!=""){
		array.push("complaintId="+complaintId);
	}

	array.push("statuForUse=1");
	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/complaint_dealer/getReplyComplaint", 
		data:array.join("&"),
		success : function(msg) { 
			$(".c1").empty();
			$(".c1").html(msg);
		},
		error:function(){
			alert("加载失败，稍后再试。");
		}
	}); 
}

function dealer_getReplyComplaint_show(complaintId){
	var array = new Array();


	if(complaintId!=null&&complaintId!=undefined&&complaintId!=""){
		array.push("complaintId="+complaintId);
	}

	array.push("statuForUse=0");
	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/complaint_dealer/getReplyComplaint", 
		data:array.join("&"),
		success : function(msg) { 
			$(".c1").html(msg);
		},
		error:function(){
			alert("加载失败，稍后再试。");
		}
	}); 
}
/*function checkLabel(){
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
	if(lable=='1'){
		$("#waitlabel").attr("class","list");
		$("#alreadylabel").attr("class","");
	}
	if(lable=='2'){
		$("#waitlabel").attr("class","");
		$("#alreadylabel").attr("class","list");
	}
	toReLoad(lable);
}*/