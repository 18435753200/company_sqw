<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
<title>UNICORN-限购标签属性设置</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${path}/commons/css/went1.css">
<script type="text/javascript" src="${path }/commons/js/user/complaint_retailer_fn.js"></script>
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script type="text/javascript">
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
	$(function(){
		    var i=1;//退出递归的条件变量
		    function newRequest(){
		    	var rows=document.getElementsByName("onsubmit");
		        if(i>=rows.length+1){ //infos是存放主机名称的数组
		            return;
		        }
		        var tagCode = $("#tagCode"+i).val();
		            $.ajax({
		            	url : CONTEXTPATH+"/complaint_retailer/xgselect?tagCode="+tagCode,   
		                contentType:'application/json;charset=UTF-8',
		                type:"get",
		                dataType:"json",
		                success : function(msg){
		                	if(msg!=null&&msg!=undefined){
		                    $("#startTime"+i).val(new Date(msg.startTime).Format("yyyy-MM-dd hh:mm:ss"));
		    				$("#endTime"+i).val(new Date(msg.endTime).Format("yyyy-MM-dd hh:mm:ss"));
		    				$("#maxNumber"+i).val(msg.maxNumber);
		    				$("#cycle"+i).val(msg.cycle);
		    				$("#limitWords"+i).val(msg.limitWords);   
		                	}
		                    i++;
		                 newRequest();
		                },
		                error:function(){
		    				alert("加载失败，稍后再试。");
		    			}
		                
		            });
		    }
		    newRequest();
		
	})
	function xgdeleted(tagCode){
		$.ajax({
        	url : CONTEXTPATH+"/complaint_retailer/xgdeleted?tagCode="+tagCode,   
            contentType:'application/json;charset=UTF-8',
            type:"get",
            dataType:"json",
            success : function(msg){
            	alert("重置成功");
            },
            error:function(){
				alert("加载失败，稍后再试。");
			}
            
        });
	}
	
</script>
</head>
<body>

	<%@include file="/WEB-INF/views/include/header.jsp"%>

	<div class="wrap">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->

		<!-- 右边 start -->
		<div class="right">
			<div class="c1">
				<div class="xia">
					 <c:forEach items="${list }" var="xg" varStatus="status">
					<form id="fm" action="${path }/complaint_retailer/xgSubmit" method="post">
					    <thead>
					       <tr>
					          <td>
					         <h2 style="line-height:30px;">标签名称 :${xg.tagName }</h21> 
					          <input type="hidden"  id="tagCode${status.count}" name="tagCode" value="${xg.tagCode }">
					          </td>
					          <td></td>
					       </tr>
					    </thead>
							<br>
					    <tbody>
					        <tr>
					           <td>开始时间 </td>
					           <td>
					              	<input style="width:100px;" class="text1" name="startTime" id="startTime${status.count}" value="" type="text" onclick="WdatePicker()">
					           </td>
					           <td>结束时间</td>
					           <td>
								<input style="width:100px;" class="text1" name="endTime" id="endTime${status.count}" value="" type="text" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startTime${status.count}\')}'})">
					           </td>
					           <td>限购数量</td>
					           <td><input style="width:50px;" id="maxNumber${status.count}" name="maxNumber" class="text1" type="text"></td>
					           <td>限购周期(天)</td>
					           <td><input style="width:50px;" id="cycle${status.count}" name="cycle" class="text1" type="text"></td>
					           <td>限购提示</td>
					           <td><input style="width:150px;" id="limitWords${status.count}" name="limitWords" type="text"></td>
					        </tr>
							<button name="onsubmit" type="submit" >保存</button>
					     </tbody>
					</form>
					     </c:forEach>
				</div>
			</div>
		</div>
	</div>

	<!-- 右边 end -->

	<div class="blank10"></div>
	<input id="labelPage" name="labelPage" type="hidden" value="${labelPage}"/>
	<input id="page" name="page" type="hidden" value="${page}"/>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>

</body>
</html>