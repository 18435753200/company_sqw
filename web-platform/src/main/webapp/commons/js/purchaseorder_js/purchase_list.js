	//初步加载此项目这个页面时
	$(document).ready(function(){
			$.ajax({ 
			     async:false,
			     type : "post", 
			     url : "../pchaseOrder/getPurchaseOrderListByCondition", 
			     dataType:"html",
			     success : function(purchase) {
			     	$("#cs").html(purchase);
				 },
				 error:function(jqXHR,textStatus,errorThrown){
				  	alert("网络异常,请稍后再试。。。。");
				 }
		     });
			
			
			$("#id").change(function(){
				var orderId = $("#id").val();
				var  matchnum = /^[0-9]*$/;
			    if(!matchnum.test(orderId)){
			     		 $("#id").val("");
			     		 $("#id").focus();
			     }
			});
		});

var  CONTEXTPATH  = $("#conPath").val();
function addOrder(){
	window.location.href = CONTEXTPATH+"/pchaseOrder/addpurchaseorder";
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

function clickSubmit(page){
//	if($("#id").val().length>18){
//		alert("没有这么长的采购单号啊");
//		return false;
//	}
	
	var fm_data = $('#purchaseorder_fm').serialize();
	
	if(page!=undefined){
		fm_data += "&page="+page;
	}
	
	$.ajax({
		 type : "post", 
     	 url : "../pchaseOrder/getPurchaseOrderListByCondition", 
     	 data:fm_data+"&random="+Math.random(),
     	 dataType:"html",
         success : function(msg){
			$("#cs").html(msg);
		},
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("查询失败 ，请稍后再试。。");
	     }
	});
	
}

function finishOrder(){
	var ids  = new Array(); 
	var flag = false;
	  $('input[name="orderlist"]:checked').each(function(){    
	     ids.push("orderIds="+$(this).val());
	     if(ids.length!=0){
	    	 flag=true;
	     }
	  });
	  if(flag){
	  $.ajax({
			type : "post", 
			url : CONTEXTPATH+"/pchaseOrder/finishOrder", 
			data:ids.join("&"),
			success : function(msg) {
				tipMessage(msg,function(){
					window.location.href="../pchaseOrder/getPurchaseOrder";
		   		 });
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("批量修改订单完成失败，请稍后重试");
			}
		}); 
	  }
}

function exportPdf(){
	
	var ids  = new Array();    
	  $('input[name="orderlist"]:checked').each(function(){    
	     ids.push("orderIds="+$(this).val());    
	  }); 
	  $.ajax({
			type : "post", 
			url : CONTEXTPATH+"/pchaseOrder/exportPdf", 
			data:ids.join("&"),
			success : function(msg) {
				tipMessage(msg,function(){
					window.location.href="../pchaseOrder/getPurchaseOrder";
		   		 });
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("批量修改订单完成失败，请稍后重试");
			}
		}); 
}



