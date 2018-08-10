$(function(){
	Flash();
	jquerytab('box');
	jquerytab('box1');	
	//注册有礼
    $('#Arule').on('click', function() {
	   $('.window_mask').show();
	   $('.foot_box').show();
	});
	
	$('.window_mask').on('click', function() {
	   $(this).hide();
	   $('.foot_box').hide();
	});
	
})
$(window).resize(function() {
	Flash();
});

var Flash = function(){
	 var imgh = $('.no-b').height();
	 $('.shop_cart a').css({"height": imgh-15 + "px"});
	 
	 //首页焦点图
	 var swiper = new Swiper('#index-container', {
        pagination: '#index-pagination',
        nextButton: '#index-button-next',
        prevButton: '#index-button-prev',
        slidesPerView: 1,
        paginationClickable: true,
        spaceBetween: 0,
        loop: true
     });
	 
	 //专题焦点图
	 $(".flash-list .swiper-container").each(function(i){
	   var swiper = new Swiper('#swiper-container'+i, {
			pagination: '#swiper-pagination'+i,
			nextButton: '#swiper-button-next'+i,
			prevButton: '#swiper-button-prev'+i,
			slidesPerView: 1,
			paginationClickable: true,
			spaceBetween: 0,
			loop: true
		});
	});
	
	//海外直邮国家列表
	var mySwiper1 = new Swiper('#col-nav',{
	   freeMode : true,
	   slidesPerView : 'auto',
	});
	
}


//专题修改
function jquerytab(name){
   var oDome=$('#' + name);
   var oSpan=oDome.find('.sp-nav li');
   var oP=oDome.find('.sp-body .column_body');
   oSpan.click(function(){
		$(this).addClass('cur').siblings().removeClass('cur');
		var index =oSpan.index(this); 
		oP.eq(index).show().siblings().hide();
   });
}
 
 
 
  

	