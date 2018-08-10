package cache;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mall.architect.redis.JedisManager;
import com.mall.dsearch.po.SearchCateDispTree;
import com.mall.search.service.impl.ProductAttrServiceImpl;
import com.mall.search.service.impl.ProductCatalogServiceImpl;

public class CacheTest {
	private ClassPathXmlApplicationContext context;
	private ProductAttrServiceImpl cacheManamger_pa;
	private ProductCatalogServiceImpl cacheManamger_pc;
/*	@Before
	public void test(){
		context = Context.getContext();
		System.out.println("------------查库启动成功-缓存-------------");
		cacheManamger_pa = (ProductAttrServiceImpl) context.getBean("productAttrServiceImpl");
		cacheManamger_pc = (ProductCatalogServiceImpl) context.getBean("productCatalogServiceImpl");
	}*/
	      
	/*private static final int JIUSHI = 90;
	private SearchService searchService;
    @Before
    public void test1() {
    	@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        context.start();
        searchService =  (SearchService) context.getBean("searchServiceImpl");
        context.getBean("jdbcTemplate");
    }
    
    	@Test
	public void cacheTest(){
		JedisManager.setString("test1", "testval1", JIUSHI);
		System.out.println(JedisManager.getString("test1"));
	}
	
	@Test
	public void ehcacheTest(){
		
		long l = System.currentTimeMillis();
		SearchRequest searchRequest = new SearchRequest();
				searchRequest.setKeyword("巧克力");
				searchRequest.setB2C(true);
				SearchResponse search =searchService.search(searchRequest);
				System.out.println("数量："+search.getHits());

		long l2 =System.currentTimeMillis();
		System.out.println("时间为：：："+(l2-l));
	}
	@Test
	public void ehcacheCatelogTest(){
		ProductCatalogServiceImpl pss = new ProductCatalogServiceImpl();
		pss.initCatalogCache();
	}*/
	
	@Test
	public void echache(){
		JedisManager.setObject("mall", 100000, "mall-");
		//JedisManager.delObject("mall");
		//System.out.println(JedisManager.getObject("mall"));

		
		//cacheManamger_pa.initProductAttrCache();
		//cacheManamger_pc.initCatalogCache();
		//SearchCateDispTree ST = (SearchCateDispTree)JedisManager.getObject("cate_044");
		//System.out.println(ST);
		
		/*SearchCateDispTree st = (SearchCateDispTree)(JedisManager.getObject("cate_044"));
		System.out.println(st);
		if(st!=null){
			System.out.println(st.getCateDispId()+" ---  "+st.getCateName());
		}*/
		
		//ProductAttrInnerCachewait pac = new ProductAttrInnerCachewait();
		//pac.startCaches();
	}
}
