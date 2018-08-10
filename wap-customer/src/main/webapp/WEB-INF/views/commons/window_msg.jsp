<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- alert窗口 --%>
<div class="pop-shade hide" id="alertWindow">
    <div class="pop">
        <div class="pop-msg">
            <div class="pop-msg-title"><!-- 商品已下架，请重新选择商品 --></div>
            <div class="pop-msg-cnt">
                <ul class="pop-goods-list">
                    <!-- <li><a href="#">好吃点香脆核桃饼 108g 好吃点香脆核桃饼 108g</a></li>
                    <li><a href="#">好吃点香脆核桃饼 108g 好吃点香脆核桃饼 108g</a></li>
                    <li><a href="#">好吃点香脆核桃饼 108g 好吃点香脆核桃饼 108g</a></li>
                    <li><a href="#">好吃点香脆核桃饼 108g 好吃点香脆核桃饼 108g</a></li> -->
                </ul>
            </div>
            <div class="pop-msg-btn"><a class="pop-btn" href="javascript:;">确定</a></div>
        </div>
    </div>
</div>

<%-- confirm窗口 --%>
<div class="pop-shade hide" id="confirmWindow">
    <div class="pop">
        <div class="pop-msg">
            <div class="pop-msg-title">众聚猫提示</div>
            <div class="pop-msg-cnt">
                <p><!-- 免税店商品需要办理入境申报，请您配合进行实名认证 --></p>
            </div>
            <div class="pop-msg-btn">
            	<a class="pop-btn cancelBtn" href="javascript:;">取消</a>
            	<a class="pop-btn sureBtn" href="javascript:;">去认证</a>
            </div>
        </div>
    </div>
</div>
