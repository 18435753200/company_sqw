package com.mall.controller.comment;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.comment.api.rpc.CommentService;
import com.mall.comment.dto.CommentRequst;
import com.mall.comment.dto.CommentResponse;
import com.mall.constant.RequestContant;
import com.mall.controller.base.BaseController;
import com.mall.wap.proxy.RemoteServiceSingleton;

/**
 * 商品相关
 * 
 * @author yuanxiayang
 *
 */
@Controller
@RequestMapping(RequestContant.COMMENT)
public class ItemCommentController extends BaseController {

	/**
	 * slf4j log4j
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ItemCommentController.class);

	public static final String COMMENT_INFO = "comment/comment";

	private static final int PAGESIZE = 5;

	/**
	 * get commnetInfo 页面
	 * 
	 * @return
	 */
	// @ResponseBody
	@RequestMapping("/{pId:[\\d]+}")
	public String getCommentStatisticsByPid(ServletRequest request,
			ServletResponse response, Model model, @PathVariable("pId") Long pId) {

		logger.info("--------------get commentInfo start");

		try {
			CommentService commentApi = RemoteServiceSingleton.getInstance()
					.getCommentApi();
			CommentRequst commentRequst = new CommentRequst();
			commentRequst.setPid(pId);
			commentRequst.setPlatformType("1");
			CommentResponse commentStatistics = commentApi
					.getAllTypeCommentCount(commentRequst);
			model.addAttribute("commentStatistics", commentStatistics);
			model.addAttribute(
					"allTotalPageNum",
					(commentStatistics.getTotalCount() % 5 == 0 ? commentStatistics
							.getTotalCount() / 5 : (commentStatistics
							.getTotalCount() / 5 + 1)));
			model.addAttribute(
					"goodTotalPageNum",
					(commentStatistics.getGoodCount() % 5 == 0 ? commentStatistics
							.getGoodCount() / 5 : (commentStatistics
							.getGoodCount() / 5 + 1)));
			model.addAttribute("okTotalPageNum", (commentStatistics
					.getOkCount() % 5 == 0 ? commentStatistics.getOkCount() / 5
					: (commentStatistics.getOkCount() / 5 + 1)));
			model.addAttribute(
					"badTotalPageNum",
					(commentStatistics.getBadCount() % 5 == 0 ? commentStatistics
							.getBadCount() / 5 : (commentStatistics
							.getBadCount() / 5 + 1)));
		} catch (Exception e) {
			logger.error(
					"-------------get commentInfo error------" + e.getMessage(),
					e);
			return COMMENT_INFO;
		}
		logger.info("--------------get commentInfo success");
		return COMMENT_INFO;
	}

	/**
	 * 异步加载分页
	 * 
	 * @return
	 */
	@RequestMapping("/{pId:[\\d]+}-{type:[0|1|2|3]+}-{no:[\\d]+}")
	public String getCommentsByPid(Model model, @PathVariable("pId") Long pId,
			@PathVariable("type") int type, @PathVariable("no") int no) {
		if (no < 1) {
			no = 1;
		}
		CommentRequst commentRequst = new CommentRequst();
		commentRequst.setCommentType(type);
		commentRequst.setPageNo(no);
		commentRequst.setPid(pId);
		commentRequst.setPageSize(PAGESIZE);
		commentRequst.setPlatformType("1");
		CommentService commentApi = RemoteServiceSingleton.getInstance()
				.getCommentApi();
		CommentResponse result = commentApi.findCommentsByPid(commentRequst);
		model.addAttribute("result", result);
		return "comment/commentDetail";
	}
}
