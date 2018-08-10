<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建APP活动</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link href="${path}/commons/css/reset.css" rel="stylesheet"
	type="text/css">
<link href="${path}/commons/css/promotion.css" rel="stylesheet"
	type="text/css">

<link rel="stylesheet" type="text/css"
	href="${path }/commons/css/coupon.css" />
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/app_activity_edit_fn.js"></script>
<script src="${path}/commons/js/plupload-master/js/plupload.full.min.js"></script>

<style type="text/css">
  .dpl-tip-inline-warning {color:red;}
  .donoevil{color:red;}
  .div1{width: 30px; height: 18px; position: absolute; left: 201px; top: 5px; border:1px solid #000; background-color: lightsalmon;}
  .div2{font-size:121%;}
</style>
</head>
<body>

	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->

	<div class="center">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/promotions/leftPage.jsp"%>
		</div>

		<!-- 左边 end -->
		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>促销管理&nbsp;&gt;&nbsp;</p>
					<p>活动管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">创建APP活动</p>
				</div>

				<div class="blank10"></div>

				<div class="promotion-wrap">
					<div class="promotion-bd manage-promotion">
						<div class="promotion-title">创建APP活动</div>
						<form method="post" id="activityAPPAction" enctype="multipart/form-data">	
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-name"><%--<i class="c_red">*</i>--%> 活动名称：</label> 
									<div class="field">
										<input type="hidden" name="activityId" id="activityId" value="${bean.activityId }">
										<input type="text" name="activityName" id="activityName" class="promotion-txt" value="${bean.activityName}">
										<div class="dpl-tip-inline-warning"></div>
									</div>
								</div>
								<div class="form-group">
								<input type="hidden" id="statuss" value="${bean.status }">
									<label for="promotion-organizers">活动类型：</label> 
									<c:if test="${bean.status==1}">
									     <select name="status" id="status" class="promotion-sel">
											<option selected="" value="1">限时抢购</option>
											<option select="" value="2">其他</option>
										</select>
									</c:if>
									<c:if test="${bean.status==2}">
									     <select name="status" id="status" class="promotion-sel">
											<option select="" value="1">限时抢购</option>
											<option selected="" value="2">其他</option>
										</select>
									</c:if>
									<c:if test="${bean.status==null }">
										<select name="status" id="status" class="promotion-sel">
											<option selected="" value="1">限时抢购</option>
											<option select="" value="2">其他</option>
										</select>
									</c:if>
								</div>
							</div>
							<div class="form-inline" id="mainPic">
								<div class="form-group">
									<label for="promotion-name">一级主图：</label>
									<div class="field">
										<input type="hidden" class="w240" id="mainPicUrl" name="mainPicUrl" readonly="readonly" value="${bean.mainPicUrl }" />
					                    <input readonly="readonly" class="promotion-txt"  id="browse" value="${bean.mainPicUrl }"> 
					                    <div id="start_upload" class="div1">
											<div class="div2">上传</div>
										</div>
										<div id="donoevil" class="donoevil"></div>
										<div class="dpl-tip-inline-warning" id="mainPicError"></div>
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-organizers">一级主图标题：</label> 
									<input type="text" name="mainTitle" id="mainTitle" class="promotion-txt" value="${bean.mainTitle }">
									<div class="dpl-tip-inline-warning"></div>
								</div>
							</div>
							<div class="form-inline" id="mainPic">
								<div class="form-group">
									<label for="promotion-name">pc一级主图：</label>
									<div class="field">
										<input type="hidden" class="w240" id="mainPicUrlpc" name="mainPicUrlpc" readonly="readonly" value="${config.custText01}" />
					                    <input readonly="readonly" class="promotion-txt"  id="browsepc" value="${config.custText01}"> 
					                    <div id="start_uploadpc" class="div1">
											<div class="div2">上传</div>
										</div>
										<div id="donoevilpc" class="donoevil"></div>
									</div>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-name">二级主图：</label>
									<div class="field">
										<input type="hidden" class="w240" id="picUrl" name="picUrl" readonly="readonly" value="${bean.picUrl }" />
					                    <input readonly="readonly" class="promotion-txt"  id="browses" value="${bean.picUrl }">
					                    <div class="div1" id="start_uploads" >
											<div class="div2">上传</div>
										</div>
										<div id="donoevils" class="donoevil"></div>
										<div class="dpl-tip-inline-warning" id="picError"></div>
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-organizers">二级主图标题：</label>
									<input type="text" name="title" id="title" class="promotion-txt" value="${bean.title }">
									<div class="dpl-tip-inline-warning"></div>
								</div>
							</div>
							<div class="form-inline" id="mainPic">
								<div class="form-group">
									<label for="promotion-name">pc二级主图：</label>
									<div class="field">
										<input type="hidden" class="w240" id="picUrlpc" name="picUrlpc" readonly="readonly" value="${config.custText02}" />
					                    <input readonly="readonly" class="promotion-txt"  id="browsespc" value="${config.custText02}"> 
					                    <div id="start_uploadspc" class="div1">
											<div class="div2">上传</div>
										</div>
										<div id="donoevilspc" class="donoevil"></div>
									</div>
								</div>
							</div>
							<div class="form-inline" id="timeTo">
								<div class="form-group">
									<label for="promotion-startTime">开始时间：</label>
									<div class="field">
										<input type="text" onClick="WdatePicker()" name="startTime" id="startTime" class="promotion-txt"
											value="<fmt:formatDate value="${bean.startTime }" pattern="yyyy-MM-dd HH:mm:ss" />">
											<div class="dpl-tip-inline-warning"></div>
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-endTime">结束时间：</label>
									<div class="field">
										<input type="text" onClick="WdatePicker()" name="endTime" id="endTime" class="promotion-txt"
											value="<fmt:formatDate value="${bean.endTime }" pattern="yyyy-MM-dd HH:mm:ss" />">
											<div class="dpl-tip-inline-warning"></div>
									</div>
								</div>
							</div>
							<%--<div class="form-group">
								<label for="promotion-des">活动描述：</label>
								<textarea name="description" id="description" class="promotion-textarea">${bean.description }</textarea>
								<span class="dpl-tip-inline-warning"></span>
							</div>
							--%><div class="form-inline">
								<div class="form-group">
									<input type="button" name="button" id="confirmButton" class="promotion-btn promotionManage-btn" value="确定">
								</div>
								<div class="form-group">
									<input type="button" name="backButton" id="backButton" class="promotion-btn promotionManage-btn" value="取消">
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="blank10"></div>
	<!-- 底部 start -->
	<div class="t_footer">
			<h1 class="logo_dl"></h1>
	</div>
	<!-- 底部 end -->
	<script type="text/javascript">

    //实例化一个plupload上传对象
    var uploader = new plupload.Uploader({
        browse_button : 'browse', //触发文件选择对话框的按钮，为那个元素id
        url : '<%=path %>/product/imageUp', //服务器端的上传页面地址
        flash_swf_url : 'js/Moxie.swf', //swf文件，当需要使用swf方式进行上传时需要配置该参数
        silverlight_xap_url : 'js/Moxie.xap', //silverlight文件，当需要使用silverlight方式进行上传时需要配置该参数
        filters: {
				  mime_types : [ //只允许上传图片
				    { title : "Image files", extensions : "jpg,gif,png" }
				  ],
				max_file_size : '2048kb', //最大只能上传400kb的文件
				prevent_duplicates : true //不允许选取重复文件
				},
		multi_selection : false		
				
    });    

    //在实例对象上调用init()方法进行初始化
    uploader.init();
	 
    //绑定各种事件，并在事件监听函数中做你想做的事
    uploader.bind('FilesAdded',function(uploader,files){
        //每个事件监听函数都会传入一些很有用的参数，
        //我们可以利用这些参数提供的信息来做比如更新UI，提示上传进度等操作
        /* alert(files): */
        console.log(files);
		$('#browse').val(files[0].name);
    });
    // 上传成功触发事件
    uploader.bind('FileUploaded',function(uploader,file,responseObject){
 	   console.log(responseObject.response);
    	var str = eval('['+responseObject.response+']');
    	$.each(str, function() {
        	if(this.success == "true"){
        		console.log(this.data.BaseUrl);
        		console.log(this.data.Url);
        		$('#mainPicUrl').val(this.data.BaseUrl);
        	//	alert("上传成功");
        		$('#donoevil').html("已上传");
        	}
  		});     
	});
    //最后给"开始上传"按钮注册事件
    document.getElementById('start_upload').onclick = function(){
        uploader.start(); //调用实例对象的start()方法开始上传文件，当然你也可以在其他地方调用该方法
    }
    
    
    
    //实例化一个plupload上传对象
    var uploaders = new plupload.Uploader({
        browse_button : 'browses', //触发文件选择对话框的按钮，为那个元素id
        url : '<%=path %>/product/imageUp', //服务器端的上传页面地址
        flash_swf_url : 'js/Moxie.swf', //swf文件，当需要使用swf方式进行上传时需要配置该参数
        silverlight_xap_url : 'js/Moxie.xap', //silverlight文件，当需要使用silverlight方式进行上传时需要配置该参数
        filters: {
				  mime_types : [ //只允许上传图片
				    { title : "Image files", extensions : "jpg,gif,png" }
				  ],
				max_file_size : '2048kb', //最大只能上传400kb的文件
				prevent_duplicates : true //不允许选取重复文件
				},
		multi_selection : false		
				
    });    

    //在实例对象上调用init()方法进行初始化
    uploaders.init();
	 
    //绑定各种事件，并在事件监听函数中做你想做的事
    uploaders.bind('FilesAdded',function(uploaders,files){
        //每个事件监听函数都会传入一些很有用的参数，
        //我们可以利用这些参数提供的信息来做比如更新UI，提示上传进度等操作
        /* alert(files): */
        console.log(files);
		$('#browses').val(files[0].name);
    });
    // 上传成功触发事件
    uploaders.bind('FileUploaded',function(uploaders,file,responseObject){
 	   console.log(responseObject.response);
    	var str = eval('['+responseObject.response+']');
    	$.each(str, function() {
        	if(this.success == "true"){
        		console.log(this.data.BaseUrl);
        		console.log(this.data.Url);
        		$('#picUrl').val(this.data.BaseUrl);
        		$('#donoevils').html("已上传");
        		alert("上传成功");
        	}
  		});     
	});
    //最后给"开始上传"按钮注册事件
    document.getElementById('start_uploads').onclick = function(){
        uploaders.start(); //调用实例对象的start()方法开始上传文件，当然你也可以在其他地方调用该方法
    }
    
    
    
    //实例化一个plupload上传对象 pc一级主图
    var uploaderpc = new plupload.Uploader({
        browse_button : 'browsepc', //触发文件选择对话框的按钮，为那个元素id
        url : '<%=path %>/product/imageUp', //服务器端的上传页面地址
        flash_swf_url : 'js/Moxie.swf', //swf文件，当需要使用swf方式进行上传时需要配置该参数
        silverlight_xap_url : 'js/Moxie.xap', //silverlight文件，当需要使用silverlight方式进行上传时需要配置该参数
        filters: {
				  mime_types : [ //只允许上传图片
				    { title : "Image files", extensions : "jpg,gif,png" }
				  ],
				max_file_size : '2048kb', //最大只能上传400kb的文件
				prevent_duplicates : true //不允许选取重复文件
				},
		multi_selection : false		
				
    });    

    //在实例对象上调用init()方法进行初始化
    uploaderpc.init();
	 
    //绑定各种事件，并在事件监听函数中做你想做的事
    uploaderpc.bind('FilesAdded',function(uploaderpc,files){
        //每个事件监听函数都会传入一些很有用的参数，
        //我们可以利用这些参数提供的信息来做比如更新UI，提示上传进度等操作
        /* alert(files): */
        console.log(files);
		$('#browsepc').val(files[0].name);
    });
    // 上传成功触发事件
    uploaderpc.bind('FileUploaded',function(uploaderpc,file,responseObject){
 	   console.log(responseObject.response);
    	var str = eval('['+responseObject.response+']');
    	$.each(str, function() {
        	if(this.success == "true"){
        		console.log(this.data.BaseUrl);
        		console.log(this.data.Url);
        		$('#mainPicUrlpc').val(this.data.BaseUrl);
        		alert("上传成功");
        		$('#donoevilpc').html("已上传");
        	}
  		});     
	});
    //最后给"开始上传"按钮注册事件
    document.getElementById('start_uploadpc').onclick = function(){
        uploaderpc.start(); //调用实例对象的start()方法开始上传文件，当然你也可以在其他地方调用该方法
    }
    
    
    
    //实例化一个plupload上传对象 pc二级主图 
    var uploaderspc = new plupload.Uploader({
        browse_button : 'browsespc', //触发文件选择对话框的按钮，为那个元素id
        url : '<%=path %>/product/imageUp', //服务器端的上传页面地址
        flash_swf_url : 'js/Moxie.swf', //swf文件，当需要使用swf方式进行上传时需要配置该参数
        silverlight_xap_url : 'js/Moxie.xap', //silverlight文件，当需要使用silverlight方式进行上传时需要配置该参数
        filters: {
				  mime_types : [ //只允许上传图片
				    { title : "Image files", extensions : "jpg,gif,png" }
				  ],
				max_file_size : '2048kb', //最大只能上传400kb的文件
				prevent_duplicates : true //不允许选取重复文件
				},
		multi_selection : false		
				
    });    

    //在实例对象上调用init()方法进行初始化
    uploaderspc.init();
	 
    //绑定各种事件，并在事件监听函数中做你想做的事
    uploaderspc.bind('FilesAdded',function(uploaderspc,files){
        //每个事件监听函数都会传入一些很有用的参数，
        //我们可以利用这些参数提供的信息来做比如更新UI，提示上传进度等操作
        /* alert(files): */
        console.log(files);
		$('#browsespc').val(files[0].name);
    });
    // 上传成功触发事件
    uploaderspc.bind('FileUploaded',function(uploaderspc,file,responseObject){
 	   console.log(responseObject.response);
    	var str = eval('['+responseObject.response+']');
    	$.each(str, function() {
        	if(this.success == "true"){
        		console.log(this.data.BaseUrl);
        		console.log(this.data.Url);
        		$('#picUrlpc').val(this.data.BaseUrl);
        		$('#donoevilspc').html("已上传");
        		alert("上传成功");
        	}
  		});     
	});
    //最后给"开始上传"按钮注册事件
    document.getElementById('start_uploadspc').onclick = function(){
    	uploaderspc.start(); //调用实例对象的start()方法开始上传文件，当然你也可以在其他地方调用该方法
    }
	</script>
</body>
</html>