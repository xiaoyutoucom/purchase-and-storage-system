package com.freight.common;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(true,200,"请求成功"),
    PARAM_ERROR(false,400,"参数错误"),
    NO_PERMISSIONS_ERROR(false,401,"权限不足, 请重新登录"),
    REJECT_ERROR(false,403,"服务器拒绝本次访问"),
    HTTP_CLIENT_ERROR(false,444,"HTTP无响应"),
    INNER_ERROR(false,500,"服务器内部错误"),
    DO_NOT_SUPPORT_METHOD(false,501,"服务器不支持该请求中使用的方法"),
    NULL_POINT(false,505,"空指针异常"),
    DATA_NOT_FOUND_ERROR(false,404,"请求资源未找到");

    // 响应是否成功
    private Boolean success;
    // 响应状态码
    private Integer code;
    // 响应信息
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResultCodeEnum(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}