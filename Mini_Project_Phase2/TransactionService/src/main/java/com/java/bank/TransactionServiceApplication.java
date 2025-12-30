package com.java.bank;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.java.bank.client")
public class TransactionServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(TransactionServiceApplication.class, args);
    }


}
