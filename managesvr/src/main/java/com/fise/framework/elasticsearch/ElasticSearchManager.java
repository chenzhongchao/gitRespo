package com.fise.framework.elasticsearch;

import java.net.InetSocketAddress;
import java.text.Normalizer.Form;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.ibatis.annotations.Param;
import org.apache.lucene.queryparser.xml.FilterBuilder;
import org.apache.lucene.search.Sort;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.index.query.GeoDistanceRangeQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.bucket.range.geodistance.GeoDistanceBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;

import com.fise.base.Page;
import com.fise.framework.config.ConfigProperties;
import com.fise.model.entity.Gym;
import com.qq.jutil.j4log.Logger;

public class ElasticSearchManager {
	private volatile static ElasticSearchManager instance;
	
	private TransportClient client;
	private String clusterName;
	private String clusterNodes;
	
	public static ElasticSearchManager getInstance() {
		if (instance == null) {
			synchronized (ElasticSearchManager.class) {
				if (instance == null) {
					instance = new ElasticSearchManager();
				}
			}
		}
		return instance;
	}
	
	private ElasticSearchManager() {
		clusterName = ConfigProperties.getValue("es_cluster_name");
		clusterNodes = ConfigProperties.getValue("es_cluster_nodes");

		buildClient();
	}
	
	private void buildClient() {
		Settings settings = Settings.settingsBuilder()
				.put("cluster.name", clusterName)
				.put("client.transport.sniff", true)
				.put("client.transport.ping_timeout", "5s")
				.build();
		
		client = TransportClient.builder().settings(settings).build();
		
		String[] esNodes = clusterNodes.split(",");
		for(String node : esNodes) {
			String address[] = node.split(":");
			client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(address[0], Integer.parseInt(address[1]))));
		}
	}
	
	// 关闭连接
	public void shutDown() {
		if (client != null) {
			client.close();
		}
	}
	
	// 增
	public void createDocument(String index, String type, String id, String json) {
		client.prepareIndex(index, type, id).setSource(json).execute().actionGet();
	}
	
	// 删
	public void deleteDocument(String index, String type, String id) {
		client.prepareDelete(index, type, id).execute().actionGet();
	}
	
	// 改
	public void updateDocument(String index, String type, String id, String json) {
		UpdateRequest updateRequest = new UpdateRequest(index, type, id).doc(json);
		try {
			client.update(updateRequest).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			throw new RuntimeException("es update document exception: " + e.getMessage());
		}
	}
	
	// 查
	public SearchHits queryGymNearby(String index, String type, Page<Gym> pageParam) {		
		Logger logger  = Logger.getLogger("es_manager");
		
		Double longitude = Double.parseDouble(pageParam.getExtraParam().get("longitude") + "");
		Double latitude = Double.parseDouble(pageParam.getExtraParam().get("latitude") + "");
		Double distance = Double.parseDouble(pageParam.getExtraParam().get("distance") + "");
		Integer pageNo = pageParam.getPageNo();
		Integer pageSize = pageParam.getPageSize();
		Integer from = 0;
		Long total = 0L;
		String geoDistanceFieldName = "location";

		if (pageNo == 1) {
			from = 0;
		}
		else {
			from = (pageNo - 1) * pageSize; 
		}
		
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);
		GeoDistanceQueryBuilder queryBuilder =
				 QueryBuilders.geoDistanceQuery(geoDistanceFieldName)
		.point(latitude, longitude)
		.distance(distance, DistanceUnit.KILOMETERS)
		.optimizeBbox("memory")
		.geoDistance(GeoDistance.SLOPPY_ARC);
		searchRequestBuilder.setQuery(queryBuilder);
		
		GeoDistanceSortBuilder sort = new GeoDistanceSortBuilder(geoDistanceFieldName);
		sort.unit(DistanceUnit.KILOMETERS);
		sort.order(SortOrder.ASC);
		sort.point(latitude, longitude);
		searchRequestBuilder.addSort(sort);
		
		SearchResponse searchResponse = searchRequestBuilder.setFrom(from).setSize(pageSize).execute().actionGet();
		total = searchResponse.getHits().getTotalHits();
		pageParam.setTotalCount(total);
		
		logger.debug("lon=" + longitude + " lan=" + latitude + " dis="+distance + " pagesize=" + pageSize + " pageno=" + pageNo + " from=" + from +" total=" + total + " index=" + index + " type=" + type);
		logger.debug("request = " + searchRequestBuilder);
		logger.debug("resp = " + searchResponse);
		
		return searchResponse.getHits();
	}
}
