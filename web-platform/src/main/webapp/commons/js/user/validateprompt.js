if($("#language").val() == "en"){
    $.extend(validatePrompt, {
        regName: {
            onFocus: "Please enter a valid e-mail address.",
            succeed: "",
            isNull: "Please enter a valid e-mail address.",
            error: {
                beUsed: "userName",
                badLength: "userName",
                badFormat: "userName",
                fullNumberName: "userName"
            }
            // ,
            // onFocusExpand: function () {
            //     // $("#morePinDiv").removeClass().addClass("intelligent-error hide");
            // }
        },

        pwd: {
            onFocus: "<span>The password should be within 6-16 characters</span>",
            succeed: "",
            isNull: "password",
            error: {
                badLength: "password",
                badFormat: "password",
                simplePwd: "password",
                weakPwd: "password"
            }
        // ,
        // onFocusExpand: function () {
        //     $("#pwdstrength").hide();
        // }
    },
        pwdRepeat: {
            onFocus: "Pleast re-enter your password",
            succeed: "",
            isNull: "Pleast re-enter your password",
            error: {
                badLength: "password 6-20",
                badFormat2: "password",
                badFormat1: "password "
            }
        },
        companyname: {
            onFocus: "",
            succeed: "",
            isNull: "Please enter company name",
            error: ""
        },
        companytype: {
            onFocus: "companytype",
            succeed: "",
            isNull: "companytype",
            error: ""
        },
        companyarea: {
            onFocus: "",
            succeed: "",
            isNull: "companyarea",
            error: ""
        },
        companyaddr: {
            onFocus: "",
            succeed: "",
            isNull: "companyaddr",
            error: ""
        },
        contactname: {
            onFocus: "",
            succeed: "",
            isNull: "contactname",
            error: ""
        },
        tele: {
            onFocus: "",
            succeed: "",
            isNull: "tele",
            error: ""
        },
        contacttele: {
            onFocus: "",
            succeed: "",
            isNull: "contacttele",
            error: ""
        },
        postalcode: {
            onFocus: "",
            succeed: "",
            isNull: "postalcode",
            error: ""
        },
        textfield: {
            onFocus: "",
            succeed: "",
            isNull: "file",
            error: ""
        },
        textfield1: {
            onFocus: "",
            succeed: "",
            isNull: "file is null",
            error: ""
        },
        cgr: {
            onFocus: "",
            succeed: "",
            isNull: "cgr",
            error: ""
        },
        brand: {
            onFocus: "",
            succeed: "",
            isNull: "brand",
            error: ""
        },
        islogin: {
            onFocus: "",
            succeed: "",
            isNull: "islogin",
            error: ""
        },
        issale: {
            onFocus: "",
            succeed: "",
            isNull: "issale",
            error: ""
        },
        skunum: {
            onFocus: "",
            succeed: "",
            isNull: "sku",
            error: "sku"
        },
        mail: {
            onFocus: "Email",
            succeed: "",
            isNull: "Email",
            error: {
                beUsed: "Email",
                badFormat: "Email",
                badLength: "Email"
            }
        }
    });

}else{
    $.extend(validatePrompt, {
        companyname: {
            onFocus: "",
            succeed: "",
            isNull: "请输入公司名称",
            error: ""
        },
        companytype: {
            onFocus: "生产厂家或者非生产厂家",
            succeed: "",
            isNull: "请输入公司性质",
            error: ""
        },
        companyarea: {
            onFocus: "",
            succeed: "",
            isNull: "请输入公司所在国家地区",
            error: ""
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
            error: ""
        },
        contacttele: {
            onFocus: "",
            succeed: "",
            isNull: "请输入联系人电话或传真",
            error: ""
        },
        postalcode: {
            onFocus: "",
            succeed: "",
            isNull: "请输入邮政编码",
            error: ""
        },
        textfield: {
            onFocus: "",
            succeed: "",
            isNull: "请上传能够证明企业合法性的证明文件",
            error: ""
        },
        textfield1: {
            onFocus: "",
            succeed: "",
            isNull: "请上传企业详细说明文件",
            error: ""
        },
        cgr: {
            onFocus: "",
            succeed: "",
            isNull: "请输入商品类别",
            error: ""
        },
        brand: {
            onFocus: "",
            succeed: "",
            isNull: "请输入商品品牌",
            error: ""
        },
        islogin: {
            onFocus: "",
            succeed: "",
            isNull: "请注明商品品牌是否在中国注册",
            error: ""
        },
        issale: {
            onFocus: "",
            succeed: "",
            isNull: "请注明商品是否在中国已销售",
            error: ""
        },
        skunum: {
            onFocus: "",
            succeed: "",
            isNull: "请输入sku数量",
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
}


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

    fileField1: function (option) {
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
/*
        $("#companyname").jdValidate(validatePrompt.companyname, validateFunction.companyname, true);
        $("#companytype").jdValidate(validatePrompt.companytype, validateFunction.companyname, true);
        $("#companyaddr").jdValidate(validatePrompt.companyaddr, validateFunction.companyname, true);
        $("#companyarea").jdValidate(validatePrompt.companyarea, validateFunction.companyname, true);

        $("#contactname").jdValidate(validatePrompt.contactname, validateFunction.companyname, true);
        $("#tele").jdValidate(validatePrompt.tele, validateFunction.companyname, true);
        $("#mailbox").jdValidate(validatePrompt.mail, validateFunction.mail, true);
        $("#contacttele").jdValidate(validatePrompt.contacttele, validateFunction.companyname, true);
        $("#postalcode").jdValidate(validatePrompt.postalcode, validateFunction.companyname, true);
        $("#fileField").jdValidate(validatePrompt.textfield, validateFunction.fileField, true);
        $("#fileField1").jdValidate(validatePrompt.textfield1, validateFunction.fileField, true);

        $("#mer-cgr").jdValidate(validatePrompt.cgr, validateFunction.companyname, true);
        $("#mer-brand").jdValidate(validatePrompt.brand, validateFunction.companyname, true);
        $("#skunum").jdValidate(validatePrompt.skunum, validateFunction.companyname, true);

        $("#chinacompanyaddr").jdValidate(validatePrompt.companyaddr, validateFunction.companyname, true);
        $("#chinacontactname").jdValidate(validatePrompt.contactname, validateFunction.companyname, true);
        $("#chinacontacttele").jdValidate(validatePrompt.contacttele, validateFunction.companyname, true);
        $("#chinacompanyname").jdValidate(validatePrompt.companyname, validateFunction.companyname, true);
        
        $("#chinamail").jdValidate(validatePrompt.mail, validateFunction.mail, true);*/

        return validateFunction.FORM_submit(["#regName", "#pwd", "#pwdRepeat"]);
        
    }
});


$("#pwd").bind("keyup", function () {
    validateFunction.pwdstrength();}).jdValidate(validatePrompt.pwd, validateFunction.pwd)
$("#pwdRepeat").jdValidate(validatePrompt.pwdRepeat, validateFunction.pwdRepeat);
$("#regName").jdValidate(validatePrompt.regName, validateFunction.regName);
/*
$("#companyname").jdValidate(validatePrompt.companyname, validateFunction.companyname);
$("#companytype").jdValidate(validatePrompt.companytype, validateFunction.companyname);
$("#companyaddr").jdValidate(validatePrompt.companyaddr, validateFunction.companyname);
$("#companyarea").jdValidate(validatePrompt.companyarea, validateFunction.companyname);

$("#contactname").jdValidate(validatePrompt.contactname, validateFunction.companyname);
$("#tele").jdValidate(validatePrompt.tele, validateFunction.companyname);
$("#mailbox").jdValidate(validatePrompt.mail, validateFunction.mail);
$("#contacttele").jdValidate(validatePrompt.contacttele, validateFunction.companyname);
$("#postalcode").jdValidate(validatePrompt.postalcode, validateFunction.companyname);
$("#fileField").jdValidate(validatePrompt.textfield, validateFunction.companyname);
$("#fileField1").jdValidate(validatePrompt.textfield1, validateFunction.companyname);

$("#mer-cgr").jdValidate(validatePrompt.cgr, validateFunction.companyname);
$("#mer-brand").jdValidate(validatePrompt.brand, validateFunction.companyname);
$("#skunum").jdValidate(validatePrompt.skunum, validateFunction.companyname);

$("#chinacompanyaddr").jdValidate(validatePrompt.companyaddr, validateFunction.companyname);
$("#chinacontactname").jdValidate(validatePrompt.contactname, validateFunction.companyname);
$("#chinacontacttele").jdValidate(validatePrompt.contacttele, validateFunction.companyname);
$("#chinacompanyname").jdValidate(validatePrompt.companyname, validateFunction.companyname);

$("#chinamail").jdValidate(validatePrompt.mail, validateFunction.mail);*/


$("#viewpwd").bind("click", function () {
    if ($(this).attr("checked") == true) {
        validateFunction.showPassword("text");
        $("#o-password").addClass("pwdbg");
    } else {
        validateFunction.showPassword("password");
        $("#o-password").removeClass("pwdbg");
    }
});

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
    	$("#formcompany").submit();
        // $("#registsubmit").attr({
        //     "disabled": "disabled"
        //     // }).removeClass().addClass("btn-img btn-regist wait-btn");
        // }).removeClass().addClass("btn-img btn-regist wait-btn");
       /* $.ajax({
            type: "POST",
            url: "regist",
            contentType: "multipart/form-data; charset=utf-8",
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
        });*/
    	
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