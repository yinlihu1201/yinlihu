package club.yinlihu.openapi;

/**
 * 通用返回参数
 */
public class ResultMap {
    public final static String SUCCESS = "0";
    public final static String FAIL = "1";

    private String code = FAIL;
    private String message = "fail";
    private Object data;

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

    public static ResultMap fail(){
        return fail(null);
    }

    public static ResultMap fail(Object data){
        return fail(FAIL, data);
    }

    public static ResultMap fail(String code,Object data){
        return fail(code,"fail" , data);
    }

    public static ResultMap fail(String code, String message,Object data){
        return new ResultMap(code, message, data);
    }

    public ResultMap(String code, String message, Object data) {
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
