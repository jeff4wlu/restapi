package com.jeff4w.example.restapi.common.Restful;

import com.jeff4w.example.restapi.common.ErrorCode;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2018-12-27 10:41
 */

public class RestResultGenerator {

    public static <T> ResponseResult<T> genResult(boolean success, T data, String message) {
        ResponseResult<T> result = ResponseResult.newInstance();
        result.setResult(success);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    /**
     * success
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> genSuccessResult(T data) {

        return genResult(true, data, null);
    }

    /**
     * error message
     * @param message error message
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> genErrorResult(String message) {

        return genResult(false, null, message);
    }

    /**
     * error
     * @param error error enum
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> genErrorResult(ErrorCode error) {

        return genErrorResult(error.getMessage());
    }

    /**
     * success no message
     * @return
     */
    public static ResponseResult genSuccessResult() {
        return genSuccessResult(null);
    }

}

