<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/22 0022
  Time: 18:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <title>会员星级设置</title>
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
                <p class="c1">会员星级设置</p>
            </div>
        </div>
        <div class="blank5"></div>
        <div class="qb">
            <div class="cont biaogee">
                <table>
                    <tr>
                        <th style="width:15%;">会员星级</th>
                        <th style="width:17.5%;">累计消费额</th>
                        <th style="width:17.5%;">直接、间接会员总数</th>
                        <th style="width:15%;">含会员星级</th>
                        <th style="width:17.5%;">含星级会员数</th>
                        <th style="width:17.5%;">返还倍数</th>
                    </tr>
                    <c:forEach items="${starList}" var="st" varStatus="status">
                        <tr>
                            <td>${st.memberStarName}
                                <input type="hidden" id="stid${status.index}" name="stid${status.index}" value="${st.memberStar}"/>
                                <input type="hidden" id="stname${status.index}" name="stname${status.index}" value="${st.memberStarName}" />
                            </td>
                            <td>
                                <input type="text" id="am${status.index}" name="am${status.index}" value="${st.amountMoney}"/>
                            </td>
                            <td>
                                <input type="text" id="mt${status.index}" name="mt${status.index}" value="${st.memberTotal}"/>
                            </td>
                            <td>
                                <select id="nms${status.index}" name="nms${status.index}" disabled>
                                    <option
                                            <c:if test="${st.needMemberStar==0}">
                                                selected
                                            </c:if>
                                            value="0">无</option>
                                    <c:forEach items="${memberStar}" var="ms">
                                        <option
                                                <c:if test="${ms.value==st.needMemberStar}">
                                                    selected
                                                </c:if>
                                                value="${ms.value}">${ms.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <input type="text" id="nst${status.index}" name="nst${status.index}" value="${st.needStarTotal}"/>
                            </td>
                            <td>
                                <input type="text" id="rm${status.index}" name="rm${status.index}" value="${st.returnMultiple}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr class="x1">
                        <td colspan="6" align="center" class="p3">
                            <input type="hidden" id="ct" name="ct" value="${fn:length(starList)}" />
                            <input type="button" class="tjiao" onclick="saveSettings()" value="保存"/>
                        </td>
                    </tr>
                </table>
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
    var savetips={sav:0,fai:0,suc:0};
    function saveSettings() {
        var cou = parseInt($("#ct").val());
        var s="";
        var d=[];
        for(var i=0;i<cou;i++) {
            var stid = $("#stid" + i).val();
            var am = $("#am" + i).val();
            var mt = $("#mt" + i).val();
            var nst = $("#nst" + i).val();
            var rm = $("#rm" + i).val();
            if (((!RegExp("^\\d{1,12}\\.\\d+$").test(am) && !RegExp("^\\d{1,12}\\.?$").test(am))) || ((!RegExp("^\\d{1,12}\\.\\d+$").test(mt) && !RegExp("^\\d{1,12}\\.?$").test(mt))) || ((!RegExp("^\\d{1,12}\\.\\d+$").test(nst) && !RegExp("^\\d{1,12}\\.?$").test(nst))) || ((!RegExp("^\\d{1,12}\\.\\d+$").test(rm) && !RegExp("^\\d{1,12}\\.?$").test(rm)))) {
                s += $("#stname" + i).val() + "、";
                continue;
            }
            d.push({oms: stid, oam: am, omt: mt, onst: nst, orm: rm});
        }
        if(s>""){
            alert("请正确设置"+s+"参数！");
        }
        savetips.fai=0;
        savetips.sav=d.length;
        for(var i=0;i<d.length;i++){
            $.ajax({
                type: 'post',
                url: '../infrastructure/updateMemberStarSettings',
                data: 'ms='+d[i].oms+"&am="+d[i].oam+"&mt="+d[i].omt+"&nst="+d[i].onst+"&rm="+d[i].orm,
                success: function (data) {
                    savetips.sav=savetips.sav-1;
                    if (data != '1') {
                        savetips.fai=savetips.fai+1;
                    }
                    if(savetips.sav<=0){
                        tipMessage((savetips.fai>0)?"操作失败，请检查后重试！":"数据修改保存成功！", function () {
                        });
                    }
                }
            });
        }
    }
</script>
</html>