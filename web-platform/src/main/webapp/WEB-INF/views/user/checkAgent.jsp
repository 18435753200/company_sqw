<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<% response.setHeader("cache-control","public"); %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-代理审核</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/kuc1.css"/>
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>
	
	<script type="text/javascript">
		function toGetList(source,jump){
			
			if(jump == 'jump'){
				resetfm();
			}
			
			$("#source").val(source);
			//$("#source").val(source);
			$("#SearchFrom").submit();
		}
		
		function updateSH(id,status,isRecord){
			
			if(status==2&&$("#reason").val()==''){
    			alert("请填写审核失败原因!");
    			return;
    		}else{
    			
				$("#id1").val(id);
				$("#status1").val(status);
				$("#isRecord").val(isRecord);
    			var source = $("#source1").val();
				
				var fmdata = $("#checkForm").serialize();
				$.ajax({
			         type: "POST",
			         data: fmdata+"&"+Math.random(),
			         url: "../user/updateCheck",
			         dataType:'json',
			         success: function (result) {
			        	 if(result == '1'){
			        		 tipMessage("操作成功!",function(){
			        	  		 window.location.href = "../user/checklist?source="+source;
			        		 });
			        	 }
			        	 if(result == '0'){
			        		alert("服务调用异常！");
			        	 }
			        	 if(result == '-1'){
 			        		alert("用户信息异常！");
 			        	 }
			        	 
			          }
			 	   });
    			
				
				
    		}
		
		}
		function updateCheck(id,status){
    		if(status==2&&$("#reason").val()==''){
    			alert("请填写审核失败原因!");
    			return;
    		}else{
    			
				$("#id1").val(id);
				$("#status1").val(status);
    			var source = $("#source1").val();
				
				var fmdata = $("#checkForm").serialize();
				$.ajax({
			         type: "POST",
			         data: fmdata+"&"+Math.random(),
			         url: "../user/updateCheck",
			         dataType:'json',
			         success: function (result) {
			        	 if(result == '1'){
			        		 tipMessage("操作成功!",function(){
			        	  		 window.location.href = "../user/checklist?source="+source;
			        		 });
			        	 }
			        	 if(result == '0'){
			        		alert("服务调用异常！");
			        	 }
			        	 if(result == '-1'){
 			        		alert("用户信息异常！");
 			        	 }
			        	 
			          }
			 	   });
    			
				
				
    		}
		}
		function valueToView(id2){
			$("#id2").val(id2);
			$("#viewForm").submit();
		}
		function valueToModifyView(id3){
			$("#id3").val(id3);
			$("#viewForm3").submit();
		}
		function valueToView1(id1){
			$("#id110").val(id1);
			$("#viewForm1").submit();
		}
		
		function valueToView2(id){
			$("#id222").val(id);
			$("#viewForm2").submit();
		}
		
		function  downCheckListExcel(){
	
			var data = $("#SearchFrom").serialize();
			window.open("${path}/user/downchecklistAgent?"+data+"&math="+Math.random(),"_blank");

		}
		
		$(function(){
			
			var message='${message}'; 
			if(message=='ok'){
				alert("操作成功!");
			}
			if(message=='1'){
				alert("缺少参数,操作失败!");
			}
			if(message=='2'){
				alert("上传失败,请重新选择上传文件!"); 		
			}
			if(message=='3'){
				alert("上传文件到图片服务器出错,请稍后再试!"); 	
			}
			if(message=='4'){
				alert("服务器忙,请稍后再试!"); 	
			}
			
		});
	</script>
</head>
<body>

 <%@include file="/WEB-INF/views/include/header.jsp"%>
	
	<div class="center">
		<!-- 左边 start -->
		 <%@include file="/WEB-INF/views/include/leftUser.jsp"%>
		<!-- 左边 end -->
		<div class="c2" style="margin-top:10px;">
			<div class="c21">
			<div class="title">
				<p>平台管理&nbsp;>&nbsp; </p>
				<p>审核管理&nbsp;&gt;&nbsp; </p>
				<p class="c1">代理审核</p>
			</div>
			</div>
			<div class="blank10"></div>

			<div class="c22">
			    <div class="c21">
						<ul class="top">

							<li <c:if test="${1==page.parameter.source }">class="list" </c:if> id="supplier"><a href="javascript:void(0)" onclick="toGetList(1,'jump')">代理商</a></li>
							<%-- <li <c:if test="${3==page.parameter.source }">class="list" </c:if>  id="dealer"><a href="javascript:void(0)" onclick="toGetList(3,'jump')">经销商</a></li>
						    <li <c:if test="${2==page.parameter.source }">class="list" </c:if>  id="retailer"><a href="javascript:void(0)" onclick="toGetList(2,'jump')">零售商</a></li> --%>

						</ul>
				 </div>
				<!-- <div class="xia"> -->
					<form id="SearchFrom"  action="${path}/user/checklistAgent" method="post" >
						<%-- <p class="p1">
						    <strong>代理名称：</strong><input type="text" id="productName" name="name" value="${page.parameter.name}" class="text1 dzf-txt">
	                        <input type="hidden" id="source" name="source"  value="${page.parameter.source}" class="text1 dzf-txt">
							&nbsp;
						    <strong>注册日期-开始：</strong><input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="createTime" name="createTime" value="<fmt:formatDate value="${page.parameter.createTime}" pattern="yyyy-MM-dd"/>" class="text1 dzf-txt">
	                         <strong>结束：</strong><input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="createTime" name="createTime" value="<fmt:formatDate value="${page.parameter.createTime}" pattern="yyyy-MM-dd"/>" class="text1 dzf-txt">
						</p> --%>
						<%-- <p class="p1">							
							<c:if test="${2 == param.source }">
								<strong>用户名称：</strong><input type="text" class="text1 dzf-txt"  name="userName" value="${page.parameter.userName }">
								&nbsp;
								<strong>商户来源：</strong>
								<select name="type"  style="height: 30px;width: 90px">
									<option value="-1" <c:if test="${!empty page.parameter.type&&page.parameter.type==-1}"> selected="selected" </c:if>>所有</option> 
									<option value="0" <c:if test="${!empty page.parameter.type&&page.parameter.type==0 }"> selected="selected"</c:if>>pad注册</option>
									<option value="1" <c:if test="${!empty page.parameter.type&&page.parameter.type==1 }"> selected="selected"</c:if>>PC注册</option>
								</select>
								&nbsp;
							</c:if>							
							<strong>审核日期-开始：</strong><input type="text" class="text1 dzf-txt" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="auditTime" value="<fmt:formatDate value="${page.parameter.auditTime}" pattern="yyyy-MM-dd"/>">
							<strong>结束：</strong><input type="text" class="text1 dzf-txt" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="auditTime" value="<fmt:formatDate value="${page.parameter.auditTime}" pattern="yyyy-MM-dd"/>">
							&nbsp;&nbsp;&nbsp;<strong>状态：</strong>
						    <select name="status" style="height: 30px;">
						        <option value="-1" <c:if test="${empty page.parameter.status}"> selected="selected" </c:if>>所有</option> 
						    	<option value="0"  <c:if test="${page.parameter.status==0}"> selected="selected" </c:if>>未审核</option>
						        <option value="1" <c:if test="${page.parameter.status==1}"> selected="selected" </c:if>>审核通过</option>
						    	<option value="2"  <c:if test="${page.parameter.status==2}"> selected="selected" </c:if>>审核未通过</option>
						    	<c:if test="${2==page.parameter.source }">
						    		<option value="3" <c:if test="${page.parameter.status==3}"> selected="selected" </c:if>>禁用</option>
						    	</c:if>
						    </select>
						    <c:if test="${1 == param.source }">
<!-- 						    	&nbsp;&nbsp;<strong>供应商类型：</strong> -->
<!-- 						    	<select name="supplyType" style="height: 30px;"> -->
						    		<option value="-1" <c:if test="${empty page.parameter.supplyType}"> selected="selected" </c:if>>所有</option>
						    		<option value="1" <c:if test="${ 1==page.parameter.supplyType}"> selected="selected" </c:if>>一般贸易</option>
									<option value="11" <c:if test="${ 11==page.parameter.supplyType}"> selected="selected" </c:if>>海外直邮</option>
									<option value="12" <c:if test="${ 12==page.parameter.supplyType}"> selected="selected" </c:if>>保税区发货</option>
									<option value="21" <c:if test="${ 21==page.parameter.supplyType}"> selected="selected" </c:if>>韩国直邮</option>
									<option value="50" <c:if test="${ 50==page.parameter.supplyType}"> selected="selected" </c:if>>海外预售</option>
									<option value="51" <c:if test="${ 51==page.parameter.supplyType}"> selected="selected" </c:if>>第三方国际发货(POP)</option>
<!-- 						    	</select> -->
						    </c:if>
						    <button type="button"  onclick="toGetList('${page.parameter.source}')" style="margin-left: 670px;">搜索${page.parameter.source}</button>
							<a href="#" id="czhi" onclick="resetfm()">重置</a>
						</p> --%>
					</form>
				<!-- </div> -->
			</div>
			<div class="blank10"></div>
		 
		 
			  <div class="btn">
					<a href="javascript:void(0)" onclick="downCheckListExcel()"> 
						<span class="button red"> 
						<%-- 	<span class="text">
								<img alt="" src="${path }/commons/images/down-icon.png" height="19px" width="19px"> 导出表格
							</span> --%>
						</span>
					</a>
			  </div>
		
		  
		  
		  
		 
		  
		  
			<div class="c24" id="c24">
				<table>
					<tr>
					<!--  <th class="ar1">登录名</th> -->
					 <th class="ar1">代理名称</th>
				     <c:if test="${2 == param.source }">
					  	 <th class="ar1">用户名称</th>
					  </c:if>

				  	 <th class="ar1">地址</th>
					  <th class="ar2">注册日期  </th>
					  <th class="ar2">审核日期</th>
					  <c:if test="${2==param.source }">
					  	<th class="ar3">备案状态</th>
					  </c:if>
					  <th class="ar3">状态</th>
					  <th class="ar4">操作</th>
					</tr>
					<c:if test="${empty page.result}">
						<tr>
							<td  <c:if test="${2==param.source}">colspan="8"</c:if><c:if test="${1==param.source }">colspan="7"</c:if><c:if test="${3==param.source ||empty param.source}">colspan="6"</c:if>>
								<center><img src="${path }/commons/images/no.png" /></center>
							</td>
						</tr>
					</c:if>
				
				    <c:set var="disableUpdate" value="${!empty  buttonsMap['商户审核-禁用商户']}">
				    </c:set>
				    
				    <c:set var="ableUpdate" value="${!empty buttonsMap['商户审核-启用商户']}">
				    </c:set>
				    <c:set var="ableBN" value="${!empty buttonsMap['商户审核-启动备案'] }">
				    </c:set>
				   <c:set var="ableCK" value="${!empty buttonsMap['商户审核-查看审核']}">
				    </c:set>
						<c:forEach items="${page.result }" var="data">
							<tr>
								<%-- <td>${fn:escapeXml(data.userLoginName)}</td> --%>
							 	<td>
								 	<p title="<c:if test="${1==page.parameter.source }">${fn:escapeXml(data.name)}</c:if>
								 	<c:if test="${2==page.parameter.source }">${fn:escapeXml(data.name)}</c:if>
								 	<c:if test="${3==page.parameter.source }">${fn:escapeXml(data.companyName)}</c:if>">
								 	
								 	<c:if test="${1==page.parameter.source }">${fn:escapeXml(data.name)}</c:if>
								 	<c:if test="${2==page.parameter.source }">${fn:escapeXml(data.name)}</c:if>
								 	<c:if test="${3==page.parameter.source }">${fn:escapeXml(data.companyName)}</c:if>
								     </p>
								</td>
								<c:if test="${2 == param.source }">
									<td>
							  	 		<p title="${data.userName }" >
							  	 			${data.userName }
							  	 		</p>
						 			 </td>
					 			 </c:if>
						<td>
									<p class="ti1" title="${fn:escapeXml(data.address)}"> ${fn:escapeXml(data.address)}</p>
								</td>
			
<%-- 								<c:if test="${1 == param.source }"> --%>
<!-- 									<td> -->
<%-- 										<c:if test="${1 == data.supplyType }"> --%>
<!-- 											<p class="ti1" title="一般贸易">一般贸易</p> -->
<%-- 										</c:if> --%>
<%-- 										<c:if test="${11 == data.supplyType }"> --%>
<!-- 											<p class="ti1" title="海外直邮">海外直邮</p> -->
<%-- 										</c:if> --%>
<%-- 										<c:if test="${12 == data.supplyType }"> --%>
<!-- 											<p class="ti1" title="保税区发货">保税区发货</p> -->
<%-- 										</c:if> --%>
<%-- 										<c:if test="${21 == data.supplyType }"> --%>
<!-- 											<p class="ti1" title="韩国直邮">韩国直邮</p> -->
<%-- 										</c:if> --%>
<%-- 										<c:if test="${50 == data.supplyType }"> --%>
<!-- 											<p class="ti1" title="海外预售">海外预售</p> -->
<%-- 										</c:if> --%>
<%-- 										<c:if test="${51 == data.supplyType }"> --%>
<!-- 											<p class="ti1" title="第三方国际发货(POP)">第三方国际发货(POP)</p> -->
<%-- 										</c:if> --%>
<!-- 									</td> -->
<%-- 								</c:if> --%>
								<%-- <td>
									<c:forEach items="${regionList}" var="reg">
										<c:if test="${data.companyQy==reg.regionId}">
											<p class="ti1" title="${reg.regionText}">${reg.regionText}</p>
										</c:if>
									</c:forEach> --%>
									<%-- <c:if test="${fn:containsIgnoreCase(data.companyQy, '1')}">
										<p class="ti1" title="自营">自营</p>
									</c:if>
									<c:if test="${fn:containsIgnoreCase(data.companyQy, '2')}">
										<p class="ti1" title="孵化">孵化</p>
									</c:if>
									<c:if test="${fn:containsIgnoreCase(data.companyQy, '3')}">
										<p class="ti1" title="高新">高新</p>
									</c:if>
									<c:if test="${fn:containsIgnoreCase(data.companyQy, '4')}">
										<p class="ti1" title="高新">普通</p>
									</c:if> --%>
									<%-- <c:if test="${empty data.companyRegion}"><p class="ti2" title="-" >-</p></c:if> --%>
								<!-- </td> -->
								<td>
									<c:if test="${!empty data.createTime}"><p class="ti2" title="<fmt:formatDate value="${data.createTime}" pattern="yyyy-MM-dd" />" > <fmt:formatDate value="${data.createTime}" pattern="yyyy-MM-dd"/></p></c:if>
									<c:if test="${empty data.createTime}"><p class="ti2" title="-" >-</p></c:if>
									
								</td>
								<td>
								<fmt:formatDate value="${data.auditTime}" pattern="yyyy-MM-dd" var="time"/>
								<c:if test="${fn:containsIgnoreCase(time, '1970-01-01')}" var="rs"></c:if>
								
								
								
								<c:if test="${!rs}"><p class="ti2" title="<fmt:formatDate value="${data.auditTime}" pattern="yyyy-MM-dd"/>"> <fmt:formatDate value="${data.auditTime}" pattern="yyyy-MM-dd"/></p></c:if>
								<c:if test="${rs}"><p class="ti2" title="-">-</c:if>
							
								</td>
								<c:if test="${2==param.source }">
								<td>
									<c:choose>
										<c:when test="${1==data.isRecord }">
											已备案
										</c:when>
										<c:otherwise>
											未备案
										</c:otherwise>
									</c:choose>
								</td>
								</c:if>
								<td>
								<c:if test="${3==data.status }">
							 		禁用
							 	</c:if>
								<c:if test="${1==data.status && (data.activeStatus eq 1 || data.activeStatus eq 0)}">
							 		审核通过
							 	</c:if>
							 	<c:if test="${2==data.status }">
							 		审核未通过
							 	</c:if>
							 	<c:if test="${(2!=data.status&&1!=data.status&&3!=data.status) || (data.status eq 1 && data.activeStatus eq -1)}">
							 		未审核
							 	</c:if>
<%-- 						 		<c:if test="${data.modifyStatus eq 1}"> --%>
<%-- 						 			</br><span class="ckan" style="cursor: pointer;color: #ff4800;" onclick="valueToModifyView('${data.supplierId}')">(有修改并查看)</span> --%>
<%-- 					 			</c:if> --%>
								</td>
								<td class="t4">
							    <form id="checkForm">
							    	<input type="hidden" id="isRecord" name="isRecord" class="text1">
							        <input type="hidden" id="id1" name="id1" class="text1">
							        <input type="hidden" id="status1" name="status1"   class="text1">
							        <input type="hidden" id="source1" name="source"  value="${fn:escapeXml(page.parameter.source)}" class="text1">
							   </form>
							   <form id="viewForm" action="${path}/user/viewInfonewLook" method="post">
							        <input type="hidden" id="id2" name="id2" class="text1">
							        <input type="hidden" name="source"  value="${fn:escapeXml(page.parameter.source)}" >
							   </form>
							   <form id="viewForm1" action="${path}/user/viewInfo1" method="post">
							        <input type="hidden" id="id110" name="id1" class="text1">
							        <input type="hidden" name="source"  value="${fn:escapeXml(page.parameter.source)}" >
							   </form>
							    <form id="viewForm2" action="${path}/user/viewInfo2" method="post">
							        <input type="hidden" id="id222" name="id1" class="text1">
							        <input type="hidden" name="source"  value="${fn:escapeXml(page.parameter.source)}" >
							   </form>
							   <form id="viewForm3" action="${path}/user/viewInfo8" method="post">
							        <input type="hidden" id="id3" name="id3" class="text1">
							        <input type="hidden" name="source"  value="${fn:escapeXml(page.parameter.source)}" >
							   </form>
							  	<c:choose>
							  		<c:when test="${2==param.source }">
							  			<c:if test="${2==page.parameter.source && 1!=data.isRecord }">
							  				<c:if test="${ableBN }">
										 	  <span id="BN" class="ckan" onclick="valueToView1('${data.retailerId}')">备案</span>
							  				</c:if>
										 </c:if>
										 
										 <c:if test="${1==data.isRecord }">
										 		<c:if test="${ableBN || ableCK}">
										 			<c:if test="${ 1!=data.status }">
												 		<span id="BNXG" class="ckan" onclick="valueToView2('${data.retailerId}')">已备案修改</span><br>
										 			</c:if>
										 		</c:if>
										 	<c:if test="${1==data.status }">
											 	<c:if test="${disableUpdate}">
											 	  <span class="cjian" onclick="updateSH('${data.retailerId}','3',0)">禁用</span>
											 	</c:if>
											 	<%--
											 	 --%>
											 	<c:if test="${ableCK }">
											 	  <span class="ckan" onclick="valueToView('${data.retailerId}')">查看</span>
											 	</c:if>  
										 	</c:if>
										 	
										 	
									 		<c:if test="${1!=data.status }">
										 		<c:if test="${2==page.parameter.source }">
										 			<c:if test="${3==data.status and ableUpdate}">
											 			<span hidden="hidden" class="cjian" onclick="updateCheck('${data.retailerId}','1')">启用</span>
											 		</c:if>
											 		<%--
											 		 --%>
											 		<c:if test="${ableCK }">
												        <span class="ckan" onclick="valueToView('${data.retailerId}')">查看</span>
											 		</c:if>
											 	</c:if>
										 	</c:if>
										 	 
										 </c:if>
										 
							  		</c:when>
							  		<c:otherwise>
									  			<c:if test="${1==data.status }">
										    <c:if test="${1==page.parameter.source && data.modifyStatus ne 1}">
										 		<span class="ckan" onclick="valueToView('${data.supplierId}')">查看</span>
										 	</c:if>
										 	<c:if test="${data.modifyStatus eq 1}">
						 						<span class="ckan" style="cursor: pointer;color: #ff4800;" onclick="valueToModifyView('${data.supplierId}')">(有修改并查看)</span>
					 						</c:if>
										 	<c:if test="${2==page.parameter.source }">
										 	
										 	<c:if test="${disableUpdate}">
										 	  <span class="cjian" onclick="updateCheck('${data.retailerId}','3')">禁用</span>
										 	</c:if>  
										 	  
										 	  <span class="ckan" onclick="valueToView('${data.retailerId}')">查看</span>
										 	</c:if>
										 	<c:if test="${3==page.parameter.source }">
										 	   
										 		<span class="ckan" onclick="valueToView('${data.dealerId}')">查看</span>
										 	</c:if>
									       
									 	</c:if>
									 	<c:if test="${1!=data.status }">
									 	   <c:if test="${1==page.parameter.source && data.modifyStatus ne 1}">
										    <%--  <span class="cjian" onclick="updateCheck('${data.supplierId}',1)">通过</span>
											 <span class="xiug" onclick="updateCheck('${data.supplierId}',2)">不通过</span> --%>
											 <span class="ckan" onclick="valueToView('${data.supplierId}')">查看</span>
										 	</c:if>
										 	<c:if test="${data.modifyStatus eq 1}">
						 						<span class="ckan" style="cursor: pointer;color: #ff4800;" onclick="valueToModifyView('${data.supplierId}')">(有修改并查看)</span>
					 						</c:if>
									 		<c:if test="${2==page.parameter.source }">
									 			<c:if test="${3==data.status and ableUpdate}">
										 			<span class="cjian" onclick="updateCheck('${data.retailerId}','1')">启用</span>
										 		</c:if>
										        <span class="ckan" onclick="valueToView('${data.retailerId}')">查看</span>
										 	</c:if>
										 	<c:if test="${3==page.parameter.source }">
											 <span class="ckan" onclick="valueToView('${data.dealerId}')">查看</span>
										 	</c:if>
										 	
									 	</c:if>
							  		</c:otherwise>
							  	</c:choose>
							    </td>
							</tr>
						</c:forEach>
				</table>
				<c:if test="${!empty page.result}">
					<%@include file="/WEB-INF/views/include/page.jsp"%>
				</c:if>
			</div>
		  
		</div>
		<div class="blank20"></div>
	</div>

	 <div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>