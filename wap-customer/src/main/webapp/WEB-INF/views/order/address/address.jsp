<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:if test="${empty receiveAddress}">
	<div class="add-address">
		<a href="javascript:selectReceiveAddress(null);" class="address-add"><i>新建地址</i></a>
        <span class="ico_rarr"><i></i></span>
	</div>
</c:if>
<div
	class="item address
		<c:if test="${empty receiveAddress}"> hide</c:if>
	">
	<input type="hidden" value="${receiveAddress.addressId}" id="addressId" />
	<a href="javascript:selectReceiveAddress(${receiveAddress.addressId});">

		<p class="u_name">
			<span class="contactor">${receiveAddress.contactor}</span>
			<tel class="tel">${receiveAddress.mobile}</tel>
		</p>
		<p class="u_address">
			<span class="address">${receiveAddress.provinceCityCountyName} ${receiveAddress.address}</span>
		</p>
	</a>
</div>

