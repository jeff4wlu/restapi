package com.jeff4w.example.restapi.common;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2018-12-27 10:32
 */
//前端的同事要求说尽量不要有null，可有为空串“” 或者 0 或者 []， 但尽量不要null。
//实体类与json互转的时候 属性值为null的不参与序列化
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseResult<T> {

    private boolean result;

    private String message;

    private T data;

    public void setResult(boolean result) {
        this.result = result;
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

    public ResponseResult() {

    }

    public static <T> ResponseResult<T> newInstance() {
        return new ResponseResult<>();
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "success=" + result +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}

