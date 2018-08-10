window.onload=function(){ 
	var s = _share_getQueryString("shareUserId");
	if(s!=null){
		$.ajax({
		  type: "post",
		  async:false,
		  url : window.location.protocol+"//"+window.location.host+"/customer/recordShareUserIdCookie?shareUserId="+s,
		  dataType:"jsonp",
		  data:{},
		   success:function(json){
			   
			   
		   }
	  	});
	}
} 




function _share_getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return decodeURIComponent(r[2]); return null;
} 