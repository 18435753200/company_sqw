
var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {
	//加载商品分页信息
	clickSubmit(1);
	$(".close-box").live('click',closeBox);
});

function toAddActivity() {
	window.location = CONTEXTPATH + "/activitis/toAddActivity";
}

function saveActivity() {
	var activeName = $("#activeName").val();
	
	window.location = CONTEXTPATH + "/activitis/saveActivity?" + "activeName=" + activeName;
}


/* 查询条件 */
//page 页数
function clickSubmit(page) {
	var activityName = $.trim($("#activityName").val());
	var createBy = $.trim($("#createBy").val());
	var expiringFrom = $("#expiringFrom").val();
	console.log(expiringFrom);
	var expiringTo = $("#expiringTo").val();
	console.log(expiringTo);
	var pro_array = new Array();
	if (activityName != "") {
		pro_array.push("activityName=" + activityName);
	}
	if (createBy != "") {
		pro_array.push("createBy=" + createBy);
	}
	if (expiringFrom != "" && expiringFrom != null) {
		pro_array.push("expiringFrom=" + expiringFrom);
	}
	if (expiringTo != "" && expiringTo != null) {
		pro_array.push("expiringTo=" + expiringTo);
	}
	if (page != "" && page != undefined) {
		pro_array.push("page=" + page);
	}
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/activitis/searchActivityList",
		data : pro_array.join("&"),
		dataType : "html",
		success : function(msg) {
			$(".promotion-bd").html(msg);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("对不起，数据异常请稍后再试!");
		}
		
	});

}

function findActivityList(){
	var page = $("#page").val();
	clickSubmit(1);
}

function create(){
	window.location=CONTEXTPATH + "/activitis/toAddActivity";
}

function stopOrEnableRule(ruleId, status) {
	console.log(1111);
	var pro_array = new Array();
	if (ruleId != "") {
		pro_array.push("ruleId=" + ruleId);
	}
	
	pro_array.push("status="+status);
		
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/activitis/modifyStatusOfRule",
		data : pro_array.join("&"),

		success : function(data) {
			if (data == 'success') {
				tipMessage("修改成功，将立即返回到规则列表！", function() {
					window.location.href = "../activitis/toActivityList";
				});
			} else if (data == 'error') {
				tipMessage("修改失败，请稍后重试！", function() {
				});
			}else if (data == 'timeout') {
				tipMessage("该规则时间已经结束！", function() {
				});
			}else {
				window.location.href = "../activitis/toActivityList";
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("对不起，请稍后再试!");
		}

	});
}

function viewRule(ruleId){
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/activitis/viewRule",
		data : "ruleId="+ruleId,
		dataType:"json",
		success : function(data) {
			var sendtype = data[0].activeProductRule.sendtype;
			var sendTypeName = "";
			if(sendtype == 1){
				sendTypeName = "(按数量)";
			}else{
				sendTypeName = "(按金额)";
			}
			//展示访问方式
			$("#accessModes").html("");
			var accessModes = data[0].accessMode;
			var $li = $("<tr class='order_hd'><th width='100px;'>访问方式名称</th><th width='100px;'>是否参加活动</th></tr>");
			$("#accessModes").append($li);
			for(var obj in accessModes){
				var accessmodeid = accessModes[obj].accessmodeid;
				var isExclude = accessModes[obj].isExclude;
				
				var name = "";
				var name1 = "";
				if(isExclude == 0){
					name1 = "参加";
				}else{
					name1 = "不参加";
				}
				if(accessmodeid == -1){
					name = "所有访问方式";
					name1 = "参加";
				}else if(accessmodeid == 0){
					name = "PAD";
				}else if(accessmodeid == 1){
					name = "PC";
				}else if(accessmodeid == 2){
					name = "ANDROID";
				}else if(accessmodeid == 3){
					name = "WAP";
				}else if(accessmodeid == 4){
					name = "IOS";
				}else if(accessmodeid == 5){
					name = "其他";
				}
				$li = $("<tr><td>"+name+"</td><td>"+name1+"</td></tr>");
				$("#accessModes").append($li);
			}
			//展示会员信息
			$("#userLevel").html("");
			var userLevel = data[0].activeProductRule.isB2c;
			if(userLevel == 3){
				$li = $("<span>B2B会员参加活动</span>");
			}else{
				$li = $("<span>B2C会员参加活动</span>");
			}
			$("#userLevel").append($li);
			//展示渠道信息
			$("#channels").html("");
			var channels = data[0].channels;
			$li = $("<tr class='order_hd'><th width='100px;'>渠道名称</th><th width='100px;'>是否参加活动</th></tr>");
			$("#channels").append($li);
			for(var obj in channels){
				var name = "";
				var chid = channels[obj].chid;
				if(chid == -1){
					name = "所有渠道";
				}else{
					name = channels[obj].chname;
				}
				var name1 = "";
				var isExclude = channels[obj].isExclude;
				if(isExclude == 0){
					name1 = "参加";
				}else{
					name1 = "不参加";
				}
				$li = $("<tr><td>"+name+"</td><td>"+name1+"</td></tr>");
				$("#channels").append($li);
			}
			//展示活动商品信息
			$("#conditionDetail").html("");
			var conditionDetail = data[0].conditionDetail;
			$li = $("<tr class='order_hd' id='tr1'><th width='300px;'>活动品类/品牌/单品类型</th><th width='300px;'>活动品类/品牌/单品ID</th><th width='270px;'>是否参与活动</th><th width='270px;'>商品发货途径</th></tr>");
			$("#conditionDetail").append($li);
			var supply = data[0].activeProductRule.supply;
			for(var obj in conditionDetail){
				var type = conditionDetail[obj].contentType;
				var typeName = "";
				if(type == 1){
					typeName = "品类";
				}else if(type == 2){
					typeName = "品牌";
				}else if(type == 3){
					typeName = "单品";
				}else{
					typeName = "全场";
				}
				var isExclude = conditionDetail[obj].isExclude;
				var isExcludeName = "";
				if(isExclude == 0){
					isExcludeName = "参加";
				}else{
					isExcludeName = "不参加";
				}
				if(supply == 1){
					$li = $("<tr><td>"+typeName+"</td><td>"+conditionDetail[obj].conditionContent+"</td><td>"+isExcludeName+"</td><td>国内发货</td></tr>");
				}else if(supply == 12){
					$li = $("<tr><td>"+typeName+"</td><td>"+conditionDetail[obj].conditionContent+"</td><td>"+isExcludeName+"</td><td>保税区发货</td></tr>");
				}
				$("#conditionDetail").append($li);
			}
			
			//展示订单条件
			$("#orderCondition").html("");
			var ruleTerm = data[0].activeProductRule.ruleTerm;
			var details = data[0].details;
			
			for(var obj in details){
				var $li;
				if(ruleTerm == 21||ruleTerm == 23||ruleTerm == 25||ruleTerm == 29||ruleTerm == 30){//普通和等比
					var bei = details[obj].term1;
					if(null == bei || "" == bei){
						$li = $("<span>订单金额/数量大于等于<input type='text' readonly='readonly' class='te' value='"+details[obj].term0+"'>元</span>");
					}else{
						$li = $("<span>订单金额/数量大于等于<input type='text' readonly='readonly' class='te' value='"+details[obj].term0+"'>元,且按<input type='text' class='te' readonly='readonly' value='"+details[obj].term1+"'>的整数倍</span>");
					}
					$("#orderCondition").append($li);
				}else if(ruleTerm == 27||ruleTerm == 28){//直降
					//限时直降没有订单条件
				}else if(ruleTerm == 26){//买赠
					
				}
				
			}
			
			//展示执行形式
			var gifts = data[0].gifts;
			if(ruleTerm == 21){
				$("#exec").html("");
				$("#exec1").html("");
				$("#exec2").html("");
				for(var obj in details){
					var $li = $("<span>订单总金额减少<input type='text' class='te' readonly='readonly' value='"+details[obj].term2+"'>元</span>");
					$("#exec").append($li);
				}
			}else if(ruleTerm == 20){
				$("#exec").html("");
				$("#exec1").html("");
				$("#exec2").html("");
				var $li = ("<tr class='order_hd'><th width='150px;'>起点金额/数量</th><th width='150px;'>结点金额/数量</th><th width='150px;'>赠品名称</th><th width='150px;'>赠品数量</th>"
				+"<th width='240px;'>活动类型(按金额/按数量)</th></tr>");
				$("#exec2").append($li);
				for(var obj in details){
					var $li = $("<tr><td>"+details[obj].term0+"</td><td>"+details[obj].term1+"</td><td>"+details[obj].term2+"元</td><td>1</td><td>满减"+sendTypeName+"</td></tr>");
					$("#exec2").append($li);
				}
			}else if(ruleTerm == 22){
				$("#exec").html("");
				$("#exec1").html("");
				$("#exec2").html("");
				var $li = ("<tr class='order_hd'><th width='150px;'>起点金额/数量</th><th width='150px;'>结点金额/数量</th><th width='150px;'>赠品名称</th><th width='150px;'>赠品数量</th>"
				+"<th width='240px;'>活动类型(按金额/按数量)</th></tr>");
				$("#exec2").append($li);
				for(var obj in details){
					var $li = $("<tr><td>"+details[obj].term0+"</td><td>"+details[obj].term1+"</td><td>"+details[obj].pName+"</td><td>"+details[obj].giftQty+"</td><td>满返"+sendTypeName+"</td></tr>");
					$("#exec2").append($li);
				}
			}else if(ruleTerm == 24){
				$("#exec").html("");
				$("#exec1").html("");
				$("#exec2").html("");
				var $li = ("<tr class='order_hd'><th width='150px;'>起点金额/数量</th><th width='150px;'>结点金额/数量</th><th width='150px;'>赠品名称</th><th width='150px;'>赠品数量</th>"
				+"<th width='240px;'>活动类型(按金额/按数量)</th></tr>");
				$("#exec2").append($li);
				for(var obj in details){
					var $li = $("<tr><td>"+details[obj].term0+"</td><td>"+details[obj].term1+"</td><td>"+details[obj].pName+":"+details[obj].giftName+"(券规则ID)</td><td>"+details[obj].giftQty+"</td><td>满赠"+sendTypeName+"</td></tr>");
					$("#exec2").append($li);
				}
			}else if(ruleTerm == 23 || ruleTerm == 25 || ruleTerm == 29 || ruleTerm == 30){
				$("#exec").html("");
				$("#exec1").html("");
				$("#exec2").html("");
				var $li = ("<tr class='order_hd'><th width='270px;'>赠品名称</th><th width='300px;'>赠品数量</th>"+
								"<th width='300px;'>活动类型(按金额/按数量)</th></tr>");
				$("#exec1").append($li);
				for(var obj in gifts){
					var name = "";
					if(ruleTerm == 23 || ruleTerm == 30){
						name = "满返" + sendTypeName;
					}else{
						name = "满赠" + sendTypeName;
					}
					var $li = $("<tr><td>"+gifts[obj].pName+":"+gifts[obj].giftName+"</td><td>"+gifts[obj].giftQty+"</td><td>"+name+"</td></tr>");
					$("#exec1").append($li);
				}
			}else if(ruleTerm == 27){
				$("#exec").html("");
				$("#exec1").html("");
				$("#exec2").html("");
				for(var obj in details){
					var $li = $("<span>单品销售单价减<input type='text' class='te' readonly='readonly' value='"+details[obj].term0+"'>元,限购数量<input type='text' class='te' readonly='readonly' value='"+details[obj].term1+"'>个</span>");
					$("#exec").append($li);
				}
			}else if(ruleTerm == 28){
				$("#exec").html("");
				$("#exec1").html("");
				$("#exec2").html("");
				for(var obj in details){
					var $li = $("<span>单品销售单价减<input type='text' class='te' readonly='readonly' value='"+details[obj].term0+"'>%(百分比),限购数量<input type='text' class='te' readonly='readonly' value='"+details[obj].term1+"'>个</span>");
					$("#exec").append($li);
				}
			}
				
			$("#goout-box").css("display","block");
		}
	});
}

var closeBox = function(){
	$("#goout-box").fadeOut();
};