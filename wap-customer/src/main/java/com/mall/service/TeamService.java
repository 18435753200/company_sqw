package com.mall.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.customer.dto.UserGroupDto;
import com.mall.customer.model.User;
import com.mall.customer.service.UserService;
import com.mall.mybatis.utility.PageBean;
import com.mall.pay.common.StringUtils;
import com.mall.supplier.dto.CompanyDto;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.vo.TeamVO;

/**
 * 我的团队
 * 
 * @author cyy
 * 
 */
@Service
public class TeamService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamService.class);

	@Autowired
	private UserService userService;

	@Autowired
	private SupplierManagerService supplierManagerService;

	/**
	 * 我的团队
	 * 
	 * @return
	 */
	public TeamVO index(User userInfo) {
		try {
			TeamVO team = new TeamVO();
			UserGroupDto userGroupDto = userService.getUserGroupDto(userInfo.getUserId(), 1, 20);
			PageBean<Supplier> pageBean = new PageBean<Supplier>();
			pageBean.setPage(1);
			pageBean.setPageSize(20);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", userInfo.getUserId());
			pageBean.setParameter(param);
			PageBean<CompanyDto> supplierList = supplierManagerService.findTjSupplier(pageBean);
			team.setSupplierList(supplierList);
			team.setUserGroupDto(userGroupDto);
			return team;
		} catch (Exception e) {
			LOGGER.error("index error ....", e);
			throw new RuntimeException("index error", e);
		}
	}

	/**
	 * 加载更多
	 * 
	 * @param type 0我的推荐用户1企业
	 * @return
	 */
	public TeamVO more(HttpServletRequest request, User userInfo, Integer type) {
		try {
			TeamVO team = new TeamVO();
			String pageStr = request.getParameter("page");
			Integer page = StringUtils.isNotEmpty(pageStr) ? Integer.parseInt(pageStr) : 1;
			String pageSizeStr = request.getParameter("pageSize");
			Integer pageSize = StringUtils.isNotEmpty(pageSizeStr) ? Integer.parseInt(pageSizeStr) : 20;
			if (type == 0) {
				UserGroupDto userGroupDto = userService.getUserGroupDto(userInfo.getUserId(), page, pageSize);
				team.setSubFirstUserList(userGroupDto.getSubFirstUserList());
			} else {
				PageBean<Supplier> pageBean = new PageBean<Supplier>();
				pageBean.setPage(page);
				pageBean.setPageSize(pageSize);
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("userTj", userInfo.getMobile());
				pageBean.setParameter(param);
				PageBean<CompanyDto> supplierList = supplierManagerService.getTjEnterpriseList(pageBean);
				team.setSupplierList(supplierList);
			}
			return team;
		} catch (Exception e) {
			LOGGER.error("more error ....", e);
			throw new RuntimeException("more error", e);
		}
	}
}
