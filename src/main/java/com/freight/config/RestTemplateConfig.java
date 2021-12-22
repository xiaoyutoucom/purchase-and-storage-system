//package com.freight.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.client.ClientHttpRequestFactory;
//import org.springframework.http.client.SimpleClientHttpRequestFactory;
//import org.springframework.web.client.RestTemplate;
//
//public class RestTemplateConfig {
//    @Bean
//    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
//        return new RestTemplate(factory);
//    }
//
//    @Bean
//    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setConnectTimeout(12000);
//        factory.setReadTimeout(12000);
//        return factory;
//    }
//
//}
