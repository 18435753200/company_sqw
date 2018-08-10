var  CONTEXTPATH  = $("#conPath").val();
var sessionId = "";
$(document) .ready(function () {
	$(".chim2").delegate("input", "change", changebox);
	$(".yanse2").delegate("input", "change", changebox);
	$(".yanse2").delegate("input", "change", changeimg);
	
	//B2C
	$("#tb-b2c .unitPrice").live("input propertychange", checknum);
	$("#tb-b2c .domesticPrice").live("input propertychange", checknum);
	$("#tb-b2c .bestoayPrice").live("input propertychange", checknum);
	
	$("#tb-b2c .unitPrice").live("blur", checknum2);
	$("#tb-b2c .domesticPrice").live("blur", checknum2);
	$("#tb-b2c .bestoayPrice").live("blur", checknum2);

	$("#tb-b2c .sellCount").live("input propertychange", checkn);
	$("#tb-b2c .sellCount").live("blur", checknum3);
	$("#tb-b2c .customsSkuNumber").live("blur", checknum4);
	$("#tb-b2c .productCode").live("blur", checknum5);
	
	$('#retaiPrePayPercent') .live('change', checkproportion);
	$("#tb-speca-quotation1 :text").live("input propertychange", checknum);
	$("#tb-speca-quotation2 :text").live("input propertychange", checknum);
	$("#tb-speca-quotation3 :text").live("input propertychange", checknum);
	$("#tb-speca-quotation1 :text").live("blur", checknum1);
	$("#tb-speca-quotation2 :text").live("blur", checknum1);
	$("#tb-speca-quotation3 :text").live("blur", checknum1);
	$("table#tb-tiaoxingma :text").live("blur", checktiao);
	$('.jinben .operate .toleft') .live('click', pre);
	$('.jinben .operate .toright') .live('click', next);
	$('.jinben .operate .del') .live('click', dele);
	$('.jinben .operate .down') .live('click', down);

	sessionId = $("#sessionId").val();
	
	//图片放大查看
	$('.jinben .p-img img') .live('click', trasform);
	$('.lb-close') .live('click', function(){
		$("#lightbox").hide();
		$("#lightboxOverlay").hide();
	});
	
	$('.b2 input') .live('blur', function(){
		var that = $(this);
		var $div = that.closest(".tq2");
		numpricecheck($div);
	});
	
	$(".same_price").bind("change",sameprice);
	
	xiug1();
	xiug3();


	$("select[name='dealerProductPackage.measureid']").bind('change',function(){
		var that = this;
		$(".danwei").text($(that).find("option:selected").text());
	});
	
	//	提交审核
	$("#postTrade").click(function(){
		//$(".fabu_btn").attr("disabled","disabled");
		//验证b2b信息可靠
		var flag = saveProduct();
		//验证b2c信息可靠
		//flag = checkB2CData(flag);
		
		if (flag == false) {
			$(".fabu_btn").removeAttr("disabled");
			tipMessage('部分信息不完整或不符合规范，请修改！',function(){});
		}
		if (flag == true) {
			var datas = $('#productAction').serialize();
			$.ajax({
				type:'post',
				url:CONTEXTPATH+'/product/editProduct',
				data: $('#productAction').serialize(),
				success:function(data){
					if(data=='1'){
						tipMessage("提交审核成功，关闭当前页面！",function(){
							reloadOpennerPage();
						});
					}else{
						tipMessage("提交审核失败，请检查后重试！",function(){
						});
					}
					
				}
			});
		}
	});
	
	//	变更属性
	$("#postProperty").click(function(){
		$(".fabu_btn").attr("disabled","disabled");
		var flag = editPropertyCheck();
		if (flag == false) {
			$(".fabu_btn").removeAttr("disabled");
			tipMessage('部分信息不完整或不符合规范，请修改！',function(){});
		}
		if (flag == true) {
			$.ajax({
				type:'post',
				url:CONTEXTPATH+'/product/editProperty',
				data: $('#productAction').serialize(),
				success:function(data){
					if(data=='1'){
						tipMessage("变更属性成功，关闭当前页面！",function(){
							reloadOpennerPage();
						});
					}else{
						tipMessage("变更属性失败，请检查后重试！",function(){
						});
					}

				}
			});
		}
	});

	//提交保存
	$("#postEdit").click(function(){
		$(".fabu_btn").attr("disabled","disabled");
		$(".fabu_btn").removeAttr("disabled");
		$.ajax({
			type:'post',
			url:CONTEXTPATH+'/product/saveProduct',
			data: $('#productAction').serialize()+"&dealerProduct.auditReason=3",
			success:function(data){
				if(data=='1'){
					tipMessage("保存成功，关闭当前页面！",function(){
						reloadOpennerPage();
					});
				}else{
					tipMessage("保存失败，请检查后重试！",function(){
						$(".fabu_btn").removeAttr("disabled");
					});
				}

			}
		});
	});
});

//验证信息 全验证
function saveProduct() {
	
	$(".tqz .dpl-tip-inline-warning").remove();
	var issubmit = true;
	var attrobjs = $("#attrobjs input[required='required'][type='text']");
	
	if(attrobjs!=''){
		$.each(attrobjs,function(){
			var attrobjval = $(this).val();
			var attrobjsconfirm = 1;
			if(attrobjval==''){
				issubmit = false;
				attrobjsconfirm = 0;
				$(this).next().text("带星号的属性值必填。").show();
			}
			if(attrobjval.length>100){
				issubmit = false;
				attrobjsconfirm = 0;
				$(this).next().text("长度不能大于50位。").show();
			}
			if(attrobjsconfirm==1){
				$(this).next().hide();
			}
		});
	}
	
var attrobjs1 = $("#attrobjs input[required='sizeForCurr'][type='text']");
	
	if(attrobjs1!=''){
		$.each(attrobjs1,function(){
			var attrobjval1 = $(this).val();
			var attrobjsconfirm1 = 1;
			if(attrobjval1.length>100){
				issubmit1 = false;
				attrobjsconfirm1 = 0;
				$(this).next().text("长度不能大于100位。").show();
			}
			if(attrobjsconfirm1==1){
				$(this).next().hide();
			}
		});
	}
	
	var notrequiredattr = $("#attrobjs input[required!='required'][type='text'][disabled!='disabled']");
	
	if(notrequiredattr!=''){
		$.each(notrequiredattr,function(){
			var attrobjval = $(this).val();
			var attrobjsconfirm = 1;
			if(attrobjval.length>200){
				issubmit = false;
				attrobjsconfirm = 0;
				$(this).next().text("长度不能大于200位。").show();
			}
			if(attrobjsconfirm==1){
				$(this).next().hide();
			}
		});
	}
	
	$('#attrobjs p[required=\'required\']').has('input[type=\'checkbox\']').each(function () {
		if ($(this).find('input[type=\'checkbox\']:checked').length < 1) {
			issubmit = false;
			$(this).find('.dpl-tip-inline-warning').text("带星号的属性值必填。").css('display','block');
		}
	});
	
	var attrError=0;
	$("#tbl tr").each(function(i,item){
		if(i>=1){
			var attrLens = $(item).find("input[type='text']").length;
			if(attrLens<3){
				issubmit=false;
				attrError=1;
			}
			$(item).find("input[type='text']").each(function(j,item1){
				if($(item1).val()=="" || $(item1).val()==null || typeof($(item1).val())=="undefined"){
					issubmit=false;
					attrError=2;
				}
			});
		}
	});
		if(attrError==1){
			$("#tblError").text("已添加的普通属性，必须有属性值!").show();
		}else if(attrError==2){
			$("#tblError").text("已添加的普通属性，属性、属性值、排序都不可为空!").show();
		}else{

			$("#tblError").hide();
		}
	var prodType = $('#prodType').val();
	var radioval = $('input:radio[name="cost"]:checked') .val();
	if (radioval == '1') {
		
		/*验证表格字段非空*/
		var spotNumPraceStat = '1';
		
		$('#spotNumPrace .pp').next(".dpl-tip-inline-warning").remove();
		
		$("#spotNumPrace .pp .b2 input[type='text']").each(function(){
			
			var spotNumPraceObj =  $(this).val();
			if(spotNumPraceObj == '' ){
				issubmit = false;
				spotNumPraceStat = '0';
			}
		});
		if(spotNumPraceStat == '0'){
			
			$("#spotNumPrace .pp").after($('<span class="dpl-tip-inline-warning" style="display:block">正确填写现货价格表格</span>'));
			
		}
		
		if(prodType!=1){
			
			var futuresNumPraceStat = '1';
			
			$('#futuresNumPrace .pp').next(".dpl-tip-inline-warning").remove();
			
			$("#futuresNumPrace .pp .b2 input[type='text']").each(function(){
				
				var futuresNumPraceObj =  $(this).val();
				if(futuresNumPraceObj == ''){
					issubmit = false;
					futuresNumPraceStat = '0';
				}
			});
			if(futuresNumPraceStat == '0'){
				
				$("#futuresNumPrace .pp").after($('<span class="dpl-tip-inline-warning" style="display:block">正确填写期货价格表格</span>'));
				
			}
		}
		/*验证表格字段非空结束*/
		
		/*验证建议零售价只能是数字 小于18位 并且批次价格小于建议零售价*/
		var  matchnum = /^[0-9.]*$/;
		var onlyPrice = $.trim($("#onlyPrice").val());
		$('#onlyPrice').next(".dpl-tip-inline-warning").remove();
		if(!matchnum.test(onlyPrice)||onlyPrice.length>18){
			$("#onlyPrice").after($('<span class="dpl-tip-inline-warning" style="display:block">建议零售价只能是数字 并且不能大于18位！</span>'));
			issubmit = false;
		}else{
			
			var fflag = true;
			$("#onlyPrice").next(".dpl-tip-inline-warning").remove();
			$("#spotNumPrace .b2 input[name='pic']").each(function(){
				var thispic = $(this).val();
				if(Number(thispic)>Number(onlyPrice)){
					fflag = false;
					issubmit = false;
				}
			});
			if(!fflag){
				$("#onlyPrice").after($('<span class="dpl-tip-inline-warning" style="display:block">建议零售价应该大于批次价格！</span>'));
			}
		}
		//验证建议零售价只能是数字 小于18位 并且批次价格小于建议零售价结束
		
		/*起批量不能为空或小于1！*/
		var startNum = $("#spotNumPrace .b2 .start").eq(0).val();
		if (startNum == '' || startNum < 1) {
			issubmit = false;
			$("#spotNumPrace .pp").after($('<span class="dpl-tip-inline-warning" style="display:block">起批量不能为空或小于1！</span>'));
		}
		
		var startNum = $("#futuresNumPrace .b2 .start").eq(0).val();
		if (startNum == '' || startNum < 1) {
			issubmit = false;
			$("#futuresNumPrace .pp").after($('<span class="dpl-tip-inline-warning" style="display:block">起批量不能为空或小于1！</span>'));
		}
		
		
		/*价格区间的数量必须后者大于前者*/
		var spotNumStartNumStat = "1";
		$("#spotNumPrace .b2 input[name='start']").each(function(is,vs){
			
			var starts = $(this).val();
			var thisstarts = $("#spotNumPrace .b2 input[name='start']").eq(is+1).val();
			if(thisstarts!=''||thisstarts!=undefined){
				
				if(Number(starts)>=Number(thisstarts)){
					issubmit = false;
					spotNumStartNumStat = '0';
				}
				
			}
		});
		if(spotNumStartNumStat == '0'){
			
			$("#spotNumPrace .pp").after($('<span class="dpl-tip-inline-warning" style="display:block">价格区间的数量必须后者大于前者。</span>'));
			
		}
		
		
		if(prodType!=1){
			
			var futuresNumStartNumStat = "1";
			
			$("#futuresNumPrace .pp").next(".dpl-tip-inline-warning").remove();
			
			$("#futuresNumPrace .b2 input[class='start']").each(function(is,vs){
				
				var starts = $(this).val();
				var thisstarts = 	$("#futuresNumPrace .b2 input[class='start']").eq(is+1).val();
				if(thisstarts!=''||thisstarts!=undefined){
					if(Number(starts)>=Number(thisstarts)){
						issubmit = false;
						futuresNumStartNumStat = '0';
					}
				}
			});
			if(futuresNumStartNumStat == '0'){
				$("#futuresNumPrace .pp").after($('<span class="dpl-tip-inline-warning" style="display:block">价格区间的数量必须后者大于前者。</span>'));
			}
			
		}
	}
	if (radioval == '2') {
		
		
		/*最小起订量*/
		var minNum = $('#minNum').val();
		var t = /^[0-9]+(.[0-9]{2})?$/;
		$('#minNum').next(".dpl-tip-inline-warning").remove();
		if (minNum == '' || minNum.length > 5 || !t.test(minNum) || Number(minNum) < 1) {
			//issubmit = false;
			//$('#minNum').after($('<span class="dpl-tip-inline-warning" style="display:block">确认最小起订量为大于0小于6位的数字</span>'));
		} else {
			$('#minNum').next(".dpl-tip-inline-warning").remove();
		}
		if(prodType!=1){
			var supplierMinNum = $('#supplierMinNum').val();
			$('#supplierMinNum').next(".dpl-tip-inline-warning").remove();
			if (supplierMinNum == '' || supplierMinNum.length > 5 || !t.test(supplierMinNum) || Number(supplierMinNum) < 1) {
				//issubmit = false;
				//$('#supplierMinNum').after($('<span class="dpl-tip-inline-warning" style="display:block">确认最小起订量为大于0小于6位的数字</span>'));
			} else {
				$('#supplierMinNum').next(".dpl-tip-inline-warning").remove();
			}
		}
		/*最小起订量结束*/
		
		
		/*非空验证*/
		var spotpricestat = '1';
		$("#spotprice").next(".dpl-tip-inline-warning").remove();
		$("#spotprice").find("input[type='text']:not(#minNum)").each(function(){
			var thisval = $(this).val();
			
			if(thisval == ""||Number(thisval)==0){
				
				//spotpricestat = '0';
				//issubmit = false;
				
			}
			if( thisval.length > 18 ){
				
				spotpricestat = '-1';
				issubmit = false;
				
			}
			
		});
		
		if(spotpricestat == '0'){
			
			
			//$("#spotprice table").after($('<span class="dpl-tip-inline-warning" style="display:block">请认真填写价格表格！价格不能是0或者是空的.</span>'));
			
		}
		if(spotpricestat == '-1'){
			
			//$("#spotprice table").after($('<span class="dpl-tip-inline-warning" style="display:block">长度不能大于18位！</span>'));
			
		}
		
		
		var prodType = $('#prodType').val();
		
		if(prodType!=1){
			
			var futuresspricestat = '1';
			
			$("#futuressprice").find("input[type='text']:not(#supplierMinNum)").each(function(){
				
				var thisval = $(this).val();
				
				if(thisval == ""||Number(thisval)==0){
					
					//futuresspricestat = '0';
					//issubmit = false;
					
				}
				
				if( thisval.length > 18 ){
					
					spotpricestat = '-1';
					issubmit = false;
					
				}
				
				
			});
			if(futuresspricestat == '0'){
				
				//$("#futuressprice table").after($('<span class="dpl-tip-inline-warning" style="display:block">请认真填写价格表格！价格不能是0或者是空的.</span>'));
				
			}
			
			if(futuresspricestat == '-1'){
				
				//$("#futuressprice table").after($('<span class="dpl-tip-inline-warning" style="display:block">长度不能大于18位！</span>'));
				
			}
			
		}
		/*非空验证结束*/
		
		/*验证建议零售价要小于单价*/
		var ts = '1';
		var t = '1';
		$("#tb-speca-quotation2 tbody tr").each(function(is,vs){
			
			var pro_price = $(this).find('.pro_price').val();
			var sugprice = $(this).find('.sugprice').val();
			
			
			$("#tb-speca-quotation3 tbody tr").each(function(i,v){
				
				if(is == i){
					
					var pro_pricea = $(this).find('.pro_price').val();
					
					if(Number(pro_pricea)>Number(sugprice)){
						
						//issubmit = false;
					//	t='0';
						
					}
				}
			});
			
			if(Number(pro_price)>Number(sugprice)){
				//issubmit = false;
				//ts='0';
			}
			
		});
		
		
		if(t == '0'){
			$("#futuressprice table").after($('<span class="dpl-tip-inline-warning" style="display:block">同一单品单价不能大于建议零售价！</span>'));
		}
		
		if(ts == '0'){
			$("#spotprice table").after($('<span class="dpl-tip-inline-warning" style="display:block">同一单品单价不能大于建议零售价！</span>'));
		}
		/*验证建议零售价要小于单价结束*/
	}
	
	
	
	var imgs = $('.jinben ul');
	
	
	
	$.each(imgs, function (i, item) {
		var num = $(item) .find('img') .length;
		if (num < 1) {
			isSubmit = false;
			$(item) .prev() .find('.dpl-tip-inline-warning') .css('display', 'inline');
		} else {
			$(item) .prev() .find('.dpl-tip-inline-warning') .css('display', 'none');
		}
	});
	if ($('.yanse2 input[type=\'checkbox\']:checked') .length == 0) {
		$('.yanse2 ') .children('.dpl-tip-inline-warning') .css('display', 'inline');
		issubmit = false;
	} else {
		$('.yanse2 ') .children('.dpl-tip-inline-warning') .css('display', 'none');
	}
	if ($('.chim2 input[type=\'checkbox\']:checked') .length == 0) {
		$('.chim2 ') .children('.dpl-tip-inline-warning') .css('display', 'inline');
		issubmit = false;
	} else {
		$('.chim2 ') .children('.dpl-tip-inline-warning') .css('display', 'none');
	}
	var imgs = $('.jinben ul');
	$.each(imgs, function (i, item) {
		var num = $(item) .find('img') .length;
		if (num < 1) {
			issubmit = false;
			$(item) .prev() .find('.dpl-tip-inline-warning') .css('display', 'inline');
		} else {
			$(item) .prev() .find('.dpl-tip-inline-warning') .css('display', 'none');
		}
	});
	var mingxitext = $('.mingxi textarea[required=\'required\']');
	$.each(mingxitext, function (i, item) {
		if (item.value == '') {
			issubmit = false;
			$(item) .parent() .next() .css('display', 'inline');
		} else {
			$(item) .parent() .next() .css('display', 'none');
		}
	});
	var mingxiinput = $('.mingxi input[required=\'required\']');
	$.each(mingxiinput, function (i, item) {
		if (item.value == '') {
			issubmit = false;
			$(item) .parent() .next() .css('display', 'inline');
		} else {
			$(item) .parent() .next() .css('display', 'none');
		}
	});
	
	if(Number($("#retaiPrePayPercent").val()) < 0){
		issubmit = false;
		$("#retaiPrePayPercent").nextAll(".dpl-tip-inline-warning").eq(0).text("预付款比例是必须是大于0的数字.").show();
		$("#retaiPrePayPercent").val("");
	}else{
		$("#retaiPrePayPercent").nextAll(".dpl-tip-inline-warning").eq(0).hide();
	}
	
	
	var productinfo = $('#productinfo') .val();
	if (productinfo.length > 100||productinfo.length<1) {
		issubmit = false;
		$('#productinfo') .next() .text('商品标题不能大于100个字,且不能是空的!') .show();
	}
	
	var spotArrvalDays = Number($("#spotArrvalDays").val());
	if(spotArrvalDays>180||spotArrvalDays<1){
		//issubmit = false;
		//$("#spotArrvalDays").parent().next().text('现货预计到货天数不能小于1天且大于180天').show();
	}else if(!RegExp("^[0-9]*$").test(spotArrvalDays)){
		issubmit = false;
		$("#spotArrvalDays").parent().next().text('现货预计到货天数只能是数字').show();
	}else{
		$("#spotArrvalDays").parent().next().hide();
	}
	
	var futuresArrvalDays = Number($("#futuresArrvalDays").val());
	if(futuresArrvalDays>180||futuresArrvalDays<1){
		//issubmit = false;
		//$("#futuresArrvalDays").parent().next().text('期货预计到货天数不能小于1天且大于180天').show();
	}else if(!RegExp("^[0-9]*$").test(futuresArrvalDays)){
		issubmit = false;
		$("#futuresArrvalDays").parent().next().text('期货预计到货天数只能是数字').show();
	}else{
		$("#futuresArrvalDays").parent().next().hide();
	}
	
	if($("#logisticsDescription").val().length>200||$("#logisticsDescription").val().length<1){
		//issubmit = false;
		//$("#logisticsDescription").parent().next().text("物流描述不能大于200字,并且不能是空的!").show();
	}else{
		$("#logisticsDescription").parent() .next().hide();;
	}
	
	var skuCodeTablecheck = '1';
	$("#skuCodeTable  input[type='text']").each(function(){
		if($(this).val()==""||Number($(this).val())==0){
			//issubmit = false;
			//skuCodeTablecheck=0;
		}
	});
	
	if(skuCodeTablecheck == '1'){
		$("#skuCodeTable").next('.dpl-tip-inline-warning').hide();
	}else{
		//$("#skuCodeTable").next('.dpl-tip-inline-warning').text("正确填写必填字段").css('display','block');
	}
	
	if($("#salesServices").val().length>200){
		issubmit = false;
		$("#salesServices").parent().next().text("售后服务不能大于200字").show();
	}else{
		$("#salesServices").parent().next().hide();
	}
	if($("#packinglist").val().length>200){
		issubmit = false;
		$("#packingList").parent().next().text("包装清单不能大于200字").show();
	}else{
		$("#packingList").parent().next().hide();
	}
	
	var servicephone = $('#servicephone') .val();
	if(servicephone.length>20){
		issubmit = false;
		$('#mobiletext') .text('售后电话不能大于20位！') .show();
	}else{
		$('#mobiletext').hide();
	}
	
	var remark = $("#remark").val();
	if(remark.length>200){
		issubmit = false;
		$("#remark").next().text("备注信息最多只能200个字!").show();
	} else {
		$("#remark").next().hide();
	}
	
	if(!UE.getEditor('editor').hasContents()){
		//issubmit=false;
		//$("#Details").text("图文详情不能为空！").show();
	} else {
		$("#Details").hide();
	}
	
	
	return issubmit;
}

/*//b2c验证
function checkB2CData(issubmit){
	
	//商品标题
	var b2cProductName = $('#b2cProductName').val();
	if (b2cProductName.length > 100||b2cProductName.length<1) {
		issubmit = false;
		$('#b2cProductName').parent().next('.dpl-tip-inline-warning').css('display', 'block');
	}else{
		$('#b2cProductName').parent().next('.dpl-tip-inline-warning').css('display', 'none');
	}
	
	//发货地
	var b2cOriginCountry = $('#b2cOriginCountry').val();
	if (b2cOriginCountry.length > 100||b2cOriginCountry.length<1) {
		issubmit = false;
		$('#b2cOriginCountry').parent().next('.dpl-tip-inline-warning').css('display', 'block');
	}else{
		$('#b2cOriginCountry').parent().next('.dpl-tip-inline-warning').css('display', 'none');
	}

	//删除笛卡尔积提示
	$("#tb-b2c-warning").css('display', 'none');
	//笛卡尔积验证非空
	$("#tb-b2c input[type='text']").each(function(){

		if($(this).val()==''){
			issubmit = false;
			$("#tb-b2c-warning").css('display', 'block');
		}

	});
	
	//图文详情
	if(!UE.getEditor('editor1').hasContents()){
		issubmit=false;
		$("#Details1").text("图文详情不能为空！").show();
	} else {
		$("#Details1").hide();
	}
	
	return issubmit;
	
}*/

//变更属性验证信息 不验证价格信息
function editPropertyCheck() {

	$(".tqz .dpl-tip-inline-warning").remove();
	var issubmit = true;

	//商品标题
	var productinfo = $('#productinfo') .val();
	if (productinfo.length > 100||productinfo.length<1) {
		issubmit = false;
		$('#productinfo') .next() .text('商品标题不能大于100个字,且不能是空的!') .show();
	}
	
	var attrobjs = $("#attrobjs input[required='required'][type='text']");

	if(attrobjs!=''){
		$.each(attrobjs,function(){
			var attrobjval = $(this).val();
			var attrobjsconfirm = 1;
			if(attrobjval==''){
				issubmit = false;
				attrobjsconfirm = 0;
				$(this).next().text("带星号的属性值必填。").show();
			}
			if(attrobjval.length>100){
				issubmit = false;
				attrobjsconfirm = 0;
				$(this).next().text("长度不能大于50位。").show();
			}
			if(attrobjsconfirm==1){
				$(this).next().hide();
			}
		});
	}

	var attrError=0;
	$("#tbl tr").each(function(i,item){
		if(i>=1){
			var attrLens = $(item).find("input[type='text']").length;
			if(attrLens<3){
				issubmit=false;
				attrError=1;
			}
			$(item).find("input[type='text']").each(function(j,item1){
				if($(item1).val()=="" || $(item1).val()==null || typeof($(item1).val())=="undefined"){
					issubmit=false;
					attrError=2;
				}
			});
		}
	});
		if(attrError==1){
			$("#tblError").text("已添加的普通属性，必须有属性值!").show();
		}else if(attrError==2){
			$("#tblError").text("已添加的普通属性，属性、属性值、排序都不可为空!").show();
		}else{

			$("#tblError").hide();
		}
	
	var notrequiredattr = $("#attrobjs input[required!='required'][type='text'][disabled!='disabled']");

	if(notrequiredattr!=''){
		$.each(notrequiredattr,function(){
			var attrobjval = $(this).val();
			var attrobjsconfirm = 1;
			if(attrobjval.length>200){
				issubmit = false;
				attrobjsconfirm = 0;
				$(this).next().text("长度不能大于200位。").show();
			}
			if(attrobjsconfirm==1){
				$(this).next().hide();
			}
		});
	}

	$('#attrobjs p[required=\'required\']').has('input[type=\'checkbox\']').each(function () {
		if ($(this).find('input[type=\'checkbox\']:checked').length < 1) {
			issubmit = false;
			$(this).find('.dpl-tip-inline-warning').text("带星号的属性值必填。").css('display','block');
		}
	});

	var imgs = $('.jinben ul');

	$.each(imgs, function (i, item) {
		var num = $(item) .find('img') .length;
		if (num < 1) {
			isSubmit = false;
			$(item) .prev() .find('.dpl-tip-inline-warning') .css('display', 'inline');
		} else {
			$(item) .prev() .find('.dpl-tip-inline-warning') .css('display', 'none');
		}
	});
	if ($('.yanse2 input[type=\'checkbox\']:checked') .length == 0) {
		$('.yanse2 ') .children('.dpl-tip-inline-warning') .css('display', 'inline');
		issubmit = false;
	} else {
		$('.yanse2 ') .children('.dpl-tip-inline-warning') .css('display', 'none');
	}
	if ($('.chim2 input[type=\'checkbox\']:checked') .length == 0) {
		$('.chim2 ') .children('.dpl-tip-inline-warning') .css('display', 'inline');
		issubmit = false;
	} else {
		$('.chim2 ') .children('.dpl-tip-inline-warning') .css('display', 'none');
	}
	var imgs = $('.jinben ul');
	$.each(imgs, function (i, item) {
		var num = $(item) .find('img') .length;
		if (num < 1) {
			issubmit = false;
			$(item) .prev() .find('.dpl-tip-inline-warning') .css('display', 'inline');
		} else {
			$(item) .prev() .find('.dpl-tip-inline-warning') .css('display', 'none');
		}
	});
	var mingxitext = $('.mingxi textarea[required=\'required\']');
	$.each(mingxitext, function (i, item) {
		if (item.value == '') {
			issubmit = false;
			$(item) .parent() .next() .css('display', 'inline');
		} else {
			$(item) .parent() .next() .css('display', 'none');
		}
	});
	var mingxiinput = $('.mingxi input[required=\'required\']');
	$.each(mingxiinput, function (i, item) {
		if (item.value == '') {
			issubmit = false;
			$(item) .parent() .next() .css('display', 'inline');
		} else {
			$(item) .parent() .next() .css('display', 'none');
		}
	});

	//预计到货天数
	var spotArrvalDays = Number($("#spotArrvalDays").val());
	if(spotArrvalDays>180||spotArrvalDays<1){
		//issubmit = false;
		//$("#spotArrvalDays").parent().next().text('现货预计到货天数不能小于1天且大于180天').show();
	}else if(!RegExp("^[0-9]*$").test(spotArrvalDays)){
		issubmit = false;
		$("#spotArrvalDays").parent().next().text('现货预计到货天数只能是数字').show();
	}else{
		$("#spotArrvalDays").parent().next().hide();
	}

	var futuresArrvalDays = Number($("#futuresArrvalDays").val());
	if(futuresArrvalDays>180||futuresArrvalDays<1){
		//issubmit = false;
		//$("#futuresArrvalDays").parent().next().text('期货预计到货天数不能小于1天且大于180天').show();
	}else if(!RegExp("^[0-9]*$").test(futuresArrvalDays)){
		issubmit = false;
		$("#futuresArrvalDays").parent().next().text('期货预计到货天数只能是数字').show();
	}else{
		$("#futuresArrvalDays").parent().next().hide();
	}
	
	//条形码
	var skuCodeTablecheck = '1';
	$("#skuCodeTable  input[type='text']").each(function(){
		if($(this).val()==""||Number($(this).val())==0){
		//	issubmit = false;
			//skuCodeTablecheck=0;
		}
	});
	
	if(skuCodeTablecheck == '1'){
		$("#skuCodeTable .dpl-tip-inline-warning").hide();
	}else{
		//$("#skuCodeTable .dpl-tip-inline-warning").text("正确填写必填字段").show();
	}

	//物流描述
	if($("#logisticsDescription").val().length>200||$("#logisticsDescription").val().length<1){
		//issubmit = false;
		//$("#logisticsDescription").parent().next().text("物流描述不能大于200字,并且不能是空的!").show();
	}else{
		$("#logisticsDescription").parent() .next().hide();;
	}
	//售后服务
	if($("#salesServices").val().length>200){
		issubmit = false;
		$("#salesServices").parent().next().text("售后服务不能大于200字").show();
	}else{
		$("#salesServices").parent().next().hide();
	}
	//包装清单
	if($("#packinglist").val().length>200){
		issubmit = false;
		$("#packingList").parent().next().text("包装清单不能大于200字").show();
	}else{
		$("#packingList").parent().next().hide();
	}
	//售后电话
	var servicephone = $('#servicephone') .val();
	if(servicephone.length>20){
		issubmit = false;
		$('#mobiletext') .text('售后电话不能大于20位！') .show();
	}else{
		$('#mobiletext').hide();
	}
	//备注信息
	var remark = $("#remark").val();
	if(remark.length>200){
		issubmit = false;
		$("#remark").next().text("备注信息最多只能200个字!").show();
	} else {
		$("#remark").next().hide();
	}
	//图文详情
	if(!UE.getEditor('editor').hasContents()){
		//issubmit=false;
		//$("#Details").text("图文详情不能为空！").show();
	} else {
		$("#Details").hide();
	}

	return issubmit;
}


function checkProductIsEdit(productId){
	var pid_array = new Array();

	if(productId!=""){

		pid_array.push("productId="+productId);

		$.ajax({
			type : "post", 
			data: pid_array.join("&"),
			url : CONTEXTPATH+"/product/checkProductIsEdit", 
			dataType:'json',
			success : function(msg) {
				if(msg.statu=='-1'){
					tipMessage(msg.name+"正在修改当前商品!",function(){
						window.opener=null;
						window.open('','_self');
						window.close();
					});
				}else if(msg.statu=='-2'){
					tipMessage("商品异常,请查证后再次编辑!",function(){
						window.opener=null;
						window.open('','_self');
						window.close();
					});
				}
			},
			error:function(){
				tipMessage("商品异常,请查证后再次编辑!",function(){
					window.opener=null;
					window.open('','_self');
					window.close();
				});
			}
		});
	}else{
		tipMessage("商品异常,请查证后再次编辑!",function(){
			window.opener=null;
			window.open('','_self');
			window.close();
		});
	}
}


//全部发布商品开始
var checkproportion=function(){
	var reg = /^(?:0|[1-9][0-9]?|100)$/;
	var retaiPrePayPercent = $("#retaiPrePayPercent").val();
	if(!reg.test(retaiPrePayPercent)){
		$("#retaiPrePayPercent").nextAll(".dpl-tip-inline-warning").eq(0).text("预付款比例必须为1到100的正整数.").show();
		$("#retaiPrePayPercent").val("");
	}else{
		$("#retaiPrePayPercent").nextAll(".dpl-tip-inline-warning").eq(0).hide();
	}
};


var xiug = function () {
	$('.i_box .jia') .click(function () {
		$('.bb') .append('<p class=\'addP\' style=\'margin-left:20px;\'><input type=\'text\' name=\'start\' value=\'\' style=\'width:50px;\'> ：<input type=\'text\' name=\'pic\' value=\'\' style=\'width:198px;\'> <span class=\'del\'>删除</span></p>');
	});
	$(document) .on('click', '.del', function () {
		$(this) .parent('.addP') .remove();
	});
};
var xiug1=function(){
	$(".tqz span.b3").click(function(){
		var that = $(this);
		var $div = that.closest(".tq2");

		$div.find(".dpl-tip-inline-warning[id!='inputwarning']").remove();
		var vali = true;
		$div.find(".b2 input").each(function(){
			if(this.value == "" ){
				$(this).closest("span").after($('<span class="dpl-tip-inline-warning" style="display:block">请填写完整后再添加</span>'));
				vali = false;
				return false;
			}
		});
		if(vali){
			var measure = $("select[name='dealerProductPackage.measureid'] option:selected").text();
			if($div.index()==3){
				$div.find('.pp').append( "<span class='b2'>起批量：<input type='text' name='start' class='start'> <span class='danwei'>"+measure+"</span>及以上 <input type='text' class='pic'  name='pic'> 元/<span class='danwei'>"+measure+"</span><span class='del'> 删除</span></span>" );
			}else{
				$div.find('.pp').append( "<span class='b2'>起批量：<input type='text' name='supplierStart' class='start'> <span class='danwei'>"+measure+"</span>及以上 <input type='text' class='pic' name='supplierPrice'> 元/<span class='danwei'>"+measure+"</span><span class='del'> 删除</span></span>" );
			}

			var length = $div.find('.pp .b2').length;
			if(length == 3){
				$($div.find(".pp .b2:eq(1)")).find(".del").remove();
				$div.find(".b3").hide();
			}
		}
	});

	$(".tqz").on('click','.del',function(){
		var that = $(this);
		var $div = that.closest(".tq2");
		that.parent('.b2').remove();
		if($div.find('.pp .b2').length == 2){
			$($div.find(".pp .b2:eq(1)")).append("<span class='del'> 删除</span>");
			$div.find(".b3").show();
		}
		numpricecheck($div);
	});

};

var xiug3 = function () {
	var prodType = $('#prodType').val();
	$('.cp1') .click(function () {
//		if(prodType =="1"){
//			$('.tqz .tq2#spotNumPrace').show();
//		}else{
//			$('.tqz .tq2:not(:eq(0))').show();
//		}
		$('.tqz .tq2:not(:eq(0))').show();
		$('.g').hide();
	});
	$('.cp2') .click(function () {
		$('.tqz .tq2:not(:eq(0))').hide();
		$('.g').show();
	});
};
//全部发布商品结束


function inituploadzizhi(url){
	$.each(url,function(i,item){
		$("#zizhi .p-img").eq(i).append("<img src='"+ url[i]+"'></img>");
	});
}



function numpricecheck($div){
	//var that = event.target;
	var value1 = "";
	var value2 = "";
	$div.find("#inputwarning").text("").hide();
	$div.find(".dpl-tip-inline-warning[id!='inputwarning']").remove();
	var isnull = true;
	$.each($div.find(".pp .b2 input"),function(){
		if ($(this).attr("class") == "start" ){
			if(!RegExp("^\\d{1,6}$").test(this.value) || this.value =="0"){
				$(this).closest("span").after($('<span class="dpl-tip-inline-warning" style="display:block">起批量应为小于6位的整数</span>'));
				isnull = false;
			}
		}else if($(this).attr("class") == "pic"){
			if((!RegExp("^\\d{1,18}\\.\\d+$").test(this.value) && !RegExp("^\\d{1,18}\\.?$").test(this.value))|| Number(this.value)<=0){
				$(this).closest("span").after($('<span class="dpl-tip-inline-warning" style="display:block">价格应小于18位浮点数</span>'));
				isnull = false;
			}else{
				this.value = Number(this.value).toFixed(2);
			}
		}
	});
	if ($div.find(".pp .b2").length>1 && isnull){
		$.each($div.find(".pp .b2"),function(i,item){
			var object = $(item);
			var val1 = object.children(".start").val();
			var val2 = object.children(".pic").val();
			if(i>0){
				var value11 = Number(val1);
				var value22 = Number(val2); 
				if(value1 < value11 && value2 > value22){
					value1 = value11;
					value2 = value22;
				}else if(value1 >= value11){
					$div.find("#inputwarning").text("价格区间的数量必须后者大于前者。").show();
					return false;
				}else{
					$div.find("#inputwarning").text("产品单价必须后者小于前者。").show();
				}

			}else{
				value1 = Number(object.children(".start").val());
				value2 = Number(object.children(".pic").val());
			}
		});
	}

}


function checkn(event) {
	var mm = event.target;
	var name = $(mm).attr("name");
	setTimeout(function () {
		var str = mm.value;
		if (!RegExp('^\\d+$') .test(str)) {
			mm.value = '';
		}
	}, 1000);
}


function checknum(event) {
	var mm = event.target;
	var name = $(mm).attr("name");
	setTimeout(function () {
		var str = mm.value;
		if (!RegExp('^\\d*\\.?\\d+$') .test(str) && !RegExp('^\\d*\\.?$') .test(str)) {
			mm.value = '';
		}
	}, 1000);
}

function checknum1(event){
	var mm = event.target;
	var str =  mm.value;
	var that = $(this);
	var name = that.attr("name");
	$(".tab_box .dpl-tip-inline-warning").remove();
	if(str!=""){
		var str =  mm.value;
		if((!RegExp("^\\d{1,18}\\.\\d+$").test(str) && !RegExp("^\\d{1,18}\\.?$").test(str)) || Number(str)<0){
			mm.value="";
			that.closest("table").after($('<span class="dpl-tip-inline-warning" style="display:block">只能是浮点型数字！</span>'));
			//$(mm).delay(100000000).val("");
		}else{
			var ss= Number(str);
			mm.value = ss.toFixed(2);
		}

		if(that.closest("table").find("#"+name).is(":checked")==true){
			changeprice(event);
		}
	}	
}
function checknum2(event){
	var mm = event.target;
	var str =  mm.value;
	var that = $(this);
	var name = that.attr("name");
	$(".mo_b .dpl-tip-inline-warning").remove();
	if(str!=""){
		var str =  mm.value;
		if((!RegExp("^\\d{1,18}\\.\\d+$").test(str) && !RegExp("^\\d{1,18}\\.?$").test(str)) || Number(str)<0){
			mm.value="";
			that.closest("table").after($('<span class="dpl-tip-inline-warning" style="display:block">只能是浮点型数字！</span>'));
		}else{
			var ss= Number(str);
			mm.value = ss.toFixed(4);
		}
	}	
}
function checknum3(event){
	var mm = event.target;
	var str =  mm.value;
	var that = $(this);
	$(".mo_b .dpl-tip-inline-warning").remove();
	if(str!=""){
		var str =  mm.value;
		if(!RegExp("^\\d{1,7}$").test(str) || Number(str)<0){
			mm.value="";
			that.closest("table").after($('<span class="dpl-tip-inline-warning" style="display:block">不能超过7位数字</span>'));
		}
	}	
}

function checknum4(event){
	var mm = event.target;
	var str =  mm.value;
	var that = $(this);
	var name = that.attr("name");
	$(".mo_b .dpl-tip-inline-warning").remove();
	if(str!=""){
		var str =  mm.value;
		if(str.length>40){
			mm.value="";
			that.closest("table").after($('<span class="dpl-tip-inline-warning" style="display:block">不能超过40位字符</span>'));
		}
	}	
}
/*商品货号校验长度*/
function checknum5(event){
	var mm = event.target;
	var str =  mm.value;
	var that = $(this);
	$(".mo_b .dpl-tip-inline-warning").remove();
	if(str!=""){
		var str =  mm.value;
		if(str.length>40){
			mm.value="";
			that.closest("table").after($('<span class="dpl-tip-inline-warning" style="display:block">不能超过40位字符</span>'));
		}
	}	
}

function checktiao(event){
	var mm = event.target;
	if(mm.classList[0]=="pro_price"){
		return;
	}
	var str =  mm.value;
	var name = $(mm).attr("name");
	$("#skuCodeTable .dpl-tip-inline-warning").remove();
	if(str!=""){
		var str =  mm.value;
		if((!RegExp("^\\d{1,4}\\.\\d+$").test(str) && !RegExp("^\\d{1,4}\\.?$").test(str)) || Number(str)<0){
			mm.value="";
			$("#tb-tiaoxingma").after($('<span class="dpl-tip-inline-warning" style="display:block">重量4位以内正整数或者正浮点数</span>'));
		}else {
			var ss= Number(str);
			mm.value = ss.toFixed(2);
		}
		
		if($(mm).closest("table").find("#"+name).is(":checked")==true){
			changeprice(event);
		}
	}
}  


//图片处理函数
function pre(event) {
	var but = event.target;
	var li = $(but).parent().parent();
	var id = li.attr("class");
	var theimg = li.find(".p-img");
	var src = li.find("img").attr("src");
	if (src != null && id != "img-1") {
		if (li.prev().children(".p-img").children().is("img")) {
			var imgboxpre = li.prev().children(".p-img");
			li.prev().find(".p-img").remove();
			li.prev().prepend(theimg);
			li.find(".p-img").remove();
			li.prepend(imgboxpre);
		} else {
			li.find(".operate").hide();
			li.prev().find(".p-img").remove();
			li.prev().prepend(theimg);
			li.prepend($("<div class='p-img'></div>"));
			li.prev().children(".operate").show();
		}    
	} else {
		return false;
	}
}
function next(event) {
	var but = event.target;
	var li = $(but).parent().parent();
	var id = li.attr("class");
	var theimg = li.find(".p-img");
	var src = li.find("img").attr("src");
	if (src != null && id != "img-6") {
		if (li.next().children(".p-img").children().is("img")) {
			var imgboxnext = li.next().children(".p-img");
			li.next().find(".p-img").remove();
			li.next().prepend(theimg);
			li.find(".p-img").remove();
			li.prepend(imgboxnext);
		} else {
			li.find(".operate").hide();
			li.next().find(".p-img").remove();
			li.next().prepend(theimg);
			li.prepend($("<div class='p-img'></div>"));
			li.next().children(".operate").show();
		}

	} else {
		return false;
	}
}
function dele(event) {
	var but = event.target;
	var li = $(but).parent().parent();
	var inhid = li.find("#diddendiv");
	var theimg = li.find("img");
	var src = theimg.attr("src");
	if (src != null) {
		li.find(".operate").hide();
		theimg.parent().empty();
		inhid.empty();

	} else {
		return false;
	}
}
var frm = null;
var timer = null;
function down(event) {
	var but = event.target;
	var li = $(but).parent().parent();
	var inhid = li.find("#diddendiv");
	var theimg = li.find("img");
	var src = theimg.attr("src");
	if (src != null) {
		window.location.href=CONTEXTPATH+"/user/downloadFile?url="+src.substring(src.indexOf("group"),src.length);
		//下载
	} else {
		return false;
	}
}
var tableInfo = [
];
var tiaoxingmainfo = [
];
var test = [
];
function adduploadimg(value, id, url) {
	var isnew = $("div.yanse input#" + id).attr("isnew") == "1" ? true : false;
	var p_img = $("<div class='p-img'></div>");
	var operate =$("<div class='operate'><i class='toleft'>左移</i><i class='toright'>右移</i><i class='del'>删除</i><i class='down'>删除</i></div>");
	if(url.length == 0){
		var img1 = $("<li class='img-1'></li>").append(p_img.clone()).append(operate.clone());
		var img6 = $("<li class='img-6'></li>").append(p_img.clone()).append(operate.clone());
		var img = $("<li></li>").append(p_img.clone()).append(operate.clone());
		var ul = $("<ul id='"+ id +"_img'></ul>").append(img1).append(img.clone()).append(img.clone()).append(img.clone()).append(img).append(img6);
	}else{
		var ul = $("<ul id='"+ id +"_img'></ul>");
		$.each(url,function(i,item){
			if(i == 0){
				ul.append($("<li class='img-1'></li>").append(p_img.clone().append("<img src='"+ url[i]+"'><input type='hidden' name='imgUrl' value='" + id+"_"+url[i]+"'/></img>")).append(operate.clone().show()));
			}else if(i == 5){
				ul.append($("<li class='img-6'></li>").append(p_img.clone().append("<img src='"+ url[i]+"'><input type='hidden' name='imgUrl' value='" + id+"_"+url[i]+"'/></img>")).append(operate.clone().show()));
			}else{
				ul.append($("<li></li>").append(p_img.clone().append("<img src='"+ url[i]+"'><input type='hidden' name='imgUrl' value='" + id+"_"+url[i]+"'/></img>" )).append(operate.clone().show()));
			}
		});

		if(url.length<6){
			for(var j = url.length;j<6;j++){
				if(j == 5){
					ul.append($("<li class='img-6'></li>").append(p_img.clone()).append(operate.clone()));
				}else{
					ul.append($("<li></li>").append(p_img.clone()).append(operate.clone()));
				}
			}
		}			
	}
	var div = isnew ? $('<div></div>') .append('<div class=\'wenzi\'><span class=\'sctp-txt\' >' + value + '的图片:</span><input id=\'' + id + '_upload\' name=\'button\' type=\'submit\'  value=\'图片上传\' /><span class=\'dpl-tip-inline-warning\'>请至少上传一张图片</span></div>') .append(ul):
		$('<div></div>') .append('<div class=\'wenzi\'><span class=\'sctp-txt\' >' + value + '的图片:</span><input id=\'' + id + '_upload\' name=\'button\' type=\'submit\'  value=\'图片上传\' /><span class=\'dpl-tip-inline-warning\'>请至少上传一张图片</span></div>') .append(ul) ;
	var object = $('<div class=\'z\' id=\'' + id + '_xianshi\'></div>') .append(div);
	$('.jinben') .append(object);
	if (flashDetect()) {
		// 探测到已经安装了flash插件 则初始化上传按钮和提示
		// 初始化swfUpload组件
		$('#' + id + '_upload') .uploadify({
			// File Upload Settings
			'height': 30,
			'swf': '../commons/js/uploadify/uploadify.swf',
			'uploader': '../product/imageUp',
			'width': 100,
			'cancelImg': '../js/img/uploadify-cancel.png',
			'auto': true,
			'buttonText': '上传图片',
			file_size_limit: '100',
			file_queue_limit: 6,
			fileTypeExts: '*.gif;*.jpg;*.jpeg;*.png',
			file_types: '*.jpg;*.png;*.jpeg;',
			file_types_description: '*.jpg;*.jpeg;*.png;*.JPG;*.JPEG;*.PNG;',
			file_dialog_start_handler: fileDialogStart,
			file_queued_handler: fileQueued,
			file_queue_error_handler: fileQueueError,
			file_dialog_complete_handler: fileDialogComplete,
			upload_start_handler: uploadStart,
			upload_progress_handler: uploadProgress,
			upload_error_handler: uploadError,
			upload_success_handler: uploadSuccess,
			upload_complete_handler: uploadComplete,
			queue_complete_handler: queueComplete
		});
		
	} else {
		// 探测到没有flash支持，给出提示。
		$('.ifile') .html('<span class="no-flash-tip">' +
				'Hi，您的浏览器OUT了，它未安装新版的Flash Player，' +
				'<a href="http://get.adobe.com/flashplayer/" target="_blank">去安装>></a>' +
		'</span>');
	}

}

function adduploadimg2(value, id, url) {

	var isnew = $("div.yanse input#" + id).attr("isnew") == "1" ? true : false;
	var p_img = $("<div class='p-img'></div>");
	var operate =$("<div class='operate'><i class='toleft'>左移</i><i class='toright'>右移</i><i class='del'>删除</i><i class='down'>删除</i></div>");
	if(value != 'topic'){
		if(url.length == 0){
			
			var img1 = $("<li class='img-1'></li>").append(p_img.clone()).append(operate.clone()).append("<div class=\'linkUrl\'><label>连接：</label> <input name=\'viewUrl\' type=\'text\' class=\'inputname\'></div>");
			var img6 = $("<li class='img-6'></li>").append(p_img.clone()).append(operate.clone()).append("<div class=\'linkUrl\'><label>连接：</label> <input name=\'viewUrl\' type=\'text\' class=\'inputname\'></div>");
			var img = $("<li></li>").append(p_img.clone()).append(operate.clone()).append("<div class=\'linkUrl\'><label>连接：</label> <input name=\'viewUrl\' type=\'text\' class=\'inputname\'></div>");
			var ul = $("<ul id='"+ id +"_img'></ul>").append(img1).append(img.clone()).append(img.clone()).append(img.clone()).append(img).append(img6);

//			var div = isnew ? $('<div></div>') .append('<div class=\'wenzi\'><span class=\'sctp-txt\' >' + value + '的图片:</span><input id=\'' + id + '_upload\' name=\'button\' type=\'submit\'  value=\'图片上传\' /><span class=\'dpl-tip-inline-warning\'>请至少上传一张图片</span></div>') .append(ul):
//				$('<div></div>') .append('<div class=\'wenzi\'><span class=\'sctp-txt\' >' + value + '的图片:</span><input id=\'' + id + '_upload\' name=\'button\' type=\'submit\'  value=\'图片上传\' /><span class=\'dpl-tip-inline-warning\'>请至少上传一张图片</span></div>') .append(ul) ;
//			var object = $('<div class=\'z\' id=\'' + id + '_xianshi\'></div>') .append(div);
//			$('.jinben') .append(object);
		}else{
			
			if(value == 'banner' || $("#bannerId").val() == 'banner'){
				var shuzi = 0;
				var ul = $("<ul id='"+ id +"_img'></ul>");
				$.each(url,function(i,item){
					shuzi += 1;
					if(i == 0){
						ul.append($("<li class='img-1'></li>").append(p_img.clone().append("<img src='"+ url[i]+"'><input type='hidden' name='imgUrl' value='" + id+"_"+url[i]+"'/></img>")).append("<div class=\'linkUrl\'><label>连接：</label> <input name=\'viewUrl\' type=\'text\' class=\'inputname\' value='"+ i +"'></div>").append(operate.clone().show()));
					}else if(i == 5){
						ul.append($("<li class='img-6'></li>").append(p_img.clone().append("<img src='"+ url[i]+"'><input type='hidden' name='imgUrl' value='" + id+"_"+url[i]+"'/></img>")).append("<div class=\'linkUrl\'><label>连接：</label> <input name=\'viewUrl\' type=\'text\' class=\'inputname\' value='"+ i +"'></div>").append(operate.clone().show()));
					}else{
						ul.append($("<li></li>").append(p_img.clone().append("<img src='"+ url[i]+"'><input type='hidden' name='imgUrl' value='" + id+"_"+url[i]+"'/></img>" )).append("<div class=\'linkUrl\'><label>连接：</label> <input name=\'viewUrl\' type=\'text\' class=\'inputname\' value='"+ i +"'></div>").append(operate.clone().show()));
					}
				});
				if(shuzi<6){
					for(var j = shuzi;j<6;j++){
						if(j == 5){
							ul.append($("<li class='img-6'></li>").append(p_img.clone()).append("<div class=\'linkUrl\'><label>连接：</label> <input name=\'viewUrl\' type=\'text\' class=\'inputname\'></div>").append(operate.clone()));
						}else{
							ul.append($("<li></li>").append(p_img.clone()).append("<div class=\'linkUrl\'><label>连接：</label> <input name=\'viewUrl\' type=\'text\' class=\'inputname\'></div>").append(operate.clone()));
						}
					}
				}
				
				var div = isnew ? $('<div></div>') .append(ul):
					$('<div></div>') .append(ul) ;
				var object = $('<div class=\'z\' id=\'' + id + '_xianshi\'></div>') .append(div);
				$('.jinben') .append(object);
			}
		}
	}
	
	if(value == 'topic'){
		
		if(url.length != 0){
			
			$("#00_img").remove();
			
			var ul = $("<ul id='"+ id +"_img'></ul>");

			ul.append($("<li class='img-1'></li>").append(p_img.clone().append("<img src='"+ url+"'><input type='hidden' name='imgUrl' value='" + id+"_"+url+"'/></img>")).append(operate.clone().show()));	
		}
		
		var div = isnew ? $('<div></div>') .append('<div class=\'wenzi\'><input id=\'' + id + '_upload\' name=\'button\' type=\'submit\'  value=\'图片上传\' /><span class=\'dpl-tip-inline-warning\'>请至少上传一张图片</span></div>') .append(ul):
			$('<div></div>') .append('<div class=\'wenzi\'><input id=\'' + id + '_upload\' name=\'button\' type=\'submit\'  value=\'图片上传\' /><span class=\'dpl-tip-inline-warning\'>请至少上传一张图片</span></div>') .append(ul) ;
		$("#"+id+"_img").find("li").append(operate.clone());
		$("#zizhi").append(div);
//		$('.jinben') .append(object);
		
	}else if(value == 'banner' || $("#bannerId").val() == 'banner'){
		var div = isnew ? $('<div></div>') .append('<div class=\'wenzi\'><span class=\'sctp-txt\' >banner的图片:</span><input id=\'' + id + '_upload\' name=\'button\' type=\'submit\'  value=\'图片上传\' /><span class=\'dpl-tip-inline-warning\'>请至少上传一张图片</span></div>') .append(ul):
			$('<div></div>') .append('<div class=\'wenzi\'><span class=\'sctp-txt\' >banner的图片:</span><input id=\'' + id + '_upload\' name=\'button\' type=\'submit\'  value=\'图片上传\' /><span class=\'dpl-tip-inline-warning\'>请至少上传一张图片</span></div>') .append(ul) ;
		var object = $('<div class=\'z\' id=\'' + id + '_xianshi\'></div>') .append(div);
		$('.jinben') .append(object);
	}
	
	if (flashDetect()) {
		// 探测到已经安装了flash插件 则初始化上传按钮和提示
		// 初始化swfUpload组件
		$('#' + id + '_upload') .uploadify({
			// File Upload Settings
			'height': 30,
			'swf': '../commons/js/uploadify/uploadify.swf',
			'uploader': '../product/imageUp',
			'width': 100,
			'cancelImg': '../js/img/uploadify-cancel.png',
			'auto': true,
			'buttonText': '上传图片',
			file_size_limit: '100',
			file_queue_limit: 6,
			fileTypeExts: '*.gif;*.jpg;*.jpeg;*.png',
			file_types: '*.jpg;*.png;*.jpeg;',
			file_types_description: '*.jpg;*.jpeg;*.png;*.JPG;*.JPEG;*.PNG;',
			file_dialog_start_handler: fileDialogStart,
			file_queued_handler: fileQueued,
			file_queue_error_handler: fileQueueError,
			file_dialog_complete_handler: fileDialogComplete,
			upload_start_handler: uploadStart,
			upload_progress_handler: uploadProgress,
			upload_error_handler: uploadError,
			upload_success_handler: uploadSuccess,
			upload_complete_handler: uploadComplete,
			queue_complete_handler: queueComplete
		});
		
	} else {
		// 探测到没有flash支持，给出提示。
		$('.ifile') .html('<span class="no-flash-tip">' +
				'Hi，您的浏览器OUT了，它未安装新版的Flash Player，' +
				'<a href="http://get.adobe.com/flashplayer/" target="_blank">去安装>></a>' +
		'</span>');
	}
}
	
function initimg() {
	var ss = $('.yanse input:checked');
	if (ss.length > 0) {
		$.each(ss, function (i, item) {
			var value = item.value;
			var id = item.id;
			adduploadimg(value, id, test);
		});
	}
}
function changeimg(event){
	var obj = event.target;
	var id = obj.id;
	var value = obj.value;
	//if(obj.checked){		
		//adduploadimg(value,id,[]);
		adduploadimg(value,id,[]);
	//}else{
	//	var ss = document.getElementById("uploader_"+id);
	//	ss.parentNode.removeChild(ss);
	//}
}
function sameprice(event){
	var but = event.target;
	var name = $(but).attr("id");
	var obj=$(but).closest("table").find("[name='"+name+"']:text");

	if (but.checked){
		if(obj.length > 1){
			var price = obj[0].value;
			$(but).closest("table").find("[name='"+name+"']:text:gt(0)").val(price);
			$(but).closest("table").find("[name='"+name+"']:text:gt(0)").attr("readonly","readonly");
			$(obj[0]).bind("input propertychange", changeprice);
		}	
	}else{
		if(obj.length > 1){
			$(but).closest("table").find("[name='"+name+"']:text:gt(0)").removeAttr("readonly");
			$(obj[0]).unbind("input propertychange", changeprice);
		}
	}
}

function changeprice(event){
	var input = event.target,value = event.target.value;
	var name = $(input).attr("name");
	var obj=$(input).closest("table").find("[name='"+name+"']:text");
	if(obj.length > 1){
		var price = obj[0].value;
		$(input).closest("table").find("[name='"+name+"']:text:gt(0)").val(price);
	}
}



function changebox(){
	var measure = $("select[name='dealerProductPackage.measureid'] option:selected").text();
	var chilen = $(".chim").length;
	var chim;
	var chim2;
	var chim3;
	var chim4;
	if(chilen >= 1){
		$(".chim").each(function(e,n){
			if(e == 0){
				chim = $(this).find('input[type=\'checkbox\']');
				var title = $(this).children("div:eq(0)").attr("title");
				$(".cm1 eq["+e+"]").show();
				$(".cm1 span").text(title);
			}
			if(e == 1){
				chim2 = $(this).find('input[type=\'checkbox\']');
				var title = $(this).children("div:eq(0)").attr("title");
				$(".cm2 eq["+e+"]").show();
				$(".cm2 span").text(title);
			}
			if(e == 2){
				chim3 = $(this).find('input[type=\'checkbox\']');
				var title = $(this).children("div:eq(0)").attr("title");
				$(".cm3 eq["+e+"]").show();
				$(".cm3 span").text(title);
			}
			if(e == 3){
				chim4 = $(this).find('input[type=\'checkbox\']');
				var title = $(this).children("div:eq(0)").attr("title");
				$(".cm4 eq["+e+"]").show();
				$(".cm4 span").text(title);
			}
		});
	}else{
		chim = $(".chim input:checked");
	}
	var yanse = $(".yanse input:checked");
	var chimlen = chim.length;
	var chimlen2 = 0;
	if(typeof(chim2)!="undefined"){
		chimlen2 = chim2.length;
	}
	var chimlen3 = 0;
	if(typeof(chim3)!="undefined"){
		chimlen3 = chim3.length;
	}
	var chimlen4 = 0;
	if(typeof(chim4)!="undefined"){
		chimlen4 = chim4.length;
	}
	var yanselen = yanse.length;
	if(yanselen == 0 && chimlen == 0){
		$("#tb-speca-quotation").hide();
		return;
	}
	tableInfo = _fStoreTableInfo("tb-speca-quotation1");
	tableInfo1 = _fStoreTableInfo("tb-speca-quotation2");
	tableInfo2 = _fStoreTableInfo("tb-speca-quotation3");
	tiaoxingmainfo = _fStoreTableInfo("tb-tiaoxingma");
	tb2cInfo = _fStoreTableInfo("tb-b2c");
	$("#tb-speca-quotation1 tbody").remove();
	$("#tb-speca-quotation2 tbody").remove();
	$("#tb-speca-quotation3 tbody").remove();
	$("#tb-tiaoxingma tbody").remove();
	$("#skuCodeTable > .dpl-tip-inline-warning").empty().hide();
	//document.getElementById("same_price").checked = false;
	//$("#tb-speca-quotation").show();
	//$("#tb-tiaoxingma tbody").remove();
	//$("#tb-speca-quotation").show();	
	$("#tb-b2c tbody").remove();
	
	//$("#tb-speca-quotation1").append(tbody);
	//$("#tb-speca-quotation2").append(tbodyfut);
	//$("#tb-speca-quotation3").append(tbodycash);
	//$("#tb-tiaoxingma").append(tbody1);
	//$("#tb-b2c").append(tbodyb2c);
	
	$("input:checkbox[name='saleAttrvalch']").each(function(e,v){
		var valId = "saleAttrval"+v.id.substring(13,v.id.length);
		$(this).val($("#"+valId).val());
	});
	$("input:checkbox[name='buyAttrvalch']").each(function(e,v){
		var valId = "buyAttrval"+v.id.substring(12,v.id.length);
		$(this).val($("#"+valId).val());
	});

	if(yanselen > 0 && chimlen >0 && chimlen2 == 0){

		$.each(yanse,function(i,n){
			var tbody= $("<tbody></tbody>"),tbodyfut= $("<tbody></tbody>"),tbodycash= $("<tbody></tbody>"),tbody1 = $("<tbody></tbody>"),tbodyb2c=$("<tbody></tbody>");
			var tr= $(""),trfut = $(""),trcash = $(""),tr1 = $(""),trb2c=$("");
			$.each(chim,function(j,m){
				if(j == 0){
					tr = $("<tr></tr>") ;
					tr.append($("<td class='yan' id='"+ n.id +"' rowspan='"+ chimlen +"'>"+ n.value +"</td><td class='chi' id='"+ m.id +"'>"+ m.value +"</td>"));
					trfut= tr.clone();
					tr1 = tr.clone();
					trcash= tr.clone();
					trb2c = tr.clone(); 
					tr.append($(
							"<td><input type='hidden' name='buyVal' value='"+n.id+"'>" +
							"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
							"<input type='hidden' name='buyName' value='"+n.value+"'>" +
							"<input type='hidden' name='saleName' value='"+m.value+"'>" + 
							"<input class='pro_price' disabled='disabled' type='text'></td>"));
					trfut.append($(
							"<td><input class='pro_price' type='text' name='productPic'></td>" +										
							"<td><input   class='sugprice' name='rPrice' type='text' ></td>"));
					trcash.append($(
							"<td>" +
							"<input class='pro_price' type='text' name='supplierPic'></td>" +										
					""));
					tr1.append($("<td><input class='pro_price' type='text' name='skuCode'><input class='pro_price' type='hidden' name='skuCode'></td>" +
							"<td><div class='preview_fake'  id='-"+n.id+"-"+m.id+"_img' ></div></td>" +
							"<td><input class='length' name='length'  type='text'></td>" +
							"<td><input class='width' name='width'  type='text'></td>" +
							"<td><input class='height' name='height'  type='text'></td>" +
							"<td><input  class='weight' name='weight' type='text' >kg/<span class='danwei'>"+measure+"</span></td>"));
					//<input class='file'  id='-"+n.id+"-"+m.id+"_upload' name='button'   type='submit' />
					trb2c.append($(
							"<td><input   class='productCode' name='productCode' type='text' ></td>"+
							"<td><input class='sellCount' type='text' name='sellCount'></td>" +										
							"<td><input   class='unitPrice' name='unitPrice' type='text' ></td>"+
							"<td><input   class='domesticPrice' name='domesticPrice' type='text' ></td>" +
							"<td><input   class='bestoayPrice' name='bestoayPrice' type='text' ></td>" +
							"<td><input   class='customsSkuNumber' name='customsSkuNumber' type='text' ></td>"));
				}else{
					tr = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
							"<td><input type='hidden' name='buyVal' value='"+n.id+"'>" +
							"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
							"<input type='hidden' name='buyName' value='"+n.value+"'>" +
							"<input type='hidden' name='saleName' value='"+m.value+"'>" +
					"<input class='pro_price' type='text' disabled='disabled' ></td></tr>");
					trfut = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
							"<td>" +
							"<input class='pro_price' type='text' name='productPic'  ></td>" +
					"<td><input class='sugprice' name='rPrice' type='text' ></td></tr>");		
					trcash = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
							"<td>" +
							"<input class='pro_price' type='text' name='supplierPic'  ></td>" +
					"</tr>");		
					tr1 = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
							"<td><input class='pro_price' type='text' name='skuCode'><input class='pro_price' type='hidden' name='skuCode'></td>" +
							"<td><div class='preview_fake'  id='-"+n.id+"-"+m.id+"_img' ></div></td>" +
							"<td><input class='length' name='length' type='text'></td>" +
							"<td><input class='width' name='width' type='text'></td>" +
							"<td><input class='height' name='height' type='text'></td>" +
							"<td><input   class='weight' type='text' name='weight'>kg/<span class='danwei'>"+measure+"</span></td></tr>");
					//<input class='file'  id='-"+n.id+"-"+m.id+"_upload' name='button'   type='submit' />
					trb2c = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
							"<td><input   class='productCode' name='productCode' type='text' ></td>"+
							"<td><input class='sellCount' type='text' name='sellCount'></td>" +										
							"<td><input   class='unitPrice' name='unitPrice' type='text' ></td>"+
							"<td><input   class='domesticPrice' name='domesticPrice' type='text' ></td>" +
							"<td><input   class='bestoayPrice' name='bestoayPrice' type='text' ></td>" +
							"<td><input   class='customsSkuNumber' name='customsSkuNumber' type='text' ></td>");	
				}
				tbody.append(tr);
				tbodyfut.append(trfut);
				tbodycash.append(trcash);
				tbody1.append(tr1);
				tbodyb2c.append(trb2c);
			});
			$("#tb-speca-quotation1").append(tbody);
			$("#tb-speca-quotation2").append(tbodyfut);
			$("#tb-speca-quotation3").append(tbodycash);
			$("#tb-tiaoxingma").append(tbody1);
			$("#tb-b2c").append(tbodyb2c);
		});
		_Itiaoimg();
		_fShowTableInfo(tableInfo,"tb-speca-quotation1");
		_fShowTableInfo(tableInfo1,"tb-speca-quotation2");
		_fShowTableInfo(tableInfo2,"tb-speca-quotation3");
		_fShowTableInfo(tiaoxingmainfo,"tb-tiaoxingma");
		_fShowTableInfo(tb2cInfo,"tb-b2c");
		return;
	}
	if(yanselen > 0 && chimlen >0 && chimlen2 > 0 && chimlen3 == 0){

		$.each(yanse,function(i,n){
			var tbody= $("<tbody></tbody>"),tbodyfut= $("<tbody></tbody>"),tbodycash= $("<tbody></tbody>"),tbody1 = $("<tbody></tbody>"),tbodyb2c=$("<tbody></tbody>");
			var tr= $(""),trfut = $(""),trcash = $(""),tr1 = $(""),trb2c=$("");
			var trx = 0;
			$.each(chim,function(j,m){
				if(j == 0){
					
					$.each(chim2,function(k,l){
					tr = $("<tr></tr>") ;
					if(trx>0){
						tr.append($("<td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
								"<td class='chi1' id='"+ l.id +"'>"+ l.value +"</td>"));
					}
					if(trx == 0){
						tr.append($("<td class='yan' id='"+ n.id +"' rowspan='"+ chimlen * chimlen2 +"'>"+ n.value +"</td><td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
								"<td class='chi1' id='"+ l.id +"'>"+ l.value +"</td>"));
					}
					trx ++;
					trfut= tr.clone();
					tr1 = tr.clone();
					trcash= tr.clone();
					trb2c = tr.clone(); 
					tr.append($(
							"<td><input type='hidden' name='buyVal' value='"+n.id+"'>" +
							"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
							"<input type='hidden' name='saleVal2' value='"+l.id+"'>" +
							"<input type='hidden' name='buyName' value='"+n.value+"'>" +
							"<input type='hidden' name='saleName' value='"+m.value+"'>" + 
							"<input type='hidden' name='saleName2' value='"+l.value+"'>" + 
							"<input class='pro_price' disabled='disabled' type='text'></td>"));
					trfut.append($(
							"<td><input class='pro_price' type='text' name='productPic'></td>" +										
							"<td><input   class='sugprice' name='rPrice' type='text' ></td>"));
					trcash.append($(
							"<td>" +
							"<input class='pro_price' type='text' name='supplierPic'></td>" +										
					""));
					tr1.append($("<td><input class='pro_price' disabled='disabled'  type='text' name='skuCode'><input class='pro_price' type='hidden' name='skuCode'></td>" +
							"<td><div class='preview_fake'  id='-"+n.id+"-"+m.id+"-"+l.id+"_img' ></div></td>" +
							"<td><input class='length' name='length'  type='text'></td>" +
							"<td><input class='width' name='width'  type='text'></td>" +
							"<td><input class='height' name='height'  type='text'></td>" +
							"<td><input  class='weight' name='weight' type='text' >kg/<span class='danwei'>"+measure+"</span></td>"));
					//<input class='file'  id='-"+n.id+"-"+m.id+"-"+l.id+"_upload' name='button'   type='submit' />
					trb2c.append($(
							"<td><input   class='productCode' name='productCode' type='text' ></td>"+
							"<td><input class='sellCount' type='text' name='sellCount'></td>" +										
							"<td><input   class='unitPrice' name='unitPrice' type='text' ></td>"+
							"<td><input   class='domesticPrice' name='domesticPrice' type='text' ></td>" +
							"<td><input   class='bestoayPrice' name='bestoayPrice' type='text' ></td>" +
							"<td><input   class='customsSkuNumber' name='customsSkuNumber' type='text' ></td>"));
					tbody.append(tr);
					tbodyfut.append(trfut);
					tbodycash.append(trcash);
					tbody1.append(tr1);
					tbodyb2c.append(trb2c);
					});
				}else{
					$.each(chim2,function(k,l){
					tr = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
							"<td class='chi1' id='"+ l.id +"'>"+ l.value +"</td>" +
							"<td><input type='hidden' name='buyVal' value='"+n.id+"'>" +
							"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
							"<input type='hidden' name='saleVal2' value='"+l.id+"'>" +
							"<input type='hidden' name='buyName' value='"+n.value+"'>" +
							"<input type='hidden' name='saleName' value='"+m.value+"'>" +
							"<input type='hidden' name='saleName2' value='"+l.value+"'>" +
					"<input class='pro_price' type='text' disabled='disabled' ></td></tr>");
					trfut = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
							"<td class='chi1' id='"+ l.id +"'>"+ l.value +"</td>" +
							"<td>" +
							"<input class='pro_price' type='text' name='productPic'  ></td>" +
					"<td><input class='sugprice' name='rPrice' type='text' ></td></tr>");		
					trcash = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
							"<td class='chi1' id='"+ l.id +"'>"+ l.value +"</td>" +
							"<td>" +
							"<input class='pro_price' type='text' name='supplierPic'  ></td>" +
					"</tr>");		
					tr1 = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
							"<td class='chi1' id='"+ l.id +"'>"+ l.value +"</td>" +
							"<td><input class='pro_price' disabled='disabled' type='text' name='skuCode'><input class='pro_price' type='hidden' name='skuCode'></td>" +
							"<td><div class='preview_fake'  id='-"+n.id+"-"+m.id+"-"+l.id+"_img' ></div></td>" +
							"<td><input class='length' name='length' type='text'></td>" +
							"<td><input class='width' name='width' type='text'></td>" +
							"<td><input class='height' name='height' type='text'></td>" +
							"<td><input   class='weight' type='text' name='weight'>kg/<span class='danwei'>"+measure+"</span></td></tr>");
					//<input class='file'  id='-"+n.id+"-"+m.id+"-"+l.id+"_upload' name='button'   type='submit' />
					trb2c = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
							"<td class='chi1' id='"+ l.id +"'>"+ l.value +"</td>" +
							"<td><input   class='productCode' name='productCode' type='text' ></td>"+
							"<td><input class='sellCount' type='text' name='sellCount'></td>" +										
							"<td><input   class='unitPrice' name='unitPrice' type='text' ></td>"+
							"<td><input   class='domesticPrice' name='domesticPrice' type='text' ></td>" +
							"<td><input   class='bestoayPrice' name='bestoayPrice' type='text' ></td>" +
							"<td><input   class='customsSkuNumber' name='customsSkuNumber' type='text' ></td>");
					tbody.append(tr);
					tbodyfut.append(trfut);
					tbodycash.append(trcash);
					tbody1.append(tr1);
					tbodyb2c.append(trb2c);
					});
				}
			});
			$("#tb-speca-quotation1").append(tbody);
			$("#tb-speca-quotation2").append(tbodyfut);
			$("#tb-speca-quotation3").append(tbodycash);
			$("#tb-tiaoxingma").append(tbody1);
			$("#tb-b2c").append(tbodyb2c);
		});
		_fShowTableInfo(tableInfo,"tb-speca-quotation1");
		_fShowTableInfo(tableInfo1,"tb-speca-quotation2");
		_fShowTableInfo(tableInfo2,"tb-speca-quotation3");
		_fShowTableInfo(tiaoxingmainfo,"tb-tiaoxingma");
		_fShowTableInfo(tb2cInfo,"tb-b2c");
		_Itiaoimg();
		return;
	}
	if(yanselen > 0 && chimlen >0 && chimlen2 > 0 && chimlen3 > 0 && chimlen4 == 0){

		$.each(yanse,function(i,n){
			var tbody= $("<tbody></tbody>"),tbodyfut= $("<tbody></tbody>"),tbodycash= $("<tbody></tbody>"),tbody1 = $("<tbody></tbody>"),tbodyb2c=$("<tbody></tbody>");
			var tr= $(""),trfut = $(""),trcash = $(""),tr1 = $(""),trb2c=$("");
			$.each(chim,function(j,m){
					$.each(chim2,function(k,l){
						$.each(chim3,function(q,s){
							tr = $("<tr></tr>");
							if(j == 0 && k == 0 && q == 0){
								tr.append($("<td class='yan' id='"+ n.id +"' rowspan='"+ chimlen * chimlen2 * chimlen3 +"'>"+ n.value +"</td>" +
										"<td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
										"<td class='chi1' id='"+ l.id +"'>"+ l.value +"</td>" +
										"<td class='chi2' id='"+ s.id +"'>"+ s.value +"</td>"));
							}else{
								tr.append($("<td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
										"<td class='chi1' id='"+ l.id +"'>"+ l.value +"</td>" +
										"<td class='chi2' id='"+ s.id +"'>"+ s.value +"</td>"));
							}
							trfut= tr.clone();
							tr1 = tr.clone();
							trcash= tr.clone();
							trb2c = tr.clone(); 
							
							tr.append($(
								"<td><input type='hidden' name='buyVal' value='"+n.id+"'>" +
								"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
								"<input type='hidden' name='saleVal2' value='"+l.id+"'>" +
								"<input type='hidden' name='saleVal3' value='"+s.id+"'>" +
								"<input type='hidden' name='buyName' value='"+n.value+"'>" +
								"<input type='hidden' name='saleName' value='"+m.value+"'>" + 
								"<input type='hidden' name='saleName2' value='"+l.value+"'>" + 
								"<input type='hidden' name='saleName3' value='"+s.value+"'>" + 
								"<input class='pro_price' disabled='disabled' type='text'></td>"));
							trfut.append($(
								"<td><input class='pro_price' type='text' name='productPic'></td>" +										
								"<td><input   class='sugprice' name='rPrice' type='text' ></td>"));
							trcash.append($("<td><input class='pro_price' type='text' name='supplierPic'></td>"));
							tr1.append($("<td><input class='pro_price' disabled='disabled'  type='text' name='skuCode'><input class='pro_price' type='hidden' name='skuCode'></td>" +
								"<td><div class='preview_fake'  id='-"+n.id+"-"+m.id+"-"+l.id+"-"+s.id+"_img' ></div></td>" +
								"<td><input class='length' name='length'  type='text'></td>" +
								"<td><input class='width' name='width'  type='text'></td>" +
								"<td><input class='height' name='height'  type='text'></td>" +
								"<td><input  class='weight' name='weight' type='text' >kg/<span class='danwei'>"+measure+"</span></td>"));
							//<input class='file'  id='-"+n.id+"-"+m.id+"-"+l.id+"-"+s.id+"_upload' name='button'   type='submit' />
							trb2c.append($(
								"<td><input   class='productCode' name='productCode' type='text' ></td>"+
								"<td><input class='sellCount' type='text' name='sellCount'></td>" +										
								"<td><input   class='unitPrice' name='unitPrice' type='text' ></td>"+
								"<td><input   class='domesticPrice' name='domesticPrice' type='text' ></td>" +
								"<td><input   class='bestoayPrice' name='bestoayPrice' type='text' ></td>" +
								"<td><input   class='customsSkuNumber' name='customsSkuNumber' type='text' ></td>"));
							tbody.append(tr);
							tbodyfut.append(trfut);
							tbodycash.append(trcash);
							tbody1.append(tr1);
							tbodyb2c.append(trb2c);
						});
					});
				});
			$("#tb-speca-quotation1").append(tbody);
			$("#tb-speca-quotation2").append(tbodyfut);
			$("#tb-speca-quotation3").append(tbodycash);
			$("#tb-tiaoxingma").append(tbody1);
			$("#tb-b2c").append(tbodyb2c);
		});
		_fShowTableInfo(tableInfo,"tb-speca-quotation1");
		_fShowTableInfo(tableInfo1,"tb-speca-quotation2");
		_fShowTableInfo(tableInfo2,"tb-speca-quotation3");
		_fShowTableInfo(tiaoxingmainfo,"tb-tiaoxingma");
		_fShowTableInfo(tb2cInfo,"tb-b2c");
		_Itiaoimg();
		return;
	}
	if(yanselen > 0 && chimlen >0 && chimlen2 > 0 && chimlen3 > 0 && chimlen4 > 0){

		$.each(yanse,function(i,n){
			var tbody= $("<tbody></tbody>"),tbodyfut= $("<tbody></tbody>"),tbodycash= $("<tbody></tbody>"),tbody1 = $("<tbody></tbody>"),tbodyb2c=$("<tbody></tbody>");
			var tr= $(""),trfut = $(""),trcash = $(""),tr1 = $(""),trb2c=$("");
			$.each(chim,function(j,m){
					$.each(chim2,function(k,l){
						$.each(chim3,function(q,s){
							$.each(chim4,function(r,t){
								tr = $("<tr></tr>");
								if(j == 0 && k == 0 && q == 0 && r == 0){
									tr.append($("<td class='yan' id='"+ n.id +"' rowspan='"+ chimlen * chimlen2 * chimlen3 * chimlen4 +"'>"+ n.value +"</td>" +
											"<td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
											"<td class='chi1' id='"+ l.id +"'>"+ l.value +"</td>" +
											"<td class='chi2' id='"+ s.id +"'>"+ s.value +"</td>" +
											"<td class='chi3' id='"+ t.id +"'>"+ t.value +"</td>"));
								}else{
									tr.append($("<td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
											"<td class='chi1' id='"+ l.id +"'>"+ l.value +"</td>" +
											"<td class='chi2' id='"+ s.id +"'>"+ s.value +"</td>" +
											"<td class='chi3' id='"+ t.id +"'>"+ t.value +"</td>"));
								}
								trfut= tr.clone();
								tr1 = tr.clone();
								trcash= tr.clone();
								trb2c = tr.clone(); 
								
								tr.append($(
									"<td><input type='hidden' name='buyVal' value='"+n.id+"'>" +
									"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
									"<input type='hidden' name='saleVal2' value='"+l.id+"'>" +
									"<input type='hidden' name='saleVal3' value='"+s.id+"'>" +
									"<input type='hidden' name='saleVal4' value='"+t.id+"'>" +
									"<input type='hidden' name='buyName' value='"+n.value+"'>" +
									"<input type='hidden' name='saleName' value='"+m.value+"'>" + 
									"<input type='hidden' name='saleName2' value='"+l.value+"'>" + 
									"<input type='hidden' name='saleName3' value='"+s.value+"'>" + 
									"<input type='hidden' name='saleName4' value='"+t.value+"'>" + 
									"<input class='pro_price' disabled='disabled' type='text'></td>"));
								trfut.append($(
									"<td><input class='pro_price' type='text' name='productPic'></td>" +										
									"<td><input   class='sugprice' name='rPrice' type='text' ></td>"));
								trcash.append($("<td><input class='pro_price' type='text' name='supplierPic'></td>"));
								tr1.append($("<td><input class='pro_price' disabled='disabled'  type='text' name='skuCode'><input class='pro_price' type='hidden' name='skuCode'></td>" +
									"<td><div class='preview_fake'  id='-"+n.id+"-"+m.id+"-"+l.id+"-"+s.id+"-"+t.id+"_img' ></div></td>" +
									"<td><input class='length' name='length'  type='text'></td>" +
									"<td><input class='width' name='width'  type='text'></td>" +
									"<td><input class='height' name='height'  type='text'></td>" +
									"<td><input  class='weight' name='weight' type='text' >kg/<span class='danwei'>"+measure+"</span></td>"));
								//<input class='file'  id='-"+n.id+"-"+m.id+"-"+l.id+"-"+s.id+"-"+t.id+"_upload' name='button'   type='submit' />
								trb2c.append($(
									"<td><input   class='productCode' name='productCode' type='text' ></td>"+
									"<td><input class='sellCount' type='text' name='sellCount'></td>" +										
									"<td><input   class='unitPrice' name='unitPrice' type='text' ></td>"+
									"<td><input   class='domesticPrice' name='domesticPrice' type='text' ></td>" +
									"<td><input   class='bestoayPrice' name='bestoayPrice' type='text' ></td>" +
									"<td><input   class='customsSkuNumber' name='customsSkuNumber' type='text' ></td>"));
								tbody.append(tr);
								tbodyfut.append(trfut);
								tbodycash.append(trcash);
								tbody1.append(tr1);
								tbodyb2c.append(trb2c);

							});
						});
					});
				});
			$("#tb-speca-quotation1").append(tbody);
			$("#tb-speca-quotation2").append(tbodyfut);
			$("#tb-speca-quotation3").append(tbodycash);
			$("#tb-tiaoxingma").append(tbody1);
			$("#tb-b2c").append(tbodyb2c);
		});
		_fShowTableInfo(tableInfo,"tb-speca-quotation1");
		_fShowTableInfo(tableInfo1,"tb-speca-quotation2");
		_fShowTableInfo(tableInfo2,"tb-speca-quotation3");
		_fShowTableInfo(tiaoxingmainfo,"tb-tiaoxingma");
		_fShowTableInfo(tb2cInfo,"tb-b2c");
		_Itiaoimg();
		return;
	}
}

function _Itiaoimg(){
	$("#tb-tiaoxingma input.file").each(function(i,item){
		var id = item.id;
		$("#"+id).uploadify({
        	'height': 24,
            'swf': '../commons/js/uploadify/uploadify.swf',
            'uploader': '../product/imageUp?platform_session_id='+sessionId,
            'width': 63,
            'cancelImg': '../commons/js/uploadify/uploadify-cancel.png',
            'multi ':false,
            'auto': true,
            'buttonText': '上传',
            file_size_limit: "1024K",
            fileTypeExts: '*.gif;*.jpg;*.jpeg;*.png',
            file_types: "*.jpg;*.png;*.jpeg;",
            file_types_description: "*.jpg;*.jpeg;*.png;*.JPG;*.JPEG;*.PNG;",
            
            upload_start_handler: uploadStart1,
            upload_success_handler: uploadSuccess1,
        });
	});
}
//储存表格价格和数量
function _fStoreTableInfo(tablename){
var g_table=[];
var obj=$("#"+tablename+" tbody");
$.each(obj,function(i,item){
var color_id = $(item).find(".yan").attr("id");
$.each($(item).children(),function(j,item1){
var chi_id = $(item1).find(".chi").attr("id");
var chi_id2 = $(item1).find(".chi1").attr("id");
var chi_id3 = $(item1).find(".chi2").attr("id");
var chi_id4 = $(item1).find(".chi3").attr("id");
var price=$(item1).find(".pro_price").val();
var preview_fake=$(item1).find(".preview_fake").html();
var jyprice = $(item1).find(".sugprice").val();
var length = $(item1).find(".length").val();
var width = $(item1).find(".width").val();
var height = $(item1).find(".height").val();
var weight = $(item1).find(".weight").val();
//var supplierPrice = $(item1).find(".supplierPrice").val();

var sellCount = $(item1).find(".sellCount").val();
var unitPrice = $(item1).find(".unitPrice").val();
var domesticPrice = $(item1).find(".domesticPrice").val();
var bestoayPrice = $(item1).find(".bestoayPrice").val();
var customsSkuNumber = $(item1).find(".customsSkuNumber").val();
var productCode = $(item1).find(".productCode").val();

var tr = [];
var tdchi = [];
if(typeof(chi_id2)!="undefined"&&typeof(chi_id3)!="undefined"&&typeof(chi_id4)!="undefined"){
	if(tablename=="tb-speca-quotation1" || tablename=="tb-speca-quotation2" || tablename=="tb-speca-quotation3"){
		tr.push(color_id);
		tdchi.push(chi_id);
		if(typeof(chi_id2)!="undefined"){
			tdchi.push(chi_id2);
		}
		if(typeof(chi_id3)!="undefined"){
			tdchi.push(chi_id3);
		}
		if(typeof(chi_id4)!="undefined"){
			tdchi.push(chi_id4);
		}
		tr.push(tdchi);
		tr.push(price);
		tr.push(jyprice);
	}else if(tablename=="tb-tiaoxingma"){
		tr.push(color_id);
		tdchi.push(chi_id);
		if(typeof(chi_id2)!="undefined"){
			tdchi.push(chi_id2);
		}
		if(typeof(chi_id3)!="undefined"){
			tdchi.push(chi_id3);
		}
		if(typeof(chi_id4)!="undefined"){
			tdchi.push(chi_id4);
		}
		tr.push(tdchi);
		tr.push(price);
		tr.push(preview_fake);
		tr.push(length);
		tr.push(width);
		tr.push(height);
		tr.push(weight);
	}else{
		tr.push(color_id);
		tdchi.push(chi_id);
		if(typeof(chi_id2)!="undefined"){
			tdchi.push(chi_id2);
		}
		if(typeof(chi_id3)!="undefined"){
			tdchi.push(chi_id3);
		}
		if(typeof(chi_id4)!="undefined"){
			tdchi.push(chi_id4);
		}
		tr.push(tdchi);
		tr.push(sellCount);
		tr.push(unitPrice);
		tr.push(domesticPrice);
		tr.push(bestoayPrice);
		tr.push(customsSkuNumber);
		tr.push(productCode);
	}
}
//tr.push(supplierPrice);
g_table.push(tr);
});
});
return g_table;
}

//遍历并显示表格价格和数量
function _fShowTableInfo(tableInfo,tablename){
	var obj=$("#"+tablename+" tbody");
	$.each(tableInfo,function(k){
		$.each(obj,function(i,item){
			var color_id = $(item).find(".yan").attr("id");
			if(color_id == tableInfo[k][0]){
				$.each($(item).children(),function(j,item1){
					var chi_id = $(item1).find(".chi").attr("id");
					var chi_id2 = $(item1).find(".chi1").attr("id");
					var chi_id3 = $(item1).find(".chi2").attr("id");
					var chi_id4 = $(item1).find(".chi3").attr("id");
					if(typeof(chi_id2)=="undefined"&&typeof(chi_id3)=="undefined"&&typeof(chi_id4)=="undefined"){
						if(chi_id == tableInfo[k][1][0]){
							$(item1).find(".pro_price").val(tableInfo[k][2]);
							if(tablename=="tb-tiaoxingma" && tableInfo[k][2]!=null&& ""!=tableInfo[k][2]){
								$(item1).find(".pro_price").attr('disabled','disabled');
							}
							$(item1).find(".preview_fake").html(tableInfo[k][3]);
							$(item1) .find('.sugprice') .val(tableInfo[k][3]);
							$(item1) .find('.length') .val(tableInfo[k][4]);
							$(item1) .find('.width') .val(tableInfo[k][5]);
							$(item1) .find('.height') .val(tableInfo[k][6]);
							$(item1) .find('.weight') .val(tableInfo[k][7]);

							
							$(item1) .find('.sellCount') .val(tableInfo[k][2]);
							$(item1) .find('.unitPrice') .val(tableInfo[k][3]);
							$(item1) .find('.domesticPrice') .val(tableInfo[k][4]);
							$(item1) .find('.bestoayPrice') .val(tableInfo[k][5]);
							$(item1) .find('.customsSkuNumber') .val(tableInfo[k][6]);
							$(item1) .find('.productCode') .val(tableInfo[k][7]);
							//$(item1) .find('.supplierPrice'). val(tableInfo[k][4]);
						}
					}
					if(typeof(chi_id2)!="undefined"&&typeof(chi_id3)=="undefined"&&typeof(chi_id4)=="undefined"){
						if(chi_id == tableInfo[k][1][0] && chi_id2 == tableInfo[k][1][1]){
							$(item1).find(".pro_price").val(tableInfo[k][2]);
							$(item1).find(".preview_fake").html(tableInfo[k][3]);
							$(item1) .find('.sugprice') .val(tableInfo[k][3]);
							$(item1) .find('.length') .val(tableInfo[k][4]);
							$(item1) .find('.width') .val(tableInfo[k][5]);
							$(item1) .find('.height') .val(tableInfo[k][6]);
							$(item1) .find('.weight') .val(tableInfo[k][7]);

							
							$(item1) .find('.sellCount') .val(tableInfo[k][2]);
							$(item1) .find('.unitPrice') .val(tableInfo[k][3]);
							$(item1) .find('.domesticPrice') .val(tableInfo[k][4]);
							$(item1) .find('.bestoayPrice') .val(tableInfo[k][5]);
							$(item1) .find('.customsSkuNumber') .val(tableInfo[k][6]);
							$(item1) .find('.productCode') .val(tableInfo[k][7]);
							//$(item1) .find('.supplierPrice'). val(tableInfo[k][4]);
						}
					}
					if(typeof(chi_id2)!="undefined"&&typeof(chi_id3)!="undefined"&&typeof(chi_id4)=="undefined"){
						if(chi_id == tableInfo[k][1][0] && chi_id2 == tableInfo[k][1][1] && chi_id3 == tableInfo[k][1][2]){
							$(item1).find(".pro_price").val(tableInfo[k][2]);
							$(item1).find(".preview_fake").html(tableInfo[k][3]);
							$(item1) .find('.sugprice') .val(tableInfo[k][3]);
							$(item1) .find('.length') .val(tableInfo[k][4]);
							$(item1) .find('.width') .val(tableInfo[k][5]);
							$(item1) .find('.height') .val(tableInfo[k][6]);
							$(item1) .find('.weight') .val(tableInfo[k][7]);

							
							$(item1) .find('.sellCount') .val(tableInfo[k][2]);
							$(item1) .find('.unitPrice') .val(tableInfo[k][3]);
							$(item1) .find('.domesticPrice') .val(tableInfo[k][4]);
							$(item1) .find('.bestoayPrice') .val(tableInfo[k][5]);
							$(item1) .find('.customsSkuNumber') .val(tableInfo[k][6]);
							$(item1) .find('.productCode') .val(tableInfo[k][7]);
							//$(item1) .find('.supplierPrice'). val(tableInfo[k][4]);
						}
					}
					if(typeof(chi_id2)!="undefined"&&typeof(chi_id3)!="undefined"&&typeof(chi_id4)!="undefined"){
						if(chi_id == tableInfo[k][1][0] && chi_id2 == tableInfo[k][1][1] && chi_id3 == tableInfo[k][1][2] && chi_id4 == tableInfo[k][1][3]){
							$(item1).find(".pro_price").val(tableInfo[k][2]);
							$(item1).find(".preview_fake").html(tableInfo[k][3]);
							$(item1) .find('.sugprice') .val(tableInfo[k][3]);
							$(item1) .find('.length') .val(tableInfo[k][4]);
							$(item1) .find('.width') .val(tableInfo[k][5]);
							$(item1) .find('.height') .val(tableInfo[k][6]);
							$(item1) .find('.weight') .val(tableInfo[k][7]);

							
							$(item1) .find('.sellCount') .val(tableInfo[k][2]);
							$(item1) .find('.unitPrice') .val(tableInfo[k][3]);
							$(item1) .find('.domesticPrice') .val(tableInfo[k][4]);
							$(item1) .find('.bestoayPrice') .val(tableInfo[k][5]);
							$(item1) .find('.customsSkuNumber') .val(tableInfo[k][6]);
							$(item1) .find('.productCode') .val(tableInfo[k][7]);
							//$(item1) .find('.supplierPrice'). val(tableInfo[k][4]);
						}
					}
				});
			}
		});
	});
}


function checkRadio(){
	var  check_count = $("#pic_count").attr("checked");

	if(check_count == 'checked'){
		$('.tq2:not(:eq(0))') .show();
		$('.g') .hide();
	}else{
		$('.tq2:not(:eq(0))').hide();
		$('.g') .show();
	}

}

function reloadOpennerPage(){
	var elnode = window.opener.document.getElementById("pageContext"); 
	var page = $(elnode).val();
	window.opener.location.href="javascript:clickSubmit("+page+")";

	window.opener=null;
	window.open('','_self');
	window.close();

	
	
}


function trasform(){
	var that = $(this);
	var url = that.attr("src").replace("/p2/","/");
	
//	var image = new Image();
//	image.src = url;
	
	$("#lightbox").show();
	$("#lightboxOverlay").show();
	$("#lightbox").css("top",Math.max(document.documentElement.scrollTop,document.body.scrollTop)+200);
	$("#lightboxOverlay").css("height",$(document).height());
	$(".lb-image").attr("src",CONTEXTPATH+"/commons/images/loading.gif");
	$(".lb-image").css("width","32px");
	 $(".lb-image").css("height","32px");
	$(".lb-image").css("margin","160px auto auto");

	var image = new Image();	

	image.onload = function(){
		$(".lb-image").attr("src",url);
		$(".lb-image").css("margin","auto");
		$(".lb-number").text(image.width+"*"+image.height);
		 $(".lb-image").css("width",image.width);
		 $(".lb-image").css("height",image.height);
		 $(".lb-outerContainer").css("width",image.width + 8);
		 $(".lb-outerContainer").css("height",image.height + 8);
		 $(".lb-dataContainer").css("width",image.width + 8);

	};
	
	image.src = url;
//	image.onreadystatechange=function(){
//		 if (image.readyState=="complete") {
//	image.onload
//	if(image.height>0){
//		$(".lb-number").text(image.width+"*"+image.height);
//		 $(".lb-image").css("width",image.width);
//		 $(".lb-image").css("height",image.height);
//		 $(".lb-outerContainer").css("width",image.width + 8);
//		 $(".lb-outerContainer").css("height",image.height + 8);
//		 $(".lb-dataContainer").css("width",image.width + 8);
//	}else{
//		$(".lb-number").text("300*300");
//		 $(".lb-image").css("width","300px");
//		 $(".lb-image").css("height","300px");
//		 $(".lb-outerContainer").css("width","308px");
//		 $(".lb-outerContainer").css("height","308px");
//		 $(".lb-dataContainer").css("width","308px");
//	}
			 
			
//		 }
//	}
}

var i=0;
var j=0;
var m=0;
$(document).ready(function(){
	
	$("#addTable").click(function(){
		
   		var tr="<tr><td><input type='text' id='attrOrd"+i+"' name='attrOrd' value='"+i+"' style='width: 30px;'/><input type='hidden' name='attrRows' value='"+i+"'/></td>"+
   		"<td><select name='type"+i+"'><option value='3'>文本</option><option value='1'>多选框</option></select></td>"+
   		"<td><input type='text' id='attrNm"+i+"' name='attrName' style='width: 80px;'/></td>"+
   		"<td id='addAttrvals"+i+"'><img src='../commons/images/img_+bg.jpg' onclick='addAttrval("+i+")'></td>"+
   		"<td><input type='button' onclick='delAttr(this)' value='删除'/></td></tr>";
   		$("#tbl").append(tr);
		i++;
	});
});
function addAttrval(obj){
	var tr = $("<input type='text' name='attrval"+obj+"' id='attrval"+j+"' style='width: 100px;'/><img src='../commons/images/img_23.png' id='img"+j+"' onclick='delAttrval("+j+")'>");
	var id = "#addAttrvals"+obj;
	$(id).append(tr);
	j++;
}
function delAttr(obj){
	document.getElementById("tbl").deleteRow(obj.parentElement.parentElement.rowIndex);
}
function delAttrval(obj){
	document.getElementById("attrval"+obj).remove();
	document.getElementById("img"+obj).remove();
}
function addBuyAttrval(){
	//var nums = $(".yanse2 input[type=\'checkbox\']:checked").length;
	var tr = $("<input type='checkbox' checked='checked' disabled='disabled' name='buyAttrvalch' id='buyAttrvalch"+m+"' alt='0'/><input type='text' name='buyAttrval' alt='"+m+"' id='buyAttrval"+m+"' style='width: 100px;'/>");
	var id = "#addBuyAttrvals";
	$(id).append(tr);
	m++;
}
function delBuyAttrval(obj){
	var obj = $("input[name='buyAttrval']").length;
	$("input[name='buyAttrval']").last().remove();
	var x = obj-1;
	var ss = document.getElementById("buyAttrval"+x+"_xianshi");
	ss.parentNode.removeChild(ss);
	changebox();
}
function addSaleAttrval(obj){
	var num1 = $("#addSaleAttrvals"+obj+" input[name='saleAttrval"+obj+"']").length;
	var tr = $("<input type='checkbox' checked='checked' disabled='disabled' name='saleAttrvalch' id='saleAttrvalch"+obj+""+num1+"' alt='0'/><input type='text' name='saleAttrval"+obj+"' alt='0' id='saleAttrval"+obj+""+num1+"' style='width: 100px;'/>");
	var id = "#addSaleAttrvals"+obj;
	$(id).append(tr);
}
function delSaleAttrval(obj){
	$("input[name='saleAttrval"+obj+"']").last().remove();
	$("input[id='saleAttrvalch"+obj+"']").last().remove();
	//document.getElementById("saleAttrval"+obj).remove();
	//document.getElementById("saleImg"+obj).remove();
	//document.getElementById("saleAttrvalch"+obj).remove();
}