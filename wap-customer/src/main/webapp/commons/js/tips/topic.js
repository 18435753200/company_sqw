$(function(){
	 //图片懒加载
     Echo.init({
       offset: 0,
       throttle: 0
     });
     //轮换图片滚动
	 var swiper = new Swiper('#index-container', {
		pagination: '#index-pagination',
		slidesPerView: 1,
       paginationClickable: true,
		loop: true,
		autoplay:5000,
	    autoplayDisableOnInteraction: false
	 });
	
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
	    var condition = "page="+pageNum;
		$.ajax({
			type : "post",
			url :$("#path").val()+"/view/topic/topicScroll",
			data : condition,
			dataType : "html",
			success : function(commentInfo){
				 $(".loadw").before(commentInfo);
				 pageNum++;
				 if(pageNum<=totalPage){
					 window.product_list_loadFlag = true;
				 }else{
					 $(".loadw").find("span").html("");
				 }
			}
		});
	}
});
/*
点赞
*/
function clicklike(topicId,likeNum){
var condition = "topicId="+topicId+"&likeNum="+likeNum+"";
$.ajax({
	type : "post",
	url :$("#path").val()+"/view/topic/clicklike",
	data : condition,
	dataType : "text",
	async : false,
	success : function(addCartFlag){
		if(addCartFlag=='100'){
			$("#tip_"+topicId).addClass("selected");
			$("#num_"+topicId).text(likeNum+1);
		}
	}
});
}