var  CONTEXTPATH=$("#conPath").val();
$(document).ready(function(){
	clickSubmit();
});

function clickSubmit(page){
	if($("#businessNumber").val() == undefined || $("#businessNumber").val() == ""){
		alert("采购单号为空");
		return false;
	}
	
	if($("#businessNumber").val().length>18){
		alert("没有这么长的采购单号啊");
		return false;
	}
	
	var fm_data = $('#notificationorder_fm').serialize();
	
	if(page!=undefined){
		fm_data += "&page="+page;
	}
	var requst_url = CONTEXTPATH+"/commoditystore/getPurchaseOrderItemPageBean";
	$.ajax({
		 type : "post", 
     	 url : requst_url, 
     	 data:fm_data+"&random="+Math.random(),
     	 dataType:"html",
         success : function(msg){
			$("#modelPurchaseOrderItemList").html(msg);
			
			$("input[name='notifySendQty']").each(function(){
				$(this).attr("readonly",true);
				$(this).css("background","#ccc");
			});
			
		},
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("查询失败 ，请稍后再试。。");
	     }
	}); 
}

function clickSendAddress(){
	var  sendAddress = $("#sendAdd").find("option:selected");
	var  receiveName = sendAddress.attr("contact");
	var  receivePhone = sendAddress.attr("telephone");
	$("#receiveName").val(receiveName);
	$("#receivePhone").val(receivePhone);
}

function saveNotificationOrder(){
	
	
	var sendAdd = $("#sendAdd option:selected");
	var storeCode = sendAdd.val();
	var sendAddress = sendAdd.text();
	var storeName = sendAdd.attr("storeName");
	$("#sendAddress").val(sendAddress);
	$("#storeName").val(storeName);
	$("#storeCode").val(storeCode);
	var chkTrue = "";
	$("input[type='checkbox'][name='purchaseOrderItemId']").each(function() { 
		if(this.checked){
			chkTrue = chkTrue+"1,";
		}else{
			chkTrue = chkTrue+"0,";
		}
	});
	chkTrue = chkTrue.substring(0, chkTrue.length-1);
	if(chkTrue != ""){
		$("#chkTrue").val(chkTrue);
	}
	if($("#notificationId").val() == undefined || $("#notificationId").val() == ""){
		alert("入库通知单号为空");
		return false;
	}
	
	if($("#businessNumber").val() == undefined || $("#businessNumber").val() == ""){
		alert("采购单号为空");
		return false;
	}
	
	if($("#estimateDate").val() == undefined || $("#estimateDate").val() == ""){
		alert("预计送达日期为空");
		return false;
	}
	/*if($("#serviceName").val() == undefined || $("#serviceName").val() == ""){
		alert("服务商名称为空");
		return false;
	}*/
	if($("#supplierCode").val() == undefined || $("#supplierCode").val() == ""){
		alert("供应商编号为空");
		return false;
	}
	if($("#shipperType").val() == undefined || $("#shipperType").val() == ""){
		alert("承运方式为空");
		return false;
	}
	if(storeCode == undefined || storeCode == ""){
		alert("请选择送货地址");
		return false;
	}
	if($("#receiveName").val() == undefined || $("#receiveName").val() == ""){
		alert("联系人为空");
		return false;
	}
	if($("#receivePhone").val() == undefined || $("#receivePhone").val() == ""){
		alert("联系电话为空");
		return false;
	}
	var flag = false;
	var aTr=$('#orderItem .sot');
    for(var i=0;i<aTr.length;i++){
        var oInp1=aTr[i].getElementsByTagName('input')[0];
        var oInp8=aTr[i].getElementsByTagName('input')[7];
        if(oInp1.checked==true&&oInp8.value==0){
        	alert('选择商品的本次通知送货数量为0');
            return false;
        }
        if(oInp1.checked==true){
        	flag = true;
        }
    }
    if(!flag){
    	alert('请选择要入库通知的商品');
        return false;
    }
    var fm_data = $('#notificationorder_fm').serialize();
    $.ajax({
    	type : "post", 
		url  : CONTEXTPATH+"/commoditystore/addNotificationOrder", 
		data:fm_data+"&random="+Math.random(),
		dataType:"text",
		success : function(msg) {
			window.location.href = CONTEXTPATH+"/commoditystore/getNotificationInOrder?target=1";

		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("请稍后再试。。");
		}
    });
	
}



function updatePurchaseOrderItem(thisObj){
	var notifySendQty = $(thisObj).parent().parent().find("input[name='notifySendQty']");
	if(thisObj.checked == true){
		notifySendQty.attr("readonly",false);
		notifySendQty.css("background","");
	}else{
		
		notifySendQty.attr("readonly",true);
		notifySendQty.css("background","#ccc");
	}
	
}

function updateNotifySendQty(thisObj,maxQty){
	var notifyQty = $(thisObj).val();
	if(notifyQty>maxQty){
		alert("通知数量不能大于剩余未通知数据(" + maxQty + ")");
		$(thisObj).val(maxQty);
		return;
	}
	
		
	/*var totalNotifyQty = 0;
	$("#notifySendQty").each(function(){
		totalNotifyQty = parseInt(totalNotifyQty,10) + parseInt($(this).val(),10);
	});
	$("#totalNotifyQty").val(totalNotifyQty);*/
}


function checkNotifyOrder(){
	var flag=false;
	$("#purchaseOrderItemId:checked").each(function(){
		flag = true;
	});
	
	if(!flag){
		alert("请选择入库通知的商品");
		return false;
	}
	if($("#notificationId").val() == undefined || $("#notificationId").val() == ""){
		alert("入库通知单号为空");
		return false;
	}
	var requst_url = CONTEXTPATH+"/commoditystore/checkNotificationOrder";
	$("#notificationorder_fm").attr("action",requst_url);
	$("#notificationorder_fm").submit();
}

function warehouseName(){
	$("#supplierclick").hide();
	$("#warehouseclick").show();
	if($("#target").val() == '3'){
		return false;
	}
	if($("#status10").val() != '1'){
		return false;
	}
	$.ajax({
		type : "post", 
		url  : CONTEXTPATH+"	/inventory/loadWareSelect", 
		dataType:'json',
		success : function(msg) {
			$("#suppliertable").empty();
			var tablelist = "";
			if(msg==""){
				 tipMessage("对不起，没有查到仓库!",function(){
					 $("#skuselect").empty();
				  });
				 return false;
			}else{
				$.each(eval(msg),function(i,data){
					tablelist+="<tr><td class='t1'><input type='radio' name='warehouseid' value='"+data.warehouseCode+"'></td><td class='t2' title='"+data.warehouseName+"'><span>"+data.warehouseName+"</span></td></tr>";
				});
				$("#suppliertable").append(tablelist);
				
				$('.alert_bu').show(10);
			}

		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("请稍后再试。。");
		}
	});
}
function submit_addpurchase_fun(){
	/*$('#purchaseOrderfm').*/
	var flag  = true;
	if ($("[name='receiveName']").val() == ''){
		flag = false;
		alert('请添加收货人姓名');
		return flag;
	}
	if ($("[name='receiveAddress']").val() == ''){
		flag = false;
		alert('请添加收货人地址');
		return flag;
	}
	if($("[name='receivePhone']").val() == ''){
		flag = false;
		alert('请添加收货人电话.');
		return flag;
	}
	if($('#ttr input').length==0){
		flag = false;
		alert('请添加商品.');
		return flag;
	}	
	var bj = 1;
	$("#ttr input[name='qty']").each(function(){
		if($(this).val()==0){
			bj = 0;
		}
	});
	if ( bj == 0 ){
		flag = false;
		alert('商品数量不能为0');
		return flag;
	} 
	var data = $('#purchaseOrderfm').serialize();
	if(data.indexOf('=&')!=-1){
		flag = false;
		alert('请完善表单信息.');
		return flag;
	}
	if(flag){
		
		$.ajax({
			type : "post", 
			url  : CONTEXTPATH+"/purchaseorder/addpurchaseorder", 
			data :data+"&random="+Math.random(),
			success : function(msg) {
				
				if(msg == '1'){
					 $.dialog.tipMessage('保存好了,返回列表页!',function(){
						 reloadOpennerPage();
					 });
				}else{
					alert('数据异常?');
				}

			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
		
	}
}


