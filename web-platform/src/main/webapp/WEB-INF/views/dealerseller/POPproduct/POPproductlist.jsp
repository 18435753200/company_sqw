 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-商品列表</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path }/commons/css/product.css"/>
    <script type="text/javascript" src="${path }/commons/js/POPproduct_list_fn.js"></script>
</head>
<body>
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
    <div class="blank"></div>

	<div class="center">
		<!--中间左边开始-->
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->
		<!--中间左边结束-->
		<div class="alert_user3" style="display: none;">
			<div class="bg"></div>
			<div class="w">
				<div class="box1">
					<img src="${path}/commons/images/close.jpg" class="w_close">
				</div>
				<div class="box3">
					<p id="showmsgplat"></p>
				</div>
				<div class="blank20"></div>
			</div>
		</div>
		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">商品列表</p>
				</div>
			</div>
			<div class="blank10"></div>
			<input type="hidden" id="isReivewHidden" value="${isReivew }" />
			<input type="hidden" id="currentPageIdHidden" value="${currentPageId }" />
			<div class="c22">
				<div class="c3" id="cp3">
					<ul class="top goods-tab">
					
						<li class="list" id="li_POP_list"><a href="javascript:void(0)" onclick="getDownProduct(1)"  liststatus='1'>商品列表</a></li>
						<li class="list" id="li_POP_sheng"><a href="javascript:void(0)" onclick="getDownProduct(2)"  liststatus='2'>商品审核</a></li>
						
						<%-- <c:if test="${! empty buttonsMap['货品列表-待出售'] }">
							<li class="" id="li_onthemarket"><a
								href="javascript:void(0)" onclick="getDownProduct(7)"
								liststatus='7'>待出售</a></li>
						</c:if>
						<c:if test="${! empty buttonsMap['货品列表-已下架'] }">
							<li class="" id="li_xiajia"><a href="javascript:void(0)"
								onclick="getDownProduct(4)" liststatus='4'>已下架</a></li>
						</c:if> --%>
<%-- 						<c:if test="${! empty buttonsMap['货品列表-商品列表'] }"> --%>
<!-- 							<li class="" id="li_xiajia"> -->
<!-- 								<a href="javascript:void(0)" onclick="getDownProduct(11)" liststatus='11'>商品列表</a> -->
<!-- 							</li> -->
<%-- 						</c:if> --%>
<%-- 						<c:if test="${! empty buttonsMap['货品列表-已删除'] }"> --%>
<!-- 							<li class="" id="li_del"><a href="javascript:void(0)" -->
<!-- 								onclick="getDownProduct(9)" liststatus='9'>已删除</a></li> -->
<%-- 						</c:if> --%>
						<!-- <li class="" id="li_newsku"><a href="javascript:void(0)"      onclick="getDownProduct(6)" id='6'>删除的单品</a></li> -->
					</ul>
					<div class="xia">
						<form action="javascript:void(0)">
							<p class="p1">
								<span>商品编码 ：</span> 
								<input type="text" class="text1" id="businessProdId"> 
								<span>商品ID ：</span>
								<input type="text" class="text1" id="productId">
								<span>供应商 ：</span> 
								<input type="text" class="text1" id="suppliername"> 
							</p>
						 	<p class="p1">
								<span>商品名称 ：</span> 
								<input type="text" class="text1" id="b2cProductName"> 
								<span>&nbsp;&nbsp;条形码 ：</span> 
								<input type="text" class="text1" id="barCode">
								</p>
								<p class="p1"> 
									<span>商品类目 ： </span>
									<select id="firstcategory"><option value="">请选择</option></select>
									<select id="secondcategory"></select> 
									<select id="thirdcategory"></select> 
									<select id="fourthcategory"></select>
							</p>
							<p class="p1" id="productIsTate">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;状态 ：</span>
								<select id="popIsTate">
									<option value="-1">请选择</option>
									<option value="1">上架的商品</option>
									<option value="0">下架的商品</option>
								</select>
							</p>
							<p class="p1" id="shengheIsTate">
								<span>审核状态 ：</span>
								<select id="popshengIsTate">
									<option value="-1">请选择</option>
									<option value="0">待审核</option>
									<option value="1">审核不通过</option>
									<option value="5">审核驳回</option>
								</select>
								
							</p>
							<p class="p1" >
								<span>商品类别 ：</span>
								<select id="prodType">
									<option value="">请选择</option>
									<option value="5">家庭号商品</option>
									<option value="6">幸福购商品</option>
									<option value="0">普通商品</option>
								</select>
								
							</p>
							<p class="p3">
								<button type="submit" id="subfm" onclick="clickSubmit()">搜索</button>
								<a href="#" id="czhi" onclick="resetfm()">重置</a>
							</p>
						</form>
					</div>


					<div class="blank5"></div>
					
					<c:if test="${! empty buttonsMap['货品列表-导出表格'] }">	
						  <div class="budd" id="exportExcelDiv">
								<a href="javascript:void(0)" onclick="downProductListExcel()"> 
									<span class="button red"> 
										<span class="text">
											<img alt="" src="${path }/commons/images/down-icon.png" height="19px" width="19px"> POP导出表格
										</span>
									</span>
								</a>
						  </div>
					 </c:if>	
					<span style="color: #ff6600;font-size: 14px;margin-bottom:5px;display: block;" class="text"></span>
					<div class="c3" id="c3"></div>
					
				</div>
			</div>
		</div>
	</div>




	<div class="blank10"></div>
	 <!-- 底部 start -->
		<%@include file="/WEB-INF/views/include/foot.jsp"%>
		<!-- 底部 end -->
		
<div class="lightbox" id="goout-box">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box"></div>
		<div class="lightbox-box-hd">
			<h2>请填写下架原因</h2>
		</div>
		<div class="lightbox-box-bd">
			<form id="alertProductStopReason">
				<select id="stopReasonType">
					<option value="缺货">缺货</option>
					<option value="滞销">滞销</option>
					<option value="汰换">汰换</option>
					<option value="更新商品信息">更新商品信息</option>
					<option value="其他">其他</option>
				</select>
				<textarea rows="3" cols="20" class="goout-text" name="stopReason" id="stopReason" ></textarea>
				<input type="hidden" value="" id="alertProductStopReasonId" name="pid">
			</form>
		</div>
		<div class="lightbox-box-bar">
			<a href="javascript:void(0);" class="lightbox-btn true-btn" onclick="proDown()">提 交</a>
			<span style="margin-left: 20px;color: red;" id="boxwarn"></span>
		</div>
	</div>
</div>

<div class="lightbox" id="goout-box-under">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box"></div>
		<div class="lightbox-box-hd">
			<h2>请填写违规下架原因</h2>
		</div>
		<div class="lightbox-box-bd">
			<form id="productIllegalUnderShelf">
			    <span style="color: red;font-size:14px;font-weight:bold;">违规下架后，商品会被移动到审核列表，状态变为审核驳回状态，请谨慎操作；</span>
			    <br/>
			    <br/>
				<select id="underReasonType">
					<option value="缺货">缺货</option>
					<option value="滞销">滞销</option>
					<option value="汰换">汰换</option>
					<option value="更新商品信息">更新商品信息</option>
					<option value="其他">其他</option>
				</select>
				<textarea rows="3" cols="20" class="goout-text" name="underReason" id="underReason" ></textarea>
				<input type="hidden" value="" id="productIllegalUnderShelfId" name="pid">
			</form>
		</div>
		<div class="lightbox-box-bar">
			<a href="javascript:void(0);" class="lightbox-btn true-btn" onclick="productUnder()">提 交</a>
			<span style="margin-left: 20px;color: red;" id="boxwarnunder"></span>
		</div>
	</div>
</div>
<div class="lightbox" id="goout-box-underxj">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box" onclick="closexj()"></div>
		<div class="lightbox-box-hd">
			<h2>商品星级标签限定(可多选)</h2>
		</div>
		<div class="lightbox-box-bd">
			<form id="productIllegalUnderShelfxj">
				<h3 ></h3>
			    <br/>
			   	<h3 id="xjid"></h3>	
				<input type="hidden"  value="" id="productIllegalUnderShelfIdxj" name="pid">
			</form>
		</div>
		<br/>
		<div class="lightbox-box-bar">
			<a href="javascript:void(0);" class="lightbox-btn true-btn" onclick="productUnderxj()">提 交</a>
			<span style="margin-left: 20px;color: red;" id="boxwarnunder"></span>
		</div>
	</div>
</div>
<div class="lightbox" id="goout-box-underxg">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box" onclick="closexg()"></div>
		<div class="lightbox-box-hd">
			<h2>商品限购标签设置(可多选)</h2>
		</div>
		<div class="lightbox-box-bd">
			<form id="productIllegalUnderShelfxg">
				<h3 ></h3>
			    <br/>
			   	<h3 id="xgid"></h3>	
				<input type="hidden"  value="" id="productIllegalUnderShelfIdxg" name="pid">
			</form>
		</div>
		<br/>
		<div class="lightbox-box-bar">
			<a href="javascript:void(0);" class="lightbox-btn true-btn" onclick="productUnderxg()">提 交</a>
			<span style="margin-left: 20px;color: red;" id="boxwarnunder"></span>
		</div>
	</div>
</div>

<div class="lightbox" id="goout-box-underzj">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box" onclick="closezj()"></div>
		<div class="lightbox-box-hd">
			<h2>商品赠券标签设置</h2>
		</div>
		<div class="lightbox-box-bd">
			<form id="productIllegalUnderShelfzj">
				<h3 ></h3>
			    <br/>
			   	<select id="zjid">
			   	</select>
				<input type="hidden"  value="" id="productIllegalUnderShelfIdzj" name="pid">
			</form>
		</div>
		<br/>
		<div class="lightbox-box-bar">
			<a href="javascript:void(0);" class="lightbox-btn true-btn" onclick="productUnderzj()">提 交</a>
			<span style="margin-left: 20px;color: red;" id="boxwarnunder"></span>
		</div>
	</div>
</div>
<div class="lightbox" id="goout-box-underlx">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box" onclick="closelx()"></div>
		<div class="lightbox-box-hd">
			<h2>商品类型标签设置(可多选)</h2>
		</div>
		<div class="lightbox-box-bd">
			<form id="productIllegalUnderShelflx">
				<h3 ></h3>
			    <br/>
			   <h3 id="lxid"></h3>	
				<input type="hidden"  value="" id="productIllegalUnderShelfIdlx" name="pid">
			</form>
		</div>
		<br/>
		<div class="lightbox-box-bar">
			<a href="javascript:void(0);" class="lightbox-btn true-btn" onclick="productUnderlx()">提 交</a>
			<span style="margin-left: 20px;color: red;" id="boxwarnunder"></span>
		</div>
	</div>
</div>

<div class="lightbox" id="del-box">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box"></div>
		<div class="lightbox-box-hd">
			<h2>请填写删除原因</h2>
		</div>
		<div class="lightbox-box-bd">
			<form id="alertProductStopReason">
				<textarea rows="3" cols="20" class="goout-text" name="delReason" id="delReason" ></textarea>
				<input type="hidden" value="" id="alertProductdelReasonId" name="pid">
			</form>
		</div>
		<div class="lightbox-box-bar">
			<a href="javascript:void(0);" class="lightbox-btn true-btn" onclick="del_product()">提 交</a>
			<span style="margin-left: 20px;color: red;" id="boxwarn"></span>
		</div>
	</div>
</div>
		
<div class="lightbox" id="copyData" style="display:none">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		
	</div>
</div>
		
</body>
</html>