$(document).ready(function(){
	jiben();
	tianjia();
	tianjia2();
	tianjia3();
	edituser();
	
	xuaze();
	del();
	//shan();
	gai();
	color();
});
//基本信息开始
var jiben=function(){
	$('.i1').attr('disabled','disabled');
	$('.te').attr('disabled','disabled');
}
//基本信息结束

// 权限页创建新角色
var tianjia=function(){
	$(".alert_user").hide();
	$(".b1").click(function(){
		$(".alert_user").show();
	});
	$(".b_colse").click(function(){
		$(".alert_user").hide();
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
	/*$(".dele").click(function(){
		$(this).parents("li").remove();
	})*/
}
//修改
var gai=function(){
	$(".span2").click(function(){
		$("#editDiv").show();
	})
}
/*function  deleteAll(){
	if($(":checkbox[name='nn'][checked]").length<=0){
		alert("请选择要删除的选项!");
	}else{
		$.dialog.confirm('确定删除选中用户?', function(){
			   $("#deleteForm").submit();
			}, function(){
			   // $.dialog.tips('执行取消操作');
			});
    	}
	}*/
// 删除按钮 start
var del=function(){
	$(".p2 .btn2").click(function(){
		if($(":checkbox[name='nn'][checked]").length<=0){
			alert("请选择要删除的选项");
		}else{
			$.dialog.confirm('确定删除选中用户?', function(){
				   $("#deleteForm").submit();
				}, function(){
				   // $.dialog.tips('执行取消操作');
				});
		}
		//$('.title_2').find('input:checked').parents('li').remove();
	})	
}
// 删除按钮 end


//用户页创建新用户
var tianjia2=function(){
	$("#addDiv").hide();
	$(".btn1").click(function(){
		$("#addDiv").show();
	})
	$("#addDiv .w_close").click(function(){
		$("#addDiv").hide();
		$("#addForm")[0].reset();	
	})
	
	$("#addUser").click(function(){
		var flag=true;
		var nameVal = $.trim($("#name").val());
		if(nameVal.length<=0){
			alert("用户名不能为空!");
			$("#name").focus();
			return;
		}
		
	/*	if(! new RegExp("^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+$").test(nameVal)){
			 alert("用户名只能是中文 英文 数字 - _ ");
			 return false;
		}*/
		if (!betweenLength(nameVal.replace(/[^\x00-\xff]/g, "**"), 4, 20)) {
			alert("用户名长度只能在4-20个字符之间");
			return false;
		}
	/*
		if(!nameVal.match(/[^\u4e00-\u9fa5]/g)){
			if(nameVal.length>20){
				alert("用户名中文长度不能大于20位!");
				$("#name").focus();
				return;
			}	
		}else {
			if(nameVal.length>26){
				alert("用户名英文长度不能大于26位!");
				$("#name").focus();
				return;
			}	
		}
       */
		/*if($.trim($("#password").val()).length<6||$.trim($("#password").val()).length>18){
			alert("密码为!");
			$("#password").focus();
			return;
		}	
		*/
		if (!betweenLength($.trim($("#password").val()).replace(/[^\x00-\xff]/g, "**"), 6, 20)) {
			alert("密码只能在6-20个字符之间");
			return false;
		}
		if (!betweenLength($.trim($("#repassword").val()).replace(/[^\x00-\xff]/g, "**"), 6, 20)) {
			alert("密码只能在6-20个字符之间");
			return false;
		}
		if($("#repassword").val()!=$("#password").val()){
			alert("两次密码不一致!");
			$("#repassword").focus();
			return;
		}	
		
		var roleVal = $.trim($("#roleId").val());
		if(roleVal.length<=0){
			alert("角色不能为空!");
			$("#roleId").focus();
			return;
		}

		$.ajax({
	         type: "POST",
	         url: "../user/isPinEngaged",
	         data: "pin="+nameVal,
	         success: function (result) {	
	        	 if(result>0){
	        	    alert("用户名已存在");
	        	    return false;
	        	 }else{
	        		$("#addForm").submit();
	        	 }
	          }
	 	   });
	})
	$("#addUserCancel").click(function(){
		$("#addDiv").hide();
		$("#addForm")[0].reset();	
	})
}
//用修改用户
var edituser=function(){
	$("#editDiv").hide();
	$("#editDiv .w_close").click(function(){
		$("#editDiv").hide();
		$("#editForm")[0].reset();	
	})
	
	$("#userEidt").click(function(){
		var flag=true;
		var nameVal = $.trim($("#namehidden").val());
		if(nameVal.length<=0){
			alert("用户名不能为空!");
			$("#name1").focus();
			return;
		}
		if (!betweenLength(nameVal.replace(/[^\x00-\xff]/g, "**"), 4, 20)) {
			alert("用户名长度只能在4-20个字符之间");
			return false;
		}
		if (!betweenLength($.trim($("#password1").val()).replace(/[^\x00-\xff]/g, "**"), 6, 20)) {
			alert("密码只能在6-20个字符之间");
			return false;
		}
		if (!betweenLength($.trim($("#repassword1").val()).replace(/[^\x00-\xff]/g, "**"), 6, 20)) {
			alert("密码只能在6-20个字符之间");
			return false;
		}

		if($("#repassword1").val()!=$("#password1").val()){
			alert("两次密码不一致!");
			$("#repassword1").focus();
			return;
		}	
		
		
		var roleVal = $.trim($("#roleId1").val());
		if(roleVal.length<=0){
			alert("角色不能为空!");
			$("#roleId1").focus();
			return;
		}
		$("#editForm").submit();	
	})
	$("#userEidtCancel").click(function(){
		$("#editDiv").hide();
		$("#editForm")[0].reset();	
	})
}
function betweenLength(str, _min, _max) {
    return (str.length >= _min && str.length <= _max);
 }

