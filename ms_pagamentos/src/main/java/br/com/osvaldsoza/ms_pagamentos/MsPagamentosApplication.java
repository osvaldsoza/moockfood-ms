package br.com.osvaldsoza.ms_pagamentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsPagamentosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPagamentosApplication.class, args);
    }
}
