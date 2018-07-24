$(function(){
     	
	 var path=$("#pa").val();
	 var provinceId=$("#p").val();
	 var cityId=$("#c").val();
	 var areaId=$("#a").val();
	 var bank=$("#ban").val();;
	 var bankProvinceId=$("#pc").val();;
	 var bankAreaId=$("#cc").val();;
	 var accoutBankno=$("#bc").val();;
	 
//  	==============================================================================================================
//      	加载省级数据
     		$.post(
        			path+"/supplier/getProvinceList",
        			   function(data){
        				$.each(eval(data),function(i,n){
        					$("#provinceId").append("<option value='"+n.provinceid+"'>"+n.provincename+"</option>");
        				});	
        				$("#provinceId").val(provinceId);
        			   }
        	);
     		
//         	==============================================================================================================
//     		加载市数据(完成时加载)
     		provinceChange();
     		function provinceChange(){
     			$("#cityId").empty();
    			$("#areaId").empty();
    			$.post(
            			path+"/supplier/getCityList",
            			   function(data){
            				var city="<option value=''>请选择</option>";
    	        				$.each(eval(data),function(i,n){
    	        					if(provinceId==n.provinceid){
    	        					city+="<option value='"+n.cityid+"'>"+n.cityname+"</option>";
    	        					}
    	        				});	
    	        				$("#cityId").append(city);
    	        				$("#cityId").val(cityId);
            			   }
            	);
     		}
//         	==============================================================================================================
//     	加载区数据(完成时加载)
     	countryChange();
     	 function countryChange(){
     		$("#areaId").empty();
			$.post(
        			path+"/supplier/getCountryList",
        			   function(data){
        				var country="<option value=''>请选择</option>";
        				$.each(eval(data),function(i,n){
        					if(cityId==n.cityid){
        					country+="<option value='"+n.countyid+"'>"+n.countyname+"</option>";
        					}
        				});
						$("#areaId").append(country);
						$("#areaId").val(areaId);
        			   }
        	);
     	 }
     	 
//     	==============================================================================================================
     	
     	
//  	加载市级数据(下拉框发生改变事件时)
		/* 加载二级目录 */
			$("#provinceId").change(function(){
			$("#cityId").empty();
			$("#areaId").empty();
			var province=$("#provinceId").val();
			$.post(
     			path+"/supplier/getCityList",
     			   function(data){
     				var city="<option value=''>请选择</option>";
	        				$.each(eval(data),function(i,n){
	        					if(province==n.provinceid){
	        					city+="<option value='"+n.cityid+"'>"+n.cityname+"</option>";
	        					}
	        				});	
	        				$("#cityId").append(city);
//      				}
     			   }
     	);
		});

//	     	==============================================================================================================			
//  	加载区域数据(下拉框发生改变事件时)
			$("#cityId").change(function(){
				$("#areaId").empty();
				var city=$("#cityId").val();
				$.post(
	        			path+"/supplier/getCountryList",
	        			   function(data){
	        				var country="<option value=''>请选择</option>";
	        				$.each(eval(data),function(i,n){
	        					if(city==n.cityid){
	        					country+="<option value='"+n.countyid+"'>"+n.countyname+"</option>";
	        					}
	        				});
							$("#areaId").append(country);
	        			   }
	        	);
			});
//	     	==============================================================================================================			
			
//		加载所有银行数据	
			$.post(
      				path+"/supplier/getBankList",
         			   function(data){
         				$.each(eval(data),function(i,n){
         					if(n.status==1){
         						$("#bank").append("<option value='"+n.bankCode+"'>"+n.bankName+"</option>");
         					}
         				});	
         					$("#bank").val(bank);
         			   }
         	);
//	     	==============================================================================================================
//			 加载该银行所有的省数据(完成时加载)
			getBankProvinceList();
			function getBankProvinceList(){
				$("#bankProvinceId").empty();
				$("#bankAreaId").empty();
				$("#accoutBankno").empty();
				$.post(
						path+"/supplier/getBankProvinceList",
						{'bankCode':bank},
	     			   function(data){
	     				var bankProvince="<option value=''>请选择</option>";
	     				$.each(eval(data),function(i,n){
	     					bankProvince+="<option value='"+n+"'>"+i+"</option>";
	     				});
							$("#bankProvinceId").append(bankProvince);
							$("#bankProvinceId").val(bankProvinceId);
	     			   },
	     			   "json"
	     	);
			}
//	     	==============================================================================================================	
//		     加载该银行所在省的所有区的数据(完成时加载)
			getBankCityList();
			function getBankCityList(){
				$("#bankAreaId").empty();
				$("#accoutBankno").empty();
				$.post(
						path+"/supplier/getBankCityList",
						{'bankCode':bank,'bankProvinceId':bankProvinceId},
	     			   function(data){
	     				var bankArea="<option value=''>请选择</option>";
	     				$.each(eval(data),function(i,n){
	     					bankArea+="<option value='"+n+"'>"+i+"</option>";
	     				});
							$("#bankAreaId").append(bankArea);
							$("#bankAreaId").val(bankAreaId);
	     			   },
	     			   "json"
	     	);
			}
				
//	     	==============================================================================================================	
//		     加载该所在省的所有区的所有银行的数据(完成时加载)
			getSubBankOfCity();
			function getSubBankOfCity(){
				$("#accoutBankno").empty();
				$.post(
						path+"/supplier/getSubBankOfCity",
						{'bankCode':bank,'bankAreaId':bankAreaId},
	     			   function(data){
	     				var accoutBankname="<option value=''>请选择</option>";
	     				$.each(eval(data),function(i,n){
	     					accoutBankname+="<option value='"+n.bankBranchCode+"'>"+n.bankBranchName+"</option>";
	     				});
							$("#accoutBankno").append(accoutBankname);
							$("#accoutBankno").val(accoutBankno);
	     			   },
	     			   "json"
	     	);
			}
			
//	     	==============================================================================================================		
//			 加载该银行所有的省数据(下拉框发生改变事件时)
		     $("#bank").change(function(){
					$("#bankProvinceId").empty();
					$("#bankAreaId").empty();
					$("#accoutBankno").empty();
					var bankCode=$("#bank").val();
					$.post(
							path+"/supplier/getBankProvinceList",
							{'bankCode':bankCode},
		     			   function(data){
		     				var bankProvince="<option value=''>请选择</option>";
		     				$.each(eval(data),function(i,n){
		     					bankProvince+="<option value='"+n+"'>"+i+"</option>";
		     				});
								$("#bankProvinceId").append(bankProvince);
		     			   },
		     			   "json"
		     	);
		     });
		     
//		     	==============================================================================================================
		     
//		     加载该银行所在省的所有区的数据(下拉框发生改变事件时)
		   $("#bankProvinceId").change(function(){
					$("#bankAreaId").empty();
					$("#accoutBankno").empty();
					var bankCode=$("#bank").val();
					var bankProvinceId=$("#bankProvinceId").val();
					$.post(
							path+"/supplier/getBankCityList",
							{'bankCode':bankCode,'bankProvinceId':bankProvinceId},
		     			   function(data){
		     				var bankArea="<option value=''>请选择</option>";
		     				$.each(eval(data),function(i,n){
		     					bankArea+="<option value='"+n+"'>"+i+"</option>";
		     				});
								$("#bankAreaId").append(bankArea);
		     			   },
		     			   "json"
		     	);
		    });
//	     	==============================================================================================================
//		     加载该所在省的所有区的所有银行的数据(下拉框发生改变事件时)
		   $("#bankAreaId").change(function(){
				$("#accoutBankno").empty();
				var bankCode=$("#bank").val();
				var bankAreaId=$("#bankAreaId").val();
				$.post(
						path+"/supplier/getSubBankOfCity",
						{'bankCode':bankCode,'bankAreaId':bankAreaId},
	     			   function(data){
	     				var accoutBankname="<option value=''>请选择</option>";
	     				$.each(eval(data),function(i,n){
	     					accoutBankname+="<option value='"+n.bankBranchCode+"'>"+n.bankBranchName+"</option>";
	     				});
							$("#accoutBankno").append(accoutBankname);
	     			   },
	     			   "json"
	     	);
	    });  
			
//	     	==============================================================================================================
      
});



//	==============================================================================================================

function checkName(){
 if($.trim($("#regName").val())==""||$.trim($("#regName").val())==undefined){
	 return ;
 }
 var url=$("#pa").val()+'/user/isPinEngaged';
 var data="pin="+$("#regName").val();
 $.ajax({
     type: "POST",
     dataType:"html",
     url: url,
     data: data,
     success: function (result) {
    	 if(result>0){
    		 $("#realname_error").show();
    	 }else{
    		 $("#realname_error").hide();
    	 }
     }
 });
}
function checkName1(){
 if($.trim($("#companyname").val())==""||$.trim($("#companyname").val())==undefined){
	 return ;
 }
 var url=$("#pa").val()+'/user/isPinName';
 var data="stringName="+$("#companyname").val();
 $.ajax({
     type: "POST",
     dataType:"html",
     url: url,
     data: data,
     success: function (result) {
    	 if(result>0){
    		 $("#company_error").show();
    	 }else{
    		 $("#company_error").hide();
    	 }
     }
 });

}
     
     

   
     
