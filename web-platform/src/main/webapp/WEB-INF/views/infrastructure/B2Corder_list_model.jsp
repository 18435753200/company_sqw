<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <title>个人周期分红设置</title>
    <%@include file="/WEB-INF/views/include/base.jsp" %>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/B2C_orderlist.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/wuliu_alert.css"/>
    <script src="${path}/commons/js/my97/WdatePicker.js"></script>
    <script src="${path }/commons/js/oneFhCycle.js"></script>
    <style type="text/css">
		.tr-th .fl {
		    width: 150px;
		}
	</style>
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp" %>
<div class="wrap">
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
                <p class="c1">个人周期分红设置</p>
            </div>
        </div>
        <form method="post" id="formid" enctype="multipart/form-data" action="${path}/infrastructure/toAddFhCyclePlanImpl">
           <input type="hidden" id="pageId" name="page">
           <input type="hidden" id="orderId" name="orderIds">
           <input type="hidden" id="planId" name="cyclePlanId">
        </form>
        <div class="tb-void">
            <form method="post" id="planImpl" enctype="multipart/form-data">
                <div>
                    <input type="checkbox" id="selectAll" onclick="selectAlls()"/>全选/反选
                    <input type="hidden" name="cyclePlanId" value="${cyclePlanId}"/>
                    <input type="button" value="确定" id="savePlanImpl" onclick="toAddPlanImpl()"/> 
                    <input type="hidden" value="${editFlag}" id="editFlag"/>
                </div>
                <div class="tb-void">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tbody>
                        <tr>
                            <th width="250">商品名称</th>
                            <th width="100">收货人</th>
                            <th width="100">数量</th>
                            <th width="100">实付款<i class="rmb-unit">¥</i></th>
                            <th width="100">支付方式</th>
                            <th width="100">状态</th>
                            <th width="80">操作</th>
                        </tr>
                        </tbody>
                        <tbody>
                        <c:forEach items="${pageBean.result }" var="orderList">
                            <tr class="tr-th">
                                <td colspan="7" style="position: relative;">
                                    <span class="fl"><input type="checkbox" name="orderIds" <c:if test="${orderList.orderCancelStatus==1}">checked</c:if>
                                                            value="${orderList.id}"/><fmt:formatDate
                                            value="${orderList.createTime }" type="both"/> </span>
                                    <span class="fc"> 订单号:<a href="javascript:void(0)">${orderList.id }</a></span>
                                    <span class="fr"> 买家:<i>${orderList.userName } </i>     &nbsp;&nbsp;&nbsp;买家ID:<i>${orderList.userId }</i> </span>
                                    <c:if test="${!empty orderList.message }">
                                        <span class="tcolr"><button type="button" class="lyan">订单留言</button></span>
                                        <div class="reply" style="display:none;">
                                                ${orderList.message }
                                        </div>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="qu">
                                        <!-- 11.宁波海外直邮  12.宁波保税区发货 13.郑州海外直邮 14.郑州保税区发货  1.国内发货 21.韩国直邮 -->
                                        <c:if test="${orderList.supplyType eq 1}"><a href="javascript:void(0)"><span
                                                class="at">国内发货</span></a></c:if>
                                        <c:if test="${orderList.supplyType eq 21}"><a href="javascript:void(0)"><span
                                                class="ac">海外直邮</span></a></c:if>
                                        <c:if test="${orderList.supplyType eq 12}"><a href="javascript:void(0)"><span
                                                class="ah">保税区发货</span></a></c:if>
                                        <c:if test="${orderList.supplyType eq 11}"><a href="javascript:void(0)"><span
                                                style="background:#F02146 ">海外直邮</span></a></c:if>
                                        <c:if test="${orderList.supplyType eq 31}"><a href="javascript:void(0)"><span
                                                style="background:#FC0EE0 ">卓悦发货</span></a></c:if>
                                        <c:if test="${orderList.supplyType eq 50}"><a href="javascript:void(0)"><span
                                                style="background:#FC0EE0 ">特卖商品</span></a></c:if>
                                        <c:if test="${orderList.supplyType eq 51}"><a href="javascript:void(0)"><span
                                                style="background:#ff4800 ">${orderList.merchantName}</span></a></c:if>
                                            <%-- <c:if test="${orderList.supplyType eq 13}"><a href="javascript:void(0)"><span  style="background:#F02146 ">郑州海外直邮</span></a></c:if>
                                            <c:if test="${orderList.supplyType eq 14}"><a href="javascript:void(0)"><span  style="background:#F02146 ">郑州保税区发货</span></a></c:if> --%>


                                        <c:forEach items="${_orderTypeList}" var="__orderTypeList">
                                            <c:if test="${orderList.orderType eq __orderTypeList.value}"><a
                                                    href="javascript:void(0)"><span
                                                    style="background:#F02146 ">${__orderTypeList.key}</span></a></c:if>
                                        </c:forEach>
                                        <span>
	        						</span>
                                    </div>
                                    <ul class="goods-list clearfix">
                                        <c:forEach items="${orderList.productList }" var="productList">
                                            <li>
                                                <div class="goods-box">
                                                    <div class="goods-pic"><a
                                                            href="${path}/user/viewInfo?source=1&&id2=${productList.supplierId}"
                                                            target="_blank"><img src="${productList.imgUrl }" width="60"
                                                                                 height="60"></a></div>
                                                    <div class="goods-txt">
                                                        <span class="sku">商品:${productList.pName }</span>
                                                        <span class="amount">数量:${productList.count }</span>
                                                    </div>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </td>
                                <td>${orderList.receiveName }</td>
                                    <%-- ${orderList.receiveName } --%>
                                <td>${orderList.totalQty }</td>
                                <td><fmt:formatNumber value="${orderList.paidPrice }"
                                                      pattern="0.00#"></fmt:formatNumber></td>
                                <td>
                                    <c:if test="${orderList.payWay eq  2}">支付宝</c:if>
                                    <c:if test="${orderList.payWay eq  3}">红旗劵支付</c:if>
                                    <c:if test="${orderList.payWay eq  4}">京东快捷支付</c:if>
                                    <c:if test="${orderList.payWay eq  5}">京东网银支付</c:if>
                                    <c:if test="${orderList.payWay eq  6}">微信支付</c:if>
                                </td>
                                <td>
                                    <c:if test="${orderList.status eq  1}">待支付</c:if>
                                    <c:if test="${orderList.status eq  21}">已支付</c:if>
                                    <c:if test="${orderList.status eq  31}">待海关审核</c:if>
                                    <c:if test="${orderList.status eq  41}">待发货</c:if>
                                    <c:if test="${orderList.status eq  67}">海关审核订单失败(待退款)</c:if>
                                    <c:if test="${orderList.status eq  68}">海关支付单审核失败(待退款)</c:if>
                                    <c:if test="${orderList.status eq  69}">海关物流单审核失败(待退款)</c:if>
                                    <c:if test="${orderList.status eq  70}">海关审核失败(待退款)</c:if>
                                    <c:if test="${orderList.status eq  79}">已退款</c:if>
                                    <c:if test="${orderList.status eq  81}">已发货</c:if>
                                    <c:if test="${orderList.status eq  91}">已收货</c:if>
                                    <c:if test="${orderList.status eq  99}">用户取消</c:if>
                                    <c:if test="${orderList.status eq  100}">自动取消</c:if>
                                </td>
                                <td class="order-doi">
                                    <c:if test="${!empty buttonsMap['C订单管理-订单详情'] }">
                                        <a href="../customerOrder/getCustomerOrderDetailsById?orderId=${orderList.id }"
                                           target="_blank">订单详情</a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <c:if test="${!empty pageBean.result}">
                    <%@include file="/WEB-INF/views/dealerbuy/basePage/basePage.jsp" %>
                </c:if>
                <div style="display:none">
                    <c:forEach items="${orders}" var="order">
                        <c:if test="${!empty order }">
                            <input type="checkbox" name="orderIds" value="${order}" checked/>
                        </c:if>
                    </c:forEach>
                </div>
            </form>
        </div>
    </div>
    <!-- 右边 end -->
</div>
<div class="blank10"></div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp" %>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        $(".lyan").live("hover",onMouseOver);
        $(".lyan").live("mouseleave",onMouseLeave);
        if (typeof($("#editFlag").val()) != "undefined" && $("#editFlag").val() == 1) {
            $("#savePlanImpl").hide();
        }
    });
    function toPage(page){
        var orderIds = $("input:checkbox[name='orderIds']:checked");
        var url="";
        if(typeof(orderIds)=="undefined" || orderIds.length == 0){
            url="";
        }else{
            //url="&orderIds=";
            for(var i=0;i<orderIds.length;i++){
                url+=orderIds[i].value+",";
            }
        }
        $("#pageId").val(page);
        $("#orderId").val(url);
        $("#planId").val("${cyclePlanIdParams}");
        $("#formid").submit();
/* window.location.href="${path}/infrastructure/toAddFhCyclePlanImpl?cyclePlanId=${cyclePlanIdParams}&page="+page+url; */
    }
</script>
</html>