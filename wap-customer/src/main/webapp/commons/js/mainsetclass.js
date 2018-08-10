//公用userMain调用 
$(function(){
		$(".footer li.myset").each(function(){
		   $(this).addClass("set").add().siblings().removeClass("set");
		});
});