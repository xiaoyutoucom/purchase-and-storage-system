package com.freight;
//
//import org.apache.log4j.Logger;
import org.apache.commons.logging.Log;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.oas.annotations.EnableOpenApi;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.TimeZone;

@SpringBootApplication
@Configuration
@MapperScan("com.freight.mapper")
@EnableOpenApi
public class FreightApplication {
    @PostConstruct
    void setDefaultTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+12"));
    }
    public static void main(String[] args) throws IOException {
        SpringApplication.run(FreightApplication.class, args);
    }

}
