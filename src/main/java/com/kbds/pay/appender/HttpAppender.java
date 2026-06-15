package com.kbds.pay.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

public class HttpAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private String url;
    private String host;
    private WebClient webClient;
    // 1. Define the encoder field
    private Encoder<ILoggingEvent> encoder;

    // 2. Add setter/getter for XML injection
    public void setEncoder(Encoder<ILoggingEvent> encoder) {
        this.encoder = encoder;
    }

    public Encoder<ILoggingEvent> getEncoder() {
        return encoder;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void start() {
        // 3. Verify and start the encoder before the appender starts
        if (Objects.isNull(encoder)) {
            addError("No encoder set for the appender named [" + name + "].");
            return;
        }
        if (Objects.isNull(webClient)) {
            webClient = WebClient.builder()
                    .baseUrl(host)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .build();
        }

        encoder.start();
        super.start();
    }

    @Override
    public void stop() {
        if (encoder != null) {
            encoder.stop();
        }
        super.stop();
    }

    @Override
    protected void append(ILoggingEvent event) {
        byte[] byteArray = encoder.encode(event);
        String formattedMsg = StringUtils.newStringUtf8(byteArray);
        MultiValueMap <String, String> bodyContent = new LinkedMultiValueMap<>();
        bodyContent.add("message", formattedMsg);

        System.out.println(formattedMsg);

        webClient.post()
                .uri(url)
                .bodyValue(bodyContent)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }

    public static void main(String[] args) {

        MultiValueMap <String, String> bodyContent = new LinkedMultiValueMap<>();
        bodyContent.add("message", "message11111123333");

        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();

        webClient.post()
                .uri("/logger/appender")
                .bodyValue(bodyContent)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
