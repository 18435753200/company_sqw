$(function(){
	
	 //滑动翻页
    window.product_list_loadFlag = true;
    var totalPage = Number($("#totalPage").val());
    var keyword = $.trim($(".search-text").val());
    var pageNum =2;
    $(window).scroll(function(){
        var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());     //浏览器的高度加上滚动条的高度
        if (($(document).height() <= totalheight)&&window.product_list_loadFlag)     //当文档的高度小于或者等于总的高度的时候，开始动态加载数据
        {
            window.product_list_loadFlag = false;
            if(totalPage>=2){
            	loadDataPager();
            }
        }   
   });
    
    function loadDataPager(){
    	//获取搜索keyword
    	var condition = "pageNum="+pageNum;
    	$.ajax({
    		type : "post",
    		url :$("#path").val()+"/index/indexPage",
    		data : condition,
    		dataType : "html",
    		success : function(indexPageInfo){
    			$(".brand-list").append(indexPageInfo);
    			 pageNum++;
    			 if(pageNum<=totalPage){
    				 window.product_list_loadFlag = true;
    			 }
    			 
    		} 
    	});
   } 
	
})


//触发评价按钮
function findAllTypeComment(){
	var pid = '966649148651077';
	var condition="pid="+pid;	
	$.ajax({
		type : "post",
		url :$("#path").val()+"/findAllTypeComment",
		data : condition,
		dataType : "html",
		success : function(commentInfo){
			$("#commentInfo").html("");
			$("#commentInfo").append(commentInfo);
		}
	})
}



//校验输入是否为数字
function regint(num){
	return /^[\d]+$/.test(num);
}

//截取字符串，小数点后两位
function splitString(str){
	var index = str.indexOf(".");
	if(index == -1){
		return str;
	}
	return str.substring(0,index+3);
}


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
    auto : 3000,
    continuous: true,
    callback : function (pos) {
        docs[index].className='';
        docs[pos].className='on';
        index = pos ;
    }
    }));
}