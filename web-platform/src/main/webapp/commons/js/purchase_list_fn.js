var  CONTEXTPATH  = $("#conPath").val();
$(document).ready(function(){
	
	clickSubmit();
	$("#id").change(function(){
		var orderId = $("#id").val();
		var  matchnum = /^[0-9]*$/;
	    if(!matchnum.test(orderId)){
	     		 $("#id").val("");
	     		 $("#id").focus();
	     }
	});
	
}); 

function clickSubmit(page){

	if($("#id").val().length>18){
		alert("没有这么长的采购单号啊");
		return false;
	}
	
	var fm_data = $('#purchaseorder_fm').serialize();
	
	if(page!=undefined){
		fm_data += "&page="+page;
	}
	
	$.ajax({
		 type : "post", 
     	 url : CONTEXTPATH+"/purchaseorder/getPurchaseOrder", 
     	 data:fm_data+"&random="+Math.random(),
     	 dataType:"html",
         success : function(msg){
			$("#modellist").html(msg);
		},
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("查询失败 ，请稍后再试。。");
	     }
	}); 
}

function delorder(orderId){
	$.dialog.confirm('删除不能恢复?',function(){
		$.ajax({
			type : "post", 
			url : CONTEXTPATH+"/purchaseorder/delorder", 
			data:"orderId="+orderId+"&math="+Math.random(),
			success : function(msg) {
				
				if(msg == "1"){
					alert("采购单已删除!");
					var pageContext = $("#pageContext").val();
					clickSubmit(pageContext);
				}else{
					alert('删除失败,要么等会再试试?');
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("删除失败 ，请稍后再试。。");
			}
		}); 
	});
}

function confimorder(orderId){
	tipMessage('确认采购单后不能进行其他操作?',function(){
		$.ajax({
			type : "post", 
			url : CONTEXTPATH+"/purchaseorder/confirmOrder", 
			data:"orderId="+orderId+"&math="+Math.random(),
			success : function(msg) {
				
				if(msg == "1"){
					alert("采购单已确认!");
					var pageContext = $("#pageContext").val();
					clickSubmit(pageContext);
				}else{
					alert('确认失败,要么等会再试试?');
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("确认失败 ，请稍后再试。。");
			}
		}); 
	});
}
