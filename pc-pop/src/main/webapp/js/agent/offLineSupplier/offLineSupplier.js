var  CONTEXTPATH  = $("#conPath").val();


$(document).ready(function(){
    getDownProduct($('#dpro').val());
   
});


/*	跳转到后台获取列表数据*/	
function getDownProduct(s){
	var parentId=$("#parentId").val();
	$.ajax({
		type : "post", 
		url  : CONTEXTPATH+"/supplier/getBusinessList", 
		data : "parentId="+parentId,
		dataType: "html",
		success : function(msg) { 
			$("#c3").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试1!");
		}
	}); 

}


//	修改上级代理查出该上级代理同级的所有代理
function getAgentSame(id, status) {
	if (status == 5) {
		var usp=$("#usp");
		common(usp,id);
	}
}

function getAgentSameTo(id) {
		var usp1=$("#usp1");
		common(usp1,id);
}

function common(na,id) {
	na.empty();
	$.post('../supplier/getAgentSame', {
		'supplierId' : id
	}, function(data) {
		if (data != null) {
			var sup = "<option value=''>请选择</option>";
			$.each(eval(data), function(i, n) {
				sup += "<option value='"+n.id+"'>" + n.name + "</option>";
			});
			na.append(sup);
		}
	});
}

//修改上级代理
function updateParentId(supplierId){
	var parentId=$("#usp").val();
	if(parentId=='' || parentId==null){
		alert("请选择要替换的上级代理!");
		return;
	}
	if(window.confirm("确认替换吗?")){
	var  array=[];
	array.push(supplierId);
	var str=array.join();
	
	$.ajax({
		async:false,
		type : "post", 
		url : "../supplier/editSupplierParentId", 
		data:{'str': str,'parentId':parentId},
		dataType:"json",
		success : function(msg) {
			if(confirm("替换成功")){
			window.location.reload();
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("替换失败");
		}
	}); 
	}
}


//批量修改上级代理前奏
function updateParentIdMore(){
	var chk_value =[]; 
	$('input[name="topProB2B"]:checked').each(function(){ 
	chk_value.push($(this).val()); 
	}); 
	if(chk_value.length==0){
		alert("你还没有选择任何内容");
		return ;
	}
	$("#checkboxValue").val(chk_value);
	var a=$("#showboot");
	a.click();
}

//批量修改上级代理
function updateMoreParentId(){
	//获取要替换的上级代理id
	var parentId=$("#usp1").val();
	if(parentId=='' || parentId==null){
		alert("请选择要替换的上级代理!");
		return;
	}
	if(window.confirm("确认替换吗?")){
		//获取多选框选中的内容
    	var chk_value =[]; 
    	$('input[name="topProB2B"]:checked').each(function(){ 
    	chk_value.push($(this).val()); 
    	}); 
    	var str=chk_value.join();
    	
    	//发送ajax替换上级代理
    	$.ajax({
    		async:false,
    		type : "post", 
    		url : "../supplier/editSupplierParentId", 
    		data:{'str': str,'parentId':parentId},
    		dataType:"json",
    		success : function(msg) {
    			if(confirm("替换成功")){
    			window.location.reload();
    			}
    		},
    		error: function(jqXHR, textStatus, errorThrown){
    			alert("替换失败");
    		}
    	}); 
	}
}

function returnPage(parentId){
	var page=1;
	document.getElementById("f").action='../supplier/getAgentPage';
	document.getElementById("parid").value=parentId;
	document.getElementById("f").submit();
	document.getElementById("f").action='';
	document.getElementById("parid").value='';
//	window.location.href="../supplier/getAgentPage?parentId=" + parentId;
}



//查看下级按钮
function findNext(parentId){
	var page=1;
	
	$.ajax({
		async:false,
		type : "post", 
		url : "../supplier/getAgentList", 
		data:{'page':page,'parentId':parentId},
		dataType:"html",
		success : function(msg) { 
			$("#c3").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("查询失败 ，请稍后再试。。");
		}
	}); 

}

//跳转到查看商家页面
function findNextBus(parentId){
	
	$.post(
			'../supplier/checkJunior',
			{'parentId':parentId},
			function(res){
				if(res==1){
		    		window.location.href="../supplier/listBusiness?parentId=" + parentId;
		    	}else{
		    		alert("该商场合伙人未拓展商家!");
		    	}
			}
	);
	
}

//查看流水    
function tradingFlow(supplierId){
	document.getElementById("f").action='../supplier/busTransRecord';
	document.getElementById("spid").value=supplierId;
	document.getElementById("f").submit();
	document.getElementById("f").action='';
	document.getElementById("spid").value='';
}

//查看二维码
function lookCode(supplierId){
	document.getElementById("f").action='../supplier/qrCode2';
	document.getElementById("spid").value=supplierId;
	document.getElementById("f").submit();
	document.getElementById("f").action='';
	document.getElementById("spid").value='';
}

//跳转到商家店铺详情页面
function storeDetail(supplierId){
	window.location.href="../supplier/storeDetail?supplierId=" + supplierId;
}





//搜索按钮和分页查找
function clickSubmit(page){
	
	var name =$.trim($("#name").val());
	
	var checkStatus = $.trim($("#checkStatus").val());
	
	var parentId = $.trim($("#parentId").val());
	

	var pro_array  = new Array();

	if(page!="" && page!=undefined){
		pro_array.push("page="+page);
	}
	if(name!="" && name!=undefined){
		pro_array.push("name="+name);
	}
	if(checkStatus!="" && checkStatus!=undefined){
		pro_array.push("checkStatus="+checkStatus);
	}
	if(parentId!="" && parentId!=undefined){
		pro_array.push("parentId="+parentId);
	}
	//判断是否输入查询条件,未输入直接发返回
	if(pro_array.length==1){
		return;
	}
	
	$.ajax({
		async:false,
		type : "post", 
		url : "../supplier/getBusinessList", 
		data:pro_array.join("&"),
		dataType:"html",
		success : function(msg) { 
			$("#c3").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("查询失败 ，请稍后再试。。");
		}
	}); 

}

