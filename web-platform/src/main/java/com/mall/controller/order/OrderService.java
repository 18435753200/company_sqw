package com.mall.controller.order;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
//import com.mall.retailer.model.Retailer;
import com.mall.platform.proxy.RemoteServiceSingleton;

/**
 * 订单服务.
 * @author zhouzb
 *
 */
public class OrderService {
	/**
	 * LOGGER.
	 */
	private static final Log LOGGER = LogFactory.getLogger(OrderService.class);
	/**
	 * @param addressId Long
	 * @return String address info
	 * @throws Exception 
	 */
	/*public String getAddressByAddressId(Retailer retailer) 
			throws Exception{
		String returnRealeAddress = "";
		
		String provincename = "";
		String cityname = "";
		String countyname = "";
		try{	
			if(retailer!=null){
				
				int provinceId = retailer.getProvinceId();
				int cityId = retailer.getCityId();
				int areaId = retailer.getAreaId();
				
				provincename = RemoteServiceSingleton.getInstance()
						.getBaseDataServiceRpc().getProvinceById(provinceId).getProvincename();
				cityname = RemoteServiceSingleton.getInstance()
						.getBaseDataServiceRpc().getCityById(cityId).getCityname();
				countyname =  RemoteServiceSingleton.getInstance()
						.getBaseDataServiceRpc().getCountyById(areaId).getCountyname();
			}
			
			returnRealeAddress = provincename+" " +cityname + " " + countyname;
		}catch (Exception e) {
			LOGGER.error("-------The buyer's address is empty-------");
		}	
		return returnRealeAddress;
	}*/
	/**
	 * @param addressId Long
	 * @return String address info
	 * @throws Exception 
	 */
	public String getAddress(Long provinceId,Long cityId,Long areaId) 
			throws Exception{
		String returnRealeAddress = "";
		
		String provincename = "";
		String cityname = "";
		String countyname = "";
		try{	

			int provinceIdint = Integer.parseInt(provinceId+"");
			int cityIdint = Integer.parseInt(cityId+"");
			int areaIdint = Integer.parseInt(areaId+"");

			provincename = RemoteServiceSingleton.getInstance()
					.getBaseDataServiceRpc().getProvinceById(provinceIdint).getProvincename();
			cityname = RemoteServiceSingleton.getInstance()
					.getBaseDataServiceRpc().getCityById(cityIdint).getCityname();
			countyname =  RemoteServiceSingleton.getInstance()
					.getBaseDataServiceRpc().getCountyById(areaIdint).getCountyname();

			returnRealeAddress = provincename+" " +cityname + " " + countyname;
		}catch (Exception e) {
			LOGGER.error("-------The buyer's address is empty-------");
		}	
		return returnRealeAddress;
	}
}
