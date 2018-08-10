$(function() {

	// 加载搜索关键字补全功能
	// 手动修改数量
	$(".search-box").delegate("input", "input propertychange", function() {
		// 获取当前input输入的值
		var content = $(this).val();
		if ("" == content) {
			$(".search-tips").empty()
			return false;
		}
		// 发送ajax请求,获取列表
		// 获取搜索keyword
		var condition = "key=" + content;
		$.ajax({
			type : "post",
			url : $("#path").val() + "/searchController/getSuggestionKeyWord",
			data : condition,
			dataType : "html",
			success : function(commentInfo) {
				$(".search-tips").empty().append(commentInfo);
			}
		});
	});

	// 检索信息提示
	// 加载搜索关键字提醒
	/*
	 * (function(){ // 淘宝 var dataUrl =
	 * $("#path").val()+'/searchController/getSuggestionKeyWord'; new
	 * KISSY.Suggest('q', dataUrl, { autoFocus: true, }); })();
	 */

	// 热门和最新搜索切换
	$("#J_searchTabs li").on(
			"click",
			function() {
				var s = $(this);
				s.addClass("current").siblings().removeClass("current"), $(
						".J_searchContents").eq(s.index()).show().siblings(
						".J_searchContents").hide();
			});

	// 热门和最新搜索 触发搜索事件
	$(".J_searchContents li").filter(".nearSearch").on(
			"click",
			function(i, item) {
				var checkWord = $(this).find("a").text();
				window.location.href = $("#path").val()
						+ "/searchController/toSearchResult?keyword="
						+ checkWord;
			})

	// 搜索结果页面，属性搜索tab切换
	$("#J_filterTabs li").on("click", function() {
		var s = $(this);
		// 获取当前点击的li标签的attrName的值
		var attVal = s.attr("attrName");
		s.addClass("current").siblings().removeClass("current");
		$(".J_filterContents").hide();
		$(".J_filterContents").filter("#" + attVal).show();
		/* $(".J_filterContents").filter("#"+attVal).show().siblings(".J_filterContents").hide(); */
	});

	// 选中属性，添加样式
	$(".J_filterContents,.J_filterp").each(
			function(a, e) {
				var t = $(e).find("li");
				t.click(function(a) {
					$(this).hasClass("active") ? $(this).removeClass("active")
							: $(this).addClass("active").siblings("li")
									.removeClass("active");
				})
			})

	$(".fenlei").each(
			function(a, e) {
				var t = $(e).find("span");
				t.click(function(a) {
					console.log("---")
					$(this).parent().parent().siblings().find('span')
							.removeClass("active");
					$(this).parent().siblings().find('span').removeClass(
							"active");
					$(this).hasClass("active") ? $(this).removeClass("active")
							: $(this).addClass("active").siblings("span")
									.removeClass("active");
				})
			})

	//
	$(".but-del").click(function() {
		$('.J_filterp').find("li").removeClass("active");
	})

	// keyword关键字搜索
	$(".search-btn")
			.click(
					function() {

						// 防止js注入/^[\d]+$/
						if ($.trim($(".search-text").val()).indexOf("script") > 0) {
							$(".search-text").val("");
							// location.reload();
							return false;
						}

						// 获取内容
						var content = encodeURIComponent($.trim($(
								".search-text").val()));
						if ("" == content) {
							return false;
						}
						// 保存本地到本地cookie，如果没有，即第一次使用，需创建cookie
						var nearKeyword = getcookie("near");
						var exp = new Date();
						exp.setTime(exp.getTime() + 60 * 24 * 60 * 60 * 1000);
						if (undefined == nearKeyword) {
							document.cookie = "near=" + content + ";expires="
									+ exp.toGMTString() + ";path=/";
						} else {
							// 重构value
							var nowContent = nearKeyword + "_" + content;
							// 重置cookie
							document.cookie = "near=" + nowContent
									+ ";expires=" + exp.toGMTString()
									+ ";path=/";
						}
						// 发送请求
						window.location.href = $("#path").val()
								+ "/searchController/toSearchResult?keyword="
								+ content;

					})

	// 触发搜索
	/*
	 * $("#sousou").click(function(){ var keyword =
	 * $.trim($(".search-text").val()); var brand =""; var cyid =""; var price
	 * =""; var countryName =$.trim($("#countryName").val()); var cdid =
	 * $.trim($("#cdid").val()); //获取品牌信息 if($("#brand li").hasClass("active")){
	 * brand = $("#brand li").filter(".active").attr("attrValId"); }else{ brand =
	 * $.trim($("#selectedBrandName").val()); } if($("#cyid
	 * li").hasClass("active")){ cyid = $("#cyid
	 * li").filter(".active").attr("attrValId"); }else{ cyid =
	 * $.trim($("#selectedCyid").val()); } if($("#price
	 * li").hasClass("active")){ price = $("#price
	 * li").filter(".active").attr("attrValId"); }else{ price =
	 * $.trim($("#selectedPriceRange").val()); } var brand = $("#brand
	 * li").filter(".active").attr("attrValId"); var cyid = $("#cyid
	 * li").filter(".active").attr("attrValId"); var price = $("#price
	 * li").filter(".active").attr("attrValId");
	 * 
	 * //调用搜索 window.location.href =
	 * $("#path").val()+"/searchController/toSearchResult?keyword="+keyword+"&cyid="+cyid+"&brandName="+brand+"&sortType="+sortType+"&countryName="+countryName+"&cdid="+cdid+"&promotion="+promotion+"&priceRange="+price;
	 *  })
	 */

	// 获取指定名称的cookie的值
	function getcookie(objname) {
		var arrstr = document.cookie.split("; ");
		for (var i = 0; i < arrstr.length; i++) {
			var temp = arrstr[i].split("=");
			if (temp[0] == objname)
				return temp[1];
		}
	}

	// 滑动翻页
	window.product_list_loadFlag = true;
	var totalPage = Number($("#totalPage").val());
	var keyword = $.trim($("#keyword").val());
	var brand = $.trim($("#selectedBrandName").val());
	var supplierId = $.trim($("#selectedSupplierId").val());
	var cdid = $.trim($("#selectedCdid").val());
	var sortType = $.trim($("#sortType").val());
	var promotion = $.trim($("#selectedPromotion").val());
	var price = $.trim($("#selectedPriceRange").val());
	var countryName = $.trim($("#countryName").val());
	var b2csupply = $.trim($("#b2csupply").val());
	var fcode = $.trim($("#fcode").val());
	var pageNum = 2;
	$(window).scroll(
			function() {
				window.product_list_loadFlag = true;
				var totalheight = parseFloat($(window).height())
						+ parseFloat($(window).scrollTop()); // 浏览器的高度加上滚动条的高度
				if (($(document).height() <= totalheight)
						&& window.product_list_loadFlag) // 当文档的高度小于或者等于总的高度的时候，开始动态加载数据
				{
					totalPage = Number($("#totalPage").val());
					window.product_list_loadFlag = false;
					// 如果totalPage<2,不请求数据
					if (totalPage >= 2) {
						$(".loadw").find("span").html("加载中...");
						window.product_list_loadFlag = true;
						loadDataPager();
					} else {
						window.product_list_loadFlag = false;
						$(".loadw").find("span").html("已经到底啦!");
					}
				}
			});

	function loadDataPager() {
		// 获取搜索keyword
		totalPage = Number($("#totalPage").val());
		var condition = "keyword=" + keyword + "&cdid=" + cdid + "&brandName="
				+ brand + "&supplierId=" + supplierId + "&sortType=" + sortType
				+ "&pageno=" + pageNum + "&countryName=" + countryName
				+ "&promotion=" + promotion + "&priceRange=" + price
				+ "&b2csupply=" + b2csupply+"&fcode="+fcode;
		$.ajax({
			type : "post",
			url : $("#path").val() + "/searchController/toSearchResultScroll1",
			data : condition,
			dataType : "html",
			success : function(commentInfo) {
				$(".list").find(".loadw").before(commentInfo);
				pageNum++;
				// alert("123123"+condition);
				if (pageNum <= totalPage) {
					$(".loadw").find("span").html("加载中...");
					window.product_list_loadFlag = true;
				} else {
					window.product_list_loadFlag = false;
					$(".loadw").find("span").html("已经到底啦!");
				}

			}

		});
	}

	// 返回首页
	$("#goHomePage").click(function() {
		window.location.href = $("#path").val();
	})

	// 初始化触发，是否显示 排序 产地 和 价格
	// $("#selectSortType").click(function(){
	// $("#cdidContent").hide();
	// $("#cyidContent").hide();
	// $("#promotionContent").hide();
	// $("#sortTypeContent").toggle();
	// })
	//    
	// $("#selectCdid").click(function(){
	// $("#sortTypeContent").hide();
	// $("#cyidContent").hide();
	// $("#promotionContent").hide();
	// $("#cdidContent").toggle();
	// })
	//    
	// $("#selectCyid").click(function(){
	// $("#sortTypeContent").hide();
	// $("#cdidContent").hide();
	// $("#promotionContent").hide();
	// $("#cyidContent").toggle();
	// })
	//    
	// $("#selectPromotion").click(function(){
	// $("#sortTypeContent").hide();
	// $("#cdidContent").hide();
	// $("#cyidContent").hide();
	// $("#promotionContent").toggle();
	// })

	/*
	 * $("#cyidContent").click(function(){
	 * 
	 * cyid = $("#cyidContent ul li").filter(".active").attr("attrValId");
	 * if(cyid==undefined){ cyid=""; } $('.scr-list').html(""); var condition =
	 * "keyword="+keyword+"&cdid="+cdid+"&brandName="+brand+"&cyid="+cyid+"&sortType="+sortType+"&countryName="+countryName+"&promotion="+promotion+"&priceRange="+price+"&b2csupply="+b2csupply;
	 * $.ajax({ type : "post", url
	 * :$("#path").val()+"/searchController/toSearchResultScroll11", data :
	 * condition, dataType : "html", success : function(commentInfo){
	 * $(".scr-list").append(commentInfo); totalPage =
	 * Number$("#totalPage").val(); if(totalPage<2){
	 * window.product_list_loadFlag = false;
	 * $(".loadw").find("span").html("已经到底啦!"); } }
	 * 
	 * }); })
	 */

	/*
	 * $("#brandNameContent").click(function(){
	 * 
	 * brand = $("#brandNameContent ul
	 * li").filter(".active").attr("brandNameId"); if(brand==undefined){
	 * brand=""; } $('.scr-list').html(""); var condition =
	 * "keyword="+keyword+"&cdid="+cdid+"&brandName="+brand+"&cyid="+cyid+"&sortType="+sortType+"&countryName="+countryName+"&promotion="+promotion+"&priceRange="+price+"&b2csupply="+b2csupply;
	 * 
	 * $.ajax({ type : "post", url
	 * :$("#path").val()+"/searchController/toSearchResultScroll1", data :
	 * condition, dataType : "html", success : function(commentInfo){
	 * $(".scr-list").append(commentInfo); totalPage =
	 * Number$("#totalPage").val(); if(totalPage<2){
	 * window.product_list_loadFlag = false;
	 * $(".loadw").find("span").html("已经到底啦!"); } }
	 * 
	 * });
	 *  // cdid = $("#cdidContent ul li").filter(".active").attr("catalogsId"); //
	 * window.location.href =
	 * $("#path").val()+"/searchController/toSearchResult?keyword="+keyword+"&cyid="+cyid+"&brandName="+brand+"&sortType="+sortType+"&countryName="+countryName+"&cdid="+cdid+"&promotion="+promotion+"&priceRange="+price;
	 *  })
	 */

	/*
	 * $(".but-go").click(function(){ price = $("#promotionContent .priceul
	 * li").filter(".active").attr("priceValuesId"); b2csupply =
	 * $("#promotionContent .supplyul
	 * li").filter(".active").attr("supplyValuesId"); sortType =
	 * $("#promotionContent #sortTypeContent
	 * li").filter(".active").attr("priceval"); console.log("price = "+price+"
	 * ,supply = "+b2csupply +" ,sortType = "+sortType); if(price==undefined){
	 * price=""; } if(b2csupply==undefined){ b2csupply=""; }
	 * if(sortType==undefined){ sortType="0"; } $('.scr-list').html(""); var
	 * condition =
	 * "keyword="+keyword+"&cdid="+cdid+"&brandName="+brand+"&cyid="+cyid+"&sortType="+sortType+"&countryName="+countryName+"&promotion="+promotion+"&priceRange="+price+"&b2csupply="+b2csupply;
	 * console.log(condition) $.ajax({ type : "post", url
	 * :$("#path").val()+"/searchController/toSearchResultScroll1", data :
	 * condition, dataType : "html", success : function(commentInfo){
	 * $(".scr-list").append(commentInfo); totalPage =
	 * Number$("#totalPage").val(); if(totalPage<2){
	 * window.product_list_loadFlag = false;
	 * $(".loadw").find("span").html("已经到底啦!"); } }
	 * 
	 * }); $('.screen').hide(); $('#promotionContent').removeClass("show"); })
	 */

	$("#promotion").find("span").click(function() {
		price = $(this).attr("promotionId");
	})

	$("#price").find("span").click(function() {
		price = $(this).attr("pricevaluesid");
	})

	$("#sure").click(
			function() {
				if (price == undefined) {
					price = "";
				}
				$('.scr-list').html("");
				var condition = "keyword=" + keyword + "&cdid=" + cdid
						+ "&brandName=" + brand + "&supplierId=" + supplierId
						+ "&sortType=" + sortType + "&countryName="
						+ countryName + "&promotion=" + promotion
						+ "&priceRange=" + price + "&b2csupply=" + b2csupply+"&fcode="+fcode;
				$.ajax({
					type : "post",
					url : $("#path").val()
							+ "/searchController/toSearchResultScroll1",
					data : condition,
					dataType : "html",
					success : function(commentInfo) {
						$(".scr-list").append(commentInfo);
					}

				});
				// window.location.href =
				// $("#path").val()+"/searchController/toSearchResult?keyword="+keyword+"&cyid="+cyid+"&brandName="+brand+"&sortType="+sortType+"&countryName="+countryName+"&cdid="+cdid+"&promotion="+promotion+"&priceRange="+price;
			})

	$("#clear").click(
			function() {
				price = "";
				$('.scr-list').html("");
				var condition = "keyword=" + keyword + "&cdid=" + cdid
						+ "&brandName=" + brand + "&supplierId=" + supplierId
						+ "&sortType=" + sortType + "&countryName="
						+ countryName + "&promotion=" + promotion
						+ "&priceRange=" + price + "&b2csupply=" + b2csupply+"&fcode="+fcode;
				$.ajax({
					type : "post",
					url : $("#path").val()
							+ "/searchController/toSearchResultScroll1",
					data : condition,
					dataType : "html",
					success : function(commentInfo) {
						$(".scr-list").append(commentInfo);
					}

				});
			})

	/*
	 * $("#priceUp").click(function(){ sortType = "4"; window.location.href =
	 * $("#path").val()+"/searchController/toSearchResult?keyword="+keyword+"&cyid="+cyid+"&brandName="+brand+"&sortType="+sortType+"&countryName="+countryName+"&cdid="+cdid+"&promotion="+promotion+"&priceRange="+price+"&b2csupply="+b2csupply;
	 *  }) $("#priceDown").click(function(){ sortType = "3";
	 * window.location.href =
	 * $("#path").val()+"/searchController/toSearchResult?keyword="+keyword+"&cyid="+cyid+"&brandName="+brand+"&sortType="+sortType+"&countryName="+countryName+"&cdid="+cdid+"&promotion="+promotion+"&priceRange="+price+"&b2csupply="+b2csupply;
	 *  }) $("#priceNew").click(function(){ sortType = "1"; window.location.href =
	 * $("#path").val()+"/searchController/toSearchResult?keyword="+keyword+"&cyid="+cyid+"&brandName="+brand+"&sortType="+sortType+"&countryName="+countryName+"&cdid="+cdid+"&promotion="+promotion+"&priceRange="+price+"&b2csupply="+b2csupply;
	 *  })
	 */

	$(".fenlei li em").click(function() {
		$(this).parent().parent().find('.second-nav').toggle();
		$(this).toggleClass('up');
	})

	$(".screen").click(function() {
		$('.screen').hide();
		$('.maytt').removeClass("show");
		$('#promotionContent').removeClass("show");
		$('.may').removeClass("show");
	});

	$(".fenlei").find("span").click(
			function() {
				pageNum = 2;
				var cdids = $(this).filter(".active").attr("categoryId");
				supplierId = $("#supplierIdContent ul li").filter(".active")
						.attr("attrValId");
				brand = $("#brandNameContent ul li").filter(".active").attr(
						"brandNameId");
				price = $("#promotionContent .priceul li").filter(".active")
						.attr("priceValuesId");
				b2csupply = $("#promotionContent .supplyul li").filter(
						".active").attr("supplyValuesId");
				sortType = $("#promotionContent #sortTypeContent li").filter(
						".active").attr("priceval");
				if (price == undefined) {
					price = "";
				}
				if (b2csupply == undefined) {
					b2csupply = "";
				}
				if (sortType == undefined) {
					sortType = "0";
				}
				if (brand == undefined) {
					brand = "";
				}
				if (supplierId == undefined) {
					supplierId = "";
				}
				if (cdids == undefined) {
					cdid = "";
				} else {
					var strs = cdids.split('-');
					cdid = strs[strs.length - 1];
				}

				$('.scr-list').html("");
				$('.scr-list1').html("");
				var condition = "keyword=" + keyword + "&cdid=" + cdid
						+ "&brandName=" + brand + "&supplierId=" + supplierId
						+ "&sortType=" + sortType + "&countryName="
						+ countryName + "&promotion=" + promotion
						+ "&priceRange=" + price + "&b2csupply=" + b2csupply+"&fcode="+fcode;
				console.log(condition)
				$.ajax({
					type : "post",
					url : $("#path").val()
							+ "/searchController/toSearchResultScroll1",
					data : condition,
					dataType : "html",
					success : function(commentInfo) {
						$(".scr-list").append(commentInfo);
						totalPage = Number($("#totalPage").val());
						if (totalPage < 2) {
							window.product_list_loadFlag = false;
							$(".loadw").find("span").html("已经到底啦!");
						}
					}

				});
				$('.screen').hide();
				$('.maytt').removeClass("show");
			});

	$(".ser").click(
			function() {
				pageNum = 2;
				var cdids = $(".fenlei span").filter(".active").attr(
						"categoryId");
				supplierId = $("#supplierIdContent ul li").filter(".active")
						.attr("attrValId");
				brand = $("#brandNameContent ul li").filter(".active").attr(
						"brandNameId");
				price = $("#promotionContent .priceul li").filter(".active")
						.attr("priceValuesId");
				b2csupply = $("#promotionContent .supplyul li").filter(
						".active").attr("supplyValuesId");
				sortType = $("#promotionContent #sortTypeContent li").filter(
						".active").attr("priceval");
				if (price == undefined) {
					price = "";
				}
				if (b2csupply == undefined) {
					b2csupply = "";
				}
				if (sortType == undefined) {
					sortType = "0";
				}
				if (brand == undefined) {
					brand = "";
				}
				if (supplierId == undefined) {
					supplierId = "";
				}
				if (cdids == undefined) {
					cdid = "";
				} else {
					var strs = cdids.split('-');
					cdid = strs[strs.length - 1];
				}

				$('.scr-list').html("");
				$('.scr-list1').html("");
				var condition = "keyword=" + keyword + "&cdid=" + cdid
						+ "&brandName=" + brand + "&supplierId=" + supplierId
						+ "&sortType=" + sortType + "&countryName="
						+ countryName + "&promotion=" + promotion
						+ "&priceRange=" + price + "&b2csupply=" + b2csupply+"&fcode="+fcode;
				console.log(condition)
				$.ajax({
					type : "post",
					url : $("#path").val()
							+ "/searchController/toSearchResultScroll1",
					data : condition,
					dataType : "html",
					success : function(commentInfo) {
						$(".scr-list").append(commentInfo);
						totalPage = Number($("#totalPage").val());
						if (totalPage < 2) {
							window.product_list_loadFlag = false;
							$(".loadw").find("span").html("已经到底啦!");
						}
					}

				});
				$('.screen').hide();
				$('.maytt').removeClass("show");
				$('#promotionContent').removeClass("show");
			});

});

function initSearchNull() {
	$("input[class='but']").click(function() {
		gotoSearch()
	});

	$('#searchInput').keydown(function(e) {
		if (e.keyCode == 13) {
			gotoSearch();
			return false;
		}
	});

	$("#searchInput").focus();
}

function getCommodityList() {
	$
			.ajax({
				type : "post",
				url : $("#basePath").val()
						+ "/searchController/getJson?type=search",
				data : "",
				dataType : "json",
				success : function(data) {
					var str = "";
					if (null == data || data.length <= 0) {
						$(".commodity").hide();
						return;
					}
					for (var i = 0; i < data.length; i++) {
						str += "productIds=" + data[i].isid + "&";
					}

					str = str.substring(0, str.length - 1);

					$
							.ajax({
								type : "post",
								url : $("#basePath").val()
										+ "/view/productMix/findCommodityProduct?type=3&"
										+ str,
								data : "",
								dataType : "jsonp",
								success : function(data) {
									if (null != data && data.length > 0) {
										var html = "";
										for (var i = 0; i < data.length; i++) {
											html += "<li>"
													+ "<h2><a href='"
													+ $("#path").val()
													+ "/item/get/"
													+ data[i].productId
													+ "'><img src='"
													+ data[i].imgUrl
													+ "'></a></h2>"
													+ "<h3><a href='"
													+ $("#path").val()
													+ "/item/get/"
													+ data[i].productId
													+ "'>"
													+ data[i].pname
													+ "</a></h3>"
													+ "<p class='promotion-price'>"
													+ data[i].price
													+ "</p>"
													+ "<p class='market-price'>市场价: "
													+ data[i].domesticPrice
													+ "</p>" + "</li>";
										}

										$($("ul[class='commodity-list']")[0])
												.html(html);
									} else {
										$(".commodity").hide();
									}
								}
							});
				}
			});
}

$(document).ready(function() {
	var conditions = $(".search-box").find("input[type='hidden']");

	var hasCategory = false;
	var hasOthers = false;
	var cdid = "";

	$.each(conditions, function() {
		var id = $(this).attr("id");
		var value = $(this).val();

		if (id == "selectedCdid" && value != "") {
			hasCategory = true;
			cdid = value;
		}

		if (id != "selectedCdid" && value != "" && value != "0") {
			hasOthers = true;
		}
	});

	if (hasCategory && !hasOthers && cdid != "") {
		recommendsProducts(cdid);
	}

});

function recommendsProducts(cdid) {

	var path = $("#path").val();

	$
			.ajax({
				type : "post",
				dataType : "json",
				url : path + "/view/productMix/findProductInfosToJson",
				data : {
					"key" : cdid
				},
				success : function(resultData) {
					var result = resultData.result;
					if (result.result == "1") {

						var data = resultData.data;

						var str = "";
						for (var i = 0; i < data.length; i++) {

							var product = data[i];

							str += '<li><a href="' + path + '/item/get/'
									+ product.pid + '">';
							str += '<p class="picl"><img src="Http://image01.zhongjumall.com/p2/'
									+ product.imageurl + '"></p>';
							str += '<p class="txtr">';
							str += '	<span class="sptop"><b><img src="Http://image01.zhongjumall.com/p2/'
									+ product.countryImg
									+ '" style="border-radius:100%;"></b><em>'
									+ product.cyName
									+ '</em><i>'
									+ product.proStyleDescribe + '</i></span>';
							str += '	<span class="smidd"><font size="3">'
									+ product.B2cPname + '</font></span>';

							if (Number(product.domestic_price) > Number(product.unit_price)) {
								str += '	<span class="sdome">市场价：<b> </b><i>'
										+ Number(product.domestic_price)
												.toFixed(2) + '</i></span>';
							}

							if (product.boolPromotion) {
								product.unit_price = product.promotion_price;
							}
							if (product.prod_type == 6) {

								str += '<span class="yiTuBiao">福</span> 	<span class="sbtom"><b> </b><i>'
										+ Number(product.hqj) + '红旗券+'
								Number(product.cash_Hqj) + '元</i></span>';
							} else if (product.prod_type == 5) {

								str += '<span class="yiTuBiao">易</span>	<span class="sbtom"><b> </b><i>'
										+ Number(product.unit_price).toFixed(2)
										+ '</i></span>';
							} else {

								str += '	<span class="sbtom"><b> </b><i>'
										+ Number(product.unit_price).toFixed(2)
										+ '</i></span>';
							}

							str += '	</p></a></li>';
						}

						$(".scr-list").prepend(str);

					}
				}
			});
}
