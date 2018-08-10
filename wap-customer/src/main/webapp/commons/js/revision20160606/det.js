
$(function(){

	swipe = [];
	  $(".swipe").each(function(i,item){
	    var iid = $(item).attr("id");
	    initslider(iid);
	  });
	  $(".swipe").removeClass('top');
	  $(".swipe").eq(0).addClass("top");


	//分享
	$('.share').click(function(){
		$('.sha').show()
	});
	$('.cancel,.share_bg').click(function(){
		$('.sha').hide()
	});

	//税费
	$('.de_taxation').click(function(){
		$('.tax_mask').show();
	});
	$('#close,.tax_frame').click(function(){
		$('.tax_mask').hide();
	});


	//商品参数
	$('#gin1').click(function(){
		$('.parameter').addClass('show');
	});
	$('#close2,.para_bg').click(function(){
		$('.parameter').removeClass('show');
	});

	//选择规格
	$('#gin2').click(function(){
		$('.m_size').addClass('show');
	});
	$('.mclose,.m_bg').click(function(){
		$('.m_size').removeClass('show');
		editGuige();
	});

	
	//保税区-服务说明
	$('.de_ret').click(function(){
		$('#exp1').addClass('show');
	});
	$('.exp_bg,.clos').click(function(){
		$('#exp1').removeClass('show');
	});
	

	//商品 详情 评价
	$('.head ul li').click(function(){
		var i = $(this).index();
		$(this).addClass('on').siblings().removeClass('on');
		$(".wrap .divw").eq(i).show().siblings().hide();
		$('html,body').animate({ scrollTop: '0px'}, 100); 
    });

	//图片详情和购买须知
	$('.notice ul li').click(function(){
		var i = $(this).index();
		$(this).addClass('on').siblings().removeClass('on');
		$(".nowrap .nodiv").eq(i).show().siblings().hide();
		$('html,body').animate({ scrollTop: '0px'}, 100); 
    });

	//全部评价
	$('.all_list ul li').click(function(){
		var i = $(this).index();
		$(this).addClass('on').siblings().removeClass('on');
		$(".all_w .all_box").eq(i).show().siblings().hide();
    });

	//查看更多评价
	$('.rev_more').click(function(){
			$('.head .ping').addClass('on').siblings().removeClass('on');			
			$('#d').show().siblings().hide(); 
    });
   
});

//处理轮播
function initslider(id){
   var elm = document.getElementById(id);
   if (!elm) return ;
   var doc = document.getElementById(id+'-doc') ;
   var slides = elm.children[0].children;
   var temp = '';
   var index = 0 ;
   for (var i = 0 , len=slides.length; i < len ; i++) {
       temp += '<em '+(i==0?'class="on"':'')+'></em>';
   }
   doc.innerHTML = temp ;
   var docs = doc.querySelectorAll('em');
   swipe.push(new Swipe(elm,{
   	speed: 400,
   	//auto : 3000,
   	continuous: true,
   	callback : function (pos) {
   		docs[index].className='';
   		docs[pos].className='on';
   		index = pos ;
   	}
   }));
}

 