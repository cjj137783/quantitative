package com.china.chen.quantitativecoretrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuantitativeCoreTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuantitativeCoreTradeApplication.class, args);
    }

}
