var  CONTEXTPATH  = $("#conPath").val();

/*	商品js	*/
$(document).ready(function(){
	
	/**
	 * 创建热词
	 */
	$("#saveHotWords").click(function(){
//		$("#productAction").action(CONTEXTPATH + "/Activity/saveActivity");
		$.ajax({
			type:'post',
			url:CONTEXTPATH+'/hotWords/saveHotWords',
			data: $('#productAction').serialize(),
			success:function(data){
				if(data=='1'){
					tipMessage("保存成功，关闭当前页面！",function(){
						reloadOpennerPage();
					});
				}else{
					tipMessage("保存失败，请检查后重试！",function(){
						$(".fabu_btn").removeAttr("disabled");
					});
				}

			}
		});
		
	});

	/**
	 * 修改热词
	 */
	$("#updateHotWords").click(function(){
		$.ajax({
			type:'post',
			url:CONTEXTPATH+'/hotWords/updateHotWords',
			data: $('#productAction').serialize(),
			success:function(data){
				if(data=='1'){
					tipMessage("保存成功，关闭当前页面！",function(){
						reloadOpennerPage();
					});
				}else{
					tipMessage("保存失败，请检查后重试！",function(){
						$(".fabu_btn").removeAttr("disabled");
					});
				}

			}
		});
		
	});
});


function operateStatus(hotId){
	
	$.dialog.confirm('确定执行此操作吗？', function(){
		var pro_array  = new Array();
		
		pro_array.push("hotId="+hotId);
		
		$.ajax({
			type:'post',
			url:CONTEXTPATH+'/hotWords/deleteHotWords',
			data: pro_array.join("&"),
			success:function(data){
				if(data=='1'){
					alert("删除成功!");
					clickSubmit();
				}else{
					alert("删除失败!");
				}

			}
		});
	});
	
}

function clickSubmit(){

	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/hotWords/getHotWordsList", 
		dataType:"html",
		success : function(msg) { 
			$("body").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 


}

function reloadOpennerPage(){
	var elnode = window.opener.document.getElementById("pageContext"); 
	var page = $(elnode).val();
	window.opener.location.href="javascript:clickSubmit()";
	
	window.opener=null;
	window.open('','_self');
	window.close();
}
