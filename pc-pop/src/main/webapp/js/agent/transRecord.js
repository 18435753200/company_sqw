var  CONTEXTPATH  = $("#conPath").val(); 

$(function(){
    	getTrandingList();
 });

    function getTrandingList(){
    	var supplierId=$("#supplierId").val();
    	var page=$("#page").val();
    	$.ajax({
    		type : "post", 
    		url  : CONTEXTPATH+"/supplier/getLookTransRecord", 
    		data : {'page':page,'supplierId':supplierId},
    		dataType: "html",
    		success : function(msg) { 
    			$("#c3").html(msg);
    		},
    		error: function(jqXHR, textStatus, errorThrown){
    			alert("对不起，数据异常请稍后再试!");
    		}
    	}); 

    }
    
    //查看商家收入流水后的返回按钮
    function returnPage(parentId){
    	document.getElementById("f").action='../supplier/listBusiness';
    	document.getElementById("parid").value=parentId;
    	document.getElementById("f").submit();
    	document.getElementById("f").action='';
    	document.getElementById("parid").value='';
    }
    
    
    function clickSubmit1(page){
    	var startTime = $("#startTime").val().trim();
    	var endTime = $("#endTime").val().trim();
    	var supplierId=$("#supplierId").val().trim();
    	var pro_array  = new Array();
    	if(page!="" && page!=null){
    		pro_array.push("page="+page);
    	}
    	if(startTime != "" && startTime!=null){
    		pro_array.push("startTime="+startTime);
    	}
    	if(endTime != ""&& endTime!=null){
    		pro_array.push("endTime="+endTime);
    	}
    	if(supplierId!= ""&& supplierId!=null){
    		pro_array.push("supplierId="+supplierId); 
    	}
    	//判断是否输入查询条件,未输入直接发返回
    	if(pro_array.length==1){
    		return;
    	}
    	$.ajax({
    		type : "post", 
    		url : "../supplier/getLookTransRecord",
    		data:pro_array.join("&"),
    		dataType:"html",
    		success : function(msg) { 
    			$("#c3").html(msg);
    		},
    		error: function(jqXHR, textStatus, errorThrown){
    			alert("对不起，数据异常请稍后再试!");
    		}
    	}); 


    }