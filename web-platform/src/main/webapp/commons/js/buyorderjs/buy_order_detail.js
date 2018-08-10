$(document).ready(function(){
    //根据状态加载订单状态的图片
	//判断加载订单状态为1则加载x13.png图片（已下单,待支付）
	var status = $("#orderStatus").val();
	if(status==1){
		$("#p2 img").attr("src","../commons/images/x13.png");
		$("#purOrderStatus").html("等待分配订单");
	}
	if(status==21){
		$("#p2 img").attr("src","../commons/images/x14.png");
		$("#purOrderStatus").html("已分单待下单");
	}
	if(status==31){
		$("#p2 img").attr("src","../commons/images/x14.png");
		$("#purOrderStatus").html("已提交采购单");
	}
	if(status==32){
		$("#p2 img").attr("src","../commons/images/x14.png");
		$("#purOrderStatus").html("经销商已填写合同");
	}
	if(status==33){
		$("#p2 img").attr("src","../commons/images/x14.png");
		$("#purOrderStatus").html("供应商已填写合同");
	}
	if(status==34){
		$("#p2 img").attr("src","../commons/images/x14.png");
		$("#purOrderStatus").html("经销商已确认合同");
	}
	if(status==62){
		$("#p2 img").attr("src","../commons/images/x15.png");
		$("#purOrderStatus").html("供应商已确认,待发货");
	}
	//判断加载订单状态为5则加载x15.png图片（已发货）
	if(status==71){
		$("#p2 img").attr("src","../commons/images/x16.png");
		$("#purOrderStatus").html("供应商已发货");
	}
	//判断加载订单状态为6则加载x16.png图片（已收货）
	if(status==81){
		$("#p2 img").attr("src","../commons/images/x17.png");
		$("#purOrderStatus").html("经销商已收货");
	}
});