$(document).ready(function(){
         $("#J_reviewsTabs li").on("click", function(){
     	    $(this).addClass("current").siblings().removeClass("current");
     	    var reviews=$(".J_reviewsList").eq($(this).index());
     	    $(reviews).show().siblings(".J_reviewsList").hide();
     	 });
         
         //处理滑动评价翻页
       	 //滑动翻页
         window.product_list_loadFlag = true;
         $(window).scroll(function(){
             var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());     //浏览器的高度加上滚动条的高度
             if (($(document).height() <= totalheight)&&window.product_list_loadFlag)     //当文档的高度小于或者等于总的高度的时候，开始动态加载数据
             {
                 //window.product_list_loadFlag = false;
                 //判断当前是否在查看评价，否则不执行翻页代码
                 if($("li[proComment]").hasClass("current")){
                	 //获得当前的查看的评价类型 
                	  var currentLi = $("#J_reviewsTabs li").filter(".current");
                	  //获取当前类型的总页数
                	  var totalPage = Number(currentLi.attr("pageNum"));
                	  //获取当前类型
                	  var commentType = currentLi.attr("commentTyp");
                	  //获取当前页码
                	  var currentPage = Number(currentLi.attr("currentNum"));
                    	//如果totalPage<2,不请求数据
                      if(totalPage>currentPage){
                      	  loadDataPager(currentPage+1,commentType);
                      } 
                 }
                 
             }   
        });     
});
function loadDataPager(pageNum,commentType){
 	//获取搜索keyword
 	var condition = "pid="+$("#PID").val()+"&pageNo="+pageNum+"&commentType="+commentType;
 	$.ajax({
 		type : "post",
 		url :$("#path").val()+"/comment/"+$('#PID').val()+"-"+commentType+"-"+pageNum,
 		data : condition,
 		dataType : "html",
 		success : function(commentInfo){
 			//依据commentType获取当前div
 			var currentDiv = $("div[commentTyp ='"+commentType+"']");
 			currentDiv.append(commentInfo);
 			//重置当前页码
 			$("#J_reviewsTabs li").filter(".current").attr("currentNum",pageNum);
 			appendDownUp();
 		}
 	});
}
    //评论内容折叠
      function appendDownUp(){	
     	   var comp = $('.com-box');
     	   comp.each(function(){
     		     if($($(this).find(".comd")).size()<1){
     		    	var conh = $(this).find('p').height();
         	   		 if(conh>50){
         				 $(this).find('.d').show();
         				 $(this).append("<div class='comd'><span class='down'></span><span class='up'></span></div>");
         				 $(this).find('.up').hide();
         			 }else{
         				 $(this).find('.d').hide(); 	  
         			 }	 
     		     }		    
     	   });
     	   $('.down').bind('click', function(){
     			var ph = $(this).parent().siblings().height();
     			$(this).parent().parent().css({'max-height':+ ph +'px'}); 
     			$(this).hide();
     			$(this).parent().find('.up').show();
     	   });
     	   $('.up').bind('click', function(){
     			$(this).hide();
     			$(this).parent().find('.down').show();
     			$(this).parent().parent().css({'max-height':'50px'});
     	   });	
     	   //评论内容折叠
      }
	      