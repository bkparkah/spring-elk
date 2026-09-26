package com.kbds.pay.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;

@RestController
@RequestMapping("/logger")
public class LoggerController {

    private static Logger logger = LoggerFactory.getLogger(LoggerController.class);

    @PostConstruct
    public void init() {
        System.out.println("LoggerController Started");
        logger.info("LoggerController Started");
    }

    @PostMapping("/appender")
    public Object postMessage(@RequestBody Map<String, String> params) {
        logger.info("message={}", params.get("message"));

        return params;
    }

}
