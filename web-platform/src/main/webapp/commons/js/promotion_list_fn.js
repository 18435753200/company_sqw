var  CONTEXTPATH  = $("#conPath").val();
/*	商品js	*/
	$(document).ready(function(){
		$("#productId").change(function(){
			var productId=$("#productId").val();
			var  matchnum = /^[0-9]*$/;
			if(!matchnum.test(productId)){
				alert("促销ID只能是数字！");
				$("#productId").val("");
				$("#productId").focus();
			}
		});
		clickSubmit(1);
		
	});

	
	/*	label跳转	*/	
	function getProductByLabel(isOverDate){
		$.ajax({
			type : "post", 
			url  : CONTEXTPATH+"/promotions/getPromotionsListByConditions", 
			data : "isOverDate="+isOverDate,
			dataType: "html",
			success : function(msg) {
				$(".cont").html(msg);
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("对不起，数据异常请稍后再试!");
			}
		}); 
			
	}
	/*	查询条件	*/
	//page  页数  istate 是否上架 0 否，1 是  statu 
	//审核状态:0-待审核/审核中 1-审核不通过 2-审核通过 3-无效记录 4-审核中、5 新增的商品（待修改）
	function clickSubmit(page){
		/* 获取选中类目 */
		
		/* 促销信息 */
		var promotionName = $.trim($("#promotionName").val());
		var promotionId = $("#promotionId").val();
		var type = $("#promotiontype").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		
		var isOverDate = checkLabelStatu();
		
		
		var  matchnum = /^[0-9]*$/;
		if(!matchnum.test(promotionId)){
			alert("促销ID只能是数字！");
			$("#promotionId").val("");
			$("#promotionId").focus();
			return false;
		}
		
		var pro_array  = new Array();
		if(promotionName!=""){
			pro_array.push("promotionName="+promotionName);
		}
		if(promotionId!=""){
			pro_array.push("promotionId="+promotionId);
		}
		if(type!=""){
			pro_array.push("type="+type);
		}
		if(startTime!=""){
			pro_array.push("startTime="+startTime);
		}
		if(endTime!=""){
			pro_array.push("endTime="+endTime);
		}
		if(isOverDate!=""){
			pro_array.push("isOverDate="+isOverDate);
		}
		if(page!=""&&page!=undefined){
			pro_array.push("page="+page);
		}
		
		
		$.ajax({
			type : "post", 
			url : CONTEXTPATH+"/promotions/getPromotionsListByConditions", 
			data:pro_array.join("&"),
			dataType:"html",
			success : function(msg) {
				$(".cont").html(msg);
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("对不起，数据异常请稍后再试!");
			}
		}); 
		
			
	}
	
	var checkLabelStatu = function (){
		var statu = "";
		$("#promotionstatu > li").each(function(){
			var attr=$(this).attr("class");
			if(attr=="list"&&$(this).find("a").text()=="进行中的促销"){
				statu="1";
			}
			if(attr=="list"&&$(this).find("a").text()=="已结束的促销"){
				statu="2";
			}
			if(attr=="list"&&$(this).find("a").text()=="未完成的促销"){
				statu="3";
			}
		});
		return statu;
	};
