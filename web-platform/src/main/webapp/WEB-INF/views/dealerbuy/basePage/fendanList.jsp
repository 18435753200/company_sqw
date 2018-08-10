<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!--弹框开始-->
	<div class="alert_bj" id="dealer" style="display: none;">
		<div class="bg"></div>
		<div class="wrap">
			<div class="hdui1">
				<p class="pic" onclick="close1()"><img src="../commons/images/close.jpg" class="b_colse"></p>
				<h2></h2>
				<table class="table">
					<tr>
						<th class="t1">经销商编号</th>
						<th class="t1">经销商名称</th>
						<th class="t1">销售规模</th>
						<th class="t1">公司地址</th>
						<th class="t2">是否独代</th>
						<th class="t1">代理区域</th>
						<th class="t2">选择</th>
					</tr>
					<c:forEach items="${dealer}" var="list">
					<tr>
					<td><p class="ov1" title="${list.dealerId}">${list.dealerId}</p></td>
					<td title="${list.dealerName}"><p class="ov1">${list.dealerName}</p></td>
					<%-- 	<td>${list.businessScale}</td> --%>
						<td>
							<p class="ov1" title="<c:if test="${list.isexclusiveAgent==0 }">经营服装</c:if>
								<c:if test="${list.isexclusiveAgent==1 }">主营食品</c:if>">
								<c:if test="${list.isexclusiveAgent==0 }">经营服装</c:if>
								<c:if test="${list.isexclusiveAgent==1 }">主营食品</c:if>
							</p>
						</td>
						<td title="${list.address }"><p class="ov1">${list.address }</p></td>
						<!-- IsnationalAgency -->
						
						<td>
							<c:if test="${list.isexclusiveAgent ==1}">是</c:if>
						    <c:if test="${list.isexclusiveAgent ==0}">否</c:if>
						</td>
						<td><p class="ov1" title="${list.areaAll}">${list.areaAll}</p></td>
						<td><input type="radio" name="dealerId" value="${list.dealerId}" /></td>
					</tr>
					</c:forEach>
				</table>
			</div>
<%-- 			
		<div class="shang" style="margin:5px 20px 0 0;">
		<c:if test="${pageBean1.totalPage >= 1}">
				<c:choose>
					<c:when test="${pageBean1.page==1 }">
						<button class="y" type="button"><img src="../commons/images/x6.png"> 上一页</button>
					</c:when>
					
					<c:otherwise>
						<a href='javascript:void(0)' onclick="toPage(${pageBean1.page-1})">
						<button type="button" class="y1"><img src="../commons/images/x71.png"> 上一页 
						</button>
						</a>
					</c:otherwise>
				</c:choose>
				<c:choose>	
								
					<c:when test="${pageBean1.page < 4}">
						
					
						<c:choose>
							<c:when test="${pageBean1.totalPage>6}">
								<c:set var="stopPage" value="7"/>
							</c:when> 
							<c:when test="${pageBean1.totalPage<8}">
								<c:set var="stopPage" value="${pageBean1.totalPage }"/>
							</c:when> 
						</c:choose>
						<c:set var="startPage" value="1"/>
							<c:forEach begin="${startPage }" end="${stopPage }" varStatus="i" >
								<c:choose>
								<c:when test="${pageBean1.page == i.index}">
										<input type="button" style="color:#ff6600; font-weight:bold; border:none;" value="${i.index }"/>		
								</c:when>
								<c:when test="${pageBean1.page !=  i.index}">
										<a href='javascript:void(0)' onclick="toPage(${i.index})"><input type='button'  value="${i.index }"/></a>
								</c:when>
								</c:choose>
							</c:forEach>
							
							
						<c:if test="${(pageBean1.totalPage-pageBean1.page)>4&&pageBean1.totalPage>8 }">
							......<a href='#'><input type='button'  value="${pageBean1.totalPage }" onclick="toPage(${pageBean1.totalPage})"/></a>
						</c:if>
					</c:when>
					
					
					
					<c:when test="${pageBean1.page > 3 }" >
							
							<c:if test="${pageBean1.page > 4 }">
								<a href='#'><input type='button'  value="1" onclick="toPage(1)"/></a>......
							</c:if>
							<c:if test="${pageBean1.page>5&&pageBean1.totalPage>8 }">
									<a href='#'><input type='button'  value="1" onclick="toPage('1')"/></a>......
							</c:if>
							
							<c:choose>
								<c:when test="${pageBean1.totalPage<8 }">
									<c:set var="startPage" value="1"/>
								</c:when>
								<c:when test="${pageBean1.totalPage-pageBean1.page<3 }">
									<c:set var="startPage" value="${pageBean1.page-(7-(pageBean1.totalPage-pageBean1.page))+1 }"/>
								</c:when>
								<c:otherwise>
									<c:set var="startPage" value="${pageBean1.page-3 }"/>
								</c:otherwise>
							</c:choose>
							
							
							<c:choose>
								<c:when test="${pageBean1.totalPage-pageBean1.page<3 }">
									<c:set var="stopPage" value="${pageBean1.totalPage }"/>
								</c:when>
								<c:otherwise>
									<c:set var="stopPage" value="${pageBean1.page+3 }"/>
								</c:otherwise>
							</c:choose>
							<c:forEach begin="${startPage }" end="${stopPage }" varStatus="i" >
									<c:choose>
									<c:when test="${pageBean1.page eq i.index}">
											<input type="button" style="color:#ff6600; font-weight:bold; border:none;" value="${i.index }"/>		
									</c:when>
									<c:when test="${pageBean1.page !=  i.index}">
											<a href='#'><input type='button'  value="${i.index }" onclick="toPage(${i.index})"/></a>
									</c:when>
									</c:choose>
							</c:forEach>
							<c:if test="${(pageBean1.totalPage-pageBean1.page)>4 }">
							......<a href='#'><input type='button'  value="${pageBean1.totalPage }" onclick="toPage(${pageBean1.totalPage})"/></a>
						</c:if>
					</c:when>
				</c:choose>	
				
				<c:choose>
					<c:when test="${pageBean1.page<pageBean1.totalPage}">
						<a href='javascript:void(0)' onclick="toPage(${pageBean1.page+1})">
							<button class="y1" type="button"> 下一页 
								 <img src="../commons/images/x7.png">
							</button>
						</a>
					</c:when>
					<c:otherwise>
						<button class="y" type="button"> 下一页  <img src="../commons/images/x61.png"></button>
					</c:otherwise>
				</c:choose>	
					</c:if>
			</div>
		
			<div class="blank10"></div> --%>
			
			
			
			<div class="x1">
			<c:if test="${!empty buttonsMap['已合并订单-保存分单'] }">
				<button class="bt1" onclick="fendan1('${orderId}')">确定</button>
			</c:if>
				<button class="bt2" onclick="close2()">取消</button>
			</div>

		</div>
	</div>
<!--弹框结束-->
