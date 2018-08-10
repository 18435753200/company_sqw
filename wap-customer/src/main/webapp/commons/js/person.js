// JavaScript Document

//我的订单左上部搜索点击文字消失
function search(obj1,obj2){
	obj1.focus(function(){
		obj2.hide();
	});
	obj1.blur(function(){
		if(obj1.val()==''){
			obj2.show();
		}
	});
	obj2.click(function(){
		obj1.focus();
	});		
}


//选项卡
function myindent(obj1,obj2){
	obj1.click(function(){
		obj1.removeClass('active');
		$(this).addClass('active');
		obj2.hide();
		obj2.eq($(this).index()).show();
	});			
}

function delect(id){
	//var aDele=$('.delly');
	var oMask=$('#mask');
	var oI=$('.casely i');
	var oCan=$('.cancelly');
	$("#delaete").val(id);
	oMask.show();
	/*aDele.click(function(){
		oMask.show();
	});*/
	oI.click(function(){
		oMask.hide();
	});
	oCan.click(function(){
		oMask.hide();
	});	
}

function address(){
	/*var aDele=$('.compilely');*/
	var oAdd=$('#add');
	var oMask=$('#mask1');
	var oI=$('.add_addressly p span');
	var oCan=$('.canly1')
	var addressIdVal = $('#AUaddressId');

	oAdd.click(function(){
		oMask.show();
		$("#AUaddressId").val("");
		$("#addressF").val("");
		$("#mobileF").val("");
		$("#contactorF").val("");
		$(".error").text("");
		
		getAllProvince(null);
	});
	/*aDele.click(function(){
		oMask.show();
	});*/
	
	oI.click(function(){
		oMask.hide();
	});
	
	oCan.click(function(){
		oMask.hide();
	});	
	
}

;(function(){
	window.myradio=function(sName){
		var aSex=document.getElementsByName(sName);
		var aSpan=[];
		
		for(var i=0;i<aSex.length;i++){
			var oS=document.createElement('span');
			if(aSex[i].checked==true){
				oS.className='radioon_ly';
			}else{
				oS.className='radiooff_ly';
			}
			aSpan.push(oS);
			aSex[i].parentNode.insertBefore(oS,aSex[i]);
			aSex[i].style.display='none';
			(function(index){
				oS.onclick=function(){
					for(var i=0;i<aSex.length;i++){
						aSpan[i].className='radiooff_ly';
						aSex[i].checked=false;	
					}
					this.className='radioon_ly';
					aSex[index].checked=true;
				};
			})(i);
		}
	}
})();

/**
 * 判断是否是数字
 */
function isNumber(value){
	if(isNaN(value)){
		return false;
	}
	else{
		return true;
	}
}

function modified(obj1,obj2,obj3){
	var skuQty1 = $("#skuQty1").val();
	obj1.click(function(){
		if(isNaN(obj3.val())){
			return;
		}
		obj3.val(parseInt(obj3.val())-1);
		if(obj3.val()<=1){
			obj3.val(1);
		}
	});
	obj2.click(function(){
		if(isNaN(obj3.val())){
			return;
		}
		if(skuQty1==obj3.val()){
			return;
		}else{
			obj3.val(parseInt(obj3.val())+1);
		}
		
		
	});
	obj3.keydown(function(event){
		if((event.keyCode<48||event.keyCode>57)&&(event.keyCode!=8)&&(event.keyCode<96||event.keyCode>105)){
			obj3.val(1);
			return false;
		}
	})
}

;(function(){
	window.mycheckbox=function(sName){
		var aSex=document.getElementsByName(sName);
		var aSpan=[];
		var oIn=document.getElementById('in');
		for(var i=0;i<aSex.length;i++){
			var oS=document.createElement('span');
			if(aSex[i].checked==true){
				oS.className='radioon1_ly';
				
			}else{
				oS.className='radiooff1_ly';
				
			}
			aSpan.push(oS);
			aSex[i].parentNode.insertBefore(oS,aSex[i]);
			aSex[i].style.display='none';
			(function(index){
				oS.onclick=function(){
					if(this.className=='radiooff1_ly'){
						$("#checkbox4").val("1");
						this.className='radioon1_ly';
						aSex[index].checked=true;
						oIn.style.display='block';
					}else{
						$("#checkbox4").val("2");
						this.className='radiooff1_ly';
						aSex[index].checked=false;
						oIn.style.display='none';
					}
				};
			})(i);
		}
	};
})();

function num(abj){
	abj.keydown(function(event){
		if((event.keyCode<48||event.keyCode>57)&&(event.keyCode!=8)&&(event.keyCode<96||event.keyCode>105)){
			abj.val();
			return false;
		}
	});		
}

function birth(){
	var year3 = $("#year3").val();
	var month3 = $("#month3").val();
	var day3 = $("#day3").val();
    var i=1915;
    var date = new Date();
    year = date.getFullYear();//获取当前年份
    var dropList;
    for(i;i<=year;i++){
    	if(year3=="" || year3 ==null){
    		 if(i==year){
    	            dropList = dropList + "<option value='"+i+"' selected>"+i+"</option>";
    	        }
    	        else{
    	            dropList = dropList + "<option value='"+i+"'>"+i+"</option>";
    	        }
    	}else{
    		 if(i==year3){
 	            dropList = dropList + "<option value='"+i+"' selected>"+i+"</option>";
 	        }
 	        else{
 	            dropList = dropList + "<option value='"+i+"'>"+i+"</option>";
 	        }
    	}
       
    }
    $('#year1').html(dropList);//生成年份下拉菜单
    var monthly;
    for(month=1;month<13;month++){
    	if(month3==month){
    		 monthly = monthly + "<option value='"+month+"' selected>"+month+"</option>";
    	}else{
    		monthly = monthly + "<option value='"+month+"'>"+month+"</option>";
    	}
    }
    $('#month1').html(monthly);//生成月份下拉菜单
    var dayly;
    for(day=1;day<=31;day++){
    	if(day3 == day){
    		 dayly = dayly + "<option value='"+day+"' selected>"+day+"</option>";
    	}else{
    		 dayly = dayly + "<option value='"+day+"'>"+day+"</option>";
    	}
    }
    $('#day1').html(dayly);//生成天数下拉菜单
    /*
     * 处理每个月有多少天---联动
     */
    $('#month1').change(function(){
        var currentDay;
        var Flag = $('#year1').val();
        var currentMonth = $('#month1').val();
        switch(currentMonth){
            case "1" :
            case "3" :
            case "5" :
            case "7" :
            case "8" :
            case "10" :
            case "12" :total = 31;break;
            case "4" :
            case "6" :
            case "9" :
            case "11" :total = 30;break;
            case "2" :
                if((Flag%4 == 0 && Flag%100 != 0) || Flag%400 == 0){
                    total = 29;
                }else{
                    total = 28;
                }
            default:break;
        }
        for(day=1;day <= total;day++){
            currentDay = currentDay + "<option value='"+day+"'>"+day+"</option>";
        }
        $('#day1').html(currentDay);//生成日期下拉菜单
        })
}

function heightli(){
	var aUl=$('.r_b_always1 ul');
	for(var i=0;i<aUl.length;i++){
		var aLi=aUl[i].children;
		for(var j=0;j<aLi.length;j++){
			var oHeight=aUl[i].children[0].offsetHeight;
			aLi[j].style.height=oHeight+'px';
		}
	}
}