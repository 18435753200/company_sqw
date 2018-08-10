<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<meta charset="UTF-8">
<%@include file="/WEB-INF/views/include/base.jsp"%>
	 <script type="text/javascript" src="${path }/commons/js/pdfobject.js"></script>
	 <script type="text/javascript">
	 	window.onload = function (){
    		var success = new PDFObject({ url: "${name}" ,pdfOpenParams: { scrollbars: '0', toolbar: '0', statusbar: '0'}}).embed();
		};
	 </script>
</head>
</html>
