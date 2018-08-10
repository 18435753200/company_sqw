function goBack(){var a=window.location.href;if(/#top/.test(a)){window.history.go(-2);window.location.load(window.location.href)}else{window.history.back();window.location.load(window.location.href)}}
$(function(){
	 //搜索关键字提示
		(function() {
		    // 淘宝
		    var dataUrl = $("#path").val()+'/searchController/getProMessage';
		    new KISSY.Suggest('keyWord', dataUrl,
		        { autoFocus: false,
		        });
		})();
  });
  
 function search(){
         var keywords=$("#keyWord").val();
         if (keywords.replace(/(^s*)|(s*$)/g, "").length ==0) {
                  document.getElementById("keyWord").setAttribute("placeholder","请输入搜索关键字");
                  $("#searchInput").focus();
                 return;
        }else{
        window.location.href=$("#path").val()+"/searchController/toSearchResult?keyword="+keywords;
      }

  }
  document.onkeydown = function (event) {
        var e = event || window.event ;
         if((e.keyCode || e.which) == 13){
          search();
       }
   };
