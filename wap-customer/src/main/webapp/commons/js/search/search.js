$(function(){
	
	//加载搜索关键字补全功能
	//手动修改数量
	$(".search-box").delegate("input","input propertychange",function(){
		//获取当前input输入的值
		var content = $(this).val();
		if(""==content){
			$(".search-tips").empty()
			return false;
		}
		//发送ajax请求,获取列表
		//获取搜索keyword
    	var condition = "key="+content;
    	$.ajax({
    		type : "post",
    		url :$("#path").val()+"/searchController/getSuggestionKeyWord",
    		data : condition,
    		dataType : "html",
    		success : function(commentInfo){
    			$(".search-tips").empty().append(commentInfo);
    		}
    	});
	});
	
	
	//检索信息提示
	//加载搜索关键字提醒
	/*(function(){
	    // 淘宝
	    var dataUrl = $("#path").val()+'/searchController/getSuggestionKeyWord';
	    new KISSY.Suggest('q', dataUrl,
	        { autoFocus: true,
	        });
	})();*/
	
    $J_filter = $("#J_filter"),
    $J_filterWrap = $("#J_filterWrap"),
    $J_filterclose = $J_filterWrap.find(".closeBtn");
    $($J_filter).on("click", function() {
        var $mask = $("#mask");
        $mask.length < 1 && ($("body").append('<div id="mask" class="mask"></div>')),
        $("html").addClass("lockscreen"),
        $("body").addClass("lockscreen"),
        $J_filterWrap.addClass("show")
    }),
    $("#mask,.closeBtn").live("click", function() {
        $("#mask").remove(),
        $("html").removeClass("lockscreen"),
        $("body").removeClass("lockscreen"),
        $J_filterWrap.removeClass("show")
    }),
    
    //热门和最新搜索切换
    $("#J_searchTabs li").on("click", function() {
        var s = $(this);
        s.addClass("current").siblings().removeClass("current"), $(".J_searchContents").eq(s.index()).show().siblings(".J_searchContents").hide();
    });

    //热门和最新搜索  触发搜索事件
    $(".J_searchContents li").filter(".nearSearch").on("click",function(i,item){
    	var checkWord = $(this).find("a").text();
    	window.location.href = $("#path").val()+"/searchController/toSearchResult?keyword="+checkWord;
    })
    
    //搜索结果页面，属性搜索tab切换
    $("#J_filterTabs li").on("click", function(){
        var s = $(this);
        //获取当前点击的li标签的attrName的值
        var attVal = s.attr("attrName");
        s.addClass("current").siblings().removeClass("current");
        $(".J_filterContents").hide();
        $(".J_filterContents").filter("#"+attVal).show();
        /*$(".J_filterContents").filter("#"+attVal).show().siblings(".J_filterContents").hide();*/
    });
    
    //选中属性，添加样式
    $(".J_filterContents").each(function(a, e) {
        var t = $(e).find("li");
        t.click(function(a) {
            $(this).hasClass("active") ? $(this).removeClass("active") : $(this).addClass("active").siblings("li").removeClass("active")
        })
    })
    
    //keyword关键字搜索
    $(".search-btn").click(function(){
    	
    	//防止js注入/^[\d]+$/
    	if($.trim($(".search-text").val()).indexOf("script")>0){
    		$(".search-text").val("");
    		//location.reload();
    		return false;
    	}
    	
    	//获取内容
    	var content = encodeURIComponent($.trim($(".search-text").val()));
    	if(""==content){
    		return false;
    	}
    	//保存本地到本地cookie，如果没有，即第一次使用，需创建cookie
    	var nearKeyword = getcookie("near");
    	var exp = new Date(); 
	    exp.setTime(exp.getTime() + 60*24*60*60*1000); 
    	if(undefined==nearKeyword){
    		document.cookie="near="+content+";expires=" + exp.toGMTString()+";path=/"; 
    	}else{
    		//重构value
    		var nowContent = nearKeyword+"_"+content;
    		//重置cookie
    		document.cookie="near="+nowContent+";expires=" + exp.toGMTString()+";path=/"; 
    	}
    	//发送请求
    	window.location.href = $("#path").val()+"/searchController/toSearchResult?keyword="+content;
    	
    })
    
    //初始化触发，是否显示 品牌 产地 和  价格
    
    //触发搜索
    $("#sousou").click(function(){
    	var keyword = $.trim($(".search-text").val());
    	var brand =""; 
    	var cyid ="";
    	var price ="";
    	var countryName =$.trim($("#countryName").val());
    	var cdid = $.trim($("#cdid").val());
    	//获取品牌信息
    	if($("#brand li").hasClass("active")){
    		brand = $("#brand li").filter(".active").attr("attrValId");
    	}else{
    		brand = $.trim($("#selectedBrandName").val());
    	}
    	if($("#cyid li").hasClass("active")){
    		cyid = $("#cyid li").filter(".active").attr("attrValId");
    	}else{
    		cyid = $.trim($("#selectedCyid").val());
    	}
    	if($("#price li").hasClass("active")){
    		price = $("#price li").filter(".active").attr("attrValId");
    	}else{
    		price = $.trim($("#selectedPriceRange").val());
    	}
    	/*var brand = $("#brand li").filter(".active").attr("attrValId");
    	var cyid = $("#cyid li").filter(".active").attr("attrValId");
    	var price = $("#price li").filter(".active").attr("attrValId");*/
    	
    	//调用搜索
    	window.location.href = $("#path").val()+"/searchController/toSearchResult?keyword="+keyword+"&brandName="+brand+"&cyid="+cyid+"&priceRange="+price+"&countryName="+countryName+"&cdid="+cdid;                    ;
    	
    })
    
    //获取指定名称的cookie的值
    function getcookie(objname){
		var arrstr = document.cookie.split("; ");
		for(var i = 0;i < arrstr.length;i ++){
			var temp = arrstr[i].split("=");
			if(temp[0] == objname) 
				return temp[1];
		}
	}
    
    //滑动翻页
    window.product_list_loadFlag = true;
    var totalPage = Number($("#totalPage").val());
    var keyword = $.trim($(".search-text").val());
    var brand = $.trim($("#selectedBrandName").val());
    var cyid = $.trim($("#selectedCyid").val());
    var price = $.trim($("#selectedPriceRange").val());
    var countryName = $.trim($("#countryName").val());
    var pageNum =1;
    $(window).scroll(function(){
    	window.product_list_loadFlag = true;
        var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());     //浏览器的高度加上滚动条的高度
        if (($(document).height() <= totalheight)&&window.product_list_loadFlag)     //当文档的高度小于或者等于总的高度的时候，开始动态加载数据
        {
        	totalPage = Number($("#totalPage").val());
            window.product_list_loadFlag = false;
            //如果totalPage<2,不请求数据
            if(totalPage>=2){
            	$(".loadw").find("span").html("加载中...");
            	window.product_list_loadFlag = true;
            	loadDataPager();
            }else{
            	window.product_list_loadFlag = false;
            	$(".loadw").find("span").html("已经到底啦!");
            }
        }   
   });
    
    function loadDataPager(){
    	//获取搜索keyword
    	totalPage = Number($("#totalPage").val());
    	var condition = "keyword="+keyword+"&brandName="+brand+"&cyid="+cyid+"&priceRange="+price+"&pageno="+pageNum+"&countryName="+countryName;
    	$.ajax({
    		type : "post",
    		url :$("#path").val()+"/searchController/toSearchResultScroll1",
    		data : condition,
    		dataType : "html",
    		success : function(commentInfo){
    			$(".p-list").append(commentInfo);
    			 pageNum++;
    			 if(pageNum<=totalPage){
    				 $(".loadw").find("span").html("加载中...");
    				 window.product_list_loadFlag = true;
    			 }else{
    				 window.product_list_loadFlag = false;
    				 $(".loadw").find("span").html("已经到底啦!");
    			 }
    		}
    	});
   } 
});

function initSearchNull(){
	$("input[class='but']").click(function(){gotoSearch()});
	
	$('#searchInput').keydown(function(e){ 
		if(e.keyCode==13){ 
			gotoSearch();
			return false;
		} 
	}); 
	
	$("#searchInput").focus();
}
function initSearchIndex() {
	
	initSearchNull();
	
	$("#hostListDiv a").each(function(){
	    $(this).attr("href", "javascript:gotoSearch('" + $(this).text() + "');");
	});
	
	var ss = getCookie("sqw_search_history_list");
	//var ss = getCookie("ccigmall_search_history_list");
	
	if (null != ss && "" != ss) {
		
		var arr = ss.split(";");
		var div = "";
		var length = arr.length;
		if (length > 10) {
			length = 9;
		}
		else {
			--length;
		}
		for (var i = 0; i <= length; i++) {
			div += "<a href=''>" + arr[i] + "</a>"
		}
		$("#historySearchListDiv").html(div);
	}
	
	$(".remove-history").click(removeHistory);
	$("#historySearchListDiv a").each(function(){
		$(this).attr("href", "javascript:gotoSearch('" + $(this).text() + "');");
	});
}
function removeHistory() {
	$("#historySearchListDiv").html("");
	setCookie("sqw_search_history_list", "", null);
	//setCookie("ccigmall_search_history_list", "", null);
}
function gotoSearch(v){
	if (null == v || "" == v) {
		v = $("#searchInput").val();
	}

	if (null == v || "" == v || "" == v.trim()) {
		return;
	}
	v = v.trim();
	var cv = v + ";";
	
	var s = getCookie("sqw_search_history_list");
	//var s = getCookie("ccigmall_search_history_list");
	
	if (null != s && ""!= s) {
		var list = s.split(";");
		
		for (var i = 0; i < list.length; i++) {
			if (v == list[i]) {
				continue;
			}
			
			if (i > 10) {
				break;
			}
			
			cv += list[i] + ";";
		}
	}
	cv = cv.substring(0, cv.length-1);
	var c_expired_date = new Date();
	c_expired_date.setDate(c_expired_date.getDate()+30);
	
	setCookie("sqw_search_history_list", cv, c_expired_date);
	//setCookie("ccigmall_search_history_list", cv, c_expired_date);
	
	nnnnnurl = $("#basePath").val() + "/searchController/toSearchResult?keyword=" + v;
	window.location.href = nnnnnurl;
}
var nnnnnurl = '';
function getCookieDecodeURI(c_name){
	//判断当前浏览器是否有cookie
	if (document.cookie.length>0){
	 //获取当前需要的cookie的key对应出现的位置
	  var c_start = document.cookie.indexOf(c_name + "=");
	  if (c_start!=-1){ 
		//定位长度  
		c_start = c_start + c_name.length+1;
		//切到第一次出现当前key对应的分号位置
		var c_end = document.cookie.indexOf(";",c_start);
		if (c_end==-1) 
			c_end=document.cookie.length;
		return decodeURI(document.cookie.substring(c_start,c_end));
		} 
	  }
	return "";
}

function getCommodityList(){
	$.ajax({
		type : "post",
		url : $("#basePath").val() + "/view/productMix/findProductInfosToJson?key=3333",
		data : "",
		dataType : "json",
		success : function(rdata){
			var data = null;
			if (null != rdata) {
				data = rdata.data;
			}
			else {
				$(".commodity").hide();
				return;
			}
			if (null != data && data.length > 0) {
				var html = "";
				for (var i = 0; i < data.length; i++) {
					html += "<li>"
						+ "<h2><a href='"+$("#path").val() + "/item/get/" + data[i].pid +"'><img src='http://image01.zhongjumall.com/" + data[i].imageurl + "'></a></h2>"
						+ "<h3><a href='"+$("#path").val() + "/item/get/" + data[i].pid +"'>" + data[i].B2cPname + "</a></h3>"
						+ "<p class='promotion-price'>" + data[i].promotion_price + "</p>"
						+ "<p class='market-price'>市场价: " + data[i].domestic_price + "</p>"
						+ "</li>";
				}
				
				$($("ul[class='commodity-list']")[0]).html(html);
			}
			else {
				$(".commodity").hide();
			}
		}
	});
}