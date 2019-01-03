package com.jeff4w.example.restapi.common.Restful;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2018-12-27 10:32
 */
//前端的同事要求说尽量不要有null，可有为空串“” 或者 0 或者 []， 但尽量不要null。
//实体类与json互转的时候 属性值为null的不参与序列化
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "返回类")
public class ResponseResult<T> {

    @ApiModelProperty(value = "结果")
    private boolean result;
    @ApiModelProperty(value = "结果描述")
    private String message;
    @ApiModelProperty(value = "jwt token信息")
    private String flashToken;
    @ApiModelProperty(value = "结果数据")
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

    public String getFlashToken() {
        return flashToken;
    }

    public void setFlashToken(String flashToken) {
        this.flashToken = flashToken;
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

