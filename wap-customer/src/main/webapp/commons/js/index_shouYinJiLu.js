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
        var  id=$("#id").val();
        window.location.href=$("#path").val()+"/wealth/supplierMoneyJL?id="+id;
  
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
    		var page = $("#page").val();
        	var totalPage = $("#totalPage").val();
        	if(totalPage== 1 || page == totalPage){
        		bottomTip.innerText = '到底啦！';
        	}else{
        		bottomTip.innerText = '上拉加载更多';
        	}
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
	var id = $("#id").val();
	var today = $("#today").val();
	$.ajax({
		type: 'POST',
		url: $("#path").val()+'/wealth/more_shouYinJiLi',
		dataType: 'json',
		async:false,
		data:"page="+ page+"&id="+id+"&day="+today,
		success: function(data) {
			$("#page").val(data.page);
			//isShowMore();
			for(var i in data.result){
				var result="";
				var dat=dateFtt("yyyy-MM-dd hh:mm:ss",new Date(data.result[i].recordTime));
				result+="<li class=\"list-item\">";
				result+="<div class=\"aui-card-list\">";
				result+="<div class=\"aui-card-list-header\">";
				result+="<div class=\"aui-info-item\">";
				result+="<span class=\"aui-margin-l-0\">";
				result+=dat;
				result+="</span></div>";
				result+="<div class=\"aui-info-item\" style=\"text-align:right\">";
				result+="交易号："+data.result[i].orderId+"</div>";
				result+="</div>";
				
				result+="<div class=\"aui-card-list-content-padded\">";
				result+="<b style=\"color:#000;font-weight: 500;font-size: 0.8rem\">";
				result+=data.result[i].cashierName;
				result+="</b>";
				result+="<b style=\"margin-left: 1rem;font-weight:500;color:#b4b5b4\">";
				result+=data.result[i].cashierMobile;
				result+="</b>";
				var price = data.result[i].orderPrice;
				var num2=price.toFixed(3);
				var a=num2.substring(0,num2.lastIndexOf('.')+3);
//				var price = data.result[i].orderPrice;
//				var orderPrice = price.toFixed(2);
				result+="<b style=\"color:#000;font-weight: 500;font-size: 1.3rem; float: right;line-height: 1;\">";
				result+=a;
				//				result+=data.result[i].orderPrice;
				result+="</b>";
				result+="</div>";
				result+="</div></li>";
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