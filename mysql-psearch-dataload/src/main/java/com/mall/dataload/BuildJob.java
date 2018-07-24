package com.mall.dataload;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.config.BeanConfiguration;
import com.mall.elasticsearchClent.ElasticSearchClientUtil;
import com.mall.po.DealerProInfo;
import com.mall.po.Supplier;
import com.mall.po.SupplierDetail;
import com.mall.service.SupplierInfoService;
import com.mall.service.impl.DealerProInfoServiceImpl;
import com.mall.service.impl.SupplierInfoServiceImpl;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windyLuo
 */

@DisallowConcurrentExecution
public class BuildJob implements Job {
    private static final Logger logger = Logger.getLogger(ProductLoadMain.class);
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long start = System.currentTimeMillis();
        logger.info("build start");
        boolean writeor = ElasticSearchClientUtil.writeDocument2(JDBCload(),JDBCloadSupplier());
        long end = System.currentTimeMillis();
        logger.info("bulid end time --：" + (end - start));
        if (false == writeor) {
            logger.info("调用 ElasticSearchClientUtil 中写索引失败");
        }
    }
    
    
    //查询商品的数据
    private List<String> JDBCload() {
        //初始商品具体实现
        DealerProInfoServiceImpl dealerProInfoServiceImpl = new DealerProInfoServiceImpl();
        //返回参数初始化
        List<DealerProInfo> dealerlist = new ArrayList<DealerProInfo>();
        //调用商品库
        long start = System.currentTimeMillis();
        dealerlist = dealerProInfoServiceImpl.getDealerProInfo("");
        long end = System.currentTimeMillis();
        logger.info("调用商品库所用时间--：" + (end - start));
        List<String> jsondealer = new ArrayList<String>();
        Gson gson = new GsonBuilder().create();
        for (DealerProInfo dealerProInfo : dealerlist) {
            jsondealer.add(gson.toJson(dealerProInfo));
        }
        logger.debug("==========" + jsondealer);
        return jsondealer;
    }
    
    //查询商家的数据
    private List<String> JDBCloadSupplier() {
    	SupplierInfoService supplierInfoService=new SupplierInfoServiceImpl();
        //调用商家信息库
        long start = System.currentTimeMillis();
        List<Supplier> list = supplierInfoService.getSupplierInfo("");
//        for (Supplier supplier : list) {
//        	System.out.println(supplier);
//        	if(list.get(list.size()-1).equals(supplier)){
////        	if(list.get(1).equals(supplier)){
//        		System.out.println(list.size());
//        		try {
//					Thread.sleep(300000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//        	}
//		}
        logger.info(list);
        
        //将商家数据翻倍,方便测试
        //====================
        
       /* List<Supplier> newList=new ArrayList<>();
        for(int i=0;i<=2;i++){
        	System.out.println(i);
        	for (Supplier supplier : list) {
        		System.out.println(supplier);
        		newList.add(supplier);
			}
        }
        System.out.println(newList);
        list=newList;*/
        //====================
        //调用商家店铺详细库
        List<SupplierDetail> Detaillist = supplierInfoService.getSupplierDetailInfo("");
        long end = System.currentTimeMillis();
        logger.info("调用库所用时间--：" + (end - start));
        //判断商家是否进行过对店铺详情页面的修改,如果有修改,就用修改后的数据,未修改就使用默认数据
        List<SupplierDetail> newDatailList=initializeSupplierDetail(list,Detaillist);
        
        for (Supplier supplier : list) {
			for (SupplierDetail supplierDetail : newDatailList) {
				if(supplier.getSupplierId().toString().equals(supplierDetail.getSupplierId().toString())){
					supplier.setSupplierDetail(supplierDetail);
				}
			}
		}
        
        
       /* List<List<String>> jsonList=new ArrayList<>();*/
        //将商家数据json格式化
        List<String> jsondealer = new ArrayList<String>();
        Gson gson = new GsonBuilder().create();
        for (Supplier supplier : list) {
            jsondealer.add(gson.toJson(supplier));
        }
       /* jsonList.add(jsondealer);*/
        logger.debug("==========" + jsondealer);
        //将商家店铺详情数据json格式化
     /*   List<String> attrJsondealer = new ArrayList<String>();
        for (SupplierDetail supplierDetail : newDatailList) {
        	attrJsondealer.add(gson.toJson(supplierDetail));
        }
        jsonList.add(attrJsondealer);
        logger.debug("==========" + jsondealer);*/
        return jsondealer;
    }
    
    
    
    
    private List<SupplierDetail> initializeSupplierDetail(List<Supplier> list, List<SupplierDetail> detaillist) {
    	SupplierDetail sd1=new SupplierDetail();
    	detaillist.add(sd1);
    	List<SupplierDetail> newList=new ArrayList<SupplierDetail>();
    	if(list.size()!=0 && detaillist.size()!=0){
	    	for (Supplier supplier : list) {
	    		int i=0;
				for (SupplierDetail supplierDetail : detaillist) {
					i++;
					if(supplier.getSupplierId()!=null && supplierDetail.getSupplierId()!=null && supplier.getSupplierId().toString().equals(supplierDetail.getSupplierId().toString())){
						supplierDetail.setMainBusiness(supplier.getCompanyBizCategoryName());
						newList.add(supplierDetail);
						i=0;
						break;
					}
					if(detaillist.size()==i){
						SupplierDetail sd=new SupplierDetail();
						sd.setSupplierId(Integer.valueOf(supplier.getSupplierId().toString()));
						sd.setNameJC(supplier.getNameJC());
						sd.setCompanyStoreLogo(supplier.getCompanyStoreLogo());
						sd.setLogoImgurl(supplier.getLogoImgurl());
						sd.setContact(supplier.getContact());
						sd.setContactTel(supplier.getContactTel());
						sd.setPhone(supplier.getPhone());
						sd.setMainBusiness(supplier.getCompanyBizCategoryName());
						sd.setLocationPoiaddress(supplier.getAddress());
						sd.setLocationCityname(supplier.getProvinceName()+supplier.getCityName()+supplier.getAreaName());
						newList.add(sd);
						i=0;
					}
					
				}
			}
    	}
		return newList;
	}


	/**
     * 测试使用
     * @param args
     */
    public static void main(String[] args) {
    	 BeanConfiguration.init();
		BuildJob bd=new BuildJob();
		ElasticSearchClientUtil.writeDocument2(bd.JDBCload(),bd.JDBCloadSupplier());
//		List<String> list = bd.JDBCloadSupplier();
//		System.out.println(list);
	}
}

