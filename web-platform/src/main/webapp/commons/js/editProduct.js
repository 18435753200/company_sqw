$(document) .ready(function () {
  $('.chim') .delegate('input', 'change', changebox);
  $('.yanse') .delegate('input', 'change', changebox);
  $('.yanse') .delegate('input', 'change', changeimg);
  $('#same_price') .bind('change', sameprice);
	$("table#tb-speca-quotation input").live("input propertychange", checknum);
	$("table#tb-speca-quotation input").live("blur", checknum1);
	$("table#tb-tiaoxingma input").live("blur", checktiao);
  $('.jinben .operate .toleft') .live('click', pre);
  $('.jinben .operate .toright') .live('click', next);
  $('.jinben .operate .del') .live('click', dele);
  $('.b2 input') .live('blur', numpricecheck);
  //xiug();
  xiug1();
  xiug3();
  changebox();
  //	
  $("#postTrade").click(function(){
	  $(".fabu_btn").attr("disabled","disabled");
	  var flag = saveProduct();
	  if (flag == false) {
		  $(".fabu_btn").removeAttr("disabled");
		  alert('部分信息不完整或不符合规范，请修改！');
	  }
	  if (flag == true) {
		  var editorValue = UE.getEditor('editor').getContent();
		  $.ajax({
			  type:'post',
			  url:'../product/editProduct',
			  data: $('#productAction').serialize()+'&editorValue='+editorValue,
			  success:function(data){
				  if(data=='1'){
					  tipMessage("保存成功，将立即返回到商品列表！",function(){
						  var  CONTEXTPATH  = $("#conPath").val();
						  window.location.href=CONTEXTPATH+"/product/getPro";
					  });
				  }else{
					  alert('保存失败，请检查后重试！');
				  }
				 
			  }
		 });
	  }
  });
});
//验证信息
function saveProduct() {
	var issubmit = true;
	
	var attrobjs = $("#attrobjs input[required='required'][type='text']");
	
	if(attrobjs!=''){
		$.each(attrobjs,function(){
		  var attrobjval = $(this).val();
		  var attrobjsconfirm = 1;
		  if(attrobjval==''){
			  issubmit = false;
			  attrobjsconfirm = 0;
			  $(this).next().text("带星号的属性值必填。").show();
		  }
		  if(attrobjval.length>50){
			  issubmit = false;
			  attrobjsconfirm = 0;
			  $(this).next().text("长度不能大于50位。").show();
		  }
      if(attrobjsconfirm==1){
        $(this).next().hide();
      }
		})
	}

	var notrequiredattr = $("#attrobjs input[required!='required'][type='text'][disabled!='disabled']");
	
	if(notrequiredattr!=''){
		$.each(notrequiredattr,function(){
		  var attrobjval = $(this).val();
		  var attrobjsconfirm = 1;
		  if(attrobjval.length>50){
			  issubmit = false;
			  attrobjsconfirm = 0;
			  $(this).next().text("长度不能大于50位。").show();
		  }
      if(attrobjsconfirm==1){
        $(this).next().hide();
      }
		})
	}
	
	$('#attrobjs p[required=\'required\']').has('input[type=\'checkbox\']').each(function () {
	    if ($(this).find('input[type=\'checkbox\']:checked').length < 1) {
	    	 issubmit = false;
	        $(this).find('.dpl-tip-inline-warning').text("带星号的属性值必填。").show();
	  }
	})

	
	
	
	var radioval = $('input:radio[name="cost"]:checked') .val();
	if (radioval == '1') {
		var isnotnull = 1;
		
		$('.pp') .find('input[type="text"]') .each(function () {
			var thisvals = $(this) .val();
			if (thisvals == '') {
				issubmit = false;
				isnotnull = 0;
			}
		});
		if(isnotnull==0){
			$('.tq2 #inputwarning') .text('按照数量报价 所有数值必填！') .show();
		}else{
			$('.tq2 #inputwarning').hide();
		}
		var  matchnum = /^[0-9.]*$/;
		var onlyPrice = $.trim($("#onlyPrice").val());
		if(!matchnum.test(onlyPrice)||onlyPrice.length>18){
			$("#onlyPrice").next() .text('建议零售价只能是数字 并且不能大于18位！') .show();
			issubmit = false;
		}else{
			var fflag = true;
			$(".tqz .pp .b2 input[name='pic']").each(function(){
				if(Number($(this).val())>Number(onlyPrice)){
					fflag = false;
					$("#onlyPrice").next() .text('建议零售价应该大于批次价格！') .show();
				}
			});
			if(fflag){
				$('#onlyPrice') .next() .hide();
			}
			
		}
		var startNum = $('#startNum') .val();
		if (startNum == '' || startNum < 1) {
			issubmit = false;
			$('.tq2 #startNumWarning') .text('起批量不能为空或小于1！') .show();
		} else {
			$('.tq2 #startNumWarning') .hide();
		}
	
	}
  if (radioval == '2') {
	  var minNum = $("#minNum").val()+'';
	  var t = /^[0-9]+(.[0-9]{2})?$/;
	  if(minNum==""||minNum.length>5||!t .test(minNum)){
		  issubmit = false;
	      $('.g #g1') .text('最小起订量应为小于6位整数且不能是空的！') .show();
	  }else{
		  $('.g #g1') .hide();
	  }
	  var core2isntnull = 1;
	    $('#tb-speca-quotation') .find('input[type="text"]') .each(function () {
	    	if ($(this) .val() == '') {
	    		issubmit = false;
	    		core2isntnull=0;
	      }
	    });
	    if(core2isntnull==0){
	    	$('.g #g2') .text('所有数值不能是空的！') .show();
	    }else{
    	 	var  textpre = /^\d{1,16}(\.\d{1,2})?$/;
   	  		var lengthIsSubmit = 1;
   	  		$('#tb-speca-quotation') .find('input[type="text"]') .each(function () {
   	  			if ($(this) .val().length>18) {
   	  				issubmit = false;
   	  				lengthIsSubmit=0;
   	  			}
   	  		});
	   	  if(lengthIsSubmit==0){
	   		  $('.g #g2') .text('所有数值长度不能超过18位！') .show();
	   	  }else{
	   		  var sssflag = true;
	   		  $("#tb-speca-quotation tr").each(function(){
	   			  var pro_price = $(this).find(".pro_price").val();
	   			  var sugprice = $(this).find(".sugprice").val();
	   			  if(Number(pro_price) > Number(sugprice)){
	   				  sssflag=false;
	   				  issubmit = false;
	   				  $('.g #g2') .text('建议零售价应该大于单价！') .show();
	   			  }
	   		  })
	   		if(sssflag){
	   			$('.g #g2').hide();
	   		}
	   	  }
	    }
  }
  var imgs = $('.jinben ul');
  $.each(imgs, function (i, item) {
    var num = $(item) .find('img') .length;
    if (num < 1) {
      isSubmit = false;
      $(item) .prev() .find('.dpl-tip-inline-warning') .css('display', 'inline');
    } else {
      $(item) .prev() .find('.dpl-tip-inline-warning') .css('display', 'none');
    }
  });
  if ($('.yanse2 input[type=\'checkbox\']:checked') .length == 0) {
    $('.yanse2 ') .children('.dpl-tip-inline-warning') .css('display', 'inline');
    issubmit = false;
  } else {
    $('.yanse2 ') .children('.dpl-tip-inline-warning') .css('display', 'none');
  }
  if ($('.chim2 input[type=\'checkbox\']:checked') .length == 0) {
    $('.chim2 ') .children('.dpl-tip-inline-warning') .css('display', 'inline');
    issubmit = false;
  } else {
    $('.chim2 ') .children('.dpl-tip-inline-warning') .css('display', 'none');
  }
  var imgs = $('.jinben ul');
  $.each(imgs, function (i, item) {
    var num = $(item) .find('img') .length;
    if (num < 1) {
      issubmit = false;
      $(item) .prev() .find('.dpl-tip-inline-warning') .css('display', 'inline');
    } else {
      $(item) .prev() .find('.dpl-tip-inline-warning') .css('display', 'none');
    }
  });
  var mingxitext = $('.mingxi textarea[required=\'required\']');
  $.each(mingxitext, function (i, item) {
    if (item.value == '') {
      issubmit = false;
      $(item) .parent() .next() .css('display', 'inline');
    } else {
      $(item) .parent() .next() .css('display', 'none');
    }
  });
  var mingxiinput = $('.mingxi input[required=\'required\']');
  $.each(mingxiinput, function (i, item) {
    if (item.value == '') {
      issubmit = false;
      $(item) .parent() .next() .css('display', 'inline');
    } else {
      $(item) .parent() .next() .css('display', 'none');
    }
  });
  var productinfo = $('#productinfo') .val();
  if (productinfo.length > 100||productinfo.length<1) {
    issubmit = false;
    $('#productinfo') .next() .text('商品标题不能大于100个字切不能是空的') .show();
  }
  
  if($("#logisticsDescription").val().length>200||$("#logisticsDescription").val().length<1){
	  issubmit = false;
	  $("#logisticsDescription").parent().next().text("物流描述不能大于200字,并且不能是空的!").show();
  }else{
	  $("#logisticsDescription").parent() .next().hide();;
  }
  
  if($("#salesServices").val().length>200){
		issubmit = false;
		$("#salesServices").parent().next().text("售后服务不能大于200字").show();
	}else{
		$("#salesServices").parent().next().hide();
	}
	if($("#packinglist").val().length>200){
		issubmit = false;
		$("#packingList").parent().next().text("包装清单不能大于200字").show();
	}else{
		$("#packingList").parent().next().hide();
	}

  var servicephone = $('#servicephone') .val();
  if(servicephone.length>20){
	  issubmit = false;
	  $('#mobiletext') .text('售后电话不能大于20位！') .show();
  }else{
	  $('#mobiletext').hide();
  }
  return issubmit;
}
//全部发布商品开始

var xiug = function () {
  $('.i_box .jia') .click(function () {
    $('.bb') .append('<p class=\'addP\' style=\'margin-left:20px;\'><input type=\'text\' name=\'start\' value=\'\' style=\'width:50px;\'> ：<input type=\'text\' name=\'pic\' value=\'\' style=\'width:198px;\'> <span class=\'del\'>删除</span></p>');
  });
  $(document) .on('click', '.del', function () {
    $(this) .parent('.addP') .remove();
  })
}
var xiug1=function(){
	$(".tqz span.b3").click(function(){
		$(".tq2 .dpl-tip-inline-warning[id!='inputwarning']").remove();
		var vali = true;
		$(".tqz .b2 input").each(function(){
			if(this.value == "" ){
					$(this).closest("span").after($('<span class="dpl-tip-inline-warning" style="display:block">请填写完整后再添加</span>'));
					vali = false;
					return false;
			}
		})
		if(vali){
			$('.pp').append( "<span class='b2'>起批量：<input type='text' name='start'> 件及以上 <input type='text' name='pic'> 元/件<span class='del'> 删除</span></span>" );
			var length = $(".pp .b2").length;
			if(length == 3){
				$($(".pp .b2:eq(1)")).find(".del").remove();
				$(".b3").hide();
			}
		}
		
	});
	$(".tqz").on('click','.del',function(){
		$(this).parent('.b2').remove();
		if($('.pp .b2').length == 2){
			$($(".pp .b2:eq(1)")).append("<span class='del'> 删除</span>");
			$(".b3").show();
		}
		numpricecheck();
	});

}

var xiug3 = function () {
  $('.tq4') .hide();
  $('.g') .hide();
  $('.cp1') .click(function () {
    $('.tq2') .show();
    $('.tqz .tq4') .hide();
    $('.g') .hide();
  });
  $('.cp2') .click(function () {
    changebox();
    $('.tqz .tq2') .hide();
    $('.tqz .tq4') .show();
    $('.g') .show();
  });
}
//全部发布商品结束

function numpricecheck(){
	//var that = event.target;
	var value1 = "";
	var value2 = "";
	$(".tq2 #inputwarning").text("").hide();
	$(".tq2 .dpl-tip-inline-warning[id!='inputwarning']").remove();
	var isnull = true;
	$.each($(".pp .b2 input"),function(){
		if ($(this).attr("name") == "start" ){
			if(!RegExp("^\\d{1,6}$").test(this.value) || this.value =="0"){
				
			
		
				$(this).closest("span").after($('<span class="dpl-tip-inline-warning" style="display:block">起批量应为小于6位的整数</span>'));
				isnull = false;
			}
		}else if($(this).attr("name") == "pic"){
			if((!RegExp("^\\d{1,18}\\.\\d+$").test(this.value) && !RegExp("^\\d{1,18}\\.?$").test(this.value))|| Number(this.value)<=0){
				$(this).closest("span").after($('<span class="dpl-tip-inline-warning" style="display:block">价格应不小于18位数</span>'));
				isnull = false;
			}else if(RegExp("^\\d{1,18}\\.$").test(this.value)){
				this.value += "00";
				
			}else if(RegExp("^\\d{1,18}$").test(this.value)){
				//add dot
				this.value += ".00";
			}else if(RegExp("^\\d{1,18}\\.\\d$").test(this.value)){
				//cut num after dot
				this.value += "0";
			}else if(RegExp("^\\d{1,18}\\.\\d+$").test(this.value)){
				var dot = this.value.split(".")
				this.value = dot[0] +"." + dot[1].substring(0,2);
			}
			
		}
	});
	if ($(".pp .b2").length>1 && isnull){
		$.each($(".pp .b2"),function(i,item){
			var object = $(item);
			var val1 = object.children()[0].value;
			var val2 = object.children()[1].value;
//			if((!RegExp("^\\d{1,6}$").test(val1)&&val1!="")|| (!RegExp("^\\d*\\.?\\d+$").test(val2)&&val2!="")){
//				$(".tq2 .dpl-tip-inline-warning").text("必须填入数字。").show();
//				return;
//			}
			if(i>0){
				var value11 = parseFloat(val1);
				var value22 = parseFloat(val2); 
				if(value1 < value11 && value2 > value22){
					value1 = value11;
					value2 = value22;
				}else if(value1 >= value11){
					$(".tq2 #inputwarning").text("价格区间的数量必须后者大于前者。").show();
					return false;
				}else{
					$(".tq2 #inputwarning").text("产品单价必须后者小于前者。").show();
				}
				
			}else{
				value1 = parseFloat(object.children()[0].value);
				value2 = parseFloat(object.children()[1].value);
			}
		});
	}
//	else{
//		var object = $($(".pp .b2")[0]);
//		if((!RegExp("^\\d*\\.?\\d+$").test(object.children()[0].value)&&object.children()[0].value!="")|| (!RegExp("^\\d*\\.?\\d+$").test(object.children()[1].value)&&object.children()[1].value!="")){
//			$(".tq2 .dpl-tip-inline-warning").text("必须填入数字。").show();
//			return;
//		}
//	}
	
}






function checknum(event) {
  var mm = event.target;
  setTimeout(function () {
    var str = mm.value;
    if (!RegExp('^\\d*\\.?\\d+$') .test(str) && !RegExp('^\\d*\\.?$') .test(str)) {
      mm.value = '';
      ;
      //$(mm).delay(100000000).val("");
    }
  }, 1000);
}
function checknum1(event){
	var mm = event.target;	
	var str =  mm.value;
	$(".tab_box .dpl-tip-inline-warning").remove();
	if(str!=""){
		var str =  mm.value;
		if((!RegExp("^\\d{1,18}\\.\\d+$").test(str) && !RegExp("^\\d{1,18}\\.?$").test(str)) || Number(str)<0){
			mm.value="";
			$("#tb-speca-quotation").after($('<span class="dpl-tip-inline-warning" style="display:block">价格应小于18位数</span>'))
			//$(mm).delay(100000000).val("");
		}else if(RegExp("^\\d{1,18}\\.$").test(str)){
			mm.value += "00";
				
		}else if(RegExp("^\\d{1,18}$").test(str)){
			//add dot
			mm.value += ".00";
		}else if(RegExp("^\\d{1,18}\\.\\d$").test(str)){
			//cut num after dot
			mm.value += "0";
		}else if(RegExp("^\\d{1,18}\\.\\d+$").test(str)){
			var dot = str.split(".")
			mm.value = dot[0] +"." + dot[1].substring(0,2);
		}
		
		if($("#same_price").attr("checked")=="checked"){
			changeprice(event);
		}
	}	
}


function checktiao(event){
	var mm = event.target;	
	var str =  mm.value;
	$(".tab_box .dpl-tip-inline-warning").remove();
	if(str!=""){
		var str =  mm.value;
		if(!RegExp("^\\d{12,13}$").test(str)){
			mm.value="";
			$("#tb-tiaoxingma").after($('<span class="dpl-tip-inline-warning" style="display:block">条形码应为12或者13位数字</span>'))
			//$(mm).delay(100000000).val("");
		}
	}	
}  


//图片处理函数
function pre(event) {
	var but = event.target;
	var li = $(but).parent().parent();
	var id = li.attr("class");
	var theimg = li.find(".p-img");
    var src = li.find("img").attr("src");
    if (src != null && id != "img-1") {
        if (li.prev().children(".p-img").children().is("img")) {
            var imgboxpre = li.prev().children(".p-img");
            li.prev().find(".p-img").remove();
            li.prev().prepend(theimg);
            li.find(".p-img").remove();
            li.prepend(imgboxpre);
        } else {
            li.find(".operate").hide();
            li.prev().find(".p-img").remove();
            li.prev().prepend(theimg);
            li.prepend($("<div class='p-img'></div>"));
            li.prev().children(".operate").show();
        }    
    } else {
        return false;
    }
}
function next(event) {
	var but = event.target;
	var li = $(but).parent().parent();
	var id = li.attr("class");
    var theimg = li.find(".p-img");
    var src = li.find("img").attr("src");
    if (src != null && id != "img-6") {
        if (li.next().children(".p-img").children().is("img")) {
            var imgboxnext = li.next().children(".p-img");
            li.next().find(".p-img").remove();
            li.next().prepend(theimg);
            li.find(".p-img").remove();
            li.prepend(imgboxnext);
        } else {
            li.find(".operate").hide();
            li.next().find(".p-img").remove();
            li.next().prepend(theimg);
            li.prepend($("<div class='p-img'></div>"));
            li.next().children(".operate").show();
        }
        
    } else {
        return false;
    }
}
function dele(event) {
	var but = event.target;
	var li = $(but).parent().parent();
	var inhid = li.find("#diddendiv");
    var theimg = li.find("img");
    var src = theimg.attr("src");
    if (src != null) {
        li.find(".operate").hide();
        theimg.parent().empty();
        inhid.empty();
       
    } else {
        return false;
    }
}

var tableInfo = [
];
var test = [
];
function adduploadimg(value, id, url) {
  var isnew = $("div.yanse input#" + id).attr("isnew") == "2" ? true : false;
  var p_img = $('<div class=\'p-img\'></div>');
  var operate = $('<div class=\'operate\'><i class=\'toleft\'>左移</i><i class=\'toright\'>右移</i><i class=\'del\'>删除</i></div>');
  if (url.length == 0) {
    var img1 = isnew ? $('<li class=\'img-1\'></li>') .append(p_img.clone()) .append(operate.clone()):$('<li class=\'img-1\'></li>') .append(p_img.clone());
    var img6 = isnew ? $('<li class=\'img-6\'></li>') .append(p_img.clone()) .append(operate.clone()):$('<li class=\'img-6\'></li>') .append(p_img.clone());
    var img = isnew ? $('<li></li>') .append(p_img.clone()) .append(operate.clone()):$('<li></li>') .append(p_img.clone());
    var ul = $('<ul id=\'' + id + '_img\'></ul>') .append(img1) .append(img.clone()) .append(img.clone()) .append(img.clone()) .append(img) .append(img6);
  } else {
    var ul = $('<ul id=\'' + id + '_img\'></ul>');
    $.each(url, function (i, item) {
      if (i == 0) {
    	 if(isnew){
    		 ul.append($('<li class=\'img-1\'><div id=\'diddendiv\'></div></li>') .append(p_img.clone() .append('<img src=\'' + url[i] + '\'><input type=\'hidden\' name=\'imgUrl\' value=\'' + id + '_' + url[i] + '\'/></img>')) .append(operate.clone() .show()));
    	 }
    	 else {
    		 ul.append($('<li class=\'img-1\'><div id=\'diddendiv\'></div></li>') .append(p_img.clone() .append('<img src=\'' + url[i] + '\'><input type=\'hidden\' name=\'imgUrl\' value=\'' + id + '_' + url[i] + '\'/></img>')));
    	 }
      } else if (i == 5) {
    	  if(isnew){
    		  ul.append($('<li class=\'img-6\'>' +
    		  '<div id=\'diddendiv\'></div></li>') .append(p_img.clone() .append('<img src=\'' + url[i] + '\'><input type=\'hidden\' name=\'imgUrl\' value=\'' + id + '_' + url[i] + '\'/></img>')) .append(operate.clone() .show()));
    	  }else{
    		  ul.append($('<li class=\'img-6\'>' +
    	        '<div id=\'diddendiv\'></div></li>') .append(p_img.clone() .append('<img src=\'' + url[i] + '\'><input type=\'hidden\' name=\'imgUrl\' value=\'' + id + '_' + url[i] + '\'/></img>'))); 
    	  }
      } else {
    	  if(isnew){
        ul.append($('<li><div id=\'diddendiv\'></div></li>') .append(p_img.clone() .append('<img src=\'' + url[i] + '\'><input type=\'hidden\' name=\'imgUrl\' value=\'' + id + '_' + url[i] + '\'/></img>')) .append(operate.clone() .show()));
      
    	  }else{
    		  ul.append($('<li><div id=\'diddendiv\'></div></li>') .append(p_img.clone() .append('<img src=\'' + url[i] + '\'><input type=\'hidden\' name=\'imgUrl\' value=\'' + id + '_' + url[i] + '\'/></img>')));
    	  }
     }
    })
    if (url.length < 6) {
      for (var j = url.length; j < 6; j++) {
        if (j == 5  && isnew) {
          ul.append($('<li class=\'img-6\'></li>') .append(p_img.clone()) .append(operate.clone()));
        } else if(j != 5  && isnew){
          ul.append($('<li></li>') .append(p_img.clone()) .append(operate.clone()));
        }else if(j == 5  && !isnew){
        	ul.append($('<li class=\'img-6\'></li>') .append(p_img.clone()));
        }else{
        	ul.append($('<li></li>') .append(p_img.clone()));
        }
      }
    }
  }
  var div = isnew ? $('<div></div>') .append('<div class=\'wenzi\'><span class=\'sctp-txt\'>' + value + '的图片:</span><input id=\'' + id + '_upload\' name=\'button\' type=\'submit\'  value=\'图片上传\' /><span class=\'dpl-tip-inline-warning\'>请至少上传一张图片</span></div>') .append(ul):
	  $('<div></div>') .append('<div class=\'wenzi\'><span class=\'sctp-txt\'>' + value + '的图片:</span></div>') .append(ul) ;
  var object = $('<div class=\'z\' id=\'' + id + '_xianshi\'></div>') .append(div);
  $('.jinben') .append(object);
  if(isnew){
	  if (flashDetect()) {
		    // 探测到已经安装了flash插件 则初始化上传按钮和提示
		    // 初始化swfUpload组件
		    $('#' + id + '_upload') .uploadify({
		      // File Upload Settings
		      'height': 30,
		      'swf': '../commons/js/uploadify/uploadify.swf',
		      'uploader': '../product/imageUp',
		      'width': 100,
		      'cancelImg': '../js/img/uploadify-cancel.png',
		      'auto': true,
		      'buttonText': '上传图片',
		      file_size_limit: '1024K',
		      file_queue_limit: 6,
		      fileTypeExts: '*.gif;*.jpg;*.jpeg;*.png',
		      file_types: '*.jpg;*.png;*.jpeg;',
		      file_types_description: '*.jpg;*.jpeg;*.png;*.JPG;*.JPEG;*.PNG;',
		      file_dialog_start_handler: fileDialogStart,
		      file_queued_handler: fileQueued,
		      file_queue_error_handler: fileQueueError,
		      file_dialog_complete_handler: fileDialogComplete,
		      upload_start_handler: uploadStart,
		      upload_progress_handler: uploadProgress,
		      upload_error_handler: uploadError,
		      upload_success_handler: uploadSuccess,
		      upload_complete_handler: uploadComplete,
		      queue_complete_handler: queueComplete
		    });
		    //$("#file_upload").append('<span class="upload-tip">一次可选6张图片哦～</span>');
		  } else {
		    // 探测到没有flash支持，给出提示。
		    $('.ifile') .html('<span class="no-flash-tip">' +
		    'Hi，您的浏览器OUT了，它未安装新版的Flash Player，' +
		    '<a href="http://get.adobe.com/flashplayer/" target="_blank">去安装>></a>' +
		    '</span>');
		  }
  }
 
}
function initimg() {
  var ss = $('.yanse input:checked');
  if (ss.length > 0) {
    $.each(ss, function (i, item) {
      var value = item.value;
      var id = item.id;
      adduploadimg(value, id, test);
    })
  }
}
function changeimg(event) {
  var obj = event.target;
  var id = obj.id;
  var value = obj.value;
  if (obj.checked) {
    adduploadimg(value, id, [
    ]);
  } else {
    $('#' + id + '_xianshi') .remove();
  }
}
function sameprice(event) {
	var but = event.target;
	var obj = $("#tb-speca-quotation tbody .pro_price");
	if (but.checked){
		if(obj.length > 1){
			var price = obj[0].value;
			$.each(obj,function(i,item){
				item.value = price;
				if(i>0){
					$(this).attr("readonly","readonly");
				}
				
			});
//			$(obj[0]).bind("input propertychange", changeprice);
		}	
	}else{
		if(obj.length > 1){
			$.each(obj,function(i,item){
				if(i>0){
					$(this).removeAttr("readonly");
				}
				
			});
//			$(obj[0]).unbind("input propertychange", changeprice);
		}
	}

}
function changeprice(event) {
  var value = event.target.value;
  var obj = $('#tb-speca-quotation tbody .pro_price');
  if (obj.length > 1) {
    var price = obj[0].value;
    $.each(obj, function (i, item) {
      item.value = price;
    })
  }
}

/*function changebox(event){	
	var chim = $(".chim input:checked");
	var yanse = $(".yanse input:checked");
	var chimlen = chim.length;
	var yanselen = yanse.length;
	if(yanselen == 0 && chimlen == 0){
		$("#tb-speca-quotation").hide();
		return;
	}

	tableInfo = _fStoreTableInfo();
	$("#tb-speca-quotation tbody").remove();
	document.getElementById("same_price").checked = false;
	$("#tb-speca-quotation").show();	
	

	if(yanselen == 0){
		var tbody = $("<tbody></tbody>");
		var tr = $("");
		$.each(chim,function(i,n){
			if(i == 0){
				tr = $("<tr ><td id='' class='yan' rowspan='"+ chimlen +"'></td>" +
						"<td class='chi'>"+ n.value +"</td>" +
						"<td><input class='pro_price' type='text'  value=''></td>" +
						"<td><input class='sugprice'  type='text' ></td></tr>")
			}else{
				tr = $("<tr><td class='chi'>"+ n.value +"</td><td>" +
						"<input type='hidden' name='buyVal' value='"+n.id+"'>" +
						"<input type='hidden' name='buyName' value='"+n.value+"'>" +
						"<input class='pro_price' type='text' name='productPic'></td><td><input class='sugprice' name='rPrice'  type='text' ></td></tr>")
			} 
			tbody.append(tr);
		});
		$("#tb-speca-quotation").append(tbody);
		_fShowTableInfo(tableInfo);
		return;
	}
	if(chimlen == 0){
		var tr = $("");
		$.each(yanse,function(i,n){
			
			tr = $("<tbody><tr><td class='yan' id='"+ n.id +"'>"+ n.value +"</td><td class='chi' id=''></td><td>" +
					"<input type='hidden' name='buyVal' value='"+n.id+"'>" +
					"<input type='hidden' name='buyName' value='"+n.value+"'>" +
					"<input class='pro_price' type='text'></td><td><input class='sugprice'  type='text'  name='rPrice'></td></tr></tbody>")
			$("#tb-speca-quotation").append(tr);
		});
		_fShowTableInfo(tableInfo);
		return;
	}
	if(yanselen > 0 && chimlen >0){
		$.each(yanse,function(i,n){
			var tbody = $("<tbody></tbody>");
			$.each(chim,function(j,m){
				if(j == 0){
					tr = $("<tr ><td class='yan' id='"+ n.id +"' rowspan='"+ chimlen +"'>"+ n.value +"</td><td class='chi' id='"+ m.id +"'>"+ m.value +"</td><td>" +
							"<input type='hidden' name='buyVal' value='"+n.id+"'>" +
							"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
							"<input type='hidden' name='buyName' value='"+n.value+"'>" +
							"<input type='hidden' name='saleName' value='"+m.value+"'>" +
							"<input class='pro_price' type='text' name='productPic'></td><td><input class='sugprice' name='rPrice' type='text' ></td></tr>")
				}else{
					tr = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td><td>" +
							"<input type='hidden' name='buyVal' value='"+n.id+"'>" +
							"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
							"<input type='hidden' name='buyName' value='"+n.value+"'>" +
							"<input type='hidden' name='saleName' value='"+m.value+"'>" +
							"<input class='pro_price' type='text' name='productPic'></td><td><input class='sugprice' name='rPrice' type='text' ></td></tr>")
				} 
				tbody.append(tr);
			});
			$("#tb-speca-quotation").append(tbody);
		});
		_fShowTableInfo(tableInfo);
	}
	
}*/
function changebox(){	
	var chim = $(".chim input:checked");
	var yanse = $(".yanse input:checked");
	var chimlen = chim.length;
	var yanselen = yanse.length;
	if(yanselen == 0 && chimlen == 0){
		$("#tb-speca-quotation").hide();
		return;
	}

	tableInfo = _fStoreTableInfo("tb-speca-quotation");
	tiaoxingmainfo = _fStoreTableInfo("tb-tiaoxingma");
	$("#tb-speca-quotation tbody").remove();
	$("#tb-tiaoxingma tbody").remove();
	document.getElementById("same_price").checked = false;
	$("#tb-speca-quotation").show();	
	

	if(yanselen == 0){
		var tbody = $("<tbody></tbody>");
		var tbody1 = $("<tbody></tbody>");
		var tr = $("");
		var tr1 = $("");
		$.each(chim,function(i,n){
			if(i == 0){
				if( $(n).attr("isnew") == "0"){
					tr = $("<tr ><td id='' class='yan' rowspan='"+ chimlen +"'></td>" +
							"<td class='chi'>"+ n.value +"</td>" +
							"<td><input class='pro_price' type='text'  value=''  disabled='disabled'></td>" +
							"<td><input class='sugprice'  type='text' disabled='disabled'></td></tr>");
				}else{
					tr = $("<tr ><td id='' class='yan' rowspan='"+ chimlen +"'></td>" +
							"<td class='chi'>"+ n.value +"</td>" +
							"<td><input class='pro_price' type='text'  value=''></td>" +
							"<td><input class='sugprice'  type='text' ></td></tr>");
				}	
				tr1 = $("<tr ><td id='' class='yan' rowspan='"+ chimlen +"'></td><td class='chi'>"+ n.value +"</td><td>" +
						"<input class='pro_price' type='text'  value=''></td></tr>")
			}else{
				if( $(n).attr("isnew") == "0"){
				tr = $("<tr><td class='chi'>"+ n.value +"</td><td>" +
						"<input type='hidden' name='buyVal' value='"+n.id+"'>" +
						"<input type='hidden' name='buyName' value='"+n.value+"'>" +
						"<input class='pro_price' type='text' name='productPic'  disabled='disabled'></td><td><input class='sugprice'   disabled='disabled' name='rPrice'  type='text' ></td></tr>")
				}else{
					tr = $("<tr><td class='chi'>"+ n.value +"</td><td>" +
							"<input type='hidden' name='buyVal' value='"+n.id+"'>" +
							"<input type='hidden' name='buyName' value='"+n.value+"'>" +
							"<input class='pro_price' type='text' name='productPic'></td><td><input class='sugprice' name='rPrice'  type='text' ></td></tr>")
					
				}
						tr1 = $("<tr><td class='chi'>"+ n.value +"</td><td>" +
						"<input class='pro_price' type='text' name='skuCode'></td></tr>");
			} 
			tbody.append(tr);
			tbody1.append(tr1);
		});
		$("#tb-speca-quotation").append(tbody);
		//条形码
		$("#tb-tiaoxingma").append(tbody1);
		_fShowTableInfo(tableInfo,"tb-speca-quotation");
		_fShowTableInfo(tiaoxingmainfo,"tb-tiaoxingma");
		return;
	}
	if(chimlen == 0){
		var tr = $("");
		var tr1 = $("");
		$.each(yanse,function(i,n){
			if( $(n).attr("isnew") == "1"){
				tr = $("<tbody><tr><td class='yan' id='"+ n.id +"'>"+ n.value +"</td><td class='chi' id=''></td><td>" +
						"<input type='hidden' name='buyVal' value='"+n.id+"'>" +
						"<input type='hidden' name='buyName' value='"+n.value+"'>" +
						"<input class='pro_price' type='text' disabled='disabled' ></td><td><input class='sugprice' disabled='disabled' type='text'  name='rPrice'></td></tr></tbody>");
				
			}else{
				tr = $("<tbody><tr><td class='yan' id='"+ n.id +"'>"+ n.value +"</td><td class='chi' id=''></td><td>" +
						"<input type='hidden' name='buyVal' value='"+n.id+"'>" +
						"<input type='hidden' name='buyName' value='"+n.value+"'>" +
						"<input class='pro_price' type='text'></td><td><input class='sugprice'  type='text'  name='rPrice'></td></tr></tbody>");
				
			}
			tr1 = $("<tbody><tr><td class='yan' id='"+ n.id +"'>"+ n.value +"</td><td class='chi' id=''></td><td>" +
					"<input class='pro_price' type='text'></td></tr></tbody>")
			$("#tb-speca-quotation").append(tr);
			$("#tb-tiaoxingma").append(tr1);
			
		});
		
		_fShowTableInfo(tableInfo,"tb-speca-quotation");
		_fShowTableInfo(tiaoxingmainfo,"tb-tiaoxingma");
		return;
	}
	if(yanselen > 0 && chimlen >0){
		
		$.each(yanse,function(i,n){
			var tbody = $("<tbody></tbody>");
			var tbody1 = $("<tbody></tbody>");
			var tr = $("");
			var tr1 = $("");
			$.each(chim,function(j,m){
				if(j == 0){
					if( $(n).attr("isnew") == "1" && $(m).attr("isnew") == "1"){
						tr = $("<tr ><td class='yan' id='"+ n.id +"' rowspan='"+ chimlen +"'>"+ n.value +"</td><td class='chi' id='"+ m.id +"'>"+ m.value +"</td><td>" +
								"<input type='hidden' name='buyVal' value='"+n.id+"'>" +
								"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
								"<input type='hidden' name='buyName' value='"+n.value+"'>" +
								"<input type='hidden' name='saleName' value='"+m.value+"'>" +
								"<input class='pro_price' type='text' name='productPic' disabled='disabled' ></td><td><input  disabled='disabled' class='sugprice' name='rPrice' type='text' ></td></tr>");
					}else{
						tr = $("<tr ><td class='yan' id='"+ n.id +"' rowspan='"+ chimlen +"'>"+ n.value +"</td><td class='chi' id='"+ m.id +"'>"+ m.value +"</td><td>" +
								"<input type='hidden' name='buyVal' value='"+n.id+"'>" +
								"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
								"<input type='hidden' name='buyName' value='"+n.value+"'>" +
								"<input type='hidden' name='saleName' value='"+m.value+"'>" +
								"<input class='pro_price' type='text' name='productPic'></td><td><input class='sugprice' name='rPrice' type='text' ></td></tr>")
					}
					
				
					tr1 = $("<tr ><td class='yan' id='"+ n.id +"' rowspan='"+ chimlen +"'>"+ n.value +"</td><td class='chi' id='"+ m.id +"'>"+ m.value +"</td><td>" +
							"<input class='pro_price' disabled='disabled' type='text' name='skuCode'><input class='pro_price' type='hidden' name='skuCode'></td></tr>")
				}else{
					if($(n).attr("isnew") == "1" && $(m).attr("isnew") == "1"){    
						tr = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td><td>" +
								"<input type='hidden' name='buyVal' value='"+n.id+"'>" +
								"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
								"<input type='hidden' name='buyName' value='"+n.value+"'>" +
								"<input type='hidden' name='saleName' value='"+m.value+"'>" +
								"<input class='pro_price' type='text' name='productPic' disabled='disabled' ></td><td><input disabled='disabled'  class='sugprice' name='rPrice' type='text' ></td></tr>")
					
					
					}else{
						tr = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td><td>" +
								"<input type='hidden' name='buyVal' value='"+n.id+"'>" +
								"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
								"<input type='hidden' name='buyName' value='"+n.value+"'>" +
								"<input type='hidden' name='saleName' value='"+m.value+"'>" +
								"<input class='pro_price' type='text' name='productPic'></td><td><input class='sugprice' name='rPrice' type='text' ></td></tr>")
					
					
					}
					
					tr1 = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td><td>" +
							"<input class='pro_price' disabled='disabled' type='text' name='skuCode'><input class='pro_price' type='hidden' name='skuCode'></td></tr>");
				} 
				tbody.append(tr);
				tbody1.append(tr1);
			});
			$("#tb-speca-quotation").append(tbody);
			$("#tb-tiaoxingma").append(tbody1);
		});
		_fShowTableInfo(tableInfo,"tb-speca-quotation");
		_fShowTableInfo(tiaoxingmainfo,"tb-tiaoxingma");
	}
	
}

//储存表格价格和数量
function _fStoreTableInfo(tablename){
	var g_table=[];
	var obj=$("#"+tablename+" tbody");
	$.each(obj,function(i,item){
		var color_id = $(item).find(".yan").attr("id");
		$.each($(item).children(),function(j,item1){
			var chi_id = $(item1).find(".chi").attr("id");
			var price=$(item1).find(".pro_price").val();
			var jyprice = $(item1).find(".sugprice").val()
			var tr = [];
			tr.push(color_id);
			tr.push(chi_id);
			tr.push(price);
			tr.push(jyprice);
			g_table.push(tr);
		});
	});
	return g_table;
}

//遍历并显示表格价格和数量
function _fShowTableInfo(tableInfo,tablename){
	var obj=$("#"+tablename+" tbody");
	$.each(tableInfo,function(k){
		$.each(obj,function(i,item){
			var color_id = $(item).find(".yan").attr("id");
			if(color_id == tableInfo[k][0]){
				$.each($(item).children(),function(j,item1){
					var chi_id = $(item1).find(".chi").attr("id");
					if(chi_id == tableInfo[k][1]){
						$(item1).find(".pro_price").val(tableInfo[k][2]);
						 $(item1) .find('.sugprice') .val(tableInfo[k][3]);
					}
				});
			}
		});
	});
}