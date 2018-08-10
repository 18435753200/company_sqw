var  CONTEXTPATH  = $("#conPath").val();
function toReLoad(flag){
	if(flag == 1){
		/*$.ajax({
			type : "post", 
			url : CONTEXTPATH+"/infrastructure/findLastCyclePlan", 
			data:"",
			success : function(msg) { 
				if(msg =='success'){
					alert("现在已经有周期分红计划在实施！");
				}else{
					window.location.href=CONTEXTPATH+"/infrastructure/toAddFhCyclePlan";
				}
			},
			error:function(){
				alert("加载失败，稍后再试。");
			}
		});*/
        window.location.href=CONTEXTPATH+"/infrastructure/toAddFhCyclePlan";
	}
}

function findFhList(){
	window.location.href=CONTEXTPATH+"/infrastructure/findCyclePlanList";
}

//1待处理 2已处理
function labelPage (lable){
	toReLoad(lable);
}
function selectAlls(){
	var ckAll = document.getElementById("selectAll");
	var cks = document.getElementsByName("orderIds");
	for(var i = 0; i < cks.length; i ++){
			cks[i].checked = ckAll.checked;
	}
}

function toAddPlanImpl(){
	var orderIds = $("input:checkbox[name='orderIds']:checked");
	if(typeof(orderIds)=="undefined" || orderIds.length == 0){
		alert("请选择需要分红的订单！");
		return;
	}
	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/infrastructure/addPlanImpl", 
		data: $('#planImpl').serialize(),
		success : function(msg) { 
			if(msg=="success"){
				alert("保存成功");
				window.location.href=CONTEXTPATH+"/infrastructure/OneFhCycle";
			}else{
				alert("保存失败 稍后再试");
			}
		},
		error:function(){
			alert("加载失败，稍后再试。");
		}
	}); 
}

function deletePlan(val){
	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/infrastructure/deletePlan", 
		data: "cyclePlanId="+val,
		success : function(msg) { 
			if(msg=="success"){
				alert("删除成功");
				window.location.href=CONTEXTPATH+"/infrastructure/findCyclePlanList";
			}else{
				alert("保存失败 稍后再试");
			}
		},
		error:function(){
			alert("加载失败，稍后再试。");
		}
	}); 
}
function selectPlan(val){
	window.location.href=CONTEXTPATH+"/infrastructure/toAddFhCyclePlanImpl?cyclePlanId="+val+"&editFlag="+1;
}
function updatePlan(val){
	window.location.href=CONTEXTPATH+"/infrastructure/toUpdateFhCyclePlan?cyclePlanId="+val;
}
