var CONTEXTPATH = $("#conPath").val();
$( document ).ready(function(){
	var show_count = 20;   //要显示的条数
	var count = 0;    //递增的开始值，这里是你的ID
    $("#actType").change(function () {
    var oType = $("#actType").val();
    $('.J-type').hide();
    $('#J-type-' + oType).show();
    
});
    
    $("#findbypid").on("click",findProByName);
    
    $("#createBy").change(function(){
    	var createBy = $("#createBy").val();
    	if(createBy == 0){
    		$("#createBy").next().text("请选择创建部门").show();
    	}else{
    		$("#createBy").next().hide();
    	}
    });
    
    $("#term2").change(function(){
    	var term2 = $("#term2").val();
    	if(term2 == 0){
    		$("#term2").next().text("请选择创建部门").show();
    	}else{
    		$("#term2").next().hide();
    	}
    });
    $("#ruleTerm").change(function(){
    	var ruleTerm = $("#ruleTerm").val();
    	if(ruleTerm == 0){
    		$("#ruleTerm").next().text("请选择活动类型").show();
    	}
    	
    	else{
    		$("#ruleTerm").next().hide();
    	}
    });
    
    $("#channels").change(function(){
    	var channels = $("#channels").val();
    	if(channels == 0){
    		$("#channels").next().show();
    	}else{
    		$("#channels").next().hide();
    	}
    });
    
    $("#ruleCondition").change(function(){
    	var ruleCondition = $("#ruleCondition").val();
    	if(ruleCondition == 100){
    		$("#ruleCondition").next().text("请选择活动类型规范限制条件").show();
    	}else{
    		$("#ruleCondition").next().hide();
    	}
    });
    $("#giftType").change(function(){
    	var giftType = $("#giftType").val();
    	
    	if(giftType == 0){
    		$("#giftType").next().text("请选择后置条件").show();
    		$("#cashAmout").hide();
    		$("#coupon").hide();
			$("#isrepeat1").hide();
    	}else {
    		$("#giftType").next().hide();
    		if(giftType == 2){
    			$("#cashAmout").show();
    		}else if(giftType == 1 || giftType == 3){
    			if(giftType == 3){
    				giftType = 2;
    			}
    			$.ajax({//获取可选的金券
    	            type : 'post',
    	            url : "../productrule/findAbleCoupon",
    	            data : "giftType="+giftType,
    	            dataType : "json",
    	            success : function(data) {
    	            	$("#term2").html("");
    	            	var $li = ("<option selected='' value="+0+">请选择</option>");
    	            	$("#term2").append($li);
    	            	for(var obj in data){
    	            		var couponId = data[obj].mainrulename;
    	            		var couponName = data[obj].couponrulename;
	    	            	var $li = $("<option value="+couponId+">"+couponName+"</option>");
	                		$("#term2").append($li);
    	            	}
    	            }
    			});
    			$("#coupon").show();
    			$("#isrepeat1").show();
    		}
    	}
    });
    
    $("#couponType").change(function(){
    	var couponType = $("#couponType").val();
    	if(couponType == 0){
    		$("#couponType").next().show();
    		$("#coupon").hide();
			$("#isrepeate1").hide();
    	}else if(couponType == 1){
    		$("#couponType").next().hide();
    		$.ajax({//获取可选的金券
	            type : 'post',
	            url : "../productrule/findAbleB2CCoupon",
	            data : "couponType="+couponType,
	            dataType : "json",
	            success : function(data) {
	            	$("#coupons").html("");
	            	var $li = ("<option selected='' value="+0+">请选择</option>");
	            	$("#coupons").append($li);
	            	for(var obj in data){
	            		var couponId = data[obj].mainrulename;
	            		var couponName = data[obj].couponrulename;
    	            	var $li = $("<option value="+couponId+">"+couponName+"</option>");
                		$("#coupons").append($li);
	            	}
	            	$("#coupon").show();
	    			$("#isrepeate1").show();
	            }
			});
    	}
    });
    
    $(".act-sel12").live("change",function(){ 
    	var giftType1 = $(this).val();
    	if(giftType1==3){
    		$("#skuListContident").empty();
    		var content = '<ul>'
		                    +'<li><p>商品名称:蛋糕</p><span>单品名称:芒果蛋糕</span></li>'
		                    +'<li><p>商品名称:蛋糕</p><span>单品名称:苹果蛋糕</span></li>'
		                    +'<li><p>商品名称:蛋糕</p><span>单品名称:柠檬蛋糕</span></li>'
		                    +'<li><p>商品名称:蛋糕</p><span>单品名称:香蕉蛋糕</span></li>'
		                 '</ul>';
	        $("#skuListContident").append(content);
			$("#goout-box-condition").css("display","block");
    		
    	};
    	
    	
    	
    });
    
 
    
    $(".close-box").click(function(){  
    	var ss=$("#goout-box-content");
    	$(".lightbox").fadeOut();
    });

    
    
   //按数量和金额
    var Tradd = $("#btn_addtr");
	var Trdel = $("#btn-deltr");
	//点击增加按钮
	Tradd.click(function(){
		  var Trfenlei= $("#isAmout option:selected").val(); 
		   if(Trfenlei==1){
			    numb(); 
				return;
			}
			if(Trfenlei==2){
				price();
				return;
			}
	  });
  
	//删除
    Trdel.click(function(){
		var btr = $("#box1 .J-type");
		if (btr.length == 1) {
		   alert("至少保留一行");
		   return;
		}else{
		   btr.last().remove();	
		   $("#box1 .J-type:last-child").find("input.start_price").attr("readonly",true).css({"background":"#eee"});
		   $("#box1 .J-type:last-child").find("input.end_price").attr("readonly",false).css({"background":""});
		}
	});


 
    
	//数量添加
	function numb(){
   		var btr = $("#box1 .J-type");
		btr.each(function(index){
			var inputS=$(this).find("input.start_price").val();
			var inputE=$(this).find("input.end_price").val(); 
			var zpnuB=$(this).find("input.zpnub").val(); 
			var zpsd= $(this).find(".zp-box option:selected").val(); 
		
			if(index==btr.length-1){
				  if(inputS==""||inputE==""||zpnuB==""||zpsd==0){
						alert("内容不能为空");
						return;
					}else if(Number(inputE) <= Number(inputS)){
						alert("第二条件必须要大于第一条件");
						return;
					}else if(Number(inputS) >= 999999){
						alert("第一条件最大不得超过最大值999999");
						return;
					}else if(Number(inputE) >= 999998){
						alert("第二条件最大不得超过最大值999998");
						return;
					}else if(!(/(^[1-9]\d*$)/.test(inputS)) || !(/(^[1-9]\d*$)/.test(inputE))|| !(/(^[1-9]\d*$)/.test(zpnuB))){
						alert("第一,二,四条件只能填正整数");
						//return;
					}else{
					   $("#box1 .J-type:first").clone().appendTo("#box1");
					   $(this).find("input.start_price").attr("readonly",true).css({"background":"#eee"});
					   $(this).find("input.start_price").next().hide();
					   $(this).find("input.end_price").attr("readonly",true).css({"background":"#eee"});
					   $(this).find("input.end_price").next().hide();
					   $(this).next().find("input.start_price").attr("readonly",true).css({"background":"#eee"});
					   $(this).next().find("input.end_price").attr("readonly",false).css({"background":""});
					   $(this).next().find("input.end_price").next().text("提示:第二条件不填，为默认最大值999999").show();
					   $(this).next().find("input.end_price").val("");
					   $(this).next().find("input.zpnub").val("");
					   var mub= $(this).find("input.end_price").val(); 
					   var total=parseFloat(mub)+parseFloat(1);
					   $(this).next().find("input.start_price").val(total);
					
					    
					}
			}
		});
				
			
	};
	  
	  	//价格添加
		function price(){
				var btr = $("#box1 .J-type");
				btr.each(function(index){
					var inputS=$(this).find("input.start_price").val();
					var inputE=$(this).find("input.end_price").val();   
					var zpnuB=$(this).find("input.zpnub").val(); 
					var zpsd= $(this).find(".zp-box option:selected").val(); 
					if(index==btr.length-1){
						  if(inputS==""||inputE==""||zpnuB==""||zpsd==0){
								alert("内容不能为空");
								return;
							}else if(Number(inputE) <= Number(inputS)){
								alert("第二条件必须要大于第一条件");
								return;
							}else if(isNaN(inputE) || isNaN(inputS)){
								alert("第一,二条件只能填数字");
								return;
							}else if(!(/(^[1-9]\d*$)/.test(zpnuB))){
								alert("第四条件只能填正整数");
								return;
							}else{
								$("#box1 .J-type:first").clone().appendTo("#box1");
								   $(this).find("input.start_price").attr("readonly",true).css({"background":"#eee"});
								   $(this).find("input.start_price").next().hide();
								   $(this).find("input.end_price").attr("readonly",true).css({"background":"#eee"});
								   $(this).find("input.end_price").next().hide();
								   $(this).next().find("input.start_price").attr("readonly",true).css({"background":"#eee"});
								   $(this).next().find("input.end_price").attr("readonly",false).css({"background":""});
								   $(this).next().find("input.end_price").next().text("提示:第二条件不填，为默认最大值999999").show();
								   $(this).next().find("input.end_price").val("");
								   $(this).next().find("input.zpnub").val("");
								   var mub= $(this).find("input.end_price").val(); 
							   var total=parseFloat(mub)+parseFloat(0.01);
							   $(this).next().find("input.start_price").val(total);
							 
							}
					}
				});
			
		};
		
    
		$("#isAmout").change( function() {
		   	   var Trfenlei= $("#isAmout option:selected").val(); 
			   if(Trfenlei==1){
					$("#box1 .J-type:gt(0)").remove();
					$("#box1 .J-type:eq(0) input").val("");
					$("#box1 .J-type:eq(0) input").attr("readonly",false).css({"background":""});
				}
				
				if(Trfenlei==2){
					$("#box1 .J-type:gt(0)").remove();
					$("#box1 .J-type:eq(0) input").val("");
					$("#box1 .J-type:eq(0) input").attr("readonly",false).css({"background":""});
				}
		});
      


    
    $("#ruleCondition").change(function(){
    	var oType = $("#ruleCondition").val();
    	var productName = $("#productName").val();
    	var skuName = $("#skuName").val();

    	//获取品牌、产品数据

    	if(0 == oType){
    		oType = "all";
    	}else if(1 == oType){
    		oType = "sort";
    	}else if(2 == oType){
    		oType = "brands";
    	}else if(3 == oType){
    		oType = "goods";
    	}else if(4 == oType){
    		oType = "skus";
    	}
    	//获取品牌、类目、产品数据

    	
		$.ajax({
            type : 'post',
            url : "../productrule/findByConditions?Rand1=" + (new Date()).getTime(),
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
                }else if(oType == "goods"){
                	//清空商品数据
                	$(".act-goods-list .act-list").html("");
                	//添加商品数据
                	for(var obj in data){
                		var object = data[obj];
                		var productName = object.productName;
                		var productId = object.productId;
                		var $li = $("<li id="+productId+"><p>"+productName+"</p><span>"+productId+"</span></li>");
                		$(".act-goods-list ul").append($li);
                	}
                }else if(oType == "skus"){
                	//清空商品数据
                	$(".act-goods-list .act-list").html("");
                	//添加商品数据
                	var totalPage = data[0].totalPage;
                	var data1 = data[0].result;
                	for(var obj in data1){
                		var object = data1[obj];
                		var skuName = object.skuNameCn;
                		var skuId = object.skuCode;
                		var price = object.unitPrice;
                		var proName = object.prodName;
                		var proId = object.productId;
                		var $li = $("<li id="+skuId+"><span>商品名称:"+proName+"</span><br/>" +
                				"<span>商品ID:"+proId+"</span><br/><p>单品名称："+skuName+"</p><span>单品ID:"+skuId+"</span><br/><span>单品价格：</span><label>"+price+"</label></li>");
                		$(".act-goods-list ul").append($li);
                	}
                	$("#total").text(totalPage);
                }
            }
        });
    	
    	if(oType == "brands"||oType == "sort"||oType == "goods"||oType == "skus"){
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
    
    $("#ruleTerm").change(function(){
    	var ruleTerm = $("#ruleTerm").val();
    	if(ruleTerm == 1){
    		$("#labelText").text("前置条件：(输入满赠金额，正整数)");
    		$("#J-type-fullcut").show();
    		$("#J-type-fullcut0").hide();
    		$("#mib-box").hide();
    	}else if(ruleTerm == 10){
    		$("#labelText").text("前置条件：(输入每单限购数量，正整数)");
    		$("#J-type-fullback").hide();
    		$("#J-type-fullback1").hide();
    		$("#J-type-fullcut").show();
    		$("#J-type-fullcut0").hide();
    		$("#mib-box").hide();
    	}else if(ruleTerm == 2){
    		$("#J-type-fullcut").hide();
    		$("#J-type-fullcut0").show();
    		$("#mib-box").hide();
    	}else if(ruleTerm == 13){
    		$("#J-type-fullback").hide();
    		$("#J-type-fullcut0").show();
    		$("#J-type-fullback1").hide();
    		$("#J-type-fullcut").hide();
    		$("#mib-box").hide();
    	}else if(ruleTerm == 14){
    		$("#J-type-fullback").show();
    		$("#J-type-fullcut0").hide();
    		$("#J-type-fullback1").hide();
    		$("#J-type-fullcut").hide();
    		$("#mib-box").hide();
    	}else if(ruleTerm == 15){
    		$("#J-type-fullback").hide();
    		$("#J-type-fullcut0").hide();
    		$("#J-type-fullback1").show();
    		$("#J-type-fullcut").hide();
    		$("#mib-box").hide();
    	}
    	else{
    		$("#term1").val("");
    		$("#J-type-fullcut").hide();
    		$("#J-type-fullcut0").hide();
    		$("#mib-box").hide();
    	}
    });
    $("#isgift").change(function(){
    	var isgift =$("#isgift").val();
    	if(isgift == 3){
    		$("#J-type-fullcut1").show();
    		
    $("#couponType").change(function(){
    	var couponType = $("#couponType").val();
    	if(couponType != 0){
    		$("#couponType").next().hide();
    		$("#coupon").show();
    	}else{
    		$("#couponType").next().show();
    	}
    });		
    		
    		
    		$("#isgift").attr("disabled",true); 
    		// $("#isgift option[value=3]").attr("selected",true); 
    		   var param_array = new Array();
				param_array.push("gifttype=3");
				//ajax获取赠品
				$.ajax({
					type:'post',
					url:"../productrule/getGift",
					dataType:"json",
					data:param_array.join("&"),
					success:function(data){
						$("#giftType1").html("");
						 var $li = ("<option  value=''>请选择</option>");
						 $("#giftType1").append($li);
						 for(var obj in data){
							 var giftId = data[obj].giftId;
							 var giftName = data[obj].giftName;
							 var $li = $("<option value="+giftId+">"+giftName+"</option>");
							 $("#giftType1").append($li);
						 }
					},
					error:function(){
						alert("系统错误!");
					}
					
				});

    		
    		
    		
    	}
    	
    });
    
    $("#isAmout").change(function(){
    	var isAmout = $("#isAmout").val();
    	if(isAmout == 1){
    		$("#mib-box").show();
    	}else if(isAmout == 2){
    		$("#mib-box").show();
    	}
    	
    });
    
    
    
    
    
    
    $(".act-list li").live("click",function(){
    	 $(this).toggleClass("active");
    });

    $("#findbyskuId").on("click",findBySkuId);
    
    $("#addbrand").on("click",addbrand);
    $("#addCategory").on("click",addsort);
    $("#addProduct").on("click",addgoods);
    $("#addsku").on("click",addskus);
    $("#selectoption .selected li").live("click",del);
    
    $("#saveProductRule").on("click",saveProductRule);
    $("#savePricedProductRule").on("click",savePricedProductRule);
    //page
    $("#previousPage").on("click",findByPreviousPage);
    $("#nextPage").on("click",findByNextPage);
    $("#firstPage").on("click",findByFirstPage);
    
    $("#pPage").on("click",findByPreviousPageB2C);
    $("#nPage").on("click",findByNextPageB2C);
    $("#fPage").on("click",findByFirstPageB2C);
});

function findProByName(){
	var proName = $("#productName").val();
	$.ajax({
        type : 'post',
        url : "../productrule/findByConditions",
        data : "oType=goods&flag=1&productName="+proName+"&Rand=" + (new Date()).getTime(),
        dataType : "json",
        success : function(data) {
			//清空商品数据
			$(".act-goods-list .act-list").html("");
			//添加商品数据
			for(var obj in data){
				var object = data[obj];
				var productName = object.productName;
				var productId = object.productId;
				var $li = $("<li id="+productId+"><p>"+productName+"</p><span>"+productId+"</span></li>");
				$(".act-goods-list ul").append($li);
			}
        }
    });
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
			var data1 = data[0].result;
        	var totalPage = data[0].totalPage;
			for(var obj in data){
        		var object = data[obj];
        		var skuName = object.skuNameCn;
        		var skuId = object.skuCode;
        		var price = object.unitPrice;
        		var proName = object.prodName;
        		var proId = object.productId;
        		var $li = $("<li id="+skuId+"><span>商品名称:"+proName+"</span><br/>" +
        				"<span>商品ID:"+proId+"</span><br/><p>单品名称："+skuName+"</p><span>单品ID:"+skuId+"</span><br/><span>单品价格：</span><label>"+price+"</label></li>");
        		$(".act-goods-list ul").append($li);
        	}
			$("#total").text(totalPage);
        }
    });
}

function del(){
	$(this).remove();
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
    $(".act-goods-list li.active").each(function(){
        var text = $(this).find("p").text();
        var id = $(this).attr("id");
        var flag = false;
        $("#selected-goods .selected-list ul li").each(function(){
            if($(this).attr("id") == id){
                flag = true;
            }
        });
        if(!flag){
            var $li = $("<li id='"+ id +"'>"+ text +"<input type='hidden' name='productIds' value='"+id+"'></li>");
            $("#selected-goods .selected-list ul").append($li);
        }
    });

    $(".act-goods-list li.active").toggleClass("active");
}

function addskus(){
	$(".act-goods-list li.active").each(function(){
        var text = $(this).find("p").text();
        var id = $(this).attr("id");
        var price = $(this).find("label").text();
        console.log(price);
        var flag = false;
        $("#selected-skus .selected-list ul li").each(function(){
            if($(this).attr("id") == id){
                flag = true;
            }
        });
        if(!flag){
            var $li = $("<li id='"+ id +"'>"+ text +"<input type='hidden' name='skuIds' value='"+id+"'><label style='display : none;'>"+price+"</label></li>");
            $("#selected-skus .selected-list ul").append($li);
        }
    });

    $(".act-goods-list li.active").toggleClass("active");
}

function saveProductRule(){
	//将disable取消
	$("#isgift").attr("disabled",false);
	
	//校验数据
	var flag = checkSubmit();
	if(flag == false){
		tipMessage('部分信息不完整或不符合规范，请修改！', function() {
        });
	}
	
	var giftT = $("#box1 .J-type #giftType1");
	    giftT.each(function(index){
	  	    var giftname = $(this).find("option:selected").text();
	    	$(this).next().next().val(giftname);
    });
	    	

	var activeId = $("#activeId").val();

	if (flag == true) {
        $.ajax({
            type : 'post',
            url : "../productrule/save",
            data : $('#productRuleAction').serialize(),
            success : function(data) {
                if (data == 'success') {
                    tipMessage("成功，将立即返回到活动列表！", function() {
                    	console.log("/productrule/list?activeId="+activeId);
                        window.location.href = CONTEXTPATH+"/productrule/list?activeId="+activeId;
                    });
                } else if(data == 'error') {
                    tipMessage("失败，请检查后重试！", function() {
                    });
                }else{
                    tipMessage("失败,原因："+data, function() {
                    });
                }
            }
        });
    }
}

function savePricedProductRule(){
	var flag = checkSubmitB2C();
	if(flag == false){
		tipMessage('部分信息不完整或不符合规范，请修改！', function() {
        });
	}
	var activeId = $("#activeId").val();
	if (flag == true) {
        $.ajax({
            type : 'post',
            url : "../productrule/saveB2CRule",
            data : $('#productRuleAction').serialize(),
            success : function(data) {
                if (data == 'success') {
                    tipMessage("成功，将立即返回到活动列表！", function() {
                    	console.log("/productrule/list?activeId="+activeId);
                        window.location.href = CONTEXTPATH+"/productrule/list?activeId="+activeId;
                    });
                } else if(data == 'error') {
                    tipMessage("失败，请检查后重试！", function() {
                    });
                }else{
                    tipMessage("失败,原因："+data, function() {
                    });
                }
            }
        });
    }
}

function checkSubmit() {
	var ruleTerm = $('#ruleTerm').val();
    var flag = true;
    // 校验名称
    var ruleName = $('#ruleName').val();
    if (ruleName.length > 100 || ruleName.length < 1) {
        flag = false;
        $('#ruleName').next().text('活动名称必填，且不能超过100字！').show();
    } else {
        $('#ruleName').next().hide();
    }
    
    var channels = $("#channels").val();
    if(channels == 0){
    	flag = false;
    }
    
    
    // 校验开始时间
    var startTime = $("#startdate").val();
    if (startTime == "") {
        $("#startdate").next().text('开始时间不能为空！').show();
        flag = false;
    } else {
        if (!checkStartTime()) {
            $("#startdate").next().text('开始时间不能小于系统当前时间！').show();
            flag = false;
        } else {
            $("#startdate").next().hide();
        }
    }
    // 校验结束时间
    var endTime = $("#enddate").val();
    if (endTime == "") {
        $("#enddate").next().text('结束时间不能为空！').show();
        flag = false;
    } else {
        if (!checkEndTime()) {
            $("#enddate").next().text('结束时间应该大于开始时间！').show();
            flag = false;
        } else {
            $("#enddate").next().hide();
        }
    }

 
    var matchnum = /^[0-9]*$/;
    
    //校验规则优先级
    var ruleCondition = $("#ruleCondition").val();
    if(ruleCondition == 1||ruleCondition == 2||ruleCondition == 3){
    	var ratio = $.trim($("#ruleSort").val());
        if (ratio != "" && !matchnum.test(ratio)) {
            flag = false;
            $('#ruleSort').next().text('规则优先级只能是正整数').show();
        } else if(ratio.length > 5){
        	flag = false;
        	$('#ruleSort').next().text('规则优先级必须是小于等于4位的正整数').show();
        }else if(ratio > 9998){
        	flag = false;
        	$('#ruleSort').next().text('规则优先级必须是小于等于9998的正整数').show();
        }else if(ratio < 1){
        	flag = false;
        	$('#ruleSort').next().text('规则优先级必须是大于等于1位的正整数').show();
        }else{
            $('#ruleSort').next().hide();
        }
    }
    //校验每天限制参加次数
    var limittimes = $("#limittimes").val();
    if (limittimes != "" && !matchnum.test(limittimes)) {
        flag = false;
        $('#limittimes').next().text('每人每天限制次数只能是正整数').show();
    } else if(limittimes.length > 5){
    	flag = false;
    	$('#limittimes').next().text('每人每天限制次数必须是小于等于4位的正整数').show();
    }else if(limittimes > 9998){
    	flag = false;
    	$('#limittimes').next().text('每人每天限制次数必须是小于等于9998的正整数').show();
    }else if(limittimes < 1){
    	flag = false;
    	$('#limittimes').next().text('每人每天限制次数必须是大于等于1位的正整数').show();
    }else{
        $('#limittimes').next().hide();
    }
    
  
    if(ruleTerm==1){
    	   var matchnum1 = /^[0-9]*$/;
    	    //校验满足金额
    	    var term1 = $("#term1").val();
    	    //满足金额必须大于0
    	    if(term1 <= 0 || !matchnum1.test(term1)){
    	    	
    	    	flag = false;
    	    	$("#term1").next().text("满足金额必须大于0,且为正整数").show();
    	    }else{
    	    	$("#term1").next().hide();
    	    }
    	    
    	    //校验是否选择优惠券
    	    var term2 = $("#term2").val();
    	    //满足金额必须大于0
    	    if(term2 == 0){
    	    	flag = false;
    	    	$("#term2").next().text("请选择优惠券").show();
    	    }else{
    	    	$("#term2").next().hide();
    	    }
    	    
    	    //校验赠品
    	    var giftType = $("#giftType").val();
    	    if(giftType == 0){
    	    	//0是请选择
    	    	flag = false;
    	    	$("#giftType").next().text("请选择赠品").show();
    	    }else{
    	    	$("#giftType").next().hide();
    	    }
    	    
    	    
    }
    
    if(ruleTerm == 2){
    	   var isgift = $("#isgift").val();
    	   if(isgift == 0){
    		   flag = false;
   	    	  $("#isgift").next().text("请选择赠品类型").show();
    	   }else{
    			$("#isgift").next().hide();
    	   }
   	   
    	var btr = $("#box1 .J-type");
			btr.each(function(index){
		      	var gv = $(this).find("#giftType1").val(); 
				var tv = $(this).find("#term_33").val(); 
				console.log(tv);
				if(gv == 0){
					$(this).find("#giftType1").next().text("赠品必选").show();
					flag = false;
				}else{
					$(this).find("#giftType1").next().hide();
				}
				if("" == tv || null == tv ||undefined == tv){
					$(this).find("#term_33").next().text("赠品的数量不能为空，且只能填正整数").show();
					flag = false;
				}else{
					$(this).find("#term_33").next().hide();
					if(tv <= 0 || !(/(^[1-9]\d*$)/.test(tv))){
		    	    	$(this).find("#term_33").next().text("赠品的数量只能填正整数").show();
		    	    	flag = false;
		    	    }
				}
		    });
    	   //校验阶梯满增条件
            var valno= $("#box1 .J-type:last-child").find("input.end_price").val();
    		var valno2= $("#box1 .J-type:last-child").find("input.start_price").val();
    		var zpnuB=$("#box1 .J-type:last-child").find("input.zpnub").val(); 
			var zpsd= $("#box1 .J-type:last-child").find(".zp-box option:selected").val(); 
			
    		if (btr.length == 1) {
    		   if(valno2 == ""||zpsd == 0){
    			 flag = false;
    			 return;   
    		   }
    		}
    		
    		if(valno!==""){
    			flag = false;	
    			return;
    		}/*else if(!(/(^[1-9]\d*$)/.test(zpnuB))){
    			alert("第四条件只能填整数");	
    			return;		
    		}*/
    	   
    }
   
    
 
    


  /*  var term_0 = $("#term_0").val();
  
    if(term_0 <= 0 || !matchnum1.test(term_0)){
    	  alert("term_0="+term_0);
    	flag = false;
    	$("#term_0").next().text("满足金额必须大于0,且为正整数").show();
    }else{
    	$("#term_0").next().hide();
    }
  */
 /* var term_1 = $("#term_1").val();
    
    if(term_1 <= 0 || !matchnum1.test(term_1)){
    	flag = false;
    	$("#term_1").next().text("满足金额必须大于0,且为正整数").show();
    }else{
    	$("#term_1").next().hide();
    }
 */
   
  
    //校验是否选择部门
    var createBy = $("#createBy").val();
    //满足金额必须大于0
    if(createBy == 0){
    	flag = false;
    	$("#createBy").next().text("请选择创建部门").show();
    }else{
    	$("#createBy").next().hide();
    }

   

    //校验活动类型
    var ruleTerm = $("#ruleTerm").val();
    if(ruleTerm == 0){
    	flag = false;
    	$("#ruleTerm").next().text("请选择活动类型").show();
    }else{
    	$("#ruleTerm").next().hide();
    }
    
    //校验活动类型规范限制条件
    var ruleCondition = $("#ruleCondition").val();
    if(ruleCondition == 100){
    	flag = false;
    	$("#ruleCondition").next().text("请选择活动类型范围限制条件").show();
    }else{
    	$("#ruleCondition").next().hide();
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
    	}else if(ruleCondition == 4){
    		var length = $("#selected-skus ul").children("li").length;
    		if(length == 0){
    			flag = false;
    			$("#ruleCondition").next().text("所选单品个数不能为空").show();
    		}else{
    			$("#ruleCondition").next().hide();
    		}
    	}
    }
    return flag;
}

function checkSubmitB2C() {
	
	var prices = $("#selected-skus label").text();
	
	
    var flag = true;
    // 校验名称
    var ruleName = $('#ruleName').val();
    if (ruleName.length > 100 || ruleName.length < 1) {
        flag = false;
        $('#ruleName').next().text('活动名称必填，且不能超过100字！').show();
    } else {
        $('#ruleName').next().hide();
    }
   console.log(1);
   console.log(flag);
    // 校验开始时间
    var startTime = $("#startdate").val();
    if (startTime == "") {
        $("#startdate").next().text('开始时间不能为空！').show();
        flag = false;
    } else {
        if (!checkStartTime()) {
            $("#startdate").next().text('开始时间不能小于系统当前时间！').show();
            flag = false;
        } else {
            $("#startdate").next().hide();
        }
    }
    // 校验结束时间
    var endTime = $("#enddate").val();
    if (endTime == "") {
        $("#enddate").next().text('结束时间不能为空！').show();
        flag = false;
    } else {
        if (!checkEndTime()) {
            $("#enddate").next().text('结束时间应该大于开始时间！').show();
            flag = false;
        } else {
            $("#enddate").next().hide();
        }
    }
    var matchnum = /^[0-9]*$/;
    
    //校验规则优先级
    var ruleCondition = $("#ruleCondition").val();
    if(ruleCondition == 4){
    	var ratio = $.trim($("#ruleSort").val());
        if (ratio != "" && !matchnum.test(ratio)) {
            flag = false;
            $('#ruleSort').next().text('规则优先级只能是正整数').show();
        } else if(ratio.length > 5){
        	flag = false;
        	$('#ruleSort').next().text('规则优先级必须是小于等于4位的正整数').show();
        }else if(ratio > 9998){
        	flag = false;
        	$('#ruleSort').next().text('规则优先级必须是小于等于9998的正整数').show();
        }else if(ratio < 1){
        	flag = false;
        	$('#ruleSort').next().text('规则优先级必须是大于等于1位的正整数').show();
        }else{
            $('#ruleSort').next().hide();
        }
    }
    console.log(2);
    console.log(flag);
    
    var matchnum1 = /^[0-9]*$/;
    if(ruleTerm == 10){
	    //校验满足金额
	    var term1 = $("#term1").val();
	    //满足金额必须大于0
	    if(term1 <= 0 || !matchnum1.test(term1)){
	    	flag = false;
	    	$("#term1").next().text("满足金额必须大于0,且为正整数").show();
	    }else{
	    	$("#term1").next().hide();
	    }
    }else if(ruleTerm == 13){
    	var term_00 = $("#term_00").val();
    	//满足金额必须大于0
	    if(term_00 <= 0 || !matchnum1.test(term_00)){
	    	flag = false;
	    	$("#term_00").next().text("满足金额必须大于0,且为正整数").show();
	    }else{
	    	$("#term_00").next().hide();
	    }
    }
    
    console.log(3);
    console.log(flag);
    
    //校验是否选择部门
    var createBy = $("#createBy").val();
    //满足金额必须大于0
    if(createBy == 0){
    	flag = false;
    	$("#createBy").next().text("请选择创建部门").show();
    }else{
    	$("#createBy").next().hide();
    }
    
    var ruleTerme = $("#ruleTerm").val();
    if(ruleTerm == 10){
	    //校验赠品
	    var term2 = $("#term2").val();
	    var giftType = $("#giftType").val();
	    if(giftType == 0){
	    	//0是请选择
	    	flag = false;
	    	$("#giftType").next().text("请选择赠品").show();
	    }else{
	    	$("#giftType").next().hide();
	    	//校验后置条件
	    	if(giftType == 2){
	    	    //满足金额必须大于0
	    	    if(term2 <= 0 || !matchnum1.test(term2)){
	    	    	flag = false;
	    	    	$("#term2").next().text("满足金额必须大于0,且为正整数").show();
	    	    }else{
	    	    	
	    	    	$("#selected-skus label").each(function(){
	    	    		var prices = $(this).text();
	    	    		console.log(prices);
	    	    		if(parseInt(prices)-5 < term2){
	    	    			flag = false;
	    	    			$("#term2").next().text("直降价格必须比商品原价小5").show();
	    	    		}
	    	    		if(flag == true){
	        	    		$("#term2").next().hide();
	        	    	}
	    	    	});
	    	    	
	    	    }
	        }else if(giftType == 1){
	        	if(term2 == 0){
	        		flag = false;
	    	    	$("#term2").next().text("请选择赠送的赠品").show();
	        	}else{
	        		$("#term2").next().hide();
	        	}
	        }
	    }
    }
    
    //校验活动类型
    if(ruleTerm == 0){
    	flag = false;
    	$("#ruleTerm").next().text("请选择活动类型").show();
    }else{
    	$("#ruleTerm").next().hide();
    }
  
    console.log(4);
    console.log(flag);
    
    //校验活动类型规范限制条件
    var ruleCondition = $("#ruleCondition").val();
    if(ruleCondition == 100){
    	flag = false;
    	$("#ruleCondition").next().text("请选择活动类型规范限制条件").show();
    }else{
    	$("#ruleCondition").next().hide();
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
    	}else if(ruleCondition == 4){
    		var length = $("#selected-skus ul").children("li").length;
    		if(length == 0){
    			flag = false;
    			$("#ruleCondition").next().text("所选单品个数不能为空").show();
    		}else{
    			$("#ruleCondition").next().hide();
    		}
    	}
    }
    return flag;
}

/**
 * 校验开始时间
 * 
 * @returns
 */
function checkStartTime() {
    var start = new Date();
    var endTime = $("#startdate").val();
    var end = new Date(endTime.replace("-", "/").replace("-", "/"));
    if (end < start) {
        return false;
    } else {
        return true;
    }

}

/**
 * 校验结束时间
 * 
 * @returns
 */
function checkEndTime() {
    var startTime = $("#startdate").val();
    var start = new Date(startTime.replace("-", "/").replace("-", "/"));
    var endTime = $("#enddate").val();
    var end = new Date(endTime.replace("-", "/").replace("-", "/"));
    if (end <= start) {
        return false;
    } else {
        return true;
    }
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
		$.ajax({
            type : 'post',
            url : "../productrule/findByCondition",
            data : "flag=0&page="+page,
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
            		var productName = object.productName;
            		var productId = object.productId;
            		var $li = $("<li id="+productId+"><p>"+productName+"</p><span>"+productId+"</span></li>");
            		$(".act-goods-list ul").append($li);
            	}
            	
            }
		});
	}
}

function findByNextPage(){
	var page = parseInt($("#page").text())+1;
	var totalPage = $("#totalPage").val();
	if(page > totalPage){
		return false;
	}else{
		$.ajax({
            type : 'post',
            url : "../productrule/findByCondition",
            data : "flag=0&oType=goods&page="+page,
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
            		var productName = object.productName;
            		var productId = object.productId;
            		var $li = $("<li id="+productId+"><p>"+productName+"</p><span>"+productId+"</span></li>");
            		$(".act-goods-list ul").append($li);
            	}
            	
            }
		});
	}
}

function findByFirstPage(){
	var page = 1;
	var totalPage = $("#totalPage").val();
	$.ajax({
        type : 'post',
        url : "../productrule/findByCondition",
        data : "flag=0&oType=goods&page="+page,
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
        		var productName = object.productName;
        		var productId = object.productId;
        		var $li = $("<li id="+productId+"><p>"+productName+"</p><span>"+productId+"</span></li>");
        		$(".act-goods-list ul").append($li);
        	}
        	
        }
	});
}

function findByPreviousPageB2C(){
	var page = parseInt($("#page").text())-1;
	if(page < 1){
		return false;
	}else{
		var skuName = $("#skuName").val();
		if(skuName == null || skuName == undefined || skuName == ''){
			$.ajax({
	            type : 'post',
	            url : "../productrule/findByConditionB2C",
	            data : "flag=0&page="+page,
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
	            		var $li = $("<li id="+skuId+"><span>商品名称:"+proName+"</span><br/>" +
                				"<span>商品ID:"+proId+"</span><br/><p>单品名称："+skuName+"</p><span>单品ID:"+skuId+"</span><br/><span>单品价格：</span><label>"+price+"</label></li>");
                		$(".act-goods-list ul").append($li);
	            	}
	            	$("#totalPage").text(totalPage);
	            }
			});
		}else{
			$.ajax({
	            type : 'post',
	            url : "../productrule/findByConditionB2C",
	            data : "flag=1&page="+page+"&skuName="+skuName,
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
	            		var $li = $("<li id="+skuId+"><span>商品名称:"+proName+"</span><br/>" +
                				"<span>商品ID:"+proId+"</span><br/><p>单品名称："+skuName+"</p><span>单品ID:"+skuId+"</span><br/><span>单品价格：</span><label>"+price+"</label></li>");
                		$(".act-goods-list ul").append($li);
	            	}
	            	$("#totalPage").text(totalPage);
	            }
			});
		}
	}
}

function findByNextPageB2C(){
	var page = parseInt($("#page").text())+1;
	var totalPage = $("#totalPage").val();
	if(page > totalPage){
		return false;
	}else{
		var skuName = $("#skuName").val();
		if(skuName == null || skuName == undefined || skuName == ''){
			$.ajax({
	            type : 'post',
	            url : "../productrule/findByConditionB2C",
	            data : "flag=0&page="+page,
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
	            		var $li = $("<li id="+skuId+"><span>商品名称:"+proName+"</span><br/>" +
                				"<span>商品ID:"+proId+"</span><br/><p>单品名称："+skuName+"</p><span>单品ID:"+skuId+"</span><br/><span>单品价格：</span><label>"+price+"</label></li>");
                		$(".act-goods-list ul").append($li);
	            	}
	            	$("#totalPage").text(totalPage);
	            }
			});
		}else{
			$.ajax({
	            type : 'post',
	            url : "../productrule/findByConditionB2C",
	            data : "flag=1&page="+page+"&skuName="+skuName,
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
	            		var $li = $("<li id="+skuId+"><span>商品名称:"+proName+"</span><br/>" +
                				"<span>商品ID:"+proId+"</span><br/><p>单品名称："+skuName+"</p><span>单品ID:"+skuId+"</span><br/><span>单品价格：</span><label>"+price+"</label></li>");
                		$(".act-goods-list ul").append($li);
	            	}
	            	$("#totalPage").text(totalPage);
	            }
			});
		}
	}
}

function findByFirstPageB2C(){
	var page = 1;
	var totalPage = $("#totalPage").val();
	var skuName = $("#skuName").val();
	if(skuName == null || skuName == undefined || skuName == ''){
		$.ajax({
	        type : 'post',
	        url : "../productrule/findByConditionB2C",
	        data : "flag=0&page="+page,
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
	        		var $li = $("<li id="+skuId+"><span>商品名称:"+proName+"</span><br/>" +
            				"<span>商品ID:"+proId+"</span><br/><p>单品名称："+skuName+"</p><span>单品ID:"+skuId+"</span><br/><span>单品价格：</span><label>"+price+"</label></li>");
            		$(".act-goods-list ul").append($li);
	        	}
	        	$("#totalPage").text(totalPage);
	        }
		});
	}else{
		$.ajax({
	        type : 'post',
	        url : "../productrule/findByConditionB2C",
	        data : "flag=1&page="+page+"&skuName="+skuName,
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
	        		var $li = $("<li id="+skuId+"><span>商品名称:"+proName+"</span><br/>" +
            				"<span>商品ID:"+proId+"</span><br/><p>单品名称："+skuName+"</p><span>单品ID:"+skuId+"</span><br/><span>单品价格：</span><label>"+price+"</label></li>");
            		$(".act-goods-list ul").append($li);
	        	}
	        	$("#totalPage").text(totalPage);
	        }
		});
	}
}
function del(){
	$(this).remove();
}

function findBySkuId(){
	var skuName = $("#skuName").val();
	var page = 1;
	var totalPage = $("#totalPage").val();
	$.ajax({
        type : 'post',
        url : "../productrule/findByConditionB2C",
        data : "flag=1&page="+page+"&skuName="+skuName,
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
        		var $li = $("<li id="+skuId+"><span>商品名称:"+proName+"</span><br/>" +
        				"<span>商品ID:"+proId+"</span><br/><p>单品名称："+skuName+"</p><span>单品ID:"+skuId+"</span><br/><span>单品价格：</span><label>"+price+"</label></li>");
        		$(".act-goods-list ul").append($li);
        	}
        	$("#totalPage").text(totalPage);
        }
	});
	
	
	
}




