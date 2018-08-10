$(document).ready(function (){
	var supplierId=$("#suid").val();
	var bank=$("#bankId").val();
	var provinceCode=$("#provinceCodeId").val();
	var cityCode=$("#cityCodeId").val();
	var bankBranchCode=$("#bankBranchCodeId").val();
//	加载省级数据
		$.post(
			"../getProvinceList",
			   function(data){
				$.each(eval(data),function(i,n){
					$("#province").append("<option value='"+n.provinceid+"'>"+n.provincename+"</option>");
				});
				
//				加载银行数据
				$.post(
	      				"../getBankList",
	         			   function(data){
	         				$.each(eval(data),function(i,n){
	         					$("#bank").append("<option value='"+n.bankCode+"'>"+n.bankName+"</option>");
	         				});	
	         				$("#bank").val(bank);
	         			   }
	         	);
				
				getBankProvinceList();
				function getBankProvinceList(){
					$("#bankProvinceId").empty();
					$("#bankAreaId").empty();
					$("#accoutBankno").empty();
					$.post(
							"../getBankProvinceList",
							{'bankCode':bank},
		     			   function(data){
		     				var bankProvince="<option value=''>请选择</option>";
		     				$.each(eval(data),function(i,n){
		     					bankProvince+="<option value='"+n+"'>"+i+"</option>";
		     				});
								$("#bankProvinceId").append(bankProvince);
								$("#bankProvinceId").val(provinceCode);
								
		     			   },
		     			   "json"
		     	);
				}
				 getBankCityList();
			     function getBankCityList(){
			    	 $("#bankAreaId").empty();
						$("#accoutBankno").empty();
						var bankCode=$("#bank").val();
						var bankProvinceId=$("#bankProvinceId").val();
						$.post(
								"../getBankCityList",
								{'bankCode':bank,'bankProvinceId':provinceCode},
			     			   function(data){
			     				var bankArea="<option value=''>请选择</option>";
			     				$.each(eval(data),function(i,n){
			     					bankArea+="<option value='"+n+"'>"+i+"</option>";
			     				});
									$("#bankAreaId").append(bankArea);
									$("#bankAreaId").val(cityCode);
			     			   },
			     			   "json"
			     	);
			     }
			     getSubBankOfCity();
			     function getSubBankOfCity(){
			    	 $("#accoutBankno").empty();
						var bankCode=$("#bank").val();
						var bankAreaId=$("#bankAreaId").val();
						$.post(
								"../getSubBankOfCity",
								{'bankCode':bank,'bankAreaId':cityCode},
			     			   function(data){
			     				var accoutBankno="<option value=''>请选择</option>";
			     				$.each(eval(data),function(i,n){
			     					accoutBankno+="<option value='"+n.bankBranchCode+"'>"+n.bankBranchName+"</option>";
			     				});
									$("#accoutBankno").append(accoutBankno);
									$("#accoutBankno").val(bankBranchCode);
			     			   },
			     			   "json"
			     	);
			     }
			    
				
				$.post(
	        			"../getAreaEcho",
	        			{'supplierId':supplierId},
	        			   function(data){
	        				if(data!=null){
	        					if(data.provinceId!=null){
		        					$("#province").val(data.provinceId);
		        				}
		        				if(data.cityId!=null){
		        					provinceChange(data.cityId);
		        				}
		        				if(data.countyId!=null){
		        					cityChange(data.countyId);
		        				}
	        				}
	        			   },"json"
	        	); 
			   }
	);
		
		$.ajaxSettings.async = false;
		
		 function provinceChange(id){
			 $("#city").empty();
				$("#country").empty();
				$.post(
		    			"../getCityList",
		    			   function(data){
		    				var a=$("#province").val();
		    				var city="<option value=''>市代理</option>";
		        				$.each(eval(data),function(i,n){
		        					if(a==n.provinceid){
		        					city+="<option value='"+n.cityid+"'>"+n.cityname+"</option>";
		        					}
		        				});	
		        				$("#city").append(city);
		        				$("#city").val(id);
//		     				}
		    			   }
		    	);
		 }
		
		 
		 function cityChange(id){
			 $("#country").empty();
				$.post(
	        			"../getCountryList",
	        			   function(data){
	        				var a=$("#city").val();
	        				var country="<option value=''>区代理</option>";
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

//	 	加载市级数据
		/* 加载二级目录 */
			$("#province").change(function(){
			$("#city").empty();
			$("#country").empty();
			$.post(
	    			"../getCityList",
	    			   function(data){
	    				var a=$("#province").val();
	    				var city="<option value=''>市代理</option>";
	        				$.each(eval(data),function(i,n){
	        					if(a==n.provinceid){
	        					city+="<option value='"+n.cityid+"'>"+n.cityname+"</option>";
	        					}
	        				});	
	        				$("#city").append(city);
//	     				}
	    			   }
	    	);
		});

			
//	 	加载区域数据
			$("#city").change(function(){
				$("#country").empty();
				$.post(
	        			"../getCountryList",
	        			   function(data){
	        				var a=$("#city").val();
	        				var country="<option value=''>区代理</option>";
	        				$.each(eval(data),function(i,n){
	        					if(a==n.cityid){
	        					country+="<option value='"+n.countyid+"'>"+n.countyname+"</option>";
	        					}
	        				});
							$("#country").append(country);
	        			   }
	        	);
			});




			
			
			
//			 加载该银行所有的省数据
		     $("#bank").change(function(){
					$("#bankProvinceId").empty();
					$("#bankAreaId").empty();
					$("#accoutBankno").empty();
					var bankCode=$("#bank").val();
					$.post(
							"../getBankProvinceList",
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
					$("#accoutBankno").empty();
					var bankCode=$("#bank").val();
					var bankProvinceId=$("#bankProvinceId").val();
					$.post(
							"../getBankCityList",
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
				$("#accoutBankno").empty();
				var bankCode=$("#bank").val();
				var bankAreaId=$("#bankAreaId").val();
				$.post(
						"../getSubBankOfCity",
						{'bankCode':bankCode,'bankAreaId':bankAreaId},
	     			   function(data){
	     				var accoutBankno="<option value=''>请选择</option>";
	     				$.each(eval(data),function(i,n){
	     					accoutBankno+="<option value='"+n.bankBranchCode+"'>"+n.bankBranchName+"</option>";
	     				});
							$("#accoutBankno").append(accoutBankno);
	     			   },
	     			   "json"
	     	);
	    });  
		   
	
		
})

