package com.lingjing.doujuan.infrastructure.utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

/**
 * API 返回结果封装类，用于统一接口返回格式。
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    // 状态码，例如 200 表示成功，500 表示服务器错误
    private int code;
    // 跟踪ID，用于跟踪请求的唯一标识符
    private String traceId;
    // 返回消息，用于描述操作结果
    private String message;
    // 返回的数据
    private T data;


    /**
     * 成功返回结果，默认状态码 200
     *
     * @param data 返回的数据
     * @param <T>  数据类型
     * @return ApiResult 实例
     */
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(200,null, "操作成功", data);
    }

    /**
     * 成功返回结果，自定义消息
     *
     * @param data    返回的数据
     * @param message 返回消息
     * @param <T>     数据类型
     * @return ApiResult 实例
     */
    public static <T> ApiResult<T> success(T data, String message) {
        return new ApiResult<>(200,null, message, data);
    }

    /**
     * 失败返回结果，默认状态码 500
     *
     * @param message 返回消息
     * @param <T>     数据类型
     * @return ApiResult 实例
     */
    public static <T> ApiResult<T> failed(String traceId, String message) {
        return new ApiResult<>(500,traceId, message, null);
    }

    /**
     * 失败返回结果，自定义状态码和消息
     *
     * @param code    状态码
     * @param message 返回消息
     * @param <T>     数据类型
     * @return ApiResult 实例
     */
    public static <T> ApiResult<T> failed(int code,String traceId, String message) {
        return new ApiResult<>(code,traceId, message, null);
    }

}

