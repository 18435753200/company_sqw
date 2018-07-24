
     $(function(){
    	 var path=$("#pa").val();
//      	加载省级数据
     		$.post(
     				path+"/supplier/getProvinceList",
        			   function(data){
        				$.each(eval(data),function(i,n){
        					$("#provinceId").append("<option value='"+n.provinceid+"'>"+n.provincename+"</option>");
        				});	
        			   }
        	);
     		
//			加载银行数据
			$.post(
      				path+"/supplier/getBankList",
         			   function(data){
         				$.each(eval(data),function(i,n){
         					if(n.status==1){
         						$("#bank").append("<option value='"+n.bankCode+"'>"+n.bankName+"</option>");
         					}
         				});	
         			   }
         	);
        	
//         	加载市级数据
			/* 加载二级目录 */
				$("#provinceId").change(function(){
				var  provinceValue=$("#provinceId").val();
				$("#cityId").empty();
				$("#areaId").empty();
				if(provinceValue!='' && provinceValue!=null){
					$.post(
							path+"/supplier/getCityList",
		        			   function(data){
		        				var a=$("#provinceId").val();
		        				var city="<option value=''>请选择</option>";
			        				$.each(eval(data),function(i,n){
			        					if(a==n.provinceid){
			        					city+="<option value='"+n.cityid+"'>"+n.cityname+"</option>";
			        					}
			        				});	
			        				$("#cityId").append(city);
		        			   }
		        	);
				};
				
			});
	
				
//         	加载区域数据
				$("#cityId").change(function(){
					var  cityValue=$("#cityId").val();
					$("#areaId").empty();
					if(cityValue!='' && cityValue!=null){
					$.post(
							path+"/supplier/getCountryList",
		        			   function(data){
		        				var a=$("#cityId").val();
		        				var country="<option value=''>请选择</option>";
		        				$.each(eval(data),function(i,n){
		        					if(a==n.cityid){
		        					country+="<option value='"+n.countyid+"'>"+n.countyname+"</option>";
		        					}
		        				});
								$("#areaId").append(country);
		        			   }
		        		);
					};
				});
				
				
				
				
//				 加载该银行所有的省数据
			     $("#bank").change(function(){
					$("#bankProvinceId").empty();
					$("#bankAreaId").empty();
					$("#accoutBankno").empty();
					var bankCode=$("#bank").val();
					if(bankCode!='' && bankCode!=null){
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
					};
			     });
			     
			     
			     
//			     加载该银行所在省的所有区的数据
			   $("#bankProvinceId").change(function(){
						$("#bankAreaId").empty();
						$("#accoutBankno").empty();
						var bankCode=$("#bank").val();
						var bankProvinceId=$("#bankProvinceId").val();
						if(bankProvinceId!='' && bankProvinceId!=null){
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
						}
			    });
			     
//			     加载该所在省的所有区的所有银行的数据
			   $("#bankAreaId").change(function(){
					$("#accoutBankno").empty();
					var bankCode=$("#bank").val();
					var bankAreaId=$("#bankAreaId").val();
					if(bankAreaId!='' && bankAreaId!=null){
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
					}
		    });  
			   
			   	
     });
     
     
//     ==============================================================================================================
     	
    
     

     
//   ==============================================================================================================
    	

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
     
  