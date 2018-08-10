
//当前选择的对象默认为"";
var g_obj="";

$(function(){
	//一级
	$(".sort-item:eq(0) ul li").live('click',function(){
		//父类目的id
		var parentCid=$(this).attr("id");
		_fdbCss($(this),1,parentCid);
		g_obj=$(this);
	});
	//二级
	$(".sort-item:eq(1) ul li").live('click',function(){
		//父类目的id
		var parentCid=$(this).attr("id");
		_fdbCss($(this),2,parentCid);
		g_obj=$(this);
	});
	//三级
	$(".sort-item:eq(2) ul li").live('click',function(){
		//父类目的id
		var parentCid=$(this).attr("id");
		_fdbCss($(this),3,parentCid);
		g_obj=$(this);
	});
	//四级
	$(".sort-item:eq(3) li").live('click',function(){
		//父类目的id
		var parentCid=$(this).attr("id");
		_fdbCss($(this),4,parentCid);
		g_obj=$(this);
	});
	//五级
	// $(".sort-item:eq(4) ul li").live('click',function(){
	// 	_fdbCss2($(this));
	// 	g_obj=$(this);
		
	// 	$(".main .infoDiv").remove();
	// 	$(".infoDiv").clone().appendTo($(".color:last").parent());
	// 	setTimeout(function(){
	// 		$(".main .infoDiv").fadeOut(400);
	// 	},3000);
	// });

});





//样式
function _fdbCss(obj,node,parentCid){
	_fdbCss2(obj);
    if(node != 4){
        f_fAjax("post","../productrule/findByConditions",parentCid,node);
    }
	
}

//当前选择的目录
function addCate(obj){
	var cate = obj.text(),pval = obj.attr("id");
    $(".act-sort-crumb dt").html("<span>你当前选择的类目：</span>");
	var temp= obj.closest(".sort-item").index();
	var sec=".sort-item ul li.cur";
	if(temp== 0){
		  $(".act-sort-crumb dt").append("<span id="+pval+"><strong>"+cate+"</strong></span>");
		  return;
	}
	$(sec).each(function(index,element){
		    var ca=$(this).closest(".sort-item").index();
		    if(ca == 0 ){
			   $(".act-sort-crumb dt").append("<span id="+$(this).attr("id")+">"+$(this).text()+"</span>");
			   
		    }
		    else if(ca == temp){
		    	$(".act-sort-crumb dt").append("<span>&gt;</span>" + "<span id="+pval+"><strong>"+cate+"</strong></span>");
		    	return false;
		    }
		    if(ca!= temp&&ca!= 0){
		    	$(".act-sort-crumb dt").append("<span>&gt;</span>" + "<span id="+$(this).attr("id")+">"+$(this).text()+"</span>");}
		    
		    
	  });
	
}

//选中 字体颜色 
function _fdbCss2(obj){
	obj.parent().find(".cur").removeClass("cur");
	obj.addClass("cur");
	addCate(obj);
}

//还原模板
function _fdbclear(node){
    node++;
    $(".sort-item:eq("+node+")").addClass("original");
    $(".sort-item:eq("+node+") ul").hide();
    $(".sort-item:eq("+node+") ul li.cur").removeClass("cur");
}

//数据交互
function f_fAjax(type,url,parentCid,node){
	$.ajax({
		type: 'post',
        url: "../productrule/findByConditions?Rand4=" + (new Date()).getTime(),
        data: "parentCid="+parentCid+"&oType=sort",
        dataType : 'json',
      
        success: function (source) {
			_fdbclear(node);

			if(source!=""){
				//移除禁用模板
				$(".sort-item:eq("+node+")").removeClass("original");
				//绑定数据
				_fdbStep(source,node);
			}
        },
        error: function () {
            //alert("error");
            _fdbclear(node);
            $(".sort-item:eq("+node+")").removeClass("original");
            $(".sort-item:eq("+node+") ul").show();
        }
    });
}


//绑定数据
function _fdbStep(data,node){
	$(".sort-item:eq("+node+") ul").html("");
	//添加商品数据
	for(var obj in data){
		var object = data[obj];
		var pubNameCn = object.pubNameCn;
		var catePubId = object.catePubId;
		var $li = $("<li id="+catePubId+"><p>"+pubNameCn+"</p></li>");
		$(".sort-item:eq("+node+") ul").append($li);
	}
	$(".sort-item:eq("+node+") ul").show();
}

