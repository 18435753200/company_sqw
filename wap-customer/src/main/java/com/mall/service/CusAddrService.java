package com.mall.service;

import java.util.Date;
import java.util.List;

import net.rubyeye.xmemcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mall.category.api.rpc.BaseDataServiceRpc;
import com.mall.category.po.AgentCity;
import com.mall.category.po.AgentCounty;
import com.mall.category.po.AgentProvince;
import com.mall.customer.model.ReceiveAddress;
import com.mall.customer.model.User;
import com.mall.customer.service.ReceiveAddressService;
import com.mall.exception.BadRequestException;
import com.mall.exception.BusinessException;
import com.mall.wap.proxy.RemoteServiceSingleton;

/**
 * 收货人地址
 * 
 * @author ccigQA01
 * 
 */
@Service
public class CusAddrService {
	private static final Logger log = LoggerFactory
			.getLogger(CusAddrService.class);

	@Autowired
	private MemcachedClient memcachedClient;
	//地址服务
	private ReceiveAddressService receiveAddressService;
	//类目服务
	private BaseDataServiceRpc baseDataServiceRpc;
	/**
	 * 待删除，统一使用 AddressControllerImpl 提供的服务
	 * 
	 * 根据用户id查询所有的 收货人地址信息
	 * @param userId
	 * @return
	 */
	@Deprecated
	public List<ReceiveAddress> getReceiveAddressesByUserId(Long userId) {
		List<ReceiveAddress> addrs=null;
		try {
			receiveAddressService=getReceiveAddressService();
		  addrs = receiveAddressService.getReceiveAddressesByUserId(userId);
		} catch (Exception e) {
			log.error("call receiveAddressService error.....",e);
			throw new BusinessException("call receiveAddressService error....",e);
		}

		setReceiveAddressProvCityCountName(addrs);
		
		return addrs;
	}
	
	
	private ReceiveAddressService getReceiveAddressService() {
		return	RemoteServiceSingleton.getInstance().getReceiveAddressService();
	}

	/**
	 * 添加收货人地址
	 * @param currentUser
	 * @param receiveAddress
	 * @return
	 */
	public String addAddress(User currentUser, ReceiveAddress receiveAddress, Model model) {
		receiveAddressService = getReceiveAddressService();
		int saveReceiveAddressWithUserId =0;
		try {	
			receiveAddress.setUserId(currentUser.getUserId());
			receiveAddress.setIsDefault(false);
			receiveAddress.setCreateTime(new Date());
			receiveAddress.setStatus(false);
			saveReceiveAddressWithUserId = receiveAddressService.saveReceiveAddressWithUserId(receiveAddress);
		} catch (Exception e) {
			log.error("call receiveAddressService error...."+e,e);
		}
		return saveReceiveAddressWithUserId==1?"addok":"adderror";
	}

	
	
	
	/**
	 * 获取省市街道的字符串
	 * @param receiveAddress
	 * @return
	 */
	private String getProCityCount(ReceiveAddress receiveAddress) {
		baseDataServiceRpc=getBaseDataServiceRpc();
		if(receiveAddress.getProvinceId()==null || receiveAddress.getCityId() ==null ){
			return "null";
		}
		   String provinceCityCountyName = baseDataServiceRpc.getProvinceCityCountyName(receiveAddress.getProvinceId(), receiveAddress.getCityId(), receiveAddress.getAreaId());
		   String address = receiveAddress.getAddress();
		   address=provinceCityCountyName+"  "+address.replaceAll(" +","");;
		  return address; 
	}


	/**
	 * 修改收货人地址
	 * @param receiveAddress
	 * @param currentUser
	 * @return
	 */
	public String updateAddress(ReceiveAddress receiveAddress, User currentUser, Model model) {
		receiveAddressService = getReceiveAddressService();
		int updateReceiveAddressWithAddressId=0;
		
		try {
			updateReceiveAddressWithAddressId = receiveAddressService.updateReceiveAddressWithAddressId(receiveAddress);
		} catch (Exception e) {
			log.error("call receiveAddressService error...."+e,e);
		}
		return updateReceiveAddressWithAddressId==1?"updateok":"updateerror";
	}

	/**
	 * 删除地址
	 * @param request
	 * @param addressId
	 * @return
	 */
	public String delteAddress( User currentUser, Long addressId, Model model) {
		receiveAddressService = getReceiveAddressService();
		int deleteReceiveAddressByAddressId =0;
		try {
			 deleteReceiveAddressByAddressId = receiveAddressService.deleteReceiveAddressByAddressId(addressId);
		} catch (Exception e) {
			log.error("call receiveAddressService error...."+e,e);
		}
		return deleteReceiveAddressByAddressId==1?"deleteok":"deleteerror";
	}

	/**
	 * 设为默认地址
	 * @param addresssId
	 * @return
	 */
	public String defaultAddress(Long addresssId, Model model) {
		receiveAddressService = getReceiveAddressService();
			int defaultAddressWithAddressId =0;
			try {
			   defaultAddressWithAddressId = receiveAddressService.setDefaultAddressWithAddressId(addresssId);
		} catch (Exception e) {
			log.error("call receiveAddressService error...."+e,e);
		}
		return defaultAddressWithAddressId==1?"defaultok":"defaulterror";
	}

	/**
	 * 待删除，统一使用 AddressControllerImpl 提供的服务
	 * 
	 * 根据id获取指定收货地址
	 * @param addressId
	 * @return
	 */
	@Deprecated
	public ReceiveAddress getAddressByAddressId(Long addressId) {
		if(addressId == null){
			log.error("addressId is null ");
			throw new BadRequestException("addressIs  is null");
		}
		receiveAddressService = getReceiveAddressService();

		ReceiveAddress address = receiveAddressService.getReceiveAddressByAddressId(addressId);
		this.setReceiveAddressProvCityCountName(address);
		
		return address;
	}


	/**
	 * 获取省份
	 * @return
	 */
	public List<AgentProvince> getProvince() {
		// TODO Auto-generated method stub
		baseDataServiceRpc=getBaseDataServiceRpc();
		List<AgentProvince> allProvices=null;
		try {
		  allProvices =  baseDataServiceRpc.getAllProvices();
		} catch (Exception e) {
			log.error("call baseDataServiceRpc.getAllProvices error ....");
			throw new BusinessException("  call service error");
		}
		return allProvices;
	}

	/**
	 * 获取类目服务
	 * @return
	 */
	private BaseDataServiceRpc getBaseDataServiceRpc() {
		return RemoteServiceSingleton.getInstance().getBaseDataServiceRpcApi();
	}


	/**
	 * 获取城市列表
	 * @param proId
	 * @return
	 */
	public List<AgentCity> getCity(Integer proId) {
		// TODO Auto-generated method stub
		baseDataServiceRpc=getBaseDataServiceRpc();
		List<AgentCity> citiesByProvinceId=null;
		try {
		 citiesByProvinceId = baseDataServiceRpc.getCitiesByProvinceId(proId);
		} catch (Exception e) {
			log.error("call baseDataServiceRpc.getCitiesByProvinceId error ....");
			throw new BusinessException("  call service error");
		}
		return citiesByProvinceId;
	}
	
	/**
	 * \获取城镇列表
	 * @param ciId
	 * @return
	 */
	public List<AgentCounty> getCountry(Integer ciId) {
		// TODO Auto-generated method stub
		baseDataServiceRpc=getBaseDataServiceRpc();
		List<AgentCounty> citiesByProvinceId=null;
		try {
		 citiesByProvinceId = baseDataServiceRpc.getCountiesByCityId(ciId);
		} catch (Exception e) {
			log.error("call baseDataServiceRpc.getCountiesByCityId error ....");
			throw new BusinessException("  call service error");
		}
		return citiesByProvinceId;
	}
	
	/**
	 * 设置省市区信息
	 * @param addressList
	 */
	private void setReceiveAddressProvCityCountName(List<ReceiveAddress> addressList) {
		if (null == addressList || addressList.size() <= 0) {
			return;
		}
		
		for (ReceiveAddress address : addressList) {
			setReceiveAddressProvCityCountName(address);
		}
	}
	
	/**
	 * 设置省市区信息
	 * @param address
	 */
	private void setReceiveAddressProvCityCountName(ReceiveAddress address) {
		if (null == address) {
			return;
		}
		
		address.setProvinceCityCountyName(this.getProvinceCityCountyName(address.getProvinceId(), address.getCityId(), address.getAreaId()));
	}
	
	private String getProvinceCityCountyName(Long provinceId, Long cityId, Long areaId) {
		baseDataServiceRpc=getBaseDataServiceRpc();
		if( provinceId == null || cityId == null ){
			return "";
		}
		return baseDataServiceRpc.getProvinceCityCountyName(provinceId, cityId, areaId);
	}
}
