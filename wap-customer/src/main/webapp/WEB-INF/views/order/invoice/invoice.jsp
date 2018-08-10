<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="item">
	<div class="bill clearfix">
    	<div class="check-p cart-all"><label><input type="checkbox">我要开发票</label></div>
        <span class="ask"></span>
    </div>
</div>
<div class="bill_open" style="display:none">
    <div class="bill_head">
        <p class="bill_one">
        	<span>发票抬头</span>
        	<i><label><input type="radio" name="one" value="0" checked="checked">个人</label></i>
        	<i><label><input type="radio" name="one" value="1">单位</label></i>
        </p> 
    </div>
    <div class="bill_mid">
    	<input type="text" placeholder="请输入发票抬头" class="txt">
    </div>
    <div class="bill_bot">
    	<table>
        	<tr>
            	<td class="cont">发票内容</td>
                <td><label><input type="radio" name="nr" value="0" checked="checked">&nbsp;&nbsp;明细</label></td>
            </tr>
        	<tr>
            	<td></td>
                <td class="clearfix">
                	<input type="radio" name="nr" class="down" value="1">
                	<select id="sel_invoice_detail" disabled="disabled">
                    	<option value="日用品">日用品</option>
                    	<option value="食品">食品</option>
                    	<option value="酒水饮料">酒水饮料</option>
                    	<option value="服饰鞋包">服饰鞋包</option>
                    	<option value="礼品">礼品</option>
                    </select>
                </td>
            </tr>
        </table>
    </div>
</div>