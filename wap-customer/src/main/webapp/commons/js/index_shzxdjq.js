$(function(){
	var page = $("#page").val();
	var totalPage = $("#totalPage").val();
	if(totalPage==0 ||totalPage== 1 || page == totalPage){
		bottomTip.innerText = '到底啦！';
	}
})
var listWrapper = document.querySelector('.list-wrapper-hook'),
    listContent = document.querySelector('.list-content-hook'),
    alert = document.querySelector('.alert-hook'),
    topTip = document.querySelector('.refresh-hook'),
    bottomTip = document.querySelector('.loading-hook');
/*
 * 此处可优化,定义一个变量,记录用户滑动的状态,初始值为0,滑动中为1,滑动结束、数据请求成功重置为0
 * 防止用户刷新列表http请求过多
*/






function initScroll() {
//	var cc = $("#thelist li").last();
//	console.log(cc);
  var scroll = new window.BScroll(listWrapper, {
    probeType: 1
  });
  
  
  
  // 滑动中
  scroll.on('scroll', function (position) {
    if(position.y > 30) {
      topTip.innerText = '释放立即刷新';
    }
  });
  
  
  
  /*
   * @ touchend:滑动结束的状态
   * @ maxScrollY:屏幕最大滚动高度
  */ 
  // 滑动结束
  scroll.on('touchend', function (position) {
    if (position.y > 30) {
      
      setTimeout(function () {
        /*
         * 这里发送ajax刷新数据
         * 刷新后,后台只返回第1页的数据,无论用户是否已经上拉加载了更多
        */
        // 恢复文本值
        topTip.innerText = '下拉刷新';
        var  account=$("#account").val();
        window.location.href=$("#path").val()+"/wealth/detail_shzxdjq";

        // 刷新成功后的提示
        refreshAlert('刷新成功');
        // 刷新列表后,重新计算滚动区域高度
        scroll.refresh();
      }, 100);
    }else if(position.y < (this.maxScrollY - 30)) {
      setTimeout(function () {
        // 恢复文本值 
    	var page = $("#page").val();
    	var totalPage = $("#totalPage").val();
    	if(totalPage== 1 || page == totalPage){
    		bottomTip.innerText = '到底啦！';
    	}else{
    		var page = parseInt($("#page").val())+1;
    		// 向列表添加数据
    		reloadData(page);
    		bottomTip.innerText = '上拉加载更多';
    		// 加载更多后,重新计算滚动区域高度
    	scroll.refresh();
    	}
      }, 100);
    }
  });
}
initScroll();
// 加载更多方法\
function reloadData(page) {
	var account = $("#account").val();
	$.ajax({
		type: 'POST',
		url: $("#path").val()+'/wealth/more_shzxdjq',
		dataType: 'json',
		async:false,
		data:"page="+ page+"&account="+account,
		success: function(data) {
			
			
			$("#page").val(data.page);
			//isShowMore();
			for(var i in data.result){
				var result="";
				var dat=dateFtt("yyyy-MM-dd hh:mm:ss",new Date(data.result[i].operatorTime));
				result+="<li class=\"list-item\">";
				result+="<div class=\"aui-card-list\">";
				result+="<div class=\"aui-card-list-header\">";
				result+="<div class=\"aui-info-item\">";
				result+="<span class=\"aui-margin-l-0\">";
				result+=dat;
				result+="</span></div>";
				result+="<div class=\"aui-info-item\">";
				if(account==6){
					result+="订单号："+data.result[i].refObjId+"</div>";
				}else{
					
					result+="交易号："+data.result[i].refObjId+"</div>";
				}
				result+="</div>";
				result+="<div class=\"aui-card-list-content-padded\">";
				result+="<b style=\"font-weight: 500;\">";
				
				/*result+=data.result[i].recordTypeString+"</b>";*/

					
				result+="<b style=\"margin-left: 1rem;color: red;font-size: 0.8rem;font-weight: 500\">";
				if(data.result[i].earning>0){
					var price = data.result[i].earning;
					var num2=price.toFixed(3);
					var a=num2.substring(0,num2.lastIndexOf('.')+3);
					result+="+<span>"+a+"</span>M券</b>";	
				}else{
					var price = data.result[i].expenditure;
					var num2=price.toFixed(3);
					var a=num2.substring(0,num2.lastIndexOf('.')+3);
					result+="-<span>"+a+"</span>M券</b>";
				}
				var price = data.result[i].balance;
				var num2=price.toFixed(3);
				var a=num2.substring(0,num2.lastIndexOf('.')+3);
				result+="&nbsp;&nbsp;<b>余额："+a+"</b></div>";
				/*result+="<div class=\"aui-card-list-content-padded\">";
				result+="<b  style=\"color:#B8B8B8;font-weight: 500;font-size: 0.6rem\">用户ID:</b>";
				result+="<b style=\"margin-left: 1rem;font-weight:500\">"+data.result[i].userId+"</b>";
				result+="<b  style=\"color:#000;font-weight: 500;font-size: 0.6rem;float: right;\">用户名称："+data.result[i].name+"</b>";
				result+="</div>"*/
				result+="<div class=\"aui-card-list-content-padded\">";
				result+="<b>备注:</b>"
				result+="<b style=\"color:#B8B8B8;font-weight: 500;padding-left: 0.5rem;font-size: 0.6rem\">";
				result+=data.result[i].memo+"</b>";
				result+="</div></div></li>";
				$("#thelist li").last().after(result);
				
			}
			
		},
		error: function() {
		}
	});

}

// 刷新成功提示方法
function refreshAlert(text) {
  text = text || '操作成功';
  alert.innerHtml = text;
  alert.style.display = 'block';
  setTimeout(function(){
    alert.style.display = 'none';
  },100);
}