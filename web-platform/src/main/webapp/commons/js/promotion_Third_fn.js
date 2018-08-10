var  CONTEXTPATH  = $("#conPath").val();
/*	商品js	*/
	$(document).ready(function(){
		
		clickSubmit(1);
		
	});

	/*	查询条件	*/
	//page  页数  istate 是否上架 0 否，1 是  statu 
	//审核状态:0-待审核/审核中 1-审核不通过 2-审核通过 3-无效记录 4-审核中、5 新增的商品（待修改）
	function clickSubmit(page){
		/* 获取选中类目 */
		
		/* 促销信息 */
		var promotionId = $("#promotionId").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		
		
		var pro_array  = new Array();
		if(promotionId!=""){
			pro_array.push("promotionId="+promotionId);
		}
		if(startTime!=""){
			pro_array.push("startTime="+startTime);
		}
		if(endTime!=""){
			pro_array.push("endTime="+endTime);
		}
		if(page!=""&&page!=undefined){
			pro_array.push("page="+page);
		}
		
		
		var skuIdListCkecked = skus_checked();
		if(skuIdListCkecked!=""){
			pro_array.push(skuIdListCkecked);
		}
		$.ajax({
			type : "post", 
			url : CONTEXTPATH+"/createPromotions/selectPromotionSKUList", 
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
	

	var skus_checked = function(){
		var skus = new Array();
		var skuchecked = new Array();
		$("input[type='checkbox'][name='skuId']").each(function() {
			var skuId = $(this).val();
			if ($(this).attr("checked") == "checked") { // 全选 
				skuchecked.push("ischeckeds="+true);
			}else{
				skuchecked.push("ischeckeds="+false);
			}
			skus.push("skuIdList="+skuId);
		}); 
		
		return skus.join("&")+"&"+skuchecked.join("&");
	};
	

	  function verificationstock(event){
	    	$(".tooltip").remove();
	    	var that = event.target?event.target:event.srcElement;
	    	var  matchnum = /^[0-9]*$/;
	    	var value = Number(that.value);
	    	var total = Number($(that).attr("comparedata"));
	    	var pic = $('<div class="tooltip"><span style="width:118px; display:block; line-height:25px; border:1px solid red;color:red; margin:5px 0 5px 0;"></span></div>');
	    	if (matchnum.test(that.value)){
	    		if(value > total){
	    			that.value = "0";
	    			$(pic).find("span").text("超过了可用库存!");
	    			$(that).closest("td").append(pic);
	    			that.focus();		
	    		}				

	    	}else{
	    		$(pic).find("span").text("分配库存错误!");
				$(that).closest("td").append(pic);
	    		that.value = "0";
	    		that.focus();
	    	}
	    }