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
						<p class="p1"><b style=" color: #FF0000;">*</b>企业名称：</p>
						<input class="i1" name ="name" value="${fn:escapeXml(data.name)}"  onblur="validateSingle(this, 'notnull')" />
							<span  class="input_warning"></span>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>企业简称：</p>
						<input class="i1" name ="nameJC" value="${fn:escapeXml(data.nameJC)}"  onblur="validateSingle(this, 'notnull')" />
							<span  class="input_warning"></span>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>上级企业编号：</p>
						<input class="i1" name ="nameJC" value="${fn:escapeXml(sjSupplier.supplierCode)}"  onblur="validateSingle(this, 'notnull')" />
							<span  class="input_warning"></span>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>企业编号：</p>
						<input class="i1" name ="nameJC" value="${fn:escapeXml(data.supplierCode)}"  onblur="validateSingle(this, 'notnull')" />
							<span  class="input_warning"></span>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>企业评级：</p>
						<input class="i1" name ="companyLevel" value="${fn:escapeXml(data.companyLevel)}"  onblur="validateSingle(this, 'notnull')" />
							<span  class="input_warning"></span>
					</li>

					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>企业类型：</p>
						<c:if test="${!empty data.type}">
						 <select  id="typeId" name ="type" disabled="disabled">
				              <option  value="0" <c:if test="${data.type eq 0}">selected="selected"</c:if>>非自营企业</option>
					          <option  value="1" <c:if test="${data.type eq 1}">selected="selected"</c:if>>子公司</option>
				              <option  value="2" <c:if test="${data.type eq 2}">selected="selected"</c:if>>连锁企业</option>
				              <option  value="3" <c:if test="${data.type eq 3}">selected="selected"</c:if>>连锁子公司</option>
				              <option  value="4" <c:if test="${data.type eq 4}">selected="selected"</c:if>>自营企业</option>
				              <option  value="5" <c:if test="${data.type eq 5}">selected="selected"</c:if>>项目产业</option>
						  </select>
						 </c:if>
					</li>
						<li class="blank20" style=""></li>
					<li style="">
						<p class="p1">
							<c:choose>
								<c:when test="${!fn:containsIgnoreCase(data.countryArea, data.countryAreaBackup)}">
									<span style=" color: #FF0000;">
										国家地区：
									</span>
								</c:when>
								<c:otherwise>
									国家地区：
								</c:otherwise>
							</c:choose>	
						</p>
						<span style="margin-top:10px; display: inline-block;">${data.countryAreaBackup}</span>
						<span  class="input_warning"></span>
					</li>
						<li class="blank20"></li>
				    <li>
						<p class="p1">
							<c:choose>
								<c:when test="${!fn:containsIgnoreCase(data.address, data.addressBackup)}">
									<span style=" color: #FF0000;">
										详细地址：
									</span>
								</c:when>
								<c:otherwise>
									详细地址：
								</c:otherwise>
							</c:choose>
						</p>
						<input class="i1" name="addressBackup" value="${fn:escapeXml(data.addressBackup)}" onblur="validateSingle(this, 'notnull')" >
							<span  class="input_warning"></span>
					</li>
						<li class="blank20"></li>
				    <li>
						<p class="p1">
							<c:choose>
								<c:when test="${!fn:containsIgnoreCase(data.manager, data.managerBackup)}">
									<span style=" color: #FF0000;">
										企业经理人：
									</span>
								</c:when>
								<c:otherwise>
									企业经理人：
								</c:otherwise>
							</c:choose>	
						</p>
						<input class="i1" name="manager" value="${fn:escapeXml(data.managerBackup)}" onblur="validateSingle(this, 'notnull')" >
							<span  class="input_warning"></span>
					</li>
						<li class="blank20"></li>
					<li>
						<p class="p1">
							<c:choose>
								<c:when test="${!fn:containsIgnoreCase(data.managerTel, data.managerTelBackup)}">
									<span style=" color: #FF0000;">
										经理人电话：
									</span>
								</c:when>
								<c:otherwise>
									经理人电话：
								</c:otherwise>
							</c:choose>	
						</p>
						<input class="i1" name ="managerTel" value="${fn:escapeXml(data.managerTelBackup)}" onblur="validateSingle(this, 'telphone')">
							<span  class="input_warning"></span>
					</li>
						<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;">*</b>邀请码：</p>
						<input class="i1" name ="userTj" value="${fn:substring(data.userTj,0,3)}****${fn:substring(data.userTj,7,11)}" onblur="validateSingle(this, 'telphone')">
							<span  class="input_warning"></span>
					</li>
						<li class="blank20"></li>
					<li>
						<p class="p1">
							<c:choose>
								<c:when test="${!fn:containsIgnoreCase(data.contact, data.contactBackup)}">
									<span style=" color: #FF0000;">
										联系人：
									</span>
								</c:when>
								<c:otherwise>
									联系人：
								</c:otherwise>
							</c:choose>	
						</p>
						<input class="i1" name ="contactor" value="${fn:escapeXml(data.contactBackup)}" onblur="validateSingle(this, 'notnull')">
							<span  class="input_warning"></span>
						
					</li>
						<li class="blank20"></li>
					<li>
						<p class="p1">
							<c:choose>
								<c:when test="${!fn:containsIgnoreCase(data.phone, data.phoneBackup)}">
									<span style=" color: #FF0000;">
										手机：
									</span>
								</c:when>
								<c:otherwise>
									手机：
								</c:otherwise>
							</c:choose>	
						</p>
						<input class="i1" name ="mobile" value="${fn:escapeXml(data.phoneBackup)}" onblur="validateSingle(this, 'telphone')">
							<span  class="input_warning"></span>
					</li>
						<li class="blank20"></li>
					<li>
						<p class="p1">
							<c:choose>
								<c:when test="${!fn:containsIgnoreCase(data.email, data.emailBackup)}">
									<span style=" color: #FF0000;">
										邮箱：
									</span>
								</c:when>
								<c:otherwise>
									邮箱：
								</c:otherwise>
							</c:choose>
						</p>
						<input class="i1" name="email" value="${fn:escapeXml(data.emailBackup)}" onblur="validateSingle(this, 'email')">
							<span  class="input_warning"></span>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">
							<c:choose>
								<c:when test="${!fn:containsIgnoreCase(data.post, data.postBackup)}">
									<span style=" color: #FF0000;">
										邮政编码：
									</span>
								</c:when>
								<c:otherwise>
									邮政编码：
								</c:otherwise>
							</c:choose>
						</p>
						<input class="i1" name ="post" value="${fn:escapeXml(data.postBackup)}">
							<span  class="input_warning"></span>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">
							<c:choose>
								<c:when test="${!fn:containsIgnoreCase(data.fax, data.faxBackup)}">
									<span style=" color: #FF0000;">
										电话/传真：
									</span>
								</c:when>
								<c:otherwise>
									电话/传真：
								</c:otherwise>
							</c:choose>
						</p>
						<input class="i1" name ="fax" value="${fn:escapeXml(data.faxBackup)}">
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">
							<c:choose>
								<c:when test="${!fn:containsIgnoreCase(data.kfTel, data.kfTelBackup)}">
									<span style=" color: #FF0000;">
										客服电话：
									</span>
								</c:when>
								<c:otherwise>
									客服电话：
								</c:otherwise>
							</c:choose>
						</p>
						<input class="i1" name ="kfTel" value="${fn:escapeXml(data.kfTelBackup)}">
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1"><b style=" color: #FF0000;"></b>企业信息：</p>
						<textarea class="i1" name="companyInfo" rows="10" cols="55">${fn:escapeXml(data.companyInfo)}</textarea>
					</li>
				     <li class="blank20"></li>
					<li>
						<p class="p1">
							<c:choose>
								<c:when test="${!fn:containsIgnoreCase(product.categories, product.categoriesBackup)}">
									<span style=" color: #FF0000;">
										商品类别：
									</span>
								</c:when>
								<c:otherwise>
									商品类别：
								</c:otherwise>
							</c:choose>
						</p>
						<input class="i1 validate[required,minSize[1],maxSize[100]]" id="mer-cgr"  name="categories"  readonly="readonly"  />
						<input id="mercgr" class="validate[required]" type="hidden" name="IndustryID" value="${product.categoriesBackup}" >
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">
							<c:choose>
								<c:when test="${!fn:containsIgnoreCase(product.brand, product.brandBackup)}">
									<span style=" color: #FF0000;">
										商品品牌：
									</span>
								</c:when>
								<c:otherwise>
									商品品牌：
								</c:otherwise>
							</c:choose>
						</p>
						<input class="i1 validate[required,minSize[1],maxSize[100]]" id="mer-brand" name="brand" value="${product.brandBackup}" />
					</li>

<%-- 					<c:if test="${data.supplyType == 51}"> --%>
<!-- 						<li class="blank20"></li> -->
<!-- 						<li> -->
<!-- 							<div style="margin-bottom: 10px;"> -->
<!-- 								<p class="p1"><b style=" color: #FF0000;">*</b>是否一件代发商户：</p> -->
<%-- 								<input type="radio" name="isOnekeySend_select" <c:if test="${data.isOnekeySend == null || data.isOnekeySend==1}">checked</c:if>  value="1"> 否 &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" <c:if test="${data.isOnekeySend != null && data.isOnekeySend==2}">checked</c:if> name="isOnekeySend_select" value="2"> 是 --%>
<!-- 							</div> -->

<!-- 							<span style="color:red">审核前请确认清楚，是否是一件代发商户</span> -->
<!-- 						</li> -->
<!-- 						<li class="blank20"></li> -->
<!-- 						<li> -->
<!-- 							<p class="p1"><b style=" color: #FF0000;">*</b>支付方式：</p> -->
<%-- <%-- 							<input type="radio" name="paymentType_select" <c:if test="${paymentType == null || paymentType==1}">checked</c:if>  value="1"> HK账户 &nbsp;&nbsp;&nbsp;&nbsp; --%>
<%-- 							<input type="radio" <c:if test="${paymentType != null && paymentType==3}">checked</c:if> name="paymentType_select" value="3"> 国内账户 --%>
<!-- 						</li> -->
<%-- 					</c:if> --%>

					<li class="blank20"></li>
	<%-- 				<li >
						<c:choose>
							<c:when test="${2==data.status || 1==data.status}">
								<!-- <label>供应商类型：</label> -->
								<p class="p1"><b style=" color: #FF0000;">*</b>供应商类型：</p>
								<div class="">
									<c:if test="${!empty allList}">
										<select disabled="disabled" id="xsname" class="validate[required,minSize[1],maxSize[100]]" name="saleType">
											<option value="">请选择</option>
											<c:forEach items="${allList}" var="sa">
														 <option <c:if test="${data.saleId == sa.saleId}">selected</c:if> 
														 value="${fn:escapeXml(sa.saleId)}">${fn:escapeXml(sa.saleName)}
														 </option>
														 <option <c:if test="${data.supplyType == sa.index}">selected</c:if> value="${fn:escapeXml(sa.index)}">${sa.name }</option>
											</c:forEach>
										</select>
									</c:if>
								</div>
							</c:when>
							<c:otherwise>
								<!-- <label>供应商类型：</label> -->
								<p class="p1"><b style=" color: #FF0000;">*</b>供应商类型：</p>
								<div class="">
											<c:if test="${data.supplyType == 51}">
												<select id="xsname" class="validate[required,minSize[1],maxSize[100]]" name="saleType">
													<option value="51">第三方国际发货（POP）</option>
												</select>
											</c:if>
											<c:if test="${data.supplyType != 51}">
												<c:if test="${!empty allList}">
													<select id="xsname" class="validate[required,minSize[1],maxSize[100]]" name="saleType">
														<option value="">请选择</option>
														<option value="1">一般贸易</option>
														<option value="11">海外直邮</option>
														<option value="12">保税区发货</option>
														<option value="21">韩国直邮</option>
														<option value="50">海外预售</option>
													</select>
												</c:if>
											</c:if>

								</div>
							</c:otherwise>
						</c:choose>
						
				   </li> --%>
					<li>
						<p class="p1">入驻区域类型：</p>
						<c:if test="${!empty data.companyRegion}">
							<select id="i1"  name="companyRegion" disabled="disabled">
								<option value="1" <c:if test="${fn:containsIgnoreCase(data.companyRegion, '1')}">selected="selected"</c:if> >自营</option>
								<option value="2" <c:if test="${fn:containsIgnoreCase(data.companyRegion, '2')}">selected="selected"</c:if> >非自营</option>
							</select>
						</c:if>
					</li>
					<li class="blank20"></li>
					<li>
						<p class="p1">入驻区域：</p>
						<c:if test="${!empty data.companyQy}">
							<select id="i1"  name="companyQy" disabled="disabled">
								<c:forEach items="${regionList}" var="reg">
									<option value="${reg.regionId}" <c:if test="${fn:containsIgnoreCase(data.companyQy,reg.regionId)}">selected="selected"</c:if> >${reg.regionText}</option>
								</c:forEach>
							</select>
						</c:if>
					</li>
					<li class="blank20"></li>
						<li>
					<p class="p1">资质文件：</p>
						<c:if test="${not empty data.companyLegitimacyUrl }">
						<a href="${path}/user/downloadFile?url=${data.companyLegitimacyUrl}" target="downloadFileIframe">资质文件下载</a>
						</c:if>
					    <c:if test="${empty data.companyLegitimacyUrl }">
						<span style="margin-top:10px; display: inline-block;">资质文件 &nbsp;&nbsp;&nbsp;<b style=" color: #FF0000;">未上传</b></span>
						</c:if>
					</li>				
				    <li class="blank20"></li>
					<li>
					<p class="p1">详情文件：</p>
					    <c:if test="${not empty data.companyDetailedUrl  }">
					        <a href="${path}/user/downloadFile?url=${data.companyDetailedUrl}" target="downloadFileIframe">详情文件下载</a>
				       </c:if>
				        <c:if test="${empty data.companyDetailedUrl }">
						<span style="margin-top:10px; display: inline-block;">详情文件 &nbsp;&nbsp;&nbsp;<b style=" color: #FF0000;">未上传</b></span>
						</c:if>
					</li>
				    <li class="blank20"></li>
					<li>
					<p class="p1">企业Logo：</p>
					    <c:if test="${not empty data.logoImgurlBackup  }">
					    	<img alt="企业Logo" src="http://image01.zhongjumall.com/${data.logoImgurlBackup}">
				       </c:if>
				        <c:if test="${empty data.logoImgurl }">
						<span style="margin-top:10px; display: inline-block;">企业Logo &nbsp;&nbsp;&nbsp;<b style=" color: #FF0000;">未上传</b></span>
						</c:if>
					</li>
					
					<div class="eck">
					   <c:if test="${1==data.status and !empty buttonsMap['普通商户审核-审核商户']}">
						<div class="eck1"><button type="text" onclick="checkSuccessBtn('${data.supplierId}',1,1)"  class="tong1">审核通过</button></div>
						<div class="eck2">
							 <form id="checkForm">
							 	<input type="hidden" id="supplyType" name="supplyType" class="text1" >
							 	<input type="hidden" id="isRecord" name="isRecord" class="text1">
						        <input type="hidden" id="id1" name="id1" class="text1">
						        <input type="hidden" id="status1" name="status1"   class="text1">
						        <input type="hidden" id="source1" name="source"  value="1" class="text1">
						        <c:if test="${data.status eq 1}">
									<textarea id="reason" rows="" maxlength="200"  name="reason" cols=""></textarea>
						        </c:if>
						        <c:if test="${data.status eq 2}">
									<textarea id="reason" rows="" maxlength="200" name="reason" cols="">${data.checkFailReason}</textarea>
						        </c:if>
								 <input type="hidden" id="isOnekeySend" name="isOnekeySend"  value="1">
								 <input type="hidden" id="paymentType" name="paymentType"  value="3">
								 <input type="hidden" id="categoriesId" name="categories"  value="3">
								 <input type="hidden" id="brandId" name="brand">
								 <input type="hidden" id="productId" name="productId" value="${product.productId }">
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
				    </div>
				    <input type="button"  class="fabu_btn" onclick="go()" value="返回" ></input>
	            	 
					<p class="blank30"></p>
					
			</div>
	
		</div>
		
	
		<!-- 右边 end -->
	</div>
	<p class="blank30"></p>
		   
	</div>
	<div class="clear"/>
	   <iframe name="downloadFileIframe" style="display:none"></iframe>
       </body>
       <script type="text/javascript">
       
       function go(){
   		 window.location.href = "../user/checklist?source=1";	
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
			   updateCheck(id,status,isRecord);
		   }, function(){
			   console.info("取消");
		   });
	   }

         function updateCheck(id,status,isRecord){
     		if(status==2&&$("#reason").val()==''){
     			alert("请填写审核失败原因!");
     			return;
     		}else{
				
				$("#id1").val(id);
				$("#status1").val(status);
				$("#isRecord").val(isRecord);
				
				var mercgr = $("#mercgr").val();			//商品类别
				var brand = $("#mer-brand").val();		//品牌
				
				$("#categoriesId").val(mercgr);
				$("brandId").val(brand);
				
				$("#supplyType").val(51);		//供应商类型默认给51

	   			var source = $("#source1").val();
				var fmdata = $("#checkForm").serialize();
 				$.ajax({
 			         type: "POST",
 			         data: fmdata+"&"+Math.random(),
 			         url: "../user/updateCheckModify",
 			         dataType:'json',
 			         success: function (result) {
 			        	 if(result == '1'){
 			        		 tipMessage("审核成功!",function(){
 			        	  		 window.location.href = "../user/checklist?source="+source;
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
    
</html>