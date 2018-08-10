var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {
	/* 加载运费模板 */
	clickSubmit(1);
	$('#createTemplate').click(function() {
		window.open(CONTEXTPATH+ "/platformlogist/toCreateTemplate","_blank");
	});
	getProvince();
});

function getProvince() {
	$.ajax({
		async : false,
		type : "post",
		url : CONTEXTPATH + "/platformlogist/getProvince",
		success : function(msg) {
			$.each(eval(msg), function(i, n) {
				$("#provinceStartId").append(
						"<option value='" + n.provinceid + "'>"
								+ n.provincename + "</option>");
				$("#provinceEndId").append(
						"<option value='" + n.provinceid + "'>"
								+ n.provincename + "</option>");
			});
		}
	});
}

/* 查询条件 */
// page 页数
function clickSubmit(page) {
	var logisticTempName = $("#logisticTempName").val();
	var provinceStartId=$("#provinceStartId").val();
	var provinceEndId=$("#provinceEndId").val();
	var pro_array = new Array();
	pro_array.push("provinceStartId=" + provinceStartId);
	pro_array.push("provinceEndId=" + provinceEndId);
	if (logisticTempName != "") {
		pro_array.push("logisticTempName=" + logisticTempName);
	}
	if (page != "" && page != undefined) {
		pro_array.push("page=" + page);
	}
	$.ajax({
		async : false,
		type : "post",
		url : CONTEXTPATH + "/platformlogist/getTemplate",
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
 * 修改模板状态
 * @param tempId
 * @param status
 */
function updateTempStatus(tempId, status) {
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/platformlogist/updateStatus",
		data : "logisticTempId=" + tempId + "&status=" + status,
		success : function(msg) {
			if (msg == "success") {
				tipMessage("修改成功！", function() {
					window.location.href = "../platformlogist/getLogisticsPage";
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

function downListExcel(){
    var logisticTempName = $("#logisticTempName").val();
    var provinceStartId=$("#provinceStartId").val();
    var provinceEndId=$("#provinceEndId").val();
    var pro_array = new Array();
    pro_array.push("provinceStartId=" + provinceStartId);
    pro_array.push("provinceEndId=" + provinceEndId);
    if (logisticTempName != "") {
        pro_array.push("logisticTempName=" + logisticTempName);
    } 
    window.open(CONTEXTPATH+"/platformlogist/downLoadExcel?"+pro_array.join("&"),"_blank");
}