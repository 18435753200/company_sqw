<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
    <title>My JSP 'setCost.jsp' starting page _国内费用设置</title>
    <%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=path%>/commons/js/uploadify/jquery.uploadify.js"></script>
	<link rel="stylesheet" href="<%=path%>/commons/js/uploadify/uploadify.css">
  </head>
  <script type="text/javascript">
  	var id='${id}';
  	var qty='${qty}';
  	var price='${price}';
  	var businessNumber='${businessNumber}';
  	var notificationId='${notificationId}';
  	var shipId='${shipId}';
  	var rowNum=0;
  $(document).ready(function(){
  pushValue('checked',qty,price);
  var exchangeRate=$("#currencyCode1").find("option:selected").attr("song");
	$("#exchangeRate1").val(exchangeRate);
  $("#upfile").uploadify({
        	'height': 24,
            'swf': '<%=path%>/commons/js/uploadify/uploadify.swf',
            'uploader': '<%=path%>/purchasereg/uploadCost',
            'width': 63,
            'cancelImg': '<%=path%>/commons/js/uploadify-cancel.png',
            'multi ':false,
            'auto': true,
            file_size_limit: "1024K",
            fileTypeExts: '*.gif;*.jpg;*.jpeg;*.png',
            file_types: "*.jpg;*.png;*.jpeg;",
            file_types_description: "*.jpg;*.jpeg;*.png;*.JPG;*.JPEG;*.PNG;",
            onUploadSuccess:function(file,data, response){ 
        	$("#imagePath"+rowNum).val(data);
        //alert('The file ' + file.name + ' was successfully uploaded with a response of ' + response + ':' + data); 
        
        }, 
        onUploadComplete:function(){  
            closeWin(); 
         } 
        });
});

function tan(row)
	 	{
	 		$(".alert_upload").show();	 
	 		rowNum=row;
	 	}
	 	
	 	function closeWin()
	 	{
	 		$(".alert_upload").hide();	
	 	}
	 	
	 	function getFileName()
	 	{
	 		$("#filePath").val($("#upfile").val());
	 	}
  function CreateRow() {
  			var t01;
			if($("#regDetil tr:last").find("td:eq(0)").text()=='')
			{
				t01=1;
			}else{
				t01 = parseInt($("#regDetil tr:last").find("td:eq(0)").text())+1;
			}
            var html="<tr><td>"+t01+"</td><td><input type=\"checkbox\" name=\"delBox\" id=\"delBox1\" value=\""+t01+"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"createDate\" id=\"createDate"+t01+"\" style=\"border:0px;\" onclick=\"WdatePicker();\"/></td><td><input type=\"text\" name=\"freightCode\" id=\"freightCode"+t01+"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"receiveAddress\" id=\"receiveAddress"+t01+"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"freightPrice\" id=\"freightPrice"+t01+"\" style=\"border:0px;\" onBlur=\"totalAllocation($(this).val(),"+t01+");\"/></td><td><select name=\"allocationCode\" id=\"allocationCode"+t01+"\" onchange=\"allocationChange($(this).attr('id'))\"><c:forEach items='${Dictionarys }' var='Dictionary'><option value='${Dictionary.dictValue }'>${Dictionary.dictName }</option></c:forEach></select></td><td><input type=\"text\" name=\"allocationQty\" id=\"allocationQty"+t01+"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"allocationPrice\" id=\"allocationPrice"+t01+"\" style=\"border:0px;\"/></td><td><select name=\"currencyCode\" id=\"currencyCode"+t01+"\" onchange=\"selCurrency($(this).find('option:selected').attr('song'),1)\" ><c:forEach items='${Currency }' var='currency'><option value='${currency.code }' song='${currency.exchangeRate }'>${currency.currencyType }</option></c:forEach></select></td><td><input type=\"text\" name=\"exchangeRate\" id=\"exchangeRate"+t01+"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"localPrice\" id=\"localPrice"+t01+"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"localTotalPrice\" id=\"localTotalPrice"+t01+"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"imagePath\" id=\"imagePath"+t01+"\" style=\"border:0px; width:200px;\" onfocus=\"tan("+t01+");\"/></td></tr>";
            var tab=$("#regDetil");
            tab.append(html);
            pushValue('checked',qty,price);
            selCurrency($("#currencyCode"+t01).find("option:selected").attr("song"),t01);
        }
        
   function DeleteRow() {
			 var num = 0;
				$(':checkbox[name=delBox]').each(function(){
					if($(this).attr('checked')){
						$(this).closest('tr').remove();
						num++;
					}
				});
        }
        
        
        function totalAllocation(value,row,id)
{
	if($("#allocationCode"+row).val()=='001')
	{
		$("#allocationPrice"+row).val(parseFloat(value/$("#allocationQty"+row).val()).toFixed(2));
	}else if($("#allocationCode"+row).val()=='003')
	{
		$("#allocationPrice"+row).val(parseFloat(value/$("#allocationQty"+row).val()).toFixed(2));
	}else if($("#allocationCode"+row).val()=='002')
	{
		
	}
}
        function selCurrency(exchangeRate,row)
{
	$("#exchangeRate"+row).val(exchangeRate);
}
   function pushValue(checked,qty,price)
    {
    
    	if(checked=='checked')
    	{
    		
    	$("select[name=allocationCode]").each(function(){
    		if($(this).find("option:selected").val()=='001')
    		{
    			
    			$("#allocationQty"+$(this).attr("id").substring(14)).val(qty);
    		}else if($(this).find("option:selected").val()=='002')
    		{
    			
    		}else if($(this).find("option:selected").val()=='003')
    		{
    			$("#allocationQty"+$(this).attr("id").substring(14)).val(price);
    		} 
		});
    	}else{
    		$("select[name=allocationCode]").each(function(){
    		if($(this).find("option:selected").val()=='001')
    		{
    			
    			$("#allocationQty"+$(this).attr("id").substring(14)).val("");
    		}else if($(this).find("option:selected").val()=='002')
    		{
    			
    		}else if($(this).find("option:selected").val()=='003')
    		{
    			$("#allocationQty"+$(this).attr("id").substring(14)).val("");
    		} 
		});
    	}
   }
   function allocationChange(id)
   {

   		if($("#"+id).val()=='001')
   		{
   			if(qty!=undefined)
   			{
   				$("#allocationQty"+id.substring(14)).val(qty);
   				
   			}
   			
   		}else if($("#"+id).val()=='002')
   		{
   			
   		}else if($("#"+id).val()=='003')
   		{
   			if(price!=undefined)
   			{
   				$("#allocationQty"+id.substring(14)).val(price);
   			}
   			
   		}
   }
   
   function saveDetil()
    {
    	var value=$("#regMess").serialize();
    	
    	
    		
			$.ajax({
		type : "post", 
		url : "savePurchaseRegDetil", 
		dataType:"text",
		data:value+"&id="+id+"&shipId="+shipId+"&businessNumber="+businessNumber+"&notificationId="+notificationId,
		success : function(msg) { 
			window.location.href="<%=path%>/purchasereg/findPurchaseReg";
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
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

			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>

		
		<!-- 左边 end -->
		<div class="right">
		
		 <div class="pu_h">
					<h3>国内运费登记</h3>
					<div class="btn">
				  	   		<a href="javascript:void(0)" onclick="CreateRow();">新增</a>
				  	   		<a href="javascript:void(0)" onclick="DeleteRow();">删除</a>
					     	<a href="javascript:void(0)" onclick="saveDetil();">保存</a>
					</div>
				</div>
				
	       <div class="pu_w" id="regfee" >
	  		<div class="pu_b">
	  		<form id="regMess">
	  			<table id="regDetil">
	  				<tr class="order_hd">
	  					<th width="60px;">序号</th>
	  					<th width="60px;">选择</th>
	  					<th width="80px;">发生日期</th>
	  					<th width="80px;">运费单号</th>
	  					<th width="110px;">接货地址</th>
	  					<th width="80px;">运费金额(元)</th>
	  					<th width="130px;">分摊方法</th>
	  					<th width="80px;">分摊数量</th>
	  					<th width="80px;">分摊金额(元)</th>
	  					<th width="80px;">币别</th>
	  					<th width="80px;">汇率</th>
	  					<th width="80px;">本币单价</th>
	  					<th width="80px;">本币总价</th>
	  					<th width="200px;">凭据摄影</th>
	  				</tr>
	  				<tr>
	  					<td>1</td>
	  					<td><input type="checkbox" name="delBox" id="delBox1" value="1" style="border:0px;"/ class="sm"></td>
	  					<td><input type="text" name="createDate" id="createDate1" style="border:0px;"onclick="WdatePicker();"/></td>
	  					<td><input type="text" name="freightCode" id="freightCode1" style="border:0px;"/></td>
	  					<td><input type="text" name="receiveAddress" id="receiveAddress1" style="border:0px; width:110px;"/></td>
	  					<td><input type="text" name="freightPrice" id="freightPrice1" style="border:0px;" onBlur="totalAllocation($(this).val(),1);"/></td>
	  					<td><select name="allocationCode" id="allocationCode1" onchange="allocationChange($(this).attr('id'))"><c:forEach items="${Dictionarys }" var="Dictionary"><option value="${Dictionary.dictValue }">${Dictionary.dictName }</option></c:forEach></select></td>
	  					<td><input type="text" name="allocationQty" id="allocationQty1" style="border:0px;"/></td>
	  					<td><input type="text" name="allocationPrice" id="allocationPrice1" style="border:0px;"/></td>
	  					<td><select name="currencyCode" id="currencyCode1" onchange="selCurrency($(this).find('option:selected').attr('song'),1)"><c:forEach items="${Currency }" var="currency"><option value="${currency.code }" song="${currency.exchangeRate }">${currency.currencyType }</option></c:forEach></select></td>
	  					<td><input type="text" name="exchangeRate" id="exchangeRate1" style="border:0px;"/></td>
	  					<td><input type="text" name="localPrice" id="localPrice1" style="border:0px;"/></td>
	  					<td><input type="text" name="localTotalPrice" id="localTotalPrice1" style="border:0px;"/></td>
	  					<td><input type="text" name="imagePath" id="imagePath1" style="border:0px; width:200px;" onfocus="tan(1);"/></td>
	  				</tr>
	  			</table>
	  			</form>
	  			</div>
	  		</div>
		</div>
	</div>
   
   
    <div  class="alert_upload"  style="display:none;">
		<div class="bg"></div>
		<div class="w">
		
			<div class="box1">
				<h2>上传图片</h2>
				<img src="/web-platform/commons/images/close.jpg" class="w_close"  onclick="closeWin();">
			</div>
			
			<div class="box2">
					
				<div class="fi">
				
				    
				    <input type="file" class="inputstyle" id="upfile">
				
			</div>
			</div>
			
			<div class="box3">
				
			</div>
				
		</div>
	</div>
	
	
  </body>
</html>
