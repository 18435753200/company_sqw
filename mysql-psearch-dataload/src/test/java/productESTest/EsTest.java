package productESTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.common.ConstantManage;
import com.mall.config.DataSourceFactory;
import com.mall.po.DealerProInfo;
import com.mall.service.impl.DealerProInfoServiceImpl;

public class EsTest {

	/*private static ClassPathXmlApplicationContext context;
	 @Before
	 public void Test(){
		 context = new ClassPathXmlApplicationContext(new String[] { "datasource.xml" });
		 context.start();
		 //DataSourceFactory.getDataSource(ConstantManager.DBNAME);
	 }
	
	 @Test
	 public void  JDBCload() {
	        //初始商品具体实现
	        DealerProInfoServiceImpl dealerProInfoServiceImpl = new DealerProInfoServiceImpl();
	        //返回参数初始化
	        List<DealerProInfo> dealerlist = new ArrayList<DealerProInfo>();
	        //调用商品库
	        long start = System.currentTimeMillis();
	        dealerlist = dealerProInfoServiceImpl.getDealerProInfo("");
	        long end = System.currentTimeMillis();
	        System.out.println(("调用商品库所用时间--：" + (end - start)));
	        List<String> jsondealer = new ArrayList<String>();
	        Gson gson = new GsonBuilder().create();
	        for (DealerProInfo dealerProInfo : dealerlist) {
	            jsondealer.add(gson.toJson(dealerProInfo));
	        }
	        System.out.println(("==========" + jsondealer));
	       // return jsondealer;
	    }
*/
}
