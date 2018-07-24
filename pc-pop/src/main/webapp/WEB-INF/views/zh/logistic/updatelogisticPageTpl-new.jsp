<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	  <title>商家后台管理系统-物流模板</title>
      <%@include file="/WEB-INF/views/zh/include/base.jsp"%>
       <link rel="stylesheet" type="text/css" href="${path}/css/logistic/logisTemplate.css">
       
        <link rel="stylesheet" href="${path}/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
        <script type="text/javascript" src="${path}/js/ztree/jquery.ztree.core-3.5.js"></script>
	 	<script type="text/javascript" src="${path}/js/ztree/jquery.ztree.excheck-3.5.js"></script>
	</head>
	<body>
        <%@include file="/WEB-INF/views/zh/include/header.jsp"%>
		<div class="wrap">
		   <%@include file="/WEB-INF/views/zh/include/leftProduct.jsp" %>
           <div>
           <form method="post" id="templateAction" action="${path}/order/updateGeneralLogisTpl" >
           	<input type="hidden" name="type" value="1">
           	
			
			<div class="c2">
				<div class="c21">
					<div class="title">
						<p>商家中心&nbsp;&gt;&nbsp;</p>
						<p>物流管理&nbsp;&gt;&nbsp;</p>
						<p class="c1">编辑物流模板</p>
					</div>
				</div>
				<div class="blank10"></div>
				<div class="right" style="font-family:微软雅黑";>
					<div class="item">
						<label style="font-size:14px"><i>*</i>模板名称：</label>
						<div class="itemright">
							<input style="border-radius:5px;" type="text" name="logisticTempName" id="logisticTempName" value="${generalLogisticTpl.logisticTempName}"> 
							<span class="dpl-tip-inline-warning"></span>
						</div>
					</div>
					
					 <div class="item" style="border-radius:5px;">
                            <label><i>*</i>发货地：</label>
                            <div class="itemright" style="border-radius:5px;">
                            
                                <select class="sui-option" id="provinceId" style="border-radius:5px;padding:0 5px" name="provinceStartId"  onchange="getCity()" >
							    </select>
							    <select class="sui-option" id="cityId" style="border-radius:5px;padding:0 5px" name="cityStartId" onchange="getCounty()">
							     
							    </select>
							    <!-- <select class="sui-option" id="areaId" name="productAreaid">
							      <option>请选择</option>
							    </select> -->
                            
                            </div>
                        </div>
					<div class="item">
						<label><i>*</i>物流运费：</label>
						<div class="itemright">
							
								<div class="item_bo">
									<input style="border-radius:5px;padding:0 5px" type="text" name="baseQt" value="${generalLogisticTpl.baseQt }"
										 id="baseQt"><span
										id="basewp">件</span>内， <input style="border-radius:5px;padding:0 5px" type="text" name="baseFee" value="${generalLogisticTpl.baseFee }"
										id="baseFee" >元， 每增加<input
										style="border-radius:5px;padding:0 5px" type="text" name="stepQt" id="stepQt" value="${generalLogisticTpl.stepQt }"
										><span id="stepwp">件</span>，增加运费
									<input style="border-radius:5px;padding:0 5px" type="text" name="stepFee" id="stepFee" value="${generalLogisticTpl.stepFee }">元
								</div>					
							
						</div>
					</div>
					
										<div class="item">
						<label style="font-size:14px"><i>*</i>包邮金额：</label>
						<div class="itemright">满
							<input style="border-radius:5px;" type="text" name="nonefeePrice" id="nonefeePrice" value="${generalLogisticTpl.nonefeePrice }">元,包邮 
							<span class="dpl-tip-inline-warning"></span>
						</div>
					</div>
					
										<div class="item">
						<label style="font-size:14px"><i>*</i>包邮件数：</label>
						<div class="itemright">满
							<input style="border-radius:5px;" type="text" name="nonefeeNum" id="nonefeeNum" value="${generalLogisticTpl.nonefeeNum }">件,包邮 
							<span class="dpl-tip-inline-warning"></span>
						</div>
					</div>
					
					<div class="item">
						<label style="font-size:14px"><i>*</i>包邮地区：</label>
						<div class="itemright">													 
						      <div class="zTreeDemoBackground left">
								<ul id="treeDemo" class="ztree"></ul>
							  </div> 
						</div>
					</div>
					
					<div id="hiddenBox" >
						<input type="hidden" name="logisticTempId" id="logisticTempId" value="${generalLogisticTpl.logisticTempId }" />
					</div>
					<div >
						<label style="font-size:14px"><i>*</i>详细说明：</label>
							<input style="margin-left: 0px;width: 280px;height: 50px;" type="text" name="memo"  value="${generalLogisticTpl.memo }">
					</div>
					<div class="btn">
						<button type="button" id="createTemp" onclick="submitform(this.form)">保存</button>
					</div>
				</div>
			</div>
		</form>
		</div>
     	<%@include file="/WEB-INF/views/zh/include/last.jsp"%>
	<!-- 底部 end -->
   </body>
   <script type="text/javascript">
   $(document).ready(function (){
		getAllProvince()
		
	})


	function getAllProvince(){
	   var p=${generalLogisticTpl.provinceStartId };
		$("#provinceId option").remove();
		$("#provinceId").append('<option value="" >请选择</option> ');
		$("#cityId option").remove();
		 $("#cityId").append('<option value=""  > 请选择 </option> ');
		 $("#areaId option").remove();
		 $("#areaId").append('<option value=""  >请选择</option> ');
		 var _dataType = "text";
		 var _type = "POST";
		 var  _url = "../limit/getProvince";
		 var _async = true;
		 $.ajax({
			 dataType:_dataType,
			 type:_type,
			 url:_url,
			 async:_async,
			 success:function(res){
				 if(res==null||res==""){
					 return false;
				 }
				 var province=eval('('+res+')');
				 for(var i=0;i<province.length;i++){
					 $("#provinceId").append('<option value="'+province[i].provinceid+'">'+province[i].provincename+'</option>')
				 }
					 $("#provinceId").val(p);
					 var c=${generalLogisticTpl.cityStartId };
						$("#cityId option").remove();
						$("#cityId").append('<option value=""  >请选择</option> ');
						 $("#areaId option").remove();
						 $("#areaId").append('<option value=""  >请选择</option> ');
						 var proId=$("#provinceId").val();
						 var _dataType="text";
						 var _type="POST";
						 var _url="../limit/getCity";
						 var _data="proId="+proId;
						 var _async=true;
						 $.ajax({
							 dataType:_dataType,
							 type:_type,
							 url:_url,
							 data:_data,
							 async:_async,
							 success:function(res){
								 if(res==null||res==""){
									 return false;
								 }
								 var city=eval('('+res+')');
								 for(var i=0;i<city.length;i++){
									 $("#cityId").append('<option  value="'+city[i].cityid+'">'+city[i].cityname+'</option>')
								 }
								 $("#cityId").val(c);
							 }
						 });
			 }
		 });
	
			
	}

	function getCity(){
		var c=${generalLogisticTpl.cityStartId };
		//alert(c);
		$("#cityId option").remove();
		$("#cityId").append('<option value=""  >请选择</option> ');
		 $("#areaId option").remove();
		 $("#areaId").append('<option value=""  >请选择</option> ');
		 var proId=$("#provinceId").val();
		 var _dataType="text";
		 var _type="POST";
		 var _url="../limit/getCity";
		 var _data="proId="+proId;
		 var _async=true;
		 $.ajax({
			 dataType:_dataType,
			 type:_type,
			 url:_url,
			 data:_data,
			 async:_async,
			 success:function(res){
				 if(res==null||res==""){
					 return false;
				 }
				 var city=eval('('+res+')');
				 for(var i=0;i<city.length;i++){
					 $("#cityId").append('<option  value="'+city[i].cityid+'">'+city[i].cityname+'</option>')
				 }
				 $("#cityId").val(c);
			 }
		 });
		
	}


   
   
   
   
   
   
  
   /* function zTreeOnCheck(event, treeId, treeNode) {
	   
	    alert(treeNode.name);
	}; */

   var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			view: {
				showIcon: false
		    },
		    callback: {
				onCheck: zTreeOnCheck
			}
		};
		
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, ${tree});
			$.fn.zTree.getZTreeObj("treeDemo").expandAll(false);
			
			
		});
		function zTreeOnCheck(event,treeId,treeNode){
            var treeObj=$.fn.zTree.getZTreeObj("treeDemo"),
            nodes=treeObj.getCheckedNodes(true),
            v="";
            for(var i=0;i<nodes.length;i++){
            v+=nodes[i].id + ",";
            }
	
		}
		
		 function submitform(a){
			  var treeObj=$.fn.zTree.getZTreeObj("treeDemo"),
	            nodes=treeObj.getCheckedNodes(true),
	            v="";
	            for(var i=0;i<nodes.length;i++){
	            v+=nodes[i].id + ",";
	            }
				var hiddenString = '<input type="hidden" name="nonefeeProvinceId" id="nonefeeProvinceId" value="'+v+'" />';
			    $("#hiddenBox").append(hiddenString);
			    document.getElementById("templateAction").submit();
			}
   </script>
</html>