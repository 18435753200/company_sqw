<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="height: 100%">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"> 
<%@include file="/WEB-INF/views/commons/base.jsp"%>
<link rel="stylesheet" href="${staticFile_s}/commons/css/mui.min.css">
<title>商家定位</title>
<script>
    window.addEventListener('message', function(event) {
        // 接收位置信息，用户选择确认位置点后选点组件会触发该事件，回传用户的位置信息
        var loc = event.data;
        if (loc && loc.module == 'locationPicker') {   //防止其他应用也会向该页面post信息，需判断module是否为'locationPicker'
          console.log('location', loc);
        //给隐藏域赋值
        	$("#lat").val(loc.latlng.lat);
			$("#lng").val(loc.latlng.lng);
			$("#poiaddress").val(loc.poiaddress);
			$("#poiname").val(loc.poiname);
			$("#cityname").val(loc.cityname);
        }
    }, false);
</script>
</head>
<body style="height: 100%">
		<header id="header" class="mui-bar mui-bar-nav">
			<span class="mui-icon mui-icon-back" onclick="javascript:history.back(-1)" id="returnPgae"></span>
			<h1 class="mui-title">商家定位</h1>
			<button class="mui-btn mui-btn-blue mui-btn-link mui-pull-right" onclick="saveLocation()">保存</button>
		</header>
			<input id="lat" type="hidden" name="locationLat" > 
			<input id="lng" type="hidden" name="locationLng" > 
			<input id="poiaddress" type="hidden" name="locationPoiaddress" > 
			<input id="poiname" type="hidden" name="locationPoiname"  > 
			<input id="cityname" type="hidden" name="locationCityname" > 
			<input id="supplierId" type="hidden" name="supplierId" value="${supplierId }"> 
			<input id="nameJC" type="hidden" name="nameJC" value="${nameJC }"> 
		<table  style="width:100%;height:93%;  position: relative;    top: 4rem;">
		  <tr >
		    <td >
		    	<iframe id="mapPage" width="100%" height="100%" frameborder=0
	  		  		src="https://apis.map.qq.com/tools/locpicker?search=1&type=1&key=W6SBZ-SR5KW-WKQR6-OAFPW-S2IZQ-H6FBZ&referer=m.zhongjumall.com">
				</iframe>
		    </td>
		  </tr>
	</table>
	
</body>
<script type="text/javascript">



	//保存商家位置
	function saveLocation(){
		var lat=$("#lat").val();
		var lng=$("#lng").val();
		var poiaddress=$("#poiaddress").val();
		if(lat=='' && lng=='' && poiaddress==''){
			alert("请选择您的店铺位置!");
		}else{
			if(confirm("确定保存你的店铺位置吗")){
				var supplierId=$("#supplierId").val();
				var locationPoiname=$("#poiname").val();
				var locationCityname=$("#cityname").val();
				if(locationPoiname=="我的位置"){
					var nameJC=$("#nameJC").val();
					locationPoiname=nameJC;
				}
				$.post(
					'${staticFile_s}/supplier/addLocation',		
					{'locationLat':lat,'locationLng':lng,'locationPoiaddress':poiaddress,'supplierId':supplierId,'locationPoiname':locationPoiname,'locationCityname':locationCityname},
					function(data){
						alert("保存成功");
						self.location=document.referrer;
					}
				);
			}
		}
	}
</script>
</html>