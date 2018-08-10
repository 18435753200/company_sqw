var supplierId = $("#supplierId").val();
$(function() {
    // 页面初始化
    getId("s11").checked = false;
    getId("s12").checked = false;
    getId("returnCoupon").innerHTML = 0;
    getId("payDiscountPrice").innerHTML = 0;
    getId("payCoupon").innerHTML = 0;
    getId("payTotalPrice").innerHTML = 0;
    getId("paySumpayContent").style.display = "none";
    getId("payDiscountpayContent").style.display = "none";
    getId("selectPay").style.display = "block";

    // 获取消费者剩余代金券
    $.ajax({
        type: "POST",
        url: "/customer/getDjqBalance",
        dataType:"json",
        success:function (data) {
            var accountBalance = data.accountBalance;
            $("#accountBalance").val(accountBalance);
            $("#mq").html(accountBalance);
        }
    });


    //全现金
    getId("weuiCellS11").onclick = function () {

        var price = getPrice();
        if (isNaN(parseInt(price))) {
            getId("s11").checked = false;
            getId("s12").checked = false;
            tips.show("请输入金额");
            return;
        }

        $.ajax({
            type: "POST",
            url: "/consume/allCashPay",
            data: {
                price: price, supplierId: supplierId
            },
            dataType:"json",
            success:function (data) {
                // 设置
                $("#payTotalPrice").html(price);
                $("#returnCoupon").html(data.djq);
                // 清除
                $("#payCoupon").html(0);
                $("#payDiscountPrice").html(0);
            }

        });

        $(".weuiCellS11 p").css("color","#E60012");
        $(".weuiCellS12 p").css("color","#000");
        getId("paySumpayContent").style.display = "block";
        getId("payDiscountpayContent").style.display = "none";
        getId("selectPay").style.display = "none";
        getId('payBtn').classList.add("active");
    };
     //券+现金
    getId("weuiCellS12").onclick = function () {
        var price = getPrice();
        if (isNaN(parseInt(price))) {
            getId("s11").checked = false;
            getId("s12").checked = false;
            tips.show("请输入金额");
            return;
        }

        $.ajax({
            url: "/consume/mixPay",
            data: {
                price: price, supplierId: supplierId
            },
            dataType:"json",
            success:function (data) {
                var djq = data.djq;
                // 设置 用券
                $("#payCoupon").html(djq);
                $("#payDiscountPrice").html(data.cash);

                $("#payTotalPrice").html(0);
                $("#returnCoupon").html(0);


                var accountBalance = $("#accountBalance").val();

                if (parseFloat(djq) > parseFloat(accountBalance)) {
                    tips.show("M券余额不足");
                    getId("selectPay").style.display = "none";
                    getId("s12").checked = false;
                    $(".weuiCellS11 p").css("color","#000");
                    getId("selectPay").style.display = "block";
                    getId("paySumpayContent").style.display = "none";
                    getId("payDiscountpayContent").style.display = "none";
                    getId('payBtn').classList.remove("active");

                    return;
                }

                $(".weuiCellS12 p").css("color","#E60012");
                $(".weuiCellS11 p").css("color","#000");
                getId("paySumpayContent").style.display = "none";
                getId("payDiscountpayContent").style.display = "block";
                getId("selectPay").style.display = "none";
                getId('payBtn').classList.add("active");
            }
        });


    };
});




// 获取金额
function getPrice() {
    totalPrice = getId("amount").innerHTML;
    totalPrice = totalPrice.replace(",","");
    return totalPrice;
}
// 获取支付类型
function getPayType() {
    var pay = document.getElementsByName("checkbox1");
    var value = null;

    for (var i=0; i<pay.length; i++) {
        if (pay[i].checked) {
            value = pay[i].value;
        }
    }
    return value;
}

// 付款按钮回调函数
function submitFun(){

    var active = getId('payBtn').getAttribute('class');

    if (active.indexOf('active') == -1) {
        return;
    }

    getPrice();
    var value = getPayType();
    var s1 = getId("s11");
    var s2 = getId("s12");
    if (value == "03") {             // 全额支付
        if (totalPrice == 0 || $.trim(totalPrice)=="" ){
        	tips.show("支付金额不能为0元");
            return;
        }
    }else if (value == "04") {       // 券低折扣
        if (totalPrice == 0 || $.trim(totalPrice)==""){
        	tips.show("支付金额不能为0元");
            return;
        }
    }else if(s1.checked == false && s2.checked == false){
        tips.show("请选择支付方式");
        return;
    }

    // 开始下单
    var openId = $("#openId").val();
    var timestamp = $("#timestamp").val();
    var payType = $("#payType").val();
    var userId = $("#userId").val();
    var qrCodeId = $("#qrCodeId").val();
    var price = 0;
    var payPrice = 0;
    var hqjPrice ;
    var returnCoupon = 0;

    if (value == "03") {             // 全额支付
        price = $("#payTotalPrice").html();
        payPrice = price;
        returnCoupon = $("#returnCoupon").html();       // 全现金返券
    }else{                              // 券低折扣
        hqjPrice = $("#payCoupon").html();              // 券+现金 用券
        payPrice = $("#payDiscountPrice").html();
        price = parseFloat(payPrice) + parseFloat(hqjPrice);
    }

    getId('payBtn').classList.remove("active");

    $.ajax({
        type: "POST",
        url: "/cusPay/chinaumsOrder?rc=" + qrCodeId,
        data: {
            price: price, payPrice: payPrice, hqjPrice: hqjPrice, timestamp: timestamp, openId: openId,
            payType: payType, supplierId: supplierId, userId: userId, qrCodeId:qrCodeId
        },
        dataType: "json",
        async: true,
        success: function (data) {
            var payStatus = data.payStatus;
            var msg = data.msg;

            if(payStatus == 'B'){
                tips.show(msg);
            }else if (payStatus == 'W') {
                var url = data.action;
                window.location.href = url;
            }else if (payStatus == 'K') {
                tips.show(data.msg);
            }
        },
        error:function () {
            tips.show("支付异常，请重新扫码支付");
        }
    });

}
//获取付款按钮和删除按钮 并且给付款按钮添加tap事件
var payBtn = getId('payBtn');
var keyboard = getId('keyboard');
new Hammer(payBtn).on('tap',submitFun);