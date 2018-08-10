$(document).ready(function(){
	jiben();
	tianjia();
	tianjia2();
	tianjia3();
	
	xuaze();
	del();
	tianjia2();
	shan();
	gai();
	color();
});
//基本信息开始
var jiben=function(){
	$('.i1').attr('disabled','disabled');
	$('.te').attr('disabled','disabled');
	$('.fabu_btn').on('click',function(){
		if($(this).text()=="修改"){
			$('.i1').removeAttr('disabled');
			$('.te').removeAttr('disabled');
			$(this).text("保存");
		}else{
			$('.i1').attr('disabled','disabled');
			$('.te').attr('disabled','disabled');
			$(this).text("修改");
		}
	})	
}
//基本信息结束


// 权限页创建新角色
var tianjia=function(){
	$(".alert_user").hide();
	$(".b1").click(function(){
		$(".alert_user").show();
		
		// $(".bor .bor_1 h6").each(function(index){
		// 	$(".bt1").click(function(){
		// 		$(".in_text").html().appendTo('$(".bor")');
		// 		$(".alert_user").hide();
		// 	});	
		// });
	});
	$(".b_colse").click(function(){
		$(".alert_user").hide();
	})
}

// 用户页创建新用户
var tianjia2=function(){
	$(".alert_user2").hide();
	$(".btn1").click(function(){
		$(".alert_user2").show();
	})
	$(".w_close").click(function(){
		$(".alert_user2").hide();
	})
}

// 权限页创建新角色
var tianjia3=function(){
	$(".alert_user").hide();
	$(".w .box1 h2").click(function(){
		$(".alert_user").show();
	})
	$(".b_colse").click(function(){
		$(".alert_user").hide();
	})
}


// 奇偶行背景颜色
var color=function(){
	$(".title_2 li").each(function(){
		$(".title_2 li:even()").css("background","#f1f1f1")
	})
}

//全选开始 
var xuaze=function(){
    $(".f_l").click(function(){
		var flg=this.checked
		$(":checkbox[name='nn']").attr("checked",flg);
	});
	$(":checkbox[name='nn']").click(function(){
		$(".f_l").attr("checked",$(":checkbox[name='nn']").length==$(":checkbox"))
	});
}
//全选结束

// 删除单行 
var shan=function(){
	$(".dele").click(function(){
		$(this).parents("li").remove();
	})
}
//修改
var gai=function(){
	$(".span2").click(function(){
		$(".alert_user2").show();
	})
}

// 删除按钮 start
var del=function(){
	$(".p2 .btn2").click(function(){
		$('.title_2').find('input:checked').parents('li').remove();
	})	
}
// 删除按钮 end

// 用户页创建新用户
var tianjia2=function(){
	$(".alert_user2").hide();
	$(".btn1").click(function(){
		$(".alert_user2").show();
	})
	$(".w_close").click(function(){
		$(".alert_user2").hide();
	})
}


