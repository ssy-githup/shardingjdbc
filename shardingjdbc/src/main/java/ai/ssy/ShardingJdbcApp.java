package ai.ssy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: sharding-jdbc
 * @description: 启动类
 * @author: ssy
 * @create: 2020-01-10 14:13
 **/

@SpringBootApplication
@MapperScan("ai.ssy.mapper")
public class ShardingJdbcApp {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcApp.class, args);
    }
}