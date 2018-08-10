var doing;
var path;
var checkGift;

var _trackDataType = 'wap'; // 标记数据来源，参数是web和wap，可以为空，默认是web
var _Schannel_website_id = '10000006';// 分站编号，不存在可不写此变量或者留空
var _trackData = _trackData || [];// 必须为全局变量，假如之前并没有声明，请加此行代码；

// 是否编辑状态
var isEditStatus = false;

$(document).ready(function() {

	doing = false;
	path = $("#path").val();
	checkGift = true;

	// get cart
	// getCart();

	// cart init
	init();

	// add event
	addEvent();

	// cart delete item
	$(".del-btn").live("click", deleteItem);

	// cart update number
	$(".amount-down").live("click", amountDown);
	$(".amount-input").live("blur", amountInput);
	$(".amount-up").live("click", amountUp);

	// cart calculate price
	$("input[name='sku-checkbox']").live("change", toggleCheckAll);
	$("input[name='checkAll']").live("change", checkAll);

	// cart settlement
	$(".settlement-cart a").live("click", settlement);

	// select activity
	$("input[name='activity-checkbox']").live("click", selectActivity);

});

function getCart() {
	$.ajax({
		type : "post",
		dataType : "html",
		async : false,
		url : path + "/cart/index",
		success : function(result) {
			$("#cartContent").html(result);
		}
	});
}

function init() {
	// 全选按钮选中
	var allSkus = $("input[name='sku-checkbox']");
	var checkedSkus = $("input[name='sku-checkbox']:checked");
	if (allSkus.length == checkedSkus.length) {
		$("input[name='checkAll']").prop("checked", true);
	}
	/*
	 * // 右上角完成隐藏、下面全选删除隐藏 $(".edit-cart,.edit-finished").hide();
	 * 
	 * if (allSkus.length > 0) { // 右上角编辑显示、下面结算显示
	 * $(".show-edit,.settlement-cart").show(); } else {
	 * $(".show-edit,.settlement-cart").hide(); }
	 */

	// 最下面导航的高亮显示样式
	$(".footer li.catset").each(function() {
		$(this).addClass("set").add().siblings().removeClass("set");
	});
}

function addEvent() {
	// 编辑、完成
	$(".show-edit,.edit-finished").live("click", function() {
		$(".show-edit").toggle();
		$(".edit-finished").toggle();
		$(".edit-cart").toggle();
		$(".settlement-cart").toggle();

		isEditStatus = !isEditStatus;
	});

	$('h3.pro-down').live('click', function() {
		$(this).parent().find('.promotion-list').show();
		$(this).hide();
		$(this).parent().find('.pro-up').show();
	})
	$('h3.pro-up').live('click', function() {
		$(this).parent().find('.promotion-list').hide();
		$(this).hide();
		$(this).parent().find('.pro-down').show();
	})
}

/**
 * 删除购物车商品
 */
function deleteItem() {
	var cur = $(this);

	var data = "";

	var activityGroups = $(".cart-group").find(".cart-activity-group");
	for (var i = 0; i < activityGroups.length; i++) {
		var activityGroup = activityGroups.eq(i);

		var skus = activityGroup.find("input[name='sku-checkbox']");
		for (var j = 0; j < skus.length; j++) {

			var sku = skus.eq(j);
			if (sku.is(":checked")) {

				var curData = sku.parents(".cart-item").attr("id").split("_");

				if (data != "") {
					data += "&";
				}
				data += "skuIdList=" + curData[2];
				_trackData.push([ 'delcartitem', curData[2] ]);
			}
		}
	}

	if (data == "") {
		$.dialog({
			content : '至少选择一件商品',
			title : '众聚猫提示',
			time : 2000,
		});

		return;
	}

	// 确定是否删除
	$.dialog({
		content : '确定删除吗？',
		title : '众聚猫提示',
		ok : function() {
			var type = "post";
			var dataType = "text";
			var url = $("#path").val() + "/cart/delete";

			$.post(url, data, function(result) {

				if (!checkResult(result)) {
					return;
				}
				location.reload();
			});
		},
		cancel : function() {

		},
		lock : false
	});

}

/**
 * 计算购物车商品总额
 */
function calculate() {

	if (isEditStatus && $(".show-edit").css("display") == "none"
			&& $(".settlement-cart").css("display") == "none") {
		return;
	}

	// 获取所有选中状态的商品
	var checkedSku = $("input[name='sku-checkbox']:checked");

	var requestData = "";
	if (checkedSku.length != 0) {
		// 拼装请求参数
		$.each(checkedSku, function(i, sku) {
			var data = $(sku).parents("[id*='data']").attr("id").split("_");
			var skuId = data[2];
			if (i == 0) {
				requestData += "skuIdList[" + i + "]=" + skuId;
			} else {
				requestData += "&skuIdList[" + i + "]=" + skuId;
			}
		});
	}
	var url = $("#path").val() + "/cart/changeSelect";
	var data = requestData;

	$.ajax({
		type : "post",
		data : data,
		dataType : "html",
		url : url,
		success : function(result) {
			$("#cartContent").html(result);
			init();
		}
	});

}
/**
 * 结算
 */
function settlement() {
	// 获取所有选中状态的商品
	var checkedSku = $("input[name='sku-checkbox']:checked").not(
			"input[isSoldout='0']");

	// 检查订单金额
	if (!checkOrderPrice()) {
		return;
	}

	// 检查赠品库存
	if (checkGift == true) {
		var giftMessage = checkGiftStock();
		if (giftMessage != null) {
			$.dialog({
				content : giftMessage,
				title : '众聚猫提示',
				ok : function() {
					checkGift = false;
					settlement();
				},
				cancel : function() {
					checkGift = true;
				},
				lock : false
			});
			return;
		}
	}

	// 拼装请求参数
	_trackData.push([ 'checkoutcart' ]);
	var url = $("#path").val() + "/cart/settlement";
	var requestData = "<form name='submit-form' method='get' action='" + url
			+ "'>";
	$.each(checkedSku, function(i, sku) {
		var data = $(sku).parents("[id*='data']").attr("id").split("_");
		var skuId = data[2];
		requestData += "<input type='hidden' name='skuIdList[" + i
				+ "]' value='" + skuId + "'/>";
	});
	requestData += "</form>";

	// 渲染页面
	$("body").append(requestData);

	// 提交
	$("form[name='submit-form']").submit();
}

/**
 * 检查结算
 * 
 * @returns {Boolean}
 */
function checkOrderPrice() {

	// 获取所有选中状态的商品
	var checkedSku = $("input[name='sku-checkbox']:checked").not(
			"input[isSoldout='0']");

	// 无选中商品时提示
	if (!checkedSku || !checkedSku.length) {
		$.dialog({
			content : '请至少选择一种商品',
			title : '众聚猫提示',
			time : 2000,
		});
		return;
	}

	$("form[name='check-form']").remove();
	var requestData = "<form name='check-form'>";

	for (var i = 0; i < checkedSku.length; i++) {
		var data = checkedSku.eq(i).parents("[id*='data']").attr("id").split(
				"_");
		var skuId = data[2];
		var qty = data[5];
		if (Number(qty) <= 0) {
			$.dialog({
				content : '购买数量需大于0',
				title : '众聚猫提示',
				time : 2000,
			});
			return false;
		}
		requestData += "<input type='hidden' name='skuIdList[" + i
				+ "]' value='" + skuId + "'/>";
	}

	requestData += "</form>";
	$("body").append(requestData);
	var data = $("form[name='check-form']").serialize();

	var rest = true;
	var url = $("#path").val() + "/cart/checkPrice";
	$
			.ajax({
				type : "post",
				data : data,
				dataType : "text",
				async : false,
				url : url,
				success : function(result) {
					switch (result) {
					case 'CCIGMALL-20000':
						break;
					case 'CCIGMALL-30000':
						$.dialog({
							content : "购买数量需大于0",
							title : '众聚猫提示',
							ok : function() {
							},
							lock : true
						});
						location.reload();
						rest = false;
						break;
					
					case 'CCIGMALL-40000':
						location.reload();
						rest = false;
						break;
					case 'CCIGMALL-50000':
						$
								.dialog({
									content : "根据海关总署要求，单笔订单金额不得超过2000元。不管数量、单件、或多种商品条件，单笔订单金额超过2000元均将被海关退单。<br/>个人年度累积消费金额2万元，超过将被海关退单。",
									title : '众聚猫提示',
									ok : function() {
									},
									lock : true
								});
						rest = false;
						break;
					case 'CCIGMALL-50010':
						$.dialog({
							content : "已超出限购数量",
							title : '众聚猫提示',
							ok : function() {
							},
							lock : true
						});
//						location.reload(); 
						rest = false;
						break;
					case 'CCIGMALL-40010':
						$.dialog({
							content : "您的星级不符合商品限定",
							title : '众聚猫提示',
							ok : function() {
							},
							lock : true
						});
//						location.reload(); 
						rest = false;
						break;
					case 'CCIGMALL-40100':
						$.dialog({
							content : "未激活用户不可购买非激活区商品",
							title : '众聚猫提示',
							ok : function() {
							},
							lock : true
						});
//						location.reload(); 
						rest = false;
						break;
					case 'CCIGMALL-41000':
						$.dialog({
							content : "激活用户不可再购买激活区商品",
							title : '众聚猫提示',
							ok : function() {
							},
							lock : true
						});
//						location.reload(); 
						rest = false;
						break;
					}
				}
			});

	return rest;
}

function checkGiftStock() {
	if (!isEditStatus && $(".show-edit").css("display") == "none"
			&& $(".settlement-cart").css("display") == "none") {
		return;
	}

	// 获取所有选中状态的商品
	var checkedSku = $("input[name='sku-checkbox']:checked");

	var requestData = "";
	if (checkedSku.length != 0) {
		// 拼装请求参数
		$.each(checkedSku, function(i, sku) {
			var data = $(sku).parents("[id*='data']").attr("id").split("_");

			var skuId = data[2];
			if (i == 0) {
				requestData += "skuIdList[" + i + "]=" + skuId;
			} else {
				requestData += "&skuIdList[" + i + "]=" + skuId;
			}
		});
	}
	var url = $("#path").val() + "/cart/calculate";
	var data = requestData;

	var giftMessage = null;
	$.ajax({
		type : "post",
		data : data,
		dataType : "json",
		async : false,
		url : url,
		success : function(result) {
			var cartDTO = result;

			var cartGroupVOList = cartDTO.cartGroupVOList;
			for (var i = 0; i < cartGroupVOList.length; i++) {

				var cartGroupVO = cartGroupVOList[i];
				var activityGroupList = cartGroupVO.activityGroupList;
				for (var j = 0; j < activityGroupList.length; j++) {

					var activityGroup = activityGroupList[j];

					if (activityGroup.giftMessage != ''
							&& activityGroup.giftMessage != 'default') {

						giftMessage = activityGroup.giftMessage;

						break;

					}
				}
			}
		}
	});

	return giftMessage;
}

/** *******************update number start************************* */
// 减少数量
function amountDown() {
	if (doing)
		return;

	var cur = $(this);
	if (cur.attr("disabled")) {
		return;
	}

	var number = cur.next().val();
	number--;
	if (number > 0) {
		doing = true;
		updateNumber(cur, number);
	}
}

// 输入数量
function amountInput() {
	if (doing)
		return;

	var cur = $(this);
	var number = cur.val();
	var data = cur.parents("[id*='data']").attr("id").split("_");

	if (isNaN(number) || number <= 0 || !(/^\d+$/.test(number))) {

		$.dialog({
			content : '只能输入非0的正整数！',
			title : '众聚猫提示',
			time : 2000,
		});

		cur.val(data[5]);
		return;
	}

	if (number == data[5]) {
		return;
	}
	doing = true;

	updateNumber(cur, number);
}

// 增加数量
function amountUp() {
	if (doing)
		return;
	doing = true;

	var cur = $(this);
	if (cur.attr("disabled")) {
		doing = false;
		return;
	}

	var number = cur.prev().val();
	number++;

	updateNumber(cur, number);
}

function updateNumber(cur, number) {

	if (!isEditStatus && $(".show-edit").css("display") == "none"
			&& $(".settlement-cart").css("display") == "none") {
		doing = false;
		return;
	}

	var data = cur.parents("[id*='data']").attr("id").split("_");
	if (number > 1073741823) {
		$.dialog({
			content : '商品数量超限',
			title : '众聚猫提示',
			time : 2000,
		});
		cur.val(data[5]);
		doing = false;
		return;
	}

	if (Number(data[8]) == 0) {
		return;
	}

	var url = $("#path").val() + "/cart/update";
	var reqData = "skuId=" + data[2] + "&number=" + number;
	$.post(url, reqData, function(result) {
		var cartResultDTO =JSON.parse(result); 
		//alert(cartResultDTO);
//		alert(cartResultDTO.resultMsg);
		if (!checkResult(result)) {
			return;
		}
		else if (cartResultDTO.resultMsg == "50001") {
			$.dialog({
				content : '超出库存！',
				title : '众聚猫提示',
				time : 2000,
			});

//			return;
		} else if (cartResultDTO.resultMsg == "50010") {
			$.dialog({
				content : '已超出限购数量！',
				title : '众聚猫提示',
				time : 2000,
			});
		}

		getCart();
		init();

		doing = false;
	});
}

/**
 * 检验库存量
 */
function checkStock(cur, number) {
	var data = cur.parents("[id*='data']").attr("id").split("_");
	var stockNumber = data[6];

	if (Number(number) > Number(stockNumber)) {
		$.dialog({
			content : '超出库存！',
			title : '众聚猫提示',
			time : 2000,
		});
		return false;
	}

	return true;
}

function checked4UpdateNumber(cur) {
	var curCheckBox = cur.parents(".cart-bd").find(
			".check-box input[name='sku-checkbox']");
	if (curCheckBox.is(":checked")) {
		return;
	}
	curCheckBox.prop("checked", "checked");
}
/** *******************update number end************************* */

function setValue(cur, result) {
	var cartDTO = result;

	// 合计
	$(".total-fore1").find("b").text(
			(Number(cartDTO.sumPrice - cartDTO.transferPrice)).toFixed(2));

	var data = cur.parents("[id*='data']").attr("id").split("_");
	var skuId = data[2];

	// 循环商品分组
	var cartGroupVOList = cartDTO.cartGroupVOList;
	for (var i = 0; i < cartGroupVOList.length; i++) {
		var cartGroupVO = cartGroupVOList[i];
		var groupProductType = cartGroupVO.groupProductType;

		// 循环商品活动分组
		var activityGroupList = cartGroupVO.activityGroupList;
		for (var j = 0; j < activityGroupList.length; j++) {

			// 循环活动分组内的商品
			var skuList = activityGroupList[j].skuList;
			for (var k = 0; k < skuList.length; k++) {

				var sku = skuList[k];
				if (skuId == sku.skuId) {
					// 数量
					var qty = sku.qty;
					cur.parents(".amount-control").find("input").val(qty);

					// 单价
					var price = sku.price;
					var straightDownPrice = sku.straightDownPrice;
					var promotionPrice = Number(price)
							- Number(straightDownPrice);
					cur.parents(".goods-info").find(".goods-price")
							.find("span").text(promotionPrice.toFixed(2));

					// 税、小计
					var cartbar = cur.parents(".cart-group").find(".cart-bar");
					var cartbarTag = "";
					if (groupProductType == 11 || groupProductType == 12) {
						cartbarTag += "<span><em>税费：</em>	 "
								+ cartGroupVO.sumTax.toFixed(2);
						if (cartGroupVO.sumTax <= 50) {
							cartbarTag += "<i>免税</i>";
						} else {
							cartbarTag += "<i>征税</i>";
						}
						cartbarTag += "</span>";
					}
					var sumPrice = Number(cartGroupVO.sumPrice)
							- Number(cartGroupVO.transferPrice);
					cartbarTag += "<span><em>合计：</em> " + sumPrice.toFixed(2)
							+ "</span>";
					cartbar.html(cartbarTag);

					// 最新数据
					var prefix = "data_";
					var pid = sku.pid + "_";
					var skuId = sku.skuId + "_";
					var price = sku.price + "_";
					promotionPrice = promotionPrice + "_";
					var qty = sku.qty + "_";
					var stockQty = sku.stockQty + "_";
					sumPrice = sumPrice + "_";
					var isSoldOut = sku.isSoldOut + "_";
					var productType = sku.productType;
					cur.parents(".cart-item").attr(
							"id",
							prefix + pid + skuId + price + promotionPrice + qty
									+ stockQty + sumPrice + isSoldOut
									+ productType);

					// 赠品
					var currentPromotionGroupTag = cur
							.parents(".cart-activity-group"); // 当前活动分组
					currentPromotionGroupTag.find(".cart-gift").remove(); // 移除所有赠品
					// 循环活动分组内的赠品
					var giftProductList = activityGroupList[j].giftList;
					// 无赠品情况
					if (!giftProductList || giftProductList.length == 0) {
						continue;
					}
					for (var p = 0; p < giftProductList.length; p++) {
						var giftProduct = giftProductList[p];

						var giftProductTag = '<div class="cart-gift"><span></span>';
						giftProductTag += '<p>' + giftProduct.pName + ' '
								+ giftProduct.skuName + '×' + giftProduct.qty
								+ '</p></div>';

						currentPromotionGroupTag.append(giftProductTag);
					}

					return;
				}
			}
		}
	}
}

function selectActivity() {
	$(this).parents(".cart-activity-group").find(
			"input[name='activity-checkbox']").prop("checked", false);
	$(this).prop("checked", "checked");

	var data = $(this).parents(".cart-item").attr("id").split("_");
	var skuId = data[2];

	var ruleId = $(this).attr("ruleId");
	var ruleTerm = $(this).attr("ruleTerm");
	var ruleName = $(this).attr("ruleName");

	var str = skuId + ",";
	str += ruleId + ",";
	str += ruleTerm + ",";
	str += ruleName;

	if (str == "") {
		return;
	}
	$.ajax({
		type : "post",
		data : {
			skuAndactivityInfo : str
		},
		dataType : "html",
		url : path + "/cart/index",
		success : function(result) {
			$("#cartContent").html(result);
			init();
		}
	});
}

/**
 * 全选 选中状态切换
 */
function toggleCheckAll() {
	var allSku = $("input[name='sku-checkbox']").length;
	var checkedSku = $("input[name='sku-checkbox']:checked").length;
	var checkAll = $("input[name='checkAll']");

	if (allSku == checkedSku) {
		checkAll.prop("checked", "checked");
	} else {
		checkAll.prop("checked", false);
	}
	calculate();

}

function checkAll() {
	var cur = $(this);
	if (cur.is(":checked")) {
		// 全选
		$("input[name='sku-checkbox']").prop("checked", "checked");
	} else {
		// 取消全选
		$("input[name='sku-checkbox']").prop("checked", false);
	}

	calculate();
}

function checkResult(result) {
	var path = $("#path").val();
	var loginUrl = path + "/customer/toLogin";
	result = JSON.parse(result);
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
