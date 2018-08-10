<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>UNICORN-创建话题</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
<%-- 	<link rel="stylesheet" href="${path }/commons/css/reset.css"> --%>
	<link rel="stylesheet" href="${path }/commons/css/shang.css">
	<link rel="stylesheet" href="${path }/commons/css/lightbox.css">
	<link rel="stylesheet" href="${path }/commons/js/uploadify/uploadify.css">
	
	<script type="text/javascript" src="${path}/product/ueditor.config.js"></script>
	<script type="text/javascript" src="${path}/product/ueditor.all.js"></script>
	
	
	<script type="text/javascript" src="${path}/commons/js/uploadify/jquery.uploadify.js"></script>
	<script type="text/javascript" src="${path}/commons/js/uploadify/queue.js"></script>
	
	<script type="text/javascript" src="${path}/commons/js/swfUploadEventHandler.js"></script>
	<script type="text/javascript" src="${path}/commons/js/shang.js"></script>
	<script type="text/javascript" src="${path}/commons/js/activityTopic.js"></script>
	
</head>
<style>
.center{width: 1000px;min-height:100%;	margin: 0 auto;	text-align:left\9; 	margin-top: 10px;}
.jh-main{  margin:0 auto;border:1px solid #e2e9ee; width:938px; overflow:hidden; padding:30px; margin-top:10px}
.jh-list li{ margin-bottom:20px;    clear: both;
    position: relative;}
.jh-list li label{width:80px; float:left; font-size:14px; color:#666; line-height:35px; }
.jh-list li input.inputname{ padding:8px 10px;width:500px;}
.jh-list li .textcent{ padding:8px 10px;width:500px; height:100px;}
.jh-list li span.web{ color:#f00; display:block;width:100%;    clear: both; margin-left:80px; padding-top:10px;}
.upimg{width:524px; float:left}
.jh-list select{ height:35px; line-height:35px; width:100px;}
.bjqcent{width:698px;float:left;}
.upimg .z{width:100%;}
.z li{margin-left:0px;}
.z .wenzi{margin:0px;}
.btn_box {margin: 0 auto;  width: 300px;}
.btn_box button {
    cursor: pointer;
    float: left;
    font-size: 18px;
    height: 40px;
    line-height: 36px;
    margin: 10px;
    text-align: center;
    width: 120px;
}
.btn_box .fabu_btn {
    background: #fa4700 none repeat scroll 0 0;
    border: 0 none;
    border-radius: 5px;
    color: #fff;
    height: 37px;
    line-height: 36px;
    text-align: center;
    width: 120px;}
    .linkUrl{width:300px; position: absolute;    left: 150px; top:0px; }

</style>
<body>
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
	
<div class="center">



<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>尖货设置&nbsp;&gt;&nbsp;</p>
					<p>话题设置&nbsp;&gt;&nbsp;</p>
					<p class="c1">Banner设置</p>
					<div class="clear"></div>
				</div>
			</div>
			
<div class="jh-main">
			<input type="hidden" id="bannerId" value="banner" />
			<form method="post" id="productAction" enctype="multipart/form-data">
				<ul class="jh-list">
			     	<li>
						<div class="upimg">
                            <div class="jinben" style="padding:0px;">
	 						  
						    </div>
                       </div>
                       <span class='web'>图片大小等于：小于100K</span>
                       <span style="display:none;" id="imgHidden" class='web'>图片url或链接不能为空!</span>
					</li>
					<li>
						<div class="btn_box" style="width:450px;text-align: center;">
							<button style="display:none;" class="fabu_btn" id="previewProd" type="button" onclick="javascript:reviewBanner()">预览</button>
							<button class="fabu_btn" type="button" onclick="javascript:saveBannerTitle(1)">保存</button>
							<button class="fabu_btn" type="button" onclick="javascript:saveBannerTitle(2)">发布</button>
							<p class="clear"></p>
						</div>
					</li>
				</ul>
			</form>
		</div>
		</div>
		
				
 </div>

 
	<div class="blank10"></div>	
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	
	<!-- 底部 end -->
	<!-- 底部 end -->
	<script type="text/javascript">
	$(document).ready(function(){
		var url_array;
		if('${jsonImg}'!="" && '${jsonImg}'!="[]"){
			url_array = ${jsonImg};
			$.each(url_array,function(n,value){
// 				alert($.stringify(value['test']));

				adduploadimg2(value['test'],'00',value['test']); 
			});
			
			$("#previewProd").show();
		}else{
			adduploadimg2('banner','00',''); 
		}
	});
	$("#selt").change( function() {
	 	 var selt =$("#selt").find("option:selected").val();
		  if(selt==1){
			$('.neirong').show();	
			$('.hdlj,.splj').hide(); 
		 }
		 if(selt==2){
			$('.hdlj').show();	
			$('.neirong,.splj').hide(); 
		 }
		  if(selt==3){
			$('.splj,.neirong').show();	
			$('.hdlj').hide();
		 }
	});

	</script>
</body>
</html>