package io.code.framework.core.resp;

import io.code.framework.core.StatusEnum;

/**
 * 返回结果工具类
 *
 * @Author: zongz
 * @Date: 2024/9/26
 */
public class RespUtil {

    /**
     * 封装成功响应的方法
     *
     * @param data 响应数据
     * @param <T>  响应数据类型
     * @return reponse
     */
    public static <T> Resp<T> success(T data) {
        Resp<T> response = new Resp<>();
        response.setData(data);
        response.setCode(StatusEnum.SUCCESS.getCode());
        response.setMessage(StatusEnum.SUCCESS.getMessage());
        return response;
    }

    /**
     * 封装error的响应
     *
     * @param statusEnum error响应的状态值
     * @param <T>
     * @return
     */
    public static <T> Resp<T> fail(StatusEnum statusEnum) {
        return fail(statusEnum.getCode(), statusEnum.getMessage());
    }

    /**
     * 封装error的响应  可自定义错误信息
     *
     * @param code    error响应的状态值
     * @param message error响应的错误信息
     * @param <T>
     * @return
     */
    public static <T> Resp<T> fail(String code, String message) {
        Resp<T> response = new Resp<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }


}
