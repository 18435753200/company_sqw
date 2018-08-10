<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
request.setAttribute("path",path);
%>
	<div class="tb-void">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tbody>
						<tr>
							<th width="150">订单号</th>
							<th width="110">收货人</th>
							<th width="110">收货人电话</th>
							<th width="110">注册手机</th>
							<th width="110">注册姓名</th>
							<th width="200">身份证号</th>
						</tr>
					</tbody>
					
					<tbody>
							<tr>
							  <td><c:out value='${customerOrder["orderNo"]}' />&nbsp;</td>
							  <td><c:out value='${customerOrder["receivename"]}' /></td>
							  <td><c:out value='${customerOrder["receivephone"]}' /></td>
							  <td><c:out value='${customerOrder["usermobile"]}' /></td>
							  <td><c:out value='${customerOrder["userrealname"]}' /></td>
							  <td><c:out value='${customerOrder["useridcard"]}' /></td>
							</tr>
					</tbody>
				</table>
			</div>
