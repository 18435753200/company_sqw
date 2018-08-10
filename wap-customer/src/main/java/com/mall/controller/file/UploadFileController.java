/**
 * 
 */
package com.mall.controller.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.csource.upload.UploadFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mall.constant.CommonConstant;
import com.mall.constant.RequestContant;
import com.mall.controller.base.BaseController;
import com.mall.utils.CommonUtil;

/**
 * @author jianping.gao
 *
 */
@Controller
@RequestMapping(value = RequestContant.UPLOAD)
public class UploadFileController extends BaseController {
	/**
	 * define log
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(UploadFileController.class);

	@RequestMapping(value = "/file")
	public void uploadImg(HttpServletRequest request,
			HttpServletResponse response, String action) {

		if (action.equals("uploadimage")) {

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 获取file框的
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

			// 遍历获取的所有文件
			List<String> picUrlS = new ArrayList<String>();
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				try {
					MultipartFile mf = entity.getValue();
					// 判断文件名是否为空。为空set null值
					String myfileUrl = UploadFileUtil.uploadFile(
							mf.getInputStream(),
							CommonUtil.getFileExt2(mf.getOriginalFilename()),
							null);
					if (null != myfileUrl) {
						String newUrl = CommonConstant.FILESERVER5 + myfileUrl;
						picUrlS.add(CommonConstant.FILESERVER5 + myfileUrl);
						String status = "{\"state\": \"SUCCESS\",\"title\": \"\",\"original\":\""
								+ mf.getOriginalFilename()
								+ "\",\"type\": \""
								+ CommonUtil.getFileExt(myfileUrl)
								+ "\",\"url\":\""
								+ newUrl
								+ "\",\"size\": \""
								+ mf.getSize() + "\"}";
						response.getWriter().print(status);
						LOG.info("uedtior上传图片，图片图片服务器返回 ：" + myfileUrl
								+ ";原始文件名：" + mf.getOriginalFilename());
					} else {
						response.getWriter().print(
								"{\"state\": \"Server is Error!\"}");
					}
				} catch (Exception e) {

					try {
						response.getWriter().print(
								"{\"state\": \"Server is Error!\"}");
					} catch (IOException e1) {
						LOG.error("富文本编辑器上传图片失败！！！" + e.getMessage(), e);
					}
					LOG.error("富文本编辑器上传图片失败！！！" + e.getMessage(), e);
				}
			}
		}
	}
}
