package com.kbds.pay.web;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.kbds.pay.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/es/search")
@ConditionalOnBean(ElasticsearchClient.class)
public class EsSearchController {
    @Autowired
    private EsService esService;

    @PostMapping("/")
    public Map<String, String> getIndexStat(@RequestBody Map<String, String> params, HttpServletRequest request, HttpServletResponse response) {
        String indexName = params.get("indexName");
        esService.getIndexStat(indexName);
        return null;
    }
}
