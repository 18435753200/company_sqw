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
    	console.log("${page.result}")
    	
    });
   
	 function comPass(id){
		
		 var sbx=$("#sbx"+id).val(); //申请的
		 var sdx=$("#sdx"+id).val(); //审批的 
		 var sss=$("#sss"+id).val();   //id
		 var ssa=$("#ssa"+id).val();   //supplierId
		 if(sdx<=0){
			 alert("对不起,你输入的审批额度无效,请重新输入");
			 return;
		 }
		
		
		 $.dialog.confirm('您确定审批通过吗?', function(){
	     
		 $.ajax({
			 url:'../infrastructure/checkPass?validVal='+sdx+'&id='+sss+'&supplierId='+ssa,
			 success:function(row){
				  if(row=1){
					 alert("审批成功");
					 window.location.reload();
				 }else{
					 alert("操作失败,请检查后重试");
				 } 
			 }
		 });
		 }); 
		 } 
	 
	 
	 
	 
	 
	 function comNotPass(id){
		
		
		 var sss=$("#sss"+id).val(); //id
		 var sgx=$("#sgx"+id).val(); //审核不通过原因
		
		
		 $.dialog.confirm('您确定审批不通过吗?', function(){
			 var reason=window.prompt("请输入失败原因,最多输入10个字");
			 sgx=reason;
			 if(!/^[\u4e00-\u9fa5]{2,4}$/.test(sgx)){
				 alert("请输入汉字,最多输入10个字");
				 return false;
			 }
			 $.ajax({
			    
				url:'../infrastructure/checkNoPass?memo='+sgx+'&id='+sss,
				success:function(row){
					if(row=1){
						alert("操作成功");
						window.location.reload();
					}else{
						alert("操作失败,请稍后重试");
					}
				}
				
			 });
			 
			 
		 });
	 }
	 
	 
    function toPage(page){
        window.location.href="${path}/infrastructure/supplierRegionSettingsxfg?page="+page;
    }
    
   

    function clickSubmit(page){
    	window.location.href="${path}/infrastructure/approve?page="+page;
    }
   

   function comNotPasssb(id){
      alert($('#sgx'+id).val());
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
                <p class="c1">审批优惠券设置</p>
            </div>
        </div>
        <div class="platform_page_content">
         <form method="post" id="planImpl" enctype="multipart/form-data">
                <div>
                    <table class="platform_page_content_table" >
         			<tr class="platform_page_content_table_tr">
                          <!--   <th width=16%">商家id</th> -->
                            <th width="14%">商家名称</th>
                            <th width="14%">申请数量</th>
                            <th width="14%">申请时间</th>
                            <th width="14%">确定数量</th>
                            <th width="14%">确定时间</th>
                            <th width="14%">申请状态</th>	
                            <th width="16%">操作</th>	
                        </tr>
                        
                 <tbody>
                       <c:forEach items="${page.result}" var="l" varStatus="status"> 
                        <tr>
                             <input type="hidden" id="sss${status.index}" name="sss${status.index}" value="${l.id }" />
                             <input type="hidden" id="ssa${status.index}" name="ssa${status.index}" value="${l.supplierId }" />
                            <td>
                                <input type="text" id="sax${status.index}" name="sax${status.index}" value="${l.supplierName }" disabled="disabled"/>
                            </td>
                            <td>
                                <input type="text" id="sbx${status.index}" name="sbx${status.index}" value="${l.applyVal }"  disabled="disabled"/>
                            </td>
                            <td>
                         <input type="text" id="sex${status.index}" name="sex${status.index}" value="<fmt:formatDate   value='${l.applyDatetime}' type='both' pattern='yyyy-MM-dd hh:mm'/>"  disabled="disabled"/>
                            </td>
                            <td>
                                <input type="text" id="sdx${status.index}" name="sdx${status.index}" value="${l.validVal }"  />
                            </td>
                            <td>
                              <input type="text" id="sex${status.index}" name="sex${status.index}" value="<fmt:formatDate   value='${l.validDatetime}' type='both' pattern='yyyy-MM-dd hh:mm'/>"  disabled="disabled"/>
                            </td>
                             
                          <td>
                            	<c:if test="${l.status==0 }">
                            		<input type="text" id="${status.index}" name="sfx${status.index}" value="无效" readOnly="true"/>
                            	</c:if>
                            	<c:if test="${l.status==1 }">
                            		<input type="text" id="${status.index}" name="sfx${status.index}" value="申请中"  readOnly="true"/>
                            	</c:if>
                            	<c:if test="${l.status==2 }">
                            		<input type="text" id="${status.index}" name="sfx${status.index}" value="审核通过 "  readOnly="true"/>
                            	</c:if>
                            	<c:if test="${l.status==3 }">
                            		<input type="text" readOnly="true"  id="${status.index}" name="sfx${status.index}" value="审核不通过"  />
                            	</c:if>
                            </td> 
                         
                         
                             <td> 
                             
                                    <input type="button" id="${status.index}" class="tjiao1"  onclick="return comPass(this.id)" value="审核通过"/>
                                  
                                     <input type="button"  id="${status.index}" class="tjiao2"  onclick="return comNotPass(this.id)" value="审核不通过"/>
                                     <c:if test="${l.memo!=null}">
                                         <input type="text" id="sgx${status.index}" name="sgx${status.index}" value="${l.memo }"  readOnly="true"/>
                                     </c:if>
                             </td> 	
                        </tr>
                       </c:forEach> 
                        </tbody>
                    </table>
                </div>
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
