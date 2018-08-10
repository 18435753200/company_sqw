$(document).ready(function(){
	$(".c3").delegate("input","click",jiesuan);
	jiesuan();
});

//全选
$(function() {
    $("#quanxuan").click(function() {
         $('input[name="ck"]').attr("checked",this.checked); 
         jiesuan();
     });
     var $subBox = $("input[name='ck']");
     $subBox.click(function(){
         $("#quanxuan").attr("checked",$subBox.length == $("input[name='ck']:checked").length ? true : false);
     });
 });

function jiesuan(){
	var checked = $(".c3 input:checked");
	var minnum = $("#minqty").val();
	var Orderqty = 0;
	var orderTotal=0;
	var orderPrice = 0;
	var pid = $("#pid").val();
	var istidu =$("#istidu").val(); 
	var num=0;
	var money =0;
	//$(".zhifu b").text(orderTotal.toFixed(2));
	if(istidu==""||istidu==null){
		var orderIds = '';
		$.each(checked,function(i,item){
			orderIds+=$(this).val()+',';
			var qty = Number($(item).closest(".two1").find(".s2").text());
			var skutotalMoney=0.00;
			if(!isNaN(qty)){
				Orderqty += qty;
			}
			skutotalMoney = Number($(item).closest(".two1").find("#skutotalMoney").val());
			if(!isNaN(skutotalMoney)){
				orderTotal=orderTotal+skutotalMoney;
				//$(".zhifu b").text(orderTotal.toFixed(2));
				$(".totalNum b").text(Orderqty);
			}
		});
		if(orderIds!=''){
			orderIds=orderIds.substring(0, orderIds.length-1);
	    }
		$.ajax({ 
		     type : "post", 
		     url : "../waitOrder/totalNumByCount",
		     data:"Orderqty="+Orderqty+"&pid="+pid+"&orderIds="+orderIds,
		     dataType:"json",
		     success : function(msg) {
		    	 if(msg==null){
		    		 $(".zhifu b").text((0).toFixed(4));
		    	 }
		    	 $("#skuPrice tr:gt(0)").remove();
		    	 $.each(msg,function(key,value){
		    		 var tbBody = "";
		    		 var trColor = "";
		    		 var tbTr = "";
		    		 if (key % 2 == 0) {
                         trColor = "even";
                     }
                     else {
                         trColor = "odd";
                     }
		    		 var price = value.dto.supplierProductPriceMap.supplierprice;
		    		 tbBody += "<tr class='" + trColor + "'><td>" + value.dto.supplierProductSku.skuId + "</td>" + "<td>" +value.dto.supplierProductSku.skuNameCn + "</td>" + "<td>"+" " + price.toFixed(4) + "</td>"+ "<td>" + value.count + "</td>"+"<td>" +""+ (price*value.count).toFixed(4) + "</td></tr>";
		    		 money +=Number((price*value.count).toFixed(4));
		    		 $("#skuPrice").append(tbBody);
			     });
		    	 $(".zhifu b").text(money.toFixed(4));
			 },
			 error:function(jqXHR,textStatus,errorThrown){
			  	alert("----网络异常,请稍后再试。。。。");
			 }
	     });
	}else{
		$.each(checked,function(i,item){
			var orderIds = '';
			orderIds+=$(this).val()+',';
			if(orderIds!=''){
				orderIds=orderIds.substring(0, orderIds.length-1);
		    }
			var qty = Number($(item).closest(".two1").find(".s2").text());
			if(!isNaN(qty)&&!isNaN(Orderqty)){
				Orderqty += qty;
			}
		});
		$.ajax({ 
		     async:false,
		     type : "post", 
		     url : "../waitOrder/totalNumByCount",
		     data:"Orderqty="+Orderqty+"&pid="+pid+"&orderIds="+orderIds,
		     dataType:"html",
		     success : function(msg) {
		    	 if(msg==""){
		    		 $(".zhifu b").text(num.toFixed(4));
		    	 }else{
		    		 $(".zhifu b").text((Number(msg)*Orderqty).toFixed(4));
		    		 $(".totalNum b").text(Orderqty);
		    	 }
			 },
			 error:function(jqXHR,textStatus,errorThrown){
			  	alert("网络异常,请稍后再试。。。。");
			 }
	     });
	}
	if(Orderqty <minnum){
		$(".zhifu input").attr("disabled","disabled");
		$(".zhifu input").css("background","none repeat scroll 0 0 #d3d3d3");	
	}else{
		$(".zhifu input").attr("disabled",false);
		$(".zhifu input").css("background","none repeat scroll 0 0 #f64c02");
	}
}

