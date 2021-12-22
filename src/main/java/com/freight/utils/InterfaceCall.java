package com.freight.utils;

import com.freight.entity.ReturnBack;
import com.google.gson.Gson;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
public class InterfaceCall {
//    @Autowired
    private RestTemplate restTemplate = new RestTemplate();;
    ReturnBack rb = new ReturnBack();
    //调用post通用方法
    public ReturnBack postjson(String url, HttpHeaders headers, Object json) {
        try
        {
            HttpEntity<Object> requestEntity = new HttpEntity<>(json,headers);
            restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            setRestTemplateEncode(restTemplate);
            ResponseEntity<String> postForEntity = restTemplate.postForEntity(url,requestEntity, String.class);

            String body = postForEntity.getBody();
            rb.setSuccess(true);
            rb.setValue(body);
            return rb;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            rb.setValue(e.toString());
            return rb;
        }
    }
    //调用post通用方法
    public ReturnBack postjson(String url,HttpHeaders headers,MultiValueMap<String, Object> object) {
        try
        {
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(object,headers);
            restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            setRestTemplateEncode(restTemplate);
            ResponseEntity<String> postForEntity = restTemplate.postForEntity(url,requestEntity, String.class);
            String body = postForEntity.getBody();
            rb.setSuccess(true);
            rb.setValue(body);
            return rb;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            rb.setValue(e.toString());
            return rb;
        }
    }
    //调用post通用方法
    public String getjson(String url,HttpHeaders headers,Object pbody,String object) {
        try
        {
            String authHeader = "Bearer " + new String(object);
            headers.set("authorization", authHeader);
            String body = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(pbody, headers),String.class).getBody();
            return body;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    //调用post通用方法
    public ReturnBack postobjectNobody(String url,HttpHeaders headers,String object) {
        try
        {
//            headers.setContentType(MediaType.APPLICATION_XML);
            String base64Credentials = new String(Base64.encodeBase64(object.getBytes()));
            String authHeader = "Basic " + base64Credentials;
            headers.set("Authorization", authHeader);
            HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
            rb.setSuccess(true);
            rb.setValue(responseEntity.getBody());
            return rb;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            rb.setValue(e.toString());
            return rb;
        }
    }
    //调用post通用方法
    public ReturnBack postobject(String url,HttpHeaders headers,Object pbody,String object) {
        try
        {
//            headers.setContentType(MediaType.APPLICATION_XML);
            String base64Credentials = new String(Base64.encodeBase64(object.getBytes()));
            String authHeader = "Basic " + base64Credentials;
            headers.set("Authorization", authHeader);
            HttpEntity<Object> requestEntity = new HttpEntity<>(pbody, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
            rb.setSuccess(true);
            rb.setValue(responseEntity.getBody());
            return rb;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            rb.setValue(e.toString());
            return rb;
        }
    }
    //调用get通用方法带参数
    public String getjson(String url, HttpHeaders headers) {
        try {
            ResponseEntity < String > exchange = restTemplate.exchange(url,
                            HttpMethod.GET,
                            new HttpEntity<String>(headers),
                            String.class);

            String body = exchange.getBody();
            Gson gson = new Gson();
            return body;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return e.toString();
        }
    }
    //调用get通用方法带参数
    public ReturnBack getjson(String url, HttpHeaders headers,Map<String, Object> param) {
        try
        {
            HttpEntity<String> requestEntity = new HttpEntity<>( headers);

            ResponseEntity<String> exchange = restTemplate.exchange(url,
                    HttpMethod.GET,
                    requestEntity, String.class, param);

            String body = exchange.getBody();
            rb.setSuccess(true);
            rb.setValue(body);
            return rb;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            rb.setValue(e.toString());
            return rb;
        }
    }
    //pdf文件下载
    public File downpdf(String url, HttpHeaders headers,String name) {
        final String APPLICATION_PDF = "application/pdf";
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            List list = new ArrayList<>();
            list.add(MediaType.valueOf(APPLICATION_PDF));
            headers.setAccept(list);

            ResponseEntity<byte[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<byte[]>(headers),
                    byte[].class);
            byte[] result = response.getBody();
            FileUtil fu = new FileUtil();
            return fu.getfile(result, name);
        }catch(Exception e)
        {
            return null;
        }
    }

    //解决中文乱码问题
    public static void setRestTemplateEncode(RestTemplate restTemplate) {
        if (null == restTemplate || ObjectUtils.isEmpty(restTemplate.getMessageConverters())) {
            return;
        }

        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        for (int i = 0; i < messageConverters.size(); i++) {
            HttpMessageConverter<?> httpMessageConverter = messageConverters.get(i);
            if (httpMessageConverter.getClass().equals(StringHttpMessageConverter.class)) {
                messageConverters.set(i, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            }
        }
    }
}
