package com.kbds.pay.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
        (scanBasePackages = {"com.kbds.pay.app"
                , "com.kbds.pay.web"
                , "com.kbds.pay.service"
                , "com.kbds.pay.config"})
public class PayApplication {
    private static Logger logger = LoggerFactory.getLogger(PayApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//
//            logger.info("Let's inspect the beans provided by Spring Boot:");
//
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                logger.info(beanName);
//            }
//
//        };
//    }
}
