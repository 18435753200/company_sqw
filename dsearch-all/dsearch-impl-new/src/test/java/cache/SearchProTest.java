package cache;

import com.mall.dsearch.vo.SearchRequest;
import com.mall.dsearch.vo.SearchResponse;
import com.mall.search.service.impl.SearchProductServiceImpl;

public class SearchProTest {

	   public static void main(String[] args) {
	        SearchRequest sr = new SearchRequest();
	        //sr.setKeyword("巧克力");
	        //sr.setPid("3205344118342971");
	        sr.setB2C(true);
	        //sr.setCyid("意大利");
	        //sr.setBrandName("Bally,Mepra");
	        sr.setCdid("044");
	        //sr.setPriceRange("0-50");
	        SearchProductServiceImpl searchProductService = new SearchProductServiceImpl();
	        SearchResponse searchResponse = searchProductService.searchProduct(sr);
	        searchResponse.getTerms();
	        System.out.println("aabbb");
	    }
}
