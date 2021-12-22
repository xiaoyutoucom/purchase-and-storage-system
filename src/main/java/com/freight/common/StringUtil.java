package com.freight.common;



import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class StringUtil {


//    // 根据Token获取用户信息 - 接口到这里，已经确定是有效的token了
//    public static  User getCurrentUser(HttpServletRequest request,UserService userService) {
//        Map<String, String> param = StringUtil.getHeadersInfo(request);
//        Map<String, String> map = Token.getParams(param.get("token"));
//
//        return userService.getUserWithoutPasswordByLoginId(map.get("id"));
//    }
    /**
     * 非空验证
     *

     */
    public static boolean Isnull(String num) {
        if(!"".equals(num) && num != null){
            return false;
        }
        return true;
    }
    /**
     * 将10进制转换为36进制
     *
     * @param num 传入的数字字符串
     * @return String
     */
    public String ten36(String num) {
        return Long.toString(Long.parseLong(num), 36).toUpperCase();
    }

    /**
     * 36进制转10进制
     *
     * @param num 传入的36进制的数字字符串
     * @return String
     */
    public String thirtySix10(String num) {
        return Long.valueOf(num, 36).toString();
    }

    /**
     * 自动生成GUID
     *
     * @return 字符型GUID串
     */
    public static String getGUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 格式化时间
     * @param date 时间对象
     * @return 时间字符串
     */
    public static String formatTime(String date) {
        Date d = new Date(date);
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        return ft.format(d);
    }
    /**
     * 格式化时间数值型
     * @param date 时间对象
     * @return 时间字符串
     */
    public static Long formatTimeNum(Date date) {
        SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMddHHmmss");
        return Long.parseLong(ft.format(date));
    }

    public static Map<String, Object> getParameter(String url) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            final String charset = "utf-8";
            url = URLDecoder.decode(url, charset);
            if (url.indexOf('?') != -1) {
                final String contents = url.substring(url.indexOf('?') + 1);
                String[] keyValues = contents.split("&");
                for (int i = 0; i < keyValues.length; i++) {
                    String key = keyValues[i].substring(0, keyValues[i].indexOf("="));
                    String value = keyValues[i].substring(keyValues[i].indexOf("=") + 1);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取request中参数
     *
     * @param request 页面请求
     */
    public static Map<String, Object> getRequestParameters(HttpServletRequest request) {
        String parameters = "";//请求参数
        if ("GET".equals(request.getMethod())) {//GET请求时的参数
            String urlParameter = request.getQueryString();//网址中的参数
            if (urlParameter != null && !"".equals(urlParameter)) {
                try {
                    urlParameter = URLDecoder.decode(urlParameter, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                urlParameter = "";
            }
            parameters = urlParameter;
        } else if ("POST".equals(request.getMethod())) {//POST请求时的参数
            String totalParameter = "";//表单及网址中全部参数
            Map<String, String[]> params = request.getParameterMap();
            int parametersNum = request.getParameterMap().size();//参数个数
            int flag = 1;
            for (String key : params.keySet()) {

                String[] values = params.get(key);
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    totalParameter += key + "=" + value;
                }
                if (flag < parametersNum) {
                    totalParameter += "&";
                }
                flag += 1;
            }
            parameters = totalParameter;
        }
        Map<String, Object> map = new HashMap<>();
        if (parameters.equals("")) {
            return map;
        }
        String[] arr = parameters.split("&");
        for (int i = 0; i < arr.length; i++) {
            String key = arr[i].substring(0, arr[i].indexOf("="));
            String value = arr[i].substring(arr[i].indexOf("=") + 1);
            map.put(key, value);
        }
        return map;
    }

    //get request headers
    public static Map<String, String> getHeadersInfo(HttpServletRequest request) {

        Map<String, String> map = new HashMap<String, String>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }
}