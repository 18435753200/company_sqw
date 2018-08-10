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
       // topTip.innerText = '下拉刷新';
//        var cityId = $("#cityId").val();
//        window.location.href=$("#path").val()+"/supplier/thousandCityWanShop?cityId="
//					+ cityId;
  
        // 刷新成功后的提示
        //refreshAlert('刷新成功');
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
	var cityId = $("#cityIds").val();
	var sectorCodeName = $("#sectorCodeName").val();
	$.ajax({
		type: 'POST',
		url: $("#path").val()+'/supplier/ajaxPullUp',
		dataType: 'json',
		async:false,
		data:"pageNo="+ page+"&sectorCodeName="+sectorCodeName+"&cityId="+cityId,
		success: function(data) {
			$("#page").val(data.data.page);
			for(var i in data.data.searchSupplier.items){
				var result="";
				result+="<li class=\"list-item\">";
				result+="<div class=\"aui-card-list\">";
				result+="<div class=\"aui-card-list-header\">";
				if(data.data.searchSupplier.items[i].companyStoreLogo!=null){
					result+="<img src='//image01.zhongjumall.com/d1/"+data.data.searchSupplier.items[i].supplierDetail.companyStoreLogo+"' class='dp_pic'"+"onclick='lookDetail("+data.data.searchSupplier.items[i].supplierId+")'>";
					
				}else{
					result+="<img src='"+$("#path").val()+"/commons/img/qcwd/dp_ban.jpg' class='dp_pic'>";
				}
				result+="<div class=\"dp_b\"><p>";
				result+="<img src='"+$("#path").val()+"/commons/img/qcwd/zjm_2.png' style='width: 10%; width: 15%; display: inline; position: relative; top: rem; background: #fff; border-radius: 30px; padding: 0.4rem;' />";
				result+="<b style='margin-left: 1rem'>"+data.data.searchSupplier.items[i].supplierDetail.nameJC+"</b></p><p>"+data.data.searchSupplier.items[i].supplierDetail.locationPoiaddress+"</p>";
				result+="<a href='tel:"+data.data.searchSupplier.items[i].supplierDetail.contactTel+"'><img src='https://cms.3qianwan.com/mImg/2018/07/07/5413bb339fe24630ad030f04735dc123.png' class='qcwd_tel' style='width: 30px;height: 30px;'></a>";
				result+="</div></div></div></li>";
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