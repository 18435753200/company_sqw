<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
	
	<table class="pu">
				<tbody>
				<!-- 
					<div class="btn">
						<a class="ne" href="javascript:void(0);">新增</a>
						<a href="#">修改</a>
						<a href="#">保存</a>
						<a href="#">放弃</a>
					</div>
					<td>
								<a href="#" class="up" onclick="editSale('${sa.saleId}')">修改</a>
								<a href="#" onclick="delSale('${sa.saleId}')" >放弃</a>
							</td>
				 -->
					<div class="pu_wrap">
						<table class="pu">
							<tr class="order-hd">
								<th width="40px">序</th>
								<th width="250px">渠道名称</th>
								<th width="200px">渠道减价率(%)</th>
								<th width="300px">说明</th>
								<th width="150px">操作</th>
							</tr>
							<c:forEach items="${allList }" var="sa">
								
								<tr class="order-bd">
									<td>${sa.channelId }</td>
									<td>${sa.channelName }</td>
									<td>${sa.channelSubtractRate }</td>
									<td>${sa.description }</td>
									<td>
										<a href="javascript:void(0)" class="up" onclick="editSale('${sa.channelId}')">修改</a>
										<a href="javascript:void(0)" onclick="delSale('${sa.channelId}')" >放弃</a>
									</td>
								</tr>
							</c:forEach>
							
						</table>
				    </div>
		       </tbody>
	</table>
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
<script type="text/javascript">
	$(function(){
		$(".b_colse,.bt2").click(function(){
			$(".alert_up").hide();
		});
		
	});
	
	
	function  editSale(id){
		$.ajax( {
			url : "${path}/saleWay/toEdit?id="+id,
			type : 'POST',
			dataType:"json",
			timeout : 30000,		
			success: function (data) {
				
					
				
				$(".alert_up").show();
				$("#id").val(data.channelId);
				$("#editName").val(data.channelName);
				$("#editRate").val(data.channelSubtractRate);
				$("#editDestion").val(data.description);
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
				url : "${path}/saleWay/del?id="+id,
				type : 'POST',
				timeout : 30000,		
				success: function (data) {
					
					if(data==0){ 
						tipMessage("该渠道已绑定商户,操作失败!",function(){
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
