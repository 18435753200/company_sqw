<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<div class="menu-hd">
	<input type="button" value="添加菜单" class="add-menu-btn" id="pIdButton" onclick="getMenuInfo('0','${pId }')"/>
</div>
<div class="menu-bd">
<table width="100%">
<colgroup>
	<col width="30" />
	<col width="70" />
	<col width="130" />
	<col width="110" />
	<col width="110" />
	<col width="60" />
	<col />
</colgroup>
	<tr>
		<th>ID</th>
		<th>名称</th>
		<th>路径</th>
		<th>创建时间</th>
		<th>创建用户</th>
		<th>状态</th>
		<th>操作</th>
	</tr>
	<c:if test="${empty menus}">
		<tr>
			<td colspan="7">
				<center><img src="${path }/commons/images/no.png" /></center>
			</td>
		</tr>
	</c:if>
	<tr></tr>
	<c:forEach items="${menus}" var="menu">
		<tr <c:if test="${menu.status==0 }">style="background-color:#cccccc;border:1px solid #cccccc;"</c:if>>
			<td><p class="brand-td" title="${fn:escapeXml(menu.menuId) }">${fn:escapeXml(menu.menuId) }</p></td>
			<td><p class="brand-td" title="${fn:escapeXml(menu.name) }">${fn:escapeXml(menu.name) }</p></td>
			<td class="ta-l"><p class="brand-td" title="${fn:escapeXml(menu.url) }">${fn:escapeXml(menu.url) }</p></td>
			<td><p class="brand-td" title='<fmt:formatDate value="${menu.createTime}" pattern="yyyy-MM-dd"/>'><fmt:formatDate value="${menu.createTime}" pattern="yyyy-MM-dd"/></p></td>
			<td><p class="brand-td" title='${fn:escapeXml(menu.createBy) }'>${fn:escapeXml(menu.createBy) }</p></td>
			<td><p class="brand-td" title='<c:if test="${menu.status==0 }">未启用</c:if><c:if test="${menu.status==1 }">启用</c:if>'><c:if test="${menu.status==0 }">未启用</c:if><c:if test="${menu.status==1 }">启用</c:if></p></td>
			<td class="t4">
				<c:if test="${menu.status==0 }">
					<a href="javascript:void(0)" onclick="upMenu('${menu.menuId}','${menu.parentMenuId}','${menu.name }')"><span class="cjian">恢复</span></a>
					<a href="javascript:void(0)" onclick="delMenu('${menu.menuId}','${menu.parentMenuId}','${menu.name }','0')"><span class="cjian">删除</span></a>
				</c:if>
				<c:if test="${menu.status==1 }">
					<a href="javascript:void(0)" onclick="getMenuInfo('${menu.menuId}')"><span class="cjian">编辑</span></a>
					<a href="javascript:void(0)" onclick="delMenu('${menu.menuId}','${menu.parentMenuId}','${menu.name }','1')"><span class="cjian">删除</span></a>
				</c:if>
			</td>
		</tr>
	</c:forEach>
</table>
</div>