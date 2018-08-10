$(document) .ready(function () {
/*	  $('.chim') .delegate('input', 'change', changebox);
	  $('.yanse') .delegate('input', 'change', changebox);
	  $('.yanse') .delegate('input', 'change', changeimg);
	  $('#same_price') .bind('change', sameprice);
		$("table#tb-speca-quotation input").live("input propertychange", checknum);
		$("table#tb-speca-quotation input").live("blur", checknum1);
		$("table#tb-tiaoxingma input").live("blur", checktiao);
	  $('.jinben .operate .toleft') .live('click', pre);
	  $('.jinben .operate .toright') .live('click', next);
	  $('.jinben .operate .del') .live('click', dele);
	  $('.b2 input') .live('blur', numpricecheck);*/
});
//验证信息
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

//全部发布商品开始

var xiug = function () {
  $('.i_box .jia') .click(function () {
    $('.bb') .append('<p class=\'addP\'  style=\'margin-left:20px;\'><input type=\'text\' name=\'start\' disabled=\'disabled\' value=\'\' style=\'width:50px;\'> ：<input type=\'text\' name=\'pic\' value=\'\' style=\'width:198px;\' disabled=\'disabled\'> ');
  });
  $(document) .on('click', '.del', function () {
    $(this) .parent('.addP') .remove();
  })
}
var xiug1=function(){
	var measure =$("#dealerProductPackagemeasureid").val();
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
			$('.pp').append( "<span class='b2'>起批量：<input type='text' name='start' disabled='disabled'> <span class='danwei'>"+measure+"</span>及以上 <input type='text' name='pic' disabled='disabled'> 元/<span class='danwei'>"+measure+"</span>" );
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
				
			
		
				$(this).closest("span").after($('<span class="dpl-tip-inline-warning" style="display:block">起批量应为小于六位的整数</span>'));
				isnull = false;
			}
		}else if($(this).attr("name") == "pic"){
			if((!RegExp("^\\d{1,18}\\.\\d+$").test(this.value) && !RegExp("^\\d{1,18}\\.?$").test(this.value))|| Number(this.value)<=0){
				$(this).closest("span").after($('<span class="dpl-tip-inline-warning" style="display:block">价格应不大于18位数并且小数点后保留两位小数</span>'));
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
					$(".tq2 #inputwarning").text("产品单价必须下面价格小于上面价格").show();
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




function inituploadzizhi(url){
	$.each(url,function(i,item){
		$("#zizhi .p-img").eq(i).append("<img src='"+ url[i]+"'></img>");
	});
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
			$("#tb-speca-quotation").after($('<span class="dpl-tip-inline-warning" style="display:block">价格应不大于18位数并且小数点后保留两位小数</span>'))
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



var salesService = $("#salesServices").val()+'';

if(salesService.length>200){
	isSubmit = false;
	$("#salesServices").parent().next().css("display","inline");
	$("#salesServices").parent() .next().text("服务描述不能大于200字");
}

var servicedescription = $("#servicedescription").val()+'';
if(servicedescription.length>200){
	isSubmit = false;
	$("#servicedescription").parent().next().css("display","inline");
	$("#servicedescription").parent() .next().text("物流描述不能大于200字");
}


var packingList = $("#packinglist").val()+'';
if(packingList.length>200){
	isSubmit = false;
	$("#packingList").parent().next().css("display","inline");
	$("#packingList").parent().next().text("包装清单不能大于200字");
}
//图片处理函数

function pre(event) {
  var but = event.target;
  var li = $(but) .parent() .parent();
  var id = li.attr('class');
  var theimg = li.find('img');
  var src = theimg.attr('src');
  var filename = theimg.attr('filename');
  var type = theimg.attr('imgtype');
  if (src != null && id != 'img-1') {
    if (li.prev() .children('.p-img') .children() .is('img')) {
      var imgboxpre = li.prev() .children('.p-img') .children('img');
      li.prev() .children('.p-img') .empty() .append(theimg);
      li.children('.p-img') .empty() .append(imgboxpre);
    } else {
      li.find('.operate') .hide();
      li.prev() .children('.p-img') .empty() .append(theimg);
      li.prev() .children('.operate') .show();
    }
  } else {
    return false;
  }
}
function next(event) {
  var but = event.target;
  var li = $(but) .parent() .parent();
  var id = li.attr('class');
  var theimg = li.find('img');
  var src = theimg.attr('src');
  var filename = theimg.attr('filename');
  var type = theimg.attr('imgtype');
  if (src != null && id != 'img-6') {
    if (li.next() .children('.p-img') .children() .is('img')) {
      var imgboxnext = li.next() .children('.p-img') .children('img');
      li.next() .children('.p-img') .empty() .append(theimg);
      li.children('.p-img') .empty() .append(imgboxnext);
    } else {
      li.find('.operate') .hide();
      li.next() .children('.p-img') .empty() .append(theimg);
      li.next() .children('.operate') .show();
    }
  } else {
    return false;
  }
}
function dele(event) {
  var but = event.target;
  var li = $(but) .parent() .parent();
  var inhid = li.find('#diddendiv');
  var theimg = li.find('img');
  var src = theimg.attr('src');
  if (src != null) {
    li.find('.operate') .hide();
    theimg.parent() .empty();
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
	  var p_img = $('<div class=\'p-img\'></div>');
	//  var operate = $('<div class=\'operate\'><i class=\'toleft\'>左移</i><i class=\'toright\'>右移</i><i class=\'del\'>删除</i></div>');

	    var ul = $('<ul id=\'' + id + '_img\'></ul>');
	    $.each(url, function (i, item) {
	      if (i == 0) {
	        ul.append($('<li class=\'img-1\'><div id=\'diddendiv\'><input type=\'hidden\' name=\'imgUrl\' value=\'' + id + '_' + url[i] + '\'/></div></li>') .append(p_img.clone() .append('<img src=\'' + url[i] + '\'/>')) );
	      } else if (i == 5) {
	        ul.append($('<li class=\'img-6\'>' +
	        '<div id=\'diddendiv\'><input type=\'hidden\' name=\'imgUrl\' value=\'' + id + '_' + url[i] + '\'/></div></li>') .append(p_img.clone() .append('<img src=\'' + url[i] + '\'/>')));
	      } else {
	        ul.append($('<li><div id=\'diddendiv\'><input type=\'hidden\' name=\'imgUrl\' value=\'' + id + '_' + url[i] + '\'/></div></li>') .append(p_img.clone() .append('<img src=\'' + url[i] + '\'/>')));
	      }
	    })
	    if (url.length < 6) {
	      for (var j = url.length; j < 6; j++) {
	        if (j == 5) {
	          ul.append($('<li class=\'img-6\'></li>') .append(p_img.clone()));
	        } else {
	          ul.append($('<li></li>') .append(p_img.clone()));
	        }
	      }
	    }
	  
	  var div = $('<div></div>') .append('<div class=\'wenzi\'>' + value + '的图片:</div>') .append(ul);
	  var object = $('<div class=\'z\' id=\'' + id + '_xianshi\'></div>') .append(div);
	  $('.jinben') .append(object);
	  
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
function changebox(){
	var measure = $("select[name='dealerProductPackage.measureid'] option:selected").text();
	var chim = $(".chim input:checked");
	var yanse = $(".yanse input:checked");
	var chimlen = chim.length;
	var yanselen = yanse.length;
	if(yanselen == 0 && chimlen == 0){
		$("#tb-speca-quotation").hide();
		return;
	}
	
	//var pricename = ["","productPic","supplierPic"];

//	tableInfo = _fStoreTableInfo("tb-speca-quotation");
//	tiaoxingmainfo = _fStoreTableInfo("tb-tiaoxingma");
	//$("#tb-speca-quotation tbody").remove();
	$("#tb-tiaoxingma tbody").remove();
	$("#tb-speca-quotation").show();	

	if(yanselen > 0 && chimlen >0){

		$.each(yanse,function(i,n){
			var tbody= $("<tbody></tbody>"),tbodyfut= $("<tbody></tbody>"),tbodycash= $("<tbody></tbody>"),tbody1 = $("<tbody></tbody>");
			var tr= $(""),trfut = $(""),trcash = $(""),tr1 = $("");
			$.each(chim,function(j,m){
				if(j == 0){
					tr = $("<tr></tr>") ;
					tr.append($("<td class='yan' id='"+ n.id +"' rowspan='"+ chimlen +"'>"+ n.value +"</td><td class='chi' id='"+ m.id +"'>"+ m.value +"</td>"));
					trfut= tr.clone();
					tr1 = tr.clone();
					trcash= tr.clone();
					tr.append($(
//							"<td><input class='supplierPrice' readonly='readonly'></td>" +
							"<td><input type='hidden' name='buyVal' value='"+n.id+"'>" +
							"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
							"<input type='hidden' name='buyName' value='"+n.value+"'>" +
							"<input type='hidden' name='saleName' value='"+m.value+"'>" + 
							"<input class='pro_price' disabled='disabled' type='text'></td>"));
					trfut.append($(
//								"<td><input class='supplierPrice' readonly='readonly'></td>" +
								"<td>" +
//								"<input type='hidden' name='buyVal' value='"+n.id+"'>" +
//								"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
//								"<input type='hidden' name='buyName' value='"+n.value+"'>" +
//								"<input type='hidden' name='saleName' value='"+m.value+"'>" + 
								"<input class='pro_price' type='text' disabled='disabled' name='productPic'></td>" +										
								"<td><input   class='sugprice' name='rPrice' disabled='disabled' type='text' ></td>"));
						trcash.append($(
//								"<td><input class='supplierPrice' readonly='readonly'></td>" +
								"<td>" +
//								"<input type='hidden' name='buyVal' value='"+n.id+"'>" +
//								"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
//								"<input type='hidden' name='buyName' value='"+n.value+"'>" +
//								"<input type='hidden' name='saleName' value='"+m.value+"'>" + 
								"<input class='pro_price' disabled='disabled' type='text' name='supplierPic'></td>" +										
								""));
						tr1.append($("<td><input class='pro_price' disabled='disabled'  type='text' name='skuCode'><input class='pro_price' type='hidden' name='skuCode'></td>" +
								"<td><div class='preview_fake'  id='-"+n.id+"_img' ></div></td>" +
								"<td><input  class='weight' name='weight' type='text' disabled='disabled' >kg/<span class='danwei'>"+measure+"</span></td>"));
				}else{
					tr = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
//							"<td><input  class='supplierPrice' readonly='readonly' type='text' ></td>" +
							"<td><input type='hidden' name='buyVal' value='"+n.id+"'>" +
							"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
							"<input type='hidden' name='buyName' value='"+n.value+"'>" +
							"<input type='hidden' name='saleName' value='"+m.value+"'>" +
							"<input class='pro_price' type='text' disabled='disabled' ></td></tr>");
					trfut = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
//							"<td><input  class='supplierPrice' readonly='readonly' type='text' ></td>" +
							"<td>" +
//							"<input type='hidden' name='buyVal' value='"+n.id+"'>" +
//							"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
//							"<input type='hidden' name='buyName' value='"+n.value+"'>" +
//							"<input type='hidden' name='saleName' value='"+m.value+"'>" +
							"<input class='pro_price' type='text' disabled='disabled' name='productPic'  ></td>" +
							"<td><input class='sugprice' name='rPrice' disabled='disabled' type='text' ></td></tr>");		
					trcash = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
//							"<td><input  class='supplierPrice' readonly='readonly' type='text' ></td>" +
							"<td>" +
//							"<input type='hidden' name='buyVal' value='"+n.id+"'>" +
//							"<input type='hidden' name='saleVal' value='"+m.id+"'>" +
//							"<input type='hidden' name='buyName' value='"+n.value+"'>" +
//							"<input type='hidden' name='saleName' value='"+m.value+"'>" +
							"<input class='pro_price' type='text' disabled='disabled' name='supplierPic'  ></td>" +
							"</tr>");		
					tr1 = $("<tr><td class='chi' id='"+ m.id +"'>"+ m.value +"</td>" +
							"<td><input class='pro_price' disabled='disabled' type='text' name='skuCode'><input class='pro_price' type='hidden' name='skuCode'></td>" +
							"<td><div class='preview_fake'  id='-"+n.id+"_img' ></div></td>" +
							"<td><input   class='weight' disabled='disabled' type='text' name='weight'>kg/<span class='danwei'>"+measure+"</span></td></tr>");
				}
				tbody.append(tr);
				tbodyfut.append(trfut);
				tbodycash.append(trcash);
				tbody1.append(tr1);
			});
			$("#tb-speca-quotation1").append(tbody);
			$("#tb-speca-quotation2").append(tbodyfut);
			$("#tb-speca-quotation3").append(tbodycash);
			$("#tb-tiaoxingma").append(tbody1);
		});
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
			var jyprice = $(item1).find(".sugprice").val();
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
						$(item1).find(".preview_fake").html(tableInfo[k][3]);
						$(item1) .find('.sugprice') .val(tableInfo[k][3]);
						$(item1) .find('.weight') .val(tableInfo[k][4]);
						$(item1) .find('.supplierPrice') .val(tableInfo[k][4]);
					}
				});
			}
		});
	});
}
function checkRadio(){
	var  check_count = $("#pic_count").attr("checked");
	if(check_count == 'checked'){
		$('.tq2') .show();
		$('.tqz .tq4') .hide();
		$('.g .tab_box') .hide();
	}else{
		$('.tqz .tq2') .hide();
		$('.tqz .g') .show();
		$('.g .tab_box') .show();
	}
}