$(document).ready(function(){
	$('input[name="ck"]').live('click',collectLoadPlan);
	$("#quanxuan").live('click',goSelectAll);
/*	$(".ar_one tr td #addTr").live('click',addTr);
	$(".ar_two tr td span").live('click',detaleTr);
	$(".ar_two tr td input.lotnumber").live('blur',isRepeat);
	$(".ar_two tr td input.currentQtyReceived").live('blur',isVerifyNumbe);
	$("input[name='maturedDate']").live('blur',isValidDate);
	$("input[name='produceDate']").live('click',WdatePicker);*/
});


function goShowMoreOrderList(){
	page1 = $(".orderTab").find("tr:last").children().closest("#bor").find(".page").val();
	page = Number(page1)+1;
	$.ajax({ 
	     async:false,
	     type : "post", 
	     url : "../containerLoadPlan/getAllCustomerOrderPageBean", 
	     data: "page="+page,
	     dataType:"html",
	     success : function(resultMsg) {
	    	 $(".orderTab").append(resultMsg);
	     },
		  error:function(jqXHR,textStatus,errorThrown){
		  	alert("网络异常,请稍后再试。。。。");
		  }
	}); 
}

function goDelLoadPlan(planId){
	 $.ajax({ 
	     async:false,
	     type : "post", 
	     url : "../containerLoadPlan/goDelLoadPlanById", 
	     data: "planId="+planId,
	     dataType:"html",
	     success : function(resultMsg) {
	     	alert(resultMsg);
	     	window.location.reload();
	     },
		  error:function(jqXHR,textStatus,errorThrown){
		  	alert("网络异常,请稍后再试。。。。");
		  }
  }); 
}

//全选
$(function(){
	$("#quanxuan").click(function(){
		 $('input[name="ck"]').attr("checked",this.checked); 
	});
	var $subBox = $("input[name='ck']");
	
	$subBox.click(function(){
		$("#quanxuan").attr("checked",$subBox.length == $("input[name='ck']:checked").length ? true : false);
	});
});


function goSelectAll(event){
	var that = $(this);
	var ischecked = that.is(":checked");
	var checked = $(".goods-tr input:checked:gt(0)");
	if(ischecked){
		$.each(checked,function(i,item){
			var result = true;
			var skuQtyTotal =0;
			var skuIdval = $(item).siblings().closest(".skuId").val();
			var skuNameCn = $(item).siblings().closest(".skuNameCn").val();
			var skuQty = $(item).siblings().closest(".skuQty").val();
			var pName = $(item).siblings().closest(".pName").val();

			if(i==0){
				var tr = "<tr class='productTr'><td colspan='1' id='bo' class='product'><ul class='goods-list clearfix'><li> " +
				" <div class='goods-box'><input type='hidden' name='' value='"+skuIdval+"' class='skuId'>" +
				" <div class='goods-pic'><a href='#'><img src='http://d7.yihaodianimg.com/N05/M00/6B/90/CgQI01RPO0WABlDXAANgbENxSaY67401_60x60.jpg' width='60' height='60'></a></div>" +
				"<div class='goods-txt'>"+pName+""+skuNameCn+"</div> </div> </li> </ul></td>" +
				"<td colspan='1' id='bor'><span id='skuQty'>"+skuQty+"</span></td></tr>";
				$("#productTab").append(tr);
				
			}else{
				var skuId = $("ul li .goods-box").find(".skuId");
				$.each(skuId,function(i,item){
					var skuIdval1 = $(item).val();
					if(skuIdval == skuIdval1){
						var skuQty1 = $(item).parents("#bo").next().find("#skuQty").text();
						skuQtyTotal = Number(skuQty1) + Number(skuQty);
						$(item).parents("#bo").next().find("#skuQty").text(skuQtyTotal);
						result = false;
						return result;
					}else{
						result = true;
						return result;
					}
				});
				if(result){
					var tr = "<tr class='productTr'><td colspan='1' id='bo' class='product'><ul class='goods-list clearfix'><li> " +
							" <div class='goods-box'><input type='hidden' name='' value='"+skuIdval+"' class='skuId'>" +
							" <div class='goods-pic'><a href='#'><img src='http://d7.yihaodianimg.com/N05/M00/6B/90/CgQI01RPO0WABlDXAANgbENxSaY67401_60x60.jpg' width='60' height='60'></a></div>" +
							"<div class='goods-txt'>"+pName+""+skuNameCn+"</div> </div> </li> </ul></td>" +
							"<td colspan='1' id='bor'><span id='skuQty'>"+skuQty+"</span></td></tr>";
					$("#productTab").append(tr);
				}
			}
			
		});
	}else{
		$(".productTr").remove();	
	}
}




//海外人员去已经确认的装箱订单
function goLoadPlan(){
	$.dialog.confirm('确定要包装选中的订单?',function(){
		var orderIds = new Array();
		var checked = $(".goods-tr input:checked");
		$.each(checked,function(i,item){
			var skuQtyTotal =0;
			var skuIdval = $(item).siblings().closest(".skuId").val();
			var skuNameCn = $(item).siblings().closest(".skuNameCn").val();
			var skuQty = $(item).siblings().closest(".skuQty").val();
			var pName = $(item).siblings().closest(".pName").val();
			var skuId = $("ul li .goods-box").find(".skuId");
			var orderId = $(item).val();
			orderIds.push("orderId="+orderId);
		});
		 $.ajax({ 
		     async:false,
		     type : "post", 
		     url : "../containerLoadPlan/goSaveLoadPlan", 
		     data:orderIds.join("&"),
		     dataType:"html",
		     success : function(msg) {
		    	 tipMessage(msg,function(){
		    		 window.location.reload();
				 });
		     },
			 error:function(jqXHR,textStatus,errorThrown){
			   alert("网络异常,请稍后再试。。。。");
			 },
		 }); 
	},function(){}); 
}


function collectLoadPlan(event){
		var result = true;
		var that = $(this);
		var skuQtyTotal =0;
		var skuIdval = that.siblings().closest(".skuId").val();
		var skuNameCn = that.siblings().closest(".skuNameCn").val();
		var skuQty = that.siblings().closest(".skuQty").val();
		var pName = that.siblings().closest(".pName").val();
		var skuId = $("ul li .goods-box").find(".skuId");

		var ischecked = that.is(":checked");
		if(ischecked){
			$.each(skuId,function(i,item){
				var skuIdval1 = $(item).val();
				if(skuIdval == skuIdval1){
					var skuQty1 = $(item).parents("#bo").next().find("#skuQty").text();
					skuQtyTotal = Number(skuQty1) + Number(skuQty);
					$(item).parents("#bo").next().find("#skuQty").text(skuQtyTotal);
					result = false;
					return result;
				}else{
					result = true;
					return result;
				}
			});
			if(result){
				var tr = "<tr class='productTr'><td colspan='1' id='bo' class='product'><ul class='goods-list clearfix'><li> " +
						" <div class='goods-box'><input type='hidden' name='' value='"+skuIdval+"' class='skuId'>" +
						" <div class='goods-pic'><a href='#'><img src='http://d7.yihaodianimg.com/N05/M00/6B/90/CgQI01RPO0WABlDXAANgbENxSaY67401_60x60.jpg' width='60' height='60'></a></div>" +
						"<div class='goods-txt'>'"+pName+""+skuNameCn+"'</div> </div> </li> </ul></td>" +
						"<td colspan='1' id='bor'><span id='skuQty'>"+skuQty+"</span></td></tr>";
				$("#productTab").append(tr);
			}
		}else{
			$.each(skuId,function(i,item){
				var skuIdval1 = $(item).val();
				if(skuIdval == skuIdval1){
					var skuQty1 = $(item).parents("#bo").next().find("#skuQty").text();
					if(skuQty1 == skuQty){
						$(item).parents(".productTr").remove();	
					}else{
						skuQtyTotal = Number(skuQty1) - Number(skuQty);
						$(item).parents("#bo").next().find("#skuQty").text(skuQtyTotal);
					}
				}
			});
		}
}
