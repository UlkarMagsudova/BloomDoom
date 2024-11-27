package com.ltclab.bloomdoomseller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BloomDoomSellerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloomDoomSellerApplication.class, args);
    }

}
