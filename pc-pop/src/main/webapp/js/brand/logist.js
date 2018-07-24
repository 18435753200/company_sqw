$(document).ready(function (){
	$(".select-val2").hide();
	function  logistics(){
	    window.location.href="add.html";
	}
	function  addlogistics(){
		window.parent.location=host+"/order/getLogisticPageTpl";
	}
	/*function area(){
		window.parent.location=host+"/limit/list";
	}*/
	window.onload = pageLoad;
	function pageLoad(){
		document.getElementById("template1").style.display = "none";
		document.getElementById("template2").style.display = "none";
		document.getElementById("template3").style.display = "none";
	}
	$(".sui-modal").hide();
	$(".sui-modal2").hide();
	$(".sui-modal3").hide();
	$("#checkbox-id1").click(function(){
		$("#template1").show();	
		$("#template2").hide();	
		$("#template3").hide();	
		$(".check-table1").hide();
		$(".check-body1").hide();
	});
	$("#checkbox-id2").click(function(){
		$("#template2").show();
		$("#template1").hide();	
		$("#template3").hide();	
		$(".check-table2").hide();
		$(".check-body2").hide();
	});
	$("#checkbox-id3").click(function(){
		$("#template3").show();	
		$("#template1").hide();	
		$("#template2").hide();	
		$(".check-table3").hide();
		$(".check-body3").hide();
	});
	var n1=0;
	var n2=0;
	var n3=0;
	$(".o-item1").click(function(){
		$(".check-table1").show();
		$(".check-body1").show();
		n1++;
		if(n1!=1){
			 $(".check-body1").append('<div><span>未添加地区&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="check_place'+n1+'">编辑</a></span><span><input type="text" name="startNum"></span><span><input type="text" name="startPrice"></span><span><input type="text" name="addNum"></span><span><input type="text" name="addPrice"></span><span><a href="">删除</a></span></div>');
		}
		$(".check_place1").click(function(){
			popCenterWindow();
	    })
	    $(".check_place2").click(function(){
		    
		    popCenterWindow2();
	    })
	    $(".check_place3").click(function(){
		    
		    popCenterWindow3();
	    })
		
	})
	$(".o-item2").click(function(){
		$(".check-table2").show();
		$(".check-body2").show();
		n2++;
		if(n2!=1){
			 $(".check-body2").append('<div><span>未添加地区&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="check_place'+n2+'">编辑</a></span><span><input type="text" name="startNum"></span><span><input type="text" name="startPrice"></span><span><input type="text" name="addNum"></span><span><input type="text" name="addPrice"></span><span><a href="">删除</a></span></div>');
		}
		$(".check_place1").click(function(){
			popCenterWindow();
	    })
		$(".check_place2").click(function(){
		    
		    popCenterWindow2();
	    })
		 $(".check_place3").click(function(){
		    
		    popCenterWindow3();
	    })
	})
	$(".o-item3").click(function(){
		$(".check-table3").show();
		$(".check-body3").show();
		n3++;
		if(n3!=1){
			 $(".check-body3").append('<div><span>未添加地区&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="check_place'+n3+'">编辑</a></span><span><input type="text" name="startNum"></span><span><input type="text" name="startPrice"></span><span><input type="text" name="addNum"></span><span><input type="text" name="addPrice"></span><span><a href="">删除</a></span></div>');
		}
		$(".check_place1").click(function(){
			popCenterWindow();
	    })
		$(".check_place2").click(function(){
		    
		    popCenterWindow2();
	    })
		$(".check_place3").click(function(){
			popCenterWindow3();
	    })
		
	})
	$(".sui-modal-close").click(function(){
		$(".sui-modal").hide();
	})
	$(".sui-modal-close2").click(function(){
		$(".sui-modal2").hide();
	})
	$(".sui-modal-close3").click(function(){
		$(".sui-modal3").hide();
	})
	$(".sui-modal-close4").click(function(){
		$(".sui-moda4").hide();
	})
	$(".operate").click(function(){
		popCenterWindow3();
	})
	$(".yes_btn").click(function(){
		window.location.href="add.html";
	})
	$(".sui-option").click(function(){
		var sel_value=$(this).val();
		console.log(sel_value);
		if(sel_value==3){
			$(".select-val1").hide();
			$(".select-val2").show();
			/*$(".select-val1").append('+<input type="text" />')*/
		}else{
			$(".select-val1").show();
			$(".select-val2").hide();
		}
		
	})
	$("#check-val").click(function(){
		if($("#check-val").attr('checked')){
			var check_value=$(this).val();
			$('.text-show span').text(check_value)
			console.log(check_value);
		}else{
			console.log(0);
		}
	})
	$(".o-item4").click(function(){
		
	    $(".sui-table-tbody").append('<tr><td style="width:20%"><div class="b-text mui-flex align-center"><div class="text-show"><span>未添加地区</span><div class="operate">编辑</div></div></div></td><td style="text-align:center"><select class="sui-option"><option>快递</option><option>EMS</option><option>平邮</option></select></td><td style="text-align:center"><select class="sui-option"><option value="1">件数</option><option value="1">金额</option><option value="3">件数+金额</option></select><div class="select-val1"><input type="text"></div><div class="select-val2"><input type="text">+<input type="text"></div></td></tr>');
	})
	

})
//获取窗口的高度 
var windowHeight; 
// 获取窗口的宽度 
var windowWidth; 
// 获取弹窗的宽度 
var popWidth; 
// 获取弹窗高度 
var popHeight; 
//获取窗口的高度 
var windowHeight2; 
// 获取窗口的宽度 
var windowWidth2; 
// 获取弹窗的宽度 
var popWidth2; 
// 获取弹窗高度 
var popHeight2; 
function init(status){ 
    windowHeight=$(window).height(); 
    windowWidth=$(window).width();
    if(status==1){
    	 popHeight=$(".sui-modal").height(); 
    	    popWidth=$(".sui-modal").width(); 
    }
    if(status==2){
   	 popHeight=$(".sui-modal2").height(); 
   	    popWidth=$(".sui-modal2").width(); 
   }
    if(status==3){
   	 popHeight=$(".sui-modal3").height(); 
   	    popWidth=$(".sui-modal3").width(); 
   }
    if(status==4){
      	 popHeight=$(".sui-moda4").height(); 
      	    popWidth=$(".sui-moda4").width(); 
      }
   
}
//关闭窗口的方法 
function closeWindow(){ 
    $(".title img").click(function(){ 
        $(this).parent().parent().hide("slow"); 
    }); 
    $("#closeShipWinBtn").click(function(){ 
        $("#center").hide("slow"); 
    }); 
} 
function closeWindow2(){ 
    $(".title img").click(function(){ 
        $(this).parent().parent().hide("slow"); 
    }); 
    $("#closeShipWinBtn").click(function(){ 
        $("#center2").hide("slow"); 
    }); 
} 
function closeWindow3(){ 
    $(".title img").click(function(){ 
        $(this).parent().parent().hide("slow"); 
    }); 
    $("#closeShipWinBtn").click(function(){ 
        $("#center3").hide("slow"); 
    }); 
} 
function closeWindow4(){ 
    $(".title img").click(function(){ 
        $(this).parent().parent().hide("slow"); 
    }); 
    $("#closeShipWinBtn").click(function(){ 
        $("#center4").hide("slow"); 
    }); 
} 
//定义弹出居中窗口的方法 
function popCenterWindow(){ 
    init(1); 
    //计算弹出窗口的左上角Y的偏移量 
	var popY=(windowHeight-popHeight)/40 + document.body.scrollTop; 
	var popX=(windowWidth-popWidth)/2; 
	//alert('jihua.cnblogs.com'); 
	//设定窗口的位置 
	$("#center").css("top",popY).css("left",popX).slideToggle("fast");  
	closeWindow(); 
}
function popCenterWindow2(){ 
    init(2); 
    //计算弹出窗口的左上角Y的偏移量 
	var popY=(windowHeight-popHeight)/40 + document.body.scrollTop; 
	var popX=(windowWidth-popWidth)/2;
	//alert('jihua.cnblogs.com'); 
	//设定窗口的位置 
	$("#center2").css("top",popY).css("left",popX).slideToggle("fast");  
	closeWindow2(); 
}
function popCenterWindow3(){ 
    init(3); 
    //计算弹出窗口的左上角Y的偏移量 
	var popY=(windowHeight-popHeight)/2 + document.body.scrollTop; 
	var popX=(windowWidth-popWidth)/2; 
	//alert('jihua.cnblogs.com'); 
	//设定窗口的位置 
	$("#center3").css("top",popY).css("left",popX).slideToggle("slow");  
	closeWindow3(); 
}
function popCenterWindow4(){ 
    init(4); 
    //计算弹出窗口的左上角Y的偏移量 
	var popY=(windowHeight-popHeight)/2 + document.body.scrollTop; 
	var popX=(windowWidth-popWidth)/2; 
	//alert('jihua.cnblogs.com'); 
	//设定窗口的位置 
	$("#center4").css("top",popY).css("left",popX).slideToggle("slow");  
	closeWindow4(); 
}

	