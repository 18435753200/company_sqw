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
getProvince($("#provinceId"));
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
            var outTypeChecked = $('#outType').val();
        	if(outTypeChecked==2){
        		$("#thirdOrderIdSpan").show();
        		$("#span_carriage").show();
        		$("#outTypeIsOut").show();
        		$("#isShowTd1").show();
        		$("#isShowTd2").show();
        		$("#userCauseSpan").hide();
        	}else{
        		$("#thirdOrderIdSpan").hide();
        		$("#span_carriage").hide();
        		$("#outTypeIsOut").hide();
        		$("#isShowTd1").hide();
        		$("#isShowTd2").hide();
        	}
        	
        	if(outTypeChecked==1){
    			$("#userCauseSpan").show();
    		}
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
function appendNewTr(obj) {
	var th0;
	if($("#sho tr:last").find("td:eq(0)").text()=="")
	{
		th0=1;
	}else{
		th0=parseInt($("#sho tr:last").find("td:eq(0)").text())+1;
	}
	//var html = "<tr><td >"+obj.children('td').eq(0).text()+"</td><td ><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text()+"'></td><td >"+obj.children('td').eq(2).text()+"</td><td >"+obj.children('td').eq(3).text()+"</td><td >"+obj.children('td').eq(4).text()+"</td><td >"+obj.children('td').eq(5).text()+"</td><td >"+obj.children('td').eq(6).text()+"</td><td >"+obj.children('td').eq(7).text()+"</td><td ><input type='text' name='allQty' id='allQty"+obj.children('td').eq(0).text()+"'/></td><td >"+obj.children('td').eq(11).text()+"</td><td >"+obj.children('td').eq(8).text()+"</td><td >"+obj.children('td').eq(9).text()+"</td><td >"+obj.children('td').eq(10).text()+"</td><td >"+obj.children('td').eq(12).text()+"</td><td >"+obj.children('td').eq(13).text()+"</td></tr>";
	//var html = "<tr><td>"+obj.children('td').eq(0).text()+"</td><td><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text()+"'></td><td><input type='text' id='pcode"+obj.children('td').eq(0).text()+"' name='pcode"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(2).text()+"' readonly='readonly'></td><td><input type='text' id='barCode"+obj.children('td').eq(0).text()+"' name='barCode"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(3).text()+"'readonly='readonly'></td><td><input type='text' id='pname"+obj.children('td').eq(0).text()+"' name='pname"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(4).text()+"'readonly='readonly'></td><td><input type='text' id='format"+obj.children('td').eq(0).text()+"' name='format"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(5).text()+"'readonly='readonly'></td><td><input type='text' id='unit"+obj.children('td').eq(0).text()+"' name='unit"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(6).text()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+obj.children('td').eq(0).text()+"' name='stockQty"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(7).text()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+obj.children('td').eq(0).text()+"' name='transferQty"+obj.children('td').eq(0).text()+"' value=''></td><td><input type='text' id='isgenuine"+obj.children('td').eq(0).text()+"' name='isgenuine"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(11).text()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+obj.children('td').eq(0).text()+"' name='batchNumber"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(8).text()+"'readonly='readonly'></td><td><input type='text' id='batchId"+obj.children('td').eq(0).text()+"' name='batchId"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(9).text()+"'readonly='readonly'></td><td><input type='text' id='productionDate"+obj.children('td').eq(0).text()+"' name='productionDate"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(10).text()+"'readonly='readonly'></td><td><input type='text' id='skuId"+obj.children('td').eq(0).text()+"' name='skuId"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(12).text()+"'readonly='readonly'></td><td><input type='text' id='pid"+obj.children('td').eq(0).text()+"' name='pid"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(13).text()+"'readonly='readonly'></td></tr>";
	//var html = "<tr class='sotClass'><td>"+obj.children('td').eq(0).text().trim()+"</td><td><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text().trim()+"'></td><td><input type='text' id='pcode"+obj.children('td').eq(0).text().trim()+"' name='pcode' value='"+obj.children('td').eq(2).text().trim()+"' readonly='readonly'></td><td><input type='text' id='barCode"+obj.children('td').eq(0).text().trim()+"' name='barCode' value='"+obj.children('td').eq(3).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pname"+obj.children('td').eq(0).text().trim()+"' name='pname' value='"+obj.children('td').eq(4).text().trim()+"'readonly='readonly'></td><td><input type='text' id='format"+obj.children('td').eq(0).text().trim()+"' name='format' value='"+obj.children('td').eq(5).text().trim()+"'readonly='readonly'></td><td><input type='text' id='unit"+obj.children('td').eq(0).text().trim()+"' name='unit' value='"+obj.children('td').eq(6).text().trim()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+obj.children('td').eq(0).text().trim()+"' name='stockQty' value='"+obj.children('td').eq(7).text().trim()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+obj.children('td').eq(0).text().trim()+"' name='transferQty'  onblur='onblurInfo("+obj.children('td').eq(0).text().trim()+");'  value=''></td><td><input type='text' id='isgenuine"+obj.children('td').eq(0).text().trim()+"' name='isgenuine' value='"+obj.children('td').eq(11).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+obj.children('td').eq(0).text().trim()+"' name='batchNumber' value='"+obj.children('td').eq(8).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchId"+obj.children('td').eq(0).text().trim()+"' name='batchId' value='"+obj.children('td').eq(9).text().trim()+"'readonly='readonly'></td><td><input type='text' id='productionDate"+obj.children('td').eq(0).text().trim()+"' name='productionDate' value='"+obj.children('td').eq(10).text().trim()+"'readonly='readonly'></td><td><input type='text' id='skuId"+obj.children('td').eq(0).text().trim()+"' name='skuId' value='"+obj.children('td').eq(12).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pid"+obj.children('td').eq(0).text().trim()+"' name='pid' value='"+obj.children('td').eq(13).text().trim()+"'readonly='readonly'></td></tr>";
 
   //var	html = "<tr class='sotClassComp'><td>"+th0+"</td><td><input type='checkbox' name='skuName' value='"+th0+"' class='sm'></td><td><input type='text' id='pcode"+th0+"' name='pcode' value='"+obj.children('td').eq(2).text().trim()+"' readonly='readonly'></td><td><input type='text' id='barCode"+th0+"' name='barCode' value='"+obj.children('td').eq(3).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pname"+th0+"' name='pname' value='"+obj.children('td').eq(4).text().trim()+"'readonly='readonly'></td><td><input type='text' id='format"+th0+"' width='120px' class='format1' name='format' value='"+obj.children('td').eq(5).text().trim()+"'readonly='readonly'></td><td><input type='text' id='unit"+th0+"' name='unit' value='"+obj.children('td').eq(6).text().trim()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+th0+"' name='stockQty' value='"+obj.children('td').eq(7).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+th0+"' name='batchNumber' value='111"+obj.children('td').eq(8).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchId"+th0+"' name='batchId' value='"+obj.children('td').eq(9).text().trim()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+th0+"' name='transferQty'  onblur='onblurInfo($(this),"+obj.children('td').eq(7).text().trim()+");'  value='' onblur='sumPrice("+th0+")'></td><td><input type='text' id='pid"+th0+"' name='pid' value='"+obj.children('td').eq(13).text().trim()+"'readonly='readonly'></td><td><input  style='display:none' type='text' id='isgenuine"+th0+"' name='isgenuine' value='"+obj.children('td').eq(11).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='productionDate"+th0+"' name='productionDate' value='"+obj.children('td').eq(10).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='skuId"+th0+"' name='skuId' value='"+obj.children('td').eq(12).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='pid"+th0+"' name='pid' value='"+obj.children('td').eq(13).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='price"+th0+"' name='price' value='"+obj.children('td').eq(14).text().trim()+"'readonly='readonly'></td></tr>";
       // 	var	html = "<tr class='sotClassComp'><td>"+th0+"</td><td><input type='checkbox' name='skuName' value='"+th0+"' class='sm'></td><td><input type='text' id='pcode"+th0+"' name='pcode' value='"+obj.children('td').eq(2).text().trim()+"' readonly='readonly'></td><td><input type='text' id='barCode"+th0+"' name='barCode' value='"+obj.children('td').eq(3).text().trim()+"'readonly='readonly'></td><td><input title='"+obj.children('td').eq(4).text().trim()+"' type='text' id='pname"+th0+"' name='pname' value='"+obj.children('td').eq(4).text().trim()+"'readonly='readonly'></td><td><input type='text' id='format"+th0+"' width='120px' class='format1' name='format' value='"+obj.children('td').eq(5).text().trim()+"'readonly='readonly'></td><td><input type='text' class='format2'  id='unit"+th0+"' name='unit' value='"+obj.children('td').eq(6).text().trim()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+th0+"' name='stockQty' value='"+obj.children('td').eq(7).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+th0+"' name='batchNumber' value='"+obj.children('td').eq(8).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchId"+th0+"' name='batchId' value='"+obj.children('td').eq(9).text().trim()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+th0+"' name='transferQty'  onblur='onblurInfo($(this),"+obj.children('td').eq(9).text().trim()+");'  value='' onblur='sumPrice("+th0+")'></td><td><input type='text' id='pid"+th0+"' name='pid' value='"+obj.children('td').eq(13).text().trim()+"'readonly='readonly'></td><td  style='display:none' ><input type='text' id='isgenuine"+th0+"' name='isgenuine' value='"+obj.children('td').eq(11).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='productionDate"+th0+"' name='productionDate' value='"+obj.children('td').eq(10).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='skuId"+th0+"' name='skuId' value='"+obj.children('td').eq(12).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='pid"+th0+"' name='pid' value='"+obj.children('td').eq(13).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='price"+th0+"' name='price' value='"+obj.children('td').eq(14).text().trim()+"'readonly='readonly'></td></tr>";
     var html = "<tr class='sotClassComp'><td>"+th0+"</td><td><input type='checkbox' name='skuName' value='"+th0+"' class='sm'></td><td><input type='text' id='pcode"+th0+"' name='pcode' value='"+obj.children('td').eq(2).text().trim()+"' readonly='readonly'></td><td><input type='text' id='barCode"+th0+"' name='barCode' value='"+obj.children('td').eq(3).text().trim()+"'readonly='readonly'></td><td><input title='"+obj.children('td').eq(4).text().trim()+"' type='text' id='pname"+th0+"' name='pname' value='"+obj.children('td').eq(4).text().trim()+"'readonly='readonly'></td><td><input type='text' id='skuIdn"+th0+"' width='120px' class='format1' name='skuIdn' value='"+obj.children('td').eq(5).text().trim()+"'readonly='readonly'></td><td><input type='text' class='format2'  id='barskuId"+th0+"' name='barskuId' value='"+obj.children('td').eq(6).text().trim()+"'readonly='readonly'></td><td><input type='text' id='format"+th0+"' name='format' value='"+obj.children('td').eq(7).text().trim()+"'readonly='readonly'></td></td><td><input type='text' id='unit"+th0+"' name='unit' value='"+obj.children('td').eq(8).text().trim()+"'readonly='readonly'></td></td><td><input type='text' id='stockQty"+th0+"' name='stockQty' value='"+obj.children('td').eq(9).text().trim()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+th0+"' name='transferQty'  onblur='onblurInfo($(this),"+obj.children('td').eq(9).text().trim()+");'  value='' onblur='sumPrice("+th0+")'></td><td><input type='text' id='isgenuine"+th0+"' name='isgenuine' value='"+obj.children('td').eq(13).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='batchNumber"+th0+"' name='batchNumber' value='"+obj.children('td').eq(10).text().trim()+"'readonly='readonly'><td style='display:none'><input type='text' id='batchId"+th0+"' name='batchId' value='"+obj.children('td').eq(11).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='productionDate"+th0+"' name='productionDate' value='"+obj.children('td').eq(12).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='skuId"+th0+"' name='skuId' value='"+obj.children('td').eq(14).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='pid"+th0+"' name='pid' value='"+obj.children('td').eq(15).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='price"+th0+"' name='price' value='"+obj.children('td').eq(16).text().trim()+"'readonly='readonly'></td><td name='goodsQtyTd'><input type='text' id='goodsQty"+th0+"' name='goodsQty' value='"+obj.children('td').eq(17).text().trim()+"'readonly='readonly'></td></tr>";
	 var outTypeChecked = $('#outType').val();
     if(outTypeChecked==2){
        	//html = "<tr class='sotClassComp'><td>"+th0+"</td><td><input type='checkbox' name='skuName' value='"+th0+"' class='sm'></td><td><input type='text' id='pcode"+th0+"' name='pcode' value='"+obj.children('td').eq(2).text().trim()+"' readonly='readonly'></td><td><input type='text' id='barCode"+th0+"' name='barCode' value='"+obj.children('td').eq(3).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pname"+th0+"' name='pname' value='"+obj.children('td').eq(4).text().trim()+"'readonly='readonly'></td><td><input type='text' id='format"+th0+"' name='format' value='"+obj.children('td').eq(5).text().trim()+"'readonly='readonly'></td><td><input type='text' id='unit"+th0+"' name='unit' value='"+obj.children('td').eq(6).text().trim()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+th0+"' name='stockQty' value='"+obj.children('td').eq(7).text().trim()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+th0+"' name='transferQty'  onblur='onblurInfo($(this),"+obj.children('td').eq(7).text().trim()+");'  value='' onblur='sumPrice("+th0+")'></td><td><input type='text' id='unitprice"+th0+"' name='unitprice' value='' onblur='sumPrice("+th0+")' maxlength='10'></td><td><input type='text' id='totalprice"+th0+"' name='totalprice' value='' readonly='readonly'></td><td><input type='text' id='isgenuine"+th0+"' name='isgenuine' value='"+obj.children('td').eq(11).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='batchNumber"+th0+"' name='batchNumber' value='"+obj.children('td').eq(8).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='batchId"+th0+"' name='batchId' value='"+obj.children('td').eq(9).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='productionDate"+th0+"' name='productionDate' value='"+obj.children('td').eq(10).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='skuId"+th0+"' name='skuId' value='"+obj.children('td').eq(12).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='pid"+th0+"' name='pid' value='"+obj.children('td').eq(13).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='price"+th0+"' name='price' value='"+obj.children('td').eq(14).text().trim()+"'readonly='readonly'></td></tr>";
        	html = "<tr class='sotClassComp'><td>"+th0+"</td><td><input type='checkbox' name='skuName' value='"+th0+"' class='sm'></td><td><input type='text' id='pcode"+th0+"' name='pcode' value='"+obj.children('td').eq(2).text().trim()+"' readonly='readonly'></td><td><input type='text' id='barCode"+th0+"' name='barCode' value='"+obj.children('td').eq(3).text().trim()+"'readonly='readonly'></td><td><input title='"+obj.children('td').eq(4).text().trim()+"' type='text' id='pname"+th0+"' name='pname' value='"+obj.children('td').eq(4).text().trim()+"'readonly='readonly'></td><td><input type='text' id='skuIdn"+th0+"' width='120px' class='format1' name='skuIdn' value='"+obj.children('td').eq(5).text().trim()+"'readonly='readonly'></td><td><input type='text' class='format2'  id='barskuId"+th0+"' name='barskuId' value='"+obj.children('td').eq(6).text().trim()+"'readonly='readonly'></td><td><input type='text' id='format"+th0+"' name='format' value='"+obj.children('td').eq(7).text().trim()+"'readonly='readonly'></td><td><input type='text' id='unit"+th0+"' name='unit' value='"+obj.children('td').eq(8).text().trim()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+th0+"' name='stockQty' value='"+obj.children('td').eq(9).text().trim()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+th0+"' name='transferQty'  onblur='onblurInfo($(this),"+obj.children('td').eq(7).text().trim()+");'  value='' onblur='sumPrice("+th0+")'></td><td><input type='text' id='unitprice"+th0+"' name='unitprice' value='' onblur='sumPrice("+th0+")' maxlength='10'></td><td><input type='text' id='totalprice"+th0+"' name='totalprice' value='' readonly='readonly'></td><td><input type='text' id='isgenuine"+th0+"' name='isgenuine' value='"+obj.children('td').eq(13).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='batchNumber"+th0+"' name='batchNumber' value='"+obj.children('td').eq(10).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='batchId"+th0+"' name='batchId' value='"+obj.children('td').eq(11).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='productionDate"+th0+"' name='productionDate' value='"+obj.children('td').eq(12).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='skuId"+th0+"' name='skuId' value='"+obj.children('td').eq(14).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='pid"+th0+"' name='pid' value='"+obj.children('td').eq(15).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='price"+th0+"' name='price' value='"+obj.children('td').eq(16).text().trim()+"'readonly='readonly'></td><td name='goodsQtyTd'><input type='text' id='goodsQty"+th0+"' name='goodsQty' value='"+obj.children('td').eq(17).text().trim()+"'readonly='readonly'></td></tr>";
       }
	return html;
}
function doThingsAfterAdd(config,skuID,butchNo,isgenuine){
	
	var len = $("#sho tr").length;
	var map = new Map();
	for ( var i = 1; i <= len; i++) {
		var tr = $('#sho tr').eq(i);"src/main/webapp/commons/js/productMixAdd_list_fn.js"
		var st8 = tr.children('td').eq(10).find("input").val();
		var st9 = tr.children('td').eq(9).find("input").val();
		var st12 = tr.children('td').eq(13).find("input").val();
		map.put(st12+st9,st8);
	}
		if(map.get(skuID+isgenuine)!=butchNo)
		{
			$("#sho").append(appendNewTr(config));
			$("#warehouseId").attr("disabled","true");
			isShowOtherType($("#outType")[0]);
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
    	var outType=$("#outType").val();
    	var otherCusType =$("#otherCusType").val();
    	var carriage =$("#carriage").val();
    	var thirdOrderId =$("#thirdOrderId").val();
    	var useCause =$("#useCause").val();
    	if(outType==2){
    		if(otherCusType==undefined||otherCusType=="")
        	{
        		alert("请选择客户名称！");
        		return;
        	}
    		if(carriage==undefined||carriage=="")
        	{
    			alert("当销售出库，需填写运费!");
        		return;
        	}else{
        		var regu = /^\d+(\.\d+)?$/;
            	if(!regu.test(carriage))
            	{
            		alert("运费不是数字!");
            		return;
            	}
        	}
    		
    		if($("#thirdOrderIdSpan").is(":hidden")){
    		}else{
	    		if(thirdOrderId==undefined||thirdOrderId=="")
	        	{
	        		alert("请填写第三方订单号！");
	        		return;
	        	}
    		}
    		
    		for(var i=0; i<checkBox.length; i++){    
        		if(checkBox[i].checked){
        			var chckId = "unitprice"+checkBox[i].value;
        			if($("#"+chckId).val()==""){
        				alert("当销售出库，需填写销售单价!");
        	    		return ;
        				}else{
        					var regu = /^\d+(\.\d+)?$/;
        			    	if(!regu.test($("#"+chckId).val()))
        			    	{
        			    		alert("销售单价不是数字!");
        			    		return;
        			    	}
        				}
        			}
        			
        	}
    		outType = $("#otherCusType").val();
    	}
    	
    	if(outType==1){
    		if(useCause==undefined||useCause=="")
        	{
    			alert("请选择领用原因！");
        		return;
        	}
		}
    	
    	var fullName=$("#fullName").val();
    	var address=$("#address").val();
    	var mobile=$("#mobile").val();
    	var provinceId=$("#provinceId").val();
    	var cityId=$("#cityId").val();
    	var areaId=$("#areaId").val();
    	if(outType==undefined||outType=="")
    	{
    		alert("请选择出库类型！");
    		return;
    	}
    	if(fullName==undefined||fullName=="")
    	{
    		alert("请填写收货人信息！");
    		return;
    	}
    	if(strlen(fullName)>15)
    	{
    		alert("填写收货人信息过长！");
    		return;
    	}
    	if(address==undefined||address=="")
    	{
    		alert("请填写收货人地址信息！");
    		return;
    	}
    	if(strlen(address)>90)
    	{
    		alert("填写收货人地址信息过长！");
    		return;
    	}
    	if(mobile==undefined||mobile=="")
    	{
    		alert("请填写收货人电话信息！");
    		return;
    	}
    	if(strlen(mobile)>11)
    	{
    		alert("填写收货人电话信息过长！");
    		return;
    	}
    	if(provinceId==undefined||provinceId=="0")
    	{
    		alert("请填写省份信息！");
    		return;
    	}
    	if(cityId==undefined||cityId=="0")
    	{
    		alert("请填写城市信息！");
    		return;
    	}
    	if(areaId==undefined||areaId=="0")
    	{
    		alert("请填写区域信息！");
    		return;
    	}
    	var data=$("#applyForm").serialize();
    	data=data+"&warehouseName="+$("#warehouseId").find("option:selected").text();
    	data=data+"&images="+images;
    	data=data+"&chk="+chk;
    	data=data+"&warehouse="+$("#warehouseId").find("option:selected").val();
    	data=data+"&provinceName="+$("#provinceId").find("option:selected").text();
    	data=data+"&cityName="+$("#cityId").find("option:selected").text();
    	data=data+"&areaName="+$("#areaId").find("option:selected").text();
    	data=data+"&otherCusType="+$("#otherCusType").val();
    	data=data+"&carriage="+carriage;
    	data=data+"&thirdOrderId="+thirdOrderId;
    	data=data+"&useCause="+useCause;
    	var result=checkTab();
    	if(result)
    	{
    		$.ajax({
		type : "post", 
		url : "doSave", 
		dataType:"text",
		data:data,
		success : function(msg) { 
		if(msg=='OK')
		{
			window.location.href="${path}/Apply/findApplyOrder";
		}else if(msg=='error'){
			alert("该商品存在多个相同skuId！");
		}else{
			alert("数据异常!");
		}
			
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
		});
    	}
    	
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
    function onblurInfo(index,value)
    {
    	
    	var outTypeValue = $("#outType").val();
    	//如果是特殊出库则库存数量为残品数量
    	if(outTypeValue == 4){
    		value = index.parent('td').nextAll("td[name='goodsQtyTd']").find("input").val();
    	}
    	
    	if(index.val()=="")
    	{
    		alert("请填写申请数量!");
    		return;
    	}
    	var g = /^[1-9]*[1-9][0-9]*$/;
    	if(!g.test(index.val()))
    	{
    		alert("申请数量不是正整数!");
    		return;
    	}
    	if(parseInt(index.val())>parseInt(value))
    	{
    		alert("申请数量不能大于可用库存数量!");
    		return ;
    	}
    }
    function checkTab()
    {
    	var flag=true;
    	var Tablen=$("#sho tr").length;
    	for(var i=1;i<Tablen;i++)
    	{
    		
		var tr = $("#sho tr").eq(i);
		var st7 = tr.children('td').eq(9).find("input").val();
		var st8 = tr.children('td').eq(10).find("input").val();
		if(st7!=undefined&&st8!=undefined)
		{
			if(st8=="")
			{
				alert("第"+i+"行申请数量不能为空!");
				flag=false;
				break;
				
			}
			var g = /^[1-9]*[1-9][0-9]*$/;
			if(!g.test(st8))
			{
				alert("第"+i+"行申请数量不是正整数!");
				flag=false;
				break;
				
			}
			if(parseInt(st7)<parseInt(st8))
			{
				alert("第"+i+"行申请数量不能大于可用库存数量!");
				flag=false;
				break;
				
			}
		}
		
    	}
    	return flag;
    }
    
    //获取所有城市
function getProvince(obj){
	obj.empty();
	obj.append("<option value='0'>请选择</option>"); 
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
				obj1.append("<option value='"+n.cityid+"'>"+n.cityname+"</option>"); 
			});	
	     }
    });
}


//根据城市ID获取市
function getCounty(obj,obj1){ 
	var cityId = obj.val();
	obj1.empty();
	obj1.append("<option value='0'>请选择</option>"); 
	$.ajax({
		 type : "post", 
     	 url :  '../baseData/findCountiesByCityId', 
     	 data:"cityId="+cityId,
     	 dataType:"json", 
         success : function(json) { 
			$.each(json,function(i,n){
				obj1.append("<option value='"+n.countyid+"'>"+n.countyname+"</option>"); 
			});	
	     }
	}); 
}
function strlen(str){
    var len = 0;
    for (var i=0; i<str.length; i++) { 
     var c = str.charCodeAt(i); 
    //单字节加1 
     if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) { 
       len++; 
     } 
     else { 
      len+=2; 
     } 
    } 
    return len;
}
</script>
<body>
	&quot;<%@include file="/WEB-INF/views/include/header.jsp"%>
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
					<a href="javascript:void(0)" onclick="doSave();">保存</a>
					<!--<a href="#">审核</a>-->
				</div>
			</div>
			<form id="applyForm">
			<div class="xia">
				<p class="p1">
					<span>出库申请单编号：</span><input type="text" name="applyChar" id="applyChar" value="${number }" readonly="readonly">
					<input type="hidden" name="sid" id="sid" value="${sid }">
					<span>出库日期：</span><input type="text" name="createTime" id="createTime" value="${now }" readonly="readonly">
					<span>申请人：</span><input type="text" name="createBy" id="createBy" value="${createBy }" readonly="readonly">
				</p>
				<p class="p1">
					<span>库房名称：</span><select name="warehouseId" id="warehouseId"><option value="">请选择</option><c:forEach items="${warehouses }" var="w"><option value="${w.warehouseCode }">${w.warehouseName }</option></c:forEach></select>
					<span>出库类型：</span>
						<select name="outType" id="outType" class="w90" onchange="isShowOtherType(this)">
							<option value="">请选择</option>
							<c:if test="${isOutStorage==1}">
								<option value="1">领用出库</option>
							</c:if>
							<option value="2">销售出库</option>
							<option value="4">特殊出库</option> 
						</select>
						
					
					<span id="userCauseSpan" style="display: none;">
					<span>*领用原因：</span><select name="useCause" id="useCause" class="w90" style="width: 130px;"><option value="">请选择</option><option value="1">样品出库 -归还</option><option value="2">样品出库-不归还</option><option value="3">地推活动</option><option value="4">礼包出库</option></select>
					</span>
					<!--<strong>出库来源：</strong> <select name="businessType" id="businessType" class="w90"><option value="">请选择</option><option value="209">其他</option></select> -->
					<span id="outTypeIsOut" style="display: none;">
						<span style="padding-left: 75px;">*客户名称：</span>
							<select name="otherCusType" id="otherCusType" class="w90">
								<option value="">请选择</option>
						 		<c:forEach items="${outTypeMap }" var="outTypeMap" varStatus="outType">
						 			<option value="${outTypeMap.key}">${outTypeMap.value}</option>
						 		</c:forEach>
						</select>
					</span>
					<span id="scrapTypeIsOut" style="display: none;">
						<span style="padding-left: 75px;">*出库原因：</span>
							<select name="scrapType" id="scrapType" class="w90">
								<option value="">请选择</option>
								<option value="5">残品出库</option>
								<option value="6" >产品临期</option>
						</select>
					</span>
				</p>
				<p class="p1">
				<!--<span>部门：</span> <input type="text" name="section" id="section">-->
				<span>收货人：</span> <input type="text" name="fullName" id="fullName">
				
				<span>联系电话：</span> <input type="text" name="mobile" id="mobile">
				<span id="span_carriage"><span>运费(元)：</span><input type="text" name="carriage" id="carriage" onblur="checkDot(this)" maxlength="10"></span>
				</p>
				<p class="p1" id="thirdOrderIdSpan" style="display: none;"><span>*第三方订单号：</span> <input type="text" name="thirdOrderId" id="thirdOrderId" class="inp" maxlength="20"></p>
				<p class="p1">
				<!--<span>部门：</span> <input type="text" name="section" id="section">-->
				<span>省份：</span><select name="provinceId" id="provinceId" onchange="getCity($('#provinceId'),$('#cityId'));"></select> 
				<span>城市：</span> <select name="cityId" id="cityId" onchange="getCounty($('#cityId'),$('#areaId'))"></select>
				<span>区：</span> <select name="areaId" id="areaId" ></select>
				</p>
				<p class="p1"><span>收货地址：</span> <input type="text" name="address" id="address" class="inp"></p>
				<p class="p1">
					<span>备注：</span><input type="text" class="inp" name="remark" id="remark">
				</p>
				<p class="p1">
					<span>附件：</span>
					<div class="w">
					<div class="wd" id="imagePath"></div><br>
					<p class="ti">目前只支持doc、xls、pdf、jpg、png文件上传</p>
					</div>
					<div class="fi_w">
					   <button type="button" class="bt">上传附件</button>
					</div>
				</p>
			</div>
			
			<div class="pu_hd">
				<h3>申请出库明细</h3>
				<div class="btn">
					<a href="javascript:void(0)" onclick="winOpen();">操作</a>
					<a href="javascript:void(0)" onclick="DeleteRow();">删除行</a>
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
			  					<th id="isShowTd4" width="60px;">库存数量</th>
			  					<th width="60px;">申请数量</th>
			  					<th width="60px;" id="isShowTd1">销售单价</th>
			  					<th width="60px;" id="isShowTd2">销售金额</th>
			  					<th width="70px;">商品状态</th>
			  					<th width="70px;" style="display:none">批次</th>
			  					<th width="70px;" style="display:none">批号</th>
			  					<th width="70px;" style="display:none">生产日期</th>
			  					<th width="70px;" style="display:none">skuId</th>
			  					<th width="70px;" style="display:none">商品ID</th>
			  					<th width="60px;" style="display:none">单价</th>
			  					<th id="isShowTd3" width="70px;">残品数量</th>
		  				</tr>
		  				<!-- <tr>
		  					<td>1</td>
			  				<td><input type="checkbox"></td>
			  				<td></td>
			  				<td></td>
			  				<td></td>
			  				<td></td>
			  				<td></td>
			  				<td></td>
			  				<td></td>
			  				<td></td>
			  				<td></td>
			  				<td>2015.5.23</td>
			  				<td></td>
		  				</tr> -->
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
			$("#thirdOrderIdSpan").show();
			$("#outTypeIsOut").show();
			$("#isShowCarriage").show();
			$("#span_carriage").show();
			$("#isShowTd1").show();
    		$("#isShowTd2").show();
    		$("#userCauseSpan").hide();
    		$("#scrapTypeIsOut").hide();
    		$("#isShowTd3").hide();
    		$("#isShowTd4").show();
    		$("input[name='transferQty']").each(
    				function(){
    					var th0 = $(this).parent().siblings()[0].childNodes[0].data;
    					if($("#unitprice"+th0).length==0){
		    				var nodeStr ="<td><input type='text' id='unitprice"+th0+"' name='unitprice' value='' onblur='sumPrice("+th0+")'></td><td><input type='text' id='totalprice"+th0+"' name='totalprice' value='' readonly='readonly'></td>";  
		    				$(this).parent().after(nodeStr);
    					}
    				});
    		$("input[name='goodsQty']").each(  
    				function(){  
    				$(this).parent().hide();  
    				});
			$("input[name='stockQty']").each(function(){
				$(this).parent().show();	
			});
		}else if(outTypeChecked==1){
			$("#thirdOrderIdSpan").hide();
			$("#outTypeIsOut").hide();
			$("#isShowCarriage").hide();
			$("#span_carriage").hide();
			$("#isShowTd1").hide();
    		$("#isShowTd2").hide();
    		$("#scrapTypeIsOut").hide();
    		$("#isShowTd3").hide();
    		$("#isShowTd4").show();
    		$("input[name='totalprice']").each(  
    				function(){  
    				$(this).parent().hide();  
    				});
    		$("input[name='unitprice']").each(  
    				function(){  
    				$(this).parent().hide();  
    				});
    		$("input[name='goodsQty']").each(  
    				function(){  
    				$(this).parent().hide();  
    				});
			$("input[name='stockQty']").each(function(){
				$(this).parent().show();	
			});
		}else if(outTypeChecked==4){
			$("#scrapTypeIsOut").show();
			$("#outTypeIsOut").hide();
			$("#userCauseSpan").hide();	
			$("#isShowTd1").hide();
    		$("#isShowTd2").hide();
			$("#isShowTd3").show();
			$("#isShowTd4").hide();
	/* 		transferQty onblur onblurInfo($(this),"+obj.children('td').eq(9).text().trim()+"); */		
    		$("input[name='totalprice']").each(  
    				function(){  
    				$(this).parent().hide();  
    				});
    		$("input[name='unitprice']").each(  
    				function(){  
    				$(this).parent().hide();  
    				});
			$("input[name='stockQty']").each(function(){
				$(this).parent().hide();	
			});
    		$("input[name='goodsQty']").each(  
    				function(){  
    				$(this).parent().show();  
    				});
		}else{
			$("#scrapTypeIsOut").show();
			$("#outTypeIsOut").hide();
			$("#userCauseSpan").hide();  
			$("#isShowTd3").hide();
			$("#isShowTd4").show();
    		$("input[name='goodsQty']").each(  
    				function(){  
    				$(this).parent().hide();  
    				});
			$("input[name='stockQty']").each(function(){
				$(this).parent().show();	
			});
		}
		
		if(outTypeChecked==1){
			$("#userCauseSpan").show();
		}
	}
	
	function sumPrice(num){
		var qty = $("#transferQty"+num).val();
		var unitPrice = $("#unitprice"+num).val()?$("#unitprice"+num).val():0.0;
    	var regu = /^\d+(\.\d+)?$/;
    	if(!regu.test(unitPrice))
    	{
    		alert("销售单价不是数字!");
    		return;
    	}
		$("#totalprice"+num).val((qty*unitPrice).toFixed(2));
	}
	
	function checkDot(dat){
		var regu = /^\d+(\.\d+)?$/;
    	if(!regu.test($(dat).val()))
    	{
    		alert("运费不是数字!");
    		return;
    	}
		var carriageVal = ($(dat).val()*1).toFixed(2);
		$(dat).val(carriageVal);
	}
</script>

<div id="dialog">  
    <iframe id="myIframe" src=""></iframe>  
</div>
</body>
</html>