var  CONTEXTPATH  = $("#conPath").val(); 
var constantId=null;
$(function(){
    	getTrandingList();
    	
 });

    function getTrandingList(){
    	var supplierId=$("#supplierId").val();
    	var page=$("#page").val();
    	$.ajax({
    		type : "post", 
    		url  : CONTEXTPATH+"/supplier/getLookTradingFlowData", 
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
    
    //查看代理分享收入流水后的返回按钮
    function returnPage(parentId){
    	document.getElementById("f").action='../supplier/getAgentPage';
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
    	if(pro_array.length<1){
    		return;
    	}
    	$.ajax({
    		type : "post", 
    		url : "../supplier/getLookTradingFlowData",
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
    
    
    function judgeLength(obj){
    	if(obj.length<2){
    		return "0"+obj;
    	}
    	return obj;
    }
    
	function closeDiv(){
		$("#tshow").fadeOut();
	}
	function showDiv(id){
		if(constantId==null ||  (constantId!=null && constantId!=id)){
			$("#supplierName").text("");
			$("#earning").text("");
			$("#supplierProvince").text("");
			$("#supplierCity").text("");
			$("#supplierCounty").text("");
			$("#address").text("");
			$("#userNickName").text("");
			$("#orderId").text("");
			$("#price").text("");
			$("#time").text("");
			
			constantId=id;
			$.post(
				"../supplier/getFlowMore",
				{'id':id},
				function(data){
					var obj = eval(data);
					$.each(obj, function(i, n){
						$("#supplierName").text(data.supplierName);
						$("#earning").text(data.earning);
						$("#supplierProvince").text(data.supplierProvince);
						$("#supplierCity").text(data.supplierCity);
						$("#supplierCounty").text(data.supplierCounty);
						$("#address").text(data.address);
						$("#userNickName").text(data.userNickName);
//						$("#orderId").text(data.orderId);
						$("#orderId").text(data.orderIdString);
						$("#price").text(data.price);
						var time=new Date(data.time);
						var month=time.getMonth()+1;
						month=judgeLength(month.toString());
						var day=time.getDate();
						day=judgeLength(day.toString());
						var hour=time.getHours();
						hour=judgeLength(hour.toString());
						var minutes=time.getMinutes();
						minutes=judgeLength(minutes.toString());
						var seconds=time.getSeconds();
						seconds=judgeLength(seconds.toString());
						$("#time").text(time.getFullYear()+"-"+month+"-"+day+" "+hour+":"+minutes+":"+seconds);
					});
				},"json"
			);
					$("#tshow").fadeIn();
		}
		if(constantId!=null && constantId==id){
			$("#tshow").fadeIn();
		}
	}