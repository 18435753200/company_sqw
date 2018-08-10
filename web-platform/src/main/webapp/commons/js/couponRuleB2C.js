var CONTEXTPATH = $("#conPath").val();
$( document ).ready(function(){
	$("#ruleCondition").change(function(){
    	var oType = $("#ruleCondition").val();
    	var skuName = $("#skuName").val();
    	
    	//获取品牌、产品数据

    	if(0 == oType){
    		oType = "all";
    	}else if(1 == oType){
    		oType = "sort";
    	}else if(2 == oType){
    		oType = "brands";
    	}else if(4 == oType){
    		oType = "skus";
    	}
    	//获取品牌、类目、产品数据

    	
		$.ajax({
            type : 'post',
            url : "../productrule/findByConditions?Rand2=" + (new Date()).getTime(),
            data : "flag=0&oType="+oType,
            dataType : "json",
            success : function(data) {
            	
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

    $("#findbyskuId").on("click",findBySkuId);
    
    $("#addbrand").on("click",addbrand);
    $("#addCategory").on("click",addsort);
    $("#addProduct").on("click",addgoods);
    $("#selectoption .selected li").live("click",del);
    $("#selected-goods .selected-list ul li").live("click",delSku);
    $("#createRuleB2C").on("click",saveCouponRuleB2C);
    $("#findbypid").on("click",findSkuByName);
    //page
    $("#pPage").on("click",findByPreviousPageB2C);
    $("#nPage").on("click",findByNextPageB2C);
    $("#fPage").on("click",findByFirstPageB2C);
});
//零元购活动目前仅限于wap
function changeActivity(){
	console.log(123);
	var accessMode = $("#platform").val();
	if(accessMode == 1){
		var value = $("#conditions input").last().val();
		console.log(value);
	}else {
		var value = $("#conditions input").last().val();
		console.log(value);
	}
}

function findSkuByName(){
	var skuName = $("#skuName").val();
	$.ajax({
        type : 'post',
        url : "../productrule/findSkusByCondition?Rand3=" + (new Date()).getTime(),
        data : "oType=skus&flag=1&skuName="+skuName,
        dataType : "json",
        success : function(data) {
			//清空商品数据
			$(".act-goods-list .act-list").html("");
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
			$("#total").text(data[0].totalPage);
        }
    });
}

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
            var $li = $("<li id='"+ id +"' title='"+title+"'>"+title+"<input type='hidden' name='productIds' value='"+id+"'></li>");
            $("#selected-goods .selected-list ul").append($li);
        }
    });
    $("#skuNum").text("已选："+skuNum+"条");
    $(".act-goods-list li.active").toggleClass("active");
    $("#allCheck").attr("checked",false);
    $("#noCheck").attr("checked",false);
}


function saveCouponRuleB2C(){
	//校验数据
	var flag = couponRuleB2CCheckSubmit();
	if(flag == false){
		tipMessage('部分信息不完整或不符合规范，请修改！', function() {
        });
	}
	
    if (flag == true) {
        var couponid=$("#couponid").val();
        var totalAcount = $("#totalAcount").val();
        
        $.ajax({
            type : 'post',
            url : '../couponRule/createRuleB2C',
            data : $('#couponRuleAction').serialize(),
            success : function(data) {
                if (data == 'success') {
                    tipMessage("创建成功，将立即返回到规则列表！", function() {
                        window.location.href = "../couponRule/toShowRuleList?couponId=" + couponid+"&totalAcount="+totalAcount;
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
    
}

function couponRuleB2CCheckSubmit(){
	var flag = true;
	var matchnum =/^(\+|-)?\d+$/;
	//校验优惠券规则名称
	var couponrulename = $("#couponrulename").val();
	if (couponrulename.length > 100 || couponrulename.length < 1) {
        flag = false;
        $('#couponrulename').next().text('规则名称必填，且不能超过100字！').show();
    } else {
        $('#couponrulename').next().hide();
    }
	
	var qty = $("#qty").val();
	if(qty == null || qty == 0 || qty == undefined){
		$("#qty").next().text("必填项，如果填写数量，必须填写正整数").show();
		flag = false;
	}else if(!matchnum.test(qty) || qty.length > 18){
		$("#qty").next().text("只能是正整数,并且不能大于18位！").show();
		flag = false;
	}else {
		$("#qty").next().hide();
	}
	//校验限制条件
	var ruleCondition = $("#ruleCondition").val();
	//当选择品牌了之后，必须至少添加一个品牌，才能保存规则
	if(ruleCondition == 2){
		var length = $("#selected-brands ul").children("li").length;
		if(length == 0){
			flag = false;
			$("#ruleCondition").next().text("所选品牌个数不能为空").show();
		}else{
			$("#ruleCondition").next().hide();
		}
	}else if(ruleCondition == 1){
		var length = $("#selected-sort ul").children("li").length;
		if(length == 0){
			flag = false;
			$("#ruleCondition").next().text("所选品类个数不能为空").show();
		}else{
			$("#ruleCondition").next().hide();
		}
	}else if(ruleCondition == 3){
		var length = $("#selected-goods ul").children("li").length;
		if(length == 0){
			flag = false;
			$("#ruleCondition").next().text("所选商品个数不能为空").show();
		}else{
			$("#ruleCondition").next().hide();
		}
	}
	var couponType = $("#couponType").val();
	if(couponType != 2){
	    var matchnum1 = /^[0-9]+(\.[0-9]+)?$/;
	    var meetpiece = $.trim($("#meetpiece").val());
	    var couponacount = $.trim($("#couponacount").val());
	    if (!matchnum1.test(meetpiece) || meetpiece.length > 18) {
	        $("#meetpiece").next().next().text('金额只能是正数,并且不能大于18位！').show();
	        flag = false;
	    } /*else {
	        if (parseInt(meetpiece) < parseInt(couponacount)) {
	            $("#meetpiece").next().next().text('消费满足金额应不能小于优惠券金额加5！').show();
	            $("#meetpiece").val();
	            flag = false;
	        } else {
	            $("#meetpiece").next().next().hide();
	        }
	    }*/
	}
	
	return flag;

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
//上一页
function findByPreviousPageB2C(){
	var page = parseInt($("#page").text())-1;
	if(page < 1){
		return false;
	}else{
		var skuName = $("#skuName").val();
		var productIdStr = $("#productIdStr").val();
		var skuIdStr = $("#skuIdStr").val();
		$.ajax({
            type : 'post',
            url : "../productrule/findByConditionB2C",
            data : "flag=0&page="+page+"&skuName="+skuName+"&productIdStr="+productIdStr+"&skuIdStr="+skuIdStr,
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
            		var proName = object.prodName;
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
//下一页
function findByNextPageB2C(){
	var page = parseInt($("#page").text())+1;
	var totalPage = $("#totalPage").val();
	if(page > totalPage){
		return false;
	}else{
		var skuName = $("#skuName").val();
		var productIdStr = $("#productIdStr").val();
		var skuIdStr = $("#skuIdStr").val();
		$.ajax({
            type : 'post',
            url : "../productrule/findByConditionB2C",
            data : "flag=0&page="+page+"&skuName="+skuName+"&productIdStr="+productIdStr+"&skuIdStr="+skuIdStr,
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
            		var proName = object.prodName;
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
//首页
function findByFirstPageB2C(){
	var page = 1;
	var totalPage = $("#totalPage").val();
	var skuName = $("#skuName").val();
	var productIdStr = $("#productIdStr").val();
	var skuIdStr = $("#skuIdStr").val();
	$.ajax({
        type : 'post',
        url : "../productrule/findByConditionB2C",
        data : "flag=0&page="+page+"&skuName="+skuName+"&productIdStr="+productIdStr+"&skuIdStr="+skuIdStr,
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
        		var proName = object.prodName;
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
	var productIdStr = $("#productIdStr").val();
	var skuIdStr = $("#skuIdStr").val();
	var page = 1;
	var totalPage = $("#totalPage").val();
	$.ajax({
        type : 'post',
        url : "../productrule/findByConditionB2C",
        data : "flag=1&page="+page+"&skuName="+skuName+"&productIdStr="+productIdStr+"&skuIdStr="+skuIdStr,
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
	        		var proName = object.prodName;
	        		var proId = object.productId;
	        		var $li = $("<li id="+skuId+"><label>商品名称:"+proName+"</label><br/>" +
	        				"<label>商品ID:"+proId+"</label><br/><p>单品名称："+skuName+"</p><span>单品ID:"+skuId+"</span><br/><span>单品价格："+price+"</span></li>");
	        		$(".act-goods-list ul").append($li);
	        	}
	        	$("#total").text(totalPage);
        	}
        }
	});
}
