var CONTEXTPATH = $("#conPath").val();
/* 商品js */
$(document).ready(function() {
    $('#couponacount').live('change', checkPrice);
    // 提交
    $("#createCoupon").click(function() {
        var flag = checkSubmit();
        if (flag == false) {
            tipMessage('部分信息不完整或不符合规范，请修改！', function() {
            });
        }
        if (flag == true) {
            $.ajax({
                type : 'post',
                url : '../coupon/createCoupon',
                data : $('#couponAction').serialize(),
                success : function(data) {
                    if (data == 'success') {
                        tipMessage("创建成功，将立即返回到优惠券列表！", function() {
                            window.location.href = "../coupon/getCouponPage";
                        });
                    } else if (data == 'error') {
                        tipMessage("创建失败，请检查后重试！", function() {
                        });
                    } else {
                        tipMessage("创建失败,原因：" + data, function() {
                        });
                    }
                }
            });
        }
    });
});

/**
 * 校验金额
 * 
 * @returns
 */
var checkPrice = function() {
    var matchnum =  /^(\+|-)?\d+$/;
    var couponAcount = $.trim($("#couponacount").val());
    if (couponAcount <= 0) {
        $("#couponacount").next().next().text('优惠券金额需大于零！').show();
        $("#couponacount").val();
    } else {
        if (!matchnum.test(couponAcount) || couponAcount.length > 18) {
            $("#couponacount").next().next().text('优惠券金额只能是正整数,并且不能大于18位！')
                    .show();
            $("#couponacount").val();
        } else {
            $("#couponacount").next().next().hide();
        }
    }
};

/**
 * 校验开始时间
 * 
 * @returns
 */
var checkStartTime = function() {
    var start = new Date();
    var endTime = $("#starttime").val();
    var end = new Date(endTime.replace("-", "/").replace("-", "/"));
    if (end < start) {
        return false;
    } else {
        return true;
    }

};

/**
 * 校验结束时间
 * 
 * @returns
 */
var checkEndTime = function() {
    var startTime = $("#starttime").val();
    var start = new Date(startTime.replace("-", "/").replace("-", "/"));
    var endTime = $("#endtime").val();
    var end = new Date(endTime.replace("-", "/").replace("-", "/"));
    if (end <= start) {
        return false;
    } else {
        return true;
    }
};
/**
 * 提交校验项
 */
function checkSubmit() {
    var flag = true;
    // 校验名称
    var couponName = $('#couponname').val();
    if (couponName.length > 100 || couponName.length < 1) {
        flag = false;
        $('#couponname').next().text('优惠券名称必填，且不能超过100字！').show();
    } else {
        $('#couponname').next().hide();
    }
    // 校验金额
    var matchnum = /^(\+|-)?\d+$/;
    var couponAcount = $.trim($("#couponacount").val());
    if (couponAcount == "") {
        $("#couponacount").next().next().text('优惠券金额不能为空！').show();
        flag = false;
    } else {
        if (couponAcount <= 0) {
            flag = false;
            $("#couponacount").next().next().text('优惠券金额需大于零！').show();
            $("#couponacount").val();
        } else {
            if (!matchnum.test(couponAcount) || couponAcount.length > 18) {
                $("#couponacount").next().next().text('优惠券金额只能是正整数 并且不能大于18位！')
                        .show();
                $("#couponacount").val();
                flag = false;
            } else {
                $("#couponacount").next().next().hide()
            }
        }
    }
    // 校验开始时间
    var startTime = $("#starttime").val();
    if (startTime == "") {
        $("#starttime").next().text('开始时间不能为空！').show();
        flag = false;
    } else {
        if (!checkStartTime()) {
            $("#starttime").next().text('开始时间不能小于系统当前时间！').show();
            flag = false;
        } else {
            $("#starttime").next().hide();
        }
    }
    // 校验结束时间
    var endTime = $("#endtime").val();
    if (endTime == "") {
        $("#endtime").next().text('结束时间不能为空！').show();
        flag = false;
    } else {
        if (!checkEndTime()) {
            $("#endtime").next().text('结束时间应该大于开始时间！').show();
            flag = false;
        } else {
            $("#endtime").next().hide();
        }
    }
    // 券用途长度校验
    var couponUse = $('#couponuse').val();
    if (couponUse.length > 200) {
        flag = false;
        $('#couponuse').next().text('券用途说明不能超过200字！').show();
    } else {
        $('#couponuse').next().hide();
    }

    return flag;
}
