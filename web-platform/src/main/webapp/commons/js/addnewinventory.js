var  CONTEXTPATH  = $("#conPath").val();
$(document).ready(function(){
	$("table input.pici").live("blur", chpici);
	$("table input.num").live("blur", chnum);
	$("#endTime").live("blur", checkendtime);
	$("#createTime").live("blur", checkcreatetime);
	$("table.sq_wrap td a").live("click",function(){
//		if($(this).closest("table").find("tr").length>2){
			$(this).closest("tr").remove();
//		}
	});
	/*$("#checkeddealername").focus(function(){
		$(".alert_bu").show();    		
		getDealerList();
	});
	
	$("#warehouseclick").click(function(){
		
		var warehouseidlen = $("#dealertable").find("input[name='warehouseid']:checked");

		if(warehouseidlen.length > 0){
			
			var codeid  = warehouseidlen.val();
			var warehousename = $("#dealertable").find("input[name='warehouseid']:checked").closest('tr').find('span').text();
			
			$("#warehouseId").attr('warehouseId',codeid);
			$("#warehouseId").val(warehousename);
			
			$('#dealertable').empty();
			$('#warehouseclick').css('display','none');
			$('#dealerclick').show();
			$('#dealerclose').show();
			$('#boxtitle').find('h2').text('选择经销商.');
			$('.wrap').find('thead .t2').text('经销商名称');
			$('#dealernametext').css('display','block');
			getDealerList();
		}else{
			alert('选择仓库!');
		}
		
	});*/
	$("#warehouseclick").click(function(){
		
		var warehouseidlen = $("#dealertable").find("input[name='warehouseid']:checked");

		if(warehouseidlen.length > 0){
			
			var codeid  = warehouseidlen.val();
			var warehousename = $("#dealertable").find("input[name='warehouseid']:checked").closest('tr').find('span').text();
			
			$("#warehouseId").attr('warehouseId',codeid);
			$("#warehouseId").val(warehousename);
			$("#dealertable").empty();
			/*$('#dealertable').empty();*/
			/*$('#warehouseclick').css('display','none');*/
			/*$('#dealerclick').show();
			$('#dealerclose').show();*/
			$('.alert_bu').css('display','none');
			loadInventoryPage();
		}else{
			alert('选择仓库!');
		}
	});
});



function loadInventoryPage(){
	
	var checkeddealer = "";
	var checkeddealerid = "";
	
	$("#dealertable tr").each(function(){

		var checked = $(this).find("input[type='radio']").attr("checked");

		if(checked=="checked"){
			checkeddealer = $(this).find("span").text();
			checkeddealerid = $(this).find("input[type='radio']").attr("dealerId");
		}

	});
	
		$("#checkeddealername").val(checkeddealer);
		$("#checkeddealername").attr("checkeddealerid",checkeddealerid);
		var productId = $("#checkeddealername").attr("productId");
		closebox();

		var array_inventory = new Array();
		//array_inventory.push("dealerId="+checkeddealerid);
		array_inventory.push("productId="+productId);
		$.ajax({
			type : "post", 
			url  : CONTEXTPATH+"/product/getInventoryListByProductId", 
			data :array_inventory.join("&")+"&random="+Math.random(),
			success : function(msg) {
				if(msg==""){
					tipMessage("该商品库存信息已存在，请进入库存管理进行设置!",function(){
						reloadOpennerPage();
					});
				}else{
					$("#despatch").html(msg);
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("查询失败 ，请稍后再试。。");
			}
		});
}

function getDealerList(){

	var array_inventory = new Array();
	var productId = $("#checkeddealername").attr("productId");
	var getDealerName = $("#getDealerName").val();
	array_inventory.push("productId="+productId);
	array_inventory.push("dealerName="+getDealerName);
	$.ajax({
		type : "post", 
		url  : CONTEXTPATH+"/user/getDealerList", 
		data :array_inventory.join("&")+"&random="+Math.random(),
		success : function(msg) {
			$("#dealertable").empty();
			var tablelist = "";
			var rData = eval("("+msg+")");
			if(rData==""){
				 tipMessage("对不起，没有查到你要找的经销商!",function(){
				  });
				 return false;
			}else{
				$.each(rData,function(i,data){
					tablelist+="<tr><td class='t1'><input type='radio' name='dealerName' dealerId='"+data.dealerId+"'></td><td class='t2' title='"+data.companyName+"'><span>"+data.companyName+"</span></td></tr>";
				});
				$("#dealertable").append(tablelist);
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("查询失败 ，请稍后再试。。");
		}
	});
}

function fmsubmit(){
	var submit = true;
	var lengthisnotnull = 0;
	var skuidlength = 0;
	$("table.sq_wrap").each(function(){
		var it = $(this);
		it.closest(".tabborder").find(".dpl-tip-inline-warning").remove();
		var isisnull = isnull(it), isiserror = iserror(it);
		if ( isiserror ||  isisnull){	
			if(isisnull){
				it.after($('<span class="dpl-tip-inline-warning" style="color:red">请将数据填写完整</span>'));
			}
			submit = false;
		}
		
		var textlength = $(this).find("input[type='text']").length;
		if(textlength==0){
			lengthisnotnull++;
		}
		skuidlength++;
	});

	if(skuidlength==lengthisnotnull){
		alert("所有SKU都没有新的补录信息?请添加补录信息,或者关闭页面!");
		submit = false;
		return false;
	}

	if(submit){
		var checkeddealerid = $("#checkeddealername").attr("checkeddealerid");
		var productId = $("#checkeddealername").attr("productId");
		var warehouseId = $("#warehouseId").attr("warehouseId");
		var fm = $("#despatch").serialize();
		$.ajax({
			type : "post", 
			url  : CONTEXTPATH+"/product/stockrepair", 
			data :fm+"&pid="+productId+"&warehouseId="+warehouseId+"&dealerId="+checkeddealerid,
			success : function(msg) {
				if(msg=="-1"){
					alert("分配失败.");
				}
				if(msg=="-2"){
					alert("对不起，添加的批次号有重复，检查后重新尝试.");
				}
				if(msg=="0"){
					tipMessage("分配完成.返回已卖出的商品页！",function(){
						reloadOpennerPage();
					});
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("查询失败 ，请稍后再试。。");
			}
		});
	}
}

function addtime(event,skuId,skuNameCn){
	var src = event.srcElement || event.target; 
	var warehouse = $('#warehouseId').val();
	var tr = $('<tr class="sq_b"><td><input type="hidden" name="skuId" value="'+skuId+'"><input type="hidden" name="skuName" value="'+skuNameCn+'"><input type="hidden" name="batchNo" class="pici"></td><td><input type="text" name="qty" class="num"></td><td><input type="hidden" name="createTime" readonly="readonly" onClick="WdatePicker()"></td><td><input type="hidden" name="endTime" readonly="readonly" onClick="WdatePicker()"></td><td name="'+warehouse +'">'+warehouse+'</td><td><a>删除</a></td></tr>');
	$(src).parent().find('tbody').append(tr);
}

function add(event,skuId,skuNameCn){
	var src = event.srcElement || event.target;
	var warehouse = $('#warehouseId').val();
	var tr = $('<tr class="sq_b"><td><input type="hidden" name="skuId" value="'+skuId+'"><input type="hidden" name="skuName" value="'+skuNameCn+'"><input type="hidden" name="batchNo" class="pici"></td><td><input type="text" name="qty"  class="num"></td><td name="'+warehouse +'">'+warehouse+'</td><td><a>删除</a></td></tr>');
	$(src).parent().find('tbody').append(tr);
}

function chnum(){
	var that =$(this), val = this.value;
	that.closest(".tabborder").find(".dpl-tip-inline-warning").remove();
	if(val!=""){
		if(!RegExp("^[0-9]+$").test(val)  || val == "0"){
			that.closest("table").after($('<span class="dpl-tip-inline-warning">数量应为大于0的数字</span>'));
			this.value = "";
		}
	}
}
function chpici(){
	var that =$(this), val = this.value;
	that.closest(".tabborder").find(".dpl-tip-inline-warning").remove();
	if(val!=""){
		if(!RegExp("^[\\w-]{1,100}$").test(val)){
			that.closest("table").after($('<span class="dpl-tip-inline-warning" >批次号为1-100位的数字、字母、-和_</span>'));
			this.value = "";
		}else if(that.closest("table").find("input.pici").length + that.closest("table").find("td.pici").length >1){
			var warehouse = that.closest("tr").find("td[name]").attr("name");
			that.closest("table").find("input.pici").not(that).each(function(i,item){
				if(val == item.value && $(item).closest("tr").find("td[name]").attr("name") == warehouse){
					that.closest("table").after($('<span class="dpl-tip-inline-warning" >批次号不能相同</span>'));
					that.val("") ;
					return;
				}
			});
			that.closest("table").find("td.pici").each(function(i,item){
				if(val == $(item).text() && $(item).closest("tr").find("td[name]").attr("name") == warehouse ){
					that.closest("table").after($('<span class="dpl-tip-inline-warning" >批次号不能相同</span>'));
					that.val("") ;
					return;
				}
			});
		}
	}
}

function checkendtime(){
	var that =$(this), val = this.value;
	that.closest(".tabborder").find(".dpl-tip-inline-warning").remove();
	if(val!=""){
		var createTime = that.parent().prev().find("#createTime").val();
		if(createTime!=""){
			if(val<createTime){
				that.closest("table").after($('<span class="dpl-tip-inline-warning" >过期日期不能小于生产日期!</span>'));
				this.value = "";
			}
		}
	}
}
function checkcreatetime(){
	var that =$(this), val = this.value;
	that.closest(".tabborder").find(".dpl-tip-inline-warning").remove();
	if(val!=""){
		var endTime = that.parent().next().find("#endTime").val();
		if(endTime!=""){
			if(val>endTime){
				that.closest("table").after($('<span class="dpl-tip-inline-warning" >过期日期不能小于生产日期!</span>'));
				this.value = "";
			}
		}
	}
}



function closebox(){
	$(".alert_bu").hide(100);
}

function isnull(table){
	var isnull = false;
	table.find("input").each(function(){
		if(this.value == "" && this.name == "qty" ){
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
function reloadOpennerPage(){
	var elnode = window.opener.document.getElementById("pageContext"); 
	var page = $(elnode).val();
	window.opener.location.href="javascript:clickSubmit("+page+")";
	
	window.opener=null;
	window.open('','_self');
	window.close();
	
}