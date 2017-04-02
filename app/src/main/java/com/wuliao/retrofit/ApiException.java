package com.wuliao.retrofit;

/**
 * Created by Swy on 2017/4/1.
 */

public class ApiException extends RuntimeException {
    //错误码 参数key错误  请求内容info为空 当天请求次数用完  数据格式异常
    private static final int RESPONSE_KEY_ERROR=40001;
    private static final int RESPONSE_INFONULL_ERROR=40002;
    private static final int RESPONSE_EXHASUT_ERROR=40004;
    private static final int RESPONSE_FORMAT_ERROR=40007;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(int resultcode) {
        this(getApiExceptionMessage(resultcode));
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        String message="";
        switch (code){
            case RESPONSE_KEY_ERROR:
                message="参数key错误";
                break;
            case RESPONSE_INFONULL_ERROR:
                message="请求内容info为空";
                break;
            case RESPONSE_EXHASUT_ERROR:
                message="当天请求次数用完";
                break;
            case RESPONSE_FORMAT_ERROR:
                message="数据格式异常";
                break;
        }
        return message;
    }
}
