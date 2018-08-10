$(function(){
    
    //滑动翻页
    window.product_list_loadFlag = true;
    var totalPage = Number($("#totalPage").val());
    var pageNum =2;
    $(window).scroll(function(){
        var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());     //浏览器的高度加上滚动条的高度
        if (($(document).height() <= totalheight)&&window.product_list_loadFlag)     //当文档的高度小于或者等于总的高度的时候，开始动态加载数据
        {
            window.product_list_loadFlag = false;
            //如果totalPage<2,不请求数据
            if(totalPage>=2){
            	loadDataPager();
            }
        }   
   });
    
    function loadDataPager(){
    	//获取搜索keyword
    	var condition = "isB2C="+true+"&pageno="+pageNum;
    	$.ajax({
    		type : "post",
    		url :$("#path").val()+"/searchController/toSearchResultScroll",
    		data : condition,
    		dataType : "html",
    		success : function(commentInfo){
    			$(".p-list").append(commentInfo);
    			 pageNum++;
    			 if(pageNum<=totalPage){
    				 window.product_list_loadFlag = true;
    			 }
    			 
    		}
    	
    	});
   } 
});