package club.yinlihu.entity;

import club.yinlihu.exception.ErrorCode;
import org.apache.commons.lang3.StringUtils;

/**
 * 通用返回参数
 */
public class ResultMap<T> {
    public final static String SUCCESS = "0";
    public final static String FAIL = "1";

    private String code;
    private String message;
    private T data;

    public static ResultMap ok(){
        return ok(null);
    }
    public static ResultMap ok(Object data){
        return ok(SUCCESS, data);
    }
    public static ResultMap ok(String code,Object data){
        return ok(code,"success" , data);
    }
    public static ResultMap ok(String code, String message,Object data){
        return new ResultMap(code, message, data);
    }

    public static ResultMap fail(String message){
        return fail(FAIL, message, null);
    }
    public static ResultMap fail(String code,Object data){
        return fail(code,"fail" , data);
    }
    public static ResultMap fail(String code, String message,Object data){
        return new ResultMap(code, message, data);
    }
    public static ResultMap fail(ErrorCode errorCode){
        return fail(errorCode.getCode(), errorCode.getDesc(), null);
    }

    /**
     * 是否正确
     * @param result
     * @return
     */
    public static boolean isSuccess(ResultMap result) {
        return StringUtils.equals(result.getCode(),SUCCESS);
    }

    public ResultMap(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
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
}
