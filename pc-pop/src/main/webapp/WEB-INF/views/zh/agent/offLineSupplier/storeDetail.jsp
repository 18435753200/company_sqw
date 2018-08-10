<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商家后台管理系统-店铺详情</title>
<%@include file="/WEB-INF/views/zh/include/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${path }/css/zh/agent/store.css"/>
<script type="text/javascript" src="${path }/js/agent/offLineSupplier/storeDetail.js"></script>
</head>
<body>
		<!-- 导航 start -->
	<%@include file="/WEB-INF/views/zh/include/header.jsp"%>
	<%@include file="/WEB-INF/views/zh/include/leftProduct.jsp" %>
	<!-- 导航 end -->
	
	
	<div class="c2">
			<div class="c21">
				<div class="title">
					<p>商家中心&nbsp;&gt;&nbsp;</p>
					<p>运营商管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">店铺详情</p>
				</div>
			</div>
			<div class="blank10"></div>
			<div class="pop_dpxq">
				<h3>店铺信息</h3>
					
<!-- 					隐藏域传值 -->
				<input type="hidden" id="path" value="${path }" >
				<input type="hidden" id="supplierId" value="${sd.supplierId }">
				<input type="hidden" id="storeId" value="${sd.id }">
				
<!-- 				门头照 -->
				<div class="pop_dpxq_mtz">
					<h4 class="mtz_title" >门头照:</h4>
					<img id="storeLogo" src="//image01.zhongjumall.com/d1/${sd.companyStoreLogo}" class="mtzimg" >
					</br>
					<input type="file" value="上传"  class="up_btn" onchange="updateStoreLogo(this)" name="companyStoreLogo" id="companyStoreLogo">
				</div>
				
<!-- 				门店头像 -->
				<div class="pop_dpxq_mdlogo">
					<b class="mtz_title" >门店头像:</b>
					<img src="${path}/images/zjm_2.png" class="mtzlogo">
<!-- 					<input type="button" value="上传" class="up_btn" style="right:0;margin-left:2rem"> -->
				</div>
				
<!-- 				门店文字信息 -->
					<button id="detailUpdate" onclick="changeDetailText()">修改</button>
					<button id="detailSave" onclick="updateTextSubmit()" style="display: none">保存</button>
				<form id="updateText" name="updateText" method="get">  
					<div class="pop_dpxq_mdname">
						<b class="mtz_title"  >门店名称:</b>
						<input type="text" value="${sd.nameJC }" name="nameJC" id="nameJC" class="mdname" readonly="readonly"  disabled="disabled">
					</div>
					<div class="pop_dpxq_mdname">
						<b class="mtz_title"  >门店电话:</b>
						<input type="text" value="${sd.contactTel }" name="contactTel" id="contactTel" class="mdname" readonly="readonly"  disabled="disabled">
					</div>
					<div class="pop_dpxq_mdname">
						<b class="mtz_title" >经营特色:</b>
						<textarea rows="5" cols=50" class="mdjyts" name="jyTS" id="jyTS" readonly="readonly" disabled="disabled">${sd.jyTS }</textarea>
					</div>
					<div class="pop_dpxq_mdname">
						<b class="mtz_title" >经营时间:</b>
						<textarea rows="5" cols=50" class="mdjyts" name="jySJ" id="jySJ" readonly="readonly"  disabled="disabled">${sd.jySJ }</textarea>
					</div>
				</form>
				
<!-- 				经营环境 -->
				<div class="pop_dpxq_mtz">
					<h4 class="mtz_title">经营环境:</h4>
					<div class="mdhj">
						<c:forEach items="${sd.attrList }" var="pic">
								<c:if test="${pic.attrType==1 }">
									<div class="mdhjimg" style="position: relative;">
										<div class="showUpdate" >
											<a href="JavaScript:void(0)" onclick="upLoadPic(this)"><font color="white">替换</font></a>
											<input type="file"   onchange="updateJyhjLogo(this,'${pic.id }')" name="companyStoreLogo" id="companyStoreLogo" style="display:none">
										</div>
										<img src="//image01.zhongjumall.com/d1/${pic.attrValue}" style="width: 100%; height: 200px;">
									</div>
								</c:if>
						</c:forEach>
						<c:if test="${hj!=0 }">
							<c:forEach begin="1" end="${hj }">
								<div class="mdhjimg" style="position: relative;">
									<div class="showUpdate" >
										<a href="JavaScript:void(0)" onclick="upLoadPic(this)">上传</a>
										<input type="file"   onchange="updateJyhjLogo(this,'0')" name="companyStoreLogo" id="companyStoreLogo" style="display:none">
									</div>
									<img src="${path }/images/noUpload.png" style="width: 100%; height: 200px;">
								</div>
							</c:forEach>
						</c:if>
					</div>
					</br>
					<input type="button" value="上传" class="up_btn">
				</div>
				<div class="pop_dpxq_mtz">
					<h4 class="mtz_title" >门店推荐:</h4>
					<div class="mdtj">
						<c:forEach items="${sd.attrList }" var="pic">
									<c:if test="${pic.attrType==2 }">
										<div class="mui-imageviewer">
											<div class="mdtjimg" style="position: relative;">
												<div class="showUpdate" >
													<a href="JavaScript:void(0)" onclick="upLoadPic(this)"><font color="white">替换</font></a>
													<input type="file"   onchange="updateMdtjLogo(this,'${pic.id }')" name="companyStoreLogo" id="companyStoreLogo" style="display:none">
												</div>
												<img src="//image01.zhongjumall.com/d1/${pic.attrValue}" style="width: 100%; height: 200px;">
											</div>
										</div>
									</c:if>
						</c:forEach>
						<c:if test="${tj!=0 }">
							<c:forEach begin="1" end="${tj }">
										<div class="mdtjimg" style="position: relative;">
											<div class="showUpdate" >
												<a href="JavaScript:void(0)" onclick="upLoadPic(this)">上传</a>
												<input type="file"   onchange="updateMdtjLogo(this,'0')" name="companyStoreLogo" id="companyStoreLogo" style="display:none">
											</div>
											<img src="${path }/images/noUpload.png" style="width: 100%; height: 200px;">
										</div>
							</c:forEach>
						</c:if>
					</div>
					</br>
					<input type="button" value="上传" class="up_btn">
				</div>
				<div class="pop_dpxq_mtz">
					<h4 class="mtz_title" >门店位置:</h4>
					<div class="mdtj">
						<iframe id="mapPage" width="100%" height="500px" frameborder=0  src="https://apis.map.qq.com/tools/locpicker?search=1&type=1&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77&referer=myapp"></iframe>					
					</div>

			  </div>


			<a href="javascript:history.go(-1)"><input type="button" style="background-color: red; color: white;width: 150px;height: 35px;cursor: pointer; font-size:20px;border-radius:5px;margin:30px 160px;border:1px  solid red"  value="返回页面"></a>
<!-- 			<a href="javascript:history.go(-1)"><input type="button" style="background-color: red; color: white;width: 150px;height: 35px;cursor: pointer; font-size:20px;border-radius:5px;border:1px  solid red"  value="修改页面"></a> -->
	
		</div>
	</div>
	
	
	
	
	
	
	<div class="blank10"></div>
	 <!-- 底部 start -->
		<%@include file="/WEB-INF/views/zh/include/foot.jsp"%>
	<!-- 底部 end -->
<script type="text/javascript">
			window.addEventListener('message', function(event) {
			    // 接收位置信息，用户选择确认位置点后选点组件会触发该事件，回传用户的位置信息
			    var loc = event.data;
			    if (loc && loc.module == 'locationPicker') {//防止其他应用也会向该页面post信息，需判断module是否为'locationPicker'
			      console.log('location', loc);
			    }
			}, false);
</script>
</body>
</html>