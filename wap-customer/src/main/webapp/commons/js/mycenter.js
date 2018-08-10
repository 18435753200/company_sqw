$("#J_mycenterTabs li").on("click", function() {
    var s = $(this);
    s.addClass("at").siblings().removeClass("at"), $(".order_w").eq(s.index()).show().siblings(".order_w").hide();
});
