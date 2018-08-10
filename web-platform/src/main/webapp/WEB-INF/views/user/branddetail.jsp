<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
	<head>
	  <meta charset="UTF-8">
	  <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	   <%@include file="/WEB-INF/views/include/base.jsp"%>
	   <title>UNICORN-品牌审核-品牌详情-${fn:escapeXml(brand.name)}</title>
	   <link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben1.css">
	   <script type="text/javascript" src="${path}/commons/js/user/jiben.js"></script>
	   
		<style type="text/css">
	     input:focus{
			border:1px solid #09F;
			outline-style:none;
		}
		.input_warning{
			float:left;
		    font-family:Arial,"宋体",Lucida,Verdana,Helvetica,sans-serif;
			font-size:12px;
			padding-top:4px;
			padding-left:24px;
		}
		#thief_warning{
			height:12px;
		}
		ol, ul,li {
			list-style: none;
		}
	</style>
    </head>
	<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	
	 <div class="wrap">
		<%@include file="/WEB-INF/views/include/leftUser.jsp"%>
		<div class="right f_l">
			<div class="title">

				<p class="c1">基本信息</p>
				<div class="clear"></div>
			</div>
			<div class="blank5"></div>
			<div class="cont">
     			<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>品牌名称：</p>
						<input class="i1" name ="companyName" id="brandName" value="${fn:escapeXml(brand.name)}"  onblur="validate(this, 'notnull,biglong')" />
						<span  class="input_warning"></span>
					</li>
					
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>供应商姓名：</p>
						<input class="i1" name ="legalPerson" value="${fn:escapeXml(brand.supplierName)}"  onblur="validate(this, 'notnull,biglong')" />
							<span  class="input_warning"></span>
					</li>
					
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>代理类型：</p>
						<input class="i1" name ="legalPersonNo" value="<c:if test="${brand.type==0 }">普通代理</c:if><c:if test="${brand.type==1 }">独家代理</c:if><c:if test="${brand.type==2 }">自主品牌</c:if>"  onblur="validate(this, 'notnull,biglong')" />
							<span  class="input_warning"></span>
					</li>
					

						<li class="blank20"></li>
				    <li>
						<p class="p1"><b style=" color: #FF0000;">*</b>创建时间：</p>
						<input class="i1" name="address" value="<fmt:formatDate value="${brand.createTime }" pattern="yyyy-MM-dd"/>" onblur="validate(this, 'notnull,biglong')" >
							<span  class="input_warning"></span>
					</li>
						<li class="blank20"></li>
					
					
					<c:if test="${!empty qualification }">
						<li class="blank20"></li>
						<p class="p1"><b style=" color: #FF0000;"></b>资质：</p>
						<li>
							<div class="pic-list">
						<c:forEach items="${qualification }" var="qualification" varStatus="i">
								<a href="${path }/user/downloadFile?url=${qualification }" target="_blank"><img src="${qualification }" onerror="" style="width: 130px;height: 130px"></a>
						</c:forEach>
							</div>
						</li>
					</c:if>
					
					
					<li>
						<p class="p1"><b style=" color: #FF0000;"></b>品牌描述：</p>
						<br>
						${brand.description}
						<span  class="input_warning"></span>
						
					</li>
					<li>
					<p class="blank"></p>
				
					<input type="button"  class="fabu_btn" onclick="javascript:history.back();" value="返回" >
					
					<p class="blank30"></p>
					<p class="blank30"></p>
					<div class="eck">
					   <c:if test="${1!=brand.status and 2!=brand.status and  !empty buttonsMap['品牌审核-审核品牌'] }">
						<div class="eck1"><button type="text" onclick="updateCheck(1)"  class="tong1">通过</button></div>
						<div class="eck2">
							<textarea id="remark" rows=""  name="remark" cols=""></textarea>
						</div>
						<div class="eck3"><button type="text" onclick="updateCheck(2)"  class="tong2">不通过</button></div>
						</c:if>
					    <c:if test="${2==brand.status}">
						    <span style="color:red;">审核不通过原因</span>
						    <div style="color:red;"></div>
							<div class="eck2">
									<textarea id="reason" rows="" disabled="disabled"  name="reason" cols="">${brand.remark}</textarea>
							</div>
						</c:if>
				    </div>
				    <!--  <input type="button"  class="fabu_btn" onclick="javascript:history.back();" value="返回" /> -->
					<p class="blank30"></p>
			</div>
		</div>
		<!-- 右边 end -->
	</div>
	<p class="blank30"></p>
		   
		</div>
		<div class="clear"/></div>
		
		
		 <div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	<input type="hidden" id="systemBrandId" name = "systemBrandId" value="${brand.systemBrandId }">
	<input type="hidden" id="brandId" name = "brandId" value="${brand.brandId }">
	
<div class="lightbox" id="error-box"s>
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box"></div>
		<div class="lightbox-box-hd">
			<h2>请修改品牌名称</h2>
		</div>
		<div class="lightbox-box-bd">
			<form id="alertProductStopReason">
				<span>英文名称：</span><input type="text" id="enBrand" name="enBrand" class="name-input">
				<div class="clear"></div>
				<span>中文名称：</span><input type="text" id="zhBrand" name="zhBrand" class="name-input">
			</form>
		</div>
		<div class="lightbox-box-bar">
			<a href="javascript:void(0);" class="lightbox-btn true-btn" onclick="saveBrandENAndZHName()">提 交</a>
			<span style="margin-left: 20px;color: red;" id="boxwarn"></span>
		</div>
	</div>
</div>
		
	</body>
    <script type="text/javascript">
          $(function(){
        	  $("#remark").keyup(function(){
        	   var len = $(this).val().length;
        	   if(len > 1000){
        	    $(this).val($(this).val().substring(0,500));
        	    alert("字数不能超过500字!");

        	   }
        	  });
        	  
        	  $(".close-box").live("click",closebox);
        	 });
          
	      	var closebox = function(){
	//    		close-box
	    		$("#error-box").fadeOut();
	    	};
          function updateCheck(status){
        	  
       	    var brandId = $("#brandId").val();
			var systemBrandId = $("#systemBrandId").val();
			var name = $("#brandName").val();
			var remark = $("#remark").val();
			
      		if(status==2&&$("#remark").val()==''){
      			alert("请填写审核不通过原因!");
      			return;
      		}else{
      			if(status==1){
	  				$.ajax({
	  			         type: "POST",
	  			         data: 'brandId='+brandId+'&systemBrandId='+systemBrandId+'&name='+name+"&"+Math.random(),
	  			         url: "${path}/brand/checkBrand",
	  			         dataType:'json',
	  			         success: function (result) {
	  			        	 if(result == '1'){
	  			        		 //如果是返回的是1 那么说明品牌已经存在 直接
	  			        		 $.ajax({
				  			         type: "POST",
				  			         data: 'status='+status+'&brandId='+ brandId+'&remark='+remark+'&math='+Math.random(),
				  			         url: "${path}/brand/brandAudit",
				  			         dataType:'json',
				  			         success: function (result) {
				  			        	 if(result == '1'){
				  			        		 tipMessage("审核成功!",function(){
							        			 location.reload();
												});
					  			        	 } 
				  			        	 if(result == '0'){
					  			        		alert("保存失败!");
					  			        	 }
				  			        	 
				  			        	 
				  			        	 }
				  			         });
	  			        		 
	  			        	 }
	  			        	 if(result == '0'){
	  			        		 //弹框填名字
	  			        		var brandName = $("#brandName").val();
	  			        		$("#enBrand").val(brandName);
	  				          	$("#zhBrand").val(brandName);
								$("#error-box").css("display","block");
								
	  			        	 }
	  			        	 
	  			        	if(result == '-1'){
	  		      				alert("服务异常!请稍后再试！！！");
	  		      			}
	  			        	 
	  			          },error:function(){
	  			        	alert("服务异常!");
	  			          }
	  			 	   });
	    			
      			}
      			if(status==2){
      				 $.ajax({
	  			         type: "POST",
	  			         data: 'status='+status+'&brandId='+brandId+'&remark='+remark+'&math='+Math.random(),
	  			         url: "${path}/brand/brandAudit",
	  			         dataType:'json',
	  			         success: function (result) {
	  			        	 if(result == '1'){
	  			        		 tipMessage("审核成功!",function(){
				        			 location.reload();
									});
		  			        	 } 
	  			        	 if(result == '0'){
		  			        		alert("保存失败!");
		  			        	 }
	  			        	 
	  			        	 
	  			        	 }
	  			         });
      			}
      			
      			
					
      		}
		}
          
          function saveBrandENAndZHName(){
        	 
	          	var enBrand = $("#enBrand").val();
	          	var zhBrand = $("#zhBrand").val();
	          	var brandId = $("#brandId").val();
	 			var systemBrandId = $("#systemBrandId").val();
	 			var remark = $("#remark").val();
	 			
	 			//alert(encodeURI(enBrand))
	 			
	 			if(enBrand!=''&&zhBrand!=''&&brandId!=''&&systemBrandId!=''){
	 			
		 			$.ajax({
				         type: "POST",
				         data: $("#alertProductStopReason").serialize()+'&brandId='+brandId+'&systemBrandId='+systemBrandId+'&remark='+remark+"&"+Math.random(),
				         url: "${path}/brand/saveBrandENAndZHName",
				         dataType:'json',
				         success: function (result) {
				        	 
				        	 if(result=='-2'){
				        		 alert("审核失败!");
				        	 }
				        	 if(result=='-1'){
				        		 alert("品牌同步失败!");
				        	 }
				        	 if(result=='0'){
				        		 tipMessage("审核成功!",function(){
				        			 location.reload();
									});
				        	 }
				        	 if(result=='1'){
				        		 alert("中文名存在!");
				        	 }
				        	 if(result=='2'){
				        		 alert("英文名存在!");
				        	 }
				        	 if(result=='3'||result=='4'||result=='5'){
				        		 alert("数据异常，请检查!");
				        	 }
				        	 
				         },
				         error:function(){
				         	alert("服务错误!");
				         }
		 			});
		          	
		        }else{
		        	alert("信息缺失，请重新再试!");
		        }
	 			
	 			
 			}
          
        </script>
</html>