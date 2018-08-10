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
					<p>尖货设置&nbsp;&gt;&nbsp;</p>
					<p>话题设置&nbsp;&gt;&nbsp;</p>
					<p class="c1">创建话题</p>
					<div class="clear"></div>
				</div>
			</div>
			
<div class="jh-main">

			<form method="post" id="productAction" enctype="multipart/form-data">
				<ul class="jh-list">
			     	<li>
						<label>分 类：</label>
						<select name="activityType" id="selt">
								<option value="1">单品</option>
								<option value="2">活动</option>
								<option value="3">视频</option>
						</select>
					</li>
					<li>
						<label>标 题：</label>
						<input name="title" id="title" type="text" class="inputname">
						<span style="display:none;" id="titleHidden" class='web'>最多显示20个字</span>
					</li>
					<li class="hdlj" style="display:none;" id="activityUrlLi">
						<label>活动链接：</label>
						<input name="activityUrl" id="activityUrl" type="text" class="inputname">
						<span  style="display:none;" id="activityUrlHidden" class='web'>请填写活动连接</span>
					</li>
					<li class="splj"  style="display:none;" id="viewUrlLi">
						<label>视频连接：</label>
						<input name="viewUrl" id="viewUrl" type="text" class="inputname">
						<span  style="display:none;" id="viewUrlHidden" class='web'>请填写视频连接</span>
					</li>
					<li>
						<label>首 图：</label>
                        <div class="upimg">
                            <div class="jinben" style="padding:0px;">
	 						   <div  class="z" id="zizhi">
									<ul id="00_img" style="width:100%;    overflow: hidden;">
										<li class="img-1">
										    <div class="p-img"></div>
										</li>
									</ul>
								</div>
						    </div>
                       </div>
						
						<span  class='web'>图片大小小于等于100K</span>
					</li>
					<li>
						<label>简 述：</label>
						<textarea name="sketich" cols="" rows="" class="textcent" id="sketich"></textarea>
						<span  class='web' id="sketichHidden">文字控制在500之内</span>
					</li>
					
					<li class="neirong" >
						<label>内 容：</label>
						<div class="bjqcent">
						<div class="i_box">
								<div class="blank20"></div>
								<!--style给定宽度可以影响编辑器的最终宽度-->
								<div style="width: 804px">
								<!-- <span class="dpl-tip-inline-warning" id="Details1">sdfsdfds</span>-->
									<script id="editor1" type="text/plain" style="width:804px;">
								${b2cDescription}
    						</script>
								</div>
								<script type="text/javascript">
									//实例化编辑器
									    var option = {
									    		textarea: 'editor1' //设置提交时编辑器内容的名字 
									    		};
									    var ue = new UE.ui.Editor(option);
									    ue.render("editor1");
									
								</script>
							</div>
						
						</div> 
							
						<span  class='web'>图片大小：小于等于100K</span>
					</li>
					
					<li>
						<div class="btn_box">
<!-- 							<button class="fabu_btn" id="previewProd" type="button">预览</button> -->
							<button class="fabu_btn" id="saveActivityTopic" type="button">创建</button>
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