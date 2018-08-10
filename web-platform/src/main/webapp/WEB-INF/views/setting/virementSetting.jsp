<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <title>转账设置</title>
    <%@include file="/WEB-INF/views/include/base.jsp" %>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/went1.css">
    <style>
        input{
            width: 80px;
        }
    </style>
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp" %>
<div class="center">
    <!-- 左边 start -->
    <div class="left f_l">
        <%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
    </div>
    <!-- 左边 end -->
    <!-- 右边 start -->
    <div class="right">
        <div class="c21">
            <div class="title">
                <p>卖家中心&nbsp;&gt;&nbsp; </p>
                <p>基础设置&nbsp;&gt;&nbsp; </p>
                <p class="c1">转账设置</p>
            </div>
        </div>
        <div class="blank5"></div>
        <div class="qb">
            <div class="cont biaogee">
            <form id="MyForm" action="/setting/saveVirementSetting" method="post">
                <table>
                	<tr>
                		<th colspan="2">
                			<c:if test="${not empty message}">
								<c:choose>
									   <c:when test="${message == 'success'}">
											更新成功!
									   </c:when>
									   <c:otherwise>
											更新失败!
									   </c:otherwise>
								</c:choose>
							</c:if>

						</th>
                	</tr>
                    <tr>
                        <th style="width:50%;">类型</th>
                        <th style="width:50%;">计量单位</th>
                    </tr>
                    <tr>
                        <td>转账手续费<input type="hidden"
                        id="virementId"
                        name="virementId"
                        value="${vs.id}"/>
                        </td>
                        <td><input type="text"
                        maxlength="11"
                        name="virementFee"
                        id="virementFee"
                        value="${vs.virementFee}"/>%</td>
                    </tr>
                    <tr>
                        <td>转账手续费上限</td>
                        <td><input type="text"
                        maxlength="11"
                        name="virementFeeUpperLimit"
                        id="virementFeeUpperLimit"
                        value="${vs.virementFeeUpperLimit}"/>券</td>
                    </tr>
                    <tr>
                        <td>转账日上限</td>
                        <td><input type="text"
                        maxlength="11"
                        name="virementFeeUpperLimitDay"
                        id="virementFeeUpperLimitDay"
                        value="${vs.virementFeeUpperLimitDay}"/>券</td>
                    </tr>
                    <tr>
						<td>转账单笔上限</td>
						<td><input type="text"
						maxlength="11"
						name="virementFeeUpperLimitDeal"
						id="virementFeeUpperLimitDeal"
						value="${vs.virementFeeUpperLimitDeal}"/>券</td>
					</tr>
                    <tr class="x1">
                        <td colspan="2" align="center" class="p3">
                            <input type="button" class="tjiao" onclick="javascript:saveCashierSettings();" value="保存"/>
                        </td>
                    </tr>
                </table>
			</form>
            </div>
        </div>
    </div>
    <!-- 右边 end -->
</div>
<div class="blank10"></div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp" %>
</body>
<script type="text/javascript">
    function saveCashierSettings() {
    	var id = $("#virementId").val();
		var virementFee = $("#virementFee").val();
		var virementFeeUpperLimit = $("#virementFeeUpperLimit").val();
		var virementFeeUpperLimitDay = $("#virementFeeUpperLimitDay").val();
		var virementFeeUpperLimitDeal = $("#virementFeeUpperLimitDeal").val();
		if (isNaN(virementFee)) {
			alert('请正确填写 "转账手续费" ！')
			return "0";
		}

		if(parseFloat(virementFee) >= 100 ||  parseFloat(virementFee) < 0){
			alert('请正确填写 "转账手续费" ！')
			return "0";
		}

		if (isNaN(virementFeeUpperLimit) || parseFloat(virementFeeUpperLimit) < 0) {
			alert('请正确填写转 "账手续费上限" ！')
			return "0";
		}

		if (isNaN(virementFeeUpperLimitDay) || parseFloat(virementFeeUpperLimitDay) < 0) {
			alert('请正确填写 "转账日上限" ！')
			return "0";
		}
		if (isNaN(virementFeeUpperLimitDeal) || parseFloat(virementFeeUpperLimitDeal) < 0) {
			alert('请正确填写 "转账单笔上限" ！')
			return "0";
		}

		$("#MyForm").submit();
    }
</script>
</html>