<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <title>代理商管理设置</title>
    <%@include file="/WEB-INF/views/include/base.jsp" %>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
    
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/page_supplieragent.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/platform_page_content.css">
    
    
</head>
<script type="text/javascript">

var savetips={sav:0,fai:0,suc:0};
    $(document).ready(function () {
    });
    function addSettings(btn) {
    	
        var sid=$("#sbx").val();
        var stx=$("#stx").val();
        var sdx=$("#sdx").val();
        var szx=$("#szx").val();
        var sax=$("#sax").val().trim();
    	
        if(szx>=100){
        	alert("分红利润不能超过100");
        	return false;
        }
        if(! /^\d{0,8}\.{0,1}(\d{1,2})?$/.test(szx)){
    	    alert("请输入正确的大于0的数字,最多有两位小数");
    	    return false;
       }
      
      
        if((!RegExp("^\\d{1,5}\\.\\d+$").test(sid) && !RegExp("^\\d{1,5}\\.?$").test(sid))|| Number(sid)<=0||sid<=""||stx<=""||sdx<=""||szx<=""||sax<=""){
            alert("请全部正确填写新增的区域数据！");
           
        }else{
        	
            $.ajax({
                type: 'post',
                url: '../infrastructure/insertSupplier',
                data: 'mode=1&code='+sid+"&name="+stx+"&type="+sdx+"&shareBenefit="+szx+"&pId="+sax,
                success: function (row) {
                	
                    if (row = 1) {
                        window.location.href="${path}/infrastructure/manager?page=${page}";
                    } else {
                        tipMessage("操作失败，请检查后重试！", function () {
                        });
                    }
                }
            });
        }
        
    }
    /* function checkRoleDel(logisticTempId,supplierId){
    	$.dialog.confirm('确定删除此模板?', function(){
    		$.ajax({
     	         type: "POST",
     	         dataType:"json",
     	         url: "./deleteGeneralLogisTpl",
     	         data: "logisticTempId="+logisticTempId+"&"+"supplierId="+supplierId,
     	         success: function (msg) {
     	        	 if(msg=0){
     	        	    alert("您的操作有误,请稍候再试!");
     	        	   
     	        	 }else{
     	        		alert("删除成功!");
     	        		window.location.href="../order/getGeneralLogisPage";
     	        	 }
     	          }
     	    });
    		},function(){
    			
    		});
    } */

    function delSettings(id) {
    	$.dialog.confirm('确定删除此模板?', function(){
    		$.ajax({
                type: 'post',
                url: '../infrastructure/deleteSupplier',
                data: "id="+id,
                success: function (data) {
                	if(data=0){
     	        	    alert("此区域已有入驻企业，不允许删除！操作失败，请检查后重试！");
     	        	   
     	        	 }else{
     	        		alert("删除成功!");
     	        		window.location.href="${path}/infrastructure/manager?page=${page}";
     	        	 }
                }
            });
    		
    		
    	},function(){
			
		});
    }
    
    function saveSettings() {
        var cou = parseInt($("#ct").val());
        var s="";
        var d=[];
        for(var i=0;i<cou;i++) {
            var sid = $("#sid" + i).val();
            var stx = $("#stx" + i).val();
            var sto = $("#sto" + i).val();
            var sdx = $("#sdx" + i).val();
            var sdo = $("#sdo" + i).val();
            var szx = $("#szx" + i).val();
            var szo = $("#szo" + i).val();
            var sax = $("#sax" + i).val();
            var sao = $("#sao" + i).val();
            var sbx = $("#sbx" + i).val();
            var sbo = $("#sbo" + i).val();
            
            if(szx>=100){
            	alert("分红利润不能超过100");
            	return false;
            }
            if(! /^\d{0,8}\.{0,1}(\d{1,2})?$/.test(szx)){
        	    alert("请输入正确的大于0的数字,最多有两位小数");
        	    return false;
           }
           
            if ((stx == sto)&&(sdx == sdo)&&(szx == szo)&&(sax == sao)) continue;
            d.push({d: sid, bx:sbx,text: stx, dt:sdx, zt:szx, at:sax});
        }
        savetips.fai=0;
        savetips.sav=d.length;
        for(var i=0;i<d.length;i++){
            $.ajax({
                type: 'post',
                url: '../infrastructure/updateSupplier',
                data: "mode=0&id="+d[i].d+"&code="+d[i].bx+"&name="+d[i].text+"&type="+d[i].dt+"&shareBenefit="+d[i].zt+"&pId="+d[i].at,
                success: function (data) {
                    savetips.sav=savetips.sav-1;
                    if (data != '1') {
                        savetips.fai=savetips.fai+1;
                       
                    }
                    if(savetips.sav<=0){
                    	if( tipMessage(savetips.fai>0)){
                    		alert("操作失败，请检查后重试！");
                    	}else{
                    		alert("数据修改保存成功！");
                    		window.location.href="${path}/infrastructure/manager?page=${page}";
                    	}
                        /* tipMessage((savetips.fai>0)?"操作失败，请检查后重试！":"数据修改保存成功！", function () {
                        }); */
                    }
                }
            });
        }
    }
    function toPage(page){
        window.location.href="${path}/infrastructure/supplierRegionSettingsxfg?page="+page;
    }
    function sum(){
    	
    }
</script>
<body>
<%@include file="/WEB-INF/views/include/header.jsp" %>
<div class="wrap">
    <!-- 左边 start -->
    <div class="left f_l">
        <%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
    </div>
    <!-- 左边 end -->
    <!-- 右边 start -->
    <div class="right">
        <div class="c21">
            <div class="title">
                <p>卖家中心&nbsp;&gt;&nbsp; </p>
                <p>基础设置&nbsp;&gt;&nbsp; </p>
                <p class="c1">代理商管理设置</p>
            </div>
        </div>
        <div class="platform_page_content">
         <form method="post" id="planImpl" enctype="multipart/form-data">
                <div>
                    <table class="platform_page_content_table" >
         			<tr class="platform_page_content_table_tr">
                            <th width=16%">编码</th>
                            <th width="16%">名称</th>
                            <th width="16%">代理类型</th>
                            <th width="16%">分润</th>
                            <th width="16%">上级</th>
                            <th width="20%">操作</th>	
                        </tr>
                
                        <c:forEach items="${list }" var="l" varStatus="status">
                            <tr>
                                    <input type="hidden" id="sid${status.index}" name="sid${status.index}" value="${l.id}"/>
                                <td>
                                 <input  id="sbx${status.index}" name="sbx${status.index}" value="${l.code}"/>
                                 <input type="hidden" id="sbx${status.index}" name="sbx${status.index}" value="${l.code}"/>
                                </td>
                                <td>
                                   <select id="stx${status.index}" name="stx${status.index}" value="${l.name}" >
                               		 
                               		<option value="">--请选择--</option>
                               		<option value="系统运营商">系统运营商</option>
                               		<option value="全国运营商">全国运营商</option>
                               		<option value="省级运营商">省级运营商</option>
                               		<option value="地市级运营商">地市级运营商</option>
                               		<option value="区县级运营商">区县级运营商</option>
                               		<option value="市场合伙人">市场合伙人</option>
                               		<option value="线下商家">线下商家</option> 
                               		
                               		<c:if test="${l.name=='系统运营商'}">
                               		<option value="系统运营商" selected="selected">系统运营商</option></c:if>
                               		<c:if test="${l.name=='全国运营商'}">
                               		<option value="全国运营商" selected="selected">全国运营商</option></c:if>
                               		<c:if test="${l.name=='省级运营商'}">
                               		<option value="省级运营商" selected="selected">省级运营商</option></c:if>
                               		<c:if test="${l.name=='地市级运营商'}">
                               		<option value="地市级运营商" selected="selected">地市级运营商</option></c:if>
                               		<c:if test="${l.name=='区县级运营商'}">
                               		<option value="区县级运营商" selected="selected">区县级运营商</option></c:if>
                               		<c:if test="${l.name=='市场合伙人'}">
                               		<option value="市场合伙人" selected="selected">市场合伙人</option></c:if>
                               		<c:if test="${l.name=='线下商家'}">
                               		<option value="线下商家" selected="selected">线下商家</option></c:if>
                               		
                               		</select>
                                    <input type="hidden" id="sto${status.index}" name="sto${status.index}" value="${l.name}"/>
                                </td>
                                
                               <td>
                               		 <select id="sdx${status.index}" name="sdx${status.index}" value="${l.type}" >
                               		 
                               		<option value="">--请选择--</option>
                               		<option value="1">SO</option>
                               		<option value="2">NO</option>
                               		<option value="3">PO</option>
                               		<option value="4">CO</option>
                               		<option value="5">DP</option>
                               		<option value="6">MP</option>
                               		<option value="7">M</option> 
                               		<c:if test="${l.type==1}">
                               		<option value="1" selected="selected">SO</option></c:if>
                               		<c:if test="${l.type==2}">
                               		<option value="2" selected="selected">NO</option></c:if>
                               		<c:if test="${l.type==3}">
                               		<option value="3" selected="selected">PO</option></c:if>
                               		<c:if test="${l.type==4}">
                               		<option value="4" selected="selected">CO</option></c:if>
                               		<c:if test="${l.type==5}">
                               		<option value="5" selected="selected">DP</option></c:if>
                               		<c:if test="${l.type==6}">
                               		<option value="6" selected="selected">MP</option></c:if>
                               		<c:if test="${l.type==7}">
                               		<option value="7" selected="selected">M</option></c:if>
                               		
                               		</select>
                                    <%-- <input type="text" id="sdx${status.index}" name="sdx${status.index}" value="${l.type}"/> --%>
                                    <input type="hidden" id="sdo${status.index}" name="sdo${status.index}" /> 
                                </td>
                                 <td>
                                    <input type="text" id="szx${status.index}" name="szx${status.index}" value="${l.shareBenefit}"/>
                                    <input type="hidden" id="szo${status.index}" name="szo${status.index}" value="${l.shareBenefit}"/>
                                </td>
                                
                                
                                
                                <%-- <td>
                                    <input type="text" id="sax${status.index}" name="sax${status.index}" value="${l.pId}"/>
                                    <input type="hidden" id="sao${status.index}" name="sao${status.index}" value="${l.pId}"/>
                                </td> --%>
                                 <td>
                               		 <select id="sax${status.index}" name="sax${status.index}"  >
                               		 <c:if test="${l.pId==0}">
                       					<option value="0">最高级</option>
                       				</c:if>
                               		<c:forEach items="${list}" var="li" varStatus="status">
                               		
                               		<c:if test="${l.pId==li.id}">
                               			<option value="${l.pId}" selected="selected">${li.name }</option>
                               		</c:if>
                               		
                       				<c:if test="${l.pId!=li.id}">
                  						<option value="${li.id}">${li.name }</option>
                  					</c:if>
                               		</c:forEach>
                               		 </select>
                  
                                    <%-- <input type="text" id="sdx${status.index}" name="sdx${status.index}" value="${l.type}"/> --%>
                                    <input type="hidden" id="sdo${status.index}" name="sdo${status.index}" /> 
                                </td>
                                 <td>
                                    <input type="button"  class="tjiao"  onclick="delSettings('${l.id}')" value="删除"/>
                                </td> 
                            </tr>
                        </c:forEach>
                        
                        
                        
                        <tr>
                           <td>
                                <input type="text" id="sbx" name="sbx" value=""/>
                                <input type="hidden" id="sbo" name="sbo" value=""/>
                            </td>
                            <td>
                                <select  id="stx" name="stx" value="">
                                	<option value="">--请选择--</option>
                               		<option value="系统运营商">系统运营商</option>
                               		<option value="全国运营商">全国运营商</option>
                               		<option value="省级运营商">省级运营商</option>
                               		<option value="地市级运营商">地市级运营商</option>
                               		<option value="区县级运营商">区县级运营商</option>
                               		<option value="市场合伙人">市场合伙人</option>
                               		<option value="线下商家">线下商家</option> 
                               		</select>
                                <input type="hidden" id="sto" name="sto" value=""/>
                            </td>
                            
                             <td>
                               		 <select id="sdx${status.index}" name="sdx${status.index}" value="${l.type}" >
                               		 
                               		<option value="">--请选择--</option>
                               		<option value="1">SO</option>
                               		<option value="2">NO</option>
                               		<option value="3">PO</option>
                               		<option value="4">CO</option>
                               		<option value="5">DO</option>
                               		<option value="6">MP</option>
                               		<option value="7">M</option> 
                               		<c:if test="${l.type==1}">
                               		<option value="1" selected="selected">SO</option></c:if>
                               		<c:if test="${l.type==2}">
                               		<option value="2" selected="selected">NO</option></c:if>
                               		<c:if test="${l.type==3}">
                               		<option value="3" selected="selected">PO</option></c:if>
                               		<c:if test="${l.type==4}">
                               		<option value="4" selected="selected">CO</option></c:if>
                               		<c:if test="${l.type==5}">
                               		<option value="5" selected="selected">DO</option></c:if>
                               		<c:if test="${l.type==6}">
                               		<option value="6" selected="selected">MP</option></c:if>
                               		<c:if test="${l.type==7}">
                               		<option value="7" selected="selected">M</option></c:if>
                               		</select>
                             </td>  		
                            
                           <!--  <td>
                                <input type="text" id="sdx" name="sdx" value=""/>
                            </td> -->
                            
                            
                            
                            <td>
                                <input type="text" id="szx" name="szx" value=""/>
                            </td>
                            
                               <td>
                               		 <select id="sax${status.index}" name="sax${status.index}"  >
                               		 
                               		<option value="">--请选择--</option>
                               		<option value="0" >最高级</option>
                               		<c:forEach items="${list}" var="l" varStatus="status">
                               			<option value="${l.id}">${l.name }</option>
                               		</c:forEach>
                               		<!-- <option value="0">0</option>
                               		<option value="1">sp</option>
                               		<option value="2">yp</option>
                               		<option value="3">op</option>
                               		<option value="4">bp</option>
                               		<option value="5">ap</option>
                               		<option value="6">mp</option> -->
       
                               		
                               		
                               		</select>
                               		</td>
                           <!--  <td>
                                <input type="text" id="sao" name="sao" value=""/>
                            </td> -->
                            
                            <td>
                                <input type="button" class="tjiao" onclick="addSettings(this)" value="新增"/>
                            </td>
                        </tr>
                        <tr class="x1">
                            <td colspan="6" align="center" class="p3">
                                <input type="hidden" id="ct" name="ct" value="${fn:length(list)}" />
                                <input type="button" class="tjiao" onclick="saveSettings()" value="保存修改"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <c:if test="${!empty pageBean.result}">
                    <%@include file="/WEB-INF/views/dealerbuy/basePage/basePage.jsp" %>
                </c:if>
            </form>
        
        
        
        </div>
    </div>
    <!-- 右边 end -->
</div>

<div class="blank10"></div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp" %>
</body>

</html>