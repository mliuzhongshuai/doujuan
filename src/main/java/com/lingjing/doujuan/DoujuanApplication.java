package com.lingjing.doujuan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
@MapperScan("com.lingjing.doujuan.infrastructure.storage")
public class DoujuanApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoujuanApplication.class, args);
    }

}
