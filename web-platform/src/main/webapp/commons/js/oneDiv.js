var  CONTEXTPATH  = $("#conPath").val();
function toReLoad(companyBy){
	var array = new Array();
	if(companyBy!=""&&companyBy!=null&&companyBy!=undefined){
		array.push("companyBy="+companyBy);
	}
	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/infrastructure/oneDividend", 
		data:array.join("&"),
		success : function(msg) { 
			$("#seelist").html(msg);
		},
		error:function(){
			alert("加载失败，稍后再试。");
		}
	});

}

function toSubmitRply(){
	var flag = true;
	$("input[name='giftHqj']").each(function(){
		if((!RegExp("^\\d{1,5}\\.\\d+$").test($(this).val()) && !RegExp("^\\d{1,5}\\.?$").test($(this).val()))|| Number($(this).val())<=0){
			 alert("请正确填写赠送红旗劵额度！");
			 flag = false;
		 }
	});
	$("input[name='giftFhed']").each(function(){
		if((!RegExp("^\\d{1,5}\\.\\d+$").test($(this).val()) && !RegExp("^\\d{1,5}\\.?$").test($(this).val()))|| Number($(this).val())<=0){
			 alert("请正确填写赠送分红额度！");
			 flag = false;
		 }
	});
	/*$("input[name='retDate']").each(function(){
		if(!RegExp("^\\d{1,3}$").test($(this).val()) || $(this).val() =="0"){
			alert("请正确填写返还时间！");
			flag = false;
		}
	});*/
	if(flag){
		var type = $("input[name='company']").eq(0).val();
		$.ajax({
			type : "post", 
			url : CONTEXTPATH+"/infrastructure/updateOneDividend", 
			data: $('#oneDivs').serialize(),
			success : function(msg) { 
				if(msg==1){
					alert("保存成功");
					window.location.href=CONTEXTPATH+"/infrastructure/oneDividend?companyBy="+type;
				}else{
					alert("保存失败 稍后再试");
				}
			},
			error:function(){
				alert("加载失败，稍后再试。");
			}
		}); 
	}
}

function checkLabel(){
	var statu = '';

	var alreadylabel = $("#alreadylabel").attr("class");
	if(alreadylabel=='list'){
		statu = '2';
	}else{
		statu = '1';
	}
	return statu;
}
//1待处理 2已处理
function labelPage (lable){
	toReLoad(lable);
}
