<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
	<head>
	  <meta charset="UTF-8">
	  <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	  <title>UNICORN-商户信息</title>
	  <%@include file="/WEB-INF/views/include/base.jsp"%>
	   <link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
     <script type="text/javascript" src="${path}/commons/js/user/validate.js"></script>
	  <script type="text/javascript" src="${path}/commons/js/user/jiben.js"></script>
	  <script type="text/javascript" src="${path}/commons/js/user/scode.js"></script>
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
	</style>
	<script type="text/javascript">
		$(function(){
			var status='${data.status}'; 
			if(status == 1 || status == 2){
				$("#noPayHqqId").attr("disabled","disabled");
				$("#fanliPriceId").attr("disabled","disabled");
			}
		})
	</script>
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
				<ul class="ul_vertical">
					
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>家庭号名称：</p>
						<input  name ="name" id="name" value="${fn:escapeXml(data.name)}"  onblur="validateSingle(this, 'notnull')" />
							<span  class="input_warning"></span>
					</li>

					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>家庭号编号：</p>
						<input readonly="readonly" name ="nameJC" id="supplierCode" value="${fn:escapeXml(data.supplierCode)}"   />
							<span  class="input_warning"></span>
					</li>

					
					
						<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>客服电话：</p>
						<input  name ="kfTel" id="managerTel" value="${fn:escapeXml(data.kfTel)}" onblur="validateSingle(this, 'telphone')">
							<span  class="input_warning"></span>
					</li>
						<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>真实姓名：</p>
						<input  name ="contactor" id="contact" value="${fn:escapeXml(data.contact)}" onblur="validateSingle(this, 'notnull')">
							<span  class="input_warning"></span>
						
					</li>
						<li class="blank20"></li>
						<p class="p1"><b style=" color: #FF0000;">*</b>邮箱：</p>
						<input  name="email" id="email" value="${fn:escapeXml(data.email)}" onblur="validateSingle(this, 'email')">
							<span  class="input_warning"></span>
					</li>
				

					<li class="blank20"></li>
					<li class="blank20"></li>
					<li>
						<p class="p1">入驻区域：</p>
						<select id="companyQyId"  name="companyQy" <c:if test="${!empty data.companyQy}">disabled="disabled"</c:if> >
							<c:forEach items="${regionList}" var="reg">
								<option value="${reg.regionId}" <c:if test="${fn:containsIgnoreCase(data.companyQy,reg.regionId)}">selected="selected"</c:if> >${reg.regionText}</option>
							</c:forEach>
							<%-- <option value="1" <c:if test="${fn:containsIgnoreCase(data.companyQy, '1')}">selected="selected"</c:if> >自营</option>
							<option value="2" <c:if test="${fn:containsIgnoreCase(data.companyQy, '2')}">selected="selected"</c:if> >孵化</option>
							<option value="3" <c:if test="${fn:containsIgnoreCase(data.companyQy, '3')}">selected="selected"</c:if> >高新</option>
							<option value="4" <c:if test="${fn:containsIgnoreCase(data.companyQy, '4')}">selected="selected"</c:if> >普通</option> --%>
						</select>
							<input id="oldCompanyQyId" type="hidden" value="${data.companyQy}"/>

					</li>
					
					
					<div class="eck">
					<c:if test="${(data.status eq 0 && data.activeStatus eq -1)||(data.status eq 1 && data.activeStatus eq -1)}">
						<div class="eck1"><button type="text" onclick="checkSuccessBtn('${data.supplierId}',1,1)"  class="tong1">审核通过</button></div>
						<div class="eck2">
							 <form id="checkForm">
							 	<input type="hidden" id="supplyType" name="supplyType" class="text1" >
							 	<input type="hidden" id="isRecord" name="isRecord" class="text1">
						        <input type="hidden" id="id1" name="id1" class="text1">
						        <input type="hidden" id="status1" name="status1"   class="text1">
						        <input type="hidden" id="source1" name="source"  value="1" class="text1">
						        <c:if test="${data.status eq 2 }">
									<textarea id="reason" rows="" maxlength="200"  name="reason" cols="">${data.checkFailReason}</textarea>
						        </c:if>
						        <c:if test="${data.status ne 2 }">
									<textarea id="reason" rows="" maxlength="200" name="reason" cols=""></textarea>
						        </c:if>
								 <input type="hidden" id="isOnekeySend" name="isOnekeySend"  value="1">
								 <input type="hidden" id="paymentType" name="paymentType"  value="3">
								 
								 <input type="hidden" id="companyRegion" name="companyRegion"  class="text1">
								 <input type="hidden" id="companyQy" name="companyQy"  class="text1">
								 <input type="hidden" id="type" name="type"  class="text1">
							</form>
							<!-- <span class="bfu"><input type="checkbox">公司名称与实际不符</span> -->
						</div>
						<div class="eck3"><button type="text" onclick="checkFailureBtn('${data.supplierId}',2,0)"  class="tong2">审核不通过</button></div>
						 </c:if>
						 <c:if test="${2==data.status}">
						    <span style="color:red;">审核不通过原因</span>
						    <div style="color:red;"></div>
						    </br>
							<div class="eck2">
									<textarea id="reason" rows="" disabled="disabled"  name="reason" cols="">${data.checkFailReason}</textarea>
							</div>
						</c:if>
						 <c:if test="${1==data.status && data.activeStatus ne -1}">
						    <span style="color:red;">审核通过原因</span>
						    <div style="color:red;"></div>
						    </br>
							<div class="eck2">
									<textarea id="reason" rows="" disabled="disabled"  name="reason" cols="">${data.checkFailReason}</textarea>
							</div>
						</c:if>
						 <form id="createForm">
							 	<input type="hidden" id="supplyType2" name="supplyType" class="text1" >
							 	<input type="hidden" id="isRecord2" name="isRecord" class="text1">
						        <input type="hidden" id="id2" name="id1" class="text1">
						        <input type="hidden" id="status2" name="status1"   class="text1">
						        <input type="hidden" id="source2" name="source"  value="1" class="text1">
								 <input type="hidden" id="isOnekeySend2" name="isOnekeySend"  value="1">
								 <input type="hidden" id="paymentType2" name="paymentType"  value="3">
								 
								 <input type="hidden" id="companyRegion2" name="companyRegion"  class="text1">
								 <input type="hidden" id="companyQy2" name="companyQy"  class="text1">
								 <input type="hidden" id="type2" name="type"  class="text1">
								 <input type="hidden" id="noPayHqqId2" name="noPayHqq"  class="text1">
								 <input type="hidden" id="fanliPriceId2" name="fanliPrice"  class="text1">
								 <input type="hidden" id="sjSupplierCodeId2" name="sjSupplierCode"  class="text1">
							</form>
	            	 
				    </div>
				    <input type="button"  class="fabu_btn" onclick="saveupdate()" value="保存修改" ></input>
				    <input type="button"  class="fabu_btn" onclick="go()" value="返回" ></input>
					<p class="blank30"></p>
					
			</div>
	
		</div>
		
	
		<!-- 右边 end -->
	</div>
	<p class="blank30"></p>
		   
	</div>
	<!-- 触发 table 1 -->
	<div class="alert_c" id="skuDiv" style="display:none;">
	  <div class="bg"></div>
		<div class="wrap">
			<div class="pic"></div>
			<div class="box1">
				<div id="boxtitle">
					<h2>选择上级企业编号</h2>
				</div>
				<div class="xia">
					<form id="SearchFrom"  action="" method="post" >
						<p class="p1" style="text-align: center;">
							<strong>企业代码：</strong><input type="text" id="serSupplierCodeId"/>
							<strong>企业名称：</strong><input type="text" id="serSupplierNameId"/>
						</p>
					</form>
				</div>
				<div id="suppliernametext" style="display: block;">
					<button type="button" onclick="hideShow();">退出</button>
					<button href="#" id="czhi" onclick="resetfm()">重置</button>
					<button type="button" onclick="GoPage();">查询</button>
				</div>
			</div>
			<div class="box2" id="skubox">
			
			</div>
			<c:if test="${empty page.result }">
				<div class="box3">
					<button type="button" class="bt1" id="supplierclick" onclick="loadSku()">确定</button>
				</div>
			</c:if>
		</div>
	</div>
	<div class="clear"/>
	   <iframe name="downloadFileIframe" style="display:none"></iframe>
       </body>
       <script type="text/javascript">
       function saveupdate(){
    	   var  CONTEXTPATH  = $("#conPath").val();
    	   var supplierId='${fn:escapeXml(data.supplierId)}';
    	   var name=$('#name').val();
    	   var supplierCode=$('#supplierCode').val();
    	   var phone=$('#phone').val();
    	   var contact=$('#contact').val();
    	   var managerTel=$('#managerTel').val();
    	   var email=$('#email').val();
    	   var pro_array  = new Array();
    	   if(name!=""){
    			pro_array.push("name="+name);
    		}
    	   if(supplierId!=""){
    			pro_array.push("supplierId="+supplierId);
    		}
    	   if(supplierCode!=""){
    			pro_array.push("supplierCode="+supplierCode);
    		}
    	   if(phone!=""){
    			pro_array.push("phone="+phone);
    		}
    	   if(contact!=""){
    			pro_array.push("contact="+contact);
    		}
    	   if(managerTel!=""){
    			pro_array.push("managerTel="+managerTel);
    		}
    	   if(email!=""){
    			pro_array.push("email="+email);
    		}
    	   $.ajax({
    			type : "post", 
    			url : CONTEXTPATH+"/user/saveupdate", 
    			data:pro_array.join("&"),
    			dataType:"json",
    			success : function(msg) { 
    				alert("修改成功!");
    			},
    			error: function(jqXHR, textStatus, errorThrown){
    				alert("服务器异常!");
    			}
    		}); 
       }
 
       function go(){
   		 window.location.href = "../user/checklist1?source=1";	
   		}
         $(function(){
        	
       		var category='${category}';
       		var ind_a='';
       		if(category.length>0){
       			ind_a=${category};	
       		}
      		   // 是否在数组内ind_a
      			function in_array(needle, haystack) {
      				if(typeof needle == 'string' || typeof needle == 'number') {
      					for(var i in haystack) {
      						if(haystack[i] == needle) {
      								return true;
      						}
      					}
      				}
      				return false;
      			};
        	var indStr='';
			if($('#mercgr').val().length>0){
				 ind_flag_arr=$('#mercgr').val().split(",");
			}
			for(var i in ind_flag_arr){
				indStr+=','+ind_a[ind_flag_arr[i]];
			}
			indStr=indStr.substring(1)?indStr.substring(1):'';
			$('#mer-cgr').val(indStr);
		    //字数限制
        	$("#reason").keyup(function(){
	        	   var len = $(this).val().length;
	        	   if(len > 1000){
	        	    $(this).val($(this).val().substring(0,500));
	        	    alert("字数不能超过500字!");
	
	        	   };
        	  });

         });

		function checkSuccessBtn(id,status,isRecord){
			/*if(confirm("确定审核通过吗？")){
				updateCheck(id,status,isRecord);
			}*/
			$.dialog.confirm('您确定审核通过吗？', function(){
				updateCheck(id,status,isRecord);
			}, function(){
				console.info("取消");
			});
		}

	   function checkFailureBtn(id,status,isRecord){
		   $.dialog.confirm('您确定审核不通过吗？', function(){
			   updateCheck1(id,status,isRecord);
		   }, function(){
			   console.info("取消");
		   });
	   }

         function updateCheck(id,status,isRecord){
     		if(status==2&&$("#reason").val()==''){
     			alert("请填写审核失败原因!");
     			return;
     		}else{
//      			var info = $('#xsname option:selected').val();
//      			if(null==info|| info=='' || undefined ==info){
//      				alert("请选择供应商类型!");
//          			return;
//      			}
					var reason=$("#reason").val();
					if(reason==''){
						reason="审核通过";
					}
 					var pid_array = new Array();
 					pid_array.push('id='+id);
 					pid_array.push('reason='+reason);
 				$.ajax({
 	 					 type: "POST",
 				         data: pid_array.join("&"),
 				         url: "../user/updateCheck1",
 				         dataType:'json',
 			         success: function (result) {
 			        	 if(result == '1'){
 			        		 tipMessage("审核成功!",function(){
 			        	  		 window.location.href = "../user/checklist1?source=1";
 			        		 });
 			        	 }
 			        	 if(result == '0'){
 			        		alert("服务调用异常！");
 			        	 }
 			        	 if(result == '-1'){
  			        		alert("用户信息异常！");
  			        	 };
 			        	 
 			          }
		 	    });
   			};
		}
         function updateCheck1(id,status,isRecord){
     		if(status==2&&$("#reason").val()==''){
     			alert("请填写审核失败原因!");
     			return;
     		}else{
//      			var info = $('#xsname option:selected').val();
//      			if(null==info|| info=='' || undefined ==info){
//      				alert("请选择供应商类型!");
//          			return;
//      	}
				var reason=$("#reason").val();
				var pid_array = new Array();
				pid_array.push('id='+id);
				pid_array.push('reason='+reason);
 				$.ajax({
 					type: "POST",
			         data: pid_array.join("&"),
			         url: "../user/updateCheck2",
			         dataType:'json',
 			         success: function (result) {
 			        	 if(result == '1'){
 			        		 tipMessage("审核成功!",function(){
 			        	  		 window.location.href = "../user/checklist1?source=1";
 			        		 });
 			        	 }
 			        	 if(result == '0'){
 			        		alert("服务调用异常！");
 			        	 }
 			        	 if(result == '-1'){
  			        		alert("用户信息异常！");
  			        	 };
 			        	 
 			          }
		 	    });
   			};
		}
    </script>
  	<script type="text/javascript">
  		function modifyInfo(supplierId){
  			if($("#companyLevelModifyId").val() == '修改'){
  				$("#companyLevelModifyId").val('保存');
  				$("#companyLevelId").removeAttr('disabled');
  			}else{
	  			var levelValue = $("#companyLevelId").val().trim();
	  			var oldLevelValue = $("#companyLevelOldId").val().trim();
	  			if(levelValue == null || levelValue == ''){
	  				alert("内容不能为空!");
	  				return;
	  			}else if(levelValue == oldLevelValue){
	  				alert("内容没有修改，请修改后再保存!");
	  				return;
	  			}
	  			
	  			$.ajax({
			         type: "GET",
			         url: "../user/modifyInfo?supplierId="+supplierId+"&newLevelVal="+levelValue,
			         dataType:'json',
			         success: function (result) {
			        	 if(result == 1){
			        		 alert("修改成功");
			        		 window.location.href = "../user/checklist1?source="+1;
			        	 }else{
			        		 alert("修改失败");
			        	 }
			          }
		 	    });
  			}
  			
  		}
  		function modifyCompanyQy(supplierId){
  			if($("#companyQyModifyId").val() ==  '修改'){
  				$("#companyQyModifyId").val('保存');
  				$("#companyQyId").removeAttr('disabled');
  			}else{
	  			var oldCompanyQyId = $("#oldCompanyQyId").val().trim();
	  			var newCompanyQy = $("#companyQyId").val().trim();
	  			if(newCompanyQy == null || newCompanyQy == ''){
	  				alert("内容不能为空!");
	  				return;
	  			}else if(oldCompanyQyId == newCompanyQy){
	  				alert("内容没有修改，请修改后再保存!");
	  				return;
	  			}
	  			
	  			$.ajax({
			         type: "GET",
			         url: "../user/modifyInfo?supplierId="+supplierId+"&newCompanyQy="+newCompanyQy,
			         dataType:'json',
			         success: function (result) {
			        	 if(result == 1){
			        		 alert("修改成功");
			        		 window.location.href = "../user/checklist?source="+1;
			        	 }else{
			        		 alert("修改失败");
			        	 }
			          }
		 	    });
  			}
  		}
  		
  		function creatAccountBtn(id,status,isRecord){
			$.dialog.confirm('您确定创建账号吗？', function(){
				creatAccount(id,status,isRecord);
			}, function(){
				console.info("取消");
			});
		}
  		//创建账号
  		function creatAccount(id,status,isRecord){
  			var noPayHqqVal = $("#noPayHqqId").val().trim();
  			var fanliPriceVal = $("#fanliPriceId").val().trim();
  			if((noPayHqqVal == null || noPayHqqVal == '') && (fanliPriceVal == null || fanliPriceVal == '')){
  				alert('创建账号时,未付红旗券和激活购买商品金额 不能同时为空!');
  				return false;
  			}
			var companyQy = $("#companyQyId").val();
			var companyRegion = $("#companyRegionId").val();
			var type = $("#typeId").val();
			var sjSupplierCodeVal = $("#sjSupplierCodeId").val();
			if(sjSupplierCodeVal == null || sjSupplierCodeVal == ''){
				if(type == 0){
					sjSupplierCodeVal = 'SQ69';		//众聚商城非自营企业
				}else if(type == 1){
					sjSupplierCodeVal = 'SQ37';		//众聚商城子公司
				}else if(type == 2){
					sjSupplierCodeVal = 'SQ89';		//众聚商城连锁企业
				}else if(type == 3){
					sjSupplierCodeVal = 'SQ79';		//众聚商城连锁子公司
				}else if(type == 4){
					sjSupplierCodeVal = 'SQ59';		//众聚商城自营企业
				}else if(type == 5){
					sjSupplierCodeVal = 'SQ99';		//众聚商城项目产业
				}
			}
			
			$("#companyQy2").val(companyQy);
			$("#companyRegion2").val(companyRegion);
			$("#type2").val(type);
			$("#sjSupplierCodeId2").val(sjSupplierCodeVal);

			var onekeySend_select = $('input[name="isOnekeySend_select"]:checked ').val();
			var paymentType_select = $('input[name="paymentType_select"]:checked ').val();

   			$("#supplyType2").val(51);		//供应商类型默认给51
			$("#id2").val(id);
			$("#status2").val(status);
			$("#isRecord2").val(isRecord);
			$("#isOnekeySend2").val(onekeySend_select);
			$("#paymentType2").val(paymentType_select);
			$("#noPayHqqId2").val(noPayHqqVal);
			$("#fanliPriceId2").val(fanliPriceVal);

   			var source = $("#source1").val();
			var fmdata = $("#createForm").serialize();
				$.ajax({
			         type: "POST",
			         data: fmdata+"&"+Math.random(),
			         url: "../user/createAccount",
			         dataType:'json',
			         success: function (result) {
			        	 if(result == '1'){
			        		 tipMessage("创建账号成功!",function(){
			        	  		 window.location.href = "../user/viewInfo?source="+1+"&id2="+id;
			        		 });
			        	 }
			        	 if(result == '0'){
			        		alert("服务调用异常！");
			        	 }
			        	 if(result == '-1'){
 			        		alert("用户信息异常！");
 			        	 };
 			        	if(result == '-2'){
 			        		alert("该企业类型下企业个人账号范围已超！");
 			        	 };
			          }
	 	    });
  		}
  	</script>
  	<script type="text/javascript">
  		function noHqq(){
  			var noHqqVal = $("#noPayHqqId").val().trim();
  			var reg = '^[1-9]\\d*$';
  			if(!noHqqVal.match(reg)){
  				alert('不能为空或小数，请重新输入!');
  				$("#noPayHqqId").val('');
  				$("#fanliPriceId").removeAttr('disabled');
  				return false;
  			}else{
	  			$("#fanliPriceId").attr("disabled","disabled");
  			}
  			/* if((parseFloat(newFenHongVal) + parseFloat(num))>60000000){
  				alert('此用户分红额度已达上限!');
  				$("#fenHongId").val('');
  				return false;
  			} */
  		}
  		function fanliPrice(){
  			var fanliPriceVal = $("#fanliPriceId").val().trim();
  			var reg = '^[1-9]\\d*$';
  			if(!fanliPriceVal.match(reg)){
  				alert('不能为空或小数，请重新输入!');
  				$("#fanliPriceId").val('');
  				$("#noPayHqqId").removeAttr('disabled');
  				return false;
  			}else{
	  			$("#noPayHqqId").attr("disabled","disabled");
  			}
  		}
  	</script>
</html>