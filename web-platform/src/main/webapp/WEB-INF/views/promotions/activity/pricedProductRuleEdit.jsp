<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>创建商品活动规则-促销活动</title>
 <%@include file="/WEB-INF/views/include/base.jsp"%> 
<link href="${path}/commons/css/reset.css" rel="stylesheet"	type="text/css">
<link href="${path}/commons/css/act.css" rel="stylesheet" type="text/css">	
<%-- <script type="text/javascript" src="${path}/commons/js/jquery-1.8.1.min.js"></script> --%>
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/act.js"></script>
<script src="${path}/commons/js/category_promotion.js"></script>
</head>
<body>
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->

	<div class="center">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/promotions/leftPage.jsp"%>
		</div>

		<!-- 左边 end -->
		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>促销管理&nbsp;&gt;&nbsp;</p>
					<p>活动管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">活动列表</p>
				</div>

				<div class="blank10"></div>


				<div class="act-wrap">
    <div class="act-bd manage-act">
						<div class="act-title">创建限时直降/阶梯满赠/满返（券）活动规则</div>

						<form method="post" id="productRuleAction"
							enctype="multipart/form-data">
							<input type="hidden" name="activeId" id="activeId"
								value="${activeId }">
							<div class="form-inline">
            <div class="form-group">
                <label for="act-name">子活动名称：</label>
                <div class="field">
                    <input type="text" name="ruleName" id="ruleName" class="act-txt"><span class="font-color"></span>
                </div>
            </div>
            <div class="form-group">
                <label for="act-section">创建部门：</label>
                <div class="field">
                    <select name="createBy" id="createBy" class="act-sel">
                        <option selected="selected" value="0">请选择</option>
                        <option value="运营部">运营部</option>
                    </select><span class="font-color">请选择创建部门</span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="actType">活动类型：</label>
            <div class="field">
                <select name="ruleTerm" id="ruleTerm" class="act-sel">
                    <option selected="selected" value="0">请选择</option>
                    <option value="10">限时直降活动</option>
                    <option value="13">阶梯满赠活动</option>
                    <option value="14">满返（券）活动</option>
                   <!--  <option value="15">满减活动</option> -->
                </select><span class="font-color">请选择活动类型</span>
            </div>
        </div>
        
         <div class="form-inline form-box J-type hide" id="J-type-fullcut0">
           <div class="form-group">
            <div class="field">
            	<label for="actType">赠品类型：</label>
                <select name="gifttype" id="isgift" class="act-sel">
                    <option selected="selected" value="0">请选择</option>
                    <option value="3">按单品</option>
                </select>
            </div>
           </div>
        </div>
        
        
        
         <div class="form-inline form-box J-type hide" id="J-type-fullcut1">
           <div class="form-group">
            <div class="field">
            	<label for="actType">满赠方式：</label>
                <select name="sendtype" id="isAmout" class="act-sel">
                    <option selected="selected" value="0">请选择</option>
                    <option value="1">按数量</option>
                    <option value="2">按金额</option>
                </select>
            </div>
           </div>
        </div>
        <!-- 限时直降 -->
        <div class="form-inline form-box J-type hide" id="J-type-fullcut">
            <div class="form-group">
                <label for="act-startMoney" id="labelText">前置条件：(输入正整数)</label>
                <div class="field">
                    <input type="text" name="term1" id="term1" class="act-txt"><span class="font-color"></span>
                </div>
            </div>
            <div class="form-group">
                <label for="actCoupon">后置条件：</label>
                <div class="field">
                    <select name="giftType" id="giftType" class="act-sel">
                        <option selected="selected" value="0">请选择</option>
                        <option value="2">直降金额</option>
                    </select><span class="font-color">请选择后置条件</span>
                </div>
            </div>
            
            <div class="form-group" style="display: none" id="cashAmout">
                <label for="actCoupon">直降金额：</label>
                <div class="field">
                    <input id="term2" name="term2" class="act-txt"><span class="font-color"></span>
                </div>
            </div>
        </div>
        <!-- 满返 -->
        <div class="form-inline form-box J-type hide" id="J-type-fullback">
        
            <div class="form-group">
                <label for="act-startMoney">满足金额：(输入正整数)</label>
                <div class="field">
                    <input type="text" name="fullAmount" id="fullAmount" class="act-txt"><span class="font-color"></span>
                </div>
            </div>
            
            <div class="form-group">
                <label for="actCoupon">返券类型：</label>
                <div class="field">
                    <select name="couponType" id="couponType" class="act-sel">
                        <option selected="selected" value="0">请选择</option>
                        <option  value="1">金券</option>

                    </select>
                    <span class="font-color">请选择优惠券类型</span>

                </div>
            </div>
            
            <div class="form-group" style="display: none" id="coupon">
                <label for="actCoupon">优惠券:金券/现金券：</label>
                <div class="field">
                    <select name="coupons" id="coupons" class="act-sel">
                        
                    </select>
                </div>
            </div>

             <div class="form-group" style="display: none" id="isrepeate1">
                <label for="actCoupon">是否自动阶梯送券：</label>
                <div class="field">
                    <select name="isrepeate" id="isrepeate" class="act-sel">
                        <option value="0" selected="selected">否</option>
                        <option value="1">是</option>
                    </select>
                </div>
            </div>

        </div>
        <!-- 满减 -->
        <div class="form-inline form-box J-type hide" id="J-type-fullback1">
        
            <div class="form-group">
                <label for="act-startMoney">满足金额：(输入正整数)</label>
                <div class="field">
                    <input type="text" name="fullAmount1" id="fullAmount1" class="act-txt"><span class="font-color"></span>
                </div>
            </div>
            <div class="form-group">
                <label for="act-startMoney">满减金额：(输入正整数)</label>
                <div class="field">
                    <input type="text" name="cutAmount" id="cutAmount" class="act-txt"><span class="font-color"></span>
                </div>
            </div>
        </div>
        <!-- 阶梯满赠 -->
          <div id="mib-box" style="display:none;">  
          
         <div id="box1">
        
	        <div class="form-inline form-box J-type  J-type-fullcut"  id="J-type-fullcut2">
	        
	            <div class="form-group">
	                <label for="act-startMoney">第一条件</label>
	                <div class="field">
	                    <input type="text" name="firstCondition" id="term_00" class="act-txt start_price"><span class="font-color"></span>
	                  
	                </div>
	            </div>
	            
	            
	             <div class="form-group">
	                <label for="actCoupon" ><span>第三条件</span></label>
	                <div class="field zp-box">
	                    <select name="thirdCondition" id="giftType1" class="act-sel" >
	                        <option selected="selected" value="0">请选择</option>

	                    </select><span class="font-color"></span>
	                    <input type="hidden" name="giftName" id="giftName" value="">
	                </div>
	            </div>
	            
	            <div class="form-group">
	                <label for="actCoupon">第四条件（每次要赠的赠品数量）</label>
	                <div class="field">
	                      <input type="text" name="fourCondition" id="term_33" class="act-txt"><span class="font-color"></span>
	                   
	                </div>
	            </div>
	            
	            <div class="form-group"  id="isrepeat1">
	                <label for="actCoupon">是否自动阶梯送赠品：</label>
	                <div class="field">
	                    <select name="isrepeat" id="isrepeat" class="act-sel">
	                        <option value="0" selected="selected">否</option>
	                        <option value="1">是</option>
	                    </select>
	                </div>
            	</div>
	            
	            <div class="form-group"  id="skuList">
	                          
	            </div>
	            
	          
	           
	        </div>
	        
	        </div>
        
        </div>      
        
        <div class="form-inline">
            <div class="form-group">
                <label for="act-startTime">开始时间：</label>
                <div class="field">
                    <input type="text" name="startdate" id="startdate" 
                    class="act-txt" onClick="WdatePicker()"><span class="font-color"></span>
                </div>
            </div>
            
            <div class="form-group">
                <label for="act-endTime">结束时间：</label>
                <div class="field">
                    <input type="text" name="enddate" id="enddate" 
                    class="act-txt" onClick="WdatePicker()"><span class="font-color"></span>
                </div>
            </div>
        </div>
        
        
        <div class="form-group">
            <label for="act-why">活动类型规范限制条件：</label>
            <div class="field">
                <select name="ruleCondition" id="ruleCondition" class="act-sel">
                    <option selected="selected" value="100">请选择</option>
                    <option value="4">单品</option>
                </select><span class="font-color">请选择活动类型规范限制条件</span>
            </div>
        </div>
        <div class="form-group" id="ruleSorts" style="display: none">
            <label for="act-yxj">规则优先级：</label>
            <div class="field">
                <input type="text" name="ruleSort" id="ruleSort" class="act-txt"><span class="font-color"></span> 
            </div>
        </div>
        <!-- 已选条件 -->
        <div class="form-group" id="selectoption">
            <label>已选条件：</label>
            <div class="field">
				<div class="selected" id ="selected-skus">
					<div class="selected-name">单品<em>&gt;</em></div>
					<div class="selected-list">
						<ul>
							<!-- skus -->
						</ul>
					</div>
				</div>
            </div>
        </div>
        
        <div class="form-box hide J-why" id="J-why-skus">
            <div class="act-goods-hd">
                <dl>
                <!-- 单品 -->
                    <dt>单品名称</dt>
                    <dd>
                        <input type="text" name="skuName" id="skuName" class="act-txt">
                        <input type="button" class="act-btn" value="查询" id="findbyskuId" name="findbyskuId">
                    </dd>
                </dl>
                <div class="act-goods-sub">
                    <input type="button" name="addsku" id="addsku" class="addto" value="添加">
                </div>
            </div>
            
            <div class="act-goods-content">
                <div class="act-goods-list">
                    <ul class="act-list">
                    
                    </ul>
                </div>
                
            </div>
            <div id="pages" align="right" class="page-goods">
            	<input id="fPage" name="firstPage" value="首页" type="button">
               	<input id="pPage" name="previousPage" value="上一页" type="button">
               	<label id="page">1</label>
               	<input id="nPage" name="nextPage" value="下一页" type="button">
               	<input id="totalPage" name="totalPage" value="99" type="text" style="display: none">
               	共<label id="total"></label>页
            </div>
        </div>
        <div class="form-group f-tac">
            <input type="button" name="savePricedProductRule" id="savePricedProductRule" 
            class="act-btn actManage-btn confirm" value="保 存">
        </div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>

	<script type="text/javascript">
		
	</script>
	
	
</body>
</html>