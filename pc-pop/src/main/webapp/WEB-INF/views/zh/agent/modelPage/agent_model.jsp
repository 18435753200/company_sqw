<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/zh/include/base2.jsp"%>

<script type="text/javascript">
	$(document).ready(function() {

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


	
	function returnPage(parentId){
		var page=1;
    	
    	$.ajax({
    		async:false,
    		type : "post", 
    		url : "../supplier/getAgentList", 
    		data:{'page':page,'parentId':parentId},
    		dataType:"html",
    		success : function(msg) { 
    			$("#c3").html(msg);
    		},
    		error: function(jqXHR, textStatus, errorThrown){
    			alert("查询失败 ，请稍后再试。。");
    		}
    	}); 
	}
	
</script>



<table id="J_BoughtTable" class="bought-table" data-spm="9"
	style="width: 100%;">
	
	


	<!-- 	方便条件查询时可以获取当前页面的父级id -->
	<c:if test="${!empty parentId }">
		<input type="hidden" value="${parentId }" name="parentId" id="parentId"></input>
		<input type="hidden" value="${ps }" name="status" id="status"></input>
		<input class="btn-primary " data-toggle="modal" data-target="#myModal1" type="hidden" id="showboot" onclick="getAgentSameTo($('#parentId').val())" />
<!-- 		批量修改上级代理使用的,可在agent.js中找到用法 -->
		<input type="hidden" id="checkboxValue" />
	</c:if>


	
	
	
	<c:forEach items="${agentMenu }" var="am"  varStatus="ama">
				<span style="cursor:pointer;<c:if test="${ama.last }"> color: purple;font-weight: bold;</c:if>"  onclick="returnPage('${am.value }')">
					&nbsp;&nbsp;
					${am.key }
					<input type="hidden" value="${am.value }" id="aa">
				<c:if test="${!ama.last }">
					&gt;
				</c:if>
				</span>
	</c:forEach>
	
	<c:if test="${ad==1 }">
			<c:if test="${sat.code==1610 }">
				<input value="添加全国运营商" type="button" style="margin: 3px 5px; background: #ff8e0c; border: 0; color: #fff; width: 110px; line-height: 25px; border-radius: 5px; box-shadow: 2px 2px 2px #ececec; " onclick="addAgent()"/>&nbsp;&nbsp;&nbsp;
			</c:if>
			<c:if test="${sat.code==1620 }">
				<input value="添加省级运营商" type="button" style="margin: 3px 5px; background: #ff8e0c; border: 0; color: #fff; width: 110px; line-height: 25px; border-radius: 5px; box-shadow: 2px 2px 2px #ececec; " onclick="addAgent()"/>&nbsp;&nbsp;&nbsp;
			</c:if>
			<c:if test="${sat.code==1630 }">
				<input value="添加地市级运营商" type="button" style="margin: 3px 5px; background: #ff8e0c; border: 0; color: #fff; width: 110px; line-height: 25px; border-radius: 5px; box-shadow: 2px 2px 2px #ececec; " onclick="addAgent()"/>&nbsp;&nbsp;&nbsp;
			</c:if>
			<c:if test="${sat.code==1640 }">
				<input value="添加区县级运营商" type="button" style="margin: 3px 5px; background: #ff8e0c; border: 0; color: #fff; width: 110px; line-height: 25px; border-radius: 5px; box-shadow: 2px 2px 2px #ececec; " onclick="addAgent()"/>&nbsp;&nbsp;&nbsp;
			</c:if>
			<c:if test="${sat.code==1650 }">
				<input value="添加合伙人" type="button" style="margin: 3px 5px; background: #ff8e0c; border: 0; color: #fff; width: 110px; line-height: 25px; border-radius: 5px; box-shadow: 2px 2px 2px #ececec; " onclick="addMpAgent()"/>&nbsp;&nbsp;&nbsp;
			</c:if>
	</c:if>



	<thead>
		
	<c:if test="${ps==5 }">
		<tr class="sep-row">
			<td colspan="6" style="padding: 5px 0 5px 0;">
				<div class="operations">
					<c:if test="${! empty pass.result}">
						<input class="all-selector gg" id="all-b2b-selector"
							type="checkbox">
						<label>运营商全选/反选</label>
						<a class="tm-btn" href="javascript:void(0);"
							onclick="updateParentIdMore()" role="button">批量修改</a>
					</c:if>
				</div>
			</td>
		</tr>
	</c:if>
	
	
		<tr class="col-name">
			<th>名称(<i style="color: #f10180; font-size: 13px;">${pass.totalCount }</i>)&nbsp;位
			</th>
			<th>业务联系人</th>
			<th>业务联系手机</th>
			<th>状态</th>
			<th>操作</th>
			<th>查看下级</th>
		</tr>
	</thead>


	<tbody class="data" id="showListTbody">

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
				<c:if test="${ps==5 }">
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
				<tr
					style="border: 1px solid #e1e8ee; text-align: center; height: 80px;"<c:if test="${pbr.status==5 }">bgcolor="#F5F5F5"</c:if>">
					<td class="busShow" colspan="1" >
						${pbr.name }</td>
					<td class="busShow" colspan="1">
						${pbr.contact }
					</td>
					<td class="busShow" colspan="1">
						<c:if test="${pbr.parentId eq supplierId || sa.code==1601}">
							${pbr.phone }
						</c:if>
						<c:if test="${pbr.parentId ne supplierId && sa.code!=1601}">
							${fn:substring(pbr.phone,0,3)}****${fn:substring(pbr.phone,7,11)}
						</c:if>
					</td>
					<td class="busShow" colspan="1">
						<c:if
							test="${pbr.activeStatus==-1 && pbr.status==0 }">
							未审核
						</c:if> 
						<c:if test="${pbr.activeStatus==1 && pbr.status==1 }">
							审核通过
						</c:if> 
						<c:if test="${pbr.activeStatus==1 && pbr.status==5 }">
							已经冻结
						</c:if>
						 <c:if test="${pbr.activeStatus==-1 && pbr.status==2 }">
							审核失败
						</c:if>
<%-- 						 <c:if test="${pbr.activeStatus==-1 && pbr.status==1 }"> --%>
<!-- 							创建账号 -->
<%-- 						</c:if> --%>
						 <c:if test="${pbr.activeStatus==-1 && pbr.status==1 }">
							未审核
						</c:if>
					</td>
					<td class="busShow" colspan="1"><c:if
							test="${pbr.activeStatus==1 && pbr.status==1 }">
							<p>
								<a class="tm-btn" href="javascript:void(0);"
									<c:if test="${sat.code!=1650 }">
									onclick="lookSupplier('${pbr.supplierId}')">查看 </a>
								</c:if>
								<c:if test="${sat.code==1650 }">
									onclick="lookMpSupplier('${pbr.supplierId}')">查看 </a>
								</c:if>
							</p>
							<%-- <p>
								<a class="tm-btn" " href="javascript:void(0);"
									onclick="lookCode('${pbr.supplierId}')">二维码 </a>
							</p> --%>
						<%-- 	<p>
								<a class="tm-btn" href="javascript:void(0);"
									 
									 onclick="rechargeCoupon('${pbr.supplierId}')">充值
								</a>
							</p> --%>
							<p>
								<a class="tm-btn" href="javascript:void(0);"
									onclick="tradingFlow('${pbr.supplierId}')">交易流水

								</a>
							</p>
						<c:if test="${sa.code==1601 || sa.code==1640}">
							<P>
								<a class="tm-btn" href="javascript:void(0);"
									onclick="disableAgent('${pbr.supplierId}')">冻结 </A>
							</P>
						</c:if>
						</c:if> <c:if test="${pbr.activeStatus==-1 && pbr.status==0 }">
							<p>
							<div class="tm-btn" style="color: grey"
								<c:if test="${sat.code!=1650 }">
								onclick="updateSupplier('${pbr.supplierId}')">修改</div>
								</c:if>
								<c:if test="${sat.code==1650 }">
								onclick="updateMpSupplier('${pbr.supplierId}')">修改</div>
								</c:if>
							</p>
						</c:if> <c:if test="${ pbr.activeStatus==-1 && pbr.status==2}">
							<div class="tm-btn" style="color: grey"
								<c:if test="${sat.code!=1650 }">
								onclick="updateSupplier('${pbr.supplierId}')">修改</div>
								</c:if>
								<c:if test="${sat.code==1650 }">
								onclick="updateMpSupplier('${pbr.supplierId}')">修改</div>
								</c:if>
							</p>
						</c:if> <c:if test="${ pbr.activeStatus==-1 && pbr.status==1}">
							<p>
								<a class="tm-btn" style="color: grey" href="javascript:void(0);"
								<c:if test="${sat.code!=1650 }">
									onclick="lookSupplier('${pbr.supplierId}')">查看 </a>
								</c:if>
								<c:if test="${sat.code==1650 }">
									onclick="lookMpSupplier('${pbr.supplierId}')">查看 </a>
								</c:if>
							</p>
						</c:if> <c:if test="${ pbr.activeStatus==1 && pbr.status==5}">
							<p>
								<a class="tm-btn" style="color: grey" href="javascript:void(0);"
								<c:if test="${sat.code!=1650 }">
									onclick="lookSupplier('${pbr.supplierId}')">查看 </a>
								</c:if>
								<c:if test="${sat.code==1650 }">
									onclick="lookMpSupplier('${pbr.supplierId}')">查看 </a>
								</c:if> 
							</p>
							<c:if test="${sa.code==1601  || sa.code==1640}">
								<p>
								<a class="tm-btn" style="color: grey" href="javascript:void(0);"
									onclick="unDisableAgent('${pbr.supplierId}')">解冻 </a>
							</p>
							</c:if>
						</c:if></td>

					<td><input type=button value="查看下级"
						<c:choose>
						    <c:when test="${pbr.activeStatus==1 && pbr.status==1 && pbr.type!=1650}">
						   	 onclick="findNext('${pbr.supplierId }')"
						    </c:when>
						    <c:when test="${pbr.activeStatus==1 && pbr.status==5 && pbr.type!=1650}">
						      onclick="findNext('${pbr.supplierId }')"
						    </c:when>
						    <c:when test="${pbr.activeStatus==1 && pbr.status==1 && pbr.type==1650}">
						      onclick="findNextBus('${pbr.supplierId }')"
						    </c:when>
						    <c:when test="${pbr.activeStatus==1 && pbr.status==5 && pbr.type==1650}">
						      onclick="findNextBus('${pbr.supplierId }')"
						    </c:when>
						    <c:otherwise>
						    onclick="alert('请等待审核')"
						    </c:otherwise>
						</c:choose>
						class="tm-btn" /></td>


				</tr>
				<tr style="height: 10px;"></tr>

					<!-- 单个上级代理的修改 -->
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
						<!-- /.modal-content -->
					</div>
					<!-- /.modal -->
				</div>
			<!-- 单个上级代理的修改 完成-->				
			</c:forEach>
		</c:if>

	
<!-- 多个上级代理的修改 -->
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
						<!-- /.modal-content -->
					</div>
					<!-- /.modal -->
				</div>
<!-- 多个上级代理的修改 完成-->
		
	

	</tbody>
</table>


<c:if test="${!empty pass.result}">
	<%@include file="/WEB-INF/views/zh/agent/include/page.jsp"%>
</c:if>

