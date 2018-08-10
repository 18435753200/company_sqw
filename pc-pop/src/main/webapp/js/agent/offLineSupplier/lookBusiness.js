
$(function(){
	var path=$("#path").val() ; 
	var proId=$("#proId").val(); 
	var citId=$("#citId").val(); 
	var areId=$("#areId").val(); 
	
	
	
    	var cbc=$("#cbc").val();;
    	var ct=$("#companyType").val();
		var name="";
		if(ct==1){
			name="企业";
		}
		if(ct==2){
			name="个体工商户";
		}
		if(ct==3){
			name="事业单位";
		}
		$.post(
    			path+"/supplier/getCategory",
    			{'sectorname':ct},
    			   function(data){
    				var city="";
        				$.each(eval(data),function(i,n){
        					if(name==n.sectortype){
        					city+="<option value='"+n.sectorcode+"'>"+n.sectorname+"</option>";
        					}
        				});	
        				$("#category").append(city);
        				$("#category").val(cbc);
    			   }
    	);
		
     	var mess= $("#message").val();
     	if(mess.length>0){
     		alert(mess);
     	}
//     	加载市级数据
    	/* 加载二级目录 */
    		$("#companyType").change(function(){
    		$("#category").empty();
    		var a=$("#companyType").val()
    		if(a==1){
    			name="企业";
    		}
    		if(a==2){
    			name="个体工商户";
    		}
    		if(a==3){
    			name="事业单位";
    		}
    		$.post(
        			path+"/supplier/getCategory",
        			{'sectorname':a},
        			   function(data){
        				var city="<option value=''>请选择</option>";
            				$.each(eval(data),function(i,n){
            					if(name==n.sectortype){
            					city+="<option value='"+n.sectorcode+"'>"+n.sectorname+"</option>";
            					}
            				});	
            				$("#category").append(city);
//         				}
        			   }
        	);
    	});

//      	加载省级数据
     		$.post(
        			path+"/supplier/getProvinceList",
        			   function(data){
        				$.each(eval(data),function(i,n){
        					$("#province").append("<option value='"+n.provinceid+"'>"+n.provincename+"</option>");
        				});	
        				//*****************************
        				$("#province").val(proId);
        				
        				provinceChange(citId);
        				countryChange(areId);
        			   }
        	);
        	
     		$.ajaxSettings.async = false;
     		function provinceChange(id){
     			$("#city").empty();
    			$("#country").empty();
    			$.post(
            			path+"/supplier/getCityList",
            			   function(data){
            				var a=$("#province").val();
            				var city="<option value=''>请选择</option>";
    	        				$.each(eval(data),function(i,n){
    	        					if(a==n.provinceid){
    	        					city+="<option value='"+n.cityid+"'>"+n.cityname+"</option>";
    	        					}
    	        				});	
    	        				$("#city").append(city);
    	        				$("#city").val(id);
            			   }
            	);
        	
     		}
     		
     	 function countryChange(id){
     		$("#country").empty();
			$.post(
        			path+"/supplier/getCountryList",
        			   function(data){
        				var a=$("#city").val();
        				var country="<option value=''>请选择</option>";
        				$.each(eval(data),function(i,n){
        					if(a==n.cityid){
        					country+="<option value='"+n.countyid+"'>"+n.countyname+"</option>";
        					}
        				});
						$("#country").append(country);
						$("#country").val(id);
        			   }
        	);
     	 }
     	 
     	$.ajaxSettings.async = true;
     	
     	
     	
//  	加载市级数据
		/* 加载二级目录 */
			$("#province").change(function(){
			$("#city").empty();
			$("#country").empty();
			$.post(
     			path+"/supplier/getCityList",
     			   function(data){
     				var a=$("#province").val();
     				var city="<option value=''>请选择</option>";
	        				$.each(eval(data),function(i,n){
	        					if(a==n.provinceid){
	        					city+="<option value='"+n.cityid+"'>"+n.cityname+"</option>";
	        					}
	        				});	
	        				$("#city").append(city);
//      				}
     			   }
     	);
		});

			
//  	加载区域数据
			$("#city").change(function(){
				$("#country").empty();
				$.post(
	        			path+"/supplier/getCountryList",
	        			   function(data){
	        				var a=$("#city").val();
	        				var country="<option value=''>请选择</option>";
	        				$.each(eval(data),function(i,n){
	        					if(a==n.cityid){
	        					country+="<option value='"+n.countyid+"'>"+n.countyname+"</option>";
	        					}
	        				});
							$("#country").append(country);
	        			   }
	        	);
			});
     	
//			加载银行数据
			$.post(
					path+"/supplier/getBankList",
	     			   function(data){
	     				$.each(eval(data),function(i,n){
	     					$("#bank").append("<option value='"+n.bankCode+"'>"+n.bankName+"</option>");
	     				});	
	     			   }
	     	);
			
			
			
//			 加载该银行所有的省数据
		     $("#bank").change(function(){
					$("#bankProvinceId").empty();
					$("#bankAreaId").empty();
					$("#accoutBankname").empty();
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
		     
		     
		     
//		     加载该银行所在省的所有区的数据
		   $("#bankProvinceId").change(function(){
					$("#bankAreaId").empty();
					$("#accoutBankname").empty();
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
		     
//		     加载该所在省的所有区的所有银行的数据
		   $("#bankAreaId").change(function(){
				$("#accoutBankname").empty();
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
							$("#accoutBankname").append(accoutBankname);
	     			   },
	     			   "json"
	     	);
	    });  	       
});
     
     