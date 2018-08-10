<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.zhongjumall.com/permission"%>
<style>
.load-div{display:none;position:fixed;top:0;bottom:0;width:100%;background-image:url(../commons/images/load.gif);background-repeat:no-repeat;background-position:center center;background-color:#000;filter:alpha(opacity=50);opacity:.50;z-index:20000000;}
</style>
<div class="load-div" id="load-div"></div>

	<!-- 导航 start -->
		<div class="t_nav">
			<div class="box">
			<ul>
				<div class="t_user f_r">
	
					<a href="${path}/platform/jiben">
					<ul class="u1 ul_horizontal f_l">
						<li><img src="${path}/commons/images/header.png"  alt="" style="width:32px; height:32px;"></li>
						<li class="m1">${loginUser.username}</li>
						<!-- <li class="m2"><img src="${path}/commons/images/img_t.png" alt=""></li> -->
				    </ul>
			   	  </a>
			   	 	 <a href="${path}/logout">
			   	 	 	<ul class="u1 ul_horizontal f_l">
				   	 	 	<li class="m3" style="cursor:pointer;color:#FFF" >
								退出
							</li>
						</ul>
					</a>
				</div>
			</ul>
			<ul class="ul_horizontal u2">
			<!-- <sec:security url="/platform/welcome">
	              <li><a href="${path }/platform/welcome">首页</a></li>
	              </sec:security> -->
	            <!--   <sec:security url="/buyOrder/buyOrder">
						<li><a href="${path}/buyOrder/buyOrder">买家中心</a></li>
						</sec:security> -->
						<sec:security url="/product/getPro">
						   <li>	<a href="${path}/POPproduct/getPro">卖家中心</a></li>
						   </sec:security>
						   <sec:security url="/coupon/getCouponPage">
	                <%-- <li><a href="${path}/coupon/getCouponPage">促销管理</a></li> --%>
	                </sec:security>
	                 <sec:security url="/user/checklist" parameter="source=1">
							   <li><a href="${path}/user/checklist?source=1">系统中心</a></li>
							   </sec:security>
			</ul>
		</div>
		
	</div>
		
	<div class="blank"></div>
	<!-- 导航 end -->
	
	