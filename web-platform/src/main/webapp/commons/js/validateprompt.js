$.extend(validatePrompt, {
    companyname: {
        onFocus: "",
        succeed: "",
        isNull: "请输入公司名称",
        error: ""
    },
    legalname: {
        onFocus: "",
        succeed: "",
        isNull: "请输入法人姓名",
        error: ""
    },
    legalidnum: {
        // onFocus: "请输入法人的身份证或者护照号码",
        onFocus: "请输入法人的身份证号码",
        succeed: "",
        isNull: "请输入法人证件号码",
        error: "请输入正确的身份证号码"
    },
    companyaddr: {
        onFocus: "",
        succeed: "",
        isNull: "请输入公司地址",
        error: ""
    },
    contactname: {
        onFocus: "",
        succeed: "",
        isNull: "请输入联系人姓名",
        error: ""
    },
    tele: {
        onFocus: "",
        succeed: "",
        isNull: "请输入手机号码",
        error: "请输入正确的手机号码"
    },
    contacttele: {
        onFocus: "",
        succeed: "",
        isNull: "请输入联系人电话",
        error: "请输入正确的电话号码"
    },
    postalcode: {
        onFocus: "",
        succeed: "",
        isNull: "请输入邮政编码",
        error: "请输入正确的邮政编码"
    },
    busilicense: {
        onFocus: "",
        succeed: "",
        isNull: "请上传营业执照",
        error: ""
    },
    Taxregistcerti: {
        onFocus: "",
        succeed: "",
        isNull: "请上传税务登记证",
        error: ""
    },
    accopenlicense: {
        onFocus: "",
        succeed: "",
        isNull: "请上传开户许可证",
        error: ""
    },
    Taxpayqualicerti: {
        onFocus: "",
        succeed: "",
        isNull: "请上传一般纳税人资格证明",
        error: ""
    },
    legalcerti: {
        onFocus: "",
        succeed: "",
        isNull: "请上传法人代表身份证明文件",
        error: ""
    },
    foreigntrade: {
        onFocus: "",
        succeed: "",
        isNull: "请上传对外贸易经营者备案登记表",
        error: ""
    },
    foreigntrade1: {
        onFocus: "",
        succeed: "",
        isNull: "请上传进出口货物收发货人报关注册登记证",
        error: ""
    },
    foreigntrade2: {
        onFocus: "申请食品经销商必须上传",
        succeed: "",
        isNull: "",
        error: ""
    },
    foreigntrade3: {
        onFocus: "申请食品化妆品经销商必须上传",
        succeed: "",
        isNull: "",
        error: ""
    },
    foreigntrade4: {
        onFocus: "申请成为食品经销商必须上传",
        succeed: "",
        isNull: "",
        error: ""
    },
    foreigntrade5: {
        onFocus: "申请成为保健品经销商必须上传",
        succeed: "",
        isNull: "",
        error: ""
    },
    mail: {
        onFocus: "请输入常用的邮箱",
        succeed: "",
        isNull: "请输入邮箱",
        error: {
            beUsed: "该邮箱已被使用，请更换其它邮箱",
            badFormat: "请输入有效的邮箱地址",
            badLength: "您填写的邮箱过长，邮箱地址只能在50个字符以内"
        }
    }
});

$.extend(validateFunction, {
    mail: function (option) {
        var format = validateRules.isEmail(option.value);
        var format2 = validateRules.betweenLength(option.value, 0, 50);
        if (!format) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        } else {
            if (!format2) {
                validateSettings.error.run(option, option.prompts.error.badLength);
            } else {
                // if (!emailstate || emailold != option.value) {
                //     if (emailold != option.value) {
                //         emailold = option.value;
                //         validateSettings.succeed.run(option);
                //         emailstate = true;
                //     } else {
                //         validateSettings.error.run(option, option.prompts.error.beUsed);
                //         emailstate = false;
                //     }
                // } else {
                //     if ($("#email_linker")) {
                //         $("#email_linker").text(option.value);
                //     }
                    validateSettings.succeed.run(option);
                // }
            }
        }
    },

    tele:function (option) {
        var format = validateRules.isMobile(option.value);
        if (!format) {
            validateSettings.error.run(option, option.prompts.error);
        } else {
            validateSettings.succeed.run(option);
        }
    },

    contacttele:function (option) {
        var format = validateRules.isTel(option.value);
        if (!format) {
            validateSettings.error.run(option, option.prompts.error);
        } else {
            validateSettings.succeed.run(option);
        }
    },

    postalcode:function (option) {
        var format = validateRules.isZipcode(option.value);
        if (!format) {
            validateSettings.error.run(option, option.prompts.error);
        } else {
            validateSettings.succeed.run(option);
        }
    },

    legalidnum:function (option) {
        var format = validateRules.isIdcard(option.value);
        if (!format) {
            validateSettings.error.run(option, option.prompts.error);
        } else {
            validateSettings.succeed.run(option);
        }
    },

    companyname: function (option) {
        var bool = validateRules.isNull(option.value);
        if (bool) {
            validateSettings.error.run(option, option.prompts.error);
            return;
        } else {
            validateSettings.succeed.run(option);
        }
    },

    fileField: function (option) {
        var bool = validateRules.isNull(option.value);
        if (bool) {
            validateSettings.error.run(option, option.prompts.error);
            return;
        } else {
            validateSettings.succeed.run(option);
        }
    },
    
    emRegCompany_validate: function () {

        $("#regName").jdValidate(validatePrompt.regName, validateFunction.regName, true);
        $("#pwd").jdValidate(validatePrompt.pwd, validateFunction.pwd, true)
        $("#pwdRepeat").jdValidate(validatePrompt.pwdRepeat, validateFunction.pwdRepeat, true);

        $("#companyname").jdValidate(validatePrompt.companyname, validateFunction.companyname, true);
        $("#companyaddr").jdValidate(validatePrompt.companyaddr, validateFunction.companyname, true);
        $("#legalname").jdValidate(validatePrompt.legalname, validateFunction.companyname, true);
        $("#legalidnum").jdValidate(validatePrompt.legalidnum, validateFunction.legalidnum, true);
        $("#contactname").jdValidate(validatePrompt.contactname, validateFunction.companyname, true);
        $("#tele").jdValidate(validatePrompt.tele, validateFunction.tele, true);
        $("#mailbox").jdValidate(validatePrompt.mail, validateFunction.mail, true);
        $("#contacttele").jdValidate(validatePrompt.contacttele, validateFunction.contacttele, true);
        $("#postalcode").jdValidate(validatePrompt.postalcode, validateFunction.postalcode, true);

        $("#busilicense").jdValidate(validatePrompt.busilicense, validateFunction.fileField, true);
        $("#Taxregistcerti").jdValidate(validatePrompt.Taxregistcerti, validateFunction.fileField, true);
        $("#accopenlicense").jdValidate(validatePrompt.accopenlicense, validateFunction.fileField, true);
        $("#Taxpayqualicerti").jdValidate(validatePrompt.Taxpayqualicerti, validateFunction.fileField, true);
        $("#legalcerti").jdValidate(validatePrompt.legalcerti, validateFunction.fileField, true);
        $("#foreigntrade").jdValidate(validatePrompt.foreigntrade, validateFunction.fileField, true);
        $("#foreigntrade1").jdValidate(validatePrompt.foreigntrade1, validateFunction.fileField, true);

        return validateFunction.FORM_submit(["#regName", "#pwd", "#pwdRepeat", "#legalname", "#companyaddr", "#legalidnum", "#companyname", "#contactname", "#tele", "#mailbox","#contacttele","#postalcode","#busilicense","#Taxregistcerti","#accopenlicense","#Taxpayqualicerti","#legalcerti","#foreigntrade","#foreigntrade1"]);
        
    }
});


$("#pwd").bind("keyup", function () {
    validateFunction.pwdstrength();}).jdValidate(validatePrompt.pwd, validateFunction.pwd)
$("#pwdRepeat").jdValidate(validatePrompt.pwdRepeat, validateFunction.pwdRepeat);
$("#regName").jdValidate(validatePrompt.regName, validateFunction.regName);

$("#companyname").jdValidate(validatePrompt.companyname, validateFunction.companyname);
$("#legalname").jdValidate(validatePrompt.legalname, validateFunction.companyname);
$("#legalidnum").jdValidate(validatePrompt.legalidnum, validateFunction.legalidnum);
$("#companyaddr").jdValidate(validatePrompt.companyaddr, validateFunction.companyname);
$("#contactname").jdValidate(validatePrompt.contactname, validateFunction.companyname);
$("#tele").jdValidate(validatePrompt.tele, validateFunction.tele);
$("#mailbox").jdValidate(validatePrompt.mail, validateFunction.mail);
$("#contacttele").jdValidate(validatePrompt.contacttele, validateFunction.contacttele);
$("#postalcode").jdValidate(validatePrompt.postalcode, validateFunction.postalcode);

$("#busilicense").jdValidate(validatePrompt.busilicense, validateFunction.fileField);
$("#Taxregistcerti").jdValidate(validatePrompt.Taxregistcerti, validateFunction.fileField);
$("#accopenlicense").jdValidate(validatePrompt.accopenlicense, validateFunction.fileField);
$("#Taxpayqualicerti").jdValidate(validatePrompt.Taxpayqualicerti, validateFunction.fileField);
$("#legalcerti").jdValidate(validatePrompt.legalcerti, validateFunction.fileField);
$("#foreigntrade").jdValidate(validatePrompt.foreigntrade, validateFunction.fileField);
$("#foreigntrade1").jdValidate(validatePrompt.foreigntrade1, validateFunction.fileField);
$("#foreigntrade2").jdValidate(validatePrompt.foreigntrade2, validateFunction.fileField);
$("#foreigntrade3").jdValidate(validatePrompt.foreigntrade3, validateFunction.fileField);
$("#foreigntrade4").jdValidate(validatePrompt.foreigntrade4, validateFunction.fileField);
$("#foreigntrade5").jdValidate(validatePrompt.foreigntrade5, validateFunction.fileField);


// $("#viewpwd").bind("click", function () {
//     if ($(this).attr("checked") == true) {
//         validateFunction.showPassword("text");
//         $("#o-password").addClass("pwdbg");
//     } else {
//         validateFunction.showPassword("password");
//         $("#o-password").removeClass("pwdbg");
//     }
// });

//公司所在地
// $("select[@name2='hncompanyarea']").bind("change", function () {
//     var elements = $("select[@name2='hncompanyarea']");
//     if (validateFunction.checkSelectGroup(elements)) {
//         $("#hncompanyarea").val("1");
//         $("#hncompanyarea").attr("sta", 2);
//         $("#hncompanyarea_error").html("");
//         $("#hncompanyarea_error").removeClass();
//         $("#hncompanyarea_succeed").addClass("succeed");
//     } else {
//         $("#hncompanyarea").val("-1");
//         $("#hncompanyarea").attr("sta", 0);
//         $("#hncompanyarea_error").html("");
//         $("#hncompanyarea_succeed").removeClass("succeed");
//     }
// });

//购买类型 设备
// $("input:checkbox[@name='purposetype']").bind("click", function () {
//     var value1 = $("#purpose").val();
//     var value2 = $(this).val();
//     if ($(this).attr("checked") == true) {
//         if (value1.indexOf(value2) == -1) {
//             $("#purpose").val(value1 + value2);
//             $("#purpose").attr("sta", 2);
//             $("#purpose_error").html("");
//             $("#purpose_error").removeClass();
//             $("#purpose_succeed").addClass("succeed");
//         }
//     } else {
//         if (value1.indexOf(value2) != -1) {
//             value1 = value1.replace(value2, "");
//             $("#purpose").val(value1);
//             if ($("#purpose").val() == "") {
//                 $("#purpose").attr("sta", 0);
//                 $("#purpose_succeed").removeClass("succeed");
//             }
//         }
//     }
// });

function protocolReg() {
    $("#closeBox").click();
    $("#registsubmit").click();
}

$("#registsubmit").click(function () {
    // var agreeProtocol = checkReadMe();
    // var regnameOk = validateRegName();
    var flag = validateFunction.emRegCompany_validate();
    if (flag) {
        // $("#registsubmit").attr({
        //     "disabled": "disabled"
        //     // }).removeClass().addClass("btn-img btn-regist wait-btn");
        // }).removeClass().addClass("btn-img btn-regist wait-btn");
        $.ajax({
            type: "POST",
            url: "www.baidu.com?r=" + Math.random(),
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: $("#formcompany").serialize(),
            success: function (result) {
                if (result) {
                    var obj = eval(result);
                    if (obj.info) {
                        $("#registsubmit").removeAttr("disabled").removeClass().addClass("btn-img btn-regist");
                        alert(obj.info);
                        verc();
                    }
                    if (obj.success == true) {
                        window.location = obj.dispatchUrl;
                    }
                }
            }
        });
    }
})
$(

function () {
    refreshAreas(1, 0);

    function refreshAreas(level, parentId) {
        var myname;
        if (level == 1) {
            myname = "companycity";
            if (parentId == -1) {
                $("#companycity").empty();
                $("#companycity").append("<option value=\"-1\" selected>请选择</option>");
                $("#companycity").css({
                    "width": "auto"
                });
                $("#companyarea").empty();
                $("#companyarea").append("<option value=\"-1\" selected>请选择</option>");
                $("#companyarea").css({
                    "width": "auto"
                });
            }
        } else {
            myname = "companyarea";
            if (parentId == -1) {
                $("#companyarea").empty();
                $("#companyarea").append("<option value=\"-1\" selected>请选择</option>");
                $("#companyarea").css({
                    "width": "auto"
                });
            }
        }
        if (parentId > 0) {
            $.getJSON(
                "../reg/area?level=" + level + "&parentId=" + parentId + "&r=" + Math.random(),

            function (result) {
                if (result.Areas != null) {
                    $("#" + myname).empty();
                    $("#" + myname).append("<option value=\"-1\"  selected>请选择</option>");
                    for (var i = 0; i < result.Areas.length; i++) {
                        var area = result.Areas[i];
                        $("#" + myname).append("<option  value=\"" + area.Id + "\">" + area.Name + "</option>");
                    }
                    $("#" + myname).css({
                        "width": "Auto"
                    });
                }
            });
        }
    }

    $("#companyprovince").change(

    function () {
        $("#hncompanyarea_error").removeClass();
        refreshAreas(1, parseInt($(this).val()));
        $("#companyarea").empty();
        $("#companyarea").append("<option value=\"-1\" selected>请选择</option>");
    })

    $("#companycity").change(

    function () {
        $("#hncompanyarea_error").removeClass();
        refreshAreas(2, parseInt($(this).val()));
    });
})

function checkReadMe() {
    if ($("#readme").attr("checked") == true) {
        $("#protocol_error").removeClass().addClass("error hide");
        return true;
    } else {
        $("#protocol_error").removeClass().addClass("error");
        return false;
    }
}

function agreeonProtocol() {
    if ($("#readme").attr("checked") == true) {
        $("#protocol_error").removeClass().addClass("error hide");
        return true;
    }
}