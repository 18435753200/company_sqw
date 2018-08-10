<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>UNICORN-创建热词</title>
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
	<script type="text/javascript" src="${path}/commons/js/hotWords_list_fn.js"></script>
	
</head>
<style>
.center{width: 1000px;min-height:100%;	margin: 0 auto;	text-align:left\9; 	margin-top: 10px;}
.jh-main{  margin:0 auto;border:1px solid #e2e9ee; width:938px; overflow:hidden; padding:30px; margin-top:10px}
.jh-list li{ margin-bottom:20px;overflow:hidden;}
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
    width: 120px;

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
					<p>商品管理&nbsp;&gt;&nbsp;</p>
					<p>热词列表&nbsp;&gt;&nbsp;</p>
					<c:if test="${not empty findHotWord}">
						<p class="c1">修改热词</p>
					</c:if>
					<c:if test="${empty findHotWord}">
						<p class="c1">创建热词</p>
					</c:if>
					<div class="clear"></div>
				</div>
			</div>
			
<div class="jh-main">

			<form method="post" id="productAction" enctype="multipart/form-data">
				<ul class="jh-list">
					<li>
						<label>描叙：</label>
						<input name="kw" type="text" class="inputname" value="${findHotWord.kw }">
						<span class='web'>最多显示20个字</span>
					</li>
<!-- 					<li class="hdlj"> -->
<!-- 						<label>链接地址：</label> -->
<%-- 						<input name="url" type="text" class="inputname" value="${findHotWord.url }"> --%>
<!-- <!-- 						<span  class='web'>请填写连接</span> -->
<!-- 					</li> -->
					
					<li>
						<div class="btn_box">
							<c:if test="${not empty findHotWord}">
								<input type="hidden" name="id" value="${findHotWord.id }" />
								<button class="fabu_btn" id="updateHotWords" type="button">修改</button>
							</c:if>
							<c:if test="${empty findHotWord}">
								<button class="fabu_btn" id="saveHotWords" type="button">创建</button>
							</c:if>
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
		adduploadimg2('topic','00',''); 
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