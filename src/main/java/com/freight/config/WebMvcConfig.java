package com.freight.config;

import com.freight.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration

public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/")
                .setViewName("forward:/swagger-ui/index.html");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
        registry
                .addInterceptor(new JwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static")
                .excludePathPatterns("/v3/*")
                .excludePathPatterns("/swagger-ui/*")
                .excludePathPatterns("/configuration/ui")
                .excludePathPatterns("/swagger-resources")
                .excludePathPatterns("/swagger-resources/*")
                .excludePathPatterns("/swagger-resources/configuration/*")
                .excludePathPatterns("/configuration/security")
                .excludePathPatterns("/error")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/**/favicon.ico")
        ;
        //registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**");
    }
//    @Override  //注释验证token
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.AuthenticationInterceptor("/**")
//                .excludePathPatterns("/static")
//                .excludePathPatterns("/swagger-ui.html")
//                .excludePathPatterns("/configuration/ui")
//                .excludePathPatterns("/swagger-resources")
//                .excludePathPatterns("/swagger-resources/configuration/ui")
//                .excludePathPatterns("/configuration/security")
//                .excludePathPatterns("/error")
//                .excludePathPatterns("/webjars/**")
//                .excludePathPatterns("/**/favicon.ico")
//                .setAuth(r -> {
//                    if(StpUtil.isLogin() == false) {
//                        // 与前端约定好，code=401时代表会话未登录
//                        SaRouter.back(SaResult.ok().setCode(401));
//                    }
//                })
//        ;    // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
//    }
//    @Bean
//    public AuthenticationInterceptor authenticationInterceptor() {
//        return new AuthenticationInterceptor();
//    }
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> arg0) {
//        // TODO Auto-generated method stub
//
//    }
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
//                .allowCredentials(true).maxAge(3600);
//    }
}