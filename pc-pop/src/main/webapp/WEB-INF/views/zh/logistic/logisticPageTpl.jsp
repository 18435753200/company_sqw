<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	  <title>商家后台管理系统-物流模板</title>
      <%@include file="/WEB-INF/views/zh/include/base.jsp"%>
	  <link rel="stylesheet" type="text/css" href="${path}/css/zh/user.css">
      <link rel="stylesheet" type="text/css" href="${path}/css/zh/juese.css">
      <link rel="stylesheet" type="text/css" href="${path}/css/zh/logist.css">
      <link rel="stylesheet" type="text/css" href="${path}/css/zh/WdatePicker.css">
       <link rel="stylesheet" href="${path}/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
	 <script type="text/javascript" src="${path}/js/ztree/jquery.ztree.core-3.5.js"></script>
	 <script type="text/javascript" src="${path}/js/ztree/jquery.ztree.excheck-3.5.js"></script>
	 <script type="text/javascript" src="${path}/js/address/address.js"></script>
	</head>
	<body>
		<div id="editDiv" class="alert_user2" style="display:none;">
		<div class="bg"></div>
		<div class="w">
			<div class="box1">
				<h2>绑定子供应商</h2>
				<img src="<%=path%>/images/close.jpg" class="w_close">
			</div>
			<div class="box3">
				<form id="editForm" action="${path}/brand/bind" method="post" >
						<input id="brandId"  type="hidden" name="brandId" >
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<colgroup>
								<col width="60px">
								<col>
							</colgroup>
							<tr>
								<td align="right">品牌名称：</td>
								<td><input id="brandName" type="text" disabled="disabled"></td>
							</tr>
							<tr>
								<td align="right">子供应商：</td>
								<td align="left">
									<select name="subSupplier" id="subSupplier">
									</select>
								</td>
							</tr>
						</table>
				        
						<button id="brandEdit" type="button"  class="bt1">确定</button>
						<button id="userEidtCancel" type="button"  class="bt2" >取消</button>
				</form>
			</div>
			<div class="blank20"></div>
		</div>
	</div>
	<!-- 弹框 end -->

        <%@include file="/WEB-INF/views/zh/include/header.jsp"%>	
	    
		<div class="wrap">
			<%@include file="/WEB-INF/views/zh/include/leftProduct.jsp" %>
			<form  id="SearchFrom"  action="${path}/brand/list" method="post">
			     <input name="sortFields"   type="hidden" value="${page.sortFields}"/> 
		         <input name="order"  type="hidden" value="${page.order}"/> 
			</form>
			
			
		      <!-- 右边 start -->
			<div class="right f_l">
				<div class="title">
					<p class="c1">物流模版设置</p>
					<div class="clear"></div>
				</div>
				<div class="blank5"></div>
				<div class="sui-modal2" id="center2">
                      <a  class="sui-modal-close2">
                         <span>×</span>
                      </a>
                       <div class="zTreeDemoBackground left">
							<ul id="treeDemo2" class="ztree"></ul>
						</div>
					</div>	
				
				<div id="modelCont">
				<div class="rtemplate-root">
                    <h3 class="main-title">新增运费模板</h3>
                    <form id="submitLogistic" action="${path}/order/addLogisTpl" method="post" >
                    <div class="ViewContainer" style="font-size:14px;">
                        <div class="sui-field sui-form-field">
                            <span style="font-size:13px;">模板名称：</span>
                            <span><input type="text" name="logisticTplName" class="sui-input"></span>
                        </div>
                        <div class="sui-field sui-form-field">
                            <span style="font-size:13px;">宝贝地址：</span>
                            <span>
                                <select class="sui-option" id="provinceId" name="productProvinceid" onchange="getCity()" >
							      <option>请选择</option>
							    </select>
							    <select class="sui-option" id="cityId" name="productCityid" onchange="getCounty()">
							      <option>请选择</option>
							    </select>
							    <select class="sui-option" id="areaId" name="productAreaid">
							      <option>请选择</option>
							    </select>
                            </span>
                        </div>
                        <div class="sui-field sui-form-field">
                            <span style="font-size:13px;">发货时间：</span>
                            <span>
                                <select class="sui-option" name="fahuoShijian">
							      <option value="1">1小时</option>
							      <option value="2">2小时</option>
							      <option value="3">3小时</option>
							    </select>
                            </span>
                        </div>
                        <div class="sui-field sui-form-field">
                            <span style="font-size:13px;">是否包邮：</span>
                            <label><input  type="radio" name="logisticFeeType" value="1" />自定义运费</label>
                            <label><input  type="radio" name="logisticFeeType" value="2" />卖家承担运费</label>
                        </div>
                        <div class="sui-field sui-form-field">
                            <span style="font-size:13px;">计价方式：</span>
                            <label><input  type="radio" name="logisticJijia" value="1"/>按件数</label>
                            <label><input  type="radio" name="logisticJijia" value="2"/>按重量</label>
                            <label><input  type="radio" name="logisticJijia" value="3"/>按体积</label>
                        </div>
                        <div class="sui-modal3" id="center3">
                      <a  class="sui-modal-close3">
                         <span>×</span>
                      </a>
                      <div class="zTreeDemoBackground left">
					<ul id="treeDemo3" class="ztree"></ul>
				</div>
				<!-- 折叠菜单over -->
                   </div>
                       <div class="sui-moda4" id="center4">
                      <a  class="sui-modal-close4">
                         <span>×</span>
                      </a>
                      <div class="zTreeDemoBackground left">
					<ul id="treeDemo4" class="ztree"></ul>
				</div>
				<!-- 折叠菜单over -->
                   </div>
                        <div class="sui-field sui-form-field" id="field">
                            <span style="font-size:13px;">运送方式：</span>
                            <p><input type="radio" id="checkbox-id1" value="1" name="logisticWay" onclick="" />快递</p>
                            <div class="template"  id="template1">
                                <div class="t-title">
                                    <span class="batch-default-title">默认运费</span>
                                    <span><input type="text" name="startNum" value="0">件内</span>
                                    <span><input type="text" name="startPrice" value="0">元，</span>
                                    <span>每增加<input type="text" name="addNum" value="0">件，</span>
                                    <span>增加运费<input type="text" name="addPrice" value="0">元</span>
                                </div>
                                <div class="check-tab">
                                    <div class="check-table1">
	                                    <span style="padding: 2px 53px;">运送到</span>
	                                    <span>首件数（件）</span>
	                                    <span>首费（元）</span>
	                                    <span>续件数（件）</span>
	                                    <span>续费（元）</span>
	                                    <span>操作</span>
                                    </div>
                                    <div class="check-body1">
                                        <span>未添加地区&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="check_place1">编辑</a></span>
	                                    <span><input type="text" name="startNum" value="0"></span>
	                                    <span><input type="text" name="startPrice" value="0"></span>
	                                    <span><input type="text" name="addNum" value="0"></span>
	                                    <span><input type="text" name="addPrice" value="0"></span>
	                                    <span><a href="">删除</a></span>
                                    </div>
                                </div>
                                <div class="o-item1">为指定城市地区设置运费</div>
                            </div>
                            <p><input type="radio" id="checkbox-id2" value="2" name="logisticWay"/>EMS</p>
                            <div class="template"  id="template2">
                                <div class="t-title">
                                    <span class="batch-default-title">默认运费</span>
                                    <span><input type="text" name="startNum"value="0">件内</span>
                                    <span><input type="text" name="startPrice"value="0">元，</span>
                                    <span>每增加<input type="text" name="addNum"value="0">件，</span>
                                    <span>增加运费<input type="text" name="addPrice"value="0">元</span>
                                </div>
                                <div class="check-tab">
                                    <div class="check-table2">
	                                    <span style="padding: 2px 53px;">运送到</span>
	                                    <span>首件数（件）</span>
	                                    <span>首费（元）</span>
	                                    <span>续件数（件）</span>
	                                    <span>续费（元）</span>
	                                    <span>操作</span>
                                    </div>
                                    <div class="check-body2">
                                        <span>未添加地区&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="check_place1">编辑</a></span>
	                                    <span><input type="text" name="startNum"value="0"></span>
	                                    <span><input type="text" name="startPrice"value="0"></span>
	                                    <span><input type="text" name="addNum"value="0"></span>
	                                    <span><input type="text" name="addPrice"value="0"></span>
	                                    <span><a href="">删除</a></span>
                                    </div>
                                </div>
                                <div class="o-item2">为指定城市地区设置运费</div>
                            </div>
                            <div id="hiddenBox2" style="display: none;"></div>
                            <div id="hiddenBox" style="display: none;"></div>
                            <div id="hiddenBox3" style="display: none;"></div>
                            <div id="hiddenBox4" style="display: none;"></div>
                            <p><input type="radio" id="checkbox-id3" value="3" name="logisticWay"/>平邮</p>
                            <div class="template"  id="template3">
                                <div class="t-title">
                                    <span class="batch-default-title">默认运费</span>
                                    <span><input type="text" name="startNum"value="0">件内</span>
                                    <span><input type="text" name="startPrice"value="0">元，</span>
                                    <span>每增加<input type="text" name="addNum"value="0">件，</span>
                                    <span>增加运费<input type="text" name="addPrice"value="0">元</span>
                                </div>
                                <div class="check-tab">
                                    <div class="check-table3">
	                                    <span style="padding: 2px 53px;">运送到</span>
	                                    <span>首件数（件）</span>
	                                    <span>首费（元）</span>
	                                    <span>续件数（件）</span>
	                                    <span>续费（元）</span>
	                                    <span>操作</span>
                                    </div>
                                    <div class="check-body3">
                                        <span>未添加地区&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="check_place1">编辑</a></span>
	                                    <span><input type="text" name="startNum"value="0"></span>
	                                    <span><input type="text" name="startPrice"value="0"></span>
	                                    <span><input type="text" name="addNum"value="0"></span>
	                                    <span><input type="text" name="addPrice"value="0"></span>
	                                    <span><a href="">删除</a></span>
                                    </div>
                                </div>
                                <div class="o-item3">为指定城市地区设置运费</div>
                            </div>
                        </div>
                        <div class="p-t">
                            <label class="p-label">
                               <input type="checkbox" name="setfee">
                               <span style="font-size:13px;"> </span>
                            </label>
                            <div class="yoyo-template promotion-template">
                               <table class="t-table sui-table">
                                    <thead class="sui-table-thead">
                                        <tr>
                                            <th>选择地区</th>
                                            <th>选择运送方式</th>
                                            <th>设置包邮条件</th>
                                        </tr>
                                    </thead>
                                    <tbody class="sui-table-tbody">
                                        <tr>
                                            <td style="width:20%">
                                               <div class="b-text mui-flex align-center">
                                                    <div class="text-show">
                                                        <span>未添加地区</span>
                                                        <div class="operate">编辑</div>
                                                    </div>
                                               </div>
                                            </td>
                                            <td style="text-align:center">
                                                <select class="sui-option">
											      <option value="1">快递</option>
											      <option value="2">EMS</option>
											      <option value="3">平邮</option>
											    </select>
                                            </td>
                                            <td style="text-align:center">
                                                <select class="sui-option">
											      <option value="1">件数</option>
											      <option value="2">金额</option>
											      <option value="3">件数+金额</option>
											    </select>
											    <div class="select-val1">
                                                     <input type="text" name="nonefeeNum">
											    </div>
											    <div class="select-val2">
                                                     <input type="text" name="nonefeeNum">+<input type="text" name="nonefeePrice">
											    </div>
                                            </td>                                                                                          
                                        </tr>

                                    </tbody>
                               </table>
                               <div class="o-item4">为指定城市地区设置运费</div>
                            </div>
                        </div>
                    </div>
                    
                    <button type="button" onclick="submitForm()" class="b2 f_l">确定</button>
                    <div class="no_btn">取消</div>
				</div>
                   <div class="sui-modal" id="center" >
                      <a  class="sui-modal-close">
                         <span>×</span>
                      </a>
                       <div class="zTreeDemoBackground left">
					<ul id="treeDemo" class="ztree"></ul>
				</div>
				<!-- 折叠菜单over -->
                   </div>
                   
                    </form>
				<!-- 折叠菜单over -->
                   
                    
                

		<div class="blank10"></div>
    </div>
<!-- 右边 end -->
			    
			</div>	
		</div>
		<!-- 底部 start -->
     	<%@include file="/WEB-INF/views/zh/include/last.jsp"%>
	<!-- 底部 end -->
	
	
   </body>
   <script type="text/javascript" src="${path}/js/brand/brand.js"></script>
   <script type="text/javascript" src="${path}/js/brand/logist.js"></script>
   <script type="text/javascript" src="${path}/js/address/address.js"></script>
   <script type="text/javascript">
   			
   $(document).ready(function(){
	   /* clickSubmit(1); */
   });
   
	function clickSubmit(page){
		
		var brandArray = new Array();
		
		if(page!=undefined && "" != page){
			brandArray.push('page='+page);
		}
		
		$.ajax({
			url : '${path}'+"/brand/getBrandModel",
			type : 'POST',
			data : brandArray.join('&')+'&math='+Math.random(),
			success: function (msg) {
				 $('#modelCont').html(msg);
			}, 
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
				alert("服务器忙，请稍后再试!"); 
			} 
		});
	}

	function editBrand(){
		window.location.href="../brand/toAddUI";
	}
	function  manage(id,name){
		$("#brandId").val(id);
		$("#brandName").val(name);
		
		$.ajax( {
			url : '${path}'+"/brand/getSubSupplier",
			type : 'POST',
			timeout : 30000,		
			success: function (msg) {
				$("#subSupplier").html("");
				$.each(eval(msg),function(i,n){
					$("#subSupplier").append("<option value='"+n.supplierId+"'>"+n.name+"</option>");
				});	  
				$('#editDiv').toggle();
			}, 
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
				alert("服务器忙，请稍后再试!"); 
			} 
		});
		
	}
	
	$("#brandEdit").click(function(){
		var brandId = $("#brandId").val();
		var subSupplier = $("#subSupplier").val();
		
		$.ajax( {
			url : '${path}'+"/brand/bind",
			type : 'POST',
			data : "brandId="+brandId+"&subSupplier="+subSupplier,
			timeout : 30000,		
			success: function (data) {
				if(data==1){ 
					tipMessage("操作成功!",function(){
						location.reload();
					}); 
				}else{ 
					tipMessage("操作失败",function(){
						location.reload();
					});
				}  
			}, 
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
				alert("服务器忙，请稍后再试!"); 
			} 
		});
		
		
	});
	
	function  delBrand(id){
		$.dialog.confirm('确定要删除此品牌?', function(){
			$.ajax( {
				url : '${path}'+"/brand/delete?id="+id,
				type : 'POST',
				timeout : 30000,		
				success: function (data) {
					if(data.status==0){ 
						tipMessage("操作失败!",function(){
							location.reload();
						}); 
					}else{ 
						tipMessage("操作成功",function(){
							location.reload();
						});
					}  
				}, 
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
					alert("服务器忙，请稍后再试!"); 
				} 
			}, function(){
			   // $.dialog.tips('执行取消操作');
			});
	   	});
	}
	function  deleteAll(){
		if($(":checkbox[name='nn'][checked]").length<=0){
			alert("Please select a item to delete!");
		}else{
			$.dialog.confirm('Are you sure to delete selected items?', function(){
				   //$("#deleteForm").submit();
			        ajaxSubmit("#deleteForm");	
			}, function(){
				   // $.dialog.tips('执行取消操作');
				});
	    	}
		}
	
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
		    }
		};
		function checkSelect(){
			
			
		     var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
             var nodes = treeObj.getCheckedNodes();
             $(nodes).each(function(){
            	 //treeNode.chkDisabled 
		        var hiddenString = '<input type="hidden" name="menus" value="'+this.id+'"/>';
                $("#hiddenBox").append(hiddenString);
		     });
             
             
		}
		function checkSelect2(){
			
			
		     var treeObj = $.fn.zTree.getZTreeObj("treeDemo2");
            var nodes = treeObj.getCheckedNodes();
            $(nodes).each(function(){
           	 //treeNode.chkDisabled 
		        var hiddenString = '<input type="hidden" name="menus2" value="'+this.id+'"/>';
               $("#hiddenBox2").append(hiddenString);
		     });
            
            
		}
		function checkSelect3(){
			
			
		     var treeObj = $.fn.zTree.getZTreeObj("treeDemo3");
           var nodes = treeObj.getCheckedNodes();
           $(nodes).each(function(){
          	 //treeNode.chkDisabled 
		        var hiddenString = '<input type="hidden" name="menus3" value="'+this.id+'"/>';
              $("#hiddenBox3").append(hiddenString);
		     });
           
           
		}
		function checkSelect4(){
			
			
		     var treeObj = $.fn.zTree.getZTreeObj("treeDemo4");
          var nodes = treeObj.getCheckedNodes();
          $(nodes).each(function(){
         	 //treeNode.chkDisabled 
		        var hiddenString = '<input type="hidden" name="menus4" value="'+this.id+'"/>';
             $("#hiddenBox4").append(hiddenString);
		     });
          
          
		}
		function submitForm(){
			
				checkSelect();
				checkSelect2();
				checkSelect3();
				checkSelect4();
				//$("#updateRoleMenu").submit();
				ajaxSubmit("#submitLogistic");
			
			
		}
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, ${tree});
			$.fn.zTree.getZTreeObj("treeDemo").expandAll(false);
			$.fn.zTree.init($("#treeDemo2"), setting, ${tree});
			$.fn.zTree.getZTreeObj("treeDemo2").expandAll(false);
			$.fn.zTree.init($("#treeDemo3"), setting, ${tree});
			$.fn.zTree.getZTreeObj("treeDemo3").expandAll(false);
		});
	 </script> 
</html>