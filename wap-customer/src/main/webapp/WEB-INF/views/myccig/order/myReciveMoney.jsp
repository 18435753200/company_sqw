<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html style="font-size: 37.5px;">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<%-- <link href="${staticFile_s}/commons/css/w_base.css" rel="stylesheet" type="text/css"> --%>
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<title>红旗宝</title>
    <style>
    	@charset "utf-8";
/*全局*/
body, div, p, ul, ol, li, dl, dt, dd, form, input, table, img, h1, h2, h3, h4, h5, h6, header, section, footer, nav, figure, figcaption, article {
    margin: 0;
    padding: 0
}

header, section, footer, nav, figure, figcaption, article, menu {
    display: block
}

body {
    font-family: "Microsoft YaHei", "微软雅黑", Arial, Georgia, Verdana, serif;
}

ul, ol {
    list-style: none;
}

a, a:hover {
    text-decoration: none;
    color: #393737;
}

img {
    border: none;
    display: inline-block;
}

h1, h2, h3, h4, h5, h6 {
    font-weight: normal;
}

em, i {
    font-style: normal;
}

.l, .left {
    float: left;
    display: inline-block;
}

.r, .right {
    float: right;
    display: inline-block;
}

.clear:after {
    content: '';
    display: block;
    width: 0;
    height: 0;
    overflow: hidden;
    clear: both;
}

header, nav, section, footer {
    margin: auto;
}

* {
    tap-highlight-color: rgba(0, 0, 0, 0);
    -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
    -ms-tap-highlight-color: rgba(0, 0, 0, 0);
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    -ms-box-sizing: border-box;
    box-sizing: border-box;
}

/************* 公用样式 ****************/
html, body {
    -webkit-user-select: none;
    -o-user-select: none;
    -ms-user-select: none;
    user-select: none;
    display: -webkit-box;
    display: -moz-box;
    -webkit-box-flex: 1;
    -o-box-flex: 1;
    -ms-box-flex: 1;
    box-flex: 1;
    -webkit-box-orient: vertical;
    -o-box-orient: vertical;
    -ms-box-orient: vertical;
    box-orient: vertical;
    height: 100%;
    width: 100%;
    max-width: 640px;
    min-width: 320px;
    margin: 0 auto;
    /*overflow: hidden;*/
}
    
    
    
    
    
        body {
            background: #EFEFEF;
        }

        .section {
            width: 100%;
        }

        .top-header {
            width: 100%;
            height: 1.3rem;
            line-height: 1.3rem;
            text-align: center;
            font-size: 0.45rem;
            font-weight: bold;
            /*border-bottom: 1px solid #d7d7da;*/
            position: relative;
            background: #fff;
        }

        .top-header a {
            width: 0.3rem;
            height: 0.56rem;
            background: url("<%=path %>/commons/img/gobak.png") no-repeat center center;
            background-size: contain;
            position: absolute;
            top: 0.37rem;
            left: 0.5rem;
        }

        .wrap {
            width: 100%;
            margin-top: 0.25rem;
            background: #fff;
            padding: 0 0 0 5%;
        }

        .wrap-item {
            width: 100%;
            height: 1.4rem;
            line-height: 0.9rem;
            padding: 0.25rem 5% 0.25rem 0;
            border-bottom: 1px solid #EFEFEF;
            font-size: 0;
            color: #2b2b2b;
            position: relative;
        }

        .readonly {
            color: #A7A7A7 !important;
        }

        .wrap .wrap-item:last-child {
            border-bottom: 0;
        }

        .item-nam {
            display: inline-block;
            width: 30%;
            height: 0.9rem;
            line-height: 0.9rem;
            font-size: 0.4rem;
            font-weight: bold;
            position: absolute;
            top: 0.25rem;
        }

        .inpt-num {
            width: 65%;
            height: 0.9rem;
            line-height: 0.9rem;
            border: 0;
            font-size: 0.4rem;
            position: absolute;
            top: 0.25rem;
            left: 30%;
        }

        .wrap-footer {
            width: 100%;
            padding: 0.25rem 5%;
            font-size: 0.35rem;
        }

        .btn-sure {
            width: 50%;
            height: 1.2rem;
            line-height: 1.2rem;
            margin: 0.8rem auto 1.8rem;
            text-align: center;
            background: #FD2C2F;
            color: #fff;
            font-size: 0.45rem;
        }
        .nav img {
	     width: 100%;
		}
    </style>
</head>

<body style="font-size: 12px;">
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
<div class="section">
	<input type="hidden" id="realpayMultipe" value="${cashierSettings.realpayMultipe}"/>
	<input type="hidden" id="counterFee" value="${cashierSettings.counterFee}"/>
	<input type="hidden" id="giftHqj" value="${cashierSettings.giftHqj}"/>
	<input type="hidden" id="giftFhed" value="${cashierSettings.giftFhed}"/>
    <div class="top-header">
              红旗宝
        <a href="javascript:goBack();"></a>
    </div>
    <nav class="nav">
			<img src="${path }/commons/img/newmyimage/nav.png">
		</nav>
    <div class="wrap">
        <div class="wrap-item">
            <div class="item-nam">实体店铺ID</div>
            <input type="text" class="inpt-num" id="shopUserId" placeholder="输入账户ID"/>
        </div>
        <div class="wrap-item">
            <div class="item-nam">真实姓名</div>
            <input type="text" class="inpt-num" id="shopRealName" placeholder="输入真实姓名"/>
        </div>
        <div class="wrap-item">
            <div class="item-nam">结算数</div>
            <input type="text" class="inpt-num" placeholder="输入数值" id="orderPrice"/>
        </div>
        <div class="wrap-item readonly">
            <div class="item-nam">实付数</div>
            <input type="text" class="inpt-num" id="price" readonly="readonly"/>
        </div>
        <div class="wrap-item readonly">
            <div class="item-nam">交易补贴</div>
            <input type="text" class="inpt-num" id="factorage" readonly="readonly"/>
        </div>
        <div class="wrap-item readonly">
            <div class="item-nam">实体店实收</div>
            <input type="text" class="inpt-num" id="shopIncome" readonly="readonly"/>
        </div>
        <div class="wrap-item readonly" >
            <div class="item-nam">奖励红旗券</div>
            <input type="text" class="inpt-num" id="zshqq" readonly="readonly"/>
        </div>
        <div class="wrap-item readonly" >
            <div class="item-nam">分红额度</div>
            <input type="text" class="inpt-num" id="fhed" readonly="readonly"/>
    </div>
    </div>
    
    <div class="wrap-footer">请准确填写对方实体店铺ID，并输入真实姓名</div>
    <div class="btn-sure">下一步</div>
</div>
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<script src="${staticFile_s}/commons/js/zepto.min.js" type="text/javascript"></script> 
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script> 
</body>
<script>
    $(function () {
        $('.btn-sure').on('click', function () {
        	var shopUserId = $("#shopUserId").val();
        	var shopRealName = $("#shopRealName").val();
        	var price = $("#orderPrice").val();
        	var _dataType = "text";
        	var _type = "POST";
        	var _url = $("#path").val()+"/cusOrder/reciveMoneySysPayValidate";
        	var data = "shopUserId="+shopUserId+"&shopRealName="+shopRealName+"&price="+price;
        	showMsg("红旗宝暂停使用");
        	/* 	$.ajax({
        		dataType : _dataType,
        		type : _type,
        		url : _url,
        		data : data,
        		success : function(res) {
        			if (res == "1") {
        				location.href=$("#path").val()+"/orderPay/tradePwdPageForReciveSys?shopUserId="+shopUserId+"&shopRealName="+shopRealName+"&price="+price;	
        			}else{
        				showMsg(res);
        			} 
        		},
        		error : function() {
        			showMsg("网络连接超时，请您稍后重试");
        		}
        	}); */
        });
        
        $('#orderPrice').on('change',function(){
        	var orderPrice = $("#orderPrice").val();
        	if(isNumber(orderPrice)){
        		$("#orderPrice").val(Number(orderPrice).toFixed(0));
        		var price1 = ($("#realpayMultipe").val()*orderPrice).toFixed(0);
        		var price2 = ($("#counterFee").val()*orderPrice).toFixed(0);
        		var shifu=(Number(price1)+Number(price2)).toFixed(0);
        		var zshqq = ($("#giftHqj").val()*Number(shifu)).toFixed(0);
        		var fhed = ($("#giftFhed").val()*Number(shifu)).toFixed(0);
        		$("#price").val(shifu);
				$("#factorage").val(price2);
				var shopIncome = (Number(orderPrice)+Number(price2)).toFixed(0);
				$("#shopIncome").val(shopIncome);
				$("#zshqq").val(Number(zshqq));
				$("#fhed").val(Number(fhed));
        	}else{
        		showMsg("请输入正确的价格");
        		$("#orderPrice").focus();
        		return false;
        	}	
        });
    });
</script>
</html>