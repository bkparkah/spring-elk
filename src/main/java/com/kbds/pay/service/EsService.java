package com.kbds.pay.service;


import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.stats.IndicesStats;

@Service
@ConditionalOnBean(ElasticsearchClient.class)
public class EsService {
    @Autowired
    private ElasticsearchClient esClient;

    @PostConstruct
    public void init() {
        try {
            final Map<String, IndicesStats> indices = esClient.indices().stats().indices();
            indices.entrySet().forEach(entry -> {
                System.out.println(entry.getKey());
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void getIndexStat(String indexName) {
        /*IndexRequest.
        esClient.indices().stats(indexRequest);*/
    }
}
