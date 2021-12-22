//package com.example.purchase.config;
//
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
//import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class MyBatisPlusConfig {
//    @Bean
//    public MybatisPlusInterceptor paginationInterceptor(){
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
//        return interceptor;
//    }
//
//    @Bean
//    public ConfigurationCustomizer configurationCustomizer() {
//        return configuration -> configuration.setUseDeprecatedExecutor(false);
//    }
//    @Bean
//    ConfigurationCustomizer mybatisConfigurationCustomizer() {
//        return configuration -> configuration.addInterceptor(new com.github.pagehelper.PageInterceptor());
//    }
//}
