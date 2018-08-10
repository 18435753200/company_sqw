<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <title>会员团队分红设置</title>
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
                <p class="c1">会员团队分红设置</p>
            </div>
        </div>
        <div class="blank5"></div>
        <div class="qb">
            <div class="cont biaogee">
                <table>
                    <tr>
                        <th style="width:25%;">账号类型</th>
                        <th style="width:25%;">团队人数</th>
                        <th style="width:25%;">直接会员（赠送红旗券）</th>
                        <th style="width:25%;">间接会员（赠送红旗券）</th>
                    </tr>
                    <c:forEach items="${memDiv}" var="st" varStatus="status">
                        <tr>
                            <td>${st.accountName}
                                <input type="hidden" id="stid${status.index}" name="stid${status.index}" value="${st.accountType}"/>
                                <input type="hidden" id="stname${status.index}" name="stname${status.index}" value="${st.accountName}" />
                            </td>
                            <td>
                                <input type="text" id="stn${status.index}" name="stn${status.index}" value="${st.teamNumber}"/>
                            </td>
                            <td>
                                <input type="text" id="somd${status.index}" name="somd${status.index}" value="${st.ownMemberDividend}"/>
                            </td>
                            <td>
                                <input type="text" id="simd${status.index}" name="simd${status.index}" value="${st.indirectMemberDividend}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr class="x1">
                        <td colspan="4" align="center" class="p3">
                            <input type="hidden" id="ct" name="ct" value="${fn:length(memDiv)}" />
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
            var stn = $("#stn" + i).val();
            var somd = $("#somd" + i).val();
            var simd = $("#simd" + i).val();
            if ((!RegExp("^\\d{1,5}\\.\\d+$").test(stn) && !RegExp("^\\d{1,5}\\.?$").test(stn)) || stn <= "") {
                s += $("#stname" + i).val() + "、";
                continue;
            }
            if ((!RegExp("^\\d{1,5}\\.\\d+$").test(somd) && !RegExp("^\\d{1,5}\\.?$").test(somd)) || somd <= "") {
                s += $("#stname" + i).val() + "、";
                continue;
            }
            if ((!RegExp("^\\d{1,5}\\.\\d+$").test(simd) && !RegExp("^\\d{1,5}\\.?$").test(simd)) || simd <= "") {
                s += $("#stname" + i).val() + "、";
                continue;
            }
            d.push({id: stid, tn: stn, omd: somd, imd: simd});
        }
        if(s>""){
            alert("请正确填写"+s+"会员团队分红设置！");
        }
        savetips.fai=0;
        savetips.sav=d.length;
        for(var i=0;i<d.length;i++){
            $.ajax({
                type: 'post',
                url: '../infrastructure/updateMemberDividend',
                data: 'stid='+d[i].id+"&stn="+d[i].tn+"&somd="+d[i].omd+"&simd="+d[i].imd,
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