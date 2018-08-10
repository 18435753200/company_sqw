$(document).ready(function (){
	    var oBtn = document.getElementById('clickbtn'); // 假设按钮的id为btn
        var oDiv = document.getElementById('jump'); // 假设弹出层的id为div
        window.onload = pageLoad1;
        function pageLoad1(){
           document.getElementById("jump").style.display = "none";
        }
        $("#clickbtn").click(function(){
        	if (oDiv.style.display == 'none') { // 如果层是隐藏的
                   $("#jump").show();
            } else { // 如果层是显示的
                   $("#jump").hide();
            }
        })
});