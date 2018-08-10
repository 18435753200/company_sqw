<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<div class="blank10"></div>
<div class="shang">
	<c:choose>
		<c:when test="${page.page==1 }">
			<button class="y" type="button">
				<img src="../commons/images/x6.jpg"> 上一页
			</button>
		</c:when>

		<c:otherwise>
			<a href='javascript:void(0)' onclick="clickSubmit(${page.page-1})">
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
						<input type="button" id="pageContext"
							style="color: #ff6600; font-weight: bold; border: none;"
							value="${i.index }" />
					</c:when>
					<c:when test="${page.page !=  i.index}">
						<a href='javascript:void(0)' onclick="clickSubmit(${i.index})">
							<input type='button' value="${i.index }" />
						</a>
					</c:when>
				</c:choose>
			</c:forEach>


			<c:if test="${(page.totalPage-page.page)>2&&page.totalPage>6 }">
							......<a href='#'><input type='button'
					value="${page.totalPage }" onclick="clickSubmit(${page.totalPage})" /></a>
			</c:if>
			<c:if test="${(page.totalPage-page.page)>2&&page.totalPage==6 }">
				<a href='#'><input type='button' value="${page.totalPage }"
					onclick="clickSubmit(${page.totalPage})" /></a>
			</c:if>
		</c:when>



		<c:when test="${page.page > 2 }">
			<c:if test="${page.page>4&&page.totalPage>6}">
				<a href='#'><input type='button' value="1"
					onclick="clickSubmit('1')" /></a>......
							</c:if>
			<c:if test="${page.page==4&&page.totalPage>6}">
				<a href='#'><input type='button' value="1"
					onclick="clickSubmit('1')" /></a>
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
						<input type="button" id="pageContext"
							style="color: #ff6600; font-weight: bold; border: none;"
							value="${i.index }" />
					</c:when>
					<c:when test="${page.page !=  i.index}">
						<a href='#'><input type='button' value="${i.index }"
							onclick="clickSubmit(${i.index})" /></a>
					</c:when>
				</c:choose>
			</c:forEach>
			<c:if test="${(page.totalPage-page.page)==3 }">
				<a href='#'><input type='button' value="${page.totalPage }"
					onclick="clickSubmit(${page.totalPage})" /></a>
			</c:if>
			<c:if test="${(page.totalPage-page.page)>3 }">
								......<a href='#'><input type='button'
					value="${page.totalPage }" onclick="clickSubmit(${page.totalPage})" /></a>
			</c:if>
		</c:when>
	</c:choose>

	<c:choose>
		<c:when test="${page.page<page.totalPage}">
			<a href='javascript:void(0)' onclick="clickSubmit(${page.page+1})">
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
		onclick="javascript: var pagenum=document.getElementById('go').value; clickSubmit(pagenum)">GO</button>
</div>
<div class="blank10"></div>
