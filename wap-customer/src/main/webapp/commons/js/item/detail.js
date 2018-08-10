var path;
swipe = [];
function createDom(paramsObj) {
	var obj = paramsObj;
	var $body = $('body');
	switch (obj.type) {
	case 4:
		/*
		 * type: 固定值4 必填项 msg: 弹出的消息 必填项 cancelCallback: 取消按钮回调 非必填项
		 * sureCallback: 确定按钮回调 非必填项
		 */
		var objCancelSureFunc = {};
		var htmlSureCancel = [];
		htmlSureCancel
				.push('<div class="rl-dialog-wp" style="display: block;position: fixed;top: 0;left: 0;background: rgba(0, 0, 0, 0.5);width: 100%;height: 100%;text-align: center;z-index: 9999;">');
		htmlSureCancel
				.push('<div class="shw-msg" style="display: block;width: 80%;padding: 0 0;text-align: center;position: absolute;top: 50%;left: 50%;-webkit-transform: translate(-50%, -50%);-moz-transform: translate(-50%, -50%);-ms-transform: translate(-50%, -50%);-o-transform: translate(-50%, -50%);transform: translate(-50%, -50%);">');
		htmlSureCancel
				.push('<div class="sure-txt" style="padding: 8% 5%;background: #fff;color: #000;border-bottom: 1px solid #DCDCDF;border-radius: 15px 15px 0 0;word-break: break-all;">'
						+ obj.msg + '</div>');
		htmlSureCancel
				.push('<div class="cancel-btn" style="box-sizing: border-box;width: 50%;padding: 3.5% 0 3.5% 5%;float: left;background: #fff;color: #057CFF;font-weight: bolder;border-radius: 0 0 0 15px;border-right: 1px solid #DCDCDF;-webkit-user-select: none;-moz-user-select: none;-ms-user-select: none;user-select: none;">立即购买</div>');
		htmlSureCancel
				.push('<div class="sure-btn" style="box-sizing: border-box;width: 50%;padding: 3.5% 5% 3.5% 0;float: right;background: #fff;color: #057CFF;font-weight: bolder;border-radius: 0 0 15px 0;-webkit-user-select: none;-moz-user-select: none;-ms-user-select: none;user-select: none;">再逛逛</div>');
		htmlSureCancel.push('</div>');
		htmlSureCancel.push('</div>');
		$body.append(htmlSureCancel.join(''));

		if (!(obj.cancelCallback && Object.prototype.toString.call(
				obj.cancelCallback).indexOf('Function') > -1)) {
			obj.cancelCallback = function() {
			};
		}

		if (!(obj.sureCallback && Object.prototype.toString.call(
				obj.sureCallback).indexOf('Function') > -1)) {
			obj.sureCallback = function() {
			};
		}

		objCancelSureFunc.btnCancelTapEvent = function(ckTmp) {
			$body.find('.rl-dialog-wp .cancel-btn').on('mousedown', function() {
				$(this).css({
					"background" : "#DCDCDF"
				});
				return false;
			}).on('mouseup', function() {
				$(".rl-dialog-wp").remove();
				ckTmp();
				return false;
			});
		};

		objCancelSureFunc.btnSureTapEvent = function(ckTmp) {
			$body.find('.rl-dialog-wp .sure-btn').on('mousedown', function() {
				$(this).css({
					"background" : "#DCDCDF"
				});
				return false;
			}).on('mouseup', function() {
				$(".rl-dialog-wp").remove();
				ckTmp();
				return false;
			});
		};

		objCancelSureFunc.btnCancelTapEvent(obj.cancelCallback);
		objCancelSureFunc.btnSureTapEvent(obj.sureCallback);
		break;
	case 5:
		/*
		 * type: 固定值4 必填项 msg: 弹出的消息 必填项 cancelCallback: 取消按钮回调 非必填项
		 * sureCallback: 确定按钮回调 非必填项
		 */
		var objCancelSureFunc = {};
		var htmlSureCancel = [];
		htmlSureCancel
				.push('<div class="rl-dialog-wp" style="display: block;position: fixed;top: 0;left: 0;background: rgba(0, 0, 0, 0.5);width: 100%;height: 100%;text-align: center;z-index: 9999;">');
		htmlSureCancel
				.push('<div class="shw-msg" style="display: block;width: 80%;padding: 0 0;text-align: center;position: absolute;top: 50%;left: 50%;-webkit-transform: translate(-50%, -50%);-moz-transform: translate(-50%, -50%);-ms-transform: translate(-50%, -50%);-o-transform: translate(-50%, -50%);transform: translate(-50%, -50%);">');
		htmlSureCancel
				.push('<div class="sure-txt" style="padding: 8% 5%;background: #fff;color: #000;border-bottom: 1px solid #DCDCDF;border-radius: 15px 15px 0 0;word-break: break-all;">'
						+ obj.msg + '</div>');
		htmlSureCancel
				.push('<div class="sure-btn" style="box-sizing: border-box;width: 100%;padding: 3.5% 5% 3.5% 0;float: right;background: #fff;color: #057CFF;font-weight: bolder;border-radius: 0 0 15px 0;-webkit-user-select: none;-moz-user-select: none;-ms-user-select: none;user-select: none;">确定</div>');
		htmlSureCancel.push('</div>');
		htmlSureCancel.push('</div>');
		$body.append(htmlSureCancel.join(''));

		if (!(obj.cancelCallback && Object.prototype.toString.call(
				obj.cancelCallback).indexOf('Function') > -1)) {
			obj.cancelCallback = function() {
			};
		}

		if (!(obj.sureCallback && Object.prototype.toString.call(
				obj.sureCallback).indexOf('Function') > -1)) {
			obj.sureCallback = function() {
			};
		}

		objCancelSureFunc.btnCancelTapEvent = function(ckTmp) {
			$body.find('.rl-dialog-wp .cancel-btn').on('mousedown', function() {
				$(this).css({
					"background" : "#DCDCDF"
				});
				return false;
			}).on('mouseup', function() {
				$(".rl-dialog-wp").remove();
				ckTmp();
				return false;
			});
		};

		objCancelSureFunc.btnSureTapEvent = function(ckTmp) {
			$body.find('.rl-dialog-wp .sure-btn').on('mousedown', function() {
				$(this).css({
					"background" : "#DCDCDF"
				});
				return false;
			}).on('mouseup', function() {
				$(".rl-dialog-wp").remove();
				ckTmp();
				return false;
			});
		};

		objCancelSureFunc.btnCancelTapEvent(obj.cancelCallback);
		objCancelSureFunc.btnSureTapEvent(obj.sureCallback);
		break;
	}
}
$(document).ready(
		function() {

			path = $("#path").val();
			pid = $("#PID").val();

			// 处理轮播图
			slideShow();

			// 初始化，显示客户默认收货地址
			// showDefaultRegion();

			// 初始化为第一个展示属性和展示属性对应的第一个sku添加样式
			init();

			// 购物车商品数量
			cartQty();

			// 切换规格（属性名） 新增
			$("li[showattrvalid]").live("click", switchSku);
			// 屏蔽 zhegnns $("li[showattrvalid]").live("click",changePriceNew);
			// 切换规格（属性值） 新增
			$("li[attrValIdV]").live("click", switchSkuValue);
			// //屏蔽 zhegnns $("li[attrValIdV]").live("click",changePriceNew);

			// 购买数量 加
			$(".amount-up").live("click", amountUp);

			// 购买数量 减
			$(".amount-down").live("click", amountDown);

			// 购买数量 手动修改
			$(".amount-control").delegate("input", "input propertychange",
					amountInput);

			// 选择 配送地址
			$("#btn-select-region").live("click", selectRegion);

			// 关闭 选择 配送地址
			$("#mask,.closeBtn").live("click", closeSelectRegion);

			// 选择地区
			$(".filter-bd").on("click", ".data_area", function() {
				$("#stock_city_item").hide();
				$("#stock_area_item").show();
			});
			$(".filter-bd").on("click", ".select_area_js", function() {
				var stock = $("#stock_province_item li.active").text();
				var city = $("#stock_city_item li.active").text();
				var area = $("#stock_area_item li.active").text();
				var velcity = stock + " " + city + " " + area;
				$(".address span").text("");
				$(".address span").append(velcity);

			}); // 选择地区

			// 立即购买
			$(".buy-btn").on("click", directBuy);
			// 星级提示
			var startip = document.getElementById("startip");
			//alert(startip.innerHTML);
			// 添加现金比例的商品不能加购物车
			var xjzfblbq = document.getElementById("xjzfblbq").value;
			// 家庭号发布的商品如果家庭号被冻结或过期不能进行交易
			var homeShangPin = document.getElementById("homeShangPin").value;
			var homeStates = document.getElementById("homeStates").value;
			// 会员企业号发布的商品如果会员企业号被冻结或过期不能进行交易
			var cbusStates = document.getElementById("cbusStates").value;
			
			if (xjzfblbq == 1) { // 添加购物车阴影 //
				$(".addcart-btn").css("background-color", "#eaeaea");
				$(".navbar-addCart").hide();
				$(".navbar-cart").hide();
				$(".good-addCart").hide();
				$(".navbar-buy").css("width", "100%");
				$(".good-buy").css("width", "100%");
			} else if (homeShangPin == 5 && homeStates != 3) {
				$(".navbar").hide();
				$(".good").hide();
				$(".homeTips").css("display", "block");

			} else {
				// 加入购物车
				$(".addcart-btn").live("click", addCart);
			}

			// window.onresize=colour;
			// colour();
			// closes();
			var aLi = document.querySelectorAll('.detail_atit li');
			var aDiv = document.querySelectorAll('.detail_abot .has');
			goodtab(aLi, aDiv);
			// pull();

			// 选择省市区，添加样式,去除同辈元素
			$(".filter-bd li").live("click", chooseRegion);

			// 切换规格属性 联动
			changeG();

			// 显示当前选择的规格
			editGuige();
		});

// 校验输入是否为数字
function regint(num) {
	return /^[\d]+$/.test(num);
}
// 截取字符串，小数点后两位
function splitString(str) {
	if (null != str) {
		var index = str.indexOf(".");
		if (index == -1) {
			return str;
		}
		return str.substring(0, index + 3);
	}
}

var Flash = function() {
	var imgh = $('.no-b').height();
	$('.shop_cart a').css({
		"height" : imgh - 15 + "px"
	});

	// 首页焦点图
	new Swiper('#detail-container', {
		pagination : '#detail-pagination',
		// nextButton: '#index-button-next',
		// prevButton: '#index-button-prev',
		slidesPerView : 1,
		paginationClickable : true,
		spaceBetween : 0,
		loop : true
	});// JavaScript Document
};

/*
 * function colour(){ var oHeight=document.documentElement.clientHeight; var
 * oTop=document.querySelector('.colour_top'); var
 * oBot=document.querySelector('.colour_bot');
 * 
 * oTop.style.height=oHeight-oBot.offsetHeight+'rem'; }
 */

/*
 * function closes(){ var oGood=document.getElementById('goods'); var
 * oColo=document.querySelector('.colour');
 * 
 * oGood.onclick=function(){ oColo.classList.add('show'); var
 * oTop=document.querySelector('.colour_top'); oTop.onclick=function(){
 * oColo.classList.remove('show'); editGuige(); }; }; }
 */

function editGuige() {
	var str = "";
	var gg = $(".color-list li");
	var sx = $(".size-list li");
	// 展示属性匹配“默认”标记
	var isMatch0 = $("#isMatch0").val();
	// 规格属性匹配“默认”标记
	var isMatch = $("#isMatch").val();

	if (isMatch0 == 0) {
		if (gg != null && gg != 'undefind') {
			str += '"' + $('.color-list .select').attr("colorshow") + '"  ';
		}
	}
	if (isMatch == 0) {
		if (sx != null && sx != 'undefind') {
			var listSize = $("#listSize").val();
			for (var i = 0; i < listSize; i++) {
				str = str
						+ '"'
						+ $(
								"ul[id='" + (i)
										+ "'] li[attrValIdV][class='select'] a")
								.html() + '" &nbsp;&nbsp;';
			}
			/*
			 * str += $('.amount-control #num').val() +$('.size-list
			 * .select').attr('measureName');
			 */
		}
	}
	if (isMatch0 == 1 && isMatch == 1) {
		$("#gin2 span").html("默认");
	} else {
		$("#gin2 span").html(str);
	}

}

function toCart() {
	var oGoods = document.getElementById('goodss');
	var oColo = document.querySelector('.colour');
	oGoods.onclick = function() {
		oColo.classList.add('show');
		var oTop = document.querySelector('.colour_top');
		oTop.onclick = function() {
			oColo.classList.remove('show');
		};
	};
}

function goodtab(abj1, abj2) {
	for (var i = 0; i < abj1.length; i++) {
		(function(index) {
			abj1[i].onclick = function() {
				tab1(index);
			};
		})(i);
	}
	function tab1(index) {
		for (var i = 0; i < abj1.length; i++) {
			abj1[i].classList.remove('cur');
			abj2[i].classList.remove('show');
		}
		abj1[index].classList.add('cur');
		abj2[index].classList.add('show');
	}
}

/*
 * function colour(){ var oHeight=document.documentElement.clientHeight; var
 * oTop=document.querySelector('.colour_top'); var
 * oBot=document.querySelector('.colour_bot'); var
 * oNull=document.getElementById('null');
 * 
 * oTop.style.height=oHeight-oBot.offsetHeight+'px'; }
 */

// 初始省份信息
function getProvice() {
	$("#stock_province_item ul").html("");

	$.ajax({
		type : "post",
		url : path + "/address/getProvice",
		async : false,
		dataType : "json",
		success : function(res) {
			if (res == null || res == "") {
				return false;
			}
			// var pros=eval('(' + res + ')');
			// $("#cityId").append('<option value="" >'+请选择+'</option> ');
			for (var i = 0; i < res.length; i++) {
				$("#stock_province_item ul").append(
						'<li><a href="javascript:void(0);" provice_id="'
								+ res[i].provinceid
								+ '" flag = "provice"  class="data_provice">'
								+ res[i].provincename + '</a></li>');
			}
			/*
			 * if(cityId!=null){ $("#cityId").val(cityId); }
			 */
		},
		error : function() {
			showErrorMsg("网络连接超时，请您稍后重试");
		}
	});

}

// 初始化市区信息
function getCity(provice_id) {
	// 隐藏省份,显示市区
	$("#stock_province_item").hide();
	$("#stock_city_item").show();
	// 清除页面缓存旧信息
	$("#stock_city_item ul").html("");
	var condition = "provinceId=" + provice_id;
	$.ajax({
		type : "post",
		url : path + "/address/getCity",
		data : condition,
		dataType : "json",
		success : function(res) {
			if (res == null || res == "") {
				return false;
			}
			for (var i = 0; i < res.length; i++) {
				$("#stock_city_item ul").append(
						'<li><a href="javascript:void(0);" city_id="'
								+ res[i].cityid
								+ '" flag = "city" class="data_area">'
								+ res[i].cityname + '</a></li>');
			}
			/*
			 * if(cityId!=null){ $("#cityId").val(cityId); }
			 */

		},
		error : function() {
			showErrorMsg("网络连接超时，请您稍后重试");
		}
	});
}

// 加载区域信息
function getCountry(city_id) {
	// 隐藏省份,市区 ,显示country
	$("#stock_province_item").hide();
	$("#stock_city_item").hide();
	$("#stock_area_item").show();
	// 清除页面缓存旧信息
	$("#stock_area_item ul").html("");
	var condition = "cityId=" + city_id;

	$.ajax({
		type : "post",
		url : path + "/address/getArea",
		data : condition,
		dataType : "json",
		success : function(res) {
			if (res == null || res == "") {
				return false;
			}
			// $("#cityId").append('<option value="" >'+请选择+'</option> ');
			for (var i = 0; i < res.length; i++) {
				$("#stock_area_item ul").append(
						'<li><a href="javascript:void(0);" area_id="'
								+ res[i].countyid
								+ '" flag = "area" class="select_area_js">'
								+ res[i].countyname + '</a></li>');
			}
			/*
			 * if(cityId!=null){ $("#cityId").val(cityId); }
			 */

		},
		error : function() {
			alert("网络连接超时，请您稍后重试");
		}
	});

}

/*
 * function pull(){ var startY=0; var endY=0; var
 * oDiv=document.querySelector('.detail_all'); var
 * oPull=document.querySelector('#pull');
 * oPull.addEventListener('touchstart',function(ev){ oDiv.style.display='block';
 * startY=ev.targetTouches[0].pageY;
 * oPull.addEventListener('touchend',endFN,false); function endFN(ev){
 * endY=ev.changedTouches[0].pageY; var disY=endY-startY; if(disY<0){
 * document.body.scrollTop=document.body.scrollTop+disY+'px'; }else{
 * oDiv.style.display='none'; } } return false; },false); }
 */

// --------------展示促销信息
function showPromotionInfo(element) {
	// 首先隐藏所有的促销信息
	$("div[promotion_skuId]").hide();
	$("li[promotion_info_skuId]").hide();
	// 获取skuid
	var skuId = element.attr("skuid");
	// 展示促销 类型
	$("div[promotion_skuId='" + skuId + "']").show();
	// 展示促销信息
	$("li[promotion_info_skuId='" + skuId + "']").show();
}

// 有新增
function init() {
	// 首先获取第一个展示属性，并添加样式first,last
	var theFirstShowAttrElement = $("li[showAttrValId]").first().addClass(
			"select");
	var theFirstShowAttrElement_val = theFirstShowAttrElement
			.attr("showAttrValId");
	// 查找对应的sku中全部显示，并为第一个添加样式showAttrValId_sku
	// $("li[attrValIdV]").show().first().addClass("select");
	var listSize = $("#listSize").val();
	for (var i = 0; i < listSize; i++) {
		$("ul[id='" + (i) + "'] li[attrValIdV]").show().first().addClass(
				"select");
	}
	// 初始化，加载默认选中sku的价格以及关税信息
	// 屏蔽 zhengns var checkedSKU = $("li[attrValIdV]").filter(".select");
	var checkedSKU = getSelectedSkuShowObj();
	changePrice(checkedSKU);
	// 屏蔽 zhengns changePriceNew();
	// 判断当前采购数量是否满足本次选中的sku的最大库存
	// 屏蔽 zhengns
	checkBuyNum(checkedSKU);
	// 显示与此sku相关促销信息
	showPromotionInfo(checkedSKU);
	// 是否显示倒计时
	// showTimeTrim(checkedSKU);

	$("#oop").attr("src", $("#ooo").attr("src"));

	promotionTimeShow();

	// 默认选中的规格属性 以及 库存数
	changeGuiAndShu();
}

function goBack() {
	var lis = $(".head").find(".tabs").find("li");
	if (lis.eq(0).hasClass("on")) {
		history.go(-1);
	} else {
		lis.eq(0).addClass('on').siblings().removeClass('on');
		$(".wrap .divw").eq(0).show().siblings().hide();
	}

}

/*
 * 获取购物车商品数量
 */
function cartQty() {
	$.ajax({
		type : "post",
		url : path + "/cart/qty",
		dataType : "text",
		success : function(totalQty) {
			// 更新页面显示购物车商品数量
			if (totalQty && Number(totalQty) > 0) {
				$(".cart-btn").find("span").html(
						"<i class='cart-num'>" + totalQty + "</i>");
			} else {
				$(".cart-btn").find("span").find("i").remove();
			}
		}
	});
}

function slideShow() {

	$(".swipe").each(function(i, item) {
		var iid = $(item).attr("id");
		initslider(iid);
	});
	$(".swipe").removeClass('top');
	$(".swipe").eq(0).addClass("top");

}

// 处理轮播
function initslider(id) {
	var elm = document.getElementById(id);
	if (!elm)
		return;
	var doc = document.getElementById(id + '-doc');
	var slides = elm.children[0].children;
	var temp = '';
	var index = 0;
	for (var i = 0, len = slides.length; i < len; i++) {
		temp += '<em ' + (i == 0 ? 'class="on"' : '') + '></em>';
	}
	doc.innerHTML = temp;
	var docs = doc.querySelectorAll('em');
	swipe.push(new Swipe(elm, {
		speed : 400,
		// auto : 3000,
		continuous : true,
		callback : function(pos) {
			docs[index].className = '';
			docs[pos].className = 'on';
			index = pos;
		}
	}));

}

function switchSku() {
	var obj = $(this);
	// 获取属性之
	var showattrvalid_val = obj.attr("showattrvalid");
	// 同级元素取出样式
	obj.addClass("select").siblings("li[showattrvalid]").removeClass("select");
	// 切换显示轮播
	changeCarousel(obj);
	// 依据当前展示属性(例如：颜色)切换显示所拥有的sku规格(例如：X XL)
	$("li[showattrvalid_sku]").hide();
	/* 获取上次选中的sku规格值li */
	var pre_sku_li_a = $("li[showattrvalid_sku][class='select'] a").html();

	$("li[showattrvalid_sku]").removeClass("select");

	/*
	 * 此处是将选择的sku属性 对应的属性值 显示 并将第一个获取到，需要修改为： 将对应属性值显示，并且和上次选择值相等的规格值获取到
	 * 如果没有和上次相等规格的，将第一个获取到
	 * 
	 */
	var showattrvalid_sku_li_a;
	var showattrvalid_sku_li;

	if (pre_sku_li_a) {
		showattrvalid_sku_li_a = $("li[showattrvalid_sku='" + showattrvalid_val
				+ "'] a");

		showattrvalid_sku_li_a.each(function() {
			if ($(this).html() == pre_sku_li_a) {
				showattrvalid_sku_li = $(this).parent();
			}
		});

	}

	// 获取第一个sku规格
	var firstSKU = $("li[showattrvalid_sku='" + showattrvalid_val + "']")
			.show().eq(0);// eq(0)
	if (showattrvalid_sku_li) {
		firstSKU = showattrvalid_sku_li;
	}
	firstSKU.addClass("select");
	// 依据当前选中sku，重置价格信息
	// 屏蔽 zhengns changePrice(firstSKU);
	var checkedSKU = getSelectedSkuShowObj();
	changePrice(checkedSKU);
	// 判断当前采购数量是否满足本次选中的sku的最大库存
	// 屏蔽 zhengns checkBuyNum(firstSKU);
	checkBuyNum(checkedSKU);
	// 展示促销信息
	// 屏蔽 zhengns showPromotionInfo(firstSKU);
	showPromotionInfo(checkedSKU);
	// 是否显示倒计时
	// showTimeTrim(firstSKU);
	changeGuiAndShu();
	$("#oop").attr("src", $(".color-list .select a img").attr("src"));
}

function switchSkuValue() {
	var obj = $(this);
	// 添加样式，去除同级样式
	obj.addClass("select").siblings("li[attrValIdV]").removeClass("select");
	// 判断当前采购数量是否满足本次选中的sku的最大库存
	var checkedSKU = getSelectedSkuShowObj();
	// checkBuyNum(obj);
	changePrice(checkedSKU);
	checkBuyNum(checkedSKU);
	// 展示促销信息
	// showPromotionInfo(obj);
	showPromotionInfo(checkedSKU);
	// 是否显示倒计时
	// showTimeTrim($(this));
	changeGuiAndShu();
}

function amountUp() {

	// 拼装attrValIdVs=showattrvalid_val+attrvalidvs
	var attrValIdVs = getSelectedSkuShowAttrValIdV();
	var skuQty = $("input[attrValIdVs='" + attrValIdVs + "']").attr("skuQty");
	var limitnum = document.getElementById("limitnum").value;

	/*
	 * if(limitnum>=0&&limitnum<=skuQty){ skuQty=Number(limitnum); }
	 */

	// 获取当前选中的规格
	var spec = $("li[showattrvalid_sku]").filter(".select");
	var that = $(this);
	var amountinput = that.prev(), num = amountinput.val();
	// alert(limitnum);
	// 获取当前选中的sku的库存
	// var stock=Number(spec.attr("skuQty"));
	if (regint(num)) {
		num = Number(num);
		if (Number(skuQty) <= 0) {
			return;
		}

		if (num + 1 > Number(skuQty)) {
			// amountinput.val(stock);
			$(".stock-tips").show();
			window.setTimeout(function() {
				$(".stock-tips").hide();
			}, 1000);
		} else if ((num + 1 > limitnum) && (limitnum >= 0)
				&& (limitnum <= Number(skuQty))) {

			$(".stock-tips1").show();
			window.setTimeout(function() {
				$(".stock-tips1").hide();
			}, 1000);

		} else {
			amountinput.val(num + 1);
		}
	} else {
		amountinput.val(0);
	}
	// 判断为数字
	/*
	 * if(regint(num)){ num = Number(num); if(stock<=0){ return; }
	 * if(num+1>stock){ // amountinput.val(stock); $(".stock-tips").show();
	 * window.setTimeout(function(){$(".stock-tips").hide();},1000); }else{
	 * amountinput.val(num+1); } }else{ amountinput.val(0); }
	 */

	// 判断库存
}

function amountDown() {
	var that = $(this);
	var amountinput = that.next(), num = amountinput.val();

	// 判断为数字
	if (regint(num)) {
		num = Number(num);
		if (num - 1 <= 0) {
			amountinput.val(0);
		} else {
			amountinput.val(num - 1);
		}
	} else {
		amountinput.val(0);
	}
}

function amountInput() {

	// 拼装attrValIdVs=showattrvalid_val+attrvalidvs
	var attrValIdVs = getSelectedSkuShowAttrValIdV();
	var skuQty = $("input[attrValIdVs='" + attrValIdVs + "']").attr("skuQty");

	// 获取当前选中的规格
	/*
	 * var spec = $("li[showattrvalid_sku]").filter(".select"); //var that =
	 * $(this); //var amountinput=that.next(), num = amountinput.val();
	 * //获取当前选中的sku的库存 var stock=Number(spec.attr("skuQty"));
	 */
	// 获取input的value,并去除前面的零
	var inputVal = Number($(this).val().replace(/\b(0+)/gi, ""));

	if (!regint(inputVal)) {
		$(this).val(0);
	} else if (inputVal >= Number(skuQty)) {
		$(this).val(Number(skuQty));
		$(".stock-tips").show();
		window.setTimeout(function() {
			$(".stock-tips").hide();
		}, 1000);
	} else {
		$(this).val(inputVal);
	}
}

function selectRegion() {
	$("body").append('<div id="mask" class="mask"></div>');
	$("html").addClass("lockscreen"), $("body").addClass("lockscreen"),

	$("#J_filterWrap").addClass("show");

	// 隐藏省份,市区 ,显示country
	$("#stock_province_item").show();
	$("#stock_city_item").hide();
	$("#stock_area_item").hide();
	// 异步加载省份信息,判断页面是否已加载过
	if ($("#stock_province_item li").size() <= 0) {
		getProvice();
	}

	// 配送地址选中
	$(".J_filterContents").each(
			function(a, e) {
				var that = $(this);
				var t = $(e).find("li");
				t.click(function(a) {
					that.hasClass("active") ? that.removeClass("active") : that
							.addClass("active").siblings("li").removeClass(
									"active");
				});
			});
}

function closeSelectRegion() {
	$("#mask").remove(), $("html").removeClass("lockscreen");
	$("body").removeClass("lockscreen");
	$("#J_filterWrap").removeClass("show");
}

function showDefaultRegion() {
	var areaIdKey = $("#areaIdKey").val();
	if ("" != areaIdKey) {
		var array_area = areaIdKey.split(',');
		$('.provi_city_area_id').attr('provinceId', array_area[0]);
		$('.provi_city_area_id').attr('cityId', array_area[1]);
		$('.provi_city_area_id').attr('areaId', array_area[2]);
		$('.provi_city_area_id').html(array_area[3]);

		// 更新库存信息
		var pid = $("#PID").val();
		var provinceId = array_area[0];
		var cityId = array_area[1];
		var areaId = array_area[2];
		var totalAddress = array_area[3];
		loadArea(pid, provinceId, cityId, areaId, totalAddress);

	} else {
		$("#btn-select-region").click();
	}
}

function loadArea(pid, provinceId, cityId, areaId, areaName) {

	var data_arr = new Array();

	data_arr.push("productId=" + pid);
	data_arr.push("provinceId=" + provinceId);
	data_arr.push("cityId=" + cityId);
	data_arr.push("districtId=" + areaId);
	data_arr.push("areaName=" + areaName);

	for (var i = 0; i < 3; i++) {
		try {
			$
					.ajax({
						type : "post",
						data : data_arr.join("&") + '&math=' + Math.random(),
						url : path + "/item/stock",
						async : false,
						dataType : "json",
						success : function(msg) {
							if (msg === '0') {
								$.dialog({
									content : "库存服务异常！",
									title : '',
									time : 500,
									lock : true
								});
							}
							if (msg.skuStockDtos != '') {
								$
										.each(
												eval(msg.skuStockDtos),
												function(i, n) {
													// skuid 太长 溢出了
													var skuid = n.skuIdV;
													var spotQty = n.spotQty;
													$('li[skuqty]')
															.each(
																	function(j,
																			m) {
																		if ($(
																				this)
																				.attr(
																						'skuid') != undefined) {
																			if (skuid === $(
																					this)
																					.parents(
																							'div.clearfix')
																					.prev()
																					.find(
																							'li.select')
																					.attr(
																							'showattrvalid')) {
																				// 重置库存
																				$(
																						this)
																						.attr(
																								'skuqty',
																								spotQty);
																				if (0 == spotQty) {
																					$(
																							"#wuh")
																							.show();
																					$(
																							"#xianh")
																							.hide();
																				} else {
																					$(
																							"#wuh")
																							.hide();
																					$(
																							"#xianh")
																							.show();
																				}
																			}
																		}
																	});
												});
							}
							// 首先获取第一个展示属性，并添加样式first,last
							var theFirstShowAttrElement = $("li[showAttrValId]")
									.filter(".select").attr("showAttrValId");
							$(
									"li[showAttrValId_sku='"
											+ theFirstShowAttrElement + "']")
									.show().first().click();

							return;
						},
						error : function(e) {
							throw "system error";
						}
					});
		} catch (e) {
			continue;
		}

	}
}

function directBuy() {
	// 确认当前是否可以立即购买
	if ($(".addcart-btn").attr("sign") == "false") {
		showMessage('商品库存不足');
		return false;
	}
	;

	if ($(this).hasClass('directBuy-btns')) {
		var specifications = $(".blist ul li").length;
		var property = $(".two_bot2 ul li").length;
		// 多规格属性不能直接添加购物车
		if (specifications > 1 || property > 1) {
			$('.m_size').addClass('show');
			return;
		}
	}
	// var companyRegion = $("#companyRegion").val();

	// 获取采购数量
	var number = Number($(".amount-input").val());
	if (0 == number) {
		// jAlert("请选择采购数量！");
		$.dialog({
			content : '请添加购买数量！',
			title : '',
			time : 500,
		});
		return false;
	}
	// 获取当前采购的kuid

	// 拼装attrValIdVs=showattrvalid_val+attrvalidvs
	var attrValIdVs = getSelectedSkuShowAttrValIdV();
	var skuId = $("input[attrValIdVs='" + attrValIdVs + "']").attr("skuid");
	// var skuId = $("li[showattrvalid_sku]").filter(".select").attr("skuId");
	var condition = "skuId=" + skuId + "&number=" + number;
	$
			.ajax({
				type : "post",
				url : path + "/cart/checkUserAccountBalance",
				data : condition,
				dataType : "text",
				success : function(checkFlag) {
					var showMessage = "";
					switch (checkFlag) {
					case '20000': // 成功

						window.location.href = path + "/cart/directBuy?skuId="
								+ skuId + "&number=" + number;

						/*
						 * var contain="skuId="+ skuId + "&number=" + number;
						 * $.post("
						 * http://localhost:8080/wap-customer/cart/directBuy",contain);
						 */
						break;

					case '10000':
						showMessage = "请先做登录！";
						window.location.href = path
								+ "/customer/toLogin?productId=" + pid;
						break;

					case '40000':
						createDom({
							type : 4,
							msg : "请先购买激活区商品激活账户，再购物",
							cancelCallback : function(e) {
								// 取消;
								location.href = "http://m.zhongjumall.com/searchController/toSearchResult?cdid=007009";
							},
							sureCallback : function() {
								// 确定;
								location.href = "http://m.zhongjumall.com/";
							}
						});
						break;
					case '41000':
						createDom({
							type : 5,
							msg : "此产品只能未激活会员购买。",
							ok : function() {
							},
						/*
						 * cancelCallback: function (e) { //取消; location.href =
						 * "http://m.zhongjumall.com/searchController/toSearchResult?cdid=007009"; },
						 * sureCallback: function () { //确定; location.href =
						 * "http://m.zhongjumall.com/"; }
						 */
						});
						break;
					case '40010':
						
						createDom({
							type : 5,
							msg : startip.innerHTML,
							ok : function() {
							},

						});
						
						
						break;
					case '40020':
						createDom({
							type : 5,
							msg : "已超出限购数量",
							ok : function() {
							},

						});
						break;
					case '40040':
						createDom({
							type : 5,
							msg : "企业小号和会员企业号不能购买家庭号",
							ok : function() {
							},

						});
						break;
					case '40050':
						createDom({
							type : 5,
							msg : "企业小号和购买过家庭号的不能再购买会员企业号",
							ok : function() {
							},
							
						});
						break;
					case '30000':
						showMessage = "系统异常！";
						break;
					case '60000':
						showMessage = "请您购买其他商品，无法购买本企业商品";
						break;
					case '60001':
						showMessage = "请您购买其他商品，无法购买本人商品";
						break;

					}
					if (showMessage != '') {
						$.dialog({
							content : showMessage,
							title : '',
							time : 3000,
							lock : true
						});
					}

					$('.m_size').removeClass('show');
				}
			});
}

function showMessage(message) {

	$.dialog({
		content : message,
		title : '',
		time : 1000,
	});

}

function addCart() {
	// 确认当前是否可以提交购物车
	if ($(".addcart-btn").attr("sign") == "false") {
		showMessage('商品库存不足');
		return;
	}

	if ($(this).hasClass('addcart-btns')) {
		var specifications = $(".blist ul li").length;
		var property = $(".two_bot2 ul li").length;
		// 多规格属性不能直接添加购物车
		if (specifications > 1 || property > 1) {
			$('.m_size').addClass('show');
			return;
		}
	}

	// 获取采购数量
	var number = Number($(".amount-input").val());
	if (0 == number) {
		$.dialog({
			content : '请添加购买数量！',
			title : '',
			time : 1000,
		});
		return;
	}
	// 获取当前采购的kuid

	// 拼装attrValIdVs=showattrvalid_val+attrvalidvs
	var attrValIdVs = getSelectedSkuShowAttrValIdV();
	var skuId = $("input[attrValIdVs='" + attrValIdVs + "']").attr("skuid");
	// var skuId = $("li[showattrvalid_sku]").filter(".select").attr("skuId");
	var companyRegion = $("#companyRegion").val();
	var condition = "skuId=" + skuId + "&number=" + number + "&companyRegion="
			+ companyRegion + "";
	$
			.ajax({
				type : "post",
				url : path + "/cart/addItem",
				data : condition,
				dataType : "text",
				success : function(addCartFlag) {
					var showMessage = "";
					switch (addCartFlag) {
					case '20000': // 添加成功
						// 添加成功返回购物车内商品总数量
						cartQty();
						break;
					case '10000':
						showMessage = "请先做登录";
						window.location.href = path
								+ "/customer/toLogin?productId=" + pid;
						break;
					case '40000':
						createDom({
							type : 4,
							msg : "请先购买激活区商品激活账户，再购物",
							cancelCallback : function(e) {
								// 取消;
								location.href = "http://m.zhongjumall.com/searchController/toSearchResult?cdid=007009";
							},
							sureCallback : function() {
								// 确定;
								location.href = "http://m.zhongjumall.com/";
							}
						});
						break;
					case '41000':
						createDom({
							type : 5,
							msg : "此产品只能未激活会员购买。",
							ok : function() {
							},

						/*
						 * cancelCallback: function (e) { //取消; location.href =
						 * "http://m.zhongjumall.com/searchController/toSearchResult?cdid=007009"; },
						 * sureCallback: function () { //确定; location.href =
						 * "http://m.zhongjumall.com/"; }
						 */
						});
						break;
					case '40010':
						createDom({
							type : 5,
							msg : startip.innerHTML,
							ok : function() {
							},

						});
						break;
					case '40020':
						createDom({
							type : 5,
							msg : "已超出限购数量",
							ok : function() {
							},

						});
						break;
					case '40030':
						createDom({
							type : 5,
							msg : "此商品不能添加购物车，只能立即购买",
							ok : function() {
							},

						});
						break;
					case '40040':
						createDom({
							type : 5,
							msg : "企业小号和会员企业号不能购买家庭号",
							ok : function() {
							},

						});
						break;
					case '40050':
						createDom({
							type : 5,
							msg : "企业小号和购买过家庭号的不能再购买会员企业号",
							ok : function() {
							},
							
						});
						break;
					case '30000':
						showMessage = "系统异常！";
						break;
					case '50001': // 库存不足
						showMessage = "库存不足";
						break;
					case '50002': // 购物车内商品数量超过100件
						showMessage = "购物车内商品数量超过100件";
						break;
					case '60000':
						showMessage = "请您购买其他商品，无法购买本企业商品";
						break;
					case '60001':
						showMessage = "请您购买其他商品，无法购买本人商品";
						break;

					}
					if (showMessage != "") {
						$.dialog({
							content : showMessage,
							title : '',
							time : 3000,
							lock : true
						});
					}

					$('.m_size').removeClass('show');
				}
			});
	editGuige();
	$("#num").val(1);
	$('.colour').removeClass('show');
}

// 切换轮播图集合
function changeCarousel(element) {
	var s = element;
	var n = s.closest("li").index(), id = s.closest("li").attr("showattrvalid");
	$(".swipe").removeClass('top');
	$("#" + id).addClass("top");
	$("#" + id).siblings().hide();
	$("#" + id).show();
	swipe[n].slide(0, 10);

}

// 获取选中的sku
function getSelectedSky() {
	var skuShowObj = getSelectedSkuShowObj();
	var skuId = skuShowObj.attr("skuid");
	console.log("selectDefaultSky skuid=" + skuId);
	return skuId;

}

// 获取选中的展示属性值以及规格值的顺序串
function getSelectedSkuShowAttrValIdV() {
	var showattrvalid_val = $("li[showattrvalid][class='select']").attr(
			"showattrvalid");
	// 获取list集合长度
	var listSize = $("#listSize").val();
	var attrvalidvs = "";
	for (var i = 0; i < listSize; i++) {
		attrvalidvs = attrvalidvs
				+ ","
				+ $("ul[id='" + (i) + "'] li[attrValIdV][class='select']")
						.attr("attrvalidv");
	}
	attrvalidvs = attrvalidvs.substring(1);
	return showattrvalid_val + "," + attrvalidvs;
}
// 获取选中的展示属性值以及规格值的顺序串对应的隐藏对象
function getSelectedSkuShowObj() {
	var skuShowAttrValIdV = getSelectedSkuShowAttrValIdV();
	return $("input[attrValIdVs='" + skuShowAttrValIdV + "']");
}

// 重置价格库存
function changePriceNew() {
	var attrValIdVs = getSelectedSkuShowAttrValIdV();

	var promotionPrice = $("input[attrValIdVs='" + attrValIdVs + "']").attr(
			"promotionPrice");
	var unitPrice = $("input[attrValIdVs='" + attrValIdVs + "']").attr(
	"unitPrice");
	var skuQty = $("input[attrValIdVs='" + attrValIdVs + "']").attr("skuQty");
	var domesticPrice = $("input[attrValIdVs='" + attrValIdVs + "']").attr(
			"domesticPrice");
	var checkedSKU = $("input[attrValIdVs='" + attrValIdVs + "']");
	/*
	 * alert(promotionPrice); alert(domesticPrice); alert(skuQty);
	 */
	$(".unitPrice").text(promotionPrice);
	//$(".unitPrice").text(unitPrice);
	$(".domesticPrice").text(domesticPrice);
	$("#currentSkuQty").text(skuQty);
	checkBuyNum(checkedSKU);
}

// 重置商品价格
function changePrice(element) {

	var unitPrice = Number(element.attr("unitPrice")).toFixed(2); // 单价
	var promotionPrice = Number(element.attr("promotionPrice")).toFixed(2); // 单价
	var domesticPrice = Number(element.attr("domesticPrice")).toFixed(2);// 国内价
	var cxuprcie = Number(element.attr("skuprcie")).toFixed(2); // 原价格
	// var fhed = Number(element.attr("fhed")).toFixed(2);//分红额度
	// var hqj = Number(element.attr("hqj")).toFixed(2);//赠送红旗卷额度

	var xjzfblbq = document.getElementById("xjzfblbq").value;// 1打标签0没打标签

	/* alert(Number(cashHqj)); alert(xjzfblbq); */
	// 返券比例
	var moneyFhed = Number(element.attr("moneyFhed")) * 0.01;

	var moneyHqj = Number(element.attr("moneyHqj")) * 0.01;
	var hqFhed = Number(element.attr("hqFhed")) * 0.01;
	var hqHqj = Number(element.attr("hqHqj")) * 0.01;
	var moneyAndHqFhed = Number(element.attr("moneyAndHqFhed")) * 0.01;
	var moneyAndHqHqj = Number(element.attr("moneyAndHqHqj")) * 0.01;
	// 支付比例
	var ticketRation = Number(element.attr("ticketRation")) * 0.01;
	var cashRation = Number(element.attr("cashRation")) * 0.01;
	// 处理美元，返回值有单位，需要保留小数点后两位
	var totalForeignPrice = element.attr("foreignPrice");
	var foreignPrice = splitString(totalForeignPrice);
	var tar = Number(element.attr("tar")).toFixed(2);// 关税

	var shuilv = $(".d-price").attr("shuilv"); // 税率
	var shuifei_ = unitPrice * shuilv / 100;
	shuifei_ = parseFloat(shuifei_).toFixed(2);

	// 获取 notTar 标识，确认是否显示横线
	var notTar = Number(element.attr("notTar"));

	// 重置页面价格
	$(".d-price .shuifei").text(shuifei_);
	$(".unitPrice").text(promotionPrice);
	//$(".unitPrice").text(unitPrice);
	$(".domesticPrice").text(domesticPrice);
	// $(".hqj").text(hqj);
	// $(".fhed").text(fhed);
	var homeShangPin = document.getElementById("homeShangPin").value;
	var cashHqj = 0;
	var hhzfhqq = 0;
	var hhzfxz = 0;

	if (xjzfblbq == 1) {
		cashHqj = Number(element.attr("cashHqj")) * 0.01;// 现金比
		hhzfhqq = Number(unitPrice * (1 - cashHqj)).toFixed(0);
		hhzfxz = Number(unitPrice - hhzfhqq).toFixed(2);
	} else if (homeShangPin == 6|| homeShangPin==0) {
		cashHqj = Number(element.attr("cashHqj"));// 现金数
		hhzfhqq = Math.ceil(unitPrice - cashHqj);
		hhzfxz = Number(cashHqj).toFixed(2);

	} else {
		hhzfhqq = Number(unitPrice).toFixed(0);
		hhzfxz = Number(0).toFixed(0);

	}
	$("#hhzfhqq").text(hhzfhqq);
	$("#hhzfxz").text(hhzfxz);

	var moneyAndHqHqj = Math.round(hhzfhqq * hqHqj)
			+ Math.round(hhzfxz * moneyHqj);

	var moneyAndHqFhed = Math.round(hhzfhqq * hqFhed)
			+ Math.round(hhzfxz * moneyFhed);

	$("#moneyAndHqHqj").text(moneyAndHqHqj);
	$("#moneyAndHqFhed").text(moneyAndHqFhed);

	$(".foreignPrice").text(foreignPrice);
	$(".unitPrices").text(cxuprcie);
	$(".tar").text(tar);
	if (1 == notTar) {
		$("font").addClass("line-through");
	}

	if (homeShangPin == 0||homeShangPin == 6 ||(xjzfblbq==1&&cashHqj!=0)) {

		$(".cashHqj").show();
	} else {
		$(".cashHqj").hide();
	}
	/*if (homeShangPin == 0&&hhzfxz==0) {
		
		$(".dui").show();
	} else {
		$(".dui").hide();
	}*/
	
	if ( homeShangPin ==5 || ( moneyAndHqHqj ==0 && moneyAndHqFhed==0 ) ) {
		
		$(".hh").hide();
	} else {
		$(".hh").show();
	}

}

function checkBuyNum(element) {

	// 拼装attrValIdVs=showattrvalid_val+attrvalidvs
	var attrValIdVs = getSelectedSkuShowAttrValIdV();
	var skuQty = $("input[attrValIdVs='" + attrValIdVs + "']").attr("skuQty");
	/*
	 * if(number>skuQty){ $(".amount-input").val(skuQty); }
	 */
	if (skuQty > 0) {
		// 重置采购数量为1
		$(".amount-input").val(1);
		// 隐藏库存不足信息
		$(".stock-tips").hide();
		// 给购物车添加和立即购买添加的标识
		$(".buy-btn").attr("sign", "yes");
		$(".addcart-btn").attr("sign", "yes");
		// 取出不可点击的样式
		$(".buy-btn").removeClass("c_gray3");
		$(".addcart-btn").removeClass("c_gray3");

		// 改变 区域货存状态
		$("#wuh").hide();
		$("#xianh").show();
		$("#currentSkuQty").text(skuQty);
		// console.log("1111");
	} else {
		// 重置采购数量为1
		$(".amount-input").val(0);
		// 显示库存不足信息
		$(".stock-tips").show();
		// 移除加入购物车的click事件
		// $(".addcart-btn").removeAttr("onclick");
		// 添加不可点击的标志
		$(".buy-btn").attr("sign", "false");
		$(".addcart-btn").attr("sign", "false");
		// 添加购物车阴影
		$(".buy-btn").addClass("c_gray3");
		$(".addcart-btn").addClass("c_gray3");
		// 改变 状态 货源状态
		$("#xianh").hide();
		$("#wuh").show();
		$("#currentSkuQty").text(skuQty);
	}
}

function chooseRegion() {
	$(this).addClass("active").siblings("li").removeClass("active");
	// 获取，当前li标签的flag的值
	var content = $.trim($(this).children().attr("flag"));
	// 如果选择的省份信息
	if ("provice" == content) {
		var provice_id = $.trim($(this).children().attr("provice_id"));
		// 获取市区信息
		getCity(provice_id);

	} else if ("city" == content) {
		var city_id = $.trim($(this).children().attr("city_id"));
		// 获取市区信息
		getCountry(city_id);

	} else if ("area" == content) {
		// 关闭
		// closePage();
		// 获取已选择的省市区
		var provice_ele = $("#stock_province_item li").filter(".active")
				.children();
		var city_ele = $("#stock_city_item li").filter(".active").children();
		var country_ele = $("#stock_area_item li").filter(".active").children();
		// 重置配送地址
		var totalAddress = provice_ele.html() + " " + city_ele.html() + " "
				+ country_ele.html();
		// 重置页面显示
		$(".provi_city_area_id").attr('provinceId',
				provice_ele.attr("provice_id")).attr('cityId',
				city_ele.attr("city_id")).attr('areaId',
				country_ele.attr("area_id")).html(totalAddress);
		// 更新库存信息
		var pid = $("#PID").val();
		var provinceId = provice_ele.attr("provice_id");
		var cityId = city_ele.attr("city_id");
		var areaId = country_ele.attr("area_id");
		loadArea(pid, provinceId, cityId, areaId, totalAddress);
		$("#J_filterWrap").removeClass("show");
		$("#mask").remove();
		$("#mask").remove();
	}
}

function openView() {
	var oDiv = document.querySelector('.countly');
	var oCan = document.querySelector('.cancely');
	var oSave = document.querySelector('.surely');

	oDiv.style.display = 'block';
	oCan.onclick = function() {
		oDiv.style.display = 'none';
	};
	oSave.onclick = function() {
		$("#num").val($("#numCon").val());
		oDiv.style.display = 'none';
	};

}

function promotionTimeShow() {
	var d = $("#serverTime").val();
	var serverTime = d * 1000;

	var dateTime = new Date();
	var difference = dateTime.getTime() / 1000 - serverTime / 1000;// 客户端与服务器时间偏移量
	var priceDownTime = $(".promotion-box span.wr");
	if (priceDownTime && priceDownTime.length > 0) {
		setInterval(function() {
			$(".promotion-box span.wr").each(function() {
				// 获取直降时间
				var priceDownDate = $(this).find(".zhij").val();

				// 转为date格式 "2015-08-31 17:58:24"
				var priceDownDate = new Date(priceDownDate.replace(/-/g, "/"));
				// console.log(priceDownDate);
				var time_stamp = priceDownDate.getTime() / 1000;
				// console.log(time_stamp);
				var endTime = new Date(parseInt(time_stamp) * 1000);// 结束时间
				var nowTime = new Date();
				var nMS = endTime.getTime() - nowTime.getTime() + difference;// 当前变化时间
				var myD = Math.floor(nMS / (1000 * 60 * 60 * 24));
				var myH = Math.floor(nMS / (1000 * 60 * 60)) % 24;
				var myM = Math.floor(nMS / (1000 * 60)) % 60;
				var myS = Math.floor(nMS / 1000) % 60;
				var myMS = Math.floor(nMS / 100) % 10;
				if (myD >= 0) {
					/* var str = myD+"天"+myH+"小时"+myM+"分"+myS+"."+myMS+"秒"; */
					var str = myD + "天" + myH + "小时" + myM + "分" + myS + "秒";
					// console.log(str);
				} else {
					var str = "";
				}
				$(this).find(".views").html(str);
			}, 1000);// 每个0.1秒执行一次
		});
	}

	var difference = dateTime.getTime() - serverTime;// 客户端与服务器时间偏移量
	var obj = $(".endtime");
	var objValue = obj.attr('value');
	if (objValue && objValue != "" && objValue != undefined && objValue != null) {
		setInterval(function() {
			var obj = $(".endtime");
			var endTime = new Date(parseInt(obj.attr('value')) * 1000);// 结束时间
			var nowTime = new Date();
			var nMS = endTime.getTime() - nowTime.getTime() + difference;// 当前变化时间
			var myD = Math.floor(nMS / (1000 * 60 * 60 * 24));
			var myH = Math.floor(nMS / (1000 * 60 * 60)) % 24;
			var myM = Math.floor(nMS / (1000 * 60)) % 60;
			var myS = Math.floor(nMS / 1000) % 60;
			var myMS = Math.floor(nMS / 100) % 10;
			if (myD >= 0) {
				var str = myD + "天" + myH + "小时" + myM + "分" + myS + "秒";
			} else {
				var str = "";
			}
			obj.html(str);
		}, 1000);// 每个0.1秒执行一次
	}
}

function commentInfo(pid) {
	/* var pid="2709626906347718"; */
	/* var condition="pid="+pid; */
	$.ajax({
		type : "post",
		url : $("#path").val() + "/comment/" + pid,
		dataType : "html",
		success : function(commentInfo) {
			$("#d").html("");
			$("#d").append(commentInfo);
		}
	});
}

function changeGuiAndShu() {
	var str = "已选:";
	var qty = 0;
	var gg = $(".color-list li");
	var sx = $(".size-list li");
	var isMatch0 = $("#isMatch0").val();
	var isMatch = $("#isMatch").val();
	if (isMatch0 == 0) {
		if (gg != null && gg != 'undefind') {
			str += '<i id="colorGu">'
					+ $('.color-list .select').attr("colorshow") + '</i>';
		}
	}
	if (isMatch == 0) {
		if (sx != null && sx != 'undefind') {
			var listSize = $("#listSize").val();
			for (var i = 0; i < listSize; i++) {
				str = str
						+ "/"
						+ $(
								"ul[id='" + (i)
										+ "'] li[attrValIdV][class='select'] a")
								.html();
			}
			// str += '<i id="sizeGu">'+$('.size-list .select a').html()+'</i>
			// &nbsp;&nbsp;';
			qty = $('.size-list .select').attr("skuQty");
		}
	}
	if (isMatch0 == 1 && isMatch == 1) {
		$(".t3").html("");
	} else {
		$(".t3").html(str);
	}

	/* $("#currentSkuQty").text(qty); */
}

function changeG() {
	// 规格
	$(".color-list li")
			.live(
					"click",
					function() {
						var colorShow = $(this).attr("colorshow");
						var skuQty = $('.size-list .select').attr("skuQty");
						// var sizeValue = $('.size-list .select a').html();
						var sizeValue = "";
						/* $("#currentSkuQty").text(skuQty); */
						var listSize = $("#listSize").val();
						for (var i = 0; i < listSize; i++) {
							sizeValue = sizeValue
									+ "/"
									+ $(
											"ul[id='"
													+ (i)
													+ "'] li[attrValIdV][class='select'] a")
											.html();
						}
						sizeValue = sizeValue.substring(1);
						$("#colorGu").text(colorShow);
						$("#sizeGu").text(sizeValue);
					})
	// 属性
	$(".size-list li")
			.live(
					"click",
					function() {
						var skuQty = $(this).attr("skuQty");
						// var sizeValue = $(this).find('a').text();
						var colorShow = $('.color-list .select').attr(
								"colorshow");
						var sizeValue = "";
						var listSize = $("#listSize").val();
						var attrvalidvs = "";
						for (var i = 0; i < listSize; i++) {
							sizeValue = sizeValue
									+ "/"
									+ $(
											"ul[id='"
													+ (i)
													+ "'] li[attrValIdV][class='select'] a")
											.html();
						}
						sizeValue = sizeValue.substring(1);
						$("#colorGu").text(colorShow);
						$("#sizeGu").text(sizeValue);
					})
}

function getCommodityList() {

	$.ajax({
		type : "get",
		url : $("#basePath").val()
				+ "/view/productMix/findProductInfosToJson?key=4069",
		data : "",
		dataType : "json",
		success : function(rdata) {
			var data = null;
			if (null != rdata) {
				data = rdata.data;
			} else {
				$(".commodity").hide();
				return;
			}
			if (null != data && data.length > 0) {
				var html = "";
				for (var i = 0; i < data.length; i++) {
					html += "<li>" + "<h2><a href='" + $("#path").val()
							+ "/item/get/" + data[i].pid
							+ "'><img src='http://image01.zhongjumall.com/"
							+ data[i].imageurl + "'></a></h2>"
							+ "<h3><a href='" + $("#path").val() + "/item/get/"
							+ data[i].pid + "'>" + data[i].B2cPname
							+ "</a></h3>" + "<p class='promotion-price'>"
							+ data[i].promotion_price + "</p>"
							+ "<p class='market-price'>市场价: "
							+ data[i].domestic_price + "</p>" + "</li>";
				}

				$("ul[class='commodity-list']").html(html);

				$(".commodity").show();
			} else {
				$(".commodity").hide();
			}
		}
	});
}