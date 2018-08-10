var CONTEXTPATH = $("#conPath").val();
/* 优惠券规则js */
$(document).ready(function() {
    var errorMsg=$("#errorMsg").val();
    if(errorMsg !=""){
        tipMessage(errorMsg+"不允许创建.将立即返回到优惠券列表！",function(){
            window.location.href=CONTEXTPATH+"/coupon/getCouponPage";
        });
    }
    $('#meetpiece').live('change', checkPrice);
    
    $("#createRule").click(function(){

        var flag = checkSubmit();
        if (flag == false) {
            tipMessage('部分信息不完整或不符合规范，请修改！', function() {
            });
        }
        if (flag == true) {
            var couponid=$("#couponid").val();
            var conditionType = $("#mainruletype").val();
            var details = new Array();
            if(conditionType == 1){//全场
            	
            }else if(conditionType == 2){//品类
            	
            }else if(conditionType == 3){//品牌
            	
            }else if(conditionType == 4){//单品
            	
            }
            $.ajax({
                type : 'post',
                url : '../couponRule/createRule',
                data : $('#couponRuleAction').serialize(),
                success : function(data) {
                    if (data == 'success') {
                        tipMessage("创建成功，将立即返回到规则列表！", function() {
                            window.location.href = "../couponRule/toShowRuleList?couponId=" + couponid;
                        });
                    } else if(data == 'error') {
                        tipMessage("创建失败，请检查后重试！", function() {
                        });
                    }else{
                        tipMessage("创建失败,原因："+data, function() {
                        });
                    }
                }
            });
        }
    
    });
    
    $("#mainruletype").change(function(){
    	var oType = $("#mainruletype").val();
    	//var skuName = $("#skuName").val();
    	
    	//获取品牌、产品数据

    	if(1 == oType){
    		oType = "all";
    	}else if(2 == oType){
    		oType = "sort";
    	}else if(3 == oType){
    		oType = "brands";
    	}else if(4 == oType){
    		oType = "skus";
    	}
    	//获取品牌、类目、产品数据

    	
		$.ajax({
            type : 'post',
            url : "../productrule/findb2bByConditions_new?Rand2=" + (new Date()).getTime(),
            data : "flag=0&oType="+oType,
            dataType : "json",
            success : function(data) {
//            	var totalPage = data[0].totalPage;
                if (oType == "brands") {
                	
                	//清空品牌数据
                	$(".act-brands-list .act-list").html("");
                	//添加品牌数据
                	for(var obj in data){
                		var brandName = data[obj].nameCn;
                		var brandId = data[obj].id;
                		var $li = $("<li id="+brandId+">"+brandName+"</li><span value="+2+"></span>");
                		$(".act-brands-list ul").append($li);
                	}
                }else if(oType == "skus"){
                	$(".act-goods-list .act-list").html("");
                	//添加单品数据
/*                	var data1 = data[0].result;
                	for(var obj in data1){
                		var object = data1[obj];
                		var skuName = object.skuNameCn;
                		var skuId = object.skuCode;
                		var price = object.unitPrice;
                		var proName = object.b2bProdName;
                		var proId = object.productId;
                		var $li = $("<li id="+skuId+"><label>商品名称:"+proName+"</label><br/>" +
                				"<label>商品ID:"+proId+"</label><br/><p>单品名称："+skuName+"</p><span>单品ID:"+skuId+"</span><br/><span>单品价格："+price+"</span></li>");
                		$(".act-goods-list ul").append($li);
                	}*/
                	$("#selected-goods").show();
                	$("#J-why-goods").show();
                	$("#total").text(0);
                }
            }
        });
    	
    	if(oType == "brands"||oType == "sort"||oType == "goods"){
    		$("#ruleSorts").show();
    	}else{
    		$("#ruleSorts").hide();
    	}
    	$('.J-why').hide();
        $('#J-why-' + oType).show();
        if(oType == "0"){
        	$('#selectoption').hide();
        }else{
        	$('.selected').hide();
        	$('#selectoption').show();
        	$('#selected-' + oType).show();
        }
    });
    
    $(".act-list li").live("click",function(){
   	    $(this).toggleClass("active");
    });
    
    $("#addbrand").on("click",addbrand);
    $("#addCategory").on("click",addsort);
    $("#addProduct").on("click",addgoods);
    $("#selectoption .selected li").live("click",del);
    $("#selected-goods .selected-list ul li").live("click",delSku);
    $("#findbyskuId").on("click",findSkuByName);
    
    //page
    $("#pPage").on("click",findByPreviousPage);
    $("#nPage").on("click",findByNextPage);
    $("#fPage").on("click",findByFirstPage);
    $("#allCheck").click(function(){
    	if($("#allCheck").is(":checked")){
    		$("#noCheck").attr("checked",false)
    		$(".act-list li").each(function(a,item){
    			if(!$(item).hasClass("active")){
    				$(item).toggleClass("active");
    			}
    	    });
	    }else{
	    	$(".act-list li").each(function(a,item){
	    		if($(item).hasClass("active")){
	    			$(item).removeClass("active");
   	    	    }
   	        });
	    }
    });
    $("#noCheck").click(function(){
    	if($("#noCheck").is(":checked")){
    		$("#allCheck").attr("checked",false)
    		$(".act-list li").each(function(a,item){
    			if($(item).hasClass("active")){
    				$(item).removeClass("active");
    			}else{
    				$(item).toggleClass("active");
    			}
    	    });
	    }
    });
});


/**
 * 提交校验项
 */
function checkSubmit() {
    var flag = true;
    // 校验名称
    var couponrulename = $('#couponrulename').val();
    if (couponrulename.length > 100 || couponrulename.length < 1) {
        flag = false;
        $('#couponrulename').next().text('优惠券规则名称必填，且不能超过100字！').show();
    } else {
        $('#couponrulename').next().hide();
    }
    // 校验金额
    var matchnum = /^(\+|-)?\d+$/;
    var meetpiece = $.trim($("#meetpiece").val());
    if (meetpiece == "") {
        $("#meetpiece").next().next().text('消费满足金额不能为空！').show();
        flag = false;
    } else {
        if (!matchnum.test(meetpiece) || meetpiece.length > 18) {
            $("#meetpiece").next().next().text('消费满足金额只能是正整数,并且不能大于18位！')
                    .show();
            $("#meetpiece").val();
            flag = false;
        } else {
            var couponacount = $("#couponacount").val();
            var couponType = $("#couponType").val();
            if(couponType != 2){
	            if (parseInt(meetpiece) < parseInt(couponacount)-5) {
	                $("#meetpiece").next().next().text('消费满足金额应比优惠券金额大5！').show();
	                $("#meetpiece").val();
	                flag = false;
	            } else {
	                $("#meetpiece").next().next().hide();
	            }
            }
        }
    }
    return flag;
}

/**
 * 校验金额
 * 
 * @returns
 */
var checkPrice = function() {
	var couponType = $("#couponType").val();
	console.log(couponType);
	if(couponType != 2){
	    var matchnum =/^(\+|-)?\d+$/;
	    var meetpiece = $.trim($("#meetpiece").val());
	    var couponacount = $.trim($("#couponacount").val());
	    if (!matchnum.test(meetpiece) || meetpiece.length > 18) {
	        $("#meetpiece").next().next().text('金额只能是正整数,并且不能大于18位！').show();
	        $("#meetpiece").val();
	    } else {
	        if (parseInt(meetpiece)-parseInt(couponacount)<=0) {
	            $("#meetpiece").next().next().text('消费满足金额减去券金额不能小于0').show();
	            $("#meetpiece").val();
	        } else {
	            $("#meetpiece").next().next().hide();
	        }
	    }
	}
};


function del(){
	$(this).remove();
}

function delSku(){
	$(this).remove();
	var skuNum=parseInt($("#skuNum").text().replace('已选：', '').replace('条', ''));
	skuNum=skuNum-1;
	$("#skuNum").text("已选："+skuNum+"条");
}

function addbrand(){
	$(".act-brands-list li.active").each(function(){
		var text = $(this).text();
		var id = $(this).attr("id");
        var flag = false;
        $("#selected-brands .selected-list ul li").each(function(){
            if($(this).attr("id") == id){
                flag = true;
            }
        });
        if(!flag){
            var $li = $("<li id="+ id +">"+ text +"<input type='hidden' name='brandIds' value='"+id+"'></li>");
            $("#selected-brands .selected-list ul").append($li);
        }
    });
  	$(".act-brands-list li.active").toggleClass("active");
}

function addsort(){
    var text = $(".act-sort-crumb strong").text(), id=$(".act-sort-crumb span:has(strong)").attr("id");
    var flag = false;
    if(id == null || 'undefined' == id){
    	flag = true;
    }
    
    $("#selected-sort .selected-list ul li").each(function(){
        if($(this).attr("id") == id){
        	
        	/*$("#selectCategory").append("<span>"+cate+"</span>");*/
        	 flag = true;
        	 tipMessage("分类["+text+"]已存在", function() {
             });
        }else if(newmachesexist(id,$(this).attr("id")) ){
        	
        	flag = true;
        	tipMessage("分类["+text+"]的父级已存在", function() {
            });
        }else if(existmachesnew($(this).attr("id"),id)){
        	flag = true;
        	tipMessage("分类["+text+"]的子级已存在", function() {
            });
        }
        
    });
    if(!flag){
    	var $li = $("<li id='"+ id +"'>"+ text +"<input type='hidden' name='catePubIds' value='"+id+"'></li>");
        $("#selected-sort .selected-list ul").append($li);	
    }
    
    
    $(".act-sort-list li.active").toggleClass("active");
}
function addgoods(){
    //$("#selected-goods .selected-list ul li").remove();
	var skuNum=parseInt($("#skuNum").text().replace('已选：', '').replace('条', ''));
    $(".act-goods-list li.active").each(function(){
        var text = $(this).find("p").text().replace('单品名称：', '');
        var productName = $(this).find("label:first-child").text().replace('商品名称:', '');
        var id = $(this).attr("id");
        var flag = false;
        $("#selected-goods .selected-list ul li").each(function(){
            if($(this).attr("id") == id){
                flag = true;
            }
        });
        title=productName+":"+text;
        if(!flag){
        	skuNum=skuNum+1;
            var $li = $("<li id='"+ id +"' title='"+title+"'>"+ title +"<input type='hidden' name='productIds' value='"+id+"'></li>");
            $("#selected-goods .selected-list ul").append($li);
        }
    });
    $("#skuNum").text("已选："+skuNum+"条");
    $(".act-goods-list li.active").toggleClass("active");
    $("#allCheck").attr("checked",false);
    $("#noCheck").attr("checked",false);
}

function findSkuByName(){
	var skuName = $("#skuName").val();
	var productIdStr = $("#productIdStr").val();
	var skuIdStr = $("#skuIdStr").val();
	$.ajax({
        type : 'post',
        url : "../productrule/findSkusByCondition?Rand3=" + (new Date()).getTime(),
        data : "oType=skus&page=1&flag=1&skuName="+skuName+"&productIdStr="+productIdStr+"&skuIdStr="+skuIdStr,
        dataType : "json",
        success : function(json) {
        	//清空商品数据
			$(".act-goods-list .act-list").html("");
        	if(json==0){
                alert("商品ID只能是数字,用','号分割!");
                $("#productIdStr").val("");
                $("#productIdStr").focus();
                $("#total").text(0);
            }else if(json==1){
            	alert("商品skuId只能是数字,用','号分割!");
                $("#skuIdStr").val("");
                $("#skuIdStr").focus();
                $("#total").text(0);
            }else{
            	var page = json[0].page;
            	$("#page").text(page);
            	var data = json[0].result;
    			//添加商品数据
    			for(var obj in data){
    				var object = data[obj];
            		var skuName = object.skuNameCn;
            		var skuId = object.skuCode;
            		var price = object.unitPrice;
            		var proName = object.prodName;
            		var proId = object.productId;
            		var $li = $("<li id="+skuId+"><label>商品名称:"+proName+"</label><br/>" +
            				"<label>商品ID:"+proId+"</label><br/><p>单品名称："+skuName+"</p><span>单品ID:"+skuId+"</span><br/><span>单品价格："+price+"</span></li>");
            		$(".act-goods-list ul").append($li);
    			}
    			var totalPage = json[0].totalPage;
    			$("#total").text(totalPage);
            }
        }
    });
}

/*正则匹配 maches 如果匹配上则返回true,否则返回false 假设：选择的为子级，已选择的为父级*/
function newmachesexist(newId,existedId){
	var flag = false;
	var len = existedId.length;
	if(newId.length >= len){
		var tempNewId = newId.substring(0,len);
		if(tempNewId == existedId){
			flag = true;
		}
	}
	return flag;
}

/*正则匹配，如果子级分类已存在则不许选择父级分类  设定：选择的为父级，已选择的为子级*/
function existmachesnew(existedId,newId){
	var flag = false;
	var len = newId.length;//新增的id 因为为父级分类，所以长度短
	if(len<existedId.length){
		var tempPid = existedId.substring(0,len);
		if(tempPid == newId){
			flag = true;
		}
	}
	return flag;
}

function findByPreviousPage(){
	var page = parseInt($("#page").text())-1;
	if(page < 1){
		return false;
	}else{
		var skuName = $("#skuName").val();
		var productIdStr = $("#productIdStr").val();
		var skuIdStr = $("#skuIdStr").val();
		$.ajax({
            type : 'post',
            url : "../productrule/findSkusByCondition?Rand4=" + (new Date()).getTime(),
            data : "flag=0&page="+page+"&oType=skus"+"&skuName="+skuName+"&productIdStr="+productIdStr+"&skuIdStr="+skuIdStr,
            dataType : "json",
            success : function(json) {
            	//清空商品数据
            	$(".act-goods-list .act-list").html("");
            	var totalPage = json[0].totalPage;
            	$("#totalPage").attr("value",totalPage);
            	$("#page").text(page);
            	
            	var data = json[0].result;
            	//添加商品数据
            	for(var obj in data){
            		var object = data[obj];
            		var skuName = object.skuNameCn;
            		var skuId = object.skuCode;
            		var price = object.unitPrice;
            		var proName = object.b2bProdName;
            		var proId = object.productId;
            		var $li = $("<li id="+skuId+"><label>商品名称:"+proName+"</label><br/>" +
            				"<label>商品ID:"+proId+"</label><br/><p>单品名称："+skuName+"</p><span>单品ID:"+skuId+"</span><br/><span>单品价格："+price+"</span></li>");
            		$(".act-goods-list ul").append($li);
            	}
            	$("#total").text(data[0].totalPage);
            }
		});
	}
}

function findByNextPage(){
	var page = parseInt($("#page").text())+1;
	console.log(page);
	var totalPage = $("#totalPage").val();
	if(page > totalPage){
		return false;
	}else{
		var skuName = $("#skuName").val();
		var productIdStr = $("#productIdStr").val();
		var skuIdStr = $("#skuIdStr").val();
		$.ajax({
            type : 'post',
            url : "../productrule/findSkusByCondition?Rand6=" + (new Date()).getTime(),
            data : "flag=0&page="+page+"&oType=skus"+"&skuName="+skuName+"&productIdStr="+productIdStr+"&skuIdStr="+skuIdStr,
            dataType : "json",
            success : function(json) {
            	//清空商品数据
            	$(".act-goods-list .act-list").html("");
            	var totalPage = json[0].totalPage;
            	$("#totalPage").attr("value",totalPage);
            	$("#page").text(page);
            	var data = json[0].result;
            	//添加商品数据
            	for(var obj in data){
            		var object = data[obj];
            		var skuName = object.skuNameCn;
            		var skuId = object.skuCode;
            		var price = object.unitPrice;
            		var proName = object.b2bProdName;
            		var proId = object.productId;
            		var $li = $("<li id="+skuId+"><label>商品名称:"+proName+"</label><br/>" +
            				"<label>商品ID:"+proId+"</label><br/><p>单品名称："+skuName+"</p><span>单品ID:"+skuId+"</span><br/><span>单品价格："+price+"</span></li>");
            		$(".act-goods-list ul").append($li);
            	}
            	$("#total").text(totalPage);
            }
		});
	}
}

function findByFirstPage(){
	var page = 1;
	var totalPage = $("#totalPage").val();
	var skuName = $("#skuName").val();
	var productIdStr = $("#productIdStr").val();
	var skuIdStr = $("#skuIdStr").val();
	$.ajax({
        type : 'post',
        url : "../productrule/findSkusByCondition?Rand8=" + (new Date()).getTime(),
        data : "flag=0&page="+page+"&oType=skus"+"&skuName="+skuName+"&productIdStr="+productIdStr+"&skuIdStr="+skuIdStr,
        dataType : "json",
        success : function(json) {
        	//清空商品数据
        	$(".act-goods-list .act-list").html("");
        	var totalPage = json[0].totalPage;
        	$("#totalPage").attr("value",totalPage);
        	$("#page").text(page);
        	
        	var data = json[0].result;
        	//添加商品数据
        	for(var obj in data){
        		var object = data[obj];
        		var skuName = object.skuNameCn;
        		var skuId = object.skuCode;
        		var price = object.unitPrice;
        		var proName = object.b2bProdName;
        		var proId = object.productId;
        		var $li = $("<li id="+skuId+"><label>商品名称:"+proName+"</label><br/>" +
        				"<label>商品ID:"+proId+"</label><br/><p>单品名称："+skuName+"</p><span>单品ID:"+skuId+"</span><br/><span>单品价格："+price+"</span></li>");
        		$(".act-goods-list ul").append($li);
        	}
        	$("#total").text(totalPage);
        }
	});
}

function findBySkuId(){
	var skuName = $("#skuName").val();
	var page = 1;
	var totalPage = $("#totalPage").val();
	$.ajax({
        type : 'post',
        url : "../productrule/findSkusByCondition?Rand10=" + (new Date()).getTime(),
        data : "flag=1&page="+page+"&skuName="+skuName+"&oType=skus",
        dataType : "json",
        success : function(json) {
        	//清空商品数据
        	$(".act-goods-list .act-list").html("");
        	var totalPage = json[0].totalPage;
        	$("#totalPage").attr("value",totalPage);
        	var page = json[0].page;
        	$("#page").text(page);
        	
        	var data = json[0].result;
        	//添加商品数据
        	for(var obj in data){
        		var object = data[obj];
        		var skuName = object.skuNameCn;
        		var skuId = object.skuCode;
        		var price = object.unitPrice;
        		var proName = object.b2bProdName;
        		var proId = object.productId;
        		var $li = $("<li id="+skuId+"><label>商品名称:"+proName+"</label><br/>" +
        				"<label>商品ID:"+proId+"</label><br/><p>单品名称："+skuName+"</p><span>单品ID:"+skuId+"</span><br/><span>单品价格："+price+"</span></li>");
        		$(".act-goods-list ul").append($li);
        	}
        	$("#total").text(totalPage);
        }
	});
}