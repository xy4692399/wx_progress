package org.goodbye;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用启动类
 * MapperScan注解指定MyBatis mapper接口的扫描路径
 */
@SpringBootApplication
@MapperScan("org.goodbye.mapper") // 扫描Mapper接口所在的包
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
