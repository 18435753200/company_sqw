<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%  
    String path = request.getContextPath();
    String url = request.getServletPath();
	request.setAttribute("url",url);
	request.setAttribute("path",path);
%>
<link href="${path}/commons/images/favicon1.ico" rel="shortcut icon"/>
<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css">
<script type="text/javascript" src="${path}/commons/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="${path}/commons/js/open.js"></script>
<script type="text/javascript" src="${path}/commons/js/lhgdialog/lhgdialog_zh.js?skin=idialog"></script>
	<script type="text/javascript" >
	window.alert =function(message){
		message = "<span style='line-height:20px;'>"+message+"</span>"
	    $.dialog({ 
		   title:'<h1>众聚商城提示您</h1>',
	 	   content:message,
	 	   lock: true, //遮挡
	 	   max: false, 
	       min: false ,
	       resize:false,
	       width:'290px',
	       height:'120px',
	       button: [
	           {
	         	 name: '确定'
	           }
	           
	       ]
	    });
	};
	tipMessage =function(content ,yes){
	   $.dialog.tipMessage(content,yes);
	};
</script>
<input id="conPath" type="hidden" value="${path}">