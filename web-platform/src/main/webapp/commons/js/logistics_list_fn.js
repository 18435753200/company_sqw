var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(
		function() {
			$('.bt2').live('click', closebox);
			$('.b_colse').live('click', closebox);
			$('#createTemplate').click(
					function() {
						var providerId = $("#providerId").val();
						if (providerId) {
							window.open(CONTEXTPATH+ "/providerlogistics/toCreateTemplate?providerId="+ providerId,"_blank");
						} else {
							alert("物流商编码异常!");
						}

					});
			/* 加载物流商 */
			getProvider();
			clickSubmit(1);
			$("#providerId").click(function(){
				clickSubmit(1);
			});
			
			

		});

/* 查询条件 */
// page 页数
function clickSubmit(page) {
	var providerId = $("#providerId").val();
	var pro_array = new Array();
	if(providerId==null){
		pro_array.push("providerId=" + 0);
	}else{
		pro_array.push("providerId=" + providerId);
	}
	
	if (page != "" && page != undefined) {
		pro_array.push("page=" + page);
	}

	$.ajax({
		async : false,
		type : "post",
		url : CONTEXTPATH + "/providerlogistics/getTemplate",
		data : pro_array.join("&"),
		dataType : "html",
		success : function(msg) {
			$(".logistics-bd").html(msg);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("对不起，数据异常请稍后再试!");
		}
	});

}

/**
 * 创建物流商
 */
function createProvder() {
	$(".alert_Provider").show(50);
}

function createProvider() {
	var providerName = $("#providerName").val();
	if (providerName.length > 20 || providerName.length < 1) {
		alert("物流商民称必填，并且不能大于20字！")
		$("#providerName").val("");
		return false;
	}
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/providerlogistics/createProvider",
		data : "providerName=" + providerName,
		success : function(msg) {
			if (msg == 1) {
				tipMessage("保存成功！", function() {
					window.location.href = "../providerlogistics/getLogisticsPage";
				});
			}else if(msg == 2){
				tipMessage("物流商名称已存在，保存失败！", function() {

				});
			} else {
				tipMessage("数据保存异常！", function() {

				});
			}
			$(".alert_Provider").hide(50);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("保存失败 ，请稍后再试。。");
		}

	});
}

/**
 * 获取物流商列表
 */
function getProvider() {
	$("#providerId").empty();
	$.ajax({
		async : false,
		type : "post",
		url : CONTEXTPATH + "/providerlogistics/getPrivider",
		success : function(msg) {
			
			$.each(eval(msg), function(i, n) {
				$("#providerId").append(
						"<option value='" + n.providerId + "'>"
								+ n.providerName + "</option>");
			});
			
		}
	});
}



function updateTempStatus(tempId, status) {
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/providerlogistics/updateStatus",
		data : "logisticTempId=" + tempId + "&status=" + status,
		success : function(msg) {
			if (msg == "success") {
				tipMessage("修改成功！", function() {
					window.location.href = "../providerlogistics/getLogisticsPage";
				});
			} else if (msg == "error") {
				tipMessage("修改失败，请检查后重试！",
						function() {
						});
			} else {
				tipMessage("修改失败,原因：" + msg,function() {
						});
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("保存失败 ，请稍后再试。。");
		}

	});
}



var MouseEvent = function(e) {
	this.x = e.pageX;
	this.y = e.pageY;
}
var Mouse = function(e) {
	var kdheight = $(document).scrollTop();
	mouse = new MouseEvent(e);
	leftpos = mouse.x + 10;
	toppos = mouse.y - kdheight + 10;
}

function closebox() {
	$(".alert_Provider").hide(100);
}

function downListExcel(){
    var providerId = $("#providerId").val();
    var pro_array = new Array();
    if(providerId==null){
        pro_array.push("providerId=" + 0);
    }else{
        pro_array.push("providerId=" + providerId);
    }
    
    window.open(CONTEXTPATH+"/providerlogistics/downLoadExcel?"+pro_array.join("&"),"_blank");
}
