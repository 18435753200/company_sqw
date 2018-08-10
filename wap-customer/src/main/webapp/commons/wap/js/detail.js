$(function(){
	
	swipe = [];
	  $(".swipe").each(function(i,item){
	    var iid = $(item).attr("id");
	    initslider(iid);
	  });
	  $(".swipe").removeClass('top');
	  $(".swipe").eq(0).addClass("top");
	  

      //判断是特卖商品50 税费不显示 加文案 显示倒计时
	  //var supply = $("#supply").val();
	  var proType = Number($("#supply").val());
	  $("#xianh").hide();
	  if(proType == 50){
		  $(".d-price").hide();
		  $("#xianh").hide();
		  $("#wuh").hide();
	  }
	  if(proType != 50){
		  $("#temai").hide();
	  }
	  
	  //初始化为第一个展示属性和展示属性对应的第一个sku添加样式
	  
		 //首先获取第一个展示属性，并添加样式first,last
		 var theFirstShowAttrElement = $("li[showAttrValId]").first().addClass("select");
		 var theFirstShowAttrElement_val = theFirstShowAttrElement.attr("showAttrValId");
		 //查找对应的sku中全部显示，并为第一个添加样式showAttrValId_sku
		 $("li[showAttrValId_sku='"+theFirstShowAttrElement_val+"']").show().first().addClass("select");
	  
		 
		//初始化，加载默认选中sku的价格以及关税信息
			var checkedSKU = $("li[showattrvalid_sku]").filter(".select");
			showPrice(checkedSKU);//零售价及市场价显示判断
			changePrice(checkedSKU);
			//判断当前采购数量是否满足本次选中的sku的最大库存
			checkBuyNum(checkedSKU);
			//显示与此sku相关促销信息
			showPromotionInfo(checkedSKU);
			//是否显示倒计时
//			showTimeTrim(checkedSKU);
			
			$("#oop").attr("src",$("#ooo").attr("src"));
	//切换展示属性关联的图片和规格
	$("li[showattrvalid]").on("click",function(){
		//获取属性之
		var  showattrvalid_val = $(this).attr("showattrvalid");
		//同级元素取出样式
		$(this).addClass("select").siblings("li[showattrvalid]").removeClass("select");
		//切换显示轮播
		changeCarousel($(this));
		//依据当前展示属性(例如：颜色)切换显示所拥有的sku规格(例如：X XL)
		$("li[showattrvalid_sku]").hide();
		$("li[showattrvalid_sku]").removeClass("select");
		//获取第一个sku规格
		var firstSKU = $("li[showattrvalid_sku='"+showattrvalid_val+"']").show().eq(0);//eq(0)
		firstSKU.addClass("select");
		//依据当前选中sku，重置价格信息
		changePrice(firstSKU);
		showPrice(firstSKU);//零售价及市场价显示判断
		//判断当前采购数量是否满足本次选中的sku的最大库存
		checkBuyNum(firstSKU);
		//展示促销信息
		showPromotionInfo(firstSKU);
		//是否显示倒计时
//				showTimeTrim(firstSKU);
		
		$("#oop").attr("src",$(".color-list .select a img").attr("src"));
	})
			
			//规格切换，例如 x XL XXL 
	$("li[showattrvalid_sku]").on("click",function(){
		//添加样式，去除同级样式
		$(this).addClass("select").siblings("li[showattrvalid_sku]").removeClass("select");
		changePrice($(this));
		showPrice($(this));//零售价及市场价显示判断
		//判断当前采购数量是否满足本次选中的sku的最大库存
		checkBuyNum($(this));
		//展示促销信息
		showPromotionInfo($(this));
		//是否显示倒计时
//		showTimeTrim($(this));
	})
			
	//加
	$(".amount-up").click(function(){
		
		//获取当前选中的规格
		var spec = $("li[showattrvalid_sku]").filter(".select");
		var that = $(this);
		var amountinput=that.prev(), num = amountinput.val(); 
 		//获取当前选中的sku的库存
		var stock=Number(spec.attr("skuQty"));
		//获取当前选中的sku的价钱
		var b2c_price = Number(spec.attr("unitPrice"));
		

		//判断为数字
		if(regint(num)){
			num = Number(num);
			if(stock<=0){
				return false;
			}
			if(num+1>stock){
				amountinput.val(stock);
				$(".stock-tips").show();
				window.setTimeout(function(){$(".stock-tips").hide()},1000);
			}else{
				amountinput.val(num+1);
			}
		}else{
			amountinput.val(0);
		}
		
		//判断库存
	})
	//减
	$(".amount-down").click(function(){
		//获取当前选中的规格
		var spec = $("li[showattrvalid_sku]").filter(".select");
		var that = $(this);
		var amountinput=that.next(), num = amountinput.val(); 
 		//获取当前选中的sku的库存
		var stock=Number(spec.attr("skuQty"));
		//获取当前选中的sku的价钱
		var b2c_price = Number(spec.attr("unitPrice"));

		//判断为数字
		if(regint(num)){
			num = Number(num);
			if(num-1<=0){
				amountinput.val(0);
			}else{
				amountinput.val(num-1);
			}
		}else{
			amountinput.val(0);
		}
		
		//判断库存
	})
	
	//手动修改数量
	$(".amount-control").delegate("input","input propertychange",function(){
		
		//获取当前选中的规格
		var spec = $("li[showattrvalid_sku]").filter(".select");
		//var that = $(this);
		//var amountinput=that.next(), num = amountinput.val(); 
 		//获取当前选中的sku的库存
		var stock=Number(spec.attr("skuQty"));
		//获取当前选中的sku的价钱
		var b2c_price = Number(spec.attr("unitPrice"));
		//获取input的value,并去除前面的零
		var inputVal=Number($(this).val().replace(/\b(0+)/gi,""));

		if(!regint(inputVal)){
			$(this).val(0);
		}else if(inputVal>=stock){
			$(this).val(stock);
			$(".stock-tips").show();
			window.setTimeout(function(){$(".stock-tips").hide()},1000);
		}else{
			$(this).val(inputVal);
		}
		//alert(inputVal);
		
	});
	
//配送地址
    $("#btn-select-region").on("click", function() {
        $("body").append('<div id="mask" class="mask"></div>');
		$("html").addClass("lockscreen"),
        $("body").addClass("lockscreen"),
        $("#J_filterWrap").addClass("show");
    })
	
    $("#mask,.closeBtn").live("click", function() {
        $("#mask").remove(),
        $("html").removeClass("lockscreen"),
        $("body").removeClass("lockscreen"),
        $("#J_filterWrap").removeClass("show")
    })
	
	//选中属性，添加样式
    $(".J_filterContents").each(function(a, e) {
        var t = $(e).find("li");
        t.click(function(a) {
            $(this).hasClass("active") ? $(this).removeClass("active") : $(this).addClass("active").siblings("li").removeClass("active")
        })
    })

    $(".data_city").bind("click", function(){
			/*var _this = $(this),
				_this_val = $.trim(_this.attr("data-info")),
				_this_val_new = "",
				closest_city = _this.closest("#select_city_area");
			//查市区
			$.sendAjax("/area/"+_this_val_new+".html","get",function(data){
				$("#show_city").html("");	//清空
				var data = $.parseJSON(data),
					html = "";
				$.each(data,function(key,entry){
					html += '<li><a href="javascript:void(0);" data-info="'+entry.code+'" class="data_area" >'+entry.name+'</a></li>';

				});
				$("#show_city").append(html);
			});*/
			$("#stock_province_item").hide();
			$("#stock_city_item").show();

	 });	// 选择城市

   //选择地区
	$(".filter-bd").on("click",".data_area",function(){
				$("#stock_city_item").hide();
				$("#stock_area_item").show();
	});
    $(".filter-bd").on("click",".select_area_js",function(){
		
		var stock= $("#stock_province_item li.active").text();
		var city= $("#stock_city_item li.active").text();
		var area= $("#stock_area_item li.active").text();
		var velcity=stock + " " + city + " " + area;
		$(".address span").text("");
		$(".address span").append(velcity);

	}); //选择地区
    
  //初始化，显示客户默认收货地址
	var areaIdKey = $("#areaIdKey").val();
	if( "" != areaIdKey ){
		var array_area = areaIdKey.split(',');
		$('.provi_city_area_id').attr('provinceId',array_area[0]);
		$('.provi_city_area_id').attr('cityId',array_area[1]);
		$('.provi_city_area_id').attr('areaId',array_area[2]);
		$('.provi_city_area_id').html(array_area[3]);
		
		//更新库存信息
		var pid = $("#PID").val();
		var provinceId = array_area[0];
		var cityId = array_area[1];
		var areaId = array_area[2];
		var totalAddress = array_area[3];
		loadArea(pid,provinceId,cityId,areaId,totalAddress);
	}else{
		$("#btn-select-region").click();
	}
	
	//税率显示
	 var proType = Number($("#supply").val());
	  if(21==proType || 1 ==proType){
		  $(".d-price").hide();
	  }
	  
	  
	  $(".d-price").click(function(){
		  $(".tax_mask").show();
	  });
	  
	  $(".tax_good h3 span").click(function(){
		  $(".tax_mask").hide();
	  });
	  
});


//-------------------定义函数---------------------------
//回退，关闭选择省市区页面
$("#mask,.closeBtn").live("click", function() {
	closePage();
})

function showPrice(element){
	var unitPrice = Number(element.attr("unitPrice")).toFixed(2); //单价
	var domesticPrice = Number(element.attr("domesticPrice")).toFixed(2);//国内价(市场价)
	unitPrice = parseFloat(unitPrice);
	domesticPrice = parseFloat(domesticPrice);
	if(domesticPrice<=unitPrice){
		$("p.shichang").hide();
	}
}

//去除锁屏，以及关闭省市区选择页面
function closePage(){
	$("#mask").remove(),
    $("html").removeClass("lockscreen"),
    $("body").removeClass("lockscreen"),
    $("#J_filterWrap").removeClass("show")
}

//立即购买
$(".buy-btn").on("click",function(){
	//确认当前是否可以立即购买
	if($(".addcart-btn").attr("sign")=="false"){
		return false;
	};
	//获取采购数量
	var number = Number($(".amount-input").val());
	if(0==number){
		//jAlert("请选择采购数量！");
		/*$.dialog({
            content : '请添加购买数量！',
            title:'',
            time: 500,
		});*/
		return false;
	}
	//当前选购商品总值超过1000 给予提示 返回（保税区商品）
	//真实价格
	var unitPrice = $(".unitPrice").text();
	//发货类型
	var supply = $("#supply").val();
	//console.log(unitPrice+'...'+supply+'---'+number);
	if (supply == '12'||supply == '14') {
		var price = Number(unitPrice)*Number(number);
		if ((Number(price) > 1000 && Number(number) > 1)) {
			$.dialog({
				content : "根据海关规定，单笔订单总价不可以超过1000元，<br/>如果单笔订单为一件且不可分割的商品除外。",
				title : '众聚猫提示',
				//height:248,
				//z-index: 9991000, 
				//minHeight:500,
				time:1500,
				lock : true
			});
			//showAlert("根据海关规定，单笔订单总价不可以超过1000元，如果单笔订单为一件且不可分割的商品除外。","确定",null);
			return false;
		}
	}
	//获取当前采购的kuid
	var skuId = $("li[showattrvalid_sku]").filter(".select").attr("skuId");
	window.location.href=$("#path").val()+"/cart/directBuy?skuId="+skuId+"&number="+number;
})

//加入购物车
$(".addcart-btn").on("click",function(){
	//确认当前是否可以提交购物车
	if($(".addcart-btn").attr("sign")=="false"){
		return false;
	};
	//获取采购数量
	var number = Number($(".amount-input").val());
	if(0==number){
		//jAlert("请选择采购数量！");
		/*$.dialog({
            content : '请添加购买数量！',
            title:'',
            time: 500,
		});*/
		return false;
	}
	//获取当前采购的kuid
	var skuId = $("li[showattrvalid_sku]").filter(".select").attr("skuId");
	
	var condition = "skuId="+skuId+"&number="+number+"";
	$.ajax({
		type : "post",
		url :$("#path").val()+"/cart/addItem",
		data : condition,
		dataType : "text",
		success : function(addCartFlag){
			var showMessage = "";
			switch(addCartFlag){
			case '20000':	// 添加成功
				//添加成功返回购物车内商品总数量
				$.ajax({
		    		type : "post",
		    		url :$("#path").val()+"/cart/qty",
		    		dataType : "text",
		    		success : function(totalQty){
		    			if(regint(totalQty)&&Number(totalQty)>0){
		    				//动态添加标签
    		    			$(".icon.i-cart-b").html("<i class='cart-num'>"+totalQty+"</i>");
		    			}/*else{
		    				$.dialog({
    		                    content : '添加购物车成功,查询数量失败！',
    		                    title:'',
    		                    ok : function() {
    		                        return true;
    		                    },
    		                    lock : true
    		    			});
		    				return false;
		    			}*/
		    		}
		    	});
				
				//弹出添加成功信息
				showMessage = "";
				break;
			case '50000':	// 添加失败
				showMessage = "添加购物车失败！";
				break;
			case '50001':	// 库存不足
				showMessage = "库存不足";
				break;
			
			case '50002':	// 购物车内商品数量超过100件
				showMessage = "购物车内商品数量超过100件";
				break;
			}
			
			/*$.dialog({
                content : showMessage,
                title:'',
                time: 500,
                lock : true
			});*/
		}
	});
	editGuige();
	$("#num").val(1);
	$('.colour').removeClass('show');
})


//切换轮播图集合
function changeCarousel(element){
	var s = element;
    var n = s.closest("li").index(),id = s.closest("li").attr("showattrvalid"); 
    $(".swipe").removeClass('top');
    $("#"+id).addClass("top");
    swipe[n].slide(0,10);
}
//重置商品价格
function changePrice(element){
	var unitPrice = Number(element.attr("unitPrice")).toFixed(2); //单价
	var domesticPrice = Number(element.attr("domesticPrice")).toFixed(2);//国内价
	var cxuprcie = Number(element.attr("skuprcie")).toFixed(2); //原价格
	//处理美元，返回值有单位，需要保留小数点后两位
	var totalForeignPrice = element.attr("foreignPrice");
	var foreignPrice = splitString(totalForeignPrice);
	var tar = Number(element.attr("tar")).toFixed(2);//关税
	
	var shuilv = $(".d-price").attr("shuilv");  //税率
	
	var shuifei_ = unitPrice*shuilv/100;
	
	//获取 notTar 标识，确认是否显示横线
	var notTar = Number(element.attr("notTar"));
	
	//重置页面价格
	$(".d-price .shuifei").text(shuifei_);
	$(".unitPrice").text(unitPrice);
	$(".domesticPrice").text(domesticPrice);
	$(".foreignPrice").text(foreignPrice);
	$(".unitPrices").text(cxuprcie);
	$(".tar").text(tar);
	if(1==notTar){
		$("font").addClass("line-through");
	}
	
	if(shuifei_<=50){
		$(".xiaoyu").show();
		$(".dayu").hide();
	}else{
		$(".dayu").show();
		$(".xiaoyu").hide();
	}
}

function checkBuyNum(element){
	//获取当前采购数量
	var number = Number($(".amount-input").val());
	var proType = Number($("#supply").val());
	//获取当前sku的库存
	var skuQty = Number(element.attr("skuQty"));
	/*if(number>skuQty){
		$(".amount-input").val(skuQty);
	}*/
	if(skuQty>0){
		//重置采购数量为1
		$(".amount-input").val(1);
		//隐藏库存不足信息
		$(".stock-tips").hide();
		//给购物车添加和立即购买添加的标识
		$(".buy-btn").attr("sign","yes");
		$(".addcart-btn").attr("sign","yes");
		//取出不可点击的样式
		$(".buy-btn").removeClass("c_gray3");
		$(".addcart-btn").removeClass("c_gray3");
		
		//改变 区域货存状态
		$("#wuh").hide();
		if(proType != 50){
			$("#xianh").show();
			$("#temai").hide();
		}
		if(50 == proType){
			$("#temai").show();
		}
		
//		console.log("1111");
	}else{
		//重置采购数量为1
		$(".amount-input").val(0);
		//显示库存不足信息
		$(".stock-tips").show();
		//移除加入购物车的click事件
		//$(".addcart-btn").removeAttr("onclick");
		//添加不可点击的标志
		$(".buy-btn").attr("sign","false");
		$(".addcart-btn").attr("sign","false");
		//添加购物车阴影
		$(".buy-btn").addClass("c_gray3");
		$(".addcart-btn").addClass("c_gray3");
		//改变 状态 货源状态
		$("#xianh").hide();
		$("#wuh").show();
		$("#temai").hide();//特卖无库存时
	}
}

//校验输入是否为数字
function regint(num){
	return /^[\d]+$/.test(num);
}
//截取字符串，小数点后两位
function splitString(str){
	var index = str.indexOf(".");
	if(index == -1){
		return str;
	}
	return str.substring(0,index+3);
}

//处理轮播
function initslider(id){
   var elm = document.getElementById(id);
   if (!elm) return ;
   var doc = document.getElementById(id+'-doc') ;
   var slides = elm.children[0].children;
   var temp = '';
   var index = 0 ;
   for (var i = 0 , len=slides.length; i < len ; i++) {
       temp += '<em '+(i==0?'class="on"':'')+'></em>';
   }
   doc.innerHTML = temp ;
   var docs = doc.querySelectorAll('em');
   swipe.push(new Swipe(elm,{
   	speed: 400,
   	//auto : 3000,
   	continuous: true,
   	callback : function (pos) {
   		docs[index].className='';
   		docs[pos].className='on';
   		index = pos ;
   	}
   }));
}

var Flash = function(){
	 var imgh = $('.no-b').height();
	 $('.shop_cart a').css({"height": imgh-15 + "px"});
	 
	 //首页焦点图
	 var swiper = new Swiper('#detail-container', {
        pagination: '#detail-pagination',
        //nextButton: '#index-button-next',
       // prevButton: '#index-button-prev',
        slidesPerView: 1,
        paginationClickable: true,
        spaceBetween: 0,
        loop: true
     });// JavaScript Document
}


function colour(){
	var oHeight=document.documentElement.clientHeight;
	var oTop=document.querySelector('.colour_top');
	var oBot=document.querySelector('.colour_bot');

	oTop.style.height=oHeight-oBot.offsetHeight+'rem';
}


function closes(){
	var oGood=document.getElementById('goods');
	var oColo=document.querySelector('.colour');

	oGood.onclick=function(){
		oColo.classList.add('show');
		var oTop=document.querySelector('.colour_top');
		oTop.onclick=function(){
			oColo.classList.remove('show');
			editGuige();
		};
	};
	
}

function editGuige(){
	console.log($('.amount-control #num').attr("value"))
	var str = "";
	var gg = $(".color-list li");
	var sx = $(".size-list li");
	if(gg!=null&&gg!='undefind'){
		str += '"'+$('.color-list .select').attr("colorshow")+'"  ';
	}
	if(sx!=null&&sx!='undefind'){
		str += '"'+$('.size-list .select a').html()+'" &nbsp;&nbsp;'
			+$('.amount-control #num').attr("value")
			+$('.size-list .select').attr('measureName');
	}
	$("#goods .yetleft span em").html(str);
}

function toCart(){
	var oGoods = document.getElementById('goodss');
	var oColo=document.querySelector('.colour');
	oGoods.onclick=function(){
		oColo.classList.add('show');
		var oTop=document.querySelector('.colour_top');
		oTop.onclick=function(){
			oColo.classList.remove('show');
		};
	};
}

function goodtab(abj1,abj2){
	for(var i=0;i<abj1.length;i++){
		(function(index){
			abj1[i].onclick=function(){
				tab1(index);
			}
		})(i);
	}
	function tab1(index){
		for(var i=0;i<abj1.length;i++){
			abj1[i].classList.remove('cur');
			abj2[i].classList.remove('show');
		}
		abj1[index].classList.add('cur');
		abj2[index].classList.add('show');
	}
}

function colour(){
	var oHeight=document.documentElement.clientHeight;
	var oTop=document.querySelector('.colour_top');
	var oBot=document.querySelector('.colour_bot');
	var oNull=document.getElementById('null');

	oTop.style.height=oHeight-oBot.offsetHeight+'px';
}

//配送地址
$("#btn-select-region").on("click", function() {
    $("body").append('<div id="mask" class="mask"></div>');
	$("html").addClass("lockscreen"),
    $("body").addClass("lockscreen"),
    
    $("#J_filterWrap").addClass("show");
	
	
	//隐藏省份,市区 ,显示country
	$("#stock_province_item").show();
	$("#stock_city_item").hide();
	$("#stock_area_item").hide();
	//异步加载省份信息,判断页面是否已加载过
	if($("#stock_province_item li").size()<=0){
		getProvice();
	}
	
})


//选择省市区，添加样式,去除同辈元素
$(".filter-bd li").live("click", function() {
	$(this).addClass("active").siblings("li").removeClass("active");
	//获取，当前li标签的flag的值
	var content = $.trim($(this).children().attr("flag"));
	//如果选择的省份信息
	if("provice"==content){
		var provice_id = $.trim($(this).children().attr("provice_id"));
		//获取市区信息
		getCity(provice_id);
		
	}else if("city"==content){
		var city_id = $.trim($(this).children().attr("city_id"));
		//获取市区信息
		getCountry(city_id);
		
	}else if("area"==content){
		//关闭
//		closePage();
		//获取已选择的省市区
		var provice_ele = $("#stock_province_item li").filter(".active").children();
		var city_ele = $("#stock_city_item li").filter(".active").children();
		var country_ele = $("#stock_area_item li").filter(".active").children();
		//重置配送地址
		var totalAddress = provice_ele.html()+" "+city_ele.html()+" "+country_ele.html();
		//重置页面显示
		$(".provi_city_area_id").attr('provinceId',provice_ele.attr("provice_id"))
								.attr('cityId',city_ele.attr("city_id"))
								.attr('areaId',country_ele.attr("area_id"))
								.html(totalAddress);
		//更新库存信息
		var pid = $("#PID").val();
		var provinceId = provice_ele.attr("provice_id");
		var cityId = city_ele.attr("city_id");
		var areaId = country_ele.attr("area_id");
		loadArea(pid,provinceId,cityId,areaId,totalAddress);
		 $("#J_filterWrap").removeClass("show");
		 $("#mask").remove();
		 $("#mask").remove();
	}
	
});


var  loadArea= function(pid,provinceId,cityId,areaId,areaName){
	
	
	var  data_arr = new Array();
	
	data_arr.push("productId="+pid);
	data_arr.push("provinceId="+provinceId);
	data_arr.push("cityId="+cityId);
	data_arr.push("districtId="+areaId);
	data_arr.push("areaName="+areaName);
//	data_arr.push("callback="+"");
	$.ajax({
		type : "post", 
		data:data_arr.join("&")+'&math='+Math.random(),
		url : $("#path").val()+"/item/stock",
		dataType:"json",
		success : function(msg) {
			if(msg === '0'){
				console.log("库存服务异常！");
				/*$.dialog({
	                content : "",
	                title:'',
	                time: 500,
	                lock : true
				});*/
			}
			/*{"istate":1,"productId":2003519749245145,
			 * "skuStockDtos":[
			 * {"futuresQty":0,"skuId":142917427878486852,"spotQty":0},
			 * {"futuresQty":0,"skuId":142917427878414462,"spotQty":0},
			 * {"futuresQty":0,"skuId":142917427878497652,"spotQty":0},
			 * {"futuresQty":0,"skuId":142917427878400196,"spotQty":0}],"supply":0}*/
			if(msg.skuStockDtos!=''){
				$.each(eval(msg.skuStockDtos),function(i,n){
					//skuid 太长 溢出了
					var skuid = n.skuIdV;
					var futuresQty = n.futuresQty;
					var spotQty =n.spotQty;
					$('li[skuqty]').each(function(j,m){
						if ( $(this).attr('skuid') != undefined ){
							if (skuid === $(this).attr('skuid')){
								/*var apec_type=$(this).closest('.p-amount').attr('spec_type');
								if (apec_type ==='0'){
									$(this).find('.stock').text(spotQty);
								};
								if (apec_type === '1'){
									$(this).find('.stock').text(futuresQty);
								};*/
								//重置库存
								$(this).attr('skuqty',spotQty);
								if(0==spotQty){
									$("#wuh").show();
									$("#xianh").hide();
									$("#temai").hide();//特卖无库存时
								}else{
									$("#wuh").hide();
									$("#xianh").show();
									$("#temai").show();
								}
							}
						}
					});

				});
			}
			
			//重新选择第一个sku
			//$("li[showattrvalid_sku]").first().click();
			
			 //首先获取第一个展示属性，并添加样式first,last
			 var theFirstShowAttrElement = $("li[showAttrValId]").filter(".select").attr("showAttrValId");
			 $("li[showAttrValId_sku='"+theFirstShowAttrElement+"']").show().first().click();			
		},
		error:function(){
			//alert("服务异常!");
			/*$.dialog({
                content : "",
                title:'',
                time: 1000,
                lock : true
			});*/
		}
	});
	
}

//初始省份信息
function getProvice(){
	$("#stock_province_item ul").html("");
	
	$.ajax({
		type : "post",
		url :$("#path").val()+"/address/getProvice",
		dataType : "json",
		   success: function(res){
			   if(res==null ||res =="") {return false;}
			   //var pros=eval('(' + res + ')');
			//   $("#cityId").append('<option value=""  >'+请选择+'</option> ');
			   for (var i = 0; i < res.length; i++) {
				   $("#stock_province_item ul").append('<li><a href="javascript:void(0);" provice_id="'+res[i].provinceid+'" flag = "provice"  class="data_provice">'+res[i].provincename+'</a></li>');
			   }
			   /*if(cityId!=null){
				   $("#cityId").val(cityId);
			   }
			   */
		   },
			error:function(){
				showErrorMsg("网络连接超时，请您稍后重试");
		   }
		}); 
	
	
}



//初始化市区信息
function getCity(provice_id){
	//隐藏省份,显示市区
	$("#stock_province_item").hide();
	$("#stock_city_item").show();
	//清除页面缓存旧信息
	$("#stock_city_item ul").html("");
	var  condition = "provinceId="+provice_id;
	$.ajax({
		type : "post",
		url :$("#path").val()+"/address/getCity",
		data : condition,
		dataType : "json",
		   success: function(res){
			   if(res==null ||res =="") {return false;}
			   for (var i = 0; i < res.length; i++) {
				   $("#stock_city_item ul").append('<li><a href="javascript:void(0);" city_id="'+res[i].cityid+'" flag = "city" class="data_area">'+res[i].cityname+'</a></li>');
			   }
			   /*if(cityId!=null){
				   $("#cityId").val(cityId);
			   }*/
			   
		   },
			error:function(){
				showErrorMsg("网络连接超时，请您稍后重试");
		   }
		}); 
	
	
	
}


//加载区域信息
function getCountry(city_id){
	//隐藏省份,市区 ,显示country
	$("#stock_province_item").hide();
	$("#stock_city_item").hide();
	$("#stock_area_item").show();
	//清除页面缓存旧信息
	$("#stock_area_item ul").html("");
	var  condition = "cityId="+city_id;
	
	$.ajax({
		type : "post",
		url :$("#path").val()+"/address/getArea",
		data : condition,
		dataType : "json",
		success: function(res){
		   if(res==null ||res =="") {return false;}
		//   $("#cityId").append('<option value=""  >'+请选择+'</option> ');
		   for (var i = 0; i < res.length; i++) {
			   $("#stock_area_item ul").append('<li><a href="javascript:void(0);" area_id="'+res[i].countyid+'" flag = "area" class="select_area_js">'+res[i].countyname+'</a></li>');
		   }
		   /*if(cityId!=null){
			   $("#cityId").val(cityId);
		   }*/
		   
		},
			error:function(){
				alert("网络连接超时，请您稍后重试");
		   }
		}); 
	
}
//-------详情页 app跳转
$(function(){
	window.onresize=colour;
	colour();
	closes();
	var aLi=document.querySelectorAll('.detail_atit li');
	var aDiv=document.querySelectorAll('.detail_abot .has');
	goodtab(aLi,aDiv);
	pull();
	
	var oShut=document.querySelector('.down_shut');
	var oDownl=document.querySelector('.download');
	oShut.onclick=function(){
		oDownl.style.display='none';
	};
	
	function txt(){
		var oBody=document.getElementsByTagName('body')[0];
		var isIOS = navigator.userAgent.match('iPad')||navigator.userAgent.match('iPhone')||navigator.userAgent.match('iPod');
		var isAndroid = navigator.userAgent.match('Android');
		 var timeout, t = 400, hasApp = true;
		 setTimeout(function () {
			 if (hasApp) {
				 return;
			 } else {
				 if(isIOS){
					 window.location.href='https://itunes.apple.com/us/app/xin-shi-jie/id1000578209?l=zh&ls=1&mt=8';//https://appsto.re/cn/HY5O7.i			
				 }else if(isAndroid){
					 window.location=$("#path").val()+"/item/view";
				 }      
			 }
		 }, 800);
		 function testApp() {
			 var t1 = Date.now();
			 var ifr = document.createElement('iframe');
			 ifr.setAttribute('src','ccigmall://m.zhongjumall.com/item/get/'+pid);
			 ifr.style.display='none';
			 oBody.appendChild(ifr);
			 setTimeout(function () {
				 try_to_open_app(t1);
			 }, t);
		 }
		 function try_to_open_app(t1) {
			 var t2 = Date.now();
			 if (t2-t1<t+30) {
				 hasApp = false;
			 }
		}
		testApp();        
	}
	var oDown=document.querySelector('.down_taste');
	var pid=document.getElementById('PID').value;
	oDown.onclick=txt;
});



function pull(){
    var startY=0;
    var endY=0;
    var oDiv=document.querySelector('.detail_all')
    var oPull=document.querySelector('#pull');
    oPull.addEventListener('touchstart',function(ev){
            oDiv.style.display='block';
            startY=ev.targetTouches[0].pageY;
            oPull.addEventListener('touchend',endFN,false)
            function endFN(ev){
                endY=ev.changedTouches[0].pageY;              
                var disY=endY-startY;
                if(disY<0){
                    document.body.scrollTop=document.body.scrollTop+disY+'px';
                }else{
                    oDiv.style.display='none';
                }
            }
            return false;
    },false)
}

//--------------展示促销信息
function showPromotionInfo(element){
	//首先隐藏所有的促销信息
	$("div[promotion_skuId]").hide();
	$("li[promotion_info_skuId]").hide();
	//获取skuid
	var skuId = element.attr("skuid");
	//展示促销 类型
	$("div[promotion_skuId='"+skuId+"']").show();
	//展示促销信息
	$("li[promotion_info_skuId='"+skuId+"']").show();
}

//是否显示时钟
function showTimeTrim(element){
	//以藏倒计时
	$("#showtime").hide();
	//获取当前sku的id
	var skuId = element.attr("skuid");
	//获取此的促销信息
	var promotionInfo = $("div[promotion_skuId='"+skuId+"']").first()
	//首先确定该sku是否直降
	var promotion_priceDownFlag = promotionInfo.attr("promotion_priceDownFlag");
	//是否显示倒计时
	if("true"==promotion_priceDownFlag){
		//显示倒计时
		$("#showtime").show();
		//获取直降时间
		var priceDownDate = $.trim(promotionInfo.attr("priceDownDate"));
		//转为date格式
		var priceDownDate = new Date(priceDownDate.replace(/-/g,"/"));
		var time_stamp = priceDownDate.getTime()/1000;
		//调用倒计时
		//var time_stamp = $("#showtime").attr("data-endtime");
		var s_days = 0;
		var s_hours = 0;
		var s_minutes = 0;
		var s_seconds = 0;
		if (time_stamp) {
		    blues_rushbuy.timer({
		        deadline: time_stamp,
		        //最终结束的时间戳,
		        callback: function() {
		        	//时间结束后，隐藏倒计时
		        	$("#showtime").hide();
		        	//时间结束后，将时间全部置为0
		            /*showStr = '<span id="rush_day">00</span>：<span id="rush_shi">00</span>：<span id="rush_fen">00</span>：<span id="rush_miao">00</span>';
		            $("#showtime").html(showStr);*/
		        },
		        //时间结束后的方法,
		        secdom: rush_miao,
		        //秒的dom节点,
		        mintdom: rush_fen,
		        //分钟的dom节点,
		        hourdom: rush_shi,
		        //小时的dom节点,
		        daydom: rush_day,
		        //小时的dom节点,
		    });
		} else {
			//如果获取的后台设置的时间为零或者""，隐藏倒计时
			$("#showtime").hide();
        	//时间结束后，将时间全部置为0
		    /*showStr = "<span>00</span>：<span>00</span>：<span>00</span>：<span>00</span>";
		    $("#showtime").html(showStr);*/
		}
	
	}
	
}


function commentInfo(pid){
	/*var pid="2709626906347718";*/
	/*var condition="pid="+pid;*/	
	$.ajax({
		type : "post",
		url :$("#path").val()+"/comment/"+pid,
		dataType : "html",
		success : function(commentInfo){
			$("#commentInfo").html("");
			$("#commentInfo").append(commentInfo);
		}
	});
}


//评论触发
/*$("#commentInfo").click(){
	//发送异步请求
	var pid = '966649148651077';
	var condition="pid="+pid;	
	$.ajax({
		type : "post",
		url :$("#path").val()+"/findAllTypeComment",
		data : condition,
		dataType : "html",
		success : function(commentInfo){
			$("#commentInfo").html("");
			$("#commentInfo").append(commentInfo);
		}
	})
}*/

