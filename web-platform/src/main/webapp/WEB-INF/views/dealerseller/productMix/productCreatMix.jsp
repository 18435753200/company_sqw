<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-情景组合列表</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path }/commons/css/comm.css"/>
    <link href="/web-platform/commons/js/my97/skin/WdatePicker.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${path }/commons/js/productMix_list_fn.js"></script>
    <script src="${path}/commons/js/my97/WdatePicker.js"></script>
    <script src="${path}/commons/js/plupload-master/js/plupload.full.min.js"></script>

</head>
<body>
	
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
    <div class="blank"></div>

	<div class="center">
		<!--中间左边开始-->
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->
		<!--中间左边结束-->
		<div class="alert_user3" style="display: none;">
			<div class="bg"></div>
			<div class="w">
				<div class="box1">
					<img src="${path}/commons/images/close.jpg" class="w_close">
				</div>
				<div class="box3">
					<p id="showmsgplat"></p>
				</div>
				<div class="blank20"></div>
			</div>
		</div>
		<div class="c2">
				<div class="right">

			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>商品储存&nbsp;&gt;&nbsp; </p>
					<p class="c1">新建商品情景组合</p>
				</div>
	        </div>
			<div class="xia">
				<!-- <p class="p1">
					<span>创建日期：</span>
					<input type="text">
					<span>创建人员：</span>
					<input type="text">
				</p> -->
				<p class="p1">
					<span>商品情景组合名称：</span>
					<input type="text" class="w240" id="sceneName">
				</p>
				<p class="p1">
					<span>商品情景组合图:</span>
					<input type="hidden" class="w240" id="detailImageUrl" readonly="readonly" >
					<input readonly="readonly" class="w240"  id="browse" placeholder="选择图片" >
					<input type="hidden" id="mixImageUrl">
					 
				<div class="fi">
					<div class="fi_w" id="start_upload" >
						<div class="fi_z">上传</div>
					</div>
					<button type="button" class="pic" onclick="openWin()">图片预览</button>
			    </div>
					
					
				</p>
				<p class="p1">
					<span>默认组合数量:</span>
					<input type="text" readonly="readonly" value="3" id="sceneNum">
				</p> 
				<p class="p1 fawei">
					<span>适用范围: </span><!--
					<em><input name="" type="checkbox" value=""><label>PC</label></em>
					<em><input name="" type="checkbox" value=""><label>APP</label></em>
					<em><input name="" type="checkbox" value=""><label>WAP</label></em>
					<em><input name="" type="checkbox" value=""><label>PAD</label></em>-->
					<em><input checked="checked" type="checkbox" id="sceneRange" value="0" disabled="disabled"><label>B2C</label></em>
				</p>
				<p class="p1">
					<span>页面显示排列方式:</span>
					<select id="showStyle">
						<option value="0">网络</option>
						<option value="1">列表</option>
					</select>
					<!-- <span id="wd">横向:</span>
					<input type="text"> -->
					<!--<span id="wd">纵向:</span>
					<input type="text">-->
				</p>
			</div>

			<div class="pu_wrap">
				<div class="pu_hd">
				   <h3>商品列表:</h3>
					<div class="btn">
						<a href="#" class="add" onclick="addClickSubmit(1)">添加商品</a>
						<!--<a href="#" class="save" onclick="saveMix()">保存</a>-->
						<input type="button" value="保存" class="save" onclick="saveMix()" >
						<!--<a href="#">审核</a>-->
					</div>
				</div>

				<div class="pu_bd" id="tab">
					<table>
						<tr class="order_hd">
							<th width="80px;">组号</th>
							<th width="130px;">商品ID</th>	
							<th width="130px;">商品条码</th>
							<th width="150px;">商品名称</th>
							<th width="120px;">默认标记</th>
							<th width="120px;">默认图片</th>
							<th width="60px;">组合数量</th>
                            <th width="40px;">操作</th>
						</tr>					</table>
				</div>

			</div>

		</div>

			</div>



		</div>

	</div>
			
		<div class="center1">
    	<div class="back"></div>
		<div class="right1">
			
			<div class="pu_hd">
				<h3>查询条件:</h3>
				<p class="close"></p>
			</div>
			<div class="xia">
			<!-- 	<p class="p1 mr">
					<span>商品类目:</span>
					<select><option>一级类目</option></select>
					<select><option>二级类目</option></select>
					<select><option>三级类目</option></select>
					<select><option>四级类目</option></select>
				</p> -->
				<p class="p1">
					<span>商品ID:</span>
					<input type="text" class="w240" id="productId2">
					<span>商品条码:</span>
					<input type="text" class="w240" id="skuCode2">
				</p>
				<p class="p1">
					<span>商品名称:</span>
					<input type="text" class="w240" id="productName2">
					
					<span>供应商名称:</span>
					<input type="text" class="w240" id="supplierName2">
				</p>
				<p class="p2">
					<button type="button" onclick="addClickSubmit()">查询</button>
				</p>
			</div>


			<div class="pu_wrap">
				<div class="pu_hd">
				   <h3>商品列表:</h3>

				</div>

				<div class="pu_bd" id="pu_wrap2">
					<!-- <table>
						<tr class="order_hd">
							<th width="40px;">序号</th>
							<th width="150px;">商品编码</th>
							<th width="150px;">商品条码</th>
							<th width="180px;">商品名称</th>
							<th width="180px;">商品图片</th>
							<th width="120px;">操作</th>

						</tr>
						<tr>
							<td>1</td>
							<td>AAA1</td>
							<td>1234561</td>
							<td>商品名称商品名称1</td>
							<td>图片图片1</td>
							<td class="verify">确认</td>
						</tr>
						<tr>
							<td>2</td>
							<td>AAA2</td>
							<td>1234562</td>
							<td>商品名称商品名称2</td>
							<td>图片图片2</td>
							<td class="verify">确认</td>
						</tr>
						<tr>
							<td>3</td>
							<td>AAA3</td>
							<td>1234563</td>
							<td>商品名称商品名称3</td>
							<td>图片图片3</td>
							<td class="verify">确认</td>
						</tr>
					</table> -->
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
		
<script>

    //实例化一个plupload上传对象
    var uploader = new plupload.Uploader({
        browse_button : 'browse', //触发文件选择对话框的按钮，为那个元素id
       	url : '${path}/product/imageUp',//服务器端的上传页面地址
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
        		$('#detailImageUrl').val(this.data.BaseUrl);
        		$('#mixImageUrl').val(this.data.Url);
        		alert("上传成功");
        	}
  		});     
	});
    //最后给"开始上传"按钮注册事件
    document.getElementById('start_upload').onclick = function(){
        uploader.start(); //调用实例对象的start()方法开始上传文件，当然你也可以在其他地方调用该方法
    }
</script>	
</body>
</html>