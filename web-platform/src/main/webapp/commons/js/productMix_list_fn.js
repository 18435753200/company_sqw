var  CONTEXTPATH  = $("#conPath").val();

$(document).ready(function() {
	clickSubmit(1);
//	addClickSubmit(1);
	addition();
	remNode();
});
function clickSubmit(page) {
    /* 获取信息 */
    var sceneName = $("#sceneName").val();
    var startDate = $("#startDate").val();
    var endDate=$("#endDate").val();
    var pro_array = new Array();
    if (page != "" && page != undefined) {
        pro_array.push("page=" + page);
    }
    if (sceneName != "" && sceneName != undefined) {
        pro_array.push("sceneName=" + sceneName);
    }
    if (startDate != "" && startDate != undefined) {
        pro_array.push("startDate=" + startDate);
    }
    if (endDate != "" && endDate != undefined) {
        pro_array.push("endDate=" + endDate);
    }

    $.ajax({
        type : "post",
        url : CONTEXTPATH + "/productMix/getProductMixByConditions",
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


/* 创建保存数据验证*/
function addition(){
	var oAdd=$('.btn .add')[0];
	var oBac=$('.center1')[0];
	var oTab=$('#tab table tbody')[0];
//   	oAdd.onclick=addClickSubmit;

	var oClose=$('.close')[0];
	oClose.onclick=function(){
		oBac.style.display='none';
		$("#pu_wrap2").html('');
	};
	
}

function addclick(){
	var oAdd=$('.btn .add')[0];
	var oBac=$('.center1')[0];
	var oTab=$('#tab table tbody')[0];
	var oBox=$('.pu_mix')[0];
	var oHeight= $(window).height(); 
	
	var aVer=$('.pu_mix .verify');

	oBac.style.display='block';
	oBox.style.height=oHeight*0.8-153+'px';
	for(var i=0;i<aVer.length;i++){
		aVer[i].onclick=function(){
			var aTr=oTab.getElementsByTagName('tr');
			var oTr=document.createElement('tr');
			var oTd1=document.createElement('td');
			if(aTr.length==1){
				oTd1.innerHTML=1;
			}else{
				for(var i=0;i<aTr.length;i++){
					var oTd=aTr[aTr.length-1].getElementsByTagName('td')[0].innerHTML;
				}
				oTd1.innerHTML=parseInt(oTd)+1;
			}
			//oTr.appendChild(oTd1);
			var oTd2=document.createElement('td');
			oTd2.className='td2'
			var oS=document.createElement('select');
			oS.className = 'groupNum';
			oS.innerHTML='<option value="1">1</option>\
									<option value="2">2</option>\
									<option value="3">3</option>';
			oTd2.appendChild(oS);
			var oInp=document.createElement('input');
			oInp.type='text';
			oInp.value=1;
			oInp.className='inp';
			oTd2.appendChild(oInp);
			oTr.appendChild(oTd2);
			var oTd3=document.createElement('td');
			oTd3.innerHTML=this.parentNode.children[0].innerHTML;
			oTr.appendChild(oTd3);
			var oTd4=document.createElement('td');
			oTd4.innerHTML=this.parentNode.children[1].innerHTML;
			oTr.appendChild(oTd4);
			var oTd5=document.createElement('td');
			oTd5.innerHTML=this.parentNode.children[2].innerHTML;
			oTd5.className='hidde';
			oTr.appendChild(oTd5);
			var oTd6=document.createElement('td');
			oTd6.innerHTML='<input type="checkbox" class="sele isDateil" >';
			oTd6.className='td6';
			oTr.appendChild(oTd6);
			var oTd7=document.createElement('td');
			oTd7.innerHTML=this.parentNode.children[3].innerHTML;
			oTr.appendChild(oTd7);
			var oTd8=document.createElement('td');
			oTd8.innerHTML='<input type="text" value="1" disabled=true  class="skuNum" onkeydown="onlyNum();">';
			oTr.appendChild(oTd8);
			var oTd9=document.createElement('td');
			oTd9.className='verify1';
			oTd9.innerHTML='删除';
			oTr.appendChild(oTd9);
			oTab.appendChild(oTr);
			oTd9.onclick=function(){
				oTab.removeChild(this.parentNode);
			};
			oS.onchange=function(){
				oInp.value=oS.options[oS.selectedIndex].text;
			};
			oBac.style.display='none';
			isDetailCheckbox();
			$("#pu_wrap2").html('');
		};
	}
	
};

function isDetailCheckbox(){
	var aCheck=$('#tab table tbody .sele');
	for(var i=0;i<aCheck.length;i++){
		aCheck[i].onclick=function(){
			if(this.checked==true){
				this.parentNode.parentNode.children[6].children[0].value=1;
				this.parentNode.parentNode.children[6].children[0].disabled='';
			}else{

				this.parentNode.parentNode.children[6].children[0].value='1';
				this.parentNode.parentNode.children[6].children[0].disabled='true';
			}
		};
	}
}


function remNode(){
	$(".verify1").bind("click",function(){ 
		console.log($(this).parent());
		$(this).parent().remove();
	});
}

function onlyNum()
{
  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
    event.returnValue=false;
}

function saveMix(){
	
	var flag = true;
	var pro_array = new Array();
	
	var sceneName = $('#sceneName').val();
	if(sceneName.length > 0 &&  sceneName.length <= 100){
		pro_array.push("sceneName=" + sceneName);
	}else{
        flag = false;
		alert("商品情景组合名称 不能为空并且不能超过100个字!");
		return ;
	}
	var detailImageUrl = $('#detailImageUrl').val();
	if(detailImageUrl.length > 0 && detailImageUrl.length <=80){
		pro_array.push("detailImageUrl=" + detailImageUrl);
	}else{
		flag = false;
		alert("图片不能为空!");
		return ;
	}
	
	var sceneRange = $('#sceneRange').val();
	if (sceneRange != "" && sceneRange != undefined) {
		pro_array.push("sceneRange=" + sceneRange);
	}else{
		flag = false;
		alert("请选择适用范围!");
		return ;
	}
	
	var showStyle = $('#showStyle').val();
	if (showStyle != "" && showStyle != undefined) {
		pro_array.push("showStyle=" + showStyle);
	}else{
		flag = false;
		alert("请选择页面显示排列方式!");
		return ;
	}
	var sceneNum = $('#sceneNum').val();
	if (sceneNum > 0 && sceneNum < 10) {
		pro_array.push("sceneNum=" + sceneNum);
	}else{
		flag = false;
		alert("默认组合商品数量 只能为0-10!");
		return ;
	}

	$('.groupNum').each(function(i){
		var groupNum = $(this).val();
		if (groupNum != "" && groupNum != undefined) {
			pro_array.push("groupNum=" + groupNum);
		}
	});
	
	
	$('.isDateil').each(function(i){
		var isDateil = $(this).attr('checked')
		console.log(isDateil);
		if (isDateil == 'checked') {
			pro_array.push("isDateil=" + 1);
		}else{
			pro_array.push("isDateil=" + 0);
		}
	});

	
	$('.skuNum').each(function(i){
		var skuNum = $(this).val();
		if (skuNum != "" && skuNum != undefined) {
			pro_array.push("skuNum=" + skuNum);
		}
	});
	
	$('.skuId').each(function(i){
		var skuId = $(this).html();
		if (skuId != "" && skuId != undefined) {
			pro_array.push("skuId=" + skuId);
		}
	});
	
	var aTtr=$('#tab table tbody tr');
	var arr=[];
	for(var i = 0 ; i < sceneNum ;i++){
		arr[i] = false;
	}
	
	for(var i=1;i<aTtr.length;i++){
		oInp1=aTtr[i].getElementsByTagName('td')[0].getElementsByTagName('select')[0];
		oChec=aTtr[i].getElementsByTagName('td')[4].getElementsByTagName('input')[0];
		if(oChec.checked==true){
			arr[oInp1.value -1]= !arr[oInp1.value -1];
		}
	}
	for(var i = 0 ; i<arr.length ;i++){
		if(!arr[i]){
			alert('商品组合 每组最少且只能有一个默认商品！');
			flag = false;
			return;
		}
	}
	
	if(flag){
		$(".save").attr("disabled","disabled");
	    $.ajax({
	        type : "post",
	        url : CONTEXTPATH + "/productMix/saveProductMix",
	        data : pro_array.join("&"),
	        dataType : "html",
	        success : function(data) {
				if(data=='1'){
					tipMessage("保存成功，关闭当前页面！",function(){
						location.href=CONTEXTPATH+"/productMix/getMixList";
					});
				}else{
					tipMessage("保存失败，请检查后重试！",function(){
						$(".save").removeAttr("disabled");
					});
				}

			}
	    });
	}
}

function addClickSubmit(page) {
    /* 获取选中类目 */
    var businessProdId = $("#productId2").val();
    var skuCode = $("#skuCode2").val();
    var productName=$("#productName2").val();
    var supplierName=$("#supplierName2").val();
    var pro_array = new Array();
    if (page != "" && page != undefined) {
        pro_array.push("page=" + page);
    }
    if (businessProdId != "" && businessProdId != undefined) {
        pro_array.push("productId=" + businessProdId);
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
            $("#pu_wrap2").html(msg);
            addclick();
        },
        error : function(jqXHR, textStatus, errorThrown) {
            alert("对不起，数据异常请稍后再试!");
        }
    });

}



function reloadOpennerPage(){
	var elnode = window.opener.document.getElementById("pageContext"); 
	var page = $(elnode).val();
	window.opener.location.href="javascript:clickSubmit("+page+")";

	window.opener=null;
	window.open('','_self');
	window.close();

	
	
}


function updateMix(){
	
	var flag = true;
	var pro_array = new Array();
	
	var sceneId = $('#sceneId').val();
	if(sceneId.length > 0 &&  sceneId.length <= 100){
		pro_array.push("sceneId=" + sceneId);
	}else{
        flag = false;
		alert("数据异常!");
		return ;
	}
	
	var sceneName = $('#sceneName').val();
	if(sceneName.length > 0 &&  sceneName.length <= 100){
		pro_array.push("sceneName=" + sceneName);
	}else{
        flag = false;
		alert("商品情景组合名称 不能为空并且不能超过100个字!");
		return ;
	}
	var detailImageUrl = $('#detailImageUrl').val();
	if(detailImageUrl.length > 0 && detailImageUrl.length <=80){
		pro_array.push("detailImageUrl=" + detailImageUrl);
	}else{
		flag = false;
		alert("图片不能为空!");
		return ;
	}
	
	var sceneRange = $('#sceneRange').val();
	if (sceneRange != "" && sceneRange != undefined) {
		pro_array.push("sceneRange=" + sceneRange);
	}else{
		flag = false;
		alert("请选择适用范围!");
		return ;
	}
	
	var showStyle = $('#showStyle').val();
	if (showStyle != "" && showStyle != undefined) {
		pro_array.push("showStyle=" + showStyle);
	}else{
		flag = false;
		alert("请选择页面显示排列方式!");
		return ;
	}
	var sceneNum = $('#sceneNum').val();
	if (sceneNum > 0 && sceneNum < 10) {
		pro_array.push("sceneNum=" + sceneNum);
	}else{
		flag = false;
		alert("默认组合商品数量 只能为0-10!");
		return ;
	}
	
	console.log(sceneNum);

	$('.groupNum').each(function(i){
		var groupNum = $(this).val();
		if (groupNum != "" && groupNum != undefined) {
			pro_array.push("groupNum=" + groupNum);
		}
	});
	
	
	$('.isDateil').each(function(i){
		var isDateil = $(this).attr('checked')
		console.log(isDateil);
		if (isDateil == 'checked') {
			pro_array.push("isDateil=" + 1);
		}else{
			pro_array.push("isDateil=" + 0);
		}
	});

	
	$('.skuNum').each(function(i){
		var skuNum = $(this).val();
		if (skuNum != "" && skuNum != undefined) {
			pro_array.push("skuNum=" + skuNum);
		}
	});
	
	$('.skuId').each(function(i){
		var skuId = $(this).html();
		if (skuId != "" && skuId != undefined) {
			pro_array.push("skuId=" + skuId);
		}
	});
	
	var aTtr=$('#tab table tbody tr');
	var arr=[];
	for(var i = 0 ; i < sceneNum ;i++){
		arr[i] = false;
	}
	
	for(var i=1;i<aTtr.length;i++){
		oInp1=aTtr[i].getElementsByTagName('td')[0].getElementsByTagName('select')[0];
		oChec=aTtr[i].getElementsByTagName('td')[4].getElementsByTagName('input')[0];
		if(oChec.checked==true){
			arr[oInp1.value -1]= !arr[oInp1.value -1];
		}
	}
	for(var i = 0 ; i<arr.length ;i++){
		if(!arr[i]){
			alert('商品组合 每组最少且只能有一个默认商品！');
			flag = false;
			return;
		}
	}
	if(flag){
		$(".save").attr("disabled","disabled");
	    $.ajax({
	        type : "post",
	        url : CONTEXTPATH + "/productMix/updateMix",
	        data : pro_array.join("&"),
	        dataType : "html",
	        success : function(data) {
				if(data=='1'){
					tipMessage("修改成功，关闭当前页面！",function(){
						location.href=CONTEXTPATH+"/productMix/getMixList";
					});
				}else{
					tipMessage("修改失败，请检查后重试！",function(){
						$(".save").removeAttr("disabled");
					});
				}

			}
	    });
	}

}

function openWin(){
	console.log($('#mixImageUrl').val());
	var url = $('#mixImageUrl').val()
	if (url == null || url == undefined || url == '') {
		alert('请上传图片');
		return;
	}
	window.open(url);
}

function deleteMix(mixId){
	if(confirm('确定要执行此操作吗?'))
    {
	    $.ajax({
	        type : "get",
	        url : CONTEXTPATH + "/productMix/deleteMix?mixId="+mixId,
	        dataType : "html",
	        success : function(data) {
				if(data=='1'){
					tipMessage("修改成功，关闭当前页面！",function(){
						location.href=CONTEXTPATH+"/productMix/getMixList";
					});
				}else{
					tipMessage("修改失败，请检查后重试！",function(){
						$(".save").removeAttr("disabled");
					});
				}

			}
	    });
    }
}

function updateStats(mixId){
	if(confirm('确定要执行此操作吗?'))
    {
	    $.ajax({
	        type : "get",
	        url : CONTEXTPATH + "/productMix/updateMixTate?mixId="+mixId,
	        dataType : "html",
	        success : function(data) {
				if(data=='1'){
					tipMessage("修改成功，关闭当前页面！",function(){
						location.href=CONTEXTPATH+"/productMix/getMixList";
					});
				}else{
					tipMessage("修改失败，请检查后重试！",function(){
						$(".save").removeAttr("disabled");
					});
				}

			}
	    });
    }
}
