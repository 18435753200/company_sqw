<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp"%>
<link href="${staticFile_s}/commons/css/reset.css" rel="stylesheet" type="text/css">
<%-- zepto alert --%>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/flexible/flexible.js"></script>
<title>我的团队</title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp"%>
</head>
<style>
html {
	background-color: #F0EFF5;
}

.opercdbox {
	width: 100%;
}

.opercdbox .header {
	width: 100%;
	height: 1.3rem;
	line-height: 1.3rem;
	text-align: center;
	font-size: 0.5rem;
	border-bottom: 1px solid #DDDDDD;
	position: fixed;
	z-index: 99;
	background: #fff;
	max-width: 640px;
	background-color: #e60012;
	color: #ffffff;
}

.opercdbox .gobak {
	width: 12%;
	height: 1.3rem;
	line-height: 1.3rem;
	position: absolute;
	top: 0;
	left: 0;
	background: url("${path }/commons/images/gobak1.png") no-repeat center
		center;
	background-size: 25%;
	background-color: #e60012;
	border-bottom: 1px solid #DDDDDD;
}

.opercdbox .wrap {
	width: 100%;
	margin-top: 1.3rem;
	z-index: 9;
}

.wrap-item .wi-title {
	height: 1.3rem;
	line-height: 1.3rem;
	font-size: 0.4rem;
	padding: 0 0.5rem;
	background: #fff;
	position: relative;
	border-bottom: 1px solid #DDDDDD;
}

.wrap-item .wi-title:after {
	content: '';
	background: url("${path }/commons/images/goin.png");
	position: absolute;
	top: 0.45rem;
	right: 0.5rem;
	height: 0.4rem;
	width: 0.4rem;
	background-size: contain;
	background-repeat: no-repeat;
	-webkit-transition: all 0.2s;
	-moz-transition: all 0.2s;
	-ms-transition: all 0.2s;
	-o-transition: all 0.2s;
	transition: all 0.2s;
}

.wi-wrap {
	width: 100%;
	padding: 0 0.2rem;
	background: #fff;
	border-bottom: 1px solid #dddddd;
}

ul.wi-list {
	background-color: #ffffff;
}

ul.wi-list>li {
	width: 100%;
	padding: 0.3rem 0;
	border-bottom: 1px solid #DDDDDD;
}

ul.wi-list>li:last-child {
	border-bottom: 0;
}

.wrap-item .wi-info {
	overflow: hidden;
	width: 92%;
	margin:0 4%;
	height: 1.3rem;
}

.wi-list>li>.wi-info {
	padding: 0 0 0.3rem;
	box-sizing: content-box;
}

.wrap-item .wi-info>div {
	float: left;
	height: 1.3rem;
	line-height: 1.3rem;
}

.wrap-item .wi-info .userphoto img {
	width: 1.3rem;
	height: 1.3rem;
	border-radius: 50%;
}

.wrap-item .wi-info .userid {
	height: 1.3rem;
	line-height: 0.45rem;
	width: 58%;
}

.wrap-item .wi-info .userid>div:nth-child(1) {
	font-size: 0.4rem;
	text-overflow: ellipsis;
	-webkit-line-clamp: 1;
	-webkit-box-orient: vertical;
	overflow: hidden;
}

.wrap-item .wi-info .userid>div:nth-child(2) {
	color: #888888;
}

.wrap-item .wi-info .usertel {
	float: right;
	width: 27%;
	font-size: 0.4rem;
	color: #888888;
	line-height: 0.65rem;
}

.teamlist {
	display: block;
	max-height: 4rem;
	width: 95%;
	clear: both;
	background: #efefef;
	border-radius: 10px;
	position: relative;
	right: -0.5rem;
	top: 0;
	overflow: scroll;
}

.teamlist li {
	padding: 0 0.2rem;
}

.teamlist li .wi-info {
	padding: 0.2rem 0;
	box-sizing: content-box;
	border-bottom: 1px solid #D0D0D0;
}

.teamlist li:last-child .wi-info {
	border-bottom: 0;
}

/*li close begin*/
.opercdbox .liClose>.wi-info {
	padding: 0;
}

.opercdbox .liClose>.teamlist {
	display: none;
}

/*li close end*/

/*li open begin*/
.opercdbox .liOpen>.wi-info {
	padding: 0 0 0.3rem;
}

.opercdbox .liOpen>.teamlist {
	display: block;
}

/*li open end*/

/*item close begin*/
.opercdbox .itemClose .wi-title:after {
	transform: rotate(-90deg);
}

.opercdbox .itemClose>.wi-wrap {
	display: none;
}

/*item close end*/

/*item open begin*/
.opercdbox .itemOpen .wi-title:after {
	transform: rotate(0deg);
}

.opercdbox .itemOpen>.wi-wrap {
	display: block;
}

/*item open begin*/
ul.wi-list>li {
	position: relative;
}

.add:before {
	content: '';
	position: absolute;
	top: 0.13rem;
	left: 1.1rem;
	background: url("${path }/commons/images/icnadd.png") no-repeat center
		center;
	background-size: contain;
	width: 0.4rem;
	height: 0.4rem;
}

ul.wi-list>li.liOpen:after {
	content: '';
	position: absolute;
	top: 1.6rem;
	left: 0.8rem;
	background: url("${path }/commons/images/icnarrow.png") no-repeat center
		center;
	background-size: contain;
	width: 0.4rem;
	height: 0.4rem;
}

.list-bottom {
	text-align: center;
	width: 100%;
}
.mt{
    margin-bottom: 0.1rem;
    margin-right: 0.2rem;
}

</style>
<body>
	<%-- hidden域 --%>
	<input type="hidden" id="path" value="<%=path%>" />
	<input type="hidden" id="basePath" value="<%=basePath%>" />
	<input type="hidden" id="userPage"
		value="${team.userGroupDto.subFirstUserList.page}" />
	<input type="hidden" id="userTotalPage"
		value="${team.userGroupDto.subFirstUserList.totalPage}" />

	<input type="hidden" id="companyPage" value="${team.supplierList.page}" />
	<input type="hidden" id="companyTotalPage"
		value="${team.supplierList.totalPage}" />
	<div class="opercdbox">
		<div class="header">
			<a class="gobak" onclick="goBack();"></a> 我的团队
		</div>
		<div class="wrap"> 

			<div class="wrap-item itemClose">
				<div class="wi-title">
					<img src="${path }/commons/img/grzxtp/tdgl.png" style="width: 30px;vertical-align: middle;" class="mt">
				团队管理</div>
				<div class="wi-wrap">
					<ul class="wi-list" id="chess">
						<c:forEach items="${team.userGroupDto.subFirstUserList.result }"
							var="firstUser">
							<li
								class="liClose <c:if test="${firstUser.subSecondUserList != null && fn:length(firstUser.subSecondUserList) > 0}">add</c:if>">
								<div class="wi-info  "    onclick="javascript:doMemberMore(this);">
									<div class="userphoto">
										<img
											src="<c:if test="${not empty firstUser.icon}"> ${firstUser.icon}</c:if> <c:if test="${empty firstUser.icon}">${path}/commons/images/default.png</c:if>">
									</div>
									<div class="userid">
										<div>
											<c:choose>
												<c:when
													test="${firstUser.realName != null && firstUser.realName != ''}">
													<h3 style="font-weight:normal;float:left; font-size:12px;">
														${firstUser.realName }</h3>
												</c:when>
												<c:otherwise>
													<h3 style="font-weight:normal;float:left; font-size:12px;">
														${firstUser.nickName }</h3>
												</c:otherwise>
											</c:choose>

											<!--  激活-->
											<c:if
												test="${firstUser.state == null || firstUser.state != 2}">
												<span
													style="background-color: #3fc57e;display: block;width: 40px; font-size:12px; border-radius:40px;height: 22px; text-align:center; line-height:25px;margin-left: 3px;float: left;">未激活</span>
											</c:if>
											<c:if
												test="${firstUser.state != null && firstUser.state == 2}">
												<span
													style="background-color: #3fc57e;display: block;width: 40px; font-size:12px; border-radius:40px;height: 22px; text-align:center; line-height:25px;margin-left: 3px;float: left;">已激活</span>
											</c:if>
											<!--红旗宝  -->
											<c:if test="${firstUser.hongqibao==2 }">
												<img
													style="display: block;width:0.6rem;height:0.6rem;margin-left: 3px;float: left;"
													src="${path }/commons/images/recive_money_icon.png">
											</c:if>

											<!--生鲜宝  -->
											<c:if test="${firstUser.shengxianbao==2 }">
												<img
													style="display: block;width: 0.6rem;height: 0.6rem;margin-left: 3px;float: left;"
													src="${path }/commons/images/recive_money_icon.png">
											</c:if>

										</div>
										<div>
											<h3 style="font-weight:normal;float:left;">ID：${firstUser.userId}</h3>
											<!-- 星级 -->

											<c:if
												test="${firstUser.state != null && firstUser.state == 2}">
												<c:if
													test="${firstUser.stars !=null && firstUser.stars != 0}">

													<c:forEach var="startIndex" begin="1"
														end="${ firstUser.stars}" step="1">
														<img class="my_star"
															style="float: left;display: block;width: 0.6rem;height:0.6rem;"
															src="${path }/commons/images/star.png" />
													</c:forEach>
												</c:if>
											</c:if>
										</div>
									</div>
									<div class="usertel">${firstUser.mobile}</div>
								</div>
								<ul class="teamlist">
									<c:forEach items="${firstUser.subSecondUserList }"
										var="secondUser">
										<li>
											<div class="wi-info">
												<div class="userphoto">
													<img
														src="<c:if test="${not empty secondUser.icon}"> ${secondUser.icon}</c:if> <c:if test="${empty secondUser.icon}">${path}/commons/images/default.png</c:if>">
												</div>
												<div class="userid">
													<div>
														<c:choose>
															<c:when
																test="${secondUser.realName != null && secondUser.realName != ''}">
																<h2
																	style="font-size:12px; font-weight:normal; float:left">${secondUser.realName }</h2>
															</c:when>
															<c:otherwise>
																<h2
																	style="font-size:12px; font-weight:normal; float:left">${secondUser.nickName }</h2>
															</c:otherwise>
														</c:choose>
														<!--  激活-->
														
														<c:if
															test="${secondUser.state == null || secondUser.state != 2}">
															<span
																style="background-color: #3fc57e;display: block;width: 40px; font-size:12px; border-radius:40px;height: 22px; text-align:center; line-height:25px;margin-left: 3px;float: left;">未激活</span>
														</c:if>
														<c:if
															test="${secondUser.state != null &&secondUser.state == 2}">
															<span
																style="background-color: #3fc57e;display: block;width: 40px; font-size:12px; border-radius:40px;height: 22px; text-align:center; line-height:25px;margin-left: 3px;float: left;">已激活</span>
														</c:if>
														<!--红旗宝  -->
														
														<c:if test="${secondUser.hongqibao==2 }">
															<img
																style="display: block;width: 0.6rem;height: 0.6rem;margin-left: 3px;float: left;"
																src="${path }/commons/images/recive_money_icon.png">
														</c:if>
														<!--生鲜宝  -->
														<c:if test="${secondUser.shengxianbao==2 }">
															<img
																style="display: block;width:0.6rem;height: 0.6rem;margin-left: 3px;float: left;"
																src="${path }/commons/images/recive_money_icon.png">
														</c:if>

													</div>


													<div>
														<h3 style="font-weight:normal;float:left;">ID：${secondUser.userId}</h3>
														<!-- 星级 -->
														
														<c:if
															test="${secondUser.state != null && secondUser.state == 2}">
															<c:if
																test="${secondUser.stars !=null && secondUser.stars != 0}">
																<c:forEach var="startIndex" begin="1" end="${secondUser.stars }" step="1">
																	<img class="my_star"
																		style="float: left;display: block;width: 20px;height: 20px;"
																		src="${path }/commons/images/star.png" />
																</c:forEach>
															</c:if>
														</c:if>

													</div>

												</div>
												<div class="usertel" style="font-size:12px;">${secondUser.mobile}</div>
											</div>
										</li>
									</c:forEach>
								</ul>
							</li>
						</c:forEach>
					</ul>
					<div class="list-bottom" id="userMore">
						<a href="javascript:void(0);" onclick="userMore()">加载更多</a>
					</div>
				</div>
			</div>

			<div class="wrap-item itemClose" style="  margin-top: 0.1rem;">
				<div class="wi-title">
				<img src="${path }/commons/img/grzxtp/qytj.png" style="width: 30px;vertical-align: middle;"  class="mt">
				我的推荐企业
				</div>
				<div class="wi-wrap">
					<ul class="wi-list" id="company">
						<c:forEach items="${team.supplierList.result}" var="company">
							<li class="liClose">
								<div class="wi-info">
								<%-- 	<div class="userphoto">
										<img src="${company.companyLogo }">
									</div> --%>
									<div class="userid" style="float:left; width:50%;">
										<div style="float:left; font-size:0.3rem;line-height: 0.7rem;text-align:center; padding-right: 1rem;">${company.companyName }</div>
									<%-- 	<div style="float:left; font-size:12px;">ID：${company.companyId }</div> --%>
									</div>
									<div class="usertel" style="width:50%; line-height: 0.75rem;    text-align:center;">
										<div style="font-size: 0.3rem;">联系人：${company.contactName }</div>
										<div style="font-size: 0.3rem;">电话：${company.contactPhone }</div>
									</div>
								</div>
							</li>
						</c:forEach>
					</ul>
					<div class="list-bottom" id="companyMore">
						<a href="javascript:void(0);" onclick="companyMore()">加载更多</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	$(function() {
		toggleItem();
		isShowMore();
	});

	function isShowMore() {
		var userPage = $("#userPage").val();
		var userTotalPage = $("#userTotalPage").val();
		if (userTotalPage == 0 || userPage == userTotalPage) {
			$("#userMore").hide();
		}

		var companyPage = $("#companyPage").val();
		var companyTotalPage = $("#companyTotalPage").val();
		if (companyTotalPage == 0 || companyPage == companyTotalPage) {
			$("#companyMore").hide();
		}
	}

	function toggleItem() {
		var $wrap_item_wi_title = $('.wrap-item .wi-title');
		var $wi_list_li = $('.wi-list>li>.wi-info');

		$wrap_item_wi_title.on('click', function() {
			var $this = $(this);
			$this = $this.parents('.wrap-item');
			if ($this.hasClass('itemClose') && !$this.hasClass('itemOpen')) {
				$this.removeClass('itemClose').addClass('itemOpen');
			} else if (!$this.hasClass('itemClose')
					&& $this.hasClass('itemOpen')) {
				$this.removeClass('itemOpen').addClass('itemClose');
				$this.find('.wi-list>li').addClass('liClose').removeClass(
						'liOpen');
			}
		});

	
	}

	function userMore() {
		
		var page = parseInt($("#userPage").val()) + 1;
		$.ajax({
					type : 'GET',
					url : '${path}/team/more',
					dataType : 'json',
					data : "page=" + page + "&type=" + 0,
					success : function(data) {
						console.log(data);
						isShowMore();
						$("#userPage").val(data.subFirstUserList.page);
						var result = [];
						for (var i = 0; i < data.subFirstUserList.result.length; i++) {
							var firstUser = data.subFirstUserList.result[i];
							result.push("<li class=\"liClose" );
							if (firstUser.subSecondUserList
									&& firstUser.subSecondUserList.length > 0) {
								result.push(" add");
							}
							result.push("\"  >");
							result.push("<div class=\"wi-info\"  onclick=\"javascript:doMemberMore(this);\" >");
							if (firstUser.icon && firstUser.icon != null
									&& firstUser.icon != "") {
								result
										.push("<div class=\"userphoto\"><img src='"+firstUser.icon+"'></div>");
							} else {
								result
										.push("<div class=\"userphoto\"><img src='${path}/commons/images/default.png'></div>");
							}
							result.push("<div class=\"userid\">");
							result.push("<div><h3 style='font-weight:normal;float:left; font-size:12px;'>");
							if (firstUser.realName != null
									&& firstUser.realName != '') {
								result.push(firstUser.realName);
							} else {
								result.push(firstUser.nickName);
							}
							result.push("</h3>");
							/*激活  */
							if (firstUser.state == null
									||firstUser.state !=2) {
								result.push("<span style='background-color: #3fc57e;display: block;width: 40px; font-size:12px; border-radius:40px;height: 22px; text-align:center; line-height:25px;margin-left: 3px;float: left;'>未激活</span>");
							}
							if (firstUser.state ==2){
								result.push("<span style='background-color: #3fc57e;display: block;width: 40px; font-size:12px; border-radius:40px;height: 22px; text-align:center; line-height:25px;margin-left: 3px;float: left;'>已激活</span>");
							}
							/* 红旗宝 生鲜宝 */
							if (firstUser.hongqibao == 2) {
								result.push("<img style='display: block;width:0.6rem;height:0.6rem;margin-left: 3px;float: left;' src='${path }/commons/images/recive_money_icon.png'>");
							} else if(firstUser.shengxianbao==2) {
								result.push("<img style='display: block;width: 0.6rem;height: 0.6rem;margin-left: 3px;float: left;' src='${path }/commons/images/recive_money_icon.png'>");
							}
							result.push("</div>");
							result.push("<div><h3 style='font-weight:normal;float:left;'>ID：" + firstUser.userId
									+ "</h3>");
							/* 星级 */
							if(firstUser.state==2){
								if(firstUser.stars!=null&&firstUser.stars!=0){
									for(var xingj=0;xingj<firstUser.stars;xingj++){
										result.push("<img class=\"my_star\" style='float: left;display: block;width: 0.6rem;height:0.6rem;' src='${path }/commons/images/star.png' />");
									}
									
								}
								
							}
							
							result.push("</div>");
							result.push("</div>");
							result.push("<div class=\"usertel\">"
									+ firstUser.mobile + "</div>");
							result.push("</div>");

							result.push("<ul class=\"teamlist\">");
							for (var j = 0; j < firstUser.subSecondUserList.length; j++) {
								var secondUser = firstUser.subSecondUserList[j];
								result.push("<li>");
								result.push("<div class=\"wi-info\">");
								if (secondUser.icon && secondUser.icon != null
										&& secondUser.icon != "") {
									result
											.push("<div class=\"userphoto\"><img src='"+secondUser.icon+"'></div>");
								} else {
									result
											.push("<div class=\"userphoto\"><img src='${path}/commons/images/default.png'></div>");
								}
								result.push("<div class=\"userid\">");
								result.push("<div><h2 style='font-size:12px; font-weight:normal; float:left'>");
								if (secondUser.realName != null
										&& secondUser.realName != '') {
									result.push(secondUser.realName);
								} else {
									result.push(secondUser.nickName);
								}
								
								
								result.push("</h2>");
								/*激活  */
								if (secondUser.state == null
										||secondUser.state !=2) {
									result.push("<span style='background-color: #3fc57e;display: block;width: 40px; font-size:12px; border-radius:40px;height: 22px; text-align:center; line-height:25px;margin-left: 3px;float: left;'>未激活</span>");
								} 
								if (secondUser.state ==2){
									result.push("<span style='background-color: #3fc57e;display: block;width: 40px; font-size:12px; border-radius:40px;height: 22px; text-align:center; line-height:25px;margin-left: 3px;float: left;'>已激活</span>");
								}
								/* 红旗宝 生鲜宝 */
								if (secondUser.hongqibao == 2) {
									result.push("<img style='display: block;width: 0.6rem;height: 0.6rem;margin-left: 3px;float: left;' src='${path }/commons/images/recive_money_icon.png'>");
								} 
								if(secondUser.shengxianbao==2) {
									result.push("<img style='display: block;width: 0.6rem;height: 0.6rem;margin-left: 3px;float: left;' src='${path }/commons/images/recive_money_icon.png'>");
								}
								
								result.push("</div>");
								result.push("<div><h3 style='font-weight:normal;float:left;'>ID：" + secondUser.userId
										+ "</h3>");
								/* 星级 */
								if(secondUser.state!=null && secondUser.state==2){
									if(secondUser.stars!=0){
										for(var xing=0;xing < secondUser.stars;xing++){
											result.push("<img class=\"my_star\" style='float:left;display:block;width:0.6rem;height:0.6rem;' src='${path }/commons/images/star.png'/>");
										}
										
									}
									
								}
								result.push("</div>");
								result.push("</div>");
								result.push("<div class=\"usertel\">"
										+ secondUser.mobile + "</div>");
								result.push("</div>");
								result.push("</li>");

							}
							result.push("</ul>");
							result.push("</li>");
						}
						$('#chess').append(result.join("")); 

					},
					error : function() {
					}
					
				});
	}
	
	
	 function doMemberMore(obj) {
			var $this = $(obj);
			$this = $this.parents('li');
			var $wi_list_li_teamlist = $this.find('.teamlist');
			var $wi_list_li_teamlist_li = $wi_list_li_teamlist.find('li');
			if ($wi_list_li_teamlist_li && $wi_list_li_teamlist_li.length
					&& $this.hasClass('liClose') && !$this.hasClass('liOpen')) {
				//展开
				$this.removeClass('liClose').addClass('liOpen');
			} else if (!$this.hasClass('liClose') && $this.hasClass('liOpen')) {
				//收起
				$this.removeClass('liOpen').addClass('liClose');
			}
		}

	function companyMore() {
		var page = parseInt($("#companyPage").val()) + 1;
		$
				.ajax({
					type : 'GET',
					url : '${path}/team/more',
					dataType : 'json',
					data : "page=" + page + "&type=" + 1,
					success : function(data) {
						isShowMore();
						$("#companyPage").val(data.page);
						var result = [];
						for (var i = 0; i < data.supplierList.result.length; i++) {
							var company = data.supplierList.result[i];
							result.push("<li class=\"liClose\">");
							result.push("<div class=\"wi-info\">");
							result
									.push("<div class=\"userphoto\"><img src='"+company.companyLogo+"'></div>");
							result.push("<div class=\"userid\" style='float:left; width:55%;'>");
							result.push("<div style='float:left; font-size:12px;'>" + company.companyName
									+ "</div>");
							result.push("<div style='float:left; font-size:12px;'>ID：" + company.companyId
									+ "</div>");
							result.push("</div>");
							result.push("<div class=\"usertel\" style='width:30%; line-height: 0.75rem;'>");
							result.push("<div style='float:left; font-size:12px;'>联系人：" + company.contactName
									+ "</div>");
							result.push("<div style='float:left; font-size:12px;'>" + company.contactPhone
									+ "</div>");
							result.push("</div>");
							result.push("</div>");
							result.push("</li>");
						}
						$('#company').append(result.join(""));
					},
					error : function() {
					}
				});
	}
</script>
</html>