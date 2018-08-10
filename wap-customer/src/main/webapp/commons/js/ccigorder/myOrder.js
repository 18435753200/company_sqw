/**
 * 删除订单（废弃）
 * @param orderId
 */
function delOrder(orderId) {
	showErrorMsg("能不光点不？！ 这块还没弄");
	return;
	if (isEmpty(orderId)) {
		showErrorMsg("删除订单失败");
		return;
	}

	$.dialog({
		content : '确定删除吗？',
		title : '众聚猫提示',
		ok : function() {

			var _dataType = "text";
			var _type = "POST";
			var _url = $("#path").val() + "/cusOrder/delOrder";
			var _async = true;
			var _data = "orderId=" + orderId;
			$.ajax({
				dataType : _dataType,
				type : _type,
				url : _url,
				data : _data,
				async : _async,
				success : function(res) {
					if (res == "ok") {
						showErrorMsg("删除订单成功");
						window.location = $("#path").val()+ "/cusOrder/toMyAllOrder?pageNow=1&status=";
					} else {
						showErrorMsg("删除失败");
					}
				},
				error : function() {
					showErrorMsg("网络连接超时，请您稍后重试");
				}
			});

		},
		cancel : function() {

		},
		lock : false
	});

}

/**
 * 查看更多 订单
 */
function findOrderMore() {
	var pageNow = $("#pageNow").val();
	var status = $("#status").val();
	var totalPage = $("#totalPage").val();
	/*
	 * if (totalPage == pageNow) { $(".load-more").hide();
	 * showErrorMsg("没有更多订单信息"); return; }
	 */
	$(".load-more").hide();

	$(".load-more").before(
			"<div id='moreOrder" + (parseInt(pageNow) + 1) + "' > </div>");
	var url = $("#path").val() + "/cusOrder/toMyAllOrder?pageNow="
			+ (parseInt(pageNow) + 1) + "&status=" + status + " #orderShow";
	$("#moreOrder" + (parseInt(pageNow) + 1) + "").load(url);
	$("#pageNow").val(parseInt(pageNow) + 1);
}

/**
 * 查看更多 订单
 */
function findSupplierOrderMore() {
	var pageNow = $("#pageNow").val();
	var status = $("#status").val();
	var totalPage = $("#totalPage").val();
	/*
	 * if (totalPage == pageNow) { $(".load-more").hide();
	 * showErrorMsg("没有更多订单信息"); return; }
	 */
	$(".load-more").hide();

	$(".load-more").before(
			"<div id='moreOrder" + (parseInt(pageNow) + 1) + "' > </div>");
	var url = $("#path").val() + "/cusOrder/toMyAllSupplierOrder?pageNow="
			+ (parseInt(pageNow) + 1) + "&status=" + status + " #orderShow";
	$("#moreOrder" + (parseInt(pageNow) + 1) + "").load(url);
	$("#pageNow").val(parseInt(pageNow) + 1);
}
//查询订单
function findOrderByStatus(status) {
	if (status == "0" || status == 0) {
		window.location = $("#path").val() + "/cusOrder/toMyAllOrder?pageNow="+ 1;
	} else {
		window.location = $("#path").val() + "/cusOrder/toMyAllOrder?pageNow="+ 1 
		+ "&status=" + status;
	}
}
// 查询订单
function findSupplierOrderByStatus(status) {
	if (status == "0" || status == 0) {
		window.location = $("#path").val() + "/cusOrder/toMyAllSupplierOrder?pageNow="+ 1;
	} else {
		window.location = $("#path").val() + "/cusOrder/toMyAllSupplierOrder?pageNow="+ 1 
		+ "&status=" + status;
	}
}
var _trackDataType = 'wap'; // 标记数据来源，参数是web和wap，可以为空，默认是web
var _Schannel_website_id = '10000006';// 分站编号，不存在可不写此变量或者留空
var _Schannel_webshop_id = 'shop_id'; // 商铺编号，不存在可不写此变量或者留空
var _trackData = _trackData || [];// 必须为全局变量，假如之前并没有声明，请加此行代码；
// 取消订单
function cancleOrder(orderId) {

	$.dialog({
		content : '你确认要取消这个订单吗？',
		title : '众聚猫提示',
		ok : function() {

			var _dataType = "text";
			var _type = "POST";
			var _url = $("#path").val() + "/cusOrder/cancleOrder";
			var _async = true;
			var _data = "orderId=" + orderId;
			$.ajax({
				dataType : _dataType,
				type : _type,
				url : _url,
				data : _data,
				async : _async,
				success : function(res) {
					if (res == "ok") {
						_trackData.push([ 'delorder',orderId+""]);
						var redirectUrl = $("#path").val()+ "/cusOrder/toMyAllOrder?pageNow=1&status=";
						okBT("取消订单成功!", redirectUrl);
						return;
					} else {
						showErrorMsg("取消失败");
					}
				},
				error : function() {
					showErrorMsg("网络连接超时！");
				}
			});

		},
		cancel : function() {},
		lock : false
	});
}

/**
 * 去支付
 * @param orderId
 */
function toPay(orderId) {
	// showErrorMsg("支付功能正在完善,请使用手机客户端进行支付");
	var is_weixin_flag = is_weixin();
	if(is_weixin_flag){
		window.location = $("#path").val() + "/cusOrder/toWeiXinPay?orderId=" + orderId;
	}else{
		window.location = $("#path").val() + "/cusOrder/toPay?orderId=" + orderId;
	}
}

function is_weixin() {
    var ua = navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == "micromessenger") {
        return true;
    } else {
        return false;
    }
}

/**
 * 确认收货
 * @param orderId
 */
function confirmOrder(obj, orderId, shipId) {

	if (isEmpty(shipId)) {
		shipId = "";
	}

	$.dialog({
		content : '确定收货吗？',
		title : '众聚猫提示',
		ok : function() {
			var url = $("#path").val() + "/cusOrder/confirmOrder?orderId="+ orderId + "&shipId=" + shipId;
			$.get(url, function(data) {
				if (data == 91) {
					window.location = $("#path").val()+ "/cusOrder/receiptSuccess/" + orderId;
				} else if (data == "ok") {
					/*
					 * $(obj).removeClass("package-btn-link");
					 * $(obj).addClass("package-btn-default");
					 * $(obj).attr("disabled","disabled"); $(obj).html("已收货");
					 */
					location.reload();
				} else {
					showErrorMsg("确认收货失败，请联系客服");
				}
			});

		},
		cancel : function() {},
		lock : false
	});

}

function showErrorMsg(str) {
	// $(".error_tips").removeClass("hide");
	// $(".error_tips").html(str);
	$.dialog({
		content : str,
		title : '众聚猫提示',
		time : 1000,
	});
	return;
}

//function goCancleOrderPage(orderId){
//	var url = $("#path").val() + "/cusOrder/cancleOrderPage?orderId="+ orderId;
//	window.location.href = url;
//}
//
//function cancelOrder2(){
//	var orderId = $("input[name='orderId']").val();
//	var orderCancelReason = $("select[name='orderCancelReason']").val();
//	var orderCancelDetail = $("textarea[name='orderCancelDetail']").val();
//	
//	if (isEmpty(orderCancelReason)) {
//		$.dialog({
//			content : '请选择订单取消原因',
//			title : '众聚猫提示',
//			time : 1000,
//		});
//		return;
//	}
//	
//	$.dialog({
//		content : '你确认要取消这个订单吗？',
//		title : '众聚猫提示',
//		ok : function() {
//
//			var _dataType = "text";
//			var _type = "POST";
//			var _url = $("#path").val() + "/cusOrder/userCancelOrder";
//			var _async = true;
//			var _data = "orderId=" + orderId + "&orderCancelReason=" + orderCancelReason + "&orderCancelDetail=" + orderCancelDetail;
//			$.ajax({
//				dataType : _dataType,
//				type : _type,
//				url : _url,
//				data : _data,
//				async : _async,
//				success : function(res) {
//					if (res == "2") {
//						var redirectUrl = $("#path").val()+ "/cusOrder/toMyAllOrder?pageNow=1&status=";
//						okBT("取消订单成功!", redirectUrl);
//						return;
//					} else {
//						showErrorMsg("取消失败");
//					}
//				},
//				error : function() {
//					showErrorMsg("网络连接超时！");
//				}
//			});
//
//		},
//		cancel : function() {},
//		lock : false
//	});
//}