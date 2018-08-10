var  CONTEXTPATH  = $("#conPath").val();
/*  商品js    */
$(document).ready(function(){
    
    /*  加载一级目录  */
    $.ajax({
        type : "post", 
        url : CONTEXTPATH+"/stockdetail/getFirstDisp", 
        success : function(msg) { 
            var fistdisplist="<option value=''>请选择</option>";
            $.each(eval(msg),function(i,n){
                fistdisplist+="<option value='"+n.catePubId+"' busino='"+n.businessNo+"'>"+n.pubNameCn+"</option>";
            }); 
            $("#firstcategory").append(fistdisplist);
        }
    }); 

    loadWareSelect();

    /* 加载二级目录 */
    $("#firstcategory").change(function(){
        $("#secondcategory").empty();
        $("#thirdcategory").empty();
        $("#fourthcategory").empty();
        $.ajax({
            type : "post", 
            url : CONTEXTPATH+"/stockdetail/getOtherDisp", 
            data:"parCateDispId="+$(this).val(),
            success : function(msg) { 
                var fistdisplist="<option value=''>请选择</option>";
                $.each(eval(msg),function(i,n){
                    fistdisplist+="<option value='"+n.catePubId+"'  busino='"+n.businessNo+"'>"+n.pubNameCn+"</option>";
                }); 
                $("#secondcategory").append(fistdisplist);
            }
        }); 
    });


    /* 加载三级目录 */
    $("#secondcategory").change(function(){
        $("#thirdcategory").empty();
        $("#fourthcategory").empty();
        $.ajax({
            type : "post", 
            url : CONTEXTPATH+"/stockdetail/getOtherDisp", 
            data:"parCateDispId="+$(this).val(),
            success : function(msg) { 
                var fistdisplist="<option value=''>请选择</option>";
                $.each(eval(msg),function(i,n){
                    fistdisplist+="<option value='"+n.catePubId+"' busino='"+n.businessNo+"'>"+n.pubNameCn+"</option>";
                }); 
                $("#thirdcategory").append(fistdisplist);
            }
        }); 
    });

    /* 加载四级目录 */    
    $("#thirdcategory").change(function(){
        $("#fourthcategory").empty();
        $.ajax({
            type : "post", 
            url : CONTEXTPATH+"/stockdetail/getOtherDisp", 
            data:"parCateDispId="+$(this).val(),
            success : function(msg) { 
                var fistdisplist="<option value=''>请选择</option>";
                $.each(eval(msg),function(i,n){
                    fistdisplist+="<option value='"+n.catePubId+"' busino='"+n.businessNo+"'>"+n.pubNameCn+"</option>";
                }); 
                $("#fourthcategory").append(fistdisplist);
            }
        }); 
    });
    
    clickSubmit(1);
    
    
    $("#batchNumber").bind("input propertychange", function() {searchBatchNum();});
    $("#queryType").change(function(){
        var type=$("#queryType").val();
        if(type==1){
            $("#batchspan").show();
            $("#batchNumber").show();
            $("#batchNum").show();
        }else{
            $("#batchspan").hide();
            $("#batchNumber").hide();
            $("#batchNum").hide();
        }
    });
    
});

function searchBatchNum() {
    var batchNumber = $("#batchNumber").val();
    if (batchNumber != undefined && batchNumber.length > 0) {
        $("#batchNum").empty();
        $.ajax({
            type : "post",
            url : CONTEXTPATH + "/stockdetail/getBatchNumBlur",
            data : "batchNumber=" + batchNumber,
            success : function(msg) {
                var batchNumlist = "";
                $.each(eval(msg), function(i, n) {
                    batchNumlist += "<option value='" + n + "'>"
                            + n + "</option>";
                });
                $("#batchNum").append(batchNumlist);
            }
        });
    }else{
        $("#batchNum").empty();
    }
}

function  loadWareSelect(){
    $.ajax({
        type : "post", 
        url  : CONTEXTPATH+"/stockdetail/loadWareSelect", 
        dataType: 'json',
        success : function(msg) {
            if(msg.length>0){
                var tablelist ="";
                $.each(eval(msg),function(i,data){
                    tablelist+="<option value="+data.warehouseName+">"+data.warehouseName+"</option>";
                });
                $('#warehouseName').append(tablelist);
            }
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert("加载库房列表失败 ，请稍后再试。。");
        }
    });
}

function getCate() {
    var cateId = "";
    var firstcategory = $("#firstcategory").find("option:selected").attr("busino");
    var secondcategory = $("#secondcategory").find("option:selected").attr("busino"); 
    var thirdcategory = $("#thirdcategory").find("option:selected").attr("busino");
    var fourthcategory = $("#fourthcategory").find("option:selected").attr("busino"); 
    if (fourthcategory != "" && fourthcategory != null) {
        cateId = fourthcategory;
    } else if (thirdcategory != "" && thirdcategory != null) {
        cateId = thirdcategory;
    } else if (secondcategory != "" && secondcategory != null) {
        cateId = secondcategory;
    } else {
        cateId = firstcategory;
    }
    return cateId;
}

function clickSubmit(page) {
    /* 获取选中类目 */
    var cate = getCate();
    var warehouseName = $("#warehouseName").val();
    var beginTime = $("#beginTime").val();
    var endTime = $("#endTime").val();
    var pstatus = $("#pstatus").val();
    var queryType = $("#queryType").val();
    var batchNumber=$("#batchNum").val();
    var pro_array = new Array();
    if (page != "" && page != undefined) {
        pro_array.push("page=" + page);
    }
    if (cate != "" && cate != undefined) {
        pro_array.push("categoryId=" + cate);
    }
    if (warehouseName != "" && warehouseName != undefined) {
        pro_array.push("warehouseName=" + warehouseName);
    }
    if (beginTime != "" && beginTime != undefined) {
        pro_array.push("beginTime=" + beginTime);
    }
    if (endTime != "" && endTime != undefined) {
        pro_array.push("endTime=" + endTime);
    }
    if (pstatus != "" && pstatus != undefined) {
        pro_array.push("pstatus=" + pstatus);
    }
    if (queryType != "" && queryType != undefined) {
        pro_array.push("queryType=" + queryType);
    }
    if (batchNumber != "" && batchNumber != undefined) {
        pro_array.push("batchNumber=" + batchNumber);
    }

    $.ajax({
        type : "post",
        url : CONTEXTPATH + "/stockdetail/getRestPage",
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