package com.freight.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter implements Filter {
    final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CorsFilter.class);

//    private EnforcerFactory enforcer;
//    @Autowired
//    public CorsFilter(EnforcerFactory enforcer){
//        this.enforcer = enforcer;
//    }

    public void init(FilterConfig filterConfig) {}
    public void destroy() {}
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("过滤器初始化");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//        response.setHeader("Content-Type", "application/json");
        System.out.println("*****过滤器被使用("+request.getRequestURI()+")*****");

        chain.doFilter(request, response);
//        String user = request.getParameter("username");
//        String path = request.getRequestURI();
//        String method = request.getMethod();
//        if (path.contains("anon")||path.contains("swagger")||path.contains("api-docs")) {
//            chain.doFilter(request, response);
//        }else if (enforcer.enforce(user, path, method)) {
//            chain.doFilter(request, response);
//        } else {
//            logger.info("无权访问");
//            Map<String, Object> result = new HashMap<String, Object>();
//            result.put("code", 1001);
//            result.put("msg", "用户权限不足");
//            result.put("data",null);
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json");
//            response.getWriter().write(JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue));
//        }

    }

}