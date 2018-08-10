<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="${path}/commons/css/reset.css">
<style type="text/css">
.shang .curr {
	color: #ff6600;
	font-weight: bold;
	border: none;
}
</style>

<c:if test="${page.totalPage>1}">
	<div class="blank10"></div>
	<div class="shang" style="margin: 5px 20px 0 0;">
		<c:choose>
			<c:when test="${page.page==1 }">
				<button class="y" type="button">
					<img src="../commons/images/x6.jpg"> 上一页
				</button>
			</c:when>

			<c:otherwise>
				<a href='javascript:void(0)' onclick="GoPage(${page.page-1})">
					<button type="button" class="y1">
						<img src="../commons/images/x71.png">上一页
					</button>
				</a>
			</c:otherwise>
		</c:choose>
		<c:choose>

			<c:when test="${page.page < 3}">


				<c:choose>
					<c:when test="${page.totalPage>4}">
						<c:set var="stopPage" value="5" />
					</c:when>
					<c:when test="${page.totalPage<5}">
						<c:set var="stopPage" value="${page.totalPage }" />
					</c:when>
				</c:choose>
				<c:set var="startPage" value="1" />
				<c:forEach begin="${startPage }" end="${stopPage }" varStatus="i">
					<c:choose>
						<c:when test="${page.page == i.index}">
							<input type="button"
								style="color: #ff6600; font-weight: bold; border: none;"
								value="${i.index }" />
						</c:when>
						<c:when test="${page.page !=  i.index}">
							<a href='javascript:void(0)' onclick="GoPage(${i.index})"> <input
								type='button' value="${i.index }" />
							</a>
						</c:when>
					</c:choose>
				</c:forEach>


				<c:if test="${(page.totalPage-page.page)>2&&page.totalPage>6 }">
							......<a href='#'><input type='button'
						value="${page.totalPage }" onclick="GoPage(${page.totalPage})" /></a>
				</c:if>
				<c:if test="${(page.totalPage-page.page)>2&&page.totalPage==6 }">
					<a href='#'><input type='button' value="${page.totalPage }"
						onclick="GoPage(${page.totalPage})" /></a>
				</c:if>
			</c:when>


			<c:when test="${page.page > 2 }">
				<c:if test="${page.page>4&&page.totalPage>6}">
					<a href='#'><input type='button' value="1"
						onclick="GoPage('1')" /></a>......
							</c:if>
				<c:if test="${page.page==4&&page.totalPage>6}">
					<a href='#'><input type='button' value="1"
						onclick="GoPage('1')" /></a>
				</c:if>
				<c:choose>
					<c:when test="${page.totalPage<7 }">
						<c:set var="startPage" value="1" />
					</c:when>
					<c:when test="${page.totalPage-page.page<2 }">
						<c:set var="startPage"
							value="${page.page-(5-(page.totalPage-page.page))+1 }" />
					</c:when>
					<c:otherwise>
						<c:set var="startPage" value="${page.page-2 }" />
					</c:otherwise>
				</c:choose>


				<c:choose>
					<c:when test="${page.totalPage-page.page<2 }">
						<c:set var="stopPage" value="${page.totalPage }" />
					</c:when>
					<c:otherwise>
						<c:set var="stopPage" value="${page.page+2 }" />
					</c:otherwise>
				</c:choose>
				<c:forEach begin="${startPage }" end="${stopPage }" varStatus="i">
					<c:choose>
						<c:when test="${page.page eq i.index}">
							<input type="button"
								style="color: #ff6600; font-weight: bold; border: none;"
								value="${i.index }" />
						</c:when>
						<c:when test="${page.page !=  i.index}">
							<a href='#'><input type='button' value="${i.index }"
								onclick="GoPage(${i.index})" /></a>
						</c:when>
					</c:choose>
				</c:forEach>
				<c:if test="${(page.totalPage-page.page)==3 }">
					<a href='#'><input type='button' value="${page.totalPage }"
						onclick="GoPage(${page.totalPage})" /></a>
				</c:if>
				<c:if test="${(page.totalPage-page.page)>3 }">
								......<a href='#'><input type='button'
						value="${page.totalPage }" onclick="GoPage(${page.totalPage})" /></a>
				</c:if>
			</c:when>
		</c:choose>

		<c:choose>
			<c:when test="${page.page<page.totalPage}">
				<a href='javascript:void(0)' onclick="GoPage(${page.page+1})">
					<button class="y1" type="button">
						下一页 <img src="../commons/images/x7.png">
					</button>
				</a>
			</c:when>
			<c:otherwise>
				<button class="y" type="button">
					下一页<img src="../commons/images/x61.png">
				</button>
			</c:otherwise>
		</c:choose>
		<input type="text" class="go" id="go" placeholder="Page Num">
		<button type="button" class="go_button"
			onclick="javascript: var pagenum=document.getElementById('go').value; if(pagenum==''){pagenum=1} GoPage(pagenum)">GO</button>
	</div>
	<div class="blank10"></div>
</c:if>

<!-- <script type="text/javascript">
	var jQFrm  = null;
	var timestamp = new Date().getTime()+"";
	
	$(document).ready(function(){
		jQFrm = $("#SearchFrom"); 
		if(jQFrm.length!=1){
			alert("获取分页信息出错!");
			return; }
		jQFrm.append("<input id='currpage_"+timestamp+"' type='hidden' name='page' value='1'/>");
		jQFrm.append("<input id='pernum_"+timestamp+"' type='hidden'   name='pageSize' value='${page.pageSize}'/>");
	});
	function GoPage(page){
		$("#currpage_"+timestamp).val(page);
		//alert($("#currpage_"+timestamp).val()+"sssss"+$("#pernum_"+timestamp).val());
		jQFrm.submit();
		}
</script> -->