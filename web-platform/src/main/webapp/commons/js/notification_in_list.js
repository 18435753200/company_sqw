var  CONTEXTPATH  = $("#conPath").val();
$(document).ready(function(){
	clickSubmit();
	
	
}); 

function addNotify(){
	var purchaseId = "";
	var index = 0;
	var flag = false;
	$("input[type='checkbox'][name='purchaseOrderId']").each(function() { 
		if(this.checked){
			if(index > 1){
				alert("不能选择两个采购单");
				flag = false;
				return;
			}
			var status = $(this).attr("orderStatus");
			if(status != 10){
				alert("采购单状态为非审核通过状态");
				flag = false;
				return;
			}
			purchaseId = $(this).val();
			
			index = index+1;
			flag=true;
		}
	});
	if(index==0)
	{
		alert("请选择一条记录!");
		return;
	}
	if(flag == true){
		window.location.href = CONTEXTPATH+"/commoditystore/getPurchaseOrder?purchaseId=" + purchaseId;
	}
	
	
}
function clickSubmit(page){
	if($("#purchaseId").val().length>18){
		alert("没有这么长的采购单号啊");
		return false;
	}
	
	var orderStatusValue=$("#orderStatus").find("option:selected").val(); 
	var emergencyValue=$("#emergency").find("option:selected").val(); 
	var warehouseCode=$("#warehouseName").find("option:selected").val(); 
	var businessTypeCode =$("#businessType").find("option:selected").val(); 
	var supplyTypeCode =$("#supplyType").find("option:selected").val(); 
	
	var warehouseNameText=$("#warehouseName").find("option:selected").text();
	
	$("#orderStatusValue").val(orderStatusValue);
	$("#emergencyValue").val(emergencyValue);
	$("#warehouseCode").val(warehouseCode);
	$("#businessTypeCode").val(businessTypeCode);
	$("#supplyTypeCode").val(supplyTypeCode);
	$("#warehouseNameText").val(warehouseNameText);
	
	
	var fm_data = $('#notificationorder_fm').serialize();
	
	if(page!=undefined){
		fm_data += "&page="+page;
	}
	
	$.ajax({
		 type : "post", 
     	 url : CONTEXTPATH+"/commoditystore/getPurchaseOrderPageBean", 
     	 data:fm_data+"&random="+Math.random(),
     	 dataType:"html",
         success : function(msg){
			$("#modelPurchaseList").html(msg);
		},
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("查询失败 ，请稍后再试。。");
	     }
	}); 
}

function getNotificationOrder(thisObj,page){
	//var purId = thisObj.getElementsByTagName('input')[0];
	var purId = thisObj;
	var purchaseId = purId.value;
	if(purchaseId == undefined && purchaseId == ""){
		return false;
	}
	$("[name='purchaseOrderId']").removeAttr("checked");
	purId.checked = true;
	//$("#purchaseId").val(purchaseId);
	
	/*if(purId.checked == true){
		$("[name='purchaseOrderId']").removeAttr("checked");
		purId.checked = true;
		$("#purchaseId").val(purchaseId);
		var fm_data = $('#notificationorder_fm').serialize();
		if(page!=undefined){
			fm_data += "&page="+page;
		}
		$.ajax({
			 type : "post", 
	     	 url : CONTEXTPATH+"/commoditystore/getNotificationOrderPageBean", 
	     	 data:fm_data+"&random="+Math.random(),
	     	 dataType:"html",
	         success : function(msg){
				$("#modellist").html(msg);
			},
			 error: function(jqXHR, textStatus, errorThrown){
		        alert("查询失败 ，请稍后再试。。");
		     }
		}); 
	}else{
		$("[name='purchaseOrderId']").removeAttr("checked");
		purId.checked = false;
		$("#purchaseId").val(null);
		$("#modellist").html("");
	}*/
	
}

function checkNotifyOrder(thisObj){
	
	var notificationId = $(thisObj).attr("notificationId");
	window.location.href = CONTEXTPATH+"/commoditystore/checkNotificationOrder?notificationId=" + notificationId;
}

function notificationOrder(purchaseId){
	var url = CONTEXTPATH+"/commoditystore/getNotificationOrderPageBean?purchaseId=" + purchaseId;
	window.open(url, "_blank");
}
