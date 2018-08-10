package com.mall.search.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * Created by wangzhenyu on 16/4/12.
 */
public class ElasticSearchClientUtil {
    private static String INDEX_NAME="";
    private static String INDEX_NAME_ONE="";
    private static String INDEX_NAME_TWO="";
    public final static String INDEX_NOW="index_now";
    public final static String INDEX_NOW_ONE="index_now_one";
    public final static String INDEX_NOW_TWO="index_now_two";
    public final static String INDEX_TYPE="product";
    public final static String INDEX_TYPE_SUPPLIER="supplier";
    public final static String INDEX_TYPE_SUPPLIERATTR="supplierattr";
    private static TransportClient transportClient;
    static{
        try {
            transportClient= TransportClient.builder()
                    .settings(Settings.builder()
                            .put("client.transport.sniff", true)
                            .put("cluster.name", "ccigmall")
                            .put("client.transport.ignore_cluster_name", false))
                    .build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("elasticsearch01"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("elasticsearch02"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("elasticsearch03"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    public static TransportClient getTransportClient(){
        return transportClient;
    }
}
