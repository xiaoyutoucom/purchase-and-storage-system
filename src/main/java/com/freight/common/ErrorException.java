package com.freight.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
// 返回对象
@ResponseBody
@Slf4j
@SuppressWarnings("unchecked")

public class ErrorException {
    //private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(Exception.class)
    public R errorException(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
        //logger.error(e.toString());
        return R.error().data(request.getRequestURL()).message(e.getMessage());
    }
}
