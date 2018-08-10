<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
	<%--<div class="comment-box">
        <div class="rate"><strong>${commentStatistics.goodRatio}</strong><br>
            <span>好评度</span></div>
        <div class="percent">
            <dl>
                <dt>好评<span>(${commentStatistics.goodRatio}%)</span></dt>
                <dd>
                    <div style="width: ${commentStatistics.goodRatio}%;"></div>
                </dd>
            </dl>
            <dl>
                <dt>中评<span>(${commentStatistics.okRatio}%)</span></dt>
                <dd class="d1">
                    <div style="width: ${commentStatistics.okRatio}%;"> </div>
                </dd>
            </dl>
            <dl>
                <dt>差评<span>(${commentStatistics.badRatio}%)</span></dt>
                <dd class="d1">
                    <div style="width: ${commentStatistics.badRatio}%;"> </div>
                </dd>
            </dl>
        </div>
	</div>	
     --%>
     <div class="comment-hd">
          <ul class="comment-tab" id="J_reviewsTabs">
              <li class="current" commentTyp="0" currentNum="1" pageNum = "${allTotalPageNum}"><a href="javascript:;">全部(${commentStatistics.totalCount})</a></li>
              <li commentTyp="1" currentNum="1" pageNum = "${goodTotalPageNum}"><a href="javascript:;">好评(${commentStatistics.goodCount})</a></li>
              <li commentTyp="2" currentNum="1" pageNum = "${okTotalPageNum}"><a href="javascript:;">中评(${commentStatistics.okCount})</a></li>
              <li commentTyp="3" currentNum="1" pageNum = "${badTotalPageNum}"><a href="javascript:;">差评(${commentStatistics.badCount})</a></li>
          </ul>
      </div>
      
      <div class="comment-bd">
          <div class="comment-list J_reviewsList" commentTyp="0" currentNum="1" pageNum = "${allTotalPageNum}">
          </div>
          <div class="comment-list J_reviewsList hide" commentTyp="1" currentNum="1" pageNum = "${goodTotalPageNum}">            
          </div>
          <div class="comment-list J_reviewsList hide" commentTyp="2" currentNum="1" pageNum = "${okTotalPageNum}">        
          </div>
          <div class="comment-list J_reviewsList hide" commentTyp="3" currentNum="1" pageNum = "${badTotalPageNum}">          
          </div>
      </div>
      
<script type="text/javascript">
$(document).ready(function(){
	var history=[0,0,0,0];	
	if($(".J_reviewsList .current").find("div").length==0){
		loadDataPager(1,0)
	}	
	
    $("#J_reviewsTabs li").on("click", function(){
	    $(this).addClass("current").siblings().removeClass("current");
	    var reviews=$(".J_reviewsList").eq($(this).index());
	    $(reviews).show().siblings(".J_reviewsList").hide();  
	    if($(reviews).find("div").length==0){
			loadDataPager(1,$(this).index())
		}	
	 });
    
    //处理滑动评价翻页
  	 //滑动翻页
    $(window).scroll(function(){
        var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());     //浏览器的高度加上滚动条的高度
        if ($(document).height() <= totalheight)     //当文档的高度小于或者等于总的高度的时候，开始动态加载数据
        {
            //判断当前是否在查看评价，否则不执行翻页代码
            if($(".tabs").find(".ping").hasClass("on")){
             
           	 //获得当前的查看的评价类型 
           	  var currentLi = $("#J_reviewsTabs li").filter(".current");
           	  //获取当前类型的总页数
           	  var totalPage = Number(currentLi.attr("pageNum"));
           	  //获取当前类型
           	  var commentType = currentLi.attr("commentTyp");
           	  var currentDiv = $("div[commentTyp ='"+commentType+"']");
           	  //获取当前页码
           	  var currentPage = Number(currentLi.attr("currentNum"));
                 if(totalPage>currentPage){
                	 //console.log($(currentDiv).attr("currentNum") + " - " +currentPage);
                 	  loadDataPager(currentPage+1,commentType);
                 } 
            }
        }   
   });    
    function loadDataPager(pageNum,commentType){
    	if(history[commentType]<pageNum){
    		history[commentType]=pageNum;
    		//获取搜索keyword
        	$.ajax({
        		type : "post",
        		url :$("#path").val()+"/comment/"+$('#PID').val()+"-"+commentType+"-"+pageNum,
        		dataType : "html",
        		success : function(commentInfo){
        			//依据commentType获取当前div
        			var currentDiv = $("div[commentTyp ='"+commentType+"']");
        			currentDiv.append(commentInfo);
        			//重置当前页码
        			$("#J_reviewsTabs li").filter(".current").attr("currentNum",pageNum);
        			$(currentDiv).attr("currentNum",pageNum);
        			appendDownUp();
        		}
        	});
    	}	
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
});

     
</script>