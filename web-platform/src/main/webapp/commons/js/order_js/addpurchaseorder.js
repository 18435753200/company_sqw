var  CONTEXTPATH=$("#conPath").val();
$(document).ready(function(){
	
	$('.pu-liebiao tbody:eq(1) input').val(0);
	reSumPrice();
	$('#addTime').click(addTime);
	
	$('#submit_addpurchase_fm').click(submit_addpurchase_fun);
	$("#warehouseName").focus(warehouseName);
	$("#warehouseclick").click(function(){
		
		var warehouseidlen = $("#suppliertable").find("input[name='warehouseid']:checked");

		if(warehouseidlen.length > 0){
			
			var codeid  = warehouseidlen.val();
			var warehousename = warehouseidlen.closest('tr').find('span').text();
			
			$("#warehouseCode").val(codeid);
			$("#warehouseName").val(warehousename);
			
			
			if($("#target").val() == '1'){
				
				$('#suppliertable').empty();
				$('#warehouseclick').css('display','none');
				$('#supplierclick').show();
				$('#boxtitle').find('h2').text('选择供应商.');
				$('.wrap').find('thead .t2').text('供应商名称');
				$('#suppliernametext').css('display','block');
			
				getSupplierList();
				
			}else{
				closebox();
			}
			
		}else{
			alert('选择仓库!');
		}
		
	});

	
});


function warehouseName(){
	$("#supplierclick").hide();
	$("#warehouseclick").show();
	if($("#target").val() == '3'){
		return false;
	}
	if($("#status10").val() != '1'){
		return false;
	}
	$.ajax({
		type : "post", 
		url  : CONTEXTPATH+"	/inventory/loadWareSelect", 
		dataType:'json',
		success : function(msg) {
			$("#suppliertable").empty();
			var tablelist = "";
			if(msg==""){
				 tipMessage("对不起，没有查到仓库!",function(){
					 $("#skuselect").empty();
				  });
				 return false;
			}else{
				$.each(eval(msg),function(i,data){
					tablelist+="<tr><td class='t1'><input type='radio' name='warehouseid' value='"+data.warehouseCode+"'></td><td class='t2' title='"+data.warehouseName+"'><span>"+data.warehouseName+"</span></td></tr>";
				});
				$("#suppliertable").append(tablelist);
				
				$('.alert_bu').show(10);
			}

		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("请稍后再试。。");
		}
	});
}
function submit_addpurchase_fun(){
	/*$('#purchaseOrderfm').*/
	var flag  = true;
	if ($("[name='receiveName']").val() == ''){
		flag = false;
		alert('请添加收货人姓名');
		return flag;
	}
	if ($("[name='receiveAddress']").val() == ''){
		flag = false;
		alert('请添加收货人地址');
		return flag;
	}
	if($("[name='receivePhone']").val() == ''){
		flag = false;
		alert('请添加收货人电话.');
		return flag;
	}
	if($('#ttr input').length==0){
		flag = false;
		alert('请添加商品.');
		return flag;
	}	
	var bj = 1;
	$("#ttr input[name='qty']").each(function(){
		if($(this).val()==0){
			bj = 0;
		}
	});
	if ( bj == 0 ){
		flag = false;
		alert('商品数量不能为0');
		return flag;
	} 
	var data = $('#purchaseOrderfm').serialize();
	if(data.indexOf('=&')!=-1){
		flag = false;
		alert('请完善表单信息.');
		return flag;
	}
	if(flag){
		
		$.ajax({
			type : "post", 
			url  : CONTEXTPATH+"/purchaseorder/addpurchaseorder", 
			data :data+"&random="+Math.random(),
			success : function(msg) {
				
				if(msg == '1'){
					 $.dialog.tipMessage('保存好了,返回列表页!',function(){
						 reloadOpennerPage();
					 });
				}else{
					alert('数据异常?');
				}

			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
		
	}
}

function checkinputnum(){
	var that = $(this);
	var tr = that.closest("tr"),val = that.val();
	
	if(!isNaN(val)){
        var decimalIndex=val.indexOf('.');
        if(decimalIndex=='-1'){     
            return false;     
        }else{
            var decimalPart=val.substring(decimalIndex+1,val.length);   
            if(decimalPart.length>4){
            	that.val(toFixed(val,'4'));
            }else{
               return false;     
            }     
        }     
	}

}

function toFixed(number, precision) {
	  var multiplier = Math.pow(10, precision);
	  return Math.round(number * multiplier) / multiplier;
	}
	

function selectwarehouse(){
	$('#suppliertable').empty();
	$('#warehouseclick').css('display','block');
	$('#supplierclick').hide();
	$('#boxtitle').find('h2').text('选择仓库.');
	$('.wrap').find('thead .t2').text('仓库名称');
	$('#suppliernametext').css('display','none');
}


function tofix(){
	var that = $(this);
	var val = that.val();
	
	if(val!="" && !isNaN(Number(val)) && Number(val) >=0){
		val = Number(val);
		that.val(val.toFixed(4));
	}else{
		that.val("0");
	}
}

function checknum(event){
	var mm = event.target;
	var str = mm.value;
	setTimeout(function () {
		if ( !RegExp('^\\d*\\.?$') .test(str)) {
			mm.value = '0';
		}
	}, 1000);
}

function checkrmb(event){
	var mm = event.target;
	var str = mm.value;
	if(str!=""){	
		if((!RegExp("^\\d+\\.\\d+$").test(str) && !RegExp("^\\d+\\.?$").test(str)) || Number(str)<0){
			mm.value = '';
		}
	}
}

function checkshui(event){
	var mm = event.target;
	var str = mm.value;
	if(str!=""){	
		if((!RegExp("^\\d+\\.\\d+$").test(str) && !RegExp("^\\d+\\.?$").test(str)) || Number(str)<0 || Number(str) > 100){
			mm.value = '';
		}
	}
}



function getSupplierList(){

	var array_inventory = new Array();
	var getSupplierName = $("#getSupplierName").val();
	array_inventory.push("supplierName="+getSupplierName);
	$.ajax({
		type : "post", 
		url  : CONTEXTPATH+"/user/getSupplierList", 
		data :array_inventory.join("&")+"&random="+Math.random(),
		success : function(msg) {
			$("#suppliertable").empty();
			var tablelist = "";
			var rData = eval("("+msg+")");
			if(rData==""){
				 tipMessage("对不起，没有查到你要找的供应商!",function(){
				  });
				 return false;
			}else{
				$.each(rData,function(i,data){
					tablelist+="<tr><td class='t1'><input type='radio' name='name' supplierId='"+data.supplierId+"'></td><td class='t2' title='"+data.name+"'><span>"+data.name+"</span></td></tr>";
				});
				$("#suppliertable").append(tablelist);
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("查询失败 ，请稍后再试。。");
		}
	});
}


function loadPurchasePage(supplierid,supplierName){
	
	var checkedsupplier = "";
	var checkedsupplierid = "";
	var loadtarget = $('#target').val();
	if(loadtarget == 3){
		$('#ttr th.operate').remove();
		$('#ttr td a').parent('td').remove();
		return false;
	}

	if (loadtarget == 1){
	
		$("#suppliertable tr").each(function(){
	
			var checked = $(this).find("input[type='radio']").attr("checked");
	
			if(checked=="checked"){
				checkedsupplier = $(this).find("span").text();
				checkedsupplierid = $(this).find("input[type='radio']").attr("supplierId");
			}
	
		});
	}else{
		
		checkedsupplier = supplierName;
		checkedsupplierid = supplierid;
		
	}
	
	if(checkedsupplier==""&&checkedsupplierid==""){
		alert("请先选择供应商");
	}else{

		$.ajax({
			type : "post", 
			url  : CONTEXTPATH+"/purchaseorder/getProductSKUBySupplierId", 
			data :"supplierId="+checkedsupplierid+"&random="+Math.random(),
			success : function(msg) {
				$("#suppliertable").empty();
				var tablelist = "<select>";
				var rData = eval("("+msg+")");
				if(rData==""){
					$('#skuselect').empty();
					$('#addTime').hide();
					tipMessage("对不起，没有查到你要找的商品!",function(){
					  });
					 return false;
				}else{
					$("#addTime").show();
					$.each(rData,function(i,data){
						tablelist+="<option productId="+data.productId+" value="+data.skuIdValue+">"+data.prodName+"_"+data.skuNameCn+"</option>";
					});
					tablelist+="</select>";
					$("#skuselect").html(tablelist);
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("查询失败 ，请稍后再试。。");
			}
		});
		
		$("#supplierName").val(checkedsupplier);
		$("#supplierId").val(checkedsupplierid);
		closebox();
	}
}

function addTime(){
	var skuName = $('#skuselect select').find("option:selected").text();
	var productid = $('#skuselect select').find("option:selected").attr('productid');
	var skuId = $('#skuselect select').val();
	
	if($("#ttr [name='skuId'][value='"+skuId+"']").length == 0){
		var ss = $('<tr class="order-bd">'+
				'<td ><input type="hidden" name="productid" value='+productid+'><input readonly="readonly" name="skuId" value="'+skuId +'"></td>'+
				'<td><input name="skuNameCn" readonly="readonly" value="'+skuName+'"></td>'+
				'<td><input name="qty" value="0"></td>'+
				'<td><input name="skuPriceRmb"  value="0.0000"></td>'+
				'<td><input name="dutyRate" value="0.0000"></td>'+
				'<td name="dutiesRmb">0.0000</td>'+
				'<td><input name="consumptionTaxRate"  value="0.0000"></td>'+
				'<td name="consumptionTaxRmb">0.0000</td>'+
				'<td><input name="valueAddedTaxRate"  value="0.0000"></td>'+
				'<td name="valueAddedTaxRmb">0.0000</td>'+
				'<td name="subtotalPriceRmb">0.0000</td>'+
				'<td name="totalPriceRmb">0.0000</td>'+
				'<td><a href="javascript:;">删除</a></td></tr>');
		$("#ttr").append(ss);  
	}
}

function closebox(){
	$(".alert_bu").hide(100);
}

function isnull(table){
	var isnull = false;
	table.find("input").each(function(){
		if(this.value == ""){
			isnull = true;
			return isnull;
		}
	});
	return isnull;
}

function iserror(table){
	var iserror = false;
	if(table.closest(".tabborder").has(".dpl-tip-inline-warning").length>0)
		iserror = true;
	return iserror;
}


function del(event){
	var that = $(this);
	var tr = that.closest("tr");
	
	tr.remove();
	
	count_sumqty();
	
	count_sumDutiesRmb();
	
	count_sumConsumptionTaxRmb();
	
	count_sumValueAddedTaxRmb();
	
	count_sumSubtotalPriceRmb();
	
	count_sumTotalPriceRmb();
	
}

function reSumPrice(){
	count_sumqty();
	
	count_sumDutiesRmb();
	
	count_sumConsumptionTaxRmb();
	
	count_sumValueAddedTaxRmb();
	
	count_sumSubtotalPriceRmb();
	
	count_sumTotalPriceRmb();
}

function valueAddedTaxRate(event){
	var that = $(this);
	var tr = that.closest("tr"),val = that.val();
	
	if(val!="" && !isNaN(Number(val)) && Number(val) >=0 && Number(val) <= 100){
		val = Number(val);
		that.val(val.toFixed(4));
	}else{
		that.val("0");
	}
	

	//count valueAddedTaxRmb
	count_valueAddedTaxRmb(tr);
	
	//count totalPriceRmb
	count_totalPriceRmb(tr);
	
}

function consumptionTaxRate(event){
	var that = $(this);
	var tr = that.closest("tr"),val = that.val();
	
	if(val!="" && !isNaN(Number(val)) && Number(val) >=0 && Number(val) <= 100){
		val = Number(val);
		that.val(val.toFixed(4));
	}else{
		that.val("0");
	}

	//count consumptionTaxRmb
	count_consumptionTaxRmb(tr);
	
	//count totalPriceRmb
	count_totalPriceRmb(tr);
	
}

function dutyRate(event){
	var that = $(this);
	var tr = that.closest("tr"),val = that.val();
	
	if(val!="" && !isNaN(Number(val)) && Number(val) >=0 && Number(val) <= 100){
		val = Number(val);
		that.val(val.toFixed(4));
	}else{
		that.val("0");
	}
	
	//count dutiesRmb
	count_dutiesRmb(tr);
	
	//count totalPriceRmb
	count_totalPriceRmb(tr);
	
}

function skuPriceRmb(event){
	var that = $(this);
	var tr = that.closest("tr"),val = that.val();
	
	if(val!="" && !isNaN(Number(val))){
		val = Number(val);
	
		that.val(val.toFixed(4));
	}else{
		that.val("0");
	}
	
			//count dutiesRmb
			count_dutiesRmb(tr);
			
			//count consumptionTaxRmb
			count_consumptionTaxRmb(tr);
			
			//count valueAddedTaxRmb
			count_valueAddedTaxRmb(tr);
			
			//count subtotalPriceRmb
			count_subtotalPriceRmb(tr);
			
			//count totalPriceRmb
			count_totalPriceRmb(tr);
		
	
	
	
}

function qty(event){
	var that = $(this);
	var tr = that.closest("tr");
	
	//count sumqty
	count_sumqty();
	
	//count dutiesRmb
	count_dutiesRmb(tr);
	
	//count consumptionTaxRmb
	count_consumptionTaxRmb(tr);
	
	//count valueAddedTaxRmb
	count_valueAddedTaxRmb(tr);
	
	//count subtotalPriceRmb
	count_subtotalPriceRmb(tr);
	
	//count totalPriceRmb
	count_totalPriceRmb(tr);
}

//count sumqty
function count_sumqty(){
	var sum = 0;
	$("#ttr input[name='qty']").each(function(i,item){
		if($(item).val() != ""){
			sum += Number($(item).val());
		}
	});
	
	$("[name='sumQty']").val(sum);
};

function count_sumDutiesRmb(){
	var sum = 0;
	$("#ttr [name='dutiesRmb']").each(function(i,item){
		if($(item).text() != ""){
			sum += Number($(item).text());
		}
	});
	
	$("[name='sumDutiesRmb']").val(sum.toFixed(4));
}

function count_sumConsumptionTaxRmb(){
	var sum = 0;
	$("#ttr [name='consumptionTaxRmb']").each(function(i,item){
		if($(item).text() != ""){
			sum += Number($(item).text());
		}
	});
	
	$("[name='sumConsumptionTaxRmb']").val(sum.toFixed(4));
}

function count_sumValueAddedTaxRmb(){
	var sum = 0;
	$("#ttr [name='valueAddedTaxRmb']").each(function(i,item){
		if($(item).text() != ""){
			sum += Number($(item).text());
		}
	});
	
	$("[name='sumValueAddedTaxRmb']").val(sum.toFixed(4));
}

function count_sumSubtotalPriceRmb(){
	var sum = 0;
	$("#ttr [name='subtotalPriceRmb']").each(function(i,item){
		if($(item).text() != ""){
			sum += Number($(item).text());
		}
	});
	
	$("[name='sumSubtotalPriceRmb']").val(sum.toFixed(4));
}

function count_sumTotalPriceRmb(){
	var sum = 0;
	$("#ttr [name='totalPriceRmb']").each(function(i,item){
		if($(item).text() != ""){
			sum += Number($(item).text());
		}
	});
	
	$("[name='sumTotalPriceRmb']").val(sum.toFixed(4));
}


//count dutiesRmb
function count_dutiesRmb(tr){
	var qty = tr.find("[name='qty']").val(), skuPriceRmb = tr.find("[name='skuPriceRmb']").val(), dutyRate = tr.find("[name='dutyRate']").val();
	
	if(qty !="" && skuPriceRmb != "" && dutyRate != ""){
		var dutiesRmb = Number(qty) * Number(skuPriceRmb) *Number(dutyRate) /100;
		tr.find("[name='dutiesRmb']").text(dutiesRmb.toFixed(4));
		
		//count sumDutiesRmb
		count_sumDutiesRmb();
	}	
};

//count consumptionTaxRmb
function count_consumptionTaxRmb(tr){
	var qty = tr.find("[name='qty']").val(), skuPriceRmb = tr.find("[name='skuPriceRmb']").val(), consumptionTaxRate = tr.find("[name='consumptionTaxRate']").val();
	
	if(qty !="" && skuPriceRmb != "" && consumptionTaxRate != ""){
		var consumptionTaxRmb = Number(qty) * Number(skuPriceRmb) *Number(consumptionTaxRate) /100;
		tr.find("[name='consumptionTaxRmb']").text(consumptionTaxRmb.toFixed(4));
		
		//count sumConsumptionTaxRmb
		count_sumConsumptionTaxRmb();
	}	
};

//count valueAddedTaxRmb
function count_valueAddedTaxRmb(tr){
	var qty = tr.find("[name='qty']").val(), skuPriceRmb = tr.find("[name='skuPriceRmb']").val(), valueAddedTaxRate = tr.find("[name='valueAddedTaxRate']").val();
	
	if(qty !="" && skuPriceRmb != "" && valueAddedTaxRate != ""){
		var valueAddedTaxRmb = Number(qty) * Number(skuPriceRmb) *Number(valueAddedTaxRate) /100;
		tr.find("[name='valueAddedTaxRmb']").text(valueAddedTaxRmb.toFixed(4));
		
		//count sumValueAddedTaxRmb
		count_sumValueAddedTaxRmb();
	}	
};

//count subtotalPriceRmb
function count_subtotalPriceRmb(tr){
	var qty = tr.find("[name='qty']").val(), skuPriceRmb = tr.find("[name='skuPriceRmb']").val();
	
	if(qty !="" && skuPriceRmb != "" ){
		var subtotalPriceRmb = Number(qty) * Number(skuPriceRmb);
		tr.find("[name='subtotalPriceRmb']").text(subtotalPriceRmb.toFixed(4));
		
		//count sumSubtotalPriceRmb
		count_sumSubtotalPriceRmb();
	}	
};

//count totalPriceRmb
function count_totalPriceRmb(tr){
	var dutiesRmb = tr.find("[name='dutiesRmb']").text(), consumptionTaxRmb = tr.find("[name='consumptionTaxRmb']").text(),
	consumptionTaxRmb = tr.find("[name='valueAddedTaxRmb']").text(), subtotalPriceRmb = tr.find("[name='subtotalPriceRmb']").text();
	
	if(dutiesRmb !="" && consumptionTaxRmb != "" && consumptionTaxRmb != "" && subtotalPriceRmb!=""){
		var totalPriceRmb = Number(dutiesRmb) + Number(consumptionTaxRmb)+ Number(consumptionTaxRmb) + Number(subtotalPriceRmb);
		tr.find("[name='totalPriceRmb']").text(totalPriceRmb.toFixed(4));
		
		//count sumTotalPriceRmb
		count_sumTotalPriceRmb();
	}	
};

function reloadOpennerPage(){
	var elnode = window.opener.document.getElementById("pageContext"); 
	var page = $(elnode).val();
	window.opener.location.href="javascript:clickSubmit("+page+")";
	
	window.opener=null;
	window.open('','_self');
	window.close();
}

function checktext(){
	var that = $(this),val = that.val();
	if(val.length > 50){
		alert("字符长度不能超于50");
		that.val("");
	}
}

function checktel(){
	var that = $(this),val = that.val();
	if(!RegExp("^0?(13|15|17|18|14)[0-9]{9}$").test(val)){
		alert("请输入正确的电话号码");
		that.val("");
	}
}