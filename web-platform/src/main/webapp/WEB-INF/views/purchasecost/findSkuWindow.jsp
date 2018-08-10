<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>费用分摊出库</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/commons/css/reset.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/commons/css/Share.css">
</head>
<script type="text/javascript">
	 $(document).ready(function(){
			var opetion = $('#sto .sho');
			for ( var i = 0; i < opetion.length; i++) {
				opetion[i].getElementsByTagName('input')[6].disabled ="true";
			}
 	});
 
	function AllCheck() {
		var obj = document.getElementsByName('outStockBox');
		for ( var i = 0; i < obj.length; i++) {
			obj[i].checked = true;
		}
	}

	function unAllCheck() {
		var obj = document.getElementsByName('outStockBox');
		for ( var i = 0; i < obj.length; i++) {
			obj[i].checked = false;
		}
	}

	function allOcation() {
		var flag = false;
		var opetion = $('#sto .sho');
		for ( var i = 0; i < opetion.length; i++) {
			var op1 = opetion[i].getElementsByTagName('input')[0];
			var op7 = opetion[i].getElementsByTagName('input')[6];
			
			if (op1.checked == true) {
				flag = true;
			};
			
			op7.value="";
			op7.disabled ="";
			//op7.readonly="true";
			op7.setAttribute("readOnly",'true');
		}
		
		if (!flag) {
			alert('请选择记录');
			return false;
		}
		
		var obj = document.getElementsByName('outStockBox'); //选择所有name="'test'"的对象，返回数组 
		//取到对象数组后，我们来循环检测它是不是被选中 
		var s = '';
		for ( var i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				s += obj[i].value + ':';
			}/*else{
				alert("请选择记录");
				return false;
			}*/
			//如果选中，将value添加到变量s中 
		}
		s = s.substring(0, s.length - 1);
		
		$.ajax({
			type : "post",
			url : "${path}/purchasecost/setallOcation",
			dataType : "json",
			//data : "values=" + s,
			data : {"values" : s},
			success : function(data) {
				if(data.success){
					var liverData = data.dataMap.dataMap;
					$.each(liverData, function(i,v){
	            			var opetion = $('#sto .sho');
	            			for ( var i = 0; i < opetion.length; i++) {
								var op7 = opetion[i].getElementsByTagName('input')[6];
								var op8 = opetion[i].getElementsByTagName('input')[7];
								
								if(op8.value ==v.skuId){
									op7.value=v.sharedFee;
									op7.disabled ="";
									op7.setAttribute("readOnly",'true');
								}
							}
	            	});
				}
			},
			error:function(json){
	            alert("处理异常");
	        }
		});
	}

	function selectName(){
		var obj = document.getElementById("name");
		if(obj.checked){
			var opetion = $('#sto .sho');
			for ( var i = 0; i < opetion.length; i++) {
				opetion[i].getElementsByTagName('input')[6].disabled ="";
				var op7 = opetion[i].getElementsByTagName('input')[6];
				op7.removeAttribute("readOnly");
				op7.value="";
			}
		}else{
			var opetion = $('#sto .sho');
			for ( var i = 0; i < opetion.length; i++) {
				opetion[i].getElementsByTagName('input')[6].disabled ="true";
				var op7 = opetion[i].getElementsByTagName('input')[6];
				op7.setAttribute("readOnly",'true');
				op7.value="";
			}
		}
	}
	
	function saveComp(){
		var chkTrue = "";
		$("input[type='checkbox'][name='outStockBox']").each(function() {
			if (this.checked) {
				chkTrue = chkTrue + "1,";
			} else {
				chkTrue = chkTrue + "0,";
			}
		});
	
		chkTrue = chkTrue.substring(0, chkTrue.length - 1);
		if (chkTrue != "") {
			$("#chkTrue").val(chkTrue);
		}
	
		var s ="";
		var flag = false;
		var opetion = $('#sto .sho');
		for ( var i = 0; i < opetion.length; i++) {
			var op1 = opetion[i].getElementsByTagName('input')[0];
			var op7 = opetion[i].getElementsByTagName('input')[6];
			var reg=/^\d+(\.\d+)?$/;
			
			if (op1.checked == true && (op7.value == 0  || !op7.value.match(reg))) {
				alert("分摊金额不能为0且必须数字");
				return false;
			}
			
			if (op1.checked == true) {
				flag = true;
			}
			
			if (op1.checked == true && op7.value != 0) {
				s += op7.value+",";
			}
		}
	
		if (!flag) {
			alert("请选择需要保存的记录");
			return false;
		}
		
		var fm_data = $('#skuAllocationForm').serialize();
		$.ajax({
			type : "post",
			url : "${path}/purchasecost/saveSkuAllocation",
			data : fm_data + "&random=" + Math.random() +"&money="+s,
			dataType : "json",
			success : function(data) {
				if(data.success){
					alert(data.message);
					//window.location.href = "${path}/purchasecost/findPurchaseCost";
					window.close();
				}else{
					alert(data.message);
				};
			},
			error:function(json){
	            alert("处理异常");
	        }
		});
	}
</script>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<div class="center">
		<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		<div class="right">

			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>采购费用&nbsp;&gt;&nbsp;</p>
					<p class="c1">费用分摊出库</p>
				</div>
			</div>
			<form id="skuAllocationForm">
				<!-- 提交判断是否选中 -->
				<input type="hidden" id="chkTrue" name="chkTrue">
					
				<div class="xia">
					<p class="p1">
						<span>费用名称：</span> <input type="text" name="costName"
							id="costName" value="${Cost }"><i></i> <input
							type="hidden" name="costCode" id="costCode" value="${costCode }">
						<span>分摊方式：</span> <input type="text" name="allocationName"
							id="allocationName" value="${Dictionarys }"> <input
							type="hidden" name="allocationCode" id="allocationCode"
							value="${allocationCode }">
					</p>
					<p class="p1">
						<span>费用总金额：</span> <input type="text" name="totalFee"
							id="totalFee" value="${totalFee }"><i> 元</i> <span>税率：</span>
						<input type="text" class="w50" name="tax" id="tax" value="${tax }"><i>
							%</i> <span>本币金额：</span> <input type="text" name="localPrice"
							id="localPrice" value="${localPrice }"><i> 元</i>
					</p>
				</div>

				<div class="pu_wrap">
					<div class="pu_hd">
						<h3>商品明细</h3>
						<div class="btn">
							<a href="javascript:void(0)" onclick="AllCheck();">全选</a> 
							<a href="javascript:void(0)" onclick="unAllCheck();">反选</a> 
							<a href="javascript:void(0)" onclick="allOcation();">计算分摊</a> 
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							是否手工分摊：<input type="checkbox" id="name" name="name"  onclick="selectName()"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0)" onclick="saveComp();">保存</a> 
						</div>
					</div>

					<div class="pu_bd">
						<table id="sto">
							<tr class="order_hd">
								<th width="40px;">序号</th>
								<th width="50px;">选择</th>
								<th width="120px;">采购订单号</th>
								<th width="120px;">商品编码</th>
								<th width="120px;">商品名称</th>
								<th width="80px;">数量</th>
								<th width="80px;">金额</th>
								<th width="100px;">分摊金额</th>
								<th width="70px;" style="display:none">skuId</th>
								<th width="70px;" style="display:none">采购单号</th>
							</tr>
							<c:forEach items="${itemDTOs }" var="result" varStatus="status">
								<tr class="sho">
									<td>${status.index+1 }</td>
									<td><input type="checkbox" name="outStockBox"
										value="${result.skuId },${result.skuPrice},${result.subtotalPrice},${localPrice },${allocationCode },${result.qty }">
									</td>
									<td><input type="text" id="businessNumberChar${status.index + 1 }" name="businessNumberChar" value="${result.businessNumberChar }"  readonly='readonly'  style="width:120px;"></input></td> 
									<td><input type="text" id="pcode${status.index + 1 }" name="pcode" value="${result.businessProdid }"  readonly='readonly'  style="width:120px;"></input></td>
									<td><input type="text" id="pname${status.index + 1 }" name="pname" value="${result.pname }"  readonly='readonly'  style="width:120px;"></input></td>
									<td><input type="text" id="qty${status.index + 1 }" name="qty" value="${result.qty }"  readonly='readonly' style="width:80px;"></input></td>
									<td><input type="text" id="price${status.index + 1 }" name="price" value="${result.subtotalPrice }"  readonly='readonly' style="width:80px;"></input></td>
									<td><input type="text" name="allocationPrice" id="allocationPrice${status.index+1 }" style="border:0px; width:200px;"  />
									</td>
									<td style='display:none'><input type="text" id="skuId${status.index + 1 }" name="skuId" value="${result.skuId}"  readonly='readonly'></input></td>
									<td style='display:none'><input type="text" id="purchaseId${status.index + 1 }" name="purchaseId" value="${result.purchaseId}"  readonly='readonly'></input></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>

			</form>

		</div>
	</div>
</body>
</html>