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
		/*  加载一级目录  */
		$.ajax({
			type : "post", 
			url : CONTEXTPATH+"/product/getFirstDisp", 
			success : function(msg) { 
				$.each(eval(msg),function(i,n){
					$("#firstcategory").append("<option value='"+n.catePubId+"'>"+n.pubNameCn+"</option>");
				});	
			}
		}); 
			 
			
			
		/* 加载二级目录 */
		$("#firstcategory").change(function(){
			$("#secondcategory").empty();
			$("#thirdcategory").empty();
			$("#fourthcategory").empty();
			$.ajax({
				 type : "post", 
	         	 url : CONTEXTPATH+"/product/getOtherDisp", 
	         	 data:"parCateDispId="+$(this).val(),
	         	 success : function(msg) { 
	         		 var fistdisplist="<option value=''>请选择</option>";
	         		 $.each(eval(msg),function(i,n){
	         			 fistdisplist+="<option value='"+n.catePubId+"'>"+n.pubNameCn+"</option>";
	         		 });	
	         		 $("#secondcategory").append(fistdisplist);
	         	 }
			}); 
		});
		
		
		/* 加载三级目录 */
		$("#secondcategory").change(function(){
			$("#thirdcategory").empty();
			$("#fourthcategory").empty();
			$.ajax({
				type : "post", 
				url : CONTEXTPATH+"/product/getOtherDisp", 
				data:"parCateDispId="+$(this).val(),
				success : function(msg) { 
					var fistdisplist="<option value=''>请选择</option>";
					$.each(eval(msg),function(i,n){
						fistdisplist+="<option value='"+n.catePubId+"'>"+n.pubNameCn+"</option>";
					});	
					$("#thirdcategory").append(fistdisplist);
				}
			}); 
		});
		
		/* 加载四级目录 */	
		$("#thirdcategory").change(function(){
			$("#fourthcategory").empty();
			$.ajax({
				type : "post", 
				url : CONTEXTPATH+"/product/getOtherDisp", 
				data:"parCateDispId="+$(this).val(),
				success : function(msg) { 
	            var fistdisplist="<option value=''>请选择</option>";
	            $.each(eval(msg),function(i,n){
	            	fistdisplist+="<option value='"+n.catePubId+"'>"+n.pubNameCn+"</option>";
	            });	
	            $("#fourthcategory").append(fistdisplist);
				}
			}); 
		});
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
		
		/* 商品信息 */
		var  productName = $.trim($("#productName").val());
		var  suppliername = $.trim($("#suppliername").val());
		var  productId = $.trim($("#productId").val());

		var  cate = getCate();
		
		var  matchnum = /^[0-9]*$/;
		if(!matchnum.test(productId)){
			alert("商品ID只能是数字！");
			$("#productId").val("");
			$("#productId").focus();
			return false;
		}
		
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
		
		if(productName!=""){
			pro_array.push("productName="+productName);
		}
		if(suppliername!=""){
			pro_array.push("suppliername="+suppliername);
		}
		if(productId!=""){
			pro_array.push("productId="+productId);
		}
		if(cate!=""&&cate!=undefined){
			pro_array.push("cate="+cate);
		}
		
		var skuIdListCkecked = skus_checked();
		if(skuIdListCkecked!=""){
			pro_array.push(skuIdListCkecked);
		}
		$.ajax({
			type : "post", 
			url : CONTEXTPATH+"/createPromotions/getProductListByConditions", 
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
	
	function toPromotionsPartThird(){
		var promotionId = $("#promotionId").val();
		
		var pro_array  = new Array();
		if(promotionId!=""){
			pro_array.push("promotionId="+promotionId);
		}
		var skuIdListCkecked = skus_checked();
		if(skuIdListCkecked!=""){
			pro_array.push(skuIdListCkecked);
		}
		$.ajax({
			type : "post", 
			url : CONTEXTPATH+"/createPromotions/saveProductSKU", 
			data:pro_array.join("&"),
			success : function(msg) {
				if(msg=='-1'){
					alert("数据异常.建议重新创建促销活动!");
				}else if(msg=='-2'){
					alert("先选择本次要促销的商品哦!");
				}else if(msg=='-3'){
					alert("对不起!请求超时.");
				}else if(msg=='-4'){
					alert("服务器忙.请重新操作!");
				}else if(msg=='-5'){
					alert("数据异常,建议重新创建促销活动!");
				}else{
					/* 促销信息 */
					var promotionId = $("#promotionId").val();
					var startTime = $("#startTime").val();
					var endTime = $("#endTime").val();
					if(promotionId!=""){
						window.location.href=CONTEXTPATH+"/createPromotions/toPromotionsPartThird?promotionId="+promotionId+"&startTime="+startTime+"&endTime="+endTime;
					}else{
						alert("数据异常,建议重新创建促销活动!");
					}

				}
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
	
	function getCate(){
		
		var cateId = "";
		var firstcategory =$("#firstcategory").val();
		var secondcategory = $("#secondcategory").val();
		var  thirdcategory = $("#thirdcategory").val();
		var fourthcategory  = $("#fourthcategory").val();
		if(fourthcategory!=""&&fourthcategory!=null){
			cateId = fourthcategory;
		}else if(thirdcategory!=""&&thirdcategory!=null){
			cateId = thirdcategory;
		}else if(secondcategory!=""&&thirdcategory!=null){
			cateId = secondcategory;
		}else{
			cateId=firstcategory;
		}
		return cateId;
	}	
