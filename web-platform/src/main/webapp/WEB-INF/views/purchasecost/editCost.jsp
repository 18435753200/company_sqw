<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
    <title>My JSP 'setCost.jsp' starting page _采购费用修改</title>
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
		<script type="text/javascript" src="<%=path%>/commons/js/uploadify/jquery.uploadify.js"></script>
	<link rel="stylesheet" href="<%=path%>/commons/js/uploadify/uploadify.css">
<script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
  </head>
  <script type="text/javascript">
  var id='${id}';
  	var qty='${qty}';
  	var price='${price}';
  $(document).ready(function(){
  	
	pushValue('checked',qty,price);
	
	$("#upfile").uploadify({
        	'height': 24,
            'swf': '<%=path%>/commons/js/uploadify/uploadify.swf',
            'uploader': '<%=path%>/purchasecost/uploadCost',
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
  function CreateRow() {
           var t01;
			if($("#detilTab tr:last").find("td:eq(0)").text()=='')
			{
				t01=1;
			}else{
				t01 = parseInt($("#detilTab tr:last").find("td:eq(0)").text())+1;
			}
            var html="<tr><td>"+t01+"</td><td><input type=\"checkbox\" name=\"delBox\" id=\"delBox"+t01+"\" value=\""+(t01)+"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"costDate\" id=\"costDate"+t01+"\" value=\"\" style=\"border:0px;\" onclick=\"WdatePicker();\"/></td><td><select name=\"costCode\" id=\"costCode"+t01+"\"><c:forEach items='${Cost }' var='Cost'><option value='${Cost.code }'>${Cost.costName }</option></c:forEach></select></td><td><input type=\"text\" name=\"waitAllocationPrice\" id=\"waitAllocationPrice"+t01+"\" value=\"\" style=\"border:0px;\" onBlur=\"totalAllocation($(this).val(),"+t01+");\"/></td><td><select name=\"allocationCode\" id=\"allocationCode"+t01+"\" onchange=\"allocationChange($(this).attr('id'))\"><c:forEach items='${Dictionarys }' var='Dictionary'><option value='${Dictionary.dictValue }'>${Dictionary.dictName }</option></c:forEach></select></td><td><input type=\"text\" name=\"allocationQty\" id=\"allocationQty"+t01+"\" value=\"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"allocationPrice\" id=\"allocationPrice"+t01+"\" value=\"\" style=\"border:0px;\"/></td><td><select name=\"currencyCode\" id=\"currencyCode"+t01+"\" onchange=\"selCurrency($(this).find('option:selected').attr('song'),"+t01+")\"><c:forEach items='${Currency }' var='currency'><option value='${currency.code }' song='${currency.exchangeRate }''>${currency.currencyType }</option></c:forEach></select></td><td><input type=\"text\" name=\"exchangeRate\" id=\"exchangeRate"+t01+"\" value=\"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"localPrice\" id=\"localPrice"+t01+"\" value=\"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"localTotalPrice\" id=\"localTotalPrice"+t01+"\" value=\"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"imagePath\" id=\"imagePath"+t01+"\" value=\"\" style=\"border:0px;\" width:200px;\" onfocus=\"tan("+t01+");\"/></td></tr>";
            var tab=$("#detilTab");
            tab.append(html);
            pushValue('checked',qty,price);
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
        function selCurrency(exchangeRate,row)
{
	$("#exchangeRate"+row).val(exchangeRate);
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
    	var value=$("#detilMess").serialize();
    	
    		
			$.ajax({
		type : "post", 
		url : "savePurchaseCostDetil", 
		dataType:"text",
		data:value+"&purchaseId="+id,
		success : function(msg) { 
			window.location.href="<%=path%>/purchasecost/findPurchaseCost";
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});    
		
    	
    	
    }
    function clickSubmit(page)
    {
    	var pro_array  = new Array();
	if(id!=""&&id!=undefined)
	{
		pro_array.push("id="+id);
	}
	if(qty!=""&&qty!=undefined)
	{
		pro_array.push("qty="+qty);
	}
	if(price!=""&&price!=undefined)
	{
		pro_array.push("price="+price);
	}
	if(page!=""&&page!=undefined)
	{
		pro_array.push("page="+page);
	}
	$.ajax({
		type : "post", 
		url : "editCost", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#purchase").html(msg);
			$("#fee").show();
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
	           	    <h3>采购费用登记</h3>
	           	    <div class="btn">
			  	   		<a href="javascript:void(0)" onclick="CreateRow();">新增</a>
			  	   		<a href="javascript:void(0)" onclick="DeleteRow();">删除</a>
				     	<a href="javascript:void(0)" onclick="saveDetil();">保存</a>
	                 </div>
	           	</div>
	           	
	           <div class="pu_w" style="" id="fee">
	  		  	<div class="pu_b">
	  		  	<form id="detilMess">
  		  			<table id="detilTab" >
  		  				<tr class="order_hd">
  		  					<th width="60px;">序号</th>
  		  					<th width="60px;">选择</th>
  		  					<th width="80px">费用日期</th>
  		  					<th width="110px">费用名称</th>
  		  					<th width="80px">待分担金额</th>
  		  					<th width="130px">分摊方式</th>
  		  					<th width="80px">分摊数量</th>
  		  					<th width="80px">分摊金额</th>
  		  					<th width="80px">币别</th>
  		  					<th width="80px">汇率</th>
  		  					<th width="80px">本币单价</th>
  		  					<th width="80px">本币总价</th>
  		  					<th width="200px">凭据摄影</th>
  		  				</tr>
  		  				<c:forEach items="${pb.result }" var="result" varStatus="status">
  		  				<tr>
  		  					<td>${status.index+1}</td>
  		  					<td><input type="checkbox" name="delBox" id="delBox1" value="1" style="border:0px;" class="sm"/></td>
  		  					<td><input type="text" name="costDate" id="costDate1" value="<fmt:formatDate value="${result.costDate }"  pattern="yyyy-MM-dd"/>" style="border:0px;" onclick="WdatePicker();"/></td>
  			  				<td><select name="costCode" id="costCode1"><c:forEach items="${Cost }" var="Cost"><c:choose><c:when test="${result.costCode==Cost.code}"><option value="${Cost.code }" selected="selected">${Cost.costName }</option></c:when><c:otherwise><option value="${Cost.code }" >${Cost.costName }</option></c:otherwise></c:choose></c:forEach></select></td>
  			  				<td><input type="text" name="waitAllocationPrice" id="waitAllocationPrice1" value="${result.waitAllocationPrice }" style="border:0px;" onBlur="totalAllocation($(this).val(),1);"/></td>
  			  				<td><select name="allocationCode" id="allocationCode1" onchange="allocationChange($(this).attr('id'))"><c:forEach items="${Dictionarys }" var="Dictionary"><c:choose><c:when test="${result.allocationCode==Dictionary.dictValue }"><option value="${Dictionary.dictValue }" selected="selected">${Dictionary.dictName }</option></c:when><c:otherwise><option value="${Dictionary.dictValue }">${Dictionary.dictName }</option></c:otherwise></c:choose></c:forEach></select></td>
  			  				<td><input type="text" name="allocationQty" id="allocationQty1" value="${result.allocationQty }" style="border:0px;"/></td>
  			  				<td><input type="text" name="allocationPrice" id="allocationPrice1" value="${result.allocationPrice }" style="border:0px;"/></td>
  			  				<td><select name="currencyCode" id="currencyCode1" onchange="selCurrency($(this).find('option:selected').attr('song'),1)"><c:forEach items="${Currency }" var="currency"><c:choose><c:when test="${result.currencyCode==currency.code }"><option value="${currency.code }" song="${currency.exchangeRate }" selected="selected">${currency.currencyType }</option></c:when><c:otherwise><option value="${currency.code }" song="${currency.exchangeRate }">${currency.currencyType }</option></c:otherwise></c:choose></c:forEach></select></td>
  			  				<td><input type="text" name="exchangeRate" id="exchangeRate1" value="${result.exchangeRate }" style="border:0px;"/></td>
  			  				<td><input type="text" name="localPrice" id="localPrice1" value="${result.localPrice }" style="border:0px;"/></td>
  			  				<td><input type="text" name="localTotalPrice" id="localTotalPrice1" value="${result.localTotalPrice }" style="border:0px;"/></td>
  			  				<td><input type="text" name="imagePath" id="imagePath1" value="${result.imagePath }" style="border:0px;  width:200px;" onfocus="tan(${status.index+1});"/></td>
  		  				</tr>
  		  				</c:forEach>
  		  			</table>
  		  			</form>
	  		  	</div>
	  		</div>
		</div>
	</div>
      <script type="text/javascript">
	 	
	 	$(".right .pu_w").css("height",$(window).height()-160+'px');
	 	
	</script>
   
   
   
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
