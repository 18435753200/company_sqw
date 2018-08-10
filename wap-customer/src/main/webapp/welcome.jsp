<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>DEMO</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=path %>/commons/js/jquery.min.js"></script>

    <script type="text/javascript">
       $(function(){
       alert(<%=path %>)
          $.ajax({
				type : "post", 
				url : <%=path %>+"systemtime/systemMillis",
				dataType:"jsonp",
				success : function(msg) {
				   alert(msg);
				},		
				error:function(){
		           alert("异常");
				}
		  });
       
       });
    </script>
  </head>
  
  <body>
    This is my DEMO page. <br>
  </body>
</html>
