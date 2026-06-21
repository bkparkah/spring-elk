package com.kbds.pay.web;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/logger")
public class LoggerController {

    private static Logger logger = LoggerFactory.getLogger("http-logger");

    @Autowired
    private Producer<String, String> producer;

    @PostMapping("/appender")
    public void postMessage(@RequestParam("message") String message) {
        Mono.just(message)
                .subscribe(msg -> {
                    producer.send(new ProducerRecord<>("pay-logger", msg));
                });
    }

    @PostMapping("/errorLogging")
    public void loggingError(@RequestParam("message") String message) {
        String a = null;
        try {
            a.substring(1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @PostMapping("/logging")
    public void loggingMessage(@RequestParam("message") String message) {
        logger.info(message);
    }
}
