package com.kbds.pay.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/s3logger")
public class S3LoggingController {
    private static Logger logger = LoggerFactory.getLogger("s3-logger");

    @PostConstruct
    public void init() {
        logger.info("Starting S3 Appender");
    }


    @PostMapping("/appender")
    public void postMessage(@RequestParam("message") String message) {
    }

    @PostMapping("/errorLogging")
    public void loggingError(@RequestParam("message") String message) {
    }

    @PostMapping("/logging")
    public void loggingMessage(@RequestParam("message") String message) {
        logger.info(message);
    }

}
