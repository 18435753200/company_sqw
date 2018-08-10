<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <title>审批优惠券设置</title>
    <%@include file="/WEB-INF/views/include/base.jsp" %>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/page_supplieragent.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/platform_page_content.css">
    
    
</head>
<script type="text/javascript">

var savetips={sav:0,fai:0,suc:0};
    $(document).ready(function () {
    	
    	 var checkMore=$("#checkMore");
    	    checkMore.click(function(){
    			if ($(this).attr("checked") == "checked") { // 全选 
    				$("input[type='checkbox'][name='topProB2B']").each(function() {
    					$(this).attr("checked", true);
    				});
    			}else{
    				$("input[type='checkbox'][name='topProB2B']").each(function() {
    					$(this).attr("checked", false);
    				});
    			}
    	    });
    });
   
   
    //批量导出
	 function outExcel(){
			var chk_value =[]; 
	    	$('input[name="topProB2B"]:checked').each(function(){ 
	    	chk_value.push($(this).val()); 
	    	}); 
	    	if(chk_value.length==0){
	    		alert("你还没有选择任何内容");
	    		return ;
	    	}
 		 $.dialog.confirm('您确定导出吗?', function(){
			window.location.href="../infrastructure/excelOut?ids="+chk_value.join();
			
 }); 
 } 
    
	//导出所有
	 function outExcelAll(){
			
	    	 var options=$("#test option:selected"); 
			 var sss=options.val();
 		 $.dialog.confirm('您确定导出吗?', function(){
			window.location.href="../infrastructure/excelOutAll?status="+sss;
			
 }); 
 } 
    
	 //条件搜索
	 function findByStatus(){
		 var options=$("#test option:selected"); 
		 var sss=options.val();
		 window.location = "../infrastructure/code?status="+sss;
	 }
		
    
    
   
    //分页查询
    function clickSubmit(page){
    	 var options=$("#test option:selected"); 
		 var sss=options.val();
    	window.location.href="${path}/infrastructure/code?page="+page+"&status="+sss;
    }
   
    //生成二维码
    function create(){
		
		 var num= $(" input[ name='num' ] ").val()
		
		 if(num<=0){
			 alert("对不起,你输入的生成数量无效,请重新输入");
			 return;
		 }
		
		 if(! /^[0-9]+$/.test(num)){
	        	alert("请输入正确的数字");
	        	return false;
		 }
		 $.dialog.confirm('您确定生成吗?', function(){
	     
		 $.ajax({
			 url:'../infrastructure/codeCreateGet?num='+num,
			 success:function(row){
				  if(row==0){
					 alert("生成成功");
					 window.location.reload();
				 }else{
					 alert("操作失败,请检查后重试");
				 } 
			 }
		 });
		 }); 
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
                <p class="c1">二维码管理设置</p>
            </div>
        </div>
        
        
        <div class="platform_page_content">
         <form method="post" id="planImpl" enctype="multipart/form-data">
                <div>
                    <table class="platform_page_content_table" >
                           <div style="float:left;">
                                <select  id="test" name="status" value="${status}"  >
                                <c:if test="${empty status}">
                                	<option value=""  id="" >--请选择--</option>
                               		<option value="1" id="1" >未导出</option>
                               		<option value="2" id="2" >已导出</option>
                               </c:if>
                                <c:if test="${status==1}">
                               		<option value=""  id="" >--请选择--</option>
                               		<option value="1" selected="selected">未导出</option>
                               		<option value="2" id="2" >已导出</option>
                               </c:if>
                                <c:if test="${status==2}">
                               		<option value=""  id="" >--请选择--</option>
                               		<option value="1" id="1" >未导出</option>
                               		<option value="2" selected="selected">已导出</option>
                               </c:if>
                            
                            </select>
                            <input type="button" name="findSearch"  id="findSearch"  onclick="findByStatus()" value="搜索"/>
                            </div>	
                            
                           <div style="float:left;margin-left:350px;">生成商家结算二维码数量:<input type="text" name="num" id="num"/>
                           <input type="button" value="生成" onclick="create()" /></div>
         
                     	
         			<tr class="platform_page_content_table_tr">
                         
                            <th width="5%"><input type="checkbox" id="checkMore"  name="a"/>全选</th>
                            <th width="24%">二维码图片</th>
                             <th width="28%">二维码ID</th>
                            <th width="20%">状态</th>
                            <th width="23%">创建时间</th>
                    </tr>
                 <tbody>
                       <c:forEach items="${page.result}" var="l" varStatus="status"> 
                        <tr>
                             <input type="hidden" id="sss${status.index}" name="sss${status.index}" value="${l.id }" />
                              <td>
                              <input type="checkbox" name="topProB2B" value="${l.rcodeid }"/>
                              </td>
                              
                            <td>
                                <input type="text" id="sbx${status.index}" name="sbx${status.index}" value=""  disabled="disabled"/>
                            </td>
                             <td>
                                <input type="text" id="sbx${status.index}" name="sbx${status.index}" value="${l.rcodeid }"  disabled="disabled"/>
                            </td>
                            <td>
                               <c:if test="${l.status==0 }">
                            		<input type="text" id="${status.index}" name="sfx${status.index}" value="不可用" readOnly="true"/>
                            	</c:if>
                            	<c:if test="${l.status==1 }">
                            		<input type="text" id="${status.index}" name="sfx${status.index}" value="可用" readOnly="true"/>
                            	</c:if>
                            	<c:if test="${l.status==2 }">
                            		<input type="text" id="${status.index}" name="sfx${status.index}" value="已导出" readOnly="true"/>
                            	</c:if>
                            </td>
                            
                             <td>
                                <input type="text" id="sbx${status.index}" name="sbx${status.index}" value="<fmt:formatDate   value='${l.createDatetime}' type='both' pattern='yyyy-MM-dd hh:mm'/>"  disabled="disabled"/>
                            </td>
                        </tr>
                       </c:forEach> 
                        
                    </table>
                 </tbody>
                
                </div>
              
                <input type="button" value="批量导出" name="excelOut" id="excelOut" onclick="outExcel()" />
                <input type="button" value="导出所有" name="excelOutAll" id="excelOutAll" onclick="outExcelAll()" />
                <c:if test="${!empty page.result}">
                    <%@include file="/WEB-INF/views/include/page3.jsp" %>
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
