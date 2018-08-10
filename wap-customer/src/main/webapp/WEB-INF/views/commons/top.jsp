<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="<%=request.getContextPath() %>"/>
<header id="header" class="homeHead">
    <h1 class="logo"><a href="${path}/index/index.html"><span class="icon i-logo"></span></a></h1>
    <div class="aside"><a href="${path }/searchController/toSearchPage"><span class="icon i-search"></span></a><a href="${path}/customer/toMy"><span class="icon i-user"></span></a><a href="${path}/cart/index"><span class="icon i-cart"></span></a></div>
</header>