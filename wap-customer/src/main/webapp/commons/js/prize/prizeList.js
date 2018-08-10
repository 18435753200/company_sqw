var check = true;
// 统计埋码
var _trackDataType = 'wap'; // 标记数据来源，参数是web和wap，可以为空，默认是web
var _Schannel_website_id = '10000006';// 分站编号，不存在可不写此变量或者留空
var _trackData = _trackData || [];// 必须为全局变量，假如之前并没有声明，请加此行代码；
var userId='433';
$(function() {
	loadPrize();
	//function sendUid(asd){
	//	userId=asd;
	//	//if(userId==''){
	//	//	$.dialog({
	//	//		content : '获取用户ID失败，请重新登陆！',
	//	//		title : '众聚猫提示',
	//	//		time: 2000
	//	//	});
	//	//}
	//}
});


function loadPrize() {
	var url = $("#path").val()+"/prize/getPrizeListForPersonal";
	var data ="userId="+userId;
	var dataType = "json";
	var path = $("#path").val();
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		async : false,
		dataType : dataType,
		success : function(data) {

			var jsonObj=eval(data);
			if(null!=jsonObj && jsonObj.length>0){
				for(var i=0;i<jsonObj.length;i++){
					var ur=path+"/prize/goWinPrize?recordId="+jsonObj[i].recordId;
					var showUrl=path+"/prize/showPrize?recordId="+jsonObj[i].recordId;
					if(jsonObj[i].type=='1'){
						if(jsonObj[i].status=='0'){
							$("#prList").append('<li class="pic"><img src="'+jsonObj[i].prizeImg+'"><div class="draw-listwen"><h2>获得'+jsonObj[i].prizeTitle+'</h2><p>'+jsonObj[i].createDate+'</p><a href="'+ur+'">领奖</a></div></li>');
						}
						if(jsonObj[i].status=='1'){
							$("#prList").append('<li class="pic"><img src="'+jsonObj[i].prizeImg+'"><div class="draw-listwen"><h2>获得'+jsonObj[i].prizeTitle+'</h2><p>'+jsonObj[i].prizeDate+'</p><a href="'+showUrl+'">查看</a></div></li>')
						}
						if(jsonObj[i].status=='2'){
							$("#prList").append('<li class="pic"><img src="'+jsonObj[i].prizeImg+'"><div class="draw-listwen"><h2>获得'+jsonObj[i].prizeTitle+'</h2><p>'+jsonObj[i].createDate+'</p><span >实物奖品已发放</span></div></li>');
							//$("#prList").append('<li class="pic"><img src="'+jsonObj[i].prizeImg+'"><div class="draw-listwen"><h2>'+jsonObj[i].prizeTitle+'</h2><p>'+jsonObj[i].prizeDate+'</p><a href="'+ur+'">领奖</a></div></li>')
						}
						if(jsonObj[i].status=='3'){
							$("#prList").append('<li class="pic"><img src="'+jsonObj[i].prizeImg+'"><div class="draw-listwen"><h2>获得'+jsonObj[i].prizeTitle+'</h2><p>'+jsonObj[i].prizeDate+'</p><a href="'+showUrl+'">查看</a></div></li>');
						}
					}else{
						$("#prList").append('<li class="pic"><img src="'+jsonObj[i].prizeImg+'"><div class="draw-listwen"><h2>获得'+jsonObj[i].prizeTitle+'</h2><p>'+jsonObj[i].createDate+'</p><span >已发放到您的账户</span></div></li>');
					}
				}
			}else{
				$("#prList").append('<li class="pic">您还未有获奖记录！</li>');
			}

		}
	});
}
