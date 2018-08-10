<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
    <title>家庭号商品最大数量设置</title>
    <%@include file="/WEB-INF/views/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
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
        <div class="c21">
            <div class="title">
                <p>卖家中心&nbsp;&gt;&nbsp; </p>
                <p>基础设置&nbsp;&gt;&nbsp; </p>
                <p class="c1">家庭号商品最大数量设置</p>
            </div>
        </div>
        <div class="blank5"></div>
        <div class="cont">
            <ul class="ul_vertical">
                <li>
                    <p class="p1">家庭号商品最大数量设置：</p>
                    <input type="text" id="cash" name="cash" value="${ratio.maxNum}" style="margin-left: 20px;"/>&nbsp;
                    <input type="hidden" id="cash1" name="cash" value="${ratio.id}" style="margin-left: 20px;"/>&nbsp;
                    <input type="hidden" id="cash2" name="cash" value="${loginUser.username}" style="margin-left: 20px;"/>&nbsp;
                    <br/><br/>
                </li>
         
                <li>
                    <input type="button"  class="fabu_btn" onclick="save()" value="保存" ></input>
                </li>
            </ul>

        </div>


    </div>
    <!-- 右边 end -->
</div>



<div class="blank10"></div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
<script type="text/javascript">
    function save(){
        var cash = $("#cash").val();
       var id=$("#cash1").val();
       var id=$("#cash1").val();
       var operateUser=$("#cash2").val();
        $.ajax({
            type:'post',
            url:'../infrastructure/setOneDividendRatio3',
            data: 'maxNum='+cash+'&id='+id+'&operateUser='+operateUser,
            success:function(data){
                if(data=='1'){
                    tipMessage("操作成功！",function(){
                    });
                }else{
                    tipMessage("操作失败，请检查后重试！",function(){
                    });
                }

            }
        });
    }
</script>
</html>