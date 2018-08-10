<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table class="pu">
				<tbody>
					<tr class="order-hd">
						<th width="150px">销售编号</th>
						<th width="240px">销售名称</th>
						<th width="150px">联系电话</th>
						<th width="150px">操作</th>
					</tr>	
					
					<c:forEach items="${allSale}" var="sa">
				        <tr class="order-bd">
							<td>${sa.saleId}</td>
							<td>${sa.saleName}</td>
							<td>${sa.salePhone}</td>
							<td>
								<a href="#" class="up" onclick="editSale('${sa.saleId}')">修改</a>
								<a href="#" onclick="delSale('${sa.saleId}')" >放弃</a>
							</td>
						</tr>
					</c:forEach>
					
		       </tbody>
		   </table>
		 <%--   	<%@include file="/WEB-INF/views/include/page.jsp"%> --%>
		   	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
<script type="text/javascript">
	$(function(){
		$(".b_colse,.bt2").click(function(){
			$(".alert_up").hide();
		});
	});
	
	function  editSale(id){
		$.ajax( {
			url : "${path}/saleManager/editSale?id="+id,
			type : 'POST',
			dataType:"json",
			timeout : 30000,		
			success: function (data) {
				
					
				
				$(".alert_up").show();
				$("#id").val(data.saleId);
				$("#editName").val(data.saleName);
				$("#editPhone").val(data.salePhone);
				/* if(data.status==0){ 
					tipMessage("操作失败!",function(){
						location.reload();
					}); 
				}else{ 
					tipMessage("操作成功!",function(){
						location.reload();
					});
				}   */
			}, 
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
				alert("服务器忙，请稍后再试！"); 
			} 
		}, function(){
		   // $.dialog.tips('执行取消操作');
		});
	}
	function delSale(id){
		$.dialog.confirm('确定放弃此销售?', function(){
			$.ajax( {
				url : "${path}/saleManager/delSale?id="+id,
				type : 'POST',
				timeout : 30000,		
				success: function (data) {
					
					
					if(data==0){ 
						tipMessage("该销售人员已绑定,操作失败!",function(){
							location.reload();
						}); 
					}else{ 
						tipMessage("操作成功!",function(){
							location.reload();
						});
					}  
				}, 
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
					alert("服务器忙，请稍后再试！"); 
				} 
			}, function(){
			   // $.dialog.tips('执行取消操作');
			});
    	});
	}
	
	
	
</script>