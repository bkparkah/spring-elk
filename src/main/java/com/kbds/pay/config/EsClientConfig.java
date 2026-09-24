package com.kbds.pay.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EsClientConfig {

    @Bean
    @ConditionalOnProperty("elastic-search.host")
    public static ElasticsearchClient getEsClientFactory(@Value("${elastic-search.host}") String host
            , @Value("${elastic-search.port}") int port
            , @Value("${elastic-search.port}") String apiKey) {
        String serverUrl = "http://" + host + ":" + port;
        RestClientBuilder clientBuilder = RestClient.builder(HttpHost.create(serverUrl));
        if (StringUtils.isNotEmpty(apiKey)) {
            clientBuilder = clientBuilder.setDefaultHeaders(new Header[]{
                    new BasicHeader("Authorization", "ApiKey " + apiKey)
            });
        }

        RestClient restClient = clientBuilder.build();

        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new ElasticsearchClient(transport);
    }
}
