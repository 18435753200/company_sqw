package com.mall.controller.comment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.authority.client.util.ValidateUtil;
import com.mall.comment.dto.CommentReplRequst;
import com.mall.comment.dto.CommentReplResponse;
import com.mall.comment.dto.CommentRequst;
import com.mall.comment.dto.CommentResponse;
import com.mall.comment.dto.CommontDTO;
import com.mall.comment.po.Comment;
import com.mall.comment.po.CommentAddTo;
import com.mall.comment.po.CommentReply;
import com.mall.comment.po.UserInfo;
import com.mall.customer.model.User;
import com.mall.customer.service.UserService;
import com.mall.dealer.product.api.DealerProductFinanceService;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Common;
import com.mall.utils.Constants;
import com.mall.utils.Page;

/**
 * .
 * 
 * @author Guofy
 * @Description:评论管理
 * @Date 2015-1-9
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController extends BaseController {
	/**
	 * . LOGGER(日志打印).
	 */
	private static final Log LOGGER = LogFactory
			.getLogger(CommentController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private DealerProductFinanceService dealerProductFinanceServiceImpl;
	
	
	@RequestMapping("/toUpload")
	public String toUpload(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "dealerseller/comment/upload";
	}
	
	@SuppressWarnings("resource")
	@RequestMapping("/upload")
	public String upload(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		MultipartHttpServletRequest mr = (MultipartHttpServletRequest) request;
		CommonsMultipartFile cf = (CommonsMultipartFile) mr.getFile("excelFile");
		byte[] fb = cf.getBytes();
		if(fb.length == 0) {
			throw new Exception("file can not be null");
		}
		InputStream inputs = new ByteArrayInputStream(fb);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputs);
		Iterator<XSSFSheet> iterator = xssfWorkbook.iterator();
		
		String mobiles = "";
		int i = 0;
		while (iterator.hasNext()) {
			XSSFSheet sheet = (XSSFSheet) iterator.next();
			String sheetName = sheet.getSheetName();
			for (Row row : sheet) {
				int rowNum = row.getRowNum();
				if(0 == rowNum) {
					continue;
				}
				CommontDTO comontDTO = new CommontDTO();
				for (Cell cell : row) {
					int columnIndex = cell.getColumnIndex();
					switch (columnIndex) {
					case 0:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cell0 = cell.getStringCellValue();
						LOGGER.info("[" + sheetName + "：" + rowNum + "]" + "cellValue0=" + cell0);
						comontDTO.setPid(Long.valueOf(cell0));
						break;
					case 1:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cell1 = cell.getStringCellValue();
						LOGGER.info("[" + sheetName + "：" + rowNum + "]" + "cellValue1=" + cell1);
						comontDTO.setpName(cell1);
						break;
					case 2:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cell2 = cell.getStringCellValue();
						LOGGER.info("[" + sheetName + "：" + rowNum + "]" + "cellValue2=" + cell2);
						comontDTO.setContext(cell2);
						break;
					case 3:
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						Double cell3 = cell.getNumericCellValue();
						LOGGER.info("[" + sheetName + "：" + rowNum + "]" + "cellValue3=" + cell3);
						comontDTO.setLevel(cell3.intValue());
						break;
					case 4:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cell4 = cell.getStringCellValue();
						LOGGER.info("[" + sheetName + "：" + rowNum + "]" + "cellValue4=" + cell4);
						comontDTO.setUserId(Long.valueOf(cell4));
						break;
					case 5:
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						Double cell5 = cell.getNumericCellValue();
						LOGGER.info("[" + sheetName + "：" + rowNum + "]" + "cellValue5=" + cell5);
						comontDTO.setIsAnonymity(cell5.intValue());
						break;
					default:
						
						break;
					}
			  }
				try {
					LOGGER.info("start to call service...");
					i ++;
					RemoteServiceSingleton.getInstance().getCommentService().upLoad(comontDTO);
				} catch (Exception e) {
					 mobiles = mobiles + "[" + sheetName + "：" + rowNum + "], ";
					 LOGGER.info("save comment exception sheetName:" + sheetName + " rowNum:" + rowNum + e.getMessage(), e);
				}
			}
			
		}
		if(StringUtils.isNotBlank(mobiles)) {
			
			LOGGER.info("upLoad failuer is : " + mobiles);
		}
		model.addAttribute("mobiles", mobiles);
		
		LOGGER.info("execute finished.count:"+i);
		
		return "dealerseller/comment/uploadInfo";
	}
	
	/**
	 * .
	 * 
	 * @Description:进入评论列表初始化页面
	 * @Title:getCommentJspPage()
	 * @return String
	 * @Date 2015-1-9
	 */
	@RequestMapping(value = "/getComments")
	public String getCommentJspPage() {
		return "dealerseller/comment/commentsManage";
	}

	/**
	 * .
	 * 
	 * @Title:getProductCommentListByCondition()
	 * @Description:根据条件查询评论列表
	 * @return
	 * @Date 2015-1-13
	 */
	@RequestMapping(value = "/getProductCommentListByCondition")
	public String getProductCommentListByCondition(HttpServletRequest request,
			HttpServletResponse response, Model model, String platformType,
			Long pid, String userName, String level, String startTime,
			String endTime, Integer page, String pName) {

		LOGGER.info("start to execute method <getProductCommentListByCondition>!!!!");

		LOGGER.info("商品ID:" + pid);
		LOGGER.info("商品名称:" + pName);
		LOGGER.info("回复人:" + userName);
		LOGGER.info("评论等级:" + level);
		LOGGER.info("开始时间:" + startTime);
		LOGGER.info("结束时间:" + endTime);
		// 平台类型.(0.1) 0,B2B.1B2C
		LOGGER.info("平台模式:" + platformType);

		CommentRequst commentRequst = new CommentRequst();
		Comment comment = new Comment();
		CommentResponse commentResponse = new CommentResponse();
		if (null != page) {
			commentRequst.setPageNo(page);
		} else {
			commentRequst.setPageNo(Constants.NUM1);
		}

		if (!Common.isEmpty(platformType)
				&& ("0".equals(platformType) || "1".equals(platformType))) {
			commentRequst.setPlatformType(platformType);
		} else {
			commentRequst.setCommentType(null);
		}

		commentRequst.setPageSize(Constants.NUM10);
		if (!Common.isEmpty(level)) {
			commentRequst.setCommentType(Integer.parseInt(level));
			comment.setLevel(Integer.parseInt(level));
		} else {
			commentRequst.setCommentType(0);
		}
		if (Common.isNull(pid)) {
			commentRequst.setPid(pid);
		}
		if (!Common.isEmpty(userName)) {

		}
		if (!Common.isEmpty(pName)) {
			commentRequst.setpName(pName);
		}
		if (!Common.isEmpty(startTime)) {
			commentRequst.setStartTime(Common.stringToDate(
					startTime.replace('-', '/'), "yyyy/MM/dd HH:mm:ss"));
		}
		if (!Common.isEmpty(endTime)) {
			commentRequst.setEndTime(Common.stringToDate(
					endTime.replace('-', '/'), "yyyy/MM/dd HH:mm:ss"));
		}
		commentRequst.setComment(comment);
		try {
			commentResponse = RemoteServiceSingleton.getInstance()
					.getCommentService()
					.findAllCommentByCondition(commentRequst);
			Page<Comment> pageBean = new Page<Comment>();
			Integer totalCount = 0;
			Integer totalPage = 0;
			totalCount = commentResponse.getTotalCount();
			if (totalCount % (Constants.NUM10) == 0) {
				totalPage = totalCount / (Constants.NUM10);
			} else {
				totalPage = totalCount / (Constants.NUM10) + 1;
			}
			LOGGER.info("totalPage<评论总页数>：" + totalPage);
			LOGGER.info("totalCount<评论总条数>：" + totalCount);
			pageBean.setTotalPage(totalPage);
			pageBean.setTotalCount(totalCount);
			pageBean.setPage(commentRequst.getPageNo());
			pageBean.setPageSize(Constants.NUM10);
			pageBean.setResult(commentResponse.getComments());
			request.getSession().setAttribute("pb", pageBean);
			request.getRequestDispatcher(
					"/WEB-INF/views/dealerseller/comment/model/commentModel.jsp")
					.forward(request, response);
		} catch (Exception e) {

			LOGGER.error(
					"wofe go get findAllCommentByCondition<根据条件获取评论列表> is failed!!!!"
							+ e.getMessage(), e);
		}
		LOGGER.info("end to execute method <getProductCommentListByCondition>!!!!");
		return null;
	}

	/**
	 * .
	 * 
	 * @Title:deleteProductComment()
	 * @Description:根据ID删除一条评论.
	 * @param String
	 *            pid
	 * @return
	 * @Date 2015-1-13
	 */
	@RequestMapping(value = "/deleteProductComment")
	@ResponseBody
	public String deleteProductComment(Long pid) {

		LOGGER.info("start to execute method <deleteProductComment>!!!!");
		LOGGER.info("要删除评论的ID:" + pid);
		String deleteResult = "";
		if (pid > 0) {
			try {
				RemoteServiceSingleton.getInstance().getCommentService()
						.deleteCommentById(pid);
				deleteResult = "删除成功";
			} catch (Exception e) {
				// TODO: handle exception
				deleteResult = "删除失败";
				LOGGER.error(
						"go deleteCommentById<删除评价!!> is failed!!!!"
								+ e.getMessage(), e);
			}
		}
		LOGGER.info("删除结果：" + deleteResult);
		LOGGER.info("end to execute method <deleteProductComment>!!!!");
		return deleteResult;
	}

	/**
	 * .
	 * 
	 * @Title:addProductCommentReploy()
	 * @Description:wofe对零售商的评价进行回复.
	 * @param String
	 *            id,String context
	 * @return String
	 * @Date 2015-1-13
	 */
	@RequestMapping(value = "/addProductCommentReploy")
	@ResponseBody
	public String addProductCommentReploy(Long id, String context) {
		String result = null;
		LOGGER.info("start to execute method <addProductCommentReploy>!!!!");

		LOGGER.info("此条评论的ID:" + id);
		LOGGER.info("回复内容:" + context);

		CommentReply commentReply = new CommentReply();
		if (!Common.isNull(id)) {
			commentReply.setCommentId(id);
		}
		if (!Common.isEmpty(context)) {
			commentReply.setContext(context);
		}
		String createBy = getCurrentUser().getUsername();
		UserInfo user = new UserInfo();
		user.setUserName(createBy);
		user.setCommentid(id);
		user.setIsComment(1);
		user.setUserId(Long.valueOf(getCurrentUser().getId()));
		commentReply.setUserInfo(user);
		try {
			LOGGER.info("当前登陆用户名称：" + createBy);
			LOGGER.info("回复评论的ID：" + commentReply.getCommentId());
			RemoteServiceSingleton.getInstance().getCommentService()
					.saveCommentRepl(commentReply);
			result = "添加成功";
		} catch (Exception e) {
			LOGGER.error(
					"go saveCommentRepl<添加回复!!> is failed" + e.getMessage(), e);
			result = "添加失败";
		}
		LOGGER.info("添加返回的结果：" + result);
		LOGGER.info("end to execute method <addProductCommentReploy>!!!!");
		return result;
	}

	/**
	 * .
	 * 
	 * @Title:isLegalCheckReplyContent()
	 * @Description:检验用户输入的字符是否含有非法字符
	 * @param String
	 *            content
	 * @return String
	 * @Date 2015-1-16
	 */
	@RequestMapping(value = "/isLegalCheckReplyContent")
	@ResponseBody
	public String isLegalCheckReplyContent(String content,String commentId) {
		LOGGER.info("start to execute method <isLegalCheckReplyContent>!!!!");
		LOGGER.info("输入回复的内容：" + content);
		String result = null;
		try {
			Set<String> IsSensitiveString = RemoteServiceSingleton
					.getInstance().getCommentService()
					.checkIsSensitive(content);
			System.out.println(IsSensitiveString + "set集合");

			List<String> list = null;
			if (!IsSensitiveString.isEmpty()) {
				list = new ArrayList<String>();
				Iterator<String> it = IsSensitiveString.iterator();
				while (it.hasNext()) {
					String str = it.next();
					list.add(str);
				}
			}
			LOGGER.info("非法字符：" + JSON.toJSONString(list));
			if(!ValidateUtil.isEmpty(list)){
				result = JSON.toJSONString(list);
			}else{
				Long id = Long.parseLong(commentId);
				CommentReply commentReply = new CommentReply();
				if (!Common.isNull(id)) {
					commentReply.setCommentId(id);
				}
				if (!Common.isEmpty(content)) {
					commentReply.setContext(content);
				}
				String createBy = getCurrentUser().getUsername();
				UserInfo user = new UserInfo();
				user.setUserName(createBy);
				user.setCommentid(id);
				user.setIsComment(1);
				user.setUserId(getCurrentUser().getSequenceId());
				commentReply.setUserInfo(user);
				RemoteServiceSingleton.getInstance().getCommentService()
				.saveCommentRepl(commentReply);
				result = "添加成功";
			}
					
		} catch (Exception e) {
			result = "content is error";
			LOGGER.error("go checkIsSensitive<检验输入的字符是否不文明!!> is failed!!!!"
					+ e.getMessage(), e);
		}
		LOGGER.info("检查的结果：" + result);
		LOGGER.info("end to execute method <isLegalCheckReplyContent>!!!!");
		return result;
	}

	/**
	 * 根据评论的ID查询此条评论下的回复
	 * 
	 * @param model
	 * @param commentId
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/getReplyListByCommentId")
	public String getReplyListByCommentId(Model model, Long commentId,
			Integer page) {

		LOGGER.info("start to execute method <getReplyListByCommentId>!!!!");
		LOGGER.info("评论ID" + commentId);
		LOGGER.info("分页码：" + page);
		Comment comment = new Comment();
		if (!Common.isNull(commentId)) {
			try {
				comment = RemoteServiceSingleton.getInstance()
						.getCommentService().findCommentById(commentId);
				List<CommentReply> commentReplys = RemoteServiceSingleton.getInstance()
						.getCommentService().findCommentReplysByCommentId(comment.getId());
	    		if(commentReplys.isEmpty()){
	    			comment.setReplies(null);
	    			comment.setRepliesCount(0);
				}else{
					comment.setReplies(commentReplys);
					comment.setRepliesCount(commentReplys.size());
				}
			} catch (Exception e) {
				LOGGER.error(
						"go findCommentById<根据评论的ID获取评论DTO!!> is failed!!!!"
								+ e.getMessage(), e);
			}
		}

		CommentReplRequst commentReplRequst = new CommentReplRequst();
		commentReplRequst.setCommentid(commentId);
		if (page != null) {
			commentReplRequst.setPageNo(page);
		} else {
			commentReplRequst.setPageNo(1);
		}
		commentReplRequst.setPageSize(Constants.PAGESIZE);
		CommentReplResponse commentReplResponse = RemoteServiceSingleton
				.getInstance().getCommentService()
				.findAllCommentReplyByCommentId(commentReplRequst);
		Page<CommentReply> pageBean = new Page<CommentReply>();
		Integer totalCount = 0;
		Integer totalPage = 0;
		totalCount = commentReplResponse.getTotalReplCount();
		if (totalCount % (Constants.NUM10) == 0) {
			totalPage = totalCount / (Constants.NUM10);
		} else {
			totalPage = totalCount / (Constants.NUM10) + 1;
		}
		LOGGER.info("totalCount<回复总条数>：" + totalCount);
		LOGGER.info("totalPage<回复总页数>：" + totalPage);
		pageBean.setTotalPage(totalPage);
		pageBean.setTotalCount(totalCount);
		pageBean.setPage(commentReplRequst.getPageNo());
		pageBean.setPageSize(Constants.NUM10);
		pageBean.setResult(commentReplResponse.getCommentRepls());
		model.addAttribute("pb", pageBean);
		model.addAttribute("comment", comment);
		LOGGER.info("end to execute method <getReplyListByCommentId>!!!!");
		return "dealerseller/comment/replyList";

	}

	/**
	 * . 评论列表的导出功能.
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param pid
	 * @param userName
	 * @param level
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param pName
	 */
	@RequestMapping(value = "/exportCommentListByConditionExcel")
	public void exportCommentListByConditionExcel(HttpServletRequest request,
			HttpServletResponse response, Model model, Long pid,
			String userName, String level, String startTime, String endTime,
			Integer page, String pName) {
		LOGGER.info("start to execute method <exportCommentListByConditionExcel>导出用户评论列表功能!!!!");
		LOGGER.info("商品ID:" + pid);
		LOGGER.info("商品名称:" + pName);
		LOGGER.info("回复人:" + userName);
		LOGGER.info("评论等级:" + level);
		LOGGER.info("开始时间:" + startTime);
		LOGGER.info("结束时间:" + endTime);
		CommentRequst commentRequst = new CommentRequst();
		Comment comment = new Comment();
		CommentResponse commentResponse = new CommentResponse();
		if (null != page) {
			commentRequst.setPageNo(page);
		} else {
			commentRequst.setPageNo(Constants.NUM1);
		}
		commentRequst.setPageSize(Constants.MAXPAGESIZE);
		if (!Common.isEmpty(level)) {
			commentRequst.setCommentType(Integer.parseInt(level));
		} else {
			commentRequst.setCommentType(0);
		}
		if (!Common.isNull(pid)) {
			commentRequst.setPid(pid);
		}
		if (!Common.isEmpty(userName)) {

		}
		if (!Common.isEmpty(pName)) {
			commentRequst.setpName(pName);
		}
		if (!Common.isEmpty(startTime)) {
			commentRequst.setStartTime(Common.stringToDate(
					startTime.replace('-', '/'), "yyyy/MM/dd HH:mm:ss"));
		}
		if (!Common.isEmpty(startTime)) {
			commentRequst.setEndTime(Common.stringToDate(
					endTime.replace('-', '/'), "yyyy/MM/dd HH:mm:ss"));
		}
		commentRequst.setComment(comment);
		try {
			commentResponse = RemoteServiceSingleton.getInstance()
					.getCommentService()
					.findAllCommentByCondition(commentRequst);
		} catch (Exception e) {
			LOGGER.error(
					"wofe go get findAllCommentByCondition<根据条件获取评论列表> is failed!!!!"
							+ e.getMessage(), e);
		}

		HSSFWorkbook book = new HSSFWorkbook();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		HSSFSheet sheet = book.createSheet("commentList");
		sheet.setColumnWidth(0, 8000);
		sheet.setColumnWidth(1, 8000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 12000);
		sheet.setColumnWidth(6, 5000);
		sheet.setColumnWidth(7, 5000);

		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		String[] strtitle = { "订单号","商品信息", "星级", "评论人","手机号", "评论内容", "评论时间", "购买时间","商品分类" };
		for (int i = 0; i < strtitle.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(strtitle[i]);
		}
		List<Comment> commentList = commentResponse.getComments();
		if (null != commentList && commentList.size() > 0) {
			
			Map<Long, String> pidCategoryMap = getPidCategoryMap(commentList);
			
			Map<Long, User> userMap = getUserMap(commentList);
			int r = 1;
			for (Comment comment2 : commentList) {
				row = sheet.createRow(r++);
				String createtime = simpleDateFormat.format(comment2.getDate());
				// 订单号
				row.createCell(Constants.SHORT0).setCellValue(
						String.valueOf(comment2.getOrderInfo().getOrderId()));
				// 商品信息
				row.createCell(Constants.SHORT1).setCellValue(
						comment2.getOrderInfo().getpName());
				// 星级
				row.createCell(Constants.SHORT2).setCellValue(
						comment2.getLevel());
				// 评论人
				row.createCell(Constants.SHORT3).setCellValue(
						comment2.getUserInfo().getUserName());
				// 手机号
				if(userMap != null){
					User user = userMap.get(comment2.getUserInfo().getUserId());
					if(user != null){
						row.createCell(Constants.SHORT4).setCellValue(
								user.getMobile());
					}
				}
				// 评论内容
				row.createCell(Constants.SHORT5).setCellValue( "[评论]" +
						comment2.getContext());
				// 评论时间
				row.createCell(Constants.SHORT6).setCellValue(createtime);

				// 购买时间
				if (!Common.isNull(comment2.getOrderInfo())
						&& !Common.isNull(comment2.getOrderInfo().getBuyDate())) {
					String buyTime = simpleDateFormat.format(comment2
							.getOrderInfo().getBuyDate());
					row.createCell(Constants.SHORT7).setCellValue(buyTime);
				}
				try {
					Long tpid = comment2.getOrderInfo().getPid();
					String cate = pidCategoryMap.get(tpid);
					if (!Common.isEmpty(cate)) {
						row.createCell(Constants.SHORT8).setCellValue(cate);
					}
				}
				catch(Exception e) {
					
				}
				
				List<CommentReply> replies = comment2.getReplies();
				if (null != replies && !replies.isEmpty()) {
					for (CommentReply reply : replies) {
						row = sheet.createRow(r++);

						row.createCell(Constants.SHORT5).setCellValue( "[回复]" +reply.getContext());
						// 评论时间
						createtime = simpleDateFormat.format(reply.getDate());
						row.createCell(Constants.SHORT6).setCellValue(createtime);

					}
				}
				
				CommentAddTo commentAddTo = comment2.getCommentAddTo();
				if (null != commentAddTo && StringUtils.isNotEmpty(commentAddTo.getContext())) {
					row = sheet.createRow(r++);

					row.createCell(Constants.SHORT5).setCellValue( "[追评]" +commentAddTo.getContext());
					// 评论时间
					createtime = simpleDateFormat.format(commentAddTo.getDate());
					row.createCell(Constants.SHORT6).setCellValue(createtime);

				}
				
			}
		}
		LOGGER.info("拼装信息完成!");
		try {
			String filename = "retailer-order-information";
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setCharacterEncoding("UTF-8");
			filename = java.net.URLEncoder.encode(filename, "gb2312");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes("UTF-8"), "GBK") + ".xls");
			response.setContentType("application/msexcel");// 定义输出类型
			book.write(os);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			LOGGER.error("wofe go 下载 Excel表格错误 is failed!!!!" + e.getMessage(),
					e);
		}
		LOGGER.info("end<> to execute method <exportCommentListByConditionExcel>导出用户评论列表功能!!!!");
	}

	private Map<Long, String> getPidCategoryMap(List<Comment> commentList) {
		List<Long> pidList = new ArrayList<Long>();
		for (Comment comment2 : commentList) {
			try {
				pidList.add(comment2.getOrderInfo().getPid());
			}
			catch(Exception e) {
				continue;
			}
		}
		
		Map<Long, String> map = new HashMap<Long, String>();
		try {
			Map<Long, String> categoryMap = dealerProductFinanceServiceImpl.findProductCategoryByPid(pidList);
			
			if(categoryMap!=null&&categoryMap.size()>0) {
				
				for(Long pid : pidList) {
					String category = categoryMap.get(pid);
					if(category !=null && !"".equals(category) && !map.containsKey(pid)){
						if (category.endsWith("+")) {
							category = category.substring(0, category.length() - 1);
							map.put(pid, category);
						}
					}
				}
        	} 
		}
		catch(Exception e) {
			logger.info("查询商品信息报错");
			logger.info("", e);
		}
		
		return map;
	}

	private Map<Long, User> getUserMap(List<Comment> commentList){
		
		// 封装请求参数
		Map<String, Long> userIds = new HashMap<String, Long>();
		for (Comment comment : commentList) {
			if(comment == null){
				continue;
			}
			
			UserInfo userInfo = comment.getUserInfo();
			if(userInfo == null || userInfo.getUserId() == null){
				logger.info("getUserMobileMap 封装请求参数 userInfo为空或者userid为空。");
				continue;
			}
			userIds.put(String.valueOf(userInfo.getUserId()), userInfo.getUserId());
		}
		
		Map<Long, User> userMap = null;
		try {
			userMap = userService.findUsersByUserIds(userIds);
		} catch (Exception e) {
			logger.error("userService.findUsersByUserIds has error.message:"+e.getMessage(),e);
			return null;
		}
		
		return userMap;
	}
	
	/**
	 * . 此方法用于日期的转换
	 * 
	 * @param binder
	 *            WebDataBinder
	 */
	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
}
