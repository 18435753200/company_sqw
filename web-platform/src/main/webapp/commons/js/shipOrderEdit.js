var CONTEXTPATH = $("#conPath").val();

function clickSubmit(page) {
		var businessTypes = $("#businessTypes").val();
		$("#businessType").val(businessTypes);
	
		var fm_data = $('#queryInfo').serialize();

		if (page != undefined) {
			fm_data += "&page=" + page;
		}

		$.ajax({
			type : "post",
			url : CONTEXTPATH+"/shiporder/selectShipOrderAllInfo",
			data : fm_data + "&random=" + Math.random(),
			dataType : "html",
			success : function(msg) {
				$("#cbAllocatePrdList").html(msg);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("查询失败 ，请稍后再试。。");
			}
		});
	}