package com.mall.search.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

//import com.mall.dsearch.cache.CacheFactory;
//import com.mall.dsearch.cache.CacheOper;
//import com.mall.dsearch.cache.ProductAttrInnerCache;

import com.mall.dsearch.vo.AttrTerm;
import com.mall.dsearch.vo.CatalogAttr;
import com.mall.dsearch.vo.CatalogAttrVal;
import com.mall.dsearch.vo.SearchCateAttrValCode;
import com.mall.dsearch.vo.SearchRequest;
import com.mall.search.utils.StringUtils;
/**
 * facet处理类
 * @author zzw
 *
 */
public class AttrFacetParse {
	private static final double FIFTY = 50;
	private static final double HUNDRED = 100;
	private static final double HUNDRED_FIFTY = 150;
	private static final String PRICE_NAME = "价格";
	private static final String INVENTORY_NAME ="库存";
	private static final String B2CSUPPLY_NAME ="货源";
	
	/**
	 * 未选择的属性矩阵
	 */
	private LinkedHashMap<CatalogAttr, LinkedHashSet<CatalogAttrVal>> matrix = new LinkedHashMap<CatalogAttr, LinkedHashSet<CatalogAttrVal>>();
	/**
	 * 已选择的属性列表
	 */
	private LinkedList<AttrTerm> terms = new LinkedList<AttrTerm>();
	
	private Map<String, Map<String, Integer>> facetFieldMap;
	private Map<String, Map<String, Integer>> rangeFacetMap;
	private Map<String, Map<String, Integer>> inventoryMap;
	private Map<String, Map<String, Integer>> b2csupplyMap;
	private Map<String, Map<String, Integer>> promotionMap;
	private Map<String, String> productCountrys;
	private Set<String> brandsFacet;
	private SearchRequest searchRequest;
	
	//private ProductAttrInnerCache productAttrInnerCache;
	
	public AttrFacetParse(Map<String, Map<String, Integer>> facetFieldMap,Map<String, Map<String, Integer>> rangeFacetMap,
			Map<String, Map<String, Integer>> b2csupplyMap, Map<String, Map<String, Integer>> inventoryMap, Map<String, String> productCountrys,
			Set<String> brandsFacet,
			//ProductAttrInnerCache productAttrInnerCache,
			SearchRequest searchRequest, Map<String, Map<String, Integer>> promotionMap){
		this.facetFieldMap = facetFieldMap;
		this.rangeFacetMap = rangeFacetMap;
		this.inventoryMap = inventoryMap;
		this.b2csupplyMap = b2csupplyMap;
		this.productCountrys = productCountrys;
		this.brandsFacet = brandsFacet;
		this.searchRequest = searchRequest;
		//this.productAttrInnerCache = productAttrInnerCache;
		this.promotionMap = promotionMap;
	}
	
	public void createFacet(){
		//long s = System.currentTimeMillis();
		//如果只是关键字搜索的话要展示全部
		if(StringUtils.isNotEmpty(searchRequest.getKeyword())&&StringUtils.isEmpty(searchRequest.getCdid())){
			//createCatalogAttr();
			//要求一级二级只显示价格，品牌和原产国，三级要全部都显示
		}else if(StringUtils.isNotEmpty(searchRequest.getCdid())&&searchRequest.getCdid().contains("-")){
			String[] split = searchRequest.getCdid().split("-");
			if(split.length>2){
				//createCatalogAttr();
			}
		}
		//过滤出已经选择的属性值，同时把已经选择的属性删除
		doFilter();
		//重新生成属性id
		recreateMatrixId();
		createPriceRangeAttr();
		createBrands();
		createCountrys();
		createInventory();
		createB2csupplyMap();
		creatPromotionMap();
		doSort();
	}
	
	private void creatPromotionMap() {
		// TODO Auto-generated method stub
		
	}

	private void createB2csupplyMap() {
		if(b2csupplyMap != null&&b2csupplyMap.size()>0){
			Map<String, Integer> b2csupplys = b2csupplyMap.get("b2csupply");
			if(b2csupplys != null && b2csupplys.size() > 0){
				Set<CatalogAttrVal> attrVals = new HashSet<CatalogAttrVal>();
				CatalogAttr attr = new CatalogAttr();
				attr.setName(B2CSUPPLY_NAME);
				attr.setSortval(0);
				attr.setB2csupply(true);
				for(String key:b2csupplys.keySet()) {
					Integer valueOf = Integer.valueOf(key);
					CatalogAttrVal attrVal = createSuppliyAttrVal(valueOf);// 创建货源属性值
					attrVals.add(attrVal);
				}
				
				String supply = searchRequest.getB2csupply();
				//价格区间，选择价格
				boolean supplySelected = false;
				if(!StringUtils.isEmpty(supply)) {
					LinkedList<AttrTerm> attrTems = new LinkedList<AttrTerm>();
					AttrTerm attrT = null;
					for(CatalogAttrVal attrVal:attrVals) {
						if(supply.contains(",")){
							String[] supplys = supply.split(",");
							for(int i = 0; i <supplys.length; i++){
								if(supplys[i].equalsIgnoreCase(attrVal.getId())){
									attrT = createAttrTerm(attrVal, attr);
									attrTems.add(attrT);
									supplySelected=true;
								}
							}
						}else{
							if(supply.equalsIgnoreCase(attrVal.getId())) {
								attrT = createAttrTerm(attrVal, attr);
								attrTems.add(attrT);
								supplySelected=true;
							}
						}
					}
					buildAttrTerm(attrTems);
				}
			if(!supplySelected&&attr != null) {
				matrix.put(attr, new LinkedHashSet<CatalogAttrVal>(attrVals));////matrix
			}
		}
		}
		
	}

	private CatalogAttrVal createSuppliyAttrVal(Integer valueOf) {
		CatalogAttrVal attrVal = new CatalogAttrVal();
		attrVal.setSortval(valueOf);
		if(1 == valueOf){
			attrVal.setName("国内发货");
			attrVal.setId("1");
		}else if(11 == valueOf){
			attrVal.setName("海外直邮");
			attrVal.setId("11");
		}else if(12 == valueOf){
			attrVal.setName("保税区发货");
			attrVal.setId("12");
		}else if(21 == valueOf){
			attrVal.setName("韩国直邮");
			attrVal.setId("21");
		}else if(51 == valueOf){
			attrVal.setName("国际发货");
			attrVal.setId("51");
		}
		return attrVal;
	}

	private void createInventory() {
		if(inventoryMap != null&&inventoryMap.size()>0){
			Map<String, Integer> inventory = inventoryMap.get("inventory");
			if(inventory != null && inventory.size() > 0){
				Set<CatalogAttrVal> attrVals = new HashSet<CatalogAttrVal>();
				CatalogAttr attr = new CatalogAttr();
				attr.setName(INVENTORY_NAME);
				attr.setSortval(-1);
				attr.setInventory(true);
				for(String key:inventory.keySet()) {
					Integer valueOf = Integer.valueOf(key);
					CatalogAttrVal attrVal = createInventoryAttrVal(valueOf);// 创建库存属性值
					attrVals.add(attrVal);
				}
				
				String isInventory = searchRequest.getIsInventory();
				//价格区间，选择价格
				boolean inventorySelected = false;
				if(!StringUtils.isEmpty(isInventory)) {
					for(CatalogAttrVal attrVal:attrVals) {
						inventorySelected = true;
						AttrTerm term = new AttrTerm();
						term.setId(attrVal.getId());
						term.setAttrValName(attrVal.getName());
						term.setAttrName(attr.getName());
						term.setInventory(true);
						terms.addFirst(term);//////terms放入了两个相同的价格
					}//end for
				}
			
			if(!inventorySelected && attr != null) {
				matrix.put(attr, new LinkedHashSet<CatalogAttrVal>(attrVals));////matrix
			}
		}
		
	}
	}

	private CatalogAttrVal createInventoryAttrVal(Integer valueOf) {
		CatalogAttrVal attrVal = new CatalogAttrVal();
		attrVal.setSortval(valueOf);
		if(valueOf>0) {
			attrVal.setName("有库存商品");
			attrVal.setId("true");
		}
		return attrVal;
	}

	private void recreateMatrixId() {
		String attrcodes = searchRequest.getAttrcodes();
		if(StringUtils.isEmpty(attrcodes)) return;
		for(CatalogAttr attr:matrix.keySet()) {
			LinkedHashSet<CatalogAttrVal> attrVals = matrix.get(attr);////matrix
			if(attr.isPriceRange()&&attr.isBrandName()) {
				continue;
			}
			//判断查询条件中是否含有任一本级属性值id
			String brother = null;
			for(CatalogAttrVal attrVal:attrVals) {
				if(attrcodes.contains(attrVal.getId())) {//通过模糊属性属性值ID匹配，所以属性属性值ID的位数必须相同
					brother = attrVal.getId();
				}
			}
			for(CatalogAttrVal attrVal:attrVals) {
				
				if(brother == null) {
					//本级属性值id不在查询条件中
					attrVal.setId(attrVal.getId()+"-"+attrcodes);
				}else{
					//本级属性值id已经在查询条件中了
					String params = filterBrother(brother, attrcodes);//从条件中过渡掉兄弟属性值id
					if(brother.equals(attrVal.getId())) {
						attrVal.setId(params);
					}else{
						attrVal.setId(attrVal.getId()+"-"+params);
					}
					
				}
			}
		}
		
	}

	private void doFilter() {
		String attrcodes = searchRequest.getAttrcodes();
		List<AttrTerm[]> attr2 = new ArrayList<AttrTerm[]>();
		if(!StringUtils.isEmpty(attrcodes)) {
			String[] attrcodesArray  = attrcodes.split("-");
			AttrTerm[] attrTermArray =new AttrTerm[attrcodesArray.length];
			List<AttrTerm[]> attr1=new ArrayList<AttrTerm[]>();
			for(String attrCode:attrcodesArray){
				if(attrCode.contains(",")){
					String[] split = attrCode.split(",");
					AttrTerm[] attrTermA = new AttrTerm[split.length];
					boolean flag = false;
					Iterator<Entry<CatalogAttr, LinkedHashSet<CatalogAttrVal>>> iterator = matrix.entrySet().iterator();////matrix
					while(iterator.hasNext()) {
						Entry<CatalogAttr, LinkedHashSet<CatalogAttrVal>> entry = iterator.next();
							if(contains2(split, entry.getKey(), entry.getValue(), attrTermA)) {//属性值是否被选择
								iterator.remove();
								flag=true;
								break;
							}
					}
					if(flag==true){
					attr1.add(attrTermA);
					}
				}else{
					Iterator<Entry<CatalogAttr, LinkedHashSet<CatalogAttrVal>>> iterator = matrix.entrySet().iterator();////matrix
					while(iterator.hasNext()) {
						Entry<CatalogAttr, LinkedHashSet<CatalogAttrVal>> entry = iterator.next();
						for(CatalogAttrVal attrVal:entry.getValue()) {
							if(contains(attrcodesArray, entry.getKey(), attrVal, attrTermArray)) {//属性值是否被选择
								iterator.remove();
								break;
							}
						}
					}
				}
			}
			attr2.add(attrTermArray);
				ArrayList<AttrTerm> alist=new ArrayList<AttrTerm>();
				if(attr1.size()>0&&attr1!=null){
					for (AttrTerm[] a1:attr1) {
						if(a1!=null){
							StringBuffer sb  = new StringBuffer();
							AttrTerm attr =new AttrTerm();
							String name =null;
							String id = null;
							for(AttrTerm aa1:a1){
								if(aa1!=null){
									name = aa1.getAttrName();
									id = aa1.getId();
									sb.append(aa1.getAttrValName()+",");
								}
							}
							attr.setAttrName(name);
							attr.setAttrValName(sb.toString().substring(0,sb.length()-1));
							attr.setId(id);
							alist.add(attr);
						}
					}
				}
				if(attr2.size()>0&&attr2!=null){
					for (AttrTerm[] a2:attr2) {
						if(a2!=null){
							for(AttrTerm aa2:a2){
								if(aa2!=null){
									alist.add(aa2);
								}
							}
						}
					}
				}
				for(AttrTerm attrTerm:alist) {
					if(attrTerm != null && !attrTerm.isPrice()&&!attrTerm.isBrandName()) {
						attrTerm.setId(filterBrother(attrTerm.getId(), attrcodes));//从条件中过渡掉兄弟属性值id
						terms.addFirst(attrTerm);//////terms
					}
				}
		}
		
	}

	/**
	 * 属性值是否被选择
	 * @param attrcodesArray
	 * @param attr
	 * @param attrVal
	 * @param attrTerms
	 * @return
	 */
	private boolean contains(String[] attrcodesArray, CatalogAttr attr, CatalogAttrVal attrVal, AttrTerm[] attrTerms) {
		boolean contains = false;
		for(int i=0;i<attrcodesArray.length;i++) {
			if(attrcodesArray[i].equalsIgnoreCase(attrVal.getId())) {
				contains = true;
				AttrTerm term = new AttrTerm();
				term.setId(attrVal.getId());
				term.setAttrName(attr.getName());
				term.setAttrValName(attrVal.getName());
				attrTerms[i] = term;
			}
		}
		return contains;
	}
	private boolean contains2(String[] attrcodesArray, CatalogAttr key,LinkedHashSet<CatalogAttrVal> linkedHashSet, AttrTerm[] attrTermA) {
		boolean contains = false;
		for(CatalogAttrVal attrVal:linkedHashSet){
			for(int i=0;i<attrcodesArray.length;i++) {

				if(attrcodesArray[i].equalsIgnoreCase(attrVal.getId())) {
					contains = true;
					AttrTerm term = new AttrTerm();
					term.setId(attrVal.getId());
					term.setAttrName(key.getName());
					term.setAttrValName(attrVal.getName());
					attrTermA[i] = term;
				}
			}
		}
		return contains;
	}
	/**
	 * 从条件中过渡掉兄弟属性值id
	 * @param brother
	 * @param attrcodes
	 * @return
	 */
	private String filterBrother(String brother, String attrcodes) {
		StringBuffer buffer = new StringBuffer();
		String[] acArray = attrcodes.split("-");
		if(acArray.length==1&&acArray[0].contains(",")){
			acArray[0]=null;
			buffer.append("");
		}else{
			for(int i=0;i<acArray.length;i++) {
				if(acArray[i].contains(",")){
					String[] contains = acArray[i].split(",");
					for(int x=0;x<contains.length;x++){
						if(contains[x].equalsIgnoreCase(brother)) {
							acArray[i]=null;
						}
						if(acArray[i]== null){
							continue;
						}else{
								if(contains[x] != null) {
									buffer.append(contains[x]+",");
								}
						}
					}
					if(!StringUtils.isEmpty(buffer.toString())){
						if(buffer.toString().endsWith(",")){
							buffer.replace(buffer.length()-1, buffer.length(), "-");
						}
					}
				}else{
					if(acArray[i].equalsIgnoreCase(brother)) {
						acArray[i] = null;
					}else{
						buffer.append(acArray[i]+"-");
					}
					if(acArray[i] ==null){
						continue;
					}
				}
			}
		}


		if(buffer.length() > 0) {
			buffer.deleteCharAt(buffer.length()-1);
		}
		return buffer.toString();
	}
/*	private void createCatalogAttr(){
		if(facetFieldMap != null){
			Map<String, Integer> dispatFacet = facetFieldMap.get("dispat");
			CacheOper cacheOper = CacheFactory.getCacheOper("prod_attrcode_");
			if(dispatFacet != null && dispatFacet.size() > 0){
				Iterator<Entry<String, Integer>> iterator = dispatFacet.entrySet().iterator();
				while(iterator.hasNext()){
					Entry<String, Integer> entry = iterator.next();
					String dispats = entry.getKey();
					Integer count = entry.getValue();
					if(dispats != null && !dispats.equals("")){
						String[] dispatArr = dispats.split(" ");
						if(dispatArr != null && dispatArr.length > 0){
							int len = dispatArr.length;
							for (int i = 0; i < len; i++) {
								String dispat = dispatArr[i];
								buildFacetAttr(dispat, cacheOper, count);
							}//end for
						}
					}//end if
				}//end while
			}
		}
	}*/
	
	private void createPriceRangeAttr(){
		Map<String, Integer> ranges = null;
		if(rangeFacetMap != null&&rangeFacetMap.size()>0){
			//区分B2B与B2C的价格区间
			if(searchRequest.isB2C()){
				ranges =rangeFacetMap.get("unit_price");
			}else{
				ranges =rangeFacetMap.get("msp");
			}
			if(ranges != null && ranges.size() > 0){
				Set<CatalogAttrVal> attrVals = new HashSet<CatalogAttrVal>();
				
				CatalogAttr attr = new CatalogAttr();
				attr.setName(PRICE_NAME);
				attr.setSortval(-3);
				attr.setPriceRange(true);
				for(String key:ranges.keySet()) {
					Double price = new Double(key);
					CatalogAttrVal attrVal = createPriceAttrVal(price);// 创建价格属性值
					attrVals.add(attrVal);
				}
				
				String priceRange = searchRequest.getPriceRange();
				//价格区间，选择价格
				boolean priceSelected = false;
				if(!StringUtils.isEmpty(priceRange)) {//         价格这里有问题
					for(CatalogAttrVal attrVal:attrVals) {
						if(priceRange.equalsIgnoreCase(attrVal.getId())) {
							priceSelected = true;
							AttrTerm term = new AttrTerm();
							term.setId(attrVal.getId());
							term.setAttrValName(attrVal.getName());
							term.setAttrName(attr.getName());
							term.setPrice(true);
							terms.addFirst(term);//////terms放入了两个相同的价格
						}
					}//end for
				}//end if
				
				//手动输入价格
				boolean priceInputed = false;
				if(!StringUtils.isEmpty(priceRange) && !priceSelected && priceRange.matches("\\d*\\-\\d*")) {
					priceInputed = true;
					AttrTerm term = new AttrTerm();
					term.setId(priceRange);
					term.setAttrValName(priceRange);
					if(priceRange.matches("\\d+\\-")){
						term.setAttrValName(priceRange.substring(0,priceRange.lastIndexOf("-"))+"以上");
					}else if(priceRange.matches("\\-\\d+")){
						term.setAttrValName("0"+priceRange);
					}
					term.setAttrName("价格");
					term.setPrice(true);
					terms.addFirst(term);//////terms
				}
				if(!priceInputed && !priceSelected && attr != null) {
					matrix.put(attr, new LinkedHashSet<CatalogAttrVal>(attrVals));////matrix
				}
				
			}
			
		}
	}
	
	private void createBrands(){
		if(brandsFacet != null && brandsFacet.size() > 0){
			CatalogAttr attr = new CatalogAttr();
			List<CatalogAttrVal> attrVals = new ArrayList<CatalogAttrVal>();
			attr.setName("品牌");
			attr.setSortval(-4);
			attr.setBrandName(true);
			for(String brand:brandsFacet){
				CatalogAttrVal attrVal = new CatalogAttrVal();
				
				if(brand.length()>3){
					if("zzz".equals(brand.substring(brand.length()-3, brand.length()))){
						attrVal.setId(brand.substring(0, brand.length()-2));
						attrVal.setName(brand.substring(0, brand.length()-3));
					}else{
						attrVal.setId(brand);
						attrVal.setName(brand);
					}
				}else{
					attrVal.setId(brand);
					attrVal.setName(brand);
				}
				attrVals.add(attrVal);
			}
			
			String brandNames = searchRequest.getBrandName();
			boolean brandNameSelected = false;
			if(!StringUtils.isEmpty(brandNames)) {
				LinkedList<AttrTerm> attrTems = new LinkedList<AttrTerm>();
				AttrTerm attrT = null;
				for(CatalogAttrVal attrVal:attrVals) {
					if(brandNames.contains(",")){
						String[] brandNameArr = brandNames.split(",");
						for(int i = 0; i <brandNameArr.length; i++){
							if(brandNameArr[i].equalsIgnoreCase(attrVal.getId())){
								attrT = createAttrTerm(attrVal, attr);
								attrTems.add(attrT);
								brandNameSelected=true;
							}
						}
					}else{
						if(brandNames.equalsIgnoreCase(attrVal.getId())) {
							attrT = createAttrTerm(attrVal, attr);
							attrTems.add(attrT);
							brandNameSelected=true;
						}
					}
				}
				buildAttrTerm(attrTems);
			}
			if(!brandNameSelected&&attr != null) {
				matrix.put(attr, new LinkedHashSet<CatalogAttrVal>(attrVals));////matrix
			}
		}
	}
	private AttrTerm createAttrTerm(CatalogAttrVal attrVal, CatalogAttr attr){
		AttrTerm term = new AttrTerm();
		term.setId(attrVal.getId());
		term.setAttrValName(attrVal.getName());
		String name = attr.getName();
		term.setAttrName(name);
		if("原产国".equals(name)){
			term.setCyid(true);
		}else if(B2CSUPPLY_NAME.equals(name)){
			term.setB2csupply(true);
		}else{
			term.setBrandName(true);
		}
		return term;
	}
	
	private void createCountrys(){
		if(productCountrys != null && productCountrys.size() > 0){
			List<CatalogAttrVal> attrVals = new ArrayList<CatalogAttrVal>();
			CatalogAttr attr = new CatalogAttr();
			attr.setName("原产国");
			attr.setSortval(-2);
			attr.setCyid(true);
			for (Map.Entry<String, String> entry : productCountrys.entrySet()) {
				CatalogAttrVal attrVal = new CatalogAttrVal();// 创建源产国属性值
			    attrVal.setId(entry.getKey());
				attrVal.setName(entry.getKey());
				attrVal.setImgUrl(entry.getValue());
				attrVals.add(attrVal);
			}
			
			boolean cyidSelected = false;
			String cyid = searchRequest.getCyid();
			if(!StringUtils.isEmpty(cyid)) {
				LinkedList<AttrTerm> attrTems = new LinkedList<AttrTerm>();
				AttrTerm attrT = null;
				for(CatalogAttrVal attrVal:attrVals) {
					if(cyid.contains(",")){
						String[] cityArr = cyid.split(",");
						for(int i = 0; i < cityArr.length; i ++){
							if(attrVal.getId().equalsIgnoreCase(cityArr[i])){
								attrT = createAttrTerm(attrVal, attr);
								attrTems.addFirst(attrT);
								cyidSelected = true;
							}
						}
					}
					else{
						if(attrVal.getId().equalsIgnoreCase(cyid)){
							attrT = createAttrTerm(attrVal, attr);
							attrTems.addFirst(attrT);
							cyidSelected = true;
						}
					}
					
				}
				//多选返回处理
				buildAttrTerm(attrTems);
			}
			
			if(!cyidSelected && attr != null) {
				matrix.put(attr, new LinkedHashSet<CatalogAttrVal>(attrVals));////matrix
			}
		}
	}
	/**
	 * 品牌，原产国，货源多选返回处理
	 * @param attrTems
	 */
	private void buildAttrTerm(LinkedList<AttrTerm> attrTems) {
		StringBuffer sb = new StringBuffer();
		StringBuffer sbId = new StringBuffer();
		//String id =null;
		String attrName = null;
		boolean brandName = false;
		boolean cyid = false;
		boolean price = false;
		boolean inventory = false;
		boolean b2csupply = false;
		for (AttrTerm term : attrTems) {
			attrName = term.getAttrName();
			brandName = term.isBrandName();
			cyid = term.isCyid();
			price = term.isPrice();
			inventory= term.isInventory();
			b2csupply = term.isB2csupply();
			//id = term.getId();
			sbId.append(term.getId()+",");
			sb.append(term.getAttrValName()+",");
		}
		AttrTerm te = new AttrTerm();
		String id=null;
		String name=null;
		if(sbId.toString()!=null&&!"".equals(sbId.toString())){
		id =sbId.toString().substring(0, sbId.length()-1);
		}
		te.setId(id);
		if(sb.toString()!=null&&!"".equals(sb.toString())){
		name = sb.toString().substring(0, sb.length()-1);
		}
		te.setAttrValName(name);
		te.setAttrName(attrName);
		te.setBrandName(brandName);
		te.setCyid(cyid);
		te.setPrice(price);
		te.setInventory(inventory);
		te.setB2csupply(b2csupply);
		terms.addFirst(te);
		
	}
/*	private void buildFacetAttr(String dispat, CacheOper cacheOper, Integer count){
		if(dispat !=null && !dispat.equals("")){
			//Object object = cacheOper.get(dispat);
			//if (object != null) {
				SearchCateAttrValCode searchAttr = productAttrInnerCache.getProductAttrCache(dispat);//(SearchCateAttrValCode) gson.fromJson(object.toString(), SearchCateAttrValCode.class);
				if(searchAttr != null){
					docreate(searchAttr, count);
				}
				
				}
				
			//}
		}//end if
*/	
	private CatalogAttr docreate(SearchCateAttrValCode searchAttr, Integer count) {
		CatalogAttr attr = new CatalogAttr();
		attr.setName(searchAttr.getAttrName());
		attr.setSortval(searchAttr.getAttrSortval());
		LinkedHashSet<CatalogAttrVal> attrVals = matrix.get(attr);
		if(attrVals == null) {
			attrVals = new LinkedHashSet<CatalogAttrVal>();
			matrix.put(attr, attrVals);////matrix
		}
		CatalogAttrVal attrVal = createAttrVal(searchAttr, count);//创建CatalogAttrVal
		attrVals.add(attrVal);
		return attr;
		
	}

	private CatalogAttrVal createAttrVal(SearchCateAttrValCode searchAttr,
			Integer count) {
		CatalogAttrVal attrVal = new CatalogAttrVal();
		attrVal.setId(searchAttr.getAttrValCodeId().toString());
		attrVal.setName(searchAttr.getAttrvalName());
		attrVal.setSortval(searchAttr.getAttrvalSortval());
		attrVal.setCount(count);
		
		return attrVal;
	}
	/**
	 * 创建价格属性值
	 * @param price
	 * @return
	 */
	private CatalogAttrVal createPriceAttrVal(Double price) {
		CatalogAttrVal attrVal = new CatalogAttrVal();
		attrVal.setSortval(price.intValue());
		if(price >= 0.0 && price < FIFTY) {
			attrVal.setName("0-50");
			attrVal.setId("0-50");
		}else if(price >= FIFTY && price<HUNDRED) {
			attrVal.setName("50-100");
			attrVal.setId("50-100");
		}else if(price >= HUNDRED && price < 200) {
			attrVal.setName("100-200");
			attrVal.setId("100-200");
		}else if(price >= 200 && price < 300) {
			attrVal.setName("200-300");
			attrVal.setId("200-300");
		}else if(price >= 300 && price < 500) {
			attrVal.setName("300-500");
			attrVal.setId("300-500");
		}else if(price >= 500 && price < 800) {
			attrVal.setName("500-800");
			attrVal.setId("500-800");
		}else if(price >= 800) {
			attrVal.setName("800以上");
			attrVal.setId("800-");
		}
		return attrVal;
	}
	
	/**
	 * 类目属性、属性值排序
	 * @param facetFieldMap
	 * @return
	 */
	private void doSort() {
		//long s = System.currentTimeMillis();
		LinkedList<Entry<CatalogAttr, LinkedHashSet<CatalogAttrVal>>> list = 
				new LinkedList<Entry<CatalogAttr,LinkedHashSet<CatalogAttrVal>>>(matrix.entrySet());
		//对属性名排序
		Collections.sort(list, new Comparator<Entry<CatalogAttr, LinkedHashSet<CatalogAttrVal>>>() {
			public int compare(Entry<CatalogAttr, LinkedHashSet<CatalogAttrVal>> o1,
					Entry<CatalogAttr, LinkedHashSet<CatalogAttrVal>> o2) {
				CatalogAttr attr1 = o1.getKey();
				CatalogAttr attr2 = o2.getKey();
				if(attr1.getSortval() == attr2.getSortval()) {
					return attr1.getName().hashCode() - attr2.getName().hashCode();
				}
				return attr1.getSortval() - attr2.getSortval();
			}
		});
		
		LinkedHashMap<CatalogAttr, LinkedHashSet<CatalogAttrVal>> newattrCodes = new LinkedHashMap<CatalogAttr, LinkedHashSet<CatalogAttrVal>>();
		Iterator<Entry<CatalogAttr, LinkedHashSet<CatalogAttrVal>>> iterator = list.iterator();
		while(iterator.hasNext()) {
			Entry<CatalogAttr, LinkedHashSet<CatalogAttrVal>> entry = iterator.next();
			LinkedList<CatalogAttrVal> attrVals = new LinkedList<CatalogAttrVal>(entry.getValue());
			//对属性值排序
			Collections.sort(attrVals, new Comparator<CatalogAttrVal>(){
				public int compare(CatalogAttrVal o1, CatalogAttrVal o2) {
					if(o1.getSortval() == o2.getSortval()) {
						return o1.getName().hashCode() - o2.getName().hashCode();
					}
					return o1.getSortval() - o2.getSortval();
				}
			});
			
			LinkedHashSet<CatalogAttrVal> hashSet = new LinkedHashSet<CatalogAttrVal>();
			for(CatalogAttrVal attrVal:attrVals) {
				hashSet.add(attrVal);
			}
			entry.setValue(hashSet);
			newattrCodes.put(entry.getKey(), hashSet);
		}
		matrix = newattrCodes;////matrix
		//long e = System.currentTimeMillis();
		//System.err.println("属性排序："+(e-s)+"毫秒");
	}
	
	public LinkedHashMap<CatalogAttr, LinkedHashSet<CatalogAttrVal>> getMatrix() {
		return matrix;
	}

	public void setMatrix(
			LinkedHashMap<CatalogAttr, LinkedHashSet<CatalogAttrVal>> matrix) {
		this.matrix = matrix;
	}

	public LinkedList<AttrTerm> getTerms() {
		return terms;
	}

	public void setTerms(LinkedList<AttrTerm> terms) {
		this.terms = terms;
	}
}
