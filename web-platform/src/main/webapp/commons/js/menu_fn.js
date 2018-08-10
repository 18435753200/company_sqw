var  CONTEXTPATH  = $("#conPath").val();
var THISPAGE = $("#pageContext").val();

$(document).ready(function(){
	$('.close-box').live('click',closebox);
});		

function saveMenu(){

	var checkstat = true;
	
	tipMessage('确定保存?',function(){
		
		$('#fm').find('input[type="text"]').each(function(){
			if($(this).val() == ''){
				checkstat = false;
			}
		});
		
		var fmdata = $('#fm').serialize();
		
		if ( fmdata.indexOf('undefined') == -1 && checkstat ){
			$.ajax({
				type:'post',
				url:CONTEXTPATH+'/menu/saveORupdateMenuById',
				data:fmdata+"&id="+Math.random(),
				error:function(){
					alert('~数据异常~');
				},
				success:function(data){
					if(data == '1'){
						alert('~~~保存成功~~~');
						resetfm();
						closebox();
						window.location.href=CONTEXTPATH+"/menu/toMenuPage";
					}else if(data == '-1'){
						alert('~~~菜单名称重复!~~~');
					}else{
						alert('~~~保存失败~~~');

					}
				}
			});
		}else{
			if(!checkstat){
				alert('表单数据必填');
			}else{
				alert('表单数据异常');
			}
		}
	});
}

function getMenuInfo(menuId,pId){
	
	resetfm();
	
	if ( menuId == '0' ){
		$('.lightbox-box-hd h2').html('添加菜单');
	}else{
		$('.lightbox-box-hd h2').html('修改菜单');
	}

	if(menuId != undefined && menuId != '' && menuId != '0'){

		$.ajax({
			type:'post',
			url:CONTEXTPATH+'/menu/getMenuInfo',
			data:"menuId="+menuId+"&id="+Math.random(),
			error:function(){
				alert('~数据异常~');
			},
			success:function(data){

				data = eval('('+data+')');

				var menuId = data.menuId;
				var name = data.name;
				var status = data.status;
				var parentMenuId = data.parentMenuId;
				var url = data.url;

				$('#name').val(name);
				$('#menuId').val(menuId);
				$('#url').val(url);
				$('#parentMenuId').val(parentMenuId);
				if(status == 0){
					$('#status option:eq(1)').attr('selected','selected');
				}
				if(status == 1){
					$('#status option:eq(0)').attr('selected','selected');
				}
				$('#EditMenu').css('display','block');
			}
		});
	}else{
		
		$('#parentMenuId').val(pId);
		$('#EditMenu').css('display','block');
		
	}
	
}
function upMenu(menuId,parentMenuId,menuName){
	var tipmsg = '';
	tipmsg = '确定要恢复~'+menuName+'~菜单吗？';
	
	$.dialog.tipMessage(tipmsg,function(){
		$.ajax({
			type:'post',
			url:CONTEXTPATH+'/menu/upMenuById',
			data:"menuId="+menuId+"&parentMenuId="+parentMenuId+"&menuName="+menuName+"&math="+Math.random(),
			error:function(){
				alert('~数据异常~');
			},
			success:function(data){
				if(data==0){
					alert('~恢复失败 怎么破~');
				}else{
					$.ajax({
						type:'post',
						url:CONTEXTPATH+'/menu/loadChildrenByPID',
						data:"pId="+parentMenuId,
						error:function(){
							alert('服务器忙，请稍后再试！');
						},
						success:function(data){
							$("#contentList").html(data);
						}
					});
					
				}
				//$("#contentList").html(data);//
				/**/
		  }
		});
	});
}

function delMenu(menuId,parentMenuId,menuName,target){
	var tipmsg = '';
	if (target==='0'){
		tipmsg = '确定要删除~'+menuName+'~菜单吗?  删除后菜单将不可恢复！请仔细判断。';
	}
	if (target==='1'){
		tipmsg = '确定要删除~'+menuName+'~菜单吗？ 本次删除后菜单不再展示在用户操作页面 。';
	}
	$.dialog.tipMessage(tipmsg,function(){
		$.ajax({
			type:'post',
			url:CONTEXTPATH+'/menu/delMenuById',
			data:"menuId="+menuId+"&target="+target+"&math="+Math.random(),
			error:function(){
				alert('~数据异常~');
			},
			success:function(data){
				if(data==0){
					alert('~删除失败 怎么破~');
				}else{
					$.ajax({
						type:'post',
						url:CONTEXTPATH+'/menu/loadChildrenByPID',
						data:"pId="+parentMenuId,
						error:function(){
							alert('服务器忙，请稍后再试！');
						},
						success:function(data){
							$("#contentList").html(data);
						}
					});
					
				}
				//$("#contentList").html(data);//
				/**/
		  }
		});
	});
	
}


var closebox = function (){
	
	$('#EditMenu').css('display','none');
	
};