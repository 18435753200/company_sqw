var useCoupons;
$(function(){
	// find available coupons
	$(".use-coupons").live("click",findCoupons);
	
	// ensure use coupons
	$(".coupons-ensure,.selectCoupons-goback").live("click",ensureUseCoupons);
	
	// select coupons
	$(".coupon-item input[name='coupons']").live("click",selectCoupons);
	
	// find available coupons quantity
	// findCouponsQuantity();
	
});

function findCoupons(){
	useCoupons = $(this);
	
	if(useCoupons.attr("disabled")){
		return ;
	}
	
	$("body").find("form[name='tempCouponsForm']").remove();
	$("body").append("<form name='tempCouponsForm'></form>");
	var tempCouponsForm = $("form[name='tempCouponsForm']");
	
	var datas = useCoupons.parents(".item-group").find(".item-sku");
	
	for (var i = 0; i < datas.length; i++) {
		
		var data = $(datas[i]).attr("id").split("_");
		
		var skuId = data[2];
		var sumPrice = Number(data[9]);
		
		tempCouponsForm.append("<input type='hidden' name='findCouponsMap["+skuId+"]' value='"+sumPrice+"'/>");
	}
	
	var url = $("#path").val()+"/coupons/find";
	var data = tempCouponsForm.serialize();
	
	$.ajax({
		type:"get",
		url:url,
		data:data,
		dataType:"html",
		success:function(result){
			if(!checkResult4Coupons(result)){
				return;
			}
			$("#couponsList").html(result);
			
			var goodsItem = useCoupons.parents(".item-group").find(".item-sku").attr("id").split("_");
			var currentCouponsId = goodsItem[12];
			if(currentCouponsId != 0 && currentCouponsId != undefined){
				var currentUseCoupons = $("input[value='"+currentCouponsId+"']");
				if(currentUseCoupons.length > 0){
					currentUseCoupons.prop("checked",true);
				}
			}
			
			var curCoupId = useCoupons.parents(".item-group").find("input[name='couponIdTxt']").val();
			
			var itemGroups = $(".item-group");
			$.each(itemGroups, function(i, itemGroup){
				var coupid = $(itemGroup).find("input[name='couponIdTxt']").val();
				if (!isEmpty(coupid)) {
					var coupons = $("input[value='"+coupid+"']");
					if (coupid == curCoupId) {
						coupons.attr("checked", "checked");
					}
					else {
						// 已被其他商品使用的优惠券，需要移除
						if(coupons.length > 0){
							coupons.parents(".coupon-item").remove();
						}
					}
				}
			});
			
			var couponItem = $(".coupon-item");
			var couponsNull = $(".mycenter-bd").find(".null");
			if(couponItem.length == 0 && couponsNull.length == 0){
				var noCouponsTag = "<div class='null'><div class='null-icon'><span class='icon i-coupon'></span></div>";
				noCouponsTag += "<div class='null-text'><p>您暂时没有可使用的优惠券</p><input type='hidden' value='0' id='hasCoupons'/></div></div>";
				
				$(".coupon-box").prepend(noCouponsTag);
			}
			
			$("#confirmOrder").hide();
			$("#couponsList").show();
		}
	});
}

// TODO
function ensureUseCoupons(){
	// 获取选中的优惠券
	var selectCoupons = $(".coupon-item input[name='coupons']:checked");
	// 无可用优惠券、未选中优惠券
	if(selectCoupons.length == 0){
		useCoupons.parents(".item-group").find("input[name='couponIdTxt']").val("");
	}
	else {
		var couponsData = selectCoupons.eq(0).parents(".coupon-item").attr("id").split("_");
		couponsId = couponsData[1];
		useCoupons.parents(".item-group").find("input[name='couponIdTxt']").val(couponsId);
	}
	
	var url = $("#path").val() + "/cart/settlement";
	var requestData = "<form name='submit-form' method='get' action='" + url+ "'>";
	
	var promotion = "";
	var loop = 0;
	var itemGroups = $(".item-group");
	$.each(itemGroups, function(i, itemGroup){
		var skus = $(itemGroup).find(".item-sku");
		var productType = "";
		$.each(skus, function(j, sku){
			var  itemId = $(sku).attr("id");
			var skuid = itemId.split("_")[2];
			productType = itemId.split("_")[11];
			requestData += "<input type='hidden' name='skuIdList[" + loop++ + "]' value='" + skuid + "'/>";
		});
		var coupid = $(itemGroup).find("input[name='couponIdTxt']").val();
		if (!isEmpty(coupid)) {
			promotion += productType + "_" + coupid + ";";
		}
	});
	if (!isEmpty(promotion)) {
		promotion = promotion.substring(0, promotion.length - 1);
		requestData += "<input type='hidden' name='promotion' value='" + promotion + "'/>"; 
	}
	
	requestData += "</form>";

	// 渲染页面
	$("body").append(requestData);

	// 提交
	$("form[name='submit-form']").submit();
}
function ensureUseCoupons_back(){
	
	// 获取选中的优惠券
	var selectCoupons = $(".coupon-item input[name='coupons']:checked");
	
	var goodsItem = useCoupons.parents(".item-group").find(".item-sku");
	var dataId = goodsItem.attr("id");
	var data = dataId.split("_");
	
	var currentUseCouponsId = data[12];
	var couponsId = 0;	// 优惠券ID
	var couponsPrice = 0;	// 优惠券金额
	var couponsType ;	// 优惠券类型
	var subtotal = 0;	// 小计金额
	
	var findCouponsQtyFlg = useCoupons.parents(".item-group").find("input[name='findCouponsQtyFlg']");
	
	// 无可用优惠券、未选中优惠券
	if(selectCoupons.length == 0){
		// 是否需要获取优惠券数量标识
		findCouponsQtyFlg.val(1);
		subtotal = data[7];
	}else{
		// 是否需要获取优惠券数量标识
		findCouponsQtyFlg.val(0);
		// 选中的优惠券信息
		var couponsData = selectCoupons.eq(0).parents(".coupon-item").attr("id").split("_");
		couponsId = couponsData[1];
		couponsPrice = getDiscount(couponsId);
		couponsType = couponsData[3];
		
		// 运费和税
		var transferAndtax = Number(data[7]) - Number(data[8]);
		var subTotalItemPrice = Number(data[8]) - Number(couponsPrice);
		
		if(subTotalItemPrice < 0){
			subTotalItemPrice = 0;
		}
		
		subtotal = subTotalItemPrice + transferAndtax;
	}
	setCoupons(couponsId,couponsPrice,couponsType,subtotal);
	
	// 设定正在使用优惠券列表
	var useCouponsList = $("#useCouponsList").find("input");
	var value = useCouponsList.val();
	
	if(couponsId != 0){
		value += couponsId+"_";
	}
	if(currentUseCouponsId != 0 && currentUseCouponsId != undefined){
		value = value.replace(currentUseCouponsId+"_","");
	}
	useCouponsList.val(value);
	
	$("#confirmOrder").show();
	$("#couponsList").hide();
	
	findCouponsQuantity();
}

function getDiscount(couponsId){
	$("body").find("form[name='tempGetDiscountForm']").remove();
	$("body").append("<form name='tempGetDiscountForm'></form>");
	var tempGetDiscountForm = $("form[name='tempGetDiscountForm']");
	
	var datas = useCoupons.parents(".item-group").find(".item-sku");
	
	for (var i = 0; i < datas.length; i++) {
		
		var data = $(datas[i]).attr("id").split("_");
		
		var skuId = data[2];
		var sumPrice = Number(data[9]);
		
		tempGetDiscountForm.append("<input type='hidden' name='findCouponsMap["+skuId+"]' value='"+sumPrice+"'/>");
	}
	
	tempGetDiscountForm.append("<input type='hidden' name='couponstockId' value='"+couponsId+"'/>");
	
	var url = $("#path").val()+"/coupons/getDiscount";
	var data = tempGetDiscountForm.serialize();
	
	var discount = 0;
	
	$.ajax({
		type:"post",
		url:url,
		data:data,
		async:false,
		dataType:"text",
		success:function(result){
			discount = Number(result);
		}
	});
	
	return discount;
}

function setCoupons(couponsId,couponsPrice,couponsType,subtotal){
	
	var goodsItem = useCoupons.parents(".item-group").find(".item-sku");
	
	for(var j = 0; j < goodsItem.length; j ++){
		
		var itemData = goodsItem.eq(j).attr("id").split("_");
		var newItemData = "";
		for(var i = 0 ; i < 15 ; i ++){
			switch(i){
			case 12:
				newItemData += couponsId+"_";
				break;
			case 13:
				newItemData += couponsPrice+"_";
				break;
			case 14:
				newItemData += couponsType;
				break;
			default:
				newItemData += itemData[i]+"_";
				break;
			}
		}
		goodsItem.eq(j).attr("id",newItemData);
	}
	
	var sumPrice = setSumPrice();
	
	var goodsBar = useCoupons.parents(".goods-bar");
	if(couponsPrice == 0){
		goodsBar.find(".coupons").remove();	// 移除优惠券显示
	}else{
		/*var couponsTypeName;
		switch(couponsType){
		case '1':
			couponsTypeName = "优惠券";
			break;
		case '2':
			couponsTypeName = "现金券";
			break;
		}*/
		goodsBar.find(".use-coupons span").text("- "+Number(couponsPrice).toFixed(2));
	}
	goodsBar.find(".subtotal").text("支付金额： "+Number(subtotal).toFixed(2));	// 支付金额
	$(".order-bar2").find("b").text(Number(sumPrice).toFixed(2));	// 总额
}

function setSumPrice(){
	var goodsGroup = $(".item-group");
	var sumPrice = 0;
	for (var i = 0; i < goodsGroup.length; i++) {
		var goodsItem = goodsGroup.eq(i).find(".item-sku").eq(0);
		var data = goodsItem.attr("id").split("_");
		var couponsPrice = data[13];
		var subItemtotal = data[8];
		var subtotal = data[7];
		if(couponsPrice != null && couponsPrice != "" && couponsPrice != undefined){
			
			// 运费和税
			var transferAndtax = Number(subtotal) - Number(subItemtotal);
			
			var subTotalItemPrice = Number(subItemtotal) - Number(couponsPrice);
			
			if(subTotalItemPrice < 0){
				subTotalItemPrice = 0;
			}
			
			sumPrice += Number(transferAndtax) + Number(subTotalItemPrice);
		}else{
			sumPrice += Number(subtotal);
		}
	}
	
	return sumPrice;
}

function selectCoupons(){
	var cur = $(this);
	$(".coupon-item input[name='coupons']").not(cur).prop("checked",false);
}

function findCouponsQuantity(){
	
	$("body").find("form[name='findCouponsQtyForm']").remove();
	$("body").append("<form name='findCouponsQtyForm'></form>");
	var findCouponsQtyForm = $("form[name='findCouponsQtyForm']");
	
	var itemGroups = $(".item-group");
	var needFindCouponsQty = false;
	for (var i = 0; i < itemGroups.length; i++) {
		
		var itemGroup = itemGroups[i];
		
		var goodsItems = $(itemGroup).find(".item-sku");
		var groupIndex = $(itemGroup).find("input[name='group_index']").val();
		var findCouponsQtyFlg = $(itemGroup).find("input[name='findCouponsQtyFlg']");
		
		if(findCouponsQtyFlg.val() == "1"){
			needFindCouponsQty = true;
			for(var j = 0 ; j < goodsItems.length ; j ++){
				
				var data = $(goodsItems[j]).attr("id").split("_");
				
				var skuId = data[2];
				var sumPrice = Number(data[9]);
				var curVal = skuId+"_"+sumPrice;
				
				var findCouponsQty = findCouponsQtyForm.find("input[name='findCouponsQtyMap["+groupIndex+"]']");
				if(findCouponsQty.length > 0 && findCouponsQty.val() != ""){
					curVal = curVal + "," + findCouponsQty.val();
					findCouponsQty.val(curVal);
				}else{
					findCouponsQtyForm.append("<input type='hidden' class='findCouponsQty' name='findCouponsQtyMap["+groupIndex+"]' value='"+curVal+"'/>");
				}
			}
		}
	}
	
	if(needFindCouponsQty){
		var url = $("#path").val()+"/coupons/findQty";
		var data = findCouponsQtyForm.serialize();
		
		$.ajax({
			type:"post",
			url:url,
			data:data,
			async:true,
			dataType:"json",
			success:function(result){
				if(result == "500"){
					$(".use-coupons span").text("0张");
					$(".use-coupons").attr("disabled","disabled");
					return;
				}
				
				var couponsQtyList = result;
				
				for(var i = 0 ; i < couponsQtyList.length ; i ++){
					var couponsQty = couponsQtyList[i];
					
					var index = couponsQty.index;
					var stockIdList = couponsQty.stockIdList;
					
					var qty = 0 ;
					var currentCouponsQty = stockIdList.length;
					if(currentCouponsQty > 0){
						
						// 已使用优惠券
						var useCouponsList = $("#useCouponsList").find("input").val();
						useCouponsList = useCouponsList.split("_");
						
						if(useCouponsList != ""){
							var unique = unique4Array(useCouponsList,stockIdList);
							qty = unique.length;
						}else{
							qty = currentCouponsQty;
						}
					}
					
					var groupIndex = $(".item-group").find("input[name='group_index'][value='"+index+"']");
					if(groupIndex && groupIndex.length > 0){
						var itemGroup = groupIndex.parents(".item-group");
						if(qty > 0){
							itemGroup.find(".use-coupons span").text(qty+"张");
							itemGroup.find(".use-coupons").removeAttr("disabled");
						}else{
							itemGroup.find(".use-coupons span").text(qty+"张");
							itemGroup.find(".use-coupons").attr("disabled","disabled");
						}
					}
				}
			}
		});
	}
}

function unique4Array(useCouponsList, stockIdList){
	
	for ( var j = 0; j < useCouponsList.length; j++) {
		var string = useCouponsList[j];
		if(stockIdList.contains(string)){
			stockIdList.remove(string);
		}
	}
    return stockIdList;
}

Array.prototype.contains = function(item){
    return RegExp(item).test(this);
};

Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
	if (this[i] == val) return i;
	}
	return -1;
	};
	
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
	this.splice(index, 1);
	}
	};

function checkResult4Coupons(result){
	var path = $("#path").val();
	var loginUrl = path+"/customer/toLogin";
	var noErrors = true;
	if(result.indexOf("needLogin") != -1){
		location.href=loginUrl;
		noErrors = false;
	}
	if(result.indexOf("errorMessage") != -1){
		noErrors = false;
		$.dialog({
            content : '系统繁忙，请稍后重试',
            title : '众聚猫提示',
            time: 2000,
		});
	}
	
	return noErrors;
}