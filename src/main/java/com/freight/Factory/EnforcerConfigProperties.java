package com.freight.Factory;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "org.jcasbin")
public class EnforcerConfigProperties {

    /**
     * datasourceUrl 连接数据库url
     */
    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    /**
     * driverClassName 数据库驱动
     */
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    /**
     * datasourceUserName 连接数据库的用户名
     */
    @Value("${spring.datasource.username}")
    private String datasourceUserName;

    /**
     * databasePassword 连接数据库密码
     */
    @Value("${spring.datasource.password}")
    private String databasePassword;

//    @Bean
//    public JDBCAdapter jdbcAdapter() throws Exception {
//
//        return new JDBCAdapter(driverClassName, datasourceUrl, datasourceUserName,databasePassword );
//    }

}
