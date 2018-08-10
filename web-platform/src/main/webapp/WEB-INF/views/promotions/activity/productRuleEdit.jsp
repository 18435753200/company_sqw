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
						<div class="act-title">创建商品活动规则</div>

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
                        <option selected="" value="0">请选择</option>
                        <option value="运营部">运营部</option>
                    </select><span class="font-color">请选择创建部门</span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="actType">活动类型：</label>
            <div class="field">
                <select name="ruleTerm" id="ruleTerm" class="act-sel">
                    <option selected="" value="0">请选择</option>
                    <option value="1">满返活动</option>
                    <option value="2">阶梯满赠活动</option>
                    
                </select><span class="font-color">请选择活动类型</span>
            </div>
        </div>
        
          <div class="form-inline form-box J-type hide" id="J-type-fullcut0">
           <div class="form-group">
           
            <div class="field">
            <label>赠品类型：</label>
                <select name="gifttype" id="isgift" class="act-sel">
                    <option  value="0">请选择</option>
                    <option value="3">按单品</option>
                </select>
                <span class="font-color"></span>
            </div>
           </div>
        </div>
      
        
        <div class="form-inline form-box J-type hide" id="J-type-fullcut1">
           <div class="form-group">
            <div class="field">
            	<label>满赠方式：</label>
                <select name="sendtype" id="isAmout" class="act-sel">
                    <option selected="selected" value="0">请选择</option>
                    <option value="1">按数量</option>
                    <option value="2">按金额</option>
                </select>
                
            </div>
           </div>
        </div>
      
        <div class="form-inline form-box J-type hide" id="J-type-fullcut">
        
            <div class="form-group">
                <label for="act-startMoney">前置条件：(输入正整数)</label>
                <div class="field">
                    <input type="text" name="term1" id="term1" class="act-txt"><span class="font-color"></span>
                </div>
            </div>
            
            <div class="form-group">
                <label for="actCoupon">后置条件：</label>
                <div class="field">
                    <select name="giftType" id="giftType" class="act-sel">
                        <option selected="" value="0">请选择</option>
                        <option  value="1">金券</option>

                    </select>
                    <span class="font-color">请选择赠品</span>

                </div>
            </div>
            
            <div class="form-group" style="display: none" id="coupon">
                <label for="actCoupon">赠品:金券/现金券：</label>
                <div class="field">
                    <select name="term2" id="term2" class="act-sel">
                        
                    </select>
                </div>
            </div>

             <div class="form-group" style="display: none" id="isrepeat1">
                <label for="actCoupon">是否自动阶梯送券：</label>
                <div class="field">
                    <select name="isrepeat" id="isrepeat" class="act-sel">
                        <option value="0" selected="selected">否</option>
                        <option value="1">是</option>
                    </select>
                </div>
            </div>

        </div>
        
        
        
    <div id="mib-box" style="display:none;">  
         <input type="button" id="btn_addtr" value="增行">
         <input type="button" id="btn-deltr"  value="删行">
          
         <div id="box1">
        
	        <div class="form-inline form-box J-type  J-type-fullcut"  id="J-type-fullcut2">
	        
	            <div class="form-group">
	                <label for="act-startMoney">第一条件</label>
	                <div class="field">
	                    <input type="text" name="firstCondition" id="term_00" class="act-txt start_price"><span class="font-color">提示:如果满赠方式为按数量，则填正整数，如果按金额，则可以填小数，最多两位</span>
	                  
	                </div>
	            </div>
	            
	            <div class="form-group">
	                <label for="act-startMoney">第二条件</label>
	                <div class="field">
	                    <input type="text" name="SecondCondition" id="term_11" class="act-txt end_price"><span class="font-color">提示:第二条件与第一条件填法一样，如果不填，为默认最大值999999</span>
	                   
	                </div>
	            </div>
	            
	             <div class="form-group">
	                <label for="actCoupon" ><span>第三条件</span></label>
	                <div class="field zp-box">
	                    <select name="thirdCondition" id="giftType1" class="act-sel" >
	                        <option selected="" value="0">请选择</option>

	                    </select><span class="font-color"></span>
	                    <input type="hidden" name="giftName" id="giftName" value="">
	                </div>
	            </div>
	            
	            <div class="form-group">
	                <label for="actCoupon">第四条件（每次要赠的赠品数量）</label>
	                <div class="field">
	                      <input type="text" name="fourCondition" id="term_33" class="act-txt zpnub" value=""><span class="font-color"></span>
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
            <label for="act-why">活动类型范围限制条件：</label>
            <div class="field">
                <select name="ruleCondition" id="ruleCondition" class="act-sel">
                    <option selected="" value="100">请选择</option>
                    <option value="0">全场</option>
                    <option value="2">品牌</option>
                    <option value="1">分类</option>
                    <option value="3">商品</option>
                </select><span class="font-color">请选择活动类型规范限制条件</span>
            </div>
        </div>
        <div class="form-group" id="ruleSorts1">
            <label for="act-yxj">每人每天限制次数：</label>
            <div class="field">
                <input type="text" name="limittimes" id="limittimes" class="act-txt"><span class="font-color"></span> 
            </div>
        </div>   
        <div class="form-group" id="b2b_channels">
            <label for="act-yxj">渠道：</label>
            <div class="field">
                <select name="chid" id="channels" class="act-sel">
                   <option value="0">请选择</option>
                   <option value="-1">全国</option>                   
                   <c:forEach var="canalCondition" items="${channelList}">
                          	<option value="${canalCondition.channelId}">${canalCondition.channelName}</option>                   
                   </c:forEach>
                </select>
                <span class="font-color">请选择渠道</span> 
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
               	<div class="selected" id="selected-brands">
					<div class="selected-name">品牌<em>&gt;</em></div>
					<div class="selected-list">
						<ul>
							<!-- brands -->
						</ul>
					</div>
				</div>
				<div class="selected" id="selected-sort">
					<div class="selected-name">分类<em>&gt;</em></div>
					<div class="selected-list">
						<ul>
							<!-- sort -->
						</ul>
					</div>
				</div>
				<div class="selected" id ="selected-goods">
					<div class="selected-name">商品<em>&gt;</em></div>
					<div class="selected-list">
						<ul>
							<!-- goods -->
						</ul>
					</div>
				</div>
            </div>
        </div>
        <!-- 品类 -->
        <div class="hide J-why" id="J-why-sort">
            <div class="act-sort-crumb" id="selectCategory">
            	<dt style="float:left">您当前选择类目：</dt>
                <input type="button" name="addCategory" id="addCategory" class="addto" value="添加">
            </div>
            <div class="form-box">
                <div class="act-sort-content">
                	<!-- 一级分类 -->
                    <div class="sort-item">
                        <ul>
                            <c:forEach items="${topCategoryList }" var="topList">
                            	<li id="${topList.catePubId }">${topList.pubNameCn}</li>
                            </c:forEach>
                        </ul>
                    </div>
                    <!-- 二级分类 -->
                    <div class="sort-item original">
                        <ul style="display: none;">
                            
                        </ul>
                    </div>
                    <!-- 三级分类 -->
                    <div class="sort-item original">
                        <ul style="display: none;">
                            
                        </ul>
                    </div>
                    <!-- 四级分类 -->
                    <div class="sort-item original">
                        <ul style="display: none;">
                            
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- 品牌 -->
        <div class="form-box hide J-why" id="J-why-brands">
            <div class="act-brands-content">
                <div class="act-brands-list">
                    <ul class="act-list">
                   		
                    </ul>
                </div>
                <div class="act-brands-sub">
                    <input type="button" name="addbrand" id="addbrand" class="addto" value="添加">
                </div>
            </div>
        </div>
        <!-- 商品 -->
        <div class="form-box hide J-why" id="J-why-goods">
            <div class="act-goods-hd">
                <dl>
                    <dt>商品名称</dt>
                    <dd>
                        <input type="text" name="productName" id="productName" class="act-txt">
                        <input type="button" class="act-btn" value="查询" id="findbypid" name="findbypid">
                    </dd>
                </dl>
                <div class="act-goods-sub">
                    <input type="button" name="addProduct" id="addProduct" class="addto" value="添加">
                </div>
            </div>
            
            <div class="act-goods-content">
                <div class="act-goods-list">
                    <ul class="act-list">
                    
                    </ul>
                </div>
                
            </div>
            <div id="pages" align="right" class="page-goods">
            	<input id="firstPage" name="firstPage" value="首页" type="button">
               	<input id="previousPage" name="previousPage" value="上一页" type="button">
               	<label id="page">1</label>
               	<input id="nextPage" name="nextPage" value="下一页" type="button">
               	<input id="totalPage" name="totalPage" value="99" type="text" style="display: none">
            </div>
        </div>
        <div class="form-group f-tac">
            <input type="button" name="saveProductRule" id="saveProductRule" 
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

<!-- 页面点击查看 规则条件-->
 <div class="lightbox" id="goout-box-condition">
	<div class="lightbox-overlay"></div>
	<div class="lightbox-box">
		<div class="close-box"></div>
		<div class="lightbox-box-hd">
			<h2>赠品列表如下：</h2>
		</div>
		<div id="skuListContident" class="lightbox-box-bd">
		          
		</div>
	</div>
</div>
	<script type="text/javascript">
		
	</script>
	
  
        
        	
	
</body>
</html>