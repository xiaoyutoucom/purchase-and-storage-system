package com.freight.common;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "结果统一响应格式", tags = "结果统一响应格式")
public class R<T> {
    /**
     * 请求状态
     *      true 失败
     *      false 成功
     */
    @ApiModelProperty(value = "请求状态，true表示成功，false表示失败")
    private Boolean success;

    /**
     * 状态码
     *      200 请求正常
     *      400 参数异常
     *      444 HTTP无响应
     *      500 服务器内部错误
     *      501 空指针异常
     *      502 数据未找到
     */
    @ApiModelProperty(value = "状态码")
    private Integer code;

    /**
     * 返回信息描述
     */
    @ApiModelProperty(value = "回调信息描述")
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 返回的数据，键值对形式
     */
    @ApiModelProperty(value = "返回的数据")
    private T data;

    // 构造器私有
    private R(){}

    // 通用返回成功
    public static R ok() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    // 通用返回失败，内部错误
    public static R error() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.INNER_ERROR.getSuccess());
        r.setCode(ResultCodeEnum.INNER_ERROR.getCode());
        r.setMessage(ResultCodeEnum.INNER_ERROR.getMessage());
        return r;
    }

    // 通用返回失败，没有权限
    public static R noPermissions() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.NO_PERMISSIONS_ERROR.getSuccess());
        r.setCode(ResultCodeEnum.NO_PERMISSIONS_ERROR.getCode());
        r.setMessage(ResultCodeEnum.NO_PERMISSIONS_ERROR.getMessage());
        return r;
    }

    // 通用返回失败，请求被拒绝
    public static R reject() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.REJECT_ERROR.getSuccess());
        r.setCode(ResultCodeEnum.REJECT_ERROR.getCode());
        r.setMessage(ResultCodeEnum.REJECT_ERROR.getMessage());
        return r;
    }

    // 通用返回失败，请求方法不支持
    public static R doNotSupport() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.DO_NOT_SUPPORT_METHOD.getSuccess());
        r.setCode(ResultCodeEnum.DO_NOT_SUPPORT_METHOD.getCode());
        r.setMessage(ResultCodeEnum.DO_NOT_SUPPORT_METHOD.getMessage());
        return r;
    }

    // 通用返回失败，空指针
    public static R nullPoint() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.NULL_POINT.getSuccess());
        r.setCode(ResultCodeEnum.NULL_POINT.getCode());
        r.setMessage(ResultCodeEnum.NULL_POINT.getMessage());
        return r;
    }

    // 通用返回失败，数据未找到
    public static R dataNotFound() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.DATA_NOT_FOUND_ERROR.getSuccess());
        r.setCode(ResultCodeEnum.DATA_NOT_FOUND_ERROR.getCode());
        r.setMessage(ResultCodeEnum.DATA_NOT_FOUND_ERROR.getMessage());
        return r;
    }

    // 设置结果，形参为结果枚举
    public static R setResult(ResultCodeEnum result) {
        R r = new R();
        r.setSuccess(result.getSuccess());
        r.setCode(result.getCode());
        r.setMessage(result.getMessage());
        return r;
    }

    /**------------使用链式编程，返回类本身-----------**/

    // 自定义返回数据
    public R data(T data) {
        this.setData(data);
        return this;
    }

    // 自定义状态信息
    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    // 自定义状态码
    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    // 自定义返回结果
    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
}