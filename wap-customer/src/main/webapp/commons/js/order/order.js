var submitOrder;
var check = true;
// 统计埋码
var _trackDataType = 'wap'; // 标记数据来源，参数是web和wap，可以为空，默认是web
var _Schannel_website_id = '10000006';// 分站编号，不存在可不写此变量或者留空
var _trackData = _trackData || [];// 必须为全局变量，假如之前并没有声明，请加此行代码；
$(function() {
	// 提交订单
	$(".order-btnt").live("click", submit);
	textCounter();

	var mySwiper1 = new Swiper('.ment-cent', {
		freeMode : true,
		slidesPerView : 'auto',
	});

	// 查看商品列表
	$(".itemlist").live("click", function() {

		var allItemList = $(".god-main").find(".pic-list");
		allItemList.hide();
		var totalCount = 0;
		var currentItemList = $(this).find("li");
		for (var i = 0; i < currentItemList.length; i++) {

			var currentItem = currentItemList.eq(i);
			var currentData = currentItem.attr("id").split("_");
			var currentSkuId = currentData[2];

			for (var j = 0; j < allItemList.length; j++) {
				var allItem = allItemList.eq(j);
				var data = allItem.attr("id").split("_");
				var skuId = data[2];
				var qty = data[5];

				if (currentSkuId == skuId) {
					allItem.show();
					totalCount += Number(qty);
				}
			}
		}

		$(".group-item-number").text("共" + totalCount + "件");
		$("#confirmOrder").hide();
		$("#itemlist").show();
	});

	$(".return-settlement").live("click", function() {
		$("#confirmOrder").show();
		$("#itemlist").hide();
	});
	
	var oBox=document.querySelector('.bill_open');
	
	$('.bill .check-p label input').live('click',function(){
		if(oBox.style.display=='none'){
			oBox.style.display='block';
		}else{
			oBox.style.display='none';
		}	
	});;
	
	$(".bill .ask").live("click", function(){
		window.location.href = $("#path").val() + "/cusOrder/invoiceInfo";
	});
	
	$('.bill_mid').hide();
	$("input[name='one']").live("click", function(){
		if ('0' == $("input[name='one']:checked").val()) {
			$('.bill_mid').hide();
		}
		else {
			$('.bill_mid').show();
		}
		
	});
	
	$("input[name='nr']").live("click", function(){
		if ('0' == $("input[name='nr']:checked").val()) {
			$("#sel_invoice_detail").attr("disabled", "disabled");
		}
		else {
			$("#sel_invoice_detail").removeAttr("disabled");
		}
	});
	
	$('div.bill').hide();
	var itemList = $("li.item-sku");
	for (var i = 0 ; i < itemList.length; i++) {
		var tid = itemList[i].id;
		if (!tid.endsWith("_12")) {
			$("div.bill").show();
			return;
		}
	}
});

function submit() {
	submitOrder = $(this);
	if (submitOrder.attr("disabled")) {
		return;
	}
	submitOrder.attr("disabled", "disabled");
	var submitForm = "<form method='post' name='submit-form'></form>";
	 $("body").find("form[name='submit-form']").remove;
	
	$("body").append(submitForm);

	if (!checkUserRealName()) {
		submitOrder.removeAttr("disabled");
		return;
	}

	if (!setItemInfo()) {
		submitOrder.removeAttr("disabled");
		return;
	}

	if (!setReceiveAddress()) {
		submitOrder.removeAttr("disabled");
		return;
	}

	if (!setCouponsInfo()) {
		submitOrder.removeAttr("disabled");
		return;
	}

	if (!setOtherInfo()) {
		submitOrder.removeAttr("disabled");
		return;
	}
	
/*	if (!setInvoiceInfo()) {
		submitOrder.removeAttr("disabled");
		return;
	}*/
	
	// 清空收货地址的cookie
	setCookie("ccigmall-settlement-address-choosed-id", "");

	doSubmit();
}

function setItemInfo() {

	var datas = $(".item-sku");
	var submitForm = $("form[name='submit-form']");

	for (var j = 0; j < datas.length; j++) {
		var data = $(datas[j]).attr("id").split("_");
		var skuId = data[2];
		submitForm.append("<input type='hidden' name='skuIdList[" + j+ "]' value='" + skuId + "'/>");
	}

	return true;
}

/**
 * 设置收货地址信息
 * @returns {Boolean}
 */
function setReceiveAddress() {
	var addressId = $(".address").find("#addressId").val();
	if (!addressId || addressId == "") {
		$.dialog({
			content : '请选择收货地址！',
			title : '',
			time : 2000,
		});
		return false;
	}

	var submitForm = $("form[name='submit-form']");
	submitForm.append("<input type='hidden' name='addressId' value='"+ addressId + "'/>");

	return true;
}

function setOtherInfo() {
	var submitForm = $("form[name='submit-form']");
	var message = $("textarea[name='message']").val().trim();// 备注
	submitForm.append("<input type='hidden' name='message' value='" + message+ "'/>");

	return true;
}
/**
 * 设置发票信息
 * @returns {Boolean}
 */
function setInvoiceInfo() {

	var submitForm = $("form[name='submit-form']");
	
	if ($(".check-p input[type='checkbox']")[0].checked) {
		submitForm.append("<input type='hidden' name='needInvoice' value='1'/>");
		
		if ('0' == $("input[name='one']:checked").val()) {
			submitForm.append("<input type='hidden' name='invoiceTitle' value='个人'/>");
		}
		else {
			var unitnname = $(".bill_mid input[type='text']").val();
			if (isEmpty(unitnname)) {
				$.dialog({
					content : '请填写发票抬头',
					title : '众聚猫提示',
					time : 1000,
				});
				return false;
			}
			if (unitnname.length > 100) {
				$.dialog({
					content : '发票抬头长度不能超过100字',
					title : '众聚猫提示',
					time : 1000,
				});
				return false;
			}
			submitForm.append("<input type='hidden' name='invoiceTitle' value='" + unitnname + "'/>");
		}
		
		if ('0' == $(".bill_bot input[name='nr']:checked").val()) {
			submitForm.append("<input type='hidden' name='invoiceDetail' value='明细'/>");
		}
		else {
			submitForm.append("<input type='hidden' name='invoiceDetail' value='" + $(".bill_bot select")[0].value + "'/>");
		}
	}
	else {
		submitForm.append("<input type='hidden' name='needInvoice' value='0'/>");
	}
	return true;
}

function setCouponsInfo() {
	var submitForm = $("form[name='submit-form']");

	var itemGroup = $(".item-group");
	for (var i = 0; i < itemGroup.length; i++) {
		var data = $(itemGroup[i]).find(".item-sku").attr("id").split("_");
		var skuId = data[2];
		var couponsId = $(itemGroup[i]).find("input[name='couponIdTxt']").val();
		var couponsType = $(itemGroup[i]).find("input[name='couponTypeTxt']").val();
		// 使用优惠券
		if (!isEmpty(couponsId)) {
			submitForm.append("<input type='hidden' name='orderPromoteRelationsMap["+ skuId+ "]' value='"+ couponsId+ "_"+ couponsType + "'/>");
		}
	}

	return true;
}

function doSubmit() {
	var submitForm = $("form[name='submit-form']");
	var laiyuanflag=$("#laiyuanflag").val();
	/*alert(laiyuanflag);*/
	var url= $("#path").val();
	if(laiyuanflag==1){//立即购买
		url =url+"/order/submitDirectBuy";
	}else if(laiyuanflag==2){//购物车结算
		url = url+"/order/submit";
	}
	
	
	var data = submitForm.serialize()+"&isCheck="+check;
	var dataType = "json";
	var path = $("#path").val();
	$.ajax({
		type : "post",
		url : url,
		data : data,
		async : false,
		dataType : dataType,
		success : function(result) {
			if (!checkResult(result, 0)) {
				submitOrder.removeAttr("disabled");
				return;
			}
			
			if (result.status == '404') {
				location.href = path + "/cart/index";
			} else if (result.status == '504') {
				location.href = path + "/cusInfo/toVerify?currentPage="+ location.href;
			} else if (result.status == '505') {
				location.href = path + "/cart/index";
			}else if (result.status == '506') {
				$.dialog({
					content : '该商品不支持配送到新疆、西藏区域，请选择其他收货地址。',
					title : '众聚猫提示',
					time : 2000,
				});
				submitOrder.removeAttr("disabled");
			}else if (result.status == '507') {
				$.dialog({
					content : '请先购买激活区商品激活账户，再购物',
					title : '众聚猫提示',
					time : 2000,
				});
				submitOrder.removeAttr("disabled");
			}else if (result.status == '508') {
				$.dialog({
					content : '此产品只能未激活会员购买。',
					title : '众聚猫提示',
					time : 2000,
				});
				submitOrder.removeAttr("disabled");
			}else if (result.status == '509') {
				$.dialog({
					content : '已超出限购数量',
					title : '众聚猫提示',
					time : 2000,
				});
				submitOrder.removeAttr("disabled");
			}else if (result.status == '510') {
				$.dialog({
					content : '您的星级不符合商品限定，请挑选其他商品',
					title : '众聚猫提示',
					time : 2000,
				});
				submitOrder.removeAttr("disabled");
			} 
			// 正常情况
			else if (result.status == '200') {
				$.dialog({
					content : '提交订单成功',
					title : '众聚猫提示',
					time : 2000,
				});
				// cps订单数据回传联盟
				if (result.cpsCookieValue && result.cpsCookieValue.length > 0) {
					//var pushUrl = path + "/view/cps/toOrderInfo?cpsCookieValue=" + result.cpsCookieValue +"&cpsOrdersInfo="+result.cpsOrdersInfo;
					var pushUrl = path + "/view/cps/toOrderInfo"
					//var cpsCookieValueStr = result.cpsCookieValue;
					//var cpsOrdersInfoStr = result.cpsOrdersInfo;
					var conStr = "&cpsCookieValue=" + result.cpsCookieValue +"&cpsOrdersInfo="+result.cpsOrdersInfo;
					//$.ajax({url:pushUrl,async:true});
					$.ajax({
						type:"POST",
						url:pushUrl,
						data:conStr,
						async:true,
						dataType:"text",
						success:function(result){
						    //alert(33); 
						  }
					});
				}

				var subOrderDTO = result.subOrderDTO;
				subOrderDTO = subOrderDTO.replace(/\"id\":(\d+)/g,"\"id\":\"$1\"");
				subOrderDTO = subOrderDTO.replace(/\"pid\":(\d+)/g,"\"pid\":\"$1\"");
				subOrderDTO = JSON.parse(subOrderDTO);
				
				
				//php state
				phpStateForOrders(subOrderDTO);
				
				// 单笔订单时跳转支付页
				var redirectUrl;
				var orderDTOs = subOrderDTO.orderDTOs;
				if(orderDTOs.length == 1){
					var is = openType();
					if(is == 'weixin' || is == 'aliPay'){
						redirectUrl = path + "/cusOrder/toWeiXinPay?orderId="+ orderDTOs[0].id;
					}else{
						redirectUrl = path + "/cusOrder/toPay?orderId="+ orderDTOs[0].id;
					}
					
				}else{
					redirectUrl = path+ "/cusOrder/toMyAllOrder?pageNow=1&status=1";
				}
				
				setInterval(function() {
					location.href = redirectUrl;
				}, 2000);

				// 异常情况
			} else {
				showErrorMessage(result);
			}
		}
	});
}

function openType() {
    var ua = navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) === "micromessenger") {
        return 'weixin';
    } else if (ua.match(/Alipay/i) === "alipay") {
        return 'aliPay';
    }
}
function phpStateForOrders(subOrderDTO){
	
	// user name
	var userName = $("#user_name_val").val();
	
	// 多个订单
	var orderDTOs = subOrderDTO.orderDTOs;
	for (var i = 0; i < orderDTOs.length; i++) {
		
		var totalQty = 0;
		var orderDTO = orderDTOs[i];
		
		// 多个商品
		var orderItemDTOs = orderDTO.orderItemDTOs;
		for (var j = 0; j < orderItemDTOs.length; j++) {
			
			// push order item info
			var orderItemDTO = orderItemDTOs[j];
			if(orderItemDTO.productType == 0){
				totalQty += orderItemDTO.skuQty;
				_trackData.push(['addorderitem',orderDTO.id+"",orderItemDTO.pid,orderItemDTO.price,'',orderItemDTO.skuQty]);
			}
		}
		
		// push order info
		_trackData.push(['addorder',orderDTO.id+"", orderDTO.orderPrice+"", orderDTO.totalTax+"", '', totalQty+"", userName+"", "在线支付"]);
		
	}
	
}


/**
 * 检查当前用户是否实名认证
 */
function checkUserRealName() {

/*	var needCheck = false;
	var itemSkus = $(".item-sku");
	for (var i = 0; i < itemSkus.length; i++) {
		var itemSku = itemSkus[i];
		var data = $(itemSku).attr("id").split("_");

		var productType = data[11];
		var checkTypes = ["11","12","21","50","51"];
		if (checkTypes.indexOf(productType) != -1) {
			needCheck = true;
			break;
		}
	}

	if (needCheck) {
		var url = $("#path").val() + "/customer/checkUserRealName";
		var flg = 0;
		$.ajax({
			type : 'GET',
			url : url,
			async : false,
			dataType : 'text',
			success : function(result) {
				if (!checkResult(result, 1)) {
					flg = 1;
				}
				if (result == "false") {
					flg = 2;
				}
			}
		});
		if (flg == 1) {
			return false;
		}
		if (flg == 2) {
			var title = "众聚猫提示";
			var content = "免税店商品需要办理入境申报，请您配合进行实名认证";
			var cancelButton = "取消";
			var sureButton = "去认证";
			var clickUrl = $("#path").val() + "/cusInfo/toVerify?currentPage="+ location.href;
			var jsFunction = null;
			showConfirm(title, content, cancelButton, sureButton, clickUrl,jsFunction);

			return false;
		}
	}*/
	return true;
}

function showErrorMessage(subOrderDTO) {
	var resultCodesStr = subOrderDTO.resultCodes;
	var path = $("#path").val();
	var resultCodes = resultCodesStr;
	var content = "系统未知错误，请稍候重试";
	var returnToCart = true;
	var currentCode = "";
	for ( var i = 0; i < resultCodes.length; i++) {
		var code = resultCodes[i].code;
		var pName = resultCodes[i].pName;
		var skuName = resultCodes[i].skuName;
		switch (code) {
		case 'E001':
			content = getErrorMessage(subOrderDTO.message, "抱歉，此商品已热卖脱销，请随时关注到货情况！请返回购物车重新下单！");
			break;
		case 'E002':
		case 'E003':
		case 'E004':
			/*content = "回写促销活动流水失败";
			break;
			content = "回写优惠券流水失败";
			break;*/
			content = getErrorMessage(subOrderDTO.message, "提交订单失败");
			break;
//		case '0001':
//			content = getErrorMessage(subOrderDTO.message, pName + " " + skuName + " 商品已下架，请重新选择商品");
//			break;
//		case '0002':
//			content = getErrorMessage(subOrderDTO.message, pName + " " + skuName + " 商品价格变动，请重新结算");
//			break;
//		case '0003':
//			content = getErrorMessage(subOrderDTO.message, pName + " " + skuName + " 商品税率变动，请重新结算");
//			break;
//		case '0004':
//			content = getErrorMessage(subOrderDTO.message, pName + " " + skuName + " 商品税额变动，请重新结算");
//			break;
//		case '0005':
//			content = getErrorMessage(subOrderDTO.message, "当前收货地址下，" + pName + " " + skuName + " 商品库存不足，请修改收货地址");
//			returnToCart = false;
//			submitOrder.removeAttr("disabled");
//			break;
//		case '0006':
//			content = getErrorMessage(subOrderDTO.message, pName + " " + skuName + " 商品超过最大允许购买金额，请重新选择商品");
//			break;
		case '0007':
			content = getErrorMessage(subOrderDTO.message, code, pName + " " + skuName + " 赠品库存不足");
			currentCode = '0007';
			submitOrder.removeAttr("disabled");
			break;
		case '0008':
			content = getErrorMessage(subOrderDTO.message, "温馨提示：保税区商品订单优惠金额不能≤0元支付");
			returnToCart = false;
			submitOrder.removeAttr("disabled");
			break;
		default:
			content = getErrorMessage(subOrderDTO.message, "提交订单失败");
			break;
		}
	}
	if (currentCode != '0007') {
		var cartIndexUrl = path + "/cart/index";
		$.dialog({
			content : content,
			title : '众聚猫提示',
			width : '241px',
			height : 'auto',
			ok : function() {
				if (returnToCart) {
					location.href = cartIndexUrl;
				}
			},
			lock : true
		});
	} else {
		$.dialog({
			content : content,
			title : '众聚猫提示',
			ok : function() {
				check = false;
				submit();
			},
			cancel : function() {
				check = true;
			},
			lock : false
		});
	}
}

function getErrorMessage(msg, defaultMsg) {
	
	if (msg && null != msg && "" != msg) {
		return msg;
	}
	return defaultMsg;
}

function checkResult(result, type) {
	var path = $("#path").val();
	var loginUrl = path + "/customer/toLogin";
	if (type != '0') {
		result = JSON.parse(result);
	}
	var noErrors = true;
	if (result.status == "needLogin") {
		location.href = loginUrl;
		noErrors = false;
	}
	if (result.errorMessage && result.errorMessage.length > 0) {
		noErrors = false;
		$.dialog({
			content : '系统繁忙，请稍后重试',
			title : '众聚猫提示',
			time : 2000,
		});
	}

	return noErrors;
}

function textCounter() {

	$("#textarea").keydown(function() {
		var curLength = $("#textarea").val().length;
		if (curLength > 45) {
			var num = $("#textarea").val().substr(0, 45);
			$("#textarea").val(num);
			$.dialog({
				content : "超过45个字了,亲!",
				title : '众聚猫提示',
				time : 2000,
			});
		}
		$("#textarea").keyup(function() {
			var curLength = $("#textarea").val().length;
			if (curLength > 45) {
				var num = $("#textarea").val().substr(0, 45);
				$("#textarea").val(num);
				$.dialog({
					content : "超过45个字了,亲!",
					title : '众聚猫提示',
					time : 2000,
				});

			};
		});
	});

}
