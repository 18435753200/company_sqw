<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <title>商品成本价设置</title>
    <%@include file="/WEB-INF/views/include/base.jsp" %>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css"/>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/went1.css"/>
    <style>
        input{
            width: 80px;
        }
    </style>

    <script type="text/javascript">
        function saveSettings() {
    		var costPriceMultiple = $("#costPriceMultiple").val();
    		if (isNaN(costPriceMultiple)) {
    			alert('请正确填写 "商品价格倍数" ！')
    			return "0";
    		}

    		if(parseFloat(costPriceMultiple) >= 100 ||  parseFloat(costPriceMultiple) < 0){
    			alert('请正确填写 "商品价格倍数" ！')
    			return "0";
    		}

    		$("#MyForm").submit();
        }
    </script>
    <script type="text/javascript">
        function saveSettings1() {
    		var costPriceMultiple = $("#costPriceMultiple1").val();
    		if (isNaN(costPriceMultiple)) {
    			alert('请正确填写 "商品价格倍数" ！')
    			return "0";
    		}

    		if(parseFloat(costPriceMultiple) >= 100 ||  parseFloat(costPriceMultiple) < 0){
    			alert('请正确填写 "商品价格倍数" ！')
    			return "0";
    		}

    		$("#MyForm1").submit();
        }
    </script>
    <script type="text/javascript">
        function saveSettings2() {
    		var costPriceMultiple = $("#costPriceMultiple2").val();
    		if (isNaN(costPriceMultiple)) {
    			alert('请正确填写 "商品价格倍数" ！')
    			return "0";
    		}

    		if(parseFloat(costPriceMultiple) >= 100 ||  parseFloat(costPriceMultiple) < 0){
    			alert('请正确填写 "商品价格倍数" ！')
    			return "0";
    		}

    		$("#MyForm2").submit();
        }
    </script>
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
					<p class="c1">商品成本价设置</p>
				</div>
			</div>
			<div class="blank5"></div>
			<div class="qb">
				<div class="cont biaogee">
					<form id="MyForm" action="${path}/setting/saveProductCostPriceSetting" method="post">
					<table>
						<tr>
							<th >商品类别</th>
							<th >商品价倍数</th>
							<th >操作</th>
							
						</tr>
						<tr>
							<td>
								普通商品<input type="hidden" id="costPriceId" name="costPriceId" value="${pcp.id}"/>
								<input type="hidden" id="type" name="type" value="1"/>
							</td>
							<td>
								<input type="text" maxlength="5" name="costPriceMultiple" id="costPriceMultiple" value="${pcp.costPriceMultiple}"/>倍
							</td>
							<td >
								<input type="button" class="tjiao" onclick="javascript:saveSettings();" value="保存"/>
							</td>
						</tr>
					</table>
					</form>
					<form id="MyForm1" action="${path}/setting/saveProductCostPriceSetting" method="post">
					<table>
						<tr >
						<td>
								家庭号商品<input type="hidden" id="costPriceId" name="costPriceId" value="${pcp1.id}"/>
								<input type="hidden" id="type" name="type" value="2"/>
							</td>
							<td>
								<input type="text" maxlength="5" name="costPriceMultiple" id="costPriceMultiple1" value="${pcp1.costPriceMultiple}"/>倍
							</td>
							<td >
								<input type="button" class="tjiao" onclick="javascript:saveSettings1();" value="保存"/>
							</td>
						</tr>
					</table>
					</form>
					<form id="MyForm2" action="${path}/setting/saveProductCostPriceSetting" method="post">
					<table>
						<tr >
						<td>
								幸福购商品<input type="hidden" id="costPriceId" name="costPriceId" value="${pcp2.id}"/>
								<input type="hidden" id="type" name="type" value="3"/>
							</td>
							<td>
								<input type="text" maxlength="5" name="costPriceMultiple" id="costPriceMultiple2" value="${pcp2.costPriceMultiple}"/>倍
							</td>
							<td >
								<input type="button" class="tjiao" onclick="javascript:saveSettings2();" value="保存"/>
							</td>
						</tr>
					</table>
					</form>
				</div>
			</div>
		</div>


    </div>

	<div class="blank10"></div>


	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp" %>
</body>
</html>