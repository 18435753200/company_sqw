<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/zh/include/base2.jsp"%>

		<script type="text/javascript">
			$(document).ready(function(){
					$("#all-b2b-selector").click(function() {
					if ($(this).attr("checked") == "checked") { // 全选 
						$("input[type='checkbox'][name='topProB2B']").each(function() {
							$(this).attr("checked", true);
						});
					} else { // 反选 
						$("input[type='checkbox'][name='topProB2B']").each(function() {
							$(this).attr("checked", false);
						});
					}
				});
			});
			
		</script>
		
		<script type="text/javascript">
		
		//导出按钮绑定的事件
		function dofileExport(){
			//发送请求（文件下载）
			window.location.href="../supplier/fileExport";
		}
		</script>
		
		
		 
		<table id="J_BoughtTable" class="bought-table" data-spm="9" style="width:100%;">
		<!-- 	方便条件查询时可以获取当前页面的父级id -->
	<c:if test="${!empty parentId }">
		<input type="hidden" value="${parentId }" name="parentId" id="parentId"></input>
		<input type="hidden" value="${ps }" name="status" id="status"></input>
		<input class="btn-primary " data-toggle="modal" data-target="#myModal1" type="hidden" id="showboot" onclick="getAgentSameTo($('#parentId').val())" />
<!-- 		批量修改上级运营商使用的,可在agent.js中找到用法 -->
		<input type="hidden" id="checkboxValue" />
	</c:if>
	
	
	<c:forEach items="${agentMenu }" var="am"  varStatus="ama">
				<span style="cursor:pointer;<c:if test="${ama.last }"> color: purple;font-weight: bold;</c:if>"  
					<c:if test="${am.key!='线下商家' }">onclick="returnPage('${am.value }')"</c:if>>
					&nbsp;&nbsp;
					${am.key }
					<input type="hidden" value="${am.value }" id="aa">
				<c:if test="${!ama.last }">
					&gt;
				</c:if>
				</span>
	</c:forEach>
	
	
			<thead>
			<c:if test="${ps==5 && (pass.totalCount<=20 || sa.code == 1601 )}">
				<tr class="sep-row">
					<td colspan="6" style="padding: 5px 0 5px 0;">
						<div class="operations">
							<c:if test="${! empty pass.result}">
								<input class="all-selector gg" id="all-b2b-selector"
									type="checkbox">
								<label>商家全选/反选</label>
								<a class="tm-btn" href="javascript:void(0);"
									onclick="updateParentIdMore()" role="button">批量修改</a>
							</c:if>
						</div>
					</td>
				</tr>
			</c:if>
			
				<tr class="col-name">
					
					<th>公司名称</th>
					<th>编号</th>
					<th>联系人</th>
					<th>电话</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			
			
			<tbody class="data">
			
			
			<!--        没有数据显示的页面 -->
			<c:if test="${empty pass.result}">
				<tr>
					<td colspan="6">
						<center>
							<img src="${path }/images/no.png" />
						</center>
					</td>
				</tr>
			</c:if>
			
			<!--        有数据显示的页面 -->
		<c:if test="${!empty pass.result}">
					<c:forEach items="${pass.result}" var="pbr">
					<tr style="height: 10px;"></tr>
					
					<c:if test="${ps==5 && (pass.totalCount<=20 || sa.code == 1601 ) }">
						<tr class="order-hd">
							<td colspan="8">
								<p>
									<input class="selector" type="checkbox" name="topProB2B"
										value="${pbr.supplierId}">
									<button class="btn-primary " data-toggle="modal"
										data-target="#myModal"
										onclick="getAgentSame($('#parentId').val(),$('#status').val());">
										修改上级</button>
								</p>
							</td>
						</tr>
					</c:if>
						
						<tr class="headShow">
							<th class="busShow" >${pbr.name }</th>
							<th class="busShow" >${pbr.supplierCode }</th>
							<th class="busShow" >${pbr.contact }</th>
							<th class="busShow" >${pbr.phone }</th>
							<th class="busShow" >
									<c:if test="${pbr.activeStatus==-1 && pbr.status==0}">
										未审核
									</c:if>
									<c:if test="${pbr.activeStatus==1 && pbr.status==1}">
										审核通过
									</c:if>
									<c:if test="${pbr.activeStatus==-1 && pbr.status==2}">
										审核失败
									</c:if>
							</th>
							
							
							<th class="busShow"  >
									<p>
										<c:if test="${pbr.status!=2}">
											<a class="tm-btn" onclick="window.location.href='${pageContext.request.contextPath}/supplier/lookBusiness?supplierId=${pbr.supplierId }'"  >查看</a>
										</c:if>
										<c:if test="${pbr.activeStatus==-1 && pbr.status==2}">
											<a class="tm-btn" onclick="window.location.href='${pageContext.request.contextPath}/supplier/toUpdateBusiness?supplierId=${pbr.supplierId }'"  >修改</a>
										</c:if>
									</p>
									<%-- <c:if test="${pbr.activeStatus==1}">
									<p><a class="tm-btn" onclick="window.location.href='${pageContext.request.contextPath}/supplier/rechargeCoupon?supplierId=${pbr.supplierId }'"  style="width:100%">充值</a></p>
									</c:if> --%>
									<c:if test="${pbr.activeStatus==1 && pbr.status==1}">
										<p>
											<a class="tm-btn" onclick="tradingFlow('${pbr.supplierId}')" >收入流水</a>
										</p>
										<p>
											<a class="tm-btn" onclick="lookCode('${pbr.supplierId}')" >二维码</a>
										</p>
										<p>
											<a class="tm-btn" onclick="storeDetail('${pbr.supplierId}')" >店铺详情</a>
										</p>
									</c:if>
							</th> 
						</tr> 
						
							<!-- 单个上级运营商的修改 -->
				<!-- 模态框（Modal） -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">请选择要替换的上级运营商</h4>
							</div>
							<div class="modal-body">
								<select id="usp" name="usp">
								</select>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
								<button type="button" class="btn btn-primary"
									onclick="updateParentId('${pbr.supplierId}')">提交更改</button>
							</div>
						</div>
					</div>
				</div>
					<!-- 单个上级运营商的修改 完成-->		
			</c:forEach>	
		</c:if>
	</tbody>
	
	<!-- 多个上级运营商的修改 -->
				<!-- 模态框（Modal） -->
				<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">请选择要替换的上级运营商</h4>
							</div>
							<div class="modal-body">
								<select id="usp1" name="usp1">
								</select>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
								<button type="button" class="btn btn-primary"
									onclick="updateMoreParentId()">批量修改</button>
							</div>
						</div>
					</div>
				</div>
<!-- 多个上级运营商的修改 完成-->
		
			
</table>

		<c:if test="${!empty pass.result}">
			<%@include file="/WEB-INF/views/zh/agent/include/sellerPage.jsp" %>
		</c:if>
