<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<title>UNICORN-库存管理</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css"	href="${path}/commons/css/inventory1.css" />
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/inventory_fn.js"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="center">
		<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>

		<!--修改页面-非食品开始-->
		<div class="alert_shul" style="display: none;">
			<div class="bg"></div>
			<div class="wrap">
				<div class="box1">
					<p class="pic">
						<img src="${path}/commons/images/close.jpg" class="b_colse">
					</p>
				</div>
				<div class="box2">
					<form>
						<table id="showBach">
							<tr>
								<td>订单占用数量</td>
								<td>锁定数量</td>
								<td>库存数量</td>
							</tr>
							<tr>
								<td id="editNotBatchOrderOccupiedNum"></td>
								<td id="editNotBatchLockQty"></td>
								<td><input type="text" id="editNotBatchQty" class="frame"></td>
							</tr>
						</table>
						<c:if test="${! empty buttonsMap['库存管理-提交修改库存'] }">
							<button class="bt1" type="button"
								onclick="subEditinventoryNotBatch()">确定</button>
						</c:if>
						<button class="bt2" type="button">取消</button>
					</form>
				</div>
			</div>
		</div>
		<!--修改页面-非食品结束-->

		<!--修改页面-食品开始  1-->
		<div class="alert_shul1" style="display: none;">
			<div class="bg"></div>
			<div class="wrap">
				<div class="box1">
					<p class="pic">
						<img src="${path}/commons/images/close.jpg" class="b_colse">
					</p>
				</div>
				<div class="box2">
					<form>
						<table>
							<tr>
								<td class="batch">批次号</td>
								<td>锁定数量</td>
								<td>订单占用数量</td>
								<td>库存数量</td>
							</tr>
							<tr>
								<td id="editBatchNo" class="batch"></td>
								<td id="editLockQty"></td>
								<td id="editPreSubQty"></td>
								<td><input type="text" id="editBatchQty" class="frame">
								</td>
							</tr>
						</table>
						<c:if test="${! empty buttonsMap['库存管理-提交修改库存'] }">
							<button type="button" class="bt1" onclick="subEditInventory()">确定</button>
						</c:if>
						<button type="button" class="bt2">取消</button>
					</form>

				</div>
			</div>
		</div>
		<!--修改页面-食品结束-->
		
		<!--转移库存到赠品页面开始-->
		<div class="alert_shul2" style="display: none;">
			<div class="bg"></div>
			<div class="wrap">
				<div class="box1">
					<p class="pic">
						<img src="${path}/commons/images/close.jpg" class="b_colse">
					</p>
				</div>
				<div class="box2">
					<form>
						<table>
							<tr>
								<td class="batch">批次号</td>
								<td>锁定数量</td>
								<td>订单占用数量</td>
								<td>可转移库存数量</td>
							</tr>
							<tr>
								<td id="editBatchNo_gift" class="batch"></td>
								<td id="editLockQty_gift"></td>
								<td id="editPreSubQty_gift"></td>
								<td><input type="text" id="editBatchQty_gift" class="frame">
								<input type="hidden" id="editBatchQty_gift_hidden"/>
								</td>
							</tr>
						</table>
						<button type="button" class="bt1" onclick="moveToGiftSave()">确定</button>
						<button type="button" class="bt2">取消</button>
					</form>
				</div>
			</div>
		</div>
		<!--转移库存到赠品页面结束-->
		
		<!--转移库存到非赠品页面开始-->
		<div class="alert_shul3" style="display: none;">
			<div class="bg"></div>
			<div class="wrap">
				<div class="box1">
					<p class="pic">
						<img src="${path}/commons/images/close.jpg" class="b_colse">
					</p>
				</div>
				<div class="box2">
					<h2>温馨提示：请填写转移数量</h2>
					<form>
						<table>
							<tr>
								<th width="120px">商品名称</th>
								<th width="120px">SKU名称</th>
								<th width="70px" class="batch">批次号</th>
								<th width="60px">锁定数量</th>
								<th width="80px">订单占用数量</th>
								<th width="120px">可转移库存数量</th>
							</tr>
							<tr>
								<td id="editPName_gift1" class="batch"><i></i></td>
								<td id="editSkuName_gift1"><i></i></td>
								<td id="editBatchNo_gift1" class="batch"></td>
								<td id="editLockQty_gift1"></td>
								<td id="editPreSubQty_gift1"></td>
								<td><input type="text" id="editBatchQty_gift1" class="frame">
								<input type="hidden" id="editBatchQty_gift_hidden1"/>
								</td>
							</tr>
						</table>
						<button type="button" class="bt1" onclick="moveGiftToStock()">确定</button>
						<button type="button" class="bt2">取消</button>
					</form>
				</div>
			</div>
		</div>
		<!--转移库存到非赠品页面结束-->
		
		
		<!--锁定库存开始-非食品-->
		<div class="alert_nolock" id="alert_nolock" style="display: none;">
			<div class="bg"></div>
			<div class="wrap">
				<div class="box1">
					<p class="pic">
						<img src="${path}/commons/images/close.jpg" class="b_colse">
					</p>
				</div>
				<div class="box2">
					<h2>锁定数量不能大于可锁定数量,且要大于0</h2>
					<form>
						<table>
							<tr>
								<td>可锁定数量</td>
								<td>锁定数量</td>
							</tr>
							<tr>
								<td id="lockNotBatchQty"></td>
								<td><input id="lockNotBatchLockQty" type="text" class="frame"></td>
							</tr>
						</table>
						<c:if test="${! empty buttonsMap['库存管理-提交锁定库存'] }">
							<button type="button" class="bt1" onclick="sublockInventoryNotBatch()">确定</button>
						</c:if>
						<button type="button" class="bt2">取消</button>
					</form>
				</div>
			</div>
		</div>
		<!--锁定库存结束-非食品-->

		<!--锁定库存开始-食品-->
		<div class="alert_lock" style="display: none;">
			<div class="bg"></div>
			<div class="wrap">
				<div class="box1">
					<p class="pic">
						<img src="${path}/commons/images/close.jpg" class="b_colse">
					</p>
				</div>
				<div class="box2">
					<div class="lock-hd">
						<h2>
							可锁定数量：<span></span>
						</h2>
						<span class="lock-tips">（锁定数量之和不能大于可锁定数量,且要大于0）</span>
						<c:if test="${! empty buttonsMap['库存管理-删除锁定库存'] }">
							<input type="hidden" id="istruedel" value="1">
						</c:if>
					</div>
					<form id="fm">
						<table width="100%">
							<thead>
								<tr>
									<td width="120">商品名称</td>
									<td width="120">SKU名称</td>
									<td width="120">批次号</td>
									<td width="130">锁定时间</td>
									<td width="100">锁定原因</td>
									<td width="80">锁定数量</td>
									<td width="auto">操作</td>
								</tr>
							</thead>
							<tbody id="locktbody">

							</tbody>
						</table>
						<button type="button" class="bt1" id="addline">加一行</button>
						<c:if test="${! empty buttonsMap['库存管理-提交锁定库存'] }">
							<button type="button" class="bt1" id="confimaddLock">确定</button>
						</c:if>
					</form>
				</div>
			</div>
		</div>
		<!--锁定库存结束-食品-->

		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">库存管理</p>
				</div>
			</div>
			<div class="blank10"></div>

			<div class="c22">
				<ul class="top">
					<c:if test="${! empty buttonsMap['到岸库存'] }">
						<c:if test="${! empty buttonsMap['到岸库存'] }">
							<li class="list" id="li_wofeInventory"><a
								href="javascript:void(0)" onclick="getInventoryByType(1)"
								liststatus='1'>到岸库存</a></li>
						</c:if>
					</c:if>
					<c:if test="${! empty buttonsMap['供应商预定库存'] }">
						<c:if test="${! empty buttonsMap['供应商预定库存'] }">
							<li class="" id="li_supplierInventory"><a
								href="javascript:void(0)" onclick="getInventoryByType(3)"
								liststatus='3'>供应商预定库存</a></li>
						</c:if>
					</c:if>
					
					<li class="" id="li_supplierInventory"><a
						href="javascript:void(0)" onclick="getInventoryByType(4)"
						liststatus='4'>转运仓库</a>
					</li>
					
					<li class="" id="li_supplierInventory"><a
						href="javascript:void(0)" onclick="getInventoryByType(5)"
						liststatus='5'>保税区</a>
					</li>
					
					<li class="" id="li_supplierInventory"><a
						href="javascript:void(0)" onclick="getInventoryByType(7)"
						liststatus='7'>卓悦库存</a>
					</li>
					
					<li class="" id="Merchandise_inventory"><a
						href="javascript:void(0)" onclick="getInventoryByType(6)"
						liststatus='6'>赠品库存</a>
					</li>
					<li class="" id="POP_inventory"><a
							href="javascript:void(0)" onclick="getInventoryByType(8)"
							liststatus='8'>POP库存</a>
					</li>
				</ul>

				<div class="search">
					<form id="inventory_fm">
						<div class="search-form clearfix">
							<dl>
								<dt>商品名称：</dt>
								<dd>
									<input name="pname" type="text" id="spname" class="search-text">
								</dd>
							</dl>
							<dl id="stocknum">
								<dt>库存数量：</dt>
								<dd>
									<input type="text" id="ssqty" class="search-text"> 到 <input
										id="seqtyNum" type="text" name="eqty" class="search-text">
								</dd>
							</dl>
							<dl id="inventorytypeSpan">
								<dt>库存类型：</dt>
								<dd>
									<select id="ownerType" class="search-sel">
										<option value="1">自有库存</option>
										<option value="2">其他库存</option>
									</select>
								</dd>
							</dl>
							<dl id="timeSpan">
								<dt>入库时间：</dt>
								<dd>
									<input type="text" class="search-text" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'sstopTime\',{d:-1});}'})" readonly="readonly" id="screateTime"> 到 <input type="text" class="search-text" onClick="WdatePicker({minDate:'#F{$dp.$D(\'screateTime\',{d:1});}'})" readonly="readonly" id="sstopTime">
								</dd>
							</dl>
							<dl id="supplierSpan">
								<dt>供应商名称：</dt>
								<dd>
									<input name="supplierName" type="text" id="supplierName" class="search-text">
								</dd>
							</dl>
							<dl id="warehouseNameSelect">
								<dt>条形码：</dt>
								<dd>
									<input name="skuCode" type="text" id="skuCode" class="search-text">
								</dd>
							</dl>
							<dl id="productID">
								<dt>商品ID：</dt>
								<dd>
									<input name="pId" type="text" id="pId" class="search-text">
								</dd>
							</dl>
							<dl id="stock_skuID">
								<dt>SKU ID：</dt>
								<dd>
									<input name="skuID" type="text" id="skuID" class="search-text">
								</dd>
							</dl>
							<dl id="skuCodeSpan">
								<dt>条形码：</dt>
								<dd>
									<input name="skuCode" type="text" id="skuCode" class="search-text">
								</dd>
							</dl>
						</div>
					</form>
					<div class="search-sub">
						<input onclick="clickSubmit()" type="button" value="搜索" class="search-btn confirm-btn">
						 <input onclick="resetwarehouseselect()" type="button" id="czhi" value="重置" class="search-btn cancel-btn">
					</div>


				</div>

				<div class="c24" id="c24" style="overflow: auto;"></div>

			</div>
			<div class="blank20"></div>
		</div>
	</div>
	<%@include file="/WEB-INF/views/include/foot.jsp" %>
</body>
</html>