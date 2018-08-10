var  CONTEXTPATH  = $("#conPath").val();

$(document).ready(function() {
	addClickSubmit(1);
});
function addClickSubmit(page) {
    /* 获取选中类目 */
    var businessProdId = $("#businessProdId2").val();
    var skuCode = $("#skuCode2").val();
    var productName=$("#productName2").val();
    var supplierName=$("#supplierName2").val();
    var pro_array = new Array();
    if (page != "" && page != undefined) {
        pro_array.push("page=" + page);
    }
    if (businessProdId != "" && businessProdId != undefined) {
        pro_array.push("businessProdId=" + businessProdId);
    }
    if (skuCode != "" && skuCode != undefined) {
        pro_array.push("skuCode=" + skuCode);
    }
    if (productName != "" && productName != undefined) {
        pro_array.push("prodName=" + productName);
    }
    if (supplierName != "" && supplierName != undefined) {
        pro_array.push("supplierName=" + supplierName);
    }

    $.ajax({
        type : "post",
        url : CONTEXTPATH + "/productMix/findMixProductListByConditions",
        data : pro_array.join("&"),
        dataType : "html",
        success : function(msg) {
            $("#pu_wrap").html(msg);
        },
        error : function(jqXHR, textStatus, errorThrown) {
            alert("对不起，数据异常请稍后再试!");
        }
    });

}