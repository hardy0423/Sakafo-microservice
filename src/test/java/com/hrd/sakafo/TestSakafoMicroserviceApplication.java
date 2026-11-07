package com.hrd.sakafo;

import org.springframework.boot.SpringApplication;

public class TestSakafoMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.from(SakafoMicroserviceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
