package com.lingjing.doujuan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 豆卷应用主类
 * 启动Spring Boot应用，配置MyBatis扫描路径和Servlet组件扫描
 */
@ServletComponentScan
@SpringBootApplication
@MapperScan("com.lingjing.doujuan.infrastructure.storage")
public class DoujuanApplication {

    /**
     * 应用程序入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(DoujuanApplication.class, args);
    }

}
