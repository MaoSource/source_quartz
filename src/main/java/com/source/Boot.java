package com.source;

import com.source.properties.SourceProperies;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Source
 * @date 2021/10/12/11:19
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableTransactionManagement
@MapperScan("com.source.mapper")
@EnableConfigurationProperties({SourceProperies.class})
public class Boot {
    public static void main(String[] args) {
        SpringApplication.run(Boot.class);
    }
}
