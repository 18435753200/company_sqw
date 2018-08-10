<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
<title>赠券标签属性设置</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${path}/commons/css/went1.css">
<script type="text/javascript" src="${path }/commons/js/oneDiv.js"></script>
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
</head>

<body>

	<%@include file="/WEB-INF/views/include/header.jsp"%>

	<div class="wrap">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->

		<!-- 右边 start -->
		<div class="right">
			<div class="c1">
				<div class="to">
					<ul class="top">
						<c:forEach items="${regionList}" var="rl" varStatus="status">
							<c:if test="${rl.tagCode==companyBy}">
								<li class="list" id="waitlabel"><a href="javascript:void(0)"
								onclick="labelPagezj('${rl.tagCode}')">${rl.tagName}</a>
							</c:if>
							<c:if test="${rl.tagCode!=companyBy}">
								<li class="list"><a href="javascript:void(0)"
								onclick="labelPagezj('${rl.tagCode}')">${rl.tagName}</a>
							</c:if>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="qb">
					<div class="biaogee" id="seelist"></div>
				</div>
			</div>
		</div>
	</div>

	<!-- 右边 end -->

	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>

	<script>
		$(document).ready(function() {
			mchu();
			getList();
			$(this).find(".top").find("li").removeClass("list");
			$(this).find(".top").find("li").eq(0).addClass("list");
		});
		var mchu = function() {
			$(".to .top li").each(
					function(index) {
						$(this).click(
								function() {
									/* ri = $(this).index();
									$(this).addClass('list').siblings()
											.removeClass('list');
									$(".qb .biaogee:eq(" + index + ")").show()
											.siblings().hide(); */
									
									$.ajax({
										type : "post",
										url : "../infrastructure/oneDividendzj",
										data : "companyBy="+tagcode,
										success : function(msg) {
											$(".biaogee").html(msg);
											$(".to .top li a").eq($(this).index()).addClass("list").siblings().removeClass("list");
										},
										error : function() {
											alert("加载失败，稍后再试。");
										}
									});
								})
					});
		}
	
	function getList(){
			$.ajax({
			type : "post",
			url : "../infrastructure/oneDividendzj",
			data : "companyBy=${companyBy}",
			success : function(msg) {
				$(".biaogee").html(msg);
			},
			error : function() {
				alert("加载失败，稍后再试。");
			}
		});
	}
	function labelPagezj(tagcode){
		$.ajax({
			type : "post",
			url : "../infrastructure/oneDividendzj",
			data : "companyBy="+tagcode,
			success : function(msg) {
				$(".biaogee").html(msg);
			},
			error : function() {
				alert("加载失败，稍后再试。");
			}
		});
	}
	</script>
</body>
</html>