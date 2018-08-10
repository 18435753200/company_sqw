$(document).ready(function(){
	addRole();
	editRole();
	xuaze();
	color();
});
//基本信息开始


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

//用户页创建新用户
var addRole=function(){
	$("#add_div").hide();
	$("#addbutton").click(function(){
		$("#add_div").show();
	})
	$("#add_div .w_close").click(function(){
		$("#add_div").hide();
		$("#addForm")[0].reset();	
	})
	
	$("#addBtn").click(function(){
	
		var nameVal = $.trim($("#name").val());
		if(nameVal.length<=0){
			alert("角色名不能为空!");
			$("#name1").focus();
			return;
		}
		if (!betweenLength(nameVal.replace(/[^\x00-\xff]/g, "**"), 4, 20)) {
			alert("角色名长度只能在4-20个字符之间");
			return false;
		}
		$.ajax({
	         type: "POST",
	         url: "../role/checkRoleOnly",
	         data: "roleName="+nameVal,
	         success: function (result) {
	        	 if(result>0){
	        	    alert("角色名已存在");
	        	    return false;
	        	 }else{
	        		$("#addForm").submit();
	        	 }
	          }
		});
	})
	$("#addBtnCancel").click(function(){
		$("#add_div").hide();
		$("#addForm")[0].reset();	
	})
}
//用修改用户
var editRole=function(){
	$("#edit_div").hide();
	
	$("#edit_div .w_close").click(function(){
		$("#edit_div").hide();
		$("#editForm")[0].reset();	
	})
	
	$("#editBtn").click(function(){
		var flag=true;
		//var name1 = $("#name1").val().trim();
		var nameVal = $.trim($("#name1").val());
		if(nameVal.length<=0){
			alert("角色名不能为空!");
			$("#name1").focus();
			return;
		}
		if (!betweenLength(nameVal.replace(/[^\x00-\xff]/g, "**"), 4, 20)) {
			alert("角色名长度只能在4-20个字符之间");
			return false;
		}
		var hiddenName1 = $("#hiddenName1").val();
		if(nameVal!=hiddenName1){
			$.ajax({
		         type: "POST",
		         url: "../role/checkRoleOnly",
		         data: "roleName="+nameVal,
		         success: function (result) {	
		        	 if(result>0){
		        	    alert("角色名已存在");
		        	    return false;
		        	 }else{
		        		 $("#editForm").submit();	
		        		 $("#edit_div").hide(20);
		        	 }
		          }
			});
		}else{
			 $("#edit_div").hide(50);
		}

	})
	$("#editBtnCancel").click(function(){
		$("#edit_div").hide();
		$("#editForm")[0].reset();	
	})
}
function betweenLength(str, _min, _max) {
    return (str.length >= _min && str.length <= _max);
 }

