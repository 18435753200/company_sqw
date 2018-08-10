var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {
	$("#AddMemberBut").on("click", AddMember);
	$("#accessMode").on("click", AddWay);
	$("#AddChannelBut").on("click", AddChannel);
	$("#AddBrandBut").on("click", AddBrand);
	$(".setdel ul li").live("click", del);
	$("#compatibleID").hide();
	$("#BrandList a").live("click", function() {
	$(this).toggleClass("active");
	});
	$("#skus").on("click", addSkus);
	$("#selectedSkus ul li").live("click", del);
	$("#areas").on("click", addAreas);
	$("#selectedArea ul li").live("click", del);
	$("#sorts").on("click", addSorts);
	$("#selectedSorts ul li").live("click", del);
	
	$("input[name=sendtype]").live("change",clearCondition);
	$("input[name=countLadder]").live("change",clearCondition1);
	
	$("#confirmButton").on("click",saveRule);
	
	$(".order-it1").click(function() {
		var value = $("input[name='fullcut']:checked").val();
		$("#div9").find('div').each(function(){
			$(this).hide();
		});
		if(value == "1") {//普通满减
			$("#fullcutResult").show();
			$("#stepsReduce").show();
			$("#stepsReduce").find('div').each(function(){
				$(this).show();
			});
		} else if (value == "2") {//等比满减
			$("#fullcutResult").show();
			$("#stepsReduce").hide();
		}
	
	});
	$("#platform").change(function(){
		var platform = $("#platform").val();
		if(platform == 1){
			$("#compatibleID").show();
		}else{
			$("#compatibleID").hide();
		}
	});
	$(".order-it2").click(function() {
		var value = $("input[name='fullsend']:checked").val();
		$("#div9").find('div').each(function(){
			$(this).hide();
		});
		var ruleTerm = $("#ruleTerm").val();
		if(ruleTerm == 2){
			$("#repeatesendcoupons").show();
			$("#repeatesendcoupons").find('div').each(function(){
				$(this).show();
			});
			$("#couponList").hide();
			if(value == 1){
				$("#notRepeateSendCoupon").show();
				$("#notRepeateSendCoupon").find('div').each(function(){
					$(this).show();
				});
				$("#couponList1").hide();
			}
			if(value == 2){
				$("#notRepeateSendCoupon").hide();
			}
		}
		if(ruleTerm == 4){
			
			$("#repeatesendgifts").show();
			$("#repeatesendgifts").find('div').each(function(){
				$(this).show();
			});
			$("#giftList").hide();
			if(value == 1) {
				$("#notRepeateSendGifts").show();
				$("#notRepeateSendGifts").find('div').each(function(){
					$(this).show();
				});
				$("#giftList1").hide();
			}
		}
	});		
	$(".order-it3").click(function() {
		var value = $("input[name='fullsend']:checked").val();
		$("#div9").find('div').each(function(){
			$(this).hide();
		});
		var ruleTerm = $("#ruleTerm").val();
		if(ruleTerm == 4){
			$("#repeatesendgifts").show();
			$("#repeatesendgifts").find('div').each(function(){
				$(this).show();
			});
			$("#giftList").hide();
			if(value == 1 || value == 3){
				$("#notRepeateSendGifts").show();
				$("#notRepeateSendGifts").find('div').each(function(){
					$(this).show();
				});
				$("#giftList1").hide();
			}
			if(value == 2 || value == 4){
				$("#notRepeateSendGifts").hide();
			}
		}
		if(ruleTerm == 2){
			$("#repeatesendcoupons").show();
			$("#repeatesendcoupons").find('div').each(function(){
				$(this).show();
			});
			$("#couponList").hide();
			if(value == 3){
				$("#notRepeateSendCoupon").show();
				$("#notRepeateSendCoupon").find('div').each(function(){
					$(this).show();
				});
				$("#couponList1").hide();
			}
			if(value == 4){
				$("#notRepeateSendCoupon").hide();
			}
		}
	});
	
	$(".chooseCommodity").live("click",function(){
		var id =$(this).attr("id");
		document.getElementById("selectCommodityNum").value = id;
		findCommodity1(1);
		$("#giftList").show();
	});
	$(".chooseCommodity1").live("click",function(){
		var id =$(this).attr("id");
		document.getElementById("selectCommodityNum1").value = id;
		findCommodity2(1);
		$("#giftList1").show();
	});
	$(".chooseCoupons").live("click",function(){
		var id =$(this).attr("id");
		document.getElementById("selectCouponNum").value = id;
		findCoupons(1);
		$("#couponList").show();
	});
	$(".chooseCoupons1").live("click",function(){
		var id =$(this).attr("id");
		document.getElementById("selectCouponNum1").value = id;
		findCoupons1(1);
		$("#couponList1").show();
	});
	//$("#findCoupons").on("click",findCoupons);	
	//$("#findCoupons1").on("click",findCoupons1);	
	$("#appendCoupon").on("click",appendCoupon);
	$("#appendCoupon1").on("click",appendCoupon1);
	$("#addCoupon").on("click",addCoupon);
	$("#addCoupon1").on("click",addCoupon1);
	$("#appendGift").on("click",appendGift);
	$("#addGift").on("click",addGift);
	$("#addDetail").on("click",addDetail);
	$("#appendGift1").on("click",appendGift1);
	$("#addGift1").on("click",addGift1);
});

function clearCondition(){
	$("#list2 tr").each(function(){
		var len = $(this).attr("id");
		if(len > 0){
			$(this).remove();
		}
	});
	$("#couponList1").hide();
}

function clearCondition1(){
	$("#list4 tr").each(function(){
		var len = $(this).attr("id");
		if(len > 0){
			$(this).remove();
		}
	});
	$("#giftList1").hide();
}

//添加选中的优惠券   不等比
function appendCoupon1(){
	$("#couponsList1").find('input').each(function(){
		var checked = $(this).attr("checked");
		if(checked == "checked"){
			var couponName = $(this).parent().next().text();
			var couponRuleId = $(this).attr("id");
			var couponRuleName = $(this).parent().next().next().text();
			var rowNumber = $("#selectCouponNum1").val();
			$("#list2").find('tr').each(function(){
				var rowId=$(this).attr("id");
				if(rowId == rowNumber) {
					$(this).children().eq(5).text(couponName);
					$(this).children().eq(6).text(couponRuleName);
					$(this).children().eq(7).children().eq(0).val(couponRuleId);
				}
			});
			$("#couponList1").hide();
		}	
	});
}

//添加选中的优惠券   等比阶梯
function appendCoupon(){
	$("#couponsList").find('input').each(function(){
		var checked = $(this).attr("checked");
		if(checked == "checked"){
			var couponName = $(this).parent().next().text();
			var couponRuleId = $(this).attr("id");
			var couponRuleName = $(this).parent().next().next().text();
			var rowNumber = $("#selectCouponNum").val();
			$("#list1").find('tr').each(function(){
				var rowId=$(this).attr("id");
				if(rowId == rowNumber) {
					$(this).children().eq(1).text(couponName);
					$(this).children().eq(1).next().text(couponRuleName);
					$(this).children().eq(1).next().next().children().eq(0).val(couponRuleId);
				}
			});
			$("#couponList").hide();
		}	
	});
}
//优惠券增行   等比阶梯
function addCoupon(){
	var sendCouponType=$("input[name=sendCouponType]:checked").val();
	if(sendCouponType == "1") {//判断是否是返还优惠券
		$("#sendCouponType1").next().hide();
		var couponName = $("#list1 tr:last").children().eq(0).next().text();
		var couponId = $("#list1 tr:last").children().eq(0).next().next().next().next().children().eq(0).val();
		var multiple = $("#list1 tr:last").children().eq(0).children().eq(0).val();
		if(couponName == "" || couponName == null){
			$("#addCoupon").next().text("末行为空，不能增加").show();
		} else if(couponId == "") {
			$("#addCoupon").next().text("请填入数量！").show();
		}else if(multiple == "") {
			$("#addCoupon").next().text("请填入阶梯！").show();
		}else{
			$("#addCoupon").next().hide();
			$("#list1 tr:last").children().eq(0).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			$("#list1 tr:last").children().eq(4).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			var len = parseInt($("#list1 tr:last").attr("id"))+1;
		    $("#list1 tr:last").after("<tr id="+len+"><td><input type='text' style='width:40px'></td><td></td><td></td><td style='display: none;'><input type='hidden' ></td><td><input type='text' ></td><td>" +
		    		"<a  class='chooseCoupons' id="+len+">选择劵</a> <a id="+len+" class='deleteCoupon' onclick=deleteCoupon('"+len+"')>删除</a></td></tr>");
		}
	} else {
		$("#sendCouponType1").next().text("请选择返还优惠券！").show();
	}
}
//优惠券增行   非等比阶梯
function addCoupon1(){
	var sendCouponType=$("input[name=sendCouponType]:checked").val();
	if(sendCouponType == "2") {//判断是否选择了阶梯返还优惠券
		$("#sendCouponType2").next().hide();
		var couponName = $("#list2 tr:last").children().eq(5).text();
		var count1 = $("#list2 tr:last").children().eq(1).children().eq(0).val();
		var count2 = $("#list2 tr:last").children().eq(2).children().eq(0).val();
		var money1 = $("#list2 tr:last").children().eq(3).children().eq(0).val();
		var money2 = $("#list2 tr:last").children().eq(4).children().eq(0).val();
		var count3 = $("#list2 tr:last").children().eq(8).children().eq(0).val();
		var sendtype =$("input[name=sendtype]:checked").val();
		if(couponName == "" || couponName == null){
			$("#addCoupon1").next().text("末行为空，不能增加").show();
		} else if(sendtype == "1" && count1 == "") {
			$("#addCoupon1").next().text("请输入起点数量！").show();
		}else if(sendtype == "1" && count2 == "") {
			$("#addCoupon1").next().text("请输入阶点数量！").show();
		} else if(sendtype == "2" &&money1 == "") {
			$("#addCoupon1").next().text("请输入起点金额！").show();
		}else if(sendtype == "2" &&money2 == "") {
			$("#addCoupon1").next().text("请输入阶点金额！").show();
		} else if(count3 == "") {
			$("#addCoupon1").next().text("请输入赠送数量！").show();
		} else if(sendtype == "1" && count1 != undefined && count2 != undefined && parseInt(count1) >= parseInt(count2)) {
			$("#addCoupon1").next().text("阶点数量必须大于起点数量！").show();
		} else if(sendtype == "2" && money1 != undefined && money2 != undefined && parseInt(money1) >= parseInt(money2)) {
			$("#addCoupon1").next().text("阶点金额必须大于起点金额！").show();
		}else {
			if("1" == sendtype) {
				$("#list2 tr:last").children().eq(1).children().eq(0).attr("readonly",true).css({"background":"#eee"});
				$("#list2 tr:last").children().eq(2).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			} else {
				$("#list2 tr:last").children().eq(3).children().eq(0).attr("readonly",true).css({"background":"#eee"});
				$("#list2 tr:last").children().eq(4).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			}
			$("#list2 tr:last").children().eq(8).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			
			$("#addCoupon1").next().hide();
			var len = parseInt($("#list2 tr:last").attr("id"))+1;
		    $("#list2 tr:last").after("<tr id="+len+"><td>"+len+"</td><td><input type='text' style='width:80px'></td><td><input type='text' style='width:80px'></td><td><input type='text' style='width:80px'></td><td><input type='text' style='width:80px'></td><td></td>" +
		    		"<td></td><td style='display: none;'><input type='hidden'></td><td><input type='text' style='width:100px'></td><td><a class='chooseCoupons1' id="+len+">选择劵</a> <a id="+len+" class='deleteCoupon1' onclick=deleteCoupon1('"+len+"')>删除</a></td></tr>");
		    
		    var length = $("#list2 tr").length;
			if("1" == sendtype) {
				$("#list2 tr:last").children().eq(3).children().eq(0).attr("readonly",true).css({"background":"#eee"});
				$("#list2 tr:last").children().eq(4).children().eq(0).attr("readonly",true).css({"background":"#eee"});
				if(length > 2) {
					$("#list2 tr:last").children().eq(1).children().eq(0).val((count2-0) + 1);
				}
			} else {
				$("#list2 tr:last").children().eq(1).children().eq(0).attr("readonly",true).css({"background":"#eee"});
				$("#list2 tr:last").children().eq(2).children().eq(0).attr("readonly",true).css({"background":"#eee"});
				if(length > 2) {
						$("#list2 tr:last").children().eq(3).children().eq(0).val((money2-0) + 0.01);
				}
			}
		}
	} else {
		$("#sendCouponType2").next().text("请选择阶梯返还优惠券！").show();
	}
	
	
}
//添加选中的赠品   等比阶梯
function appendGift(){
	$("#fullPresentCommodity1").find('input').each(function(){
		var checked = $(this).attr("checked");
		if(checked == "checked"){
			var productName = $(this).parent().next().next().next().text();
			var skuName = $(this).parent().next().next().next().next().text();
			var skuId = $(this).attr("id");
			var pid = $(this).val();
			var rowNumber = $("#selectCommodityNum").val();
			$("#list3").find('tr').each(function(){
				var rowId=$(this).attr("id");
				if(rowId == rowNumber) {
					$(this).children().eq(1).text(productName);
					$(this).children().eq(2).text(skuName);
					$(this).children().eq(3).children().eq(0).val(skuId);
					$(this).children().eq(5).children().eq(0).val(pid);
				}
			});
			$("#giftList").hide();
		}	
	});
}

//阶梯满减增行
function addDetail(){
	var readioIdPresente=$("input[name=readioIdPresente]:checked").val();
	if(readioIdPresente == "2") {
		$("#readioIdPresente2").next().hide();
		var multiple = $("#list5 tr:last").children().eq(0).children().eq(0).val();
		var start = $("#list5 tr:last").children().eq(1).children().eq(0).val();
		var end = $("#list5 tr:last").children().eq(2).children().eq(0).val();
		var money = $("#list5 tr:last").children().eq(3).children().eq(0).val();
		if(multiple == ""){
			$("#addDetail").next().text("请输入阶梯！").show();
		} else if(start == "") {
			$("#addDetail").next().text("请输入起点金额！").show();
		} else if (end == "") {
			$("#addDetail").next().text("请输入阶点金额！").show();
		}else if (money == "") {
			$("#addDetail").next().text("请输入金额！").show();
		}else{
			$("#list5 tr:last").children().eq(0).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			$("#list5 tr:last").children().eq(1).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			$("#list5 tr:last").children().eq(2).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			$("#list5 tr:last").children().eq(3).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			$("#addDetail").next().hide();
			var len = parseInt($("#list5 tr:last").attr("id"))+1;
		    $("#list5 tr:last").after("<tr id="+len+"><td><input type='text' style='width:90px'></td><td><input type='text' style='width:120px'></td><td><input type='text' style='width:120px'></td><td><input type='text' style='width:160px'></td><td>" +
		    		"<a id="+len+" class='deletePresente' onclick=deletePresente('"+len+"')>删除</a></td></tr>");
		
			var length = $("#list5 tr").length;

			if(length > 2) {
				$("#list5 tr:last").children().eq(1).children().eq(0).val((end-0) + 0.01);
			}
		}
	} else {
		$("#readioIdPresente2").next().text("请选择！").show();
	}
}

//赠品增行   等比阶梯
function addGift(){
	var selectCategoryLadder=$("input[name=selectCategoryLadder]:checked").val();
	if(selectCategoryLadder == "1") { //赠送商品是否选中
		$("#selectCategoryLadder1").next().hide();
		var couponName = $("#list3 tr:last").children().eq(0).next().text();
		var count = $("#list3 tr:last").children().eq(4).children().eq(0).val();
		var multiple = $("#list3 tr:last").children().eq(0).children().eq(0).val();
		if(couponName == "" || couponName == null){
			$("#addGift").next().text("末行为空，不能增加").show();
		} else if(count == "") {
			$("#addGift").next().text("请填入数量！").show();
		} else if (multiple == "") {
			$("#addGift").next().text("请输入阶梯！").show();
		}else{
			$("#list3 tr:last").children().eq(4).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			$("#list3 tr:last").children().eq(0).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			$("#addGift").next().hide();
			var len = parseInt($("#list3 tr:last").attr("id"))+1;
		    $("#list3 tr:last").after("<tr id="+len+"><td><input type='text' style='width:40px'></td><td></td><td></td><td style='display: none;'><input type='hidden'></td><td><input type='text' style='width:100px'></td><td style='display: none;'><input type='hidden' ></td><td>" +
		    		"<a  class='chooseCommodity' id="+len+">选择单品</a> <a id="+len+" class='deleteCommodity' onclick=deleteCommodity('"+len+"')>删除</a></td></tr>");
		}
	} else {
		$("#selectCategoryLadder1").next().text("请选择赠送商品！").show();
	}

}
//添加选中的赠品   非等比阶梯
function appendGift1(){
	$("#fullPresentCommodity2").find('input').each(function(){
		var checked = $(this).attr("checked");
		if(checked == "checked"){
			var rowNumber = $("#selectCommodityNum1").val();
			var productName = $(this).parent().next().next().next().text();
			var skuName = $(this).parent().next().next().next().next().text();
			var skuId = $(this).attr("id");
			var pid = $(this).val();
			
			
			$("#list4").find('tr').each(function(){
				var rowId=$(this).attr("id");
				if(rowId == rowNumber) {
					$(this).children().eq(5).text(productName);
					$(this).children().eq(6).text(skuName);
					$(this).children().eq(7).children().eq(0).val(skuId);
					$(this).children().eq(9).children().eq(0).val(pid);
				}
			});
			$("#giftList1").hide();
		}	
	});
}
//赠品增行   非等比阶梯
function addGift1(){
	var selectCategoryLadder=$("input[name=selectCategoryLadder]:checked").val();
	if(selectCategoryLadder == "2") { //阶梯赠送商品是否选中
		$("#selectCategoryLadder2").next().hide();
		var couponName = $("#list4 tr:last").children().eq(5).text();
		var count1 = $("#list4 tr:last").children().eq(1).children().eq(0).val();
		var count2 = $("#list4 tr:last").children().eq(2).children().eq(0).val();
		var money1 = $("#list4 tr:last").children().eq(3).children().eq(0).val();
		var money2 = $("#list4 tr:last").children().eq(4).children().eq(0).val();
		var count3 = $("#list4 tr:last").children().eq(8).children().eq(0).val();
		var countLadderId = $('input:radio[name="countLadder"]:checked').attr("id");
		
		if(couponName == "" || couponName == null){
			$("#addGift1").next().text("末行为空，不能增加").show();
		} else if("countLadder1" == countLadderId && count1 == "") {
			$("#addGift1").next().text("请输入起点数量！").show();
		} else if("countLadder1" == countLadderId && count2 == "") {
			$("#addGift1").next().text("请输入阶点数量！").show();
		} else if("countLadder2" == countLadderId && money1 == "") {
			$("#addGift1").next().text("请输入起点金额！").show();
		} else if("countLadder2" == countLadderId && money2 == "") {
			$("#addGift1").next().text("请输入阶点金额！").show();
		} else if(count3 == "") {
			$("#addGift1").next().text("请输入赠送数量！").show();
		} else if("countLadder1" == countLadderId && count1 != undefined && count2 != undefined && parseInt(count1) >= parseInt(count2)) {
			$("#addGift1").next().text("阶点数量必须大于起点数量！").show();
		} else if("countLadder2" == countLadderId && money1 != undefined && money2 != undefined && parseInt(money1) >= parseInt(money2)) {
			$("#addGift1").next().text("阶点金额必须大于起点金额！").show();
		}else {
			if("countLadder1" == countLadderId) {
				$("#list4 tr:last").children().eq(1).children().eq(0).attr("readonly",true).css({"background":"#eee"});
				$("#list4 tr:last").children().eq(2).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			} else {
				$("#list4 tr:last").children().eq(3).children().eq(0).attr("readonly",true).css({"background":"#eee"});
				$("#list4 tr:last").children().eq(4).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			}
			$("#list4 tr:last").children().eq(8).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			$("#addGift1").next().hide();
			var len = parseInt($("#list4 tr:last").attr("id"))+1;
		    $("#list4 tr:last").after("<tr id="+len+"><td>"+len+"</td><td><input type='text' style='width:60px'></td><td><input type='text' style='width:60px'></td><td><input type='text' style='width:60px'></td><td><input type='text' style='width:60px'></td><td></td><td></td>" +
		    		"<td style='display: none;'><input type='hidden'></td><td><input type='text' style='width:100px'></td><td style='display: none;'><input type='hidden'></td><td><a class='chooseCommodity1' id="+len+">选择单品</a> <a id="+len+" class='deleteCommodity1' onclick=deleteCommodity1('"+len+"')>删除</a></td></tr>");
		}
		
		 var length = $("#list4 tr").length;
		if("countLadder1" == countLadderId) {
			$("#list4 tr:last").children().eq(3).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			$("#list4 tr:last").children().eq(4).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			if(length > 2) {
				$("#list4 tr:last").children().eq(1).children().eq(0).val((count2-0) + 1);
			}
		} else {
			$("#list4 tr:last").children().eq(1).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			$("#list4 tr:last").children().eq(2).children().eq(0).attr("readonly",true).css({"background":"#eee"});
			if(length > 2) {
				$("#list4 tr:last").children().eq(3).children().eq(0).val((money2-0) + 0.01);
		}
		}
	} else {
		$("#selectCategoryLadder2").next().text("请选择阶梯赠送商品！").show();
	}
}

//删除行  单品等比阶梯
function deleteCommodity(val){
	$("#list3 tr").each(function(){
		var len = $(this).attr("id");
		if(len == val) {
			$(this).remove();
		}
		if(len > val) {
			//$(this).attr("id", len-1);
			//$(this).children().eq(0).text(len-1);
			$(this).children().last().attr("id",len-1);
			$(this).children().last().attr("onclick","").click(eval("function(){deleteCommodity(len-1)}"));
		}
	});
	$("#list3 tr:last").children().eq(4).children().eq(0).attr("readonly",false).css({"background":""});
	$("#list3 tr:last").children().eq(0).children().eq(0).attr("readonly",false).css({"background":""});
	$("#addGift").next().hide();
}

//删除行  单品不等比阶梯
function deleteCommodity1(val){
	$("#list4 tr").each(function(){
		var len = $(this).attr("id");
		if(len == val) {
			$(this).remove();
		}
		if(len > val) {
			//$(this).attr("id", len-1);
			$(this).children().eq(0).text(len-1);
			$(this).children().last().attr("id",len-1);
			$(this).children().last().attr("onclick","").click(eval("function(){deleteCommodity1(len-1)}"));
		}
	});
	var countLadderId = $('input:radio[name="countLadder"]:checked').attr("id");
	if("countLadder1" == countLadderId) {
		$("#list4 tr:last").children().eq(1).children().eq(0).attr("readonly",false).css({"background":""});
		$("#list4 tr:last").children().eq(2).children().eq(0).attr("readonly",false).css({"background":""});
	} else {
		$("#list4 tr:last").children().eq(3).children().eq(0).attr("readonly",false).css({"background":""});
		$("#list4 tr:last").children().eq(4).children().eq(0).attr("readonly",false).css({"background":""});
	}
	$("#list4 tr:last").children().eq(8).children().eq(0).attr("readonly",false).css({"background":""});
	$("#addGift1").next().hide();
}

//删除行  等比阶梯
function deleteCoupon(val){
	$("#list1 tr").each(function(){
		var len = $(this).attr("id");
		if(len == val){
			$(this).remove();
		}
		if(len > val){
			//$(this).attr("id",len-1);
			$(this).children().last().attr("id",len-1);
			$(this).children().last().attr("onclick","").click(eval("function(){deleteCoupon(len-1)}"));
		}
	});
	$("#list1 tr:last").children().eq(0).children().eq(0).attr("readonly",false).css({"background":""});
	$("#list1 tr:last").children().eq(4).children().eq(0).attr("readonly",false).css({"background":""});
	$("#addCoupon").next().hide();
}

//删除行  活动总金额按设定区间段减少
function deletePresente(val){
	$("#list5 tr").each(function(){
		var len = $(this).attr("id");
		if(len == val){
			$(this).remove();
		}
	});
	$("#list5 tr:last").children().eq(0).children().eq(0).attr("readonly",false).css({"background":""});
	$("#list5 tr:last").children().eq(1).children().eq(0).attr("readonly",false).css({"background":""});
	$("#list5 tr:last").children().eq(2).children().eq(0).attr("readonly",false).css({"background":""});
	$("#list5 tr:last").children().eq(3).children().eq(0).attr("readonly",false).css({"background":""});
	$("#addDetail").next().hide();
}

//删除行  非等比阶梯
function deleteCoupon1(val){
	$("#list2 tr").each(function(){
		var len = $(this).attr("id");
		if(len == val){
			$(this).remove();
		}
		if(len > val){
			//$(this).attr("id",len-1);
			$(this).children().eq(0).text(""+(len-1));
			$(this).children().last().attr("id",len-1);
			$(this).children().last().attr("onclick","").click(eval("function(){deleteCoupon1(len-1)}"));
		}
	});
	
	var sendtype =$("input[name=sendtype]:checked").val();
	if("1" == sendtype) {
		$("#list2 tr:last").children().eq(1).children().eq(0).attr("readonly",false).css({"background":""});
		$("#list2 tr:last").children().eq(2).children().eq(0).attr("readonly",false).css({"background":""});
	} else {
		$("#list2 tr:last").children().eq(3).children().eq(0).attr("readonly",false).css({"background":""});
		$("#list2 tr:last").children().eq(4).children().eq(0).attr("readonly",false).css({"background":""});
	}
	$("#list2 tr:last").children().eq(8).children().eq(0).attr("readonly",false).css({"background":""});
	
	$("#addCoupon1").next().hide();
}

function findBrand(pageNum){
	var brandName=$("#brandName").val();
	var param_array = new Array();
	if(brandName != null && brandName != "") {
		param_array.push("brandName=" + brandName);
	}
	if(pageNum != null && pageNum != undefined) {
		param_array.push("pageNum="+pageNum);
	}
		$.ajax({
			type : 'post',
			url : "../activitis/searchBrand",
			data : param_array.join("&"),
			success : function(data) {
				$("#BrandList").html(data);
			}
		});
}

//满赠时，按阶梯赠送查询单品列表
function findCommodity1(page){
	var param_array = new Array();
	var categoryOne = $("#categoryOneFullPresent").val();
	var categoryTwo = $("#categoryTwoFullPresent").val();
	var categoryThree = $("#categoryThreeFullPresent").val();
	var categoryFour = $("#categoryFourFullPresent").val();
	var supplier = $("#supplier").val();
	var productId=$("#productId").val();
	var productCode1=$("#productCode1").val();
	var b2cType = $("input[name=member]:checked").val();
	param_array.push("b2cType="+b2cType);
	var flag = "1";
	if(categoryFour != 0) {
		param_array.push("categoryId=" + categoryFour);
	} else if (categoryThree != 0) {
		param_array.push("categoryId=" + categoryThree);
	} else if (categoryTwo != 0) {
		param_array.push("categoryId=" + categoryTwo);
	} else if (categoryOne != 0) {
		param_array.push("categoryId=" + categoryOne);
	} 
	if(supplier != "") {
		param_array.push("supplierName=" + supplier);
	}
	param_array.push("flag=" + flag);
	if(page != null && page != undefined) {
		param_array.push("page=" + page);
	}
	if(productId != null && productId != "") {
		param_array.push("productId=" + productId);
	}
	if(productCode1 != null && productCode1 != "") {
		param_array.push("productCode=" + productCode1);
	}
		$.ajax({
			type : 'post',
			url : "../rules/findCommodity",
			data : param_array.join("&"),
			success : function(data) {
				//分页显示优惠券信息
				$("#fullPresentCommodity1").html(data);
			}
		});
}

//满赠时，不等比单品查询列表
function findCommodity2(page){
	var param_array = new Array();
	var categoryOne = $("#categoryOneFullPresent1").val();
	var categoryTwo = $("#categoryTwoFullPresent1").val();
	var categoryThree = $("#categoryThreeFullPresent1").val();
	var categoryFour = $("#categoryFourFullPresent1").val();
	var supplier = $("#supplier1").val();
	var productId1=$("#productId1").val();
	var productCode2=$("#productCode2").val();
	var b2cType = $("input[name=member]:checked").val();
	param_array.push("b2cType="+b2cType);
	var flag = "2";
	if(categoryFour != 0) {
		param_array.push("categoryId=" + categoryFour);
	} else if (categoryThree != 0) {
		param_array.push("categoryId=" + categoryThree);
	} else if (categoryTwo != 0) {
		param_array.push("categoryId=" + categoryTwo);
	} else if (categoryOne != 0) {
		param_array.push("categoryId=" + categoryOne);
	} 
	if(supplier != "") {
		param_array.push("supplierName=" + supplier);
	}
	if(page != null && page != undefined) {
		param_array.push("page=" + page);
	}
	param_array.push("flag=" + flag);
	if(productId1 != null && productId1 != "") {
		param_array.push("productId=" + productId1);
	}
	if(productCode2 != null && productCode2 != "") {
		param_array.push("productCode=" + productCode2);
	}
		$.ajax({
			type : 'post',
			url : "../rules/findCommodity",
			data : param_array.join("&"),
			success : function(data) {
				//分页显示优惠券信息
				$("#fullPresentCommodity2").html(data);
			}
		});
}

function findCoupons(page){
	var couponName = $("#couponName").val();
	var couponStartdate = $("#couponStartdate").val();
	var couponEnddate = $("#couponEnddate").val();
	var couponType = $("#couponType").val();
	var couponMember=$("input[name=member]:checked").val();
	var flag = "1";
	var param_array = new Array();
	param_array.push("coupnName="+couponName);
	param_array.push("startDate="+couponStartdate);
	param_array.push("endDate="+couponEnddate);
	param_array.push("couponType="+couponType);
	param_array.push("couponMember="+couponMember);
	param_array.push("flag="+flag);
	if(page != null && page != undefined) {
		param_array.push("page="+page);
	}
	if(couponStartdate != "" && couponEnddate != "" && couponStartdate >= couponEnddate){
		$("#couponEnddate").next().text("开始时间不能小于结束时间").show();
	}else{
		$("#couponEnddate").next().hide();
		$.ajax({
			type : 'post',
			url : "../rules/findCoupons",
			data : param_array.join("&"),
			success : function(data) {
				//分页显示优惠券信息
				$("#couponsList").html(data);

			}
		});
	}
}

function findCoupons1(page){
	var couponName = $("#couponName1").val();
	var couponStartdate = $("#couponStartdate1").val();
	var couponEnddate = $("#couponEnddate1").val();
	var couponType = $("#couponType1").val();
	var couponMember=$("input[name=member]:checked").val();
	var flag = "2";
	var param_array = new Array();
	param_array.push("coupnName="+couponName);
	param_array.push("startDate="+couponStartdate);
	param_array.push("endDate="+couponEnddate);
	param_array.push("couponType="+couponType);
	param_array.push("couponMember="+couponMember);
	param_array.push("flag="+flag);
	if(page != null && page != undefined) {
		param_array.push("page="+page);
	}
	if(couponStartdate != "" && couponEnddate != "" && couponStartdate >= couponEnddate){
		$("#couponEnddate1").next().text("开始时间不能小于结束时间").show();
	}else{
		$("#couponEnddate1").next().hide();
		$.ajax({
			type : 'post',
			url : "../rules/findCoupons",
			data : param_array.join("&"),
			success : function(data) {
				//分页显示优惠券信息
				$("#couponsList1").html(data);
			}
		});
	}
}


function saveRule(){
	// 校验数据
	//var flag = true;
	var flag = checkInformation();
	if (flag == false) {
		tipMessage('部分信息不完整或不符合规范，请修改！',
				function() {	});
	}
	var url = "";
	var memberId = $('input:radio[name="member"]:checked').attr("id");
	if ("member1" == memberId) {
		url += "&memberValue=3";
	} else {
		url += "&memberValue=4";
	}
	var accessMethodId = $('input:radio[name="accessMethod"]:checked').attr("id");
	if ("accessMethod1" == accessMethodId) {
		url += "&accessMethodValue=-1";
	} else {
		url += "&accessMethodValue="+ getAccessMethodValue();
	}
	var categoryId = $('input:radio[name="category"]:checked').attr("id");
	if ("category1" == categoryId) {
		url += "&categoryValue=-1";
	} else if("category2" == categoryId){
		url += "&categoryValue="+getArrayByDivId("selectedSorts");
	}
	var brandId = $('input:radio[name="brand"]:checked').attr("id");
	if ("brand1" == brandId) {
		url += "&brandValue=-1";
	} else {
		url += "&brandValue="+getArrayByDivId("AddBrand");
	}
	var regionId = $('input:radio[name="region"]:checked').attr("id");
	if ("region1" == regionId) {
		url += "&regionValue=-1";
	} else {
		url += "&regionValue="+getRegionByDivId("selectedArea");
	}
	var cannelId = $('input:radio[name="channel"]:checked').attr("id");
	if ("channel1" == cannelId) {
		url += "&cannelValue=-1";
	} else {
		url += "&cannelValue="+getArrayByDivId("AddChannel");
	}
	var commodityId = $('input:radio[name="commodity"]:checked').attr("id");
	if ("commodity1" == commodityId) {
		url += "&commodityValue=-1";
	} else {
		url += "&commodityValue="+getArrayByDivId("selectedSkus");
	}
	url += "&nonparticipatorValue=" + getNonparticipator();
	
	var activeId=document.getElementById("activeId").value;
	url += "&activeId=" + activeId;
	var productFromMethod = $("#productFromMethod option:selected").val();
	url += "&productFromMethod=" + productFromMethod;
	
	var ruleTermValue = $("#ruleTerm").val();
	if(ruleTermValue == "1") {//满减
		var fullcutValue = $("input[name=fullcut]:checked").val();
		url += "&fullcutValue=" + fullcutValue;
			if(fullcutValue == "1") {//普通满减
				var readioIdPresente=$("input[name=readioIdPresente]:checked").val();
				url += "&radioFullCutValue=" + readioIdPresente;
				var orderMoneyValue = $("#activeMoneyOne").val();
				url += "&orderMoneyValue=" + orderMoneyValue;
				if(readioIdPresente == "1") {//普通满减
				
				} else {//阶梯满减
					var str1 = new Array();
					var str2 = new Array();
					var str3 = new Array();
					var str4 = new Array();
					$("#list5").find('tr').each(function() {
						var rowId=$(this).attr("id");
						if(rowId != 0) {
							var multiple = $(this).children().eq(0).children().eq(0).val();
							var start = $(this).children().eq(1).children().eq(0).val();
							var end = $(this).children().eq(2).children().eq(0).val();
							var money = $(this).children().eq(3).children().eq(0).val();
							str1.push(multiple);
							str2.push(start);
							if(end == "") {
								str3.push("999999");
							} else {
								str3.push(end);
							}
							str4.push(money);
						}
					});
					url += "&multipleValue=" + str1;	
					url += "&startValue=" + str2;	
					url += "&endValue=" + str3;	
					url += "&moneyValue=" + str4;	
				}
			} else {//等比满减
				var activeMoney = $("#activeMoney").val();
				//var ingeterMultiple = $("#ingeterMultiple").val();
				url += "&activeMoney=" + activeMoney;
				url += "&ingeterMultiple=" + activeMoney;
			}
			var moneyPresent1=$("#moneyPresent1").val();
			url += "&moneyPresent1=" + moneyPresent1;
			
	} else if(ruleTermValue == "2") {//满返
		//收集订单条件的数据
		var str = new Array();
		var fullsendValue = $("input[name=fullsend]:checked").val();
		//普通满返
		str.push(fullsendValue);
		if(fullsendValue == "1") {//普通满返 按金额
			var commodityMoney1=$("#commodityMoney1").val();
			str.push(commodityMoney1);
		} else if (fullsendValue == "2") {//等比满返 按金额
			var commodityMoney2=$("#commodityMoney2").val();
			str.push(commodityMoney2);
			//var ingeterMultiple2=$("#ingeterMultiple2").val();
			str.push(commodityMoney2);
		} else if(fullsendValue == "3") {//普通满返，按数量
			var commodityCount1=$("#commodityCount1").val();
			str.push(commodityCount1);
		} else if(fullsendValue == "4") {//等比满返，按数量
			var commodityCount2=$("#commodityCount2").val();
			str.push(commodityCount2);
			//var ingeterMultiple3=$("#ingeterMultiple3").val();
			str.push(commodityCount2);
		}
		url += "&orderCondition=" + str;	
		
		//收集执行形式数据
		var executeFormalization = new Array();
		var sendCouponTypeValue = $("input[name=sendCouponType]:checked").val();
		executeFormalization.push(sendCouponTypeValue);
		if(sendCouponTypeValue == "1") {//按阶梯赠送优惠券
			var str1 = new Array();
			var str2 = new Array();
			var str3 = new Array();
			$("#list1").find('tr').each(function() {
				var rowId=$(this).attr("id");
				if(rowId != 0) {
					var count = $(this).children().eq(4).children().eq(0).val();
					var businessId = $(this).children().eq(3).children().eq(0).val();
					var multiple = $(this).children().eq(0).children().eq(0).val();
					str1.push(businessId);
					str2.push(count);
					str3.push(multiple);
				}
			});
			url += "&executeFormalization=" + executeFormalization;	
			url += "&couponId=" + str1;	
			url += "&couponCount=" + str2;	
			url += "&multiple=" + str3;	
		}else if(sendCouponTypeValue == "2") {//返回优惠券(不等比)
			var countOrMoney = $("input[name=sendtype]:checked").val();
			executeFormalization.push(countOrMoney);
			var noEX1 = new Array();
			var noEX2 = new Array();
			var noEX3 = new Array();
			var noEX4 = new Array();
			$("#list2").find('tr').each(function() {
				var rowId=$(this).attr("id");
					if(rowId != 0) {
						var businessId = $(this).children().eq(7).children().eq(0).val();
						var count = $(this).children().eq(8).children().eq(0).val();
						noEX1.push(businessId);
						noEX2.push(count);
						if(countOrMoney == "1") {//按数量阶梯
							var count1 = $(this).children().eq(1).children().eq(0).val();
							var count2 = $(this).children().eq(2).children().eq(0).val();
							noEX3.push(count1);
							if(count2 == "") {
								noEX4.push("999999");
							} else {
								noEX4.push(count2);
							}
						} else {//按金额阶梯
							var money1 = $(this).children().eq(3).children().eq(0).val();
							var money2 = $(this).children().eq(4).children().eq(0).val();
							noEX3.push(money1);
							if(money2 == "") {
								noEX4.push("999999");
							} else {
								noEX4.push(money2);
							}
						}
					}
			});
			url += "&executeFormalization=" + executeFormalization;	
			url += "&couponId=" + noEX1;	
			url += "&couponCount=" + noEX2;	
			url += "&couponStart=" + noEX3;	
			url += "&couponEnd=" + noEX4;	
		}
		
	} else if(ruleTermValue == 3) {//限时直降
		//收集执行形式
		var str1 = new Array();
		var singlePricePresente=$("input[name=singlePricePresente]:checked").val();
		str1.push(singlePricePresente);
		if(singlePricePresente == "1") {
			var singleVal=$("#singleMoneyPresent").val();
			var restrictionCount=$("#restrictionCount").val();
			str1.push(singleVal);
			str1.push(restrictionCount);
			url += "&singleMoneyPresent=" + str1;	
		} else if(singlePricePresente == "2") {
			var percentageMoney=$("#percentageMoney").val();
			var restrictionCount1=$("#restrictionCount1").val();
			str1.push(percentageMoney);
			str1.push(restrictionCount1);
			url += "&singleMoneyPresent=" + str1;	
		}

		
		
		
	} else if(ruleTermValue == 4) {//满赠
		//收集订单条件的数据
		var str = new Array();
		var fullsendValue = $("input[name=fullsend]:checked").val();
		//普通满赠
		str.push(fullsendValue);
		
		if(fullsendValue == "1") { //普通满赠   按金额
			var commodityMoney1=$("#commodityMoney1").val();
			str.push(commodityMoney1);
		} else if (fullsendValue == "2") {//等比满赠   按金额
			var commodityMoney2=$("#commodityMoney2").val();
			str.push(commodityMoney2);
			//var ingeterMultiple2=$("#ingeterMultiple2").val();
			str.push(commodityMoney2);
		} else if(fullsendValue == "3") {//普通满赠   按数量
			var commodityCount1 = $("#commodityCount1").val();
			str.push(commodityCount1);
		} else if (fullsendValue == "4") {//等比满赠  按数量
			var commodityCount2=$("#commodityCount2").val();
			str.push(commodityCount2);
			//var ingeterMultiple3=$("#ingeterMultiple3").val();
			str.push(commodityCount2);
		}
		url += "&orderCondition=" + str;	
		
		//收集执行形式数据
		var executeFormalization = new Array();
		var selectCategoryLadderValue = $("input[name= selectCategoryLadder]:checked").val();
		executeFormalization.push(selectCategoryLadderValue);
		if(selectCategoryLadderValue == "1") {// 按阶梯赠送单品
			var str1 = new Array();
			var str2 = new Array();
			var str3 = new Array();
			var str4 = new Array();
			var str5 = new Array();
			var str6 = new Array();//阶梯
			$("#list3").find('tr').each(function() {
				var rowId=$(this).attr("id");
				if(rowId != 0) {
					var count = $(this).children().eq(4).children().eq(0).val();
					var businessId = $(this).children().eq(3).children().eq(0).val();
					var pname  = $(this).children().eq(1).text();
					var skuName  = $(this).children().eq(2).text();
					var pid = $(this).children().eq(5).children().eq(0).val();
					var multiple = $(this).children().eq(0).children().eq(0).val();
					str1.push(businessId);
					str2.push(count);
					str3.push(pname);
					str4.push(skuName);
					str5.push(pid);
					str6.push(multiple);
				}
			});
			url += "&executeFormalization=" + executeFormalization;	
			url += "&couponId=" + str1;	
			url += "&couponCount=" + str2;	
			url += "&pname=" + str3;	
			url += "&skuName=" + str4;	
			url += "&pid=" + str5;	
			url += "&multiple=" + str6;	
		}else if(selectCategoryLadderValue == "2") {//赠送单品(不等比)
			var countLadder = $("input[name=countLadder]:checked").val();
			executeFormalization.push(countLadder);
			var noEX1 = new Array();
			var noEX2 = new Array();
			var noEX3 = new Array();
			var noEX4 = new Array();
			var noEX5 = new Array();
			var noEX6 = new Array();
			var noEX7 = new Array();
			$("#list4").find('tr').each(function() {
				var rowId=$(this).attr("id");
					if(rowId != 0) {
						var pname = $(this).children().eq(5).text();
						var skuName = $(this).children().eq(6).text();
						var businessId = $(this).children().eq(7).children().eq(0).val();
						var count = $(this).children().eq(8).children().eq(0).val();
						var pid = $(this).children().eq(9).children().eq(0).val();
						noEX1.push(businessId);
						noEX2.push(count);
						noEX5.push(pname);
						noEX6.push(skuName);
						noEX7.push(pid);
						if(countLadder == "1") {//按数量阶梯
							var count1 = $(this).children().eq(1).children().eq(0).val();
							var count2 = $(this).children().eq(2).children().eq(0).val();
							noEX3.push(count1);
							if(count2 == "") {
								noEX4.push("999999");
							} else {
								noEX4.push(count2);
							}
						} else {//按金额阶梯
							var money1 = $(this).children().eq(3).children().eq(0).val();
							var money2 = $(this).children().eq(4).children().eq(0).val();
							noEX3.push(money1);
							if(money2 == "") {
								noEX4.push("999999");
							} else {
								noEX4.push(money2);
							}
						}
					}
			});
			url += "&executeFormalization=" + executeFormalization;	
			url += "&couponId=" + noEX1;	
			url += "&couponCount=" + noEX2;	
			url += "&couponStart=" + noEX3;	
			url += "&couponEnd=" + noEX4;	
			url += "&pname=" + noEX5;	
			url += "&skuName=" + noEX6;	
			url += "&pid=" + noEX7;	
		}
		
		
	} else if(ruleTermValue == 5) {
		
	}
	/*var limittimes1 = $("limittimes1").val();
	url += */
	if (flag == true) {
		console.log(flag);
		console.log(url);
		console.log($('#rulesAction').serialize()+ url);
		$.ajax({
			type : 'post',
			url : CONTEXTPATH+ "/rules/saveRules",
			traditional : true,
			data : $('#rulesAction').serialize()+ url,
			success : function(data) {
				if (data == 'success') {
					tipMessage(
							"成功，将立即返回到规则列表！",
							function() {
								window.location.href = CONTEXTPATH+ "/activitis/toActivityList";
							});
				} else if (data == 'error') {
					tipMessage("失败，检查后重试！",
							function() {});
				} else {
					tipMessage("失败,原因："+ data,
							function() {	});
				}
			}
		});
	}
}

//获取活动区域
function getRegionByDivId( buttonId) {
	var str = new Array();
	var a = 0;
	$("#"+buttonId+" ul li").each(function() {
		var value = $(this).attr("value");
		str[a] = value;
		a = a + 1;
	});
	return str;
}

function getArrayByDivId( buttonId) {
	var str = new Array();
	var a = 0;
	$("#"+buttonId+" ul li").each(function() {
		var id = $(this).attr("id");
		str[a] = id;
		a = a + 1;
	});
	return str;
}

function getNonparticipator() {
	var str = new Array();  
	$('input[name="nonparticipator"]:checked').each(function(){  
		str.push($(this).val());//向数组中添加元素  
	}); 
	return str;
}


//获取访问方式
function getAccessMethodValue() {
	var str = new Array();
	var a = 0;
	$("#selectedAccessMode ul li").each(function() {
		var id = $(this).attr("id");
		str[a] = id;
		a = a + 1;
	});
	return str;
}

// 获取活动会员
function getMemberVal() {
	var str = new Array();
	var a = 0;
	$("#AddMember ul li").each(function() {
		var id = $(this).attr("id");
		str[a] = id;
		a = a + 1;
	});
	return str;
}

//校验执行形式
function checkExecuteFormalization(flag) {
	var ruleValue = $("#ruleTerm").val();
	var matchnum1 = /^[0-9]*$/;
	var matchnum2 = /^[0-9]+(\.[0-9]+)?$/;
	var moneyId=$("input[name=fullcut]:checked").attr("id");
	var orderLimit;
	if(moneyId == "moneyCheckd1") {
		orderLimit = $("#activeMoneyOne").val();
	}else{
		orderLimit = $("#activeMoney").val();
	}
	if(ruleValue == 1) {
		var readioIdPresenteId = $("input[name=readioIdPresente]:checked").attr("id");
		if("readioIdPresente1" == readioIdPresenteId) {
			var moneyPresentOne =$("#moneyPresent1").val();
			if(moneyPresentOne == "") {
				$("#moneyPresent1").next().text('请输入金额！').show();
				flag = false;
			} else if (moneyPresentOne <= 0 || !matchnum2.test(moneyPresentOne)) {
				$("#moneyPresent1").next().text('金额必须大于0！').show();
				flag = false;
			}else if(parseInt(orderLimit) < parseInt(moneyPresentOne)){
				console.log(orderLimit-moneyPresentOne);
				$("#moneyPresent1").next().text('满减金额应小于订单满足金额').show();
				flag = false;
			}else{
				$("#moneyPresent1").next().hide();
			}
		}
	} else if (ruleValue == 2) {
		var sendCouponTypeId=$("input[name=sendCouponType]:checked").attr("id");
		if("sendCouponType1" == sendCouponTypeId) {
			var length = $("#list1 tbody tr").length;
			if(length == 1) {
				$("#addCoupon").next().text('请增加优惠券！').show();
				flag = false;
			} else {
				$("#addCoupon").next().hide();
			}
			$("#addCoupon1").next().hide();
			$("#list1").find('tr').each(function() {
				var rowId=$(this).attr("id");
				if(rowId != 0) {
					var count = $(this).children().eq(4).children().eq(0).val();
					var multiple = $(this).children().eq(0).children().eq(0).val();
					var businessId = $(this).children().eq(3).children().eq(0).val();
					if(businessId == "") {
						$("#addCoupon").next().text('必须选择券！').show();
						flag = false;
					} else if(count == "") {
						$("#addCoupon").next().text('请输入赠送数量！').show();
						flag = false;
					} else if (count <= 0 || !matchnum1.test(count)) {
						$("#addCoupon").next().text('赠送数量必须大于0,且为正整数！').show();
						flag = false;
					}else if(multiple == "") {
						$("#addCoupon").next().text('请输入阶梯！').show();
						flag = false;
					} else if (multiple <= 0 || !matchnum1.test(multiple)) {
						$("#addCoupon").next().text('阶梯必须大于0,且为正整数！').show();
						flag = false;
					}else {
						$("#addCoupon").next().hide();
					}
				}
			});
		} else  {
			var length = $("#list2 tbody tr").length;
			if(length == 1) {
				$("#addCoupon1").next().text('请增加优惠券！').show();
				flag = false;
			} else {
				$("#addCoupon1").next().hide();
			}
			$("#addCoupon").next().hide();
			$("#list2").find('tr').each(function() {
				var sendtypeId = $("input[name=sendtype]:checked").attr("id");
				if(sendtypeId == "sendtype1") {
					var rowId=$(this).attr("id");
					if(rowId != 0) {
						var businessId = $(this).children().eq(7).children().eq(0).val();
						var count1 = $(this).children().eq(1).children().eq(0).val();
						var count = $(this).children().eq(8).children().eq(0).val();
						if(businessId == "") {
							$("#addCoupon1").next().text('必须选择券！').show();
							flag = false;
						} else if (count == "") {
							$("#addCoupon1").next().text('请输入赠送数量！').show();
							flag = false;
						} else if (count <= 0 || !matchnum1.test(count)) {
							$("#addCoupon1").next().text('赠送数量必须大于0,且为正整数！').show();
							flag = false;
						} else if (count1 == "") {
							$("#addCoupon1").next().text('请输入起点数量！').show();
							flag = false;
						} else if (count1 <= 0 || !matchnum1.test(count1)) {
							$("#addCoupon1").next().text('起点数量必须大于0,且为正整数！').show();
							flag = false;
						}  else {
							$("#addCoupon1").next().hide();
						}
					}
				} else {
					var rowId=$(this).attr("id");
					if(rowId != 0) {
						var businessId = $(this).children().eq(7).children().eq(0).val();
						var count1 = $(this).children().eq(3).children().eq(0).val();
						var count2 = $(this).children().eq(4).children().eq(0).val();
						var count = $(this).children().eq(8).children().eq(0).val();
						if(businessId == "") {
							$("#addCoupon1").next().text('必须选择券！').show();
							flag = false;
						} else if (count == "") {
							$("#addCoupon1").next().text('请输入赠送数量！').show();
							flag = false;
						} else if (count <= 0 || !matchnum1.test(count)) {
							$("#addCoupon1").next().text('赠送数量必须大于0,且为正整数！').show();
							flag = false;
						} else if (count1 == "") {
							$("#addCoupon1").next().text('请输入起点金额！').show();
							flag = false;
						} else {
							$("#addCoupon1").next().hide();
						}
					}
				}
			
			});
		}
		
	} else if (ruleValue == 3) {
		var singlePricePresenteId=$("input[name=singlePricePresente]:checked").attr("id");
		if(singlePricePresenteId == "singlePricePresente1") {
			var singleval=$("#singleMoneyPresent").val();
			if(singleval == "") {
				$("#singleMoneyPresent").next().next().text('请输入金额！').show();
				flag = false;
			} else if (singleval <= 0 || !matchnum2.test(singleval)) {
				$("#singleMoneyPresent").next().next().text('金额必须大于0').show();
				flag = false;
			}else {
				$("#singleMoneyPresent").next().next().hide();
			}
			var count =$("#restrictionCount").val();
			if(count == "") {
				$("#restrictionCount").next().text("请输入限购数量！").show();
				flag = false;
			}else if (count <= 0 || !matchnum1.test(count)) {
				$("#restrictionCount").next().text('限购数量必须大于0,且为正整数！').show();
				flag = false;
			}else {
				$("#restrictionCount").next().hide();
			}
		}
	} else if (ruleValue == 4) {
		var selectCategoryLadderId=$("input[name=selectCategoryLadder]:checked").attr("id");
		if("selectCategoryLadder1" == selectCategoryLadderId) {
			var length = $("#list3 tbody tr").length;
			if(length == 1) {
				$("#addGift").next().text('请增加单品！').show();
				flag = false;
			} else {
				$("#addGift").next().hide();
			}
			$("#addGift1").next().hide();
			$("#list3").find('tr').each(function() {
				var rowId=$(this).attr("id");
				if(rowId != 0) {
					var businessId = $(this).children().eq(3).children().eq(0).val();
					var count = $(this).children().eq(4).children().eq(0).val();
					var multiple = $(this).children().eq(0).children().eq(0).val();
					if(businessId == "") {
						$("#addGift").next().text('必须选择单品！').show();
						flag = false;
					} else if(count == "") {
						$("#addGift").next().text('请输入赠送数量！').show();
						flag = false;
					}else if(multiple == "") {
						$("#addGift").next().text('请输入阶梯！').show();
						flag = false;
					} else if (count <= 0 || !matchnum1.test(count)) {
						$("#addGift").next().text('赠送数量必须大于0,且为正整数！').show();
						flag = false;
					}else if (multiple <= 0 || !matchnum1.test(multiple)) {
						$("#addGift").next().text('阶梯必须大于0,且为正整数！').show();
						flag = false;
					}else {
						$("#addGift").next().hide();
					}
				}
			});
		} else  {
			var length = $("#list4 tbody tr").length;
			if(length == 1) {
				$("#addGift1").next().text('请增加单品！').show();
				flag = false;
			} else {
				$("#addGift1").next().hide();
			}
			$("#addGift").next().hide();
			$("#list4").find('tr').each(function() {
				var rowId=$(this).attr("id");
				if(rowId != 0) {
					var businessId = $(this).children().eq(7).children().eq(0).val();
					var count = $(this).children().eq(8).children().eq(0).val();
					if(businessId == "") {
						$("#addGift1").next().text('必须选择单品！').show();
						flag = false;
					} else if(count == "") {
						$("#addGift1").next().text('请输入赠送数量！').show();
						flag = false;
					} else if (count <= 0 || !matchnum1.test(count)) {
						$("#addGift1").next().text('赠送数量必须大于0,且为正整数！').show();
						flag = false;
					}else {
						$("#addGift1").next().hide();
					}
				}
			});
		}
		
	} else if (ruleValue == 5) {
		
	} else {
		
	}
	return flag;
}

//校验订单条件
function checkOrderCondition(flag) {
	var matchnum1 = /^[0-9]*$/;
	var matchnum2 = /^[0-9]+(\.[0-9]+)?$/;
	var ruleValue = $("#ruleTerm").val();
	if(ruleValue == 1) {
		var moneyId=$("input[name=fullcut]:checked").attr("id");
		if(moneyId == "moneyCheckd1") {
			$("#activeMoneyOne").next().hide();
			var moneyOne = $("#activeMoneyOne").val();
			if(moneyOne == "") {
				$("#activeMoneyOne").next().text('请输入金额！').show();
				flag = false;
			} else if (moneyOne <= 0 || !matchnum2.test(moneyOne)) {
				$("#activeMoneyOne").next().text('金额必须大于0').show();
				flag = false;
			}else {
				$("#activeMoneyOne").next().hide();
			}
		} else {
			$("#activeMoneyOne").next().hide();
			var money = $("#activeMoney").val();
			if( money == "") {
				$("#activeMoney").next().text('请输入金额！').show();
				flag = false;
			} else if (money <= 0 || !matchnum2.test(money)) {
				$("#activeMoney").next().text('金额必须大于0').show();
				flag = false;
			}else {
				$("#activeMoney").next().hide();
			}
		}
	} else if (ruleValue == 2) {//满返
		var checkedMoneyId = $("input[name=fullsend]:checked").attr("id");
		if(checkedMoneyId == "moneyCheckd3") {//普通满返，按金额
			$("#commodityMoney2").next().hide();
			var commodityVal = $("#commodityMoney1").val();
			if(commodityVal == "") {
				$("#commodityMoney1").next().text('请输入金额！').show();
				flag = false;
			} else if (commodityVal <= 0 || !matchnum1.test(commodityVal)) {
				$("#commodityMoney1").next().text('金额必须大于0,且为正整数！').show();
				flag = false;
			}else {
				$("#commodityMoney1").next().hide();
			}
		} else if(checkedMoneyId == "moneyCheckd4") {//等比满返，按金额
			$("#commodityMoney1").next().hide();
			var commodityVal2=$("#commodityMoney2").val();
			//var ingeterVal2 = $("#ingeterMultiple2").val();
			if(commodityVal2 == "") {
				$("#commodityMoney2").next().text('请输入金额！').show();
				flag = false;
			} else if (commodityVal2 <= 0 || !matchnum1.test(commodityVal2)) {
				$("#commodityMoney2").next().text('金额和倍数必须大于0,且为正整数！').show();
				flag = false;
			}else {
				$("#commodityMoney2").next().hide();
			}
		} else if (checkedMoneyId == "countChecked1") {//普通满返，按数量
			$("#commodityCount2").next().hide();
			var commodityCount1 = $("#commodityCount1").val();
			if(commodityCount1 == "") {
				$("#commodityCount1").next().text('请输入数量！').show();
				flag = false;
			} else if (commodityCount1 <= 0 || !matchnum1.test(commodityCount1)) {
				$("#commodityCount1").next().text('数量必须大于0,且为正整数！').show();
				flag = false;
			}else {
				$("#commodityCount1").next().hide();
			}
		} else if (checkedMoneyId == "countChecked2") {//等比满返，按数量
			$("#commodityCount1").next().hide();
			var commodityCount2=$("#commodityCount2").val();
			//var ingeterMultiple3 = $("#ingeterMultiple3").val();
			if(commodityCount2 == "") {
				$("#commodityCount2").next().text('请输入数量！').show();
				flag = false;
			} else if (commodityCount2 <= 0 || !matchnum1.test(commodityCount2)) {
				$("#commodityCount2").next().text('数量必须大于0,且为正整数！').show();
				flag = false;
			}else {
				$("#commodityCount2").next().hide();
			}
		}
		
	} else if (ruleValue == 3) {//限时直降
		
 	} else if (ruleValue == 4) {
		var checkedMoneyId = $("input[name=fullsend]:checked").attr("id");
		if(checkedMoneyId == "moneyCheckd3") {
			$("#commodityMoney2").next().hide();
			$("#commodityCount1").next().hide();
			$("#commodityCount2").next().hide();
			var commodityVal = $("#commodityMoney1").val();
			if(commodityVal == "") {
				$("#commodityMoney1").next().text('请输入金额！').show();
				flag = false;
			} else if (commodityVal <= 0 || !matchnum1.test(commodityVal)) {
				$("#commodityMoney1").next().text('金额必须大于0,且为正整数！').show();
				flag = false;
			}else {
				$("#commodityMoney1").next().hide();
			}
		} else if (checkedMoneyId == "moneyCheckd4"){
			$("#commodityMoney1").next().hide();
			$("#commodityCount1").next().hide();
			$("#commodityCount2").next().hide();
			var commodityVal2=$("#commodityMoney2").val();
			//var ingeterVal2 = $("#ingeterMultiple2").val();
			if(commodityVal2 == "") {
				$("#commodityMoney2").next().text('请输入金额！').show();
				flag = false;
			} else if (commodityVal2 <= 0 || !matchnum1.test(commodityVal2)) {
				$("#commodityMoney2").next().text('金额必须大于0,且为正整数！').show();
				flag = false;
			} else {
				$("#commodityMoney2").next().hide();
			}
		} else if (checkedMoneyId == "countChecked1") {
			$("#commodityMoney1").next().hide();
			$("#commodityMoney2").next().hide();
			$("#commodityCount2").next().hide();
			var countValue1=$("#commodityCount1").val();
			if(countValue1 == "") {
				$("#commodityCount1").next().text("请输入数量！").show();
				flag = false;
			} else if (countValue1 <= 0 || !matchnum1.test(countValue1)) {
				$("#commodityCount1").next().text('数量必须大于0,且为正整数！').show();
				flag = false;
			}else {
				$("#commodityCount1").next().hide();
			}
		} else if (checkedMoneyId == "countChecked2") {
			$("#commodityMoney1").next().hide();
			$("#commodityMoney2").next().hide();
			$("#commodityCount1").next().hide();
			var countValue2 = $("#commodityCount2").val();
			//var ingeterVal = $("#ingeterMultiple3").val();
			if(countValue2 == "") {
				$("#commodityCount2").next().text("请输入数量！").show();
				flag = false;
			} else if (countValue2 <= 0 || !matchnum1.test(countValue2)) {
				$("#commodityCount2").next().text('数量必须大于0,且为正整数！').show();
				flag = false;
			} else {
				$("#commodityCount2").next().hide();
			}
		}
	} else if (ruleValue == 5) {
		
	}
	return flag;
}

//校验维度信息
function checkDimension(flag) {
	var accessMethodId = $("input[name=accessMethod]:checked").attr("id");
	if("accessMethod2" == accessMethodId) {
		var length = $("#selectedAccessMode ul li").length;
		if(length == 0) {
			$("#accessMethod2").next().text('请添加访问方式！').show();
			flag = false;
		} else {
			$("#accessMethod2").next().hide();
		}
	} else {
		$("#accessMethod2").next().hide();
	}
	
	var regionId = $("input[name=region]:checked").attr("id");
	if("region2" == regionId) {
		var length = $("#selectedArea ul li").length;
		var value = $("#selectedArea ul li").attr("value");
		if(length == 0 || value == 0) {
			$("#region2").next().text('请添加活动区域！').show();
			flag = false;
		} else {
			$("#region2").next().hide();
		}
	} else {
		$("#region2").next().hide();
	}
	
	var channelId=$("input[name=channel]:checked").attr("id");
	if("channel2" == channelId) {
		var length = $("#AddChannel ul li").length;
		if(length == 0) {
			$("#channel2").next().text('请添加活动渠道！').show();
			flag = false;
		} else {
			$("#channel2").next().hide();
		}
	} else {
		$("#channel2").next().hide();
	}
	
	
	return flag;
}


//校验商品(包含品牌，品类，单品)
function checkComodityBrandCategory(flag) {
	var categoryId=$("input[name=category]:checked").attr("id");
	var brandId=$("input[name=brand]:checked").attr("id");
	var commodityId = $("input[name=commodity]:checked").attr("id");
	if("category3" == categoryId && "brand3" == brandId && "commodity3" == commodityId) {
		$("#showInfomation").text("品类、品牌、单品必须选择一个");
		flag = false;
	} else {
		$("#showInfomation").hide();
	}
	if(categoryId == "category2") {
		var length = $("#selectedSorts ul li").length;
		if(length == 0) {
			$("#category2").next().text('请添加品类！').show();
			flag = false;
		} else {
			$("#category2").next().hide();
		}
	} else {
		$("#category2").next().hide();
	}
	if(brandId == "brand2") {
		var length = $("#AddBrand ul li").length;
		if(length == 0) {
			$("#brand2").next().text('请添加品牌！').show();
			flag = false;
		}else {
			$("#brand2").next().hide();
		}
	} else {
		$("#brand2").next().hide();
	}
	
	if(commodityId == "commodity2") {
		var length = $("#selectedSkus ul li").length;
		if(length == 0) {
			$("#commodity2").next().text('请添加单品！').show();
			flag = false;
		} else {
			$("#commodity2").next().hide();
		}
	} else {
		$("#commodity2").next().hide();
	}
	return flag;
}

//校验基本信息
function checkInformation() {
	var flag = true;
	var categoryName=$("#categoryName").val();
	if(categoryName == "") {
		 $("#categoryName").next().text('创建人员不能为空！').show();
		 flag = false;
	} else {
		$("#categoryName").next().hide();
	}
	var createBy=$("#createBy").val();
	if(createBy == "") {
		$("#createBy").next().text('创建部门不能位空！').show();
		flag = false;
	} else {
		$("#createBy").next().hide();
	}
	var ruleName=$("#ruleName").val();
	if (ruleName == ""  || ruleName.length > 100 || ruleName.leng <1) {
		$("#ruleName").next().text('活动名称必填，且不能超过100字！').show();
		flag = false;
	} else {
		$("#ruleName").next().hide();
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
    //校验限制次数
    var limittimes = $("#limittimes1").val();
    var matchnum =/^(\+|-)?\d+$/;
    if(null == limittimes || limittimes == undefined || limittimes == ""){
    	$("#limittimes1").next().text("限制次数必填").show();
    }else if(!matchnum.test(limittimes)){
    	$("#limittimes1").next().text("限制次数必须为正整数").show();
    }else if(limittimes.length > 18){
    	$("#limittimes1").next().text("限制次数最大不能超过18位").show();
    }else{
    	$("#limittimes1").next().hide();
    } 
    
    var ruleTerm=$("#ruleTerm").val();
    if(ruleTerm == 0) {
    	$("#ruleTerm").next().text('请选择活动类型！').show();
    	flag = false;
    } else {
    	$("#ruleTerm").next().hide();
    }
    flag = checkComodityBrandCategory(flag);
   flag =  checkDimension(flag);
	
   flag = checkOrderCondition(flag);
  flag = checkExecuteFormalization(flag);
	return flag;
}

// 设定活动会员
function AddMember() {
	$("#memberDiv .fr input:checked")
			.each(
					function() {
						var text = $(this).parent().find('span').text();
						var id = $(this).parent().attr("id");
						var flag = false;
						$("#AddMember ul li").each(function() {
							if ($(this).attr("id") == id) {
								flag = true;
							}
						});
						if (!flag) {
							var $li = $("<li id="
									+ id
									+ "><a><strong>"
									+ text
									+ "</strong><b></b> <input type='hidden' name='brandIds' value='"
									+ id + "'></a></li>");
							$("#AddMember ul").append($li);
						}
					});
	$("#memberDiv .fr input:checked").removeAttr("checked");
}

// 设定访问方式
function AddWay() {
	$("#Acmeth .fr input:checked")
			.each(
					function() {
						var text = $(this).parent().find('span').text();
						var id = $(this).parent().attr("id");
						var flag = false;
						$("#selectedAccessMode ul li").each(function() {
							if ($(this).attr("id") == id) {
								flag = true;
							}
						});
						if (!flag) {
							var $li = $("<li id="
									+ id
									+ "><a><strong>"
									+ text
									+ "</strong><b></b> <input type='hidden' name='brandIds' value='"
									+ id + "'</a></li>");
							var $li = $("<li id="
									+ id
									+ "><a>访问方式：<strong>"
									+ text
									+ "</strong><b></b> <input type='hidden' name='brandIds' value='"
									+ id + "'></a></li>");
							$("#selectedAccessMode ul").append($li);
						}
					});
	$("#Acmeth .fr input:checked").removeAttr("checked");
}

// 选择活动渠道
function AddChannel() {
	$("#channelDiv .fr input:checked").each(function() {
				var text = $(this).parent().find('span').text();
				var id = $(this).parent().attr("id");
				var flag = false;
				$("#AddChannel ul li").each(function() {
					if ($(this).attr("id") == id) {
						flag = true;
					}
				});
				if (!flag) {
					var $li = $("<li id="
							+ id
							+ "><a><strong>"
							+ text
							+ "</strong><b></b> <input type='hidden' name='brandIds' value='"
							+ id + "'</a></li>");
					$("#AddChannel ul").append($li);
				}
			});
	$("#channelDiv .fr input:checked").removeAttr("checked");
}



// 选择活动品牌
function AddBrand() {
	$("#BrandList a.active")
			.each(
					function() {
						var text = $(this).text();
						var id = $(this).attr("id");
						var flag = false;
						$("#AddBrand ul li").each(function() {
							if ($(this).attr("id") == id) {
								flag = true;
							}
						});
						if (!flag) {
							var $li = $("<li id="
									+ id
									+ "><a><strong>"
									+ text
									+ "</strong><b></b> <input type='hidden' name='brandIds' value='"
									+ id + "'</a></li>");
							$("#AddBrand ul").append($li);
						}
					});
	$("#BrandList a.active").toggleClass("active");
}

// 删除
// 设定活动商品
function addSkus() {
	$("#commodityList input:checked").each(function() {
		var prodName = $(this).parent().next().next().next().next().text();
		var skuName = $(this).parent().next().next().text();
		var skuId = $(this).attr("id");//单品ID
		var flag = false;
		var memberId = $("input[name=member]:checked").attr("id");
		if(memberId == "member1") {//b2b
			$("#selectedSkus ul li").each(function() {
				if ($(this).attr("id") == skuId) {
					flag = true;
				}
			});
		} else {//b2c
			var productFromMethod=$("#productFromMethod").val();
			if(productFromMethod !=  "1") {
				/*var length = $("#selectedSkus ul li").length;
				if(length == "1") {
					flag = true;
				}*/
			} else {
				$("#selectedSkus ul li").each(function() {
					if ($(this).attr("id") == skuId) {
						flag = true;
					}
				});
			}
			$("#selectedSkus ul li").each(function() {
				if ($(this).attr("id") == skuId) {
					flag = true;
				}
			});
		}
		
		if (!flag) {
			var $li = $("<li id='"
					+ skuId
					+ "'><a>活动商品：<strong>"
					+ prodName
					+ ":"
					+ skuName
					+ "</strong><b></b> <input type='hidden' name='skuIds' value='"
					+ skuId + "'></a></li>");
			$("#selectedSkus ul").append($li);
		}
	});
}

// 设定活动区域
function addAreas() {
	console.log(111);
	var provinceName = $("#province option:selected").text();
	console.log(provinceName);
	var provinceId = $("#province option:selected").val();
	console.log(provinceId);
	var cityName = $("#city option:selected").text();
	console.log(cityName);
	var cityId = $("#city option:selected").val();
	console.log(cityId);

	var flag = false;
	if (provinceId != 0 && cityId == 0) {// 只选择省的情况下只判断省是否重复
		$("#selectedArea ul li").each(function() {
			if ($(this).attr("id") == provinceId) {
				flag = true;
			}
		});
		if (!flag) {
			var $li = $("<li id='"
					+ provinceId
					+ "' value='"
					+ cityId
					+ "'><a>活动区域：<strong>"
					+ provinceName
					+ ":"
					+ cityName
					+ "</strong><b></b> <input type='hidden' name='skuIds' value='"
					+ provinceId + "'></a></li>");
			$("#selectedArea ul").append($li);
		}
	} else if (provinceId != 0 && cityId != 0) {// 省和市都选了的情况下先判断省，省如果已经选了，再判断市
		$("#selectedArea ul li").each(function() {
			if ($(this).attr("id") == provinceId) {
				if ($(this).attr("value") == 0) {// 已经添加了省，底下的市添加不了了
					flag = true;
				} else {
					if ($(this).attr("value") == cityId) {// 当前市已经添加过，不再添加
						flag = true;
					}
				}
			}
		});
		if (!flag) {
			var $li = $("<li id='"
					+ provinceId
					+ "' value='"
					+ cityId
					+ "'><a>活动区域：<strong>"
					+ provinceName
					+ ":"
					+ cityName
					+ "</strong><b></b> <input type='hidden' name='skuIds' value='"
					+ provinceId + "'></a></li>");
			$("#selectedArea ul").append($li);
		}
	} else {// 省、市都没选择

	}
}

// 设定活动品类
function addSorts() {
	var flag = false;
	var four = $("#categoryFour option:selected").val();
	if (four == 0) {
		var three = $("#categoryThree option:selected").val();
		if (three == 0) {
			var two = $("#categoryTwo option:selected").val();
			if (two == 0) {
				var one = $("#categoryOne option:selected").val();
				if (one == 0) {
					// 不做处理
				} else {// 添加一级品类
					$("#selectedSorts ul li").each(
							function() {
								if (newmachesexist($(this).attr("id"), one)
										|| newmachesexist(one, $(this).attr(
												"id"))) {
									flag = true;
								}
							});
					if (!flag) {
						var oneName = $("#categoryOne option:selected").text();
						var $li = $("<li id='"
								+ one
								+ "'><a>活动品类：<strong>"
								+ oneName
								+ "</strong><b></b> <input type='hidden' name='skuIds' value='"
								+ one + "'></a></li>");
						$("#selectedSorts ul").append($li);
					}
				}
			} else {// 添加二级品类
				$("#selectedSorts ul li")
						.each(
								function() {
									if (newmachesexist($(this).attr("id"), two)
											|| newmachesexist(two, $(this)
													.attr("id"))) {
										flag = true;
									}
								});
				if (!flag) {
					var twoName = $("#categoryTwo option:selected").text();
					var $li = $("<li id='"
							+ two
							+ "'><a>活动品类：<strong>"
							+ twoName
							+ "</strong><b></b> <input type='hidden' name='skuIds' value='"
							+ two + "'></a></li>");
					$("#selectedSorts ul").append($li);
				}
			}
		} else {// 添加三级品类
			$("#selectedSorts ul li").each(
					function() {
						if (newmachesexist($(this).attr("id"), three)
								|| newmachesexist(three, $(this).attr("id"))) {
							flag = true;
						}
					});
			if (!flag) {
				var threeName = $("#categoryThree option:selected").text();
				var $li = $("<li id='"
						+ three
						+ "'><a>活动品类：<strong>"
						+ threeName
						+ "</strong><b></b> <input type='hidden' name='skuIds' value='"
						+ three + "'></a></li>");
				$("#selectedSorts ul").append($li);
			}
		}
	} else {// 添加四级品类
		$("#selectedSorts ul li").each(
				function() {
					if (newmachesexist($(this).attr("id"), four)
							|| newmachesexist(four, $(this).attr("id"))) {
						flag = true;
					}
				});
		if (!flag) {
			var fourName = $("#categoryFour option:selected").text();
			var $li = $("<li id='"
					+ four
					+ "'><a>活动品类：<strong>"
					+ fourName
					+ "</strong><b></b> <input type='hidden' name='skuIds' value='"
					+ four + "'></a></li>");
			$("#selectedSorts ul").append($li);
		}
	}

}

function newmachesexist(newId, existedId) {
	var flag = false;
	var len = existedId.length;
	if (newId.length >= len) {
		var tempNewId = newId.substring(0, len);
		if (tempNewId == existedId) {
			flag = true;
		}
	}
	return flag;
}

function del() {
	$(this).remove();
}
/* 查询商品 */
// page 页数
function clickSubmit(page) {
	
	$("#commodityList").html("");
	//page = 1;
	var pro_array = new Array();
	var categoryOne = $("#categoryOneCommodity").val();
	var categoryTwo = $("#categoryTwoCommodity").val();
	var categoryThree = $("#categoryThreeCommodity").val();
	var categoryFour = $("#categoryFourCommodity").val();
	var supplier = $("#supplier2").val();
	var categoryCode = $("#categoryCode").val();
	var productCode = $("#productCode").val();
	var b2cType = $("input[name=member]:checked").val();
	pro_array.push("b2cType="+b2cType);
	if(categoryFour != 0) {
		pro_array.push("categoryId=" + categoryFour);
	} else if (categoryThree != 0) {
		pro_array.push("categoryId=" + categoryThree);
	} else if (categoryTwo != 0) {
		pro_array.push("categoryId=" + categoryTwo);
	} else if (categoryOne != 0) {
		pro_array.push("categoryId=" + categoryOne);
	} 
	if(supplier != "") {
		pro_array.push("supplierName=" + supplier);
	}
	if(categoryCode != "") {
		pro_array.push("categoryCode=" + categoryCode);
	}
	if(productCode != "") {
		pro_array.push("productCode=" + productCode);
	}
	
	if (page != "" && page != undefined) {
		pro_array.push("page=" + page);
	}
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/activitis/searchProduct",
		data : pro_array.join("&"),
		dataType : "html",
		success : function(msg) {
			$("#commodityList").html(msg);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("对不起，数据异常请稍后再试!");
		}

	});

}

//品牌分页
function brandPaging(page) {
	var pro_array = new Array();
	
	if (page != "" && page != undefined) {
		pro_array.push("page=" + page);
	}
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/activitis/searchBrand",
		data : pro_array.join("&"),
		dataType : "html",
		success : function(msg) {
			$("#commodityList").html(msg);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("对不起，数据异常请稍后再试!");
		}

	});

}

// 点击展开事件
function spread(val) {
	if (1 == val) {
		var display = document.getElementById("div1").style.display;
		if (display == "none") {
			$("#div1").show();
		} else {
			$("#div1").hide();
		}
	}
	if (2 == val) {
		var display = document.getElementById("div2").style.display;
		if (display == "none") {
			$("#div2").show();
		} else {
			$("#div2").hide();
		}
	}
	if (3 == val) {
		var display = document.getElementById("div3").style.display;
		if (display == "none") {
			$("#div3").show();
		} else {
			$("#div3").hide();
		}
	}
	if (4 == val) {
		var display = document.getElementById("div4").style.display;
		if (display == "none") {
			$("#div4").show();
		} else {
			$("#div4").hide();
		}
	}
	if (5 == val) {
		var display = document.getElementById("div5").style.display;
		if (display == "none") {
			$("#div5").show();
		} else {
			$("#div5").hide();
		}
	}
	if (6 == val) {
		var display = document.getElementById("div6").style.display;
		if (display == "none") {
			$("#div6").show();
		} else {
			$("#div6").hide();
		}
	}
	if (7 == val) {
		var display = document.getElementById("div7").style.display;
		if (display == "none") {
			$("#div7").show();
		} else {
			$("#div7").hide();
		}
	}
	if (8 == val) {
		var display = document.getElementById("div8").style.display;
		if (display == "none") {
			$("#div8").show();
		} else {
			$("#div8").hide();
		}
	}
	if (9 == val) {
		var display = document.getElementById("div9").style.display;
		if (display == "none") {
			$("#div9").show();
		} else {
			$("#div9").hide();
		}
	}
	if (10 == val) {
		var display = document.getElementById("div10").style.display;
		if (display == "none") {
			$("#div10").show();
		} else {
			$("#div10").hide();
		}
	}
	if (11 == val) {
		var dis = document.getElementById("div11").style.display;
		if (dis == "none") {
			$("#div11").show();
		} else {
			$("#div11").hide();
		}
		
		var displayState = document.getElementById("cateogryBrandCommodity").style.display;
		if(displayState == "none") {
			$("#cateogryBrandCommodity").show();
		} else {
			$("#cateogryBrandCommodity").hide();
		}
	}
}

//发货途径选择控制
function productFromMethodChange(val) {
	var ruleTerm=$("#ruleTerm").val();
	//if(ruleTerm != "3") {//不为限时直降
	var memberVal=$("input[name=member]:checked").val();
	if(memberVal == "4") {//B2C
		if(val == 1 || val == 31) {//国内发货或卓悦商品
			$("#selectCommodity").show();
			$("#selectBrand").show();
			$("#selectActiveCategory").show();
			//$("#commodity3").removeAttr("disabled");
			//$("#commodity1").removeAttr("disabled");
			$("#commodity3").attr("checked", "checked");
			$("#notAttend").show();
			$("#commodityDiv").hide();
		}else {
			$("#selectCommodity").show();
			$("#selectBrand").hide();
			//$("#selectActiveCategory").hide();
			$("#selectActiveCategory").show();
			$("#commodity3").attr("checked", "checked");
			//showCommodity();//部分选中的时候加载数据
			//$("#commodity3").attr("disabled", "disabled");
			//$("#commodity1").attr("disabled", "disabled");
			$("#notAttend").show();
			$("#commodityDiv").hide();
		}
	}
	//}
}

// 订单条件
function activityModel(val) {
	if(val == 1){//满减
		$("#ruleTerm").next().hide();
		$("#ordercondition1").show();
		$("#ordercondition2").hide();
		$("#ordercondition3").hide();
		$("#fullcutResult").show();
		$("#sendcoupons").hide();
		$("#repeatesendcoupons").hide();
		$("#repeatesendgifts").hide();
		$("#cutprice").hide();
		$("#sendgifts").hide();
		$("#notRepeateSendCoupon").hide();
		$("#notRepeateSendGifts").hide();
		//$("#ordercondition6").hide();
		$("#percentage").hide();
		$("#stepsReduce").show();
	}else if(val == 2){//满返
		$("#ruleTerm").next().hide();
		$("#ordercondition1").hide();
		$("#ordercondition2").show();
		$("#ordercondition3").show();
		$("#fullcutResult").hide();
		$("#sendcoupons").hide();
		$("#repeatesendcoupons").show();
		$("#repeatesendcoupons").find('div').each(function(){
			$(this).show();
		});
		$("#couponList").hide();
		$("#notRepeateSendCoupon").show();
		$("#notRepeateSendCoupon").find('div').each(function(){
			$(this).show();
		});
		$("#couponList1").hide();
		$("#repeatesendgifts").hide();
		$("#cutprice").hide();
		$("#sendgifts").hide();
		$("#notRepeateSendGifts").hide();
		//$("#ordercondition6").hide();
		$("#percentage").hide();
		$("#stepsReduce").hide();
	}else if(val == 3){//限时直降
		$("#ruleTerm").next().hide();
		$("#ordercondition1").hide();
		$("#ordercondition2").hide();
		$("#ordercondition3").hide();
		$("#fullcutResult").hide();
		$("#sendcoupons").hide();
		$("#repeatesendcoupons").hide();
		$("#repeatesendgifts").hide();
		$("#cutprice").show();
		$("#sendgifts").hide();
		$("#notRepeateSendCoupon").hide();
		$("#notRepeateSendGifts").hide();
		//$("#ordercondition6").show();
		$("#percentage").show();
		$("#stepsReduce").hide();
		
		$("#selectCommodity").show();
		$("#selectBrand").show();
		$("#selectActiveCategory").show();
	}else if(val == 4){//满赠
		$("#ruleTerm").next().hide();
		$("#ordercondition1").hide();
		$("#ordercondition2").show();
		$("#ordercondition3").show();
		$("#fullcutResult").hide();
		$("#sendcoupons").hide();
		$("#repeatesendcoupons").hide();
		$("#notRepeateSendCoupon").hide();
		$("#repeatesendgifts").show();
		$("#repeatesendgifts").find('div').each(function(){
			$(this).show();
		});
		$("#giftList").hide();
		$("#cutprice").hide();
		$("#sendgifts").hide();
		$("#notRepeateSendGifts").show();
		$("#notRepeateSendGifts").find('div').each(function(){
			$(this).show();
		});
		$("#giftList1").hide();
		//$("#ordercondition6").hide();
		$("#percentage").hide();
		$("#stepsReduce").hide();
	}else if(val == 5){//买赠
		$("#ruleTerm").next().hide();
		$("#ordercondition1").hide();
		$("#ordercondition2").hide();
		$("#ordercondition3").hide();
		$("#fullcutResult").hide();
		$("#sendcoupons").hide();
		$("#repeatesendcoupons").hide();
		$("#repeatesendgifts").hide();
		$("#notRepeateSendCoupon").hide();
		$("#cutprice").hide();
		$("#sendgifts").show();
		$("#sendgifts").find('div').each(function(){
			$(this).show();
		});
		$("#notRepeateSendGifts").hide();
		//$("#ordercondition6").hide();
		$("#percentage").hide();
		$("#stepsReduce").hide();
	}else{
		$("#ruleTerm").next().text("请选择活动类型").show();
		$("#ordercondition1").hide();
		$("#ordercondition2").hide();
		$("#ordercondition3").hide();
		$("#fullcutResult").hide();
		$("#sendcoupons").hide();
		$("#repeatesendcoupons").hide();
		$("#repeatesendgifts").hide();
		$("#cutprice").hide();
		$("#sendgifts").hide();
		$("#notRepeateSendCoupon").hide();
		$("#notRepeateSendGifts").hide();
		//$("#ordercondition6").hide();
		$("#percentage").hide();
		$("#stepsReduce").hide();
	}
}

//隐藏活动类型DIV
function concealActivityModelDiv() {
	$("#ordercondition1").hide();
	$("#ordercondition2").hide();
	$("#ordercondition3").hide();
	$("#ordercondition4").hide();
	$("#ordercondition5").hide();
	$("#ordercondition7").hide();
}

$(function() {
	showCont();
	$("input[name=member]").click(function() {
		showCont();
	});
	showAccessMethod();
	$("input[name=accessMethod]").click(function() {
		showAccessMethod();
	});

	showCategory();
	$("input[name=category]").click(function() {
		showCategory();
	});

	showBrand();
	$("input[name=brand]").click(function() {
		showBrand();
	});

	showRegion();
	$("input[name=region]").click(function() {
		showRegion();
	});

	showChannel();
	$("input[name=channel]").click(function() {
		showChannel();
	});

	showCommodity();
	$("input[name=commodity]").click(function() {
		showCommodity();
	});

});

// 活动会员
function showCont() {
	switch ($("input[name=member]:checked").attr("id")) {
	case "member1"://B2B会员
		//单品是否显示的相关控制
		$("#selectCommodity").show();
		$("#selectBrand").show();
		$("#selectActiveCategory").show();
		$("#commodity3").removeAttr("disabled");
		$("#commodity1").removeAttr("disabled");
		$("#commodity3").attr("checked", "checked");
		showCommodity();
		$("#notAttend").show();
		//优惠券是否清空的相关控制
		clearCoupon();
		break;
	case "member2"://B2C会员
		var ruleTerm=$("#ruleTerm").val();
		if(ruleTerm != "3") {//不为限时直降
			var fromMethodVal = $("#productFromMethod option:selected").val();
			if(fromMethodVal == "1" || fromMethodVal == "31") {//国内发货或卓悦商品
				//单品是否显示的相关控制
				$("#selectCommodity").show();
				$("#selectBrand").show();
				$("#selectActiveCategory").show();
				$("#commodity3").removeAttr("disabled");
				$("#commodity1").removeAttr("disabled");
				$("#commodity3").attr("checked", "checked");
				$("#notAttend").show();
			} else {
				//单品是否显示的相关控制
				$("#selectCommodity").show();
				$("#selectBrand").hide();
				$("#selectActiveCategory").show();
				$("#commodity3").attr("checked", "checked");
				//showCommodity();//部分选中的时候加载数据
				$("#notAttend").show();
			}
		}
		//优惠券是否清空的相关控制
		clearCoupon();
		break;
	default:
		break;
	}
}
//优惠券清空
function clearCoupon() {
	var sendCouponType=$("input[name=sendCouponType]:checked").val();
	//if(sendCouponType == "1") {
		var lengthCommon = $("#list1 tbody tr").length;
		if(lengthCommon > 1) {
			$("#list1").html("");//先清空
			$li = $("<tbody><tr id='0'><th width='40px;'>阶梯</th><th width='200px;'>优惠券名称</th><th width='200px;'>优惠券规则名称</th><th style='display: none;'></th><th width='100px;'>赠送数量</th><th width='150px;'>操作</th></tr></tbody>");
			$("#list1").append($li);		
		}
	//} else if(sendCouponType == "2") {
		var lengthLadder = $("#list2 tbody tr").length;
		if(lengthLadder > 1) {
			$("#list2").html("");//先清空
			$li = $("<tbody><tr id='0'><th width='40px;'>序号</th><th width='80px;'>起点数量</th><th width='80px;'>阶点数量</th><th width='80px;'>起点金额</th><th width='80px;'>阶点金额</th><th width='100px;'>优惠劵名称</th><th width='100px;'>优惠劵规则名称</th><th style='display: none;'></th><th width='100px;'>赠送数量</th><th width='120px;'>操作</th></tr></tbody>");
			$("#list2").append($li);		
		}
	//}
}

// 活动方式
function showAccessMethod() {
	switch ($("input[name=accessMethod]:checked").attr("id")) {
	case "accessMethod1":
		$("#accessMethodDiv").hide();
		break;
	case "accessMethod2":
		$("#accessMethodDiv").show();
		break;
	default:
		break;
	}
}

// 活动品类
function showCategory() {
	switch ($("input[name=category]:checked").attr("id")) {
	case "category1":
		$("#categoryDiv").hide();
		break;
	case "category2":
		$("#categoryDiv").show();
		break;
	case "category3":
		$("#categoryDiv").hide();
		break;
	default:
		break;
	}
}

// 活动品牌
function showBrand() {
	switch ($("input[name=brand]:checked").attr("id")) {
	case "brand1":
		$("#brandDiv").hide();
		break;
	case "brand2":
		$("#brandDiv").show();
		break;
	case "brand3":
		$("#brandDiv").hide();
		break;
	default:
		break;
	}
}

// 活动区域
function showRegion() {
	switch ($("input[name=region]:checked").attr("id")) {
	case "region1":
		$("#regionDiv").hide();
		break;
	case "region2":
		$("#regionDiv").show();
		break;
	default:
		break;
	}
}

// 活动渠道
function showChannel() {
	switch ($("input[name=channel]:checked").attr("id")) {
	case "channel1":
		$("#channelDiv").hide();
		break;
	case "channel2":
		$("#channelDiv").show();
		break;
	default:
		break;
	}
}

// 活动商品
function showCommodity() {
	switch ($("input[name=commodity]:checked").attr("id")) {
	case "commodity1":
		$("#commodityDiv").hide();
		break;
	case "commodity2":
		clickSubmit(1);
		$("#commodityDiv").show();
		break;
	case "commodity3":
		$("#commodityDiv").hide();
		break;
	default:
		break;
	}
}

//满赠时查询品类二级目录(赠送单品不等比)
function selectCategoryFullPresent1(val) {
	if (val != 0) {
		$
				.ajax({
					type : "post",
					url : CONTEXTPATH + "/activitis/searchCategoryByPid?"
							+ "pid=" + val,
					dataType : "json",
					success : function(data) {
						if (data != null) {
							document.getElementById("categoryTwoFullPresent1").options.length = 1;
							document.getElementById("categoryThreeFullPresent1").options.length = 1;
							document.getElementById("categoryFourFullPresent1").options.length = 1;
							for ( var obj in data) {
								var catePubId = data[obj].catePubId;
								var pubNameCn = data[obj].pubNameCn;
								var $li = $("<option value=" + catePubId + ">"
										+ pubNameCn + "</option>");
								$("#categoryTwoFullPresent1").append($li);
							}
						}
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert("对不起，数据异常请稍后再试!");
					}
				});

	} else {
		document.getElementById("categoryTwoFullPresent1").options.length = 1;
		document.getElementById("categoryThreeFullPresent1").options.length = 1;
		document.getElementById("categoryFourFullPresent1").options.length = 1;
	}
}

//满赠时选择二级品类，查询三级级品类(赠送单品不等比)
function selectCategoryTwoFullPresent1(val) {
	if (val != 0) {
		$
				.ajax({
					type : "post",
					url : CONTEXTPATH + "/activitis/searchCategoryByPid?"
							+ "pid=" + val,
					dataType : "json",
					success : function(data) {
						if (data != null) {
							document.getElementById("categoryThreeFullPresent1").options.length = 1;
							document.getElementById("categoryFourFullPresent1").options.length = 1;
							for ( var obj in data) {
								var catePubId = data[obj].catePubId;
								var pubNameCn = data[obj].pubNameCn;
								var $li = $("<option value=" + catePubId + ">"
										+ pubNameCn + "</option>");
								$("#categoryThreeFullPresent1").append($li);
							}
						}
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert("对不起，数据异常请稍后再试!");
					}

				});

	} else {
		document.getElementById("categoryThreeFullPresent1").options.length = 1;
		document.getElementById("categoryFourFullPresent1").options.length = 1;
	}
}

//满赠时 选择三级品类，查询四级级品类(赠送单品不等比)
function selectCategoryThreeFullPresent1(val) {
	if (val != 0) {
		$.ajax({
			type : "post",
			url : CONTEXTPATH + "/activitis/searchCategoryByPid?" + "pid="
					+ val,
			dataType : "json",
			success : function(data) {
				if (data != null) {
					document.getElementById("categoryFourFullPresent1").options.length = 1;
					for ( var obj in data) {
						var catePubId = data[obj].catePubId;
						var pubNameCn = data[obj].pubNameCn;
						var $li = $("<option value=" + catePubId + ">"
								+ pubNameCn + "</option>");
						$("#categoryThreeFullPresent1").append($li);
					}
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("对不起，数据异常请稍后再试!");
			}

		});

	}
}

//满赠时查询品类二级目录
function selectCategoryFullPresent(val) {
	if (val != 0) {
		$
				.ajax({
					type : "post",
					url : CONTEXTPATH + "/activitis/searchCategoryByPid?"
							+ "pid=" + val,
					dataType : "json",
					success : function(data) {
						if (data != null) {
							document.getElementById("categoryTwoFullPresent").options.length = 1;
							document.getElementById("categoryThreeFullPresent").options.length = 1;
							document.getElementById("categoryFourFullPresent").options.length = 1;
							for ( var obj in data) {
								var catePubId = data[obj].catePubId;
								var pubNameCn = data[obj].pubNameCn;
								var $li = $("<option value=" + catePubId + ">"
										+ pubNameCn + "</option>");
								$("#categoryTwoFullPresent").append($li);
							}
						}
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert("对不起，数据异常请稍后再试!");
					}
				});

	} else {
		document.getElementById("categoryTwoFullPresent").options.length = 1;
		document.getElementById("categoryThreeFullPresent").options.length = 1;
		document.getElementById("categoryFourFullPresent").options.length = 1;
	}
}

//满赠时选择二级品类，查询三级级品类
function selectCategoryTwoFullPresent(val) {
	if (val != 0) {
		$
				.ajax({
					type : "post",
					url : CONTEXTPATH + "/activitis/searchCategoryByPid?"
							+ "pid=" + val,
					dataType : "json",
					success : function(data) {
						if (data != null) {
							document.getElementById("categoryThreeFullPresent").options.length = 1;
							document.getElementById("categoryFourFullPresent").options.length = 1;
							for ( var obj in data) {
								var catePubId = data[obj].catePubId;
								var pubNameCn = data[obj].pubNameCn;
								var $li = $("<option value=" + catePubId + ">"
										+ pubNameCn + "</option>");
								$("#categoryThreeFullPresent").append($li);
							}
						}
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert("对不起，数据异常请稍后再试!");
					}

				});

	} else {
		document.getElementById("categoryThreeFullPresent").options.length = 1;
		document.getElementById("categoryFourFullPresent").options.length = 1;
	}
}

//满赠时 选择三级品类，查询四级级品类
function selectCategoryThreeFullPresent(val) {
	if (val != 0) {
		$.ajax({
			type : "post",
			url : CONTEXTPATH + "/activitis/searchCategoryByPid?" + "pid="
					+ val,
			dataType : "json",
			success : function(data) {
				if (data != null) {
					document.getElementById("categoryFourFullPresent").options.length = 1;
					for ( var obj in data) {
						var catePubId = data[obj].catePubId;
						var pubNameCn = data[obj].pubNameCn;
						var $li = $("<option value=" + catePubId + ">"
								+ pubNameCn + "</option>");
						$("#categoryThreeFullPresent").append($li);
					}
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("对不起，数据异常请稍后再试!");
			}

		});

	}
}

//单品查询条件，选择品类一级目录，查询二级目录
function selectCategoryCommodity(val) {
	if (val != 0) {
		$
				.ajax({
					type : "post",
					url : CONTEXTPATH + "/activitis/searchCategoryByPid?"
							+ "pid=" + val,
					dataType : "json",
					success : function(data) {
						if (data != null) {
							document.getElementById("categoryTwoCommodity").options.length = 1;
							document.getElementById("categoryThreeCommodity").options.length = 1;
							document.getElementById("categoryFourCommodity").options.length = 1;
							for ( var obj in data) {
								var catePubId = data[obj].catePubId;
								var pubNameCn = data[obj].pubNameCn;
								var $li = $("<option value=" + catePubId + ">"
										+ pubNameCn + "</option>");
								$("#categoryTwoCommodity").append($li);
							}
						}
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert("对不起，数据异常请稍后再试!");
					}
				});

	} else {
		document.getElementById("categoryTwoCommodity").options.length = 1;
		document.getElementById("categoryThreeCommodity").options.length = 1;
		document.getElementById("categoryFourCommodity").options.length = 1;
	}
}
//单品查询条件，选择品类二级目录，查询三级目录
function selectCategoryTwoCommodity(val) {
	if (val != 0) {
		$.ajax({
					type : "post",
					url : CONTEXTPATH + "/activitis/searchCategoryByPid?"
							+ "pid=" + val,
					dataType : "json",
					success : function(data) {
						if (data != null) {
							document.getElementById("categoryThreeCommodity").options.length = 1;
							document.getElementById("categoryFourCommodity").options.length = 1;
							for ( var obj in data) {
								var catePubId = data[obj].catePubId;
								var pubNameCn = data[obj].pubNameCn;
								var $li = $("<option value=" + catePubId + ">"
										+ pubNameCn + "</option>");
								$("#categoryThreeCommodity").append($li);
							}
						}
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert("对不起，数据异常请稍后再试!");
					}

				});

	} else {
		document.getElementById("categoryThreeCommodity").options.length = 1;
		document.getElementById("categoryFourCommodity").options.length = 1;
	}
}
//单品查询条件，选择品类三级目录，查询四级目录
function selectCategoryThreeCommodity(val) {
	if (val != 0) {
		$.ajax({
			type : "post",
			url : CONTEXTPATH + "/activitis/searchCategoryByPid?" + "pid="
					+ val,
			dataType : "json",
			success : function(data) {
				if (data != null) {
					document.getElementById("categoryFourCommodity").options.length = 1;
					for ( var obj in data) {
						var catePubId = data[obj].catePubId;
						var pubNameCn = data[obj].pubNameCn;
						var $li = $("<option value=" + catePubId + ">"
								+ pubNameCn + "</option>");
						$("#categoryThreeCommodity").append($li);
					}
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("对不起，数据异常请稍后再试!");
			}

		});

	}
}

//查询品类二级目录
function selectCategory(val) {
	if (val != 0) {
		$
				.ajax({
					type : "post",
					url : CONTEXTPATH + "/activitis/searchCategoryByPid?"
							+ "pid=" + val,
					dataType : "json",
					success : function(data) {
						if (data != null) {
							document.getElementById("categoryTwo").options.length = 1;
							document.getElementById("categoryThree").options.length = 1;
							document.getElementById("categoryFour").options.length = 1;
							for ( var obj in data) {
								var catePubId = data[obj].catePubId;
								var pubNameCn = data[obj].pubNameCn;
								var $li = $("<option value=" + catePubId + ">"
										+ pubNameCn + "</option>");
								$("#categoryTwo").append($li);
							}
						}
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert("对不起，数据异常请稍后再试!");
					}
				});

	} else {
		document.getElementById("categoryTwo").options.length = 1;
		document.getElementById("categoryThree").options.length = 1;
		document.getElementById("categoryFour").options.length = 1;
	}
}

// 选择二级品类，查询三级级品类
function selectCategoryTwo(val) {
	if (val != 0) {
		$
				.ajax({
					type : "post",
					url : CONTEXTPATH + "/activitis/searchCategoryByPid?"
							+ "pid=" + val,
					dataType : "json",
					success : function(data) {
						if (data != null) {
							document.getElementById("categoryThree").options.length = 1;
							document.getElementById("categoryFour").options.length = 1;
							for ( var obj in data) {
								var catePubId = data[obj].catePubId;
								var pubNameCn = data[obj].pubNameCn;
								var $li = $("<option value=" + catePubId + ">"
										+ pubNameCn + "</option>");
								$("#categoryThree").append($li);
							}
						}
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert("对不起，数据异常请稍后再试!");
					}

				});

	} else {
		document.getElementById("categoryThree").options.length = 1;
		document.getElementById("categoryFour").options.length = 1;
	}
}

// 选择三级品类，查询四级级品类
function selectCategoryThree(val) {
	if (val != 0) {
		$.ajax({
			type : "post",
			url : CONTEXTPATH + "/activitis/searchCategoryByPid?" + "pid="
					+ val,
			dataType : "json",
			success : function(data) {
				if (data != null) {
					document.getElementById("categoryFour").options.length = 1;
					for ( var obj in data) {
						var catePubId = data[obj].catePubId;
						var pubNameCn = data[obj].pubNameCn;
						var $li = $("<option value=" + catePubId + ">"
								+ pubNameCn + "</option>");
						$("#categoryThree").append($li);
					}
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("对不起，数据异常请稍后再试!");
			}

		});

	}
}

// 用选择的省查询市
function selectProvices(val) {
	var val = Number(val);
	if (val != 0) {
		$.ajax({
			type : "post",
			url : CONTEXTPATH + "/activitis/searchCityByProviceId?" + "pid="
					+ val,
			dataType : "json",
			success : function(data) {
				if (data != null) {
					document.getElementById("city").options.length = 1;
					for ( var obj in data) {
						var cityid = data[obj].cityid;
						var cityname = data[obj].cityname;
						var $li = $("<option value=" + cityid + ">" + cityname
								+ "</option>");
						$("#city").append($li);
					}
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("对不起，数据异常请稍后再试!");
			}

		});

	} else {
		document.getElementById("city").options.length = 1;
	}
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

