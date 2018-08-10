<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.mall.mybatis.utility.PageBean" %>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${path}/commons/css/went1.css">
<form method="post" id="oneDivs" enctype="multipart/form-data">
<table>
	<tr>
		<th style="width:25%;">类型</th>
		<th style="width:25%;">专区名称</th>
		<th style="width:25%;">赠送红旗劵(%)</th>
		<th style="width:25%;">赠送分红额度(%)</th>
		<th style="width:20%; display: none;">周期返还时间</th>
	</tr>
	<c:if test="${empty ondDivList}">
		<tr>
			<td colspan="6" style="align:center"><img src="${path }/commons/images/no.png" />
			</td>
		</tr>
	</c:if>
	<c:if test="${!empty ondDivList}">
	<c:forEach items="${ondDivList}" var="oneDiv">
		<tr>
			<td>	<c:if test="${oneDiv.dividendType == 1}">现金</c:if>
					<c:if test="${oneDiv.dividendType == 2}">红旗劵</c:if>
					<c:if test="${oneDiv.dividendType == 3}">现金+红旗劵</c:if>
 			</td>
			<td>${companyText}
				<input type="hidden" name="company" value="${oneDiv.tagCode}" />
			</td>
			<td>
				<input type="hidden" name="dividendId" value="${oneDiv.id}"/>
				<input type="text" name="giftHqj" value="${oneDiv.giftHqj}"/>
			</td>
			<td>
				<input type="text" name="giftFhed" value="${oneDiv.giftFhed}"/>
			</td>
			<td style="display: none;">
				<input type="text" name="retDate" value="${oneDiv.operateDate}"/>
			</td>
		</tr>
	</c:forEach>
	<tr class="x1">
		<td colspan="5" align="center" class="p3">
			<input type="button" class="tjiao" onclick="toSubmitRplyzj()" value="保存" />
		</td>
	</tr>
	</c:if>
</table>
</form>
<script>
function toSubmitRplyzj(){
	var flag = true;
	$("input[name='giftHqj']").each(function(){
		if((!RegExp("^\\d{1,5}\\.\\d+$").test($(this).val()) && !RegExp("^\\d{1,5}\\.?$").test($(this).val()))|| Number($(this).val())<=0){
			 alert("请正确填写赠送红旗劵额度！");
			 flag = false;
		 }
	});
	$("input[name='giftFhed']").each(function(){
		if((!RegExp("^\\d{1,5}\\.\\d+$").test($(this).val()) && !RegExp("^\\d{1,5}\\.?$").test($(this).val()))|| Number($(this).val())<=0){
			 alert("请正确填写赠送分红额度！");
			 flag = false;
		 }
	});
	/*$("input[name='retDate']").each(function(){
		if(!RegExp("^\\d{1,3}$").test($(this).val()) || $(this).val() =="0"){
			alert("请正确填写返还时间！");
			flag = false;
		}
	});*/
	if(flag){
		var type = $("input[name='company']").eq(0).val();
		alert(type);
		$.ajax({
			type : "post", 
			url : CONTEXTPATH+"/infrastructure/updateOneDividendzj", 
			data: $('#oneDivs').serialize(),
			success : function(msg) { 
				if(msg==1){
					alert("保存成功");
					window.location.href=CONTEXTPATH+"/infrastructure/oneDividendzj?companyBy="+type;
				}else{
					alert("保存失败 稍后再试");
				}
			},
			error:function(){
				alert("加载失败，稍后再试。");
			}
		}); 
	}
}

</script>