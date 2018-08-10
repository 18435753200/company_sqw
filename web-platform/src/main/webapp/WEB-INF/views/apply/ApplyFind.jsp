<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>出库申请(通知)单</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/manual.css">
    <script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
</head>
	<script type="text/javascript" src="<%=path%>/commons/js/uploadify/jquery.uploadify.js"></script>
	<script type="text/javascript" src="${path}/commons/js/map.js"></script>
	<link rel="stylesheet" href="<%=path%>/commons/js/uploadify/uploadify.css">
<script type="text/javascript">
$(document).ready(function(){
	var outTypeChecked = $('#outType').val();
	if(outTypeChecked==2){
		$("#span_carriage").show();
		$("#outTypeIsOut").show();
		$("#span_carriage").show();
	}else{
		$("#span_carriage").hide();
		$("#outTypeIsOut").hide();
		$("#span_carriage").hide();
	}
$("#warehouseId").attr("disabled","true");
getCity($('#provinceId'),$('#cityId'));
            $("#upfile").uploadify({
           
           
            'swf': '<%=path%>/commons/js/uploadify/uploadify.swf',
            'uploader': '<%=path%>/Apply/uploadApply',
            'width': 100,
            'cancelImg': '<%=path%>/commons/js/uploadify-cancel.png',
            'multi ':false,
            'auto': true,
            file_size_limit: "10240K",
            fileTypeExts: '*.gif;*.jpg;*.jpeg;*.png;*.xls;*.doc;*.pdf',
            file_types: "*.jpg;*.png;*.jpeg;*.xls;*.doc;*.pdf",
            file_types_description: "*.jpg;*.jpeg;*.png;*.JPG;*.JPEG;*.PNG;*.xls;*.doc;*.pdf",
            onUploadSuccess:function(file,data, response){
            $("#imagePath").append("<p><a href=\"${path}/OnLine/Look?name="+data.split(";")[0]+"\" text=\""+file.name+"\" fileName=\""+data.split(";")[1]+"\" target=\"_blank\" url=\""+data.split(";")[0]+"\">"+file.name+"</a></p>");
        	$(".alert_user2").hide();
           //alert('The file ' + file.name + ' was successfully uploaded with a response of ' + response + ':' + data); 
        
        }, 
        onUploadComplete:function(){  
            closeWin(); 
         } 
        });
});
	function openUp()
	 	{
	 		$(".alert_upload").show();
	 	}
	 	
	 	function closeWin()
	 	{
	 		$(".alert_upload").hide();	
	 	}
function winOpen()
{
	if($("#warehouseId").val()==""||$("#warehouseId").val()==undefined)
	{
		alert("请选择仓库!");
		return;
	}
	showDialog("${path}/allocateNotify/getOperationPage?transferOutWarehouseCode="+$("#warehouseId").val() + "&transferOutWarehouseName="+encodeURI(encodeURI($("#warehouseId").find("option:selected").text())));
	
	

}
function showDialog(url)
{
	var isChrome = window.navigator.userAgent.indexOf("Chrome") !=-1;
	if(isChrome)
	{
		window.open(url,"库存明细","height="+window.screen.height*0.8+", width="+window.screen.width*0.8+", top="+(window.screen.availHeight-30-window.screen.height*0.8)/2+", left="+ (window.screen.availWidth-10-window.screen.width*0.8)/2+", toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
	}else{
		window.showModalDialog(url,window,"dialogWidth:"+window.screen.width*0.8+"px;dialogHeight:"+window.screen.height*0.8+"px");
	}
}
/*function callParentFunction(){
 var pWindow=window.dialogArguments;  if(pWindow != null){
  pWindow.doThingsAfterAdd(param);

 }else{
  window.opener.doThingsAfterAdd(param);
 }
}*/

function doThingsAfterAdd(config,skuID,butchNo){
	
	var len = $("#sho tr").length;
	var map = new Map();
	for ( var i = 1; i <= len; i++) {
		var tr = $('#sho tr').eq(i);
		var st9 = tr.children('td').eq(11).text();
		var st12 = tr.children('td').eq(13).text();
		map.put(st12,st9);
	}
		if(map.get(skuID)!=butchNo)
		{
			$("#sho").append(config);
			$("#warehouseId").attr("disabled","true");
		}
		
	}
	
	function DeleteRow() {
			 var num = 0;
				$(':checkbox[name=skuName]').each(function(){
					if($(this).attr('checked')){
						$(this).closest('tr').remove();
						num++;
					}
				});
				var len = $("#sho tr").length;
			if(len==1)
			{
				//$("#warehouseId").attr("disabled","");
				$("#warehouseId").removeAttr("disabled"); 
			}
        }
        
    function doSave()
    {
    	var images="";
    	$("#imagePath p a").each(function(){ 
    	images+=$(this).attr("fileName")+";"+$(this).attr("url")+";"+$(this).attr("text")+"|";
    	});
    	if(images!="")
    	{
    		images=images.substring(0,images.length-1);
    	}
    	var s=0;
    	var checkBox=document.getElementsByName('skuName');
    	var chk="";
    	for(var i=0; i<checkBox.length; i++){    
    		if(checkBox[i].checked){
    		s++;  //如果选中，将value添加到变量s中   
    		if($("#transferQty"+checkBox[i].value).val()=="")
    		{
    			alert("请输入申请数量！");
    			return;
    		}  
    		var flag=checkQty($("#stockQty"+checkBox[i].value).val(),$("#transferQty"+checkBox[i].value).val());
    		if(!flag)
    		{
    			alert("申请数量不能大于可用库存数量!");
    			return;
    		}
    		chk+="1,";   
    		}else{
    			chk+="0,";
    		}
    	}
    	if(s==0)
    	{
    		alert("请选择出库的商品!");
    		return ;
    	}
    	var data=$("#applyForm").serialize();
    	data=data+"&warehouseName="+$("#warehouseId").find("option:selected").text();
    	data=data+"&images="+images;
    	data=data+"&chk="+chk;
    	data=data+"&otherCusType="+$("#otherCusType").val();
    	$.ajax({
		type : "post", 
		url : "doUpdate", 
		dataType:"text",
		data:data,
		success : function(msg) { 
		if(msg=='OK')
		{
			window.location.href="${path}/Apply/findApplyOrder";
		}else{
			alert("此张采购单为供应商送货无需新建物流单!");
		}
			
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
    }
    function checkQty(stockQty,transferQty)
    {
    	if(parseInt(transferQty)>parseInt(stockQty))
    	{
    		return false;
    	}else{
    		return true;
    	}
    }
    function onblurInfo(index)
    {
    	if($("#transferQty"+index).val()=="")
    	{
    		alert("请填写申请数量!");
    		return;
    	}
    	if(parseInt($("#transferQty"+index).val())>parseInt($("#stockQty"+index).val()))
    	{
    		alert("申请数量不能大于可用库存数量!");
    		return ;
    	}
    }
    
    function doCheck()
    {
    	var images="";
    	$("#imagePath p a").each(function(){ 
    	images+=$(this).attr("fileName")+";"+$(this).attr("url")+";"+$(this).attr("text")+"|";
    	});
    	if(images!="")
    	{
    		images=images.substring(0,images.length-1);
    	}
    	var s=0;
    	var checkBox=document.getElementsByName('skuName');
    	var chk="";
    	for(var i=0; i<checkBox.length; i++){    
    		if(checkBox[i].checked){
    		s++;  //如果选中，将value添加到变量s中   
    		if($("#transferQty"+checkBox[i].value).val()=="")
    		{
    			alert("请输入申请数量！");
    			return;
    		}  
    		var flag=checkQty($("#stockQty"+checkBox[i].value).val(),$("#transferQty"+checkBox[i].value).val());
    		if(!flag)
    		{
    			alert("申请数量不能大于可用库存数量!");
    			return;
    		}
    		chk+="1,";   
    		}else{
    			chk+="0,";
    		}
    	}
    	if(s==0)
    	{
    		alert("请选择出库的商品!");
    		return ;
    	}
    	var data=$("#applyForm").serialize();
    	data=data+"&warehouseName="+$("#warehouseId").find("option:selected").text();
    	data=data+"&images="+images;
    	data=data+"&chk="+chk;
    	data=data+"&otherCusType="+$("#otherCusType").val();
    	$.ajax({
		type : "post", 
		url : "doCheck", 
		dataType:"text",
		data:data,
		success : function(msg) { 
		if(msg=='OK')
		{
			window.location.href="${path}/Apply/findApplyOrder";
		}else{
			alert("此张采购单为供应商送货无需新建物流单!");
		}
			
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
    }
    function getProvince(obj){
	$.ajax({ 
	    type : "post", 
	    url : '../baseData/findAllProvince', 
	    dataType:"json",
	    success : function(json) { 
	        $.each(eval(json),function(i,v){
	        	obj.append("<option value='"+v.provinceid+"'>"+v.provincename+"</option>"); 
			});
		}
	});
}


//根据省份ID获取市
function getCity(obj,obj1){
	var city='${dto.cityId}';
	var provinceId = obj.val();
	obj1.empty();
	obj1.append("<option value='0'>请选择</option>"); 
	$.ajax({
		 type : "post", 
     	 url :  '../baseData/findCitiesByProvinceId', 
     	 data:"provinceId="+provinceId,
     	 dataType:"json", 
         success : function(json) { 
			$.each(json,function(i,n){
				if(n.cityid==city)
				{
					obj1.append("<option value='"+n.cityid+"' selected='selected'>"+n.cityname+"</option>"); 
					getCounty($('#cityId'),$('#areaId'));
				}else{
					obj1.append("<option value='"+n.cityid+"'>"+n.cityname+"</option>"); 
				}
				
			});	
	     }
    });
}


//根据城市ID获取市
function getCounty(obj,obj1){ 
	var cityId = obj.val();
	var count='${dto.areaId}';
	obj1.empty();
	obj1.append("<option value='0'>请选择</option>"); 
	$.ajax({
		 type : "post", 
     	 url :  '../baseData/findCountiesByCityId', 
     	 data:"cityId="+cityId,
     	 dataType:"json", 
         success : function(json) { 
			$.each(json,function(i,n){
				if(n.countyid==count)
				{
					obj1.append("<option value='"+n.countyid+"' selected='selected'>"+n.countyname+"</option>"); 
				}else{
					obj1.append("<option value='"+n.countyid+"'>"+n.countyname+"</option>"); 
				}
				
			});	
	     }
	}); 
}
</script>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
	<div class="center">
	<!-- 左边 start -->
	 <div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
		</div>
		
		<!-- 左边 end -->
		<div class="right">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>商品储存&nbsp;&gt;&nbsp; </p>
					<p class="c1">出库申请(通知)单</p>
				</div>
	        </div>

	        <div class="pu_hd">
				<h3>表头信息</h3>
				<div class="btn">
					<!-- <a href="#">修改</a> -->
					<!--<a href="javascript:void(0)" onclick="doSave();">保存</a>
					<a href="javascript:void(0)" onclick="doCheck();">审核</a>-->
				</div>
			</div>
			<form id="applyForm">
			<div class="xia">
				<p class="p1">
					<span>出库申请单编号：</span><input type="text" name="applyChar" id="applyChar" value="${dto.applyChar }" readonly="readonly">
					<input type="hidden" name="sid" id="sid" value="${dto.sid }">
					<span>出库日期：</span><input type="text" name="createTime" id="createTime" value="<fmt:formatDate value="${dto.createTime }" pattern="yyyy-MM-dd"/>" readonly="readonly">
					<span>申请人：</span><input type="text" name="createBy" id="createBy" value="${dto.createBy }" readonly="readonly">
				</p>
				<p class="p1">
					<span>库房名称：</span><select name="warehouseId" id="warehouseId"><c:forEach items="${warehouses }" var="w">
					<c:if test="${dto.warehouseId eq w.warehouseCode}">
							<option value="${w.warehouseCode }" selected="selected">${w.warehouseName }</option>
					</c:if>
					<c:if test="${dto.warehouseId ne w.warehouseCode}">
							<option value="${w.warehouseCode }">${w.warehouseName }</option>
							</c:if>
					
					</c:forEach></select>
					<span>出库类型：</span><select name="outType" id="outType" class="w90" onchange="isShowOtherType(this)">
						<c:if test="${dto.outType==1 }"><option value="">请选择</option><option value="1" selected="selected">领用出库</option><option value="2">销售出库</option><option value="4">特殊出库</option></c:if>
						<c:if test="${dto.outType==47 ||dto.outType==46 ||dto.outType==45 ||dto.outType==43 ||dto.outType==42 ||dto.outType==41 ||dto.outType==40 ||dto.outType==39 || dto.outType==38 || dto.outType==32 || dto.outType==31 || dto.outType==30 || dto.outType==29 ||dto.outType==28 || dto.outType==27 || dto.outType==26 || dto.outType==25 || dto.outType==21 || dto.outType==22 || dto.outType==23 || dto.outType==24 || dto.outType==2 || dto.outType==3||dto.outType==9}"><option value="">请选择</option><option value="1">领用出库</option><option value="2" selected="selected">销售出库</option><option value="4">特殊出库</option></c:if>
						<c:if test="${dto.outType==4 }"><option value="">请选择</option>
						<c:if test="${isOutStorage==1}">
								<option value="1">领用出库</option>
							</c:if>
						<option value="2">销售出库</option><option value="4" selected="selected">特殊出库</option></c:if>
					</select>
					<c:if test="${dto.outType==1}">
					<span id="userCauseSpan">
					<span>*领用原因：</span>
					<c:if test="${dto.useCause==1}"><select name="useCause" id="useCause" class="w90" style="width: 130px;"><option value="">请选择</option><option value="1" selected="selected">样品出库 -归还</option><option value="2">样品出库-不归还</option><option value="3">地推活动</option></select></c:if>
					<c:if test="${dto.useCause==2}"><select name="useCause" id="useCause" class="w90" style="width: 130px;"><option value="">请选择</option><option value="1">样品出库 -归还</option><option value="2" selected="selected">样品出库-不归还</option><option value="3">地推活动</option></select></c:if>
					<c:if test="${dto.useCause==3}"><select name="useCause" id="useCause" class="w90" style="width: 130px;"><option value="">请选择</option><option value="1">样品出库 -归还</option><option value="2">样品出库-不归还</option><option value="3" selected="selected">地推活动</option></select></c:if>
					<c:if test="${dto.useCause==4}"><select name="useCause" id="useCause" class="w90" style="width: 130px;"><option value="">请选择</option><option value="1">样品出库 -归还</option><option value="2">样品出库-不归还</option><option value="3">地推活动</option><option value="4" selected="selected">礼包出库</option></select></c:if>
					</span>
					</c:if>
					<!--<strong>出库来源：</strong><select name="businessType" id="businessType" class="w90">
					<c:if test="${dto.businessType==209 }"><option >请选择</option><option value="209" selected="selected">其他</option></c:if>
					</select>-->
					<c:if test="${dto.outType==47 ||dto.outType==46 ||dto.outType==45 ||dto.outType==43 ||dto.outType==42 ||dto.outType==41 ||dto.outType==40 ||dto.outType==39 || dto.outType==38 || dto.outType==32 || dto.outType==31 || dto.outType==30 || dto.outType==29 || dto.outType==28 || dto.outType==27 || dto.outType==26 || dto.outType==25 || dto.outType==21 || dto.outType==22 || dto.outType==23 || dto.outType==24 || dto.outType==2 || dto.outType==3 || dto.outType==9}">
						<span id="outTypeIsOut" style="display: none;">
							<span style="padding-left: 75px;">客户名称：</span>
							<select name="otherCusType" id="otherCusType" class="w90" defaltVal="${dto.outType}">
								 	<option value="">请选择</option>
								 	<c:forEach items="${outTypeMap }" var="outTypeMap" varStatus="outType">
								 		<option value="${outTypeMap.key}" <c:if test="${dto.outType==outTypeMap.key }"> selected=true </c:if> >${outTypeMap.value}</option>
								 	</c:forEach>
							</select>
						</span>
					</c:if>
					<c:if test="${dto.outType==4}">
							<span id="scraptypeSpan">
							<span>*出库原因：</span>
							<c:if test="${dto.useCause==5}"><select name="useCause" id="useCause" class="w90" style="width: 130px;"><option value="">请选择</option><option value="5" selected="selected">残品出库</option><option value="6">产品临期</option></select></c:if>
							<c:if test="${dto.useCause==6}"><select name="useCause" id="useCause" class="w90" style="width: 130px;"><option value="">请选择</option><option value="5">残品出库</option><option value="6" selected="selected">产品临期</option></select></c:if>
							</span>
						</c:if>
				</p>
				<p class="p1">
				<!--<span>部门：</span> <input type="text" name="section" id="section" value="${dto.section }">-->
				<span>收货人：</span> <input type="text" name="fullName" id="fullName" value="${dto.fullName }">
				
				<span>联系电话：</span> <input type="text" name="mobile" id="mobile" value="${dto.mobile }">
				<span id="span_carriage"><span>运费(元)：</span><input type="text" name="carriage" id="carriage" value="${dto.carriage }" maxlength="10"></span>
				</p>
				<p class="p1">
				<!--<span>部门：</span> <input type="text" name="section" id="section">-->
				<span>省份：</span><select name="provinceId" id="provinceId" onchange="getCity($('#provinceId'),$('#cityId'));"><option value='0'>请选择</option><c:forEach items="${agentProvinces }" var="agent" >
				<c:if test="${dto.provinceId eq agent.provinceid}">
							<option value='${agent.provinceid }' selected="selected">${agent.provincename }</option>
					</c:if>
				
				<c:if test="${dto.provinceId ne agent.provinceid}">
							<option value='${agent.provinceid }' >${agent.provincename }</option>
							</c:if>
				</c:forEach></select> 
				<span>城市：</span> <select name="cityId" id="cityId" onchange="getCounty($('#cityId'),$('#areaId'))"></select>
				<span>区：</span> <select name="areaId" id="areaId" ></select>
				</p>
				<p class="p1"><span>收货地址：</span> <input type="text" name="address" id="address" value="${dto.address }"></p>
				<p class="p1">
					<span>备注：</span><input type="text" class="inp" name="remark" id="remark" value="${dto.remark }">
				</p>
				<p class="p1">
					<span>附件：</span>
					<div class="w">
					<div class="wd" id="imagePath">
					<c:forEach items="${dto.fileDtos }" var="file">
					<p><a href="${path}/OnLine/Look?name=${file.urlPath}" text="${file.fileName}" fileName="${file.fileName}" target="_blank" url="${file.urlPath}">${file.fileName}</a></p>
					</c:forEach>
					</div>
					<br>
					<p class="ti">目前只支持doc、xls、pdf、jpg、png文件上传</p>
					</div>
					<div class="fi_w">
					   <!--<button type="button" class="bt">上传附件</button>-->
					</div>
				</p>
			</div>
			
			<div class="pu_hd">
				<h3>申请出库明细</h3>
				<div class="btn">
					<!-- <a href="javascript:void(0)" onclick="winOpen();">操作</a>
					<a href="javascript:void(0)" onclick="DeleteRow();">删除行</a> -->
				</div>
			</div>
			
			<div class="pu_w">
		  		<div class="pu_b">
		  			<table id="sho">
		  				<tbody><tr class="order_hd">
		  					<th width="40px;">序号</th>
			  					<th width="40px;">选择</th>
			  					<th width="100px;">商品编码</th>
			  					<th width="100px;">商品条码</th>
			  					<th width="80px;">商品名称</th>
			  					<th width="120px;">SKU_ID</th>
			  					<th width="80px;">仓库条码</th>
			  					<th width="40px;">规格</th>
			  					<th width="40px;">单位</th>
			  					<th width="60px;">库存数量</th>
			  					<th width="60px;">申请数量</th>
			  					<th width="60px;" id="isShowTd1" <c:if test="${dto.outType!=47 &&dto.outType!=46 &&dto.outType!=45&&dto.outType!=43 &&dto.outType!=42 &&dto.outType!=41 &&dto.outType!=40 && dto.outType!=39 && dto.outType!=38 && dto.outType!=32 && dto.outType!=31 && dto.outType!=30 && dto.outType!=29 && dto.outType!=28 && dto.outType!=27 && dto.outType!=26 && dto.outType!=25 && dto.outType!=21 && dto.outType!=22 && dto.outType!=23 && dto.outType!=24 && dto.outType!=2 && dto.outType!=3 && dto.outType!=9}">style="display:none"</c:if>>销售单价</th>
				  				<th width="60px;" id="isShowTd2" <c:if test="${dto.outType!=47 &&dto.outType!=46 &&dto.outType!=45&&dto.outType!=43 &&dto.outType!=42 &&dto.outType!=41 &&dto.outType!=40 && dto.outType!=39 && dto.outType!=38 && dto.outType!=32 && dto.outType!=31 && dto.outType!=30 && dto.outType!=29 && dto.outType!=28 && dto.outType!=27 && dto.outType!=26 && dto.outType!=25 && dto.outType!=21 && dto.outType!=22 && dto.outType!=23 && dto.outType!=24 && dto.outType!=2 && dto.outType!=3 && dto.outType!=9}">style="display:none"</c:if>>销售金额</th>
			  					<th width="70px;">商品状态</th>
			  					<th width="70px;">批次</th>
			  					<th width="70px;">批号</th>
			  					<th width="70px;">生产日期</th>
			  					<th width="70px;" style="display:none">skuId</th>
			  					<th width="70px;" style="display:none">商品ID</th>
			  					<th width="60px;" style="display:none">单价</th>
		  				</tr>
		  				<c:forEach items="${dto.itemDtos }" var="result" varStatus="status">
		  				<tr>
		  					<td>${status.index+1 }</td>
		  					<td><input type="checkbox" name="skuName" value="${status.index+1 }"  class="sm"></td>
		  					<td><input type="text" id="pcode${status.index+1 }" name="pcode" value="${result.pcode }" readonly="readonly"></td>
		  					<td><input type="text" id="barCode${status.index+1 }" name="barCode" value="${result.barCode }" readonly="readonly"></td>
		  					<td><input type="text" id="pname${status.index+1 }" name="pname" value="${result.pname }" readonly="readonly"></td>
		  					<td><input type="text" title="${result.skuId }" id="skuId${status.index+1 }" name="skuId" value="${result.skuId }" readonly="readonly"></td>
		  					<td><input type="text" id="barskuId${status.index+1 }" name="barskuId" value="${result.barCode}" readonly="readonly"></td>
		  					<td><input type="text" id="format${status.index+1 }" name="format" value="${result.format }" readonly="readonly"></td>
		  					<td><input type="text" id="unit${status.index+1 }" name="unit" value="${result.unit }" readonly="readonly"></td>
		  					<td><input type="text" id="stockQty${status.index+1 }" name="stockQty" value="${result.qty }" readonly="readonly"></td>
		  					<td><input type="text" id="transferQty${status.index+1 }" name="transferQty" onblur="onblurInfo(${status.index+1 });" value="${result.applyQty }"></td>
		  					<c:if test="${dto.outType==47 ||dto.outType==46 ||dto.outType==45||dto.outType==43 ||dto.outType==42 ||dto.outType==41 ||dto.outType==40 ||dto.outType == 39 || dto.outType == 38 || dto.outType==32 || dto.outType==31 || dto.outType==30 || dto.outType==29 || dto.outType==28 || dto.outType==27 || dto.outType==26 || dto.outType==25 || dto.outType==21 || dto.outType==22 || dto.outType==23 || dto.outType==24 || dto.outType==2 || dto.outType==3 || dto.outType==9}">
			  					<td><input type='text' id="unitprice${status.index+1}" name="unitprice" value="${result.price}" onkeyup="sumPrice(${status.index+1})" maxlength="10"></td>
			  					<td><input type='text' id="totalprice${status.index+1}" name="totalprice" value="${result.price*result.applyQty}" readonly="readonly"></td>
			  				</c:if>
		  					<td><input type="text" id="isgenuine${status.index+1 }" name="isgenuine" value="<c:if test="${result.isgenuine==1 }">正品</c:if><c:if test="${result.isgenuine==2 }">残品</c:if>" readonly="readonly"></td>
		  					<td><input type="text" id="batchNumber${status.index+1 }" name="batchNumber" value="${result.batchNumber }" readonly="readonly"></td>
		  					<td><input type="text" id="batchId${status.index+1 }" name="batchId" value="${result.batchId }" readonly="readonly"></td>
		  					<td><input type="text" id="productionDate${status.index+1 }" name="productionDate" value="<fmt:formatDate value="${result.productionDate }" pattern="yyyy-MM-dd"/>" readonly="readonly"></td>
		  					<%-- <td><input type="text" id="skuId${status.index+1 }" name="skuId" value="${result.skuId }" readonly="readonly"></td>
		  					<td><input type="text" id="pid${status.index+1 }" name="pid" value="${result.pid }" readonly="readonly"></td> --%>
		  				</tr>
		  				</c:forEach>
		  			</tbody>
		  			</table>
		  		</div>
	        </div>
</form>
		</div>
	</div>
	
	
		<div class="alert_user2">
			<div class="bg"></div>
			<div class="w">
				<div class="box1">
					<img src="${path }/commons/images/close.jpg" class="w_close">
				</div>
				<div class="box3">
					<input type="file" class="inputstyle" id="upfile">
					
					
				</div>
				
			</div>
		</div>

<script type="text/javascript">
	$(".bt").click(function(){
		$(".alert_user2").show();
	});
	$(".w_close,.bt1,.bt2").click(function(){
		$(".alert_user2").hide();
	}); 
	
	function isShowOtherType(dat){
		var outTypeChecked = $(dat).val();
		if(outTypeChecked==2){
			$("#scraptypeSpan").hide();
			$("#outTypeIsOut").show();
		}else if(outTypeChecked==1){
			$("#scraptypeSpan").hide();
			$("#userCauseSpan").hide();
			$("#outTypeIsOut").hide();
		}else if(outTypeChecked==4){
			$("#outTypeIsOut").hide();
			$("#scraptypeSpan").show();
		}
		
		
	}
	
</script>

<div id="dialog">  
    <iframe id="myIframe" src=""></iframe>  
</div>
</body>
</html>