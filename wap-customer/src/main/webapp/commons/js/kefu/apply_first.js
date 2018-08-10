$(function() {
	// 增
	$("#J-amount-up").click(function() {
		var that = $(this);
		var amountinput = $('#J-amount'), num = amountinput.val();
		// 获取当前商品的购买数量
		var stock = Number($('#applyNum_hidden').val());
		// 判断为数字
		if (regint(num)) {
			num = Number(num);
			if (stock <= 0) {
				return false;
			}
			if (num + 1 > stock) {
				amountinput.val(stock);
			} else {
				amountinput.val(num + 1);
			}
		} else {
			amountinput.val(1);
		}

	})

	// 减
	$("#J-amount-down").click(function() {
		var that = $(this);
		var amountinput = $('#J-amount'), num = amountinput.val();
		// 获取当前商品的购买数量
		var stock = Number($('#applyNum_hidden').val());
		// 判断为数字
		if (regint(num)) {
			num = Number(num);
			if (num - 1 <= 0) {
				amountinput.val(1);
			} else {
				amountinput.val(num - 1);
			}
		} else {
			amountinput.val(1);
		}
		// 判断库存
	})
	// 手动修改数量
	$("#J-amount").bind("change", function() {
		// 获取当前商品的购买数量
		var stock = Number($('#applyNum_hidden').val());
		// 获取input的value,并去除前面的零
		var inputVal = Number($(this).val().replace(/\b(0+)/gi, ""));
		if (!regint(inputVal)) {
			$(this).val(1);
		} else if (inputVal >= stock) {
			$(this).val(stock);
		} else if (inputVal == 0) {
			$(this).val(1);
		} else {
			$(this).val(inputVal);
		}

	});
	
	inituploader("","00",[]);

});

// 校验输入是否为数字
function regint(num) {
	return /^[\d]+$/.test(num);
}

// 提示错误信息
function showError(str) {
	$.dialog({
		content : str,
		title : '众聚猫提示',
		time : 1000
	});
}

// 验证问题描述，退换货的方式，返回商品的方式
function validateApply() {
	var applyTypes = $('input:radio[name="applyType"]');
	for (var i = 0; i < applyTypes.length; i++) {
	}
}

function uploadImage() {  
    //判断是否有选择上传文件  
        var imgPath = $("#pageUpload").val();  
    	alert(imgPath);
        if (imgPath == "") {  
            alert("请选择上传图片！");  
            return;  
        }  
        //判断上传文件的后缀名  
        var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);  
        if (strExtension != 'jpg' && strExtension != 'gif'  
        && strExtension != 'png' && strExtension != 'bmp') {  
            alert("请选择图片文件");  
            return;  
        }  
        $.ajax({
            type: "POST",  
            url: "<%=path%>/kf/rrg/upload",  
            data: { imgPath: $("#pageUpload").val() },  
            cache: false,  
            success: function(data) {
                alert("上传成功");  
                $("#imgDiv").empty();  
                $("#imgDiv").html(data);  
                $("#imgDiv").show();  
            },  
            error: function(XMLHttpRequest, textStatus, errorThrown) {  
                alert("上传失败，请检查网络后重试");  
            }  
        });  
    }  

function onSubmitFirst(){
	if($("#desc").val()==""){
		showError("请填写问题描述！");
		return false;
	}
	document.forms[0].submit();
}
