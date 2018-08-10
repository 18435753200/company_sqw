$(document).ready(function(){
	$('input[name="ck"]').live('click',collectLoadPlan);
	$("#quanxuan").live('click',goSelectAll);
	$(".orderTab").find("tr").live('mousemove',hover);
	$(".orderTab").find("tr").live('mouseleave',mouseleave);
	
	var $category = $('.goods-tr:gt(9):not(:last)');
	$category.hide();
	var $toggleBtn = $('.showMore a');
	$toggleBtn.toggle(function(){
		$category.show();
		$(this).find('span').removeClass('hide').addClass('show').text('显示更多订单');	
	},function(){
		$category.hide();
		$(this).find('span').removeClass('show').addClass('hide').text('显示更多订单');
	});
});

function hover(event){
	$(this).css("background","#f0efee");
}

function mouseleave(event){
	$(this).css("background","#fff");
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

function goShowMoreOrderList(){
	var checkTime = $(".orderTab").find("tr:last").children().find(".createTime").val();
	page1 = $(".orderTab").find("tr:last").children().closest("#bor").find(".page").val();
	page = Number(page1)+1;
	$.ajax({ 
	     async:false,
	     type : "post", 
	     url : "../containerLoadPlan/showUpdateContentList", 
	     data: {"page":1,"checkTime":checkTime},  
	     dataType:"html",
	     success : function(resultMsg) {
	    	 $(".orderTab tbody").append(resultMsg);
	     },
		  error:function(jqXHR,textStatus,errorThrown){
		  	alert("网络异常,请稍后再试。。。。");
		  }
	}); 
}


function goSelectAll(event){
	$(".productTr").remove();	
	var skuQtyTotal4 =0;
	var skuWeightTotal =0;
	var skuVolumeTotal =0;
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
			var imgUrl =  $(item).siblings().closest(".imgUrl").val();
			var weight = $(item).siblings().closest(".weight").val();
			var volume = $(item).siblings().closest(".volume").val();
			var totalCount = $("#totalCount").text();
			var totalWeghit = $("#totalWeghit").text();
			var totalvolume = $("#totalvolume").text();
			if(i==0){
				var tr = "<tr class='productTr'><td colspan='1' id='bo' class='product'><ul class='goods-list clearfix'><li> " +
				" <div class='goods-box'><input type='hidden' name='' value='"+skuIdval+"' class='skuId'>" +
				" <div class='goods-pic'><a href='#'><img src='"+imgUrl+"' width='60' height='60'></a></div>" +
				"<div class='goods-txt'>"+pName+""+skuNameCn+"</div> </div> </li> </ul></td>" +
				"<td colspan='1' id='bor'><span id='skuQty'>"+skuQty+"</span></td></tr>";
				$("#productTab").append(tr);
				$("#totalCount").text(skuQty);
				$("#totalWeghit").text(weight);
				$("#totalvolume").text(volume);
			}else{
				var skuId = $("ul li .goods-box").find(".skuId");
				$.each(skuId,function(i,item){
					skuQtyTotal4 = Number(totalCount) + Number(skuQty);
					skuWeightTotal = (Number(totalWeghit) + Number(weight)).toFixed(2);
					skuVolumeTotal = (Number(totalvolume)  + Number(volume)).toFixed(2) ;
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
				
				$("#totalCount").text(skuQtyTotal4);
				$("#totalWeghit").text(skuWeightTotal);
				$("#totalvolume").text(skuVolumeTotal);
				if(result){
					var tr = "<tr class='productTr'><td colspan='1' id='bo' class='product'><ul class='goods-list clearfix'><li> " +
							" <div class='goods-box'><input type='hidden' name='' value='"+skuIdval+"' class='skuId'>" +
							" <div class='goods-pic'><a href='#'><img src='"+imgUrl+"' width='60' height='60'></a></div>" +
							"<div class='goods-txt'>"+pName+""+skuNameCn+"</div> </div> </li> </ul></td>" +
							"<td colspan='1' id='bor'><span id='skuQty'>"+skuQty+"</span></td></tr>";
					$("#productTab").append(tr);
				}
			}
			
		});
	}else{
		$("#totalCount").text(0);
		$("#totalWeghit").text(0);
		$("#totalvolume").text(0);
		$(".productTr").remove();	
	}
}

//汇总选中商品的信息
function collectLoadPlan(event){
	var result = true;
	var that = $(this);
	var skuQtyTotal =0;
	var skuQtyTotal4 =0;
	var skuWeightTotal =0;
	var skuVolumeTotal =0;
	var skuIdval = that.siblings().closest(".skuId").val();
	var skuNameCn = that.siblings().closest(".skuNameCn").val();
	var skuQty = that.siblings().closest(".skuQty").val();
	var imgUrl = that.siblings().closest(".imgUrl").val();
	var pName = that.siblings().closest(".pName").val();
	var weight = that.siblings().closest(".weight").val();
	var volume = that.siblings().closest(".volume").val();
	var skuId = $("ul li .goods-box").find(".skuId");

	var totalCount = $("#totalCount").text();
	var totalWeghit = $("#totalWeghit").text();
	var totalvolume = $("#totalvolume").text();
	var ischecked = that.is(":checked");
	if(!skuId.lenght > 0){
		$("#totalCount").text(skuQty);
		$("#totalWeghit").text(weight);
		$("#totalvolume").text(volume);
	}
	if(ischecked){
		$.each(skuId,function(i,item){
			skuQtyTotal4 = Number(totalCount) + Number(skuQty);
			skuWeightTotal = (Number(totalWeghit) + Number(weight)).toFixed(2);
			skuVolumeTotal = (Number(totalvolume)  + Number(volume)).toFixed(2) ;
			$("#totalCount").text(skuQtyTotal4);
			$("#totalWeghit").text(skuWeightTotal);
			$("#totalvolume").text(skuVolumeTotal);
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
					" <div class='goods-pic'><a href='#'><img src='"+imgUrl+"' width='60' height='60'></a></div>" +
					"<div class='goods-txt'>"+pName+""+skuNameCn+"</div> </div> </li> </ul></td>" +
					"<td colspan='1' id='bor'><span id='skuQty'>"+skuQty+"</span></td></tr>";
			$("#productTab").append(tr);
		}
	}else{
		if(!skuId.lenght > 0){
			$("#totalCount").text(0);
			$("#totalWeghit").text(0);
			$("#totalvolume").text(0);
		}
		$.each(skuId,function(i,item){
			skuQtyTotal4 = Number(totalCount) - Number(skuQty);
			skuWeightTotal = (Number(totalWeghit) - Number(weight)).toFixed(2);
			skuVolumeTotal = (Number(totalvolume)  - Number(volume)).toFixed(2) ;
			
			$("#totalCount").text(skuQtyTotal4);
			$("#totalWeghit").text(skuWeightTotal);
			$("#totalvolume").text(skuVolumeTotal);
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


//经销商勾选修改装箱单
function goUpdateLoadPlanByPlanId(){
	if($(".goods-tr input:checked").closest("#quanxuan").length>0){
		var checked = $(".goods-tr input:checked:gt(0)");
	}else{
		var checked = $(".goods-tr input:checked");
	}
	if(!checked.length>0){
		alert("您还沒有选择要装箱的订单!");
	}else{
		$.dialog.confirm('确定要修改选中的订单?',function(){
			var orderIds = new Array();
			var checked = $(".goods-tr input:checked");
			var planId = $("#planId").val();
			$.each(checked,function(i,item){
				var skuQtyTotal =0;
				var skuIdval = $(item).siblings().closest(".skuId").val();
				var skuNameCn = $(item).siblings().closest(".skuNameCn").val();
				var skuQty = $(item).siblings().closest(".skuQty").val();
				var imgUrl = $(item).siblings().closest(".imgUrl").val();
				var pName = $(item).siblings().closest(".pName").val();
				var skuId = $("ul li .goods-box").find(".skuId");
				var orderId = $(item).val();
				if(orderId != ""){
					orderIds.push("orderId="+orderId);
				}
			});
			if(planId != ""){
				orderIds.push("planId="+planId);
			}
			
			 $.ajax({ 
			     async:false,
			     type : "post", 
			     url : "../containerLoadPlan/goUpdateOrderByPlanId", 
			     data:orderIds.join("&"),
			     dataType:"html",
			     success : function(msg) {
			    	 tipMessage(msg,function(){
			    		 window.location.reload(true);
			    		// window.location.href="xxx.xx";
					 });
			     },
				 error:function(jqXHR,textStatus,errorThrown){
				   alert("网络异常,请稍后再试。。。。");
				 },
			 }); 
		},function(){}); 
	}
}