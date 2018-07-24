package com.mall.elasticsearchClent;

import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequestBuilder;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 
 */
public class ElasticSearchClientUtil {
    private static final Logger logger = Logger.getLogger(ElasticSearchClientUtil.class);
    
    private final static String INDEX_NOW = "index_now";
    private final static String INDEX_TYPE = "product";
    private static String INDEX_NAME = "";
    
    private final static String INDEX_NOW_ONE = "index_now_one";
    private final static String INDEX_TYPE_SUPPLIER = "supplier";
    private static String INDEX_NAME_ONE = "";
    
    private final static String INDEX_NOW_TWO = "index_now_two";
    private final static String INDEX_TYPE_SUPPLIERATTR = "supplierattr";
    private static String INDEX_NAME_TWO = "";
    
    private static TransportClient transportClient;

    static {
        try {
            transportClient = TransportClient.builder()
                    .settings(Settings.builder()
                            .put("client.transport.sniff", true)  // 自动把集群下的机器添加到列表中
                            .put("cluster.name", "ccigmall")  // 集群名
                            .put("client.transport.ignore_cluster_name", false))  //是否忽略集群名称验证连接的节点
                    .build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("elasticsearch01"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("elasticsearch02"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("elasticsearch03"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static TransportClient getTransportClient() {
        return transportClient;
    }

    public static boolean writeDocument2(List<String> list,List<String> supplierList) {
        long start = System.currentTimeMillis();
        //创建商品索引
        createNewIndex();
        //创建商家索引
        createNewSupplierIndex();
        //创建商家店铺详情页面索引
//        createNewSupplierAttrIndex();
        long end = System.currentTimeMillis();
        logger.info("createIndex:" + (end - start));
        start = System.currentTimeMillis();
        BulkProcessor bulkProcessor = BulkProcessor.builder(
                transportClient,
                new BulkProcessor.Listener() {
                    public void beforeBulk(long executionId,
                                           BulkRequest request) {

                    }

                    public void afterBulk(long executionId,
                                          BulkRequest request,
                                          BulkResponse response) {
                    }

                    public void afterBulk(long executionId,
                                          BulkRequest request,
                                          Throwable failure) {
                    }
                })
                .setBulkActions(1000)
                .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.MB))
                        //.setFlushInterval(TimeValue.timeValueSeconds(5))
                .setConcurrentRequests(4)
                .setBackoffPolicy(
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();
        for (String document : list) {
            bulkProcessor.add(new IndexRequest(INDEX_NAME, INDEX_TYPE).source(document));
        }
        for (String document : supplierList) {
        	bulkProcessor.add(new IndexRequest(INDEX_NAME_ONE, INDEX_TYPE_SUPPLIER).source(document));
        }
     /*   for (String document : attrList) {
        	bulkProcessor.add(new IndexRequest(INDEX_NAME_TWO, INDEX_TYPE_SUPPLIERATTR).source(document));
        }*/
        try {
            bulkProcessor.awaitClose(100L, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        end = System.currentTimeMillis();
        logger.info("buildIndex:" + (end - start));
        start = System.currentTimeMillis();
        tabIndex();
        tabSupplierIndex();
//        tabSupplierAttrIndex();
        end = System.currentTimeMillis();
        logger.info("tabIndex:" + (end - start));
        return true;
    }

    public static boolean writeDocument(List<String> list) {
        long start = System.currentTimeMillis();
        createNewIndex();
        long end = System.currentTimeMillis();
        logger.info("createIndex:" + (end - start));
        start = System.currentTimeMillis();
        BulkRequestBuilder bulkRequestBuilder = transportClient.prepareBulk();
        for (String document : list) {
            bulkRequestBuilder.add((transportClient.prepareIndex(INDEX_NAME, INDEX_TYPE).setSource(document)));
        }
        BulkResponse bulkResponse = bulkRequestBuilder.get();
        if (bulkResponse.hasFailures()) {
            logger.info(bulkResponse.buildFailureMessage());
            return false;
        }
        end = System.currentTimeMillis();
        logger.info("buildIndex:" + (end - start));
        start = System.currentTimeMillis();
        tabIndex();
        end = System.currentTimeMillis();
        logger.info("tabIndex:" + (end - start));
        return true;
    }

    //创建index 指定分片备份 mapping
    private static void createNewIndex() {
        INDEX_NAME = "product_" + System.currentTimeMillis();
        try {
            AdminClient adminClient = transportClient.admin();
            BufferedReader br = new BufferedReader(new InputStreamReader(ElasticSearchClientUtil.class.getResourceAsStream("/product.mapping")));
            String s = null;
            StringBuilder sb = new StringBuilder();
            while ((s = br.readLine()) != null)
                sb.append(s);
            adminClient.indices().prepareCreate(INDEX_NAME)
                    .setSettings(Settings.builder().put("index.number_of_shards", "1").put("index.number_of_replicas", "2"))
                    .addMapping(
                            "product", sb.toString()
                    )
                    .get();
        } catch (Exception e) {
            logger.error(e);
        }
    }
    
    private static void createNewSupplierIndex() {
    	INDEX_NAME_ONE = "supplier" + System.currentTimeMillis();
    	try {
    		AdminClient adminClient = transportClient.admin();
    		BufferedReader br = new BufferedReader(new InputStreamReader(ElasticSearchClientUtil.class.getResourceAsStream("/supplier.mapping")));
    		String s = null;
    		StringBuilder sb = new StringBuilder();
    		while ((s = br.readLine()) != null)
    			sb.append(s);
    		adminClient.indices().prepareCreate(INDEX_NAME_ONE)
    		.setSettings(Settings.builder().put("index.number_of_shards", "1").put("index.number_of_replicas", "2"))
    		.addMapping(
    				"supplier", sb.toString()
    				)
    		.get();
    	} catch (Exception e) {
    		logger.error(e);
    	}
    }
    
    private static void createNewSupplierAttrIndex() {
    	INDEX_NAME_TWO = "supplierattr" + System.currentTimeMillis();
    	try {
    		AdminClient adminClient = transportClient.admin();
    		BufferedReader br = new BufferedReader(new InputStreamReader(ElasticSearchClientUtil.class.getResourceAsStream("/supplierAttr.mapping")));
    		String s = null;
    		StringBuilder sb = new StringBuilder();
    		while ((s = br.readLine()) != null){
    			sb.append(s);
    		}
    		adminClient.indices().prepareCreate(INDEX_NAME_TWO)
    		.setSettings(Settings.builder().put("index.number_of_shards", "1").put("index.number_of_replicas", "2"))
    		.addMapping(
    				"supplierattr", sb.toString()
    				)
    		.get();
    	} catch (Exception e) {
    		logger.error(e);
    	}
    }

    //写完索引后 更改别名，指向新的
    private static void tabIndex() {
        AdminClient adminClient = transportClient.admin();
        IndicesAliasesRequestBuilder aliasBuilder = adminClient.indices().prepareAliases();
        try {
        	//切换商品索引别名
            if (adminClient.indices().aliasesExist(new GetAliasesRequest(INDEX_NOW)).get().exists() == true) {
                ImmutableOpenMap<String, List<AliasMetaData>> aliases = null;
                aliases = adminClient.indices().getAliases(new GetAliasesRequest(INDEX_NOW)).get().getAliases();
                String[] keys = aliases.keys().toArray(String.class);
                aliasBuilder.addAlias(INDEX_NAME, INDEX_NOW).removeAlias(keys, INDEX_NOW).get();
                for (String key : keys) {
                    adminClient.indices().prepareDelete(key).get();
                }
            } else {
                aliasBuilder.addAlias(INDEX_NAME, INDEX_NOW).get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    
    //写完索引后 更改别名，指向新的
    private static void tabSupplierIndex() {
        AdminClient adminClient = transportClient.admin();
        IndicesAliasesRequestBuilder aliasBuilder = adminClient.indices().prepareAliases();
        try {
            //切换商家信息索引别名
            if (adminClient.indices().aliasesExist(new GetAliasesRequest(INDEX_NOW_ONE)).get().exists() == true) {
    			ImmutableOpenMap<String, List<AliasMetaData>> aliases = null;
    			aliases = adminClient.indices().getAliases(new GetAliasesRequest(INDEX_NOW_ONE)).get().getAliases();
    			String[] keys = aliases.keys().toArray(String.class);
    			aliasBuilder.addAlias(INDEX_NAME_ONE, INDEX_NOW_ONE).removeAlias(keys, INDEX_NOW_ONE).get();
    			for (String key : keys) {
    				adminClient.indices().prepareDelete(key).get();
    			}
    		} else {
    			aliasBuilder.addAlias(INDEX_NAME_ONE, INDEX_NOW_ONE).get();
    		}
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    
    //写完索引后 更改别名，指向新的
    private static void tabSupplierAttrIndex() {
        AdminClient adminClient = transportClient.admin();
        IndicesAliasesRequestBuilder aliasBuilder = adminClient.indices().prepareAliases();
        try {
            //切换商家信息索引别名
            if (adminClient.indices().aliasesExist(new GetAliasesRequest(INDEX_NOW_TWO)).get().exists() == true) {
    			ImmutableOpenMap<String, List<AliasMetaData>> aliases = null;
    			aliases = adminClient.indices().getAliases(new GetAliasesRequest(INDEX_NOW_TWO)).get().getAliases();
    			String[] keys = aliases.keys().toArray(String.class);
    			aliasBuilder.addAlias(INDEX_NAME_TWO, INDEX_NOW_TWO).removeAlias(keys, INDEX_NOW_TWO).get();
    			for (String key : keys) {
    				adminClient.indices().prepareDelete(key).get();
    			}
    		} else {
    			aliasBuilder.addAlias(INDEX_NAME_TWO, INDEX_NOW_TWO).get();
    		}
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
