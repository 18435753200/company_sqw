var  CONTEXTPATH  = $("#conPath").val(); 

$(function(){
    	getAgentList();
    	
 });

    function getAgentList(){
    	var pid=$("#pid").val();
    	if(pid==null || pid==undefined){
    		pid='';
    	}
    	var page=$("#page").val();
    	$.ajax({
    		type : "post", 
    		url  : CONTEXTPATH+"/supplier/getAgentList", 
    		data : {'page':page,'parentId':pid},
    		dataType: "html",
    		success : function(msg) { 
    			$("#c3").html(msg);
    		},
    		error: function(jqXHR, textStatus, errorThrown){
    			alert("对不起，数据异常请稍后再试!");
    		}
    	}); 

    }
    
    
    
//    查看下级按钮
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
    
//    跳转到查看商家页面
    function findNextBus(parentId){
    	
    	$.post(
    			'../supplier/checkJunior',
    			{'parentId':parentId},
    			function(res){
    				if(res==1){
    					document.getElementById("f").action='../supplier/listBusiness';
    			    	document.getElementById("parid").value=parentId;
    			    	document.getElementById("f").submit();
    			    	document.getElementById("f").action='';
    			    	document.getElementById("parid").value='';
    		    	}else{
    		    		alert("该市场合伙人未拓展商家!");
    		    	}
    			}
    	);
    	
    }
    
//    搜索按钮和分页查找
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
    		url : "../supplier/getAgentList", 
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
    
//    查看二维码
    function lookCode(uid){
    	window.location.href="../supplier/getQrCode?uId="+uid;
    }
    
//    添加代理
    function addAgent(){
    	var parentId=$("#parentId").val();
    	document.getElementById("f").action='../supplier/addAgentPage';
    	document.getElementById("parid").value=parentId;
    	document.getElementById("f").submit();
    	document.getElementById("f").action='';
    	document.getElementById("parid").value='';
//    	window.location.href="../supplier/addAgentPage?parentId="+parentId;
    }
//    添加市场合伙人
    function addMpAgent(){
    	var parentId=$("#parentId").val();
    	document.getElementById("f").action='../supplier/addMpAgentPage';
    	document.getElementById("parid").value=parentId;
    	document.getElementById("f").submit();
    	document.getElementById("f").action='';
    	document.getElementById("parid").value='';
//    	window.location.href="../supplier/addMpAgentPage?parentId="+parentId;
    }
    
//    查看代理
    function lookSupplier(supplierId){
    	document.getElementById("f").action='../supplier/getAgent';
    	document.getElementById("spid").value=supplierId;
    	document.getElementById("f").submit();
    	document.getElementById("f").action='';
    	document.getElementById("spid").value='';
    }
//    查看MP市场合伙人
    function lookMpSupplier(supplierId){
    	document.getElementById("f").action='../supplier/getMpAgent';
    	document.getElementById("spid").value=supplierId;
    	document.getElementById("f").submit();
    	document.getElementById("f").action='';
    	document.getElementById("spid").value='';
    }
    
    //查看代理信息后的返回按钮
    function returnAgentPage(parentId){
    	document.getElementById("f").action='../supplier/getAgentPage';
    	document.getElementById("parid").value=parentId;
    	document.getElementById("f").submit();
    	document.getElementById("f").action='';
    	document.getElementById("parid").value='';
    }
//   充值优惠券
    function rechargeCoupon(supplierId){
    	window.location.href="../supplier/rechargeCoupon?supplierId="+supplierId;
    }
    
//    修改代理
    function updateSupplier(supplierId){
    	document.getElementById("f").action='../supplier/editAgent';
    	document.getElementById("spid").value=supplierId;
    	document.getElementById("f").submit();
    	document.getElementById("f").action='';
    	document.getElementById("spid").value='';
//    	window.location.href="../supplier/editAgent?supplierId="+supplierId;
    }
    
//    修改市场合伙人
    function updateMpSupplier(supplierId){
    	document.getElementById("f").action='../supplier/editMpAgent';
    	document.getElementById("spid").value=supplierId;
    	document.getElementById("f").submit();
    	document.getElementById("f").action='';
    	document.getElementById("spid").value='';
//    	window.location.href="../supplier/editMpAgent?supplierId="+supplierId;
    }
//   查看流水    
    function tradingFlow(supplierId){
    	document.getElementById("f").action='../supplier/getLookTradingFlow';
    	document.getElementById("spid").value=supplierId;
    	document.getElementById("f").submit();
    	document.getElementById("f").action='';
    	document.getElementById("spid").value='';
    }
    
    
//    禁用代理
    function disableAgent(supplierId){
    	if(confirm("确定冻结吗?")){
    		$.ajax({
        		async:false,
        		type : "post", 
        		url : "../supplier/editFreezeAgent", 
        		data:{'id': supplierId},
        		dataType:"json",
        		success : function(msg) {
        			if(confirm("冻结成功")){
        			window.location.reload();
        			}
        		},
        		error: function(jqXHR, textStatus, errorThrown){
        			alert("冻结失败");
        		}
        	}); 
    	}
    }
//    解冻代理
    function unDisableAgent(supplierId){
    	if(confirm("确定解冻吗?")){
    		
    		$.ajax({
    			async:false,
    			type : "post", 
    			url : "../supplier/editUnFreezeAgent", 
    			data:{'id': supplierId},
    			dataType:"json",
    			success : function(msg) {
    				if(confirm("解冻成功")){
    					window.location.reload();
    				}
    			},
    			error: function(jqXHR, textStatus, errorThrown){
    				alert("解冻失败");
    			}
    		}); 
    	}
    }    
    
//    修改上级代理
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
    
//    批量修改上级代理前奏
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
    
//    批量修改上级代理
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
    
    
    